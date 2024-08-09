/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ItemModelsProperties {
    private static final Map<ResourceLocation, IItemPropertyGetter> GLOBAL_PROPERTY_MAP = Maps.newHashMap();
    private static final ResourceLocation DAMAGED = new ResourceLocation("damaged");
    private static final ResourceLocation DAMAGE = new ResourceLocation("damage");
    private static final IItemPropertyGetter field_239413_d_ = ItemModelsProperties::lambda$static$0;
    private static final IItemPropertyGetter field_239414_e_ = ItemModelsProperties::lambda$static$1;
    private static final Map<Item, Map<ResourceLocation, IItemPropertyGetter>> ITEM_PROPERTY_MAP = Maps.newHashMap();

    private static IItemPropertyGetter registerGlobalProperty(ResourceLocation resourceLocation, IItemPropertyGetter iItemPropertyGetter) {
        GLOBAL_PROPERTY_MAP.put(resourceLocation, iItemPropertyGetter);
        return iItemPropertyGetter;
    }

    private static void registerProperty(Item item, ResourceLocation resourceLocation, IItemPropertyGetter iItemPropertyGetter) {
        ITEM_PROPERTY_MAP.computeIfAbsent(item, ItemModelsProperties::lambda$registerProperty$2).put(resourceLocation, iItemPropertyGetter);
    }

    @Nullable
    public static IItemPropertyGetter func_239417_a_(Item item, ResourceLocation resourceLocation) {
        IItemPropertyGetter iItemPropertyGetter;
        if (item.getMaxDamage() > 0) {
            if (DAMAGE.equals(resourceLocation)) {
                return field_239414_e_;
            }
            if (DAMAGED.equals(resourceLocation)) {
                return field_239413_d_;
            }
        }
        if ((iItemPropertyGetter = GLOBAL_PROPERTY_MAP.get(resourceLocation)) != null) {
            return iItemPropertyGetter;
        }
        Map<ResourceLocation, IItemPropertyGetter> map = ITEM_PROPERTY_MAP.get(item);
        return map == null ? null : map.get(resourceLocation);
    }

    private static float lambda$static$15(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0f : 0.0f;
    }

    private static float lambda$static$14(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0f : 0.0f;
    }

    private static float lambda$static$13(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        boolean bl;
        if (livingEntity == null) {
            return 0.0f;
        }
        boolean bl2 = livingEntity.getHeldItemMainhand() == itemStack;
        boolean bl3 = bl = livingEntity.getHeldItemOffhand() == itemStack;
        if (livingEntity.getHeldItemMainhand().getItem() instanceof FishingRodItem) {
            bl = false;
        }
        return (bl2 || bl) && livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).fishingBobber != null ? 1.0f : 0.0f;
    }

    private static float lambda$static$12(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return ElytraItem.isUsable(itemStack) ? 0.0f : 1.0f;
    }

    private static float lambda$static$11(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return livingEntity != null && CrossbowItem.isCharged(itemStack) && CrossbowItem.hasChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0f : 0.0f;
    }

    private static float lambda$static$10(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return livingEntity != null && CrossbowItem.isCharged(itemStack) ? 1.0f : 0.0f;
    }

    private static float lambda$static$9(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0f : 0.0f;
    }

    private static float lambda$static$8(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        if (livingEntity == null) {
            return 0.0f;
        }
        return CrossbowItem.isCharged(itemStack) ? 0.0f : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / (float)CrossbowItem.getChargeTime(itemStack);
    }

    private static float lambda$static$7(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0f : 0.0f;
    }

    private static float lambda$static$6(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        if (livingEntity == null) {
            return 0.0f;
        }
        return livingEntity.getActiveItemStack() != itemStack ? 0.0f : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0f;
    }

    private static float lambda$static$5(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return itemStack.hasTag() ? (float)itemStack.getTag().getInt("CustomModelData") : 0.0f;
    }

    private static float lambda$static$4(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return livingEntity instanceof PlayerEntity ? ((PlayerEntity)livingEntity).getCooldownTracker().getCooldown(itemStack.getItem(), 0.0f) : 0.0f;
    }

    private static float lambda$static$3(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return livingEntity != null && livingEntity.getPrimaryHand() != HandSide.RIGHT ? 1.0f : 0.0f;
    }

    private static Map lambda$registerProperty$2(Item item) {
        return Maps.newHashMap();
    }

    private static float lambda$static$1(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return MathHelper.clamp((float)itemStack.getDamage() / (float)itemStack.getMaxDamage(), 0.0f, 1.0f);
    }

    private static float lambda$static$0(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        return itemStack.isDamaged() ? 1.0f : 0.0f;
    }

    static {
        ItemModelsProperties.registerGlobalProperty(new ResourceLocation("lefthanded"), ItemModelsProperties::lambda$static$3);
        ItemModelsProperties.registerGlobalProperty(new ResourceLocation("cooldown"), ItemModelsProperties::lambda$static$4);
        ItemModelsProperties.registerGlobalProperty(new ResourceLocation("custom_model_data"), ItemModelsProperties::lambda$static$5);
        ItemModelsProperties.registerProperty(Items.BOW, new ResourceLocation("pull"), ItemModelsProperties::lambda$static$6);
        ItemModelsProperties.registerProperty(Items.BOW, new ResourceLocation("pulling"), ItemModelsProperties::lambda$static$7);
        ItemModelsProperties.registerProperty(Items.CLOCK, new ResourceLocation("time"), new IItemPropertyGetter(){
            private double field_239435_a_;
            private double field_239436_b_;
            private long field_239437_c_;

            @Override
            public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity) {
                Entity entity2;
                Entity entity3 = entity2 = livingEntity != null ? livingEntity : itemStack.getAttachedEntity();
                if (entity2 == null) {
                    return 0.0f;
                }
                if (clientWorld == null && entity2.world instanceof ClientWorld) {
                    clientWorld = (ClientWorld)entity2.world;
                }
                if (clientWorld == null) {
                    return 0.0f;
                }
                double d = clientWorld.getDimensionType().isNatural() ? (double)clientWorld.func_242415_f(1.0f) : Math.random();
                d = this.func_239438_a_(clientWorld, d);
                return (float)d;
            }

            private double func_239438_a_(World world, double d) {
                if (world.getGameTime() != this.field_239437_c_) {
                    this.field_239437_c_ = world.getGameTime();
                    double d2 = d - this.field_239435_a_;
                    d2 = MathHelper.positiveModulo(d2 + 0.5, 1.0) - 0.5;
                    this.field_239436_b_ += d2 * 0.1;
                    this.field_239436_b_ *= 0.9;
                    this.field_239435_a_ = MathHelper.positiveModulo(this.field_239435_a_ + this.field_239436_b_, 1.0);
                }
                return this.field_239435_a_;
            }
        });
        ItemModelsProperties.registerProperty(Items.COMPASS, new ResourceLocation("angle"), new IItemPropertyGetter(){
            private final Angle field_239439_a_ = new Angle();
            private final Angle field_239440_b_ = new Angle();

            @Override
            public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity) {
                Entity entity2;
                Entity entity3 = entity2 = livingEntity != null ? livingEntity : itemStack.getAttachedEntity();
                if (entity2 == null) {
                    return 0.0f;
                }
                if (clientWorld == null && entity2.world instanceof ClientWorld) {
                    clientWorld = (ClientWorld)entity2.world;
                }
                BlockPos blockPos = CompassItem.func_234670_d_(itemStack) ? this.func_239442_a_(clientWorld, itemStack.getOrCreateTag()) : this.func_239444_a_(clientWorld);
                long l = clientWorld.getGameTime();
                if (blockPos != null && !(entity2.getPositionVec().squareDistanceTo((double)blockPos.getX() + 0.5, entity2.getPositionVec().getY(), (double)blockPos.getZ() + 0.5) < (double)1.0E-5f)) {
                    double d;
                    boolean bl = livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).isUser();
                    double d2 = 0.0;
                    if (bl) {
                        d2 = livingEntity.rotationYaw;
                    } else if (entity2 instanceof ItemFrameEntity) {
                        d2 = this.func_239441_a_((ItemFrameEntity)entity2);
                    } else if (entity2 instanceof ItemEntity) {
                        d2 = 180.0f - ((ItemEntity)entity2).getItemHover(0.5f) / ((float)Math.PI * 2) * 360.0f;
                    } else if (livingEntity != null) {
                        d2 = livingEntity.renderYawOffset;
                    }
                    d2 = MathHelper.positiveModulo(d2 / 360.0, 1.0);
                    double d3 = this.func_239443_a_(Vector3d.copyCentered(blockPos), entity2) / 6.2831854820251465;
                    if (bl) {
                        if (this.field_239439_a_.func_239448_a_(l)) {
                            this.field_239439_a_.func_239449_a_(l, 0.5 - (d2 - 0.25));
                        }
                        d = d3 + this.field_239439_a_.field_239445_a_;
                    } else {
                        d = 0.5 - (d2 - 0.25 - d3);
                    }
                    return MathHelper.positiveModulo((float)d, 1.0f);
                }
                if (this.field_239440_b_.func_239448_a_(l)) {
                    this.field_239440_b_.func_239449_a_(l, Math.random());
                }
                double d = this.field_239440_b_.field_239445_a_ + (double)((float)itemStack.hashCode() / 2.14748365E9f);
                return MathHelper.positiveModulo((float)d, 1.0f);
            }

            @Nullable
            private BlockPos func_239444_a_(ClientWorld clientWorld) {
                return clientWorld.getDimensionType().isNatural() ? clientWorld.func_239140_u_() : null;
            }

            @Nullable
            private BlockPos func_239442_a_(World world, CompoundNBT compoundNBT) {
                Optional<RegistryKey<World>> optional;
                boolean bl = compoundNBT.contains("LodestonePos");
                boolean bl2 = compoundNBT.contains("LodestoneDimension");
                if (bl && bl2 && (optional = CompassItem.func_234667_a_(compoundNBT)).isPresent() && world.getDimensionKey() == optional.get()) {
                    return NBTUtil.readBlockPos(compoundNBT.getCompound("LodestonePos"));
                }
                return null;
            }

            private double func_239441_a_(ItemFrameEntity itemFrameEntity) {
                Direction direction = itemFrameEntity.getHorizontalFacing();
                int n = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getOffset() : 0;
                return MathHelper.wrapDegrees(180 + direction.getHorizontalIndex() * 90 + itemFrameEntity.getRotation() * 45 + n);
            }

            private double func_239443_a_(Vector3d vector3d, Entity entity2) {
                return Math.atan2(vector3d.getZ() - entity2.getPosZ(), vector3d.getX() - entity2.getPosX());
            }
        });
        ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("pull"), ItemModelsProperties::lambda$static$8);
        ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("pulling"), ItemModelsProperties::lambda$static$9);
        ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("charged"), ItemModelsProperties::lambda$static$10);
        ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("firework"), ItemModelsProperties::lambda$static$11);
        ItemModelsProperties.registerProperty(Items.ELYTRA, new ResourceLocation("broken"), ItemModelsProperties::lambda$static$12);
        ItemModelsProperties.registerProperty(Items.FISHING_ROD, new ResourceLocation("cast"), ItemModelsProperties::lambda$static$13);
        ItemModelsProperties.registerProperty(Items.SHIELD, new ResourceLocation("blocking"), ItemModelsProperties::lambda$static$14);
        ItemModelsProperties.registerProperty(Items.TRIDENT, new ResourceLocation("throwing"), ItemModelsProperties::lambda$static$15);
    }

    static class Angle {
        private double field_239445_a_;
        private double field_239446_b_;
        private long field_239447_c_;

        private Angle() {
        }

        private boolean func_239448_a_(long l) {
            return this.field_239447_c_ != l;
        }

        private void func_239449_a_(long l, double d) {
            this.field_239447_c_ = l;
            double d2 = d - this.field_239445_a_;
            d2 = MathHelper.positiveModulo(d2 + 0.5, 1.0) - 0.5;
            this.field_239446_b_ += d2 * 0.1;
            this.field_239446_b_ *= 0.8;
            this.field_239445_a_ = MathHelper.positiveModulo(this.field_239445_a_ + this.field_239446_b_, 1.0);
        }
    }
}

