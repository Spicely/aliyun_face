import 'dart:async';

import 'package:flutter/services.dart';

class AliyunFace {
  static const MethodChannel _channel = MethodChannel('aliyun_face');

  /// 初始化
  static Future<bool> init() async {
    final bool status = await _channel.invokeMethod('init');
    return status;
  }

  /// 验证
  static Future<bool> verify(String zimID) async {
    final bool status = await _channel.invokeMethod('verify', {'zimID': zimID});
    return status;
  }
}
