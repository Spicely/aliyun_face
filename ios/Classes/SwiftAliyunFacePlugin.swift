import Flutter
import UIKit
import AliyunIdentityManager

public class SwiftAliyunFacePlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "plugins.muka.com/aliyun_face", binaryMessenger: registrar.messenger())
    let instance = SwiftAliyunFacePlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(methodCall: FlutterMethodCall, result: @escaping FlutterResult) {
      switch methodCall.method{
      case "init":
          AliyunSdk.`init`()
          result(true)
      case "getMetaInfos":
         let metaInfo = AliyunIdentityManager.getMetaInfo()
          result(metaInfo)
      case "verify":
          
      default:
          result(FlutterMethodNotImplemented)
      }
  }
}
