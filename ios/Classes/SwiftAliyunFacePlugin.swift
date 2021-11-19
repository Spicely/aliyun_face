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
            let args = methodCall.arguments as? [String: Any]
            let zimID = args?["zimID"] as? String
            if (zimID != nil){
                let extParams: [String : Any] = ["currentCtr": UIApplication.shared.keyWindow?.rootViewController]
                AliyunIdentityManager.sharedInstance()?.verify(with: zimID, extParams: extParams, onCompletion: { (response) in
                    DispatchQueue.main.async {
                        var resString = ""
                        switch response?.code {
                        case .ZIMResponseSuccess:
                            resString = "认证成功"
                            result(true)
                            break;
                        case .ZIMInterrupt:
                            resString = "初始化失败"
                            result(false)
                            break
                        case .ZIMTIMEError:
                            resString = "设备时间错误"
                            result(false)
                            break
                        case .ZIMNetworkfail:
                            resString = "网络错误"
                            result(false)
                            break
                        case .ZIMInternalError:
                            resString = "用户退出"
                            result(false)
                            break
                        case .ZIMResponseFail:
                            resString = "刷脸失败 "
                            result(false)
                        default:
                            break
                        }
                        NSLog("%@",resString )
                    }
                })
            }
        default:
            result(FlutterMethodNotImplemented)
        }
    }
}
