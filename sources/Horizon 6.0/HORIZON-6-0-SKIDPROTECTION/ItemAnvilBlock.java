package HORIZON-6-0-SKIDPROTECTION;

public class ItemAnvilBlock extends ItemMultiTexture
{
    private static final String áˆºÑ¢Õ = "CL_00001764";
    
    public ItemAnvilBlock(final Block p_i1826_1_) {
        super(p_i1826_1_, p_i1826_1_, new String[] { "intact", "slightlyDamaged", "veryDamaged" });
    }
    
    @Override
    public int Ý(final int damage) {
        return damage << 2;
    }
}
