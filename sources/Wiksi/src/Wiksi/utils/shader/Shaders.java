package src.Wiksi.utils.shader;

import src.Wiksi.utils.shader.shaders.AlphaGlsl;
import src.Wiksi.utils.shader.shaders.ContrastGlsl;
import src.Wiksi.utils.shader.shaders.FontGlsl;
import src.Wiksi.utils.shader.shaders.GaussianBloomGlsl;
import src.Wiksi.utils.shader.shaders.KawaseDownGlsl;
import src.Wiksi.utils.shader.shaders.KawaseUpGlsl;
import src.Wiksi.utils.shader.shaders.MaskGlsl;
import src.Wiksi.utils.shader.shaders.OutlineGlsl;
import src.Wiksi.utils.shader.shaders.VertexGlsl;
import src.Wiksi.utils.shader.shaders.WhiteGlsl;
import lombok.Getter;
import src.Wiksi.utils.shader.shaders.RoundedGlsl;
import src.Wiksi.utils.shader.shaders.RoundedOutGlsl;
import src.Wiksi.utils.shader.shaders.SmoothGlsl;

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
    @Getter
    private IShader MainMenu = new MaskGlsl();
}
