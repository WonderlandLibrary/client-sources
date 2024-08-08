package net.futureclient.client.modules.miscellaneous;

import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.futureclient.client.modules.miscellaneous.sneak.Listener3;
import net.futureclient.client.modules.miscellaneous.sneak.Listener2;
import net.futureclient.client.modules.miscellaneous.sneak.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.mh;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Sneak extends Ea
{
    private Value<Boolean> safeWalk;
    private boolean k;
    
    public static Minecraft getMinecraft() {
        return Sneak.D;
    }
    
    public Sneak() {
        super("Sneak", new String[] { mh.M("\r.;!5"), "Snea", mh.M("\u00130%5") }, true, -4801884, Category.MISCELLANEOUS);
        this.safeWalk = new Value<Boolean>(false, new String[] { "SafeWalk", mh.M("3)"), "safe" });
        this.M(new Value[] { this.safeWalk });
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Sneak.D;
    }
    
    public void b() {
        super.b();
        if (!Sneak.D.gameSettings.keyBindSneak.isKeyDown()) {
            Sneak.D.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.D.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    public static Minecraft getMinecraft8() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft16() {
        return Sneak.D;
    }
    
    public static boolean M(final Sneak sneak, final boolean k) {
        return sneak.k = k;
    }
    
    public static Value M(final Sneak sneak) {
        return sneak.safeWalk;
    }
    
    public static boolean M(final Sneak sneak) {
        return sneak.k;
    }
    
    public static Minecraft getMinecraft17() {
        return Sneak.D;
    }
    
    public static Minecraft getMinecraft18() {
        return Sneak.D;
    }
}
