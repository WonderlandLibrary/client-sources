package baritone;

import baritone.api.event.events.*;
import java.util.function.*;
import java.util.*;

public final class c extends b
{
    public c(final a a) {
        super(a);
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        if (!baritone.a.a().allowInventory.value) {
            return;
        }
        if (tickEvent.getType() == TickEvent$Type.OUT) {
            return;
        }
        if (this.a.player().by != this.a.player().bx) {
            return;
        }
        if (this.a() >= 9) {
            this.a(this.a(), 8);
        }
        final aow b = aox.b;
        final Class<ajb> clazz = ajb.class;
        final aow aow = b;
        final fi a = this.a.player().bv.a;
        int n = -1;
        double n2 = -1.0;
        for (int i = 0; i < a.size(); ++i) {
            final aip aip;
            final double a2;
            if (!(aip = (aip)a.get(i)).b() && clazz.isInstance(aip.c()) && (a2 = da.a(aip, aow.t())) > n2) {
                n2 = a2;
                n = i;
            }
        }
        final int n3;
        if ((n3 = n) >= 9) {
            this.a(n3, 0);
        }
    }
    
    public final void a(final int n, final Predicate<Integer> predicate) {
        final ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 8; ++i) {
            if (((aip)this.a.player().bv.a.get(i)).b() && !predicate.test(i)) {
                list.add(i);
            }
        }
        if (list.isEmpty()) {
            for (int j = 1; j < 8; ++j) {
                if (!predicate.test(j)) {
                    list.add(j);
                }
            }
        }
        final OptionalInt optionalInt;
        if ((optionalInt = (list.isEmpty() ? OptionalInt.empty() : OptionalInt.of(list.get(new Random().nextInt(list.size()))))).isPresent()) {
            this.a(n, optionalInt.getAsInt());
        }
    }
    
    private void a(final int n, final int n2) {
        this.a.playerController().windowClick(this.a.player().bx.d, (n < 9) ? (n + 36) : n, n2, afw.c, (aed)this.a.player());
    }
    
    private int a() {
        final fi a = this.a.player().bv.a;
        for (int i = 0; i < a.size(); ++i) {
            if (baritone.a.a().acceptableThrowawayItems.value.contains(((aip)a.get(i)).c())) {
                return i;
            }
        }
        return -1;
    }
    
    public final boolean a() {
        final Iterator<ain> iterator = baritone.a.a().acceptableThrowawayItems.value.iterator();
        while (iterator.hasNext()) {
            if (this.a(false, (ain)iterator.next()::equals)) {
                return true;
            }
        }
        return false;
    }
    
    public final boolean a(final int n, int n2, int n3) {
        final bs a = this.a.a;
        final int n4 = n2;
        final int n5 = n3;
        n3 = n4;
        n2 = n;
        final bs bs = a;
        awt desiredState;
        awt awt2;
        final awt awt = a.isActive() ? (bs.a.inSchematic(n2 - bs.a.p(), n3 - bs.a.q(), n5 - bs.a.r()) ? (((desiredState = bs.a.desiredState(n2 - bs.a.p(), n3 - bs.a.q(), n5 - bs.a.r())).u() == aox.a) ? (awt2 = null) : (awt2 = desiredState)) : (awt2 = null)) : (awt2 = null);
        final awt awt3 = awt2;
        if (awt != null && this.a(true, (Predicate<? super ain>)c::a)) {
            return true;
        }
        final Iterator<ain> iterator = baritone.a.a().acceptableThrowawayItems.value.iterator();
        while (iterator.hasNext()) {
            if (this.a(true, (ain)iterator.next()::equals)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean a(final boolean b, final Predicate<? super ain> predicate) {
        final bud player;
        final fi a = (player = this.a.player()).bv.a;
        for (int i = 0; i < 9; i = (byte)(i + 1)) {
            if (predicate.test(((aip)a.get(i)).c())) {
                if (b) {
                    player.bv.d = i;
                }
                return true;
            }
        }
        if (predicate.test(((aip)player.bv.c.get(0)).c())) {
            for (int j = 0; j < 9; j = (byte)(j + 1)) {
                final aip aip;
                if ((aip = (aip)a.get(j)).b() || aip.c() instanceof ajb) {
                    if (b) {
                        player.bv.d = j;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean a(final awt awt, final ain ain) {
        return ain instanceof ahb && ((ahb)ain).d().equals(awt.u());
    }
}
