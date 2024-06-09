package me.travis.wurstplus.module.modules.render;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;

/**
 * Created by 086 on 9/04/2018.
 */
@Module.Info(name = "AntiFog", description = "Disables or reduces fog", category = Module.Category.RENDER)
public class AntiFog extends Module {

    public static Setting<VisionMode> mode;
    private static AntiFog INSTANCE;

    public AntiFog() {
        (AntiFog.INSTANCE = this).register(AntiFog.mode);
    }
    
    public static boolean enabled() {
        return AntiFog.INSTANCE.isEnabled();
    }
    
    static {
        AntiFog.mode = Settings.e("Mode", VisionMode.NOFOG);
        AntiFog.INSTANCE = new AntiFog();
    }

    public enum VisionMode {
        NOFOG, AIR;
    }

}
