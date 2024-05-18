package wwe.modules.render;

import java.util.*;
import net.minecraft.block.*;
import wwe.modules.*;
import wwe.utils.*;
import wwe.*;

public class Xray extends qMIe
{
    public static ArrayList<Block> dMba;
    public static boolean yolQ;
    
    public Xray() {
        super("Xray", "Lets you see ores and blocks underground", 45, Category.RENDER);
    }
    
    @Override
    public void LPxx() {
        if (Wrapper.mc.world != null && Wrapper.ypJK().zzbo && !Xray.yolQ) {
            HAxG.TuzD("Hello! It appears this is your first time loading wwe for 1.12 & toggling xray, So we are going show you how to add blocks to the xray. The command is .xray add/remove blockname, example .xray add diamond_ore, if you type that in chat it will add diamond ore to the xray list!");
            Xray.yolQ = true;
        }
        Wrapper.mc.renderGlobal.loadRenderers();
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        Wrapper.mc.renderGlobal.loadRenderers();
        super.qBmy();
    }
    
    public static boolean fNgC(final Block block) {
        return Xray.dMba.contains(block);
    }
    
    static {
        Xray.dMba = new ArrayList<Block>();
    }
}
