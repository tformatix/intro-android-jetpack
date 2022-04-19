package at.fhooe.mc.jetpack

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import at.fhooe.mc.jetpack.room.AppDatabase
import at.fhooe.mc.jetpack.room.BlogPost
import org.openapitools.client.apis.BlogPostApi
import org.openapitools.client.models.BlogPostModel
import java.lang.Exception
import java.net.SocketTimeoutException

/**
 * class for manages backend call and sharedPreferences
 */
object BlogManager {

    /**
     * refresh blogs from api and update values in room db
     * @see Context
     */
    fun getBlogs(current: Context) {
        val blogPostApi = BlogPostApi(Constants.HTTP_BASE_URL)

        try {
            val blogs = blogPostApi.blogPostGet()

            for (i in blogs) {
                val blogPost = i.id?.let {
                    BlogPost(it, i.userName, i.message, i.postedDateTime)
                }

                blogPost?.let {
                    AppDatabase.getDatabase(current).blogPostDao().insertAll(it)
                }
            }
        }
        catch (e: SocketTimeoutException) {
            Log.e(TAG_MAIN_ACTIVITY, "socket time out exception")
        }
    }

    /**
     * sends a message to the cloud via HTTP POST
     * @param message entered message of the user
     */
    fun postMessage(message: String, context: Context) {
        val blogPostApi = BlogPostApi(Constants.HTTP_BASE_URL)

        try {
            val blogPost = BlogPostModel(
                userName = getUsername(context),
                message = message
            )

            blogPostApi.blogPostPost(blogPost)
        }
        catch (e: Exception) {
            Log.e(TAG_MAIN_ACTIVITY, e.message.toString())
        }
    }

    /**
     * returns the username stored in the sharedPreferences
     * @see SharedPreferences
     * @return username
     */
    fun getUsername(context: Context): String {
        val sharedPrefs = context.getSharedPreferences(
            Constants.SHARED_PREFS_USER,
            Context.MODE_PRIVATE
        )

        return sharedPrefs.getString(Constants.SHARED_PREFS_USER_NAME, "").orEmpty()
    }
}