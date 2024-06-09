package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import com.google.common.collect.Maps;
import java.util.Random;
import java.util.UUID;
import java.util.Map;

public class Item_1028566121
{
    public static final RegistryNamespaced HorizonCode_Horizon_È;
    private static final Map à;
    protected static final UUID Â;
    private CreativeTabs Ø;
    protected static Random Ý;
    protected int Ø­áŒŠá;
    private int áŒŠÆ;
    protected boolean Âµá€;
    protected boolean Ó;
    private Item_1028566121 áˆºÑ¢Õ;
    private String ÂµÈ;
    private String á;
    private static final String ˆÏ­ = "CL_00000041";
    
    static {
        HorizonCode_Horizon_È = new RegistryNamespaced();
        à = Maps.newHashMap();
        Â = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
        Item_1028566121.Ý = new Random();
    }
    
    public Item_1028566121() {
        this.Ø­áŒŠá = 64;
    }
    
    public static int HorizonCode_Horizon_È(final Item_1028566121 itemIn) {
        return (itemIn == null) ? 0 : Item_1028566121.HorizonCode_Horizon_È.Ø­áŒŠá(itemIn);
    }
    
    public static Item_1028566121 HorizonCode_Horizon_È(final int id) {
        return (Item_1028566121)Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È(id);
    }
    
    public static Item_1028566121 HorizonCode_Horizon_È(final Block blockIn) {
        return Item_1028566121.à.get(blockIn);
    }
    
