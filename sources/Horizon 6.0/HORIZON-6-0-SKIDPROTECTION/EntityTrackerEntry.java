package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import java.util.Set;
import org.apache.logging.log4j.Logger;

public class EntityTrackerEntry
{
    private static final Logger £à;
    public Entity HorizonCode_Horizon_È;
    public int Â;
    public int Ý;
    public int Ø­áŒŠá;
    public int Âµá€;
    public int Ó;
    public int à;
    public int Ø;
    public int áŒŠÆ;
    public double áˆºÑ¢Õ;
    public double ÂµÈ;
    public double á;
    public int ˆÏ­;
    private double µà;
    private double ˆà;
    private double ¥Æ;
    private boolean Ø­à;
    private boolean µÕ;
    private int Æ;
    private Entity Šáƒ;
    private boolean Ï­Ðƒà;
    private boolean áŒŠà;
    public boolean £á;
    public Set Å;
    private static final String ŠÄ = "CL_00001443";
    
    static {
        £à = LogManager.getLogger();
    }
    
    public EntityTrackerEntry(final Entity p_i1525_1_, final int p_i1525_2_, final int p_i1525_3_, final boolean p_i1525_4_) {
        this.Å = Sets.newHashSet();
        this.HorizonCode_Horizon_È = p_i1525_1_;
        this.Â = p_i1525_2_;
        this.Ý = p_i1525_3_;
        this.µÕ = p_i1525_4_;
        this.Ø­áŒŠá = MathHelper.Ý(p_i1525_1_.ŒÏ * 32.0);
        this.Âµá€ = MathHelper.Ý(p_i1525_1_.Çªà¢ * 32.0);
        this.Ó = MathHelper.Ý(p_i1525_1_.Ê * 32.0);
        this.à = MathHelper.Ø­áŒŠá(p_i1525_1_.É * 256.0f / 360.0f);
        this.Ø = MathHelper.Ø­áŒŠá(p_i1525_1_.áƒ * 256.0f / 360.0f);
        this.áŒŠÆ = MathHelper.Ø­áŒŠá(p_i1525_1_.Û() * 256.0f / 360.0f);
        this.áŒŠà = p_i1525_1_.ŠÂµà;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return p_equals_1_ instanceof EntityTrackerEntry && ((EntityTrackerEntry)p_equals_1_).HorizonCode_Horizon_È.ˆá() == this.HorizonCode_Horizon_È.ˆá();
    }
    
    @Override
    public int hashCode() {
        return this.HorizonCode_Horizon_È.ˆá();
    }
    
