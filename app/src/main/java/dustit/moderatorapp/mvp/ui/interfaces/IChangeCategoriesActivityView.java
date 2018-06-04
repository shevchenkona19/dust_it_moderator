package dustit.moderatorapp.mvp.ui.interfaces;

import java.util.List;

import dustit.moderatorapp.mvp.model.entities.Category;

public interface IChangeCategoriesActivityView extends IActivityView {
    void onCreatedCategory(String name);
    void onDeletedCategory(String id);
    void onFailedToCreateCategory(String name);
    void onFailedToDeleteCategory(String id);
    void onFailedToLoadCategories();
    void onCategoriesLoaded(List<Category> categories);
}
