package tom.dev.com.taskmanagement.ui.register

import tom.dev.com.taskmanagement.baseapp.BaseContract
import tom.dev.com.taskmanagement.network.models.User

interface RegisterContract {

    interface View: BaseContract.View {
        fun successCreate(idUser: Int)
        fun failureCreate()
    }

    interface Presenter: BaseContract.Presenter<RegisterContract.View> {
        fun newUser(user: User)
    }

}