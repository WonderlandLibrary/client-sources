package wwe.modules.combat;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import wwe.*;
import net.minecraft.util.math.*;

public class AimAssist extends qMIe
{
    int yfNH;
    private boolean Gnzx;
    private int PaSt;
    public static float iGUX;
    public static float TgZu;
    public static EntityLivingBase oYbE;
    public static List<EntityLivingBase> Yhkx;
    public int pcWm;
    protected long xlKA;
    private long lnip;
    public BooleanValue players;
    public BooleanValue mobs;
    public BooleanValue insvibles;
    public BooleanValue walls;
    public NumberValue range;
    
    public AimAssist() {
        super("Aim Assist", "Automatically aims at entities", 0, Category.COMBAT);
        this.yfNH = 0;
        this.xlKA = (0x333038385E597EFDL ^ 0xCCCFC7C7A1A68102L);
        this.lnip = (0xB4891C0ABEF33D36L ^ 0xB4891C0ABEF33D36L);
    }
    
    @Override
    public void HrDr() {
        this.players = new BooleanValue("Players", this, "Target players", true);
        this.mobs = new BooleanValue("Mobs", this, "Target mobs", false);
        this.insvibles = new BooleanValue("Insvibles", this, "Target Insvibles", false);
        this.walls = new BooleanValue("Walls", this, "target threw walls", true);
        this.range = new NumberValue("Range", this, "Range for aiming", 6.0, 1.0, 6.0);
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        AimAssist.iGUX = Wrapper.mc.player.rotationYaw;
        AimAssist.TgZu = Wrapper.mc.player.rotationPitch;
        AimAssist.oYbE = null;
        super.qBmy();
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if (!this.FEgb(AimAssist.oYbE) || AimAssist.oYbE == null) {
            this.orZD();
        }
        for (final EntityLiving next : Wrapper.mc.world.loadedEntityList) {
            if (next instanceof EntityLiving) {
                final EntityLiving oYbE = next;
                if (this.FEgb((EntityLivingBase)oYbE) && oYbE.getDistance((Entity)Wrapper.mc.player) < AimAssist.oYbE.getDistance((Entity)Wrapper.mc.player)) {
                    AimAssist.oYbE = (EntityLivingBase)oYbE;
                }
            }
        }
        if (this.FEgb(AimAssist.oYbE)) {
            this.Kvbu((Entity)AimAssist.oYbE, Float.MAX_VALUE, Float.MAX_VALUE);
            final EntityPlayerSP player = Wrapper.mc.player;
            player.rotationPitch += 9.0E-4f;
            this.ioqL();
        }
    }
    
    public void Udcg() {
        for (final EntityLivingBase next : Wrapper.mc.world.loadedEntityList) {
            if (next instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = next;
                if (this.FEgb(entityLivingBase) && entityLivingBase != Wrapper.mc.player) {
                    AimAssist.Yhkx.add(entityLivingBase);
                }
                else if (AimAssist.Yhkx.contains(entityLivingBase)) {
                    AimAssist.Yhkx.remove(entityLivingBase);
                }
            }
        }
    }
    
    public boolean FEgb(final EntityLivingBase entityLivingBase) {
        if (this.players.OnGi() && entityLivingBase instanceof EntityPlayer && this.tYaC((Entity)entityLivingBase) && ((EntityPlayer)entityLivingBase).getHealth() > 0.0f && !entityLivingBase.isDead) {
            vlwv.Zqnd();
            if (!vlwv.Zqnd().UsVJ(entityLivingBase.getName()) && !entityLivingBase.getName().startsWith("Body #")) {
                if (this.walls.OnGi()) {
                    return this.insvibles.OnGi() || !entityLivingBase.isInvisible();
                }
                if (!this.insvibles.OnGi()) {
                    return !entityLivingBase.isInvisible();
                }
                return entityLivingBase.canEntityBeSeen((Entity)Wrapper.mc.player);
            }
        }
        if (this.mobs.OnGi()) {
            if (entityLivingBase instanceof EntityLivingBase && !(entityLivingBase instanceof EntityPlayer) && this.QqsU((Entity)entityLivingBase) && entityLivingBase.getHealth() > 0.0f && !entityLivingBase.isDead) {
                vlwv.Zqnd();
                if (!vlwv.Zqnd().UsVJ(entityLivingBase.getName()) && !entityLivingBase.getName().startsWith("Body #")) {
                    if (this.walls.OnGi()) {
                        return this.insvibles.OnGi() || !entityLivingBase.isInvisible();
                    }
                    if (!this.insvibles.OnGi()) {
                        return !entityLivingBase.isInvisible();
                    }
                    return entityLivingBase.canEntityBeSeen((Entity)Wrapper.mc.player);
                }
            }
            if (entityLivingBase instanceof EntityLivingBase && !(entityLivingBase instanceof EntityPlayer) && this.QqsU((Entity)entityLivingBase) && entityLivingBase.getHealth() > 0.0f && !entityLivingBase.isDead) {
                vlwv.Zqnd();
                if (!vlwv.Zqnd().UsVJ(entityLivingBase.getName()) && !entityLivingBase.getName().startsWith("Body #")) {
                    if (this.walls.OnGi()) {
                        return this.insvibles.OnGi() || !entityLivingBase.isInvisible();
                    }
                    if (!this.insvibles.OnGi()) {
                        return !entityLivingBase.isInvisible();
                    }
                    return entityLivingBase.canEntityBeSeen((Entity)Wrapper.mc.player);
                }
            }
        }
        return false;
    }
    
