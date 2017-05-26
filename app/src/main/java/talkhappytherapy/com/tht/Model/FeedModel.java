package talkhappytherapy.com.tht.Model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FeedModel {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("venue")
    @Expose
    private String venue;

    @SerializedName("datetime")
    @Expose
    private String datetime;

    @SerializedName("imgurl")
    @Expose
    private String imgurl;

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The id
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @return The datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * @param datetime The id
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


}
