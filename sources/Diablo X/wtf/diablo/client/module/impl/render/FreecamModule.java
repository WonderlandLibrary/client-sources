package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.math.MathUtil;

@ModuleMetaData(name = "Freecam", description = "Detatches the player's camera from their body", category = ModuleCategoryEnum.RENDER)
public class FreecamModule extends AbstractModule {
    private final NumberSetting<Integer> speed = new NumberSetting<>("Speed", 150, 100, 1000, 25);

    private int fakePlayerID = -1;
    private float startYaw = 0F;
    private float startPitch = 0F;

    public FreecamModule() {
        this.registerSettings(speed);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        fakePlayerID = generateRandomID();
        createFakePlayer(fakePlayerID);

        startYaw = mc.thePlayer.rotationYaw;
        startPitch = mc.thePlayer.rotationPitch;
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        removeFakePlayer(fakePlayerID);
        fakePlayerID = -1;
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        if (event.getEventType() != EventTypeEnum.PRE)
            return;

        final double yaw = mc.thePlayer.cameraYaw;
        final double pitch = mc.thePlayer.cameraPitch;

        if (fakePlayerID == -1) {
            fakePlayerID = mc.thePlayer.getEntityId();

            startYaw = mc.thePlayer.rotationYaw;
            startPitch = mc.thePlayer.rotationPitch;
        }

        mc.thePlayer.rotationYaw = startYaw;
        mc.thePlayer.rotationPitch = startPitch;

        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            mc.getRenderViewEntity().motionX -= Math.sin(Math.toRadians(yaw)) * speed.getValue() / 1000;
            mc.getRenderViewEntity().motionZ += Math.cos(Math.toRadians(yaw)) * speed.getValue() / 1000;
        }

        if (mc.gameSettings.keyBindBack.isKeyDown()) {
            mc.getRenderViewEntity().motionX += Math.sin(Math.toRadians(yaw)) * speed.getValue() / 1000;
            mc.getRenderViewEntity().motionZ -= Math.cos(Math.toRadians(yaw)) * speed.getValue() / 1000;
        }

        if (mc.gameSettings.keyBindLeft.isKeyDown()) {
            mc.getRenderViewEntity().motionX += Math.sin(Math.toRadians(yaw - 90)) * speed.getValue() / 1000;
            mc.getRenderViewEntity().motionZ -= Math.cos(Math.toRadians(yaw - 90)) * speed.getValue() / 1000;
        }

        if (mc.gameSettings.keyBindRight.isKeyDown()) {
            mc.getRenderViewEntity().motionX += Math.sin(Math.toRadians(yaw + 90)) * speed.getValue() / 1000;
            mc.getRenderViewEntity().motionZ -= Math.cos(Math.toRadians(yaw + 90)) * speed.getValue() / 1000;
        }

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.getRenderViewEntity().motionY += speed.getValue() / 1000;
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.getRenderViewEntity().motionY -= speed.getValue() / 1000;
        }

        //mc.getRenderViewEntity().setPosition(mc.getRenderViewEntity().posX + mc.getRenderViewEntity().motionX, mc.getRenderViewEntity().posY + mc.getRenderViewEntity().motionY, mc.getRenderViewEntity().posZ + mc.getRenderViewEntity().motionZ);
        mc.getRenderViewEntity().motionX = 0;
        mc.getRenderViewEntity().motionY = 0;
        mc.getRenderViewEntity().motionZ = 0;
    };

    private int generateRandomID() {
        return -MathUtil.getRandomInt(0, 1000000);
    }

    private void createFakePlayer(final int id) {
        final EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        entityOtherPlayerMP.clonePlayer(mc.thePlayer, true);
        entityOtherPlayerMP.copyLocationAndAnglesFrom(mc.thePlayer);
        entityOtherPlayerMP.rotationYawHead = mc.thePlayer.rotationYawHead;

        mc.theWorld.addEntityToWorld(id, entityOtherPlayerMP);
    }

    private void removeFakePlayer(final int id) {
        mc.theWorld.removeEntityFromWorld(id);
    }
}
