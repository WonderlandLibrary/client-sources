// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils;

import net.minecraft.network.Packet;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.src.BlockUtils;
import org.apache.http.util.EntityUtils;

public class Helper
{
    private static EntityUtils entityUtils;
    private static BlockUtils blockUtils;
    private static SallosRender.R2DUtils r2DUtils;
    private static SallosRender.R3DUtils r3DUtils;
    private static SallosRender.ColorUtils colorUtils;
    private static MathUtils mathUtils;
    
    static {
        Helper.r2DUtils = new SallosRender.R2DUtils();
        Helper.r3DUtils = new SallosRender.R3DUtils();
        Helper.colorUtils = new SallosRender.ColorUtils();
    }
    
    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }
    
    public static EntityPlayerSP player() {
        return mc().thePlayer;
    }
    
    public static WorldClient world() {
        return mc().theWorld;
    }
    
    public static EntityUtils entityUtils() {
        return Helper.entityUtils;
    }
    
    public static BlockUtils blockUtils() {
        return Helper.blockUtils;
    }
    
    public static SallosRender.R2DUtils get2DUtils() {
        return Helper.r2DUtils;
    }
    
    public static SallosRender.R3DUtils get3DUtils() {
        return Helper.r3DUtils;
    }
    
    public static SallosRender.ColorUtils colorUtils() {
        return Helper.colorUtils;
    }
    
    public static MathUtils mathUtils() {
        return Helper.mathUtils;
    }
    
    public static void sendPacket(final Packet p) {
        mc().getNetHandler().addToSendQueue(p);
    }
}
