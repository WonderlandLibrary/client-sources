package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.world.TickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import net.minecraft.item.ItemBlock;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", ModuleCategory.PLAYER);
        this.registerProperties(blocksOnly);
    }

    private final BooleanProperty blocksOnly = BooleanProperty.newInstance("Blocks Only", true);

    @SubscribeEvent
    private void onUpdateNigger(TickEvent event){
        if(mc.thePlayer != null){

            if(blocksOnly.getValue() && (mc.thePlayer.inventory.getCurrentItem() == null || !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock))){
                return;
            }

            mc.rightClickDelayTimer = 0;
        }

    }
}
