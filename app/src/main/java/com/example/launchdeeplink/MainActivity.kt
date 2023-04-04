package com.example.launchdeeplink

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.launchdeeplink.ui.theme.LaunchDeeplinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchDeeplinkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main(this)
                }
            }
        }
    }
}

fun launchDeeplink(context: Context, deeplinkValue: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkValue))
    startActivity(context, intent, null)
}

@Composable
fun Main(context: Context) {
    var deeplink = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Enter your deeplink:")
        Spacer(modifier = Modifier.padding(16.dp))
        TextField(
            value = deeplink.value,
            onValueChange = { deeplink.value = it },
            label = { Text("paste here:") },
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = {
                launchDeeplink(context, deeplink.value)
            }) {
            Text(text = "Launch Deeplink")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(context: Context = LocalContext.current) {
    LaunchDeeplinkTheme {
        Main(context)
    }
}