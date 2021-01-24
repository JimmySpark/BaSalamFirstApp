package mohagheghi.mahdi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import mohagheghi.mahdi.data.entity.ProductCache

@Database(entities = [ProductCache::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        const val DB_NAME = "app_db"
    }
}