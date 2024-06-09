package com.leafclient.leaf.utils.render.shader.exception

import com.leafclient.leaf.utils.render.shader.Shader
import java.lang.RuntimeException

/**
 * Exception thrown by the ShaderProgram or the Shader class when
 * there's an error at the creation of the shader (due to hardware or drivers)
 */
class UnsupportedShaderException:
        RuntimeException("Uh, it seems like shaders are not supported on this machine...")

/**
 * Exception thrown by the ShaderProgram when the vertex shader is not a vertex, same for
 * fragment shader.
 */
class InvalidShaderException(type: Shader.Type, expectedType: Shader.Type):
        RuntimeException("Expected shader of type $expectedType and not $type")

/**
 * Exception thrown by the Shader class when compilation fails
 */
class ShaderCompileException(glInfo: String):
        RuntimeException("Error while creating shader (OpenGL: $glInfo)")