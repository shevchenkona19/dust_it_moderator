package dustit.moderatorapp.mvp.presenter.activities;

import javax.inject.Inject;

import dustit.moderatorapp.App;
import dustit.moderatorapp.mvp.datamanager.DataManager;
import dustit.moderatorapp.mvp.model.entities.ResponseCode;
import dustit.moderatorapp.mvp.presenter.base.BasePresenter;
import dustit.moderatorapp.mvp.ui.interfaces.ISettingsActivityView;
import rx.Subscriber;

public class SettingsActivityPresenter extends BasePresenter<ISettingsActivityView> {
    @Inject
    DataManager dataManager;

    public SettingsActivityPresenter() {
        App.get().getAppComponent().inject(this);
    }

    public void logout() {
        dataManager.deleteCurrentToken();
        getView().onSuccess();
    }
}
