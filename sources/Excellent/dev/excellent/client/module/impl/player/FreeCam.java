package dev.excellent.client.module.impl.player;

import com.mojang.authlib.GameProfile;
import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.NumberValue;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.UUID;

@SuppressWarnings("unused")
@ModuleInfo(name = "Free Cam", description = "Позволяет вам визуально перемещаться сквозь стены.", category = Category.PLAYER)
public class FreeCam extends Module {
    public static Singleton<FreeCam> singleton = Singleton.create(() -> Module.link(FreeCam.class));
    private final NumberValue yspeed = new NumberValue("Скорость по Y", this, 0.25F, 0.05F, 5F, 0.05F);
    private final NumberValue hspeed = new NumberValue("Скорость по X, Z", this, 0.5F, 0.05F, 5F, 0.05F);
    private Vector3d pos = null;
    @Getter
    private FakePlayer player = null;
    private boolean prevFlying;

    @Override
    protected void onEnable() {
        super.onEnable();
        if (mc.player == null) {
            return;
        }
        mc.player.setJumping(false);
        initFakePlayer();
        addFakePlayer();
        player.spawn();
        mc.player.movementInput = new MovementInput();
        mc.player.moveForward = 0;
        mc.player.moveStrafing = 0;
        mc.setRenderViewEntity(player);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        if (mc.player == null) {
            return;
        }
        removeFakePlayer();
        mc.setRenderViewEntity(null);
        mc.player.movementInput = new MovementInputFromOptions(mc.gameSettings);
    }

    private final Listener<MotionEvent> onMotion = event -> {
        mc.player.motion = Vector3d.ZERO;
        event.cancel();
    };

    private final Listener<Render2DEvent> onRender = event -> {
        if (pos == null) {
            return;
        }
        float xPosition = (float) (player.getPosX() - mc.player.getPosX());
        float yPosition = (float) (player.getPosY() - mc.player.getPosY());
        float zPosition = (float) (player.getPosZ() - mc.player.getPosZ());

        String position = "X:" + Mathf.round(xPosition, 1) + " Y:" + Mathf.round(yPosition, 1) + " Z:" + Mathf.round(zPosition, 1);
        Fonts.INTER_BOLD.get(14).drawCenter(event.getMatrix(), position, scaled().x / 2F, scaled().y / 2F - 30, -1);
    };
    private final Listener<WorldChangeEvent> onWorldChange = event -> toggle();

    private final Listener<PacketEvent> onPacket = event -> {
        if (mc.world == null || mc.player == null || !mc.player.isAlive()) {
            toggle();
            return;
        }
        if (event.getPacket() instanceof CPlayerPacket wrapper) {
            if (wrapper.moving) {
                wrapper.x = player.getPosX();
                wrapper.y = player.getPosY();
                wrapper.z = player.getPosZ();
            }
            wrapper.onGround = player.isOnGround();
            if (wrapper.rotating) {
                wrapper.yaw = player.rotationYaw;
                wrapper.pitch = player.rotationPitch;
            }
        }
    };
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (player != null) {
            player.noClip = true;
            player.setOnGround(false);
            setMotion(hspeed.getValue().floatValue(), player);
            if (Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
                player.setPosition(player.getPosX(), player.getPosY() + yspeed.getValue().floatValue(), player.getPosZ());
            }
            if (Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown()) {
                player.setPosition(player.getPosX(), player.getPosY() - yspeed.getValue().floatValue(), player.getPosZ());
            }
            player.abilities.isFlying = true;

        }
    };

    private void initFakePlayer() {
        pos = mc.player.getPositionVec();
        player = new FakePlayer(-404);
        player.copyLocationAndAnglesFrom(mc.player);
        player.rotationYawHead = mc.player.rotationYawHead;
        player.rotationPitchHead = mc.player.rotationPitchHead;
    }

    private void addFakePlayer() {
        pos = mc.player.getPositionVec();
        mc.world.addEntity(-404, player);
    }

    private void removeFakePlayer() {
        resetFlying();
        mc.world.removeEntityFromWorld(-404);
        player = null;
        pos = null;
    }

    private void resetFlying() {
        if (prevFlying) {
            mc.player.abilities.isFlying = false;
            prevFlying = false;
        }
    }

    private void setMotion(final double motion, FakePlayer player) {
        double forward = player.movementInput.moveForward;
        double strafe = player.movementInput.moveStrafe;
        float yaw = player.rotationYaw;
        if (forward == 0 && strafe == 0) {
            player.motion.x = 0;
            player.motion.z = 0;
        } else {
            if (forward != 0) {
                if (strafe > 0) yaw += (float) (forward > 0 ? -45 : 45);
                else if (strafe < 0) yaw += (float) (forward > 0 ? 45 : -45);
                strafe = 0;
                if (forward > 0) forward = 1;
                else if (forward < 0) forward = -1;
            }
            player.motion.x = forward * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f)) + strafe * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f));
            player.motion.z = forward * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f)) - strafe * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f));
        }
    }

    public static class FakePlayer extends ClientPlayerEntity implements IMinecraft {
        private static final ClientPlayNetHandler NETWORK_HANDLER = new ClientPlayNetHandler(IMinecraft.mc, IMinecraft.mc.currentScreen, IMinecraft.mc.getConnection().getNetworkManager(), new GameProfile(UUID.randomUUID(), "fakeplayer")) {
            @Override
            public void sendPacket(IPacket<?> packetIn) {
                super.sendPacket(packetIn);
            }
        };

        public FakePlayer(int entityId) {
            super(IMinecraft.mc, IMinecraft.mc.world, NETWORK_HANDLER, IMinecraft.mc.player.getStats(), IMinecraft.mc.player.getRecipeBook(), false, false);

            setEntityId(entityId);
            movementInput = new MovementInputFromOptions(IMinecraft.mc.gameSettings);
        }

        public void spawn() {
            if (world != null) {
                world.addEntity(this);
            }
        }

        @Override
        public void livingTick() {
            super.livingTick();
        }

        @Override
        public void rotateTowards(double yaw, double pitch) {
            super.rotateTowards(yaw, pitch);
        }
    }
}
