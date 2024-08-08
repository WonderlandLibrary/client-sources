package net.futureclient.client;

import net.minecraft.client.Minecraft;
import io.netty.util.internal.ConcurrentSet;
import java.util.Set;

public class hE extends Ea
{
    private Set<hE.sE> k;
    
    public hE() {
        super("QdvAjd\u007fe}", new String[] { "QdvAjd\u007fe}", "OduAyd`e" }, true, -7933377, Category.EXPLOITS);
        final int n = 2;
        this.k = (Set<hE.sE>)new ConcurrentSet();
        final n[] array = new n[n];
        array[0] = new VE(this);
        array[1] = (n)new jF(this);
        this.M(array);
    }
    
    public void B() {
        super.B();
    }
    
    public static Minecraft getMinecraft() {
        return hE.D;
    }
    
    public void b() {
        super.b();
        this.k.clear();
    }
    
    public static Minecraft getMinecraft1() {
        return hE.D;
    }
    
    public static Set M(final hE he) {
        return he.k;
    }
    
    public static Minecraft getMinecraft2() {
        return hE.D;
    }
}
