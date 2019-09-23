import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:serial_plugin/serial_plugin.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _serial = 'Unknown';

  @override
  void initState() {
    super.initState();
    initSerial();
  }

  Future<void> initSerial() async {
    String serial;
    try {
      serial = await SerialPlugin.serial;
    } on PlatformException {
      serial = 'Failed to get platform version.';
    }
    if (!mounted) return;
    setState(() {
      _serial = serial;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('序列号: $_serial\n'),
        ),
      ),
    );
  }
}
