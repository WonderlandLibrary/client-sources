package net.futureclient.client.modules.movement.jesus;

import net.futureclient.client.events.Event;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Objects;
import net.futureclient.client.Cb;
import net.futureclient.client.IG;
import net.futureclient.client.ZG;
import net.minecraft.block.BlockLiquid;
import net.futureclient.client.modules.movement.Jesus;
import net.futureclient.client.wE;
import net.futureclient.client.n;

public class Listener2 extends n<wE>
{
    public final Jesus k;
    
    public Listener2(final Jesus k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final wE we) {
        final BlockPos m = we.M();
        if (we.M() instanceof BlockLiquid && !Jesus.getMinecraft1().player.isSneaking() && ZG.M().fallDistance < 3.0f && !IG.e() && IG.M(false) && IG.M(we.M()) && we.M() != null && ((we.M().equals((Object)Jesus.getMinecraft24().player) && !((Cb.gA)Jesus.M(this.k).M()).equals((Object)Cb.gA.D)) || (we.M().getControllingPassenger() != null && Objects.equals(we.M().getControllingPassenger(), Jesus.getMinecraft7().player)))) {
            we.M(new AxisAlignedBB((double)m.getX(), (double)m.getY(), (double)m.getZ(), (double)(m.getX() + 1), m.getY() + 1.0185579796E-314, (double)(m.getZ() + 1)));
        }
    }
    
    public void M(final Event event) {
        this.M((wE)event);
    }
}
