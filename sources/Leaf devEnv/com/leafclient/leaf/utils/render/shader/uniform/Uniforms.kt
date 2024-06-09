package com.leafclient.leaf.utils.render.shader.uniform

import com.leafclient.leaf.utils.render.shader.ShaderProgram
import org.lwjgl.opengl.ARBShaderObjects
import org.lwjgl.opengl.GL20
import kotlin.reflect.KProperty

/**
 * Creates a new [ShaderUniform] with a [Int] value
 */
@Suppress("unchecked_cast")
fun ShaderProgram.uniform(name: String, default: Int) =
    ShaderUniform(this, name, default, GL20::glUniform1i).also { uniform ->
        this.uniforms.add(uniform as ShaderUniform<Any>)
    }

/**
 * Creates a new [ShaderUniform] with a [Float] value
 */
@Suppress("unchecked_cast")
fun ShaderProgram.uniform(name: String, default: Float) =
    ShaderUniform(this, name, default, GL20::glUniform1f).also { uniform ->
        this.uniforms.add(uniform as ShaderUniform<Any>)
    }

/**
 * Creates a new [ShaderUniform] with a [Float] value
 */
@Suppress("unchecked_cast")
fun ShaderProgram.uniform(name: String, floatPair: Pair<Float, Float>) =
    ShaderUniform(
        this,
        name,
        floatPair
    ) { uniformId, pair ->
        GL20.glUniform2f(uniformId, pair.first, pair.second)
    }.also { uniform ->
        this.uniforms.add(uniform as ShaderUniform<Any>)
    }

/**
 * Creates a new [ShaderUniform] with a [Float] value
 */
@Suppress("unchecked_cast")
fun ShaderProgram.uniform(name: String, default: Boolean) =
    ShaderUniform(
        this,
        name,
        default
    ) { uniformId, boolean ->
        GL20.glUniform1i(uniformId, if (boolean) 1 else 0)
    }.also { uniform ->
        this.uniforms.add(uniform as ShaderUniform<Any>)
    }

/**
 * Represents a [ShaderUniform] for an OpenGL program and allows its manipulation
 */
class ShaderUniform<T: Any>(program: ShaderProgram, codeName: String, defaultValue: T, val updater: (Int, T) -> Unit) {

    /**
     * Contains the uniform's id
     */
    var uniformId: Int = ARBShaderObjects.glGetUniformLocationARB(program.programId, codeName)

    /**
     * Contains the current value of this uniform
     */
    var value = defaultValue

    /**
     * Variable used to know if this uniform should be updated
     */
    var isOutdated = true

    /**
     * Returns the value of this uniform
     */
    operator fun getValue(thisRef: Any, property: KProperty<*>) = value

    /**
     * Defines the value of this uniform so it can be applied at next render
     */
    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        if(this.value != value) {
            this.value = value
            isOutdated = true
        }
    }

}