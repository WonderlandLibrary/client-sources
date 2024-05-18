package baritone;

import baritone.api.*;
import baritone.api.pathing.movement.*;
import baritone.api.utils.input.*;
import java.util.function.*;
import baritone.api.utils.*;
import java.util.*;

public abstract class ae implements af, IMovement
{
    public static final fa[] a;
    protected final IBaritone a;
    protected final IPlayerContext a;
    private ah a;
    protected final BetterBlockPos a;
    protected final BetterBlockPos b;
    protected final BetterBlockPos[] a;
    protected final BetterBlockPos c;
    public Double a;
    public List<et> a;
    private List<et> c;
    public List<et> b;
    private Boolean a;
    
    protected ae(final IBaritone a, final BetterBlockPos a2, final BetterBlockPos b, final BetterBlockPos[] a3, final BetterBlockPos c) {
        super();
        final ah ah = new ah();
        final MovementStatus prepping = MovementStatus.PREPPING;
        final ah a4 = ah;
        ah.a = prepping;
        this.a = a4;
        this.a = null;
        this.c = null;
        this.b = null;
        this.a = a;
        this.a = a.getPlayerContext();
        this.a = a2;
        this.b = b;
        this.a = a3;
        this.c = c;
    }
    
    protected ae(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2, final BetterBlockPos[] array) {
        this(baritone, betterBlockPos, betterBlockPos2, array, null);
    }
    
    @Override
    public double getCost() {
        return this.a;
    }
    
    public final double a(final ad ad) {
        if (this.a == null) {
            this.a = this.b(ad);
        }
        return this.a;
    }
    
    public abstract double b(final ad p0);
    
    @Override
    public MovementStatus update() {
        this.a.player().bO.b = false;
        this.a = this.a(this.a);
        if (af.c(this.a, this.a.playerFeet())) {
            this.a.a(Input.JUMP, true);
        }
        if (this.a.player().aD()) {
            this.a.a(Input.CLICK_LEFT, true);
        }
        Optional.ofNullable(this.a.a.a).ifPresent(this::a);
        this.a.getInputOverrideHandler().clearAllKeys();
        this.a.a.forEach(this::a);
        this.a.a.clear();
        if (this.a.a.isComplete()) {
            this.a.getInputOverrideHandler().clearAllKeys();
        }
        return this.a.a;
    }
    
    protected boolean a(final ah ah) {
        if (ah.a == MovementStatus.WAITING) {
            return true;
        }
        final BetterBlockPos[] a;
        final int length = (a = this.a).length;
        int i = 0;
        while (i < length) {
            final BetterBlockPos betterBlockPos = a[i];
            if (!af.a(this.a, betterBlockPos) && !(cn.a(this.a, (et)betterBlockPos) instanceof aru)) {
                final Optional<Rotation> reachable;
                if ((reachable = RotationUtils.reachable(this.a.player(), betterBlockPos, this.a.playerController().getBlockReachDistance())).isPresent()) {
                    final Rotation rotation = reachable.get();
                    af.a(this.a, cn.a(this.a, (et)betterBlockPos));
                    ah.a = new ai(rotation, true);
                    if (Objects.equals(this.a.getSelectedBlock().orElse(null), betterBlockPos) || this.a.playerRotations().isReallyCloseTo(rotation)) {
                        ah.a(Input.CLICK_LEFT, true);
                    }
                    return false;
                }
                ah.a = new ai(RotationUtils.calcRotationFromVec3d(this.a.player().f(1.0f), VecUtils.getBlockPosCenter(betterBlockPos), this.a.playerRotations()), true);
                ah.a(Input.CLICK_LEFT, true);
                return false;
            }
            else {
                ++i;
            }
        }
        return true;
    }
    
    @Override
    public boolean safeToCancel() {
        return this.b(this.a);
    }
    
    protected boolean b(final ah ah) {
        return true;
    }
    
    @Override
    public BetterBlockPos getSrc() {
        return this.a;
    }
    
    @Override
    public BetterBlockPos getDest() {
        return this.b;
    }
    
    @Override
    public void reset() {
        final ah ah = new ah();
        final MovementStatus prepping = MovementStatus.PREPPING;
        final ah a = ah;
        ah.a = prepping;
        this.a = a;
    }
    
    public ah a(ah ah) {
        if (!this.a(ah)) {
            final ah ah2 = ah;
            final MovementStatus prepping = MovementStatus.PREPPING;
            ah = ah2;
            ah2.a = prepping;
            return ah;
        }
        if (ah.a == MovementStatus.PREPPING) {
            ah.a = MovementStatus.WAITING;
        }
        if (ah.a == MovementStatus.WAITING) {
            ah.a = MovementStatus.RUNNING;
        }
        return ah;
    }
    
    @Override
    public et getDirection() {
        return this.getDest().b((fq)this.getSrc());
    }
    
    public final void a(final ad ad) {
        this.a = ad.a.a(this.b.a, this.b.c);
    }
    
    @Override
    public boolean calculatedWhileLoaded() {
        return this.a;
    }
    
    @Override
    public void resetBlockCache() {
        this.a = null;
        this.c = null;
        this.b = null;
    }
    
    public List<et> a(final cn cn) {
        if (this.a != null) {
            return this.a;
        }
        final ArrayList<BetterBlockPos> a = new ArrayList<BetterBlockPos>();
        BetterBlockPos[] a2;
        for (int length = (a2 = this.a).length, i = 0; i < length; ++i) {
            final BetterBlockPos betterBlockPos = a2[i];
            if (!af.a(cn, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c)) {
                a.add(betterBlockPos);
            }
        }
        return this.a = (List<et>)a;
    }
    
    public final List<et> b(final cn cn) {
        if (this.c != null) {
            return this.c;
        }
        final ArrayList<BetterBlockPos> c = new ArrayList<BetterBlockPos>();
        if (this.c != null && !af.b(cn, this.c.a, this.c.b, this.c.c)) {
            c.add(this.c);
        }
        return this.c = (List<et>)c;
    }
    
    public List<et> c(final cn cn) {
        if (this.b == null) {
            this.b = new ArrayList<et>();
        }
        return this.b;
    }
    
    private void a(final Input input, final Boolean b) {
        this.a.getInputOverrideHandler().setInputForceState(input, b);
    }
    
    private void a(final Rotation rotation) {
        this.a.getLookBehavior().updateTarget(rotation, this.a.a.a);
    }
    
    static {
        a = new fa[] { fa.c, fa.d, fa.f, fa.e, fa.a };
    }
}
