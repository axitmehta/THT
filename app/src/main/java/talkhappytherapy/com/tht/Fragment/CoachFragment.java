package talkhappytherapy.com.tht.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import talkhappytherapy.com.tht.Adapter.ChatListAdapter;
import talkhappytherapy.com.tht.Model.Chat;
import talkhappytherapy.com.tht.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Alienware on 26-05-2017.
 */

public class CoachFragment extends Fragment {


    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://talk-happy-therapy.firebaseio.com/";

    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;
    private EditText inputText;
    private FirebaseUser user;
    private ProgressWheel progresswheel;
    private MaterialTextField mat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coach, container, false);
        Firebase.setAndroidContext(getActivity());
        user = FirebaseAuth.getInstance().getCurrentUser();
        initViews(view);
        return view;
    }

    private void initViews(View view){
        // Make sure we have a mUsername
        setupUsername();

        //setTitle("Chatting as " + mUsername);

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("chat");

        // Setup our input methods. Enter key on the keyboard or pushing the send button
        inputText = (EditText) view.findViewById(R.id.edit_text);
        mat = (MaterialTextField) view.findViewById(R.id.matedit);
        mat.expand();

        progresswheel = (ProgressWheel)view.findViewById(R.id.progress_wheel);

        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputText.getText().toString();
                if (!input.equals("")) {
                    // Create our 'model', a Chat object
                    Chat chat = new Chat(input, mUsername);
                    // Create a new, auto-generated child of that chat location, and save our chat data there
                    mFirebaseRef.push().setValue(chat);
                    inputText.setText("");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = (ListView) getActivity().findViewById(R.id.list);
        //final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), getActivity(), R.layout.chat_message, mUsername);
        try {
            listView.setAdapter(mChatListAdapter);
        }catch (Exception e)
        {
            System.out.println(e);
        }
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    //Toast.makeText(getContext(), "Connected to Chat", Toast.LENGTH_SHORT).show();
                    progresswheel.setVisibility(View.GONE);
                } else {
                    //Toast.makeText(getContext(), "Disconnected from Chat", Toast.LENGTH_SHORT).show();
                    progresswheel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void setupUsername() {
        if (user != null) {
            if(user.isAnonymous())
            {
                SharedPreferences prefs = getContext().getSharedPreferences("ChatPrefs", 0);
                mUsername = prefs.getString("username", null);
                if (mUsername == null) {
                    Random r = new Random();
                    // Assign a random user name if we don't have one saved.
                    mUsername = "THT(Anonymous)" + r.nextInt(100000);
                    prefs.edit().putString("username", mUsername).apply();
                }
            }
            else {// User is signed in
                mUsername = user.getDisplayName();
            }
        } else {
            SharedPreferences prefs = getContext().getSharedPreferences("ChatPrefs", 0);
            mUsername = prefs.getString("username", null);
            if (mUsername == null) {
                Random r = new Random();
                // Assign a random user name if we don't have one saved.
                mUsername = "THT (Anonymity)" + r.nextInt(100000);
                prefs.edit().putString("username", mUsername).apply();
            }
        }
    }

    private void sendMessage() {

        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
        }
    }

}