package tom.dev.com.taskmanagement.di.module

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import tom.dev.com.taskmanagement.ui.login.LoginContract
import tom.dev.com.taskmanagement.ui.login.LoginPresenter

@Module
class LoginModule(private var appCompatActivity: AppCompatActivity) {

    @Provides
    fun provideActivity(): AppCompatActivity {
        return appCompatActivity
    }

    @Provides
    fun provideLoginPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }

}