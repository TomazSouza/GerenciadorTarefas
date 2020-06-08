package tom.dev.com.taskmanagement.di.component

import dagger.Component
import tom.dev.com.taskmanagement.di.module.RegisterModule
import tom.dev.com.taskmanagement.ui.register.RegisterActivity

@Component(modules = [RegisterModule::class])
interface RegisterComponent {

    fun inject(registerActivity: RegisterActivity)

}