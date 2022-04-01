package at.fhooe.mc.jetpack

import android.hardware.camera2.params.ColorSpaceTransform
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import at.fhooe.mc.jetpack.room.BlogPost
import at.fhooe.mc.jetpack.ui.theme.FHred
import org.openapitools.client.apis.BlogPostApi
import org.openapitools.client.models.BlogPostDto
import java.time.OffsetDateTime

@Composable
fun BlogScreen() {
    val blogPost = BlogPost(1, "Michael Zauner", "hallo \nwie gehds", OffsetDateTime.now())
    val blogPostApi = BlogPostApi(Constants.HTTP_BASE_URL) // use for api calls


    val blogPosts = blogPostApi.blogPostGet()
    for (i in blogPosts) {
        MessageRow(i)
    }

}


@Composable
fun MessageRow(blogPostDto: BlogPostDto) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)) {

            // username
            blogPostDto.userName?.let {
                Text(
                    it, color = FHred,
                )
            }

            // message
            blogPostDto.message?.let {
                Text(
                    it, color = Color.White
                )
            }
        }
    }
}