package net.futureclient.client.modules.combat;

import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.futureclient.client.ZG;
import net.minecraft.entity.Entity;
import net.futureclient.client.pg;
import net.futureclient.client.te;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.combat.autolog.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class AutoLog extends Ea
{
    private NumberValue health;
    private Value<Boolean> onRender;
    private ArrayList<EntityPlayer> k;
    
    public AutoLog() {
        super("AutoLog", new String[] { "AutoLog" }, true, -5197648, Category.COMBAT);
        this.onRender = new Value<Boolean>(false, new String[] { "OnRender", "LogOnRender", "or", "o" });
        this.health = new NumberValue(5.0f, 1.0f, 19.0f, 1, new String[] { "Health", "h" });
        final int n = 2;
        this.k = new ArrayList<EntityPlayer>();
        final Value[] array = new Value[n];
        array[0] = this.onRender;
        array[1] = this.health;
        this.M(array);
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return AutoLog.D;
    }
    
    public void B() {
        this.k.clear();
        if (AutoLog.D.player != null) {
            final te te = (te)pg.M().M().M((Class)te.class);
            final Iterator<Entity> iterator = AutoLog.D.world.playerEntities.iterator();
            while (iterator.hasNext()) {
                final EntityPlayer entityPlayer;
                if (ZG.b((Entity)(entityPlayer = (EntityPlayer)iterator.next())) && !this.k.contains(entityPlayer) && (!te.q.M() || !pg.M().M().M(entityPlayer.getName())) && !entityPlayer.getName().equals(AutoLog.D.player.getName()) && !(entityPlayer instanceof EntityPlayerSP)) {
                    this.k.add(entityPlayer);
                }
            }
        }
        super.B();
    }
    
    public static Minecraft getMinecraft1() {
        return AutoLog.D;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoLog.D;
    }
    
    public static Minecraft getMinecraft3() {
        return AutoLog.D;
    }
    
    public void b() {
        this.k.clear();
        super.b();
    }
    
    public static Minecraft getMinecraft4() {
        return AutoLog.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AutoLog.D;
    }
    
    public static Minecraft getMinecraft6() {
        return AutoLog.D;
    }
    
    public static Minecraft getMinecraft7() {
        return AutoLog.D;
    }
    
    public static Minecraft getMinecraft8() {
        return AutoLog.D;
    }
    
    public static Minecraft getMinecraft9() {
        return AutoLog.D;
    }
    
    public static ArrayList M(final AutoLog autoLog) {
        return autoLog.k;
    }
    
    public static NumberValue M(final AutoLog autoLog) {
        return autoLog.health;
    }
    
    public static Value M(final AutoLog autoLog) {
        return autoLog.onRender;
    }
    
    public static Minecraft getMinecraft10() {
        return AutoLog.D;
    }
}
