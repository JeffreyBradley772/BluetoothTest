package com.example.bluetoothtest

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
//    var uiState = mutableStateOf(viewModel.scanStatus).value.observeAsState()
    Scaffold (
        topBar = {
            BluetoothScannerTopBar(modifier)
        }
    ) {

    }
    Column(
        modifier = modifier
            .padding(70.dp)
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

        ScanList()
    }

}

@Preview
@Composable
fun ScanList(modifier: Modifier = Modifier, viewModel: SatPaqScanViewModel = SatPaqScanViewModel()) {
    Column(modifier = modifier
        .padding(16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val list = viewModel.scanResults.observeAsState().value
        list?.forEach { item ->
            Row() {
                Text(item.satPaqAddress)
            }
        }
    }
}
