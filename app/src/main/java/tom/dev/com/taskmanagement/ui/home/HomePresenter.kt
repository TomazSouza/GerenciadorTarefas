package tom.dev.com.taskmanagement.ui.home

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tom.dev.com.taskmanagement.network.api.ApiServiceInterface
import tom.dev.com.taskmanagement.network.models.BaseResponse

class HomePresenter : HomeContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var view: HomeContract.View

    override fun getAllTasks(idUser: Int) {
        val subscribe = api.getAllTasks(idUser)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ baseResponse: BaseResponse? ->
                    view.showProgress(false)

                    if (baseResponse?.error!!)
                        view.failureGetTasks()
                    else if (baseResponse.message.equals("Nenhuma tarefa cadastrada", true))
                        view.nothinTask()
                    else
                        baseResponse.tasks?.let { view.successGetAllTasks(it) }
                }, {
                    view.showProgress(false)
                    view.failureGetTasks()
                })
        subscribe?.let { subscriptions.add(it) }
    }

    override fun deleteTask(idTask: Int, userId: Int) {
        val subscribe = api.deleteTask(idTask, userId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ baseResponse: BaseResponse? ->

                    view.showProgress(false)

                    if (baseResponse?.error!!)
                        view.failureDelete()
                    else
                        view.successDelete()

                }, { error ->

                    view.showProgress(false)
                    view.failureDelete()

                })
        subscribe?.let { subscriptions.add(it) }
    }

    override fun unsubscribe() {
        this.subscriptions.clear()
        this.subscriptions.dispose()
    }

    override fun attach(view: HomeContract.View) {
       this.view = view
    }

}