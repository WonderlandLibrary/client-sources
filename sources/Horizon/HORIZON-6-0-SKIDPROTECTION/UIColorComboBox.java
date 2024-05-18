package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class UIColorComboBox extends Item
{
    private List<String> HorizonCode_Horizon_È;
    private boolean à;
    
    public UIColorComboBox(final List<String> modes) {
        this.HorizonCode_Horizon_È = modes;
    }
    
    private Long à() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float k) {
        final String t1 = this.Ó().substring(0, 1).toUpperCase();
        final String t2 = this.Ó().substring(1, this.Ó().length());
        final String t3 = String.valueOf(t1) + t2;
        final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
        int textWidth = Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(t3);
        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + 15, 1.0f, 0, -754974720);
        if (Horizon.à.equalsIgnoreCase("Arial")) {
            UIFonts.Ý.HorizonCode_Horizon_È(t3, this.Â() + this.Ø­áŒŠá() / 2 - textWidth / 2, this.Ý() + 3 - 2, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("Helvetica")) {
            UIFonts.Ø­à.HorizonCode_Horizon_È(t3, this.Â() + this.Ø­áŒŠá() / 2 - textWidth / 2, this.Ý() + 3 - 0, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("Comfortaa")) {
            UIFonts.µÕ.HorizonCode_Horizon_È(t3, this.Â() + this.Ø­áŒŠá() / 2 - textWidth / 2 - 1, this.Ý() + 3 - 1, -1249039);
        }
        if (this.à) {
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 15, this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), 1.0f, 0, -1560281088);
            int top = 0;
            for (final String x : this.HorizonCode_Horizon_È) {
                top += Minecraft.áŒŠà().µà.HorizonCode_Horizon_È + 6;
                final String tt1 = x.substring(0, 1).toUpperCase();
                final String tt2 = x.substring(1, x.length());
                final String tt3 = String.valueOf(tt1) + tt2;
                textWidth = Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(tt3);
                if (Horizon.à.equalsIgnoreCase("Arial")) {
                    UIFonts.Ý.HorizonCode_Horizon_È(tt3, this.Â() + 5, this.Ý() + 3 - 2 + top, -1249039);
                }
                else if (Horizon.à.equalsIgnoreCase("SegoeUI")) {
                    UIFonts.µà.HorizonCode_Horizon_È(tt3, this.Â() + 5, this.Ý() + 3 - 3 + top, -1249039);
                }
                else if (Horizon.à.equalsIgnoreCase("Helvetica")) {
                    UIFonts.Ø­à.HorizonCode_Horizon_È(tt3, this.Â() + 5, this.Ý() + 3 - 0 + top, -1249039);
                }
                else {
                    if (!Horizon.à.equalsIgnoreCase("Comfortaa")) {
                        continue;
                    }
                    UIFonts.µÕ.HorizonCode_Horizon_È(tt3, this.Â() + 5, this.Ý() + 3 - 1 + top, -1249039);
                }
            }
        }
        if (this.à) {
            int y = 0;
            for (final String x : this.HorizonCode_Horizon_È) {
                y += UIFonts.Ý.HorizonCode_Horizon_È + 2;
                if (i >= this.Â() && i <= this.Â() + this.Ø­áŒŠá() && j >= this.Ý() + y && j <= this.Ý() + 14 + y) {
                    if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â() + this.Ø­áŒŠá() - 1, this.Ý() + 15 + y - 12, this.Â() + this.Ø­áŒŠá(), this.Ý() + 15 + y, 1.0f, 0, -15024996);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â() + this.Ø­áŒŠá() - 1, this.Ý() + 15 + y - 12, this.Â() + this.Ø­áŒŠá(), this.Ý() + 15 + y, 1.0f, 0, -1618884);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â() + this.Ø­áŒŠá() - 1, this.Ý() + 15 + y - 12, this.Â() + this.Ø­áŒŠá(), this.Ý() + 15 + y, 1.0f, 0, -13710223);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â() + this.Ø­áŒŠá() - 1, this.Ý() + 15 + y - 12, this.Â() + this.Ø­áŒŠá(), this.Ý() + 15 + y, 1.0f, 0, -3394561);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â() + this.Ø­áŒŠá() - 1, this.Ý() + 15 + y - 12, this.Â() + this.Ø­áŒŠá(), this.Ý() + 15 + y, 1.0f, 0, -21744);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â() + this.Ø­áŒŠá() - 1, this.Ý() + 15 + y - 12, this.Â() + this.Ø­áŒŠá(), this.Ý() + 15 + y, 1.0f, 0, color);
                    }
                }
                if (x.equalsIgnoreCase(this.Ó())) {
                    if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 15 + y - 12, this.Â() + 1, this.Ý() + 15 + y, 1.0f, 0, -15024996);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 15 + y - 12, this.Â() + 1, this.Ý() + 15 + y, 1.0f, 0, -1618884);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 15 + y - 12, this.Â() + 1, this.Ý() + 15 + y, 1.0f, 0, -13710223);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 15 + y - 12, this.Â() + 1, this.Ý() + 15 + y, 1.0f, 0, -3394561);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 15 + y - 12, this.Â() + 1, this.Ý() + 15 + y, 1.0f, 0, -21744);
                    }
                    else {
                        if (!Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                            continue;
                        }
                        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 15 + y - 12, this.Â() + 1, this.Ý() + 15 + y, 1.0f, 0, color);
                    }
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final int k) {
        if (k == 0 && this.Â(i, j)) {
            this.à = !this.à;
            if (this.à) {
                Minecraft.áŒŠà().á.HorizonCode_Horizon_È("tile.piston.in", 20.0f, 20.0f);
            }
            else {
                Minecraft.áŒŠà().á.HorizonCode_Horizon_È("tile.piston.out", 20.0f, 20.0f);
            }
        }
        if (k == 0 && this.à) {
            int y = 0;
            for (final String x : this.HorizonCode_Horizon_È) {
                y += UIFonts.Ý.HorizonCode_Horizon_È + 2;
                if (i >= this.Â() && i <= this.Â() + this.Ø­áŒŠá() && j >= this.Ý() + y && j <= this.Ý() + 14 + y) {
                    Minecraft.áŒŠà().á.HorizonCode_Horizon_È("tile.piston.in", 20.0f, 20.0f);
                    Horizon.Âµá€ = x;
                }
            }
        }
    }
    
    @Override
    public int Âµá€() {
        int height = 15;
        for (final String x : this.HorizonCode_Horizon_È) {
            height += UIFonts.Ý.HorizonCode_Horizon_È + 2;
        }
        return this.à ? height : 14;
    }
    
    public boolean Â(final int i, final int j) {
        return i >= this.Â() && i <= this.Â() + this.Ø­áŒŠá() && j >= this.Ý() && j <= this.Ý() + 14;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.à;
    }
    
    public String Ó() {
        return Horizon.Âµá€;
    }
}
