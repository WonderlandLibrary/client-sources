// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class TpsSync extends Module
{
    private static TpsSync INSTANCE;
    public Setting<Boolean> attack;
    public Setting<Boolean> mining;
    
    public TpsSync() {
        super("TpsSync", "Syncs your client with the TPS.", Category.PLAYER, true, true, false);
        this.attack = (Setting<Boolean>)this.register(new Setting("Attack", (T)Boolean.FALSE));
        this.mining = (Setting<Boolean>)this.register(new Setting("Mine", (T)Boolean.TRUE));
        this.setInstance();
    }
    
    public static TpsSync getInstance() {
        if (TpsSync.INSTANCE == null) {
            TpsSync.INSTANCE = new TpsSync();
        }
        return TpsSync.INSTANCE;
    }
    
    private void setInstance() {
        TpsSync.INSTANCE = this;
    }
    
    static {
        TpsSync.INSTANCE = new TpsSync();
    }
}
