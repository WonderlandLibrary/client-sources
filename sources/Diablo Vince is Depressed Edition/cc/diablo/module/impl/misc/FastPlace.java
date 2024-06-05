/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.EventType;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;

public class FastPlace
extends Module {
    public FastPlace() {
        super("Fast Place", "Speeds up block placement", 0, Category.Misc);
    }

    private static boolean isSplashPot(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion)stack.getItem();
            return ItemPotion.isSplash(stack.getItemDamage());
        }
        return false;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (e.getType() == EventType.Pre && !(FastPlace.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) && !(FastPlace.mc.thePlayer.getItemInUse().getItem() instanceof ItemSword) && (FastPlace.mc.thePlayer.getItemInUse().getItem() instanceof ItemFood || FastPlace.mc.thePlayer.getItemInUse().getItem() instanceof ItemBucketMilk || FastPlace.mc.thePlayer.getItemInUse().getItem() instanceof ItemPotion && !FastPlace.isSplashPot(FastPlace.mc.thePlayer.getItemInUse())) && !FastPlace.mc.thePlayer.isBlocking() && FastPlace.mc.thePlayer.getItemInUseDuration() == 16) {
            for (int i = 0; i < 17; ++i) {
                FastPlace.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                Timer.timerSpeed = 1.1f;
            }
            FastPlace.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            Timer.timerSpeed = 1.0f;
        }
    }

    @Override
    public void onDisable() {
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}

