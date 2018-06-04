package dustit.moderatorapp.mvp.presenter.base;

import dustit.moderatorapp.mvp.ui.interfaces.IView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shevc on 15.09.2017.
 * Let's GO!
 */

public abstract class BasePresenter<V extends IView> {
    private V v;
    private CompositeSubscription compositeSubscription;

    public BasePresenter(){
        compositeSubscription = new CompositeSubscription();
    }

    public V getView() {
        return v;
    }

    public void bind(V v) {
        this.v = v;
    }

    public void unbind() {
        compositeSubscription.unsubscribe();
        compositeSubscription.clear();
        v = null;
    }

    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }
}
