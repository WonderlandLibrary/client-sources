package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.*;
import java.util.*;

public final class Trajectories extends ModBase
{
    private boolean release;
    public int Tempo;
    
    public Trajectories() {
        super("Trajectories", "V", true, ".t trajectories");
        this.Tempo = 0;
        this.setDescription("Mostra dove punta l'arco / palla di neve / Ender Pearl / Uovo.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            try {
                boolean bow = false;
                final sq player = MorbidWrapper.mcObj().g;
                if (player.cd() == null) {
                    return;
                }
                final wk item = player.cd().b();
                if (!(item instanceof uw) && !(item instanceof xk) && !(item instanceof vy) && !(item instanceof vu)) {
                    return;
                }
                bow = (item instanceof uw);
                double posX = bgy.b - kx.b(player.A / 180.0f * 3.1415927f) * 0.16f;
                double posY = bgy.c + player.e() - 0.10000000149011612;
                double posZ = bgy.d - kx.a(player.A / 180.0f * 3.1415927f) * 0.16f;
                double motionX = -kx.a(player.A / 180.0f * 3.1415927f) * kx.b(player.B / 180.0f * 3.1415927f) * (bow ? 1.0 : 0.4);
                double motionY = -kx.a(player.B / 180.0f * 3.1415927f) * (bow ? 1.0 : 0.4);
                double motionZ = kx.b(player.A / 180.0f * 3.1415927f) * kx.b(player.B / 180.0f * 3.1415927f) * (bow ? 1.0 : 0.4);
                final boolean stop = MorbidWrapper.mcObj().e.S() || !MorbidWrapper.mcObj().e.f((int)posX, (int)posY, (int)posZ) || MorbidWrapper.mcObj().g.f(posX, posY, posZ) > 255.0;
                if (stop) {
                    return;
                }
                final float d = kx.a(motionX * motionX + motionY * motionY + motionZ * motionZ);
                if (player.bW() <= 0 && bow) {
                    return;
                }
                final int i = 72000 - player.bW();
                float power = i / 20.0f;
                power = (power * power + power * 2.0f) / 3.0f;
                if (power < 0.1) {
                    return;
                }
                if (power > 1.0f) {
                    power = 1.0f;
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                GL11.glColor3f(1.0f - power, power, 0.0f);
                motionX /= d;
                motionY /= d;
                motionZ /= d;
                motionX *= (bow ? (power * 2.0f) : 1.0f) * 1.5;
                motionY *= (bow ? (power * 2.0f) : 1.0f) * 1.5;
                motionZ *= (bow ? (power * 2.0f) : 1.0f) * 1.5;
                GL11.glLineWidth(1.0f);
                GL11.glBegin(3);
                boolean hasLanded = false;
                boolean isEntity = false;
                ara landingPosition = null;
                final float size = bow ? 0.3f : 0.25f;
                while (!hasLanded && !stop) {
                    arc present = MorbidWrapper.mcObj().e.U().a(posX, posY, posZ);
                    arc future = MorbidWrapper.mcObj().e.U().a(posX + motionX, posY + motionY, posZ + motionZ);
                    final ara possibleLandingStrip = MorbidWrapper.mcObj().e.a(present, future, false, true);
                    present = MorbidWrapper.mcObj().e.U().a(posX, posY, posZ);
                    future = MorbidWrapper.mcObj().e.U().a(posX + motionX, posY + motionY, posZ + motionZ);
                    if (possibleLandingStrip != null) {
                        hasLanded = true;
                        landingPosition = possibleLandingStrip;
                    }
                    final aqx arrowBox = new aqx(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
                    final List entities = getEntitiesWithinAABB(arrowBox.a(motionX, motionY, motionZ).b(1.0, 1.0, 1.0));
                    for (int index = 0; index < entities.size(); ++index) {
                        final mp e = entities.get(index);
                        if (e.K() && e != player) {
                            final aqx var12 = e.E.b(0.3, 0.3, 0.3);
                            final ara possibleEntityLanding = var12.a(present, future);
                            if (possibleEntityLanding != null) {
                                hasLanded = true;
                                isEntity = true;
                                landingPosition = possibleEntityLanding;
                            }
                        }
                    }
                    posX += motionX;
                    posY += motionY;
                    posZ += motionZ;
                    float motionAdjustment = 0.99f;
                    final aqx boundingBox = new aqx(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
                    if (isInMaterial(boundingBox.b(0.0, -0.4000000059604645, 0.0).e(0.001, 0.001, 0.001), aif.h)) {
                        motionAdjustment = 0.8f;
                    }
                    motionX *= motionAdjustment;
                    motionY *= motionAdjustment;
                    motionZ *= motionAdjustment;
                    motionY -= (bow ? 0.05 : 0.03);
                    GL11.glVertex3d(posX - bgy.b, posY - bgy.c, posZ - bgy.d);
                }
                GL11.glEnd();
                GL11.glPushMatrix();
                GL11.glTranslated(posX - bgy.b, posY - bgy.c, posZ - bgy.d);
                if (landingPosition != null) {
                    switch (landingPosition.e) {
                        case 2: {
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                            break;
                        }
                        case 3: {
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                            break;
                        }
                        case 4: {
                            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                            break;
                        }
                        case 5: {
                            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                            break;
                        }
                    }
                    if (isEntity) {
                        GL11.glColor3f(1.0f, 0.0f, 0.0f);
                        ++this.Tempo;
                        if (this.release) {
                            this.spara();
                        }
                    }
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                this.renderPoint();
                GL11.glEnable(2929);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
                GL11.glDepthMask(true);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
            catch (Exception e2) {}
        }
    }
    
    private void renderPoint() {
        GL11.glBegin(1);
        GL11.glVertex3d(-0.4, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, -0.4);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.4, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.4);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glEnd();
        final Cylinder c = new Cylinder();
        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.4f, 0.4f, 0.1f, 24, 1);
    }
    
    public void spara() {
        if (this.Tempo == 4) {
            Minecraft.x().z.S.e = false;
            this.Tempo = 0;
        }
    }
    
    public static boolean isInMaterial(final aqx par1AxisAlignedBB, final aif par2Material) {
        boolean flag = false;
        final int a1 = kx.c(par1AxisAlignedBB.a);
        final int a2 = kx.c(par1AxisAlignedBB.d + 1.0);
        final int a3 = kx.c(par1AxisAlignedBB.b);
        final int a4 = kx.c(par1AxisAlignedBB.e + 1.0);
        final int a5 = kx.c(par1AxisAlignedBB.c);
        final int a6 = kx.c(par1AxisAlignedBB.f + 1.0);
        if (!MorbidWrapper.mcObj().e.e(a1, a3, a5, a2, a4, a6)) {
            return false;
        }
        for (int i1 = a1; i1 < a2; ++i1) {
            for (int i2 = a3; i2 < a4; ++i2) {
                for (int i3 = a5; i3 < a6; ++i3) {
                    final apa b1 = apa.r[MorbidWrapper.mcObj().e.a(i1, i2, i3)];
                    if (b1 != null && b1.cO == par2Material) {
                        final double b2 = i2 + 1 - ane.d(MorbidWrapper.mcObj().e.h(i1, i2, i3));
                        if (a4 >= b2) {
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }
    
    public static List getEntitiesWithinAABB(final aqx par2AxisAlignedBB) {
        final ArrayList a = new ArrayList();
        final int i1 = kx.c((par2AxisAlignedBB.a - 2.0) / 16.0);
        final int i2 = kx.c((par2AxisAlignedBB.d + 2.0) / 16.0);
        final int i3 = kx.c((par2AxisAlignedBB.c - 2.0) / 16.0);
        final int i4 = kx.c((par2AxisAlignedBB.f + 2.0) / 16.0);
        for (int a2 = i1; a2 <= i2; ++a2) {
            for (int a3 = i3; a3 <= i4; ++a3) {
                if (MorbidWrapper.mcObj().e.c(a2, a3)) {
                    MorbidWrapper.mcObj().e.e(a2, a3).a(MorbidWrapper.mcObj().g, par2AxisAlignedBB, a, null);
                }
            }
        }
        return a;
    }
    
    @Override
    public void onCommand(final String paramString) {
        final String[] arrayOfString = paramString.split(" ");
        if (paramString.toLowerCase().startsWith(".t release")) {
            this.release = !this.release;
            ModBase.setCommandExists(true);
            this.getWrapper();
            MorbidWrapper.addChat("AutoRelease toggled " + (this.release ? "on" : "off"));
        }
    }
}
