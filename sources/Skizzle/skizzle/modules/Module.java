/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.files.FileManager;
import skizzle.modules.ModuleManager;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.settings.BooleanSetting;
import skizzle.settings.DescriptionSetting;
import skizzle.settings.KeybindSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.settings.Setting;
import skizzle.util.AnimationHelper;
import skizzle.util.Colors;
import skizzle.util.RandomHelper;
import skizzle.util.Timer;

public class Module {
    public Timer animationTime = new Timer();
    public boolean hasToggledDisabled = false;
    public boolean animgoingin = true;
    public String displayName;
    public Minecraft mc;
    public Category category;
    public boolean expanded;
    public boolean cgExpanded;
    public double animStageVerticle = 0.0;
    public int settingAnimStageH = 0;
    public int rndColor;
    public List<Setting> settings;
    public boolean displayHud = true;
    public boolean toggled;
    public boolean ghostEnabled = false;
    public FileManager fileManager;
    public String name;
    public int index;
    public int settingAnimStageV = 0;
    public double animStage = 30.0;
    public KeybindSetting keyCode = new KeybindSetting(0);

    public void setSuffix(String Nigga) {
        Module Nigga2;
        if (Nigga.equals("")) {
            Nigga2.displayName = Nigga2.name;
        } else {
            EnumChatFormatting Nigga3 = EnumChatFormatting.GRAY;
            if (ModuleManager.hudModule.displayMode.getMode().equals(Qprot0.0("\ud144\u71c4\uea16\uebd5\u0c96\u54de\u8c3b"))) {
                Nigga3 = EnumChatFormatting.DARK_GRAY;
            }
            Nigga2.displayName = String.valueOf(Nigga2.name) + " " + (Object)((Object)Nigga3) + Nigga;
        }
    }

    public void onEventOverride(Event Nigga) {
    }

    public boolean isEnabled() {
        Module Nigga;
        return Nigga.toggled;
    }

    public boolean canToggle() {
        return true;
    }

    public int getKey() {
        Module Nigga;
        return Nigga.keyCode.code;
    }

    public void onDisable() {
    }

    public int lambda$0(Setting Nigga) {
        Module Nigga2;
        return Nigga == Nigga2.keyCode ? 1 : 0;
    }

    public Module(String Nigga, int Nigga2, Category Nigga3) {
        Module Nigga4;
        Nigga4.mc = Minecraft.getMinecraft();
        Nigga4.settings = new ArrayList<Setting>();
        Nigga4.fileManager = new FileManager();
        Nigga4.name = Nigga;
        Nigga4.displayName = Nigga;
        Nigga4.keyCode.code = Nigga2;
        Nigga4.category = Nigga3;
        Nigga4.addSettings(Nigga4.keyCode);
        if (Nigga4.fileManager.checkActiveModule(Nigga4)) {
            Nigga4.toggled = true;
            Nigga4.displayHud = true;
            Nigga4.animgoingin = false;
        }
        try {
            ScaledResolution Nigga5 = new ScaledResolution(Nigga4.mc, Nigga4.mc.displayWidth, Nigga4.mc.displayHeight);
            MinecraftFontRenderer Nigga6 = Client.fontNormal;
            Nigga4.animStage = Nigga5.getScaledWidth() - (Nigga5.getScaledWidth() - Nigga6.getStringWidth(Nigga4.displayName) - 12);
        }
        catch (Exception exception) {}
        Nigga4.rndColor = Colors.getColor(RandomHelper.randomInt(0, 255), RandomHelper.randomInt(0, 255), RandomHelper.randomInt(0, 255));
    }

    public void onEvent(Event Nigga) {
    }

    public void onEnable() {
    }

