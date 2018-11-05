package com.nlscan.cordova.plugin.barcodescanner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *@date 20181103
 *@author Alan
 *@company nlscan
 *@describe Cordova plugin to interface with NLSCAN barcode scanners.
 **/
public class BarcodeScanner extends CordovaPlugin {

    private BroadcastReceiver mReceiver;
    private static final String SCANNER_RESULT = "nlscan.action.SCANNER_RESULT";
    private static final String SCANNER_TRIG = "nlscan.action.SCANNER_TRIG";
    private static final String ACTION_BAR_SCANCFG = "ACTION_BAR_SCANCFG";
    private static final String Tag = "BarcodeScannerTag";
    private Context context;
    private static boolean registeredTag = false;
    private Activity activity;

    /**
      *@date
      *@author Alan
      *@company nlscan
      *@describe plugin init method
     **/
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.activity = cordova.getActivity();
        context = super.webView.getContext();
        registerReceiver();
        initSetting();
    }

    /**
     *@date 20181103
     *@author Alan
     *@company nlscan
     *@describe set scanner as power on,normal trig,sound on
     **/
    private void initSetting(){
        Intent intent = new Intent ("ACTION_BAR_SCANCFG");
        intent.putExtra("EXTRA_SCAN_MODE", 3);
        intent.putExtra("EXTRA_SCAN_POWER", 1);
        intent.putExtra("EXTRA_TRIG_MODE", 0);
        intent.putExtra("EXTRA_SCAN_NOTY_SND", 1);
        context.sendBroadcast(intent);
    }

    /**
      *@date 20181103
      *@author Alan
      *@company nlscan
      *@describe plugin execute method
     **/
    @Override
    public boolean execute(String action, JSONArray para, CallbackContext callbackContext) throws JSONException {
        if (action.equals("scan")) {
            Log.d(Tag,"execute method is called");
            Intent intent = new Intent(SCANNER_TRIG);
            context.sendBroadcast(intent);
            return true;
        } else if(action.equals("scanSetting")){
            Log.d(Tag,"Action:"+para.getString(0)+" Para:"+para.getString(1));
            Intent intent = new Intent (ACTION_BAR_SCANCFG);
            intent.putExtra(para.getString(0), Integer.parseInt(para.getString(1)));
            context.sendBroadcast(intent);
            return true;

        }
        return false;
    }


    /**
     *@date 20181103
     *@author Alan
     *@company nlscan
     *@describe regist the scan receiver.receive the scanning result and return it to js source.
     **/
    private void registerReceiver() {
        if (!registeredTag) {
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (SCANNER_RESULT.equals(action)) {
                        final String scanResult_1 = intent.getStringExtra("SCAN_BARCODE1");
                        final String scanStatus = intent.getStringExtra("SCAN_STATE");
                        Log.d(Tag, "scanResult_1：" + scanResult_1);
                        if ("ok".equals(scanStatus)) {
                            if (!TextUtils.isEmpty(scanResult_1)) {
                                final String result = scanResult_1;
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d(Tag,"result:"+result);
                                        webView.loadUrl("javascript:nlscan.plugins.barcodescanner.show('" + result + "')");
                                    }
                                });
                            }
                        }
                    }
                }
            };
            IntentFilter intFilter = new IntentFilter(SCANNER_RESULT);
            context.registerReceiver(mReceiver, intFilter);
            registeredTag = true;
        }

    }

    /**
     *@date 20181103
     *@author Alan
     *@company nlscan
     *@describe unregist scan receiver
     **/
    private void unRegisterReceiver() {
        if (registeredTag) {
            try {
                context.unregisterReceiver(mReceiver);
                registeredTag = false;
            } catch (Exception e) {
            }
        }

    }

    /**
     *@date 20181103
     *@author Alan
     *@company nlscan
     *@describe unregist scan receiver when destroy
     **/
    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }
}
