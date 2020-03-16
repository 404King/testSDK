package com.example.flutterapp

import android.os.Bundle
import com.example.flutterapp.view.MyLayoutFactory
import com.example.flutterapp.view.TTAdManagerHolder
import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant
import android.content.Context

class MainActivity: FlutterActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)
    TTAdManagerHolder.init(this)
    MyLayoutFactory.registerWith(this)
  }

}
