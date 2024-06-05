package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Mouse;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class AutoPotion extends Mod {

    public AutoPotion() {
        super("Auto Potion", 0, true, ModCategory.PLAYER);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState()) return;

        if (event.getType() == Type.PRE) {
            Block block = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            if (block != null && block != Blocks.air)
                return;

            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.getStackInSlot(i) != null) {
                    if (isThrowable(i)) {
                        if (mc.gameSettings.keyBindUseItem.pressed)
                            event.setPitch(80);
                    }
                }
            }

        }

        if (event.getType() == Type.POST) {
            Block block = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            if (block != null && block != Blocks.air)
                return;
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.getStackInSlot(i) != null) {
                    if (isThrowable(i)) {
                        if (mc.gameSettings.keyBindUseItem.pressed) {
                            mc.gameSettings.keyBindUseItem.pressed = false;
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                        }
                    }
                }
            }
        }
    }


    public boolean isThrowable(int i) {
        ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
        if (ItemPotion.isSplash(itemStack.getItemDamage())) {
            ItemPotion itemPotion = (ItemPotion) Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem();

            for (PotionEffect potionEffect : itemPotion.getEffects(itemStack)) {
                if (potionEffect.getPotionID() == Potion.heal.id)
                    return true;
                if (potionEffect.getPotionID() == Potion.moveSpeed.id)
                    return true;
                if (potionEffect.getPotionID() == Potion.jump.id)
                    return true;
                if (potionEffect.getPotionID() == Potion.damageBoost.id)
                    return true;
                if (potionEffect.getPotionID() == Potion.resistance.id)
                    return true;
                if (potionEffect.getPotionID() == Potion.regeneration.id)
                    return true;
                if (potionEffect.getPotionID() == Potion.fireResistance.id)
                    return true;
                if (potionEffect.getPotionID() == Potion.absorption.id)
                    return true;
            }
        }

        return false;
    }


    public boolean blackList(Block block) {
        return false;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
