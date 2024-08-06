package club.strifeclient.module.implementations.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.event.implementations.rendering.Render2DEvent;
import club.strifeclient.font.CustomFontRenderer;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.module.implementations.movement.flight.MotionFlight;
import club.strifeclient.module.implementations.movement.flight.WatchdogFlight;
import club.strifeclient.module.implementations.movement.flight.ZoneCraftFlight;
import club.strifeclient.setting.Mode;
import club.strifeclient.setting.ModeEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.util.math.MathUtil;
import club.strifeclient.util.player.MovementUtil;
import club.strifeclient.util.rendering.ColorUtil;
import net.minecraft.util.BlockPos;

import java.util.function.Supplier;

@ModuleInfo(name = "Flight", description = "Fly like a bird.", aliases = "Fly", category = Category.MOVEMENT)
public final class Flight extends Module {

    public final ModeSetting<FlightMode> modeSetting = new ModeSetting<>("Mode", FlightMode.MOTION);
    public final BooleanSetting showDistanceSetting = new BooleanSetting("Show Distance", true);
    public final BooleanSetting bobbingSetting = new BooleanSetting("Bobbing", true);
    public final DoubleSetting bobbingAmountSetting = new DoubleSetting("Bobbing Amount", 1, 0, 20, 1, bobbingSetting::getValue);
    public final DoubleSetting flightSpeedSetting = new DoubleSetting("Flight Speed", 1, 1, 6, 0.1, () -> modeSetting.getValue() == FlightMode.ZONE_CRAFT);

    private double distance;
    public BlockPos startPos;
    private CustomFontRenderer font;

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        distance += Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ);
        if (bobbingSetting.getValue())
            mc.thePlayer.cameraYaw = MovementUtil.isMoving() ? bobbingAmountSetting.getFloat() / 100 : 0;
    };

    @EventHandler
    private final Listener<Render2DEvent> render2DEventListener = e -> {
        if(font == null)
            font = Client.INSTANCE.getFontManager().getFontByName("ProductSans").size(19);
        final String text = startPos != null ? "Travelled: \u00A77" + MathUtil.round(distance, 2) + " blocks" : "Not flying";
        font.drawStringWithShadow(text, mc.getScaledResolution().getScaledWidth() / 2f + font.getWidth(text) / 2,
                mc.getScaledResolution().getScaledHeight() / 2f - font.getHeight(text) / 2, ColorUtil.getHUDColor(1).getRGB());
    };

    @Override
    protected void onDisable() {
        distance = 0;
        mc.thePlayer.motionX = mc.thePlayer.motionY = mc.thePlayer.motionZ = 0;
        super.onDisable();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        startPos = new BlockPos(mc.thePlayer.getPositionVector());
    }

    public enum FlightMode implements ModeEnum<Flight> {
        WATCHDOG(new WatchdogFlight(), "Watchdog"), MOTION(new MotionFlight(), "Motion"), ZONE_CRAFT(new ZoneCraftFlight(), "Zone Craft");
        final Mode<FlightMode> mode;
        final String name;
        FlightMode(Mode<FlightMode> mode, final String name) {
            this.mode = mode;
            this.name = name;
        }
        @Override
        public Mode<FlightMode> getMode() {
            return mode;
        }
        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public Supplier<Object> getSuffix() {
        return () -> modeSetting.getValue().getName();
    }
}
