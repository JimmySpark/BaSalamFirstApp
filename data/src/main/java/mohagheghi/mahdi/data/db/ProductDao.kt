package mohagheghi.mahdi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mohagheghi.mahdi.data.entity.ProductCache

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(products: List<ProductCache>)

    @Query("SELECT * FROM tbl_products ORDER BY rating DESC")
    fun getAll(): Flow<List<ProductCache>>

    @Query("DELETE FROM tbl_products")
    suspend fun deleteAll()

    /*@Query("SELECT COUNT(*) FROM tbl_products")
    fun getCount(): Int*/
}