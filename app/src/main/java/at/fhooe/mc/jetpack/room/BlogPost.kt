package at.fhooe.mc.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "blog_post")
data class BlogPost(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String?,
    @ColumnInfo val message: String?,
    @ColumnInfo(name = "posted_date_time") val postedDateTime: OffsetDateTime?
)
