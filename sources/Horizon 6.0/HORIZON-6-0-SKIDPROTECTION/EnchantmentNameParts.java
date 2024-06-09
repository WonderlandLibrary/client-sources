package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class EnchantmentNameParts
{
    private static final EnchantmentNameParts HorizonCode_Horizon_È;
    private Random Â;
    private String[] Ý;
    private static final String Ø­áŒŠá = "CL_00000756";
    
    static {
        HorizonCode_Horizon_È = new EnchantmentNameParts();
    }
    
    public EnchantmentNameParts() {
        this.Â = new Random();
        this.Ý = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
    }
    
    public static EnchantmentNameParts HorizonCode_Horizon_È() {
        return EnchantmentNameParts.HorizonCode_Horizon_È;
    }
    
    public String Â() {
        final int var1 = this.Â.nextInt(2) + 3;
        String var2 = "";
        for (int var3 = 0; var3 < var1; ++var3) {
            if (var3 > 0) {
                var2 = String.valueOf(var2) + " ";
            }
            var2 = String.valueOf(var2) + this.Ý[this.Â.nextInt(this.Ý.length)];
        }
        return var2;
    }
    
    public void HorizonCode_Horizon_È(final long p_148335_1_) {
        this.Â.setSeed(p_148335_1_);
    }
}
