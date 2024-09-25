/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import skizzle.friends.Friend;
import skizzle.friends.FriendManager;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.BooleanSetting;
import skizzle.util.Timer;

public class Target
extends Module {
    public BooleanSetting players;
    public BooleanSetting team;
    public BooleanSetting friends;
    public static Minecraft mc = Minecraft.getMinecraft();
    public BooleanSetting invis;
    public BooleanSetting monster;
    public Timer timer = new Timer();
    public BooleanSetting passive;

    public Target() {
        super(Qprot0.0("\u688c\u71ca\u53de\ua7e3\u424a\ued16"), 0, Module.Category.COMBAT);
        Target Nigga;
        Nigga.players = new BooleanSetting(Qprot0.0("\u6888\u71c7\u53cd\ua7fd\u424a\ued10\u8c3c"), true);
        Nigga.passive = new BooleanSetting(Qprot0.0("\u6888\u71ca\u53df\ua7f7\u4246\ued14\u8c2a"), true);
        Nigga.monster = new BooleanSetting(Qprot0.0("\u6895\u71c4\u53c2\ua7f7\u425b\ued07\u8c3d"), true);
        Nigga.friends = new BooleanSetting(Qprot0.0("\u689e\u71d9\u53c5\ua7e1\u4241\ued06\u8c3c"), true);
        Nigga.team = new BooleanSetting(Qprot0.0("\u688c\u71ce\u53cd\ua7e9"), false);
        Nigga.invis = new BooleanSetting(Qprot0.0("\u6891\u71c5\u53da\ua7ed\u425c\ued0b\u8c2d\u04f9\u5707"), false);
        Nigga.addSettings(Nigga.players, Nigga.passive, Nigga.monster, Nigga.friends, Nigga.team, Nigga.invis);
    }

    public boolean isTarget(EntityLivingBase Nigga) {
        Target Nigga2;
        if (!Nigga2.invis.isEnabled() && Nigga.isInvisible()) {
            return false;
        }
        if (Nigga2.players.isEnabled() && Nigga instanceof EntityOtherPlayerMP && (Nigga.getName().length() < 3 || Nigga.getName().length() > 16)) {
            return false;
        }
        if (!Nigga2.players.isEnabled() && Nigga instanceof EntityOtherPlayerMP) {
            return false;
        }
        if (!Nigga2.passive.isEnabled() && ("" + Nigga.getClass()).contains(Qprot0.0("\u68a8\u71ca\u53df\uc6de\ue3af\ued14\u8c2a"))) {
            return false;
        }
        if (!Nigga2.monster.isEnabled() && ("" + Nigga.getClass()).contains(Qprot0.0("\u68b5\u71c4\u53c2\uc6de\ue3b2\ued07\u8c3d"))) {
            return false;
        }
        for (Friend Nigga3 : FriendManager.friends) {
            if (!(Nigga instanceof EntityPlayer) || Nigga2.friends.isEnabled() || !Nigga.getName().equals(Nigga3.getName())) continue;
            return false;
        }
        if (Nigga.getTeam() != null && !Nigga2.team.isEnabled() && Nigga.getTeam().isSameTeam(Target.mc.thePlayer.getTeam())) {
            return false;
        }
        return ModuleManager.antiBot.isTarget(Nigga);
    }

    /*
     * Exception decompiling
     */
    public static double getPrediction(EntityLivingBase Nigga) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl16 : GETSTATIC - null : trying to set 0 previously set to 1
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

