import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:baidu_face/baidu_face.dart';

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
    print('=======================');
    bool status = await BaiduFace.init('comMuka-face-android');

    print(status);
    print('=======================');
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: ListView(
            children: [
              Center(
                child: ElevatedButton(
                  child: const Text('活体检测'),
                  onPressed: () async {
                    LikenessResult res = await BaiduFace.likeness();
                    print(res.toString());
                  },
                ),
              ),
              Center(
                child: ElevatedButton(
                  child: const Text('活体检测2'),
                  onPressed: () async {
                    DetectResult res = await BaiduFace.detect();
                    print(res.toString());
                  },
                ),
              ),
            ],
          )),
    );
  }
}
