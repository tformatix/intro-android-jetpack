package at.fhooe.mc.jetpack

import android.graphics.drawable.Drawable
import androidx.annotation.StringRes

sealed class BottomNavScreens(val route: String, val name: String, val icon: Int) {
    object Blog: BottomNavScreens("blog", "blog", R.drawable.ic_blog)
    object Settings: BottomNavScreens("settings", "settings", R.drawable.ic_settings)
}
