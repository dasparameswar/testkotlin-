package com.testkotlinviewapp

import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class CustomColorViewManager : SimpleViewManager<CustomColorGLView>() {
    override fun getName(): String = "CustomColorView"

    override fun createViewInstance(reactContext: ThemedReactContext): CustomColorGLView {
        return CustomColorGLView(reactContext)
    }

    @ReactProp(name = "color")
    fun setColor(view: CustomColorGLView, color: String) {
        view.setColor(color)
    }
}
