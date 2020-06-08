package tom.dev.com.taskmanagement.di.component

import dagger.Component
import tom.dev.com.taskmanagement.baseapp.BaseApp
import tom.dev.com.taskmanagement.di.module.ApplicationModule

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: BaseApp)

}