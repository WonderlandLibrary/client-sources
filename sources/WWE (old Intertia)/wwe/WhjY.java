package wwe;

import wwe.utils.*;

public class WhjY extends HAxG
{
    public WhjY() {
        super("guireset");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        if (array.length < 1) {
            HAxG.TuzD(this.ZlRY());
            return;
        }
        if (array[0].equalsIgnoreCase("preset") || array[0].equalsIgnoreCase("presets")) {
            final hEGx prNz = vlwv.pRNz;
            final XdhZ aTlJ = hEGx.aTlJ;
            final hEGx prNz2 = vlwv.pRNz;
            aTlJ.OsiI = hEGx.aTlJ.KyWB;
            final hEGx prNz3 = vlwv.pRNz;
            final XdhZ aTlJ2 = hEGx.aTlJ;
            final hEGx prNz4 = vlwv.pRNz;
            aTlJ2.zzJD = hEGx.aTlJ.PVCD;
            Wrapper.ypJK().VvWw();
            HAxG.TuzD("Reset the position of the frame presets");
            return;
        }
        if (array[0].equalsIgnoreCase("all")) {
            int i = 0;
            while (i < vlwv.pRNz.nYFA.size()) {
                final FinI finI = vlwv.pRNz.nYFA.get(i);
                finI.kVnf = finI.ypmq;
                finI.CQIZ = finI.noHS;
                Wrapper.ypJK().VvWw();
                ++i;
                if (WhjY.\u2006\u200f\u200d\u200b\u2005\u2007) {
                    throw null;
                }
            }
            final hEGx prNz5 = vlwv.pRNz;
            final XdhZ aTlJ3 = hEGx.aTlJ;
            final hEGx prNz6 = vlwv.pRNz;
            aTlJ3.OsiI = hEGx.aTlJ.KyWB;
            final hEGx prNz7 = vlwv.pRNz;
            final XdhZ aTlJ4 = hEGx.aTlJ;
            final hEGx prNz8 = vlwv.pRNz;
            aTlJ4.zzJD = hEGx.aTlJ.PVCD;
            Wrapper.ypJK().VvWw();
            HAxG.TuzD("Reset positions of all frames");
            return;
        }
        int j = 0;
        while (j < vlwv.pRNz.nYFA.size()) {
            final FinI finI2 = vlwv.pRNz.nYFA.get(j);
            if (finI2.XLez.name().equalsIgnoreCase(array[0])) {
                finI2.kVnf = finI2.ypmq;
                finI2.CQIZ = finI2.noHS;
                Wrapper.ypJK().VvWw();
                HAxG.TuzD("Reset the position of the frame " + finI2.XLez.name());
                return;
            }
            ++j;
            if (WhjY.\u2006\u200f\u200d\u200b\u2005\u2007) {
                throw null;
            }
        }
    }
    
    @Override
    public String UiIp() {
        return "Reset a frames position in the gui";
    }
    
    @Override
    public String ZlRY() {
        return "guireset FrameName/All";
    }
}
