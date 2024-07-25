package club.bluezenith.module.modules.misc;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.AttackEvent;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;


public class AutoTool extends Module {

    final BooleanValue autoSword = new BooleanValue("AutoSword", false).setIndex(1);
    public AutoTool() {
        super("AutoTool", ModuleCategory.MISC);
    }

    int lastSlot = -1;
    boolean set;


    @Listener
    public void onTick(UpdateEvent event) {
        if(mc.thePlayer == null) return;
        if(mc.gameSettings.keyBindAttack.pressed) {
            if(mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                float toolSpeed = -1;
                int toolIndex = -1;
                for(int i = 0; i < 8; i++) {
                    final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
                    if(stack == null) continue;
                    final float newSpeed = stack.getItem().getStrVsBlock(stack, mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock());
                    if(newSpeed > toolSpeed) {
                        toolIndex = i;
                        toolSpeed = newSpeed;
                    }
                }
                lastSlot = mc.thePlayer.inventory.currentItem;
                if(toolIndex != -1) {
                    mc.thePlayer.inventory.currentItem = toolIndex;
                    set = false;
                }
            } else setLastSlot();
        } else setLastSlot();
    }

    void setLastSlot() {
        if(set && lastSlot != -1 && !mc.gameSettings.keyBindAttack.pressed) {
            set = false;
            mc.thePlayer.inventory.currentItem = lastSlot;
            lastSlot = -1;
        }
    }

    @Listener
    public void onPreAttack(AttackEvent event) {
        if(event.isPost() || !autoSword.get()) return;

        float swordDamage = -1;
        int swordSlot = -1;
        for(int i = 0; i < 8; i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if(stack == null) continue;
            if(stack.getItem() instanceof ItemSword) {
                float damage = ((ItemSword) stack.getItem()).getDamageVsEntity();
                if(damage > swordDamage) {
                    swordDamage = damage;
                    swordSlot = i;
                }
            }
        }
        if(swordSlot != -1) {
            mc.thePlayer.inventory.currentItem = swordSlot;
        }
    }

}
