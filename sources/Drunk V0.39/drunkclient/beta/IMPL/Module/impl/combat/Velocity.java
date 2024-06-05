/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.combat;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPacketReceive;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Value;
import java.awt.Color;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity
extends Module {
    private final Numbers<Double> v = new Numbers<Double>("Veritical", "Veritical", 0.0, 0.0, 100.0, 1.0);
    private final Numbers<Double> h = new Numbers<Double>("Horizontal", "Horizontal", 0.0, 0.0, 100.0, 1.0);
    public static Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[])VelocityMode.values(), (Enum)VelocityMode.Packet);
    private Value<Double> xz;
    private Value<Double> y;

    public Velocity() {
        super("Velocity", new String[]{"antivelocity", "antiknockback", "antikb"}, Type.COMBAT, "Don't take knockback");
        this.addValues(this.h, this.v);
        this.addValues(mode);
        this.setColor(new Color(191, 191, 191).getRGB());
    }

    @EventHandler
    private void onPacket(EventPacketReceive e) {
        switch (mode.getModeAsString()) {
            case "Packet": {
                if (!(e.getPacket() instanceof S12PacketEntityVelocity)) {
                    if (!(e.getPacket() instanceof S27PacketExplosion)) return;
                }
                e.setCancelled(true);
                return;
            }
            case "Custom": {
                if (!(e.getPacket() instanceof S12PacketEntityVelocity)) return;
                S12PacketEntityVelocity packet = (S12PacketEntityVelocity)e.getPacket();
                packet.motionX = (int)((Double)this.h.getValue() / 100.0);
                packet.motionY = (int)((Double)this.v.getValue() / 100.0);
                packet.motionZ = (int)((Double)this.h.getValue() / 100.0);
                return;
            }
        }
    }

    /*
     * Exception decompiling
     */
    private void onPacket(EventPreUpdate event) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$FailedRewriteException: Block member is not a case, it's a class org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredComment
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteSwitch(SwitchStringRewriter.java:236)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at the.bytecode.club.bytecodeviewer.decompilers.impl.CFRDecompiler.decompileToZip(CFRDecompiler.java:306)
         *     at the.bytecode.club.bytecodeviewer.resources.ResourceDecompiling.lambda$null$5(ResourceDecompiling.java:159)
         *     at java.lang.Thread.run(Unknown Source)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static enum VelocityMode {
        Packet,
        Custom,
        AAC,
        Reserve;

    }
}

