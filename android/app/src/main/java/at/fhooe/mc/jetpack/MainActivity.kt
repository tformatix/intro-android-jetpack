package at.fhooe.mc.jetpack

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import at.fhooe.mc.jetpack.Constants.TAG
import at.fhooe.mc.jetpack.ui.theme.JetpackComposeGitTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import at.fhooe.mc.jetpack.room.AppDatabase
import at.fhooe.mc.jetpack.room.BlogPost
import at.fhooe.mc.jetpack.ui.theme.FHred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.BlogPostApi

const val TAG_MAIN_ACTIVITY = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "$TAG_MAIN_ACTIVITY::onCreate()")
        setContent {
            JetpackComposeGitTheme {

                // get blog messages from api when activity got created
                getBlogs(applicationContext)

                MainScreen()
            }
        }
    }
}

/**
 * refresh blogs from api and update values in room db
 * @see Context
 */
fun getBlogs(current: Context) {
    val blogPostApi = BlogPostApi(Constants.HTTP_BASE_URL)

    val blogs = blogPostApi.blogPostGet()

    for (i in blogs) {
        val blogPost = i.id?.let {
            BlogPost(it, i.userName, i.message, i.postedDateTime)
        }

        blogPost?.let {
            AppDatabase.getDatabase(current).blogPostDao().insertAll(it)
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val bottomNavItems = listOf(
        BottomNavScreens.Blog,
        BottomNavScreens.Settings
    )

    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController, bottomNavItems)
        },
        floatingActionButton = {
            FloatingActionButton(backgroundColor = FHred, onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        getBlogs(context)
                    }
                }
            }) {
                Icon(Icons.Filled.Refresh,"")
            }
        },
        floatingActionButtonPosition = FabPosition.End
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
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = "Icons",
                        tint = Color.White
                    )
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
            BlogScreen()
        }
        composable(BottomNavScreens.Settings.route) {
            SettingsScreen()
        }
    }
}

/**
 * function for previewing the MainScreen
 */
@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    MainScreen()
}