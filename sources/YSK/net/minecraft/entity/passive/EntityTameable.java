package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.server.management.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable
{
    private static final String[] I;
    protected EntityAISit aiSit;
    
    @Override
    public EntityLivingBase getOwner() {
        try {
            final UUID fromString = UUID.fromString(this.getOwnerId());
            EntityLivingBase playerEntityByUUID;
            if (fromString == null) {
                playerEntityByUUID = null;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                playerEntityByUUID = this.worldObj.getPlayerEntityByUUID(fromString);
            }
            return playerEntityByUUID;
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }
    
    static {
        I();
    }
    
    @Override
    public boolean isOnSameTeam(final EntityLivingBase entityLivingBase) {
        if (this.isTamed()) {
            final EntityLivingBase owner = this.getOwner();
            if (entityLivingBase == owner) {
                return " ".length() != 0;
            }
            if (owner != null) {
                return owner.isOnSameTeam(entityLivingBase);
            }
        }
        return super.isOnSameTeam(entityLivingBase);
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x4D ^ 0x4A)) {
            this.playTameEffect(" ".length() != 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (b == (0x9F ^ 0x99)) {
            this.playTameEffect("".length() != 0);
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    public EntityTameable(final World world) {
        super(world);
        this.aiSit = new EntityAISit(this);
        this.setupTamedAI();
    }
    
    @Override
    public Entity getOwner() {
        return this.getOwner();
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        final String s = EntityTameable.I[0x74 ^ 0x71];
        String ownerId;
        if (nbtTagCompound.hasKey(EntityTameable.I[0x56 ^ 0x50], 0x2B ^ 0x23)) {
            ownerId = nbtTagCompound.getString(EntityTameable.I[0x24 ^ 0x23]);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            ownerId = PreYggdrasilConverter.getStringUUIDFromName(nbtTagCompound.getString(EntityTameable.I[0xCC ^ 0xC4]));
        }
        if (ownerId.length() > 0) {
            this.setOwnerId(ownerId);
            this.setTamed(" ".length() != 0);
        }
        this.aiSit.setSitting(nbtTagCompound.getBoolean(EntityTameable.I[0x16 ^ 0x1F]));
        this.setSitting(nbtTagCompound.getBoolean(EntityTameable.I[0xA7 ^ 0xAD]));
    }
    
    private static void I() {
        (I = new String[0x9D ^ 0x91])["".length()] = I("", "ziuRh");
        EntityTameable.I[" ".length()] = I("\u000b\u0010&\u0011\u001d\u00112\u00010", "DgHto");
        EntityTameable.I["  ".length()] = I("", "KLzfr");
        EntityTameable.I["   ".length()] = I("\u001f:6#7\u0005\u0018\u0011\u0002", "PMXFE");
        EntityTameable.I[0x82 ^ 0x86] = I(")$\f\"\r\u0014*", "zMxVd");
        EntityTameable.I[0xA2 ^ 0xA7] = I("", "PrmKv");
        EntityTameable.I[0x37 ^ 0x31] = I("\u001a;'\u0000=\u0000\u0019\u0000!", "ULIeO");
        EntityTameable.I[0x26 ^ 0x21] = I("#\u001d=\u0003\u00109?\u001a\"", "ljSfb");
        EntityTameable.I[0x29 ^ 0x21] = I("6\u0019\u00071\u001b", "yniTi");
        EntityTameable.I[0x96 ^ 0x9F] = I("'8,5,\u001a6", "tQXAE");
        EntityTameable.I[0xC8 ^ 0xC2] = I("=\u001f;=\u001d\u0000\u0011", "nvOIt");
        EntityTameable.I[0x5D ^ 0x56] = I("\u0012\u000e.\u0004\u0002\u0004\u00075\u001b\u000b\u0004\u00152\u0012!\u0004\u0015", "afAsF");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isOwner(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase == this.getOwner()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void setupTamedAI() {
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.getOwnerId() == null) {
            nbtTagCompound.setString(EntityTameable.I[" ".length()], EntityTameable.I["  ".length()]);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            nbtTagCompound.setString(EntityTameable.I["   ".length()], this.getOwnerId());
        }
        nbtTagCompound.setBoolean(EntityTameable.I[0x9B ^ 0x9F], this.isSitting());
    }
    
    public void setSitting(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x7F ^ 0x6F);
        if (b) {
            this.dataWatcher.updateObject(0x75 ^ 0x65, (byte)(watchableObjectByte | " ".length()));
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0xB ^ 0x1B, (byte)(watchableObjectByte & -"  ".length()));
        }
    }
    
    public boolean isSitting() {
        if ((this.dataWatcher.getWatchableObjectByte(0x93 ^ 0x83) & " ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x3D ^ 0x2D, (byte)"".length());
        this.dataWatcher.addObject(0xB ^ 0x1A, EntityTameable.I["".length()]);
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (!this.worldObj.isRemote && this.worldObj.getGameRules().getBoolean(EntityTameable.I[0x1F ^ 0x14]) && this.hasCustomName() && this.getOwner() instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.getOwner()).addChatMessage(this.getCombatTracker().getDeathMessage());
        }
        super.onDeath(damageSource);
    }
    
    public boolean isTamed() {
        if ((this.dataWatcher.getWatchableObjectByte(0x8A ^ 0x9A) & (0x67 ^ 0x63)) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public Team getTeam() {
        if (this.isTamed()) {
            final EntityLivingBase owner = this.getOwner();
            if (owner != null) {
                return owner.getTeam();
            }
        }
        return super.getTeam();
    }
    
    protected void playTameEffect(final boolean b) {
        EnumParticleTypes enumParticleTypes = EnumParticleTypes.HEART;
        if (!b) {
            enumParticleTypes = EnumParticleTypes.SMOKE_NORMAL;
        }
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < (0x14 ^ 0x13)) {
            this.worldObj.spawnParticle(enumParticleTypes, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int["".length()]);
            ++i;
        }
    }
    
    public void setOwnerId(final String s) {
        this.dataWatcher.updateObject(0xE ^ 0x1F, s);
    }
    
    public EntityAISit getAISit() {
        return this.aiSit;
    }
    
    public boolean shouldAttackEntity(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        return " ".length() != 0;
    }
    
    @Override
    public String getOwnerId() {
        return this.dataWatcher.getWatchableObjectString(0x76 ^ 0x67);
    }
    
    public void setTamed(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x33 ^ 0x23);
        if (b) {
            this.dataWatcher.updateObject(0x61 ^ 0x71, (byte)(watchableObjectByte | (0x36 ^ 0x32)));
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x80 ^ 0x90, (byte)(watchableObjectByte & -(0x7F ^ 0x7A)));
        }
        this.setupTamedAI();
    }
}
