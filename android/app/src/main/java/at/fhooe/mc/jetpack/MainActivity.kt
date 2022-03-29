package at.fhooe.mc.jetpack


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import at.fhooe.mc.jetpack.Constants.HTTP_BASE_URL
import at.fhooe.mc.jetpack.Constants.TAG
import at.fhooe.mc.jetpack.ui.theme.JetpackComposeGitTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.openapitools.client.apis.BlogPostApi
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.*


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
private fun MainScreen() {

    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavScreens.Blog,
        BottomNavScreens.Settings)

    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController, bottomNavItems)
        },
    ) {
        MainScreenNavigationConfigurations(navController)
    }
}

@Composable
private fun AppBottomNavigation(navController: NavHostController, bottomNavItems: List<BottomNavScreens>) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        bottomNavItems.forEach { screen ->
            BottomNavigationItem(
                icon = {
                        Icon(painter = painterResource(id = screen.icon),
                             contentDescription = "Icons",
                             tint = Color.White)
                    },
                label = { Text(screen.name) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString("KEY_ROUTE")
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavScreens.Blog.route) {
        composable(BottomNavScreens.Blog.route) {
            Blog()
        }
        composable(BottomNavScreens.Settings.route) {
            Settings()
        }
    }
}

@Composable
private fun Blog() {
    Text("hey du")
}

@Composable
private fun Settings() {
    Text("i am settings")
}

/**
 * function for previewing the MainScreen
 */
@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    MainScreen()
}