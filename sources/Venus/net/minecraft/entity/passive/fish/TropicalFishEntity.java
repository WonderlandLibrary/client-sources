/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.fish;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class TropicalFishEntity
extends AbstractGroupFishEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(TropicalFishEntity.class, DataSerializers.VARINT);
    private static final ResourceLocation[] BODY_TEXTURES = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_a.png"), new ResourceLocation("textures/entity/fish/tropical_b.png")};
    private static final ResourceLocation[] PATTERN_TEXTURES_A = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_a_pattern_1.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_2.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_3.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_4.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_5.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_6.png")};
    private static final ResourceLocation[] PATTERN_TEXTURES_B = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_b_pattern_1.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_2.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_3.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_4.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_5.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_6.png")};
    public static final int[] SPECIAL_VARIANTS = new int[]{TropicalFishEntity.pack(Type.STRIPEY, DyeColor.ORANGE, DyeColor.GRAY), TropicalFishEntity.pack(Type.FLOPPER, DyeColor.GRAY, DyeColor.GRAY), TropicalFishEntity.pack(Type.FLOPPER, DyeColor.GRAY, DyeColor.BLUE), TropicalFishEntity.pack(Type.CLAYFISH, DyeColor.WHITE, DyeColor.GRAY), TropicalFishEntity.pack(Type.SUNSTREAK, DyeColor.BLUE, DyeColor.GRAY), TropicalFishEntity.pack(Type.KOB, DyeColor.ORANGE, DyeColor.WHITE), TropicalFishEntity.pack(Type.SPOTTY, DyeColor.PINK, DyeColor.LIGHT_BLUE), TropicalFishEntity.pack(Type.BLOCKFISH, DyeColor.PURPLE, DyeColor.YELLOW), TropicalFishEntity.pack(Type.CLAYFISH, DyeColor.WHITE, DyeColor.RED), TropicalFishEntity.pack(Type.SPOTTY, DyeColor.WHITE, DyeColor.YELLOW), TropicalFishEntity.pack(Type.GLITTER, DyeColor.WHITE, DyeColor.GRAY), TropicalFishEntity.pack(Type.CLAYFISH, DyeColor.WHITE, DyeColor.ORANGE), TropicalFishEntity.pack(Type.DASHER, DyeColor.CYAN, DyeColor.PINK), TropicalFishEntity.pack(Type.BRINELY, DyeColor.LIME, DyeColor.LIGHT_BLUE), TropicalFishEntity.pack(Type.BETTY, DyeColor.RED, DyeColor.WHITE), TropicalFishEntity.pack(Type.SNOOPER, DyeColor.GRAY, DyeColor.RED), TropicalFishEntity.pack(Type.BLOCKFISH, DyeColor.RED, DyeColor.WHITE), TropicalFishEntity.pack(Type.FLOPPER, DyeColor.WHITE, DyeColor.YELLOW), TropicalFishEntity.pack(Type.KOB, DyeColor.RED, DyeColor.WHITE), TropicalFishEntity.pack(Type.SUNSTREAK, DyeColor.GRAY, DyeColor.WHITE), TropicalFishEntity.pack(Type.DASHER, DyeColor.CYAN, DyeColor.YELLOW), TropicalFishEntity.pack(Type.FLOPPER, DyeColor.YELLOW, DyeColor.YELLOW)};
    private boolean field_204228_bA = true;

    private static int pack(Type type, DyeColor dyeColor, DyeColor dyeColor2) {
        return type.func_212550_a() & 0xFF | (type.func_212551_b() & 0xFF) << 8 | (dyeColor.getId() & 0xFF) << 16 | (dyeColor2.getId() & 0xFF) << 24;
    }

    public TropicalFishEntity(EntityType<? extends TropicalFishEntity> entityType, World world) {
        super((EntityType<? extends AbstractGroupFishEntity>)entityType, world);
    }

    public static String func_212324_b(int n) {
        return "entity.minecraft.tropical_fish.predefined." + n;
    }

    public static DyeColor func_212326_d(int n) {
        return DyeColor.byId(TropicalFishEntity.getBodyColor(n));
    }

    public static DyeColor func_212323_p(int n) {
        return DyeColor.byId(TropicalFishEntity.getPatternColor(n));
    }

    public static String func_212327_q(int n) {
        int n2 = TropicalFishEntity.func_212325_s(n);
        int n3 = TropicalFishEntity.getPattern(n);
        return "entity.minecraft.tropical_fish.type." + Type.func_212548_a(n2, n3);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setVariant(compoundNBT.getInt("Variant"));
    }

    public void setVariant(int n) {
        this.dataManager.set(VARIANT, n);
    }

    @Override
    public boolean isMaxGroupSize(int n) {
        return !this.field_204228_bA;
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    @Override
    protected void setBucketData(ItemStack itemStack) {
        super.setBucketData(itemStack);
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        compoundNBT.putInt("BucketVariantTag", this.getVariant());
    }

    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(Items.TROPICAL_FISH_BUCKET);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_TROPICAL_FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    private static int getBodyColor(int n) {
        return (n & 0xFF0000) >> 16;
    }

    public float[] func_204219_dC() {
        return DyeColor.byId(TropicalFishEntity.getBodyColor(this.getVariant())).getColorComponentValues();
    }

    private static int getPatternColor(int n) {
        return (n & 0xFF000000) >> 24;
    }

    public float[] func_204222_dD() {
        return DyeColor.byId(TropicalFishEntity.getPatternColor(this.getVariant())).getColorComponentValues();
    }

    public static int func_212325_s(int n) {
        return Math.min(n & 0xFF, 1);
    }

    public int getSize() {
        return TropicalFishEntity.func_212325_s(this.getVariant());
    }

    private static int getPattern(int n) {
        return Math.min((n & 0xFF00) >> 8, 5);
    }

    public ResourceLocation getPatternTexture() {
        return TropicalFishEntity.func_212325_s(this.getVariant()) == 0 ? PATTERN_TEXTURES_A[TropicalFishEntity.getPattern(this.getVariant())] : PATTERN_TEXTURES_B[TropicalFishEntity.getPattern(this.getVariant())];
    }

    public ResourceLocation getBodyTexture() {
        return BODY_TEXTURES[TropicalFishEntity.func_212325_s(this.getVariant())];
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        int n;
        int n2;
        int n3;
        int n4;
        iLivingEntityData = super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        if (compoundNBT != null && compoundNBT.contains("BucketVariantTag", 0)) {
            this.setVariant(compoundNBT.getInt("BucketVariantTag"));
            return iLivingEntityData;
        }
        if (iLivingEntityData instanceof TropicalFishData) {
            TropicalFishData tropicalFishData = (TropicalFishData)iLivingEntityData;
            n4 = tropicalFishData.size;
            n3 = tropicalFishData.pattern;
            n2 = tropicalFishData.bodyColor;
            n = tropicalFishData.patternColor;
        } else if ((double)this.rand.nextFloat() < 0.9) {
            int n5 = Util.getRandomInt(SPECIAL_VARIANTS, this.rand);
            n4 = n5 & 0xFF;
            n3 = (n5 & 0xFF00) >> 8;
            n2 = (n5 & 0xFF0000) >> 16;
            n = (n5 & 0xFF000000) >> 24;
            iLivingEntityData = new TropicalFishData(this, n4, n3, n2, n);
        } else {
            this.field_204228_bA = false;
            n4 = this.rand.nextInt(2);
            n3 = this.rand.nextInt(6);
            n2 = this.rand.nextInt(15);
            n = this.rand.nextInt(15);
        }
        this.setVariant(n4 | n3 << 8 | n2 << 16 | n << 24);
        return iLivingEntityData;
    }

    static enum Type {
        KOB(0, 0),
        SUNSTREAK(0, 1),
        SNOOPER(0, 2),
        DASHER(0, 3),
        BRINELY(0, 4),
        SPOTTY(0, 5),
        FLOPPER(1, 0),
        STRIPEY(1, 1),
        GLITTER(1, 2),
        BLOCKFISH(1, 3),
        BETTY(1, 4),
        CLAYFISH(1, 5);

        private final int field_212552_m;
        private final int field_212553_n;
        private static final Type[] field_212554_o;

        private Type(int n2, int n3) {
            this.field_212552_m = n2;
            this.field_212553_n = n3;
        }

        public int func_212550_a() {
            return this.field_212552_m;
        }

        public int func_212551_b() {
            return this.field_212553_n;
        }

        public static String func_212548_a(int n, int n2) {
            return field_212554_o[n2 + 6 * n].func_212549_c();
        }

        public String func_212549_c() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        static {
            field_212554_o = Type.values();
        }
    }

    static class TropicalFishData
    extends AbstractGroupFishEntity.GroupData {
        private final int size;
        private final int pattern;
        private final int bodyColor;
        private final int patternColor;

        private TropicalFishData(TropicalFishEntity tropicalFishEntity, int n, int n2, int n3, int n4) {
            super(tropicalFishEntity);
            this.size = n;
            this.pattern = n2;
            this.bodyColor = n3;
            this.patternColor = n4;
        }
    }
}

