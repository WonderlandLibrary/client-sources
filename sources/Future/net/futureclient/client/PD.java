package net.futureclient.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.util.math.AxisAlignedBB;
import java.awt.Color;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.futureclient.loader.mixin.common.render.wrapper.IRenderManager;
import net.futureclient.client.events.EventType;
import net.futureclient.client.events.EventRender;
import net.futureclient.client.events.Event;

public class PD extends n<KD>
{
    public final bf k;
    
    public PD(final bf k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventRender)event);
    }
    
    public void M(final EventRender eventRender) {
        if (!eventRender.getType().equals(EventType.PRE)) {
            return;
        }
        if (bf.M(this.k) != null) {
            final double n = bf.M(this.k).getX() - ((IRenderManager)bf.l().getRenderManager()).getRenderPosX();
            final double n2 = bf.M(this.k).getY() - ((IRenderManager)bf.M().getRenderManager()).getRenderPosY();
            final double n3 = bf.M(this.k).getZ() - ((IRenderManager)bf.i().getRenderManager()).getRenderPosZ();
            final BlockPos blockPos = new BlockPos(bf.M(this.k).getX(), bf.M(this.k).getY(), bf.M(this.k).getZ());
            final AxisAlignedBB boundingBox = bf.K().world.getBlockState(blockPos).getBoundingBox((IBlockAccess)bf.H().world, blockPos);
            final ye ye = (ye)pg.M().M().M((Class)ye.class);
            final Color color = new Color(ye.L.getRed() / 255.0f, ye.L.getGreen() / 255.0f, ye.L.getBlue() / 255.0f, 0.9f);
            xG.B();
            WG.M(new AxisAlignedBB(n + boundingBox.minX, n2 + boundingBox.minY, n3 + boundingBox.minZ, n + boundingBox.maxX, n2 + boundingBox.maxY, n3 + boundingBox.maxZ), 1.5f, color);
            xG.C();
            final Color color2 = new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.3f);
            xG.B();
            WG.M(new AxisAlignedBB(n + boundingBox.minX, n2 + boundingBox.minY, n3 + boundingBox.minZ, n + boundingBox.maxX, n2 + boundingBox.maxY, n3 + boundingBox.maxZ), color2);
            xG.C();
        }
        final float n4 = 1.0f;
        final int n5 = 1;
        final int n6 = 1;
        GL11.glColor4f(n4, (float)n6, (float)n5, (float)n6);
    }
}
