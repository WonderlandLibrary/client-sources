package alphentus.gui.clickgui;

import alphentus.config.DrawConfigManager;
import alphentus.init.Init;
import alphentus.mod.ModCategory;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox
 * @since on 30/07/2020.
 */
public class ClickGUI extends GuiScreen {

    public int width, height, x, y;

    public String currentCategory = "";
    public ArrayList<Panels> panels = new ArrayList<Panels>();
    int mouse;
    private final GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("", 20, false, false, false);

    private Translate translate;

    public ClickGUI () {
        this.translate = new Translate(0, 0);
        x = 75;
        y = 75;
        height = 325;
        int xplus = 80;
        for (ModCategory modCategory : ModCategory.values()) {
            panels.add(new Panels(modCategory));
            xplus += 80;
        }
        width = x + xplus;
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks) {
        final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        translate.interpolate(resolution.getScaledWidth(), resolution.getScaledHeight(), 8);

        GL11.glPushMatrix();

        GL11.glTranslatef(resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2, 0);
        GL11.glScaled(translate.getX() / resolution.getScaledWidth(), translate.getY() / resolution.getScaledHeight(), 0);
        GL11.glTranslatef(-resolution.getScaledWidth() / 2, -resolution.getScaledHeight() / 2, 0);

        x = resolution.getScaledWidth() / 2 - resolution.getScaledWidth() / 3 + 40;
        width = x + panels.size() * 80 + 80;

        mouse = Mouse.getDWheel();

        Init.getInstance().blurUtil.blur(x + 80, y + 5, width - 80 - x, height - 4, 30);

        Gui.drawRect(x, y, width, y + height, new Color(59, 56, 59, 210).getRGB());
        Gui.drawRect(x, y, x + 80, y + height, new Color(62, 57, 62, 255).getRGB());


        fontRenderer.drawString(Init.getInstance().CLIENT_NAME, x + 80 / 2 - fontRenderer.getStringWidth(Init.getInstance().CLIENT_NAME) / 2, y + 20 / 2 - fontRenderer.getFontHeight() / 2, Init.getInstance().CLIENT_COLOR.getRGB(), true);

        int xPush = x + 80;
        for (Panels panels : this.panels) {
            panels.setPosition(xPush, y, 80, 25);
            panels.drawScreen(mouseX, mouseY, partialTicks);
            xPush += 80;
        }

        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseReleased (int mouseX, int mouseY, int state) {
        for (Panels panels : this.panels) {
            panels.mouseRelease();
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped (char typedChar, int keyCode) throws IOException {
        for (Panels panels : this.panels) {
            panels.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui () {
        this.translate = new Translate(0, 0);
        super.initGui();
    }

    public boolean isHoveredConfig(float mouseX, float mouseY) {
        return (mouseX > x && mouseX < x + 80 && mouseY > y && mouseY < y + 25);
    }

    @Override
    protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {

        for (Panels panels : this.panels) {
            panels.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (isHoveredConfig(mouseX, mouseY))
            mc.displayGuiScreen(new DrawConfigManager());

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
