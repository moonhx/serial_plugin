import 'dart:async';

import 'package:flutter/services.dart';

class SerialPlugin {
  static const MethodChannel _channel =
      const MethodChannel('serial_plugin');

  static Future<String> get serial async {
    final String serial = await _channel.invokeMethod('getSerial');
    return serial;
  }
}
