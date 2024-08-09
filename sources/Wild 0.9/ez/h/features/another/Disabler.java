package ez.h.features.another;

import ez.h.utils.*;
import java.util.*;
import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Disabler extends Feature
{
    boolean save;
    OptionSlider ping;
    private final List<lk> moveQueue;
    ArrayList<lj> packets;
    OptionMode mode;
    private final Counter matrixModeCounter;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.mode.getMode());
        if (this.mode.isMode("MatrixElytra") && Disabler.mc.h.T % (0x1D ^ 0x9) == 0) {
            for (final agr agr : Disabler.mc.h.bx.c) {
                if (agr.d().c() == air.cS) {
                    Disabler.mc.c.a(0, agr.e, 0, afw.a, (aed)Disabler.mc.h);
                    Disabler.mc.c.a(0, 6, 0, afw.a, (aed)Disabler.mc.h);
                    Disabler.mc.h.d.a((ht)new lq((vg)Disabler.mc.h, lq.a.i));
                    Disabler.mc.h.d.a((ht)new lq((vg)Disabler.mc.h, lq.a.i));
                    Disabler.mc.c.a(0, 6, 0, afw.a, (aed)Disabler.mc.h);
                    Disabler.mc.c.a(0, agr.e, 0, afw.a, (aed)Disabler.mc.h);
                    Disabler.mc.c.e();
                    return;
                }
            }
        }
        if (this.mode.isMode("StormHVH")) {
            eventMotion.onGround = false;
            Disabler.mc.h.z = false;
        }
        if (this.mode.isMode("Matrix") && this.matrixModeCounter.isDelay(5000L)) {
            if (this.moveQueue.size() > 0) {
                Objects.requireNonNull(Disabler.mc.v()).a((ht)this.moveQueue.get(0));
                this.moveQueue.remove(0);
            }
            this.matrixModeCounter.reset();
        }
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (this.mode.isMode("Matrix") && !Disabler.mc.E() && eventPacketSend.getPacket() instanceof lk) {
            this.moveQueue.add((lk)eventPacketSend.getPacket());
            eventPacketSend.setCancelled(true);
        }
        if (this.mode.isMode("Ping Spoof")) {
            if (eventPacketSend.getPacket() instanceof lj && this.save) {
                this.packets.add((lj)eventPacketSend.getPacket());
                eventPacketSend.setCancelled(true);
            }
            if (this.save && !this.packets.isEmpty()) {
                this.save = false;
                final Iterator<lj> iterator;
                lj lj;
                new Thread(() -> {
                    try {
                        this.packets.iterator();
                        while (iterator.hasNext()) {
                            lj = iterator.next();
                            Thread.sleep((long)this.ping.getNum());
                            Disabler.mc.v().a((ht)lj);
                            this.packets.remove(lj);
                        }
                        this.packets.clear();
                        this.save = true;
                    }
                    catch (Exception ex) {}
                    return;
                }, "ping").start();
            }
            if (this.packets.isEmpty() && !this.save) {
                new Thread(() -> {
                    try {
                        Thread.sleep(20L);
                        this.save = true;
                    }
                    catch (Exception ex2) {}
                }, "toSave").start();
            }
        }
    }
    
    @Override
    public void updateElements() {
        this.ping.display = this.mode.isMode("Ping Spoof");
        super.updateElements();
    }
    
    public Disabler() {
        super("Disabler", "\u0423\u0445\u0443\u0434\u0448\u0430\u0435\u0442 \u0440\u0430\u0431\u043e\u0442\u0443 \u043d\u0435\u043a\u043e\u0442\u043e\u0440\u044b\u0445 \u0430\u043d\u0442\u0438\u0447\u0438\u0442\u043e\u0432.", Category.ANOTHER);
        this.save = true;
        this.packets = new ArrayList<lj>();
        this.matrixModeCounter = new Counter();
        this.moveQueue = new ArrayList<lk>();
        this.mode = new OptionMode(this, "Mode", "MatrixElytra", new String[] { "MatrixElytra", "Matrix", "StormHVH", "Ping Spoof" }, 0);
        this.ping = new OptionSlider(this, "Ping", 5000.0f, 1.0f, 20000.0f, OptionSlider.SliderType.MS);
        this.addOptions(this.mode, this.ping);
    }
    
    @Override
    public void onEnable() {
        this.save = true;
        this.packets.clear();
        super.onEnable();
    }
}
