package tom.dev.com.taskmanagement.baseapp

class BaseContract {

    interface Presenter<in T> {
        fun unsubscribe()
        fun attach(view: T)
    }

    interface View {
        fun showProgress(show: Boolean)
    }

}