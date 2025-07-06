package com.testkotlinviewapp

import android.content.Context
import android.graphics.Color
import android.view.View

class CustomColorView(context: Context) : View(context) {
  init { setBackgroundColor(Color.RED) }     // default
}
