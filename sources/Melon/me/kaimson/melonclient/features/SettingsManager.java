package me.kaimson.melonclient.features;

import me.kaimson.melonclient.utils.*;
import java.awt.*;

public class SettingsManager extends Module
{
    public static final SettingsManager INSTANCE;
    public final Setting showName;
    public final Setting fixNametagRot;
    public final Setting borderlessWindow;
    public final Setting generalPerformance;
    public final Setting chunkUpdates;
    public final Setting transparentNametags;
    public final Setting improvedFontRenderer;
    public final Setting mainColor;
    public final Setting buttonFont;
    
    public SettingsManager() {
        super("General Settings", -1, false);
        new Setting(this, "General Options");
        this.showName = new Setting(this, "Show name in F5").setDefault(false);
        this.fixNametagRot = new Setting(this, "Fix nametag rotation").setDefault(true);
        this.borderlessWindow = new Setting(this, "Borderless Window").setDefault(false);
        new Setting(this, "Performance Options");
        this.generalPerformance = new Setting(this, "Enable...").setDefault(true);
        this.chunkUpdates = new Setting(this, "Lazy Chunk Loading").setDefault(5).setRange("Off (Vanilla)", "Lowest", "Low", "Medium", "High", "Highest");
        this.transparentNametags = new Setting(this, "Transparent Nametags").setDefault(false);
        this.improvedFontRenderer = new Setting(this, "Improved Font Renderer").setDefault(false);
        new Setting(this, "Style");
        this.mainColor = new Setting(this, "Main UI Color").setDefault(new Color(23, 191, 99).getRGB(), 0);
        this.buttonFont = new Setting(this, "Custom Button Font").setDefault(true);
    }
    
    static {
        INSTANCE = new SettingsManager();
    }
}
