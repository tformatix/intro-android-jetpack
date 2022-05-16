package at.fhooe.mc.jetpack.room

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

/**
 * ViewModel which holds a list of the actual BlogPost objects
 * @see Flow (asynchronous data stream)
 * @param application application context
 */
class BlogViewModel(private val application: Application) : ViewModel() {

    // Flow always holds the actual list of BlogPosts
    var allBlogs: Flow<List<BlogPost>>

    init {
        val app = AppDatabase.getDatabase(application)

        // fetch all BlogPosts from the room db
        allBlogs = app.blogPostDao().getAll()
    }
}