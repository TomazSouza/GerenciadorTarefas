package tom.dev.com.taskmanagement.di.module

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import tom.dev.com.taskmanagement.network.api.ApiServiceInterface
import tom.dev.com.taskmanagement.ui.register.RegisterContract
import tom.dev.com.taskmanagement.ui.register.RegisterPresenter

@Module
class RegisterModule(private var appCompatActivity: AppCompatActivity) {

    @Provides
    fun provideActivity(): AppCompatActivity {
        return appCompatActivity
    }

    @Provides
    fun provideRegisterPresenter(): RegisterContract.Presenter {
        return RegisterPresenter()
    }

    @Provides
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }

}