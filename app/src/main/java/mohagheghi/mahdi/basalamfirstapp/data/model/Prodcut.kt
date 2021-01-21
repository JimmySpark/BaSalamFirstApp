package mohagheghi.mahdi.basalamfirstapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_products")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    @Embedded val photo: Photo,
    @Embedded val vendor: Vendor,
    val weight: Int,
    val price: Long,
    @Embedded val rating: Rating
) {
    data class Photo(
        @ColumnInfo(name = "photo_url") val url: String
    )

    data class Vendor(
        @ColumnInfo(name = "vendor_name") val name: String
    )

    data class Rating(
        val rating: Float,
        @ColumnInfo(name = "rating_count") val count: Int
    )
}