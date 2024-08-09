package wtf.resolute.utiled.shader;

import wtf.resolute.utiled.shader.shaders.*;
import lombok.Getter;

public class Shaders {
    @Getter
    private static Shaders Instance = new Shaders();
    @Getter
    private IShader font = new FontGlsl();
    @Getter
    private IShader vertex = new VertexGlsl();
    @Getter
    private IShader rounded = new RoundedGlsl();
    @Getter
    private IShader roundedout = new RoundedOutGlsl();
    @Getter
    private IShader smooth = new SmoothGlsl();
    @Getter
    private IShader white = new WhiteGlsl();
    @Getter
    private IShader alpha = new AlphaGlsl();
    @Getter
    private IShader gaussianbloom = new GaussianBloomGlsl();
    @Getter
    private IShader kawaseUp = new KawaseUpGlsl();
    @Getter
    private IShader kawaseDown = new KawaseDownGlsl();
    @Getter
    private IShader outline = new OutlineGlsl();
    @Getter
    private IShader contrast = new ContrastGlsl();
    @Getter
    private IShader mask = new MaskGlsl();
}
