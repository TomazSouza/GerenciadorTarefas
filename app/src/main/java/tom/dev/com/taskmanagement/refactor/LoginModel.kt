package tom.dev.com.taskmanagement.refactor

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tom.dev.com.taskmanagement.network.api.ApiServiceInterface
import tom.dev.com.taskmanagement.network.models.BaseResponse
import tom.dev.com.taskmanagement.network.models.User

class LoginModel {

    private val subscriptions = CompositeDisposable()

    fun execute( api: ApiServiceInterface, user: User, response: ContractResponse<BaseResponse>) {
        val subscribe = api.loginUser(user)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ baseResponse: BaseResponse? ->
                if (baseResponse != null)
                    response.onSuccess(baseResponse)
                else
                    response.onFailure(Throwable("Response error"))
            }, { error ->
                response.onFailure(error)
            })
        subscribe?.let { subscriptions.add(it) }
    }

}