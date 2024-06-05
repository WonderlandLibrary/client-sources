package de.tired.module.impl.list.misc;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.network.play.client.C01PacketChatMessage;

@ModuleAnnotation(name = "Crasher", category = ModuleCategory.MISC)
public class Crasher extends Module {

    public ModeSetting crasher = new ModeSetting("crasher", this, new String[]{"TreeAC"});

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (crasher.getValue().equalsIgnoreCase("crasher")) {
            if (getPlayer().ticksExisted % 12 == 0) {
                sendPacketUnlogged(new C01PacketChatMessage("/ac"));
                if (getPlayer().openContainer != null) {
                    for (int i = 0; i < 10; i++)
                        getPlayerController().windowClick(getPlayer().openContainer.windowId, i, 0, 0, getPlayer());
                }
            }
        }
    }
    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
