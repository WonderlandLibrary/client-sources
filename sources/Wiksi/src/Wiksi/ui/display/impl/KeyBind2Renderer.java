package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.impl.render.HUD;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.client.KeyStorage;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.KawaseBlur;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class KeyBind2Renderer implements ElementRenderer
{
    private final Dragging dragging;
    private float animation;
    private float width;
    private float height;
    final ResourceLocation bind1 = new ResourceLocation("Wiksi/images/hud/bind1.png");
    final float iconSize = 10;

    public void render(final EventDisplay eventDisplay) {
        final MatrixStack ms = eventDisplay.getMatrixStack();
        final float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        final float fontSize = 6.5f;
        final float padding = 5.0f;
        final ITextComponent name = GradientUtil.gradient("Hotkeys");
        float maxWidth = Fonts.sfbold.getWidth(name, fontSize) + padding * 2.0f;
        final Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();



        DisplayUtils.drawRoundedRect(posX, posY + 1.0f, this.animation, 18.0f, 4.0f, ColorUtils.rgba(21, 21, 21, 215));
        Fonts.sfui.drawText(ms, "Hotkeys", posX + 20, posY + padding + 1.5f,ColorUtils.rgb(255,255,255), 6.5f);
        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        DisplayUtils.drawImage(bind1, posX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(255,255,255));
        DisplayUtils.drawRectVerticalW(posX + 18.0f, posY + 3.0f, width - 1, 14.0f, 3, ColorUtils.rgba(255, 255, 255, (int) (255 * 0.75f)));
        posY += fontSize + padding * 2.0f;
        float localHeight = fontSize + padding * 2.0f;
        posY += 3.0f;
        for (Function f : Wiksi.getInstance().getFunctionRegistry().getFunctions()) {
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
                DisplayUtils.drawRoundedRect(posX, posY, this.animation, 12.0f, 3.0f, ColorUtils.rgba(21, 21, 21, (int)(215.0 * f.getAnimation().getValue())));
                Fonts.sfui.drawText(ms, nameText, posX + 4.0f, posY + 2.5f, ColorUtils.rgba(255, 255, 255, (int)(255.0 * f.getAnimation().getValue())), fontSize);
                Fonts.sfui.drawText(ms, bindText, posX + this.animation - 4.0f - bindWidth, posY + 2.5f, ColorUtils.rgba(255, 255, 255, (int)(255.0 * f.getAnimation().getValue())), fontSize);
                DisplayUtils.drawRectVerticalW(posX + 63.0f, posY + 2.0f, width - 1, 8.0f, 3, ColorUtils.rgba(255, 255, 255, (int) (255 * 0.75f)));
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

    public KeyBind2Renderer(final Dragging dragging) {
        this.dragging = dragging;
    }
}
