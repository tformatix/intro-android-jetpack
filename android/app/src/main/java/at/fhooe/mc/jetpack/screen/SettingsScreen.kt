package at.fhooe.mc.jetpack.screen

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import at.fhooe.mc.jetpack.Constants
import at.fhooe.mc.jetpack.R

/**
 * settings screen
 * @see Composable
 */
@Composable
fun SettingsScreen() {
    val sharedPrefs: SharedPreferences = LocalContext.current.getSharedPreferences(
        Constants.SHARED_PREFS_USER,
        Context.MODE_PRIVATE
    )
    var userName by remember {
        mutableStateOf(
            TextFieldValue(
                sharedPrefs.getString(Constants.SHARED_PREFS_USER_NAME, null) ?: ""
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // input text for entering username
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = {
                Text(stringResource(R.string.screen_settings_txt_enter_user_name))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // save button
        Button(
            onClick = {
                sharedPrefs.edit()
                    .putString(Constants.SHARED_PREFS_USER_NAME, userName.text)
                    .apply()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.screen_settings_btn_save))
        }
    }
}