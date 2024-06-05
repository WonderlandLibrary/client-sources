package white.floor.features.impl.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventMouseKey;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.friend.Friend;
import white.floor.helpers.friend.FriendManager;

public class MCF extends Feature {

    EntityLivingBase friend;

    public MCF() {
        super("MCF", "add entity to friend.", 0, Category.MISC);
    }

    @EventTarget
    public void mcf(EventMouseKey eventMouseKey) {

        friend = (EntityLivingBase) mc.objectMouseOver.entityHit;

        if(eventMouseKey.key == 2 && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            if (FriendManager.isFriend(friend.getName())) {
                FriendManager.removeFriend(friend.getName());
                Main.msg(ChatFormatting.RED + friend.getName() + ChatFormatting.WHITE + " was removed from ur friends list.", true);
            } else {
                FriendManager.getFriends().addFriend(friend.getName(), "MiddleClickFriend");
                Main.msg(ChatFormatting.GREEN + friend.getName() + ChatFormatting.WHITE + " added to ur friend list.", true);
            }
        }
    }
}
