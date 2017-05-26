package talkhappytherapy.com.tht.Adapter;

/**
 * Created by Karthik on 22-06-2016.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import talkhappytherapy.com.tht.R;


public class GetFeedAdapter extends RecyclerView.Adapter<GetFeedAdapter.MyViewHolder>  {

    private List<FeedModel> listData;
    private Activity activity;

    public GetFeedAdapter(List<FeedModel> list, Activity activity) {
        this.listData = list;
        this.activity = activity;
    }

    public GetFeedAdapter(ArrayList<List<FeedModel>> data) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_for_feed, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        holder.title.setText(listData.get(position).getTitle());
        holder.description.setText(listData.get(position).getDescription());
        holder.venue.setText(listData.get(position).getVenue());
        holder.datetime.setText(listData.get(position).getDatetime());
        Glide.with(activity)
                .load(listData.get(position).getImgurl())
                .into(holder.back_img);
        System.out.println("THIS IS THE IMAGE : "+listData.get(position).getImgurl());
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
