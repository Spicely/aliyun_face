import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:aliyun_face/aliyun_face.dart';
import 'package:flutter_muka/flutter_muka.dart';

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
              String metaInfos = await AliyunFace.getMetaInfos;
              dynamic data = await HttpUtils.request(
                'http://shopceshi.xn--51-d05d.com/api/rl/index',
                data: FormData.fromMap({'MetaInfo': metaInfos}),
              );
              await AliyunFace.verify(data['data']['ResultObject']['CertifyId']);
            },
          ),
        ),
      ),
    );
  }
}
