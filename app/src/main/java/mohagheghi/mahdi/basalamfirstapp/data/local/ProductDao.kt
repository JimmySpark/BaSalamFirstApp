package mohagheghi.mahdi.basalamfirstapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(products: List<Product>)

    @Query("SELECT * FROM tbl_products ORDER BY rating DESC")
    fun getAll(): LiveData<List<Product>>

    @Query("DELETE FROM tbl_products")
    fun deleteAll()

    /*@Query("SELECT COUNT(*) FROM tbl_products")
    fun getCount(): Int*/
}