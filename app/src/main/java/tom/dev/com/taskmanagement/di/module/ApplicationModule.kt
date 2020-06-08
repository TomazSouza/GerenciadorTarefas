package tom.dev.com.taskmanagement.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import tom.dev.com.taskmanagement.baseapp.BaseApp
import tom.dev.com.taskmanagement.di.scope.PerApplication
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }

}