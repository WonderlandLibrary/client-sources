// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.render;

import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class ViewModel extends Module
{
    public Setting<Float> sizeX;
    public Setting<Float> sizeY;
    public Setting<Float> sizeZ;
    public Setting<Float> rotationX;
    public Setting<Float> rotationY;
    public Setting<Float> rotationZ;
    public Setting<Float> positionX;
    public Setting<Float> positionY;
    public Setting<Float> positionZ;
    public Setting<Float> itemFOV;
    private static ViewModel INSTANCE;
    
    public ViewModel() {
        super("Viewmodel", "Changes to the viewmodel.", Category.RENDER, false, false, false);
        this.sizeX = (Setting<Float>)this.register(new Setting("Size-X", (T)1.0f, (T)0.0f, (T)2.0f));
        this.sizeY = (Setting<Float>)this.register(new Setting("Size-Y", (T)1.0f, (T)0.0f, (T)2.0f));
        this.sizeZ = (Setting<Float>)this.register(new Setting("Size-X", (T)1.0f, (T)0.0f, (T)2.0f));
        this.rotationX = (Setting<Float>)this.register(new Setting("Rotation-X", (T)0.0f, (T)0.0f, (T)1.0f));
        this.rotationY = (Setting<Float>)this.register(new Setting("Rotation-Y", (T)0.0f, (T)0.0f, (T)1.0f));
        this.rotationZ = (Setting<Float>)this.register(new Setting("Rotation-Z", (T)0.0f, (T)0.0f, (T)1.0f));
        this.positionX = (Setting<Float>)this.register(new Setting("Position-X", (T)0.0f, (T)(-2.0f), (T)2.0f));
        this.positionY = (Setting<Float>)this.register(new Setting("Position-Y", (T)0.0f, (T)(-2.0f), (T)2.0f));
        this.positionZ = (Setting<Float>)this.register(new Setting("Position-Z", (T)0.0f, (T)(-2.0f), (T)2.0f));
        this.setInstance();
    }
    
    private void setInstance() {
        ViewModel.INSTANCE = this;
    }
    
    public static ViewModel getINSTANCE() {
        if (ViewModel.INSTANCE == null) {
            ViewModel.INSTANCE = new ViewModel();
        }
        return ViewModel.INSTANCE;
    }
    
    static {
        ViewModel.INSTANCE = new ViewModel();
    }
}
