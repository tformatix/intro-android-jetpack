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
import androidx.compose.ui.text.style.TextAlign
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
                text = sharedPrefs.getString(Constants.SHARED_PREFS_USER_NAME, null) ?: ""
            )
        )
    }
    val isError = userName.text.isEmpty()

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
            trailingIcon = {
                if (isError)
                    Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )

        // error text
        if(isError) {
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