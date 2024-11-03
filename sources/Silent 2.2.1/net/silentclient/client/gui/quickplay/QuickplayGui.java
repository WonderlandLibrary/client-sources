package net.silentclient.client.gui.quickplay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.hypixel.QuickPlayMod;
import net.silentclient.client.mods.util.Server;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class QuickplayGui extends SilentScreen {
    private SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
    private float scrollY = 0;

    @Override
    public void initGui() {
        super.initGui();
        MenuBlurUtils.loadBlur();
        defaultCursor = false;
        this.scrollY = 0;
        this.buttonList.clear();
        this.buttonList.add(new IconButton(0, this.width - 14 - 3,  3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        MenuBlurUtils.renderBackground(this);

        Client.getInstance().getSilentFontRenderer().drawString("Quickplay",3, 3, 14, SilentFontRenderer.FontType.TITLE);

        super.drawScreen(mouseX, mouseY, partialTicks);

        int modeBaseX = this.width / 2;

        int column = 1;
        float modeY = 24 - scrollAnimation.getValue();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
        int s = r.getScaleFactor();
        int listHeight = height - 24;
        int translatedY = r.getScaledHeight() - 24 - listHeight;
        GL11.glScissor(0 * s, translatedY * s, this.width * s, listHeight * s);

        for(QuickPlayMod.QuickplayModeType quickplayMode : (Server.isRuHypixel() ? QuickPlayMod.ruhypixelQuickplayModes : QuickPlayMod.hypixelQuickplayModes)) {
            int modeX = column == 1 ? modeBaseX - 155 : modeBaseX + 5;
            boolean isHovered = MouseUtils.isInside(mouseX, mouseY, modeX, modeY, 150, 30);
            if(isHovered) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
                RenderUtil.drawRoundedRect(modeX, modeY, 150, 30, 3, new Color(255, 255, 255,  30).getRGB());
            }
            RenderUtil.drawRoundedOutline(modeX, modeY, 150, 30, 3, 1, Theme.borderColor().getRGB());
            RenderUtil.drawImage(new ResourceLocation("silentclient/mods/quickplay/"+quickplayMode.icon+".png"), modeX + 2, modeY + 2, 26, 26);
            Client.getInstance().getSilentFontRenderer().drawString(quickplayMode.name, modeX + 32, modeY + 8, 14, SilentFontRenderer.FontType.TITLE);

            if(column == 1) {
                column = 2;
            } else {
                column = 1;
                modeY += 40;
            }
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        scrollAnimation.setAnimation(scrollY, 12);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dw = Mouse.getEventDWheel();
        double newScrollY = this.scrollY;
        int height = this.height;
        if(dw != 0) {
            if (dw > 0) {
                dw = -1;
            } else {
                dw = 1;
            }
            float amountScrolled = (float) (dw * 10);
            if (newScrollY + amountScrolled > 0)
                newScrollY += amountScrolled;
            else
                newScrollY = 0;
            if((newScrollY < (((Math.ceil(((Server.isRuHypixel() ? QuickPlayMod.ruhypixelQuickplayModes : QuickPlayMod.hypixelQuickplayModes).size() / 2)) + 1) * 40) + 24) && ((((Math.ceil((Server.isRuHypixel() ? QuickPlayMod.ruhypixelQuickplayModes : QuickPlayMod.hypixelQuickplayModes).size() / 2)) + 1) * 40) + 24) > height) || amountScrolled < 0) {
                this.scrollY = (float) newScrollY;
                if(this.scrollY < 0) {
                    this.scrollY = 0;
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int modeBaseX = this.width / 2;

        int column = 1;
        float modeY = 24 - scrollAnimation.getValue();

        for(QuickPlayMod.QuickplayModeType quickplayMode : (Server.isRuHypixel() ? QuickPlayMod.ruhypixelQuickplayModes : QuickPlayMod.hypixelQuickplayModes)) {
            int modeX = column == 1 ? modeBaseX - 155 : modeBaseX + 5;
            boolean isHovered = MouseUtils.isInside(mouseX, mouseY, modeX, modeY, 150, 30);
            if(isHovered) {
                mc.displayGuiScreen(new QuickplayModeGui(quickplayMode));
                break;
            }

            if(column == 1) {
                column = 2;
            } else {
                column = 1;
                modeY += 40;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        MenuBlurUtils.unloadBlur();
    }
}
