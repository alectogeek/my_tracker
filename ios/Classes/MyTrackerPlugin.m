#import "MyTrackerPlugin.h"
#if __has_include(<my_tracker/my_tracker-Swift.h>)
#import <my_tracker/my_tracker-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "my_tracker-Swift.h"
#endif

@implementation MyTrackerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMyTrackerPlugin registerWithRegistrar:registrar];
}
@end
