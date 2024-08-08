package net.futureclient.client.modules.movement;

import net.minecraft.init.Blocks;
import net.futureclient.client.modules.movement.icespeed.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class IceSpeed extends Ea
{
    public IceSpeed() {
        super("IceSpeed", new String[] { "IceSpeed", "IceSped", "OiceSped", "OiceSpeed", "Isped", "Ispeed" }, true, -14564873, Category.MOVEMENT);
        this.M(new n[] { new Listener1(this) });
    }
    
    public void b() {
        super.b();
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
}
