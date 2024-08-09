package ez.h.features.player;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.*;
import java.util.*;
import ez.h.event.events.*;
import ez.h.utils.*;

public class Scaffold extends Feature
{
    private final OptionSlider delay;
    private final OptionBoolean jump;
    public static BlockData data;
    public static List<aow> invalidBlocks;
    private final OptionBoolean swing;
    public OptionBoolean sneak;
    float serverYaw;
    private final OptionMode blockRotation;
    public OptionMode sneakMode;
    private final OptionSlider delayRandom;
    public static OptionBoolean down;
    private final OptionBoolean safewalk;
    public static OptionBoolean sprintoff;
    float serverPitch;
    public OptionSlider sneakSpeed;
    public OptionSlider sneakChance;
    public OptionSlider placeOffset;
    public static boolean isSneaking;
    private final Counter time;
    public OptionSlider rotationOffset;
    private final OptionSlider chance;
    private int slot;
    private final OptionMode towerMode;
    public OptionBoolean airCheck;
    private final OptionSlider speed;
    
    private bhe getVectorToRotate(final BlockData blockData) {
        final et pos = blockData.pos;
        final fa face = blockData.face;
        double n = pos.p() + 0.5;
        double n2 = pos.q() + 0.5;
        double n3 = pos.r() + 0.5;
        if (face == fa.b || face == fa.a) {
            n += 0.4;
            n3 += 0.4;
        }
        else {
            n2 += 0.4;
        }
        if (face == fa.e || face == fa.f) {
            n3 += this.rotationOffset.getNum();
        }
        if (face == fa.d || face == fa.c) {
            n += this.rotationOffset.getNum();
        }
        return new bhe(n, n2, n3);
    }
    
