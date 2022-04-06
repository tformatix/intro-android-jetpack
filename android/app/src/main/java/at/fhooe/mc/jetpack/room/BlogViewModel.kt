package at.fhooe.mc.jetpack.room

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

class BlogViewModel(private val application: Application) : ViewModel() {
    var allBlogs: Flow<List<BlogPost>>

    init {
        val app = AppDatabase.getDatabase(application)
        allBlogs = app.blogPostDao().getAll()
    }
}