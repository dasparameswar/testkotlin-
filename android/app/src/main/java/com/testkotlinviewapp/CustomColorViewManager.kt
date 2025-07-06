package com.testkotlinviewapp

import android.graphics.Color
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class CustomColorViewManager : SimpleViewManager<CustomColorView>() {

  override fun getName() = "CustomColorView"

  override fun createViewInstance(rc: ThemedReactContext) = CustomColorView(rc)

  @ReactProp(name = "color")
  fun setColor(view: CustomColorView, color: String?) {
    val parsed = runCatching { Color.parseColor(color ?: "#FF0000") }.getOrElse { Color.GRAY }
    view.setBackgroundColor(parsed)
  }
}
