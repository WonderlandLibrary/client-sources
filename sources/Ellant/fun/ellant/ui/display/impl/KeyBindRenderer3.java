package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Function;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.client.KeyStorage;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;

public class KeyBindRenderer3 implements ElementRenderer {
    private final Dragging dragging;
    private float width;
    private float height;

    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        float fontSize = 6.0F;
        float padding = 5.0F;
        ITextComponent name = GradientUtil.gradient("Hotkeys", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        float topHeight = 15.0F;
        if ((Boolean)Ellant.getInstance().getFunctionRegistry().getHud().shadow.get()) {
            DisplayUtils.drawShadow(posX, posY, this.width, topHeight, 6, ColorUtils.getColor1(), ColorUtils.getColor2());
        }

        DisplayUtils.drawRoundedRect(posX, posY, this.width, topHeight, new Vector4f(4.0F, 4.0F, 4.0F, 4.0F), (new Color(12, 13, 13, 230)).getRGB());
        Fonts.sfbold.drawCenteredText(ms, name, posX + this.width / 2.0F, posY + topHeight / 2.0F - 3.5F, 7.0F);
        posY += fontSize + padding + 4.0F;
        float maxWidth = Fonts.sfbold.getWidth(name, fontSize) + padding * 2.0F;
        float localHeight = fontSize + padding;
        posY += 3.0F;
        Iterator var12 = Ellant.getInstance().getFunctionRegistry().getFunctions().iterator();

        while(var12.hasNext()) {
            Function f = (Function)var12.next();
            f.getAnimation().update();
            if (f.getAnimation().getValue() > 0.0D && f.getBind() != 0) {
                String nameText = f.getName();
                float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);
                String bindText = "[" + KeyStorage.getKey(f.getBind()) + "]";
                float bindWidth = Fonts.sfMedium.getWidth(bindText, fontSize);
                float localWidth = nameWidth + bindWidth + padding * 3.0F;
                if ((Boolean)Ellant.getInstance().getFunctionRegistry().getHud().shadow.get()) {
                    DisplayUtils.drawShadow(posX, posY, this.width, 15.0F, 6, ColorUtils.getColor1(), ColorUtils.getColor2());
                }

                DisplayUtils.drawRoundedRect(posX, posY, this.width, 15.0F, new Vector4f(4.0F, 4.0F, 4.0F, 4.0F), (new Color(12, 13, 12, 210)).getRGB());
                Fonts.sfbold.drawText(ms, nameText, posX + padding, posY + 4.5F, ColorUtils.rgba(255, 255, 255, (int)(255.0D * f.getAnimation().getValue())), fontSize);
                Fonts.sfbold.drawText(ms, bindText, posX + this.width - padding - bindWidth, posY + 4.5F, ColorUtils.rgba(255, 255, 255, (int)(255.0D * f.getAnimation().getValue())), fontSize);
                if (localWidth > maxWidth) {
                    maxWidth = localWidth;
                }

                posY += (float)((double)(fontSize + padding) * f.getAnimation().getValue()) + 6.0F;
                localHeight += (float)((double)(fontSize + padding) * f.getAnimation().getValue());
            }
        }

        this.width = Math.max(maxWidth, 66.0F);
        this.height = localHeight;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
    }

    public KeyBindRenderer3(Dragging dragging) {
        this.dragging = dragging;
    }
}