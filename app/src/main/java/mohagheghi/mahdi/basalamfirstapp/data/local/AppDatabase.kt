package mohagheghi.mahdi.basalamfirstapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mohagheghi.mahdi.basalamfirstapp.data.local.dao.ProductDao
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance!!
            }
        }
    }
}