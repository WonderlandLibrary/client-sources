package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import net.futureclient.client.ZA;
import net.futureclient.client.pg;
import net.futureclient.client.modules.miscellaneous.antivanish.Listener2;
import net.futureclient.client.modules.miscellaneous.antivanish.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import net.futureclient.client.Zg;
import net.futureclient.client.Ea;

public class AntiVanish extends Ea
{
    private final Zg D;
    private CopyOnWriteArrayList<UUID> k;
    
    public AntiVanish() {
        super("AntiVanish", new String[] { "AntiVanish", "avanish", "vanish" }, true, -16588821, Category.MISCELLANEOUS);
        final int n = 2;
        this.k = new CopyOnWriteArrayList<UUID>();
        this.D = new Zg();
        final n[] array = new n[n];
        array[0] = new Listener1(this);
        array[1] = new Listener2(this);
        this.M(array);
        pg.M().M().e((Object)new ZA(this, new String[] { "WhosVanished", "AntiVanishList", "VanishList" }));
    }
    
    public static Minecraft getMinecraft() {
        return AntiVanish.D;
    }
    
    public static CopyOnWriteArrayList M(final AntiVanish antiVanish) {
        return antiVanish.k;
    }
    
    public static Minecraft getMinecraft1() {
        return AntiVanish.D;
    }
    
    public static boolean M(final AntiVanish antiVanish, final UUID uuid) {
        return antiVanish.M(uuid);
    }
    
    private boolean M(final UUID uuid) {
        if (this.k.contains(uuid)) {
            final boolean b = true;
            this.k.remove(uuid);
            return b;
        }
        this.k.add(uuid);
        return false;
    }
    
    public static Zg M(final AntiVanish antiVanish) {
        return antiVanish.D;
    }
}
