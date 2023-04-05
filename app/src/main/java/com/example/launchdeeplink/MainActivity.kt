package com.example.launchdeeplink

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
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
    if (deeplinkValue.isEmpty()) {
        return
    }

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkValue))
    try {
        startActivity(context, intent, null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Main(context: Context) {
    var deeplink = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Text(text = "Enter your deeplink:")
        Spacer(modifier = Modifier.padding(4.dp))
        TextField(
            value = deeplink.value,
            onValueChange = { deeplink.value = it },
            label = { Text("paste here:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))

        Row {
            Button(
                onClick = {
                    launchDeeplink(context, deeplink.value)
                }) {
                Text(text = "Launch Deeplink")
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    deeplink.value = ""
                }) {
                Text(text = "Clear")
            }

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