package studio.dreamys.mixin.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.GuiModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import studio.dreamys.font.Fonts;
import studio.dreamys.util.RenderUtils;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawClientBackground(width, height);

        Fonts.font35RobotoMedium.drawString("near 1.8.9", 2, height - 10, -1);
        Fonts.font35RobotoMedium.drawString( "dxxxxy#0776", width - Fonts.font35RobotoMedium.getStringWidth("dxxxxy#0776") - 2, height - 10, -1);

        Gui.drawRect(width / 2 - 115, height / 4 + 35, width / 2 + 115, height / 4 + 175, Integer.MIN_VALUE);

        //draw buttons
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Overwrite
    public void initGui() {
        int defaultHeight = height / 4 + 48;
        buttonList.add(new GuiButton(0, width / 2 - 100, defaultHeight + 24, 98, 20, "Credits"));
        buttonList.add(new GuiButton(1, width / 2 + 2, defaultHeight + 24, 98, 20, "Mods"));
        buttonList.add(new GuiButton(2, width / 2 - 100, defaultHeight + 24 * 2, 98, 20, "Server Status"));
        buttonList.add(new GuiButton(3, width / 2 + 2, defaultHeight + 24 * 2, 98, 20, "Background"));

        buttonList.add(new GuiButton(4, width / 2 - 100, defaultHeight, 98, 20, I18n.format("menu.singleplayer")));
        buttonList.add(new GuiButton(5, width / 2 + 2, defaultHeight, 98, 20, I18n.format("menu.multiplayer")));

        buttonList.add(new GuiButton(6, width / 2 - 100, defaultHeight + 24 * 3, "Connect to Hypixel"));
        buttonList.add(new GuiButton(7, width / 2 - 100, defaultHeight + 24 * 4, 98, 20, I18n.format("Options")));
        buttonList.add(new GuiButton(8, width / 2 + 2, defaultHeight + 24 * 4, 98, 20, I18n.format("menu.quit")));
    }

    @Overwrite
    protected void actionPerformed(GuiButton button) {
//        if (button.id == 0) {
//            this.mc.displayGuiScreen(new GuiCredits(this));
//        }
//
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiModList(this));
        }
//
//        if (button.id == 2) {
//            this.mc.displayGuiScreen(new GuiServerStatus(this));
//        }
//
//        if (button.id == 3) {
//            this.mc.displayGuiScreen(new GuiBackground(this));
//        }

        if (button.id == 4) {
            mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 5) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 6) {
            mc.displayGuiScreen(new GuiConnecting(this, mc, new ServerData("Hypixel", "mc.hypixel.net", false)));
        }

        if (button.id == 7) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }

        if (button.id == 8) {
            mc.shutdown();
        }
    }
}
