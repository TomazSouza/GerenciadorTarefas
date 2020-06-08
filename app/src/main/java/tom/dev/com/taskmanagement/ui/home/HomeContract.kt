package tom.dev.com.taskmanagement.ui.home

import tom.dev.com.taskmanagement.baseapp.BaseContract
import tom.dev.com.taskmanagement.network.models.Tasks

interface HomeContract {

    interface Presenter: BaseContract.Presenter<HomeContract.View> {
        fun getAllTasks(idUser: Int)
        fun deleteTask(idTask: Int, userId: Int)
    }

    interface View: BaseContract.View {
        fun successGetAllTasks( tasks: List<Tasks>)
        fun failureGetTasks()
        fun failureDelete()
        fun successDelete()
        fun nothinTask()
    }

}