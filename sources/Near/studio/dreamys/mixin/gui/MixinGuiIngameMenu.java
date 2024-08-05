package studio.dreamys.mixin.gui;

import net.minecraft.client.gui.*;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.GuiModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import studio.dreamys.clickgui.ClickGUI;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {

    @Overwrite
    public void initGui() {
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 24, "Back to Game"));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 48, 98, 20, "near"));
        buttonList.add(new GuiButton(2, width / 2 + 2, height / 4 + 48, 98, 20, "Status"));
        buttonList.add(new GuiButton(3, width / 2 - 100, height / 4 + 72, "Server List"));
        buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 96, 98, 20, "Options"));
        buttonList.add(new GuiButton(5, width / 2 + 2, height / 4 + 96, 98, 20, "Mod Options"));
        buttonList.add(new GuiButton(6, width / 2 - 100, height / 4 + 120, "Disconnect"));
    }

    @Overwrite
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(null);
        }

        if (button.id == 1) {
            mc.displayGuiScreen(new ClickGUI());
        }

//        if (button.id == 2) {
//            mc.displayGuiScreen(new GuiStatus());
//        }

        if (button.id == 3) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 4) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }

        if (button.id == 5) {
            FMLClientHandler.instance().showGuiScreen(new GuiModList(this));
        }

        if (button.id == 6) {
            mc.theWorld.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
            if (mc.isIntegratedServerRunning()) {
                mc.displayGuiScreen(new GuiMainMenu());
            }
            else {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            }
        }
    }
}
