package HORIZON-6-0-SKIDPROTECTION;

public class EntityMinecartChest extends EntityMinecartContainer
{
    private static final String HorizonCode_Horizon_È = "CL_00001671";
    
    public EntityMinecartChest(final World worldIn) {
        super(worldIn);
    }
    
    public EntityMinecartChest(final World worldIn, final double p_i1715_2_, final double p_i1715_4_, final double p_i1715_6_) {
        super(worldIn, p_i1715_2_, p_i1715_4_, p_i1715_6_);
    }
    
    @Override
    public void Â(final DamageSource p_94095_1_) {
        super.Â(p_94095_1_);
        this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.ˆáƒ), 1, 0.0f);
    }
    
    @Override
    public int áŒŠÆ() {
        return 27;
    }
    
    @Override
    public HorizonCode_Horizon_È à() {
        return EntityMinecart.HorizonCode_Horizon_È.Â;
    }
    
    @Override
    public IBlockState Ø() {
        return Blocks.ˆáƒ.¥à().HorizonCode_Horizon_È(BlockChest.Õ, EnumFacing.Ý);
    }
    
    @Override
    public int Ï­Ðƒà() {
        return 8;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "minecraft:chest";
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerChest(playerInventory, this, playerIn);
    }
}
