package koks.gui.clickgui;

import koks.Koks;
import koks.gui.clickgui.commonvalue.CommonPanel;
import koks.gui.clickgui.elements.ElementKeyBind;
import koks.modules.Module;
import koks.utilities.ColorUtil;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:10
 */
public class ClickGUI extends GuiScreen {

    public List<CategoryButton> panelList = new ArrayList<>();
    private int x, y, width, height, dragX, dragY;
    private boolean dragging;
    private final RenderUtils renderUtils = new RenderUtils();
    public final CommonPanel commonPanel;

    public Module.Category category = Module.Category.COMBAT;
    public final ColorUtil cl = new ColorUtil();

    public ClickGUI() {
        this.x = 50;
        this.y = 50;
        this.width = 350;
        this.height = 300;
        this.commonPanel = new CommonPanel(x + width + 50, y, 140, 20);
        Arrays.stream(Module.Category.values()).forEach(category -> panelList.add(new CategoryButton(category)));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (dragging) {
            this.x = dragX + mouseX;
            this.y = dragY + mouseY;
        }

        Gui.drawRect(x, y - 5, x + width, y + height, new Color(22, 22, 22, 255).getRGB());
        Gui.drawRect(x, y, x + 50, y + height, new Color(12, 12, 12, 255).getRGB());
        Gui.drawRect(x + 50, y, x + 51, y + height, new Color(40, 39, 42, 255).getRGB());

        int testRainbow = 0;
        for (int i = x; i < x + width - 2; i++) {
            Gui.drawRect(i + 1, y - 3, i + 2, y - 2, cl.rainbow(5000, testRainbow, 1));
            testRainbow += 1;
        }

        GL11.glPushMatrix();
        renderUtils.scissor(getX(), getY(), getX() + getWidth(), getY() + getHeight());
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        final float[] y = {this.y + 2.5F};
        panelList.forEach(panelButton -> {
            panelButton.setInformation(x + 3, y[0], 45, 45);
            panelButton.drawScreen(mouseX, mouseY);
            y[0] += 50;
        });
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        renderUtils.drawOutlineRect(x, this.y - 5, x + width, this.y + height, 2, new Color(40, 39, 42, 255));
        this.commonPanel.drawScreen(mouseX, mouseY);


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseX > x && mouseX < x + width && mouseY > y - 5 && mouseY < y + 4.5F && mouseButton == 0) {
            this.dragging = true;
            this.dragX = x - mouseX;
            this.dragY = y - mouseY;
        }
        if (mouseX > 0 && mouseX < 70 && mouseY > 0 && mouseY < 50 && mouseButton == 0) {
            mc.displayGuiScreen(Koks.getKoks().drawConfigManager);
        }
        if (mouseX > 0 && mouseX < 70 && mouseY > new ScaledResolution(mc).getScaledHeight() - 50 && mouseY < new ScaledResolution(mc).getScaledHeight() && mouseButton == 0) {
            mc.displayGuiScreen(Koks.getKoks().panelGUI);
        }
        panelList.forEach(panelButton -> panelButton.mouseClicked(mouseX, mouseY, mouseButton));
        this.commonPanel.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        panelList.forEach(CategoryButton::mouseReleased);
        this.commonPanel.mouseRelease();
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        AtomicBoolean settingKey = new AtomicBoolean(false);
        panelList.forEach(panelList -> panelList.MODULE_BUTTONS.forEach(moduleButton -> moduleButton.elementList.forEach(element -> {
            if (element instanceof ElementKeyBind) {
                if (((ElementKeyBind) element).settingKey) {
                    settingKey.set(true);
                    element.keyTyped(keyCode);
                }
            }
        })));
        if (settingKey.get())
            return;
        super.keyTyped(typedChar, keyCode);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
