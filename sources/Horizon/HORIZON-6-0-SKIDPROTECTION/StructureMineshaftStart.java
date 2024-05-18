package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class StructureMineshaftStart extends StructureStart
{
    private static final String Ý = "CL_00000450";
    
    public StructureMineshaftStart() {
    }
    
    public StructureMineshaftStart(final World worldIn, final Random p_i2039_2_, final int p_i2039_3_, final int p_i2039_4_) {
        super(p_i2039_3_, p_i2039_4_);
        final StructureMineshaftPieces.Ý var5 = new StructureMineshaftPieces.Ý(0, p_i2039_2_, (p_i2039_3_ << 4) + 2, (p_i2039_4_ << 4) + 2);
        this.HorizonCode_Horizon_È.add(var5);
        var5.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È, p_i2039_2_);
        this.Ø­áŒŠá();
        this.HorizonCode_Horizon_È(worldIn, p_i2039_2_, 10);
    }
}
