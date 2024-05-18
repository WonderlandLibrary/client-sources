package net.minecraft.entity.passive;

import com.google.common.base.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.server.management.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.block.material.*;

public class EntityHorse extends EntityAnimal implements IInvBasic
{
    private int eatingHaystackCounter;
    private float prevMouthOpenness;
    private static final String[] horseTextures;
    private static final int[] armorValues;
    private boolean field_110294_bI;
    protected int temper;
    private float headLean;
    private String[] horseTexturesArray;
    private int openMouthCounter;
    private float mouthOpenness;
    private int gallopTime;
    private boolean field_175508_bO;
    private int jumpRearingCounter;
    private AnimalChest horseChest;
    private float prevRearingAmount;
    private static final IAttribute horseJumpStrength;
    private static final String[] horseArmorTextures;
    private String texturePrefix;
    protected boolean horseJumping;
    public int field_110279_bq;
    public int field_110278_bp;
    private static final String[] HORSE_ARMOR_TEXTURES_ABBR;
    protected float jumpPower;
    private static final String[] I;
    private static final String[] horseMarkingTextures;
    private float prevHeadLean;
    private float rearingAmount;
    private static final String[] HORSE_MARKING_TEXTURES_ABBR;
    private boolean hasReproduced;
    private static final String[] HORSE_TEXTURES_ABBR;
    private static final Predicate<Entity> horseBreedingSelector;
    
    public void setJumpPower(int length) {
        if (this.isHorseSaddled()) {
            if (length < 0) {
                length = "".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                this.field_110294_bI = (" ".length() != 0);
                this.makeHorseRear();
            }
            if (length >= (0xEA ^ 0xB0)) {
                this.jumpPower = 1.0f;
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                this.jumpPower = 0.4f + 0.4f * length / 90.0f;
            }
        }
    }
    
    @Override
    protected Item getDropItem() {
        int n;
        if (this.rand.nextInt(0x7F ^ 0x7B) == 0) {
            n = " ".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        final int horseType = this.getHorseType();
        Item item;
        if (horseType == (0xAF ^ 0xAB)) {
            item = Items.bone;
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else if (horseType == "   ".length()) {
            if (n2 != 0) {
                item = null;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                item = Items.rotten_flesh;
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
        }
        else {
            item = Items.leather;
        }
        return item;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntityHorse.I[0x26 ^ 0x63], this.isEatingHaystack());
        nbtTagCompound.setBoolean(EntityHorse.I[0x64 ^ 0x22], this.isChested());
        nbtTagCompound.setBoolean(EntityHorse.I[0xE2 ^ 0xA5], this.getHasReproduced());
        nbtTagCompound.setBoolean(EntityHorse.I[0xE9 ^ 0xA1], this.isBreeding());
        nbtTagCompound.setInteger(EntityHorse.I[0x75 ^ 0x3C], this.getHorseType());
        nbtTagCompound.setInteger(EntityHorse.I[0x3 ^ 0x49], this.getHorseVariant());
        nbtTagCompound.setInteger(EntityHorse.I[0x46 ^ 0xD], this.getTemper());
        nbtTagCompound.setBoolean(EntityHorse.I[0x89 ^ 0xC5], this.isTame());
        nbtTagCompound.setString(EntityHorse.I[0x38 ^ 0x75], this.getOwnerId());
        if (this.isChested()) {
            final NBTTagList list = new NBTTagList();
            int i = "  ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (i < this.horseChest.getSizeInventory()) {
                final ItemStack stackInSlot = this.horseChest.getStackInSlot(i);
                if (stackInSlot != null) {
                    final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                    nbtTagCompound2.setByte(EntityHorse.I[0x2D ^ 0x63], (byte)i);
                    stackInSlot.writeToNBT(nbtTagCompound2);
                    list.appendTag(nbtTagCompound2);
                }
                ++i;
            }
            nbtTagCompound.setTag(EntityHorse.I[0xDB ^ 0x94], list);
        }
        if (this.horseChest.getStackInSlot(" ".length()) != null) {
            nbtTagCompound.setTag(EntityHorse.I[0x7A ^ 0x2A], this.horseChest.getStackInSlot(" ".length()).writeToNBT(new NBTTagCompound()));
        }
        if (this.horseChest.getStackInSlot("".length()) != null) {
            nbtTagCompound.setTag(EntityHorse.I[0x3 ^ 0x52], this.horseChest.getStackInSlot("".length()).writeToNBT(new NBTTagCompound()));
        }
    }
    
    public EntityHorse(final World world) {
        super(world);
        this.horseTexturesArray = new String["   ".length()];
        this.field_175508_bO = ("".length() != 0);
        this.setSize(1.4f, 1.6f);
        this.isImmuneToFire = ("".length() != 0);
        this.setChested("".length() != 0);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask(" ".length(), new EntityAIPanic(this, 1.2));
        this.tasks.addTask(" ".length(), new EntityAIRunAroundLikeCrazy(this, 1.2));
        this.tasks.addTask("  ".length(), new EntityAIMate(this, 1.0));
        this.tasks.addTask(0x63 ^ 0x67, new EntityAIFollowParent(this, 1.0));
        this.tasks.addTask(0x1 ^ 0x7, new EntityAIWander(this, 0.7));
        this.tasks.addTask(0xAA ^ 0xAD, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(0x8E ^ 0x86, new EntityAILookIdle(this));
        this.initHorseChest();
    }
    
    public String getHorseTexture() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }
        return this.texturePrefix;
    }
    
    private void mountTo(final EntityPlayer entityPlayer) {
        entityPlayer.rotationYaw = this.rotationYaw;
        entityPlayer.rotationPitch = this.rotationPitch;
        this.setEatingHaystack("".length() != 0);
        this.setRearing("".length() != 0);
        if (!this.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
        }
    }
    
