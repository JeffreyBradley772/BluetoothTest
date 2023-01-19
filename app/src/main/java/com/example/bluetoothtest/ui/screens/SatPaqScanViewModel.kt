package com.example.bluetoothtest.ui.screens


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import higherground.lib.satpaq.logger.HGNormalLog
import higherground.lib.satpaq.satpaq.SPScanManager
import higherground.lib.satpaq.satpaq.SatPaqDevice
import no.nordicsemi.android.support.v18.scanner.ScanResult


class SatPaqScanViewModel : ViewModel() {
    enum class BleScanStatus {
        STOPPED,
        SCANNING,
    }

    companion object {
        private val TAG: String = SatPaqScanViewModel::class.java.simpleName
    }

    val scanResults = MutableLiveData<List<SatPaqDevice>>()
    val scannedSatPaqs: LiveData<List<SatPaqDevice>>
        get() = scanResults

    private val bleScanStatus = MutableLiveData<BleScanStatus>()
    val scanStatus: LiveData<BleScanStatus>
        get() = bleScanStatus

    private val scanManager = SPScanManager.getInstance()

    private val scanCallback = object :
        SPScanManager.SPScanManagerCallback {

        override fun onScanSatPaq(result: ScanResult, isDFU: Boolean) {
            HGNormalLog.trace(TAG, "onScanSatPaq ")
            updateSatPaqInList(result, isDFU)
        }

        // Normally it timed out and stopped scanning...
        override fun onScanError(errorType: SPScanManager.SPScanErrorType) {
            if (errorType == SPScanManager.SPScanErrorType.Timeout) {
                HGNormalLog.trace(TAG, "Scanning Finished")
            } else {
                HGNormalLog.warn(TAG, "Scanning Finished with error:  $errorType")
            }
            bleScanStatus.postValue(BleScanStatus.STOPPED)
        }
    }

    /**
     * This function builds up a unique list of SatPaqs that are in range of the phone and that are
     * not currently connected.
     *
     * The list is sorted by strongest RSSI values first. In a roomful of
     * SatPaqs a user can find their SatPaq by holding it closest to the phone and looking for it
     * at or near the top of the list.
     * @param scanResult Low level Android BLE scan result record
     * @param isDFU If true the SatPaq Nordic bootloader doing the advertising (needed for
     * firmware updates)
     *
     * DD XXX the SPScanManager should have been returning us nice information-hiding SatPaqDevice
     * objects rather than raw BLE scan results
     */
    private fun updateSatPaqInList(scanResult: ScanResult, isDFU: Boolean) {

        val satpaqsList: MutableList<SatPaqDevice> = scanResults.value as MutableList<SatPaqDevice>

        val satpaq = satpaqsList.firstOrNull { it.scanResult.device.address == scanResult.device.address }
        if (satpaq == null) {
            satpaqsList.add(SatPaqDevice(scanResult, isDFU))
            satpaqsList.sortBy { -it.scanResult.rssi }

            scanResults.postValue(satpaqsList)
        }
    }

    /**
     * @param restart Specifies if it requires us to clear our cached SatPaq list
     */
    fun startScan(restart: Boolean) {
        HGNormalLog.trace(TAG, "startScan: restart=$restart")

        if (scanManager.isScanning) {
            scanManager.stopScan()
        }

        if (restart || scanResults.value == null) {
            scanResults.value = ArrayList()
        }

        scanManager.startScan("", "", true, scanCallback)
        bleScanStatus.postValue(BleScanStatus.SCANNING)
    }

    fun stopScan() {
        HGNormalLog.trace(TAG, "stopScan")
        scanManager.stopScan()
        bleScanStatus.postValue(BleScanStatus.STOPPED)
    }
}