    public Scaffold() {
        super("Scaffold", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0441\u0442\u0430\u0432\u0438\u0442 \u0431\u043b\u043e\u043a\u0438 \u043f\u043e\u0434 \u0432\u0430\u0441.", Category.MOVEMENT);
        this.time = new Counter();
        this.airCheck = new OptionBoolean(this, "Check Air", true);
        this.sneak = new OptionBoolean(this, "Sneak", true);
        this.sneakChance = new OptionSlider(this, "Sneak Chance", 100.0f, 0.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.sneakSpeed = new OptionSlider(this, "Sneak Speed", 0.05f, 0.01f, 1.0f, OptionSlider.SliderType.NULL);
        this.sneakMode = new OptionMode(this, "Sneak Mode", "Packet", new String[] { "Packet", "Client" }, 0);
        this.rotationOffset = new OptionSlider(this, "Rotation Offset", 0.25f, 0.0f, 1.0f, OptionSlider.SliderType.NULL);
        this.placeOffset = new OptionSlider(this, "Place Offset", 0.2f, 0.01f, 0.3f, OptionSlider.SliderType.NULL);
        this.blockRotation = new OptionMode(this, "Rotations", "Matrix", new String[] { "Matrix", "None" }, 0);
        this.towerMode = new OptionMode(this, "Tower", "Matrix", new String[] { "Matrix", "NCP", "Default" }, 0);
        this.chance = new OptionSlider(this, "Chance", 100.0f, 0.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.delay = new OptionSlider(this, "Min Delay", 0.0f, 0.0f, 300.0f, OptionSlider.SliderType.MS);
        this.delayRandom = new OptionSlider(this, "Random Delay", 0.0f, 0.0f, 1000.0f, OptionSlider.SliderType.MS);
        this.speed = new OptionSlider(this, "Speed", 0.6f, 0.05f, 1.2f, OptionSlider.SliderType.NULL);
        Scaffold.sprintoff = new OptionBoolean(this, "Stop Sprinting", true);
        this.safewalk = new OptionBoolean(this, "SafeWalk", true);
        this.jump = new OptionBoolean(this, "Jump", false);
        Scaffold.down = new OptionBoolean(this, "DownWard", false);
        this.swing = new OptionBoolean(this, "SwingHand", false);
        final Option[] array = new Option[0x7D ^ 0x6F];
        array[0] = this.blockRotation;
        array[1] = this.towerMode;
        array[2] = this.chance;
        array[3] = this.delay;
        array[4] = this.delayRandom;
        array[5] = this.rotationOffset;
        array[6] = this.placeOffset;
        array[7] = this.speed;
        array[8] = this.sneak;
        array[9] = this.sneakMode;
        array[0x58 ^ 0x52] = this.sneakChance;
        array[0x13 ^ 0x18] = this.sneakSpeed;
        array[0x68 ^ 0x64] = Scaffold.sprintoff;
        array[0xA7 ^ 0xAA] = this.airCheck;
        array[0x92 ^ 0x9C] = this.safewalk;
        array[0x4D ^ 0x42] = this.jump;
        array[0x55 ^ 0x45] = Scaffold.down;
        array[0x1A ^ 0xB] = this.swing;
        this.addOptions(array);
    }
    
    @EventTarget
    public void onPitch(final EventPitchHead eventPitchHead) {
        eventPitchHead.pitchHead = this.serverPitch;
    }
    
    @EventTarget
    public void onSafe(final SafeWalkEvent safeWalkEvent) {
        if (!this.isEnabled()) {
            return;
        }
        if (this.safewalk.enabled && !Scaffold.isSneaking) {
            safeWalkEvent.setCancelled(Scaffold.mc.h.z);
        }
    }
    
    public static boolean doesHotbarHaveBlock() {
        for (int i = 0; i < 9; ++i) {
            if (Scaffold.mc.h.bv.a(i).c() instanceof ahb) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onDisable() {
        Scaffold.mc.h.bv.d = this.slot;
        Utils.setTimer(0.0f);
        Scaffold.mc.h.d.a((ht)new lq((vg)Scaffold.mc.h, lq.a.b));
        Scaffold.mc.t.Y.i = false;
        super.onDisable();
    }
    
    public static int searchBlock() {
        for (int i = 0; i < (0x6A ^ 0x47); ++i) {
            if (Scaffold.mc.h.bx.a(i).d().c() instanceof ahb) {
                return i;
            }
        }
        return -1;
    }
    
    private bhe getVectorToPlace(final BlockData blockData) {
        final et pos = blockData.pos;
        final fa face = blockData.face;
        double n = pos.p() + 0.5;
        double n2 = pos.q() + 0.5;
        double n3 = pos.r() + 0.5;
        if (face == fa.b || face == fa.a) {
            n += 0.3;
            n3 += 0.3;
        }
        else {
            n2 += 0.5;
        }
        if (face == fa.e || face == fa.f) {
            n3 += this.placeOffset.getNum();
        }
        if (face == fa.d || face == fa.c) {
            n += this.placeOffset.getNum();
        }
        return new bhe(n, n2, n3);
    }
    
    private boolean isValidItem(final ain ain) {
        return ain instanceof ahb && !Scaffold.invalidBlocks.contains(((ahb)ain).d());
    }
    
    public aow getBlockByPos(final et et) {
        return Scaffold.mc.f.o(et).u();
    }
    
    private boolean canSneak() {
        final et et = new et(Scaffold.mc.h.p - this.sneakSpeed.getNum(), Scaffold.mc.h.q - this.sneakSpeed.getNum(), Scaffold.mc.h.r - this.sneakSpeed.getNum());
        final et et2 = new et(Scaffold.mc.h.p - this.sneakSpeed.getNum(), Scaffold.mc.h.q - this.sneakSpeed.getNum(), Scaffold.mc.h.r + this.sneakSpeed.getNum());
        final et et3 = new et(Scaffold.mc.h.p + this.sneakSpeed.getNum(), Scaffold.mc.h.q - this.sneakSpeed.getNum(), Scaffold.mc.h.r + this.sneakSpeed.getNum());
        final et et4 = new et(Scaffold.mc.h.p + this.sneakSpeed.getNum(), Scaffold.mc.h.q - this.sneakSpeed.getNum(), Scaffold.mc.h.r - this.sneakSpeed.getNum());
        return Scaffold.mc.h.l.o(et).u() == aox.a && Scaffold.mc.h.l.o(et2).u() == aox.a && Scaffold.mc.h.l.o(et3).u() == aox.a && Scaffold.mc.h.l.o(et4).u() == aox.a;
    }
    
    @Override
    public void onEnable() {
        if (Scaffold.mc.h != null) {
            this.slot = Scaffold.mc.h.bv.d;
        }
        Scaffold.data = null;
        super.onEnable();
    }
    
    private int getBlockCount() {
        int n = 0;
        for (int i = 0; i < (0xB2 ^ 0x9F); ++i) {
            if (Scaffold.mc.h.bx.a(i).e()) {
                final aip d = Scaffold.mc.h.bx.a(i).d();
                if (this.isValidItem(d.c())) {
                    n += d.c;
                }
            }
        }
        return n;
    }
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
    }
    
    static {
        final aow[] array = new aow[0x8E ^ 0xA6];
        array[0] = aox.bC;
        array[1] = aox.al;
        array[2] = aox.cy;
        array[3] = aox.ai;
        array[4] = aox.cg;
        array[5] = (aow)aox.ae;
        array[6] = aox.z;
        array[7] = aox.a;
        array[8] = (aow)aox.j;
        array[9] = (aow)aox.l;
        array[0xAE ^ 0xA4] = (aow)aox.i;
        array[0x55 ^ 0x5E] = (aow)aox.k;
        array[0xC ^ 0x0] = (aow)aox.m;
        array[0xAA ^ 0xA7] = aox.aH;
        array[0x8A ^ 0x84] = aox.aa;
        array[0xAA ^ 0xA5] = aox.cf;
        array[0xAE ^ 0xBE] = aox.aN;
        array[0xB9 ^ 0xA8] = aox.aG;
        array[0x73 ^ 0x61] = aox.cd;
        array[0x4E ^ 0x5D] = aox.ay;
        array[0x65 ^ 0x71] = aox.B;
        array[0xA7 ^ 0xB2] = aox.az;
        array[0x64 ^ 0x72] = aox.ch;
        array[0x60 ^ 0x77] = aox.aB;
        array[0xB5 ^ 0xAD] = aox.ci;
        array[0x1B ^ 0x2] = (aow)aox.U;
        array[0x31 ^ 0x2B] = (aow)aox.bM;
        array[0x59 ^ 0x42] = (aow)aox.cP;
        array[0x6A ^ 0x76] = (aow)aox.Q;
        array[0xAE ^ 0xB3] = (aow)aox.P;
        array[0x9C ^ 0x82] = (aow)aox.N;
        array[0x34 ^ 0x2B] = (aow)aox.O;
        array[0xE4 ^ 0xC4] = aox.cf;
        array[0xF ^ 0x2E] = aox.bj;
        array[0x24 ^ 0x6] = (aow)aox.cH;
        array[0xAB ^ 0x88] = aox.bi;
        array[0x85 ^ 0xA1] = (aow)aox.aK;
        array[0x3C ^ 0x19] = aox.au;
        array[0xA1 ^ 0x87] = aox.G;
        array[0x36 ^ 0x11] = aox.aU;
        Scaffold.invalidBlocks = Arrays.asList(array);
    }
    
    @EventTarget
    public void onPreMotion(final EventMotion eventMotion) {
        if (!this.isEnabled()) {
            return;
        }
        final String mode = this.towerMode.getMode();
        this.setSuffix(this.blockRotation.getMode());
        if (mode.equalsIgnoreCase("Matrix")) {
            if (!Scaffold.mc.h.isMoving()) {
                if (Scaffold.mc.h.z && Scaffold.mc.t.X.e()) {
                    Scaffold.mc.h.cu();
                }
                if (Scaffold.mc.h.t > 0.0 && !Scaffold.mc.h.z) {
                    final bud h = Scaffold.mc.h;
                    h.t -= 0.00994;
                }
                else {
                    final bud h2 = Scaffold.mc.h;
                    h2.t -= 0.00995;
                }
            }
        }
        else if (mode.equalsIgnoreCase("NCP") && !Scaffold.mc.h.isMoving()) {
            if (Scaffold.mc.h.z && Scaffold.mc.t.X.e()) {
                Scaffold.mc.h.cu();
            }
            if (Scaffold.mc.h.t < 0.1 && !(Scaffold.mc.f.o(new et(Scaffold.mc.h.p, Scaffold.mc.h.q, Scaffold.mc.h.r).a(0.0, -2.0, 0.0)).u() instanceof aom)) {
                final bud h3 = Scaffold.mc.h;
                h3.t -= 190.0;
            }
        }
        if (Scaffold.mc.t.Y.i && Scaffold.down.enabled) {
            Scaffold.mc.t.Y.i = false;
            Scaffold.isSneaking = true;
        }
        else {
            Scaffold.isSneaking = false;
        }
        final bud h4 = Scaffold.mc.h;
        h4.s *= this.speed.getNum();
        final bud h5 = Scaffold.mc.h;
        h5.u *= this.speed.getNum();
        if (!doesHotbarHaveBlock()) {
            if (!(Scaffold.mc.h.cp().c() instanceof ahb) && searchBlock() != -1) {
                Scaffold.mc.c.a(0, searchBlock(), 1, afw.b, (aed)Scaffold.mc.h);
            }
        }
        final et et = Scaffold.isSneaking ? new et((vg)Scaffold.mc.h).a(1, -1, 0).b() : new et((vg)Scaffold.mc.h).a(0, -1, 0);
        for (double n = Scaffold.mc.h.q - 1.0; n > 0.0; --n) {
            final BlockData blockData = this.getBlockData(et);
            if (blockData != null && Scaffold.mc.h.q - n <= 7.0) {
                Scaffold.data = blockData;
            }
        }
        if (Scaffold.sprintoff.enabled) {
            Scaffold.mc.h.f(false);
        }
        if (Scaffold.data != null && this.slot != -1 && !Scaffold.mc.h.ao()) {
            this.getVectorToRotate(Scaffold.data);
            if (this.blockRotation.getMode().equalsIgnoreCase("Matrix")) {
                final float n2 = MathUtils.Rotations.getYawByMotion() - 180.0f;
                eventMotion.setYaw(n2);
                eventMotion.setPitch(82.5f);
                this.serverYaw = eventMotion.yaw;
                this.serverPitch = eventMotion.pitch;
                Scaffold.mc.h.aN = n2;
                Scaffold.mc.h.aO = n2;
                Scaffold.mc.h.aP = n2;
            }
        }
    }
    
    public BlockData getBlockData(et b) {
        BlockData blockData = null;
        int n = 0;
        while (blockData == null) {
            if (n >= 2) {
                break;
            }
            if (this.isBlockPosAir(b.a(0, 0, 1))) {
                blockData = new BlockData(b.a(0, 0, 1), fa.c);
                break;
            }
            if (this.isBlockPosAir(b.a(0, 0, -1))) {
                blockData = new BlockData(b.a(0, 0, -1), fa.d);
                break;
            }
            if (this.isBlockPosAir(b.a(1, 0, 0))) {
                blockData = new BlockData(b.a(1, 0, 0), fa.e);
                break;
            }
            if (this.isBlockPosAir(b.a(-1, 0, 0))) {
                blockData = new BlockData(b.a(-1, 0, 0), fa.f);
                break;
            }
            if (Scaffold.mc.t.X.e() && this.isBlockPosAir(b.a(0, -1, 0))) {
                blockData = new BlockData(b.a(0, -1, 0), fa.b);
                break;
            }
            if (this.isBlockPosAir(b.a(0, 1, 0)) && Scaffold.isSneaking) {
                blockData = new BlockData(b.a(0, 1, 0), fa.a);
                break;
            }
            if (this.isBlockPosAir(b.a(0, 1, 1)) && Scaffold.isSneaking) {
                blockData = new BlockData(b.a(0, 1, 1), fa.a);
                break;
            }
            if (this.isBlockPosAir(b.a(0, 1, -1)) && Scaffold.isSneaking) {
                blockData = new BlockData(b.a(0, 1, -1), fa.a);
                break;
            }
            if (this.isBlockPosAir(b.a(1, 1, 0)) && Scaffold.isSneaking) {
                blockData = new BlockData(b.a(1, 1, 0), fa.a);
                break;
            }
            if (this.isBlockPosAir(b.a(-1, 1, 0)) && Scaffold.isSneaking) {
                blockData = new BlockData(b.a(-1, 1, 0), fa.a);
                break;
            }
            if (this.isBlockPosAir(b.a(1, 0, 1))) {
                blockData = new BlockData(b.a(1, 0, 1), fa.c);
                break;
            }
            if (this.isBlockPosAir(b.a(-1, 0, -1))) {
                blockData = new BlockData(b.a(-1, 0, -1), fa.d);
                break;
            }
            if (this.isBlockPosAir(b.a(1, 0, 1))) {
                blockData = new BlockData(b.a(1, 0, 1), fa.e);
                break;
            }
            if (this.isBlockPosAir(b.a(-1, 0, -1))) {
                blockData = new BlockData(b.a(-1, 0, -1), fa.f);
                break;
            }
            if (this.isBlockPosAir(b.a(-1, 0, 1))) {
                blockData = new BlockData(b.a(-1, 0, 1), fa.c);
                break;
            }
            if (this.isBlockPosAir(b.a(1, 0, -1))) {
                blockData = new BlockData(b.a(1, 0, -1), fa.d);
                break;
            }
            if (this.isBlockPosAir(b.a(1, 0, -1))) {
                blockData = new BlockData(b.a(1, 0, -1), fa.e);
                break;
            }
            if (this.isBlockPosAir(b.a(-1, 0, 1))) {
                blockData = new BlockData(b.a(-1, 0, 1), fa.f);
                break;
            }
            b = b.b();
            ++n;
        }
        return blockData;
    }
    
    @EventTarget
    public void onUpdate(final EventMotion eventMotion) {
        if (!this.isEnabled()) {
            return;
        }
        if (!doesHotbarHaveBlock() || Scaffold.data == null) {
            return;
        }
        int d = -1;
        final int d2 = Scaffold.mc.h.bv.d;
        final et pos = Scaffold.data.pos;
        final bhe vectorToPlace = this.getVectorToPlace(Scaffold.data);
        for (int i = 0; i < 9; ++i) {
            if (this.isValidItem(Scaffold.mc.h.bv.a(i).c())) {
                d = i;
            }
        }
        if (d != -1) {
            if (this.jump.enabled && !Scaffold.mc.t.X.e() && Scaffold.mc.h.z) {
                Scaffold.mc.h.cu();
            }
            if (!this.jump.enabled && doesHotbarHaveBlock() && Scaffold.mc.h.isMoving() && !Scaffold.mc.t.X.e() && this.sneak.enabled && MathUtils.nextFloat(0.0f, 100.0f) <= this.sneakChance.getNum() && doesHotbarHaveBlock()) {
                if (this.canSneak()) {
                    if (this.sneakMode.getMode().equals("Packet")) {
                        Scaffold.mc.h.d.a((ht)new lq((vg)Scaffold.mc.h, lq.a.f));
                        Scaffold.mc.h.d.a((ht)new lq((vg)Scaffold.mc.h, lq.a.a));
                    }
                    else if (this.sneakMode.getMode().equals("Client")) {
                        Scaffold.mc.t.Y.i = true;
                    }
                }
                else if (this.sneakMode.getMode().equals("Packet")) {
                    Scaffold.mc.h.d.a((ht)new lq((vg)Scaffold.mc.h, lq.a.b));
                }
                else if (this.sneakMode.getMode().equals("Client")) {
                    Scaffold.mc.t.Y.i = false;
                }
            }
            if (this.time.hasReached(this.delay.getNum() + MathUtils.nextFloat(0.0f, this.delayRandom.getNum())) && this.canPlace()) {
                if (MathUtils.nextFloat(0.0f, 100.0f) <= this.chance.getNum()) {
                    Scaffold.mc.h.bv.d = d;
                }
                if (Utils.isBlockEdge((vp)Scaffold.mc.h) || (Scaffold.mc.f.o(Scaffold.mc.h.c().b()).u() instanceof aom && this.canPlace())) {
                    Scaffold.mc.c.a(Scaffold.mc.h, Scaffold.mc.f, pos, Scaffold.data.face, vectorToPlace, ub.a);
                    if (this.swing.enabled) {
                        Scaffold.mc.h.a(ub.a);
                    }
                    else {
                        Scaffold.mc.h.d.a((ht)new ly(ub.a));
                    }
                }
                Scaffold.mc.h.bv.d = d2;
                this.time.reset();
            }
        }
    }
    
    @Override
    public void updateElements() {
        this.sneakChance.display = this.sneak.enabled;
        this.sneakSpeed.display = this.sneak.enabled;
        this.sneakMode.display = this.sneak.enabled;
        super.updateElements();
    }
    
    public boolean isBlockPosAir(final et et) {
        return this.getBlockByPos(et) != aox.a && !(this.getBlockByPos(et) instanceof aru);
    }
    
    private boolean canPlace() {
        final et et = new et(Scaffold.mc.h.p - this.placeOffset.getNum(), Scaffold.mc.h.q - this.placeOffset.getNum(), Scaffold.mc.h.r - this.placeOffset.getNum());
        final et et2 = new et(Scaffold.mc.h.p - this.placeOffset.getNum(), Scaffold.mc.h.q - this.placeOffset.getNum(), Scaffold.mc.h.r + this.placeOffset.getNum());
        final et et3 = new et(Scaffold.mc.h.p + this.placeOffset.getNum(), Scaffold.mc.h.q - this.placeOffset.getNum(), Scaffold.mc.h.r + this.placeOffset.getNum());
        final et et4 = new et(Scaffold.mc.h.p + this.placeOffset.getNum(), Scaffold.mc.h.q - this.placeOffset.getNum(), Scaffold.mc.h.r - this.placeOffset.getNum());
        return Scaffold.mc.h.l.o(et).u() == aox.a && Scaffold.mc.h.l.o(et2).u() == aox.a && Scaffold.mc.h.l.o(et3).u() == aox.a && Scaffold.mc.h.l.o(et4).u() == aox.a;
    }
    
    private static class BlockData
    {
        public et pos;
        public fa face;
        
        private BlockData(final et pos, final fa face) {
            this.pos = pos;
            this.face = face;
        }
    }
}