    public void HorizonCode_Horizon_È(final List p_73122_1_) {
        this.£á = false;
        if (!this.Ø­à || this.HorizonCode_Horizon_È.Âµá€(this.µà, this.ˆà, this.¥Æ) > 16.0) {
            this.µà = this.HorizonCode_Horizon_È.ŒÏ;
            this.ˆà = this.HorizonCode_Horizon_È.Çªà¢;
            this.¥Æ = this.HorizonCode_Horizon_È.Ê;
            this.Ø­à = true;
            this.£á = true;
            this.Â(p_73122_1_);
        }
        if (this.Šáƒ != this.HorizonCode_Horizon_È.Æ || (this.HorizonCode_Horizon_È.Æ != null && this.ˆÏ­ % 60 == 0)) {
            this.Šáƒ = this.HorizonCode_Horizon_È.Æ;
            this.HorizonCode_Horizon_È(new S1BPacketEntityAttach(0, this.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È.Æ));
        }
        if (this.HorizonCode_Horizon_È instanceof EntityItemFrame && this.ˆÏ­ % 10 == 0) {
            final EntityItemFrame var2 = (EntityItemFrame)this.HorizonCode_Horizon_È;
            final ItemStack var3 = var2.µà();
            if (var3 != null && var3.HorizonCode_Horizon_È() instanceof ItemMap) {
                final MapData var4 = Items.ˆØ.HorizonCode_Horizon_È(var3, this.HorizonCode_Horizon_È.Ï­Ðƒà);
                for (final EntityPlayer var6 : p_73122_1_) {
                    final EntityPlayerMP var7 = (EntityPlayerMP)var6;
                    var4.HorizonCode_Horizon_È(var7, var3);
                    final Packet var8 = Items.ˆØ.Ø­áŒŠá(var3, this.HorizonCode_Horizon_È.Ï­Ðƒà, var7);
                    if (var8 != null) {
                        var7.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var8);
                    }
                }
            }
            this.Â();
        }
        if (this.ˆÏ­ % this.Ý == 0 || this.HorizonCode_Horizon_È.áŒŠÏ || this.HorizonCode_Horizon_È.É().HorizonCode_Horizon_È()) {
            if (this.HorizonCode_Horizon_È.Æ == null) {
                ++this.Æ;
                final int var9 = MathHelper.Ý(this.HorizonCode_Horizon_È.ŒÏ * 32.0);
                final int var10 = MathHelper.Ý(this.HorizonCode_Horizon_È.Çªà¢ * 32.0);
                final int var11 = MathHelper.Ý(this.HorizonCode_Horizon_È.Ê * 32.0);
                final int var12 = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.É * 256.0f / 360.0f);
                final int var13 = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.áƒ * 256.0f / 360.0f);
                final int var14 = var9 - this.Ø­áŒŠá;
                final int var15 = var10 - this.Âµá€;
                final int var16 = var11 - this.Ó;
                Object var17 = null;
                final boolean var18 = Math.abs(var14) >= 4 || Math.abs(var15) >= 4 || Math.abs(var16) >= 4 || this.ˆÏ­ % 60 == 0;
                final boolean var19 = Math.abs(var12 - this.à) >= 4 || Math.abs(var13 - this.Ø) >= 4;
                if (this.ˆÏ­ > 0 || this.HorizonCode_Horizon_È instanceof EntityArrow) {
                    if (var14 >= -128 && var14 < 128 && var15 >= -128 && var15 < 128 && var16 >= -128 && var16 < 128 && this.Æ <= 400 && !this.Ï­Ðƒà && this.áŒŠà == this.HorizonCode_Horizon_È.ŠÂµà) {
                        if (var18 && var19) {
                            var17 = new S14PacketEntity.Ý(this.HorizonCode_Horizon_È.ˆá(), (byte)var14, (byte)var15, (byte)var16, (byte)var12, (byte)var13, this.HorizonCode_Horizon_È.ŠÂµà);
                        }
                        else if (var18) {
                            var17 = new S14PacketEntity.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.ˆá(), (byte)var14, (byte)var15, (byte)var16, this.HorizonCode_Horizon_È.ŠÂµà);
                        }
                        else if (var19) {
                            var17 = new S14PacketEntity.Â(this.HorizonCode_Horizon_È.ˆá(), (byte)var12, (byte)var13, this.HorizonCode_Horizon_È.ŠÂµà);
                        }
                    }
                    else {
                        this.áŒŠà = this.HorizonCode_Horizon_È.ŠÂµà;
                        this.Æ = 0;
                        var17 = new S18PacketEntityTeleport(this.HorizonCode_Horizon_È.ˆá(), var9, var10, var11, (byte)var12, (byte)var13, this.HorizonCode_Horizon_È.ŠÂµà);
                    }
                }
                if (this.µÕ) {
                    final double var20 = this.HorizonCode_Horizon_È.ÇŽÉ - this.áˆºÑ¢Õ;
                    final double var21 = this.HorizonCode_Horizon_È.ˆá - this.ÂµÈ;
                    final double var22 = this.HorizonCode_Horizon_È.ÇŽÕ - this.á;
                    final double var23 = 0.02;
                    final double var24 = var20 * var20 + var21 * var21 + var22 * var22;
                    if (var24 > var23 * var23 || (var24 > 0.0 && this.HorizonCode_Horizon_È.ÇŽÉ == 0.0 && this.HorizonCode_Horizon_È.ˆá == 0.0 && this.HorizonCode_Horizon_È.ÇŽÕ == 0.0)) {
                        this.áˆºÑ¢Õ = this.HorizonCode_Horizon_È.ÇŽÉ;
                        this.ÂµÈ = this.HorizonCode_Horizon_È.ˆá;
                        this.á = this.HorizonCode_Horizon_È.ÇŽÕ;
                        this.HorizonCode_Horizon_È(new S12PacketEntityVelocity(this.HorizonCode_Horizon_È.ˆá(), this.áˆºÑ¢Õ, this.ÂµÈ, this.á));
                    }
                }
                if (var17 != null) {
                    this.HorizonCode_Horizon_È((Packet)var17);
                }
                this.Â();
                if (var18) {
                    this.Ø­áŒŠá = var9;
                    this.Âµá€ = var10;
                    this.Ó = var11;
                }
                if (var19) {
                    this.à = var12;
                    this.Ø = var13;
                }
                this.Ï­Ðƒà = false;
            }
            else {
                final int var9 = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.É * 256.0f / 360.0f);
                final int var10 = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.áƒ * 256.0f / 360.0f);
                final boolean var25 = Math.abs(var9 - this.à) >= 4 || Math.abs(var10 - this.Ø) >= 4;
                if (var25) {
                    this.HorizonCode_Horizon_È(new S14PacketEntity.Â(this.HorizonCode_Horizon_È.ˆá(), (byte)var9, (byte)var10, this.HorizonCode_Horizon_È.ŠÂµà));
                    this.à = var9;
                    this.Ø = var10;
                }
                this.Ø­áŒŠá = MathHelper.Ý(this.HorizonCode_Horizon_È.ŒÏ * 32.0);
                this.Âµá€ = MathHelper.Ý(this.HorizonCode_Horizon_È.Çªà¢ * 32.0);
                this.Ó = MathHelper.Ý(this.HorizonCode_Horizon_È.Ê * 32.0);
                this.Â();
                this.Ï­Ðƒà = true;
            }
            final int var9 = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.Û() * 256.0f / 360.0f);
            if (Math.abs(var9 - this.áŒŠÆ) >= 4) {
                this.HorizonCode_Horizon_È(new S19PacketEntityHeadLook(this.HorizonCode_Horizon_È, (byte)var9));
                this.áŒŠÆ = var9;
            }
            this.HorizonCode_Horizon_È.áŒŠÏ = false;
        }
        ++this.ˆÏ­;
        if (this.HorizonCode_Horizon_È.È) {
            this.Â(new S12PacketEntityVelocity(this.HorizonCode_Horizon_È));
            this.HorizonCode_Horizon_È.È = false;
        }
    }
    
    private void Â() {
        final DataWatcher var1 = this.HorizonCode_Horizon_È.É();
        if (var1.HorizonCode_Horizon_È()) {
            this.Â(new S1CPacketEntityMetadata(this.HorizonCode_Horizon_È.ˆá(), var1, false));
        }
        if (this.HorizonCode_Horizon_È instanceof EntityLivingBase) {
            final ServersideAttributeMap var2 = (ServersideAttributeMap)((EntityLivingBase)this.HorizonCode_Horizon_È).µÐƒÓ();
            final Set var3 = var2.Â();
            if (!var3.isEmpty()) {
                this.Â(new S20PacketEntityProperties(this.HorizonCode_Horizon_È.ˆá(), var3));
            }
            var3.clear();
        }
    }
    
    public void HorizonCode_Horizon_È(final Packet p_151259_1_) {
        for (final EntityPlayerMP var3 : this.Å) {
            var3.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_151259_1_);
        }
    }
    
    public void Â(final Packet p_151261_1_) {
        this.HorizonCode_Horizon_È(p_151261_1_);
        if (this.HorizonCode_Horizon_È instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.HorizonCode_Horizon_È).HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_151261_1_);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        for (final EntityPlayerMP var2 : this.Å) {
            var2.Ø­à(this.HorizonCode_Horizon_È);
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayerMP p_73118_1_) {
        if (this.Å.contains(p_73118_1_)) {
            p_73118_1_.Ø­à(this.HorizonCode_Horizon_È);
            this.Å.remove(p_73118_1_);
        }
    }
    
    public void Â(final EntityPlayerMP p_73117_1_) {
        if (p_73117_1_ != this.HorizonCode_Horizon_È) {
            if (this.Ý(p_73117_1_)) {
                if (!this.Å.contains(p_73117_1_) && (this.Âµá€(p_73117_1_) || this.HorizonCode_Horizon_È.Šáƒ)) {
                    this.Å.add(p_73117_1_);
                    final Packet var2 = this.Ý();
                    p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
                    if (!this.HorizonCode_Horizon_È.É().Ø­áŒŠá()) {
                        p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1CPacketEntityMetadata(this.HorizonCode_Horizon_È.ˆá(), this.HorizonCode_Horizon_È.É(), true));
                    }
                    final NBTTagCompound var3 = this.HorizonCode_Horizon_È.£ÇªÓ();
                    if (var3 != null) {
                        p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S49PacketUpdateEntityNBT(this.HorizonCode_Horizon_È.ˆá(), var3));
                    }
                    if (this.HorizonCode_Horizon_È instanceof EntityLivingBase) {
                        final ServersideAttributeMap var4 = (ServersideAttributeMap)((EntityLivingBase)this.HorizonCode_Horizon_È).µÐƒÓ();
                        final Collection var5 = var4.Ý();
                        if (!var5.isEmpty()) {
                            p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S20PacketEntityProperties(this.HorizonCode_Horizon_È.ˆá(), var5));
                        }
                    }
                    this.áˆºÑ¢Õ = this.HorizonCode_Horizon_È.ÇŽÉ;
                    this.ÂµÈ = this.HorizonCode_Horizon_È.ˆá;
                    this.á = this.HorizonCode_Horizon_È.ÇŽÕ;
                    if (this.µÕ && !(var2 instanceof S0FPacketSpawnMob)) {
                        p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S12PacketEntityVelocity(this.HorizonCode_Horizon_È.ˆá(), this.HorizonCode_Horizon_È.ÇŽÉ, this.HorizonCode_Horizon_È.ˆá, this.HorizonCode_Horizon_È.ÇŽÕ));
                    }
                    if (this.HorizonCode_Horizon_È.Æ != null) {
                        p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1BPacketEntityAttach(0, this.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È.Æ));
                    }
                    if (this.HorizonCode_Horizon_È instanceof EntityLiving && ((EntityLiving)this.HorizonCode_Horizon_È).ŠáˆºÂ() != null) {
                        p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1BPacketEntityAttach(1, this.HorizonCode_Horizon_È, ((EntityLiving)this.HorizonCode_Horizon_È).ŠáˆºÂ()));
                    }
                    if (this.HorizonCode_Horizon_È instanceof EntityLivingBase) {
                        for (int var6 = 0; var6 < 5; ++var6) {
                            final ItemStack var7 = ((EntityLivingBase)this.HorizonCode_Horizon_È).Ý(var6);
                            if (var7 != null) {
                                p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S04PacketEntityEquipment(this.HorizonCode_Horizon_È.ˆá(), var6, var7));
                            }
                        }
                    }
                    if (this.HorizonCode_Horizon_È instanceof EntityPlayer) {
                        final EntityPlayer var8 = (EntityPlayer)this.HorizonCode_Horizon_È;
                        if (var8.Ï­Ó()) {
                            p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S0APacketUseBed(var8, new BlockPos(this.HorizonCode_Horizon_È)));
                        }
                    }
                    if (this.HorizonCode_Horizon_È instanceof EntityLivingBase) {
                        final EntityLivingBase var9 = (EntityLivingBase)this.HorizonCode_Horizon_È;
                        for (final PotionEffect var11 : var9.ÇŽÈ()) {
                            p_73117_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1DPacketEntityEffect(this.HorizonCode_Horizon_È.ˆá(), var11));
                        }
                    }
                }
            }
            else if (this.Å.contains(p_73117_1_)) {
                this.Å.remove(p_73117_1_);
                p_73117_1_.Ø­à(this.HorizonCode_Horizon_È);
            }
        }
    }
    
    public boolean Ý(final EntityPlayerMP p_180233_1_) {
        final double var2 = p_180233_1_.ŒÏ - this.Ø­áŒŠá / 32;
        final double var3 = p_180233_1_.Ê - this.Ó / 32;
        return var2 >= -this.Â && var2 <= this.Â && var3 >= -this.Â && var3 <= this.Â && this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180233_1_);
    }
    
    private boolean Âµá€(final EntityPlayerMP p_73121_1_) {
        return p_73121_1_.ÇŽÉ().Ô().HorizonCode_Horizon_È(p_73121_1_, this.HorizonCode_Horizon_È.£Õ, this.HorizonCode_Horizon_È.Œà);
    }
    
    public void Â(final List p_73125_1_) {
        for (int var2 = 0; var2 < p_73125_1_.size(); ++var2) {
            this.Â(p_73125_1_.get(var2));
        }
    }
    
    private Packet Ý() {
        if (this.HorizonCode_Horizon_È.ˆáŠ) {
            EntityTrackerEntry.£à.warn("Fetching addPacket for removed entity");
        }
        if (this.HorizonCode_Horizon_È instanceof EntityItem) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 2, 1);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityPlayerMP) {
            return new S0CPacketSpawnPlayer((EntityPlayer)this.HorizonCode_Horizon_È);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityMinecart) {
            final EntityMinecart var9 = (EntityMinecart)this.HorizonCode_Horizon_È;
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 10, var9.à().HorizonCode_Horizon_È());
        }
        if (this.HorizonCode_Horizon_È instanceof EntityBoat) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 1);
        }
        if (this.HorizonCode_Horizon_È instanceof IAnimals) {
            this.áŒŠÆ = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.Û() * 256.0f / 360.0f);
            return new S0FPacketSpawnMob((EntityLivingBase)this.HorizonCode_Horizon_È);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityFishHook) {
            final EntityPlayer var10 = ((EntityFishHook)this.HorizonCode_Horizon_È).Â;
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 90, (var10 != null) ? var10.ˆá() : this.HorizonCode_Horizon_È.ˆá());
        }
        if (this.HorizonCode_Horizon_È instanceof EntityArrow) {
            final Entity var11 = ((EntityArrow)this.HorizonCode_Horizon_È).Ý;
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 60, (var11 != null) ? var11.ˆá() : this.HorizonCode_Horizon_È.ˆá());
        }
        if (this.HorizonCode_Horizon_È instanceof EntitySnowball) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 61);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityPotion) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 73, ((EntityPotion)this.HorizonCode_Horizon_È).ˆÏ­());
        }
        if (this.HorizonCode_Horizon_È instanceof EntityExpBottle) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 75);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityEnderPearl) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 65);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityEnderEye) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 72);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityFireworkRocket) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 76);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityFireball) {
            final EntityFireball var12 = (EntityFireball)this.HorizonCode_Horizon_È;
            S0EPacketSpawnObject var13 = null;
            byte var14 = 63;
            if (this.HorizonCode_Horizon_È instanceof EntitySmallFireball) {
                var14 = 64;
            }
            else if (this.HorizonCode_Horizon_È instanceof EntityWitherSkull) {
                var14 = 66;
            }
            if (var12.HorizonCode_Horizon_È != null) {
                var13 = new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, var14, ((EntityFireball)this.HorizonCode_Horizon_È).HorizonCode_Horizon_È.ˆá());
            }
            else {
                var13 = new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, var14, 0);
            }
            var13.Ø­áŒŠá((int)(var12.Â * 8000.0));
            var13.Âµá€((int)(var12.Ý * 8000.0));
            var13.Ó((int)(var12.Ø­áŒŠá * 8000.0));
            return var13;
        }
        if (this.HorizonCode_Horizon_È instanceof EntityEgg) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 62);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityTNTPrimed) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 50);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityEnderCrystal) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 51);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityFallingBlock) {
            final EntityFallingBlock var15 = (EntityFallingBlock)this.HorizonCode_Horizon_È;
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 70, Block.HorizonCode_Horizon_È(var15.Ø()));
        }
        if (this.HorizonCode_Horizon_È instanceof EntityArmorStand) {
            return new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 78);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityPainting) {
            return new S10PacketSpawnPainting((EntityPainting)this.HorizonCode_Horizon_È);
        }
        if (this.HorizonCode_Horizon_È instanceof EntityItemFrame) {
            final EntityItemFrame var16 = (EntityItemFrame)this.HorizonCode_Horizon_È;
            final S0EPacketSpawnObject var13 = new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 71, var16.Â.Ý());
            final BlockPos var17 = var16.ˆÏ­();
            var13.HorizonCode_Horizon_È(MathHelper.Ø­áŒŠá((float)(var17.HorizonCode_Horizon_È() * 32)));
            var13.Â(MathHelper.Ø­áŒŠá((float)(var17.Â() * 32)));
            var13.Ý(MathHelper.Ø­áŒŠá((float)(var17.Ý() * 32)));
            return var13;
        }
        if (this.HorizonCode_Horizon_È instanceof EntityLeashKnot) {
            final EntityLeashKnot var18 = (EntityLeashKnot)this.HorizonCode_Horizon_È;
            final S0EPacketSpawnObject var13 = new S0EPacketSpawnObject(this.HorizonCode_Horizon_È, 77);
            final BlockPos var17 = var18.ˆÏ­();
            var13.HorizonCode_Horizon_È(MathHelper.Ø­áŒŠá((float)(var17.HorizonCode_Horizon_È() * 32)));
            var13.Â(MathHelper.Ø­áŒŠá((float)(var17.Â() * 32)));
            var13.Ý(MathHelper.Ø­áŒŠá((float)(var17.Ý() * 32)));
            return var13;
        }
        if (this.HorizonCode_Horizon_È instanceof EntityXPOrb) {
            return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.HorizonCode_Horizon_È);
        }
        throw new IllegalArgumentException("Don't know how to add " + this.HorizonCode_Horizon_È.getClass() + "!");
    }
    
    public void Ø­áŒŠá(final EntityPlayerMP p_73123_1_) {
        if (this.Å.contains(p_73123_1_)) {
            this.Å.remove(p_73123_1_);
            p_73123_1_.Ø­à(this.HorizonCode_Horizon_È);
        }
    }
}
