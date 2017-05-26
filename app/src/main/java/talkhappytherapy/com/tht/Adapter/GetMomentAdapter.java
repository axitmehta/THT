package talkhappytherapy.com.tht.Adapter;

/**
 * Created by Karthik on 22-06-2016.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import talkhappytherapy.com.tht.Model.FeedModel;
import talkhappytherapy.com.tht.Model.MomentModel;
import talkhappytherapy.com.tht.R;


public class GetMomentAdapter extends RecyclerView.Adapter<GetMomentAdapter.MyViewHolder>  {

    private List<MomentModel> listData;
    private Activity activity;
    private String val;

    public GetMomentAdapter(List<MomentModel> list, Activity activity) {
        this.listData = list;
        this.activity = activity;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_for_moment, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        val = listData.get(position).getVal();
        holder.card.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                int position =  holder.getAdapterPosition();
                System.out.println(position);
                Toast.makeText(v.getContext(), "You just clicked on a expression. Subsequent pages will be added!", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, description, venue, datetime;
        public ImageView back_img;
        public CardView card;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.textView);
            description = (TextView) view.findViewById(R.id.textView1);
            venue = (TextView) view.findViewById(R.id.textView2);
            datetime = (TextView) view.findViewById(R.id.textView3);
            back_img = (ImageView)view.findViewById(R.id.imageView);
            card = (CardView) view.findViewById(R.id.card);
        }
    }
}
