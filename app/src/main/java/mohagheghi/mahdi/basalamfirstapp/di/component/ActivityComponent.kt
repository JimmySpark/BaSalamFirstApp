package mohagheghi.mahdi.basalamfirstapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Subcomponent
import mohagheghi.mahdi.basalamfirstapp.view.util.Loading
import javax.inject.Named

@Subcomponent
interface ActivityComponent {

    fun getLoading(): Loading

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance @Named("loading context") context: Context): ActivityComponent
    }
}