package net.minecraft.entity.monster;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.storage.loot.LootTableList;

public enum SkeletonType
{
    NORMAL("Skeleton", LootTableList.ENTITIES_SKELETON),
    WITHER("WitherSkeleton", LootTableList.ENTITIES_WITHER_SKELETON),
    STRAY("Stray", LootTableList.field_189968_an);

    private final TextComponentTranslation field_190140_d;
    private final ResourceLocation field_190141_e;

    private SkeletonType(String p_i47153_3_, ResourceLocation p_i47153_4_)
    {
        this.field_190140_d = new TextComponentTranslation("entity." + p_i47153_3_ + ".name", new Object[0]);
        this.field_190141_e = p_i47153_4_;
    }

    public int func_190135_a()
    {
        return this.ordinal();
    }

    public static SkeletonType func_190134_a(int p_190134_0_)
    {
        return values()[p_190134_0_];
    }

    public ResourceLocation func_190129_c()
    {
        return this.field_190141_e;
    }

    public SoundEvent func_190136_d()
    {
        switch (this)
        {
            case WITHER:
                return SoundEvents.field_190036_ha;

            case STRAY:
                return SoundEvents.field_190032_gu;

            default:
                return SoundEvents.ENTITY_SKELETON_AMBIENT;
        }
    }

    public SoundEvent func_190132_e()
    {
        switch (this)
        {
            case WITHER:
                return SoundEvents.field_190038_hc;

            case STRAY:
                return SoundEvents.field_190034_gw;

            default:
                return SoundEvents.ENTITY_SKELETON_HURT;
        }
    }

    public SoundEvent func_190133_f()
    {
        switch (this)
        {
            case WITHER:
                return SoundEvents.field_190037_hb;

            case STRAY:
                return SoundEvents.field_190033_gv;

            default:
                return SoundEvents.ENTITY_SKELETON_DEATH;
        }
    }

    public SoundEvent func_190131_g()
    {
        switch (this)
        {
            case WITHER:
                return SoundEvents.field_190039_hd;

            case STRAY:
                return SoundEvents.field_190035_gx;

            default:
                return SoundEvents.ENTITY_SKELETON_STEP;
        }
    }
}
