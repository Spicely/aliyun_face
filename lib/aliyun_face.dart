import 'dart:async';

import 'package:flutter/services.dart';

class AliyunFace {
  static const MethodChannel _channel = MethodChannel('plugins.muka.com/aliyun_face');

  /// 初始化
  static Future<bool> init() async {
    final bool status = await _channel.invokeMethod('init');
    return status;
  }

  ///
  /// 获取MetaInfo数据
  ///
  /// 在调用刷脸认证服务端发起认证请求时，需要传入该MetaInfo值
  ///
  static Future<String> get getMetaInfos async {
    final String metaInfos = await _channel.invokeMethod('getMetaInfos');
    return metaInfos;
  }

  /// 活体验证
  static Future<bool> verify(String zimID) async {
    final bool status = await _channel.invokeMethod('verify', {'zimID': zimID});
    return status;
  }
}
