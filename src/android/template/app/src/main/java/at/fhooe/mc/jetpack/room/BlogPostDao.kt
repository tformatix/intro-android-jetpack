package at.fhooe.mc.jetpack.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * data access object for the blog post table
 * @see Dao
 */
@Dao
interface BlogPostDao {
    /**
     * @return all blog posts (flow reacts on updates)
     * @see Query
     */
    @Query("SELECT * FROM blog_post")
    fun getAll(): Flow<List<BlogPost>>

    /**
     * insert a range of blog posts
     * @see Insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg blogPosts: BlogPost)

    /**
     * delete all blog posts
     * @see Query
     */
    @Query("DELETE FROM blog_post")
    fun deleteAll()
}