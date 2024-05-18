package wwe;

import java.awt.*;
import net.minecraft.block.*;
import wwe.modules.render.*;
import wwe.utils.*;
import java.util.*;

public class lRvb extends HAxG
{
    public lRvb() {
        super("search");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 1) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            if (array[0].equalsIgnoreCase("add")) {
                if (array.length < 5) {
                    HAxG.TuzD(this.ZlRY());
                    return;
                }
                final Color color = new Color(Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4]));
                if (Block.getBlockFromName(array[1]) != null) {
                    if (Search.Hxmz.containsKey(Block.getBlockFromName(array[1]))) {
                        HAxG.TuzD("Thats already in the search list");
                        return;
                    }
                    Search.Hxmz.put(Block.getBlockFromName(array[1]), color);
                    Wrapper.ypJK().JDPi();
                    HAxG.TuzD("Added " + array[1] + " to the search list");
                    if (((QSTh)vlwv.hNxW.qHPF((Class<Search>)QSTh.class)).IuiN()) {
                        Wrapper.mc.renderGlobal.loadRenderers();
                        if (lRvb.\u2006\u200e\u2005\u2005\u2009\u2003\u2003\u2006\u200f) {
                            throw null;
                        }
                    }
                }
                else {
                    HAxG.TuzD("Thats not a block?");
                }
                if (lRvb.\u2006\u200e\u2005\u2005\u2009\u2003\u2003\u2006\u200f) {
                    throw null;
                }
            }
            else if (this.wJNH(array[0])) {
                if (Block.getBlockFromName(array[1]) != null) {
                    if (!Search.Hxmz.containsKey(Block.getBlockFromName(array[1]))) {
                        HAxG.TuzD("Thats not in the search list");
                        return;
                    }
                    final HashMap<Block, Object> hxmz = new HashMap<Block, Object>();
                    for (final Map.Entry<Block, Color> entry : Search.Hxmz.entrySet()) {
                        if (entry.getKey() != Block.getBlockFromName(array[1])) {
                            hxmz.put(entry.getKey(), entry.getValue());
                        }
                        if (lRvb.\u2006\u200e\u2005\u2005\u2009\u2003\u2003\u2006\u200f) {
                            throw null;
                        }
                    }
                    Search.Hxmz = (HashMap<Block, Color>)hxmz;
                    Wrapper.ypJK().JDPi();
                    Search.Hxmz.remove(Block.getBlockFromName(array[1]));
                    HAxG.TuzD("Removed " + array[1] + " from the search list");
                    if (((QSTh)vlwv.hNxW.qHPF((Class<Search>)QSTh.class)).IuiN()) {
                        Wrapper.mc.renderGlobal.loadRenderers();
                    }
                    if (lRvb.\u2006\u200e\u2005\u2005\u2009\u2003\u2003\u2006\u200f) {
                        throw null;
                    }
                }
                else {
                    HAxG.TuzD("Thats not a block?");
                    if (lRvb.\u2006\u200e\u2005\u2005\u2009\u2003\u2003\u2006\u200f) {
                        throw null;
                    }
                }
            }
            else {
                HAxG.TuzD(this.ZlRY());
            }
            if (lRvb.\u2006\u200e\u2005\u2005\u2009\u2003\u2003\u2006\u200f) {
                throw null;
            }
        }
        catch (Exception ex) {
            HAxG.TuzD("Maybe one or more of the colors is incorrect, pick a number between 0 and 255");
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "add/remove a block from the search module";
    }
    
    @Override
    public String ZlRY() {
        return "search add/remove blockname red green blue";
    }
}
