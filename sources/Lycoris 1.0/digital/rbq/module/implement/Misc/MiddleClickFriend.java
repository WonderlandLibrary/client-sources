package digital.rbq.module.implement.Misc;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import digital.rbq.Lycoris;
import digital.rbq.event.MiddleClickEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.utility.ChatUtils;

/**
 * Created by John on 2016/11/25.
 */
public class MiddleClickFriend extends Module {
    public MiddleClickFriend(){
        super("MClickFriend", Category.Misc, false);
    }

    @EventTarget
    public void onMiddleClick(MiddleClickEvent event){
        if(this.mc.objectMouseOver.entityHit instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) this.mc.objectMouseOver.entityHit;

            if(Lycoris.INSTANCE.getFriendManager().isFriend(player.getName())){
                Lycoris.INSTANCE.getFriendManager().delFriend(player.getName());

                ChatUtils.sendMessageToPlayer("Removed " + EnumChatFormatting.GOLD + player.getName() + EnumChatFormatting.RESET +  " from friends");
            }
            else{
                Lycoris.INSTANCE.getFriendManager().addFriend(player.getName());
                ChatUtils.sendMessageToPlayer("Added " + EnumChatFormatting.GOLD + player.getName() + EnumChatFormatting.RESET + " to friends");
            }

        }
    }
}
