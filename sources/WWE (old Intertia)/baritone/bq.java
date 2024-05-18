package baritone;

import baritone.api.pathing.path.*;
import baritone.api.pathing.calc.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.input.*;
import baritone.api.*;
import baritone.api.utils.*;
import java.util.*;
import java.util.function.*;

public final class bq implements IPathExecutor, cr
{
    public final IPath a;
    public int a;
    private int b;
    private int c;
    private Double a;
    private Integer a;
    public boolean a;
    private boolean c;
    public HashSet<et> a;
    public HashSet<et> b;
    public HashSet<et> c;
    private j a;
    public IPlayerContext a;
    public boolean b;
    
    public bq(final j a, final IPath a2) {
        super();
        this.c = true;
        this.a = new HashSet<et>();
        this.b = new HashSet<et>();
        this.c = new HashSet<et>();
        this.a = a;
        this.a = a.a;
        this.a = a2;
        this.a = 0;
    }
    
    public final boolean a() {
        if (this.a == this.a.length() - 1) {
            ++this.a;
        }
        if (this.a >= this.a.length()) {
            return true;
        }
        final BetterBlockPos betterBlockPos = this.a.positions().get(this.a);
        final BetterBlockPos playerFeet = this.a.playerFeet();
        if (!betterBlockPos.equals(playerFeet) && !aox.a.equals(cn.a(this.a, (et)playerFeet.down()))) {
            for (int n = 0; n < this.a - 1 && n < this.a.length(); ++n) {
                if (playerFeet.equals(this.a.positions().get(n))) {
                    this.a("Skipping back " + (this.a - n) + " steps, to " + n);
                    final int a = this.a;
                    this.a = Math.max(n - 1, 0);
                    for (int i = this.a; i <= a; ++i) {
                        this.a.movements().get(i).reset();
                    }
                    this.b();
                    this.a();
                    return false;
                }
            }
            for (int j = this.a + 3; j < this.a.length(); ++j) {
                if (playerFeet.equals(this.a.positions().get(j))) {
                    if (j - this.a > 2) {
                        this.a("Skipping forward " + (j - this.a) + " steps, to " + j);
                    }
                    this.a = j - 1;
                    this.b();
                    this.a();
                    return false;
                }
            }
        }
        final rr<Double, et> a2 = this.a(this.a);
        if (this.a(a2, 2.0)) {
            ++this.b;
            System.out.println("FAR AWAY FROM PATH FOR " + this.b + " TICKS. Current distance: " + a2.a() + ". Threshold: 2.0");
            if (this.b > 200.0) {
                this.a("Too far away from path for too long, cancelling path");
                this.c();
                return false;
            }
        }
        else {
            this.b = 0;
        }
        if (this.a(a2, 3.0)) {
            this.a("too far from path");
            this.c();
            return false;
        }
        final cn cn = new cn(this.a);
        for (int k = this.a - 10; k < this.a + 10; ++k) {
            if (k >= 0 && k < this.a.movements().size()) {
                final ae ae;
                final List<et> a3 = (ae = this.a.movements().get(k)).a(cn);
                final List<et> b = ae.b(cn);
                final List<et> c = ae.c(cn);
                ae.resetBlockCache();
                if (!a3.equals(ae.a(cn))) {
                    this.c = true;
                }
                if (!b.equals(ae.b(cn))) {
                    this.c = true;
                }
                if (!c.equals(ae.c(cn))) {
                    this.c = true;
                }
            }
        }
        if (this.c) {
            final HashSet<et> a4 = new HashSet<et>();
            final HashSet<et> b2 = new HashSet<et>();
            final HashSet<et> c2 = new HashSet<et>();
            for (int l = this.a; l < this.a.movements().size(); ++l) {
                final ae ae2 = this.a.movements().get(l);
                a4.addAll((Collection<?>)ae2.a(cn));
                b2.addAll((Collection<?>)ae2.b(cn));
                c2.addAll((Collection<?>)ae2.c(cn));
            }
            this.a = a4;
            this.b = b2;
            this.c = c2;
            this.c = false;
        }
        final IMovement movement;
        final boolean safeToCancel = (movement = this.a.movements().get(this.a)).safeToCancel();
        if (this.a == null || this.a != this.a) {
            this.a = this.a;
            this.a = movement.getCost();
            for (int n2 = 1; n2 < baritone.a.a().costVerificationLookahead.value && this.a + n2 < this.a.length() - 1; ++n2) {
                if (((ae)this.a.movements().get(this.a + n2)).b(this.a.a) >= 1000000.0 && safeToCancel) {
                    this.a("Something has changed in the world and a future movement has become impossible. Cancelling.");
                    this.c();
                    return true;
                }
            }
        }
        final ae ae3 = (ae)movement;
        final ad a5 = this.a.a;
        final ae ae4 = ae3;
        ae3.a = null;
        final double a6;
        if ((a6 = ae4.a(a5)) >= 1000000.0 && safeToCancel) {
            this.a("Something has changed in the world and this movement has become impossible. Cancelling.");
            this.c();
            return true;
        }
        if (!movement.calculatedWhileLoaded() && a6 - this.a > baritone.a.a().maxCostIncrease.value && safeToCancel) {
            this.a("Original cost " + this.a + " current cost " + a6 + ". Cancelling.");
            this.c();
            return true;
        }
        final Optional<aa> inProgress;
        final Optional<IPath> bestPathSoFar;
        final List<BetterBlockPos> positions;
        if ((inProgress = this.a.getInProgress()).isPresent() && this.a.player().z && af.b(this.a, this.a.playerFeet().down()) && af.a(this.a, this.a.playerFeet()) && af.a(this.a, this.a.playerFeet().up()) && this.a.movements().get(this.a).safeToCancel() && (bestPathSoFar = inProgress.get().bestPathSoFar()).isPresent() && (positions = bestPathSoFar.get().positions()).size() >= 3 && positions.subList(1, positions.size()).contains(this.a.playerFeet())) {
            this.a("Pausing since current best path is a backtrack");
            this.a();
            return true;
        }
        final MovementStatus update;
        if ((update = movement.update()) == MovementStatus.UNREACHABLE || update == MovementStatus.FAILED) {
            this.a("Movement returns status ".concat(String.valueOf(update)));
            this.c();
            return true;
        }
        if (update == MovementStatus.SUCCESS) {
            ++this.a;
            this.b();
            this.a();
            return true;
        }
        final boolean inputForcedDown = this.a.a.a.isInputForcedDown(Input.SPRINT);
        this.a.a.a.setInputForceState(Input.SPRINT, false);
        boolean b5 = false;
        Label_2599: {
            Label_2598: {
                if (new ad(this.a.a).d) {
                    final IMovement movement2;
                    final bg bg;
                    if ((movement2 = this.a.movements().get(this.a)) instanceof bn && this.a < this.a.length() - 3 && (bg = (bg)this.a.movements().get(this.a + 1)) instanceof bg && a(this.a, (bn)movement2, bg, this.a.movements().get(this.a + 2))) {
                        final IPlayerContext a7 = this.a;
                        final bn bn = (bn)movement2;
                        final IPlayerContext playerContext = a7;
                        boolean b4 = false;
                        Label_1841: {
                            if (Math.abs(bn.getDirection().p() * (bn.getSrc().c + 0.5 - playerContext.player().r)) + Math.abs(bn.getDirection().r() * (bn.getSrc().a + 0.5 - playerContext.player().p)) <= 0.1) {
                                final et b3 = bn.getSrc().b((fq)bn.getDirection()).b(2);
                                if (af.a(playerContext.world().o(b3))) {
                                    b4 = true;
                                    break Label_1841;
                                }
                                if (Math.abs(bn.getDirection().p() * (b3.p() + 0.5 - playerContext.player().p)) + Math.abs(bn.getDirection().r() * (b3.r() + 0.5 - playerContext.player().r)) > 0.8) {
                                    b4 = true;
                                    break Label_1841;
                                }
                            }
                            b4 = false;
                        }
                        if (b4) {
                            this.a("Skipping traverse to straight ascend");
                            ++this.a;
                            this.b();
                            this.a();
                            this.a.a.a.setInputForceState(Input.JUMP, true);
                            b5 = true;
                            break Label_2599;
                        }
                        this.a("Too far to the side to safely sprint ascend");
                    }
                    if (inputForcedDown) {
                        b5 = true;
                        break Label_2599;
                    }
                    if (movement2 instanceof bh) {
                        if (((bh)movement2).a() && !((bh)movement2).b()) {
                            this.a("Sprinting would be unsafe");
                            break Label_2598;
                        }
                        if (this.a < this.a.length() - 2) {
                            final IMovement movement3;
                            if ((movement3 = this.a.movements().get(this.a + 1)) instanceof bg && movement2.getDirection().a().equals((Object)movement3.getDirection().b())) {
                                ++this.a;
                                this.b();
                                this.a();
                                this.a("Skipping descend to straight ascend");
                                b5 = true;
                                break Label_2599;
                            }
                            final IPlayerContext a8 = this.a;
                            final bh bh = (bh)movement2;
                            final IMovement movement4 = movement3;
                            final bh bh2 = bh;
                            final IPlayerContext playerContext2 = a8;
                            if ((movement4 instanceof bh && movement4.getDirection().equals((Object)bh2.getDirection())) || (movement4 instanceof bn && movement4.getDirection().b().equals((Object)bh2.getDirection()) && af.b(playerContext2, movement4.getDest().down())) || (movement4 instanceof bi && baritone.a.a().allowOvershootDiagonalDescend.value)) {
                                if (this.a.playerFeet().equals(movement2.getDest())) {
                                    ++this.a;
                                    this.b();
                                    this.a();
                                }
                                b5 = true;
                                break Label_2599;
                            }
                        }
                    }
                    if (movement2 instanceof bg && this.a != 0) {
                        final bn bn2;
                        if ((bn2 = (bn)this.a.movements().get(this.a - 1)) instanceof bh && bn2.getDirection().a().equals((Object)movement2.getDirection().b()) && this.a.player().q >= movement2.getSrc().up().q()) {
                            this.a.a.a.setInputForceState(Input.JUMP, false);
                            b5 = true;
                            break Label_2599;
                        }
                        if (this.a < this.a.length() - 2 && bn2 instanceof bn && a(this.a, bn2, (bg)movement2, this.a.movements().get(this.a + 1))) {
                            b5 = true;
                            break Label_2599;
                        }
                    }
                    final rr<bhe, et> a9;
                    if (movement2 instanceof bk && (a9 = this.a((bk)movement2)) != null) {
                        final et et = (et)a9.b();
                        if (!this.a.positions().contains(et)) {
                            throw new IllegalStateException();
                        }
                        if (this.a.playerFeet().equals(et)) {
                            this.a = this.a.positions().indexOf(et);
                            this.b();
                            this.a();
                            b5 = true;
                            break Label_2599;
                        }
                        this.a();
                        this.a.a.a.updateTarget(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), (bhe)a9.a(), this.a.playerRotations()), false);
                        this.a.a.a.setInputForceState(Input.MOVE_FORWARD, true);
                        b5 = true;
                        break Label_2599;
                    }
                }
            }
            b5 = false;
        }
        if (!(this.b = b5)) {
            this.a.player().f(false);
        }
        ++this.c;
        if (this.c > this.a + baritone.a.a().movementTimeoutTicks.value) {
            this.a("This movement has taken too long (" + this.c + " ticks, expected " + this.a + "). Cancelling.");
            this.c();
            return true;
        }
        return safeToCancel;
    }
    
    private rr<Double, et> a(final IPath path) {
        double n = -1.0;
        Object o = null;
        for (final BetterBlockPos betterBlockPos : path.positions()) {
            final double entityDistanceToCenter;
            if ((entityDistanceToCenter = VecUtils.entityDistanceToCenter((vg)this.a.player(), betterBlockPos)) < n || n == -1.0) {
                n = entityDistanceToCenter;
                o = betterBlockPos;
            }
        }
        return (rr<Double, et>)new rr((Object)n, o);
    }
    
    private boolean a(final rr<Double, et> rr, final double n) {
        return (double)rr.a() > n && (!(this.a.movements().get(this.a) instanceof bk) || VecUtils.entityFlatDistanceToCenter((vg)this.a.player(), this.a.positions().get(this.a + 1)) >= n);
    }
    
    private rr<bhe, et> a(final bk bk) {
        final et direction;
        if (((fq)(direction = bk.getDirection())).q() < -3) {
            return null;
        }
        if (!bk.a.isEmpty()) {
            return null;
        }
        fq fq = null;
        int n = 0;
        IMovement movement;
    Label_0229:
        for (fq = new fq(((fq)direction).p(), 0, ((fq)direction).r()), n = this.a + 1; n < this.a.length() - 1 && n < this.a + 3 && (movement = this.a.movements().get(n)) instanceof bn && fq.equals((Object)movement.getDirection()); ++n) {
            for (int i = movement.getDest().b; i <= bk.getSrc().b + 1; ++i) {
                if (!af.a(this.a.world().o(new et(movement.getDest().a, i, movement.getDest().c)))) {
                    break Label_0229;
                }
            }
            if (!af.b(this.a, movement.getDest().down())) {
                break;
            }
        }
        if (--n == this.a) {
            return null;
        }
        final double n2 = n - this.a - 0.4;
        return (rr<bhe, et>)new rr((Object)new bhe(fq.p() * n2 + bk.getDest().a + 0.5, (double)bk.getDest().b, fq.r() * n2 + bk.getDest().c + 0.5), (Object)bk.getDest().a(fq.p() * (n - this.a), 0, fq.r() * (n - this.a)));
    }
    
    private static boolean a(final IPlayerContext playerContext, final bn bn, final bg bg, final IMovement movement) {
        if (!baritone.a.a().sprintAscends.value) {
            return false;
        }
        if (!bn.getDirection().equals((Object)bg.getDirection().b())) {
            return false;
        }
        if (movement.getDirection().p() != bg.getDirection().p() || movement.getDirection().r() != bg.getDirection().r()) {
            return false;
        }
        if (!af.b(playerContext, bn.getDest().down())) {
            return false;
        }
        if (!af.b(playerContext, bg.getDest().down())) {
            return false;
        }
        if (!bg.a.isEmpty()) {
            return false;
        }
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                et et = bn.getSrc().up(j);
                if (i == 1) {
                    et = et.a((fq)bn.getDirection());
                }
                if (!af.a(playerContext.world().o(et))) {
                    return false;
                }
            }
        }
        return !af.a(playerContext.world().o((et)bn.getSrc().up(3)).u()) && !af.a(playerContext.world().o((et)bg.getDest().up(2)).u());
    }
    
    private void b() {
        this.a();
        this.c = 0;
    }
    
    public final void a() {
        this.a.a.a.clearAllKeys();
    }
    
    private void c() {
        this.a();
        this.a.a.a.a.a();
        this.a = this.a.length() + 3;
        this.a = true;
    }
    
    @Override
    public final int getPosition() {
        return this.a;
    }
    
    public final bq a(final bq bq) {
        if (bq == null) {
            return this.a();
        }
        return br.a(this.a, bq.a, false).map((Function<? super br, ? extends bq>)this::a).orElse(this.a());
    }
    
    private bq a() {
        if (this.a <= baritone.a.a().maxPathHistoryLength.value) {
            return this;
        }
        final int intValue;
        final int n = intValue = baritone.a.a().pathHistoryCutoffAmount.value;
        final IPath a = this.a;
        final bp bp;
        if (!(bp = new bp(a, intValue, a.length() - 1)).getDest().equals(this.a.getDest())) {
            throw new IllegalStateException();
        }
        this.a("Discarding earliest segment movements, length cut from " + this.a.length() + " to " + bp.length());
        final bq bq;
        (bq = new bq(this.a, bp)).a = this.a - n;
        bq.a = this.a;
        if (this.a != null) {
            bq.a = this.a - n;
        }
        bq.c = this.c;
        return bq;
    }
    
    @Override
    public final IPath getPath() {
        return this.a;
    }
    
    private bq a(bq bq, final br br) {
        if (!br.getDest().equals(bq.getPath().getDest())) {
            throw new IllegalStateException();
        }
        (bq = new bq(this.a, br)).a = this.a;
        bq.a = this.a;
        bq.a = this.a;
        bq.c = this.c;
        return bq;
    }
}
