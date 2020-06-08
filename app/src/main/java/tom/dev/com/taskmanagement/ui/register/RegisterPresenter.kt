package tom.dev.com.taskmanagement.ui.register

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tom.dev.com.taskmanagement.network.api.ApiServiceInterface
import tom.dev.com.taskmanagement.network.models.BaseResponse
import tom.dev.com.taskmanagement.network.models.User

class RegisterPresenter : RegisterContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var view: RegisterContract.View

    override fun newUser(user: User) {
        val subscribe = api.registerUser(user)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ baseResponse: BaseResponse? ->

                    view.showProgress(false)
                    if (baseResponse?.error!!)
                        view.failureCreate()
                    else
                        baseResponse?.id_user?.let { view.successCreate(it) }

                }, { it ->

                    view.showProgress(false)
                    view.failureCreate()

                })

        subscribe?.let { subscriptions.add(it) }
    }

    override fun unsubscribe() {
        this.subscriptions.clear()
        this.subscriptions.dispose()
    }

    override fun attach(view: RegisterContract.View) {
        this.view = view
    }

}