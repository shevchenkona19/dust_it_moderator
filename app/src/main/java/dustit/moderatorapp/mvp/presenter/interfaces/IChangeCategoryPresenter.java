package dustit.moderatorapp.mvp.presenter.interfaces;

/**
 * Created by User on 07.02.2018.
 */

public interface IChangeCategoryPresenter {

    void reloadCategoriesList();

    void createCategory(String name);

    void deleteCategory(String id);
}
