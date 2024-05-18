package wwe.modules.combat;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import wwe.*;

public class BowAimBot extends qMIe
{
    private EntityLivingBase oYbE;
    public ModeValue rotations;
    public BooleanValue players;
    public BooleanValue animals;
    public BooleanValue mobs;
    public BooleanValue invisibles;
    
    public BowAimBot() {
        super("Bow Aim Bot", "Shoots your bow at entities", 0, Category.COMBAT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Packet");
        list.add("Client");
        this.rotations = new ModeValue("Rotations", this, "test", "Client", list);
        this.players = new BooleanValue("Players", this, "Target players", true);
        this.animals = new BooleanValue("Animals", this, "Target friendly animals", false);
        this.mobs = new BooleanValue("Mobs", this, "Target hostile mobs", false);
        this.invisibles = new BooleanValue("invisibles", this, "Target invisible entities", false);
    }
    
    @oqrr
    public void oHiJ(final omqH omqH) {
        this.oYbE = null;
        if (Wrapper.mc.player.getHeldItemMainhand() != null && Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && Wrapper.mc.player.isHandActive()) {
            double n = Double.MAX_VALUE;
            for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayerSP)) {
                    final EntityLivingBase oYbE = (EntityLivingBase)entity;
                    if (FIwW.Bvtk(oYbE, this.players.OnGi(), this.animals.OnGi(), this.mobs.OnGi(), this.invisibles.OnGi(), false, Double.MAX_VALUE) && Wrapper.mc.player.canEntityBeSeen((Entity)oYbE) && Wrapper.mc.player.getDistance((Entity)oYbE) < n) {
                        this.oYbE = oYbE;
                        n = Wrapper.mc.player.getDistance((Entity)oYbE);
                    }
                }
                if (BowAimBot.\u2000\u2003\u2001\u200f\u200a\u2002\u200b\u2006) {
                    throw null;
                }
            }
            if (this.oYbE == null) {
                return;
            }
            final float n2 = Wrapper.mc.player.getItemInUseCount() / 20.0f;
            float rotationPitch = (float)(-Math.toDegrees(this.fbvt(this.oYbE, MathHelper.clamp((n2 * n2 + n2 * 2.0f) / 3.0f, 0.0f, 1.0f) * 3.0f, 0.05000000074505806)));
            if (Float.isNaN(rotationPitch)) {
                rotationPitch = Wrapper.mc.player.rotationPitch;
            }
            final Vec3d wUgG = wUgG((Entity)this.oYbE, 0.0f);
            final float prFd = (float)(Math.atan2(wUgG.z - Wrapper.mc.player.posZ, wUgG.x - Wrapper.mc.player.posX) * 180.0 / 3.141592653589793) - 90.0f;
            if (this.rotations.OnGi().equalsIgnoreCase("Client")) {
                Wrapper.mc.player.rotationYaw = prFd;
                Wrapper.mc.player.rotationPitch = rotationPitch;
                if (BowAimBot.\u2000\u2003\u2001\u200f\u200a\u2002\u200b\u2006) {
                    throw null;
                }
            }
            else {
                Wrapper.mc.player.rotationYawHead = prFd;
                omqH.prFd = prFd;
                omqH.OjyD = rotationPitch;
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
    
    private float fbvt(final EntityLivingBase entityLivingBase, final double n, final double n2) {
        final double n3 = entityLivingBase.posY + entityLivingBase.getEyeHeight() / 2.0f - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n4 = entityLivingBase.posX - Wrapper.mc.player.posX;
        final double n5 = entityLivingBase.posZ - Wrapper.mc.player.posZ;
        return this.Htsg(n, n2, Math.sqrt(n4 * n4 + n5 * n5), n3);
    }
    
    private float Htsg(final double n, final double n2, final double n3, final double n4) {
        final double sqrt = Math.sqrt(n * n * n * n - n2 * (n2 * (n3 * n3) + 2.0 * n4 * (n * n)));
        return (float)Math.min(Math.atan2(n * n + sqrt, n2 * n3), Math.atan2(n * n - sqrt, n2 * n3));
    }
    
    private static Vec3d LIoU(final Vec3d vec3d, final Vec3d vec3d2, final float n) {
        return new Vec3d(vec3d.x + (vec3d.x - vec3d2.x) * n, vec3d.y + (vec3d.y - vec3d2.y) * n, vec3d.z + (vec3d.z - vec3d2.z) * n);
    }
    
    public static Vec3d wUgG(final Entity entity, final float n) {
        return LIoU(new Vec3d(entity.posX, entity.posY, entity.posZ), new Vec3d(entity.prevPosX, entity.prevPosY, entity.prevPosZ), n);
    }
}
