
import 'dart:async';

import 'package:flutter/services.dart';

class MyTracker {
  static const MethodChannel _channel =
      const MethodChannel('my_tracker');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
