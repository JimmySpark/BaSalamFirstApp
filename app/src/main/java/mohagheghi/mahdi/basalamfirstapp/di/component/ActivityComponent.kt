package mohagheghi.mahdi.basalamfirstapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Subcomponent
import mohagheghi.mahdi.basalamfirstapp.view.ui.activity.MainActivity
import javax.inject.Named

@Subcomponent
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @Named("loading context") context: Context
        ): ActivityComponent
    }
}