package net.minecraft.init;

import net.minecraft.stats.*;
import org.apache.logging.log4j.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import com.mojang.authlib.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;

public class Bootstrap
{
    private static final Logger LOGGER;
    private static boolean alreadyRegistered;
    private static final String[] I;
    private static final PrintStream SYSOUT;
    
    public static boolean isRegistered() {
        return Bootstrap.alreadyRegistered;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u000b%%\u0016+\n", "XqaSy");
        Bootstrap.I[" ".length()] = I("\u001a\u0012\u000e>-\u001d", "IFJqx");
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void printToSYSOUT(final String s) {
        Bootstrap.SYSOUT.println(s);
    }
    
    public static void register() {
        if (!Bootstrap.alreadyRegistered) {
            Bootstrap.alreadyRegistered = (" ".length() != 0);
            if (Bootstrap.LOGGER.isDebugEnabled()) {
                redirectOutputToLog();
            }
            Block.registerBlocks();
            BlockFire.init();
            Item.registerItems();
            StatList.init();
            registerDispenserBehaviors();
        }
    }
    
    static {
        I();
        SYSOUT = System.out;
        Bootstrap.alreadyRegistered = ("".length() != 0);
        LOGGER = LogManager.getLogger();
    }
    
    private static void redirectOutputToLog() {
        System.setErr(new LoggingPrintStream(Bootstrap.I["".length()], System.err));
        System.setOut(new LoggingPrintStream(Bootstrap.I[" ".length()], Bootstrap.SYSOUT));
    }
    
    static void registerDispenserBehaviors() {
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense() {
            @Override
            protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                final EntityArrow entityArrow = new EntityArrow(world, position.getX(), position.getY(), position.getZ());
                entityArrow.canBePickedUp = " ".length();
                return entityArrow;
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
                    if (3 == -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense() {
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
                    if (4 <= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                return new EntityEgg(world, position.getX(), position.getY(), position.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense() {
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
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                return new EntitySnowball(world, position.getX(), position.getY(), position.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense() {
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
                    if (3 < 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected float func_82498_a() {
                return super.func_82498_a() * 0.5f;
            }
            
            @Override
            protected float func_82500_b() {
                return super.func_82500_b() * 1.25f;
            }
            
            @Override
            protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                return new EntityExpBottle(world, position.getX(), position.getY(), position.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new IBehaviorDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();
            
            @Override
            public ItemStack dispense(final IBlockSource blockSource, final ItemStack itemStack) {
                ItemStack itemStack2;
                if (ItemPotion.isSplash(itemStack.getMetadata())) {
                    itemStack2 = new BehaviorProjectileDispense(this, itemStack) {
                        final Bootstrap$5 this$1;
                        private final ItemStack val$stack;
                        
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
                                if (3 < -1) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        @Override
                        protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                            return new EntityPotion(world, position.getX(), position.getY(), position.getZ(), this.val$stack.copy());
                        }
                        
                        @Override
                        protected float func_82498_a() {
                            return super.func_82498_a() * 0.5f;
                        }
                        
                        @Override
                        protected float func_82500_b() {
                            return super.func_82500_b() * 1.25f;
                        }
                    }.dispense(blockSource, itemStack);
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else {
                    itemStack2 = this.field_150843_b.dispense(blockSource, itemStack);
                }
                return itemStack2;
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem() {
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final Entity spawnCreature = ItemMonsterPlacer.spawnCreature(blockSource.getWorld(), itemStack.getMetadata(), blockSource.getX() + facing.getFrontOffsetX(), blockSource.getBlockPos().getY() + 0.2f, blockSource.getZ() + facing.getFrontOffsetZ());
                if (spawnCreature instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                    ((EntityLiving)spawnCreature).setCustomNameTag(itemStack.getDisplayName());
                }
                itemStack.splitStack(" ".length());
                return itemStack;
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
                    if (4 == 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem() {
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
                    if (2 != 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                blockSource.getWorld().spawnEntityInWorld(new EntityFireworkRocket(blockSource.getWorld(), blockSource.getX() + facing.getFrontOffsetX(), blockSource.getBlockPos().getY() + 0.2f, blockSource.getZ() + facing.getFrontOffsetZ(), itemStack));
                itemStack.splitStack(" ".length());
                return itemStack;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(281 + 178 + 36 + 507, blockSource.getBlockPos(), "".length());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem() {
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(151 + 442 - 172 + 588, blockSource.getBlockPos(), "".length());
            }
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final IPosition dispensePosition = BlockDispenser.getDispensePosition(blockSource);
                final double n = dispensePosition.getX() + facing.getFrontOffsetX() * 0.3f;
                final double n2 = dispensePosition.getY() + facing.getFrontOffsetY() * 0.3f;
                final double n3 = dispensePosition.getZ() + facing.getFrontOffsetZ() * 0.3f;
                final World world = blockSource.getWorld();
                final Random rand = world.rand;
                world.spawnEntityInWorld(new EntitySmallFireball(world, n, n2, n3, rand.nextGaussian() * 0.05 + facing.getFrontOffsetX(), rand.nextGaussian() * 0.05 + facing.getFrontOffsetY(), rand.nextGaussian() * 0.05 + facing.getFrontOffsetZ()));
                itemStack.splitStack(" ".length());
                return itemStack;
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
                    if (false) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
            
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
                    if (3 <= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(597 + 239 - 248 + 412, blockSource.getBlockPos(), "".length());
            }
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final World world = blockSource.getWorld();
                final double n = blockSource.getX() + facing.getFrontOffsetX() * 1.125f;
                final double n2 = blockSource.getY() + facing.getFrontOffsetY() * 1.125f;
                final double n3 = blockSource.getZ() + facing.getFrontOffsetZ() * 1.125f;
                final BlockPos offset = blockSource.getBlockPos().offset(facing);
                final Material material = world.getBlockState(offset).getBlock().getMaterial();
                double n4;
                if (Material.water.equals(material)) {
                    n4 = 1.0;
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                }
                else {
                    if (!Material.air.equals(material) || !Material.water.equals(world.getBlockState(offset.down()).getBlock().getMaterial())) {
                        return this.field_150842_b.dispense(blockSource, itemStack);
                    }
                    n4 = 0.0;
                }
                world.spawnEntityInWorld(new EntityBoat(world, n, n2 + n4, n3));
                itemStack.splitStack(" ".length());
                return itemStack;
            }
        });
        final BehaviorDefaultDispenseItem behaviorDefaultDispenseItem = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                if (((ItemBucket)itemStack.getItem()).tryPlaceContainedLiquid(blockSource.getWorld(), blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata())))) {
                    itemStack.setItem(Items.bucket);
                    itemStack.stackSize = " ".length();
                    return itemStack;
                }
                return this.field_150841_b.dispense(blockSource, itemStack);
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
                    if (4 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, behaviorDefaultDispenseItem);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, behaviorDefaultDispenseItem);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final IBlockState blockState = world.getBlockState(offset);
                final Block block = blockState.getBlock();
                final Material material = block.getMaterial();
                Item item;
                if (Material.water.equals(material) && block instanceof BlockLiquid && blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL) == 0) {
                    item = Items.water_bucket;
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                }
                else {
                    if (!Material.lava.equals(material) || !(block instanceof BlockLiquid) || blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL) != 0) {
                        return super.dispenseStack(blockSource, itemStack);
                    }
                    item = Items.lava_bucket;
                }
                world.setBlockToAir(offset);
                if ((itemStack.stackSize -= " ".length()) == 0) {
                    itemStack.setItem(item);
                    itemStack.stackSize = " ".length();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else if (blockSource.getBlockTileEntity().addItemStack(new ItemStack(item)) < 0) {
                    this.field_150840_b.dispense(blockSource, new ItemStack(item));
                }
                return itemStack;
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
                    if (2 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem() {
            private boolean field_150839_b = " ".length();
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                if (this.field_150839_b) {
                    blockSource.getWorld().playAuxSFX(345 + 374 - 360 + 641, blockSource.getBlockPos(), "".length());
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else {
                    blockSource.getWorld().playAuxSFX(849 + 333 - 845 + 664, blockSource.getBlockPos(), "".length());
                }
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                if (world.isAirBlock(offset)) {
                    world.setBlockState(offset, Blocks.fire.getDefaultState());
                    if (itemStack.attemptDamageItem(" ".length(), world.rand)) {
                        itemStack.stackSize = "".length();
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                    }
                }
                else if (world.getBlockState(offset).getBlock() == Blocks.tnt) {
                    Blocks.tnt.onBlockDestroyedByPlayer(world, offset, Blocks.tnt.getDefaultState().withProperty((IProperty<Comparable>)BlockTNT.EXPLODE, (boolean)(" ".length() != 0)));
                    world.setBlockToAir(offset);
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
                else {
                    this.field_150839_b = ("".length() != 0);
                }
                return itemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem() {
            private boolean field_150838_b = " ".length();
            
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
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                if (EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(itemStack.getMetadata())) {
                    final World world = blockSource.getWorld();
                    final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                    if (ItemDye.applyBonemeal(itemStack, world, offset)) {
                        if (!world.isRemote) {
                            world.playAuxSFX(562 + 1672 - 402 + 173, offset, "".length());
                            "".length();
                            if (0 >= 4) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.field_150838_b = ("".length() != 0);
                    }
                    return itemStack;
                }
                return super.dispenseStack(blockSource, itemStack);
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                if (this.field_150838_b) {
                    blockSource.getWorld().playAuxSFX(142 + 847 - 716 + 727, blockSource.getBlockPos(), "".length());
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else {
                    blockSource.getWorld().playAuxSFX(750 + 368 - 810 + 693, blockSource.getBlockPos(), "".length());
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem() {
            private static final String[] I;
            
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
                    if (3 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, offset.getX() + 0.5, offset.getY(), offset.getZ() + 0.5, null);
                world.spawnEntityInWorld(entityTNTPrimed);
                world.playSoundAtEntity(entityTNTPrimed, Bootstrap$14.I["".length()], 1.0f, 1.0f);
                itemStack.stackSize -= " ".length();
                return itemStack;
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u0005\u001b\u00071c\u0016\u0014\u001ez=\u0010\u0013\u00071)", "bzjTM");
            }
            
            static {
                I();
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.skull, new BehaviorDefaultDispenseItem() {
            private boolean field_179240_b = " ".length();
            private static final String[] I;
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final BlockPos offset = blockSource.getBlockPos().offset(facing);
                final BlockSkull skull = Blocks.skull;
                if (world.isAirBlock(offset) && skull.canDispenserPlace(world, offset, itemStack)) {
                    if (!world.isRemote) {
                        world.setBlockState(offset, skull.getDefaultState().withProperty((IProperty<Comparable>)BlockSkull.FACING, EnumFacing.UP), "   ".length());
                        final TileEntity tileEntity = world.getTileEntity(offset);
                        if (tileEntity instanceof TileEntitySkull) {
                            if (itemStack.getMetadata() == "   ".length()) {
                                GameProfile gameProfileFromNBT = null;
                                if (itemStack.hasTagCompound()) {
                                    final NBTTagCompound tagCompound = itemStack.getTagCompound();
                                    if (tagCompound.hasKey(Bootstrap$15.I["".length()], 0x5C ^ 0x56)) {
                                        gameProfileFromNBT = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag(Bootstrap$15.I[" ".length()]));
                                        "".length();
                                        if (2 <= -1) {
                                            throw null;
                                        }
                                    }
                                    else if (tagCompound.hasKey(Bootstrap$15.I["  ".length()], 0x2A ^ 0x22)) {
                                        final String string = tagCompound.getString(Bootstrap$15.I["   ".length()]);
                                        if (!StringUtils.isNullOrEmpty(string)) {
                                            gameProfileFromNBT = new GameProfile((UUID)null, string);
                                        }
                                    }
                                }
                                ((TileEntitySkull)tileEntity).setPlayerProfile(gameProfileFromNBT);
                                "".length();
                                if (2 < 2) {
                                    throw null;
                                }
                            }
                            else {
                                ((TileEntitySkull)tileEntity).setType(itemStack.getMetadata());
                            }
                            ((TileEntitySkull)tileEntity).setSkullRotation(facing.getOpposite().getHorizontalIndex() * (0x8F ^ 0x8B));
                            Blocks.skull.checkWitherSpawn(world, offset, (TileEntitySkull)tileEntity);
                        }
                        itemStack.stackSize -= " ".length();
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                }
                else {
                    this.field_179240_b = ("".length() != 0);
                }
                return itemStack;
            }
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String[0x63 ^ 0x67])["".length()] = I("\u0018):\u001c\u001a\u00045!\u0015\u0004", "KBOpv");
                Bootstrap$15.I[" ".length()] = I("2:\u001c5(.&\u0007<6", "aQiYD");
                Bootstrap$15.I["  ".length()] = I(":\u001e,\u0018\u001c&\u00027\u0011\u0002", "iuYtp");
                Bootstrap$15.I["   ".length()] = I(";\n\r'\u001a'\u0016\u0016.\u0004", "haxKv");
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
                    if (3 <= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                if (this.field_179240_b) {
                    blockSource.getWorld().playAuxSFX(305 + 815 - 385 + 265, blockSource.getBlockPos(), "".length());
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else {
                    blockSource.getWorld().playAuxSFX(600 + 509 - 337 + 229, blockSource.getBlockPos(), "".length());
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.pumpkin), new BehaviorDefaultDispenseItem() {
            private boolean field_179241_b = " ".length();
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final BlockPumpkin blockPumpkin = (BlockPumpkin)Blocks.pumpkin;
                if (world.isAirBlock(offset) && blockPumpkin.canDispenserPlace(world, offset)) {
                    if (!world.isRemote) {
                        world.setBlockState(offset, blockPumpkin.getDefaultState(), "   ".length());
                    }
                    itemStack.stackSize -= " ".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    this.field_179241_b = ("".length() != 0);
                }
                return itemStack;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                if (this.field_179241_b) {
                    blockSource.getWorld().playAuxSFX(447 + 710 - 585 + 428, blockSource.getBlockPos(), "".length());
                    "".length();
                    if (-1 == 3) {
                        throw null;
                    }
                }
                else {
                    blockSource.getWorld().playAuxSFX(0 + 914 - 78 + 165, blockSource.getBlockPos(), "".length());
                }
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
                    if (false) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
    }
}
