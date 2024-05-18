package baritone;

import java.io.*;
import baritone.api.utils.*;
import baritone.api.utils.input.*;
import baritone.api.process.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import baritone.api.pathing.goals.*;

public final class bs extends ck implements IBuilderProcess
{
    private HashSet<BetterBlockPos> a;
    private String a;
    public ISchematic a;
    public fq a;
    private int a;
    
    public bs(final a a) {
        super(a);
    }
    
    @Override
    public final void build(final String a, final ISchematic a2, final fq a3) {
        this.a = a;
        this.a = a2;
        this.a = a3;
    }
    
    @Override
    public final boolean build(final String s, File a, final fq fq) {
        try {
            final FileInputStream fileInputStream = new FileInputStream((File)a);
            Throwable t = null;
            try {
                a = (Throwable)gi.a((InputStream)fileInputStream);
                fileInputStream.close();
            }
            catch (Throwable t3) {
                final Throwable t2 = t3;
                t = t3;
                throw t2;
            }
            finally {
                if (t != null) {
                    try {
                        fileInputStream.close();
                    }
                    catch (Throwable a) {
                        t.addSuppressed(a);
                    }
                }
                else {
                    fileInputStream.close();
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        if (a == null) {
            return false;
        }
        this.build(s, new dp((fy)a), fq);
        return true;
    }
    
    public final void a(final et et, final et et2) {
        this.build("clear area", new do(Math.abs(et.p() - et2.p()) + 1, Math.abs(et.q() - et2.q()) + 1, Math.abs(et.r() - et2.r()) + 1), (fq)new et(Math.min(et.p(), et2.p()), Math.min(et.q(), et2.q()), Math.min(et.r(), et2.r())));
    }
    
    @Override
    public final boolean isActive() {
        return this.a != null;
    }
    
    private Optional<rr<BetterBlockPos, Rotation>> a(final bu bu) {
        final BetterBlockPos playerFeet = this.a.playerFeet();
        for (int i = -5; i <= 5; ++i) {
            for (int j = 0; j <= 5; ++j) {
                for (int k = -5; k <= 5; ++k) {
                    final int n = playerFeet.a + i;
                    final int n2 = playerFeet.b + j;
                    final int n3 = playerFeet.c + k;
                    final awt a;
                    final awt a2;
                    if ((a = bu.a(bu, n, n2, n3)) != null && (a2 = bu.a.a(n, n2, n3)).u() != aox.a && !a(a2, a)) {
                        final BetterBlockPos betterBlockPos = new BetterBlockPos(n, n2, n3);
                        final Optional<Rotation> reachable;
                        if ((reachable = RotationUtils.reachable(this.a.player(), betterBlockPos, this.a.playerController().getBlockReachDistance())).isPresent()) {
                            return Optional.of(new rr((Object)betterBlockPos, (Object)reachable.get()));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
    
    private Optional<bz> a(final bu bu, final List<awt> list) {
        final BetterBlockPos playerFeet = this.a.playerFeet();
        for (int i = -5; i <= 5; ++i) {
            for (int j = -5; j <= 1; ++j) {
            Label_0977:
                for (int k = -5; k <= 5; ++k) {
                    final int n = playerFeet.a + i;
                    final int n2 = playerFeet.b + j;
                    final int n3 = playerFeet.c + k;
                    final awt a;
                    if ((a = bu.a(bu, n, n2, n3)) != null) {
                        final awt a2 = bu.a.a(n, n2, n3);
                        if (af.a(n, n3, a2, bu.a) && !a(a2, a) && (j != 1 || bu.a.a(n, n2 + 1, n3).u() != aox.a)) {
                            list.add(a);
                            final awt awt = a;
                            final int n4 = n;
                            final int n5 = n2;
                            final int n6 = n3;
                            final cn a3 = bu.a;
                            final int n7 = n6;
                            final int n8 = n5;
                            final int n9 = n4;
                            final awt awt2 = awt;
                        Label_0965:
                            while (true) {
                                fa[] values;
                                for (int length = (values = fa.values()).length, l = 0; l < length; ++l) {
                                    final fa fa = values[l];
                                    final BetterBlockPos offset = new BetterBlockPos(n9, n8, n7).offset(fa);
                                    final awt a4 = a3.a(offset);
                                    if (!af.a(offset.a, offset.c, a4, a3) && this.a.world().a(awt2.u(), (et)new BetterBlockPos(n9, n8, n7), false, fa, (vg)null)) {
                                        final bhb e = a4.e((amy)this.a.world(), (et)offset);
                                        final fa fa2 = fa;
                                        bhe[] array3 = null;
                                        bhe[] array2 = null;
                                        switch (bt.a[fa2.ordinal()]) {
                                            case 1: {
                                                final bhe[] array = array2 = (array3 = new bhe[5]);
                                                array[0] = new bhe(0.5, 1.0, 0.5);
                                                array[1] = new bhe(0.1, 1.0, 0.5);
                                                array[2] = new bhe(0.9, 1.0, 0.5);
                                                array[3] = new bhe(0.5, 1.0, 0.1);
                                                array[4] = new bhe(0.5, 1.0, 0.9);
                                                break;
                                            }
                                            case 2: {
                                                final bhe[] array4 = array2 = (array3 = new bhe[5]);
                                                array4[0] = new bhe(0.5, 0.0, 0.5);
                                                array4[1] = new bhe(0.1, 0.0, 0.5);
                                                array4[2] = new bhe(0.9, 0.0, 0.5);
                                                array4[3] = new bhe(0.5, 0.0, 0.1);
                                                array4[4] = new bhe(0.5, 0.0, 0.9);
                                                break;
                                            }
                                            case 3:
                                            case 4:
                                            case 5:
                                            case 6: {
                                                final double n10 = (fa2.g() == 0) ? 0.5 : ((1 + fa2.g()) / 2.0);
                                                final double n11 = (fa2.i() == 0) ? 0.5 : ((1 + fa2.i()) / 2.0);
                                                final bhe[] array5 = array2 = (array3 = new bhe[2]);
                                                array5[0] = new bhe(n10, 0.25, n11);
                                                array5[1] = new bhe(n10, 0.75, n11);
                                                break;
                                            }
                                            default: {
                                                throw new NullPointerException();
                                            }
                                        }
                                        final bhe[] array6 = array2;
                                        for (int length2 = array3.length, n12 = 0; n12 < length2; ++n12) {
                                            final bhe bhe = array6[n12];
                                            final Rotation calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(this.a.playerHead(), new bhe(offset.a + e.a * bhe.b + e.d * (1.0 - bhe.b), offset.b + e.b * bhe.c + e.e * (1.0 - bhe.c), offset.c + e.c * bhe.d + e.f * (1.0 - bhe.d)), this.a.playerRotations());
                                            final bhc rayTraceTowards;
                                            final OptionalInt a5;
                                            if ((rayTraceTowards = RayTraceUtils.rayTraceTowards((vg)this.a.player(), calcRotationFromVec3d, this.a.playerController().getBlockReachDistance())) != null && rayTraceTowards.a == bhc$a.b && rayTraceTowards.a().equals((Object)offset) && rayTraceTowards.b == fa.d() && (a5 = this.a(awt2, rayTraceTowards, calcRotationFromVec3d)).isPresent()) {
                                                final Optional<bz> optional2;
                                                final Optional<bz> optional = optional2 = Optional.of(new bz(this, a5.getAsInt(), offset, fa.d(), calcRotationFromVec3d));
                                                break Label_0965;
                                            }
                                        }
                                        continue;
                                        Optional<bz> optional2 = null;
                                        final Optional<bz> optional3 = optional2;
                                        Optional<bz> optional = null;
                                        if (optional.isPresent()) {
                                            return optional3;
                                        }
                                        continue Label_0977;
                                    }
                                }
                                Optional<bz> optional2;
                                final Optional<bz> optional = optional2 = Optional.empty();
                                continue Label_0965;
                            }
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
    
    private OptionalInt a(final awt awt, final bhc bhc, final Rotation rotation) {
        for (int i = 0; i < 9; ++i) {
            final aip aip;
            if (!(aip = (aip)this.a.player().bv.a.get(i)).b() && aip.c() instanceof ahb) {
                final float v = this.a.player().v;
                final float w = this.a.player().w;
                this.a.player().v = rotation.getYaw();
                this.a.player().w = rotation.getPitch();
                final awt a = ((ahb)aip.c()).d().a(this.a.world(), bhc.a().a(bhc.b), bhc.b, (float)bhc.c.b - bhc.a().p(), (float)bhc.c.c - bhc.a().q(), (float)bhc.c.d - bhc.a().r(), aip.c().a(aip.j()), (vp)this.a.player());
                this.a.player().v = v;
                this.a.player().w = w;
                if (a(a, awt)) {
                    return OptionalInt.of(i);
                }
            }
        }
        return OptionalInt.empty();
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        this.a.a.clearAllKeys();
        final bu bu2;
        final bu bu = bu2 = new bu(this);
        boolean b3 = false;
        Label_0095: {
            Label_0094: {
                if (this.a == null) {
                    this.a = new HashSet<BetterBlockPos>();
                    this.b(bu2);
                    if (this.a.isEmpty()) {
                        break Label_0094;
                    }
                }
                this.a(bu2);
                if (this.a.isEmpty()) {
                    this.b(bu2);
                }
                if (!this.a.isEmpty()) {
                    b3 = true;
                    break Label_0095;
                }
            }
            b3 = false;
        }
        if (!b3) {
            this.b("Done building");
            this.onLostControl();
            return null;
        }
        final HashSet<BetterBlockPos> a;
        (a = new HashSet<BetterBlockPos>(this.a)).removeIf(this::a);
        if (!a.isEmpty()) {
            this.a = a;
        }
        if (this.a.a.isInputForcedDown(Input.CLICK_LEFT)) {
            this.a = 5;
        }
        else {
            --this.a;
        }
        final Optional<rr<BetterBlockPos, Rotation>> a2 = this.a(bu);
        this.a.a.clearAllKeys();
        if (a2.isPresent() && b2 && this.a.player().z) {
            final Rotation rotation = (Rotation)a2.get().b();
            final BetterBlockPos betterBlockPos = (BetterBlockPos)a2.get().a();
            this.a.a.updateTarget(rotation, true);
            af.a(this.a, bu.a(betterBlockPos));
            if (this.a.player().aU()) {
                this.a.a.setInputForceState(Input.SNEAK, true);
            }
            if (Objects.equals(this.a.objectMouseOver().a(), betterBlockPos) || this.a.playerRotations().isReallyCloseTo(rotation)) {
                this.a.a.setInputForceState(Input.CLICK_LEFT, true);
            }
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        final ArrayList<awt> list = new ArrayList<awt>();
        final Optional<bz> a3;
        if ((a3 = this.a(bu, (List<awt>)list)).isPresent() && b2 && this.a.player().z && this.a <= 0) {
            final Rotation a4 = a3.get().a;
            this.a.a.updateTarget(a4, true);
            this.a.player().bv.d = a3.get().a;
            this.a.a.setInputForceState(Input.SNEAK, true);
            if ((Objects.equals(this.a.objectMouseOver().a(), a3.get().a) && this.a.objectMouseOver().b.equals((Object)a3.get().a)) || this.a.playerRotations().isReallyCloseTo(a4)) {
                this.a.a.setInputForceState(Input.CLICK_RIGHT, true);
            }
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        final List<awt> a5 = this.a(36);
        Label_0812: {
            if (baritone.a.a().allowInventory.value) {
                final ArrayList<Integer> list2 = new ArrayList<Integer>();
                final ArrayList<Object> list3 = new ArrayList<Object>();
            Label_0633:
                for (final awt awt : list) {
                    for (int i = 0; i < 9; ++i) {
                        if (a(a5.get(i), awt)) {
                            list2.add(i);
                            continue Label_0633;
                        }
                    }
                    list3.add(awt);
                }
                for (int j = 9; j < 36; ++j) {
                    final Iterator<awt> iterator2 = list3.iterator();
                    while (iterator2.hasNext()) {
                        if (a(a5.get(j), iterator2.next())) {
                            this.a.a.a(j, list2::contains);
                            break Label_0812;
                        }
                    }
                }
            }
        }
        Goal goal;
        if ((goal = this.a(bu, a5.subList(0, 9))) == null && (goal = this.a(bu, a5)) == null) {
            this.b("Unable to do it =(");
            this.onLostControl();
            return null;
        }
        return new cv(goal, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH, bu);
    }
    
    private void a(final bu bu) {
        final BetterBlockPos playerFeet = this.a.playerFeet();
        for (int i = -5; i <= 5; ++i) {
            for (int j = -5; j <= 5; ++j) {
                for (int k = -5; k <= 5; ++k) {
                    final int n = playerFeet.a + i;
                    final int n2 = playerFeet.b + j;
                    final int n3 = playerFeet.c + k;
                    final awt a;
                    if ((a = bu.a(bu, n, n2, n3)) != null) {
                        if (a(bu.a.a(n, n2, n3), a)) {
                            this.a.remove(new BetterBlockPos(n, n2, n3));
                        }
                        else {
                            this.a.add(new BetterBlockPos(n, n2, n3));
                        }
                    }
                }
            }
        }
    }
    
    private void b(final bu bu) {
        this.a = new HashSet<BetterBlockPos>();
        for (int i = 0; i < this.a.heightY(); ++i) {
            for (int j = 0; j < this.a.lengthZ(); ++j) {
                for (int k = 0; k < this.a.widthX(); ++k) {
                    if (this.a.inSchematic(k, i, j) && !a(bu.a.a(k + this.a.p(), i + this.a.q(), j + this.a.r()), this.a.desiredState(k, i, j))) {
                        this.a.add(new BetterBlockPos(k + this.a.p(), i + this.a.q(), j + this.a.r()));
                    }
                }
            }
        }
    }
    
    private Goal a(final bu bu, final List<awt> list) {
        final List<? super Object> list2 = this.a.stream().filter(bs::a).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        final Goal[] array = this.a.stream().filter(bs::a).map((Function<? super Object, ?>)bw::new).toArray(bs::b);
        final Goal[] array2;
        if ((array2 = list2.stream().filter(bs::a).map((Function<? super Object, ?>)this::a).toArray(bs::a)).length != 0) {
            return new by(new GoalComposite(array2), new GoalComposite(array));
        }
        if (array.length == 0) {
            return null;
        }
        return new GoalComposite(array);
    }
    
    @Override
    public final void onLostControl() {
        this.a = null;
        this.a = null;
        this.a = null;
    }
    
    @Override
    public final String displayName0() {
        return "Building " + this.a;
    }
    
    public final List<awt> a(final int n) {
        final ArrayList<awt> list = new ArrayList<awt>();
        for (int i = 0; i < n; ++i) {
            final aip aip;
            if ((aip = (aip)this.a.player().bv.a.get(i)).b() || !(aip.c() instanceof ahb)) {
                list.add(aox.a.t());
            }
            else {
                list.add(((ahb)aip.c()).d().a(this.a.world(), (et)this.a.playerFeet(), fa.b, (float)this.a.player().p, (float)this.a.player().q, (float)this.a.player().r, aip.c().a(aip.j()), (vp)this.a.player()));
            }
        }
        return list;
    }
    
    public static boolean a(final awt awt, final awt awt2) {
        return awt2 == null || awt.equals(awt2);
    }
    
    private static Goal[] a(final int n) {
        return new Goal[n];
    }
    
    private Goal a(final bu bu, BetterBlockPos betterBlockPos) {
        betterBlockPos = betterBlockPos;
        if (this.a.world().o((et)betterBlockPos).u() != aox.a) {
            return new bx(betterBlockPos);
        }
        final boolean b = this.a.world().o(betterBlockPos.a()).u() != aox.a;
        final fa[] a = ae.a;
        for (int i = 0; i < 5; ++i) {
            final fa fa = a[i];
            if (af.a(this.a, betterBlockPos.a(fa)) && this.a.world().a(bu.a(bu, betterBlockPos.p(), betterBlockPos.q(), betterBlockPos.r()).u(), (et)betterBlockPos, false, fa, (vg)null)) {
                return new bv(betterBlockPos, b);
            }
        }
        return new bx(betterBlockPos);
    }
    
    private static boolean a(final List list, final BetterBlockPos betterBlockPos) {
        return !list.contains(betterBlockPos.down()) && !list.contains(betterBlockPos.down(2));
    }
    
    private static Goal[] b(final int n) {
        return new Goal[n];
    }
    
    private static boolean a(final bu bu, final BetterBlockPos betterBlockPos) {
        return bu.a.a(betterBlockPos).u() != aox.a;
    }
    
    private static boolean a(final bu bu, final List list, final BetterBlockPos betterBlockPos) {
        return bu.a.a(betterBlockPos).u() == aox.a && list.contains(bu.a(bu, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c));
    }
    
    private boolean a(final BetterBlockPos betterBlockPos) {
        return betterBlockPos.f(this.a.player().p, this.a.player().q, this.a.player().r) > 200.0;
    }
    
    static a a(final bs bs) {
        return bs.a;
    }
    
    static ISchematic a(final bs bs) {
        return bs.a;
    }
    
    static fq a(final bs bs) {
        return bs.a;
    }
}