    public static Item_1028566121 HorizonCode_Horizon_È(final String id) {
        final Item_1028566121 var1 = (Item_1028566121)Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new ResourceLocation_1975012498(id));
        if (var1 == null) {
            try {
                return HorizonCode_Horizon_È(Integer.parseInt(id));
            }
            catch (NumberFormatException ex) {}
        }
        return var1;
    }
    
    public boolean HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        return false;
    }
    
    public Item_1028566121 Â(final int maxStackSize) {
        this.Ø­áŒŠá = maxStackSize;
        return this;
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        return false;
    }
    
    public float HorizonCode_Horizon_È(final ItemStack stack, final Block p_150893_2_) {
        return 1.0f;
    }
    
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        return itemStackIn;
    }
    
    public ItemStack Â(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        return stack;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public int Ý(final int damage) {
        return 0;
    }
    
    public boolean Â() {
        return this.Ó;
    }
    
    protected Item_1028566121 HorizonCode_Horizon_È(final boolean hasSubtypes) {
        this.Ó = hasSubtypes;
        return this;
    }
    
    public int Ý() {
        return this.áŒŠÆ;
    }
    
    protected Item_1028566121 Ø­áŒŠá(final int maxDurability) {
        this.áŒŠÆ = maxDurability;
        return this;
    }
    
    public boolean Ø­áŒŠá() {
        return this.áŒŠÆ > 0 && !this.Ó;
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        return false;
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final World worldIn, final Block blockIn, final BlockPos pos, final EntityLivingBase playerIn) {
        return false;
    }
    
    public boolean Â(final Block blockIn) {
        return false;
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target) {
        return false;
    }
    
    public Item_1028566121 Âµá€() {
        this.Âµá€ = true;
        return this;
    }
    
    public boolean Ó() {
        return this.Âµá€;
    }
    
    public boolean à() {
        return false;
    }
    
    public Item_1028566121 Â(final String unlocalizedName) {
        this.á = unlocalizedName;
        return this;
    }
    
    public String HorizonCode_Horizon_È(final ItemStack stack) {
        final String var2 = this.Â(stack);
        return (var2 == null) ? "" : StatCollector.HorizonCode_Horizon_È(var2);
    }
    
    public String Ø() {
        return "item." + this.á;
    }
    
    public String Â(final ItemStack stack) {
        return "item." + this.á;
    }
    
    public Item_1028566121 Â(final Item_1028566121 containerItem) {
        this.áˆºÑ¢Õ = containerItem;
        return this;
    }
    
    public boolean áŒŠÆ() {
        return true;
    }
    
    public Item_1028566121 áˆºÑ¢Õ() {
        return this.áˆºÑ¢Õ;
    }
    
    public boolean ÂµÈ() {
        return this.áˆºÑ¢Õ != null;
    }
    
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        return 16777215;
    }
    
    public void HorizonCode_Horizon_È(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
    }
    
    public void Ý(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
    }
    
    public boolean á() {
        return false;
    }
    
    public EnumAction Ý(final ItemStack stack) {
        return EnumAction.HorizonCode_Horizon_È;
    }
    
    public int Ø­áŒŠá(final ItemStack stack) {
        return 0;
    }
    
    public void HorizonCode_Horizon_È(final ItemStack stack, final World worldIn, final EntityPlayer playerIn, final int timeLeft) {
    }
    
    protected Item_1028566121 Ý(final String potionEffect) {
        this.ÂµÈ = potionEffect;
        return this;
    }
    
    public String Âµá€(final ItemStack stack) {
        return this.ÂµÈ;
    }
    
    public boolean Ó(final ItemStack stack) {
        return this.Âµá€(stack) != null;
    }
    
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
    }
    
    public String à(final ItemStack stack) {
        return new StringBuilder().append(StatCollector.HorizonCode_Horizon_È(String.valueOf(this.HorizonCode_Horizon_È(stack)) + ".name")).toString().trim();
    }
    
    public boolean Ø(final ItemStack stack) {
        return stack.Šáƒ();
    }
    
    public EnumRarity áŒŠÆ(final ItemStack stack) {
        return stack.Šáƒ() ? EnumRarity.Ý : EnumRarity.HorizonCode_Horizon_È;
    }
    
    public boolean áˆºÑ¢Õ(final ItemStack stack) {
        return this.HorizonCode_Horizon_È() == 1 && this.Ø­áŒŠá();
    }
    
    protected MovingObjectPosition HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final boolean useLiquids) {
        final float var4 = playerIn.Õ + (playerIn.áƒ - playerIn.Õ);
        final float var5 = playerIn.á€ + (playerIn.É - playerIn.á€);
        final double var6 = playerIn.áŒŠà + (playerIn.ŒÏ - playerIn.áŒŠà);
        final double var7 = playerIn.ŠÄ + (playerIn.Çªà¢ - playerIn.ŠÄ) + playerIn.Ðƒáƒ();
        final double var8 = playerIn.Ñ¢á + (playerIn.Ê - playerIn.Ñ¢á);
        final Vec3 var9 = new Vec3(var6, var7, var8);
        final float var10 = MathHelper.Â(-var5 * 0.017453292f - 3.1415927f);
        final float var11 = MathHelper.HorizonCode_Horizon_È(-var5 * 0.017453292f - 3.1415927f);
        final float var12 = -MathHelper.Â(-var4 * 0.017453292f);
        final float var13 = MathHelper.HorizonCode_Horizon_È(-var4 * 0.017453292f);
        final float var14 = var11 * var12;
        final float var15 = var10 * var12;
        final double var16 = 5.0;
        final Vec3 var17 = var9.Â(var14 * var16, var13 * var16, var15 * var16);
        return worldIn.HorizonCode_Horizon_È(var9, var17, useLiquids, !useLiquids, false);
    }
    
    public int ˆÏ­() {
        return 0;
    }
    
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        subItems.add(new ItemStack(itemIn, 1, 0));
    }
    
    public CreativeTabs £á() {
        return this.Ø;
    }
    
    public Item_1028566121 HorizonCode_Horizon_È(final CreativeTabs tab) {
        this.Ø = tab;
        return this;
    }
    
    public boolean Å() {
        return false;
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack toRepair, final ItemStack repair) {
        return false;
    }
    
    public Multimap £à() {
        return (Multimap)HashMultimap.create();
    }
    
    public static void µà() {
        HorizonCode_Horizon_È(Blocks.Ý, new ItemMultiTexture(Blocks.Ý, Blocks.Ý, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002178";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("stone"));
        HorizonCode_Horizon_È(Blocks.Ø­áŒŠá, new ItemColored(Blocks.Ø­áŒŠá, false));
        HorizonCode_Horizon_È(Blocks.Âµá€, new ItemMultiTexture(Blocks.Âµá€, Blocks.Âµá€, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002169";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("dirt"));
        Ý(Blocks.Ó);
        HorizonCode_Horizon_È(Blocks.à, new ItemMultiTexture(Blocks.à, Blocks.à, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002168";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("wood"));
        HorizonCode_Horizon_È(Blocks.Ø, new ItemMultiTexture(Blocks.Ø, Blocks.Ø, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002167";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("sapling"));
        Ý(Blocks.áŒŠÆ);
        HorizonCode_Horizon_È(Blocks.£á, new ItemMultiTexture(Blocks.£á, Blocks.£á, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002166";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockSand.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ø­áŒŠá();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("sand"));
        Ý(Blocks.Å);
        Ý(Blocks.£à);
        Ý(Blocks.µà);
        Ý(Blocks.ˆà);
        HorizonCode_Horizon_È(Blocks.¥Æ, new ItemMultiTexture(Blocks.¥Æ, Blocks.¥Æ, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002165";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("log"));
        HorizonCode_Horizon_È(Blocks.Ø­à, new ItemMultiTexture(Blocks.Ø­à, Blocks.Ø­à, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002164";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø() + 4).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("log"));
        HorizonCode_Horizon_È(Blocks.µÕ, new ItemLeaves(Blocks.µÕ).Ø­áŒŠá("leaves"));
        HorizonCode_Horizon_È(Blocks.Æ, new ItemLeaves(Blocks.Æ).Ø­áŒŠá("leaves"));
        HorizonCode_Horizon_È(Blocks.Šáƒ, new ItemMultiTexture(Blocks.Šáƒ, Blocks.Šáƒ, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002163";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return ((stack.Ø() & 0x1) == 0x1) ? "wet" : "dry";
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("sponge"));
        Ý(Blocks.Ï­Ðƒà);
        Ý(Blocks.áŒŠà);
        Ý(Blocks.ŠÄ);
        Ý(Blocks.Ñ¢á);
        HorizonCode_Horizon_È(Blocks.ŒÏ, new ItemMultiTexture(Blocks.ŒÏ, Blocks.ŒÏ, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002162";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockSandStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("sandStone"));
        Ý(Blocks.Çªà¢);
        Ý(Blocks.ÇŽÉ);
        Ý(Blocks.ˆá);
        HorizonCode_Horizon_È(Blocks.ÇŽÕ, new ItemPiston(Blocks.ÇŽÕ));
        Ý(Blocks.É);
        HorizonCode_Horizon_È(Blocks.áƒ, new ItemColored(Blocks.áƒ, true).HorizonCode_Horizon_È(new String[] { "shrub", "grass", "fern" }));
        Ý(Blocks.á€);
        HorizonCode_Horizon_È(Blocks.Õ, new ItemPiston(Blocks.Õ));
        HorizonCode_Horizon_È(Blocks.ŠÂµà, new ItemCloth(Blocks.ŠÂµà).Ø­áŒŠá("cloth"));
        HorizonCode_Horizon_È(Blocks.Âµà, new ItemMultiTexture(Blocks.Âµà, Blocks.Âµà, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002177";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockFlower.Â.HorizonCode_Horizon_È(BlockFlower.HorizonCode_Horizon_È.HorizonCode_Horizon_È, stack.Ø()).Ø­áŒŠá();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("flower"));
        HorizonCode_Horizon_È(Blocks.Ç, new ItemMultiTexture(Blocks.Ç, Blocks.Ç, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002176";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockFlower.Â.HorizonCode_Horizon_È(BlockFlower.HorizonCode_Horizon_È.Â, stack.Ø()).Ø­áŒŠá();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("rose"));
        Ý(Blocks.È);
        Ý(Blocks.áŠ);
        Ý(Blocks.ˆáŠ);
        Ý(Blocks.áŒŠ);
        HorizonCode_Horizon_È(Blocks.Ø­Âµ, new ItemSlab(Blocks.Ø­Âµ, Blocks.Ø­Âµ, Blocks.£ÂµÄ).Ø­áŒŠá("stoneSlab"));
        Ý(Blocks.Ä);
        Ý(Blocks.Ñ¢Â);
        Ý(Blocks.Ï­à);
        Ý(Blocks.áˆºáˆºÈ);
        Ý(Blocks.ÇŽá€);
        Ý(Blocks.Ï);
        Ý(Blocks.ÇªÓ);
        Ý(Blocks.áˆºÏ);
        Ý(Blocks.ˆáƒ);
        Ý(Blocks.£Ï);
        Ý(Blocks.Ø­á);
        Ý(Blocks.ˆÉ);
        Ý(Blocks.£Â);
        Ý(Blocks.£Ó);
        Ý(Blocks.ˆÐƒØ­à);
        Ý(Blocks.áŒŠÏ);
        Ý(Blocks.áŒŠáŠ);
        Ý(Blocks.ˆÓ);
        Ý(Blocks.ÇªÔ);
        Ý(Blocks.Û);
        Ý(Blocks.ÇŽá);
        Ý(Blocks.Ñ¢à);
        Ý(Blocks.áˆº);
        Ý(Blocks.Šà);
        HorizonCode_Horizon_È(Blocks.áŒŠá€, new ItemSnow(Blocks.áŒŠá€));
        Ý(Blocks.¥Ï);
        Ý(Blocks.ˆà¢);
        Ý(Blocks.Ñ¢Ç);
        Ý(Blocks.£É);
        Ý(Blocks.Ðƒà);
        Ý(Blocks.¥É);
        Ý(Blocks.£ÇªÓ);
        Ý(Blocks.ÂµÕ);
        Ý(Blocks.Š);
        Ý(Blocks.Ø­Ñ¢á€);
        Ý(Blocks.Ñ¢Ó);
        Ý(Blocks.Ø­Æ);
        Ý(Blocks.áŒŠÔ);
        Ý(Blocks.ŠÕ);
        Ý(Blocks.£Ø­à);
        Ý(Blocks.áŒŠÕ);
        Ý(Blocks.áˆºà);
        HorizonCode_Horizon_È(Blocks.ÐƒÂ, new ItemMultiTexture(Blocks.ÐƒÂ, Blocks.ÐƒÂ, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002175";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockSilverfish.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("monsterStoneEgg"));
        HorizonCode_Horizon_È(Blocks.£áƒ, new ItemMultiTexture(Blocks.£áƒ, Blocks.£áƒ, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002174";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockStoneBrick.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("stonebricksmooth"));
        Ý(Blocks.Ï­áˆºÓ);
        Ý(Blocks.Çª);
        Ý(Blocks.ÇŽÄ);
        Ý(Blocks.ˆÈ);
        Ý(Blocks.ˆÅ);
        HorizonCode_Horizon_È(Blocks.ÇŽà, new ItemColored(Blocks.ÇŽà, false));
        Ý(Blocks.ŠáˆºÂ);
        Ý(Blocks.Ø­Ñ¢Ï­Ø­áˆº);
        Ý(Blocks.ŒÂ);
        Ý(Blocks.Ï­Ï);
        Ý(Blocks.ŠØ);
        Ý(Blocks.ˆÐƒØ);
        Ý(Blocks.Çªà);
        Ý(Blocks.¥Å);
        Ý(Blocks.Œáƒ);
        HorizonCode_Horizon_È(Blocks.Œá, new ItemLilyPad(Blocks.Œá));
        Ý(Blocks.µÂ);
        Ý(Blocks.Ñ¢ÇŽÏ);
        Ý(Blocks.ÇªÂ);
        Ý(Blocks.¥Âµá€);
        Ý(Blocks.¥áŠ);
        Ý(Blocks.µÊ);
        Ý(Blocks.áˆºáˆºáŠ);
        Ý(Blocks.áŒŠÉ);
        HorizonCode_Horizon_È(Blocks.ÇŽÊ, new ItemSlab(Blocks.ÇŽÊ, Blocks.ÇŽÊ, Blocks.ŒÓ).Ø­áŒŠá("woodSlab"));
        Ý(Blocks.µÏ);
        Ý(Blocks.µÐƒÓ);
        Ý(Blocks.¥áŒŠà);
        Ý(Blocks.ˆÂ);
        Ý(Blocks.ˆØ­áˆº);
        Ý(Blocks.£Ô);
        Ý(Blocks.ŠÏ);
        Ý(Blocks.ˆ);
        Ý(Blocks.ŠÑ¢Ó);
        Ý(Blocks.áˆºá);
        HorizonCode_Horizon_È(Blocks.Ï­Ó, new ItemMultiTexture(Blocks.Ï­Ó, Blocks.Ï­Ó, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002173";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockWall.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("cobbleWall"));
        Ý(Blocks.ˆÕ);
        HorizonCode_Horizon_È(Blocks.ÇªÅ, new ItemAnvilBlock(Blocks.ÇªÅ).Ø­áŒŠá("anvil"));
        Ý(Blocks.ÇŽ);
        Ý(Blocks.ÇŽÅ);
        Ý(Blocks.¥Ðƒá);
        Ý(Blocks.ÐƒÓ);
        Ý(Blocks.ŒÐƒà);
        Ý(Blocks.ÐƒáˆºÄ);
        Ý(Blocks.áˆºÉ);
        HorizonCode_Horizon_È(Blocks.Ø­È, new ItemMultiTexture(Blocks.Ø­È, Blocks.Ø­È, new String[] { "default", "chiseled", "lines" }).Ø­áŒŠá("quartzBlock"));
        Ý(Blocks.Ñ¢Õ);
        Ý(Blocks.Ø­à¢);
        Ý(Blocks.áŒŠÓ);
        HorizonCode_Horizon_È(Blocks.Ø­Â, new ItemCloth(Blocks.Ø­Â).Ø­áŒŠá("clayHardenedStained"));
        Ý(Blocks.¥ÇªÅ);
        Ý(Blocks.áˆºÓ);
        Ý(Blocks.ÂµÊ);
        HorizonCode_Horizon_È(Blocks.áˆºÂ, new ItemCloth(Blocks.áˆºÂ).Ø­áŒŠá("woolCarpet"));
        Ý(Blocks.Ø­);
        Ý(Blocks.ÐƒÉ);
        Ý(Blocks.ŠÂµÏ);
        Ý(Blocks.Ðƒ);
        Ý(Blocks.£Ç);
        Ý(Blocks.ÇŽØ­à);
        HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, new ItemDoublePlant(Blocks.ÇªÇªÉ, Blocks.ÇªÇªÉ, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002172";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockDoublePlant.Â.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("doublePlant"));
        HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, new ItemCloth(Blocks.ÐƒáŒŠÂµÐƒÕ).Ø­áŒŠá("stainedGlass"));
        HorizonCode_Horizon_È(Blocks.Ø­áƒ, new ItemCloth(Blocks.Ø­áƒ).Ø­áŒŠá("stainedGlassPane"));
        HorizonCode_Horizon_È(Blocks.ÇŽáˆºÈ, new ItemMultiTexture(Blocks.ÇŽáˆºÈ, Blocks.ÇŽáˆºÈ, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002171";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockPrismarine.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("prismarine"));
        Ý(Blocks.Ï­È);
        HorizonCode_Horizon_È(Blocks.áˆºÛ, new ItemMultiTexture(Blocks.áˆºÛ, Blocks.áˆºÛ, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002170";
            
            public String HorizonCode_Horizon_È(final ItemStack stack) {
                return BlockRedSandstone.HorizonCode_Horizon_È.HorizonCode_Horizon_È(stack.Ø()).Ý();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        }).Ø­áŒŠá("redSandStone"));
        Ý(Blocks.ˆÇªÓ);
        HorizonCode_Horizon_È(Blocks.µØ, new ItemSlab(Blocks.µØ, Blocks.µØ, Blocks.ÇªØ).Ø­áŒŠá("stoneSlab2"));
        HorizonCode_Horizon_È(256, "iron_shovel", new ItemSpade(Item_1028566121.HorizonCode_Horizon_È.Ý).Â("shovelIron"));
        HorizonCode_Horizon_È(257, "iron_pickaxe", new ItemPickaxe(Item_1028566121.HorizonCode_Horizon_È.Ý).Â("pickaxeIron"));
        HorizonCode_Horizon_È(258, "iron_axe", new ItemAxe(Item_1028566121.HorizonCode_Horizon_È.Ý).Â("hatchetIron"));
        HorizonCode_Horizon_È(259, "flint_and_steel", new ItemFlintAndSteel().Â("flintAndSteel"));
        HorizonCode_Horizon_È(260, "apple", new ItemFood(4, 0.3f, false).Â("apple"));
        HorizonCode_Horizon_È(261, "bow", new ItemBow().Â("bow"));
        HorizonCode_Horizon_È(262, "arrow", new Item_1028566121().Â("arrow").HorizonCode_Horizon_È(CreativeTabs.áˆºÑ¢Õ));
        HorizonCode_Horizon_È(263, "coal", new ItemCoal().Â("coal"));
        HorizonCode_Horizon_È(264, "diamond", new Item_1028566121().Â("diamond").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(265, "iron_ingot", new Item_1028566121().Â("ingotIron").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(266, "gold_ingot", new Item_1028566121().Â("ingotGold").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(267, "iron_sword", new ItemSword(Item_1028566121.HorizonCode_Horizon_È.Ý).Â("swordIron"));
        HorizonCode_Horizon_È(268, "wooden_sword", new ItemSword(Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È).Â("swordWood"));
        HorizonCode_Horizon_È(269, "wooden_shovel", new ItemSpade(Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È).Â("shovelWood"));
        HorizonCode_Horizon_È(270, "wooden_pickaxe", new ItemPickaxe(Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È).Â("pickaxeWood"));
        HorizonCode_Horizon_È(271, "wooden_axe", new ItemAxe(Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È).Â("hatchetWood"));
        HorizonCode_Horizon_È(272, "stone_sword", new ItemSword(Item_1028566121.HorizonCode_Horizon_È.Â).Â("swordStone"));
        HorizonCode_Horizon_È(273, "stone_shovel", new ItemSpade(Item_1028566121.HorizonCode_Horizon_È.Â).Â("shovelStone"));
        HorizonCode_Horizon_È(274, "stone_pickaxe", new ItemPickaxe(Item_1028566121.HorizonCode_Horizon_È.Â).Â("pickaxeStone"));
        HorizonCode_Horizon_È(275, "stone_axe", new ItemAxe(Item_1028566121.HorizonCode_Horizon_È.Â).Â("hatchetStone"));
        HorizonCode_Horizon_È(276, "diamond_sword", new ItemSword(Item_1028566121.HorizonCode_Horizon_È.Ø­áŒŠá).Â("swordDiamond"));
        HorizonCode_Horizon_È(277, "diamond_shovel", new ItemSpade(Item_1028566121.HorizonCode_Horizon_È.Ø­áŒŠá).Â("shovelDiamond"));
        HorizonCode_Horizon_È(278, "diamond_pickaxe", new ItemPickaxe(Item_1028566121.HorizonCode_Horizon_È.Ø­áŒŠá).Â("pickaxeDiamond"));
        HorizonCode_Horizon_È(279, "diamond_axe", new ItemAxe(Item_1028566121.HorizonCode_Horizon_È.Ø­áŒŠá).Â("hatchetDiamond"));
        HorizonCode_Horizon_È(280, "stick", new Item_1028566121().Âµá€().Â("stick").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(281, "bowl", new Item_1028566121().Â("bowl").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(282, "mushroom_stew", new ItemSoup(6).Â("mushroomStew"));
        HorizonCode_Horizon_È(283, "golden_sword", new ItemSword(Item_1028566121.HorizonCode_Horizon_È.Âµá€).Â("swordGold"));
        HorizonCode_Horizon_È(284, "golden_shovel", new ItemSpade(Item_1028566121.HorizonCode_Horizon_È.Âµá€).Â("shovelGold"));
        HorizonCode_Horizon_È(285, "golden_pickaxe", new ItemPickaxe(Item_1028566121.HorizonCode_Horizon_È.Âµá€).Â("pickaxeGold"));
        HorizonCode_Horizon_È(286, "golden_axe", new ItemAxe(Item_1028566121.HorizonCode_Horizon_È.Âµá€).Â("hatchetGold"));
        HorizonCode_Horizon_È(287, "string", new ItemReed(Blocks.áŒŠÈ).Â("string").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(288, "feather", new Item_1028566121().Â("feather").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(289, "gunpowder", new Item_1028566121().Â("sulphur").Ý(PotionHelper.ÂµÈ).HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(290, "wooden_hoe", new ItemHoe(Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È).Â("hoeWood"));
        HorizonCode_Horizon_È(291, "stone_hoe", new ItemHoe(Item_1028566121.HorizonCode_Horizon_È.Â).Â("hoeStone"));
        HorizonCode_Horizon_È(292, "iron_hoe", new ItemHoe(Item_1028566121.HorizonCode_Horizon_È.Ý).Â("hoeIron"));
        HorizonCode_Horizon_È(293, "diamond_hoe", new ItemHoe(Item_1028566121.HorizonCode_Horizon_È.Ø­áŒŠá).Â("hoeDiamond"));
        HorizonCode_Horizon_È(294, "golden_hoe", new ItemHoe(Item_1028566121.HorizonCode_Horizon_È.Âµá€).Â("hoeGold"));
        HorizonCode_Horizon_È(295, "wheat_seeds", new ItemSeeds(Blocks.Ï­Ï­Ï, Blocks.£Â).Â("seeds"));
        HorizonCode_Horizon_È(296, "wheat", new Item_1028566121().Â("wheat").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(297, "bread", new ItemFood(5, 0.6f, false).Â("bread"));
        HorizonCode_Horizon_È(298, "leather_helmet", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 0, 0).Â("helmetCloth"));
        HorizonCode_Horizon_È(299, "leather_chestplate", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 0, 1).Â("chestplateCloth"));
        HorizonCode_Horizon_È(300, "leather_leggings", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 0, 2).Â("leggingsCloth"));
        HorizonCode_Horizon_È(301, "leather_boots", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 0, 3).Â("bootsCloth"));
        HorizonCode_Horizon_È(302, "chainmail_helmet", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Â, 1, 0).Â("helmetChain"));
        HorizonCode_Horizon_È(303, "chainmail_chestplate", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Â, 1, 1).Â("chestplateChain"));
        HorizonCode_Horizon_È(304, "chainmail_leggings", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Â, 1, 2).Â("leggingsChain"));
        HorizonCode_Horizon_È(305, "chainmail_boots", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Â, 1, 3).Â("bootsChain"));
        HorizonCode_Horizon_È(306, "iron_helmet", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Ý, 2, 0).Â("helmetIron"));
        HorizonCode_Horizon_È(307, "iron_chestplate", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Ý, 2, 1).Â("chestplateIron"));
        HorizonCode_Horizon_È(308, "iron_leggings", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Ý, 2, 2).Â("leggingsIron"));
        HorizonCode_Horizon_È(309, "iron_boots", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Ý, 2, 3).Â("bootsIron"));
        HorizonCode_Horizon_È(310, "diamond_helmet", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Âµá€, 3, 0).Â("helmetDiamond"));
        HorizonCode_Horizon_È(311, "diamond_chestplate", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Âµá€, 3, 1).Â("chestplateDiamond"));
        HorizonCode_Horizon_È(312, "diamond_leggings", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Âµá€, 3, 2).Â("leggingsDiamond"));
        HorizonCode_Horizon_È(313, "diamond_boots", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Âµá€, 3, 3).Â("bootsDiamond"));
        HorizonCode_Horizon_È(314, "golden_helmet", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Ø­áŒŠá, 4, 0).Â("helmetGold"));
        HorizonCode_Horizon_È(315, "golden_chestplate", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Ø­áŒŠá, 4, 1).Â("chestplateGold"));
        HorizonCode_Horizon_È(316, "golden_leggings", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Ø­áŒŠá, 4, 2).Â("leggingsGold"));
        HorizonCode_Horizon_È(317, "golden_boots", new ItemArmor(ItemArmor.HorizonCode_Horizon_È.Ø­áŒŠá, 4, 3).Â("bootsGold"));
        HorizonCode_Horizon_È(318, "flint", new Item_1028566121().Â("flint").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(319, "porkchop", new ItemFood(3, 0.3f, true).Â("porkchopRaw"));
        HorizonCode_Horizon_È(320, "cooked_porkchop", new ItemFood(8, 0.8f, true).Â("porkchopCooked"));
        HorizonCode_Horizon_È(321, "painting", new ItemHangingEntity(EntityPainting.class).Â("painting"));
        HorizonCode_Horizon_È(322, "golden_apple", new ItemAppleGold(4, 1.2f, false).¥Æ().HorizonCode_Horizon_È(Potion.á.É, 5, 1, 1.0f).Â("appleGold"));
        HorizonCode_Horizon_È(323, "sign", new ItemSign().Â("sign"));
        HorizonCode_Horizon_È(324, "wooden_door", new ItemDoor(Blocks.Ï­Ô).Â("doorOak"));
        final Item_1028566121 var0 = new ItemBucket(Blocks.Â).Â("bucket").Â(16);
        HorizonCode_Horizon_È(325, "bucket", var0);
        HorizonCode_Horizon_È(326, "water_bucket", new ItemBucket(Blocks.áˆºÑ¢Õ).Â("bucketWater").Â(var0));
        HorizonCode_Horizon_È(327, "lava_bucket", new ItemBucket(Blocks.á).Â("bucketLava").Â(var0));
        HorizonCode_Horizon_È(328, "minecart", new ItemMinecart(EntityMinecart.HorizonCode_Horizon_È.HorizonCode_Horizon_È).Â("minecart"));
        HorizonCode_Horizon_È(329, "saddle", new ItemSaddle().Â("saddle"));
        HorizonCode_Horizon_È(330, "iron_door", new ItemDoor(Blocks.ŠÓ).Â("doorIron"));
        HorizonCode_Horizon_È(331, "redstone", new ItemRedstone().Â("redstone").Ý(PotionHelper.áŒŠÆ));
        HorizonCode_Horizon_È(332, "snowball", new ItemSnowball().Â("snowball"));
        HorizonCode_Horizon_È(333, "boat", new ItemBoat().Â("boat"));
        HorizonCode_Horizon_È(334, "leather", new Item_1028566121().Â("leather").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(335, "milk_bucket", new ItemBucketMilk().Â("milk").Â(var0));
        HorizonCode_Horizon_È(336, "brick", new Item_1028566121().Â("brick").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(337, "clay_ball", new Item_1028566121().Â("clay").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(338, "reeds", new ItemReed(Blocks.Ðƒáƒ).Â("reeds").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(339, "paper", new Item_1028566121().Â("paper").HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(340, "book", new ItemBook().Â("book").HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(341, "slime_ball", new Item_1028566121().Â("slimeball").HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(342, "chest_minecart", new ItemMinecart(EntityMinecart.HorizonCode_Horizon_È.Â).Â("minecartChest"));
        HorizonCode_Horizon_È(343, "furnace_minecart", new ItemMinecart(EntityMinecart.HorizonCode_Horizon_È.Ý).Â("minecartFurnace"));
        HorizonCode_Horizon_È(344, "egg", new ItemEgg().Â("egg"));
        HorizonCode_Horizon_È(345, "compass", new Item_1028566121().Â("compass").HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ));
        HorizonCode_Horizon_È(346, "fishing_rod", new ItemFishingRod().Â("fishingRod"));
        HorizonCode_Horizon_È(347, "clock", new Item_1028566121().Â("clock").HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ));
        HorizonCode_Horizon_È(348, "glowstone_dust", new Item_1028566121().Â("yellowDust").Ý(PotionHelper.áˆºÑ¢Õ).HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(349, "fish", new ItemFishFood(false).Â("fish").HorizonCode_Horizon_È(true));
        HorizonCode_Horizon_È(350, "cooked_fish", new ItemFishFood(true).Â("fish").HorizonCode_Horizon_È(true));
        HorizonCode_Horizon_È(351, "dye", new ItemDye().Â("dyePowder"));
        HorizonCode_Horizon_È(352, "bone", new Item_1028566121().Â("bone").Âµá€().HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(353, "sugar", new Item_1028566121().Â("sugar").Ý(PotionHelper.Â).HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(354, "cake", new ItemReed(Blocks.ÂµÂ).Â(1).Â("cake").HorizonCode_Horizon_È(CreativeTabs.Ø));
        HorizonCode_Horizon_È(355, "bed", new ItemBed().Â(1).Â("bed"));
        HorizonCode_Horizon_È(356, "repeater", new ItemReed(Blocks.áŒŠá).Â("diode").HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá));
        HorizonCode_Horizon_È(357, "cookie", new ItemFood(2, 0.1f, false).Â("cookie"));
        HorizonCode_Horizon_È(358, "filled_map", new ItemMap().Â("map"));
        HorizonCode_Horizon_È(359, "shears", new ItemShears().Â("shears"));
        HorizonCode_Horizon_È(360, "melon", new ItemFood(2, 0.3f, false).Â("melon"));
        HorizonCode_Horizon_È(361, "pumpkin_seeds", new ItemSeeds(Blocks.ÇªÉ, Blocks.£Â).Â("seeds_pumpkin"));
        HorizonCode_Horizon_È(362, "melon_seeds", new ItemSeeds(Blocks.ŠÏ­áˆºá, Blocks.£Â).Â("seeds_melon"));
        HorizonCode_Horizon_È(363, "beef", new ItemFood(3, 0.3f, true).Â("beefRaw"));
        HorizonCode_Horizon_È(364, "cooked_beef", new ItemFood(8, 0.8f, true).Â("beefCooked"));
        HorizonCode_Horizon_È(365, "chicken", new ItemFood(2, 0.3f, true).HorizonCode_Horizon_È(Potion.¥Æ.É, 30, 0, 0.3f).Â("chickenRaw"));
        HorizonCode_Horizon_È(366, "cooked_chicken", new ItemFood(6, 0.6f, true).Â("chickenCooked"));
        HorizonCode_Horizon_È(367, "rotten_flesh", new ItemFood(4, 0.1f, true).HorizonCode_Horizon_È(Potion.¥Æ.É, 30, 0, 0.8f).Â("rottenFlesh"));
        HorizonCode_Horizon_È(368, "ender_pearl", new ItemEnderPearl().Â("enderPearl"));
        HorizonCode_Horizon_È(369, "blaze_rod", new Item_1028566121().Â("blazeRod").HorizonCode_Horizon_È(CreativeTabs.á).Âµá€());
        HorizonCode_Horizon_È(370, "ghast_tear", new Item_1028566121().Â("ghastTear").Ý("+0-1-2-3&4-4+13").HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(371, "gold_nugget", new Item_1028566121().Â("goldNugget").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(372, "nether_wart", new ItemSeeds(Blocks.ÂµáˆºÂ, Blocks.ŠÕ).Â("netherStalkSeeds").Ý("+4"));
        HorizonCode_Horizon_È(373, "potion", new ItemPotion().Â("potion"));
        HorizonCode_Horizon_È(374, "glass_bottle", new ItemGlassBottle().Â("glassBottle"));
        HorizonCode_Horizon_È(375, "spider_eye", new ItemFood(2, 0.8f, false).HorizonCode_Horizon_È(Potion.µÕ.É, 5, 0, 1.0f).Â("spiderEye").Ý(PotionHelper.Ø­áŒŠá));
        HorizonCode_Horizon_È(376, "fermented_spider_eye", new Item_1028566121().Â("fermentedSpiderEye").Ý(PotionHelper.Âµá€).HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(377, "blaze_powder", new Item_1028566121().Â("blazePowder").Ý(PotionHelper.à).HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(378, "magma_cream", new Item_1028566121().Â("magmaCream").Ý(PotionHelper.Ø).HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(379, "brewing_stand", new ItemReed(Blocks.ÇŽÈ).Â("brewingStand").HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(380, "cauldron", new ItemReed(Blocks.ÇªáˆºÕ).Â("cauldron").HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(381, "ender_eye", new ItemEnderEye().Â("eyeOfEnder"));
        HorizonCode_Horizon_È(382, "speckled_melon", new Item_1028566121().Â("speckledMelon").Ý(PotionHelper.Ó).HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(383, "spawn_egg", new ItemMonsterPlacer().Â("monsterPlacer"));
        HorizonCode_Horizon_È(384, "experience_bottle", new ItemExpBottle().Â("expBottle"));
        HorizonCode_Horizon_È(385, "fire_charge", new ItemFireball().Â("fireball"));
        HorizonCode_Horizon_È(386, "writable_book", new ItemWritableBook().Â("writingBook").HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(387, "written_book", new ItemEditableBook().Â("writtenBook").Â(16));
        HorizonCode_Horizon_È(388, "emerald", new Item_1028566121().Â("emerald").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(389, "item_frame", new ItemHangingEntity(EntityItemFrame.class).Â("frame"));
        HorizonCode_Horizon_È(390, "flower_pot", new ItemReed(Blocks.ŠáŒŠà¢).Â("flowerPot").HorizonCode_Horizon_È(CreativeTabs.Ý));
        HorizonCode_Horizon_È(391, "carrot", new ItemSeedFood(3, 0.6f, Blocks.Ñ¢È, Blocks.£Â).Â("carrots"));
        HorizonCode_Horizon_È(392, "potato", new ItemSeedFood(1, 0.3f, Blocks.Çªáˆºá, Blocks.£Â).Â("potato"));
        HorizonCode_Horizon_È(393, "baked_potato", new ItemFood(5, 0.6f, false).Â("potatoBaked"));
        HorizonCode_Horizon_È(394, "poisonous_potato", new ItemFood(2, 0.3f, false).HorizonCode_Horizon_È(Potion.µÕ.É, 5, 0, 0.6f).Â("potatoPoisonous"));
        HorizonCode_Horizon_È(395, "map", new ItemEmptyMap().Â("emptyMap"));
        HorizonCode_Horizon_È(396, "golden_carrot", new ItemFood(6, 1.2f, false).Â("carrotGolden").Ý(PotionHelper.á).HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(397, "skull", new ItemSkull().Â("skull"));
        HorizonCode_Horizon_È(398, "carrot_on_a_stick", new ItemCarrotOnAStick().Â("carrotOnAStick"));
        HorizonCode_Horizon_È(399, "nether_star", new ItemSimpleFoiled().Â("netherStar").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(400, "pumpkin_pie", new ItemFood(8, 0.3f, false).Â("pumpkinPie").HorizonCode_Horizon_È(CreativeTabs.Ø));
        HorizonCode_Horizon_È(401, "fireworks", new ItemFirework().Â("fireworks"));
        HorizonCode_Horizon_È(402, "firework_charge", new ItemFireworkCharge().Â("fireworksCharge").HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(403, "enchanted_book", new ItemEnchantedBook().Â(1).Â("enchantedBook"));
        HorizonCode_Horizon_È(404, "comparator", new ItemReed(Blocks.ÐƒÇŽà).Â("comparator").HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá));
        HorizonCode_Horizon_È(405, "netherbrick", new Item_1028566121().Â("netherbrick").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(406, "quartz", new Item_1028566121().Â("netherquartz").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(407, "tnt_minecart", new ItemMinecart(EntityMinecart.HorizonCode_Horizon_È.Ø­áŒŠá).Â("minecartTnt"));
        HorizonCode_Horizon_È(408, "hopper_minecart", new ItemMinecart(EntityMinecart.HorizonCode_Horizon_È.Ó).Â("minecartHopper"));
        HorizonCode_Horizon_È(409, "prismarine_shard", new Item_1028566121().Â("prismarineShard").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(410, "prismarine_crystals", new Item_1028566121().Â("prismarineCrystals").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(411, "rabbit", new ItemFood(3, 0.3f, true).Â("rabbitRaw"));
        HorizonCode_Horizon_È(412, "cooked_rabbit", new ItemFood(5, 0.6f, true).Â("rabbitCooked"));
        HorizonCode_Horizon_È(413, "rabbit_stew", new ItemSoup(10).Â("rabbitStew"));
        HorizonCode_Horizon_È(414, "rabbit_foot", new Item_1028566121().Â("rabbitFoot").Ý(PotionHelper.£á).HorizonCode_Horizon_È(CreativeTabs.ÂµÈ));
        HorizonCode_Horizon_È(415, "rabbit_hide", new Item_1028566121().Â("rabbitHide").HorizonCode_Horizon_È(CreativeTabs.á));
        HorizonCode_Horizon_È(416, "armor_stand", new ItemArmorStand().Â("armorStand").Â(16));
        HorizonCode_Horizon_È(417, "iron_horse_armor", new Item_1028566121().Â("horsearmormetal").Â(1).HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(418, "golden_horse_armor", new Item_1028566121().Â("horsearmorgold").Â(1).HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(419, "diamond_horse_armor", new Item_1028566121().Â("horsearmordiamond").Â(1).HorizonCode_Horizon_È(CreativeTabs.Ó));
        HorizonCode_Horizon_È(420, "lead", new ItemLead().Â("leash"));
        HorizonCode_Horizon_È(421, "name_tag", new ItemNameTag().Â("nameTag"));
        HorizonCode_Horizon_È(422, "command_block_minecart", new ItemMinecart(EntityMinecart.HorizonCode_Horizon_È.à).Â("minecartCommandBlock").HorizonCode_Horizon_È((CreativeTabs)null));
        HorizonCode_Horizon_È(423, "mutton", new ItemFood(2, 0.3f, true).Â("muttonRaw"));
        HorizonCode_Horizon_È(424, "cooked_mutton", new ItemFood(6, 0.8f, true).Â("muttonCooked"));
        HorizonCode_Horizon_È(425, "banner", new ItemBanner().Ø­áŒŠá("banner"));
        HorizonCode_Horizon_È(427, "spruce_door", new ItemDoor(Blocks.Œà).Â("doorSpruce"));
        HorizonCode_Horizon_È(428, "birch_door", new ItemDoor(Blocks.Ðƒá).Â("doorBirch"));
        HorizonCode_Horizon_È(429, "jungle_door", new ItemDoor(Blocks.ˆÏ).Â("doorJungle"));
        HorizonCode_Horizon_È(430, "acacia_door", new ItemDoor(Blocks.áˆºÇŽØ).Â("doorAcacia"));
        HorizonCode_Horizon_È(431, "dark_oak_door", new ItemDoor(Blocks.ÇªÂµÕ).Â("doorDarkOak"));
        HorizonCode_Horizon_È(2256, "record_13", new ItemRecord("13").Â("record"));
        HorizonCode_Horizon_È(2257, "record_cat", new ItemRecord("cat").Â("record"));
        HorizonCode_Horizon_È(2258, "record_blocks", new ItemRecord("blocks").Â("record"));
        HorizonCode_Horizon_È(2259, "record_chirp", new ItemRecord("chirp").Â("record"));
        HorizonCode_Horizon_È(2260, "record_far", new ItemRecord("far").Â("record"));
        HorizonCode_Horizon_È(2261, "record_mall", new ItemRecord("mall").Â("record"));
        HorizonCode_Horizon_È(2262, "record_mellohi", new ItemRecord("mellohi").Â("record"));
        HorizonCode_Horizon_È(2263, "record_stal", new ItemRecord("stal").Â("record"));
        HorizonCode_Horizon_È(2264, "record_strad", new ItemRecord("strad").Â("record"));
        HorizonCode_Horizon_È(2265, "record_ward", new ItemRecord("ward").Â("record"));
        HorizonCode_Horizon_È(2266, "record_11", new ItemRecord("11").Â("record"));
        HorizonCode_Horizon_È(2267, "record_wait", new ItemRecord("wait").Â("record"));
    }
    
    private static void Ý(final Block blockIn) {
        HorizonCode_Horizon_È(blockIn, new ItemBlock(blockIn));
    }
    
    protected static void HorizonCode_Horizon_È(final Block blockIn, final Item_1028566121 itemIn) {
        HorizonCode_Horizon_È(Block.HorizonCode_Horizon_È(blockIn), (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(blockIn), itemIn);
        Item_1028566121.à.put(blockIn, itemIn);
    }
    
    private static void HorizonCode_Horizon_È(final int id, final String textualID, final Item_1028566121 itemIn) {
        HorizonCode_Horizon_È(id, new ResourceLocation_1975012498(textualID), itemIn);
    }
    
    private static void HorizonCode_Horizon_È(final int id, final ResourceLocation_1975012498 textualID, final Item_1028566121 itemIn) {
        Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È(id, textualID, itemIn);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("WOOD", 0, "WOOD", 0, 0, 59, 2.0f, 0.0f, 15), 
        Â("STONE", 1, "STONE", 1, 1, 131, 4.0f, 1.0f, 5), 
        Ý("IRON", 2, "IRON", 2, 2, 250, 6.0f, 2.0f, 14), 
        Ø­áŒŠá("EMERALD", 3, "EMERALD", 3, 3, 1561, 8.0f, 3.0f, 10), 
        Âµá€("GOLD", 4, "GOLD", 4, 0, 32, 12.0f, 0.0f, 22);
        
        private final int Ó;
        private final int à;
        private final float Ø;
        private final float áŒŠÆ;
        private final int áˆºÑ¢Õ;
        private static final HorizonCode_Horizon_È[] ÂµÈ;
        private static final String á = "CL_00000042";
        
        static {
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            ÂµÈ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i1874_1_, final int p_i1874_2_, final int harvestLevel, final int maxUses, final float efficiency, final float damageVsEntity, final int enchantability) {
            this.Ó = harvestLevel;
            this.à = maxUses;
            this.Ø = efficiency;
            this.áŒŠÆ = damageVsEntity;
            this.áˆºÑ¢Õ = enchantability;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.à;
        }
        
        public float Â() {
            return this.Ø;
        }
        
        public float Ý() {
            return this.áŒŠÆ;
        }
        
        public int Ø­áŒŠá() {
            return this.Ó;
        }
        
        public int Âµá€() {
            return this.áˆºÑ¢Õ;
        }
        
        public Item_1028566121 Ó() {
            return (this == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? Item_1028566121.HorizonCode_Horizon_È(Blocks.à) : ((this == HorizonCode_Horizon_È.Â) ? Item_1028566121.HorizonCode_Horizon_È(Blocks.Ó) : ((this == HorizonCode_Horizon_È.Âµá€) ? Items.ÂµÈ : ((this == HorizonCode_Horizon_È.Ý) ? Items.áˆºÑ¢Õ : ((this == HorizonCode_Horizon_È.Ø­áŒŠá) ? Items.áŒŠÆ : null))));
        }
    }
}
