#import "AliyunFacePlugin.h"
#if __has_include(<aliyun_face/aliyun_face-Swift.h>)
#import <aliyun_face/aliyun_face-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "aliyun_face-Swift.h"
#endif

@implementation AliyunFacePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAliyunFacePlugin registerWithRegistrar:registrar];
}
@end
