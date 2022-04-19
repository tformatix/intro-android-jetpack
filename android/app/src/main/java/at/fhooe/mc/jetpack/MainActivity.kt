package at.fhooe.mc.jetpack

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import at.fhooe.mc.jetpack.Constants.TAG
import at.fhooe.mc.jetpack.ui.theme.JetpackComposeGitTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import at.fhooe.mc.jetpack.room.AppDatabase
import at.fhooe.mc.jetpack.room.BlogPost
import at.fhooe.mc.jetpack.screen.BlogScreen
import at.fhooe.mc.jetpack.screen.SettingsScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.openapitools.client.apis.BlogPostApi
import java.net.SocketTimeoutException

const val TAG_MAIN_ACTIVITY = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "$TAG_MAIN_ACTIVITY::onCreate()")

        // get blog messages from api when activity got created
        lifecycleScope.launch(Dispatchers.IO) {
            BlogManager.getBlogs(applicationContext)
        }

        setContent {
            JetpackComposeGitTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavScreens.Blog,
        BottomNavScreens.Settings
    )

    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController, bottomNavItems)
        }
    )
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainScreenNavigationConfigurations(navController)
        }
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
    val startDestination =
        if (BlogManager.getUsername(LocalContext.current).isEmpty())
            BottomNavScreens.Settings.route
        else
            BottomNavScreens.Blog.route

    NavHost(navController, startDestination = startDestination) {
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