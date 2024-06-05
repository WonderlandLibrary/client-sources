/*
 * Decompiled with CFR 0.150.
 */
package clickgui.panel.component.impl;

import java.awt.Color;

import clickgui.panel.Panel;
import clickgui.panel.component.Component;
import clickgui.setting.Setting;

import net.minecraft.client.Minecraft;
import white.floor.Main;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;

public final class BooleanComponent
extends Component {
    private int opacity = 120;
    private int animation = 20;
    float textHoverAnimate = 0.0f;
    float leftRectAnimation = 0.0f;
    double rightRectAnimation = 0.0;

    public BooleanComponent(Setting option, Panel panel, int x, int y, int width, int height) {
        super(panel, x, y, width, height);
        this.option = option;
    }

    @Override
    public void onDraw(int mouseX, int mouseY) {
        Panel parent = this.getPanel();
        int x = parent.getX() + this.getX();
        int y = parent.getY() + this.getY();
        boolean hovered = this.isMouseOver(mouseX, mouseY);
        if (hovered) {
            if (this.opacity < 200) {
                this.opacity += 5;
            }
        } else if (this.opacity > 120) {
            this.opacity -= 5;
        }
        if (this.option.getValBoolean()) {
            if (this.animation < 30) {
                ++this.animation;
            }
        } else if (this.animation > 20) {
            --this.animation;
        }

        int yTotal = 0;
        for (int i = 0; i < Main.featureDirector.getFeatures().size(); ++i) {
            yTotal += Fonts.neverlose500_16.getHeight() + 3;
        }

        DrawHelper.drawNewRect(x, y, x + this.getWidth(), y + this.getHeight(), parent.dragging ? new Color(25, 25, 25).getRGB() : DrawHelper.getColorWithOpacity(BACKGROUND, 255 - this.opacity).getRGB());
        int color = this.option.getValBoolean() ? -1 : new Color(this.opacity, this.opacity, this.opacity).getRGB();
        this.textHoverAnimate = AnimationHelper.animation(this.textHoverAnimate, hovered ? 2.3f : 2.0f, 0.01f);
        this.leftRectAnimation = AnimationHelper.animation(this.leftRectAnimation, this.option.getValBoolean() ? 10.0f : 17.0f, 1.0E-13f);
        this.rightRectAnimation = AnimationHelper.animation((float) this.rightRectAnimation, this.option.getValBoolean() ? 3 : 10, 1.0E-13f);
        DrawHelper.drawRoundedRect1(x + 3.5, y + 2.5, x + 13.5, y + this.height - 2.5, Main.getClientColor((float) (y + height / 1.5f - 4.5f), yTotal, 4).getRGB());
        DrawHelper.drawRoundedRect1(x + 4, y + 3, x + 13, y + this.height - 3, new Color(30, 30, 30).getRGB());
        Fonts.neverlose500_14.drawStringWithShadow(this.option.getName(), x + 16.5f, y + this.getHeight() - 9f, color);
        Fonts.stylesicons_14.drawString(this.option.getValBoolean() ? "H" : "", x + 5, y + 6.8, -1);

    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            this.option.setValBoolean(!this.option.getValBoolean());
        }
    }
}

