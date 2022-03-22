package at.fhooe.mc.jetpack

import androidx.annotation.StringRes

sealed class BottomNavScreens(val route: String, @StringRes val resourceId: Int, val icon: VectorAsset) {
    object blog: BottomNavScreens("blog")
    object settings: BottomNavScreens("settings")
}
