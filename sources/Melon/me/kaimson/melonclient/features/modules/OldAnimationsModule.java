package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.gui.utils.*;

public class OldAnimationsModule extends Module
{
    public static OldAnimationsModule INSTANCE;
    public final Setting blockHit;
    public final Setting eating;
    public final Setting bow;
    public final Setting build;
    public final Setting rod;
    public final Setting swing;
    public final Setting block;
    public final Setting damage;
    public final Setting hitColor;
    
    public OldAnimationsModule() {
        super("Old Animations", 16);
        new Setting(this, "Animations");
        this.blockHit = new Setting(this, "Blockhit").setDefault(false);
        this.eating = new Setting(this, "Eating").setDefault(false);
        this.bow = new Setting(this, "Bow").setDefault(false);
        this.build = new Setting(this, "Build").setDefault(false);
        this.rod = new Setting(this, "Rod").setDefault(false);
        this.swing = new Setting(this, "Swing").setDefault(false);
        this.block = new Setting(this, "Block").setDefault(false);
        this.damage = new Setting(this, "Damage").setDefault(false);
        new Setting(this, "Color Options");
        this.hitColor = new Setting(this, "Hit Color").setDefault(GuiUtils.glToRGB(1.0f, 0.0f, 0.0f, 0.3f), 0);
        OldAnimationsModule.INSTANCE = this;
    }
}
