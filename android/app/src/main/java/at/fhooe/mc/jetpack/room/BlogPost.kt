package at.fhooe.mc.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

/**
 * entity of a blog post (table: blog_post)
 * @param id primary key
 * @param userName user name (user_name)
 * @param message some message
 * @param postedDateTime date, when user posted to blog (posted_date_time)
 * @see Entity
 */
@Entity(tableName = "blog_post")
data class BlogPost(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo val message: String?,
    @ColumnInfo(name = "posted_date_time") val postedDateTime: OffsetDateTime?
)
