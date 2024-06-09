package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class TileEntityBeacon extends TileEntityLockable implements IInventory, IUpdatePlayerListBox
{
    public static final Potion[][] Âµá€;
    private final List Ó;
    private long à;
    private float Ø;
    private boolean áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    private ItemStack ˆÏ­;
    private String £á;
    private static final String Å = "CL_00000339";
    
    static {
        Âµá€ = new Potion[][] { { Potion.Ý, Potion.Âµá€ }, { Potion.ˆÏ­, Potion.áˆºÑ¢Õ }, { Potion.à }, { Potion.á } };
    }
    
    public TileEntityBeacon() {
        this.Ó = Lists.newArrayList();
        this.áˆºÑ¢Õ = -1;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.Šáƒ() % 80L == 0L) {
            this.Ø­à();
        }
    }
    
    public void Ø­à() {
        this.Ï­Ðƒà();
        this.Šáƒ();
    }
    
    private void Šáƒ() {
        if (this.áŒŠÆ && this.áˆºÑ¢Õ > 0 && !this.HorizonCode_Horizon_È.ŠÄ && this.ÂµÈ > 0) {
            final double var1 = this.áˆºÑ¢Õ * 10 + 10;
            byte var2 = 0;
            if (this.áˆºÑ¢Õ >= 4 && this.ÂµÈ == this.á) {
                var2 = 1;
            }
            final int var3 = this.Â.HorizonCode_Horizon_È();
            final int var4 = this.Â.Â();
            final int var5 = this.Â.Ý();
            final AxisAlignedBB var6 = new AxisAlignedBB(var3, var4, var5, var3 + 1, var4 + 1, var5 + 1).Â(var1, var1, var1).HorizonCode_Horizon_È(0.0, this.HorizonCode_Horizon_È.É(), 0.0);
            final List var7 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(EntityPlayer.class, var6);
            for (final EntityPlayer var9 : var7) {
                var9.HorizonCode_Horizon_È(new PotionEffect(this.ÂµÈ, 180, var2, true, true));
            }
            if (this.áˆºÑ¢Õ >= 4 && this.ÂµÈ != this.á && this.á > 0) {
                for (final EntityPlayer var9 : var7) {
                    var9.HorizonCode_Horizon_È(new PotionEffect(this.á, 180, 0, true, true));
                }
            }
        }
    }
    
    private void Ï­Ðƒà() {
        final int var1 = this.áˆºÑ¢Õ;
        final int var2 = this.Â.HorizonCode_Horizon_È();
        final int var3 = this.Â.Â();
        final int var4 = this.Â.Ý();
        this.áˆºÑ¢Õ = 0;
        this.Ó.clear();
        this.áŒŠÆ = true;
        HorizonCode_Horizon_È var5 = new HorizonCode_Horizon_È(EntitySheep.HorizonCode_Horizon_È(EnumDyeColor.HorizonCode_Horizon_È));
        this.Ó.add(var5);
        boolean var6 = true;
        for (int var7 = var3 + 1; var7 < this.HorizonCode_Horizon_È.áƒ(); ++var7) {
            final BlockPos var8 = new BlockPos(var2, var7, var4);
            final IBlockState var9 = this.HorizonCode_Horizon_È.Â(var8);
            float[] var10;
            if (var9.Ý() == Blocks.ÐƒáŒŠÂµÐƒÕ) {
                var10 = EntitySheep.HorizonCode_Horizon_È((EnumDyeColor)var9.HorizonCode_Horizon_È(BlockStainedGlass.Õ));
            }
            else if (var9.Ý() != Blocks.Ø­áƒ) {
                if (var9.Ý().Â() >= 15) {
                    this.áŒŠÆ = false;
                    this.Ó.clear();
                    break;
                }
                var5.HorizonCode_Horizon_È();
                continue;
            }
            else {
                var10 = EntitySheep.HorizonCode_Horizon_È((EnumDyeColor)var9.HorizonCode_Horizon_È(BlockStainedGlassPane.Âµà));
            }
            if (!var6) {
                var10 = new float[] { (var5.Â()[0] + var10[0]) / 2.0f, (var5.Â()[1] + var10[1]) / 2.0f, (var5.Â()[2] + var10[2]) / 2.0f };
            }
            if (Arrays.equals(var10, var5.Â())) {
                var5.HorizonCode_Horizon_È();
            }
            else {
                var5 = new HorizonCode_Horizon_È(var10);
                this.Ó.add(var5);
            }
            var6 = false;
        }
        if (this.áŒŠÆ) {
            for (int var7 = 1; var7 <= 4; this.áˆºÑ¢Õ = var7++) {
                final int var11 = var3 - var7;
                if (var11 < 0) {
                    break;
                }
                boolean var12 = true;
                for (int var13 = var2 - var7; var13 <= var2 + var7 && var12; ++var13) {
                    for (int var14 = var4 - var7; var14 <= var4 + var7; ++var14) {
                        final Block var15 = this.HorizonCode_Horizon_È.Â(new BlockPos(var13, var11, var14)).Ý();
                        if (var15 != Blocks.ˆØ­áˆº && var15 != Blocks.ˆáŠ && var15 != Blocks.Ø­á && var15 != Blocks.áŒŠ) {
                            var12 = false;
                            break;
                        }
                    }
                }
                if (!var12) {
                    break;
                }
            }
            if (this.áˆºÑ¢Õ == 0) {
                this.áŒŠÆ = false;
            }
        }
        if (!this.HorizonCode_Horizon_È.ŠÄ && this.áˆºÑ¢Õ == 4 && var1 < this.áˆºÑ¢Õ) {
            for (final EntityPlayer var17 : this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(EntityPlayer.class, new AxisAlignedBB(var2, var3, var4, var2, var3 - 4, var4).Â(10.0, 5.0, 10.0))) {
                var17.HorizonCode_Horizon_È(AchievementList.Õ);
            }
        }
    }
    
    public List µÕ() {
        return this.Ó;
    }
    
    public float Æ() {
        if (!this.áŒŠÆ) {
            return 0.0f;
        }
        final int var1 = (int)(this.HorizonCode_Horizon_È.Šáƒ() - this.à);
        this.à = this.HorizonCode_Horizon_È.Šáƒ();
        if (var1 > 1) {
            this.Ø -= var1 / 40.0f;
            if (this.Ø < 0.0f) {
                this.Ø = 0.0f;
            }
        }
        this.Ø += 0.025f;
        if (this.Ø > 1.0f) {
            this.Ø = 1.0f;
        }
        return this.Ø;
    }
    
    @Override
    public Packet £á() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.Â(var1);
        return new S35PacketUpdateTileEntity(this.Â, 3, var1);
    }
    
    @Override
    public double ÂµÈ() {
        return 65536.0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.ÂµÈ = compound.Ó("Primary");
        this.á = compound.Ó("Secondary");
        this.áˆºÑ¢Õ = compound.Ó("Levels");
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        compound.HorizonCode_Horizon_È("Primary", this.ÂµÈ);
        compound.HorizonCode_Horizon_È("Secondary", this.á);
        compound.HorizonCode_Horizon_È("Levels", this.áˆºÑ¢Õ);
    }
    
    @Override
    public int áŒŠÆ() {
        return 1;
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return (slotIn == 0) ? this.ˆÏ­ : null;
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (index != 0 || this.ˆÏ­ == null) {
            return null;
        }
        if (count >= this.ˆÏ­.Â) {
            final ItemStack var3 = this.ˆÏ­;
            this.ˆÏ­ = null;
            return var3;
        }
        final ItemStack ˆï­ = this.ˆÏ­;
        ˆï­.Â -= count;
        return new ItemStack(this.ˆÏ­.HorizonCode_Horizon_È(), count, this.ˆÏ­.Ø());
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (index == 0 && this.ˆÏ­ != null) {
            final ItemStack var2 = this.ˆÏ­;
            this.ˆÏ­ = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        if (index == 0) {
            this.ˆÏ­ = stack;
        }
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.£á : "container.beacon";
    }
    
    @Override
    public boolean j_() {
        return this.£á != null && this.£á.length() > 0;
    }
    
    public void HorizonCode_Horizon_È(final String p_145999_1_) {
        this.£á = p_145999_1_;
    }
    
    @Override
    public int Ñ¢á() {
        return 1;
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â) == this && playerIn.Âµá€(this.Â.HorizonCode_Horizon_È() + 0.5, this.Â.Â() + 0.5, this.Â.Ý() + 0.5) <= 64.0;
    }
    
    @Override
    public void Âµá€(final EntityPlayer playerIn) {
    }
    
    @Override
    public void Ó(final EntityPlayer playerIn) {
    }
    
    @Override
    public boolean Ø­áŒŠá(final int index, final ItemStack stack) {
        return stack.HorizonCode_Horizon_È() == Items.µ || stack.HorizonCode_Horizon_È() == Items.áŒŠÆ || stack.HorizonCode_Horizon_È() == Items.ÂµÈ || stack.HorizonCode_Horizon_È() == Items.áˆºÑ¢Õ;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "minecraft:beacon";
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerBeacon(playerInventory, this);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int id) {
        switch (id) {
            case 0: {
                return this.áˆºÑ¢Õ;
            }
            case 1: {
                return this.ÂµÈ;
            }
            case 2: {
                return this.á;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int id, final int value) {
        switch (id) {
            case 0: {
                this.áˆºÑ¢Õ = value;
                break;
            }
            case 1: {
                this.ÂµÈ = value;
                break;
            }
            case 2: {
                this.á = value;
                break;
            }
        }
    }
    
    @Override
    public int Âµá€() {
        return 3;
    }
    
    @Override
    public void ŒÏ() {
        this.ˆÏ­ = null;
    }
    
    @Override
    public boolean Ý(final int id, final int type) {
        if (id == 1) {
            this.Ø­à();
            return true;
        }
        return super.Ý(id, type);
    }
    
    public static class HorizonCode_Horizon_È
    {
        private final float[] HorizonCode_Horizon_È;
        private int Â;
        private static final String Ý = "CL_00002042";
        
        public HorizonCode_Horizon_È(final float[] p_i45669_1_) {
            this.HorizonCode_Horizon_È = p_i45669_1_;
            this.Â = 1;
        }
        
        protected void HorizonCode_Horizon_È() {
            ++this.Â;
        }
        
        public float[] Â() {
            return this.HorizonCode_Horizon_È;
        }
        
        public int Ý() {
            return this.Â;
        }
    }
}
