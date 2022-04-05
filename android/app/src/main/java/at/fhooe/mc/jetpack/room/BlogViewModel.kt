package at.fhooe.mc.jetpack.room

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class BlogViewModel(private val application: Application) : ViewModel() {
    var allBlogs: Flow<List<BlogPost>>

    init {
        val app = AppDatabase.getDatabase(application)
        allBlogs = app.blogPostDao().getAll()
    }
}