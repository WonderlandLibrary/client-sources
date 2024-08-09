package ru.FecuritySQ.module.сражение;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPostMotion;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.event.imp.WalkingUpdateEvent;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.module.дисплей.ClientOverlay;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionNumric;
import ru.FecuritySQ.shader.StencilUtil;
import ru.FecuritySQ.utils.ColorUtil;
import ru.FecuritySQ.utils.Counter;
import ru.FecuritySQ.utils.GCDFixUtility;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    public OptionNumric range = new OptionNumric("Дистаниця", 4, 1, 10, 0.1F);
    public OptionBoolList ignore = new OptionBoolList("Игнорировать", new OptionBoolean("Друзья", false), new OptionBoolean("Невидмки", false));
    public OptionBoolean snap = new OptionBoolean("МультиПоинты", true);
    public OptionBoolean coret = new OptionBoolean("Корекция ротации", true);
    private Vector2f rotation = new Vector2f();
    Counter timer = new Counter();
    private float prevAdditionYaw;
    private boolean rotatedBefore;
    private final Counter shieldCounter = new Counter();

    public static PlayerEntity target;

    public KillAura() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(range);
        addOption(ignore);
        addOption(snap);
        addOption(coret);
    }
    @Override
    public void enable() {
        target = null;
        super.enable();
    }

    @Override
    public void disable() {
        target = null;
        super.disable();
    }

    @Override
    public void event(Event e) {
        if(!isEnabled()) return;
        if(e instanceof WalkingUpdateEvent event){
            if (target == null) return;
            event.setYaw(rotation.x);
            event.setPitch(rotation.y);
            mc.player.rotationYawHead = rotation.x;
            mc.player.renderYawOffset = rotation.x;
            mc.player.setSprinting(false);
        }
        if (coret.get()) {
            calculateCorrectYawOffset(rotation.x);
        }
        if(e instanceof EventPostMotion){

            List<PlayerEntity> valid = mc.world.getPlayers().stream().filter(entityLivingBase1 -> {
                if (entityLivingBase1.isInvisible() && ignore.setting("Невидмки").get()) return false;
                if (ignore.setting("Друзья").get() && (FecuritySQ.get().getFriendManager().isFriend(entityLivingBase1.getName().getString())))
                    return false;
                if (entityLivingBase1.isInvisible() && ignore.setting("Невидмки").get()) return false;
                if(mc.player.getDistance(entityLivingBase1) >= range.current) return false;

                if(!entityLivingBase1.isAlive()) return false;
                if(!mc.player.isAlive()) return false;
                if(FecuritySQ.get().getFriendManager().isFriend(entityLivingBase1.getName().getString())) return false;
                return mc.player != entityLivingBase1;
            }).collect(Collectors.toList());

            for(PlayerEntity playerEntity : valid){
                target = playerEntity;
            }

            if(valid.isEmpty() || !valid.contains(target)){
                target = null;
            }

            if (target == null) {
                rotation = new Vector2f(mc.player.rotationYaw, mc.player.rotationPitch);
                return;
            }

            rotatedBefore = false;
            if(whenFalling()) {
                if (mc.player.getCooledAttackStrength(0) == 1) {
                    mc.player.connection.sendPacket(new CUseEntityPacket(target, mc.player.isSneaking()));
                    mc.player.swingArm(Hand.MAIN_HAND);
                    mc.player.resetCooldown();
                }
            }
            if (!rotatedBefore) {
                setRotation(target, false);
            }
        }
    }

    public boolean whenFalling() {
        boolean critWater = mc.player.areEyesInFluid(FluidTags.WATER);
        final boolean reasonForCancelCritical = mc.player.isPotionActive(Effects.BLINDNESS)
                || mc.player.isOnLadder()
                || mc.player.isInWater() && critWater
                || mc.player.isRidingHorse()
                || mc.player.abilities.isFlying || mc.player.isElytraFlying();

        if (mc.player.getCooledAttackStrength(1) < 0.92F)
            return false;
        if (!reasonForCancelCritical) {
            return !mc.player.isOnGround() && mc.player.fallDistance > 0.0F;
        }
        return true;
    }

    public static boolean isHitBoxVisible(Vector3d vec3d) {
        final Vector3d eyesPos = new Vector3d(mc.player.getPosX(), mc.player.getBoundingBox().minY + mc.player.getEyeHeight(), mc.player.getPosZ());


        RayTraceResult result = mc.world.rayTraceBlocks(new RayTraceContext(eyesPos, vec3d, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, mc.renderViewEntity));


        return result == null;
    }
    public static Vector3d getBestPoint(Entity e) {
        float x = (float) limit(e.getBoundingBox().minX, e.getBoundingBox().maxX - e.getBoundingBox().minX, mc.player.getPosX());
        float y = (float) limit(e.getBoundingBox().minY, e.getBoundingBox().maxY - e.getBoundingBox().minY, mc.player.getPosY() + mc.player.getEyeHeight());
        float z = (float) limit(e.getBoundingBox().minZ, e.getBoundingBox().maxZ - e.getBoundingBox().minZ, mc.player.getPosZ());

        return new Vector3d(
                e.getBoundingBox().minX + (e.getBoundingBox().maxX - e.getBoundingBox().minX) * x,
                e.getBoundingBox().minY + (e.getBoundingBox().maxY - e.getBoundingBox().minY) * y,
                e.getBoundingBox().minZ + (e.getBoundingBox().maxZ - e.getBoundingBox().minZ) * z
        );
    }

    private static double limit(double min, double max, double cur) {
        return Math.min(1, Math.max(0, (cur - min) / max));
    }
    public static Vector3d getVecTarget(LivingEntity target, double distance) {
        Vector3d vec = target.getPositionVec().add(new Vector3d(0, MathHelper.clamp(target.getEyeHeight() * (mc.player.getDistance(target) / (distance + target.getWidth())), mc.player.getEyeHeight() / 2, mc.player.getEyeHeight()), 0));
        if (!isHitBoxVisible(vec)) {
            for (double i = target.getWidth() * 0.05; i <= target.getWidth() * 0.95; i += target.getWidth() * 0.9 / 8f) {
                for (double j = target.getWidth() * 0.05; j <= target.getWidth() * 0.95; j += target.getWidth() * 0.9 / 8f) {
                    for (double k = 0; k <= target.getHeight(); k += target.getHeight() / 8f) {
                        if (isHitBoxVisible(new Vector3d(i, k, j).add(target.getPositionVec().add(new Vector3d(-target.getWidth() / 2, 0, -target.getWidth() / 2))))) {
                            vec = new Vector3d(i, k, j).add(target.getPositionVec().add(new Vector3d(-target.getWidth() / 2, 0, -target.getWidth() / 2)));
                            break;
                        }
                    }
                }
            }
        }
        return vec;
    }
    public static float calculateCorrectYawOffset(float yaw) {
        // Инициализация переменных
        double xDiff = mc.player.getPosX() - mc.player.prevPosX;
        double zDiff = mc.player.getPosZ() - mc.player.prevPosZ;
        float distSquared = (float) (xDiff * xDiff + zDiff * zDiff);
        float renderYawOffset = mc.player.renderYawOffset;
        float offset = renderYawOffset;
        float yawOffsetDiff;

        // Вычисление смещения, если расстояние больше порогового значения
        if (distSquared > 0.0025000002f) {
            offset = (float) MathHelper.atan2(zDiff, xDiff) * 180.0f / (float) Math.PI - 90.0f;
        }

        // Установка смещения равным углу поворота, если игрок машет рукой
        if (mc.player != null && mc.player.swingProgress > 0.0f) {
            offset = yaw;
        }

        // Ограничение разницы смещений
        yawOffsetDiff = MathHelper.wrapDegrees(yaw - (renderYawOffset + MathHelper.wrapDegrees(offset - renderYawOffset) * 0.3f));
        yawOffsetDiff = MathHelper.clamp(yawOffsetDiff, -75.0f, 75.0f);

        // Вычисление итогового смещения
        renderYawOffset = yaw - yawOffsetDiff;
        if (yawOffsetDiff * yawOffsetDiff > 2500.0f) {
            renderYawOffset += yawOffsetDiff * 0.2f;
        }

        return renderYawOffset;
    }
    private void setRotation(LivingEntity base, boolean attack) {
        this.rotatedBefore = true;

        final Vector3d targetPos = getVecTarget(base,
                1.5f +
                        range.current);

        final double playerX = mc.player.getPosX();
        final double playerY = mc.player.getPosY() + (double) mc.player.getEyeHeight();
        final double playerZ = mc.player.getPosZ();

        final double targetXOffset = targetPos.x - playerX;
        final double targetYOffset = targetPos.y - playerY;
        final double targetZOffset = targetPos.z - playerZ;
        final double targetHorizontalDistance = sqrt(targetXOffset, targetZOffset);

        float[] rotations = new float[]{
                (float) Math.toDegrees(Math.atan2(targetZOffset, targetXOffset)) - 90.0F,
                (float) (-Math.toDegrees(Math.atan2(targetYOffset, targetHorizontalDistance)))
        };

        final float yawDiff = (rotations[0] - this.rotation.x);
        final float pitchDiff = rotations[1] - this.rotation.y;
        final float wrappedYawDiff = (int) MathHelper.wrapDegrees(yawDiff);


        float finalYaw = upgradeClamp(wrappedYawDiff, -60.0F, 60.0F);

        final float finalPitch = upgradeClamp(pitchDiff, -35.F, 35.F);

        if (Math.abs(finalYaw - this.prevAdditionYaw) < 4.0f) {
            finalYaw = GCDFixUtility.getSensitivity(this.prevAdditionYaw + 4.5f);
        }

        rotations[0] = this.rotation.x + finalYaw;
        rotations[1] = upgradeClamp(this.rotation.y + finalPitch / 4.0F, -90.0F, 90.0F);

        float gcd = GCDFixUtility.getGCDValue();

        rotations[0] = (float) ((double) rotations[0] - (double) (rotations[0] - this.rotation.x) % gcd);
        rotations[1] = (float) ((double) rotations[1] - (double) (rotations[1] - rotation.y) % gcd);

        this.rotation.x = rotations[0];
        this.rotation.y = rotations[1];
        this.prevAdditionYaw = finalYaw;
    }
    public static float upgradeClamp(float def, float min, float max) {
        return Math.min(Math.max(def, min), max);
    }

    public static double sqrt(double var1, double var3) {
        return Math.sqrt(Math.pow(var1, 2.0) + Math.pow(var3, 2.0));
    }

}
