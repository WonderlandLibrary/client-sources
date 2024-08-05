package fr.dog.module.impl.player;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;

import java.util.Random;

public class RandomModuleToggler extends Module {
    public RandomModuleToggler() {
        super("AutoModuleToggle", ModuleCategory.PLAYER);
    }


    private Random random = new Random();
    @SubscribeEvent
    public void onUpdate(PlayerTickEvent event){
        if(mc.thePlayer.ticksExisted % 20 != 0){
            return;
        }
        for(Module m : Dog.getInstance().getModuleManager().getObjects()){
            if(m.getName() == this.getName()){
                return;
            }
            if(random.nextBoolean()){
                m.toggle();
            }
        }
    }
}
