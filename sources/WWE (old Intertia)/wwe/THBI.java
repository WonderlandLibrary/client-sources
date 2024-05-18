package wwe;

import wwe.utils.*;

public class THBI extends HAxG
{
    public THBI() {
        super("friend");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 2) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            if (array[0].equalsIgnoreCase("add")) {
                if (vlwv.Zqnd().UsVJ(array[1])) {
                    HAxG.TuzD(array[1] + " is already your friend");
                    if (THBI.\u200c\u200b\u200b\u2002\u2006\u2003\u200a\u200c\u200e) {
                        throw null;
                    }
                }
                else {
                    vlwv.Zqnd().kDXm(array[1], array[1]);
                    HAxG.TuzD(array[1] + " has been added to the friends list");
                    Wrapper.ypJK().pRVY();
                    if (THBI.\u200c\u200b\u200b\u2002\u2006\u2003\u200a\u200c\u200e) {
                        throw null;
                    }
                }
            }
            else if (this.wJNH(array[0])) {
                if (!vlwv.Zqnd().UsVJ(array[1])) {
                    HAxG.TuzD(array[1] + " isent your friend");
                    if (THBI.\u200c\u200b\u200b\u2002\u2006\u2003\u200a\u200c\u200e) {
                        throw null;
                    }
                }
                else {
                    vlwv.Zqnd().bSEt(array[1]);
                    HAxG.TuzD("Removed " + array[1] + " from the friends list");
                    Wrapper.ypJK().pRVY();
                    if (THBI.\u200c\u200b\u200b\u2002\u2006\u2003\u200a\u200c\u200e) {
                        throw null;
                    }
                }
            }
            else {
                HAxG.TuzD(this.ZlRY());
            }
            if (THBI.\u200c\u200b\u200b\u2002\u2006\u2003\u200a\u200c\u200e) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "Adds a friend";
    }
    
    @Override
    public String ZlRY() {
        return "friend add/remove name";
    }
}
