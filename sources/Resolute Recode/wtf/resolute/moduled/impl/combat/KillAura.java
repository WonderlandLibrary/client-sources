package wtf.resolute.moduled.impl.combat;

import com.google.common.eventbus.Subscribe;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.*;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.evented.interfaces.Listener;
import wtf.resolute.evented.interfaces.Render2DEvent;
import wtf.resolute.manage.friends.FriendStorage;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.ModeListSetting;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.moduled.settings.impl.SliderSetting;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.math.SensUtils;
import wtf.resolute.utiled.math.StopWatch;
import wtf.resolute.utiled.player.InventoryUtil;
import wtf.resolute.utiled.player.MouseUtil;
import wtf.resolute.utiled.player.MoveUtils;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;

import static com.mojang.blaze3d.platform.GlStateManager.GL_QUADS;
import static java.lang.Math.*;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR_TEX;
import static net.minecraft.util.math.MathHelper.clamp;
import static net.minecraft.util.math.MathHelper.wrapDegrees;
import static wtf.resolute.ResoluteInfo.startTime;

@ModuleAnontion(
        name = "Aura",
        type = Categories.Combat,
        server = ""
)
public class KillAura extends Module {
    @Getter
    private final ModeSetting type = new ModeSetting("Тип", "Плавная", "Плавная", "Резкая", "Новая", "Незаметная","FunTime");
    private final SliderSetting attackRange = new SliderSetting("Дистанция аттаки", 3.2f, 3f, 6f, 0.1f);
    private final SliderSetting rotateDistance = new SliderSetting("Дистанция ротации", 1f, 0f, 3f, 0.1f);
    private final BooleanSetting criticalHits = new BooleanSetting("Криты с места", false);
    final ModeListSetting targets = new ModeListSetting("Таргеты",
            new BooleanSetting("Игроки", true),
            new BooleanSetting("Голые", true),
            new BooleanSetting("Мобы", false),
            new BooleanSetting("Животные", false),
            new BooleanSetting("Друзья", false),
            new BooleanSetting("Голые невидимки", true),
            new BooleanSetting("Невидимки", true));

    @Getter
    final ModeListSetting options = new ModeListSetting("Опции",
            new BooleanSetting("Только Криты", true),
            new BooleanSetting("Криты с пробелом", true),
            new BooleanSetting("Ломать щит", true),
            new BooleanSetting("Отжимать щит", true),
            new BooleanSetting("Синхронизировать атаку с ТПС", false),
            new BooleanSetting("Фокусировать одну цель", true),
            new BooleanSetting("Ускорять ротацию при атаке", false),
            new BooleanSetting("Коррекция движения", true));
    final ModeSetting correctionType = new ModeSetting("Тип коррекции", "Незаметный", "Незаметный", "Сфокусированный");
    @Getter
    private final StopWatch stopWatch = new StopWatch();
    private Vector2f rotateVector = new Vector2f(0, 0);
    @Getter
    private LivingEntity target;
    private Entity selected;
    int ticks = 0;
    boolean isRotated;

    final AutoPotion autoPotion;

    public KillAura(AutoPotion autoPotion) {
        this.autoPotion = autoPotion;
        addSettings(type, attackRange, rotateDistance, targets, options, correctionType, criticalHits);
    }

