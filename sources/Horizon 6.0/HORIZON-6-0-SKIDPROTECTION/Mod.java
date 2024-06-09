package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.awt.TrayIcon;
import java.util.ArrayList;

public class Mod
{
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private Category Ó;
    private boolean à;
    private boolean Ø;
    private boolean áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    public String HorizonCode_Horizon_È;
    public Minecraft Â;
    private String ÂµÈ;
    private final ArrayList<Property> á;
    
    public Mod() {
        this.Â = Minecraft.áŒŠà();
        this.á = new ArrayList<Property>();
        this.Ó = this.ÂµÈ().Ø­áŒŠá();
        this.Ý = 0;
        this.ÂµÈ = this.ÂµÈ().HorizonCode_Horizon_È();
        this.HorizonCode_Horizon_È = "";
        this.Ø­áŒŠá = -1;
    }
    
    public Category Â() {
        return this.Ó;
    }
    
    public String Ý() {
        return this.ÂµÈ;
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final int color) {
        this.Ø­áŒŠá = color;
    }
    
    public void HorizonCode_Horizon_È(final String tag) {
        this.ÂµÈ = tag;
    }
    
    public int Âµá€() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final boolean using) {
        this.áˆºÑ¢Õ = using;
    }
    
    public boolean Ó() {
        return this.áˆºÑ¢Õ;
    }
    
    public void Â(final boolean backslide) {
        this.Ø = backslide;
    }
    
    public boolean à() {
        return this.Ø;
    }
    
    public final void Â(final int i) {
        this.Ý = i;
    }
    
    public boolean Ø() {
        return this.à;
    }
    
    public void Ý(final boolean extending) {
        this.à = extending;
    }
    
    public int áŒŠÆ() {
        return this.Âµá€;
    }
    
    public void Ý(final int slide) {
        this.Âµá€ = slide;
    }
    
    public final void HorizonCode_Horizon_È(final Property property) {
        this.á.add(property);
    }
    
    public boolean áˆºÑ¢Õ() {
        return this.áŒŠÆ;
    }
    
    public void HorizonCode_Horizon_È(final float pt) {
    }
    
    public Mod HorizonCode_Horizon_È(final Category category) {
        this.Ó = category;
        return this;
    }
    
    public Mod Ø­áŒŠá(final int keybind) {
        this.Ý = keybind;
        return this;
    }
    
    public Mod Ø­áŒŠá(final boolean state) {
        this.áŒŠÆ = state;
        return this;
    }
    
    public ModInfo ÂµÈ() {
        return this.getClass().getAnnotation(ModInfo.class);
    }
    
    public void HorizonCode_Horizon_È() {
    }
    
    public void á() {
    }
    
    public GuiScreen HorizonCode_Horizon_È(final GuiScreen guiscreen) {
        return guiscreen;
    }
    
    public void ˆÏ­() {
        if (this.áŒŠÆ) {
            if (Horizon.ÂµÈ && !this.ÂµÈ().HorizonCode_Horizon_È().equalsIgnoreCase("gui")) {
                GhostTray.HorizonCode_Horizon_È.displayMessage("Module Deactivated", "Deactivated " + this.ÂµÈ().HorizonCode_Horizon_È(), TrayIcon.MessageType.INFO);
            }
            if (!Horizon.ÂµÈ && !this.ÂµÈ().HorizonCode_Horizon_È().equalsIgnoreCase("gui") && this.Â.á != null) {
                this.Â.á.HorizonCode_Horizon_È("random.wood_click", 20.0f, 0.8f);
            }
            EventManager_1550089733.Â(this);
            final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
            ModuleManager.Â.remove(this.Ý(), this);
            this.á();
            this.áŒŠÆ = false;
            this.Ý(this.áŒŠÆ() + 4);
            this.Â(true);
        }
        else {
            if (Horizon.ÂµÈ && !this.ÂµÈ().HorizonCode_Horizon_È().equalsIgnoreCase("gui")) {
                GhostTray.HorizonCode_Horizon_È.displayMessage("Module Activated", "Activated " + this.ÂµÈ().HorizonCode_Horizon_È(), TrayIcon.MessageType.INFO);
            }
            if (!Horizon.ÂµÈ && !this.ÂµÈ().HorizonCode_Horizon_È().equalsIgnoreCase("gui") && this.Â.á != null) {
                this.Â.á.HorizonCode_Horizon_È("random.wood_click", 20.0f, 1.0f);
            }
            EventManager_1550089733.HorizonCode_Horizon_È(this);
            final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
            ModuleManager.Â.put(this.Ý(), this);
            this.HorizonCode_Horizon_È();
            this.áŒŠÆ = true;
            this.Ý(0);
            this.Ý(true);
            if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                this.HorizonCode_Horizon_È(ColorUtil.HorizonCode_Horizon_È(1L, 1.0f).getRGB());
            }
        }
    }
    
    public final ArrayList<Property> £á() {
        return this.á;
    }
    
    public final Property Â(final String name) {
        for (final Property prop : this.£á()) {
            if (prop.HorizonCode_Horizon_È().equalsIgnoreCase(name)) {
                return prop;
            }
        }
        return null;
    }
}
