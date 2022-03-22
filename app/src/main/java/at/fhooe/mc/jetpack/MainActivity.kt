package at.fhooe.mc.jetpack

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.fhooe.mc.jetpack.Constants.HTTP_BASE_URL
import at.fhooe.mc.jetpack.Constants.TAG
import at.fhooe.mc.jetpack.ui.theme.JetpackComposeGitTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.openapitools.client.apis.BlogApi

const val TAG_MAIN_ACTIVITY = "MainActivity"

class MainActivity : ComponentActivity() {

    private val blogApi = BlogApi(HTTP_BASE_URL) // use for api calls

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "$TAG_MAIN_ACTIVITY::onCreate()")

        // network call on IO thread
        GlobalScope.launch(Dispatchers.IO){
            val blogPosts = blogApi.blogBlogPostsGet()

            // back to UI thread
            runOnUiThread {
                setContent {
                    JetpackComposeGitTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                            Greeting("Android")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeGitTheme {
        Greeting("Android")
    }
}