package com.client.glowclient.modules.combat;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.server.*;
import com.client.glowclient.*;
import net.minecraft.util.text.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoLog extends ModuleContainer
{
    public static BooleanValue totemCount;
    public static final NumberValue health;
    public static final NumberValue totemCount;
    
    static {
        final String s = "AutoLog";
        final String s2 = "Health";
        final String s3 = "Amount before log";
        final double n = 5.0;
        final double n2 = 1.0;
        health = ValueFactory.M(s, s2, s3, n, n2, n2, 20.0);
        AutoLog.totemCount = ValueFactory.M("AutoLog", "TotemCount", "Logs out when specified totem count is reached or below", false);
        final String s4 = "AutoLog";
        final String s5 = "TotemCount";
        final String s6 = "Amount of totems to log off at";
        final double n3 = 1.0;
        totemCount = ValueFactory.M(s4, s5, s6, n3, n3, 0.0, 10.0);
    }
    
    public AutoLog() {
        super(Category.COMBAT, "AutoLog", false, -1, "Disconnect on events");
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Wrapper.mc.player != null && !Wrapper.mc.player.isCreative()) {
            final int n;
            if ((n = (int)(Wrapper.mc.player.getHealth() + Wrapper.mc.player.getAbsorptionAmount())) <= AutoLog.health.M()) {
                AutoReconnect.b = true;
                Ob.M().closeChannel((ITextComponent)new TextComponentString(new StringBuilder().insert(0, "You were logged out because your health was lower than the specified health [").append(n).append("]").toString()));
                this.k();
            }
            int n2 = 0;
            int n3 = 0;
            int n4;
            int i = n4 = 0;
            while (i < Wrapper.mc.player.inventory.getSizeInventory()) {
                final ItemStack stackInSlot;
                if ((stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(n4)) != null && stackInSlot.getItem().equals(Items.TOTEM_OF_UNDYING)) {
                    n2 = (n3 = n2 + stackInSlot.getCount());
                }
                i = ++n4;
            }
            if (n3 <= AutoLog.totemCount.M() && AutoLog.totemCount.M()) {
                AutoReconnect.b = true;
                Ob.M().closeChannel((ITextComponent)new TextComponentString("You were logged out due to you having less than the specified amount of totems"));
                this.k();
            }
        }
    }
}
