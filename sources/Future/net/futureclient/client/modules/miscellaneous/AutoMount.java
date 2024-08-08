package net.futureclient.client.modules.miscellaneous;

import net.futureclient.client.uB;
import net.minecraft.entity.item.EntityBoat;
import java.util.Objects;
import net.futureclient.client.pg;
import net.futureclient.client.te;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.futureclient.client.bj;
import net.futureclient.client.ZH;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.futureclient.client.ZG;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.miscellaneous.automount.Listener3;
import net.futureclient.client.modules.miscellaneous.automount.Listener2;
import net.futureclient.client.modules.miscellaneous.automount.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Kc;
import net.futureclient.client.R;
import net.minecraft.entity.Entity;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class AutoMount extends Ea
{
    private Value<Boolean> skeletonHorse;
    private NumberValue delay;
    private float L;
    private Value<Boolean> horse;
    private Value<Boolean> llama;
    private Entity j;
    private R<Kc.vc> require;
    private Timer M;
    private float d;
    private Value<Boolean> pig;
    private Value<Boolean> boat;
    private Value<Boolean> donkey;
    
    public AutoMount() {
        super("AutoMount", new String[] { "AutoMount", "RideableAura", "RidableAura", "BoatAura", "B0atAura" }, true, -39424, Category.MISCELLANEOUS);
        final Entity j = null;
        this.M = new Timer();
        this.j = j;
        this.require = new R<Kc.vc>(Kc.vc.D, new String[] { "Require", "Requirement" });
        this.delay = new NumberValue(0.5f, 0.0f, 10.0f, new String[] { "Delay", "D" });
        this.boat = new Value<Boolean>(true, new String[] { "Boat", "Boats", "Boaty", "b" });
        this.horse = new Value<Boolean>(true, new String[] { "Horse", "Horses", "Horseys", "Hors", "h" });
        this.skeletonHorse = new Value<Boolean>(true, new String[] { "SkeletonHorse", "SkeletonHorses", "Skellies", "s" });
        this.donkey = new Value<Boolean>(true, new String[] { "Donkey", "Donkeys", "Donker", "Donkers", "Donk", "Donkeh", "d" });
        this.llama = new Value<Boolean>(true, new String[] { "Llama", "Llamas", "Lamas", "Lama", "Laama", "Laamas", "l" });
        this.pig = new Value<Boolean>(true, new String[] { "Pig", "Pigs", "Piggo", "p" });
        this.M(new Value[] { this.require, this.boat, this.horse, this.skeletonHorse, this.donkey, this.pig, this.llama, this.delay });
        this.M(new n[] { (n)new Listener1(this), new Listener2(this), new Listener3(this) });
    }
    
    public static Value B(final AutoMount autoMount) {
        return autoMount.horse;
    }
    
    public static Value C(final AutoMount autoMount) {
        return autoMount.boat;
    }
    
    public void b() {
        final Entity j = null;
        super.b();
        this.j = j;
        this.M.e();
    }
    
    public static Value b(final AutoMount autoMount) {
        return autoMount.skeletonHorse;
    }
    
    public static Minecraft getMinecraft() {
        return AutoMount.D;
    }
    
    public static Minecraft getMinecraft1() {
        return AutoMount.D;
    }
    
    public static float e(final AutoMount autoMount, final float d) {
        return autoMount.d = d;
    }
    
    public static Value e(final AutoMount autoMount) {
        return autoMount.llama;
    }
    
    public static float e(final AutoMount autoMount) {
        return autoMount.L;
    }
    
    public static Value i(final AutoMount autoMount) {
        return autoMount.pig;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoMount.D;
    }
    
    public static boolean M(final AutoMount autoMount, final Entity entity) {
        return autoMount.M(entity);
    }
    
    public static Value M(final AutoMount autoMount) {
        return autoMount.donkey;
    }
    
    public static Entity M(final AutoMount autoMount, final Entity j) {
        return autoMount.j = j;
    }
    
    public static float M(final AutoMount autoMount, final float l) {
        return autoMount.L = l;
    }
    
    public static NumberValue M(final AutoMount autoMount) {
        return autoMount.delay;
    }
    
    public static Entity M(final AutoMount autoMount) {
        return autoMount.j;
    }
    
    public static float M(final AutoMount autoMount) {
        return autoMount.d;
    }
    
    private void M(Entity entity) {
        final boolean activeItemStackBlocking = AutoMount.D.player.isActiveItemStackBlocking();
        final boolean sneaking = AutoMount.D.player.isSneaking();
        final boolean sprinting = AutoMount.D.player.isSprinting();
        if (activeItemStackBlocking) {
            ZG.e();
        }
        if (sneaking) {
            AutoMount.D.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoMount.D.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (sprinting) {
            AutoMount.D.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoMount.D.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
        final Entity m;
        if ((m = new bj(entity, ZH.zi.d).M()) != this.j) {
            entity = m;
        }
        AutoMount.D.playerController.interactWithEntity((EntityPlayer)AutoMount.D.player, entity, EnumHand.MAIN_HAND);
        if (activeItemStackBlocking) {
            ZG.B();
        }
        if (sneaking) {
            AutoMount.D.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoMount.D.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        if (sprinting) {
            AutoMount.D.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoMount.D.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }
    
    public static Timer M(final AutoMount autoMount) {
        return autoMount.M;
    }
    
    public static void M(final AutoMount autoMount, final Entity entity) {
        autoMount.M(entity);
    }
    
    private boolean M(final Entity entity) {
        if (entity == null) {
            return false;
        }
        if (!entity.isEntityAlive()) {
            return false;
        }
        if (entity instanceof EntityAgeable && ((EntityAgeable)entity).isChild()) {
            return false;
        }
        final te te = (te)pg.M().M().M((Class)te.class);
        if (AutoMount.D.player.getDistance(entity) > te.J.B().floatValue()) {
            return false;
        }
        if (!AutoMount.D.player.canEntityBeSeen(entity) && !(boolean)te.m.M()) {
            return false;
        }
        if (!ZH.M(entity, te.H.B().floatValue(), ZH.zi.d)) {
            return false;
        }
        if (entity.getPassengers().contains(AutoMount.D.player)) {
            return false;
        }
        if (Objects.equals(entity.getControllingPassenger(), AutoMount.D.player)) {
            return false;
        }
        if (entity instanceof EntityBoat) {
            switch (uB.k[this.require.M().ordinal()]) {
                case 1:
                    return true;
                case 2:
                    return entity.getPassengers().size() == 1 && entity.getControllingPassenger() != null;
                case 3:
                    if (entity.getPassengers().size() == 1 && entity.getControllingPassenger() != null) {
                        return !(boolean)te.q.M() || pg.M().M().M(entity.getControllingPassenger().getName());
                    }
                    break;
            }
            return false;
        }
        return true;
    }
}
