package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.potion.Potion;

/**
 * Created by Kix on 6/3/2017.
 * Made for the eclipse project.
 */
public class VampireZ extends Module {

    public VampireZ() {
        super("VampireZ", 0, Category.MISC);
    }

    @SubscribeEvent
    public void update(UpdateEvent e){
        if(mc.thePlayer.isPotionActive(Potion.blindness)){
            mc.thePlayer.removePotionEffect(Potion.blindness.getId());
        }
        if(mc.thePlayer.isPotionActive(Potion.confusion)){
            mc.thePlayer.removePotionEffect(Potion.confusion.getId());
        }
    }

}
