package com.leafclient.leaf.utils.render.shader

import com.leafclient.leaf.utils.render.shader.exception.ShaderCompileException
import com.leafclient.leaf.utils.render.shader.exception.UnsupportedShaderException
import org.lwjgl.opengl.ARBFragmentShader
import org.lwjgl.opengl.ARBShaderObjects
import org.lwjgl.opengl.ARBVertexShader
import org.lwjgl.opengl.GL11

/**
 * Represents an OpenGL [Shader] and allow its manipulation, it can be either a [Type.FRAGMENT]
 * or [Type.VERTEX], both are supported by this class.
 */
class Shader(shaderCode: String, val shaderType: Type) {

    val shaderId: Int

    init {
        // Gets the shader id
        shaderId = ARBShaderObjects.glCreateShaderObjectARB(shaderType.openGlConstant)
        if(shaderId == 0)
            throw UnsupportedShaderException()

        // Defines the shader's source
        ARBShaderObjects.glShaderSourceARB(shaderId, shaderCode)
        // Compiles it and push it to the graphics card
        ARBShaderObjects.glCompileShaderARB(shaderId)

        // Error? uh no!
        if(ARBShaderObjects.glGetObjectParameteriARB(shaderId, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) {
            throw ShaderCompileException(
                ARBShaderObjects.glGetInfoLogARB(
                    shaderId, 500
                )
            )
        }
    }

    /**
     * Contains the two types of shader available and is used by the [Shader]
     * to identify them.
     */
    enum class Type(val openGlConstant: Int) {
        VERTEX(ARBVertexShader.GL_VERTEX_SHADER_ARB),
        FRAGMENT(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB)
    }

    companion object {
        /**
         * Creates a [Shader] instance from specified file
         */
        fun fromFile(filePath: String, type: Type): Shader {
            return Shader(
                Shader::class.java.getResourceAsStream(filePath)
                    .reader()
                    .readText(),
                type
            )
        }

        val defaultVertex = fromFile("/assets/minecraft/shaders/vertex.vert", Type.VERTEX)
    }

}