    public void addSettings(Setting ... Nigga) {
        Module Nigga2;
        Nigga2.settings.addAll(Arrays.asList(Nigga));
        Nigga2.settings.sort(Comparator.comparingInt(Nigga2::lambda$0));
        for (Setting Nigga3 : Nigga2.settings) {
            if (Nigga3 instanceof DescriptionSetting) continue;
            String Nigga4 = Nigga2.name.toLowerCase();
            String Nigga5 = Nigga4.replace(" ", "_");
            String Nigga6 = Nigga3.name.toLowerCase();
            String Nigga7 = Nigga6.replace(" ", "_");
            String[] Nigga8 = Client.readSettings;
            if (Client.readSettings == null) {
                Nigga8 = Client.readSettings;
            }
            String[] arrstring = Nigga8;
            int n = Nigga8.length;
            for (int i = 0; i < n; ++i) {
                String Nigga9 = arrstring[i];
                try {
                    Comparable<Boolean> Nigga10;
                    String[] Nigga11 = Nigga9.split(";");
                    String[] Nigga12 = Nigga11[1].split(":");
                    if (!Nigga11[0].equals(Nigga5) || !Nigga12[0].equals(Nigga7)) continue;
                    if (Nigga3 instanceof BooleanSetting) {
                        Nigga10 = Boolean.parseBoolean(Nigga12[1]);
                        ((BooleanSetting)Nigga3).setEnabled((Boolean)Nigga10);
                    }
                    if (Nigga3 instanceof NumberSetting) {
                        Nigga10 = Double.parseDouble(Nigga12[1]);
                        ((NumberSetting)Nigga3).value = (Double)Nigga10;
                        ((NumberSetting)Nigga3).sliderValue = (Double)Nigga10;
                    }
                    if (Nigga3 instanceof ModeSetting) {
                        Nigga10 = Integer.parseInt(Nigga12[1]);
                        if ((Integer)Nigga10 > ((ModeSetting)Nigga3).modes.size() - 1) {
                            Nigga10 = ((ModeSetting)Nigga3).modes.size() - 1;
                        }
                        ((ModeSetting)Nigga3).setMode((Integer)Nigga10);
                    }
                    if (!(Nigga3 instanceof KeybindSetting)) continue;
                    Nigga10 = Integer.parseInt(Nigga12[1]);
                    ((KeybindSetting)Nigga3).code = (Integer)Nigga10;
                    continue;
                }
                catch (Exception exception) {}
            }
        }
    }

    public static {
        throw throwable;
    }

    public boolean checkGhostState() {
        Module Nigga;
        return Nigga.ghostEnabled;
    }

    /*
     * Exception decompiling
     */
    public void toggle() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl172 : GETSTATIC - null : trying to set 0 previously set to 1
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

    public static enum Category {
        COMBAT(Qprot0.0("\u6d90\u71c4\u56ca\uab3d\u5308\ue81d")),
        MOVEMENT(Qprot0.0("\u6d9e\u71c4\u56d1\uab3a\u5304\ue80c\u8c21\u01ea")),
        PLAYER(Qprot0.0("\u6d83\u71c7\u56c6\uab26\u530c\ue81b")),
        WORLD(Qprot0.0("\u6d84\u71c4\u56d5\uab33\u530d")),
        RENDER(Qprot0.0("\u6d81\u71ce\u56c9\uab3b\u530c\ue81b")),
        SERVER(Qprot0.0("\u6d80\u71ce\u56d5\uab29\u530c\ue81b")),
        OTHER(Qprot0.0("\u6d9c\u71df\u56cf\uab3a\u531b")),
        PROFILES(Qprot0.0("\u6d83\u71d9\u56c8\uab39\u5300\ue805\u8c2a\u01ed")),
        SCRIPTS(Qprot0.0("\u6d80\u71c8\u56d5\uab36\u5319\ue81d\u8c3c"));

        public int cgY = -100;
        public int lastX = 0;
        public int lastY = 0;
        public int scroll = 0;
        public int moduleIndex;
        public boolean clickGuiExpand = false;
        public boolean isMoving = false;
        public AnimationHelper outAnim = new AnimationHelper();
        public boolean goingIn = false;
        public double lastRotation = 0.0;
        public int cgX = 10;
        public double rotation = 0.0;
        public String name;

        public Category(String Nigga) {
            Category Nigga2;
            Nigga2.name = Nigga;
        }
    }
}

