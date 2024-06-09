package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;

public class Panel
{
    public String Â;
    private Category HorizonCode_Horizon_È;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private int Ø;
    private boolean áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private ArrayList<Item> á;
    
    public Panel(final String title, final Category category, final int x, final int y, final int width, final int height, final boolean open, final boolean visible) {
        this.á = new ArrayList<Item>();
        this.Â = title;
        this.HorizonCode_Horizon_È = category;
        this.Ý = x;
        this.Ø­áŒŠá = y;
        this.à = width;
        this.Ø = height;
        this.áŒŠÆ = open;
        this.áˆºÑ¢Õ = visible;
        this.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È() {
    }
    
    public void HorizonCode_Horizon_È(final int i, final int j, final float k) {
        if (!this.Â()) {
            return;
        }
        this.Â(i, j);
        final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
        final int totalItemHeight = this.áŒŠÆ ? (this.ÂµÈ() - 1) : 0;
        if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
            Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá(), this.Ó() - 4, this.Ø­áŒŠá() + this.à(), (int)(this.Ó() + 14.0f), -15294331);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("red")) {
            Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá(), this.Ó() - 4, this.Ø­áŒŠá() + this.à(), (int)(this.Ó() + 14.0f), -4179669);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
            Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá(), this.Ó() - 4, this.Ø­áŒŠá() + this.à(), (int)(this.Ó() + 14.0f), -14176672);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
            Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá(), this.Ó() - 4, this.Ø­áŒŠá() + this.à(), (int)(this.Ó() + 14.0f), -3394561);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
            Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá(), this.Ó() - 4, this.Ø­áŒŠá() + this.à(), (int)(this.Ó() + 14.0f), -21744);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
            Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá(), this.Ó() - 4, this.Ø­áŒŠá() + this.à(), (int)(this.Ó() + 14.0f), color);
        }
        if (this.áŒŠÆ) {
            Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá(), (int)(this.Ó() + 14.0f), this.Ø­áŒŠá() + this.à(), this.Ó() + this.Ø() + totalItemHeight, -1879048192);
        }
        else {
            Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá(), (int)(this.Ó() + 14.0f), this.Ø­áŒŠá() + this.à(), this.Ó() + this.Ø() + totalItemHeight - 3, -1879048192);
        }
        if (Horizon.à.equalsIgnoreCase("Arial")) {
            UIFonts.Ý.HorizonCode_Horizon_È(this.Â, this.Ø­áŒŠá() + 2, this.Ó() - 2, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("SegoeUI")) {
            UIFonts.µà.HorizonCode_Horizon_È(this.Â, this.Ø­áŒŠá() + 2, this.Ó() - 1, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Helvetica")) {
            UIFonts.Ø­à.HorizonCode_Horizon_È(this.Â, this.Ø­áŒŠá() + 2, this.Ó() + 1, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Comfortaa")) {
            UIFonts.µÕ.HorizonCode_Horizon_È(this.Â, this.Ø­áŒŠá() + 2, this.Ó() + 0, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Vibes")) {
            UIFonts.Æ.HorizonCode_Horizon_È(this.Â, this.Ø­áŒŠá() + 2, this.Ó() + 3, -1);
        }
        if (this.áŒŠÆ) {
            GuiUtils.HorizonCode_Horizon_È().Ý(this.Ø­áŒŠá() + this.à() - 12, this.Ó() + 1, -1);
        }
        else {
            GuiUtils.HorizonCode_Horizon_È().Â(this.Ø­áŒŠá() + this.à() - 12, this.Ó() + 4, -1);
        }
        if (this.áŒŠÆ) {
            int y = this.Ó() + this.Ø() - 2;
            for (final Item item : this.áˆºÑ¢Õ()) {
                if (this.HorizonCode_Horizon_È(totalItemHeight, j)) {
                    RenderHelper_1118140819.HorizonCode_Horizon_È(this.Ý, y, totalItemHeight, j, Integer.MIN_VALUE);
                }
                item.HorizonCode_Horizon_È(this.Ý + 2, y);
                item.HorizonCode_Horizon_È(this.à() - 4);
                item.HorizonCode_Horizon_È(i, j, k);
                y += item.Âµá€() + 1;
            }
        }
    }
    
    public boolean Â() {
        return this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final boolean visible) {
        this.áˆºÑ¢Õ = visible;
    }
    
    private void Â(final int i, final int j) {
        if (this.ÂµÈ) {
            this.Ý = this.Âµá€ + i;
            this.Ø­áŒŠá = this.Ó + j;
        }
    }
    
    public void HorizonCode_Horizon_È(final int i, final int j, final int k) {
        if (!this.Â()) {
            return;
        }
        if (k == 0 && i >= this.Ø­áŒŠá() + 90 && i <= this.Ø­áŒŠá() + this.à() - 1 && j >= this.Ó() && j <= this.Ó() + this.Ø() - 3) {
            this.áŒŠÆ = !this.áŒŠÆ;
            if (this.áŒŠÆ) {
                Minecraft.áŒŠà().á.HorizonCode_Horizon_È("tile.piston.in", 20.0f, 20.0f);
            }
            else {
                Minecraft.áŒŠà().á.HorizonCode_Horizon_È("tile.piston.out", 20.0f, 20.0f);
            }
            return;
        }
        if (k == 0 && this.HorizonCode_Horizon_È(i, j)) {
            this.Âµá€ = this.Ý - i;
            this.Ó = this.Ø­áŒŠá - j;
            this.ÂµÈ = true;
        }
        else if (this.áŒŠÆ) {
            for (final Item item : this.áˆºÑ¢Õ()) {
                item.HorizonCode_Horizon_È(i, j, k);
            }
        }
    }
    
    public void Â(final int i, final int j, final int k) {
        if (k == 0) {
            this.ÂµÈ = false;
        }
        if (this.áŒŠÆ) {
            for (final Item item : this.áˆºÑ¢Õ()) {
                item.Â(i, j, k);
            }
        }
    }
    
    public Category Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Ø­áŒŠá() {
        return this.Ý;
    }
    
    public String Âµá€() {
        return this.Â;
    }
    
    public int Ó() {
        return this.Ø­áŒŠá;
    }
    
    public void Â(final boolean open) {
        this.áŒŠÆ = open;
    }
    
    public int à() {
        return this.à;
    }
    
    public int Ø() {
        return this.Ø;
    }
    
    public boolean áŒŠÆ() {
        return this.áŒŠÆ;
    }
    
    public ArrayList<Item> áˆºÑ¢Õ() {
        return this.á;
    }
    
    public boolean HorizonCode_Horizon_È(final int i, final int j) {
        return i >= this.Ø­áŒŠá() && i <= this.Ø­áŒŠá() + this.à() && j >= this.Ó() && j <= this.Ó() + this.Ø() - (this.áŒŠÆ ? 1 : 0);
    }
    
    public int ÂµÈ() {
        int height = 0;
        for (final Item item : this.áˆºÑ¢Õ()) {
            height += item.Âµá€() + 1;
        }
        return height;
    }
    
    public void HorizonCode_Horizon_È(final int dragX) {
        this.Ý = dragX;
    }
    
    public void Â(final int dragY) {
        this.Ø­áŒŠá = dragY;
    }
}
