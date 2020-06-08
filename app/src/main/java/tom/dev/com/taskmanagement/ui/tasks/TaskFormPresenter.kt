package tom.dev.com.taskmanagement.ui.tasks

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tom.dev.com.taskmanagement.network.api.ApiServiceInterface
import tom.dev.com.taskmanagement.network.models.BaseResponse
import tom.dev.com.taskmanagement.network.models.Tasks

class TaskFormPresenter : TaskFormContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var view: TaskFormContract.View

    override fun newTask(tasks: Tasks) {
        val subscribe = api.createTask(tasks)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ baseResponse: BaseResponse? ->

                    view.showProgress(false)

                    if (baseResponse?.error!!)
                        view.failureCreate()
                    else
                        view.successCreate()

                }, { error ->

                    view.showProgress(false)
                    view.failureCreate()

                })
        subscribe?.let { subscriptions.add(it) }
    }

    override fun updateTask(idTask: Int, tasks: Tasks) {
        val subscribe = api.updateTask(idTask, tasks)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ baseResponse: BaseResponse? ->

                view.showProgress(false)

                if (baseResponse?.error!!)
                    view.failureUpdate()
                else
                    view.successUpdate()

            }, { error ->

                view.showProgress(false)
                view.failureCreate()

            })
        subscribe?.let { subscriptions.add(it) }
    }

    override fun unsubscribe() {
        this.subscriptions.clear()
        this.subscriptions.dispose()
    }

    override fun attach(view: TaskFormContract.View) {
        this.view = view
    }

}