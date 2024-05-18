package wwe.modules.player;

import wwe.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.util.text.*;

public class AutoSign extends qMIe
{
    public static ITextComponent[] ALQZ;
    
    public AutoSign() {
        super("Auto Sign", "Automatically places set text on a sign", 0, Category.PLAYER);
    }
    
    @Override
    public void LPxx() {
        Wrapper.ypJK().CLso();
    }
    
    static {
        AutoSign.ALQZ = new ITextComponent[] { (ITextComponent)new TextComponentString("I use WWE Client"), (ITextComponent)new TextComponentString("its the best free"), (ITextComponent)new TextComponentString("anarchy mod/"), (ITextComponent)new TextComponentString("utility mod") };
    }
}
