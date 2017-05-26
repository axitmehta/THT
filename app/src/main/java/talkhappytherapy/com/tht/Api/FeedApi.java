package talkhappytherapy.com.tht.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import talkhappytherapy.com.tht.Model.FeedModel;
import talkhappytherapy.com.tht.Model.StatusModel;


public interface FeedApi {

    @GET("content.json")
    Call<FeedModelResponse> getfeed();


    class FeedModelResponse
    {
        @SerializedName("events")
        @Expose
        public List<FeedModel> FeedModelList = new ArrayList<>();

        public List<FeedModel> getFeedModelList() {
            return FeedModelList;
        }

        public void setFeedModelList(List<FeedModel> FeedModelList) {
            this.FeedModelList = FeedModelList;
        }
    }
}
