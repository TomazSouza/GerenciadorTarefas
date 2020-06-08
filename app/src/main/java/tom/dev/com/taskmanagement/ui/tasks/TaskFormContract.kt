package tom.dev.com.taskmanagement.ui.tasks

import tom.dev.com.taskmanagement.baseapp.BaseContract
import tom.dev.com.taskmanagement.network.models.Tasks

interface TaskFormContract {

    interface View : BaseContract.View {
        fun successCreate()
        fun failureCreate()
        fun successUpdate()
        fun failureUpdate()
    }

    interface Presenter : BaseContract.Presenter<TaskFormContract.View> {
        fun newTask(tasks: Tasks)
        fun updateTask(idTask: Int, tasks: Tasks)
    }

}