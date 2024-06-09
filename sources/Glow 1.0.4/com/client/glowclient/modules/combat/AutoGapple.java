package com.client.glowclient.modules.combat;

import com.client.glowclient.events.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class AutoGapple extends ModuleContainer
{
    private boolean L;
    public static final BooleanValue health;
    public static final NumberValue healthAmount;
    public static final BooleanValue regen;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            int n = -1;
            ItemStack itemStack = null;
            final ItemStack heldItemMainhand = Wrapper.mc.player.getHeldItemMainhand();
            int n2;
            int i = n2 = 0;
            while (true) {
                while (i < 9) {
                    final ItemStack stackInSlot;
                    if ((stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(n2)).getItem() instanceof ItemAppleGold) {
                        n = n2;
                        final ItemStack itemStack2 = itemStack = stackInSlot;
                        if (itemStack2 != null && itemStack.getItem() instanceof ItemAppleGold) {
                            final int n3 = (int)(Wrapper.mc.player.getHealth() + Wrapper.mc.player.getAbsorptionAmount());
                            if (!Wrapper.mc.player.isHandActive() && Wrapper.mc.rightClickDelayTimer == 0 && AutoGapple.regen.M() && !Wrapper.mc.player.isPotionActive(MobEffects.REGENERATION)) {
                                this.L = true;
                                Wrapper.mc.player.inventory.currentItem = n;
                                KeybindHelper.keyUseItem.M(true);
                                Wrapper.mc.rightClickMouse();
                                return;
                            }
                            if (AutoGapple.health.M() && n3 < AutoGapple.healthAmount.M()) {
                                this.L = true;
                                Wrapper.mc.player.inventory.currentItem = n;
                                KeybindHelper.keyUseItem.M(true);
                                Wrapper.mc.rightClickMouse();
                                return;
                            }
                        }
                        if (Wrapper.mc.player.isPotionActive(MobEffects.REGENERATION) && heldItemMainhand.getItem() instanceof ItemAppleGold) {
                            KeybindHelper.keyUseItem.M(false);
                        }
                        return;
                    }
                    else {
                        i = ++n2;
                    }
                }
                final ItemStack itemStack2 = itemStack;
                continue;
            }
        }
        catch (Exception ex) {}
    }
    
    public AutoGapple() {
        final boolean l = false;
        super(Category.COMBAT, "AutoGapple", false, -1, "Gapples when you run out of regen");
        this.L = l;
    }
    
    static {
        health = ValueFactory.M("AutoGapple", "Health", "Eat a gapple under specific health", true);
        regen = ValueFactory.M("AutoGapple", "Regen", "Eat a gapple once you run out of regen", true);
        final String s = "AutoGapple";
        final String s2 = "HealthAmount";
        final String s3 = "Amount of health before you eat a gapple";
        final double n = 10.0;
        final double n2 = 1.0;
        healthAmount = ValueFactory.M(s, s2, s3, n, n2, n2, 20.0);
    }
}
