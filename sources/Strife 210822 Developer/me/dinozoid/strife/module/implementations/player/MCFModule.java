package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.system.MouseEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.target.implementations.Friend;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.entity.Entity;

@ModuleInfo(name = "MCF", renderName = "MCF", aliases = "MiddleClickFriend", description = "Middle click to friend.", category = Category.PLAYER)
public class MCFModule extends Module {

    @EventHandler
    private final Listener<MouseEvent> mouseListener = new Listener<>(event -> {
        if(event.mouseButton() == 1) {
            if(mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
                Entity entity = mc.objectMouseOver.entityHit;
                if(StrifeClient.INSTANCE.targetRepository().targetBy(entity.getName()) == null) {
                    StrifeClient.INSTANCE.targetRepository().add(new Friend(entity.getName()));
                    PlayerUtil.sendMessage("&c[MCF]&f Added friend.");
                } else {
                    StrifeClient.INSTANCE.targetRepository().remove(StrifeClient.INSTANCE.targetRepository().targetBy(entity.getName()));
                    PlayerUtil.sendMessage("&c[MCF]&f Removed friend.");
                }
            }
        }
    });

}
