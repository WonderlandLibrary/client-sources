package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.Ellant;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.KawaseBlur;
import net.minecraft.util.text.ITextComponent;
import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.client.KeyStorage;
import fun.ellant.functions.api.Function;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import fun.ellant.events.EventDisplay;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.ui.display.ElementRenderer;
import net.minecraft.util.ResourceLocation;

public class TestKeyRenderer5 implements ElementRenderer {
    private final Dragging dragging;
    private float animation;
    private float width;
    private float height;
    final ResourceLocation bind1 = new ResourceLocation("expensive/images/hud/binds.png");
    final float iconSize = 10;

    public void render(final EventDisplay eventDisplay) {
        final MatrixStack ms = eventDisplay.getMatrixStack();
        final float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        final float fontSize = 6.5f;
        final float padding = 5.0f;
        final ITextComponent name = GradientUtil.gradient("Hotkeys", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        float maxWidth = Fonts.sfbold.getWidth(name, fontSize) + padding * 2.0f;
        final Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();


        DisplayUtils.drawRoundedRect(posX, posY + 1.0f, this.animation, 18.0f, 4.0f, ColorUtils.rgba(30, 30, 30, 255));
        Fonts.sfui.drawCenteredText(ms, name, posX + this.animation / 1.9f - 5, posY + padding + 1.5f, 6.5f);
        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        DisplayUtils.drawImage(bind1, posX + padding, posY + padding, iconSize, iconSize, colors);
        DisplayUtils.drawRectVerticalW(posX + 20.0f, posY + 3.0f, width - 1, 14.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
        posY += fontSize + padding * 2.0f;
        float localHeight = fontSize + padding * 2.0f;
        posY += 3.0f;
        for (Function f : Ellant.getInstance().getFunctionRegistry().getFunctions()) {
            f.getAnimation().update();
            if (f.getAnimation().getValue() > 0.0) {
                if (f.getBind() == 0) {
                    continue;
                }
                final String nameText = f.getName();
                final float nameWidth = Fonts.sfbold.getWidth(nameText, fontSize);
                final String bindText = "[" + KeyStorage.getKey(f.getBind()) + "]";
                final float bindWidth = Fonts.sfbold.getWidth(bindText, fontSize);
                final float localWidth = nameWidth + bindWidth + padding * 3.0f;
                DisplayUtils.drawRoundedRect(posX, posY, this.animation, 12.0f, 3.0f, ColorUtils.rgba(30, 30, 30, (int)(255.0 * f.getAnimation().getValue())));
                Fonts.sfui.drawText(ms, nameText, posX + 4.0f, posY + 2.0f, ColorUtils.rgba(230, 230, 230, (int)(255.0 * f.getAnimation().getValue())), fontSize);
                Fonts.sfui.drawText(ms, bindText, posX + this.animation - 4.0f - bindWidth, posY + 2.0f, ColorUtils.rgba(230, 230, 230, (int)(255.0 * f.getAnimation().getValue())), fontSize);
                DisplayUtils.drawRectVerticalW(posX + 63.0f, posY + 2.0f, width - 1, 8.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
                if (localWidth > maxWidth) {
                    maxWidth = localWidth;
                }
                posY += (float)((7.5f + padding) * f.getAnimation().getValue());
                localHeight += (float)((fontSize + padding) * f.getAnimation().getValue());

            }
        }
        this.animation = MathUtil.lerp(this.animation, Math.max(maxWidth, 80.0f), 10.0f);
        this.height = localHeight + 2.5f;
        this.dragging.setWidth(this.animation);
        this.dragging.setHeight(this.height);
    }

    public static void sizeAnimation(final double width, final double height, final double scale) {
        GlStateManager.translated(width, height, 0.0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0.0);
    }
    private void drawStyledRect(float posX,
                                float posY,
                                float x,
                                float y,
                                float width) {
        float textWidth = 8;
        float padding = 1;
        float height = 1;
        float radius = 2;

        KawaseBlur.blur.updateBlur(1, 2);
        KawaseBlur.blur.render(() -> {
            DisplayUtils.drawRoundedRect(posX, posY, textWidth + padding * 2, padding * 0, 2, ColorUtils.rgba(21, 21, 21, 155));
        });
        DisplayUtils.drawRoundedRect(posX - 0, posY, textWidth + 5, padding * 5, 2, ColorUtils.rgba(30, 30, 30, 155));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));
    }

    public TestKeyRenderer5(final Dragging dragging) {
        this.dragging = dragging;
    }
}