    public int getTemper() {
        return this.temper;
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        if (n == 382 + 47 - 34 + 104 && this.canCarryChest()) {
            if (itemStack == null && this.isChested()) {
                this.setChested("".length() != 0);
                this.initHorseChest();
                return " ".length() != 0;
            }
            if (itemStack != null && itemStack.getItem() == Item.getItemFromBlock(Blocks.chest) && !this.isChested()) {
                this.setChested(" ".length() != 0);
                this.initHorseChest();
                return " ".length() != 0;
            }
        }
        final int n2 = n - (158 + 85 - 171 + 328);
        if (n2 >= 0 && n2 < "  ".length() && n2 < this.horseChest.getSizeInventory()) {
            if (n2 == 0 && itemStack != null && itemStack.getItem() != Items.saddle) {
                return "".length() != 0;
            }
            if (n2 != " ".length() || ((itemStack == null || isArmorItem(itemStack.getItem())) && this.canWearArmor())) {
                this.horseChest.setInventorySlotContents(n2, itemStack);
                this.updateHorseSlots();
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        else {
            final int n3 = n - (298 + 414 - 322 + 110) + "  ".length();
            if (n3 >= "  ".length() && n3 < this.horseChest.getSizeInventory()) {
                this.horseChest.setInventorySlotContents(n3, itemStack);
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
    
    @Override
    public boolean canBePushed() {
        if (this.riddenByEntity == null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public String getOwnerId() {
        return this.dataWatcher.getWatchableObjectString(0x4B ^ 0x5E);
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (!this.worldObj.isRemote) {
            this.dropChestItems();
        }
    }
    
    public float getRearingAmount(final float n) {
        return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * n;
    }
    
    protected void spawnHorseParticles(final boolean b) {
        EnumParticleTypes enumParticleTypes;
        if (b) {
            enumParticleTypes = EnumParticleTypes.HEART;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            enumParticleTypes = EnumParticleTypes.SMOKE_NORMAL;
        }
        final EnumParticleTypes enumParticleTypes2 = enumParticleTypes;
        int i = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (i < (0x64 ^ 0x63)) {
            this.worldObj.spawnParticle(enumParticleTypes2, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int["".length()]);
            ++i;
        }
    }
    
    public boolean isTame() {
        return this.getHorseWatchableBoolean("  ".length());
    }
    
    public void makeHorseRearWithSound() {
        this.makeHorseRear();
        final String angrySoundName = this.getAngrySoundName();
        if (angrySoundName != null) {
            this.playSound(angrySoundName, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemStack) {
        return "".length() != 0;
    }
    
    public void openGUI(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityPlayer) && this.isTame()) {
            this.horseChest.setCustomName(this.getName());
            entityPlayer.displayGUIHorse(this, this.horseChest);
        }
    }
    
    @Override
    public void moveEntityWithHeading(float n, float moveForward) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && this.isHorseSaddled()) {
            final float rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationYaw = rotationYaw;
            this.prevRotationYaw = rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final float rotationYaw2 = this.rotationYaw;
            this.renderYawOffset = rotationYaw2;
            this.rotationYawHead = rotationYaw2;
            n = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5f;
            moveForward = ((EntityLivingBase)this.riddenByEntity).moveForward;
            if (moveForward <= 0.0f) {
                moveForward *= 0.25f;
                this.gallopTime = "".length();
            }
            if (this.onGround && this.jumpPower == 0.0f && this.isRearing() && !this.field_110294_bI) {
                n = 0.0f;
                moveForward = 0.0f;
            }
            if (this.jumpPower > 0.0f && !this.isHorseJumping() && this.onGround) {
                this.motionY = this.getHorseJumpStrength() * this.jumpPower;
                if (this.isPotionActive(Potion.jump)) {
                    this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + " ".length()) * 0.1f;
                }
                this.setHorseJumping(" ".length() != 0);
                this.isAirBorne = (" ".length() != 0);
                if (moveForward > 0.0f) {
                    final float sin = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
                    final float cos = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
                    this.motionX += -0.4f * sin * this.jumpPower;
                    this.motionZ += 0.4f * cos * this.jumpPower;
                    this.playSound(EntityHorse.I[0xD4 ^ 0x90], 0.4f, 1.0f);
                }
                this.jumpPower = 0.0f;
            }
            this.stepHeight = 1.0f;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;
            if (!this.worldObj.isRemote) {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                super.moveEntityWithHeading(n, moveForward);
            }
            if (this.onGround) {
                this.jumpPower = 0.0f;
                this.setHorseJumping("".length() != 0);
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            final double n2 = this.posX - this.prevPosX;
            final double n3 = this.posZ - this.prevPosZ;
            float n4 = MathHelper.sqrt_double(n2 * n2 + n3 * n3) * 4.0f;
            if (n4 > 1.0f) {
                n4 = 1.0f;
            }
            this.limbSwingAmount += (n4 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            this.stepHeight = 0.5f;
            this.jumpMovementFactor = 0.02f;
            super.moveEntityWithHeading(n, moveForward);
        }
    }
    
    public int getHorseType() {
        return this.dataWatcher.getWatchableObjectByte(0x89 ^ 0x9A);
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        Block.SoundType soundType = block.stepSound;
        if (this.worldObj.getBlockState(blockPos.up()).getBlock() == Blocks.snow_layer) {
            soundType = Blocks.snow_layer.stepSound;
        }
        if (!block.getMaterial().isLiquid()) {
            final int horseType = this.getHorseType();
            if (this.riddenByEntity != null && horseType != " ".length() && horseType != "  ".length()) {
                this.gallopTime += " ".length();
                if (this.gallopTime > (0x9F ^ 0x9A) && this.gallopTime % "   ".length() == 0) {
                    this.playSound(EntityHorse.I[0x6B ^ 0x51], soundType.getVolume() * 0.15f, soundType.getFrequency());
                    if (horseType == 0 && this.rand.nextInt(0x73 ^ 0x79) == 0) {
                        this.playSound(EntityHorse.I[0xB4 ^ 0x8F], soundType.getVolume() * 0.6f, soundType.getFrequency());
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                    }
                }
                else if (this.gallopTime <= (0xC5 ^ 0xC0)) {
                    this.playSound(EntityHorse.I[0x6C ^ 0x50], soundType.getVolume() * 0.15f, soundType.getFrequency());
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                }
            }
            else if (soundType == Block.soundTypeWood) {
                this.playSound(EntityHorse.I[0x5D ^ 0x60], soundType.getVolume() * 0.15f, soundType.getFrequency());
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                this.playSound(EntityHorse.I[0x82 ^ 0xBC], soundType.getVolume() * 0.15f, soundType.getFrequency());
            }
        }
    }
    
    private void func_110266_cB() {
        this.openHorseMouth();
        if (!this.isSilent()) {
            this.worldObj.playSoundAtEntity(this, EntityHorse.I[0x94 ^ 0xB2], 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        }
    }
    
    public boolean isChested() {
        return this.getHorseWatchableBoolean(0x8D ^ 0x85);
    }
    
    public int getHorseVariant() {
        return this.dataWatcher.getWatchableObjectInt(0x33 ^ 0x27);
    }
    
    @Override
    protected String getHurtSound() {
        this.openHorseMouth();
        if (this.rand.nextInt("   ".length()) == 0) {
            this.makeHorseRear();
        }
        final int horseType = this.getHorseType();
        String s;
        if (horseType == "   ".length()) {
            s = EntityHorse.I[0x2C ^ 0x1C];
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else if (horseType == (0x92 ^ 0x96)) {
            s = EntityHorse.I[0x81 ^ 0xB0];
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (horseType != " ".length() && horseType != "  ".length()) {
            s = EntityHorse.I[0x44 ^ 0x76];
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            s = EntityHorse.I[0x46 ^ 0x75];
        }
        return s;
    }
    
    static {
        I();
        horseBreedingSelector = (Predicate)new Predicate<Entity>() {
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                if (entity instanceof EntityHorse && ((EntityHorse)entity).isBreeding()) {
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
                    if (2 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        horseJumpStrength = new RangedAttribute(null, EntityHorse.I["".length()], 0.7, 0.0, 2.0).setDescription(EntityHorse.I[" ".length()]).setShouldWatch(" ".length() != 0);
        final String[] horseArmorTextures2 = new String[0x87 ^ 0x83];
        horseArmorTextures2[" ".length()] = EntityHorse.I["  ".length()];
        horseArmorTextures2["  ".length()] = EntityHorse.I["   ".length()];
        horseArmorTextures2["   ".length()] = EntityHorse.I[0x80 ^ 0x84];
        horseArmorTextures = horseArmorTextures2;
        final String[] horse_ARMOR_TEXTURES_ABBR = new String[0x2 ^ 0x6];
        horse_ARMOR_TEXTURES_ABBR["".length()] = EntityHorse.I[0x54 ^ 0x51];
        horse_ARMOR_TEXTURES_ABBR[" ".length()] = EntityHorse.I[0xA9 ^ 0xAF];
        horse_ARMOR_TEXTURES_ABBR["  ".length()] = EntityHorse.I[0xAC ^ 0xAB];
        horse_ARMOR_TEXTURES_ABBR["   ".length()] = EntityHorse.I[0xA4 ^ 0xAC];
        HORSE_ARMOR_TEXTURES_ABBR = horse_ARMOR_TEXTURES_ABBR;
        final int[] armorValues2 = new int[0x63 ^ 0x67];
        armorValues2[" ".length()] = (0x70 ^ 0x75);
        armorValues2["  ".length()] = (0xAE ^ 0xA9);
        armorValues2["   ".length()] = (0x3E ^ 0x35);
        armorValues = armorValues2;
        final String[] horseTextures2 = new String[0x81 ^ 0x86];
        horseTextures2["".length()] = EntityHorse.I[0x9B ^ 0x92];
        horseTextures2[" ".length()] = EntityHorse.I[0xAD ^ 0xA7];
        horseTextures2["  ".length()] = EntityHorse.I[0x32 ^ 0x39];
        horseTextures2["   ".length()] = EntityHorse.I[0x5D ^ 0x51];
        horseTextures2[0x52 ^ 0x56] = EntityHorse.I[0x69 ^ 0x64];
        horseTextures2[0x34 ^ 0x31] = EntityHorse.I[0xC9 ^ 0xC7];
        horseTextures2[0x19 ^ 0x1F] = EntityHorse.I[0x35 ^ 0x3A];
        horseTextures = horseTextures2;
        final String[] horse_TEXTURES_ABBR = new String[0x9A ^ 0x9D];
        horse_TEXTURES_ABBR["".length()] = EntityHorse.I[0xF ^ 0x1F];
        horse_TEXTURES_ABBR[" ".length()] = EntityHorse.I[0x75 ^ 0x64];
        horse_TEXTURES_ABBR["  ".length()] = EntityHorse.I[0x1B ^ 0x9];
        horse_TEXTURES_ABBR["   ".length()] = EntityHorse.I[0x3D ^ 0x2E];
        horse_TEXTURES_ABBR[0xBA ^ 0xBE] = EntityHorse.I[0x4B ^ 0x5F];
        horse_TEXTURES_ABBR[0x78 ^ 0x7D] = EntityHorse.I[0x77 ^ 0x62];
        horse_TEXTURES_ABBR[0x5F ^ 0x59] = EntityHorse.I[0x19 ^ 0xF];
        HORSE_TEXTURES_ABBR = horse_TEXTURES_ABBR;
        final String[] horseMarkingTextures2 = new String[0x8E ^ 0x8B];
        horseMarkingTextures2[" ".length()] = EntityHorse.I[0x25 ^ 0x32];
        horseMarkingTextures2["  ".length()] = EntityHorse.I[0x36 ^ 0x2E];
        horseMarkingTextures2["   ".length()] = EntityHorse.I[0x8 ^ 0x11];
        horseMarkingTextures2[0x5D ^ 0x59] = EntityHorse.I[0x5B ^ 0x41];
        horseMarkingTextures = horseMarkingTextures2;
        final String[] horse_MARKING_TEXTURES_ABBR = new String[0x61 ^ 0x64];
        horse_MARKING_TEXTURES_ABBR["".length()] = EntityHorse.I[0x68 ^ 0x73];
        horse_MARKING_TEXTURES_ABBR[" ".length()] = EntityHorse.I[0x27 ^ 0x3B];
        horse_MARKING_TEXTURES_ABBR["  ".length()] = EntityHorse.I[0x9E ^ 0x83];
        horse_MARKING_TEXTURES_ABBR["   ".length()] = EntityHorse.I[0x6A ^ 0x74];
        horse_MARKING_TEXTURES_ABBR[0x52 ^ 0x56] = EntityHorse.I[0xE ^ 0x11];
        HORSE_MARKING_TEXTURES_ABBR = horse_MARKING_TEXTURES_ABBR;
    }
    
    public boolean setTamedBy(final EntityPlayer entityPlayer) {
        this.setOwnerId(entityPlayer.getUniqueID().toString());
        this.setHorseTamed(" ".length() != 0);
        return " ".length() != 0;
    }
    
    public void setHorseTamed(final boolean b) {
        this.setHorseWatchableBoolean("  ".length(), b);
    }
    
    public boolean canWearArmor() {
        if (this.getHorseType() == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, (IEntityLivingData)onInitialSpawn);
        "".length();
        int length = "".length();
        int horseType;
        if (onInitialSpawn instanceof GroupData) {
            horseType = ((GroupData)onInitialSpawn).horseType;
            length = ((((GroupData)onInitialSpawn).horseVariant & 21 + 39 - 24 + 219) | this.rand.nextInt(0x27 ^ 0x22) << (0x89 ^ 0x81));
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            if (this.rand.nextInt(0xB2 ^ 0xB8) == 0) {
                horseType = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                final int nextInt = this.rand.nextInt(0x95 ^ 0x92);
                final int nextInt2 = this.rand.nextInt(0x45 ^ 0x40);
                horseType = "".length();
                length = (nextInt | nextInt2 << (0x55 ^ 0x5D));
            }
            onInitialSpawn = new GroupData(horseType, length);
        }
        this.setHorseType(horseType);
        this.setHorseVariant(length);
        if (this.rand.nextInt(0x63 ^ 0x66) == 0) {
            this.setGrowingAge(-(16353 + 19720 - 27609 + 15536));
        }
        if (horseType != (0x17 ^ 0x13) && horseType != "   ".length()) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getModifiedMaxHealth());
            if (horseType == 0) {
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.getModifiedMovementSpeed());
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.17499999701976776);
                "".length();
                if (false == true) {
                    throw null;
                }
            }
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
        }
        if (horseType != "  ".length() && horseType != " ".length()) {
            this.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue(this.getModifiedJumpStrength());
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            this.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue(0.5);
        }
        this.setHealth(this.getMaxHealth());
        return (IEntityLivingData)onInitialSpawn;
    }
    
    @Override
    protected String getLivingSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(0x84 ^ 0x8E) == 0 && !this.isMovementBlocked()) {
            this.makeHorseRear();
        }
        final int horseType = this.getHorseType();
        String s;
        if (horseType == "   ".length()) {
            s = EntityHorse.I[0x86 ^ 0xB2];
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (horseType == (0x7F ^ 0x7B)) {
            s = EntityHorse.I[0xB8 ^ 0x8D];
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else if (horseType != " ".length() && horseType != "  ".length()) {
            s = EntityHorse.I[0x86 ^ 0xB0];
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            s = EntityHorse.I[0xF4 ^ 0xC3];
        }
        return s;
    }
    
    public boolean isAdultHorse() {
        int n;
        if (this.isChild()) {
            n = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        final Entity entity = damageSource.getEntity();
        int n2;
        if (this.riddenByEntity != null && this.riddenByEntity.equals(entity)) {
            n2 = "".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n2 = (super.attackEntityFrom(damageSource, n) ? 1 : 0);
        }
        return n2 != 0;
    }
    
    public int getMaxTemper() {
        return 0x1B ^ 0x7F;
    }
    
    public boolean isHorseJumping() {
        return this.horseJumping;
    }
    
    public boolean func_175507_cI() {
        return this.field_175508_bO;
    }
    
    public void setHorseVariant(final int n) {
        this.dataWatcher.updateObject(0x49 ^ 0x5D, n);
        this.resetTexturePrefix();
    }
    
    private void dropItemsInChest(final Entity entity, final AnimalChest animalChest) {
        if (animalChest != null && !this.worldObj.isRemote) {
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < animalChest.getSizeInventory()) {
                final ItemStack stackInSlot = animalChest.getStackInSlot(i);
                if (stackInSlot != null) {
                    this.entityDropItem(stackInSlot, 0.0f);
                }
                ++i;
            }
        }
    }
    
    public boolean isEatingHaystack() {
        return this.getHorseWatchableBoolean(0x3 ^ 0x23);
    }
    
    public boolean canCarryChest() {
        final int horseType = this.getHorseType();
        if (horseType != "  ".length() && horseType != " ".length()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public boolean func_110239_cn() {
        if (this.getHorseType() != 0 && this.getHorseArmorIndexSynced() <= 0) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void setRearing(final boolean b) {
        if (b) {
            this.setEatingHaystack("".length() != 0);
        }
        this.setHorseWatchableBoolean(0xCB ^ 0x8B, b);
    }
    
    public boolean isRearing() {
        return this.getHorseWatchableBoolean(0x6 ^ 0x46);
    }
    
    public float getHorseSize() {
        return 0.5f;
    }
    
    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        }
        switch (this.getHorseType()) {
            default: {
                return StatCollector.translateToLocal(EntityHorse.I[0xAC ^ 0x8D]);
            }
            case 1: {
                return StatCollector.translateToLocal(EntityHorse.I[0x75 ^ 0x57]);
            }
            case 2: {
                return StatCollector.translateToLocal(EntityHorse.I[0x27 ^ 0x4]);
            }
            case 3: {
                return StatCollector.translateToLocal(EntityHorse.I[0x10 ^ 0x34]);
            }
            case 4: {
                return StatCollector.translateToLocal(EntityHorse.I[0x7A ^ 0x5F]);
            }
        }
    }
    
    public boolean isHorseSaddled() {
        return this.getHorseWatchableBoolean(0x1 ^ 0x5);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setEatingHaystack(nbtTagCompound.getBoolean(EntityHorse.I[0xF8 ^ 0xAA]));
        this.setBreeding(nbtTagCompound.getBoolean(EntityHorse.I[0xCB ^ 0x98]));
        this.setChested(nbtTagCompound.getBoolean(EntityHorse.I[0xC9 ^ 0x9D]));
        this.setHasReproduced(nbtTagCompound.getBoolean(EntityHorse.I[0xC7 ^ 0x92]));
        this.setHorseType(nbtTagCompound.getInteger(EntityHorse.I[0x36 ^ 0x60]));
        this.setHorseVariant(nbtTagCompound.getInteger(EntityHorse.I[0x9 ^ 0x5E]));
        this.setTemper(nbtTagCompound.getInteger(EntityHorse.I[0xD ^ 0x55]));
        this.setHorseTamed(nbtTagCompound.getBoolean(EntityHorse.I[0x3A ^ 0x63]));
        final String s = EntityHorse.I[0x35 ^ 0x6F];
        String ownerId;
        if (nbtTagCompound.hasKey(EntityHorse.I[0x3A ^ 0x61], 0x34 ^ 0x3C)) {
            ownerId = nbtTagCompound.getString(EntityHorse.I[0x67 ^ 0x3B]);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            ownerId = PreYggdrasilConverter.getStringUUIDFromName(nbtTagCompound.getString(EntityHorse.I[0x77 ^ 0x2A]));
        }
        if (ownerId.length() > 0) {
            this.setOwnerId(ownerId);
        }
        final IAttributeInstance attributeInstanceByName = this.getAttributeMap().getAttributeInstanceByName(EntityHorse.I[0x4B ^ 0x15]);
        if (attributeInstanceByName != null) {
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(attributeInstanceByName.getBaseValue() * 0.25);
        }
        if (this.isChested()) {
            final NBTTagList tagList = nbtTagCompound.getTagList(EntityHorse.I[0x18 ^ 0x47], 0x8F ^ 0x85);
            this.initHorseChest();
            int i = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
                final int n = compoundTag.getByte(EntityHorse.I[0xC5 ^ 0xA5]) & 38 + 111 - 48 + 154;
                if (n >= "  ".length() && n < this.horseChest.getSizeInventory()) {
                    this.horseChest.setInventorySlotContents(n, ItemStack.loadItemStackFromNBT(compoundTag));
                }
                ++i;
            }
        }
        if (nbtTagCompound.hasKey(EntityHorse.I[0x5F ^ 0x3E], 0x4B ^ 0x41)) {
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(EntityHorse.I[0xD5 ^ 0xB7]));
            if (loadItemStackFromNBT != null && isArmorItem(loadItemStackFromNBT.getItem())) {
                this.horseChest.setInventorySlotContents(" ".length(), loadItemStackFromNBT);
            }
        }
        if (nbtTagCompound.hasKey(EntityHorse.I[0xEF ^ 0x8C], 0x55 ^ 0x5F)) {
            final ItemStack loadItemStackFromNBT2 = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(EntityHorse.I[0x10 ^ 0x74]));
            if (loadItemStackFromNBT2 != null && loadItemStackFromNBT2.getItem() == Items.saddle) {
                this.horseChest.setInventorySlotContents("".length(), loadItemStackFromNBT2);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
        }
        else if (nbtTagCompound.getBoolean(EntityHorse.I[0x48 ^ 0x2D])) {
            this.horseChest.setInventorySlotContents("".length(), new ItemStack(Items.saddle));
        }
        this.updateHorseSlots();
    }
    
    private void makeHorseRear() {
        if (!this.worldObj.isRemote) {
            this.jumpRearingCounter = " ".length();
            this.setRearing(" ".length() != 0);
        }
    }
    
    public String[] getVariantTexturePaths() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }
        return this.horseTexturesArray;
    }
    
    public boolean isBreeding() {
        return this.getHorseWatchableBoolean(0xD0 ^ 0xC0);
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.spawn_egg) {
            return super.interact(entityPlayer);
        }
        if (!this.isTame() && this.isUndead()) {
            return "".length() != 0;
        }
        if (this.isTame() && this.isAdultHorse() && entityPlayer.isSneaking()) {
            this.openGUI(entityPlayer);
            return " ".length() != 0;
        }
        if (this.func_110253_bW() && this.riddenByEntity != null) {
            return super.interact(entityPlayer);
        }
        if (currentItem != null) {
            int n = "".length();
            if (this.canWearArmor()) {
                int n2 = -" ".length();
                if (currentItem.getItem() == Items.iron_horse_armor) {
                    n2 = " ".length();
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else if (currentItem.getItem() == Items.golden_horse_armor) {
                    n2 = "  ".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else if (currentItem.getItem() == Items.diamond_horse_armor) {
                    n2 = "   ".length();
                }
                if (n2 >= 0) {
                    if (!this.isTame()) {
                        this.makeHorseRearWithSound();
                        return " ".length() != 0;
                    }
                    this.openGUI(entityPlayer);
                    return " ".length() != 0;
                }
            }
            if (n == 0 && !this.isUndead()) {
                float n3 = 0.0f;
                int length = "".length();
                int n4 = "".length();
                if (currentItem.getItem() == Items.wheat) {
                    n3 = 2.0f;
                    length = (0x4A ^ 0x5E);
                    n4 = "   ".length();
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
                else if (currentItem.getItem() == Items.sugar) {
                    n3 = 1.0f;
                    length = (0x45 ^ 0x5B);
                    n4 = "   ".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (Block.getBlockFromItem(currentItem.getItem()) == Blocks.hay_block) {
                    n3 = 20.0f;
                    length = 153 + 132 - 161 + 56;
                    "".length();
                    if (0 == -1) {
                        throw null;
                    }
                }
                else if (currentItem.getItem() == Items.apple) {
                    n3 = 3.0f;
                    length = (0x82 ^ 0xBE);
                    n4 = "   ".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else if (currentItem.getItem() == Items.golden_carrot) {
                    n3 = 4.0f;
                    length = (0xBE ^ 0x82);
                    n4 = (0xA5 ^ 0xA0);
                    if (this.isTame() && this.getGrowingAge() == 0) {
                        n = " ".length();
                        this.setInLove(entityPlayer);
                        "".length();
                        if (2 == 0) {
                            throw null;
                        }
                    }
                }
                else if (currentItem.getItem() == Items.golden_apple) {
                    n3 = 10.0f;
                    length = 151 + 133 - 209 + 165;
                    n4 = (0x5C ^ 0x56);
                    if (this.isTame() && this.getGrowingAge() == 0) {
                        n = " ".length();
                        this.setInLove(entityPlayer);
                    }
                }
                if (this.getHealth() < this.getMaxHealth() && n3 > 0.0f) {
                    this.heal(n3);
                    n = " ".length();
                }
                if (!this.isAdultHorse() && length > 0) {
                    this.addGrowth(length);
                    n = " ".length();
                }
                if (n4 > 0 && (n != 0 || !this.isTame()) && n4 < this.getMaxTemper()) {
                    n = " ".length();
                    this.increaseTemper(n4);
                }
                if (n != 0) {
                    this.func_110266_cB();
                }
            }
            if (!this.isTame() && n == 0) {
                if (currentItem != null && currentItem.interactWithEntity(entityPlayer, this)) {
                    return " ".length() != 0;
                }
                this.makeHorseRearWithSound();
                return " ".length() != 0;
            }
            else {
                if (n == 0 && this.canCarryChest() && !this.isChested() && currentItem.getItem() == Item.getItemFromBlock(Blocks.chest)) {
                    this.setChested(" ".length() != 0);
                    this.playSound(EntityHorse.I[0xEF ^ 0xAC], 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                    n = " ".length();
                    this.initHorseChest();
                }
                if (n == 0 && this.func_110253_bW() && !this.isHorseSaddled() && currentItem.getItem() == Items.saddle) {
                    this.openGUI(entityPlayer);
                    return " ".length() != 0;
                }
                if (n != 0) {
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = currentItem;
                        if ((itemStack.stackSize -= " ".length()) == 0) {
                            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                        }
                    }
                    return " ".length() != 0;
                }
            }
        }
        if (!this.func_110253_bW() || this.riddenByEntity != null) {
            return super.interact(entityPlayer);
        }
        if (currentItem != null && currentItem.interactWithEntity(entityPlayer, this)) {
            return " ".length() != 0;
        }
        this.mountTo(entityPlayer);
        return " ".length() != 0;
    }
    
    public boolean getHasReproduced() {
        return this.hasReproduced;
    }
    
    @Override
    public void updateRiderPosition() {
        super.updateRiderPosition();
        if (this.prevRearingAmount > 0.0f) {
            final float sin = MathHelper.sin(this.renderYawOffset * 3.1415927f / 180.0f);
            final float cos = MathHelper.cos(this.renderYawOffset * 3.1415927f / 180.0f);
            final float n = 0.7f * this.prevRearingAmount;
            this.riddenByEntity.setPosition(this.posX + n * sin, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + 0.15f * this.prevRearingAmount, this.posZ - n * cos);
            if (this.riddenByEntity instanceof EntityLivingBase) {
                ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
            }
        }
    }
    
    public void setChested(final boolean b) {
        this.setHorseWatchableBoolean(0x71 ^ 0x79, b);
    }
    
    @Override
    protected boolean isMovementBlocked() {
        int n;
        if (this.riddenByEntity != null && this.isHorseSaddled()) {
            n = " ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (!this.isEatingHaystack() && !this.isRearing()) {
            n = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public double getHorseJumpStrength() {
        return this.getEntityAttribute(EntityHorse.horseJumpStrength).getAttributeValue();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote && this.dataWatcher.hasObjectChanged()) {
            this.dataWatcher.func_111144_e();
            this.resetTexturePrefix();
        }
        if (this.openMouthCounter > 0 && (this.openMouthCounter += " ".length()) > (0x97 ^ 0x89)) {
            this.openMouthCounter = "".length();
            this.setHorseWatchableBoolean(124 + 52 - 63 + 15, "".length() != 0);
        }
        if (!this.worldObj.isRemote && this.jumpRearingCounter > 0 && (this.jumpRearingCounter += " ".length()) > (0xF ^ 0x1B)) {
            this.jumpRearingCounter = "".length();
            this.setRearing("".length() != 0);
        }
        if (this.field_110278_bp > 0 && (this.field_110278_bp += " ".length()) > (0xAB ^ 0xA3)) {
            this.field_110278_bp = "".length();
        }
        if (this.field_110279_bq > 0) {
            this.field_110279_bq += " ".length();
            if (this.field_110279_bq > 21 + 15 + 104 + 160) {
                this.field_110279_bq = "".length();
            }
        }
        this.prevHeadLean = this.headLean;
        if (this.isEatingHaystack()) {
            this.headLean += (1.0f - this.headLean) * 0.4f + 0.05f;
            if (this.headLean > 1.0f) {
                this.headLean = 1.0f;
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
        }
        else {
            this.headLean += (0.0f - this.headLean) * 0.4f - 0.05f;
            if (this.headLean < 0.0f) {
                this.headLean = 0.0f;
            }
        }
        this.prevRearingAmount = this.rearingAmount;
        if (this.isRearing()) {
            final float n = 0.0f;
            this.headLean = n;
            this.prevHeadLean = n;
            this.rearingAmount += (1.0f - this.rearingAmount) * 0.4f + 0.05f;
            if (this.rearingAmount > 1.0f) {
                this.rearingAmount = 1.0f;
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
        }
        else {
            this.field_110294_bI = ("".length() != 0);
            this.rearingAmount += (0.8f * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6f - 0.05f;
            if (this.rearingAmount < 0.0f) {
                this.rearingAmount = 0.0f;
            }
        }
        this.prevMouthOpenness = this.mouthOpenness;
        if (this.getHorseWatchableBoolean(113 + 115 - 222 + 122)) {
            this.mouthOpenness += (1.0f - this.mouthOpenness) * 0.7f + 0.05f;
            if (this.mouthOpenness > 1.0f) {
                this.mouthOpenness = 1.0f;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else {
            this.mouthOpenness += (0.0f - this.mouthOpenness) * 0.7f - 0.05f;
            if (this.mouthOpenness < 0.0f) {
                this.mouthOpenness = 0.0f;
            }
        }
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        final EntityHorse entityHorse = (EntityHorse)entityAgeable;
        final EntityHorse entityHorse2 = new EntityHorse(this.worldObj);
        final int horseType = this.getHorseType();
        final int horseType2 = entityHorse.getHorseType();
        int horseType3 = "".length();
        if (horseType == horseType2) {
            horseType3 = horseType;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if ((horseType == 0 && horseType2 == " ".length()) || (horseType == " ".length() && horseType2 == 0)) {
            horseType3 = "  ".length();
        }
        if (horseType3 == 0) {
            final int nextInt = this.rand.nextInt(0x3B ^ 0x32);
            int nextInt2;
            if (nextInt < (0xBF ^ 0xBB)) {
                nextInt2 = (this.getHorseVariant() & 253 + 157 - 185 + 30);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (nextInt < (0x1E ^ 0x16)) {
                nextInt2 = (entityHorse.getHorseVariant() & 218 + 206 - 283 + 114);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else {
                nextInt2 = this.rand.nextInt(0xAD ^ 0xAA);
            }
            final int nextInt3 = this.rand.nextInt(0x9B ^ 0x9E);
            int horseVariant;
            if (nextInt3 < "  ".length()) {
                horseVariant = (nextInt2 | (this.getHorseVariant() & 5040 + 3030 + 14831 + 42379));
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else if (nextInt3 < (0x34 ^ 0x30)) {
                horseVariant = (nextInt2 | (entityHorse.getHorseVariant() & 12371 + 45825 - 47376 + 54460));
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else {
                horseVariant = (nextInt2 | (this.rand.nextInt(0xA8 ^ 0xAD) << (0xC9 ^ 0xC1) & 42839 + 38323 - 28012 + 12130));
            }
            entityHorse2.setHorseVariant(horseVariant);
        }
        entityHorse2.setHorseType(horseType3);
        entityHorse2.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + entityAgeable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + this.getModifiedMaxHealth()) / 3.0);
        entityHorse2.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue((this.getEntityAttribute(EntityHorse.horseJumpStrength).getBaseValue() + entityAgeable.getEntityAttribute(EntityHorse.horseJumpStrength).getBaseValue() + this.getModifiedJumpStrength()) / 3.0);
        entityHorse2.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + entityAgeable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.getModifiedMovementSpeed()) / 3.0);
        return entityHorse2;
    }
    
    private void func_110210_cH() {
        this.field_110278_bp = " ".length();
    }
    
    @Override
    public boolean allowLeashing() {
        if (!this.isUndead() && super.allowLeashing()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void setHorseWatchableBoolean(final int n, final boolean b) {
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(0x2E ^ 0x3E);
        if (b) {
            this.dataWatcher.updateObject(0xA1 ^ 0xB1, watchableObjectInt | n);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x5A ^ 0x4A, watchableObjectInt & (n ^ -" ".length()));
        }
    }
    
    @Override
    public void onInventoryChanged(final InventoryBasic inventoryBasic) {
        final int horseArmorIndexSynced = this.getHorseArmorIndexSynced();
        final boolean horseSaddled = this.isHorseSaddled();
        this.updateHorseSlots();
        if (this.ticksExisted > (0x71 ^ 0x65)) {
            if (horseArmorIndexSynced == 0 && horseArmorIndexSynced != this.getHorseArmorIndexSynced()) {
                this.playSound(EntityHorse.I[0x9C ^ 0xB5], 0.5f, 1.0f);
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            else if (horseArmorIndexSynced != this.getHorseArmorIndexSynced()) {
                this.playSound(EntityHorse.I[0xB3 ^ 0x99], 0.5f, 1.0f);
            }
            if (!horseSaddled && this.isHorseSaddled()) {
                this.playSound(EntityHorse.I[0x63 ^ 0x48], 0.5f, 1.0f);
            }
        }
    }
    
    private void setHorseTexturePaths() {
        this.texturePrefix = EntityHorse.I[0x10 ^ 0x2F];
        this.horseTexturesArray["".length()] = null;
        this.horseTexturesArray[" ".length()] = null;
        this.horseTexturesArray["  ".length()] = null;
        final int horseType = this.getHorseType();
        final int horseVariant = this.getHorseVariant();
        if (horseType == 0) {
            final int n = horseVariant & 106 + 179 - 178 + 148;
            final int n2 = (horseVariant & 34550 + 57233 - 64622 + 38119) >> (0xBF ^ 0xB7);
            if (n >= EntityHorse.horseTextures.length) {
                this.field_175508_bO = ("".length() != 0);
                return;
            }
            this.horseTexturesArray["".length()] = EntityHorse.horseTextures[n];
            this.texturePrefix = String.valueOf(this.texturePrefix) + EntityHorse.HORSE_TEXTURES_ABBR[n];
            if (n2 >= EntityHorse.horseMarkingTextures.length) {
                this.field_175508_bO = ("".length() != 0);
                return;
            }
            this.horseTexturesArray[" ".length()] = EntityHorse.horseMarkingTextures[n2];
            this.texturePrefix = String.valueOf(this.texturePrefix) + EntityHorse.HORSE_MARKING_TEXTURES_ABBR[n2];
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            this.horseTexturesArray["".length()] = EntityHorse.I[0x17 ^ 0x57];
            this.texturePrefix = String.valueOf(this.texturePrefix) + EntityHorse.I[0x77 ^ 0x36] + horseType + EntityHorse.I[0xE7 ^ 0xA5];
        }
        final int horseArmorIndexSynced = this.getHorseArmorIndexSynced();
        if (horseArmorIndexSynced >= EntityHorse.horseArmorTextures.length) {
            this.field_175508_bO = ("".length() != 0);
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            this.horseTexturesArray["  ".length()] = EntityHorse.horseArmorTextures[horseArmorIndexSynced];
            this.texturePrefix = String.valueOf(this.texturePrefix) + EntityHorse.HORSE_ARMOR_TEXTURES_ABBR[horseArmorIndexSynced];
            this.field_175508_bO = (" ".length() != 0);
        }
    }
    
    public static boolean isArmorItem(final Item item) {
        if (item != Items.iron_horse_armor && item != Items.golden_horse_armor && item != Items.diamond_horse_armor) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x2B ^ 0x4D])["".length()] = I("\u001a: \u0003\f\\?'\u001d\u0019!! \u0015\u0007\u0015!:", "rURpi");
        EntityHorse.I[" ".length()] = I("?::\u0000Q&;%\u0015\u001f\u0012;?", "uOWpq");
        EntityHorse.I["  ".length()] = I("\u001724$\u0007\u00112?\u007f\u0017\r#%$\u000bL?#\"\u0001\u0006x-\"\u001f\f%c8\u001d\u0011$)\u000f\u0013\u0011:#\"-\n%#>\\\u00139+", "cWLPr");
        EntityHorse.I["   ".length()] = I("\u00076070\u00016;l \u001d'!7<\\;'16\u0016|)1(\u001c!g+*\u0001 -\u001c$\u0001>'1\u001a\u0014<$'k\u0003=/", "sSHCE");
        EntityHorse.I[0xB5 ^ 0xB1] = I("\u0007\u000b6!4\u0001\u000b=z$\u001d\u001a'!8\\\u0006!'2\u0016A/',\u001c\u001ca=.\u0001\u001d+\n \u0001\u0003!'\u001e\u0017\u0007/8.\u001d\n`%/\u0014", "snNUA");
        EntityHorse.I[0xC1 ^ 0xC4] = I("", "qMwRB");
        EntityHorse.I[0xC3 ^ 0xC5] = I("80\b", "UUgDz");
        EntityHorse.I[0x38 ^ 0x3F] = I("\u000e(\u0001", "iGnCR");
        EntityHorse.I[0xB8 ^ 0xB0] = I(" %\u001c", "DLsia");
        EntityHorse.I[0x4 ^ 0xD] = I("\u001a=\f\u0019-\u001c=\u0007B=\u0000,\u001d\u0019!A0\u001b\u001f+\u000bw\u001c\u0002*\u001d=+\u001a0\u0007,\u0011C(\u0000?", "nXtmX");
        EntityHorse.I[0x88 ^ 0x82] = I("\u0006(\t;-\u0000(\u0002`=\u001c9\u0018;!]%\u001e=+\u0017b\u0019 *\u0001(.,*\u0017,\u001c6v\u0002#\u0016", "rMqOX");
        EntityHorse.I[0x37 ^ 0x3C] = I("=!\u00110?;!\u001ak/'0\u000003f,\u000669,k\u0001+8:!6'\",7\u001d*?=j\u0019*-", "IDiDJ");
        EntityHorse.I[0xB2 ^ 0xBE] = I("\u0019'\u001f&\u0001\u001f'\u0014}\u0011\u00036\u000e&\rB*\b \u0007\bm\u000f=\u0006\u001e'80\u0006\u00025\t|\u0004\u0003%", "mBgRt");
        EntityHorse.I[0x29 ^ 0x24] = I("\u0019\u001f6\r8\u001f\u001f=V(\u0003\u000e'\r4B\u0012!\u000b>\bU&\u0016?\u001e\u001f\u0011\u001b!\f\u0019%W=\u0003\u001d", "mzNyM");
        EntityHorse.I[0xB4 ^ 0xBA] = I("\u0003\n\u0012$1\u0005\n\u0019\u007f!\u0019\u001b\u0003$=X\u0007\u0005\"7\u0012@\u0002?6\u0004\n576\u0016\u0016D *\u0010", "wojPD");
        EntityHorse.I[0x7A ^ 0x75] = I("\u0000\u0004.\r$\u0006\u0004%V4\u001a\u0015?\r([\t9\u000b\"\u0011N>\u0016#\u0007\u0004\t\u001d0\u0006\n4\u000b>\u0003\u000fx\t?\u0013", "taVyQ");
        EntityHorse.I[0x38 ^ 0x28] = I("\u001c\u001f\u001e", "thvoT");
        EntityHorse.I[0x32 ^ 0x23] = I(")\u0002#", "AaQRk");
        EntityHorse.I[0x0 ^ 0x12] = I("\t\u0000\u0002", "acjyc");
        EntityHorse.I[0x39 ^ 0x2A] = I("\u0012\u001b\u0000", "zyrYH");
        EntityHorse.I[0xA0 ^ 0xB4] = I("-.\u000b", "ELgWA");
        EntityHorse.I[0x9A ^ 0x8F] = I("\n\u000b\u001d", "bloUO");
        EntityHorse.I[0x48 ^ 0x5E] = I("9')", "QCKbT");
        EntityHorse.I[0xBD ^ 0xAA] = I(">\"\u0014\u0017!8\"\u001fL1$3\u0005\u0017-e/\u0003\u0011'/h\u0004\f&9\"3\u000e58,\u0005\r39\u0018\u001b\u000b=>\"B\u0013:-", "JGlcT");
        EntityHorse.I[0x56 ^ 0x4E] = I("\r\u0017\u0015\u0003=\u000b\u0017\u001eX-\u0017\u0006\u0004\u00031V\u001a\u0002\u0005;\u001c]\u0005\u0018:\n\u00172\u001a)\u000b\u0019\u0004\u0019/\n-\u001a\u001f!\r\u0017\u000b\u001e-\u0015\u0016C\u0007&\u001e", "yrmwH");
        EntityHorse.I[0x2B ^ 0x32] = I(" ?6=\u0014&?=f\u0004:.'=\u0018{2!;\u00121u&&\u0013'?\u0011$\u0000&1''\u0006'\u00059!\b ?*&\u0015't>'\u0006", "TZNIa");
        EntityHorse.I[0x7E ^ 0x64] = I("\u0002\u00017\u0017\u0000\u0004\u0001<L\u0010\u0018\u0010&\u0017\fY\f \u0011\u0006\u0013K'\f\u0007\u0005\u0001\u0010\u000e\u0014\u0004\u000f&\r\u0012\u0005;-\u000f\u0014\u0015\u000f+\f\u0001\u0005J?\r\u0012", "vdOcu");
        EntityHorse.I[0xBF ^ 0xA4] = I("", "Yzucn");
        EntityHorse.I[0x39 ^ 0x25] = I("\u0000*\f", "wESKZ");
        EntityHorse.I[0x83 ^ 0x9E] = I("=:\u0000", "JWoYV");
        EntityHorse.I[0x33 ^ 0x2D] = I("\u0001\u0005\u0015", "vazYj");
        EntityHorse.I[0x1C ^ 0x3] = I("2\u0002.", "PfAPo");
        EntityHorse.I[0x9C ^ 0xBC] = I("", "mRPVE");
        EntityHorse.I[0x76 ^ 0x57] = I("\u0001\u001b=>\"\u001d[!8$\u0017\u0010g97\t\u0010", "duIWV");
        EntityHorse.I[0x5D ^ 0x7F] = I("1\u001f;\u0001\u0011-_+\u0007\u000b?\u00146F\u000b5\u001c*", "TqOhe");
        EntityHorse.I[0x42 ^ 0x61] = I(" \u001c\u001b0\u001e<\\\u0002,\u0006 \\\u00018\u0007 ", "EroYj");
        EntityHorse.I[0x58 ^ 0x7C] = I("\u0002\u001e\f(\u0006\u001e^\u0002.\u001f\u0005\u0019\u001d)\u001d\u0015\u0003\u001do\u001c\u0006\u001d\u001d", "gpxAr");
        EntityHorse.I[0x6B ^ 0x4E] = I("\u0014#\u0013$\u0005\bc\u0014&\u0014\u001d(\u0013\"\u001f\u0019\"\u0015>\u0014_#\u0006 \u0014", "qMgMq");
        EntityHorse.I[0x4B ^ 0x6D] = I("2\u000b&&(0", "WjROF");
        EntityHorse.I[0x6C ^ 0x4B] = I(",:\r]\u0018.'\u001c\u0016^-4\u0001\u0017", "AUosp");
        EntityHorse.I[0x2E ^ 0x6] = I("2\u0004\u0011!\t9\u0003\u0006!\u0018", "zkcRl");
        EntityHorse.I[0x67 ^ 0x4E] = I("\u0006=4v\u000b\u0004 %=M\n ;7\u0011", "kRVXc");
        EntityHorse.I[0x62 ^ 0x48] = I("%?\u0001H)'\"\u0010\u0003o)\"\u000e\t3", "HPcfA");
        EntityHorse.I[0x3E ^ 0x15] = I("&\u0002)[\t$\u001f8\u0010O'\b*\u0001\t.\u001f", "KmKua");
        EntityHorse.I[0x3B ^ 0x17] = I("5;\fH\u000e7&\u001d\u0003H\";\u0003\u0004\u000f=z\n\u0003\u0007,<", "XTnff");
        EntityHorse.I[0x70 ^ 0x5D] = I("\b=\u000fZ,\n \u001e\u0011j\u00169\b\u0018!\u0011=\u0003Z \u00003\u0019\u001c", "eRmtD");
        EntityHorse.I[0x20 ^ 0xE] = I("'>5D\u001a%#$\u000f\\.46\u001e\u001a", "JQWjr");
        EntityHorse.I[0x9D ^ 0xB2] = I("\u000e\u001d4\\1\f\u0000%\u0017w\u0007\u001d8\u0019<\u001a\\2\u00178\u0017\u001a", "crVrY");
        EntityHorse.I[0x65 ^ 0x55] = I("\u001d\u001f'E+\u001f\u00026\u000em\n\u001f(\t*\u0015^-\u00027", "ppEkC");
        EntityHorse.I[0x10 ^ 0x21] = I("/\u0019:I--\u0004+\u0002k1\u001d=\u000b 6\u00196I-+\u0002", "BvXgE");
        EntityHorse.I[0xAB ^ 0x99] = I("\u001a(\u0010d\u0003\u00185\u0001/E\u001f.\u0006", "wGrJk");
        EntityHorse.I[0x9A ^ 0xA9] = I(")\u0016\ra,+\u000b\u001c*j \u0016\u0001$!=W\u0007&0", "DyoOD");
        EntityHorse.I[0x82 ^ 0xB6] = I("\b>*g>\n#;,x\u001f>%+?\u0000\u007f!-:\u0000", "eQHIV");
        EntityHorse.I[0x99 ^ 0xAC] = I("'\u001b\u0010a9%\u0006\u0001*\u007f9\u001f\u0017#4>\u001b\u001ca8.\u0018\u0017", "JtrOQ");
        EntityHorse.I[0xAD ^ 0x9B] = I("+,%i.)14\"h/'+\"", "FCGGF");
        EntityHorse.I[0x28 ^ 0x1F] = I("\"\u0015\u0013J0 \b\u0002\u0001v+\u0015\u001f\u000f=6T\u0018\u00004*", "OzqdX");
        EntityHorse.I[0x67 ^ 0x5F] = I("*\u0018\u0018V+(\u0005\t\u001dm&\u0019\u001d\n:", "GwzxC");
        EntityHorse.I[0x14 ^ 0x2D] = I("!\u0002*d\u0018#\u001f;/^(\u0002&!\u00155C)$\u0017>\u0014", "LmHJp");
        EntityHorse.I[0x6F ^ 0x55] = I("(++y *6:2f\"%%;'5", "EDIWH");
        EntityHorse.I[0x5 ^ 0x3E] = I("\u0000\u0002\fe\u0019\u0002\u001f\u001d._\u000f\u001f\u000b*\u0005\u0005\b", "mmnKq");
        EntityHorse.I[0x26 ^ 0x1A] = I("\u0000\u001e\u001aW&\u0002\u0003\u000b\u001c`\u001a\u001e\u0017\u001d", "mqxyN");
        EntityHorse.I[0x39 ^ 0x4] = I("(\n#H\u001d*\u00172\u0003[2\n.\u0002", "EeAfu");
        EntityHorse.I[0x81 ^ 0xBF] = I("\u0006+6l\r\u00046''K\u0018+26", "kDTBe");
        EntityHorse.I[0x40 ^ 0x7F] = I("\f\u0002\u00060<K", "dmtCY");
        EntityHorse.I[0xC9 ^ 0x89] = I("", "JIYhb");
        EntityHorse.I[0x4B ^ 0xA] = I("3", "lhRks");
        EntityHorse.I[0x23 ^ 0x61] = I("/", "pEldJ");
        EntityHorse.I[0x3A ^ 0x79] = I("88!H.=> \r(;'/\t=", "UWCfM");
        EntityHorse.I[0x52 ^ 0x16] = I("\u001c\u0003\u0014G\u0012\u001e\u001e\u0005\fT\u001b\u0019\u001b\u0019", "qlviz");
        EntityHorse.I[0x2E ^ 0x6B] = I("\u00116$\u0011\f3\u001f1\u0001\u0011 63\u0013", "TWPxb");
        EntityHorse.I[0x6E ^ 0x28] = I("&\"?'?\u0000.\u0012;9\u0016/", "eJZTK");
        EntityHorse.I[0x2 ^ 0x45] = I(":\u0000)\u0011'\u0002\u00135'7\u0011\u0004>", "raZCB");
        EntityHorse.I[0x7D ^ 0x35] = I("!4\u0014\u001e", "cFqzN");
        EntityHorse.I[0x8 ^ 0x41] = I("21\u00147", "fHdRN");
        EntityHorse.I[0x37 ^ 0x7D] = I("/;\u001c$\b\u0017.", "yZnMi");
        EntityHorse.I[0x8E ^ 0xC5] = I(" 5 \u0001\u000b\u0006", "tPMqn");
        EntityHorse.I[0x4D ^ 0x1] = I("6\u0019\u000e!", "bxcDZ");
        EntityHorse.I[0x4B ^ 0x6] = I("\u000e-;\u0014\b\u0014\u000f\u001c5", "AZUqz");
        EntityHorse.I[0x19 ^ 0x57] = I("\u0012(!\u0011", "ADNeD");
        EntityHorse.I[0x1E ^ 0x51] = I("\u001f$\u0013,\u001c", "VPvAo");
        EntityHorse.I[0x3 ^ 0x53] = I("; \u0018\u0016#3&\u0010\u0014", "zRuyQ");
        EntityHorse.I[0x24 ^ 0x75] = I("7#\u0006\t\u0001\u0001\u000b\u0016\b\u0000", "dBbmm");
        EntityHorse.I[0x63 ^ 0x31] = I("\u0001\u00127-)#;\"=40\u0012 /", "DsCDG");
        EntityHorse.I[0x6D ^ 0x3E] = I("\f4\u000b\r", "NFnij");
        EntityHorse.I[0xE ^ 0x5A] = I("\u00162 $80>\r8>&?", "UZEWL");
        EntityHorse.I[0x3B ^ 0x6E] = I("=+\u00154\t\u00058\t\u0002\u0019\u0016/\u0002", "uJffl");
        EntityHorse.I[0xD8 ^ 0x8E] = I("\u001a\u0014\u001e\t", "NmnlN");
        EntityHorse.I[0xE3 ^ 0xB4] = I("\u00009\u000b\u0002\u00128,", "VXyks");
        EntityHorse.I[0x14 ^ 0x4C] = I(";\u001c\u001d\u0013\u0006\u001d", "oypcc");
        EntityHorse.I[0x3D ^ 0x64] = I("%\u0004%\u0015", "qeHpa");
        EntityHorse.I[0x3A ^ 0x60] = I("", "cJJNv");
        EntityHorse.I[0xE9 ^ 0xB2] = I(")0\u001b\u001013\u0012<1", "fGuuC");
        EntityHorse.I[0xE6 ^ 0xBA] = I("\u0005-,\u00044\u001f\u000f\u000b%", "JZBaF");
        EntityHorse.I[0xC8 ^ 0x95] = I("\u001f\u0011#(\u0013", "PfMMa");
        EntityHorse.I[0x1E ^ 0x40] = I("*\u0018/)\u000e", "yhJLj");
        EntityHorse.I[0xE7 ^ 0xB8] = I("$\u001d?> ", "miZSS");
        EntityHorse.I[0x1 ^ 0x61] = I("6\u000e\t\u001a", "ebfnw");
        EntityHorse.I[0x3F ^ 0x5E] = I("(6'\n1 0/\b", "iDJeC");
        EntityHorse.I[0x6D ^ 0xF] = I("\u0002?''\u0014\n9/%", "CMJHf");
        EntityHorse.I[0xDB ^ 0xB8] = I("\u001d&=\u0014\u001b+\u000e-\u0015\u001a", "NGYpw");
        EntityHorse.I[0x19 ^ 0x7D] = I("+\r\u001c&\r\u001d%\f'\f", "xlxBa");
        EntityHorse.I[0xEA ^ 0x8F] = I("*\t\u0010\u0017\u0003\u001c", "yhtso");
    }
    
    private double getModifiedMovementSpeed() {
        return (0.44999998807907104 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3) * 0.25;
    }
    
    public int getHorseArmorIndexSynced() {
        return this.dataWatcher.getWatchableObjectInt(0x3C ^ 0x2A);
    }
    
    @Override
    public float getEyeHeight() {
        return this.height;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x25 ^ 0x22)) {
            this.spawnHorseParticles(" ".length() != 0);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else if (b == (0x67 ^ 0x61)) {
            this.spawnHorseParticles("".length() != 0);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    private boolean getHorseWatchableBoolean(final int n) {
        if ((this.dataWatcher.getWatchableObjectInt(0x68 ^ 0x78) & n) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        this.prepareChunkForSpawn();
        return super.getCanSpawnHere();
    }
    
    protected String getAngrySoundName() {
        this.openHorseMouth();
        this.makeHorseRear();
        final int horseType = this.getHorseType();
        String s;
        if (horseType != "   ".length() && horseType != (0x14 ^ 0x10)) {
            if (horseType != " ".length() && horseType != "  ".length()) {
                s = EntityHorse.I[0x22 ^ 0x1A];
                "".length();
                if (2 == 4) {
                    throw null;
                }
            }
            else {
                s = EntityHorse.I[0x13 ^ 0x2A];
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
        }
        else {
            s = null;
        }
        return s;
    }
    
    private void resetTexturePrefix() {
        this.texturePrefix = null;
    }
    
    protected EntityHorse getClosestHorse(final Entity entity, final double n) {
        double n2 = Double.MAX_VALUE;
        Entity entity2 = null;
        final Iterator<Entity> iterator = this.worldObj.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(n, n, n), EntityHorse.horseBreedingSelector).iterator();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Entity entity3 = iterator.next();
            final double distanceSq = entity3.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (distanceSq < n2) {
                entity2 = entity3;
                n2 = distanceSq;
            }
        }
        return (EntityHorse)entity2;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x4B ^ 0x5B, "".length());
        this.dataWatcher.addObject(0x25 ^ 0x36, (byte)"".length());
        this.dataWatcher.addObject(0x6A ^ 0x7E, "".length());
        this.dataWatcher.addObject(0xD6 ^ 0xC3, String.valueOf(EntityHorse.I[0xB3 ^ 0x93]));
        this.dataWatcher.addObject(0xAF ^ 0xB9, "".length());
    }
    
    @Override
    public int getTotalArmorValue() {
        return EntityHorse.armorValues[this.getHorseArmorIndexSynced()];
    }
    
    public int increaseTemper(final int n) {
        final int clamp_int = MathHelper.clamp_int(this.getTemper() + n, "".length(), this.getMaxTemper());
        this.setTemper(clamp_int);
        return clamp_int;
    }
    
    public void setHasReproduced(final boolean hasReproduced) {
        this.hasReproduced = hasReproduced;
    }
    
    @Override
    public int getTalkInterval() {
        return 319 + 267 - 532 + 346;
    }
    
    private void initHorseChest() {
        final AnimalChest horseChest = this.horseChest;
        (this.horseChest = new AnimalChest(EntityHorse.I[0x4D ^ 0x65], this.getChestSize())).setCustomName(this.getName());
        if (horseChest != null) {
            horseChest.func_110132_b(this);
            final int min = Math.min(horseChest.getSizeInventory(), this.horseChest.getSizeInventory());
            int i = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i < min) {
                final ItemStack stackInSlot = horseChest.getStackInSlot(i);
                if (stackInSlot != null) {
                    this.horseChest.setInventorySlotContents(i, stackInSlot.copy());
                }
                ++i;
            }
        }
        this.horseChest.func_110134_a(this);
        this.updateHorseSlots();
    }
    
    private void openHorseMouth() {
        if (!this.worldObj.isRemote) {
            this.openMouthCounter = " ".length();
            this.setHorseWatchableBoolean(44 + 48 - 13 + 49, " ".length() != 0);
        }
    }
    
    private boolean canMate() {
        if (this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && !this.isSterile() && this.getHealth() >= this.getMaxHealth() && this.isInLove()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(EntityHorse.horseJumpStrength);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552);
    }
    
    @Override
    protected String getDeathSound() {
        this.openHorseMouth();
        final int horseType = this.getHorseType();
        String s;
        if (horseType == "   ".length()) {
            s = EntityHorse.I[0x2 ^ 0x2E];
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (horseType == (0x80 ^ 0x84)) {
            s = EntityHorse.I[0x9F ^ 0xB2];
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (horseType != " ".length() && horseType != "  ".length()) {
            s = EntityHorse.I[0x67 ^ 0x49];
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            s = EntityHorse.I[0xBE ^ 0x91];
        }
        return s;
    }
    
    public boolean func_110253_bW() {
        return this.isAdultHorse();
    }
    
    private float getModifiedMaxHealth() {
        return 15.0f + this.rand.nextInt(0x82 ^ 0x8A) + this.rand.nextInt(0x17 ^ 0x1E);
    }
    
    public boolean isUndead() {
        final int horseType = this.getHorseType();
        if (horseType != "   ".length() && horseType != (0x27 ^ 0x23)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void setScaleForAge(final boolean b) {
        if (b) {
            this.setScale(this.getHorseSize());
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            this.setScale(1.0f);
        }
    }
    
    @Override
    public boolean isOnLadder() {
        return "".length() != 0;
    }
    
    public float getMouthOpennessAngle(final float n) {
        return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * n;
    }
    
    @Override
    protected void func_142017_o(final float n) {
        if (n > 6.0f && this.isEatingHaystack()) {
            this.setEatingHaystack("".length() != 0);
        }
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 0x1D ^ 0x1B;
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return "".length() != 0;
        }
        if (entityAnimal.getClass() != this.getClass()) {
            return "".length() != 0;
        }
        final EntityHorse entityHorse = (EntityHorse)entityAnimal;
        if (!this.canMate() || !entityHorse.canMate()) {
            return "".length() != 0;
        }
        final int horseType = this.getHorseType();
        final int horseType2 = entityHorse.getHorseType();
        if (horseType != horseType2 && (horseType != 0 || horseType2 != " ".length()) && (horseType != " ".length() || horseType2 != 0)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void setTemper(final int temper) {
        this.temper = temper;
    }
    
    public void setOwnerId(final String s) {
        this.dataWatcher.updateObject(0x21 ^ 0x34, s);
    }
    
    public void setEatingHaystack(final boolean eating) {
        this.setEating(eating);
    }
    
    public float getGrassEatingAmount(final float n) {
        return this.prevHeadLean + (this.headLean - this.prevHeadLean) * n;
    }
    
    public boolean prepareChunkForSpawn() {
        this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), "".length(), MathHelper.floor_double(this.posZ)));
        return " ".length() != 0;
    }
    
    public void dropChestItems() {
        this.dropItemsInChest(this, this.horseChest);
        this.dropChests();
    }
    
    private void updateHorseSlots() {
        if (!this.worldObj.isRemote) {
            int horseSaddled;
            if (this.horseChest.getStackInSlot("".length()) != null) {
                horseSaddled = " ".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                horseSaddled = "".length();
            }
            this.setHorseSaddled(horseSaddled != 0);
            if (this.canWearArmor()) {
                this.setHorseArmorStack(this.horseChest.getStackInSlot(" ".length()));
            }
        }
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.8f;
    }
    
    @Override
    public void fall(final float n, final float n2) {
        if (n > 1.0f) {
            this.playSound(EntityHorse.I[0x7F ^ 0x58], 0.4f, 1.0f);
        }
        final int ceiling_float_int = MathHelper.ceiling_float_int((n * 0.5f - 3.0f) * n2);
        if (ceiling_float_int > 0) {
            this.attackEntityFrom(DamageSource.fall, ceiling_float_int);
            if (this.riddenByEntity != null) {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, ceiling_float_int);
            }
            final Block block = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.2 - this.prevRotationYaw, this.posZ)).getBlock();
            if (block.getMaterial() != Material.air && !this.isSilent()) {
                final Block.SoundType stepSound = block.stepSound;
                this.worldObj.playSoundAtEntity(this, stepSound.getStepSound(), stepSound.getVolume() * 0.5f, stepSound.getFrequency() * 0.75f);
            }
        }
    }
    
    public void setHorseSaddled(final boolean b) {
        this.setHorseWatchableBoolean(0x1C ^ 0x18, b);
    }
    
    public void setHorseArmorStack(final ItemStack itemStack) {
        this.dataWatcher.updateObject(0x2 ^ 0x14, this.getHorseArmorIndex(itemStack));
        this.resetTexturePrefix();
    }
    
    @Override
    public void setEating(final boolean b) {
        this.setHorseWatchableBoolean(0x82 ^ 0xA2, b);
    }
    
    public void setBreeding(final boolean b) {
        this.setHorseWatchableBoolean(0xBB ^ 0xAB, b);
    }
    
    public boolean isSterile() {
        if (!this.isUndead() && this.getHorseType() != "  ".length()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private int getChestSize() {
        final int horseType = this.getHorseType();
        int length;
        if (!this.isChested() || (horseType != " ".length() && horseType != "  ".length())) {
            length = "  ".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            length = (0x7E ^ 0x6F);
        }
        return length;
    }
    
    public void setHorseType(final int n) {
        this.dataWatcher.updateObject(0x40 ^ 0x53, (byte)n);
        this.resetTexturePrefix();
    }
    
    public void setHorseJumping(final boolean horseJumping) {
        this.horseJumping = horseJumping;
    }
    
    private double getModifiedJumpStrength() {
        return 0.4000000059604645 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2;
    }
    
    private int getHorseArmorIndex(final ItemStack itemStack) {
        if (itemStack == null) {
            return "".length();
        }
        final Item item = itemStack.getItem();
        int n;
        if (item == Items.iron_horse_armor) {
            n = " ".length();
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else if (item == Items.golden_horse_armor) {
            n = "  ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else if (item == Items.diamond_horse_armor) {
            n = "   ".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public void dropChests() {
        if (!this.worldObj.isRemote && this.isChested()) {
            this.dropItem(Item.getItemFromBlock(Blocks.chest), " ".length());
            this.setChested("".length() != 0);
        }
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.rand.nextInt(36 + 116 - 5 + 53) == 0) {
            this.func_110210_cH();
        }
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            if (this.rand.nextInt(66 + 631 - 158 + 361) == 0 && this.deathTime == 0) {
                this.heal(1.0f);
            }
            if (!this.isEatingHaystack() && this.riddenByEntity == null && this.rand.nextInt(50 + 138 + 49 + 63) == 0 && this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - " ".length(), MathHelper.floor_double(this.posZ))).getBlock() == Blocks.grass) {
                this.setEatingHaystack(" ".length() != 0);
            }
            if (this.isEatingHaystack() && (this.eatingHaystackCounter += " ".length()) > (0xBA ^ 0x88)) {
                this.eatingHaystackCounter = "".length();
                this.setEatingHaystack("".length() != 0);
            }
            if (this.isBreeding() && !this.isAdultHorse() && !this.isEatingHaystack()) {
                final EntityHorse closestHorse = this.getClosestHorse(this, 16.0);
                if (closestHorse != null && this.getDistanceSqToEntity(closestHorse) > 4.0) {
                    this.navigator.getPathToEntityLiving(closestHorse);
                }
            }
        }
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public int horseType;
        public int horseVariant;
        
        public GroupData(final int horseType, final int horseVariant) {
            this.horseType = horseType;
            this.horseVariant = horseVariant;
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
                if (-1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
