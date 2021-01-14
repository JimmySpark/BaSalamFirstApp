package mohagheghi.mahdi.basalamfirstapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import mohagheghi.mahdi.basalamfirstapp.di.module.ApiModule
import mohagheghi.mahdi.basalamfirstapp.di.module.DatabaseModule
import mohagheghi.mahdi.basalamfirstapp.di.module.ViewModelModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DatabaseModule::class, ViewModelModule::class])
interface AppComponent {

    fun getActivityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance @Named("db context") context: Context
        ): AppComponent
    }
}