package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.module.Module;
/**
 *Made by Viktisen
 */

@Module.Info(name = "fakeddos", category = Module.Category.CHAT)
public class fakeddos extends Module {

    public void onEnable() {
        mc.player.sendChatMessage("lag");
        mc.player.world.sendQuittingDisconnectingPacket();
        disable();
    }

}