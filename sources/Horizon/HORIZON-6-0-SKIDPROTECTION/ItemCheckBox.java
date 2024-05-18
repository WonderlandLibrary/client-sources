package HORIZON-6-0-SKIDPROTECTION;

public class ItemCheckBox extends Item
{
    private String HorizonCode_Horizon_È;
    private boolean à;
    
    public ItemCheckBox(final String name) {
        this.HorizonCode_Horizon_È = name;
    }
    
    private Long Ø() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float k) {
        if (Horizon.à.equalsIgnoreCase("Arial")) {
            UIFonts.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â() + 13, this.Ý() + 1, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("SegoeUI")) {
            UIFonts.µà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â(), this.Ý() - 1, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("Helvetica")) {
            UIFonts.Ø­à.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â() + 13, this.Ý() + 3, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("Comfortaa")) {
            UIFonts.µÕ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â() + 13, this.Ý() + 2, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("Vibes")) {
            UIFonts.Æ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â(), this.Ý() + 3, -1249039);
        }
        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 2, this.Â() + 11, this.Ý() + 13, 1.0f, 0, -754974720);
        final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
        if (this.à) {
            if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                UIFonts.Â.HorizonCode_Horizon_È("×", this.Â(), this.Ý() - 3, 1357335612);
                UIFonts.Â.HorizonCode_Horizon_È("×", this.Â() + 1, this.Ý() - 2, -4179669);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                UIFonts.Â.HorizonCode_Horizon_È("×", this.Â() + 1, this.Ý() - 2, -14176672);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                UIFonts.Â.HorizonCode_Horizon_È("×", this.Â() + 1, this.Ý() - 2, -15294331);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
                UIFonts.Â.HorizonCode_Horizon_È("×", this.Â() + 1, this.Ý() - 2, -3394561);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
                UIFonts.Â.HorizonCode_Horizon_È("×", this.Â() + 1, this.Ý() - 2, -21744);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                UIFonts.Â.HorizonCode_Horizon_È("×", this.Â() + 1, this.Ý() - 2, color);
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
            this.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    public int Âµá€() {
        return 14;
    }
    
    public String Ó() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean à() {
        return this.à;
    }
    
    public ItemCheckBox HorizonCode_Horizon_È(final boolean state) {
        this.à = state;
        return this;
    }
    
    public boolean Â(final int i, final int j) {
        return i >= this.Â() && i <= this.Â() + this.Ø­áŒŠá() && j >= this.Ý() && j <= this.Ý() + this.Âµá€();
    }
    
    public void HorizonCode_Horizon_È() {
    }
}
