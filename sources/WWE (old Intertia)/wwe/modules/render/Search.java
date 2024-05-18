package wwe.modules.render;

import javax.vecmath.*;
import net.minecraft.block.*;
import java.awt.*;
import wwe.modules.*;
import wwe.utils.*;
import java.util.*;
import wwe.*;
import org.lwjgl.opengl.*;
import me.THEREALWWEFAN231.wwefan.mixin.mixins.interfaces.*;
import net.minecraft.util.math.*;

public class Search extends qMIe
{
    public static ArrayList<Vector3f> dMba;
    public static HashMap<Block, Color> Hxmz;
    public BooleanValue tracer;
    public BooleanValue esp;
    
    public Search() {
        super("Search", "Draws either a tracer or a block at nether/end portals", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        this.tracer = new BooleanValue("Tracer", this, "Render a tracer to the block", true);
        this.esp = new BooleanValue("ESP", this, "Render a glowing outline at a block", false);
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        if (Wrapper.mc.world != null) {
            HAxG.TuzD("This may lag some computers");
        }
        Wrapper.mc.renderGlobal.loadRenderers();
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        Search.dMba.clear();
        super.qBmy();
    }
    
    @oqrr
    public void rswX(final iQuY iQuY) {
        final Vector3f vector3f = new Vector3f((float)iQuY.pBuW().getX(), (float)iQuY.pBuW().getY(), (float)iQuY.pBuW().getZ());
        if (!Search.dMba.contains(vector3f) && Search.Hxmz.containsKey(iQuY.YAVh())) {
            final int idFromBlock = Block.getIdFromBlock(Wrapper.mc.world.getBlockState(new BlockPos((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ())).getBlock());
            if (Wrapper.mc.player.getDistance((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ()) <= 128.0 && idFromBlock != 0) {
                Search.dMba.add(new Vector3f((float)iQuY.pBuW().getX(), (float)iQuY.pBuW().getY(), (float)iQuY.pBuW().getZ()));
            }
        }
    }
    
    public Color ywJw(final double n, final double n2, final double n3) {
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        for (final Map.Entry<Block, Color> entry : Search.Hxmz.entrySet()) {
            if (entry.getKey() == Wrapper.mc.world.getBlockState(blockPos).getBlock()) {
                return entry.getValue();
            }
            if (Search.\u2000\u2003\u200d\u2007\u200f\u2003\u2008\u200a) {
                throw null;
            }
        }
        return new Color(255, 255, 255);
    }
    
    @oqrr
    public void XGzV(final RTIo rtIo) {
        if (this.IuiN()) {
            for (final Vector3f vector3f : Search.dMba) {
                final double n = vector3f.getX() - yYGD.onRX();
                final double n2 = vector3f.getY() - yYGD.UuJK();
                final double n3 = vector3f.getZ() - yYGD.LkVR();
                if (Wrapper.mc.player.getDistance((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ()) <= 128.0) {
                    if (!Search.Hxmz.containsKey(Wrapper.mc.world.getBlockState(new BlockPos((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ())).getBlock())) {
                        if (Search.\u2000\u2003\u200d\u2007\u200f\u2003\u2008\u200a) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        if (this.tracer.OnGi()) {
                            this.OTTy(n, n2, n3, this.ywJw(vector3f.getX(), vector3f.getY(), vector3f.getZ()));
                        }
                        if (this.esp.OnGi()) {
                            BIIV.zIBn(new BlockPos((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ()), this.ywJw(vector3f.getX(), vector3f.getY(), vector3f.getZ()), 1.0, 1.0);
                        }
                    }
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (Search.\u2000\u2003\u200d\u2007\u200f\u2003\u2008\u200a) {
                    throw null;
                }
            }
        }
    }
    
    private void OTTy(final double n, final double n2, final double n3, final Color color) {
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ((InterfaceMixinEntityRenderer)Wrapper.mc.entityRenderer).orientCameraVoid(yYGD.XgOL().renderPartialTicks);
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glColor3f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue());
        final Vec3d rotateYaw = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.mc.player.rotationYaw));
        GL11.glBegin(2);
        GL11.glVertex3d(rotateYaw.x, Wrapper.mc.player.getEyeHeight() + rotateYaw.y, rotateYaw.z);
        GL11.glVertex3d(n, n2, n3);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    static {
        Search.dMba = new ArrayList<Vector3f>();
        Search.Hxmz = new HashMap<Block, Color>();
    }
}
