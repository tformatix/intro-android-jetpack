package at.fhooe.mc.jetpack

import android.graphics.drawable.Drawable
import androidx.annotation.StringRes

/**
 * declares which screens are available for the bottom navigation
 * @param route navigation route (necessary for the navController)
 * @param name of the screen
 * @param icon which should be display in bottom nav
 */
sealed class BottomNavScreens(val route: String, val name: String, val icon: Int) {
    object Blog: BottomNavScreens("blog", "blog", R.drawable.ic_blog)
    object Settings: BottomNavScreens("settings", "settings", R.drawable.ic_settings)
}
