package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;

import fun.ellant.Ellant;
import fun.ellant.events.EventMotion;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.api.FunctionRegistry;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;

@FunctionRegister(name = "Freelook", type = Category.RENDER, desc = "Свободная камера")
public class FreeLock extends Function {
    public float rotYaw = 0;
    public float rotPitch = 0;
    FunctionRegistry functionRegistry = Ellant.getInstance().getFunctionRegistry();

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (functionRegistry.getKillAura().isState() && functionRegistry.getKillAura().getTarget() != null) return;

        mc.gameSettings.setPointOfView(PointOfView.THIRD_PERSON_BACK);

        setRots();
    }
    @Subscribe
    private void onMotion(EventMotion e) {
        if (functionRegistry.getKillAura().isState() && functionRegistry.getKillAura().getTarget() != null) return;
        setRots();

        e.setYaw(rotYaw);
        e.setPitch(rotPitch);
    }
    @Subscribe
    private void onWalking(EventMotion e) {
        if (functionRegistry.getKillAura().isState() && functionRegistry.getKillAura().getTarget() != null) return;
        setRots();

        e.setYaw(rotYaw);
        e.setYaw(rotPitch);
    }
    private void setRots() {
        mc.player.renderYawOffset = calculateCorrectYawOffset(rotYaw);
        mc.player.rotationYawHead = rotYaw;
        mc.player.rotationPitchHead = rotPitch;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.setPointOfView(PointOfView.FIRST_PERSON);
        mc.player.rotationYaw = mc.player.rotationYawHead;
        mc.player.rotationPitch = mc.player.rotationPitchHead;

        super.onDisable();
    }
    @Override
    public boolean onEnable() {
        mc.gameSettings.setPointOfView(PointOfView.THIRD_PERSON_BACK);
        rotYaw = mc.player.rotationYaw;
        rotPitch = mc.player.rotationPitch;

        super.onEnable();
        return false;
    }
    public static float calculateCorrectYawOffset(float yaw) {
        double xDiff = mc.player.getPosX() - mc.player.prevPosX;
        double zDiff = mc.player.getPosZ() - mc.player.prevPosZ;
        float distSquared = (float) (xDiff * xDiff + zDiff * zDiff);
        float renderYawOffset = mc.player.prevRenderYawOffset;
        float offset = renderYawOffset;
        float yawOffsetDiff;

        if (distSquared > 0.0025000002f) {
            offset = (float) MathHelper.atan2(zDiff, xDiff) * 180.0f / (float) Math.PI - 90.0f;
        }

        if (mc.player != null && mc.player.swingProgress > 0.0f) {
            offset = yaw;
        }

        yawOffsetDiff = MathHelper.wrapDegrees(yaw - (renderYawOffset + MathHelper.wrapDegrees(offset - renderYawOffset) * 0.3f));
        yawOffsetDiff = MathHelper.clamp(yawOffsetDiff, -75.0f, 75.0f);

        renderYawOffset = yaw - yawOffsetDiff;
        if (yawOffsetDiff * yawOffsetDiff > 2500.0f) {
            renderYawOffset += yawOffsetDiff * 0.2f;
        }

        return renderYawOffset;
    }
}