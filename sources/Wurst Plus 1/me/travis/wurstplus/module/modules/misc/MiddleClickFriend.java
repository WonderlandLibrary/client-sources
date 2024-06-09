package me.travis.wurstplus.module.modules.misc;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.event.events.MiddleClickEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.command.commands.FriendCommand;

@Module.Info(name = "MiddleClick Friend", category = Module.Category.RENDER)
public class MiddleClickFriend extends Module {    

    @Override
    protected void onEnable() {
        
    }

    @EventHandler
    private Listener<MiddleClickEvent> listener = new Listener<>(event -> {
        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            String name = mc.objectMouseOver.entityHit.getName();
            if (!Friends.isFriend(name)) {
                new Thread(() -> {
                    Friends.Friend f = FriendCommand.getFriendByName(name);
                    if (f == null) {
                        Command.sendChatMessage("Failed to find UUID of " + name);
                        return;
                    }
                    Friends.INSTANCE.friends.getValue().add(f);
                    Command.sendChatMessage("Added &b" + name + "&r to friend" + "s list");
                }).start();
            } else {
                if (!Friends.isFriend(name)) {
                    Command.sendChatMessage("That player isn't your friend.");
                    return;
                }

                Friends.Friend friend = Friends.INSTANCE.friends.getValue().stream().filter(friend1 -> friend1.getUsername().equalsIgnoreCase(name)).findFirst().get();
                Friends.INSTANCE.friends.getValue().remove(friend);
                Command.sendChatMessage("&b" + friend.getUsername() + "&r has been unfriended.");
                return;
            }
        }

    });

}
