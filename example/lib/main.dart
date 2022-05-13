import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:aliyun_face/aliyun_face.dart';
import 'package:dio/dio.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    await AliyunFace.init();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: ElevatedButton(
            child: Text('活体验证'),
            onPressed: () async {
              String metaInfo = await AliyunFace.getMetaInfos;
              Response res = await Dio().post('https://test.uhomeing.com/app/face/getCertifyId', data: {'meta_info': metaInfo});
              print(res.data);
              bool status = await AliyunFace.verify(res.data['data']);
              if (status) {
                print('success');
              } else {
                print('人脸识别失败,请稍后重试');
              }
            },
          ),
        ),
      ),
    );
  }
}
