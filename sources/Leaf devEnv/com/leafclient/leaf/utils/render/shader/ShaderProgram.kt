package com.leafclient.leaf.utils.render.shader

import com.leafclient.leaf.utils.render.shader.exception.InvalidShaderException
import com.leafclient.leaf.utils.render.shader.exception.UnsupportedShaderException
import com.leafclient.leaf.utils.render.shader.uniform.ShaderUniform
import org.lwjgl.opengl.ARBShaderObjects
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20

/**
 * Represents an OpenGL program
 */
open class ShaderProgram(vertexShader: Shader, fragmentShader: Shader) {

    val programId: Int = ARBShaderObjects.glCreateProgramObjectARB()

    val uniforms = mutableListOf<ShaderUniform<Any>>()

    init {
        if(vertexShader.shaderType != Shader.Type.VERTEX)
            throw InvalidShaderException(
                vertexShader.shaderType,
                Shader.Type.VERTEX
            )

        if(fragmentShader.shaderType != Shader.Type.FRAGMENT)
            throw InvalidShaderException(
                fragmentShader.shaderType,
                Shader.Type.FRAGMENT
            )

        // Gets the program id
        if(programId == 0)
            throw UnsupportedShaderException()

        // Attaches both shader to the program
        ARBShaderObjects.glAttachObjectARB(programId, vertexShader.shaderId)
        ARBShaderObjects.glAttachObjectARB(programId, fragmentShader.shaderId)

        // Link and validate!
        ARBShaderObjects.glLinkProgramARB(programId)
        ARBShaderObjects.glValidateProgramARB(programId)
    }

    /**
     * Pushes a new matrix and uses the program
     */
    fun pushAndUse() {
        // Starts a new render
        GL11.glPushMatrix()
        GL20.glUseProgram(programId)

        // Updates uniforms if required
        uniforms
            .filter { it.isOutdated }
            .forEach {
                it.updater.invoke(it.uniformId, it.value)
                it.isOutdated = false
            }
    }

    /**
     * Pops the matrix and stop using the program
     */
    fun pop() {
        GL20.glUseProgram(0)
        GL11.glPopMatrix()
    }

}