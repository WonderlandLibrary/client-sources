package us.dev.direkt.module.internal.core.ui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.gui.screen.EventDisplayGui;
import us.dev.direkt.event.internal.filters.GuiScreenFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.annotations.ModData;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.io.IOException;

@ModData(label = "ReconnectorUI", category = ModCategory.CORE)
public class ReconnectorUI extends Module {

    @Listener
    protected Link<EventDisplayGui> onGuiDisplay = new Link<>(event -> {
        event.setGuiScreen(new GuiIngameMenu() {
            public void initGui() {
                this.buttonList.clear();
                int i = -16;
                int j = 98;
                this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 - 16, 200, 20, I18n.format("menu.returnToMenu")));

                if (!this.mc.isIntegratedServerRunning()) {
                    this.buttonList.set(0, new GuiButton(1, this.width / 2 + 2, this.height / 4 + 120 - 16, 98, 20, I18n.format("menu.disconnect")));
                    this.buttonList.add(new GuiButton(1337, this.width / 2 - 100, this.height / 4 + 120 - 16, 98, 20, "Reconnect"));
                }

                this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, 200, 20, I18n.format("menu.returnToGame")));
                this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options")));
                GuiButton guibutton;
                this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan")));
                this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements")));
                this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats")));
                guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
            }

            protected void actionPerformed(GuiButton button) throws IOException {
                super.actionPerformed(button);
                if (button.id == 1337) {
                    Wrapper.getWorld().sendQuittingDisconnectingPacket();
                    Wrapper.getMinecraft().displayGuiScreen(new GuiConnecting(null, Wrapper.getMinecraft(), Wrapper.getMinecraft().getCurrentServerData()));
                }
            }
        });
    }, new GuiScreenFilter(GuiIngameMenu.class));

}
