package at.fhooe.mc.jetpack.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BlogPostDao {
    @Query("SELECT * FROM blog_post")
    fun getAll(): List<BlogPost>

    @Insert
    fun insertAll(vararg blogPosts: BlogPost)

    @Query("DELETE FROM blog_post")
    fun deleteAll()
}