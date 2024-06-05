
package clickgui.panel.component.impl;

import java.awt.Color;
import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;

import clickgui.panel.Panel;
import clickgui.panel.component.Component;
import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.font.Fonts;
import me.rich.helpers.render.ColorHelper;
import me.rich.helpers.render.RenderHelper;
import net.minecraft.client.Minecraft;

public final class ModeComponent
extends Component {
    private int opacity = 120;

    public ModeComponent(Setting option, Panel panel, int x, int y, int width, int height) {
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
        RenderHelper.drawNewRect(x, y, x + this.getWidth(), y + this.getHeight(), parent.dragging ? new Color(25, 25, 25).getRGB() : ColorHelper.getColorWithOpacity(BACKGROUND, 255 - this.opacity).getRGB());
        int color = new Color(this.opacity, this.opacity, this.opacity).getRGB();
        RenderHelper.drawNewRect(x, y, x + 1, y + this.getHeight(), Main.getClientColor().getRGB());
        Fonts.neverlose500_14.drawStringWithShadow(String.format("%s: %s".toLowerCase(), this.option.getName().toLowerCase(), (Object)ChatFormatting.GRAY + this.option.getValString().toLowerCase()), x + 3.5, y + (float)this.getHeight() / 2.0f - 2.0f, -1);
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            ArrayList<String> options = this.option.getOptions();
            int index = options.indexOf(this.option.getValString());
            if (mouseButton == 0) {
                ++index;
            } else if (mouseButton == 1) {
                --index;
            }
            if (index >= options.size()) {
                index = 0;
            } else if (index < 0) {
                index = options.size() - 1;
            }
            this.option.setValString(this.option.getOptions().get(index));
        }
    }
}

