package com.client.glowclient.modules.combat;

import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;
import net.minecraft.item.*;

public class KickBow extends ModuleContainer
{
    private boolean B;
    public static NumberValue shulkerSlot;
    
    @Override
    public void E() {
        this.B = false;
    }
    
    static {
        final String s = "KickBow";
        final String s2 = "ShulkerSlot";
        final String s3 = "Slot of the ban book shulker box";
        final double n = 9.0;
        final double n2 = 1.0;
        KickBow.shulkerSlot = ValueFactory.M(s, s2, s3, n, n2, n2, 9.0);
    }
    
    public KickBow() {
        final boolean b = false;
        super(Category.COMBAT, "KickBow", false, -1, "Kicks people when killed with a bow");
        this.B = b;
    }
    
    @Override
    public void M() {
        if (Item.getIdFromItem(KickBow.B.player.getHeldItemMainhand().getItem()) == 261 && KickBow.B.gameSettings.keyBindUseItem.isKeyDown()) {
            this.B = true;
            return;
        }
        if (this.B) {
            KickBow.B.player.inventory.currentItem = KickBow.shulkerSlot.M() - 1;
            this.B = false;
        }
    }
}
