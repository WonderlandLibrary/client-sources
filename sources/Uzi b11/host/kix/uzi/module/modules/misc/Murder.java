package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;

/**
 * Created by myche on 4/13/2017.
 */
public class Murder extends Module {

    public Murder() {
        super("Murder", 0, Category.MISC);
    }

    @SubscribeEvent
    public void update(UpdateEvent e){
        mc.theWorld.loadedEntityList.forEach(player ->{
            if(player instanceof EntityPlayer){
                if(((EntityPlayer) player).getHeldItem().getItem() instanceof ItemSword && player != mc.thePlayer){
                    Logger.logToChat(((EntityPlayer) player).getName() + " is the murderer!");
                }
            }
        });
    }

}
