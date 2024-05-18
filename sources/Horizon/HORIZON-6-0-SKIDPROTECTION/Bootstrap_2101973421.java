package HORIZON-6-0-SKIDPROTECTION;

import java.io.OutputStream;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.PrintStream;

public class Bootstrap_2101973421
{
    private static final PrintStream HorizonCode_Horizon_È;
    private static boolean Â;
    private static final Logger Ý;
    private static final String Ø­áŒŠá = "CL_00001397";
    
    static {
        HorizonCode_Horizon_È = System.out;
        Bootstrap_2101973421.Â = false;
        Ý = LogManager.getLogger();
    }
    
    public static boolean HorizonCode_Horizon_È() {
        return Bootstrap_2101973421.Â;
    }
    
    static void Â() {
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.à, new BehaviorProjectileDispense() {
            private static final String Â = "CL_00001398";
            
            @Override
            protected IProjectile HorizonCode_Horizon_È(final World worldIn, final IPosition position) {
                final EntityArrow var3 = new EntityArrow(worldIn, position.Â(), position.Ý(), position.Ø­áŒŠá());
                var3.HorizonCode_Horizon_È = 1;
                return var3;
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.¥É, new BehaviorProjectileDispense() {
            private static final String Â = "CL_00001404";
            
            @Override
            protected IProjectile HorizonCode_Horizon_È(final World worldIn, final IPosition position) {
                return new EntityEgg(worldIn, position.Â(), position.Ý(), position.Ø­áŒŠá());
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.Ñ¢à, new BehaviorProjectileDispense() {
            private static final String Â = "CL_00001405";
            
            @Override
            protected IProjectile HorizonCode_Horizon_È(final World worldIn, final IPosition position) {
                return new EntitySnowball(worldIn, position.Â(), position.Ý(), position.Ø­áŒŠá());
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.áŒŠÉ, new BehaviorProjectileDispense() {
            private static final String Â = "CL_00001406";
            
            @Override
            protected IProjectile HorizonCode_Horizon_È(final World worldIn, final IPosition position) {
                return new EntityExpBottle(worldIn, position.Â(), position.Ý(), position.Ø­áŒŠá());
            }
            
            @Override
            protected float HorizonCode_Horizon_È() {
                return super.HorizonCode_Horizon_È() * 0.5f;
            }
            
            @Override
            protected float Â() {
                return super.Â() * 1.25f;
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.µÂ, new IBehaviorDispenseItem() {
            private final BehaviorDefaultDispenseItem Â = new BehaviorDefaultDispenseItem();
            private static final String Ý = "CL_00001407";
            
            @Override
            public ItemStack HorizonCode_Horizon_È(final IBlockSource source, final ItemStack stack) {
                return ItemPotion.Ó(stack.Ø()) ? new BehaviorProjectileDispense() {
                    private static final String Ý = "CL_00001408";
                    
                    @Override
                    protected IProjectile HorizonCode_Horizon_È(final World worldIn, final IPosition position) {
                        return new EntityPotion(worldIn, position.Â(), position.Ý(), position.Ø­áŒŠá(), stack.áˆºÑ¢Õ());
                    }
                    
                    @Override
                    protected float HorizonCode_Horizon_È() {
                        return super.HorizonCode_Horizon_È() * 0.5f;
                    }
                    
                    @Override
                    protected float Â() {
                        return super.Â() * 1.25f;
                    }
                }.HorizonCode_Horizon_È(source, stack) : this.Â.HorizonCode_Horizon_È(source, stack);
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.áˆºáˆºáŠ, new BehaviorDefaultDispenseItem() {
            private static final String Â = "CL_00001410";
            
            public ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final EnumFacing var3 = BlockDispenser.Âµá€(source.à());
                final double var4 = source.Â() + var3.Ø();
                final double var5 = source.Âµá€().Â() + 0.2f;
                final double var6 = source.Ø­áŒŠá() + var3.áˆºÑ¢Õ();
                final Entity var7 = ItemMonsterPlacer.HorizonCode_Horizon_È(source.HorizonCode_Horizon_È(), stack.Ø(), var4, var5, var6);
                if (var7 instanceof EntityLivingBase && stack.¥Æ()) {
                    ((EntityLiving)var7).à(stack.µà());
                }
                stack.HorizonCode_Horizon_È(1);
                return stack;
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.ŠáŒŠà¢, new BehaviorDefaultDispenseItem() {
            private static final String Â = "CL_00001411";
            
            public ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final EnumFacing var3 = BlockDispenser.Âµá€(source.à());
                final double var4 = source.Â() + var3.Ø();
                final double var5 = source.Âµá€().Â() + 0.2f;
                final double var6 = source.Ø­áŒŠá() + var3.áˆºÑ¢Õ();
                final EntityFireworkRocket var7 = new EntityFireworkRocket(source.HorizonCode_Horizon_È(), var4, var5, var6, stack);
                source.HorizonCode_Horizon_È().HorizonCode_Horizon_È(var7);
                stack.HorizonCode_Horizon_È(1);
                return stack;
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
                source.HorizonCode_Horizon_È().Â(1002, source.Âµá€(), 0);
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.ÇŽØ, new BehaviorDefaultDispenseItem() {
            private static final String Â = "CL_00001412";
            
            public ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final EnumFacing var3 = BlockDispenser.Âµá€(source.à());
                final IPosition var4 = BlockDispenser.HorizonCode_Horizon_È(source);
                final double var5 = var4.Â() + var3.Ø() * 0.3f;
                final double var6 = var4.Ý() + var3.Ø() * 0.3f;
                final double var7 = var4.Ø­áŒŠá() + var3.áˆºÑ¢Õ() * 0.3f;
                final World var8 = source.HorizonCode_Horizon_È();
                final Random var9 = var8.Å;
                final double var10 = var9.nextGaussian() * 0.05 + var3.Ø();
                final double var11 = var9.nextGaussian() * 0.05 + var3.áŒŠÆ();
                final double var12 = var9.nextGaussian() * 0.05 + var3.áˆºÑ¢Õ();
                var8.HorizonCode_Horizon_È(new EntitySmallFireball(var8, var5, var6, var7, var10, var11, var12));
                stack.HorizonCode_Horizon_È(1);
                return stack;
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
                source.HorizonCode_Horizon_È().Â(1009, source.Âµá€(), 0);
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.ÇªØ­, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem Â = new BehaviorDefaultDispenseItem();
            private static final String Ý = "CL_00001413";
            
            public ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final EnumFacing var3 = BlockDispenser.Âµá€(source.à());
                final World var4 = source.HorizonCode_Horizon_È();
                final double var5 = source.Â() + var3.Ø() * 1.125f;
                final double var6 = source.Ý() + var3.áŒŠÆ() * 1.125f;
                final double var7 = source.Ø­áŒŠá() + var3.áˆºÑ¢Õ() * 1.125f;
                final BlockPos var8 = source.Âµá€().HorizonCode_Horizon_È(var3);
                final Material var9 = var4.Â(var8).Ý().Ó();
                double var10;
                if (Material.Ø.equals(var9)) {
                    var10 = 1.0;
                }
                else {
                    if (!Material.HorizonCode_Horizon_È.equals(var9) || !Material.Ø.equals(var4.Â(var8.Âµá€()).Ý().Ó())) {
                        return this.Â.HorizonCode_Horizon_È(source, stack);
                    }
                    var10 = 0.0;
                }
                final EntityBoat var11 = new EntityBoat(var4, var5, var6 + var10, var7);
                var4.HorizonCode_Horizon_È(var11);
                stack.HorizonCode_Horizon_È(1);
                return stack;
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
                source.HorizonCode_Horizon_È().Â(1000, source.Âµá€(), 0);
            }
        });
        final BehaviorDefaultDispenseItem var0 = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem Â = new BehaviorDefaultDispenseItem();
            private static final String Ý = "CL_00001399";
            
            public ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final ItemBucket var3 = (ItemBucket)stack.HorizonCode_Horizon_È();
                final BlockPos var4 = source.Âµá€().HorizonCode_Horizon_È(BlockDispenser.Âµá€(source.à()));
                if (var3.HorizonCode_Horizon_È(source.HorizonCode_Horizon_È(), var4)) {
                    stack.HorizonCode_Horizon_È(Items.áŒŠáŠ);
                    stack.Â = 1;
                    return stack;
                }
                return this.Â.HorizonCode_Horizon_È(source, stack);
            }
        };
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.¥Ä, var0);
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.ˆÓ, var0);
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.áŒŠáŠ, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem Â = new BehaviorDefaultDispenseItem();
            private static final String Ý = "CL_00001400";
            
            public ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final World var3 = source.HorizonCode_Horizon_È();
                final BlockPos var4 = source.Âµá€().HorizonCode_Horizon_È(BlockDispenser.Âµá€(source.à()));
                final IBlockState var5 = var3.Â(var4);
                final Block var6 = var5.Ý();
                final Material var7 = var6.Ó();
                Item_1028566121 var8;
                if (Material.Ø.equals(var7) && var6 instanceof BlockLiquid && (int)var5.HorizonCode_Horizon_È(BlockLiquid.à¢) == 0) {
                    var8 = Items.ˆÓ;
                }
                else {
                    if (!Material.áŒŠÆ.equals(var7) || !(var6 instanceof BlockLiquid) || (int)var5.HorizonCode_Horizon_È(BlockLiquid.à¢) != 0) {
                        return super.Â(source, stack);
                    }
                    var8 = Items.¥Ä;
                }
                var3.Ø(var4);
                final int â = stack.Â - 1;
                stack.Â = â;
                if (â == 0) {
                    stack.HorizonCode_Horizon_È(var8);
                    stack.Â = 1;
                }
                else if (((TileEntityDispenser)source.Ø()).HorizonCode_Horizon_È(new ItemStack(var8)) < 0) {
                    this.Â.HorizonCode_Horizon_È(source, new ItemStack(var8));
                }
                return stack;
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.Ø­áŒŠá, new BehaviorDefaultDispenseItem() {
            private boolean Â = true;
            private static final String Ý = "CL_00001401";
            
            @Override
            protected ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final World var3 = source.HorizonCode_Horizon_È();
                final BlockPos var4 = source.Âµá€().HorizonCode_Horizon_È(BlockDispenser.Âµá€(source.à()));
                if (var3.Ø­áŒŠá(var4)) {
                    var3.Â(var4, Blocks.Ô.¥à());
                    if (stack.HorizonCode_Horizon_È(1, var3.Å)) {
                        stack.Â = 0;
                    }
                }
                else if (var3.Â(var4).Ý() == Blocks.Ñ¢Â) {
                    Blocks.Ñ¢Â.Â(var3, var4, Blocks.Ñ¢Â.¥à().HorizonCode_Horizon_È(BlockTNT.Õ, true));
                    var3.Ø(var4);
                }
                else {
                    this.Â = false;
                }
                return stack;
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
                if (this.Â) {
                    source.HorizonCode_Horizon_È().Â(1000, source.Âµá€(), 0);
                }
                else {
                    source.HorizonCode_Horizon_È().Â(1001, source.Âµá€(), 0);
                }
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.áŒŠÔ, new BehaviorDefaultDispenseItem() {
            private boolean Â = true;
            private static final String Ý = "CL_00001402";
            
            @Override
            protected ItemStack Â(final IBlockSource source, final ItemStack stack) {
                if (EnumDyeColor.HorizonCode_Horizon_È == EnumDyeColor.HorizonCode_Horizon_È(stack.Ø())) {
                    final World var3 = source.HorizonCode_Horizon_È();
                    final BlockPos var4 = source.Âµá€().HorizonCode_Horizon_È(BlockDispenser.Âµá€(source.à()));
                    if (ItemDye.HorizonCode_Horizon_È(stack, var3, var4)) {
                        if (!var3.ŠÄ) {
                            var3.Â(2005, var4, 0);
                        }
                    }
                    else {
                        this.Â = false;
                    }
                    return stack;
                }
                return super.Â(source, stack);
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
                if (this.Â) {
                    source.HorizonCode_Horizon_È().Â(1000, source.Âµá€(), 0);
                }
                else {
                    source.HorizonCode_Horizon_È().Â(1001, source.Âµá€(), 0);
                }
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ñ¢Â), new BehaviorDefaultDispenseItem() {
            private static final String Â = "CL_00001403";
            
            @Override
            protected ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final World var3 = source.HorizonCode_Horizon_È();
                final BlockPos var4 = source.Âµá€().HorizonCode_Horizon_È(BlockDispenser.Âµá€(source.à()));
                final EntityTNTPrimed var5 = new EntityTNTPrimed(var3, var4.HorizonCode_Horizon_È() + 0.5, var4.Â(), var4.Ý() + 0.5, null);
                var3.HorizonCode_Horizon_È(var5);
                var3.HorizonCode_Horizon_È(var5, "game.tnt.primed", 1.0f, 1.0f);
                --stack.Â;
                return stack;
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Items.ˆ, new BehaviorDefaultDispenseItem() {
            private boolean Â = true;
            private static final String Ý = "CL_00002278";
            
            @Override
            protected ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final World var3 = source.HorizonCode_Horizon_È();
                final EnumFacing var4 = BlockDispenser.Âµá€(source.à());
                final BlockPos var5 = source.Âµá€().HorizonCode_Horizon_È(var4);
                final BlockSkull var6 = Blocks.ÇªÈ;
                if (var3.Ø­áŒŠá(var5) && var6.Â(var3, var5, stack)) {
                    if (!var3.ŠÄ) {
                        var3.HorizonCode_Horizon_È(var5, var6.¥à().HorizonCode_Horizon_È(BlockSkull.Õ, EnumFacing.Â), 3);
                        final TileEntity var7 = var3.HorizonCode_Horizon_È(var5);
                        if (var7 instanceof TileEntitySkull) {
                            if (stack.Ø() == 3) {
                                GameProfile var8 = null;
                                if (stack.£á()) {
                                    final NBTTagCompound var9 = stack.Å();
                                    if (var9.Â("SkullOwner", 10)) {
                                        var8 = NBTUtil.HorizonCode_Horizon_È(var9.ˆÏ­("SkullOwner"));
                                    }
                                    else if (var9.Â("SkullOwner", 8)) {
                                        var8 = new GameProfile((UUID)null, var9.áˆºÑ¢Õ("SkullOwner"));
                                    }
                                }
                                ((TileEntitySkull)var7).HorizonCode_Horizon_È(var8);
                            }
                            else {
                                ((TileEntitySkull)var7).HorizonCode_Horizon_È(stack.Ø());
                            }
                            ((TileEntitySkull)var7).Â(var4.Âµá€().Ý() * 4);
                            Blocks.ÇªÈ.HorizonCode_Horizon_È(var3, var5, (TileEntitySkull)var7);
                        }
                        --stack.Â;
                    }
                }
                else {
                    this.Â = false;
                }
                return stack;
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
                if (this.Â) {
                    source.HorizonCode_Horizon_È().Â(1000, source.Âµá€(), 0);
                }
                else {
                    source.HorizonCode_Horizon_È().Â(1001, source.Âµá€(), 0);
                }
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Æ), new BehaviorDefaultDispenseItem() {
            private boolean Â = true;
            private static final String Ý = "CL_00002277";
            
            @Override
            protected ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final World var3 = source.HorizonCode_Horizon_È();
                final BlockPos var4 = source.Âµá€().HorizonCode_Horizon_È(BlockDispenser.Âµá€(source.à()));
                final BlockPumpkin var5 = (BlockPumpkin)Blocks.Ø­Æ;
                if (var3.Ø­áŒŠá(var4) && var5.áŒŠÆ(var3, var4)) {
                    if (!var3.ŠÄ) {
                        var3.HorizonCode_Horizon_È(var4, var5.¥à(), 3);
                    }
                    --stack.Â;
                }
                else {
                    this.Â = false;
                }
                return stack;
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
                if (this.Â) {
                    source.HorizonCode_Horizon_È().Â(1000, source.Âµá€(), 0);
                }
                else {
                    source.HorizonCode_Horizon_È().Â(1001, source.Âµá€(), 0);
                }
            }
        });
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÑ¢Ó), new BehaviorDefaultDispenseItem() {
            private static final String Â = "CL_00002276";
            
            @Override
            protected ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final World var3 = source.HorizonCode_Horizon_È();
                final BlockPos var4 = source.Âµá€().HorizonCode_Horizon_È(BlockDispenser.Âµá€(source.à()));
                if (var3.Ø­áŒŠá(var4)) {
                    if (!var3.ŠÄ) {
                        final IBlockState var5 = Blocks.ŠÑ¢Ó.¥à().HorizonCode_Horizon_È(BlockCommandBlock.Õ, false);
                        var3.HorizonCode_Horizon_È(var4, var5, 3);
                        ItemBlock.HorizonCode_Horizon_È(var3, var4, stack);
                        var3.Â(source.Âµá€(), source.Ó());
                    }
                    --stack.Â;
                }
                return stack;
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source, final EnumFacing facingIn) {
            }
        });
    }
    
    public static void Ý() {
        if (!Bootstrap_2101973421.Â) {
            Bootstrap_2101973421.Â = true;
            if (Bootstrap_2101973421.Ý.isDebugEnabled()) {
                Ø­áŒŠá();
            }
            Block.Ç();
            BlockFire.È();
            Item_1028566121.µà();
            StatList.HorizonCode_Horizon_È();
            Â();
        }
    }
    
    private static void Ø­áŒŠá() {
        System.setErr(new LoggingPrintStream("STDERR", System.err));
        System.setOut(new LoggingPrintStream("STDOUT", Bootstrap_2101973421.HorizonCode_Horizon_È));
    }
    
    public static void HorizonCode_Horizon_È(final String p_179870_0_) {
        Bootstrap_2101973421.HorizonCode_Horizon_È.println(p_179870_0_);
    }
}
