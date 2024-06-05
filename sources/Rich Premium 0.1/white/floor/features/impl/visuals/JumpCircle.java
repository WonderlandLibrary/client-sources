package white.floor.features.impl.visuals;
import clickgui.setting.Setting;
import net.minecraft.util.math.MathHelper;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event3D;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JumpCircle extends Feature {

    static List<Circle> circles = new ArrayList<>();

    public JumpCircle() {
        super("JumpCircles", "Render circles under ur foots if u jumping.", 0, Category.VISUALS);
        Main.settingsManager.rSetting(new Setting("Speed", this, 1, 0.1, 0.5, false));
        Main.settingsManager.rSetting(new Setting("Max Radius", this, 2, 0.5, 2, false));
        Main.settingsManager.rSetting(new Setting("Line Width", this, 1, 0.1, 3, false));
    }

    @EventTarget
    public void onJump(EventUpdate event) {
        if(mc.player.jumpTicks >= 10)
            circles.add(new Circle((float) mc.player.posX, (float) (mc.player.posY - 0.38), (float) mc.player.posZ));
    }

    @EventTarget
    public void onRender(Event3D event) {
        if (circles.size() <= 0)
            return;

        circles.removeIf(circle -> circle.factor > Main.settingsManager.getSettingByName(Main.featureDirector.getModule(JumpCircle.class), "Max Radius").getValFloat());

        for (Circle circle : circles) {
            circle.factor = (float) MathHelper.lerp(circle.factor, Main.settingsManager.getSettingByName(Main.featureDirector.getModule(JumpCircle.class), "Max Radius").getValFloat() + 0.3, Main.settingsManager.getSettingByName(Main.featureDirector.getModule(JumpCircle.class), "Speed").getValFloat() * 2 * Feature.deltaTime()); // Main.settingsManager.getSettingByName(Main.featureDirector.getModule(JumpCircle.class), "Speed").getValFloat() * 2 * deltaTime();
            
            DrawHelper.draw3DCircle(circle, mc.getRenderPartialTicks(), circle.factor, Main.settingsManager.getSettingByName(Main.featureDirector.getModule(JumpCircle.class), "Line Width").getValFloat(), new Color(255, 255, 255));
            DrawHelper.draw3DCircle(circle, mc.getRenderPartialTicks(), circle.factor - 0.005, Main.settingsManager.getSettingByName(Main.featureDirector.getModule(JumpCircle.class), "Line Width").getValFloat() + 0.5f, DrawHelper.setAlpha(new Color(199, 198, 198), 100));
            DrawHelper.draw3DCircle(circle, mc.getRenderPartialTicks(), circle.factor - 0.01, Main.settingsManager.getSettingByName(Main.featureDirector.getModule(JumpCircle.class), "Line Width").getValFloat() + 0.5f, DrawHelper.setAlpha(new Color(180, 180, 180), 90));
        }
    }

    public static class Circle {
        public final float spawnX;
        public final float spawnY;
        public final float spawnZ;

        public float factor = 0;

        public Circle(float spawnX, float spawnY, float spawnZ) {
            this.spawnX = spawnX;
            this.spawnY = spawnY;
            this.spawnZ = spawnZ;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}