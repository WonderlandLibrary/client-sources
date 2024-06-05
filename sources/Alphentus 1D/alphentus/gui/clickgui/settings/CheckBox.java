package alphentus.gui.clickgui.settings;

import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 30/07/2020.
 */
public class CheckBox {

    private final Setting setting;
    private int x, y, width, height;
    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei19;
    private final UnicodeFontRenderer fontRenderer2 = Init.getInstance().fontManager.myinghei22;
    private HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    private Translate translate;
    private float float2;

    public CheckBox(Setting setting) {
        this.setting = setting;
        this.translate = new Translate(0, 0);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

            Gui.drawRect(x + fontRenderer.getStringWidth(setting.getName() + " ") + 1, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, x + fontRenderer.getStringWidth(setting.getName() + " ") + 1 + fontRenderer.FONT_HEIGHT, y + height / 2 + fontRenderer.FONT_HEIGHT / 2, 0x99000000);
            fontRenderer.drawStringWithShadow(setting.getName(), x, y + height / 2 - fontRenderer.FONT_HEIGHT / 2 - 1, -1);

            if (setting.isState())
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("\u2713", x + fontRenderer.getStringWidth(setting.getName() + " ") + 3.5F, y + height / 2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2 - 0.5F, -1);

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovering(mouseX, mouseY) && mouseButton == 0)
            this.setting.setState(!this.setting.isState());
    }

    public boolean isHovering(int mouseX, int mouseY) {
            return mouseX > x + fontRenderer.getStringWidth(setting.getName() + " ") + 1 && mouseX < x + fontRenderer.getStringWidth(setting.getName() + " ") + 1 + fontRenderer.FONT_HEIGHT && mouseY > y + height / 2 - fontRenderer.FONT_HEIGHT / 2 && mouseY < y + height / 2 + fontRenderer.FONT_HEIGHT / 2;
    }

    public Setting getSetting() {
        return setting;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
