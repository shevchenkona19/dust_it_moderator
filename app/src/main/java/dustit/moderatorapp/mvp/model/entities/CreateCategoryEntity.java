package dustit.moderatorapp.mvp.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 07.02.2018.
 */

public class CreateCategoryEntity {

    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    public CreateCategoryEntity(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
