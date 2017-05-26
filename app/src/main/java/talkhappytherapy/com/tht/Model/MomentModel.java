package talkhappytherapy.com.tht.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MomentModel {

    @SerializedName("val")
    @Expose
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
