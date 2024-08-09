/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader;

import mpp.venusfr.utils.shader.IShader;
import mpp.venusfr.utils.shader.shaders.AlphaGlsl;
import mpp.venusfr.utils.shader.shaders.ContrastGlsl;
import mpp.venusfr.utils.shader.shaders.FontGlsl;
import mpp.venusfr.utils.shader.shaders.GaussianBloomGlsl;
import mpp.venusfr.utils.shader.shaders.KawaseDownGlsl;
import mpp.venusfr.utils.shader.shaders.KawaseUpGlsl;
import mpp.venusfr.utils.shader.shaders.MaskGlsl;
import mpp.venusfr.utils.shader.shaders.OutlineGlsl;
import mpp.venusfr.utils.shader.shaders.RoundedGlsl;
import mpp.venusfr.utils.shader.shaders.RoundedOutGlsl;
import mpp.venusfr.utils.shader.shaders.SmoothGlsl;
import mpp.venusfr.utils.shader.shaders.VertexGlsl;
import mpp.venusfr.utils.shader.shaders.WhiteGlsl;

public class Shaders {
    private static Shaders Instance = new Shaders();
    private IShader font = new FontGlsl();
    private IShader vertex = new VertexGlsl();
    private IShader rounded = new RoundedGlsl();
    private IShader roundedout = new RoundedOutGlsl();
    private IShader smooth = new SmoothGlsl();
    private IShader white = new WhiteGlsl();
    private IShader alpha = new AlphaGlsl();
    private IShader gaussianbloom = new GaussianBloomGlsl();
    private IShader kawaseUp = new KawaseUpGlsl();
    private IShader kawaseDown = new KawaseDownGlsl();
    private IShader outline = new OutlineGlsl();
    private IShader contrast = new ContrastGlsl();
    private IShader mask = new MaskGlsl();

    public static Shaders getInstance() {
        return Instance;
    }

    public IShader getFont() {
        return this.font;
    }

    public IShader getVertex() {
        return this.vertex;
    }

    public IShader getRounded() {
        return this.rounded;
    }

    public IShader getRoundedout() {
        return this.roundedout;
    }

    public IShader getSmooth() {
        return this.smooth;
    }

    public IShader getWhite() {
        return this.white;
    }

    public IShader getAlpha() {
        return this.alpha;
    }

    public IShader getGaussianbloom() {
        return this.gaussianbloom;
    }

    public IShader getKawaseUp() {
        return this.kawaseUp;
    }

    public IShader getKawaseDown() {
        return this.kawaseDown;
    }

    public IShader getOutline() {
        return this.outline;
    }

    public IShader getContrast() {
        return this.contrast;
    }

    public IShader getMask() {
        return this.mask;
    }
}

