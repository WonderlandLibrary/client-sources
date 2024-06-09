package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraRPC;
import me.finz0.osiris.command.Command;

public class RpcModule extends Module {
    public RpcModule() {
        super("DiscordRPC", Category.MISC, "Discord Rich Presence");
        setDrawn(false);
    }

    public void onEnable(){
        AuroraRPC.init();
        if(mc.player != null)
            Command.sendClientMessage("discord rpc started");
    }

    public void onDisable(){
        Command.sendClientMessage("you need to restart your game disable rpc");
    }
}
