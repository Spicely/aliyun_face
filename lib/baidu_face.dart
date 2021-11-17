import 'dart:async';

import 'package:flutter/services.dart';

enum BaiduFaceLanguage {
  zh,
}

class BaiduFace {
  static const MethodChannel _channel = MethodChannel('plugins.muka.com/baidu_face');

  ///
  /// 初始化
  ///
  /// 启动时调用一次
  ///
  static Future<bool> init(String licenseID) async {
    final bool status = await _channel.invokeMethod('init', {"licenseID": licenseID});
    return status;
  }

  ///
  /// 活体检测
  ///
  static Future<LikenessResult> likeness({
    BaiduFaceLanguage language = BaiduFaceLanguage.zh,
  }) async {
    late String lang;
    switch (language) {
      default:
        lang = 'zh-CN';
    }
    final Map<dynamic, dynamic> res = await _channel.invokeMethod('likeness', {"language": lang});
    return LikenessResult.fromMap(res);
  }

  ///
  /// 无动作活体检测
  ///
  static Future<DetectResult> detect({
    BaiduFaceLanguage language = BaiduFaceLanguage.zh,
  }) async {
    late String lang;
    switch (language) {
      default:
        lang = 'zh-CN';
    }
    final Map<dynamic, dynamic> map = await _channel.invokeMethod('detect', {"language": lang});
    return DetectResult.fromMap(map);
  }
}

class LikenessResult {
  LikenessResult({
    required this.success,
    required this.image,
  });
  factory LikenessResult.fromMap(Map<dynamic, dynamic> map) => LikenessResult(success: map['success'], image: map['srcimage']);
  final String success;
  // sucess=true
  final String image;
  @override
  String toString() => 'LivenessResult: $success,$image';
}

class DetectResult {
  DetectResult({
    required this.success,
    required this.image,
  });

  factory DetectResult.fromMap(Map<dynamic, dynamic> map) => DetectResult(success: map['success'], image: map['srcimage']);
  final String success;
  // sucess=true
  final String image;
  @override
  String toString() => 'DetectResult: $success,$image';
}
