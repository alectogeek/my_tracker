import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:my_tracker/my_tracker.dart';

void main() {
  const MethodChannel channel = MethodChannel('my_tracker');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await MyTracker.platformVersion, '42');
  });
}
