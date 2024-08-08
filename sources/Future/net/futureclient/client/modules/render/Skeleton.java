package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.skeleton.Listener2;
import net.futureclient.client.modules.render.skeleton.Listener1;
import net.futureclient.client.n;
import java.util.HashMap;
import net.futureclient.client.Category;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import net.futureclient.client.Ea;

public class Skeleton extends Ea
{
    private final Map<EntityPlayer, float[][]> k;
    
    public Skeleton() {
        super("Skeleton", new String[] { "Skeleton", "SkeletonESP", "Skelly", "Skele", "Skeleesp", "Skeleboy" }, true, -2368549, Category.RENDER);
        final int n = 2;
        this.k = new HashMap<EntityPlayer, float[][]>();
        final n[] array = new n[n];
        array[0] = new Listener1(this);
        array[1] = (n)new Listener2(this);
        this.M(array);
    }
    
    public static Minecraft getMinecraft() {
        return Skeleton.D;
    }
    
    public static Map M(final Skeleton skeleton) {
        return skeleton.k;
    }
    
    public static Minecraft getMinecraft1() {
        return Skeleton.D;
    }
}
