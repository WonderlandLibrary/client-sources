package net.futureclient.client.modules.render.search;

import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import net.minecraft.util.math.Vec3d;
import net.futureclient.loader.mixin.common.render.entity.wrapper.IEntityRenderer;
import org.lwjgl.opengl.GL11;
import net.futureclient.client.WG;
import net.minecraft.util.math.AxisAlignedBB;
import net.futureclient.client.xG;
import java.awt.Color;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.futureclient.loader.mixin.common.render.wrapper.IRenderManager;
import net.futureclient.client.sB;
import net.futureclient.client.events.EventType;
import net.futureclient.client.events.EventRender;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Search;
import net.futureclient.client.KD;
import net.futureclient.client.n;

public class Listener2 extends n<KD>
{
    public final Search k;
    
    public Listener2(final Search k) {
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
        final Iterator<sB.cC> iterator = this.k.M.iterator();
        while (iterator.hasNext()) {
            final sB.cC cc;
            final double n = (cc = iterator.next()).b() - ((IRenderManager)Search.getMinecraft5().getRenderManager()).getRenderPosX();
            final double n2 = cc.M() - ((IRenderManager)Search.getMinecraft3().getRenderManager()).getRenderPosY();
            final double n3 = cc.e() - ((IRenderManager)Search.getMinecraft7().getRenderManager()).getRenderPosZ();
            final BlockPos blockPos = new BlockPos(cc.b(), cc.M(), cc.e());
            final IBlockState blockState;
            final AxisAlignedBB boundingBox = (blockState = Search.getMinecraft4().world.getBlockState(blockPos)).getBoundingBox((IBlockAccess)Search.getMinecraft10().world, blockPos);
            if (Search.getMinecraft11().player.getDistance(cc.b(), cc.M(), cc.e()) <= 0.0 && this.k.D.contains(blockState.getBlock())) {
                final int m;
                final Color color = new Color(((m = Search.M(this.k, cc.b(), cc.M(), cc.e())) >> 24 & 0xFF) / 255.0f, (m >> 16 & 0xFF) / 255.0f, (m >> 8 & 0xFF) / 255.0f, (m & 0xFF) / 255.0f);
                if (Search.b(this.k).M()) {
                    xG.B();
                    WG.M(new AxisAlignedBB(n + boundingBox.minX, n2 + boundingBox.minY, n3 + boundingBox.minZ, n + boundingBox.maxX, n2 + boundingBox.maxY, n3 + boundingBox.maxZ), 1.5f, color);
                    xG.C();
                }
                if (Search.e(this.k).M()) {
                    xG.B();
                    WG.M(new AxisAlignedBB(n + boundingBox.minX, n2 + boundingBox.minY, n3 + boundingBox.minZ, n + boundingBox.maxX, n2 + boundingBox.maxY, n3 + boundingBox.maxZ), color);
                    xG.C();
                }
                if (!Search.M(this.k).M()) {
                    continue;
                }
                final float n4 = 1.0f;
                final int n5 = 1;
                GL11.glColor4f((float)n5, (float)n5, n4, (float)n5);
                xG.B();
                GL11.glLoadIdentity();
                GL11.glLineWidth(Search.M(this.k).B().floatValue());
                ((IEntityRenderer)Search.getMinecraft2().entityRenderer).orientCameraWrapper(eventRender.M());
                final double n6 = 0.0;
                final Vec3d rotateYaw = new Vec3d(n6, n6, 1.0).rotatePitch(-(float)Math.toRadians(Search.getMinecraft8().player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Search.getMinecraft1().player.rotationYaw));
                GL11.glBegin(1);
                final double n7 = n;
                GL11.glVertex3d(rotateYaw.x, Search.getMinecraft().player.getEyeHeight() + rotateYaw.y, rotateYaw.z);
                GL11.glVertex3d(n7 + 0.0, n2 + 0.0, n3 + 0.0);
                final float n8 = 1.0f;
                final int n9 = 1;
                GL11.glColor4f((float)n9, (float)n9, n8, (float)n9);
                GL11.glEnd();
                xG.C();
            }
        }
        final float n10 = 1.0f;
        final int n11 = 1;
        final int n12 = 1;
        GL11.glColor4f(n10, (float)n12, (float)n11, (float)n12);
    }
}
