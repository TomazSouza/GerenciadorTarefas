package tom.dev.com.taskmanagement.ui.login

import tom.dev.com.taskmanagement.baseapp.BaseContract
import tom.dev.com.taskmanagement.network.models.User

interface LoginContract {

    interface View : BaseContract.View {
        fun successSignInUser(idUser: Int)
        fun failureSignInUser(message: String)
    }

    interface Presenter : BaseContract.Presenter<LoginContract.View> {
        fun signInUser(user: User)
        fun verifyApiOnline()
    }

}