package com.client.glowclient.modules.movement;

import com.client.glowclient.events.*;
import net.minecraft.entity.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import net.minecraft.client.*;

public class Sprint extends ModuleContainer
{
    private boolean B;
    public final pf b;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!(Ob.M() instanceof EntityBoat) && eventUpdate.getEntityLiving().moveForward > 0.0f && !eventUpdate.getEntityLiving().collidedHorizontally && !eventUpdate.getEntityLiving().isSneaking()) {
            this.d();
        }
    }
    
    private void a() {
        if (this.B) {
            KeybindHelper.keySprint.M(false);
            KeybindHelper.keySprint.M();
            this.B = false;
        }
    }
    
    public Sprint() {
        final boolean b = false;
        super(Category.MOVEMENT, "Sprint", false, -1, "Sprints automatically");
        this.B = b;
        this.b = pf.B;
    }
    
    @Override
    public void E() {
        this.a();
    }
    
    private void d() {
        switch (aE.b[this.b.ordinal()]) {
            case 1: {
                final Minecraft mc = Wrapper.mc;
                while (false) {}
                if (!mc.player.collidedHorizontally && !Wrapper.mc.player.isSprinting()) {
                    Wrapper.mc.player.setSprinting(true);
                    return;
                }
                break;
            }
            default: {
                if (!this.B) {
                    KeybindHelper.keySprint.D();
                    this.B = true;
                }
                if (!KeybindHelper.keySprint.M().isKeyDown()) {
                    KeybindHelper.keySprint.M(true);
                    break;
                }
                break;
            }
        }
    }
}
