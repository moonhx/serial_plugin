package com.example.serial_plugin;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.provider.Settings;


/** SerialPlugin */
public class SerialPlugin implements MethodCallHandler {

  private final Context context;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "serial_plugin");
    channel.setMethodCallHandler(new SerialPlugin(registrar.context()));
  }

  // 初始化context
  private SerialPlugin(Context context) {
    this.context = context;
  }

  // 判断设备是平板还是手机
  public static boolean isPad(Context context) {
    return (context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK) 
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getSerial")) {
      String serial = "暂无";
      try {
        //实例化TelephonyManager对象
        TelephonyManager tm = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        serial = tm.getDeviceId();
        if (serial == null) {
          serial = "暂无";
        }
        // 如果是平板可以获取meid
        if(isPad(context) && (serial == "暂无" || serial == null)){
          serial = tm.getMeid();
          if (serial == null) {
            serial = "暂无";
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      result.success(serial);
    } else {
      result.notImplemented();
    }
  }
}
