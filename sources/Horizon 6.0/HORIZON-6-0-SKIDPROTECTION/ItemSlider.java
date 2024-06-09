package HORIZON-6-0-SKIDPROTECTION;

public class ItemSlider extends Item
{
    private String HorizonCode_Horizon_È;
    private float à;
    private float Ø;
    private boolean áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    
    public ItemSlider(final String name, final float maxvalue) {
        this.HorizonCode_Horizon_È = name;
        this.Ø = maxvalue;
    }
    
    private Long Ø() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float k) {
        final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
        final int colord = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).brighter().getRGB();
        if (this.à * this.Ø >= 100.0f) {
            UIFonts.Ø­áŒŠá.HorizonCode_Horizon_È(String.valueOf(this.HorizonCode_Horizon_È) + ": " + String.valueOf(this.à * this.Ø).substring(0, 3), this.Â(), this.Ý(), -1);
        }
        else if (this.à * this.Ø >= 10.0f) {
            UIFonts.Ø­áŒŠá.HorizonCode_Horizon_È(String.valueOf(this.HorizonCode_Horizon_È) + ": " + String.valueOf(this.à * this.Ø).substring(0, 2), this.Â(), this.Ý(), -1);
        }
        else {
            UIFonts.Ø­áŒŠá.HorizonCode_Horizon_È(String.valueOf(this.HorizonCode_Horizon_È) + ": " + String.valueOf(this.à * this.Ø).substring(0, 3), this.Â(), this.Ý(), -1);
        }
        RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.Ø­áŒŠá(), this.Ý() + 14, -754974720);
        if (Horizon.Âµá€.equalsIgnoreCase("red")) {
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá(), this.Ý() + 14, -4179669);
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá() - 2.0f, this.Ý() + 14, -1618884);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá(), this.Ý() + 14, -15294331);
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá() - 2.0f, this.Ý() + 14, -15024996);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá(), this.Ý() + 14, -14176672);
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá() - 2.0f, this.Ý() + 14, -13710223);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá(), this.Ý() + 14, -7196238);
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá() - 2.0f, this.Ý() + 14, -3394561);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá(), this.Ý() + 14, -1671646);
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá() - 2.0f, this.Ý() + 14, -21744);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá(), this.Ý() + 14, color);
            RenderHelper_1118140819.HorizonCode_Horizon_È(this.Â(), this.Ý() + 9, this.Â() + this.à * this.Ø­áŒŠá() - 2.0f, this.Ý() + 14, colord);
        }
        if (this.áŒŠÆ) {
            this.à = (i - (this.Â() + 4)) / (this.Ø­áŒŠá() - 4);
            this.à = MathHelper.HorizonCode_Horizon_È(this.à, 0.0f, 1.0f);
            this.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int k) {
        if (k == 0 && this.Â(mouseX, mouseY)) {
            this.áŒŠÆ = true;
        }
    }
    
    public void HorizonCode_Horizon_È(final Minecraft mc, final int mouseX, final float mouseY) {
    }
    
    @Override
    public void Â(final int mouseX, final int mouseY, final int mouseButton) {
        this.áŒŠÆ = false;
    }
    
    @Override
    public int Âµá€() {
        return 15;
    }
    
    public ItemSlider HorizonCode_Horizon_È(final float value) {
        this.à = value;
        return this;
    }
    
    public ItemSlider HorizonCode_Horizon_È(final boolean enabled) {
        this.áˆºÑ¢Õ = enabled;
        return this;
    }
    
    public boolean Ó() {
        return this.áˆºÑ¢Õ;
    }
    
    public float à() {
        return this.à * this.Ø;
    }
    
    public boolean Â(final int i, final int j) {
        return i >= this.Â() && i <= this.Â() + this.Ø­áŒŠá() && j >= this.Ý() + 7 && j <= this.Ý() + this.Âµá€();
    }
    
    public void HorizonCode_Horizon_È() {
    }
}
