package baritone;

import baritone.api.event.events.type.*;
import baritone.api.event.events.*;
import baritone.api.cache.*;
import java.util.function.*;
import java.util.*;
import baritone.api.utils.*;

public final class f extends b
{
    private final List<i> a;
    private Integer a;
    
    public f(final a a) {
        super(a);
        this.a = new ArrayList<i>();
    }
    
    @Override
    public final synchronized void onTick(final TickEvent tickEvent) {
        if (!baritone.a.a().containerMemory.value) {
            return;
        }
        if (tickEvent.getType() == TickEvent$Type.OUT) {
            ((List)(this.a = null)).clear();
        }
    }
    
    @Override
    public final synchronized void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
        if (playerUpdateEvent.getState() == EventState.PRE && baritone.a.a().containerMemory.value) {
            final int d = this.a.player().by.d;
            if (this.a != null) {
                if (d == this.a) {
                    h.a(this.a(), this.a.player().by.a().subList(0, 27));
                }
                else {
                    this.a().a();
                    this.a = null;
                }
            }
            if (this.a() != null) {
                this.a().a(d).ifPresent(this::a);
            }
        }
    }
    
    @Override
    public final synchronized void onSendPacket(final PacketEvent packetEvent) {
        if (!baritone.a.a().containerMemory.value) {
            return;
        }
        final ht<?> packet = packetEvent.getPacket();
        if (packetEvent.getState() == EventState.PRE) {
            final avj r;
            if (packet instanceof ma && (r = this.a.world().r(packetEvent.cast().a())) instanceof avx) {
                final avx avx;
                int w_ = (avx = (avx)r).w_();
                final et w;
                et et = w = r.w();
                et et2 = null;
                Label_0179: {
                    final cn a;
                    final aow u;
                    if ((u = (a = this.a.a).a(w).u()) == aox.cg || u == aox.ae) {
                        for (int i = 0; i < 4; ++i) {
                            final et a2 = w.a(fa.b(i));
                            if (a.a(a2).u() == u) {
                                et2 = a2;
                                break Label_0179;
                            }
                        }
                    }
                    et2 = null;
                }
                final et et3 = et2;
                System.out.println(et + " " + et3);
                if (et3 != null) {
                    w_ <<= 1;
                    if (et3.p() < et.p() || et3.r() < et.r()) {
                        et = et3;
                    }
                }
                this.a.add(new i(System.nanoTime() / 1000000L, w_, avx.l(), et, (byte)0));
            }
            if (packet instanceof lg) {
                this.a().a();
            }
        }
    }
    
    @Override
    public final synchronized void onReceivePacket(final PacketEvent packetEvent) {
        if (!baritone.a.a().containerMemory.value) {
            return;
        }
        final ht<?> packet = packetEvent.getPacket();
        if (packetEvent.getState() == EventState.PRE) {
            if (packet instanceof ir) {
                final ir ir = packetEvent.cast();
                this.a.removeIf(f::a);
                System.out.println("Received packet " + ir.b() + " " + ir.e() + " " + ir.d() + " " + ir.a());
                System.out.println(ir.c());
                if (ir.c() instanceof hp && ((hp)ir.c()).i().equals("container.enderchest")) {
                    this.a = ir.a();
                    return;
                }
                this.a.stream().filter((Predicate<? super Object>)f::a).findFirst().ifPresent((Consumer<? super Object>)this::a);
            }
            if (packet instanceof iq) {
                this.a().a();
            }
        }
    }
    
    @Override
    public final void onBlockInteract(final BlockInteractEvent blockInteractEvent) {
        if (blockInteractEvent.getType() == BlockInteractEvent$Type.USE && cn.a(this.a, blockInteractEvent.getPos()) instanceof aou) {
            this.a.a.a.getWaypoints().addWaypoint(new t("bed", IWaypoint$Tag.BED, blockInteractEvent.getPos()));
        }
    }
    
    @Override
    public final void onPlayerDeath() {
        this.a.a.a.getWaypoints().addWaypoint(new t("death", IWaypoint$Tag.DEATH, this.a.playerFeet()));
    }
    
    private r a() {
        if (this.a.a.a == null) {
            return null;
        }
        return (r)this.a.a.a.getContainerMemory();
    }
    
    public final Optional<List<aip>> a() {
        return Optional.ofNullable(h.a(this.a())).map((Function<? super List, ? extends List<aip>>)Collections::unmodifiableList);
    }
    
    private h a() {
        return h.a(this.a.a.a.a.getParent(), this.a.player().bm());
    }
    
    private void a(s s) {
        final s s2 = s;
        final IPlayerContext a = this.a;
        s = s2;
        s2.a.clear();
        s.a.addAll(a.player().by.a().subList(0, s.a));
    }
    
    private void a(final ir ir, final i i) {
        this.a.remove(i);
        this.a().a(i.a, ir.a(), ir.d());
    }
    
    private static boolean a(final ir ir, final i i) {
        return i.a.equals(ir.b()) && i.a == ir.d();
    }
    
    private static boolean a(final i i) {
        return System.nanoTime() / 1000000L - i.a > 1000L;
    }
}
