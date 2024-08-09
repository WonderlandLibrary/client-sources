package fun.ellant.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.player.ClientPlayerEntity;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.optifine.CustomColors;
import fun.ellant.Ellant;
import fun.ellant.command.friends.FriendStorage;
import fun.ellant.events.EventInput;
import fun.ellant.events.EventMotion;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.impl.player.ElytraTarget;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeListSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.math.SensUtils;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.InventoryUtil;
import fun.ellant.utils.player.MouseUtil;
import fun.ellant.utils.player.MoveUtils;

@FunctionRegister(
        name = "Aura",
        type = Category.COMBAT, desc = "Автоматически бьёт существ"
)
public class KillAura extends Function {
    private final ModeSetting type = new ModeSetting("Тип", "Плавная", new String[]{"Плавная", "Резкая", "FTnew", "HW"});
    private final SliderSetting attackRange = new SliderSetting("Дистанция аттаки", 3.0F, 3.0F, 6.0F, 0.1F);
    final ModeListSetting targets = new ModeListSetting("Таргеты", new BooleanSetting[]{new BooleanSetting("Игроки", true), new BooleanSetting("Голые", true), new BooleanSetting("Мобы", false), new BooleanSetting("Животные", false), new BooleanSetting("Друзья", false), new BooleanSetting("Голые невидимки", true), new BooleanSetting("Невидимки", true)});
    final ModeListSetting options = new ModeListSetting("Опции", new BooleanSetting[]{new BooleanSetting("Только криты", true), new BooleanSetting("Ломать щит", true), new BooleanSetting("Отжимать щит", true), new BooleanSetting("Ускорять ротацию при атаке", false), new BooleanSetting("Синхронизировать атаку с ТПС", false), new BooleanSetting("Фокусировать одну цель", true), new BooleanSetting("Коррекция движения", true), new BooleanSetting("ElytraTarget", false),  new BooleanSetting("Бить через стены", true)});
    final ModeSetting correctionType = new ModeSetting("Тип коррекции", "Незаметный", new String[]{"Незаметный", "Сфокусированный", "ElytraTarget"});
    private final StopWatch stopWatch = new StopWatch();
    final ElytraTarget elytraTarget = new ElytraTarget();
    private Vector2f rotateVector = new Vector2f(0.0F, 0.0F);
    private LivingEntity target;
    private Entity selected;
    int ticks = 0;
    boolean isRotated;
    final AutoPotion autoPotion;
    float lastYaw;
    float lastPitch;

    public KillAura(AutoPotion autoPotion) {
        this.autoPotion = autoPotion;
        this.addSettings(new Setting[]{this.type, this.attackRange, this.targets, this.options, this.correctionType});
    }

    @Subscribe
    public void onInput(EventInput eventInput) {
        if ((Boolean)this.options.getValueByName("Коррекция движения").get() && this.correctionType.is("Незаметная") && this.target != null && mc.player != null) {
            MoveUtils.fixMovement(eventInput, this.rotateVector.x);
        }

        if ((Boolean)this.options.getValueByName("ElytraTarget").get()) {
            this.elytraTarget.onEnable();
        }

    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if ((Boolean)this.options.getValueByName("Фокусировать одну цель").get() && (this.target == null || !this.isValid(this.target)) || !(Boolean)this.options.getValueByName("Фокусировать одну цель").get()) {
            this.updateTarget();
        }

        if (this.target == null || this.autoPotion.isState() && this.autoPotion.isActive()) {
            this.stopWatch.setLastMS(0L);
            this.reset();
        } else {
            this.isRotated = false;
            int randomNumber = CustomColors.random.nextInt();
            int ss = CustomColors.random.nextInt(1, 3);
            if (this.shouldPlayerFalling() && this.stopWatch.hasTimeElapsed()) {
                this.updateAttack();
                this.ticks = ss;
            }

            if (this.type.is("Резкая")) {
                if (this.ticks > 0) {
                    this.updateRotation(true, 180.0F, 90.0F);
                    --this.ticks;
                } else {
                    this.reset();
                }
            } else if (!this.isRotated) {
                this.updateRotation(false, 80.0F, 35.0F);
            }
        }

        if (this.target != null && (!this.autoPotion.isState() || !this.autoPotion.isActive())) {
            this.isRotated = false;
            if (this.shouldPlayerFalling() && this.stopWatch.hasTimeElapsed()) {
                this.updateAttack();
                this.ticks = 255;
            }

            if (this.type.is("AuraNews")) {
                if (this.ticks > 0) {
                    this.updateRotation(true, 180.0F, 90.0F);
                    --this.ticks;
                } else {
                    this.reset();
                }
            } else if (!this.isRotated) {
                this.updateRotation(false, 80.0F, 35.0F);
            }
        } else {
            this.stopWatch.setLastMS(0L);
            this.reset();
        }

    }

