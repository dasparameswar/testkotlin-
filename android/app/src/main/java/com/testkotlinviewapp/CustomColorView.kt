package com.testkotlinviewapp

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CustomColorGLView(context: Context) : GLSurfaceView(context) {

    private val renderer: ColorTriangleRenderer

    init {
        setEGLContextClientVersion(2)
        renderer = ColorTriangleRenderer()
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    fun setColor(colorString: String) {
        renderer.setBackgroundColor(colorString)
        requestRender()
    }

    private class ColorTriangleRenderer : Renderer {

        private var red = 0f
        private var green = 1f
        private var blue = 0f

        private lateinit var vertexBuffer: FloatBuffer
        private val triangleCoords = floatArrayOf(
            0.0f, 0.5f, 0.0f,  // top
           -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
        )

        private val vertexShaderCode = """
            attribute vec4 vPosition;
            void main() {
                gl_Position = vPosition;
            }
        """

        private val fragmentShaderCode = """
            precision mediump float;
            uniform vec4 vColor;
            void main() {
                gl_FragColor = vColor;
            }
        """

        private var program = 0
        private var positionHandle = 0
        private var colorHandle = 0

        private val triangleColor = floatArrayOf(1f, 1f, 1f, 1f) // White

        fun setBackgroundColor(color: String) {
            try {
                val parsed = android.graphics.Color.parseColor(color)
                red = (parsed shr 16 and 0xFF) / 255f
                green = (parsed shr 8 and 0xFF) / 255f
                blue = (parsed and 0xFF) / 255f
            } catch (e: IllegalArgumentException) {
                red = 0f; green = 1f; blue = 0f // fallback to green
            }
        }

        override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(red, green, blue, 1.0f)

            val bb = ByteBuffer.allocateDirect(triangleCoords.size * 4)
            bb.order(ByteOrder.nativeOrder())
            vertexBuffer = bb.asFloatBuffer()
            vertexBuffer.put(triangleCoords)
            vertexBuffer.position(0)

            val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
            val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

            program = GLES20.glCreateProgram().also {
                GLES20.glAttachShader(it, vertexShader)
                GLES20.glAttachShader(it, fragmentShader)
                GLES20.glLinkProgram(it)
            }
        }

        override fun onDrawFrame(unused: GL10?) {
            GLES20.glClearColor(red, green, blue, 1f)
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

            GLES20.glUseProgram(program)

            positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
            GLES20.glEnableVertexAttribArray(positionHandle)
            GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)

            colorHandle = GLES20.glGetUniformLocation(program, "vColor")
            GLES20.glUniform4fv(colorHandle, 1, triangleColor, 0)

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)
            GLES20.glDisableVertexAttribArray(positionHandle)
        }

        override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
        }

        private fun loadShader(type: Int, shaderCode: String): Int {
            return GLES20.glCreateShader(type).also { shader ->
                GLES20.glShaderSource(shader, shaderCode)
                GLES20.glCompileShader(shader)
            }
        }
    }
}
