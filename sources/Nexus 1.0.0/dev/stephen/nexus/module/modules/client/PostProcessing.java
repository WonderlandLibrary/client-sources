package dev.stephen.nexus.module.modules.client;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;

// thunderhack dev gave me these
public class PostProcessing extends Module {
    public static final BooleanSetting targetHud = new BooleanSetting("TargetHud", false);
    public static final BooleanSetting waterMark = new BooleanSetting("WaterMark", false);
    public static final BooleanSetting arrayList = new BooleanSetting("Arraylist", false);

    public PostProcessing() {
        super("PostProcessing", "Adds blur", 0, ModuleCategory.CLIENT);
        this.addSettings(targetHud, waterMark, arrayList);
    }

    public static boolean shouldBlurTargetHud() {
        return Client.INSTANCE.getModuleManager().getModule(PostProcessing.class).isEnabled() && targetHud.getValue();
    }

    public static boolean shouldBlurWaterMark() {
        return Client.INSTANCE.getModuleManager().getModule(PostProcessing.class).isEnabled() && waterMark.getValue();
    }

    public static boolean shouldBlurArrayList() {
        return Client.INSTANCE.getModuleManager().getModule(PostProcessing.class).isEnabled() && arrayList.getValue();
    }
}
