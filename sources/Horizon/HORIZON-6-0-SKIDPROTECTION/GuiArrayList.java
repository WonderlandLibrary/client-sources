package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiArrayList
{
    private TimeHelper HorizonCode_Horizon_È;
    private Map<String, String> Â;
    
    public GuiArrayList() {
        this.Â = new HashMap<String, String>();
        this.HorizonCode_Horizon_È = new TimeHelper();
    }
    
    public void HorizonCode_Horizon_È() {
        final ScaledResolution sr = new ScaledResolution(Minecraft.áŒŠà(), Minecraft.áŒŠà().Ó, Minecraft.áŒŠà().à);
        final int width = sr.HorizonCode_Horizon_È();
        final int height = sr.Â();
        int i = 0;
        this.Ý();
        this.Â();
        final ArrayList display = new ArrayList();
        for (final Mod x1 : Horizon.HorizonCode_Horizon_È.áˆºÏ.HorizonCode_Horizon_È()) {
            if (!x1.ÂµÈ().HorizonCode_Horizon_È().equalsIgnoreCase("gui") && (x1.áˆºÑ¢Õ() || x1.à())) {
                display.add(x1.Ý());
            }
        }
        Collections.sort((List<Object>)display, (Comparator<? super Object>)this.HorizonCode_Horizon_È(Minecraft.áŒŠà().µà));
        for (final String var10 : display) {
            final int mwidth = sr.HorizonCode_Horizon_È() - 0;
            final int mheight = 10 * i + 1;
            final List<String> activemods = (List<String>)display;
            int col = -1;
            for (final String count : activemods) {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                final Mod module = ModuleManager.Â.get(count);
                final int mods = activemods.indexOf(count);
                try {
                    if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(var10, mwidth - Horizon.à¢.áˆºÏ.Â(var10).áŒŠÆ(), (float)mheight, -1618884);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(var10, mwidth - Horizon.à¢.áˆºÏ.Â(var10).áŒŠÆ(), (float)mheight, -13330213);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(var10, mwidth - Horizon.à¢.áˆºÏ.Â(var10).áŒŠÆ(), (float)mheight, -13710223);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
                        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(var10, mwidth - Horizon.à¢.áˆºÏ.Â(var10).áŒŠÆ(), (float)mheight, -21744);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
                        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(var10, mwidth - Horizon.à¢.áˆºÏ.Â(var10).áŒŠÆ(), (float)mheight, -3394561);
                    }
                    else {
                        if (!Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                            continue;
                        }
                        col = ColorUtil.HorizonCode_Horizon_È(mods * 140000000L, 1.0f).getRGB();
                        module.HorizonCode_Horizon_È(col);
                        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(var10, mwidth - Horizon.HorizonCode_Horizon_È.áˆºÏ.Â(var10).áŒŠÆ(), (float)mheight, Horizon.HorizonCode_Horizon_È.áˆºÏ.Â(var10).Ø­áŒŠá(), true);
                    }
                }
                catch (NullPointerException ex) {}
            }
            ++i;
        }
    }
    
    public void Â() {
        for (final Mod m : Horizon.HorizonCode_Horizon_È.áˆºÏ.HorizonCode_Horizon_È()) {
            final Iterator localIterator2 = this.Â.entrySet().iterator();
            localIterator2.hasNext();
        }
        this.Â.clear();
        for (final Mod i : Horizon.HorizonCode_Horizon_È.áˆºÏ.HorizonCode_Horizon_È()) {
            this.Â.put(i.ÂµÈ().HorizonCode_Horizon_È(), i.Ý());
        }
    }
    
    public Comparator<String> HorizonCode_Horizon_È(final FontRenderer fr) {
        return new Comparator<String>() {
            public int HorizonCode_Horizon_È(final String arg0, final String arg1) {
                return (fr.HorizonCode_Horizon_È(arg0) > fr.HorizonCode_Horizon_È(arg1)) ? -1 : ((fr.HorizonCode_Horizon_È(arg1) > fr.HorizonCode_Horizon_È(arg0)) ? 1 : 0);
            }
        };
    }
    
    public void Ý() {
        if (this.HorizonCode_Horizon_È.Â(10L)) {
            this.HorizonCode_Horizon_È.Ø­áŒŠá();
            for (final Mod x : Horizon.HorizonCode_Horizon_È.áˆºÏ.HorizonCode_Horizon_È()) {
                if (x.Ø()) {
                    x.Â(false);
                    if (x.áŒŠÆ() >= Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(x.Ý())) {
                        x.Ý(false);
                        x.Ý(Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(x.Ý()));
                    }
                    else {
                        x.Ý(x.áŒŠÆ() + 3);
                    }
                }
                else {
                    if (!x.à()) {
                        continue;
                    }
                    if (x.áŒŠÆ() <= 0) {
                        x.Â(false);
                        x.Ý(0);
                    }
                    x.Ý(x.áŒŠÆ() - 3);
                }
            }
        }
    }
}
