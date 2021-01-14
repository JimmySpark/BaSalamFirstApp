package mohagheghi.mahdi.basalamfirstapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}