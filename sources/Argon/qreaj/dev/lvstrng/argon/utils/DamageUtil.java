// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import dev.lvstrng.argon.Argon;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameMode;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Objects;

@SuppressWarnings("all")
public final class DamageUtil {
    public static GameMode method262(final PlayerEntity player) {
        final int n = 0;
        final PlayerListEntry playerListEntry = Argon.mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        final int n2 = n;
        final PlayerListEntry playerListEntry2 = playerListEntry;
        if (n2 == 0 && playerListEntry2 == null) {
            return GameMode.SPECTATOR;
        }
        return playerListEntry2.getGameMode();
    }

    public static double method263(final PlayerEntity player, final Vec3d crystal, final boolean predictMovement, final BlockPos obsdianPos, final boolean ignoreTerrain) {
        if (player == null) {
            return 0.0;
        }
        if (method262(player) == GameMode.CREATIVE) {
            return 0.0;
        }
        Vec3d vec3d = new Vec3d(player.getPos().x, player.getPos().y, player.getPos().z);
        if (predictMovement) {
            vec3d = new Vec3d(vec3d.x + player.getVelocity().x, vec3d.y + player.getVelocity().y, vec3d.z + player.getVelocity().z);
        }
        final double sqrt = Math.sqrt(vec3d.squaredDistanceTo(crystal));
        if (sqrt > 12.0) {
            return 0.0;
        }
        final double n = (1.0 - sqrt / 12.0) * method267(crystal, player, predictMovement, obsdianPos, ignoreTerrain);
        final double method265 = method265(player, method266(player, net.minecraft.entity.DamageUtil.getDamageLeft((float) method264((n * n + n) / 2.0 * 7.0 * 12.0 + 1.0), (float) player.getArmor(), (float) player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue())), new Explosion((World) Argon.mc.world, (Entity) null, crystal.x, crystal.y, crystal.z, 6.0f, false, Explosion.DestructionType.DESTROY));
        return (method265 < 0.0) ? 0.0 : method265;
    }

    private static double method264(final double b) {
        return switch (Argon.mc.world.getDifficulty().ordinal()) {
            case 1 -> 0.0;
            case 2 -> Math.min(b / 2.0 + 1.0, b);
            case 3 -> b * 3.0 / 2.0;
            default -> b;
        };
    }

    private static double method265(final Entity entity, double n, final Explosion explosion) {
        final int n2 = 0;
        int protectionAmount = EnchantmentHelper.getProtectionAmount(entity.getArmorItems(), Argon.mc.world.getDamageSources().explosion(explosion));
        final int n3 = n2;
        double n5;
        final int n4 = (int) (n5 = protectionAmount);
        double n6 = 0.0;
        if (n3 == 0) {
            if (n4 > 20) {
                protectionAmount = 20;
            }
            n = (n6 = n * (1.0 - protectionAmount / 25.0));
            if (n3 != 0) {
                return n6;
            }
        }
        return n6;
    }

