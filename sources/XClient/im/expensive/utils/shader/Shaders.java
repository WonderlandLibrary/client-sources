package im.expensive.utils.shader;

import im.expensive.utils.shader.shaders.AlphaGlsl;
import im.expensive.utils.shader.shaders.ContrastGlsl;
import im.expensive.utils.shader.shaders.FontGlsl;
import im.expensive.utils.shader.shaders.GaussianBloomGlsl;
import im.expensive.utils.shader.shaders.KawaseDownGlsl;
import im.expensive.utils.shader.shaders.KawaseUpGlsl;
import im.expensive.utils.shader.shaders.MaskGlsl;
import im.expensive.utils.shader.shaders.OutlineGlsl;
import im.expensive.utils.shader.shaders.VertexGlsl;
import im.expensive.utils.shader.shaders.WhiteGlsl;
import lombok.Getter;
import im.expensive.utils.shader.shaders.RoundedGlsl;
import im.expensive.utils.shader.shaders.RoundedOutGlsl;
import im.expensive.utils.shader.shaders.SmoothGlsl;

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
