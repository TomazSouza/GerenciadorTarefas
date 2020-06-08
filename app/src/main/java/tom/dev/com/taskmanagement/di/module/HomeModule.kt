package tom.dev.com.taskmanagement.di.module

import dagger.Module
import dagger.Provides
import tom.dev.com.taskmanagement.network.api.ApiServiceInterface
import tom.dev.com.taskmanagement.ui.home.HomeContract
import tom.dev.com.taskmanagement.ui.home.HomePresenter

@Module
class HomeModule {

    @Provides
    fun provideHomePresenter(): HomeContract.Presenter {
        return HomePresenter()
    }

    @Provides
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }

}