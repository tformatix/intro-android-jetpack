package at.fhooe.mc.jetpack

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.fhooe.mc.jetpack.room.AppDatabase
import at.fhooe.mc.jetpack.room.BlogPost
import at.fhooe.mc.jetpack.room.BlogViewModel
import at.fhooe.mc.jetpack.ui.theme.FHred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

    Column {
        list.forEach { message -> MessageRow(blogPost = message) }
    }
}

/**
 * display each blog message in an own surface
 * @see Composable
 */
@Composable
fun MessageRow(blogPost: BlogPost) {
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
                    Text(
                        it,
                        color = FHred,
                    )
                }

                // message
                blogPost.message?.let {
                    Text(
                        it,
                        color = Color.White
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                val dateTime = blogPost.postedDateTime?.let { formatDateTime(it) }

                if (dateTime != null) {
                    Text(
                        text = dateTime,
                        color = Color.Yellow,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

/**
 * display a shorter version of the offsetDateTime from the BlogPost object
 * @param dateTime of the blogPost entry
 * @return compressed version of dateTime
 */
fun formatDateTime(dateTime: OffsetDateTime): String {
    return "${dateTime.dayOfMonth}-${dateTime.monthValue}-${dateTime.year} ${dateTime.hour}:${dateTime.minute}"
}