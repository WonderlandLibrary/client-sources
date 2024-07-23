package io.github.liticane.monoxide.util.render.shader.access;


import io.github.liticane.monoxide.util.render.shader.shaders.BloomShader;
import io.github.liticane.monoxide.util.render.shader.shaders.BlurShader;

public interface ShaderAccess {

    BlurShader blurShader = new BlurShader();

    BloomShader bloomShader = new BloomShader();


}
