package net.minecraft.entity.item;

import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class EntityArmorStand extends EntityLivingBase
{
    private static final Rotations DEFAULT_LEFTLEG_ROTATION;
    private Rotations rightLegRotation;
    private Rotations bodyRotation;
    private Rotations leftLegRotation;
    private static final Rotations DEFAULT_HEAD_ROTATION;
    private Rotations leftArmRotation;
    private static final Rotations DEFAULT_LEFTARM_ROTATION;
    private static final Rotations DEFAULT_RIGHTLEG_ROTATION;
    private static final Rotations DEFAULT_RIGHTARM_ROTATION;
    private long punchCooldown;
    private final ItemStack[] contents;
    private static final Rotations DEFAULT_BODY_ROTATION;
    private Rotations rightArmRotation;
    private boolean canInteract;
    private static final String[] I;
    private Rotations headRotation;
    private boolean field_181028_bj;
    private int disabledSlots;
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (i < this.contents.length) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            if (this.contents[i] != null) {
                this.contents[i].writeToNBT(nbtTagCompound2);
            }
            list.appendTag(nbtTagCompound2);
            ++i;
        }
        nbtTagCompound.setTag(EntityArmorStand.I["".length()], list);
        if (this.getAlwaysRenderNameTag() && (this.getCustomNameTag() == null || this.getCustomNameTag().length() == 0)) {
            nbtTagCompound.setBoolean(EntityArmorStand.I[" ".length()], this.getAlwaysRenderNameTag());
        }
        nbtTagCompound.setBoolean(EntityArmorStand.I["  ".length()], this.isInvisible());
        nbtTagCompound.setBoolean(EntityArmorStand.I["   ".length()], this.isSmall());
        nbtTagCompound.setBoolean(EntityArmorStand.I[0x60 ^ 0x64], this.getShowArms());
        nbtTagCompound.setInteger(EntityArmorStand.I[0x22 ^ 0x27], this.disabledSlots);
        nbtTagCompound.setBoolean(EntityArmorStand.I[0x84 ^ 0x82], this.hasNoGravity());
        nbtTagCompound.setBoolean(EntityArmorStand.I[0x98 ^ 0x9F], this.hasNoBasePlate());
        if (this.func_181026_s()) {
            nbtTagCompound.setBoolean(EntityArmorStand.I[0xAE ^ 0xA6], this.func_181026_s());
        }
        nbtTagCompound.setTag(EntityArmorStand.I[0xC8 ^ 0xC1], this.readPoseFromNBT());
    }
    
    private void setSmall(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0xAC ^ 0xA6);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | " ".length());
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            b2 = (byte)(watchableObjectByte & -"  ".length());
        }
        this.dataWatcher.updateObject(0x11 ^ 0x1B, b2);
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        this.contents[n] = itemStack;
    }
    
    public Rotations getLeftArmRotation() {
        return this.leftArmRotation;
    }
    
    public boolean hasNoGravity() {
        if ((this.dataWatcher.getWatchableObjectByte(0xC ^ 0x6) & "  ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void updatePotionMetadata() {
        this.setInvisible(this.canInteract);
    }
    
    private void writePoseToNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagList tagList = nbtTagCompound.getTagList(EntityArmorStand.I[0x6B ^ 0x7F], 0x2A ^ 0x2F);
        if (tagList.tagCount() > 0) {
            this.setHeadRotation(new Rotations(tagList));
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            this.setHeadRotation(EntityArmorStand.DEFAULT_HEAD_ROTATION);
        }
        final NBTTagList tagList2 = nbtTagCompound.getTagList(EntityArmorStand.I[0x60 ^ 0x75], 0x5B ^ 0x5E);
        if (tagList2.tagCount() > 0) {
            this.setBodyRotation(new Rotations(tagList2));
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            this.setBodyRotation(EntityArmorStand.DEFAULT_BODY_ROTATION);
        }
        final NBTTagList tagList3 = nbtTagCompound.getTagList(EntityArmorStand.I[0xD1 ^ 0xC7], 0x49 ^ 0x4C);
        if (tagList3.tagCount() > 0) {
            this.setLeftArmRotation(new Rotations(tagList3));
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            this.setLeftArmRotation(EntityArmorStand.DEFAULT_LEFTARM_ROTATION);
        }
        final NBTTagList tagList4 = nbtTagCompound.getTagList(EntityArmorStand.I[0x97 ^ 0x80], 0x78 ^ 0x7D);
        if (tagList4.tagCount() > 0) {
            this.setRightArmRotation(new Rotations(tagList4));
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            this.setRightArmRotation(EntityArmorStand.DEFAULT_RIGHTARM_ROTATION);
        }
        final NBTTagList tagList5 = nbtTagCompound.getTagList(EntityArmorStand.I[0x36 ^ 0x2E], 0x86 ^ 0x83);
        if (tagList5.tagCount() > 0) {
            this.setLeftLegRotation(new Rotations(tagList5));
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.setLeftLegRotation(EntityArmorStand.DEFAULT_LEFTLEG_ROTATION);
        }
        final NBTTagList tagList6 = nbtTagCompound.getTagList(EntityArmorStand.I[0xAB ^ 0xB2], 0x4D ^ 0x48);
        if (tagList6.tagCount() > 0) {
            this.setRightLegRotation(new Rotations(tagList6));
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            this.setRightLegRotation(EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntityArmorStand.I[0x14 ^ 0x1E], 0xBE ^ 0xB7)) {
            final NBTTagList tagList = nbtTagCompound.getTagList(EntityArmorStand.I[0xB9 ^ 0xB2], 0x61 ^ 0x6B);
            int i = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (i < this.contents.length) {
                this.contents[i] = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i));
                ++i;
            }
        }
        this.setInvisible(nbtTagCompound.getBoolean(EntityArmorStand.I[0x17 ^ 0x1B]));
        this.setSmall(nbtTagCompound.getBoolean(EntityArmorStand.I[0x7D ^ 0x70]));
        this.setShowArms(nbtTagCompound.getBoolean(EntityArmorStand.I[0x2D ^ 0x23]));
        this.disabledSlots = nbtTagCompound.getInteger(EntityArmorStand.I[0x93 ^ 0x9C]);
        this.setNoGravity(nbtTagCompound.getBoolean(EntityArmorStand.I[0xA8 ^ 0xB8]));
        this.setNoBasePlate(nbtTagCompound.getBoolean(EntityArmorStand.I[0xD4 ^ 0xC5]));
        this.func_181027_m(nbtTagCompound.getBoolean(EntityArmorStand.I[0x9B ^ 0x89]));
        int field_181028_bj;
        if (this.func_181026_s()) {
            field_181028_bj = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            field_181028_bj = " ".length();
        }
        this.field_181028_bj = (field_181028_bj != 0);
        this.noClip = this.hasNoGravity();
        this.writePoseToNBT(nbtTagCompound.getCompoundTag(EntityArmorStand.I[0x6B ^ 0x78]));
    }
    
    public void setLeftArmRotation(final Rotations leftArmRotation) {
        this.leftArmRotation = leftArmRotation;
        this.dataWatcher.updateObject(0x94 ^ 0x99, leftArmRotation);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        if (super.canBeCollidedWith() && !this.func_181026_s()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setHeadRotation(final Rotations headRotation) {
        this.headRotation = headRotation;
        this.dataWatcher.updateObject(0x80 ^ 0x8B, headRotation);
    }
    
    @Override
    protected void collideWithNearbyEntities() {
        final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
        if (entitiesWithinAABBExcludingEntity != null && !entitiesWithinAABBExcludingEntity.isEmpty()) {
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity = entitiesWithinAABBExcludingEntity.get(i);
                if (entity instanceof EntityMinecart && ((EntityMinecart)entity).getMinecartType() == EntityMinecart.EnumMinecartType.RIDEABLE && this.getDistanceSqToEntity(entity) <= 0.2) {
                    entity.applyEntityCollision(this);
                }
                ++i;
            }
        }
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(n2) || n2 == 0.0) {
            n2 = 4.0;
        }
        final double n3 = n2 * 64.0;
        if (n < n3 * n3) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setLeftLegRotation(final Rotations leftLegRotation) {
        this.leftLegRotation = leftLegRotation;
        this.dataWatcher.updateObject(0x5B ^ 0x54, leftLegRotation);
    }
    
    public Rotations getHeadRotation() {
        return this.headRotation;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.worldObj.isRemote) {
            return "".length() != 0;
        }
        if (DamageSource.outOfWorld.equals(damageSource)) {
            this.setDead();
            return "".length() != 0;
        }
        if (this.isEntityInvulnerable(damageSource) || this.canInteract || this.func_181026_s()) {
            return "".length() != 0;
        }
        if (damageSource.isExplosion()) {
            this.dropContents();
            this.setDead();
            return "".length() != 0;
        }
        if (DamageSource.inFire.equals(damageSource)) {
            if (!this.isBurning()) {
                this.setFire(0x6D ^ 0x68);
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                this.damageArmorStand(0.15f);
            }
            return "".length() != 0;
        }
        if (DamageSource.onFire.equals(damageSource) && this.getHealth() > 0.5f) {
            this.damageArmorStand(4.0f);
            return "".length() != 0;
        }
        final boolean equals = EntityArmorStand.I[0x9 ^ 0x29].equals(damageSource.getDamageType());
        if (!EntityArmorStand.I[0xE2 ^ 0xC3].equals(damageSource.getDamageType()) && !equals) {
            return "".length() != 0;
        }
        if (damageSource.getSourceOfDamage() instanceof EntityArrow) {
            damageSource.getSourceOfDamage().setDead();
        }
        if (damageSource.getEntity() instanceof EntityPlayer && !((EntityPlayer)damageSource.getEntity()).capabilities.allowEdit) {
            return "".length() != 0;
        }
        if (damageSource.isCreativePlayer()) {
            this.playParticles();
            this.setDead();
            return "".length() != 0;
        }
        final long totalWorldTime = this.worldObj.getTotalWorldTime();
        if (totalWorldTime - this.punchCooldown > 5L && !equals) {
            this.punchCooldown = totalWorldTime;
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            this.dropBlock();
            this.playParticles();
            this.setDead();
        }
        return "".length() != 0;
    }
    
    public EntityArmorStand(final World world, final double n, final double n2, final double n3) {
        this(world);
        this.setPosition(n, n2, n3);
    }
    
    @Override
    public ItemStack[] getInventory() {
        return this.contents;
    }
    
    private void func_175422_a(final EntityPlayer entityPlayer, final int n) {
        final ItemStack itemStack = this.contents[n];
        if ((itemStack == null || (this.disabledSlots & " ".length() << n + (0x57 ^ 0x5F)) == 0x0) && (itemStack != null || (this.disabledSlots & " ".length() << n + (0x6B ^ 0x7B)) == 0x0)) {
            final int currentItem = entityPlayer.inventory.currentItem;
            final ItemStack stackInSlot = entityPlayer.inventory.getStackInSlot(currentItem);
            if (entityPlayer.capabilities.isCreativeMode && (itemStack == null || itemStack.getItem() == Item.getItemFromBlock(Blocks.air)) && stackInSlot != null) {
                final ItemStack copy = stackInSlot.copy();
                copy.stackSize = " ".length();
                this.setCurrentItemOrArmor(n, copy);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else if (stackInSlot != null && stackInSlot.stackSize > " ".length()) {
                if (itemStack == null) {
                    final ItemStack copy2 = stackInSlot.copy();
                    copy2.stackSize = " ".length();
                    this.setCurrentItemOrArmor(n, copy2);
                    final ItemStack itemStack2 = stackInSlot;
                    itemStack2.stackSize -= " ".length();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
            }
            else {
                this.setCurrentItemOrArmor(n, stackInSlot);
                entityPlayer.inventory.setInventorySlotContents(currentItem, itemStack);
            }
        }
    }
    
    public EntityArmorStand(final World world) {
        super(world);
        this.contents = new ItemStack[0x3A ^ 0x3F];
        this.headRotation = EntityArmorStand.DEFAULT_HEAD_ROTATION;
        this.bodyRotation = EntityArmorStand.DEFAULT_BODY_ROTATION;
        this.leftArmRotation = EntityArmorStand.DEFAULT_LEFTARM_ROTATION;
        this.rightArmRotation = EntityArmorStand.DEFAULT_RIGHTARM_ROTATION;
        this.leftLegRotation = EntityArmorStand.DEFAULT_LEFTLEG_ROTATION;
        this.rightLegRotation = EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION;
        this.setSilent(" ".length() != 0);
        this.noClip = this.hasNoGravity();
        this.setSize(0.5f, 1.975f);
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int n) {
        return this.contents[n];
    }
    
    private void setNoGravity(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0xCC ^ 0xC6);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | "  ".length());
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            b2 = (byte)(watchableObjectByte & -"   ".length());
        }
        this.dataWatcher.updateObject(0x22 ^ 0x28, b2);
    }
    
    private void setNoBasePlate(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x6A ^ 0x60);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | (0x37 ^ 0x3F));
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            b2 = (byte)(watchableObjectByte & -(0x74 ^ 0x7D));
        }
        this.dataWatcher.updateObject(0x22 ^ 0x28, b2);
    }
    
    public boolean isSmall() {
        if ((this.dataWatcher.getWatchableObjectByte(0x5F ^ 0x55) & " ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onKillCommand() {
        this.setDead();
    }
    
    @Override
    public boolean canBePushed() {
        return "".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        final Rotations watchableObjectRotations = this.dataWatcher.getWatchableObjectRotations(0x8E ^ 0x85);
        if (!this.headRotation.equals(watchableObjectRotations)) {
            this.setHeadRotation(watchableObjectRotations);
        }
        final Rotations watchableObjectRotations2 = this.dataWatcher.getWatchableObjectRotations(0x37 ^ 0x3B);
        if (!this.bodyRotation.equals(watchableObjectRotations2)) {
            this.setBodyRotation(watchableObjectRotations2);
        }
        final Rotations watchableObjectRotations3 = this.dataWatcher.getWatchableObjectRotations(0x8F ^ 0x82);
        if (!this.leftArmRotation.equals(watchableObjectRotations3)) {
            this.setLeftArmRotation(watchableObjectRotations3);
        }
        final Rotations watchableObjectRotations4 = this.dataWatcher.getWatchableObjectRotations(0x14 ^ 0x1A);
        if (!this.rightArmRotation.equals(watchableObjectRotations4)) {
            this.setRightArmRotation(watchableObjectRotations4);
        }
        final Rotations watchableObjectRotations5 = this.dataWatcher.getWatchableObjectRotations(0x3 ^ 0xC);
        if (!this.leftLegRotation.equals(watchableObjectRotations5)) {
            this.setLeftLegRotation(watchableObjectRotations5);
        }
        final Rotations watchableObjectRotations6 = this.dataWatcher.getWatchableObjectRotations(0x79 ^ 0x69);
        if (!this.rightLegRotation.equals(watchableObjectRotations6)) {
            this.setRightLegRotation(watchableObjectRotations6);
        }
        final boolean func_181026_s = this.func_181026_s();
        if (!this.field_181028_bj && func_181026_s) {
            this.func_181550_a("".length() != 0);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            if (!this.field_181028_bj || func_181026_s) {
                return;
            }
            this.func_181550_a(" ".length() != 0);
        }
        this.field_181028_bj = func_181026_s;
    }
    
    private void dropContents() {
        int i = "".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (i < this.contents.length) {
            if (this.contents[i] != null && this.contents[i].stackSize > 0) {
                if (this.contents[i] != null) {
                    Block.spawnAsEntity(this.worldObj, new BlockPos(this).up(), this.contents[i]);
                }
                this.contents[i] = null;
            }
            ++i;
        }
    }
    
    public boolean func_181026_s() {
        if ((this.dataWatcher.getWatchableObjectByte(0x85 ^ 0x8F) & (0x52 ^ 0x42)) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void setShowArms(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x22 ^ 0x28);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | (0xBA ^ 0xBE));
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            b2 = (byte)(watchableObjectByte & -(0x7 ^ 0x2));
        }
        this.dataWatcher.updateObject(0xAB ^ 0xA1, b2);
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean interactAt(final EntityPlayer entityPlayer, final Vec3 vec3) {
        if (this.func_181026_s()) {
            return "".length() != 0;
        }
        if (this.worldObj.isRemote || entityPlayer.isSpectator()) {
            return " ".length() != 0;
        }
        int n = "".length();
        final ItemStack currentEquippedItem = entityPlayer.getCurrentEquippedItem();
        int n2;
        if (currentEquippedItem != null) {
            n2 = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        if (n3 != 0 && currentEquippedItem.getItem() instanceof ItemArmor) {
            final ItemArmor itemArmor = (ItemArmor)currentEquippedItem.getItem();
            if (itemArmor.armorType == "   ".length()) {
                n = " ".length();
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            else if (itemArmor.armorType == "  ".length()) {
                n = "  ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else if (itemArmor.armorType == " ".length()) {
                n = "   ".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else if (itemArmor.armorType == 0) {
                n = (0x60 ^ 0x64);
            }
        }
        if (n3 != 0 && (currentEquippedItem.getItem() == Items.skull || currentEquippedItem.getItem() == Item.getItemFromBlock(Blocks.pumpkin))) {
            n = (0xBC ^ 0xB8);
        }
        int n4 = "".length();
        final boolean small = this.isSmall();
        double yCoord;
        if (small) {
            yCoord = vec3.yCoord * 2.0;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            yCoord = vec3.yCoord;
        }
        final double n5 = yCoord;
        Label_0586: {
            if (n5 >= 0.1) {
                final double n6 = n5;
                final double n7 = 0.1;
                double n8;
                if (small) {
                    n8 = 0.8;
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else {
                    n8 = 0.45;
                }
                if (n6 < n7 + n8 && this.contents[" ".length()] != null) {
                    n4 = " ".length();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    break Label_0586;
                }
            }
            final double n9 = n5;
            final double n10 = 0.9;
            double n11;
            if (small) {
                n11 = 0.3;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                n11 = 0.0;
            }
            if (n9 >= n10 + n11) {
                final double n12 = n5;
                final double n13 = 0.9;
                double n14;
                if (small) {
                    n14 = 1.0;
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
                else {
                    n14 = 0.7;
                }
                if (n12 < n13 + n14 && this.contents["   ".length()] != null) {
                    n4 = "   ".length();
                    "".length();
                    if (!true) {
                        throw null;
                    }
                    break Label_0586;
                }
            }
            if (n5 >= 0.4) {
                final double n15 = n5;
                final double n16 = 0.4;
                double n17;
                if (small) {
                    n17 = 1.0;
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
                else {
                    n17 = 0.8;
                }
                if (n15 < n16 + n17 && this.contents["  ".length()] != null) {
                    n4 = "  ".length();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                    break Label_0586;
                }
            }
            if (n5 >= 1.6 && this.contents[0x6 ^ 0x2] != null) {
                n4 = (0x20 ^ 0x24);
            }
        }
        int n18;
        if (this.contents[n4] != null) {
            n18 = " ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n18 = "".length();
        }
        final int n19 = n18;
        if ((this.disabledSlots & " ".length() << n4) != 0x0 || (this.disabledSlots & " ".length() << n) != 0x0) {
            n4 = n;
            if ((this.disabledSlots & " ".length() << n) != 0x0) {
                if ((this.disabledSlots & " ".length()) != 0x0) {
                    return " ".length() != 0;
                }
                n4 = "".length();
            }
        }
        if (n3 != 0 && n == 0 && !this.getShowArms()) {
            return " ".length() != 0;
        }
        if (n3 != 0) {
            this.func_175422_a(entityPlayer, n);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (n19 != 0) {
            this.func_175422_a(entityPlayer, n4);
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x2E ^ 0xC])["".length()] = I("\u0006\u0000\u00148\u0003.\u0014\u000f%", "CqaQs");
        EntityArmorStand.I[" ".length()] = I("\u001790\u001f-9\u0002\"\u0006'\u0002%0\u0002 8)", "TLCkB");
        EntityArmorStand.I["  ".length()] = I("\u0019:\u0005!\u000b96\u001f-", "PTsHx");
        EntityArmorStand.I["   ".length()] = I("%8,&(", "vUMJD");
        EntityArmorStand.I[0xAD ^ 0xA9] = I("\u000b%?\u0005\u0003* #", "XMPrB");
        EntityArmorStand.I[0xA2 ^ 0xA7] = I("\u00030\u0003\u0016.+<\u0014$ (-\u0003", "GYpwL");
        EntityArmorStand.I[0x5F ^ 0x59] = I("\"\f3\u0011\r\u001a\n\u0000\u001a", "lctcl");
        EntityArmorStand.I[0x84 ^ 0x83] = I("\u000f\u001a\u000f\u0000 $%!\u0000'$", "AuMaS");
        EntityArmorStand.I[0x41 ^ 0x49] = I("%##*\u0010\u001a", "hBQAu");
        EntityArmorStand.I[0x60 ^ 0x69] = I("\u001e\u00066*", "NiEOk");
        EntityArmorStand.I[0x54 ^ 0x5E] = I("$\u0001:,8\f\u0015!1", "apOEH");
        EntityArmorStand.I[0x86 ^ 0x8D] = I("\u0014\u0012\u0014?\u0018<\u0006\u000f\"", "QcaVh");
        EntityArmorStand.I[0x18 ^ 0x14] = I("\u0003\u0005\u0007\u001c##\t\u001d\u0010", "JkquP");
        EntityArmorStand.I[0x1E ^ 0x13] = I("\u0015;\r\t)", "FVleE");
        EntityArmorStand.I[0x4 ^ 0xA] = I("<\u0018$% \u001d\u001d8", "opKRa");
        EntityArmorStand.I[0x3 ^ 0xC] = I("+\u001f\n\u0017\u0005\u0003\u0013\u001d%\u000b\u0000\u0002\n", "ovyvg");
        EntityArmorStand.I[0x27 ^ 0x37] = I("\t$4* 1\"\u0007!", "GKsXA");
        EntityArmorStand.I[0x52 ^ 0x43] = I("\u001f\"\r\u0000;4\u001d#\u0000<4", "QMOaH");
        EntityArmorStand.I[0x3B ^ 0x29] = I("8'6\r0\u0007", "uFDfU");
        EntityArmorStand.I[0xF ^ 0x1C] = I("\u0012<\u00140", "BSgUQ");
        EntityArmorStand.I[0x37 ^ 0x23] = I("\u0000\u0003\u0011 ", "HfpDZ");
        EntityArmorStand.I[0x32 ^ 0x27] = I("%\u0006\u0005\u000e", "giawP");
        EntityArmorStand.I[0x9D ^ 0x8B] = I("\u0000\u0013\t%1>\u001b", "LvoQp");
        EntityArmorStand.I[0x49 ^ 0x5E] = I("*\u00000\u000499\u001b:", "xiWlM");
        EntityArmorStand.I[0x86 ^ 0x9E] = I("\u0007',\u0019:.%", "KBJmv");
        EntityArmorStand.I[0x40 ^ 0x59] = I("\u000b\u0002(\u0003\u001c\u0015\u000e(", "YkOkh");
        EntityArmorStand.I[0xDB ^ 0xC1] = I("&\f\u000f\u0007", "nincg");
        EntityArmorStand.I[0x17 ^ 0xC] = I("7\u0000,\u0014", "uoHmL");
        EntityArmorStand.I[0x2D ^ 0x31] = I("'\u0002\"'\u0015\u0019\n", "kgDST");
        EntityArmorStand.I[0x53 ^ 0x4E] = I("\u0015$\u0012\"&\u0006?\u0018", "GMuJR");
        EntityArmorStand.I[0x9A ^ 0x84] = I("\"2\u0002\u0010\u001c\u000b0", "nWddP");
        EntityArmorStand.I[0x98 ^ 0x87] = I("\u0014<,\u001f%\n0,", "FUKwQ");
        EntityArmorStand.I[0xBD ^ 0x9D] = I("\u0019(1\b8", "xZCgO");
        EntityArmorStand.I[0xE0 ^ 0xC1] = I("2\u0014\u0002!\u00150", "BxcXp");
    }
    
    public void setBodyRotation(final Rotations bodyRotation) {
        this.bodyRotation = bodyRotation;
        this.dataWatcher.updateObject(0x78 ^ 0x74, bodyRotation);
    }
    
    public boolean hasNoBasePlate() {
        if ((this.dataWatcher.getWatchableObjectByte(0x32 ^ 0x38) & (0x47 ^ 0x4F)) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x47 ^ 0x4D, (byte)"".length());
        this.dataWatcher.addObject(0x8D ^ 0x86, EntityArmorStand.DEFAULT_HEAD_ROTATION);
        this.dataWatcher.addObject(0x12 ^ 0x1E, EntityArmorStand.DEFAULT_BODY_ROTATION);
        this.dataWatcher.addObject(0xBC ^ 0xB1, EntityArmorStand.DEFAULT_LEFTARM_ROTATION);
        this.dataWatcher.addObject(0x7B ^ 0x75, EntityArmorStand.DEFAULT_RIGHTARM_ROTATION);
        this.dataWatcher.addObject(0x68 ^ 0x67, EntityArmorStand.DEFAULT_LEFTLEG_ROTATION);
        this.dataWatcher.addObject(0x28 ^ 0x38, EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION);
    }
    
    public void setRightLegRotation(final Rotations rightLegRotation) {
        this.rightLegRotation = rightLegRotation;
        this.dataWatcher.updateObject(0x7E ^ 0x6E, rightLegRotation);
    }
    
    public Rotations getRightLegRotation() {
        return this.rightLegRotation;
    }
    
    private void playParticles() {
        if (this.worldObj instanceof WorldServer) {
            final WorldServer worldServer = (WorldServer)this.worldObj;
            final EnumParticleTypes block_DUST = EnumParticleTypes.BLOCK_DUST;
            final double posX = this.posX;
            final double n = this.posY + this.height / 1.5;
            final double posZ = this.posZ;
            final int n2 = 0xCC ^ 0xC6;
            final double n3 = this.width / 4.0f;
            final double n4 = this.height / 4.0f;
            final double n5 = this.width / 4.0f;
            final double n6 = 0.05;
            final int[] array = new int[" ".length()];
            array["".length()] = Block.getStateId(Blocks.planks.getDefaultState());
            worldServer.spawnParticle(block_DUST, posX, n, posZ, n2, n3, n4, n5, n6, array);
        }
    }
    
    private void func_181027_m(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x71 ^ 0x7B);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | (0x48 ^ 0x58));
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            b2 = (byte)(watchableObjectByte & -(0x63 ^ 0x72));
        }
        this.dataWatcher.updateObject(0x1B ^ 0x11, b2);
    }
    
    @Override
    public float getEyeHeight() {
        float n;
        if (this.isChild()) {
            n = this.height * 0.5f;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n = this.height * 0.9f;
        }
        return n;
    }
    
    private void func_181550_a(final boolean b) {
        final double posX = this.posX;
        final double posY = this.posY;
        final double posZ = this.posZ;
        if (b) {
            this.setSize(0.5f, 1.975f);
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            this.setSize(0.0f, 0.0f);
        }
        this.setPosition(posX, posY, posZ);
    }
    
    static {
        I();
        DEFAULT_HEAD_ROTATION = new Rotations(0.0f, 0.0f, 0.0f);
        DEFAULT_BODY_ROTATION = new Rotations(0.0f, 0.0f, 0.0f);
        DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0f, 0.0f, -10.0f);
        DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0f, 0.0f, 10.0f);
        DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0f, 0.0f, -1.0f);
        DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0f, 0.0f, 1.0f);
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.contents["".length()];
    }
    
    public void setRightArmRotation(final Rotations rightArmRotation) {
        this.rightArmRotation = rightArmRotation;
        this.dataWatcher.updateObject(0x3B ^ 0x35, rightArmRotation);
    }
    
    @Override
    protected void collideWithEntity(final Entity entity) {
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        int length;
        if (n == (0x72 ^ 0x11)) {
            length = "".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else {
            length = n - (0x25 ^ 0x41) + " ".length();
            if (length < 0 || length >= this.contents.length) {
                return "".length() != 0;
            }
        }
        if (itemStack != null && EntityLiving.getArmorPosition(itemStack) != length && (length != (0x7B ^ 0x7F) || !(itemStack.getItem() instanceof ItemBlock))) {
            return "".length() != 0;
        }
        this.setCurrentItemOrArmor(length, itemStack);
        return " ".length() != 0;
    }
    
    @Override
    public void setInvisible(final boolean canInteract) {
        super.setInvisible(this.canInteract = canInteract);
    }
    
    @Override
    public boolean isImmuneToExplosions() {
        return this.isInvisible();
    }
    
    @Override
    public boolean isChild() {
        return this.isSmall();
    }
    
    @Override
    public boolean isServerWorld() {
        if (super.isServerWorld() && !this.hasNoGravity()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected float func_110146_f(final float n, final float n2) {
        this.prevRenderYawOffset = this.prevRotationYaw;
        this.renderYawOffset = this.rotationYaw;
        return 0.0f;
    }
    
    @Override
    public ItemStack getCurrentArmor(final int n) {
        return this.contents[n + " ".length()];
    }
    
    private void damageArmorStand(final float n) {
        final float health = this.getHealth() - n;
        if (health <= 0.5f) {
            this.dropContents();
            this.setDead();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            this.setHealth(health);
        }
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        if (!this.hasNoGravity()) {
            super.moveEntityWithHeading(n, n2);
        }
    }
    
    public Rotations getBodyRotation() {
        return this.bodyRotation;
    }
    
    private NBTTagCompound readPoseFromNBT() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        if (!EntityArmorStand.DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            nbtTagCompound.setTag(EntityArmorStand.I[0x46 ^ 0x5C], this.headRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            nbtTagCompound.setTag(EntityArmorStand.I[0xAC ^ 0xB7], this.bodyRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation)) {
            nbtTagCompound.setTag(EntityArmorStand.I[0x4B ^ 0x57], this.leftArmRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation)) {
            nbtTagCompound.setTag(EntityArmorStand.I[0x1E ^ 0x3], this.rightArmRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation)) {
            nbtTagCompound.setTag(EntityArmorStand.I[0x2C ^ 0x32], this.leftLegRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation)) {
            nbtTagCompound.setTag(EntityArmorStand.I[0x79 ^ 0x66], this.rightLegRotation.writeToNBT());
        }
        return nbtTagCompound;
    }
    
    public boolean getShowArms() {
        if ((this.dataWatcher.getWatchableObjectByte(0x1E ^ 0x14) & (0x45 ^ 0x41)) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Rotations getLeftLegRotation() {
        return this.leftLegRotation;
    }
    
    public Rotations getRightArmRotation() {
        return this.rightArmRotation;
    }
    
    private void dropBlock() {
        Block.spawnAsEntity(this.worldObj, new BlockPos(this), new ItemStack(Items.armor_stand));
        this.dropContents();
    }
}
