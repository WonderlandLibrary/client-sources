package club.bluezenith.ui.clickguis.novo.components.modules.values;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.math.RoundingMode;

import static club.bluezenith.ui.clickgui.ClickGui.i;
import static club.bluezenith.util.render.RenderUtil.rect;
import static java.lang.Math.*;
import static java.math.BigDecimal.valueOf;
import static net.minecraft.client.renderer.GlStateManager.*;

public class SliderComponent extends Component {
    private static final TFontRenderer fontHelvetica = FontUtil.createFont("helvetica", 27),
                                       fontSF = FontUtil.createFont("posaytightposaycleanposayfresh2", 27);

    private final Value<?> parent;
    private boolean lock, setValuesOnce;

    private float start, end, max;

    public SliderComponent(Value<?> parent, float x, float y) {
        super(x, y);
        this.parent = parent;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height - 1;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void draw(int mouseX, int mouseY, ScaledResolution scaledResolution) {
        final TFontRenderer fontSF = fontHelvetica;

        {
            float x = 3F + this.x,
                    y = this.y + .5F;

            fontSF.drawString(parent.name, x, y, -1, true);
            fontSF.drawString(String.valueOf(parent.get()), x + fontSF.getStringWidthF(parent.name) + 1.3F, y, -1, true);
        }

        if(!Mouse.isButtonDown(0))
            lock = false;

        max = width - 10F;

        drag(mouseX, mouseY);

        if(parent instanceof FloatValue) {
            final FloatValue val = (FloatValue) parent;

            final float valueClamped = MathHelper.clamp(val.get(), val.min, val.max);

            end = max * (valueClamped - val.min) / (val.max - val.min);
        } else {
            final IntegerValue val = (IntegerValue) parent;

            final float valueClamped = MathHelper.clamp(val.get().floatValue(), val.min, val.max);

            end = max * (valueClamped - val.min.floatValue()) / (val.max.floatValue() - val.min.floatValue());
        }

        drawSlider((start = 4F + x), 1 + y + fontSF.getHeight(parent.name) + 2, min(max, end));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float yx;

        if(mouseButton == 0 && i(mouseX, mouseY, start, yx = (y + fontSF.getHeight(parent.name) + 2) - 1, x + width - 10, yx + 4)) {
            lock = true;

        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(lock)
            lock = false;

    }

    private void drag(int mouseX, int mouseY) {
        if(lock && mouseX >= start && mouseX <= start + max) {
            if(parent instanceof FloatValue) {
                FloatValue floatValue = (FloatValue) parent;

                final float range = floatValue.max - floatValue.min;

                float value = floatValue.min + (mouseX - start) / max * range;
                value = round(value / floatValue.increment) * floatValue.increment;
                value = valueOf(value).setScale(2, RoundingMode.HALF_UP).floatValue();

                floatValue.set(value);
            } else {
                IntegerValue intValue = (IntegerValue) parent;

                final float range = intValue.max.floatValue() - intValue.min.floatValue();

                float value = intValue.min.floatValue() + (mouseX - start) / max * range;
                value = round(value / intValue.increment) * intValue.increment;

                intValue.set(valueOf(value).setScale(2, RoundingMode.HALF_UP).intValue());
            }

        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return isMouseInBounds(mouseX, mouseY);
    }

    private void drawSlider(final float x, final float y, final float xSize) {
        rect(x, y + 0.5, x+xSize, y + 1.5, Color.WHITE);
        rect(x + 0.5, y, x+xSize - 0.5, y + 2, Color.WHITE);

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        translate(xSize + x, y + 1, 0);
        GL11.glBegin(GL11.GL_TRIANGLES);

        for (float i = 22.5f; i < 360 + 22.5; i+=45) {

            GlStateManager.color(1, 1, 1, 1);
            GL11.glVertex3d(0, 0, 0);

            GL11.glVertex3d(cos((i + 45) * Math.PI / 180) * 2, (sin((i + 45) * Math.PI / 180) * 2), 0);

            GL11.glVertex3d(cos(i * Math.PI / 180) * 2, (sin(i * Math.PI / 180) * 2), 0);
        }

        GL11.glEnd();
        translate(-(xSize + x), -(y + 1), 0);

        disableBlend();
        enableAlpha();
        enableTexture2D();
    }

    @Override
    public boolean shouldLockDragging() {
        return lock;
    }

    @Override
    public boolean shouldUpdateWidth() {
        return true;
    }
}
