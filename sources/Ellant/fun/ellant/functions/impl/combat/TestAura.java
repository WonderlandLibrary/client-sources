package fun.ellant.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import fun.ellant.Ellant;
import fun.ellant.command.friends.FriendStorage;
import fun.ellant.events.EventInput;
import fun.ellant.events.EventMotion;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeListSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.math.SensUtils;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.InventoryUtil;
import fun.ellant.utils.player.MouseUtil;
import fun.ellant.utils.player.MoveUtils;
import java.util.ArrayList;
import java.util.Comparator;
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
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="TestAura", type=Category.COMBAT, desc = "Пон?")
public class TestAura
        extends Function {
    private final ModeSetting type = new ModeSetting("\u0422\u0438\u043f", "FT", "FT", "RW");
    private final SliderSetting attackRange = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f \u0430\u0442\u0442\u0430\u043a\u0438", 3.0f, 3.0f, 6.0f, 0.1f);
    final ModeListSetting targets = new ModeListSetting("\u0422\u0430\u0440\u0433\u0435\u0442\u044b", new BooleanSetting("\u0418\u0433\u0440\u043e\u043a\u0438", true), new BooleanSetting("\u0413\u043e\u043b\u044b\u0435", true), new BooleanSetting("\u041c\u043e\u0431\u044b", false), new BooleanSetting("\u0416\u0438\u0432\u043e\u0442\u043d\u044b\u0435", false), new BooleanSetting("\u0414\u0440\u0443\u0437\u044c\u044f", false), new BooleanSetting("\u0413\u043e\u043b\u044b\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043a\u0438", true), new BooleanSetting("\u041d\u0435\u0432\u0438\u0434\u0438\u043c\u043a\u0438", true));
    final ModeListSetting options = new ModeListSetting("\u041e\u043f\u0446\u0438\u0438", new BooleanSetting("\u0422\u043e\u043b\u044c\u043a\u043e \u043a\u0440\u0438\u0442\u044b", true), new BooleanSetting("\u041b\u043e\u043c\u0430\u0442\u044c \u0449\u0438\u0442", true), new BooleanSetting("\u041e\u0442\u0436\u0438\u043c\u0430\u0442\u044c \u0449\u0438\u0442", true), new BooleanSetting("\u0423\u0441\u043a\u043e\u0440\u044f\u0442\u044c \u0440\u043e\u0442\u0430\u0446\u0438\u044e \u043f\u0440\u0438 \u0430\u0442\u0430\u043a\u0435", false), new BooleanSetting("\u0421\u0438\u043d\u0445\u0440\u043e\u043d\u0438\u0437\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0430\u0442\u0430\u043a\u0443 \u0441 \u0422\u041f\u0421", false), new BooleanSetting("\u0424\u043e\u043a\u0443\u0441\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043e\u0434\u043d\u0443 \u0446\u0435\u043b\u044c", true), new BooleanSetting("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f", true));
    final ModeSetting correctionType = new ModeSetting("\u0422\u0438\u043f \u043a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u0438", "\u041d\u0435\u0437\u0430\u043c\u0435\u0442\u043d\u044b\u0439", "\u041d\u0435\u0437\u0430\u043c\u0435\u0442\u043d\u044b\u0439", "\u0421\u0444\u043e\u043a\u0443\u0441\u0438\u0440\u043e\u0432\u0430\u043d\u043d\u044b\u0439");
    private final StopWatch stopWatch = new StopWatch();
    private Vector2f rotateVector = new Vector2f(0.0f, 0.0f);
    private LivingEntity target;
    private Entity selected;
    int ticks = 0;
    boolean isRotated;
    final AutoPotion autoPotion;
    float lastYaw;
    float lastPitch;

    public TestAura(AutoPotion autoPotion) {
        this.autoPotion = autoPotion;
        this.addSettings(this.type, this.attackRange, this.targets, this.options, this.correctionType);
    }

    @Subscribe
    public void onInput(EventInput eventInput) {
        if (((Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get()).booleanValue() && this.correctionType.is("\u041d\u0435\u0437\u0430\u043c\u0435\u0442\u043d\u0430\u044f") && this.target != null && KillAura.mc.player != null) {
            MoveUtils.fixMovement(eventInput, this.rotateVector.x);
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (((Boolean)this.options.getValueByName("\u0424\u043e\u043a\u0443\u0441\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043e\u0434\u043d\u0443 \u0446\u0435\u043b\u044c").get()).booleanValue() && (this.target == null || !this.isValid(this.target)) || !((Boolean)this.options.getValueByName("\u0424\u043e\u043a\u0443\u0441\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043e\u0434\u043d\u0443 \u0446\u0435\u043b\u044c").get()).booleanValue()) {
            this.updateTarget();
        }
        if (!(this.target == null || this.autoPotion.isState() && this.autoPotion.isActive())) {
            this.isRotated = false;
            if (this.shouldPlayerFalling() && this.stopWatch.hasTimeElapsed()) {
                this.updateAttack();
                this.ticks = 2;
            }
            if (this.type.is("RW")) {
                if (this.ticks > 0) {
                    this.updateRotation(true, 31.0f, 11.0f);
                    --this.ticks;
                } else {
                    this.reset();
                }
            } else if (!this.isRotated) {
                this.updateRotation(false, 42.0f, 33.0f);
            }
        } else {
            this.stopWatch.setLastMS(0L);
            this.reset();
        }
    }

    @Subscribe
    private void onWalking(EventMotion e) {
        if (this.target == null || this.autoPotion.isState() && this.autoPotion.isActive()) {
            return;
        }
        float yaw = this.rotateVector.x;
        float pitch = this.rotateVector.y;
        e.setYaw(yaw);
        e.setPitch(pitch);
        KillAura.mc.player.rotationYawHead = yaw;
        KillAura.mc.player.renderYawOffset = yaw;
        KillAura.mc.player.rotationPitchHead = pitch;
    }

    private void updateTarget() {
        ArrayList<Object> targets = new ArrayList<Object>();
        for (Entity entity2 : KillAura.mc.world.getAllEntities()) {
            LivingEntity living;
            if (!(entity2 instanceof LivingEntity) || !this.isValid(living = (LivingEntity)entity2)) continue;
            targets.add(living);
        }
        if (targets.isEmpty()) {
            this.target = null;
            return;
        }
        if (targets.size() == 1) {
            this.target = (LivingEntity)targets.get(0);
            return;
        }
        targets.sort(Comparator.comparingDouble(object -> {
            if (object instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity)object;
                return -this.getEntityArmor(player);
            }
            if (object instanceof LivingEntity) {
                LivingEntity base = (LivingEntity)object;
                return -base.getTotalArmorValue();
            }
            return 0.0;
        }).thenComparing((object, object2) -> {
            double d2 = this.getEntityHealth((LivingEntity)object);
            double d3 = this.getEntityHealth((LivingEntity)object2);
            return Double.compare(d2, d3);
        }).thenComparing((object, object2) -> {
            double d2 = KillAura.mc.player.getDistance((LivingEntity)object);
            double d3 = KillAura.mc.player.getDistance((LivingEntity)object2);
            return Double.compare(d2, d3);
        }));
        this.target = (LivingEntity)targets.get(0);
    }

    private void updateRotation(boolean attack, float rotationYawSpeed, float rotationPitchSpeed) {
        Vector3d vec = this.target.getPositionVec().add(0.0, MathHelper.clamp(KillAura.mc.player.getPosYEye() - this.target.getPosY(), 0.0, (double)this.target.getHeight() * (KillAura.mc.player.getDistanceEyePos(this.target) / (double)((Float)this.attackRange.get()).floatValue())), 0.0).subtract(KillAura.mc.player.getEyePosition(1.0f));
        this.isRotated = true;
        float yawToTarget = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90.0);
        float pitchToTarget = (float)(-Math.toDegrees(Math.atan2(vec.y, Math.hypot(vec.x, vec.z))));
        float yawDelta = MathHelper.wrapDegrees(yawToTarget - this.rotateVector.x);
        float pitchDelta = MathHelper.wrapDegrees(pitchToTarget - this.rotateVector.y);
        int roundedYaw = (int)yawDelta;
        switch ((String)this.type.get()) {
            case "FT": {
                float clampedYaw = Math.min(Math.max(Math.abs(yawDelta), 1.2f), rotationYawSpeed);
                float clampedPitch = Math.min(Math.max(Math.abs(pitchDelta), 1.1f), rotationPitchSpeed);
                clampedPitch = attack && this.selected != this.target && ((Boolean)this.options.getValueByName("\u0423\u0441\u043a\u043e\u0440\u044f\u0442\u044c \u0440\u043e\u0442\u0430\u0446\u0438\u044e \u043f\u0440\u0438 \u0430\u0442\u0430\u043a\u0435").get()).booleanValue() ? Math.max(Math.abs(pitchDelta), 1.1f) : (clampedPitch /= 3.0f);
                if (Math.abs(clampedYaw - this.lastYaw) <= 3.0f) {
                    clampedYaw = this.lastYaw + 3.1f;
                }
                float yaw = this.rotateVector.x + (yawDelta > 0.0f ? clampedYaw : -clampedYaw);
                float pitch = MathHelper.clamp(this.rotateVector.y + (pitchDelta > 0.0f ? clampedPitch : -clampedPitch), -89.0f, 89.0f);
                float gcd = SensUtils.getGCDValue();
                yaw -= (yaw - this.rotateVector.x) % gcd;
                pitch -= (pitch - this.rotateVector.y) % gcd;
                this.rotateVector = new Vector2f(yaw, pitch);
                this.lastYaw = clampedYaw;
                this.lastPitch = clampedPitch;
                if (!((Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get()).booleanValue()) break;
                KillAura.mc.player.rotationYawOffset = yaw;
                break;
            }
            case "RW": {
                float yaw = this.rotateVector.x + (float)roundedYaw;
                float pitch = MathHelper.clamp(this.rotateVector.y + pitchDelta, -90.0f, 90.0f);
                float gcd = SensUtils.getGCDValue();
                yaw -= (yaw - this.rotateVector.x) % gcd;
                pitch -= (pitch - this.rotateVector.y) % gcd;
                this.rotateVector = new Vector2f(yaw, pitch);
                if (!((Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get()).booleanValue()) break;
                KillAura.mc.player.rotationYawOffset = yaw;
            }
        }
    }

    private void updateAttack() {
        this.selected = MouseUtil.getMouseOver(this.target, this.rotateVector.x, this.rotateVector.y, ((Float)this.attackRange.get()).floatValue());
        if (((Boolean)this.options.getValueByName("\u0423\u0441\u043a\u043e\u0440\u044f\u0442\u044c \u0440\u043e\u0442\u0430\u0446\u0438\u044e \u043f\u0440\u0438 \u0430\u0442\u0430\u043a\u0435").get()).booleanValue()) {
            this.updateRotation(true, 72.0f, 45.0f);
        }
        if (!(this.selected != null && this.selected == this.target || KillAura.mc.player.isElytraFlying())) {
            return;
        }
        if (KillAura.mc.player.isBlocking() && ((Boolean)this.options.getValueByName("\u041e\u0442\u0436\u0438\u043c\u0430\u0442\u044c \u0449\u0438\u0442").get()).booleanValue()) {
            KillAura.mc.playerController.onStoppedUsingItem(KillAura.mc.player);
        }
        this.stopWatch.setLastMS(500L);
        KillAura.mc.playerController.attackEntity(KillAura.mc.player, this.target);
        KillAura.mc.player.swingArm(Hand.MAIN_HAND);
        LivingEntity livingEntity = this.target;
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)livingEntity;
            if (((Boolean)this.options.getValueByName("\u041b\u043e\u043c\u0430\u0442\u044c \u0449\u0438\u0442").get()).booleanValue()) {
                this.breakShieldPlayer(player);
            }
        }
    }

    private boolean shouldPlayerFalling() {
        boolean cancelReason = KillAura.mc.player.isInWater() && KillAura.mc.player.areEyesInFluid(FluidTags.WATER) || KillAura.mc.player.isInLava() || KillAura.mc.player.isOnLadder() || KillAura.mc.player.isPassenger() || KillAura.mc.player.abilities.isFlying;
        float attackStrength = KillAura.mc.player.getCooledAttackStrength((Boolean)this.options.getValueByName("\u0421\u0438\u043d\u0445\u0440\u043e\u043d\u0438\u0437\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0430\u0442\u0430\u043a\u0443 \u0441 \u0422\u041f\u0421").get() != false ? Ellant.getInstance().getTpsCalc().getAdjustTicks() : 1.5f);
        if (attackStrength < 0.92f) {
            return false;
        }
        if (!cancelReason && ((Boolean)this.options.getValueByName("\u0422\u043e\u043b\u044c\u043a\u043e \u043a\u0440\u0438\u0442\u044b").get()).booleanValue()) {
            return !KillAura.mc.player.isOnGround() && KillAura.mc.player.fallDistance > 0.0f;
        }
        return true;
    }

    private boolean isValid(LivingEntity entity2) {
        if (entity2 instanceof ClientPlayerEntity) {
            return false;
        }
        if (entity2.ticksExisted < 3) {
            return false;
        }
        if (KillAura.mc.player.getDistanceEyePos(entity2) > (double)((Float)this.attackRange.get()).floatValue()) {
            return false;
        }
        if (entity2 instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity)entity2;
            if (AntiBot.isBot(entity2)) {
                return false;
            }
            if (!((Boolean)this.targets.getValueByName("\u0414\u0440\u0443\u0437\u044c\u044f").get()).booleanValue() && FriendStorage.isFriend(p.getName().getString())) {
                return false;
            }
            if (p.getName().getString().equalsIgnoreCase(KillAura.mc.player.getName().getString())) {
                return false;
            }
        }
        if (entity2 instanceof PlayerEntity && !((Boolean)this.targets.getValueByName("\u0418\u0433\u0440\u043e\u043a\u0438").get()).booleanValue()) {
            return false;
        }
        if (entity2 instanceof PlayerEntity && entity2.getTotalArmorValue() == 0 && !((Boolean)this.targets.getValueByName("\u0413\u043e\u043b\u044b\u0435").get()).booleanValue()) {
            return false;
        }
        if (entity2 instanceof PlayerEntity && entity2.isInvisible() && entity2.getTotalArmorValue() == 0 && !((Boolean)this.targets.getValueByName("\u0413\u043e\u043b\u044b\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043a\u0438").get()).booleanValue()) {
            return false;
        }
        if (entity2 instanceof PlayerEntity && entity2.isInvisible() && !((Boolean)this.targets.getValueByName("\u041d\u0435\u0432\u0438\u0434\u0438\u043c\u043a\u0438").get()).booleanValue()) {
            return false;
        }
        if (entity2 instanceof MonsterEntity && !((Boolean)this.targets.getValueByName("\u041c\u043e\u0431\u044b").get()).booleanValue()) {
            return false;
        }
        if (entity2 instanceof AnimalEntity && !((Boolean)this.targets.getValueByName("\u0416\u0438\u0432\u043e\u0442\u043d\u044b\u0435").get()).booleanValue()) {
            return false;
        }
        return !entity2.isInvulnerable() && entity2.isAlive() && !(entity2 instanceof ArmorStandEntity);
    }

    private void breakShieldPlayer(PlayerEntity entity2) {
        if (entity2.isBlocking()) {
            int invSlot = InventoryUtil.getInstance().getAxeInInventory(false);
            int hotBarSlot = InventoryUtil.getInstance().getAxeInInventory(true);
            if (hotBarSlot == -1 && invSlot != -1) {
                int bestSlot = InventoryUtil.getInstance().findBestSlotInHotBar();
                KillAura.mc.playerController.windowClick(0, invSlot, 0, ClickType.PICKUP, KillAura.mc.player);
                KillAura.mc.playerController.windowClick(0, bestSlot + 36, 0, ClickType.PICKUP, KillAura.mc.player);
                KillAura.mc.player.connection.sendPacket(new CHeldItemChangePacket(bestSlot));
                KillAura.mc.playerController.attackEntity(KillAura.mc.player, entity2);
                KillAura.mc.player.swingArm(Hand.MAIN_HAND);
                KillAura.mc.player.connection.sendPacket(new CHeldItemChangePacket(KillAura.mc.player.inventory.currentItem));
                KillAura.mc.playerController.windowClick(0, bestSlot + 36, 0, ClickType.PICKUP, KillAura.mc.player);
                KillAura.mc.playerController.windowClick(0, invSlot, 0, ClickType.PICKUP, KillAura.mc.player);
            }
            if (hotBarSlot != -1) {
                KillAura.mc.player.connection.sendPacket(new CHeldItemChangePacket(hotBarSlot));
                KillAura.mc.playerController.attackEntity(KillAura.mc.player, entity2);
                KillAura.mc.player.swingArm(Hand.MAIN_HAND);
                KillAura.mc.player.connection.sendPacket(new CHeldItemChangePacket(KillAura.mc.player.inventory.currentItem));
            }
        }
    }

    private void reset() {
        if (((Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get()).booleanValue()) {
            KillAura.mc.player.rotationYawOffset = -2.14748365E9f;
        }
        this.rotateVector = new Vector2f(KillAura.mc.player.rotationYaw, KillAura.mc.player.rotationPitch);
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        this.reset();
        this.target = null;
        return false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.reset();
        this.stopWatch.setLastMS(0L);
        this.target = null;
    }

    private double getEntityArmor(PlayerEntity entityPlayer2) {
        double d2 = 0.0;
        for (int i2 = 0; i2 < 4; ++i2) {
            ItemStack is = entityPlayer2.inventory.armorInventory.get(i2);
            if (!(is.getItem() instanceof ArmorItem)) continue;
            d2 += this.getProtectionLvl(is);
        }
        return d2;
    }

    private double getProtectionLvl(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof ArmorItem) {
            ArmorItem i = (ArmorItem)item;
            double damageReduceAmount = i.getDamageReduceAmount();
            if (stack.isEnchanted()) {
                damageReduceAmount += (double)EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack) * 0.25;
            }
            return damageReduceAmount;
        }
        return 0.0;
    }

    private double getEntityHealth(LivingEntity ent) {
        if (ent instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)ent;
            return (double)(player.getHealth() + player.getAbsorptionAmount()) * (this.getEntityArmor(player) / 20.0);
        }
        return ent.getHealth() + ent.getAbsorptionAmount();
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