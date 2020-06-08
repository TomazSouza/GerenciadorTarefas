package tom.dev.com.taskmanagement.di.component

import dagger.Component
import tom.dev.com.taskmanagement.di.module.HomeModule
import tom.dev.com.taskmanagement.ui.home.HomeActivity

@Component(modules = [HomeModule::class])
interface HomeComponent {

    fun inject(homeActivity: HomeActivity)

}