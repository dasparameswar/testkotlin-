package com.testkotlinviewapp

import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView

class MainActivity : ReactActivity() {
  override fun getMainComponentName(): String = "TestKotlinViewApp"

  override fun createReactActivityDelegate(): ReactActivityDelegate {
    return object : DefaultReactActivityDelegate(this, mainComponentName, false) {
      override fun createRootView() = RNGestureHandlerEnabledRootView(context)
    }
  }
}
