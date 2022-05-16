package at.fhooe.mc.jetpack.screen

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
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
 * composable functions for the Settings screen
 * @see Composable
 */
@Composable
fun SettingsScreen() {
    // shared preferences for user information (username)
    val sharedPrefs: SharedPreferences = LocalContext.current.getSharedPreferences(
        Constants.SHARED_PREFS_USER,
        Context.MODE_PRIVATE
    )

    // by ... declares a delegated property (provides the value of a property and handles its changes)
    // remember ... keeps a value (any value) consistent across recompositions
    // MutableState ... holds a value, where Compose will automatically observe changes to the value
    var userName by remember {
        mutableStateOf(
            TextFieldValue(
                text = sharedPrefs.getString(Constants.SHARED_PREFS_USER_NAME, null) ?: ""
            )
        )
    }

    // should display error message?
    val isError = userName.text.isEmpty()

    // children in vertical sequence
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // input text for entering username (outlined styling)
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = {
                // placeholder
                Text(stringResource(R.string.screen_settings_txt_enter_user_name))
            },
            trailingIcon = {
                // error icon
                if (isError)
                    Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )

        if(isError) {
            // error text
            Text(
                text = stringResource(R.string.screen_settings_txt_error),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // save button
        Button(
            onClick = {
                sharedPrefs.edit()
                    .putString(Constants.SHARED_PREFS_USER_NAME, userName.text)
                    .apply()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isError
        ) {
            Text(stringResource(R.string.screen_settings_btn_save))
        }
    }
}