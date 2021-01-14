package mohagheghi.mahdi.basalamfirstapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import mohagheghi.mahdi.basalamfirstapp.data.local.AppDatabase
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule() {

    @Provides
    @Singleton
    fun provideRoom(@Named("db context") context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase) = appDatabase.productDao()
}