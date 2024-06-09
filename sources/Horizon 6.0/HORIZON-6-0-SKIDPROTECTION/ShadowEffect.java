package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.ConvolveOp;
import java.awt.RenderingHints;
import java.awt.image.Kernel;
import java.util.Iterator;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class ShadowEffect implements ConfigurableEffect
{
    public static final int HorizonCode_Horizon_È = 16;
    public static final float[][] Â;
    private Color Ý;
    private float Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private int à;
    private int Ø;
    
    static {
        Â = Ý(16);
    }
    
    public ShadowEffect() {
        this.Ý = Color.black;
        this.Ø­áŒŠá = 0.6f;
        this.Âµá€ = 2.0f;
        this.Ó = 2.0f;
        this.à = 0;
        this.Ø = 1;
    }
    
    public ShadowEffect(final Color color, final int xDistance, final int yDistance, final float opacity) {
        this.Ý = Color.black;
        this.Ø­áŒŠá = 0.6f;
        this.Âµá€ = 2.0f;
        this.Ó = 2.0f;
        this.à = 0;
        this.Ø = 1;
        this.Ý = color;
        this.Âµá€ = xDistance;
        this.Ó = yDistance;
        this.Ø­áŒŠá = opacity;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BufferedImage image, Graphics2D g, final UnicodeFont unicodeFont, final Glyph glyph) {
        g = (Graphics2D)g.create();
        g.translate(this.Âµá€, this.Ó);
        g.setColor(new Color(this.Ý.getRed(), this.Ý.getGreen(), this.Ý.getBlue(), Math.round(this.Ø­áŒŠá * 255.0f)));
        g.fill(glyph.Âµá€());
        for (final Effect effect : unicodeFont.µÕ()) {
            if (effect instanceof OutlineEffect) {
                final Composite composite = g.getComposite();
                g.setComposite(AlphaComposite.Src);
                g.setStroke(((OutlineEffect)effect).Âµá€());
                g.draw(glyph.Âµá€());
                g.setComposite(composite);
                break;
            }
        }
        g.dispose();
        if (this.à > 1 && this.à < 16 && this.Ø > 0) {
            this.HorizonCode_Horizon_È(image);
        }
    }
    
    private void HorizonCode_Horizon_È(final BufferedImage image) {
        final float[] matrix = ShadowEffect.Â[this.à - 1];
        final Kernel gaussianBlur1 = new Kernel(matrix.length, 1, matrix);
        final Kernel gaussianBlur2 = new Kernel(1, matrix.length, matrix);
        final RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        final ConvolveOp gaussianOp1 = new ConvolveOp(gaussianBlur1, 1, hints);
        final ConvolveOp gaussianOp2 = new ConvolveOp(gaussianBlur2, 1, hints);
        final BufferedImage scratchImage = EffectUtil.HorizonCode_Horizon_È();
        for (int i = 0; i < this.Ø; ++i) {
            gaussianOp1.filter(image, scratchImage);
            gaussianOp2.filter(scratchImage, image);
        }
    }
    
    public Color HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final Color color) {
        this.Ý = color;
    }
    
    public float Ý() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final float distance) {
        this.Âµá€ = distance;
    }
    
    public float Ø­áŒŠá() {
        return this.Ó;
    }
    
    public void Â(final float distance) {
        this.Ó = distance;
    }
    
    public int Âµá€() {
        return this.à;
    }
    
    public void HorizonCode_Horizon_È(final int blurKernelSize) {
        this.à = blurKernelSize;
    }
    
    public int Ó() {
        return this.Ø;
    }
    
    public void Â(final int blurPasses) {
        this.Ø = blurPasses;
    }
    
    public float à() {
        return this.Ø­áŒŠá;
    }
    
    public void Ý(final float opacity) {
        this.Ø­áŒŠá = opacity;
    }
    
    @Override
    public String toString() {
        return "Shadow";
    }
    
    @Override
    public List Â() {
        final List values = new ArrayList();
        values.add(EffectUtil.HorizonCode_Horizon_È("Color", this.Ý));
        values.add(EffectUtil.HorizonCode_Horizon_È("Opacity", this.Ø­áŒŠá, 0.0f, 1.0f, "This setting sets the translucency of the shadow."));
        values.add(EffectUtil.HorizonCode_Horizon_È("X distance", this.Âµá€, Float.MIN_VALUE, Float.MAX_VALUE, "This setting is the amount of pixels to offset the shadow on the x axis. The glyphs will need padding so the shadow doesn't get clipped."));
        values.add(EffectUtil.HorizonCode_Horizon_È("Y distance", this.Ó, Float.MIN_VALUE, Float.MAX_VALUE, "This setting is the amount of pixels to offset the shadow on the y axis. The glyphs will need padding so the shadow doesn't get clipped."));
        final List options = new ArrayList();
        options.add(new String[] { "None", "0" });
        for (int i = 2; i < 16; ++i) {
            options.add(new String[] { String.valueOf(i) });
        }
        final String[][] optionsArray = options.toArray(new String[options.size()][]);
        values.add(EffectUtil.HorizonCode_Horizon_È("Blur kernel size", String.valueOf(this.à), optionsArray, "This setting controls how many neighboring pixels are used to blur the shadow. Set to \"None\" for no blur."));
        values.add(EffectUtil.HorizonCode_Horizon_È("Blur passes", this.Ø, "The setting is the number of times to apply a blur to the shadow. Set to \"0\" for no blur."));
        return values;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final List values) {
        for (final HorizonCode_Horizon_È value : values) {
            if (value.HorizonCode_Horizon_È().equals("Color")) {
                this.Ý = (Color)value.Ý();
            }
            else if (value.HorizonCode_Horizon_È().equals("Opacity")) {
                this.Ø­áŒŠá = (float)value.Ý();
            }
            else if (value.HorizonCode_Horizon_È().equals("X distance")) {
                this.Âµá€ = (float)value.Ý();
            }
            else if (value.HorizonCode_Horizon_È().equals("Y distance")) {
                this.Ó = (float)value.Ý();
            }
            else if (value.HorizonCode_Horizon_È().equals("Blur kernel size")) {
                this.à = Integer.parseInt((String)value.Ý());
            }
            else {
                if (!value.HorizonCode_Horizon_È().equals("Blur passes")) {
                    continue;
                }
                this.Ø = (int)value.Ý();
            }
        }
    }
    
    private static float[][] Ý(final int level) {
        final float[][] pascalsTriangle = Ø­áŒŠá(level);
        final float[][] gaussianTriangle = new float[pascalsTriangle.length][];
        for (int i = 0; i < gaussianTriangle.length; ++i) {
            float total = 0.0f;
            gaussianTriangle[i] = new float[pascalsTriangle[i].length];
            for (int j = 0; j < pascalsTriangle[i].length; ++j) {
                total += pascalsTriangle[i][j];
            }
            final float coefficient = 1.0f / total;
            for (int k = 0; k < pascalsTriangle[i].length; ++k) {
                gaussianTriangle[i][k] = coefficient * pascalsTriangle[i][k];
            }
        }
        return gaussianTriangle;
    }
    
    private static float[][] Ø­áŒŠá(int level) {
        if (level < 2) {
            level = 2;
        }
        final float[][] triangle = new float[level][];
        triangle[0] = new float[1];
        triangle[1] = new float[2];
        triangle[0][0] = 1.0f;
        triangle[1][0] = 1.0f;
        triangle[1][1] = 1.0f;
        for (int i = 2; i < level; ++i) {
            (triangle[i] = new float[i + 1])[0] = 1.0f;
            triangle[i][i] = 1.0f;
            for (int j = 1; j < triangle[i].length - 1; ++j) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
        return triangle;
    }
}
