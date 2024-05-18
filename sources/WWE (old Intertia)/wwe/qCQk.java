package wwe;

import net.minecraft.block.*;
import wwe.modules.render.*;
import wwe.utils.*;

public class qCQk extends HAxG
{
    public qCQk() {
        super("xray");
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
                    if (Xray.dMba.contains(Block.getBlockFromName(array[1]))) {
                        HAxG.TuzD("Thats already in the xray list");
                        return;
                    }
                    Xray.dMba.add(Block.getBlockFromName(array[1]));
                    HAxG.TuzD("Added " + array[1] + " to the xray list");
                    Wrapper.ypJK().TQyP();
                    if (vlwv.hNxW.qHPF((Class<Xray>)jCBS.class).IuiN()) {
                        Wrapper.mc.renderGlobal.loadRenderers();
                        if (qCQk.\u2003\u2008\u200d) {
                            throw null;
                        }
                    }
                }
                else {
                    HAxG.TuzD("Thats not a block?");
                    if (qCQk.\u2003\u2008\u200d) {
                        throw null;
                    }
                }
            }
            else if (this.wJNH(array[0])) {
                if (Block.getBlockFromName(array[1]) != null) {
                    if (!Xray.dMba.contains(Block.getBlockFromName(array[1]))) {
                        HAxG.TuzD("Thats not in the xray list");
                        return;
                    }
                    Xray.dMba.remove(Block.getBlockFromName(array[1]));
                    HAxG.TuzD("Removed " + array[1] + " from the xray list");
                    Wrapper.ypJK().TQyP();
                    if (vlwv.hNxW.qHPF((Class<Xray>)jCBS.class).IuiN()) {
                        Wrapper.mc.renderGlobal.loadRenderers();
                        if (qCQk.\u2003\u2008\u200d) {
                            throw null;
                        }
                    }
                }
                else {
                    HAxG.TuzD("Thats not a block?");
                    if (qCQk.\u2003\u2008\u200d) {
                        throw null;
                    }
                }
            }
            else {
                HAxG.TuzD(this.ZlRY());
            }
            if (qCQk.\u2003\u2008\u200d) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "add/remove blocks from xray";
    }
    
    @Override
    public String ZlRY() {
        return "xray add/remove name of block";
    }
}