    public boolean tYaC(final Entity entity) {
        boolean b;
        if (entity.getDistance((Entity)Wrapper.mc.player) <= this.range.OnGi()) {
            b = true;
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean QqsU(final Entity entity) {
        boolean b;
        if (entity.getDistance((Entity)Wrapper.mc.player) <= this.range.OnGi()) {
            b = true;
        }
        else {
            b = false;
        }
        return b;
    }
    
    public void YjnP() {
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
    }
    
    public void orZD() {
        for (final EntityLivingBase next : Wrapper.mc.world.loadedEntityList) {
            if (next instanceof EntityLivingBase) {
                final EntityLivingBase oYbE = next;
                if (!(oYbE instanceof EntityPlayerSP) && this.FEgb(oYbE)) {
                    AimAssist.oYbE = oYbE;
                }
            }
        }
    }
    
    @oqrr
    public void UKKd(final AbZf abZf) {
    }
    
    private boolean PUuD(final Item item) {
        return item instanceof ItemSword;
    }
    
    @oqrr
    public void gbjy(final uqzS uqzS) {
        if (!this.FEgb(AimAssist.oYbE)) {
            return;
        }
        if (uqzS.MYlK() instanceof CPacketPlayer) {
            final CPacketPlayer cPacketPlayer = (CPacketPlayer)uqzS.MYlK();
            yYGD.gcRV(cPacketPlayer, AimAssist.iGUX);
            yYGD.ZNGm(cPacketPlayer, AimAssist.TgZu);
        }
        if (uqzS.MYlK() instanceof CPacketPlayer) {
            final CPacketPlayer cPacketPlayer2 = (CPacketPlayer)uqzS.MYlK();
            yYGD.gcRV(cPacketPlayer2, AimAssist.iGUX);
            yYGD.ZNGm(cPacketPlayer2, AimAssist.TgZu);
        }
        if (uqzS.MYlK() instanceof CPacketPlayer) {
            final CPacketPlayer cPacketPlayer3 = (CPacketPlayer)uqzS.MYlK();
            yYGD.gcRV(cPacketPlayer3, AimAssist.iGUX);
            yYGD.ZNGm(cPacketPlayer3, AimAssist.TgZu);
        }
        if (uqzS.MYlK() instanceof CPacketPlayer && this.Gnzx && this.PaSt < 2) {
            uqzS.Ggez(true);
            ++this.PaSt;
        }
        else if (this.PaSt == 2) {
            this.PaSt = 0;
            this.Gnzx = false;
        }
    }
    
    public void ioqL() {
        this.XTPp();
        if (this.FEgb(AimAssist.oYbE)) {
            this.Kvbu((Entity)AimAssist.oYbE, Float.MAX_VALUE, Float.MAX_VALUE);
            final EntityPlayerSP player = Wrapper.mc.player;
            player.rotationPitch += 9.0E-4f;
            this.Kvbu((Entity)AimAssist.oYbE, Float.MAX_VALUE, Float.MAX_VALUE);
            final EntityPlayerSP player2 = Wrapper.mc.player;
            player2.rotationPitch += 9.0E-4f;
        }
    }
    
    public void Kvbu(final Entity entity, final float n, final float n2) {
        final double n3 = entity.posX - Wrapper.mc.player.posX;
        final double n4 = entity.posZ - Wrapper.mc.player.posZ;
        double n5;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n5 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        }
        else {
            n5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        }
        final double n6 = MathHelper.sqrt(n3 * n3 + n4 * n4);
        final float n7 = (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        final double n8 = n5;
        float n9;
        if (entity instanceof EntityPlayer) {
            n9 = 0.5f;
        }
        else {
            n9 = 0.0f;
        }
        AimAssist.TgZu = qxzm(Wrapper.mc.player.rotationPitch, (float)(-(Math.atan2(n8 - n9, n6) * 180.0 / 3.141592653589793)), n2);
        AimAssist.iGUX = qxzm(Wrapper.mc.player.rotationYaw, n7, n);
        Wrapper.mc.player.rotationYawHead = AimAssist.iGUX;
        Wrapper.mc.player.rotationPitch = AimAssist.TgZu;
        Wrapper.mc.player.rotationYaw = AimAssist.iGUX;
    }
    
    public static float qxzm(final float n, final float n2, final float n3) {
        float wrapDegrees = MathHelper.wrapDegrees(n2 - n);
        if (wrapDegrees > n3) {
            wrapDegrees = n3;
        }
        if (wrapDegrees < -n3) {
            wrapDegrees = -n3;
        }
        return n + wrapDegrees;
    }
    
    public final void mrir() {
        this.xlKA = System.currentTimeMillis();
    }
    
    public final boolean ioDJ(final double n) {
        boolean b;
        if (this.lnip >= this.xlKA + n) {
            b = true;
        }
        else {
            b = false;
        }
        return b;
    }
    
    public final void XTPp() {
        this.lnip = System.currentTimeMillis();
    }
}