    @Subscribe
    private void onWalking(EventMotion e) {
        if (this.target != null && (!this.autoPotion.isState() || !this.autoPotion.isActive())) {
            float yaw = this.rotateVector.x;
            float pitch = this.rotateVector.y;
            e.setYaw(yaw);
            e.setPitch(pitch);
            mc.player.rotationYawHead = yaw;
            mc.player.renderYawOffset = yaw;
            mc.player.rotationPitchHead = pitch;
        }
    }

    private void updateTarget() {
        List<LivingEntity> targets = new ArrayList();
        Iterator var2 = mc.world.getAllEntities().iterator();

        while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity)entity;
                if (this.isValid(living)) {
                    targets.add(living);
                }
            }
        }

        if (targets.isEmpty()) {
            this.target = null;
        } else if (targets.size() == 1) {
            this.target = (LivingEntity)targets.get(0);
        } else {
            targets.sort(Comparator.comparingDouble((object) -> {
                if (object instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity)object;
                    return -this.getEntityArmor(player);
                } else if (object instanceof LivingEntity) {
                    LivingEntity base = (LivingEntity)object;
                    return (double)(-base.getTotalArmorValue());
                } else {
                    return 0.0D;
                }
            }).thenComparing((object, object2) -> {
                double d2 = this.getEntityHealth((LivingEntity)object);
                double d3 = this.getEntityHealth((LivingEntity)object2);
                return Double.compare(d2, d3);
            }).thenComparing((object, object2) -> {
                double d2 = (double)mc.player.getDistance((LivingEntity)object);
                double d3 = (double)mc.player.getDistance((LivingEntity)object2);
                return Double.compare(d2, d3);
            }));
            this.target = (LivingEntity)targets.get(0);
        }
    }

    private void updateRotation(boolean attack, float rotationYawSpeed, float rotationPitchSpeed) {
        Vector3d vec = this.target.getPositionVec().add(0.0D, MathHelper.clamp(mc.player.getPosYEye() - this.target.getPosY(), 0.0D, (double)this.target.getHeight() * (mc.player.getDistanceEyePos(this.target) / (double)(Float)this.attackRange.get())), 0.0D).subtract(mc.player.getEyePosition(1.0F));
        this.isRotated = true;
        float yawToTarget = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90.0D);
        float pitchToTarget = (float)(-Math.toDegrees(Math.atan2(vec.y, Math.hypot(vec.x, vec.z))));
        float yawDelta = MathHelper.wrapDegrees(yawToTarget - this.rotateVector.x);
        float pitchDelta = MathHelper.wrapDegrees(pitchToTarget - this.rotateVector.y);
        int roundedYaw = (int)yawDelta;
        String var10 = (String)this.type.get();
        byte var11 = -1;
        switch(var10.hashCode()) {
            case 2319:
                if (var10.equals("HW")) {
                    var11 = 4;
                }
                break;
            case 67257874:
                if (var10.equals("FTnew")) {
                    var11 = 5;
                }
                break;

            case 1195463127:
                if (var10.equals("Резкая")) {
                    var11 = 2;
                }
                break;

            case 1977199454:
                if (var10.equals("Плавная")) {
                    var11 = 0;
                }
        }

        float clampedYaw;
        float clampedPitch;
        float yaw;
        float pitch;
        float gcd;
        switch(var11) {
            case 0:
                clampedYaw = Math.min(Math.max(Math.abs(yawDelta), 1.0F), rotationYawSpeed);
                clampedPitch = Math.min(Math.max(Math.abs(pitchDelta), 1.0F), rotationPitchSpeed);
                if (attack && this.selected != this.target && (Boolean)this.options.getValueByName("Ускорять ротацию при атаке").get()) {
                    clampedPitch = Math.max(Math.abs(pitchDelta), 1.0F);
                } else {
                    clampedPitch /= 3.0F;
                }

                if (Math.abs(clampedYaw - this.lastYaw) <= 3.0F) {
                    clampedYaw = this.lastYaw + 3.1F;
                }

                yaw = this.rotateVector.x + (yawDelta > 0.0F ? clampedYaw : -clampedYaw);
                pitch = MathHelper.clamp(this.rotateVector.y + (pitchDelta > 0.0F ? clampedPitch : -clampedPitch), -89.0F, 89.0F);
                gcd = SensUtils.getGCDValue();
                yaw -= (yaw - this.rotateVector.x) % gcd;
                pitch -= (pitch - this.rotateVector.y) % gcd;
                this.rotateVector = new Vector2f(yaw, pitch);
                this.lastYaw = clampedYaw;
                this.lastPitch = clampedPitch;
                if ((Boolean)this.options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
                break;
            case 1:
                clampedYaw = Math.min(Math.max(Math.abs(yawDelta), 1.0F), rotationYawSpeed);
                clampedPitch = Math.min(Math.max(Math.abs(pitchDelta), 1.0F), rotationPitchSpeed);
                if (attack && this.selected != this.target && (Boolean)this.options.getValueByName("Ускорять ротацию при атаке").get()) {
                    clampedPitch = Math.max(Math.abs(pitchDelta), 1.0F);
                } else {
                    clampedPitch /= 3.0F;
                }

                ++this.ticks;
                if (this.ticks % 3 == 0 && !mc.player.isOnGround()) {
                    mc.gameSettings.keyBindSneak.setPressed(false);
                }

                if (this.ticks % 6 == 0 && !mc.player.isOnGround()) {
                    mc.gameSettings.keyBindSneak.setPressed(true);
                }

                if (Math.abs(clampedYaw - this.lastYaw) <= 3.0F) {
                    clampedYaw = this.lastYaw + 3.1F;
                }

                yaw = this.rotateVector.x + (yawDelta > 0.0F ? clampedYaw : -clampedYaw);
                pitch = MathHelper.clamp(this.rotateVector.y + (pitchDelta > 0.0F ? clampedPitch : -clampedPitch), -89.0F, 89.0F);
                gcd = SensUtils.getGCDValue();
                yaw -= (yaw - this.rotateVector.x) % gcd;
                pitch -= (pitch - this.rotateVector.y) % gcd;
                this.rotateVector = new Vector2f(yaw, pitch);
                this.lastYaw = clampedYaw;
                this.lastPitch = clampedPitch;
                if ((Boolean)this.options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
                break;
            case 2:
                clampedYaw = this.rotateVector.x + (float)roundedYaw;
                clampedPitch = MathHelper.clamp(this.rotateVector.y + pitchDelta, -90.0F, 90.0F);
                yaw = SensUtils.getGCDValue();
                clampedYaw -= (clampedYaw - this.rotateVector.x) % yaw;
                clampedPitch -= (clampedPitch - this.rotateVector.y) % yaw;
                this.rotateVector = new Vector2f(clampedYaw, clampedPitch);
                if ((Boolean)this.options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = clampedYaw;
                }
                break;
            case 3:
                clampedYaw = this.rotateVector.x + (float)roundedYaw;
                clampedPitch = MathHelper.clamp(this.rotateVector.y + pitchDelta, -90.0F, 90.0F);
                yaw = SensUtils.getGCDValue();
                clampedYaw -= (clampedYaw - this.rotateVector.x) % yaw;
                clampedPitch -= (clampedPitch - this.rotateVector.y) % yaw;
                this.rotateVector = new Vector2f(clampedYaw, clampedPitch);
                if ((Boolean)this.options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = clampedYaw;
                }
                break;
            case 4:
                clampedYaw = Math.min(Math.max(Math.abs(yawDelta), 1.0F), rotationYawSpeed);
                clampedPitch = Math.min(Math.max(Math.abs(pitchDelta), 1.0F), rotationPitchSpeed);
                if (attack && this.selected != this.target && (Boolean)this.options.getValueByName("Ускорять ротацию при атаке").get()) {
                    clampedPitch = Math.max(Math.abs(pitchDelta), 1.0F);
                } else {
                    clampedPitch /= 3.0F;
                }

                if (Math.abs(clampedYaw - this.lastYaw) <= 3.0F) {
                    clampedYaw = this.lastYaw + 3.1F;
                }

                yaw = this.rotateVector.x + (yawDelta > 0.0F ? clampedYaw : -clampedYaw);
                pitch = MathHelper.clamp(this.rotateVector.y + (pitchDelta > 0.0F ? clampedPitch : -clampedPitch), -89.0F, 89.0F);
                gcd = SensUtils.getGCDValue();
                yaw -= (yaw - this.rotateVector.x) % gcd;
                pitch -= (pitch - this.rotateVector.y) % gcd;
                this.rotateVector = new Vector2f(yaw, pitch);
                this.lastYaw = clampedYaw;
                this.lastPitch = clampedPitch;
                if ((Boolean) this.options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
                break;
            case 5:
                pitch = Math.min(Math.max(Math.abs(yawDelta), 1.4F), rotationYawSpeed);
                gcd = Math.min(Math.max(Math.abs(pitchDelta), 1.2F), rotationPitchSpeed);
                float horizontalRandom1 = (float)(Math.random() * 6.0D - 3.0D);
                float verticalRandom1 = (float)(Math.random() * 0.85D - 0.425D);
                if (attack && this.selected != this.target && (Boolean)this.options.getValueByName("Ускорять ротацию").get()) {
                    gcd = Math.max(Math.abs(pitchDelta), 1.0F);
                } else {
                    gcd /= 3.0F;
                }

                float interpolatedYaw1 = this.rotateVector.x + (yawDelta > 0.0F ? pitch : -pitch);
                float interpolatedPitch1 = MathHelper.clamp(this.rotateVector.y + (pitchDelta > 0.0F ? gcd : -gcd), -89.0F, 89.0F);
                clampedYaw = interpolatedYaw1 + horizontalRandom1;
                clampedPitch = interpolatedPitch1 + verticalRandom1;
                yaw = SensUtils.getGCDValue();
                clampedYaw -= (clampedYaw - this.rotateVector.x) % yaw;
                clampedPitch -= (clampedPitch - this.rotateVector.y) % yaw;
                this.rotateVector = new Vector2f(clampedYaw, clampedPitch);
                this.lastYaw = pitch;
                this.lastPitch = gcd;
                if ((Boolean)this.options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = clampedYaw;
                }
                break;
            case 6:
                clampedYaw = Math.min(Math.max(Math.abs(yawDelta), 1.0F), rotationYawSpeed);
                clampedPitch = Math.min(Math.max(Math.abs(pitchDelta), 0.1F), rotationPitchSpeed);
                if (attack && this.selected != this.target && (Boolean)this.options.getValueByName("Ускорять ротацию при атаке").get()) {
                    clampedPitch = Math.max(Math.abs(pitchDelta), 0.1F);
                } else {
                    clampedPitch = (float)((double)clampedPitch / 0.6D);
                }

                if (Math.abs(clampedYaw - this.lastYaw) <= 0.2F) {
                    clampedYaw = this.lastYaw + 0.1F;
                }

                yaw = this.rotateVector.x + (yawDelta > 0.0F ? clampedYaw : -clampedYaw);
                pitch = MathHelper.clamp(this.rotateVector.y + (pitchDelta > 0.0F ? clampedPitch : -clampedPitch), -32.0F, 32.0F);
                gcd = SensUtils.getGCDValue();
                yaw -= (yaw - this.rotateVector.x) % gcd;
                pitch -= (pitch - this.rotateVector.y) % gcd;
                this.rotateVector = new Vector2f(yaw, pitch);
                this.lastYaw = clampedYaw;
                this.lastPitch = clampedPitch;
                if ((Boolean)this.options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
        }

    }

    private void updateAttack() {
        this.selected = MouseUtil.getMouseOver(this.target, this.rotateVector.x, this.rotateVector.y, (double)(Float)this.attackRange.get());
        if ((Boolean)this.options.getValueByName("Ускорять ротацию при атаке").get()) {
            this.updateRotation(true, 60.0F, 35.0F);
        }

        if (this.selected != null && this.selected == this.target || mc.player.isElytraFlying()) {
            if ((Boolean)this.options.getValueByName("Бить через стены").get() || this.isPathClear(this.target)) {
                if (mc.player.isBlocking() && (Boolean)this.options.getValueByName("Отжимать щит").get()) {
                    mc.playerController.onStoppedUsingItem(mc.player);
                }

                this.stopWatch.setLastMS(500L);
                mc.playerController.attackEntity(mc.player, this.target);
                mc.player.swingArm(Hand.MAIN_HAND);
                LivingEntity var2 = this.target;
                if (var2 instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity)var2;
                    if ((Boolean)this.options.getValueByName("Ломать щит").get()) {
                        this.breakShieldPlayer(player);
                    }
                }

            }
        }
    }

    private boolean shouldPlayerFalling() {
        boolean cancelReason = mc.player.isInWater() && mc.player.areEyesInFluid(FluidTags.WATER) || mc.player.isInLava() || mc.player.isOnLadder() || mc.player.isPassenger() || mc.player.abilities.isFlying;
        float attackStrength = mc.player.getCooledAttackStrength((Boolean)this.options.getValueByName("Синхронизировать атаку с ТПС").get() ? Ellant.getInstance().getTpsCalc().getAdjustTicks() : 1.5F);
        if (attackStrength < 0.92F) {
            return false;
        } else if (!cancelReason && (Boolean)this.options.getValueByName("Только криты").get()) {
            return !mc.player.isOnGround() && mc.player.fallDistance > 0.0F;
        } else {
            return true;
        }
    }

    private boolean isValid(LivingEntity entity) {
        if (entity instanceof ClientPlayerEntity) {
            return false;
        } else if (entity.ticksExisted < 3) {
            return false;
        } else if (mc.player.getDistanceEyePos(entity) > (double)(Float)this.attackRange.get()) {
            return false;
        } else {
            if (entity instanceof PlayerEntity) {
                PlayerEntity p = (PlayerEntity)entity;
                if (AntiBot.isBot(entity)) {
                    return false;
                }

                if (!(Boolean)this.targets.getValueByName("Друзья").get() && FriendStorage.isFriend(p.getName().getString())) {
                    return false;
                }

                if (p.getName().getString().equalsIgnoreCase(mc.player.getName().getString())) {
                    return false;
                }
            }

            if (entity instanceof PlayerEntity && !(Boolean)this.targets.getValueByName("Игроки").get()) {
                return false;
            } else if (entity instanceof PlayerEntity && entity.getTotalArmorValue() == 0 && !(Boolean)this.targets.getValueByName("Голые").get()) {
                return false;
            } else if (entity instanceof PlayerEntity && entity.isInvisible() && entity.getTotalArmorValue() == 0 && !(Boolean)this.targets.getValueByName("Голые невидимки").get()) {
                return false;
            } else if (entity instanceof PlayerEntity && entity.isInvisible() && !(Boolean)this.targets.getValueByName("Невидимки").get()) {
                return false;
            } else if (entity instanceof MonsterEntity && !(Boolean)this.targets.getValueByName("Мобы").get()) {
                return false;
            } else if (entity instanceof AnimalEntity && !(Boolean)this.targets.getValueByName("Животные").get()) {
                return false;
            } else {
                return !entity.isInvulnerable() && entity.isAlive() && !(entity instanceof ArmorStandEntity);
            }
        }
    }

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

    private boolean isPathClear(LivingEntity entity) {
        Vector3d startPos = mc.player.getPositionVec().add(0.0D, (double)mc.player.getEyeHeight(), 0.0D);
        Vector3d endPos = entity.getPositionVec().add(0.0D, (double)entity.getEyeHeight(), 0.0D);
        return mc.world.rayTraceBlocks(new RayTraceContext(startPos, endPos, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, mc.player)).getType() == RayTraceResult.Type.MISS;
    }
    private void reset() {
        if ((Boolean)this.options.getValueByName("Коррекция движения").get()) {
            mc.player.rotationYawOffset = -2.14748365E9F;
        }

        this.rotateVector = new Vector2f(mc.player.rotationYaw, mc.player.rotationPitch);
    }

    public boolean onEnable() {
        super.onEnable();
        this.reset();
        this.target = null;
        return false;
    }

    public void onDisable() {
        super.onDisable();
        this.reset();
        this.stopWatch.setLastMS(0L);
        this.target = null;
    }

    private double getEntityArmor(PlayerEntity entityPlayer2) {
        double d2 = 0.0D;

        for(int i2 = 0; i2 < 4; ++i2) {
            ItemStack is = (ItemStack)entityPlayer2.inventory.armorInventory.get(i2);
            if (is.getItem() instanceof ArmorItem) {
                d2 += this.getProtectionLvl(is);
            }
        }

        return d2;
    }

    private double getProtectionLvl(ItemStack stack) {
        Item var3 = stack.getItem();
        if (var3 instanceof ArmorItem) {
            ArmorItem i = (ArmorItem)var3;
            double damageReduceAmount = (double)i.getDamageReduceAmount();
            if (stack.isEnchanted()) {
                damageReduceAmount += (double)EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack) * 0.25D;
            }

            return damageReduceAmount;
        } else {
            return 0.0D;
        }
    }

    private double getEntityHealth(LivingEntity ent) {
        if (ent instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)ent;
            return (double)(player.getHealth() + player.getAbsorptionAmount()) * (this.getEntityArmor(player) / 20.0D);
        } else {
            return (double)(ent.getHealth() + ent.getAbsorptionAmount());
        }
    }

    public ModeSetting getType() {
        return this.type;
    }

    public ModeListSetting getOptions() {
        return this.options;
    }

    public StopWatch getStopWatch() {
        return this.stopWatch;
    }

    public LivingEntity getTarget() {
        return this.target;
    }
}