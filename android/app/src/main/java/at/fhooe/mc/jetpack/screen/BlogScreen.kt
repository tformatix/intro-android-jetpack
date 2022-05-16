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
 * composable functions for the Blog screen
 * @see Composable
 */
@Composable
fun BlogScreen(viewModel: BlogViewModel =
                   BlogViewModel(LocalContext.current.applicationContext as Application)) {
    val list: List<BlogPost> by viewModel.allBlogs.collectAsState(initial = emptyList())

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val username = BlogManager.getUsername(context)

    Scaffold(
        bottomBar = { MessageBox() },
        floatingActionButton = {
            FloatingActionButton(backgroundColor = FHred, onClick = {
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
        LazyColumn(modifier = Modifier.padding(it)) {
            items(list) { item ->
                MessageRow(blogPost = item, username)
            }
        }
    }
}

/**
 * display each blog message in an own surface
 * @see Composable
 */
@Composable
fun MessageRow(blogPost: BlogPost, localUsername: String) {
    val shape = RoundedCornerShape(4.dp)

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

                // username
                blogPost.userName?.let {
                    if (localUsername == it) {
                        Text(it, color = Color.Blue)
                    }
                    else {
                        Text(it, color = FHred)
                    }
                }

                // message
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
    var text by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Row() {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "Enter Message ...") },
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                if (text.isNotEmpty()) {
                    // post message
                    BlogManager.postMessage(text, context)

                    // fetch changes and update local db
                    BlogManager.getBlogs(context)

                    // reset Message
                    text = ""
                }
            }
        }) {
            Icon(Icons.Filled.Send, "")
        }
    }
}