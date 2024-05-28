package arsenic.utils.font;

import arsenic.utils.interfaces.IFontRenderer;
import arsenic.utils.render.PosInfo;
import org.lwjgl.opengl.GL11;

import java.util.function.BiConsumer;

public class FontRendererExtension<T extends IFontRenderer>{

    float scale = 1f;
    float scaleReciprocal = 1f;
    float tempScale = 1f;
    float tempScaleReciprocal = 1f;

    private IFontRenderer fontRenderer = null;

    public FontRendererExtension(T fontRenderer) {
        this.fontRenderer = fontRenderer;
    }
    public void resetScale() {
        setScale(1f);
    }
    public float getWidth(String text) {
        return fontRenderer.getWidth(text);
    }
    public float getHeight(String text) {return fontRenderer.getHeight(text);}

    public void scale(float scale) {
        setScale(this.scale * scale);
    }

    public void setScale(float scale) {
        this.scale = scale;
        scaleReciprocal = 1f/scale;
    }

    public BiConsumer<PosInfo, String> getScaleModifier(float scale) {
        return (posInfo, string) -> {
            this.tempScaleReciprocal = 1f/scale;
            this.tempScale = scale;
            posInfo.setX(posInfo.getX() * tempScaleReciprocal);
            posInfo.setY(posInfo.getY() * tempScaleReciprocal);
        };
    }

    public final BiConsumer<PosInfo, String> CENTREX = (posInfo, string) -> posInfo.moveX(- (fontRenderer.getWidth(string)/2f));
    public final BiConsumer<PosInfo, String> CENTREY = (posInfo, string) -> posInfo.moveY(- (fontRenderer.getHeight(string)/2f));
    public final BiConsumer<PosInfo, String> LEFTSHIFTX = (posInfo, string) -> posInfo.moveX(- (fontRenderer.getWidth(string)));
    private final BiConsumer<PosInfo, String> SCALE = (posInfo, string) -> {
        posInfo.setX(posInfo.getX() * scaleReciprocal);
        posInfo.setY(posInfo.getY() * scaleReciprocal);
    };

    public void drawString(String text, float x, float y, int color, BiConsumer<PosInfo, String> ... modifiers) {
        PosInfo posInfo = new PosInfo(x, y);
        setup(posInfo, text, modifiers);
        fontRenderer.drawString(text, posInfo.getX(), posInfo.getY(), color);
        finsh();
    }

    public void drawStringWithShadow(String text, float x, float y, int color, BiConsumer<PosInfo, String> ... modifiers) {
        PosInfo posInfo = new PosInfo(x, y);
        setup(posInfo, text, modifiers);
        fontRenderer.drawStringWithShadow(text, posInfo.getX(), posInfo.getY(), color);
        finsh();
    }

    //issues with y values btw
    public void drawWrappingString(String unSplitText, float x, float y, int color, BiConsumer<PosInfo, String> ... modifiers) {
        for(String text : unSplitText.split("\n")) {
            drawString(text, x, y, color, modifiers);
            y += fontRenderer.getHeight(text);
        }
    }

    private void setup(PosInfo posInfo, String text, BiConsumer<PosInfo, String> ... modifiers) {
        for(BiConsumer<PosInfo, String> modifier : modifiers) {
            modifier.accept(posInfo, text);
        }
        float scale = tempScale * this.scale;
        if(scale != 1f) {
            SCALE.accept(posInfo, text);
            GL11.glScalef(scale, scale, scale);
        }
    }

    private void finsh() {
        float scale = tempScale * this.scale;
        if(scale != 1f) {
            float scaleReciprocal = 1f/(tempScale * this.scale);
            GL11.glScalef(scaleReciprocal, scaleReciprocal, scaleReciprocal);
        }
        this.tempScale = 1f;
        this.tempScaleReciprocal = 1f;
    }
}
