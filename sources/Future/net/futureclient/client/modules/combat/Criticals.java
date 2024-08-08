package net.futureclient.client.modules.combat;

import net.futureclient.client.db;
import net.futureclient.client.pg;
import net.futureclient.client.modules.movement.Speed;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.Re;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.combat.criticals.Listener2;
import net.futureclient.client.modules.combat.criticals.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.nE;
import net.futureclient.client.R;
import java.util.Random;
import net.futureclient.client.Ea;

public class Criticals extends Ea
{
    private final Random a;
    public R<nE.tE> mode;
    private final Timer k;
    
    public Criticals() {
        super("Criticals", new String[] { "Criticals", "critical", "crit", "crits" }, true, -3343176, Category.COMBAT);
        this.mode = new R<nE.tE>(nE.tE.D, new String[] { "Mode", "m" });
        final int n = 1;
        this.k = new Timer();
        this.a = new Random();
        final Value[] array = new Value[n];
        array[0] = this.mode;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Criticals.D;
    }
    
    public void C() {
        if (!this.b()) {
            return;
        }
        switch (Re.k[this.mode.M().ordinal()]) {
            case 4:
                if (this.k.e(500L)) {
                    final double n = 1.282622531E-314;
                    final double n2 = n + n * (1.0 + this.a.nextInt(this.a.nextBoolean() ? 34 : 43));
                    final double[] array;
                    final int length = (array = new double[] { 1.531232163E-314 + n2, 0.0, 1.135895857E-315 + n2, 0.0 }).length;
                    int i = 0;
                    int n3 = 0;
                    while (i < length) {
                        final double n4 = array[n3];
                        final NetHandlerPlayClient connection = Criticals.D.player.connection;
                        final double posX = Criticals.D.player.posX;
                        final double n5 = Criticals.D.player.posY + n4;
                        final double posZ = Criticals.D.player.posZ;
                        ++n3;
                        connection.sendPacket((Packet)new CPacketPlayer.Position(posX, n5, posZ, false));
                        i = n3;
                    }
                    this.k.e();
                    break;
                }
                break;
        }
    }
    
    public static Minecraft getMinecraft1() {
        return Criticals.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Criticals.D;
    }
    
    private boolean b() {
        final Speed speed;
        return ((speed = (Speed)pg.M().M().M((Class)db.class)) == null || !speed.M()) && Criticals.D.player.onGround && Criticals.D.player.collidedVertically && !Criticals.D.player.isInLava() && !Criticals.D.player.isInWater();
    }
    
    public static Minecraft getMinecraft3() {
        return Criticals.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Criticals.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Criticals.D;
    }
    
    public static boolean M(final Criticals criticals) {
        return criticals.b();
    }
    
    public static Minecraft getMinecraft6() {
        return Criticals.D;
    }
}
