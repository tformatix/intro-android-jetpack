package at.fhooe.mc.jetpack.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * data access object for the blog post table
 * @see Dao
 */
@Dao
interface BlogPostDao {
    /**
     * @return all blog posts
     * @see Query
     */
    @Query("SELECT * FROM blog_post")
    fun getAll(): List<BlogPost>

    /**
     * insert a range of blog posts
     * @see Insert
     */
    @Insert
    fun insertAll(vararg blogPosts: BlogPost)

    /**
     * delete all blog posts
     * @see Query
     */
    @Query("DELETE FROM blog_post")
    fun deleteAll()
}