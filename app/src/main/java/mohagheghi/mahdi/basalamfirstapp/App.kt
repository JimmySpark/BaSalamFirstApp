package mohagheghi.mahdi.basalamfirstapp

import android.app.Application
import mohagheghi.mahdi.basalamfirstapp.di.component.AppComponent
import mohagheghi.mahdi.basalamfirstapp.di.component.DaggerAppComponent

class App : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.factory().create(this, 4)
    }

    fun getComponent() = component
}