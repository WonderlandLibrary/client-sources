package net.minecraft.entity.passive;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;

public class EntitySheep extends EntityAnimal
{
    private int sheepTimer;
    private static final Map<EnumDyeColor, float[]> DYE_TO_RGB;
    private EntityAIEatGrass entityAIEatGrass;
    private final InventoryCrafting inventoryCrafting;
    private static final String[] I;
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        if (!this.getSheared()) {
            this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), this.getFleeceColor().getMetadata()), 0.0f);
        }
        final int n2 = this.rand.nextInt("  ".length()) + " ".length() + this.rand.nextInt(" ".length() + n);
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < n2) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_mutton, " ".length());
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                this.dropItem(Items.mutton, " ".length());
            }
            ++i;
        }
    }
    
    public float getHeadRotationPointY(final float n) {
        float n2;
        if (this.sheepTimer <= 0) {
            n2 = 0.0f;
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else if (this.sheepTimer >= (0xA2 ^ 0xA6) && this.sheepTimer <= (0xA0 ^ 0x84)) {
            n2 = 1.0f;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (this.sheepTimer < (0x6B ^ 0x6F)) {
            n2 = (this.sheepTimer - n) / 4.0f;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n2 = -(this.sheepTimer - (0x0 ^ 0x28) - n) / 4.0f;
        }
        return n2;
    }
    
    private EnumDyeColor getDyeColorMixFromParents(final EntityAnimal entityAnimal, final EntityAnimal entityAnimal2) {
        final int dyeDamage = ((EntitySheep)entityAnimal).getFleeceColor().getDyeDamage();
        final int dyeDamage2 = ((EntitySheep)entityAnimal2).getFleeceColor().getDyeDamage();
        this.inventoryCrafting.getStackInSlot("".length()).setItemDamage(dyeDamage);
        this.inventoryCrafting.getStackInSlot(" ".length()).setItemDamage(dyeDamage2);
        final ItemStack matchingRecipe = CraftingManager.getInstance().findMatchingRecipe(this.inventoryCrafting, ((EntitySheep)entityAnimal).worldObj);
        int metadata;
        if (matchingRecipe != null && matchingRecipe.getItem() == Items.dye) {
            metadata = matchingRecipe.getMetadata();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            int n;
            if (this.worldObj.rand.nextBoolean()) {
                n = dyeDamage;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n = dyeDamage2;
            }
            metadata = n;
        }
        return EnumDyeColor.byDyeDamage(metadata);
    }
    
    @Override
    protected String getHurtSound() {
        return EntitySheep.I[0xC ^ 0xA];
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setSheared(nbtTagCompound.getBoolean(EntitySheep.I["   ".length()]));
        this.setFleeceColor(EnumDyeColor.byMetadata(nbtTagCompound.getByte(EntitySheep.I[0x2F ^ 0x2B])));
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntitySheep.I[0x97 ^ 0x9F], 0.15f, 1.0f);
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        this.setFleeceColor(getRandomSheepColor(this.worldObj.rand));
        return onInitialSpawn;
    }
    
    public static float[] func_175513_a(final EnumDyeColor enumDyeColor) {
        return EntitySheep.DYE_TO_RGB.get(enumDyeColor);
    }
    
    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }
    
    public EntitySheep(final World world) {
        super(world);
        this.inventoryCrafting = new InventoryCrafting(new Container() {
            final EntitySheep this$0;
            
            @Override
            public boolean canInteractWith(final EntityPlayer entityPlayer) {
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
                    if (3 != 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }, "  ".length(), " ".length());
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.setSize(0.9f, 1.3f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask(" ".length(), new EntityAIPanic(this, 1.25));
        this.tasks.addTask("  ".length(), new EntityAIMate(this, 1.0));
        this.tasks.addTask("   ".length(), new EntityAITempt(this, 1.1, Items.wheat, (boolean)("".length() != 0)));
        this.tasks.addTask(0x10 ^ 0x14, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(0xBC ^ 0xB9, this.entityAIEatGrass);
        this.tasks.addTask(0x56 ^ 0x50, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0xAA ^ 0xAD, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(0x62 ^ 0x6A, new EntityAILookIdle(this));
        this.inventoryCrafting.setInventorySlotContents("".length(), new ItemStack(Items.dye, " ".length(), "".length()));
        this.inventoryCrafting.setInventorySlotContents(" ".length(), new ItemStack(Items.dye, " ".length(), "".length()));
    }
    
    @Override
    public EntitySheep createChild(final EntityAgeable entityAgeable) {
        final EntitySheep entitySheep = (EntitySheep)entityAgeable;
        final EntitySheep entitySheep2 = new EntitySheep(this.worldObj);
        entitySheep2.setFleeceColor(this.getDyeColorMixFromParents(this, entitySheep));
        return entitySheep2;
    }
    
    private static void I() {
        (I = new String[0x93 ^ 0x9A])["".length()] = I("#\u0006(v<&\f/(a=\u0001/9=", "NiJXO");
        EntitySheep.I[" ".length()] = I("\u0007\u0005'\u001b\u00181\t", "TmBzj");
        EntitySheep.I["  ".length()] = I("\u0015'!)\u0014", "VHMFf");
        EntitySheep.I["   ".length()] = I("%8?4+\u00134", "vPZUY");
        EntitySheep.I[0x11 ^ 0x15] = I("\u0004,5 \u0003", "GCYOq");
        EntitySheep.I[0xBE ^ 0xBB] = I("\u001c\u0018+B)\u0019\u0012,\u001ct\u0002\u00160", "qwIlZ");
        EntitySheep.I[0x1E ^ 0x18] = I("\u000f\r-O*\n\u0007*\u0011w\u0011\u00036", "bbOaY");
        EntitySheep.I[0x1C ^ 0x1B] = I("<5\u000f~79?\b j\";\u0014", "QZmPD");
        EntitySheep.I[0x36 ^ 0x3E] = I("9;\u0015z;<1\u0012$f' \u0012$", "TTwTH");
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x36 ^ 0x3C)) {
            this.sheepTimer = (0x5B ^ 0x73);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 0.95f * this.height;
    }
    
    public boolean getSheared() {
        if ((this.dataWatcher.getWatchableObjectByte(0x83 ^ 0x93) & (0x50 ^ 0x40)) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static EnumDyeColor getRandomSheepColor(final Random random) {
        final int nextInt = random.nextInt(0x3A ^ 0x5E);
        EnumDyeColor enumDyeColor;
        if (nextInt < (0xC2 ^ 0xC7)) {
            enumDyeColor = EnumDyeColor.BLACK;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (nextInt < (0x9C ^ 0x96)) {
            enumDyeColor = EnumDyeColor.GRAY;
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else if (nextInt < (0x9A ^ 0x95)) {
            enumDyeColor = EnumDyeColor.SILVER;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (nextInt < (0x65 ^ 0x77)) {
            enumDyeColor = EnumDyeColor.BROWN;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else if (random.nextInt(309 + 478 - 290 + 3) == 0) {
            enumDyeColor = EnumDyeColor.PINK;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            enumDyeColor = EnumDyeColor.WHITE;
        }
        return enumDyeColor;
    }
    
    @Override
    protected String getDeathSound() {
        return EntitySheep.I[0x96 ^ 0x91];
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.sheepTimer = Math.max("".length(), this.sheepTimer - " ".length());
        }
        super.onLivingUpdate();
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.wool);
    }
    
    public void setSheared(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x6E ^ 0x7E);
        if (b) {
            this.dataWatcher.updateObject(0x5 ^ 0x15, (byte)(watchableObjectByte | (0xBC ^ 0xAC)));
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x4A ^ 0x5A, (byte)(watchableObjectByte & -(0xD6 ^ 0xC7)));
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntitySheep.I[" ".length()], this.getSheared());
        nbtTagCompound.setByte(EntitySheep.I["  ".length()], (byte)this.getFleeceColor().getMetadata());
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x8C ^ 0x9C, new Byte((byte)"".length()));
    }
    
    public EnumDyeColor getFleeceColor() {
        return EnumDyeColor.byMetadata(this.dataWatcher.getWatchableObjectByte(0x5C ^ 0x4C) & (0xB7 ^ 0xB8));
    }
    
    @Override
    protected String getLivingSound() {
        return EntitySheep.I[0x28 ^ 0x2D];
    }
    
    public float getHeadRotationAngleX(final float n) {
        if (this.sheepTimer > (0x1F ^ 0x1B) && this.sheepTimer <= (0xAB ^ 0x8F)) {
            return 0.62831855f + 0.2199115f * MathHelper.sin((this.sheepTimer - (0x9B ^ 0x9F) - n) / 32.0f * 28.7f);
        }
        float n2;
        if (this.sheepTimer > 0) {
            n2 = 0.62831855f;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n2 = this.rotationPitch / 57.295776f;
        }
        return n2;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    public void setFleeceColor(final EnumDyeColor enumDyeColor) {
        this.dataWatcher.updateObject(0x2E ^ 0x3E, (byte)((this.dataWatcher.getWatchableObjectByte(0x2A ^ 0x3A) & 24 + 27 + 79 + 110) | (enumDyeColor.getMetadata() & (0x3A ^ 0x35))));
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.shears && !this.getSheared() && !this.isChild()) {
            if (!this.worldObj.isRemote) {
                this.setSheared(" ".length() != 0);
                final int n = " ".length() + this.rand.nextInt("   ".length());
                int i = "".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (i < n) {
                    final EntityItem entityDropItem;
                    final EntityItem entityItem = entityDropItem = this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), this.getFleeceColor().getMetadata()), 1.0f);
                    entityDropItem.motionY += this.rand.nextFloat() * 0.05f;
                    final EntityItem entityItem2 = entityItem;
                    entityItem2.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    final EntityItem entityItem3 = entityItem;
                    entityItem3.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    ++i;
                }
            }
            currentItem.damageItem(" ".length(), entityPlayer);
            this.playSound(EntitySheep.I["".length()], 1.0f, 1.0f);
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    public void eatGrassBonus() {
        this.setSheared("".length() != 0);
        if (this.isChild()) {
            this.addGrowth(0xAA ^ 0x96);
        }
    }
    
    static {
        I();
        DYE_TO_RGB = Maps.newEnumMap((Class)EnumDyeColor.class);
        final Map<EnumDyeColor, float[]> dye_TO_RGB = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor white = EnumDyeColor.WHITE;
        final float[] array = new float["   ".length()];
        array["".length()] = 1.0f;
        array[" ".length()] = 1.0f;
        array["  ".length()] = 1.0f;
        dye_TO_RGB.put(white, array);
        final Map<EnumDyeColor, float[]> dye_TO_RGB2 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor orange = EnumDyeColor.ORANGE;
        final float[] array2 = new float["   ".length()];
        array2["".length()] = 0.85f;
        array2[" ".length()] = 0.5f;
        array2["  ".length()] = 0.2f;
        dye_TO_RGB2.put(orange, array2);
        final Map<EnumDyeColor, float[]> dye_TO_RGB3 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor magenta = EnumDyeColor.MAGENTA;
        final float[] array3 = new float["   ".length()];
        array3["".length()] = 0.7f;
        array3[" ".length()] = 0.3f;
        array3["  ".length()] = 0.85f;
        dye_TO_RGB3.put(magenta, array3);
        final Map<EnumDyeColor, float[]> dye_TO_RGB4 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor light_BLUE = EnumDyeColor.LIGHT_BLUE;
        final float[] array4 = new float["   ".length()];
        array4["".length()] = 0.4f;
        array4[" ".length()] = 0.6f;
        array4["  ".length()] = 0.85f;
        dye_TO_RGB4.put(light_BLUE, array4);
        final Map<EnumDyeColor, float[]> dye_TO_RGB5 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor yellow = EnumDyeColor.YELLOW;
        final float[] array5 = new float["   ".length()];
        array5["".length()] = 0.9f;
        array5[" ".length()] = 0.9f;
        array5["  ".length()] = 0.2f;
        dye_TO_RGB5.put(yellow, array5);
        final Map<EnumDyeColor, float[]> dye_TO_RGB6 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor lime = EnumDyeColor.LIME;
        final float[] array6 = new float["   ".length()];
        array6["".length()] = 0.5f;
        array6[" ".length()] = 0.8f;
        array6["  ".length()] = 0.1f;
        dye_TO_RGB6.put(lime, array6);
        final Map<EnumDyeColor, float[]> dye_TO_RGB7 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor pink = EnumDyeColor.PINK;
        final float[] array7 = new float["   ".length()];
        array7["".length()] = 0.95f;
        array7[" ".length()] = 0.5f;
        array7["  ".length()] = 0.65f;
        dye_TO_RGB7.put(pink, array7);
        final Map<EnumDyeColor, float[]> dye_TO_RGB8 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor gray = EnumDyeColor.GRAY;
        final float[] array8 = new float["   ".length()];
        array8["".length()] = 0.3f;
        array8[" ".length()] = 0.3f;
        array8["  ".length()] = 0.3f;
        dye_TO_RGB8.put(gray, array8);
        final Map<EnumDyeColor, float[]> dye_TO_RGB9 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor silver = EnumDyeColor.SILVER;
        final float[] array9 = new float["   ".length()];
        array9["".length()] = 0.6f;
        array9[" ".length()] = 0.6f;
        array9["  ".length()] = 0.6f;
        dye_TO_RGB9.put(silver, array9);
        final Map<EnumDyeColor, float[]> dye_TO_RGB10 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor cyan = EnumDyeColor.CYAN;
        final float[] array10 = new float["   ".length()];
        array10["".length()] = 0.3f;
        array10[" ".length()] = 0.5f;
        array10["  ".length()] = 0.6f;
        dye_TO_RGB10.put(cyan, array10);
        final Map<EnumDyeColor, float[]> dye_TO_RGB11 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor purple = EnumDyeColor.PURPLE;
        final float[] array11 = new float["   ".length()];
        array11["".length()] = 0.5f;
        array11[" ".length()] = 0.25f;
        array11["  ".length()] = 0.7f;
        dye_TO_RGB11.put(purple, array11);
        final Map<EnumDyeColor, float[]> dye_TO_RGB12 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor blue = EnumDyeColor.BLUE;
        final float[] array12 = new float["   ".length()];
        array12["".length()] = 0.2f;
        array12[" ".length()] = 0.3f;
        array12["  ".length()] = 0.7f;
        dye_TO_RGB12.put(blue, array12);
        final Map<EnumDyeColor, float[]> dye_TO_RGB13 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor brown = EnumDyeColor.BROWN;
        final float[] array13 = new float["   ".length()];
        array13["".length()] = 0.4f;
        array13[" ".length()] = 0.3f;
        array13["  ".length()] = 0.2f;
        dye_TO_RGB13.put(brown, array13);
        final Map<EnumDyeColor, float[]> dye_TO_RGB14 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor green = EnumDyeColor.GREEN;
        final float[] array14 = new float["   ".length()];
        array14["".length()] = 0.4f;
        array14[" ".length()] = 0.5f;
        array14["  ".length()] = 0.2f;
        dye_TO_RGB14.put(green, array14);
        final Map<EnumDyeColor, float[]> dye_TO_RGB15 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor red = EnumDyeColor.RED;
        final float[] array15 = new float["   ".length()];
        array15["".length()] = 0.6f;
        array15[" ".length()] = 0.2f;
        array15["  ".length()] = 0.2f;
        dye_TO_RGB15.put(red, array15);
        final Map<EnumDyeColor, float[]> dye_TO_RGB16 = EntitySheep.DYE_TO_RGB;
        final EnumDyeColor black = EnumDyeColor.BLACK;
        final float[] array16 = new float["   ".length()];
        array16["".length()] = 0.1f;
        array16[" ".length()] = 0.1f;
        array16["  ".length()] = 0.1f;
        dye_TO_RGB16.put(black, array16);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
    }
}
