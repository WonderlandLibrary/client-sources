package fr.dog.module.impl.combat;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.math.TimeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AutoGoldenHead extends Module {
    public AutoGoldenHead() {
        super("AutoHead", ModuleCategory.COMBAT);
        this.registerProperties(minHealth, cdTime);
    }

    private final TimeUtil cdAmount = new TimeUtil();
    private final NumberProperty minHealth = NumberProperty.newInstance("Min Health", 1f, 12f, 20f, 1f);
    private final NumberProperty cdTime = NumberProperty.newInstance("Cooldown", 0f, 500f, 5000f, 50f);
    private int hSlot = -1;


    @SubscribeEvent
    private void onUpdate(PlayerTickEvent event){
        if(mc.thePlayer.ticksExisted > 30 && mc.thePlayer.getHealth() <= minHealth.getValue() && cdAmount.finished(cdTime.getValue().intValue())){
            int slot = findHead();
            if(slot > -1){
                int olds = mc.thePlayer.inventory.currentItem;
                hSlot = slot;
                mc.thePlayer.inventory.currentItem = slot;
                mc.playerController.syncCurrentPlayItem();
                mc.rightClickMouse();
                mc.thePlayer.inventory.currentItem = olds;
            }else{
                if(hSlot != -1){
                    mc.playerController.syncCurrentPlayItem();
                    hSlot = -1;
                }
            }
        }else{
            if(hSlot != -1){
                mc.playerController.syncCurrentPlayItem();
                hSlot = -1;
            }
        }
    }


    private int findHead(){
        for(int i = 0; i < 9; i++){
            if(mc.thePlayer.inventory.getStackInSlot(i) != null){
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
                Item item = stack.getItem();

                if(item == Items.skull){
                    return i;
                }

            }
        }


        return -1;

    }
}
