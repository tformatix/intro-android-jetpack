package at.fhooe.mc.jetpack

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import at.fhooe.mc.jetpack.Constants.HTTP_BASE_URL
import at.fhooe.mc.jetpack.Constants.TAG
import at.fhooe.mc.jetpack.ui.theme.JetpackComposeGitTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.openapitools.client.apis.BlogPostApi

const val TAG_MAIN_ACTIVITY = "MainActivity"

class MainActivity : ComponentActivity() {

    private val blogPostApi = BlogPostApi(HTTP_BASE_URL) // use for api calls

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "$TAG_MAIN_ACTIVITY::onCreate()")

        // network call on IO thread
        lifecycleScope.launch(Dispatchers.IO) {
            val blogPosts = blogPostApi.blogPostGet()

            // back to UI thread
            runOnUiThread {
                setContent {
                    JetpackComposeGitTheme {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavScreens.blog,
        BottomNavScreens.settings)

    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController, bottomNavItems)
        },
    ) {
        MainScreenNavigationConfigurations(navController)
    }
}

@Composable
fun AppBottomNavigation(navController: NavHostController, bottomNavItems: List<BottomNavScreens>) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon) },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                alwaysShowLabels = false,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun