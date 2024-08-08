// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.misc;

import me.perry.mcdonalds.util.Util;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class McDonaldsDupe extends Module
{
    public Setting<String> discord;
    
    public McDonaldsDupe() {
        super("Advertise", "Advertises McDonalds Client.", Category.MISC, true, false, false);
        this.discord = (Setting<String>)this.register(new Setting("Discord", (T)"https://discord.gg/snDa88Vjfz"));
    }
    
    @Override
    public void onEnable() {
        if (Util.mc.player != null) {
            Util.mc.player.sendChatMessage("Join McDonalds Client Today at: " + this.discord.getValueAsString() + "!");
        }
        this.disable();
    }
}
