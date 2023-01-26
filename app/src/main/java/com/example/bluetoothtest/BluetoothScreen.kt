package com.example.bluetoothtest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Scroller
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothtest.ui.screens.SatPaqScanViewModel
import higherground.lib.satpaq.satpaq.SatPaqDevice


@Preview
@Composable
fun BluetoothScannerTopBar(
    modifier: Modifier = Modifier
) {
        TopAppBar (
            title = { Text(text = "SatPaq Scanner") },
            modifier = modifier,
            navigationIcon = (null))
}

@Preview
@Composable
fun BluetoothApp(modifier: Modifier = Modifier, viewModel: SatPaqScanViewModel = SatPaqScanViewModel()) {
    Scaffold (
        topBar = {
            BluetoothScannerTopBar(modifier)
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {
                viewModel.startScan(true)
            }) {
                Color(R.color.white)
                Text("Scan")
            }
            ScanList(viewModel = viewModel)
        }

    }

}

@SuppressLint("MissingPermission")
@Composable
fun ScanList(modifier: Modifier = Modifier, viewModel: SatPaqScanViewModel) {
    Column(modifier = modifier
        .padding(16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val scanStatus = viewModel.scanStatus.observeAsState().value
        Text(text = scanStatus.toString())
        // Create the observer which updates the UI.
        var mutableState by remember { mutableStateOf(viewModel.scannedSatPaqs) }

        Column(
        ) {
            mutableState.value?.forEach {
                Row(modifier = Modifier
                    .padding(Dp(4F))
                    .border(1.dp, Color.Blue, shape = RoundedCornerShape(8.dp))
                    .clickable(onClick = { /* onClick method */ })
                    .fillMaxWidth()
                    .height(Dp(50F)),
                    verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier
                        .weight(1f)
                        .padding(start = Dp(6F))) {
                        Text(text = "ID: " + it.scanResult.device.name, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold), textAlign = TextAlign.Center)
                    }
                    Column(modifier = Modifier
                        .weight(1f)) {
                        Text(text = "RSI: " + it.scanResult.rssi.toString(), style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold), textAlign = TextAlign.Right)
                    }
                }
            }
        }
    }
}
