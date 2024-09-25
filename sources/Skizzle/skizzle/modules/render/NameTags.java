/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;

public class NameTags
extends Module {
    public NumberSetting scale;
    public Minecraft mc = Minecraft.getMinecraft();

    public NameTags() {
        super(Qprot0.0("\u8b07\u71ca\ub050\ua7e1\ua2ca\u0e92\u8c28\ue777"), 0, Module.Category.RENDER);
        NameTags Nigga;
        Nigga.scale = new NumberSetting(Qprot0.0("\u8b1a\u71c8\ub05c\ua7e8\ua2fb"), 6.0, 1.0, 12.0, 0.0);
        Nigga.addSettings(Nigga.scale);
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventMotion) {
            Nigga.isPre();
        }
    }

    @Override
    public void onDisable() {
        Nigga.mc.gameSettings.gammaSetting = Float.intBitsToFloat(1.10099034E9f ^ 0x7E1FC77B);
    }

    @Override
    public void onEnable() {
        Nigga.mc.gameSettings.gammaSetting = Float.intBitsToFloat(1.00777165E9f ^ 0x7ED95FFB);
    }

    /*
     * Exception decompiling
     */
    public void renderNameTag(int Nigga, Entity Nigga, String Nigga) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl399 : INVOKESTATIC - null : trying to set 0 previously set to 1
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:203)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1542)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:400)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
         * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
         * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
         * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
         * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
         * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
         * org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException(Decompilation failed);
    }
}

