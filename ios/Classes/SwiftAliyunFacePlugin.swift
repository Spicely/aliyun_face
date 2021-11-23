import Flutter
import UIKit

public class SwiftAliyunFacePlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {        
        AliyunFaceContronll(withMessenger: registrar.messenger()).register()
    }
}
