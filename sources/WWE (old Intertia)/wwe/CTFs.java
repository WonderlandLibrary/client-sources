package wwe;

import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class CTFs extends gkrg
{
    public BlockPos phME;
    
    public CTFs(final BlockPos phME) {
        super();
        this.phME = phME;
    }
    
    public static class wwe/BnvE extends CTFs
    {
        public EnumHand oeXY;
        
        public wwe/BnvE(final BlockPos blockPos, final EnumHand oeXY) {
            super(blockPos);
            this.oeXY = oeXY;
        }
    }
    
    public static class wwe/GJnV extends CTFs
    {
        public wwe/GJnV(final BlockPos blockPos) {
            super(blockPos);
        }
    }
    
    public static class wwe/MQil extends CTFs
    {
        public wwe/MQil(final BlockPos blockPos) {
            super(blockPos);
        }
    }
}
