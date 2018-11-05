
# BarcodeScannerPlugin
This is a cordova plugin for newland MT65,MT90.

You can install it to your cordova app and use offered js api to get scanning result or change scanner's setting.

# How to install
 ①Download the plugin and extract it.
 
 ②Move to your cordova project folder and execute the following command
 
    cordova plugin add ../extract path/BarcodeScannerPlugin/


# JS API
Aftere install BarcodeScanner plugin,you can use the following JS API to get scanning result or change scanner setting.



| Method                                                 | Arg1                 | Arg2                                                         | Comment                                                      |
| ------------------------------------------------------ | -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| nlscan.plugins.barcodescanner.scan()                   | null                 | null                                                         | start to scan                                                |
| nlscan.plugins.barcodescanner.show(arg1)               | scan result | null                                                         | In BarcodeScanner.js the scanning result is set to a textarea(id is "outputArea"),you can modify it to other dom object.Actually, this method is for Java code. |
| nlscan.plugins.barcodescanner.scanSetting([arg1,arg2]) | EXTRA_SCAN_POWER     | 0 or 1 |0: Disable scanning<br />1: Enable scanning                                                                                 |
|                                                        | EXTRA_TRIG_MODE      | 0 or 1 or 2|0: Trigger mode is normal trigger<br />1: Trigger mode is continuous trigger<br />2: Trigger mode is timeout trigger        |
|                                                        | EXTRA_SCAN_NOTY_SND  | 0 or 1 |0: Turn off the voice prompts<br />1: Turn on the voice prompts                                                             |
