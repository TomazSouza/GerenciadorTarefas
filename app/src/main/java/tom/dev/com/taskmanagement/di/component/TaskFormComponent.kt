package tom.dev.com.taskmanagement.di.component

import dagger.Component
import tom.dev.com.taskmanagement.di.module.TaskFormModule
import tom.dev.com.taskmanagement.ui.tasks.TaskFormActivity

@Component(modules = [TaskFormModule::class])
interface TaskFormComponent {

    fun inject(taskFormActivity: TaskFormActivity)

}