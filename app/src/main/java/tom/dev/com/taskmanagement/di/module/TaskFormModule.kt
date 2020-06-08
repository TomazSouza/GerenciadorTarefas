package tom.dev.com.taskmanagement.di.module

import dagger.Module
import dagger.Provides
import tom.dev.com.taskmanagement.network.api.ApiServiceInterface
import tom.dev.com.taskmanagement.ui.tasks.TaskFormPresenter
import tom.dev.com.taskmanagement.ui.tasks.TaskFormContract

@Module
class TaskFormModule {

    @Provides
    fun provideTaskFormModule(): TaskFormContract.Presenter {
        return TaskFormPresenter()
    }

    @Provides
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }

}