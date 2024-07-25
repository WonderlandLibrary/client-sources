package club.bluezenith.module.modules.render;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ColorValue;
import club.bluezenith.module.value.types.FloatValue;
import net.minecraft.client.Minecraft;

public class Camera extends Module {

    public static Camera module;
    private static float prevBrightness = 0f;
    public static float yaw = 0;
    public static float pitch = 0;
    public static float prevYaw = 0;
    public static float prevPitch = 0;
    public static final BooleanValue rotations = new BooleanValue("Third Person Rotations", true, true, null).setIndex(1);
    public static final BooleanValue noFire = new BooleanValue("Show fire in 1st person", true, true, null).setIndex(2);
    public static final BooleanValue cameraClip = new BooleanValue("Camera clip", false, true, null).setIndex(3);
    public static final BooleanValue fullbright = new BooleanValue("FullBright", false, true, (p1, enabled) -> {
       final Minecraft mc = Minecraft.getMinecraft();
       if(!BlueZenith.getBlueZenith().getModuleManager().getModule(Camera.class).getState() && player != null) {
           BlueZenith.getBlueZenith().getNotificationPublisher().postError("Camera", "You have to enable the module\nbefore toggling fullbright", 3000);
           return p1;
       }
       if(!enabled) {
           mc.gameSettings.gammaSetting = prevBrightness;
       } else {
           prevBrightness = mc.gameSettings.gammaSetting;
           mc.gameSettings.gammaSetting = 10000;
       }
       return enabled;
    }, null).setIndex(4);
    public static final BooleanValue noHurtCam = new BooleanValue("No Hurt Cam", false, true, null).setIndex(5);
    public static final BooleanValue customFog = new BooleanValue("Custom fog", false).setIndex(6);
    public static final ColorValue fogColor = new ColorValue("Fog color").setIndex(7).showIf(customFog::get);
    public static final FloatValue fogDistance = AbstractBuilder.createFloat("Fog distance").index(8).min(-1F).max(0.95F).increment(0.05F).showIf(customFog::get).defaultOf(1F).build();
    public static final BooleanValue smoothZoom = new BooleanValue("Smooth zoom", false).setIndex(9);
    public static final FloatValue zoomSpeed = AbstractBuilder.createFloat("Zoom speed").index(10).defaultOf(40F).min(10F).max(100F).increment(5F).showIf(smoothZoom::get).build();
    public static final BooleanValue smoothZoomCamera = new BooleanValue("Smooth zoom camera", true).setIndex(11);

    public Camera() {
        super("Camera", ModuleCategory.RENDER);
        module = this;
    }
}
