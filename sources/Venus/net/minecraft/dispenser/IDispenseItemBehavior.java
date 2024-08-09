/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import java.util.List;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.WitherSkeletonSkullBlock;
import net.minecraft.dispenser.BeehiveDispenseBehavior;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.DispenseBoatBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ExperienceBottleEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public interface IDispenseItemBehavior {
    public static final IDispenseItemBehavior NOOP = IDispenseItemBehavior::lambda$static$0;

    public ItemStack dispense(IBlockSource var1, ItemStack var2);

    public static void init() {
        DispenserBlock.registerDispenseBehavior(Items.ARROW, new ProjectileDispenseBehavior(){

            @Override
            protected ProjectileEntity getProjectileEntity(World world, IPosition iPosition, ItemStack itemStack) {
                ArrowEntity arrowEntity = new ArrowEntity(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
                arrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
                return arrowEntity;
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.TIPPED_ARROW, new ProjectileDispenseBehavior(){

            @Override
            protected ProjectileEntity getProjectileEntity(World world, IPosition iPosition, ItemStack itemStack) {
                ArrowEntity arrowEntity = new ArrowEntity(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
                arrowEntity.setPotionEffect(itemStack);
                arrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
                return arrowEntity;
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.SPECTRAL_ARROW, new ProjectileDispenseBehavior(){

            @Override
            protected ProjectileEntity getProjectileEntity(World world, IPosition iPosition, ItemStack itemStack) {
                SpectralArrowEntity spectralArrowEntity = new SpectralArrowEntity(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
                spectralArrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
                return spectralArrowEntity;
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.EGG, new ProjectileDispenseBehavior(){

            @Override
            protected ProjectileEntity getProjectileEntity(World world, IPosition iPosition, ItemStack itemStack) {
                return Util.make(new EggEntity(world, iPosition.getX(), iPosition.getY(), iPosition.getZ()), arg_0 -> 4.lambda$getProjectileEntity$0(itemStack, arg_0));
            }

            private static void lambda$getProjectileEntity$0(ItemStack itemStack, EggEntity eggEntity) {
                eggEntity.setItem(itemStack);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.SNOWBALL, new ProjectileDispenseBehavior(){

            @Override
            protected ProjectileEntity getProjectileEntity(World world, IPosition iPosition, ItemStack itemStack) {
                return Util.make(new SnowballEntity(world, iPosition.getX(), iPosition.getY(), iPosition.getZ()), arg_0 -> 5.lambda$getProjectileEntity$0(itemStack, arg_0));
            }

            private static void lambda$getProjectileEntity$0(ItemStack itemStack, SnowballEntity snowballEntity) {
                snowballEntity.setItem(itemStack);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.EXPERIENCE_BOTTLE, new ProjectileDispenseBehavior(){

            @Override
            protected ProjectileEntity getProjectileEntity(World world, IPosition iPosition, ItemStack itemStack) {
                return Util.make(new ExperienceBottleEntity(world, iPosition.getX(), iPosition.getY(), iPosition.getZ()), arg_0 -> 6.lambda$getProjectileEntity$0(itemStack, arg_0));
            }

            @Override
            protected float getProjectileInaccuracy() {
                return super.getProjectileInaccuracy() * 0.5f;
            }

            @Override
            protected float getProjectileVelocity() {
                return super.getProjectileVelocity() * 1.25f;
            }

            private static void lambda$getProjectileEntity$0(ItemStack itemStack, ExperienceBottleEntity experienceBottleEntity) {
                experienceBottleEntity.setItem(itemStack);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.SPLASH_POTION, new IDispenseItemBehavior(){

            @Override
            public ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
                return new ProjectileDispenseBehavior(this){
                    final 7 this$0;
                    {
                        this.this$0 = var1_1;
                    }

                    @Override
                    protected ProjectileEntity getProjectileEntity(World world, IPosition iPosition, ItemStack itemStack) {
                        return Util.make(new PotionEntity(world, iPosition.getX(), iPosition.getY(), iPosition.getZ()), arg_0 -> 1.lambda$getProjectileEntity$0(itemStack, arg_0));
                    }

                    @Override
                    protected float getProjectileInaccuracy() {
                        return super.getProjectileInaccuracy() * 0.5f;
                    }

                    @Override
                    protected float getProjectileVelocity() {
                        return super.getProjectileVelocity() * 1.25f;
                    }

                    private static void lambda$getProjectileEntity$0(ItemStack itemStack, PotionEntity potionEntity) {
                        potionEntity.setItem(itemStack);
                    }
                }.dispense(iBlockSource, itemStack);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.LINGERING_POTION, new IDispenseItemBehavior(){

            @Override
            public ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
                return new ProjectileDispenseBehavior(this){
                    final 8 this$0;
                    {
                        this.this$0 = var1_1;
                    }

                    @Override
                    protected ProjectileEntity getProjectileEntity(World world, IPosition iPosition, ItemStack itemStack) {
                        return Util.make(new PotionEntity(world, iPosition.getX(), iPosition.getY(), iPosition.getZ()), arg_0 -> 1.lambda$getProjectileEntity$0(itemStack, arg_0));
                    }

                    @Override
                    protected float getProjectileInaccuracy() {
                        return super.getProjectileInaccuracy() * 0.5f;
                    }

                    @Override
                    protected float getProjectileVelocity() {
                        return super.getProjectileVelocity() * 1.25f;
                    }

                    private static void lambda$getProjectileEntity$0(ItemStack itemStack, PotionEntity potionEntity) {
                        potionEntity.setItem(itemStack);
                    }
                }.dispense(iBlockSource, itemStack);
            }
        });
        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior(){

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
                EntityType<?> entityType = ((SpawnEggItem)itemStack.getItem()).getType(itemStack.getTag());
                entityType.spawn(iBlockSource.getWorld(), itemStack, null, iBlockSource.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, true);
                itemStack.shrink(1);
                return itemStack;
            }
        };
        for (SpawnEggItem object2 : SpawnEggItem.getEggs()) {
            DispenserBlock.registerDispenseBehavior(object2, defaultDispenseItemBehavior);
        }
        DispenserBlock.registerDispenseBehavior(Items.ARMOR_STAND, new DefaultDispenseItemBehavior(){

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
                BlockPos blockPos = iBlockSource.getBlockPos().offset(direction);
                ServerWorld serverWorld = iBlockSource.getWorld();
                ArmorStandEntity armorStandEntity = new ArmorStandEntity(serverWorld, (double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5);
                EntityType.applyItemNBT(serverWorld, null, armorStandEntity, itemStack.getTag());
                armorStandEntity.rotationYaw = direction.getHorizontalAngle();
                serverWorld.addEntity(armorStandEntity);
                itemStack.shrink(1);
                return itemStack;
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.SADDLE, new OptionalDispenseBehavior(){

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
                List<LivingEntity> list = iBlockSource.getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockPos), 11::lambda$dispenseStack$0);
                if (!list.isEmpty()) {
                    ((IEquipable)((Object)list.get(0))).func_230266_a_(SoundCategory.BLOCKS);
                    itemStack.shrink(1);
                    this.setSuccessful(false);
                    return itemStack;
                }
                return super.dispenseStack(iBlockSource, itemStack);
            }

            private static boolean lambda$dispenseStack$0(LivingEntity livingEntity) {
                if (!(livingEntity instanceof IEquipable)) {
                    return true;
                }
                IEquipable iEquipable = (IEquipable)((Object)livingEntity);
                return !iEquipable.isHorseSaddled() && iEquipable.func_230264_L__();
            }
        });
        OptionalDispenseBehavior optionalDispenseBehavior = new OptionalDispenseBehavior(){

            @Override
            protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
                for (AbstractHorseEntity abstractHorseEntity : iBlockSource.getWorld().getEntitiesWithinAABB(AbstractHorseEntity.class, new AxisAlignedBB(blockPos), 12::lambda$dispenseStack$0)) {
                    if (!abstractHorseEntity.isArmor(itemStack) || abstractHorseEntity.func_230277_fr_() || !abstractHorseEntity.isTame()) continue;
                    abstractHorseEntity.replaceItemInInventory(401, itemStack.split(1));
                    this.setSuccessful(false);
                    return itemStack;
                }
                return super.dispenseStack(iBlockSource, itemStack);
            }

            private static boolean lambda$dispenseStack$0(AbstractHorseEntity abstractHorseEntity) {
                return abstractHorseEntity.isAlive() && abstractHorseEntity.func_230276_fq_();
            }
        };
        DispenserBlock.registerDispenseBehavior(Items.LEATHER_HORSE_ARMOR, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.IRON_HORSE_ARMOR, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.GOLDEN_HORSE_ARMOR, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.DIAMOND_HORSE_ARMOR, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.WHITE_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.ORANGE_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.CYAN_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.BLUE_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.BROWN_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.BLACK_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.GRAY_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.GREEN_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.LIGHT_BLUE_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.LIGHT_GRAY_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.LIME_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.MAGENTA_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.PINK_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.PURPLE_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.RED_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.YELLOW_CARPET, optionalDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(Items.CHEST, new OptionalDispenseBehavior(){

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
                for (AbstractChestedHorseEntity abstractChestedHorseEntity : iBlockSource.getWorld().getEntitiesWithinAABB(AbstractChestedHorseEntity.class, new AxisAlignedBB(blockPos), 13::lambda$dispenseStack$0)) {
                    if (!abstractChestedHorseEntity.isTame() || !abstractChestedHorseEntity.replaceItemInInventory(499, itemStack)) continue;
                    itemStack.shrink(1);
                    this.setSuccessful(false);
                    return itemStack;
                }
                return super.dispenseStack(iBlockSource, itemStack);
            }

            private static boolean lambda$dispenseStack$0(AbstractChestedHorseEntity abstractChestedHorseEntity) {
                return abstractChestedHorseEntity.isAlive() && !abstractChestedHorseEntity.hasChest();
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.FIREWORK_ROCKET, new DefaultDispenseItemBehavior(){

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity((World)iBlockSource.getWorld(), itemStack, iBlockSource.getX(), iBlockSource.getY(), iBlockSource.getX(), true);
                IDispenseItemBehavior.dispenseEntity(iBlockSource, fireworkRocketEntity, direction);
                fireworkRocketEntity.shoot(direction.getXOffset(), direction.getYOffset(), direction.getZOffset(), 0.5f, 1.0f);
                iBlockSource.getWorld().addEntity(fireworkRocketEntity);
                itemStack.shrink(1);
                return itemStack;
            }

            @Override
            protected void playDispenseSound(IBlockSource iBlockSource) {
                iBlockSource.getWorld().playEvent(1004, iBlockSource.getBlockPos(), 0);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.FIRE_CHARGE, new DefaultDispenseItemBehavior(){

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
                IPosition iPosition = DispenserBlock.getDispensePosition(iBlockSource);
                double d = iPosition.getX() + (double)((float)direction.getXOffset() * 0.3f);
                double d2 = iPosition.getY() + (double)((float)direction.getYOffset() * 0.3f);
                double d3 = iPosition.getZ() + (double)((float)direction.getZOffset() * 0.3f);
                ServerWorld serverWorld = iBlockSource.getWorld();
                Random random2 = serverWorld.rand;
                double d4 = random2.nextGaussian() * 0.05 + (double)direction.getXOffset();
                double d5 = random2.nextGaussian() * 0.05 + (double)direction.getYOffset();
                double d6 = random2.nextGaussian() * 0.05 + (double)direction.getZOffset();
                serverWorld.addEntity(Util.make(new SmallFireballEntity(serverWorld, d, d2, d3, d4, d5, d6), arg_0 -> 15.lambda$dispenseStack$0(itemStack, arg_0)));
                itemStack.shrink(1);
                return itemStack;
            }

            @Override
            protected void playDispenseSound(IBlockSource iBlockSource) {
                iBlockSource.getWorld().playEvent(1018, iBlockSource.getBlockPos(), 0);
            }

            private static void lambda$dispenseStack$0(ItemStack itemStack, SmallFireballEntity smallFireballEntity) {
                smallFireballEntity.setStack(itemStack);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.OAK_BOAT, new DispenseBoatBehavior(BoatEntity.Type.OAK));
        DispenserBlock.registerDispenseBehavior(Items.SPRUCE_BOAT, new DispenseBoatBehavior(BoatEntity.Type.SPRUCE));
        DispenserBlock.registerDispenseBehavior(Items.BIRCH_BOAT, new DispenseBoatBehavior(BoatEntity.Type.BIRCH));
        DispenserBlock.registerDispenseBehavior(Items.JUNGLE_BOAT, new DispenseBoatBehavior(BoatEntity.Type.JUNGLE));
        DispenserBlock.registerDispenseBehavior(Items.DARK_OAK_BOAT, new DispenseBoatBehavior(BoatEntity.Type.DARK_OAK));
        DispenserBlock.registerDispenseBehavior(Items.ACACIA_BOAT, new DispenseBoatBehavior(BoatEntity.Type.ACACIA));
        DefaultDispenseItemBehavior defaultDispenseItemBehavior2 = new DefaultDispenseItemBehavior(){
            private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                BucketItem bucketItem = (BucketItem)itemStack.getItem();
                BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
                ServerWorld serverWorld = iBlockSource.getWorld();
                if (bucketItem.tryPlaceContainedLiquid(null, serverWorld, blockPos, null)) {
                    bucketItem.onLiquidPlaced(serverWorld, itemStack, blockPos);
                    return new ItemStack(Items.BUCKET);
                }
                return this.defaultBehaviour.dispense(iBlockSource, itemStack);
            }
        };
        DispenserBlock.registerDispenseBehavior(Items.LAVA_BUCKET, defaultDispenseItemBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.WATER_BUCKET, defaultDispenseItemBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.SALMON_BUCKET, defaultDispenseItemBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.COD_BUCKET, defaultDispenseItemBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.PUFFERFISH_BUCKET, defaultDispenseItemBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.TROPICAL_FISH_BUCKET, defaultDispenseItemBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.BUCKET, new DefaultDispenseItemBehavior(){
            private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                BlockPos blockPos;
                ServerWorld serverWorld = iBlockSource.getWorld();
                BlockState blockState = serverWorld.getBlockState(blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING)));
                Block block = blockState.getBlock();
                if (block instanceof IBucketPickupHandler) {
                    Fluid fluid = ((IBucketPickupHandler)((Object)block)).pickupFluid(serverWorld, blockPos, blockState);
                    if (!(fluid instanceof FlowingFluid)) {
                        return super.dispenseStack(iBlockSource, itemStack);
                    }
                    Item item = fluid.getFilledBucket();
                    itemStack.shrink(1);
                    if (itemStack.isEmpty()) {
                        return new ItemStack(item);
                    }
                    if (((DispenserTileEntity)iBlockSource.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0) {
                        this.defaultBehaviour.dispense(iBlockSource, new ItemStack(item));
                    }
                    return itemStack;
                }
                return super.dispenseStack(iBlockSource, itemStack);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.FLINT_AND_STEEL, new OptionalDispenseBehavior(){

            @Override
            protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                ServerWorld serverWorld = iBlockSource.getWorld();
                this.setSuccessful(false);
                Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
                BlockPos blockPos = iBlockSource.getBlockPos().offset(direction);
                BlockState blockState = serverWorld.getBlockState(blockPos);
                if (AbstractFireBlock.canLightBlock(serverWorld, blockPos, direction)) {
                    serverWorld.setBlockState(blockPos, AbstractFireBlock.getFireForPlacement(serverWorld, blockPos));
                } else if (CampfireBlock.canBeLit(blockState)) {
                    serverWorld.setBlockState(blockPos, (BlockState)blockState.with(BlockStateProperties.LIT, true));
                } else if (blockState.getBlock() instanceof TNTBlock) {
                    TNTBlock.explode(serverWorld, blockPos);
                    serverWorld.removeBlock(blockPos, true);
                } else {
                    this.setSuccessful(true);
                }
                if (this.isSuccessful() && itemStack.attemptDamageItem(1, serverWorld.rand, null)) {
                    itemStack.setCount(0);
                }
                return itemStack;
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.BONE_MEAL, new OptionalDispenseBehavior(){

            @Override
            protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                this.setSuccessful(false);
                ServerWorld serverWorld = iBlockSource.getWorld();
                BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
                if (!BoneMealItem.applyBonemeal(itemStack, serverWorld, blockPos) && !BoneMealItem.growSeagrass(itemStack, serverWorld, blockPos, null)) {
                    this.setSuccessful(true);
                } else if (!serverWorld.isRemote) {
                    serverWorld.playEvent(2005, blockPos, 0);
                }
                return itemStack;
            }
        });
        DispenserBlock.registerDispenseBehavior(Blocks.TNT, new DefaultDispenseItemBehavior(){

            @Override
            protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                ServerWorld serverWorld = iBlockSource.getWorld();
                BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
                TNTEntity tNTEntity = new TNTEntity(serverWorld, (double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, null);
                serverWorld.addEntity(tNTEntity);
                ((World)serverWorld).playSound(null, tNTEntity.getPosX(), tNTEntity.getPosY(), tNTEntity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0f, 1.0f);
                itemStack.shrink(1);
                return itemStack;
            }
        });
        OptionalDispenseBehavior optionalDispenseBehavior2 = new OptionalDispenseBehavior(){

            @Override
            protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                this.setSuccessful(ArmorItem.func_226626_a_(iBlockSource, itemStack));
                return itemStack;
            }
        };
        DispenserBlock.registerDispenseBehavior(Items.CREEPER_HEAD, optionalDispenseBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.ZOMBIE_HEAD, optionalDispenseBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.DRAGON_HEAD, optionalDispenseBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.SKELETON_SKULL, optionalDispenseBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.PLAYER_HEAD, optionalDispenseBehavior2);
        DispenserBlock.registerDispenseBehavior(Items.WITHER_SKELETON_SKULL, new OptionalDispenseBehavior(){

            @Override
            protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                ServerWorld serverWorld = iBlockSource.getWorld();
                Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
                BlockPos blockPos = iBlockSource.getBlockPos().offset(direction);
                if (serverWorld.isAirBlock(blockPos) && WitherSkeletonSkullBlock.canSpawnMob(serverWorld, blockPos, itemStack)) {
                    serverWorld.setBlockState(blockPos, (BlockState)Blocks.WITHER_SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, direction.getAxis() == Direction.Axis.Y ? 0 : direction.getOpposite().getHorizontalIndex() * 4), 0);
                    TileEntity tileEntity = serverWorld.getTileEntity(blockPos);
                    if (tileEntity instanceof SkullTileEntity) {
                        WitherSkeletonSkullBlock.checkWitherSpawn(serverWorld, blockPos, (SkullTileEntity)tileEntity);
                    }
                    itemStack.shrink(1);
                    this.setSuccessful(false);
                } else {
                    this.setSuccessful(ArmorItem.func_226626_a_(iBlockSource, itemStack));
                }
                return itemStack;
            }
        });
        DispenserBlock.registerDispenseBehavior(Blocks.CARVED_PUMPKIN, new OptionalDispenseBehavior(){

            @Override
            protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                ServerWorld serverWorld = iBlockSource.getWorld();
                BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
                CarvedPumpkinBlock carvedPumpkinBlock = (CarvedPumpkinBlock)Blocks.CARVED_PUMPKIN;
                if (serverWorld.isAirBlock(blockPos) && carvedPumpkinBlock.canDispenserPlace(serverWorld, blockPos)) {
                    if (!serverWorld.isRemote) {
                        serverWorld.setBlockState(blockPos, carvedPumpkinBlock.getDefaultState(), 0);
                    }
                    itemStack.shrink(1);
                    this.setSuccessful(false);
                } else {
                    this.setSuccessful(ArmorItem.func_226626_a_(iBlockSource, itemStack));
                }
                return itemStack;
            }
        });
        DispenserBlock.registerDispenseBehavior(Blocks.SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
        for (DyeColor dyeColor : DyeColor.values()) {
            DispenserBlock.registerDispenseBehavior(ShulkerBoxBlock.getBlockByColor(dyeColor).asItem(), new ShulkerBoxDispenseBehavior());
        }
        DispenserBlock.registerDispenseBehavior(Items.GLASS_BOTTLE.asItem(), new OptionalDispenseBehavior(){
            private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();

            private ItemStack glassBottleFill(IBlockSource iBlockSource, ItemStack itemStack, ItemStack itemStack2) {
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    return itemStack2.copy();
                }
                if (((DispenserTileEntity)iBlockSource.getBlockTileEntity()).addItemStack(itemStack2.copy()) < 0) {
                    this.defaultBehaviour.dispense(iBlockSource, itemStack2.copy());
                }
                return itemStack;
            }

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                this.setSuccessful(true);
                ServerWorld serverWorld = iBlockSource.getWorld();
                BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
                BlockState blockState = serverWorld.getBlockState(blockPos);
                if (blockState.isInAndMatches(BlockTags.BEEHIVES, 24::lambda$dispenseStack$0) && blockState.get(BeehiveBlock.HONEY_LEVEL) >= 5) {
                    ((BeehiveBlock)blockState.getBlock()).takeHoney(serverWorld, blockState, blockPos, null, BeehiveTileEntity.State.BEE_RELEASED);
                    this.setSuccessful(false);
                    return this.glassBottleFill(iBlockSource, itemStack, new ItemStack(Items.HONEY_BOTTLE));
                }
                if (serverWorld.getFluidState(blockPos).isTagged(FluidTags.WATER)) {
                    this.setSuccessful(false);
                    return this.glassBottleFill(iBlockSource, itemStack, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER));
                }
                return super.dispenseStack(iBlockSource, itemStack);
            }

            private static boolean lambda$dispenseStack$0(AbstractBlock.AbstractBlockState abstractBlockState) {
                return abstractBlockState.hasProperty(BeehiveBlock.HONEY_LEVEL);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.GLOWSTONE, new OptionalDispenseBehavior(){

            @Override
            public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
                Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
                BlockPos blockPos = iBlockSource.getBlockPos().offset(direction);
                ServerWorld serverWorld = iBlockSource.getWorld();
                BlockState blockState = serverWorld.getBlockState(blockPos);
                this.setSuccessful(false);
                if (blockState.isIn(Blocks.RESPAWN_ANCHOR)) {
                    if (blockState.get(RespawnAnchorBlock.CHARGES) != 4) {
                        RespawnAnchorBlock.chargeAnchor(serverWorld, blockPos, blockState);
                        itemStack.shrink(1);
                    } else {
                        this.setSuccessful(true);
                    }
                    return itemStack;
                }
                return super.dispenseStack(iBlockSource, itemStack);
            }
        });
        DispenserBlock.registerDispenseBehavior(Items.SHEARS.asItem(), new BeehiveDispenseBehavior());
    }

    public static void dispenseEntity(IBlockSource iBlockSource, Entity entity2, Direction direction) {
        entity2.setPosition(iBlockSource.getX() + (double)direction.getXOffset() * (0.5000099999997474 - (double)entity2.getWidth() / 2.0), iBlockSource.getY() + (double)direction.getYOffset() * (0.5000099999997474 - (double)entity2.getHeight() / 2.0) - (double)entity2.getHeight() / 2.0, iBlockSource.getZ() + (double)direction.getZOffset() * (0.5000099999997474 - (double)entity2.getWidth() / 2.0));
    }

    private static ItemStack lambda$static$0(IBlockSource iBlockSource, ItemStack itemStack) {
        return itemStack;
    }
}

