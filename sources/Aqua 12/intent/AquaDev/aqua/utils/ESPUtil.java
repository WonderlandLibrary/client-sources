// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.client.Minecraft;

public class ESPUtil
{
    public static ESPUtil INSTANCE;
    Minecraft mc;
    
    public ESPUtil() {
        this.mc = Minecraft.getMinecraft();
    }
}
