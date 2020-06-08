package tom.dev.com.taskmanagement.baseapp

import android.app.Application
import tom.dev.com.taskmanagement.di.component.ApplicationComponent
import tom.dev.com.taskmanagement.di.component.DaggerApplicationComponent
import tom.dev.com.taskmanagement.di.module.ApplicationModule
import tom.dev.com.taskmanagement.network.models.Tasks
import tom.dev.com.taskmanagement.network.models.User

class BaseApp : Application() {

    private lateinit var mComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()
    }

    private fun setup() {
        this.mComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        this.mComponent.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return mComponent
    }

    companion object {
        lateinit var instance: BaseApp private set
        var sUserId: Int = 0
        var sIdUser: Int = 0
        var sIdTask: Int = 0
        var sTasks: List<Tasks> = ArrayList()
        var sIsChecked: Boolean = false
        var sUser: User? = null
    }

    fun getIsChecked(): Boolean {
        return sIsChecked
    }

    fun setIdUser(idUser: Int?) {
        if (idUser != null) {
            sIdUser = idUser
        }
    }

    fun destroySession() {
        sUserId = 0
        sIdUser = 0
        sIdTask = 0
        sTasks = ArrayList()
        sIsChecked = false
        sUser = null
    }

    fun getIdUser(): Int = sIdUser

    fun setTasks(tasks: List<Tasks>) {
        sTasks = tasks
    }

    fun getTasks() = sTasks

    fun getDataUser(): User? {
        return sUser
    }

}