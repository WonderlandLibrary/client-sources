// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class Wireframe extends Module
{
    private static Wireframe INSTANCE;
    public final Setting<Float> alpha;
    public final Setting<Float> cAlpha;
    public final Setting<Float> lineWidth;
    public final Setting<Float> crystalLineWidth;
    public Setting<RenderMode> mode;
    public Setting<RenderMode> cMode;
    public Setting<Boolean> players;
    public Setting<Boolean> playerModel;
    public Setting<Boolean> crystals;
    public Setting<Boolean> crystalModel;
    
    public Wireframe() {
        super("Wireframe", "Draws a wireframe esp around other players.", Category.RENDER, false, false, false);
        this.alpha = (Setting<Float>)this.register(new Setting("PAlpha", (T)255.0f, (T)0.1f, (T)255.0f));
        this.cAlpha = (Setting<Float>)this.register(new Setting("CAlpha", (T)255.0f, (T)0.1f, (T)255.0f));
        this.lineWidth = (Setting<Float>)this.register(new Setting("PLineWidth", (T)1.0f, (T)0.1f, (T)3.0f));
        this.crystalLineWidth = (Setting<Float>)this.register(new Setting("CLineWidth", (T)1.0f, (T)0.1f, (T)3.0f));
        this.mode = (Setting<RenderMode>)this.register(new Setting("PMode", (T)RenderMode.SOLID));
        this.cMode = (Setting<RenderMode>)this.register(new Setting("CMode", (T)RenderMode.SOLID));
        this.players = (Setting<Boolean>)this.register(new Setting("Players", (T)Boolean.FALSE));
        this.playerModel = (Setting<Boolean>)this.register(new Setting("PlayerModel", (T)Boolean.FALSE));
        this.crystals = (Setting<Boolean>)this.register(new Setting("Crystals", (T)Boolean.FALSE));
        this.crystalModel = (Setting<Boolean>)this.register(new Setting("CrystalModel", (T)Boolean.FALSE));
        this.setInstance();
    }
    
    public static Wireframe getINSTANCE() {
        if (Wireframe.INSTANCE == null) {
            Wireframe.INSTANCE = new Wireframe();
        }
        return Wireframe.INSTANCE;
    }
    
    private void setInstance() {
        Wireframe.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onRenderPlayerEvent(final RenderPlayerEvent.Pre event) {
        event.getEntityPlayer().hurtTime = 0;
    }
    
    static {
        Wireframe.INSTANCE = new Wireframe();
    }
    
    public enum RenderMode
    {
        SOLID, 
        WIREFRAME;
    }
}
