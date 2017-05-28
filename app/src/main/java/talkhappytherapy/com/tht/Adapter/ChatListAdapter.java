package talkhappytherapy.com.tht.Adapter;

        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.net.Uri;
        import android.text.util.Linkify;
        import android.view.View;
        import android.webkit.URLUtil;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.firebase.client.Query;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.PrintStream;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLConnection;
        import java.util.ArrayList;
        import java.util.List;

        import talkhappytherapy.com.tht.Model.Chat;
        import talkhappytherapy.com.tht.R;

/**
 * Created by Ashu on 20/11/15.
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat> {

    private final Activity activity;
    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;
    private TextView txt;
    private RelativeLayout chat_rel;
    private Bitmap bitmap;
    private ImageView image_link;
    private LinearLayout messagelay;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
        this.activity = activity;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Chat chat) {
        // Map a Chat object to an entry in our listview
        String author = chat.getAuthor();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        authorText.setText(author + ": ");
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.YELLOW);
        }
        else {
            if (author != null) {
                if (author.equalsIgnoreCase("admin") || author.equalsIgnoreCase("talk happy therapy")){
                    authorText.setTextColor(Color.RED);
                }
                else {
                    authorText.setTextColor(Color.GREEN);
                }
            }
        }

        chat_rel = (RelativeLayout) view.findViewById(R.id.chat_rel);

        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ll.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        // Defining the layout parameters of the TextView
        RelativeLayout.LayoutParams lr = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lr.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


        if (author != null) {
            if(author.equals(mUsername))
            {
                chat_rel.setLayoutParams(lr);
            }
            else
            {
                chat_rel.setLayoutParams(ll);
            }
        }

        txt = ((TextView) view.findViewById(R.id.message));
        image_link = (ImageView)view.findViewById(R.id.imagelink);
        messagelay = (LinearLayout)view.findViewById(R.id.messagelay);

        try {
            if (URLUtil.isValidUrl(chat.getMessage()) && chat.getMessage().contains("jpg") || chat.getMessage().contains("png") || chat.getMessage().contains("jpeg") || chat.getMessage().contains("gif")) {
                image_link.setVisibility(View.VISIBLE);
                txt.setVisibility(View.GONE);
                Glide.with(activity)
                        .load(chat.getMessage())
                        .into(image_link);

            } else {

                txt.setVisibility(View.VISIBLE);
                image_link.setVisibility(View.GONE);

                if(containsAKeyword(chat.getMessage()))
                {
                    authorText.setTextColor(Color.RED);
                    authorText.setText("Admin+ : ");
                    txt.setText("*Moderated by Admin*, Please refrain from using offensive language!");
                }
                else
                {
                    if (author != null && author.equals(mUsername)) {
                        authorText.setTextColor(Color.YELLOW);
                    }
                    else {
                        if (author != null) {
                            if (author.equalsIgnoreCase("admin") || author.equalsIgnoreCase("talk happy therapy")){
                                authorText.setTextColor(Color.RED);
                            }
                            else {
                                authorText.setTextColor(Color.GREEN);
                            }
                        }
                    }
                    txt.setText(chat.getMessage());
                    Linkify.addLinks(txt, Linkify.ALL);
                }
            }
        }
        catch (Exception ignored)
        {}
    }



    private String[] keywords = {"fuck", "chutiya", "ass", "dick", "motherf", "cunt", "suck", "pussy", "fart", "cock", "arse", "faggot", "bitch"};


    private boolean containsAKeyword(String myString){
        for(String keyword : keywords){
            if(myString.toLowerCase().contains(keyword)){
                return true;
            }
        }
        return false; // Never found match.
    }
}