package net.minecraft.entity.monster;

import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.effect.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class EntityCreeper extends EntityMob
{
    private int timeSinceIgnited;
    private int field_175494_bm;
    private int fuseTime;
    private int lastActiveTime;
    private int explosionRadius;
    private static final String[] I;
    
    public void ignite() {
        this.dataWatcher.updateObject(0x9D ^ 0x8F, (byte)" ".length());
    }
    
    private static void I() {
        (I = new String[0x52 ^ 0x42])["".length()] = I("(.\u0019*\u0003=%", "XAnOq");
        EntityCreeper.I[" ".length()] = I("+\r\u0004\u0013", "mxwvz");
        EntityCreeper.I["  ".length()] = I("\u0015\u001a\b\u0018\u0005#\u000b\u0017\u001a81\u0006\u0011\u0001\u0019", "Pbxtj");
        EntityCreeper.I["   ".length()] = I("\u000b\u001f\u0003%\u001d\u0007\u001c", "bxmLi");
        EntityCreeper.I[0x21 ^ 0x25] = I("\u0006,\u001f\u001f\u001e\u0013'", "vChzl");
        EntityCreeper.I[0xC6 ^ 0xC3] = I("\u0007\u00140\u0002", "AaCgx");
        EntityCreeper.I[0x59 ^ 0x5F] = I("\u0001;0#", "GNCFr");
        EntityCreeper.I[0xF ^ 0x8] = I("/\u0016\u0013\u00009\u0019\u0007\f\u0002\u0004\u000b\n\n\u0019%", "jnclV");
        EntityCreeper.I[0x55 ^ 0x5D] = I(".1;+!\u0018 $)\u001c\n-\"2=", "kIKGN");
        EntityCreeper.I[0xAC ^ 0xA5] = I("'\u000281'+\u0001", "NeVXS");
        EntityCreeper.I[0xCC ^ 0xC6] = I("3%\u000b\u000e15%@\u001b39:\u000b\u000f", "PWnkA");
        EntityCreeper.I[0x76 ^ 0x7D] = I("\b\u0002,{:\u0017\b+%<\u0017C=4 ", "emNUY");
        EntityCreeper.I[0x86 ^ 0x8A] = I("\u001b\u001b!]4\u0004\u0011&\u00032\u0004Z'\u00166\u0002\u001c", "vtCsW");
        EntityCreeper.I[0xBE ^ 0xB3] = I("0,\u0015\u0017G?\"\t\u001b\u001d3", "VEgri");
        EntityCreeper.I[0x89 ^ 0x87] = I(",\u00162>\u0000(\u001c6\u0010\u001c&", "AyPyr");
        EntityCreeper.I[0xBA ^ 0xB5] = I("\u0013\u001e\u00025\u0014;\u001e .", "wqOZv");
    }
    
    static {
        I();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x8D ^ 0x9D, (byte)(-" ".length()));
        this.dataWatcher.addObject(0x62 ^ 0x73, (byte)"".length());
        this.dataWatcher.addObject(0x89 ^ 0x9B, (byte)"".length());
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        return " ".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            if (this.hasIgnited()) {
                this.setCreeperState(" ".length());
            }
            final int creeperState = this.getCreeperState();
            if (creeperState > 0 && this.timeSinceIgnited == 0) {
                this.playSound(EntityCreeper.I[0x53 ^ 0x59], 1.0f, 0.5f);
            }
            this.timeSinceIgnited += creeperState;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = "".length();
            }
            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }
        super.onUpdate();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        this.timeSinceIgnited += (int)(n * 1.5f);
        if (this.timeSinceIgnited > this.fuseTime - (0x9F ^ 0x9A)) {
            this.timeSinceIgnited = this.fuseTime - (0x4A ^ 0x4F);
        }
    }
    
    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        final DataWatcher dataWatcher = this.dataWatcher;
        final int n = 0x70 ^ 0x61;
        int n2;
        if (nbtTagCompound.getBoolean(EntityCreeper.I[0x2 ^ 0x6])) {
            n2 = " ".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
        if (nbtTagCompound.hasKey(EntityCreeper.I[0x6D ^ 0x68], 0x2E ^ 0x4D)) {
            this.fuseTime = nbtTagCompound.getShort(EntityCreeper.I[0x5D ^ 0x5B]);
        }
        if (nbtTagCompound.hasKey(EntityCreeper.I[0x82 ^ 0x85], 0x73 ^ 0x10)) {
            this.explosionRadius = nbtTagCompound.getByte(EntityCreeper.I[0x56 ^ 0x5E]);
        }
        if (nbtTagCompound.getBoolean(EntityCreeper.I[0x51 ^ 0x58])) {
            this.ignite();
        }
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        super.onStruckByLightning(entityLightningBolt);
        this.dataWatcher.updateObject(0x82 ^ 0x93, (byte)" ".length());
    }
    
    public boolean hasIgnited() {
        if (this.dataWatcher.getWatchableObjectByte(0x51 ^ 0x43) != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityCreeper.I[0x25 ^ 0x2E];
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (damageSource.getEntity() instanceof EntitySkeleton) {
            final int idFromItem = Item.getIdFromItem(Items.record_13);
            this.dropItem(Item.getItemById(idFromItem + this.rand.nextInt(Item.getIdFromItem(Items.record_wait) - idFromItem + " ".length())), " ".length());
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (damageSource.getEntity() instanceof EntityCreeper && damageSource.getEntity() != this && ((EntityCreeper)damageSource.getEntity()).getPowered() && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper)damageSource.getEntity()).func_175493_co();
            this.entityDropItem(new ItemStack(Items.skull, " ".length(), 0x7B ^ 0x7F), 0.0f);
        }
    }
    
    public float getCreeperFlashIntensity(final float n) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * n) / (this.fuseTime - "  ".length());
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.dataWatcher.getWatchableObjectByte(0x9 ^ 0x18) == " ".length()) {
            nbtTagCompound.setBoolean(EntityCreeper.I["".length()], " ".length() != 0);
        }
        nbtTagCompound.setShort(EntityCreeper.I[" ".length()], (short)this.fuseTime);
        nbtTagCompound.setByte(EntityCreeper.I["  ".length()], (byte)this.explosionRadius);
        nbtTagCompound.setBoolean(EntityCreeper.I["   ".length()], this.hasIgnited());
    }
    
    @Override
    protected String getDeathSound() {
        return EntityCreeper.I[0x58 ^ 0x54];
    }
    
    public boolean getPowered() {
        if (this.dataWatcher.getWatchableObjectByte(0x22 ^ 0x33) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void func_175493_co() {
        this.field_175494_bm += " ".length();
    }
    
    @Override
    protected boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.flint_and_steel) {
            this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, EntityCreeper.I[0x80 ^ 0x8D], 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            entityPlayer.swingItem();
            if (!this.worldObj.isRemote) {
                this.ignite();
                currentItem.damageItem(" ".length(), entityPlayer);
                return " ".length() != 0;
            }
        }
        return super.interact(entityPlayer);
    }
    
    public boolean isAIEnabled() {
        if (this.field_175494_bm < " ".length() && this.worldObj.getGameRules().getBoolean(EntityCreeper.I[0x2A ^ 0x25])) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (4 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityCreeper(final World world) {
        super(world);
        this.fuseTime = (0x86 ^ 0x98);
        this.explosionRadius = "   ".length();
        this.field_175494_bm = "".length();
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), new EntityAICreeperSwell(this));
        this.tasks.addTask("   ".length(), new EntityAIAvoidEntity<Object>(this, EntityOcelot.class, 6.0f, 1.0, 1.2));
        this.tasks.addTask(0x6C ^ 0x68, new EntityAIAttackOnCollide(this, 1.0, (boolean)("".length() != 0)));
        this.tasks.addTask(0x82 ^ 0x87, new EntityAIWander(this, 0.8));
        this.tasks.addTask(0xE ^ 0x8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0x1F ^ 0x19, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityPlayer.class, (boolean)(" ".length() != 0)));
        this.targetTasks.addTask("  ".length(), new EntityAIHurtByTarget(this, (boolean)("".length() != 0), new Class["".length()]));
    }
    
    public void setCreeperState(final int n) {
        this.dataWatcher.updateObject(0xBF ^ 0xAF, (byte)n);
    }
    
    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(0xAE ^ 0xBE);
    }
    
    @Override
    public int getMaxFallHeight() {
        int length;
        if (this.getAttackTarget() == null) {
            length = "   ".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            length = "   ".length() + (int)(this.getHealth() - 1.0f);
        }
        return length;
    }
    
    private void explode() {
        if (!this.worldObj.isRemote) {
            final boolean boolean1 = this.worldObj.getGameRules().getBoolean(EntityCreeper.I[0x4B ^ 0x45]);
            float n;
            if (this.getPowered()) {
                n = 2.0f;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                n = 1.0f;
            }
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * n, boolean1);
            this.setDead();
        }
    }
}
