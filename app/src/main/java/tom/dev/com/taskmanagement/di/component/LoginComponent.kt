package tom.dev.com.taskmanagement.di.component

import dagger.Component
import tom.dev.com.taskmanagement.di.module.LoginModule
import tom.dev.com.taskmanagement.ui.login.LoginActivity

@Component(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}