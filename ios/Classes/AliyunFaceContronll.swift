//
//  AliyunFaceContronll.swift
//  aliyun_face
//
//  Created by Spice ly on 2021/11/19.
//

import Foundation
import AliyunIdentityManager

class AliyunFaceContronll: NSObject{
    private var messenger: FlutterBinaryMessenger
    private var navigationController: UIViewController!
    
    init(withMessenger messenger: FlutterBinaryMessenger) {
        self.messenger = messenger
        
        super.init()
    }
    func register() {
        self.navigationController = UIApplication.shared.delegate?.window?!.rootViewController
        let channel = FlutterMethodChannel(name: "plugins.muka.com/aliyun_face", binaryMessenger:messenger)
        channel.setMethodCallHandler(onMethodCall)
    }
    
    func getJSONStringFromDictionary(dictionary:NSDictionary) -> String {
        if (!JSONSerialization.isValidJSONObject(dictionary)) {
            print("无法解析出JSONString")
            return ""
        }
        let data : NSData! = try? JSONSerialization.data(withJSONObject: dictionary, options: []) as NSData?
        let JSONString = NSString(data:data as Data,encoding: String.Encoding.utf8.rawValue)
        return JSONString! as String
        
    }
    
    func onMethodCall(methodCall: FlutterMethodCall, result: @escaping FlutterResult) {
        switch methodCall.method{
        case "init":
            AliyunSdk.initialize()
            result(true)
        case "getMetaInfos":
            let metaInfo = AliyunIdentityManager.getMetaInfo()
            result(getJSONStringFromDictionary(dictionary: metaInfo! as NSDictionary))
        case "verify":
            let args = methodCall.arguments as? [String: Any]
            let zimID = args?["zimID"] as? String
            if (zimID != nil){
                let extParams: [String : Any] = ["currentCtr": self.navigationController as Any]
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
