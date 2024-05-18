package net.minecraft.entity.monster;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
    private static final UUID MODIFIER_UUID;
    private static final String[] I;
    private int witchAttackTimer;
    private static final Item[] witchDrops;
    private static final AttributeModifier MODIFIER;
    
    static {
        I();
        MODIFIER_UUID = UUID.fromString(EntityWitch.I["".length()]);
        MODIFIER = new AttributeModifier(EntityWitch.MODIFIER_UUID, EntityWitch.I[" ".length()], -0.25, "".length()).setSaved("".length() != 0);
        final Item[] witchDrops2 = new Item[0x2C ^ 0x24];
        witchDrops2["".length()] = Items.glowstone_dust;
        witchDrops2[" ".length()] = Items.sugar;
        witchDrops2["  ".length()] = Items.redstone;
        witchDrops2["   ".length()] = Items.spider_eye;
        witchDrops2[0xB7 ^ 0xB3] = Items.glass_bottle;
        witchDrops2[0x59 ^ 0x5C] = Items.gunpowder;
        witchDrops2[0x6E ^ 0x68] = Items.stick;
        witchDrops2[0x1A ^ 0x1D] = Items.stick;
        witchDrops = witchDrops2;
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.getAggressive()) {
                final int witchAttackTimer = this.witchAttackTimer;
                this.witchAttackTimer = witchAttackTimer - " ".length();
                if (witchAttackTimer <= 0) {
                    this.setAggressive("".length() != 0);
                    final ItemStack heldItem = this.getHeldItem();
                    this.setCurrentItemOrArmor("".length(), null);
                    if (heldItem != null && heldItem.getItem() == Items.potionitem) {
                        final List<PotionEffect> effects = Items.potionitem.getEffects(heldItem);
                        if (effects != null) {
                            final Iterator<PotionEffect> iterator = effects.iterator();
                            "".length();
                            if (1 <= -1) {
                                throw null;
                            }
                            while (iterator.hasNext()) {
                                this.addPotionEffect(new PotionEffect(iterator.next()));
                            }
                        }
                    }
                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(EntityWitch.MODIFIER);
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
            }
            else {
                int n = -" ".length();
                if (this.rand.nextFloat() < 0.15f && this.isInsideOfMaterial(Material.water) && !this.isPotionActive(Potion.waterBreathing)) {
                    n = 8057 + 5844 - 13186 + 7522;
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else if (this.rand.nextFloat() < 0.15f && this.isBurning() && !this.isPotionActive(Potion.fireResistance)) {
                    n = 8638 + 7518 - 3024 + 3175;
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                }
                else if (this.rand.nextFloat() < 0.05f && this.getHealth() < this.getMaxHealth()) {
                    n = 12272 + 6392 - 4973 + 2650;
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else if (this.rand.nextFloat() < 0.25f && this.getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                    n = 5438 + 3926 + 2499 + 4411;
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                else if (this.rand.nextFloat() < 0.25f && this.getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                    n = 4048 + 1971 + 1910 + 8345;
                }
                if (n > -" ".length()) {
                    this.setCurrentItemOrArmor("".length(), new ItemStack(Items.potionitem, " ".length(), n));
                    this.witchAttackTimer = this.getHeldItem().getMaxItemUseDuration();
                    this.setAggressive(" ".length() != 0);
                    final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                    entityAttribute.removeModifier(EntityWitch.MODIFIER);
                    entityAttribute.applyModifier(EntityWitch.MODIFIER);
                }
            }
            if (this.rand.nextFloat() < 7.5E-4f) {
                this.worldObj.setEntityState(this, (byte)(0x26 ^ 0x29));
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x56 ^ 0x59)) {
            int i = "".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
            while (i < this.rand.nextInt(0x3E ^ 0x1D) + (0x9C ^ 0x96)) {
                this.worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.12999999523162842, this.getEntityBoundingBox().maxY + 0.5 + this.rand.nextGaussian() * 0.12999999523162842, this.posZ + this.rand.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0, new int["".length()]);
                ++i;
            }
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 1.62f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public boolean getAggressive() {
        if (this.getDataWatcher().getWatchableObjectByte(0x2E ^ 0x3B) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected String getLivingSound() {
        return null;
    }
    
    public void setAggressive(final boolean b) {
        final DataWatcher dataWatcher = this.getDataWatcher();
        final int n = 0x25 ^ 0x30;
        int n2;
        if (b) {
            n2 = " ".length();
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt("   ".length()) + " ".length();
        int i = "".length();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (i < n2) {
            int nextInt = this.rand.nextInt("   ".length());
            final Item item = EntityWitch.witchDrops[this.rand.nextInt(EntityWitch.witchDrops.length)];
            if (n > 0) {
                nextInt += this.rand.nextInt(n + " ".length());
            }
            int j = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (j < nextInt) {
                this.dropItem(item, " ".length());
                ++j;
            }
            ++i;
        }
    }
    
    public EntityWitch(final World world) {
        super(world);
        this.setSize(0.6f, 1.95f);
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), new EntityAIArrowAttack(this, 1.0, 0x21 ^ 0x1D, 10.0f));
        this.tasks.addTask("  ".length(), new EntityAIWander(this, 1.0));
        this.tasks.addTask("   ".length(), new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask("   ".length(), new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)("".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityPlayer.class, (boolean)(" ".length() != 0)));
    }
    
    @Override
    protected String getHurtSound() {
        return null;
    }
    
    @Override
    protected String getDeathSound() {
        return null;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(0x26 ^ 0x33, (byte)"".length());
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("s\u00010]X\u0003wFA.q{5A[u\u0006GA.spMAVv\u00040)_r\u0000ET^\u0003", "FBtlo");
        EntityWitch.I[" ".length()] = I("\u00051;\u0001\u001e(-5O\u00061&7\u000bU1&<\u000e\u00195:", "ACRou");
    }
    
    @Override
    protected float applyPotionDamageCalculations(final DamageSource damageSource, float applyPotionDamageCalculations) {
        applyPotionDamageCalculations = super.applyPotionDamageCalculations(damageSource, applyPotionDamageCalculations);
        if (damageSource.getEntity() == this) {
            applyPotionDamageCalculations = 0.0f;
        }
        if (damageSource.isMagicDamage()) {
            applyPotionDamageCalculations *= 0.15;
        }
        return applyPotionDamageCalculations;
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        if (!this.getAggressive()) {
            final EntityPotion entityPotion = new EntityPotion(this.worldObj, this, 28556 + 25550 - 34269 + 12895);
            final double n2 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - 1.100000023841858;
            final EntityPotion entityPotion2 = entityPotion;
            entityPotion2.rotationPitch += 20.0f;
            final double n3 = entityLivingBase.posX + entityLivingBase.motionX - this.posX;
            final double n4 = n2 - this.posY;
            final double n5 = entityLivingBase.posZ + entityLivingBase.motionZ - this.posZ;
            final float sqrt_double = MathHelper.sqrt_double(n3 * n3 + n5 * n5);
            if (sqrt_double >= 8.0f && !entityLivingBase.isPotionActive(Potion.moveSlowdown)) {
                entityPotion.setPotionDamage(30923 + 19867 - 28620 + 10528);
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else if (entityLivingBase.getHealth() >= 8.0f && !entityLivingBase.isPotionActive(Potion.poison)) {
                entityPotion.setPotionDamage(19070 + 6786 + 198 + 6606);
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else if (sqrt_double <= 3.0f && !entityLivingBase.isPotionActive(Potion.weakness) && this.rand.nextFloat() < 0.25f) {
                entityPotion.setPotionDamage(18283 + 18953 - 23780 + 19240);
            }
            entityPotion.setThrowableHeading(n3, n4 + sqrt_double * 0.2f, n5, 0.75f, 8.0f);
            this.worldObj.spawnEntityInWorld(entityPotion);
        }
    }
}
