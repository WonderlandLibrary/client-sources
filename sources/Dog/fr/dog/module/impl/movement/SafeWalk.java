package fr.dog.module.impl.movement;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.move.MovementEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.util.player.MoveUtil;
import net.minecraft.item.ItemBlock;

public class SafeWalk extends Module {
    public SafeWalk() {
        super("SafeWalk", ModuleCategory.MOVEMENT);
    }

    public boolean doSafeWalk(){
        if(!this.isEnabled()){
            return false;
        }
        if (mc.thePlayer.onGround && mc.thePlayer.rotationPitch > 70 && mc.thePlayer.movementInput.moveForward < 0) {
            return mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock;
        }
        return false;
    }

    @SubscribeEvent
    private void onEventMovement(MovementEvent event){
        if(doSafeWalk()){
            MoveUtil.strafeWithEvent(event, 0.21f);
        }
    }
}
