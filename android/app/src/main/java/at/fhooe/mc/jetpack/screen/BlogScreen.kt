package at.fhooe.mc.jetpack.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.fhooe.mc.jetpack.BlogManager
import at.fhooe.mc.jetpack.room.BlogPost
import at.fhooe.mc.jetpack.room.BlogViewModel
import at.fhooe.mc.jetpack.ui.theme.FHred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime

/**
 * Blog screen
 * user can read and write message to the blog
 * @see Composable
 */
@Composable
fun BlogScreen(viewModel: BlogViewModel =
                   BlogViewModel(LocalContext.current.applicationContext as Application)) {

    // collect changes from the viewModel
    val list: List<BlogPost> by viewModel.allBlogs.collectAsState(initial = emptyList())

    // get CoroutineScope because message receive need to be in a different thread
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val username = BlogManager.getUsername(context)

    // scaffold is used to separate the screen in multiple parts (top, center, bottom,...)
    Scaffold(
        bottomBar = { MessageBox() },
        floatingActionButton = {
            FloatingActionButton(backgroundColor = FHred, onClick = {

                // refresh button is pressed, get changes from the backend
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        BlogManager.getBlogs(context)
                    }
                }
            }) {
                Icon(Icons.Filled.Refresh,"")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        // TODO: create a "scrollable list" of the messages
    }
}

/**
 * display each message in a single row (with a surface)
 * @see Composable
 */
@Composable
fun MessageRow(blogPost: BlogPost, localUsername: String) {
    // TODO: implement a composable function, which displays a single message
}

/**
 * Box at the bottom bar where the user can enter a message
 * and a send button
 * @see Composable */
@Composable
fun MessageBox() {
    // TODO: implement a box at the bottom bar, where the user can enter a new message
}