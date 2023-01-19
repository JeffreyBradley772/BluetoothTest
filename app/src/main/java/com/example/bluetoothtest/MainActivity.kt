package com.example.bluetoothtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bluetoothtest.ui.theme.BluetoothTestTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.ui.unit.dp
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.navigation.findNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
//                MyScreenContent(navController = findNavController())
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable() () -> Unit) {
    MaterialTheme {
        Surface {
            content()
        }
    }
}

@Composable
fun MyScreenContent(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
//            navController.navigate()
        }) {
            Text("Button")
        }
    }
}
