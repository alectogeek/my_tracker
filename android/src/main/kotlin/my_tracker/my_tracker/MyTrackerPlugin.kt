package my_tracker.my_tracker

import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import com.my.tracker.MyTracker

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** MyTrackerPlugin */
class MyTrackerPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var activity: Activity


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "my_tracker")
        context = flutterPluginBinding.applicationContext
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "setTrackerParams" -> {
                setTrackerParams(call, result)
            }
            "setTrackerConfig" -> {
                setTrackerConfig(call, result)
            }
            "initTracker" -> {
                initTracker(call, result)
            }
            "trackEvent" -> {
                trackEvent(call, result)
            }
            "flush" -> {
                flush(call, result)
            }
            "setDebugMode" -> {
                setDebugMode(call, result)
            }
            else -> {
                result.notImplemented()
            }
        }

    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    fun setTrackerParams(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>

        val trackerParams = MyTracker.getTrackerParams()
        trackerParams.customUserId = args["customId"] as String?
        trackerParams.email = args["email"] as String?
        trackerParams.phone = args["phone"] as String?

        result.success(null)
    }

    fun setTrackerConfig(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val trackerConfig = MyTracker.getTrackerConfig()

        trackerConfig.isTrackingLaunchEnabled = (args["isTrackingLaunchEnabled"] ?: true) as Boolean
        trackerConfig.launchTimeout = (args["launchTimeout"] ?: 30) as Int
        trackerConfig.bufferingPeriod = (args["bufferingPeriod"] ?: 900) as Int
        trackerConfig.forcingPeriod = (args["forcingPeriod"] ?: 900) as Int
        trackerConfig.isAutotrackingPurchaseEnabled = (args["isAutotrackingPurchaseEnabled"] ?: true) as Boolean
        trackerConfig.isTrackingLocationEnabled = (args["isTrackingLocationEnabled"] ?: true) as Boolean
        trackerConfig.isTrackingPreinstallEnabled = (args["isTrackingPreinstallEnabled"] ?: true) as Boolean

        result.success(null)
    }

    fun initTracker(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val sdkKey = args["sdkKey"] as String

        MyTracker.initTracker(sdkKey, activity.application)
        MyTracker.trackLaunchManually(activity)
        result.success(null)
    }


    fun trackEvent(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>

        MyTracker.trackEvent(args["name"] as String, if (args["params"] is Map<*, *>) (args["params"] as Map<String, String>) else null)
        result.success(null)
    }

    fun flush(call: MethodCall, result: Result) {
        MyTracker.flush()
        result.success(null)
    }

    fun setDebugMode(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>

        MyTracker.setDebugMode(args["enableDebug"] as Boolean)
        result.success(null)
    }


    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity;
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }
}
