package dustit.moderatorapp.mvp.presenter.activities;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dustit.moderatorapp.App;
import dustit.moderatorapp.mvp.datamanager.DataManager;
import dustit.moderatorapp.mvp.model.entities.Category;
import dustit.moderatorapp.mvp.model.entities.ResponseCode;
import dustit.moderatorapp.mvp.presenter.base.BasePresenter;
import dustit.moderatorapp.mvp.presenter.interfaces.IChangeCategoryPresenter;
import dustit.moderatorapp.mvp.ui.interfaces.IChangeCategoriesActivityView;
import rx.Subscriber;

/**
 * Created by User on 07.02.2018.
 */

public class ChangeCategoriesActivityPresenter extends BasePresenter<IChangeCategoriesActivityView> implements IChangeCategoryPresenter {
    @Inject
    DataManager dataManager;

    public ChangeCategoriesActivityPresenter() {
        App.get().getAppComponent().inject(this);
    }

    @Override
    public void reloadCategoriesList() {
        final List<Category> categories = new ArrayList<>();
        addSubscription(dataManager.getCategories().subscribe(new Subscriber<Category>() {
            @Override
            public void onCompleted() {
                getView().onCategoriesLoaded(categories);
            }

            @Override
            public void onError(Throwable e) {
                getView().onFailedToLoadCategories();
            }

            @Override
            public void onNext(Category category) {
                categories.add(category);
            }
        }));
    }

    @Override
    public void createCategory(final String name) {
        addSubscription(dataManager.createCategory(name).subscribe(new Subscriber<ResponseCode>() {
            @Override
            public void onCompleted() {
                getView().onCreatedCategory(name);
            }

            @Override
            public void onError(Throwable e) {
                getView().onFailedToCreateCategory(name);
            }

            @Override
            public void onNext(ResponseCode responseCode) {
                if (responseCode.getCode() != 200) {
                    getView().onFailedToCreateCategory(name);
                }
            }
        }));
    }

    @Override
    public void deleteCategory(final String id) {
        addSubscription(dataManager.deleteCategory(id).subscribe(new Subscriber<ResponseCode>() {
            @Override
            public void onCompleted() {
                getView().onDeletedCategory(id);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MY", e.getMessage());
                getView().onFailedToDeleteCategory(id);
            }

            @Override
            public void onNext(ResponseCode responseCode) {
                if (responseCode.getCode() != 200) {
                    getView().onFailedToDeleteCategory(id);
                }
            }
        }));
    }
}
