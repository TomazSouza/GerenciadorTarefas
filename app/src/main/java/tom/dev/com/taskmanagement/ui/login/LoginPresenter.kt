package tom.dev.com.taskmanagement.ui.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tom.dev.com.taskmanagement.network.api.ApiServiceInterface
import tom.dev.com.taskmanagement.network.models.BaseResponse
import tom.dev.com.taskmanagement.network.models.User

class LoginPresenter : LoginContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var view: LoginContract.View

    override fun signInUser(user: User) {

        val subscribe = api.loginUser(user)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ baseResponse: BaseResponse? ->

                view.showProgress(false)
                if (baseResponse?.error!!)
                    view.failureSignInUser(baseResponse.message!!)
                else
                    baseResponse.id_user?.let { view.successSignInUser(it) }

            }, { error ->

                view.showProgress(false)
                view.failureSignInUser(error.message!!)

            })
        subscribe?.let { subscriptions.add(it) }
    }

    override fun unsubscribe() {
        this.subscriptions.clear()
        this.subscriptions.dispose()
    }

    override fun attach(view: LoginContract.View) {
        this.view = view
    }

    override fun verifyApiOnline() {

    }
}