    @Subscribe
    public void onInput(EventInput eventInput) {
        if (eventInput != null && options.getValueByName("Коррекция движения").get() && correctionType.is("Незаметный")) {
            MoveUtils.fixMovement(eventInput, rotateVector.x);
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (options.getValueByName("Фокусировать одну цель").get() && (target == null || !isValid(target)) || !options.getValueByName("Фокусировать одну цель").get()) {
            updateTarget();
        }
        if (target != null && !(autoPotion.isState() && autoPotion.isActive())) {
            isRotated = false;
            if (shouldPlayerFalling() && (stopWatch.hasTimeElapsed())) {
                updateAttack();
                ticks = 2;
            }
            if (type.is("Резкая")) {
                if (ticks > 0) {
                    updateRotation(true, 180, 90);
                    ticks--;
                } else {
                    reset();
                    ticks--;
                }
            } else {
                if (!isRotated) {
                    updateRotation(false, 80, 35);
                }
            }
            if (shouldPlayerFalling() && (stopWatch.hasTimeElapsed())) {
                updateAttack();
                ticks = 1;
            }
            if (type.is("Новая")) {
                if (ticks > 0) {
                    updateRotation(true, 180, 90);
                } else {
                    reset();
                }
            }
            if (type.is("Незаметная")) {
                if (ticks > 0) {
                    updateRotation(true, 70, 50);
                }
            }
        } else {
            stopWatch.setLastMS(0);
            reset();
        }
    }
    private void drawMarker(WorldEvent e) {
        if(target!=null) {
            MatrixStack ms = DisplayUtils.matrixFrom(e.getStack(), mc.gameRenderer.getActiveRenderInfo());
            ms.push();

            RenderSystem.pushMatrix();
            RenderSystem.disableLighting();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.shadeModel(7425);
            RenderSystem.disableCull();
            RenderSystem.disableAlphaTest();
            RenderSystem.blendFuncSeparate(770, 1, 0, 1);

            double x = target.getPosX();
            double y = target.getPosY() + target.getHeight() / 2f;
            double z = target.getPosZ();
            double radius = 0.7f;
//                targetAnim = AnimationMath.fast(targetAnim,50+(target.hurtTime/5),5);
            float speed = 30;
            float size = 0.3f;
            double distance = 20; //расстояние между кругами
            int lenght = 24;
            int maxAlpha = 255;
            int alphaFactor = 20;


//                MainUtil.sendMesage(RenderUtil.Render2D.getHurtPercent(target)+"");

            ActiveRenderInfo camera = mc.getRenderManager().info;

            ms.translate(-mc.getRenderManager().info.getProjectedView().getX(),
                    -mc.getRenderManager().info.getProjectedView().getY(),
                    -mc.getRenderManager().info.getProjectedView().getZ());

            Vector3d interpolated = MathUtil.interpolate(target.getPositionVec(), new Vector3d(target.lastTickPosX, target.lastTickPosY, target.lastTickPosZ), e.getPartialTicks());
            interpolated.y += 0.75f;

            ms.translate(interpolated.x+0.2f, interpolated.y + 0.5f, interpolated.z);
//                ms.translate(
//                        target.getPosX()-mc.player.getPosX(),y,z+0.7f);

            mc.getTextureManager().bindTexture(new ResourceLocation("resolute/images/circleglow.png"));

            //y1
            for (int i = 0; i < lenght; i++) {
                Quaternion r = camera.getRotation().copy();

                buffer.begin(GL_QUADS, POSITION_COLOR_TEX);



                double angle = 0.15f * (System.currentTimeMillis() - startTime - (i * distance)) / (speed); // Изменение скорости вращения
                double s = sin(angle)*radius;
                double c = cos(angle)*radius;

                ms.translate(s, (c), -c); // Смещение точки относительно центра

                ms.translate(-size/2f, -size/2f, 0);
                ms.rotate(r);
                ms.translate(size/2f, size/2f, 0);

                int color = ColorUtils.getColorStyle(i);
                int alpha = MathHelper.clamp(maxAlpha - (i * alphaFactor), 0, maxAlpha);
                buffer.pos(ms.getLast().getMatrix(), 0, -size, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(0, 0).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, -size, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(0, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, 0, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(1, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), 0, 0, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(1, 0).endVertex();

                tessellator.draw();

                ms.translate(-size/2f, -size/2f, 0);
                r.conjugate();
                ms.rotate(r);
                ms.translate(size/2f, size/2f, 0);

                ms.translate(-(s), -(c), (c)); // Смещение в обратную сторону
            }
            //y2
            for (int i = 0; i < lenght; i++) {
                Quaternion r = camera.getRotation().copy();

                buffer.begin(GL_QUADS, POSITION_COLOR_TEX);


                double angle = 0.15f * (System.currentTimeMillis() - startTime - (i * distance)) / (speed); // Изменение скорости вращения
                double s = sin(angle)*radius;
                double c = cos(angle)*radius;

                ms.translate(-s, s, -c); // Смещение точки относительно центра

                ms.translate(-size/2f, -size/2f, 0);
                ms.rotate(r);
                ms.translate(size/2f, size/2f, 0);


                int color = ColorUtils.getColorStyle(i);
                int alpha = MathHelper.clamp(maxAlpha - (i * alphaFactor), 0, maxAlpha);
                buffer.pos(ms.getLast().getMatrix(), 0, -size, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(0, 0).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, -size, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(0, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, 0, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(1, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), 0, 0, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(1, 0).endVertex();

                tessellator.draw();

                ms.translate(-size/2f, -size/2f, 0);
                r.conjugate();
                ms.rotate(r);
                ms.translate(size/2f, size/2f, 0);

                ms.translate((s), -(s), (c)); // Смещение в обратную сторону
            }
            //y3
            for (int i = 0; i < lenght; i++) {
                Quaternion r = camera.getRotation().copy();

                buffer.begin(GL_QUADS, POSITION_COLOR_TEX);

                double angle = 0.15f * (System.currentTimeMillis() - startTime - (i * distance)) / (speed); // Изменение скорости вращения
                double s = sin(angle)*radius;
                double c = cos(angle)*radius;

                ms.translate(c, c, s); // Смещение точки относительно центра

                ms.translate(-size/2f, -size/2f, 0);
                ms.rotate(r);
                ms.translate(size/2f, size/2f, 0);


                int color = ColorUtils.getColorStyle(i);
                int alpha = MathHelper.clamp(maxAlpha - (i * alphaFactor), 0, maxAlpha);
                buffer.pos(ms.getLast().getMatrix(), 0, -size, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(0, 0).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, -size, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(0, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), -size, 0, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(1, 1).endVertex();
                buffer.pos(ms.getLast().getMatrix(), 0, 0, 0).color(DisplayUtils.reAlphaInt(color,alpha)).tex(1, 0).endVertex();

                tessellator.draw();

                ms.translate(-size/2f, -size/2f, 0);
                r.conjugate();
                ms.rotate(r);
                ms.translate(size/2f, size/2f, 0);

                ms.translate(-(c), -(c), -(s)); // Смещение в обратную сторону
            }

            ms.translate(-x, -y, -z);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.enableAlphaTest();
            RenderSystem.depthMask(true);
            RenderSystem.popMatrix();
            ms.pop();
        }
    }
    public LivingEntity getTarget() {
        return target;
    }
    @Subscribe
    private void onWalking(EventMotion e) {
        if (type.is("Плавная")) {
            if (this.target != null && (!this.autoPotion.isState() || !this.autoPotion.isActive())) {
                float yaw = this.rotateVector.x;
                float pitch = this.rotateVector.y;
                float headRot = mc.player.prevRotationYawHead;
                float maxHeadRot = 40.0F;
                float bodRot = mc.player.prevRotationYaw;
                float bodYaw;
                if (abs(headRot - bodRot) > maxHeadRot) {
                    if (headRot > bodRot) {
                        bodYaw = headRot + ThreadLocalRandom.current().nextFloat(1.0F, 15.0F);
                    } else {
                        bodYaw = headRot - ThreadLocalRandom.current().nextFloat(1.0F, 15.0F);
                    }
                } else {
                    bodYaw = bodRot;
                }

                float newBodYaw = min(max(bodYaw, 0.0F), 10.0F);
                mc.player.renderYawOffset = bodYaw;
                mc.player.rotationYawHead = yaw;
                mc.player.rotationPitchHead = pitch;
                e.setYaw(yaw);
                e.setPitch(pitch);
            }
        }
        if (type.is("Резкая")) {
            if (this.target != null && (!this.autoPotion.isState() || !this.autoPotion.isActive())) {
                float yaw = this.rotateVector.x;
                float pitch = this.rotateVector.y;
                float headRot = mc.player.prevRotationYawHead;
                float maxHeadRot = 40.0F;
                float bodRot = mc.player.prevRotationYaw;
                float bodYaw;
                if (abs(headRot - bodRot) > maxHeadRot) {
                    if (headRot > bodRot) {
                        bodYaw = headRot + ThreadLocalRandom.current().nextFloat(1.0F, 15.0F);
                    } else {
                        bodYaw = headRot - ThreadLocalRandom.current().nextFloat(1.0F, 15.0F);
                    }
                } else {
                    bodYaw = bodRot;
                }

                float newBodYaw = min(max(bodYaw, 0.0F), 10.0F);
                mc.player.renderYawOffset = bodYaw;
                mc.player.rotationYawHead = yaw;
                mc.player.rotationPitchHead = pitch;
                e.setYaw(yaw);
                e.setPitch(pitch);
            }
        }
        if (type.is("Новая")) {
            if (this.target != null && (!this.autoPotion.isState() || !this.autoPotion.isActive())) {
                float yaw = this.rotateVector.x;
                float pitch = this.rotateVector.y;
                float headRot = mc.player.prevRotationYawHead;
                float maxHeadRot = 40.0F;
                float bodRot = mc.player.prevRotationYaw;
                float bodYaw;
                if (abs(headRot - bodRot) > maxHeadRot) {
                    if (headRot > bodRot) {
                        bodYaw = headRot + ThreadLocalRandom.current().nextFloat(1.0F, 15.0F);
                    } else {
                        bodYaw = headRot - ThreadLocalRandom.current().nextFloat(1.0F, 15.0F);
                    }
                } else {
                    bodYaw = bodRot;
                }

                float newBodYaw = min(max(bodYaw, 0.0F), 10.0F);
                mc.player.renderYawOffset = bodYaw;
                mc.player.rotationYawHead = yaw;
                mc.player.rotationPitchHead = pitch;
                e.setYaw(yaw);
                e.setPitch(pitch);
            }
        }
        if (type.is("Незаметная")) {
            if (this.target != null && (!this.autoPotion.isState() || !this.autoPotion.isActive())) {
                float yaw = this.rotateVector.x;
                float pitch = this.rotateVector.y;
                float headRot = mc.player.prevRotationYawHead;
                float maxHeadRot = 40.0F;
                float bodRot = mc.player.prevRotationYaw;
                float bodYaw;
                if (abs(headRot - bodRot) > maxHeadRot) {
                    if (headRot > bodRot) {
                        bodYaw = headRot + ThreadLocalRandom.current().nextFloat(1.0F, 15.0F);
                    } else {
                        bodYaw = headRot - ThreadLocalRandom.current().nextFloat(1.0F, 15.0F);
                    }
                } else {
                    bodYaw = bodRot;
                }

                float newBodYaw = min(max(bodYaw, 0.0F), 10.0F);
                mc.player.renderYawOffset = bodYaw;
                mc.player.rotationYawHead = yaw;
                mc.player.rotationPitchHead = pitch;
                e.setYaw(yaw);
                e.setPitch(pitch);
            }
        }
        if (type.is("FunTime")) {
            if (target == null || autoPotion.isState() && autoPotion.isActive()) return;

            float yaw = rotateVector.x;
            float pitch = rotateVector.y;

            e.setYaw(yaw);
            e.setPitch(pitch);
            mc.player.rotationYawHead = yaw;
            mc.player.renderYawOffset = yaw;
            mc.player.rotationPitchHead = pitch;
        }
    }

    private void updateTarget() {
        List<LivingEntity> targets = new ArrayList<>();

        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof LivingEntity living && isValid(living)) {
                targets.add(living);
            }
        }

        if (targets.isEmpty()) {
            target = null;
            return;
        }

        if (targets.size() == 1) {
            target = targets.get(0);
            return;
        }

        targets.sort(Comparator.comparingDouble(object -> {
            if (object instanceof PlayerEntity player) {
                return -getEntityArmor(player);
            }
            if (object instanceof LivingEntity base) {
                return -base.getTotalArmorValue();
            }
            return 0.0;
        }).thenComparing((object, object2) -> {
            double d2 = getEntityHealth((LivingEntity) object);
            double d3 = getEntityHealth((LivingEntity) object2);
            return Double.compare(d2, d3);
        }).thenComparing((object, object2) -> {
            double d2 = mc.player.getDistance((LivingEntity) object);
            double d3 = mc.player.getDistance((LivingEntity) object2);
            return Double.compare(d2, d3);
        }));

        target = targets.get(0);
    }

    float lastYaw, lastPitch;

    private void updateRotation(boolean attack, float rotationYawSpeed, float rotationPitchSpeed) {
        Vector3d vec = target.getPositionVec().add(0, clamp(mc.player.getPosYEye() - target.getPosY(),
                        0, target.getHeight() * (mc.player.getDistanceEyePos(target) / attackRange.get())), 0)
                .subtract(mc.player.getEyePosition(1));

        isRotated = true;

        float yawToTarget = (float) wrapDegrees(toDegrees(atan2(vec.z, vec.x)) - 90);
        float pitchToTarget = (float) (-toDegrees(atan2(vec.y, hypot(vec.x, vec.z))));

        float yawDelta = (wrapDegrees(yawToTarget - rotateVector.x));
        float pitchDelta = (wrapDegrees(pitchToTarget - rotateVector.y));
        int roundedYaw = (int) yawDelta;

        switch (type.get()) {
            case "Плавная" -> {
                float clampedYaw = min(max(abs(yawDelta), 1.0f), rotationYawSpeed);
                float clampedPitch = min(max(abs(pitchDelta), 1.0f), rotationPitchSpeed);


                if (abs(clampedYaw - this.lastYaw) <= 2.0f) {
                    clampedYaw = this.lastYaw + 2.1f;
                }

                float yaw = rotateVector.x + (yawDelta > 0 ? clampedYaw : -clampedYaw);
                float pitch = clamp(rotateVector.y + (pitchDelta > 0 ? clampedPitch : -clampedPitch), -89.0F, 89.0F);


                float gcd = SensUtils.getGCDValue();
                yaw -= (yaw - rotateVector.x) % gcd;
                pitch -= (pitch - rotateVector.y) % gcd;


                rotateVector = new Vector2f(yaw, pitch);
                lastYaw = clampedYaw;
                lastPitch = clampedPitch;
                if (options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
            }
            case "Резкая" -> {
                float yaw = rotateVector.x + roundedYaw;
                float pitch = clamp(rotateVector.y + pitchDelta, -90, 90);

                float gcd = SensUtils.getGCDValue();
                yaw -= (yaw - rotateVector.x) % gcd;
                pitch -= (pitch - rotateVector.y) % gcd;

                rotateVector = new Vector2f(yaw, pitch);

                if (options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
            }
            case "Новая" -> {
                float yaw = rotateVector.x + roundedYaw;
                float pitch = clamp(rotateVector.y + pitchDelta, -90, 90);

                float gcd = SensUtils.getGCDValue();
                yaw -= (yaw - rotateVector.x) % gcd;
                pitch -= (pitch - rotateVector.y) % gcd;

                rotateVector = new Vector2f(yaw, pitch);

                if (options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
            }
            case "FunTime" -> {
                float clampedYaw = min(max(abs(yawDelta), 1.0f), rotationYawSpeed);
                float clampedPitch = min(max(abs(pitchDelta), 1.0f), rotationPitchSpeed);

                if (attack && selected != target && options.getValueByName("Ускорять ротацию при атаке").get()) {
                    clampedPitch = max(abs(pitchDelta), 1.0f);
                } else {
                    clampedPitch /= 3f;
                }


                if (abs(clampedYaw - this.lastYaw) <= 3.0f) {
                    clampedYaw = this.lastYaw + 3.1f;
                }

                float yaw = rotateVector.x + (yawDelta > 0 ? clampedYaw : -clampedYaw);
                float pitch = clamp(rotateVector.y + (pitchDelta > 0 ? clampedPitch : -clampedPitch), -89.0F, 89.0F);


                float gcd = SensUtils.getGCDValue();
                yaw -= (yaw - rotateVector.x) % gcd;
                pitch -= (pitch - rotateVector.y) % gcd;


                rotateVector = new Vector2f(yaw, pitch);
                lastYaw = clampedYaw;
                lastPitch = clampedPitch;
                if (options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
            }
            case "Незаметная" -> {
                float gcd = SensUtils.getGCDValue();
                // Объявляем переменные для хранения текущих углов поворота
                float currentPitch = 0.0f;
                float currentYaw = 0.0f;

                // Проверяем, если игрок находится на расстоянии не более 0.6 от земли
                if (mc.player.fallDistance <= 0.6) {
                    // Добавляем случайное отклонение к yaw
                    float yawRandomness = (float) (random() * 14 - 7); // Генерируем случайное значение от -7 до 7
                    float yaw = rotateVector.x + roundedYaw + yawRandomness;

                    // Добавляем случайное отклонение к pitch
                    float pitchRandomness = (float) (random() * 2 - 5); // Генерируем случайное значение от -1 до 1
                    float pitch = clamp(rotateVector.y + pitchDelta + pitchRandomness, -90, 90);
                    yaw -= (yaw - rotateVector.x) % gcd;
                    pitch -= (pitch - rotateVector.y) % gcd;
                    rotateVector = new Vector2f(yaw, pitch);

                    // Устанавливаем углы поворота камеры игрока
                    mc.player.rotationYaw = rotateVector.x;
                    mc.player.rotationPitch = rotateVector.y;
                } else {
                    // Если цель не найдена, пропускаем изменение угла поворота
                    return;
                }
            }

        }
    }


    private void updateAttack() {
        if (type.is("Плавная")) {
            selected = MouseUtil.getMouseOver(target, rotateVector.x, rotateVector.y, attackRange.get());
            if (mc.player.getHealth() < 12 && autoPotion.isActive()) {
                return;
            }
            if ((selected == null || selected != target) && !mc.player.isElytraFlying()) {
                return;
            }

            if (mc.player.isBlocking() && options.getValueByName("Отжимать щит").get()) {
                mc.playerController.onStoppedUsingItem(mc.player);
            }

            stopWatch.setLastMS(50);
            // Проверяем тип критических ударов

            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(Hand.MAIN_HAND);

            if (target instanceof PlayerEntity player && options.getValueByName("Ломать щит").get()) {
                breakShieldPlayer(player);
            }
        }
        if (type.is("Резкая")) {
            selected = MouseUtil.getMouseOver(target, rotateVector.x, rotateVector.y, attackRange.get());
            if (mc.player.getHealth() < 12 && autoPotion.isActive()) {
                return;
            }
            if ((selected == null || selected != target) && !mc.player.isElytraFlying()) {
                return;
            }

            if (mc.player.isBlocking() && options.getValueByName("Отжимать щит").get()) {
                mc.playerController.onStoppedUsingItem(mc.player);
            }

            stopWatch.setLastMS(50);
            // Проверяем тип критических ударов

            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(Hand.MAIN_HAND);

            if (target instanceof PlayerEntity player && options.getValueByName("Ломать щит").get()) {
                breakShieldPlayer(player);
            }
        }
        if (type.is("Новая")) {
            selected = MouseUtil.getMouseOver(target, rotateVector.x, rotateVector.y, attackRange.get());
            if (mc.player.getHealth() < 12 && autoPotion.isActive()) {
                return;
            }
            if ((selected == null || selected != target) && !mc.player.isElytraFlying()) {
                return;
            }

            if (mc.player.isBlocking() && options.getValueByName("Отжимать щит").get()) {
                mc.playerController.onStoppedUsingItem(mc.player);
            }

            stopWatch.setLastMS(50);
            // Проверяем тип критических ударов

            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(Hand.MAIN_HAND);

            if (target instanceof PlayerEntity player && options.getValueByName("Ломать щит").get()) {
                breakShieldPlayer(player);
            }
        }
        if (type.is("Незаметная")) {
            selected = MouseUtil.getMouseOver(target, rotateVector.x, rotateVector.y, attackRange.get());
            if (mc.player.getHealth() < 12 && autoPotion.isActive()) {
                return;
            }
            if ((selected == null || selected != target) && !mc.player.isElytraFlying()) {
                return;
            }

            if (mc.player.isBlocking() && options.getValueByName("Отжимать щит").get()) {
                mc.playerController.onStoppedUsingItem(mc.player);
            }

            stopWatch.setLastMS(50);
            // Проверяем тип критических ударов

            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(Hand.MAIN_HAND);

            if (target instanceof PlayerEntity player && options.getValueByName("Ломать щит").get()) {
                breakShieldPlayer(player);
            }
        }
        if (type.is("FunTime")) {
            selected = MouseUtil.getMouseOver(target, rotateVector.x, rotateVector.y, attackRange.get());

            if (options.getValueByName("Ускорять ротацию при атаке").get()) {
                updateRotation(true, 60, 35);
            }

            if ((selected == null || selected != target) && !mc.player.isElytraFlying()) {
                return;
            }

            if (mc.player.isBlocking() && options.getValueByName("Отжимать щит").get()) {
                mc.playerController.onStoppedUsingItem(mc.player);
            }

            stopWatch.setLastMS(500);
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(Hand.MAIN_HAND);

            if (target instanceof PlayerEntity player && options.getValueByName("Ломать щит").get()) {
                breakShieldPlayer(player);
            }
        }
    }
    private boolean shouldPerformCriticalHit() {
        // Проверяем, выполняется ли условие для критического удара с места
        return !mc.player.isOnGround() && !mc.player.isInWater() && !mc.player.isInLava() &&
                !mc.player.isElytraFlying() && !mc.player.isOnLadder() && !mc.player.isPassenger() &&
                !mc.player.abilities.isFlying;
    }

    private boolean shouldPlayerFalling() {
        boolean cancelReason = mc.player.isInWater() && mc.player.areEyesInFluid(FluidTags.WATER) || mc.player.isInLava() || mc.player.isOnLadder() || mc.player.isPassenger() || mc.player.abilities.isFlying;
        boolean onSpace = mc.player.isOnGround() && !mc.gameSettings.keyBindJump.isKeyDown();

        float attackStrength = mc.player.getCooledAttackStrength(options.getValueByName("Синхронизировать атаку с ТПС").get()
                ? ResoluteInfo.getInstance().getTpsCalc().getAdjustTicks() : 1.5f);


        if (attackStrength < 0.92f) {
            return false;
        }

        if (!cancelReason && options.getValueByName("Криты с пробелом").get()) {
            return onSpace || !mc.player.isOnGround() && mc.player.fallDistance > 0;
        }
        if (!cancelReason && options.getValueByName("Только Криты").get()) {
            return !mc.player.isOnGround() && mc.player.fallDistance > 0;
        }


        return true;
    }

    private boolean isValid(LivingEntity entity) {
        if (entity instanceof ClientPlayerEntity) return false;

        if (entity.ticksExisted < 3) return false;
        if (mc.player.getDistanceEyePos(entity) > (double)attackRange.get() + (double)rotateDistance.get()) return false;;

        if (entity instanceof PlayerEntity p) {
            if (AntiBot.isBot(entity)) {
                return false;
            }
            if (!targets.getValueByName("Друзья").get() && FriendStorage.isFriend(p.getName().getString())) {
                return false;
            }
            if (p.getName().getString().equalsIgnoreCase(mc.player.getName().getString())) return false;
        }

        if (entity instanceof PlayerEntity && !targets.getValueByName("Игроки").get()) {
            return false;
        }
        if (entity instanceof PlayerEntity && entity.getTotalArmorValue() == 0 && !targets.getValueByName("Голые").get()) {
            return false;
        }
        if (entity instanceof PlayerEntity && entity.isInvisible() && entity.getTotalArmorValue() == 0 && !targets.getValueByName("Голые невидимки").get()) {
            return false;
        }
        if (entity instanceof PlayerEntity && entity.isInvisible() && !targets.getValueByName("Невидимки").get()) {
            return false;
        }

        if (entity instanceof MonsterEntity && !targets.getValueByName("Мобы").get()) {
            return false;
        }
        if (entity instanceof AnimalEntity && !targets.getValueByName("Животные").get()) {
            return false;
        }

        return !entity.isInvulnerable() && entity.isAlive() && !(entity instanceof ArmorStandEntity);
    }
    /*
    private double getDistance(LivingEntity entity) {
        return RayTraceUtils.getVector(entity).length();
    }
     */

    private void breakShieldPlayer(PlayerEntity entity) {
        if (entity.isBlocking()) {
            int invSlot = InventoryUtil.getInstance().getAxeInInventory(false);
            int hotBarSlot = InventoryUtil.getInstance().getAxeInInventory(true);

            if (hotBarSlot == -1 && invSlot != -1) {
                int bestSlot = InventoryUtil.getInstance().findBestSlotInHotBar();
                mc.playerController.windowClick(0, invSlot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, bestSlot + 36, 0, ClickType.PICKUP, mc.player);

                mc.player.connection.sendPacket(new CHeldItemChangePacket(bestSlot));
                mc.playerController.attackEntity(mc.player, entity);
                mc.player.swingArm(Hand.MAIN_HAND);
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));

                mc.playerController.windowClick(0, bestSlot + 36, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, invSlot, 0, ClickType.PICKUP, mc.player);
            }

            if (hotBarSlot != -1) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(hotBarSlot));
                mc.playerController.attackEntity(mc.player, entity);
                mc.player.swingArm(Hand.MAIN_HAND);
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
            }
        }
    }


    private void reset() {
        if (options.getValueByName("Коррекция движения").get()) {
            mc.player.rotationYawOffset = Integer.MIN_VALUE;
        }
        rotateVector = new Vector2f(mc.player.rotationYaw, mc.player.rotationPitch);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        reset();
        target = null;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        reset();
        stopWatch.setLastMS(0);
        target = null;
    }

    private double getEntityArmor(PlayerEntity entityPlayer2) {
        double d2 = 0.0;
        for (int i2 = 0; i2 < 4; ++i2) {
            ItemStack is = entityPlayer2.inventory.armorInventory.get(i2);
            if (!(is.getItem() instanceof ArmorItem)) continue;
            d2 += getProtectionLvl(is);
        }
        return d2;
    }

    private double getProtectionLvl(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem i) {
            double damageReduceAmount = i.getDamageReduceAmount();
            if (stack.isEnchanted()) {
                damageReduceAmount += (double) EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack) * 0.25;
            }
            return damageReduceAmount;
        }
        return 0;
    }

    private double getEntityHealth(LivingEntity ent) {
        if (ent instanceof PlayerEntity player) {
            return (double) (player.getHealth() + player.getAbsorptionAmount()) * (getEntityArmor(player) / 20.0);
        }
        return ent.getHealth() + ent.getAbsorptionAmount();
    }
}