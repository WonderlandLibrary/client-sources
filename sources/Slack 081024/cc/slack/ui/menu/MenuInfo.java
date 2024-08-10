package cc.slack.ui.menu;

import cc.slack.start.Slack;
import cc.slack.features.modules.impl.other.Tweaks;
import cc.slack.ui.clickgui.ClickGui;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.other.FileUtil;
import cc.slack.utils.render.RenderUtil;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static net.minecraft.client.gui.GuiOverlayDebug.bytesToMb;

public class MenuInfo extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("slack/menu/mainmenu.jpg"));
        drawModalRectWithCustomSizedTexture(0, 0,0,0, this.width, this.height, this.width, this.height);
        GlStateManager.pushMatrix();
        GlStateManager.translate(width/2f, height/2f - mc.MCfontRenderer.FONT_HEIGHT/2f, 0);
        GlStateManager.scale(3, 3, 1);
        GlStateManager.translate(-(width/1.69f), -(height/1.77),0);
        GlStateManager.popMatrix();
        RenderUtil.drawRoundedRect(398F, 100F, 608F, 400F, 8F, new Color(0,0,0,170).getRGB());
        RenderUtil.drawRoundedRect(98F, 100F, 308F, 400F, 8F, new Color(0,0,0,170).getRGB());
        RenderUtil.drawRoundedRect(698F, 100F, 908F, 400F, 8F, new Color(0,0,0,170).getRGB());
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;


        // Client detailed information
        Fonts.axi35.drawString("Detailed Information", 707, 120, -1);


        if (Objects.equals(Slack.getInstance().info.getType(), "Release")) {
            Fonts.apple20.drawString("Type: ", 707, 160, -1);
            Fonts.apple20.drawString(Slack.getInstance().info.getType(), 735, 160, new Color(0, 200, 0).getRGB());
        } else if (Objects.equals(Slack.getInstance().info.getType(), "Beta")) {
            Fonts.apple20.drawString("Type: ", 707, 160, -1);
            Fonts.apple20.drawString(Slack.getInstance().info.getType(), 735, 160, new Color(185, 0, 54).getRGB());
        } else {
            Fonts.apple20.drawString("Type: ", 707, 160, -1);
            Fonts.apple20.drawString(Slack.getInstance().info.getType(), 735, 160, new Color(31, 48, 189).getRGB());
        }


        Fonts.apple20.drawString("Version: ", 707, 180, -1);
        Fonts.apple20.drawString(Slack.getInstance().info.getVersion(), 747, 180, new Color(0, 200, 0).getRGB());


        if (Objects.equals(Slack.getInstance().getModuleManager().getInstance(Tweaks.class).status, "OFF")) {
            Fonts.apple20.drawString("RPC Status: ", 707, 200, -1);
            Fonts.apple20.drawString(Slack.getInstance().getModuleManager().getInstance(Tweaks.class).status, 763, 200, new Color(200, 0, 0).getRGB());
        } else {
            Fonts.apple20.drawString("RPC Status: ", 707, 200, -1);
            Fonts.apple20.drawString(Slack.getInstance().getModuleManager().getInstance(Tweaks.class).status, 763, 200, new Color(0, 200, 0).getRGB());

        }
        Fonts.apple20.drawString("Module Manager Status: ", 707, 220, -1);
        Fonts.apple20.drawString("ON", 820, 220, new Color(0, 200, 0).getRGB());


        Fonts.apple20.drawString("Config Manager Status: ", 707, 240, -1);
        Fonts.apple20.drawString("ON", 815, 240, new Color(0, 200, 0).getRGB());

        Fonts.apple20.drawString("CMD Manager Status: ", 707, 260, -1);
        Fonts.apple20.drawString("ON", 807, 260, new Color(0, 200, 0).getRGB());

        if (Objects.equals(Slack.getInstance().info.getVersion(), "Release")) {
            Fonts.apple20.drawString("Client Build Status: ", 707, 280, -1);
            Fonts.apple20.drawString("Stable", 807, 280, new Color(0, 200, 0).getRGB());
        } else {
            Fonts.apple20.drawString("Client Build Status: ", 707, 280, -1);
            Fonts.apple20.drawString("Unstable", 795, 280, new Color(200, 0, 0).getRGB());
        }


        // Client Things Menu
        Fonts.axi35.drawString("Client", 475, 120, -1);


        // System Specs Font

        Fonts.axi35.drawString("System Specs", 140, 120, -1);
        Fonts.apple20.drawString("GPU: ", 105, 160, -1);
        Fonts.apple18.drawString(Config.openGlRenderer, 105, 180, -1);
        Fonts.apple20.drawString("CPU: ", 105, 200, -1);
        Fonts.apple18.drawString(OpenGlHelper.func_183029_j(), 105, 220, -1);

        // Memory Info Font
        Fonts.axi35.drawString("Memory Info", 140, 260, -1);
        Fonts.apple20.drawString("Total Memory (RAM): ", 105, 300, -1);
        Fonts.apple18.drawString(String.valueOf(String.format("% 2d%% %03dMB/%03dMB", l * 100L / i, bytesToMb(l), bytesToMb(i))), 200, 300, -1);
        Fonts.apple20.drawString("Allocated Memory (RAM): ", 105, 320, -1);
        Fonts.apple18.drawString(String.valueOf(String.format("% 2d%% %03dMB", j * 100L / i, bytesToMb(j))), 220, 320, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);


    }


    @Override
    public void initGui() {

        this.menuList.add(new MainMenuButton(1, width - 460, height / 2 - 40, "Show ClickGUI"));
        this.menuList.add(new MainMenuButton(2, width - 460, height / 2 - 15, "Open Config Folder"));
        this.menuList.add(new MainMenuButton(3, width - 460, height / 2 + 10, "Join Discord Server"));
        this.menuList.add(new MainMenuButton(4, width - 460, height / 2 + 35, "Check Website"));

        super.initGui();
    }

    @SuppressWarnings("all")
    @Override
    protected void actionPerformedMenu(MainMenuButton buttonMenu) throws IOException {
        super.actionPerformedMenu(buttonMenu);

        if(buttonMenu.id == 1) {
            mc.displayGuiScreen(new ClickGui());
        }

        if(buttonMenu.id == 2) {
            FileUtil.showFolder("/SlackClient/configs");
        }

        if(buttonMenu.id == 3) {
            FileUtil.showURL(Slack.getInstance().DiscordServer);
        }

        if(buttonMenu.id == 4) {
            FileUtil.showURL(Slack.getInstance().Website);
        }
    }

}
