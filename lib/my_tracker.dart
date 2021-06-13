import 'dart:async';

import 'package:flutter/services.dart';

class MyTracker {
  MyTracker._();

  static MyTracker instance = MyTracker._();

  static const MethodChannel _channel = const MethodChannel('my_tracker');

  Future<void> setTrackerParams({String? customUserId, String? email, String? phone}) {
    return _channel.invokeMethod('setTrackerParams', {
      'customUserId': customUserId,
      'email': email,
      'phone': phone,
    });
  }

  Future<void> setTrackerConfig({
    bool? isTrackingLaunchEnabled,
    int? launchTimeout,
    int? bufferingPeriod,
    int? forcingPeriod,
    bool? isAutotrackingPurchaseEnabled,
    bool? isTrackingLocationEnabled,
    bool? isTrackingPreinstallEnabled,
  }) {
    return _channel.invokeMethod('setTrackerParams', {
      'isTrackingLaunchEnabled': isTrackingLaunchEnabled,
      'launchTimeout': launchTimeout,
      'bufferingPeriod': bufferingPeriod,
      'forcingPeriod': forcingPeriod,
      'isAutotrackingPurchaseEnabled': isAutotrackingPurchaseEnabled,
      'isTrackingLocationEnabled': isTrackingLocationEnabled,
      'isTrackingPreinstallEnabled': isTrackingPreinstallEnabled,
    });
  }

  Future<void> initTracker({required String sdkKey}) {
    return _channel.invokeMethod('initTracker', {'sdkKey': sdkKey});
  }

  Future<void> trackEvent(String name, {Map<String, String>? params}) {
    return _channel.invokeMethod('trackEvent', {
      'name': name,
      'params': params,
    });
  }

  Future<void> flush() {
    return _channel.invokeMethod('flush');
  }
}
