package digital.rbq.gui.clickgui.classic.component;

import digital.rbq.gui.clickgui.classic.ClickGUI;
import digital.rbq.gui.clickgui.classic.GuiRenderUtils;
import digital.rbq.gui.clickgui.classic.window.Window;
import digital.rbq.gui.fontRenderer.FontManager;
import digital.rbq.module.implement.Render.Hud;
import digital.rbq.utility.ColorUtils;

import java.awt.*;

public class Label extends Component {
    public Label(Window window, int offX, int offY, String title) {
        super(window, offX, offY, title);
        this.width = ClickGUI.settingsWidth;
        this.height = (int) (FontManager.tiny.getHeight(this.title) + 3);
        this.type = "Label";
    }

    public void render(int mouseX, int mouseY) {
        FontManager.tiny.drawString(this.title, (int) (this.x + (this.width / 2 - FontManager.tiny.getStringWidth(this.title) / 2)), this.y + (this.height / 2 - FontManager.tiny.getHeight(this.title) / 2) + 2, Hud.isLightMode ? ColorUtils.GREY.c : 16777215);

        float space = this.width - FontManager.tiny.getStringWidth(this.title) - 8;

        if (space > 2) {
            float width = 0.5f;
            GuiRenderUtils.drawRect(this.x + 2, this.y + this.height / 2 + 1, space / 2, width, new Color(195, 195, 195));
            GuiRenderUtils.drawRect(this.x + this.width - 2 - space / 2, this.y + this.height / 2 + 1, space / 2, width, new Color(195, 195, 195));
        }

    }

    @Override
    public void mouseUpdates(int var1, int var2, boolean var3) {

    }
}
