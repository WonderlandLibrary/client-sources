package wwe;

import net.minecraft.block.*;
import wwe.modules.world.*;
import wwe.utils.*;

public class ddkD extends HAxG
{
    public ddkD() {
        super("nuker");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 2) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            if (array[0].equalsIgnoreCase("add")) {
                if (Block.getBlockFromName(array[1]) != null) {
                    if (Nuker.mTio.contains(Block.getBlockFromName(array[1]))) {
                        HAxG.TuzD("Thats already in the nuker list");
                        return;
                    }
                    Nuker.mTio.add(Block.getBlockFromName(array[1]));
                    HAxG.TuzD("Added " + array[1] + " to the nuker list");
                    Wrapper.ypJK().pRYs();
                    if (ddkD.\u2009\u2004\u2004\u2007\u200e) {
                        throw null;
                    }
                }
                else {
                    HAxG.TuzD("Thats not a block?");
                    if (ddkD.\u2009\u2004\u2004\u2007\u200e) {
                        throw null;
                    }
                }
            }
            else if (this.wJNH(array[0])) {
                if (Block.getBlockFromName(array[1]) != null) {
                    if (!Nuker.mTio.contains(Block.getBlockFromName(array[1]))) {
                        HAxG.TuzD("Thats not in the nuker list");
                        return;
                    }
                    Nuker.mTio.remove(Block.getBlockFromName(array[1]));
                    HAxG.TuzD("Removed " + array[1] + " from the nuker list");
                    Wrapper.ypJK().pRYs();
                    if (ddkD.\u2009\u2004\u2004\u2007\u200e) {
                        throw null;
                    }
                }
                else {
                    HAxG.TuzD("Thats not a block?");
                    if (ddkD.\u2009\u2004\u2004\u2007\u200e) {
                        throw null;
                    }
                }
            }
            else {
                HAxG.TuzD(this.ZlRY());
            }
            if (ddkD.\u2009\u2004\u2004\u2007\u200e) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "add/remove blocks from nuker, without option allBlocks on";
    }
    
    @Override
    public String ZlRY() {
        return "nuker add/remove name of block";
    }
}
