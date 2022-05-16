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
        // create a "scrollable list" from the messages
        LazyColumn(modifier = Modifier.padding(it)) {
            items(list) { item ->
                MessageRow(blogPost = item, username)
            }
        }
    }
}

/**
 * display each message in a single row (with a surface)
 * @see Composable
 */
@Composable
fun MessageRow(blogPost: BlogPost, localUsername: String) {
    val shape = RoundedCornerShape(4.dp)

    // message has a round background shape
    // set background color to primary (light/dark different)
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 2.5.dp, horizontal = 4.dp),
        shape = shape
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp)) {
            Column(modifier = Modifier
                .padding(4.dp)) {

                // color message from the user himself in a different color
                blogPost.userName?.let {

                    // name matching based on local username (stored in shared preferences)
                    if (localUsername == it) {
                        Text(it, color = Color.Blue)
                    }
                    else {
                        Text(it, color = FHred)
                    }
                }

                blogPost.message?.let {
                    Text(it, color = Color.White)
                }
            }
        }
    }
}

/**
 * Box at the bottom bar where the user can enter a message
 * and a send button
 * @see Composable */
@Composable
fun MessageBox() {
    // listener of the entered text
    var text by remember { mutableStateOf("") }

    // get CoroutineScope because message sent need to be in a different thread
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Row() {
        // textField where the user can enter a message
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "Enter Message ...") },
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = {
            // launch coroutine and post/fetch changes
            coroutineScope.launch(Dispatchers.IO) {
                if (text.isNotEmpty()) {
                    // send POST request to the backend
                    BlogManager.postMessage(text, context)

                    // update changes and update local db
                    BlogManager.getBlogs(context)

                    // reset entered message
                    text = ""
                }
            }
        }) {
            Icon(Icons.Filled.Send, "")
        }
    }
}