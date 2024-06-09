package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.UUID;
import com.mojang.authlib.GameProfile;

public class ItemSkull extends Item_1028566121
{
    private static final String[] à;
    private static final String Ø = "CL_00000067";
    
    static {
        à = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
    }
    
    public ItemSkull() {
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side == EnumFacing.HorizonCode_Horizon_È) {
            return false;
        }
        final IBlockState var9 = worldIn.Â(pos);
        final Block var10 = var9.Ý();
        final boolean var11 = var10.HorizonCode_Horizon_È(worldIn, pos);
        if (!var11) {
            if (!worldIn.Â(pos).Ý().Ó().Â()) {
                return false;
            }
            pos = pos.HorizonCode_Horizon_È(side);
        }
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        if (!Blocks.ÇªÈ.Ø­áŒŠá(worldIn, pos)) {
            return false;
        }
        if (!worldIn.ŠÄ) {
            worldIn.HorizonCode_Horizon_È(pos, Blocks.ÇªÈ.¥à().HorizonCode_Horizon_È(BlockSkull.Õ, side), 3);
            int var12 = 0;
            if (side == EnumFacing.Â) {
                var12 = (MathHelper.Ý(playerIn.É * 16.0f / 360.0f + 0.5) & 0xF);
            }
            final TileEntity var13 = worldIn.HorizonCode_Horizon_È(pos);
            if (var13 instanceof TileEntitySkull) {
                final TileEntitySkull var14 = (TileEntitySkull)var13;
                if (stack.Ø() == 3) {
                    GameProfile var15 = null;
                    if (stack.£á()) {
                        final NBTTagCompound var16 = stack.Å();
                        if (var16.Â("SkullOwner", 10)) {
                            var15 = NBTUtil.HorizonCode_Horizon_È(var16.ˆÏ­("SkullOwner"));
                        }
                        else if (var16.Â("SkullOwner", 8) && var16.áˆºÑ¢Õ("SkullOwner").length() > 0) {
                            var15 = new GameProfile((UUID)null, var16.áˆºÑ¢Õ("SkullOwner"));
                        }
                    }
                    var14.HorizonCode_Horizon_È(var15);
                }
                else {
                    var14.HorizonCode_Horizon_È(stack.Ø());
                }
                var14.Â(var12);
                Blocks.ÇªÈ.HorizonCode_Horizon_È(worldIn, pos, var14);
            }
            --stack.Â;
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        for (int var4 = 0; var4 < ItemSkull.à.length; ++var4) {
            subItems.add(new ItemStack(itemIn, 1, var4));
        }
    }
    
    @Override
    public int Ý(final int damage) {
        return damage;
    }
    
    @Override
    public String Â(final ItemStack stack) {
        int var2 = stack.Ø();
        if (var2 < 0 || var2 >= ItemSkull.à.length) {
            var2 = 0;
        }
        return String.valueOf(super.Ø()) + "." + ItemSkull.à[var2];
    }
    
    @Override
    public String à(final ItemStack stack) {
        if (stack.Ø() == 3 && stack.£á()) {
            if (stack.Å().Â("SkullOwner", 8)) {
                return StatCollector.HorizonCode_Horizon_È("item.skull.player.name", stack.Å().áˆºÑ¢Õ("SkullOwner"));
            }
            if (stack.Å().Â("SkullOwner", 10)) {
                final NBTTagCompound var2 = stack.Å().ˆÏ­("SkullOwner");
                if (var2.Â("Name", 8)) {
                    return StatCollector.HorizonCode_Horizon_È("item.skull.player.name", var2.áˆºÑ¢Õ("Name"));
                }
            }
        }
        return super.à(stack);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        super.HorizonCode_Horizon_È(nbt);
        if (nbt.Â("SkullOwner", 8) && nbt.áˆºÑ¢Õ("SkullOwner").length() > 0) {
            GameProfile var2 = new GameProfile((UUID)null, nbt.áˆºÑ¢Õ("SkullOwner"));
            var2 = TileEntitySkull.Â(var2);
            nbt.HorizonCode_Horizon_È("SkullOwner", NBTUtil.HorizonCode_Horizon_È(new NBTTagCompound(), var2));
            return true;
        }
        return false;
    }
}
