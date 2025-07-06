package com.testkotlinviewapp

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CustomColorGLView(context: Context) : GLSurfaceView(context) {

    private val renderer: ColorRenderer

    init {
        setEGLContextClientVersion(2)
        renderer = ColorRenderer()
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    fun setColor(colorString: String) {
        renderer.setColor(colorString)
        requestRender()
    }

    private class ColorRenderer : Renderer {
        private var red = 0f
        private var green = 1f
        private var blue = 0f

        fun setColor(color: String) {
            try {
                val parsed = android.graphics.Color.parseColor(color)
                red = (parsed shr 16 and 0xFF) / 255f
                green = (parsed shr 8 and 0xFF) / 255f
                blue = (parsed and 0xFF) / 255f
            } catch (e: IllegalArgumentException) {
                red = 0f; green = 1f; blue = 0f // fallback to green
            }
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(red, green, blue, 1f)
        }

        override fun onDrawFrame(gl: GL10?) {
            GLES20.glClearColor(red, green, blue, 1f)
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
        }
    }
}
