package dev.vertic.ui.mainmenu;

import dev.vertic.Client;
import dev.vertic.Utils;
import dev.vertic.ui.MenuBackground;
import dev.vertic.ui.altmanager.AltManager;
import dev.vertic.util.render.BlurUtil;
import dev.vertic.util.render.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;

public class MainMenu extends GuiScreen implements Utils {

    private final Minecraft mc = Minecraft.getMinecraft();
    private String[] buttons = {"SinglePlayer", "MultiPlayer", "AltManager"};
    private double yPos = 100;

    public MainMenu() {

    }

    @Override
    public void initGui() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Utils.mc);
        double sW = sr.getScaledWidth_double();
        double sH = sr.getScaledHeight_double();
        yPos = (sH / 2) - ((25 * buttons.length) - (15 * buttons.length)) / 2;
        MenuBackground.run(partialTicks, null);
        font16.drawStringWithShadow(Utils.mc.getDebugFPS() + "", 5, 5, -1);
        font32.drawCenteredStringWithShadow(Client.name, sW / 2, yPos - 30 - font32.getHeight(), -1);


        BlurUtil.doBlur(
                25,
                () -> {
                    for (int i = 0; i < 3; i++) {
                        DrawUtil.centeredRoundedRect(sW / 2, yPos, 150, 25, 10, new Color(200, 200, 200, 255));
                        yPos += 40;
                    }
                }
        );

        yPos = (sH / 2) - ((25 * buttons.length) - (15 * buttons.length)) / 2;

        for (int i = 0; i < 3; i++) {
            DrawUtil.centeredRoundedOutline(sW / 2, yPos, 150, 25, 10, 1, new Color(200, 200, 200, 255));
            font24.drawCenteredStringWithShadow(buttons[i], sW / 2, yPos - (font24.getHeight() / 2), -1);
            yPos += 40;
        }


    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(Utils.mc);
        double sW = sr.getScaledWidth_double();
        double sH = sr.getScaledHeight_double();
        yPos = (sH / 2) - ((25 * buttons.length) - (15 * buttons.length)) / 2;
        double btnWidth = 150;
        double btnHeight = 25;

        for (int i = 0; i < 3; i++) {
            if (mouseX > (sW / 2) - (btnWidth/2) && mouseX < (sW / 2) + (btnWidth / 2) && mouseY > yPos - (btnHeight / 2) && mouseY < yPos + (btnHeight / 2)) {
                switch(i) {
                    case 0:
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case 1:
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case 2:
                        mc.displayGuiScreen(new AltManager());
                        break;
                    default:
                        break;
                }
            }
            yPos += 40;
        }
    }
}