    private static double method266(final LivingEntity livingEntity, double n) {
        if (livingEntity.hasStatusEffect(StatusEffects.RESISTANCE)) {
            n *= 1.0 - (livingEntity.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 0.2;
        }
        return Math.max(n, 0.0);
    }

    private static double method267(final Vec3d vec3d, final Entity entity, final boolean b, final BlockPos blockPos, final boolean b2) {
        Box box = entity.getBoundingBox();
        final int n2 = 0;
        if (b) {
            final Vec3d velocity = entity.getVelocity();
            box = box.offset(velocity.x, velocity.y, velocity.z);
        }
        final double n3 = 1.0 / ((box.maxX - box.minX) * 2.0 + 1.0);
        final double n4 = 1.0 / ((box.maxY - box.minY) * 2.0 + 1.0);
        final double n5 = 1.0 / ((box.maxZ - box.minZ) * 2.0 + 1.0);
        final double n6 = (1.0 - Math.floor(1.0 / n3) * n3) / 2.0;
        final double n7 = (1.0 - Math.floor(1.0 / n5) * n5) / 2.0;
        final double n8 = n3;
        if (n2 == 0) {
            if (n8 >= 0.0) {
                final double n9 = n4;
                if (n2 == 0) {
                    if (n9 >= 0.0) {
                        final double n10 = n5;
                        if (n2 == 0) {
                            if (n10 >= 0.0) {
                                int n11 = 0;
                                int n12 = 0;
                                double n13 = 0.0;
                                double n14 = 0.0;
                                Label_0376_Outer:
                                while (n13 <= 1.0) {
                                    n14 = 0.0;
                                    if (n2 == 0) {
                                        double n15 = n14;
                                        Label_0201:
                                        while (true) {
                                            Label_0366_Outer:
                                            while (n15 <= 1.0) {
                                                final double n16 = 0.0;
                                                if (n2 == 0) {
                                                    double n17 = n16;
                                                    while (true) {
                                                        while (n17 <= 1.0) {
                                                            final RaycastContext raycastContext = new RaycastContext(new Vec3d(MathHelper.lerp(n13, box.minX, box.maxX) + n6, MathHelper.lerp(n15, box.minY, box.maxY), MathHelper.lerp(n17, box.minZ, box.maxZ) + n7), vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity);
                                                            if (n2 == 0) {
                                                                if (n2 == 0) {
                                                                    if (method268(raycastContext, blockPos, b2).getType() == HitResult.Type.MISS) {
                                                                        ++n11;
                                                                    }
                                                                    ++n12;
                                                                    n17 += n5;
                                                                }
                                                                if (n2 != 0) {
                                                                    break;
                                                                }
                                                                continue Label_0366_Outer;
                                                            } else {
                                                                if (n2 != 0) {
                                                                    break Label_0366_Outer;
                                                                }
                                                                continue Label_0201;
                                                            }
                                                        }
                                                        n15 += n4;
                                                        continue Label_0376_Outer;
                                                    }
                                                }
                                                n13 = n16;
                                                if (n2 != 0) {
                                                    break Label_0376_Outer;
                                                }
                                                continue Label_0376_Outer;
                                            }
                                            continue;
                                        }
                                    }
                                    return n14;
                                }
                                final double n18 = n11 / (double) n12;
                                return n14;
                            }
                        }
                    }
                }
            }
        }
        return n8;
    }

    private static BlockHitResult method268(final RaycastContext raycastContext, final BlockPos blockPos, final boolean b) {
        return (BlockHitResult) BlockView.raycast(raycastContext.getStart(), raycastContext.getEnd(), (Object) raycastContext, (a, nb) -> method272(nb, b, raycastContext, blockPos), a -> method271(raycastContext));
    }

    public static double method269(final PlayerEntity entity, final boolean charged) {
        double n = 0.0;
        if (charged) {
            if (entity.getActiveItem().getItem() == Items.NETHERITE_SWORD) {
                n += 8.0;
            } else if (entity.getActiveItem().getItem() == Items.DIAMOND_SWORD) {
                n += 7.0;
            } else if (entity.getActiveItem().getItem() == Items.GOLDEN_SWORD) {
                n += 4.0;
            } else if (entity.getActiveItem().getItem() == Items.IRON_SWORD) {
                n += 6.0;
            } else if (entity.getActiveItem().getItem() == Items.STONE_SWORD) {
                n += 5.0;
            } else if (entity.getActiveItem().getItem() == Items.WOODEN_SWORD) {
                n += 4.0;
            }
            n *= 1.5;
        }
        if (entity.getActiveItem().getEnchantments() != null && EnchantmentHelper.get(entity.getActiveItem()).containsKey(Enchantments.SHARPNESS)) {
            n += 0.5 * EnchantmentHelper.getLevel(Enchantments.SHARPNESS, entity.getActiveItem()) + 0.5;
        }
        if (entity.getActiveStatusEffects().containsKey(StatusEffects.STRENGTH)) {
            n += 3 * (Objects.requireNonNull(entity.getStatusEffect(StatusEffects.STRENGTH)).getAmplifier() + 1);
        }
        final double method270 = method270(entity, net.minecraft.entity.DamageUtil.getDamageLeft((float) method266(entity, n), (float) entity.getArmor(), (float) entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue()));
        return Math.max(method270, 0.0);
    }

    private static double method270(final Entity entity, double n) {
        final int n2 = 0;
        int protectionAmount = EnchantmentHelper.getProtectionAmount(entity.getArmorItems(), Argon.mc.world.getDamageSources().generic());
        final int n3 = n2;
        double n5;
        final int n4 = (int) (n5 = protectionAmount);
        double n6 = 0.0;
        if (n3 == 0) {
            if (n4 > 20) {
                protectionAmount = 20;
            }
            n = (n6 = n * (1.0 - protectionAmount / 25.0));
            if (n3 != 0) {
                return n6;
            }
        }
        return n6;
    }

    private static BlockHitResult method271(final RaycastContext raycastContext) {
        final Vec3d subtract = raycastContext.getStart().subtract(raycastContext.getEnd());
        return BlockHitResult.createMissed(raycastContext.getEnd(), Direction.getFacing(subtract.x, subtract.y, subtract.z), BlockPos.ofFloored(raycastContext.getEnd()));
    }

    private static BlockHitResult method272(final BlockPos blockPos, final boolean b, final RaycastContext raycastContext, final BlockPos blockPos2) {
        BlockState blockState;
        if (blockPos2.equals(blockPos)) {
            blockState = Blocks.OBSIDIAN.getDefaultState();
        } else {
            blockState = Argon.mc.world.getBlockState(blockPos2);
            if (blockState.getBlock().getBlastResistance() < 600.0f && b) {
                blockState = Blocks.AIR.getDefaultState();
            }
        }
        final Vec3d start = raycastContext.getStart();
        final Vec3d end = raycastContext.getEnd();
        final BlockHitResult raycastBlock = Argon.mc.world.raycastBlock(start, end, blockPos2, raycastContext.getBlockShape(blockState, Argon.mc.world, blockPos2), blockState);
        final BlockHitResult raycast = VoxelShapes.empty().raycast(start, end, blockPos2);
        return (((raycastBlock == null) ? Double.MAX_VALUE : raycastContext.getStart().squaredDistanceTo(raycastBlock.getPos())) <= ((raycast == null) ? Double.MAX_VALUE : raycastContext.getStart().squaredDistanceTo(raycast.getPos()))) ? raycastBlock : raycast;
    }
}
