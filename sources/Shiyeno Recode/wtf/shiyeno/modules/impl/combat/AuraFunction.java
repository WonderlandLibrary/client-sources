package wtf.shiyeno.modules.impl.combat;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CEntityActionPacket.Action;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventInput;
import wtf.shiyeno.events.impl.player.EventMotion;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.math.AuraUtil;
import wtf.shiyeno.util.math.GCDUtil;
import wtf.shiyeno.util.math.MathUtil;
import wtf.shiyeno.util.math.RayTraceUtil;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.movement.MoveUtil;
import wtf.shiyeno.util.render.RenderUtil.IntColor;
import wtf.shiyeno.util.world.InventoryUtil;

@FunctionAnnotation(
        name = "AttackAura",
        type = Type.Combat
)
public class AuraFunction extends Function {
    public static LivingEntity target = null;
    public Vector2f rotate = new Vector2f(0.0F, 0.0F);
    private final ModeSetting rotationMode = new ModeSetting("Мод ротации", "Плавный", new String[]{"Плавный", "Резкий"});
    private final SliderSetting ticksnap = (new SliderSetting("Скорость ротации", 3.0F, 2.0F, 4.0F, 1.0F)).setVisible(() -> {
        return this.rotationMode.getIndex() == 1;
    });
    private final ModeSetting sortMode = new ModeSetting("Сортировать", "По всему", new String[]{"По всему", "По здоровью", "По дистанции"});
    private final MultiBoxSetting targets = new MultiBoxSetting("Цели", new BooleanOption[]{new BooleanOption("Игроки", true), new BooleanOption("Друзья", false), new BooleanOption("Голые", true), new BooleanOption("Мобы", false)});
    private final BooleanOption elytraTarget = (new BooleanOption("Элитра таргет", false)).setVisible(() -> {
        return this.rotationMode.getIndex() == 1;
    });
    private final BooleanOption targetNearElytra = (new BooleanOption("Таргет на элитры", false)).setVisible(() -> {
        return this.elytraTarget.get() && this.rotationMode.getIndex() == 1;
    });
    private final BooleanOption targetOnlyElytra = (new BooleanOption("Таргетить только элитры", false)).setVisible(() -> {
        return this.elytraTarget.get() && this.rotationMode.getIndex() == 1;
    });
    public static final BooleanOption speedOnFireWorkElytra = new BooleanOption("Ускорение на элитре", false);
    private final SliderSetting distance = new SliderSetting("Дистанция аттаки", 2.99F, 2.0F, 5.8F, 1.0E-4F);
    private final SliderSetting rotateDistance = (new SliderSetting("Дистанция ротации", 1.5F, 0.0F, 30.0F, 0.05F)).setVisible(() -> {
        return this.rotationMode.getIndex() == 0;
    });
    private final SliderSetting elytradistance = (new SliderSetting("Элитра дистанция", 1.5F, 0.0F, 5.0F, 0.05F)).setVisible(() -> {
        return this.rotationMode.getIndex() == 0 || this.rotationMode.getIndex() == 1 && this.elytraTarget.get();
    });
    private final SliderSetting elytrarotate = (new SliderSetting("Элитра ротация", 1.0F, 0.0F, 30.0F, 0.05F)).setVisible(() -> {
        return this.rotationMode.getIndex() == 0 || this.rotationMode.getIndex() == 1 && this.elytraTarget.get();
    });
    public final MultiBoxSetting settings = new MultiBoxSetting("Настройки", new BooleanOption[]{new BooleanOption("Только критами", true), new BooleanOption("Коррекция движения", true), new BooleanOption("Отжимать щит", true), new BooleanOption("Ломать щит", true), new BooleanOption("Кольцо", true)});
    private final BooleanOption onlySpaceCritical = (new BooleanOption("Умные криты", false)).setVisible(() -> {
        return this.settings.get(0);
    });
    private final ModeSetting correctionType = (new ModeSetting("Тип коррекции", "Незаметный", new String[]{"Незаметный", "Сфокусированный"})).setVisible(() -> {
        return this.settings.get(1);
    });
    private final BooleanOption FTBypass = (new BooleanOption("Обход FunTime", false)).setVisible(() -> {
        return this.rotationMode.getIndex() == 0;
    });
    int ticksUntilNextAttack;
    private boolean hasRotated;
    private long cpsLimit = 0L;
    private final TimerUtil timerUtil = new TimerUtil();
    boolean test = false;
    public Vector2f clientRot = null;
    float lastYaw;
    float lastPitch;
    private double prevCircleStep;
    private double circleStep;

    public AuraFunction() {
        this.addSettings(new Setting[]{this.rotationMode, this.targets, this.sortMode, this.distance, this.rotateDistance, this.ticksnap, this.elytrarotate, this.elytradistance, this.settings, this.correctionType, this.onlySpaceCritical, this.FTBypass, this.elytraTarget, this.targetNearElytra, this.targetOnlyElytra, speedOnFireWorkElytra});
    }

    public void onEvent(Event event) {
        if (event instanceof EventInput eventInput) {
            if (this.settings.get(1) && this.correctionType.getIndex() == 0 && Managment.FUNCTION_MANAGER.freeCam.player == null && !mc.player.isInWater()) {
                MoveUtil.fixMovement(eventInput, Managment.FUNCTION_MANAGER.autoPotionFunction.isActivePotion ? Minecraft.getInstance().player.rotationYaw : this.rotate.x);
            }
        }

        if (event instanceof EventUpdate updateEvent) {
            if (target == null || !this.isValidTarget(target)) {
                target = this.findTarget();
            }

            if (target == null) {
                this.cpsLimit = System.currentTimeMillis();
                this.rotate = new Vector2f(mc.player.rotationYaw, mc.player.rotationPitch);
                return;
            }

            this.attackAndRotateOnEntity(target);
        }

        if (event instanceof EventMotion motionEvent) {
            this.handleMotionEvent(motionEvent);
        }

        if (event instanceof EventRender e) {
            if (e.isRender3D() && target != null && this.settings.get(4)) {
                this.drawCircle(target, e);
            }
        }

    }

    private void handleMotionEvent(EventMotion motionEvent) {
        if (target != null && !Managment.FUNCTION_MANAGER.autoPotionFunction.isActivePotion) {
            motionEvent.setYaw(this.rotate.x);
            motionEvent.setPitch(this.rotate.y);
            mc.player.rotationYawHead = this.rotate.x;
            mc.player.renderYawOffset = this.rotate.x;
            mc.player.rotationPitchHead = this.rotate.y;
        }

    }

    private void attackAndRotateOnEntity(LivingEntity target) {
        boolean elytraFly = false;
        if (this.elytraTarget.get() && mc.player.isElytraFlying()) {
            elytraFly = true;
        }

        this.hasRotated = false;
        if (this.rotationMode.getIndex() == 0 || elytraFly) {
            this.hasRotated = false;
            if (this.shouldAttack(target) && !Managment.FUNCTION_MANAGER.autoPotionFunction.isActivePotion && (this.FTBypass.get() && RayTraceUtil.getMouseOver(target, this.rotate.x, this.rotate.y, (double)this.distance.getValue().floatValue()) == target || !this.FTBypass.get())) {
                this.attackTarget(target);
            }

            if (!this.hasRotated) {
                this.setRotation(target, false);
            }
        }

        if (this.rotationMode.getIndex() == 1 && !elytraFly) {
            if (this.shouldAttack(target) && !Managment.FUNCTION_MANAGER.autoPotionFunction.isActivePotion) {
                this.ticksUntilNextAttack = this.ticksnap.getValue().intValue();
                this.attackTarget(target);
            }

            if (this.ticksUntilNextAttack > 0) {
                this.setRotation(target, false);
                --this.ticksUntilNextAttack;
            } else {
                this.rotate.x = mc.player.rotationYaw;
                this.rotate.y = mc.player.rotationPitch;
            }
        }

    }

    private void attackTarget(LivingEntity targetEntity) {
        if (this.settings.get(2) && mc.player.isBlocking()) {
            mc.playerController.onStoppedUsingItem(mc.player);
        }

        boolean sprint = false;
        if (CEntityActionPacket.lastUpdatedSprint && !mc.player.isInWater()) {
            mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, Action.STOP_SPRINTING));
            sprint = true;
        }

        this.cpsLimit = System.currentTimeMillis() + 550L;
        mc.playerController.attackEntity(mc.player, targetEntity);
        mc.player.swingArm(Hand.MAIN_HAND);
        if (this.settings.get(3)) {
            this.breakShieldAndSwapSlot();
        }

        if (sprint) {
            mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, Action.START_SPRINTING));
        }

    }

    private void breakShieldAndSwapSlot() {
        LivingEntity targetEntity = target;
        if (targetEntity instanceof PlayerEntity player) {
            if (target.isActiveItemStackBlocking(2) && !player.isSpectator() && !player.isCreative() && (target.getHeldItemOffhand().getItem() == Items.SHIELD || target.getHeldItemMainhand().getItem() == Items.SHIELD)) {
                int slot = this.breakShield(player);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
            }
        }

    }

    public int breakShield(LivingEntity target) {
        int hotBarSlot = InventoryUtil.getAxe(true);
        if (hotBarSlot != -1) {
            mc.player.connection.sendPacket(new CHeldItemChangePacket(hotBarSlot));
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(Hand.MAIN_HAND);
            mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
            return hotBarSlot;
        } else {
            int inventorySLot = InventoryUtil.getAxe(false);
            if (inventorySLot != -1) {
                mc.playerController.pickItem(inventorySLot);
                mc.playerController.attackEntity(mc.player, target);
                mc.player.swingArm(Hand.MAIN_HAND);
                return inventorySLot;
            } else {
                return -1;
            }
        }
    }

    private boolean shouldAttack(LivingEntity targetEntity) {
        return this.canAttack() && targetEntity != null && this.cpsLimit <= System.currentTimeMillis();
    }

    private void setRotation(LivingEntity base, boolean attack) {
        this.hasRotated = true;
        Vector3d vec3d = AuraUtil.getVector(base);
        double diffX = vec3d.x;
        double diffY = vec3d.y;
        double diffZ = vec3d.z;
        float[] rotations = new float[]{(float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F, (float)(-Math.toDegrees(Math.atan2(diffY, Math.hypot(diffX, diffZ))))};
        float deltaYaw = MathHelper.wrapDegrees(MathUtil.calculateDelta(rotations[0], this.rotate.x));
        float deltaPitch = MathUtil.calculateDelta(rotations[1], this.rotate.y);
        float limitedYaw = Math.min(Math.max(Math.abs(deltaYaw), 1.0F), 360.0F);
        float limitedPitch = Math.min(Math.max(Math.abs(deltaPitch), 1.0F), 90.0F);
        float finalYaw = this.rotate.x + (deltaYaw > 0.0F ? limitedYaw : -limitedYaw) + ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F);
        float finalPitch = MathHelper.clamp(this.rotate.y + (deltaPitch > 0.0F ? limitedPitch : -limitedPitch) + ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F), -90.0F, 90.0F);
        float gcd = GCDUtil.getGCDValue();
        finalYaw = (float)((double)finalYaw - (double)(finalYaw - this.rotate.x) % (double)gcd);
        finalPitch = (float)((double)finalPitch - (double)(finalPitch - this.rotate.y) % (double)gcd);
        this.rotate.x = finalYaw;
        this.rotate.y = finalPitch;
    }

    public boolean canAttack() {
        boolean onSpace = this.onlySpaceCritical.get() && mc.player.isOnGround() && !mc.gameSettings.keyBindJump.isKeyDown();
        boolean reasonForAttack = mc.player.isPotionActive(Effects.BLINDNESS) || mc.player.isOnLadder() || mc.player.isInWater() && mc.player.areEyesInFluid(FluidTags.WATER) || mc.player.isRidingHorse() || mc.player.abilities.isFlying || mc.player.isElytraFlying() || mc.player.isInLava() && mc.player.areEyesInFluid(FluidTags.LAVA);
        float elytradistance1 = 0.0F;
        if (mc.player.isElytraFlying()) {
            elytradistance1 = this.elytradistance.getValue().floatValue();
        }

        if (!mc.player.isElytraFlying()) {
            elytradistance1 = 0.0F;
        }

        if (!(this.getDistance(target) >= (double)(this.distance.getValue().floatValue() - elytradistance1)) && !(mc.player.getCooledAttackStrength(1.5F) < 0.92F)) {
            if (Managment.FUNCTION_MANAGER.freeCam.player != null) {
                return true;
            } else if (!reasonForAttack && this.settings.get(0)) {
                return onSpace || !mc.player.isOnGround() && mc.player.fallDistance > 0.0F;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private LivingEntity findTarget() {
        List<LivingEntity> targets = new ArrayList();
        Iterator var2 = mc.world.getAllEntities().iterator();

        while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            if (entity instanceof LivingEntity && this.isValidTarget((LivingEntity)entity)) {
                targets.add((LivingEntity)entity);
            }
        }

        if (targets.isEmpty()) {
            return null;
        } else {
            if (targets.size() > 1) {
                switch (this.sortMode.get()) {
                    case "По всему":
                        targets.sort(Comparator.comparingDouble((target) -> {
                            if (target instanceof PlayerEntity player) {
                                return -this.getEntityArmor(player);
                            } else if (target instanceof LivingEntity livingEntity) {
                                return (double)(-livingEntity.getTotalArmorValue());
                            } else {
                                return 0.0;
                            }
                        }).thenComparing((o, o1) -> {
                            double health = this.getEntityHealth((LivingEntity)o);
                            double health1 = this.getEntityHealth((LivingEntity)o1);
                            return Double.compare(health, health1);
                        }).thenComparing((object, object2) -> {
                            double d2 = this.getDistance((LivingEntity)object);
                            double d3 = this.getDistance((LivingEntity)object2);
                            return Double.compare(d2, d3);
                        }));
                        break;
                    case "По дистанции":
                        AuraFunction var6 = Managment.FUNCTION_MANAGER.auraFunction;
                        Objects.requireNonNull(var6);
                        targets.sort(Comparator.comparingDouble(var6::getDistance).thenComparingDouble(this::getEntityHealth));
                        break;
                    case "По здоровью":
                        Comparator var10001 = Comparator.comparingDouble(this::getEntityHealth);
                        ClientPlayerEntity var10002 = mc.player;
                        Objects.requireNonNull(var10002);
                        targets.sort(var10001.thenComparingDouble(var10002::getDistance));
                }
            } else {
                this.cpsLimit = System.currentTimeMillis();
            }

            return (LivingEntity)targets.get(0);
        }
    }

    private boolean isValidTarget(LivingEntity base) {
        if (!base.getShouldBeDead() && base.isAlive() && base != mc.player) {
            if (base instanceof PlayerEntity) {
                String playerName = base.getName().getString();
                if (Managment.FRIEND_MANAGER.isFriend(playerName) && !this.targets.get(1) || Managment.FUNCTION_MANAGER.freeCam.player != null && playerName.equals(Managment.FUNCTION_MANAGER.freeCam.player.getName().getString()) || base.getTotalArmorValue() == 0 && (!this.targets.get(0) || !this.targets.get(2))) {
                    return false;
                }
            }

            if (AntiBot.checkBot(base)) {
                return false;
            } else {
                if (base instanceof PlayerEntity) {
                    boolean nearElytraPlayer = false;
                    Iterator var3 = mc.world.getPlayers().iterator();

                    while(var3.hasNext()) {
                        PlayerEntity entity = (PlayerEntity)var3.next();
                        if (entity != mc.player && !Managment.FRIEND_MANAGER.isFriend(entity.getName().getString()) && ((ItemStack)entity.inventory.armorInventory.get(2)).getItem() == Items.ELYTRA && mc.player.getDistance(entity) < this.elytrarotate.getValue().floatValue() + this.distance.getValue().floatValue() - this.elytradistance.getValue().floatValue()) {
                            nearElytraPlayer = true;
                        }
                    }

                    if (((ItemStack)((PlayerEntity)base).inventory.armorInventory.get(2)).getItem() != Items.ELYTRA && mc.player.isElytraFlying() && this.elytraTarget.get() && this.rotationMode.getIndex() == 1) {
                        if (nearElytraPlayer && !this.targetOnlyElytra.get()) {
                            return false;
                        }

                        if (this.targetOnlyElytra.get()) {
                            return false;
                        }
                    }
                }

                if ((base instanceof MobEntity || base instanceof AnimalEntity) && !this.targets.get(3)) {
                    return false;
                } else if (base instanceof PlayerEntity && !this.targets.get(0)) {
                    return false;
                } else if (!(base instanceof ArmorStandEntity) && (!(base instanceof PlayerEntity) || !((PlayerEntity)base).isBot)) {
                    float elytrarotate1 = 0.0F;
                    if (mc.player.isElytraFlying()) {
                        elytrarotate1 = this.elytrarotate.getValue().floatValue();
                    }

                    if (!mc.player.isElytraFlying()) {
                        elytrarotate1 = 0.0F;
                    }

                    return this.getDistance(base) <= (double)(this.distance.getValue().floatValue() + (this.rotationMode.getIndex() == 0 ? this.rotateDistance.getValue().floatValue() + elytrarotate1 : 0.0F) + (this.rotationMode.getIndex() == 1 && mc.player.isElytraFlying() && this.elytraTarget.get() ? this.rotateDistance.getValue().floatValue() + elytrarotate1 : 0.0F));
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private double getDistance(LivingEntity entity) {
        return AuraUtil.getVector(entity).length();
    }

    public double getEntityArmor(PlayerEntity target) {
        double totalArmor = 0.0;
        Iterator var4 = target.inventory.armorInventory.iterator();

        while(var4.hasNext()) {
            ItemStack armorStack = (ItemStack)var4.next();
            if (armorStack != null && armorStack.getItem() instanceof ArmorItem) {
                totalArmor += this.getProtectionLvl(armorStack);
            }
        }

        return totalArmor;
    }

    public double getEntityHealth(Entity ent) {
        if (ent instanceof PlayerEntity player) {
            double armorValue = this.getEntityArmor(player) / 20.0;
            return (double)(player.getHealth() + player.getAbsorptionAmount()) * armorValue;
        } else if (ent instanceof LivingEntity livingEntity) {
            return (double)(livingEntity.getHealth() + livingEntity.getAbsorptionAmount());
        } else {
            return 0.0;
        }
    }

    private double getProtectionLvl(ItemStack stack) {
        ArmorItem armor = (ArmorItem)stack.getItem();
        double damageReduce = (double)armor.getDamageReduceAmount();
        if (stack.isEnchanted()) {
            damageReduce += (double)EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack) * 0.25;
        }

        return damageReduce;
    }

    public void onDisable() {
        this.rotate = new Vector2f(mc.player.rotationYaw, mc.player.rotationPitch);
        target = null;
        this.cpsLimit = System.currentTimeMillis();
        super.onDisable();
    }

    private void drawCircle(LivingEntity target, EventRender e) {
        EntityRendererManager rm = mc.getRenderManager();
        double x = target.lastTickPosX + (target.getPosX() - target.lastTickPosX) * (double)e.partialTicks - rm.info.getProjectedView().getX();
        double y = target.lastTickPosY + (target.getPosY() - target.lastTickPosY) * (double)e.partialTicks - rm.info.getProjectedView().getY();
        double z = target.lastTickPosZ + (target.getPosZ() - target.lastTickPosZ) * (double)e.partialTicks - rm.info.getProjectedView().getZ();
        float height = target.getHeight();
        double duration = 2000.0;
        double elapsed = (double)System.currentTimeMillis() % duration;
        boolean side = elapsed > duration / 2.0;
        double progress = elapsed / (duration / 2.0);
        if (side) {
            --progress;
        } else {
            progress = 1.0 - progress;
        }

        progress = progress < 0.5 ? 2.0 * progress * progress : 1.0 - Math.pow(-2.0 * progress + 2.0, 2.0) / 2.0;
        double eased = (double)(height / 2.0F) * (progress > 0.5 ? 1.0 - progress : progress) * (double)(side ? -1 : 1);
        RenderSystem.pushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.shadeModel(7425);
        RenderSystem.disableCull();
        RenderSystem.lineWidth(1.5F);
        RenderSystem.color4f(-1.0F, -1.0F, -1.0F, -1.0F);
        buffer.begin(8, DefaultVertexFormats.POSITION_COLOR);
        float[] colors = null;

        int i;
        for(i = 0; i <= 360; ++i) {
            colors = IntColor.rgb(Managment.STYLE_MANAGER.getCurrentStyle().getColor(i));
            buffer.pos(x + Math.cos(Math.toRadians((double)i)) * (double)target.getWidth() * 0.8, y + (double)height * progress, z + Math.sin(Math.toRadians((double)i)) * (double)target.getWidth() * 0.8).color(colors[0], colors[1], colors[2], 0.5F).endVertex();
            buffer.pos(x + Math.cos(Math.toRadians((double)i)) * (double)target.getWidth() * 0.8, y + (double)height * progress + eased, z + Math.sin(Math.toRadians((double)i)) * (double)target.getWidth() * 0.8).color(colors[0], colors[1], colors[2], 0.0F).endVertex();
        }

        buffer.finishDrawing();
        WorldVertexBufferUploader.draw(buffer);
        RenderSystem.color4f(-1.0F, -1.0F, -1.0F, -1.0F);
        buffer.begin(2, DefaultVertexFormats.POSITION_COLOR);

        for(i = 0; i <= 360; ++i) {
            buffer.pos(x + Math.cos(Math.toRadians((double)i)) * (double)target.getWidth() * 0.8, y + (double)height * progress, z + Math.sin(Math.toRadians((double)i)) * (double)target.getWidth() * 0.8).color(colors[0], colors[1], colors[2], 0.5F).endVertex();
        }

        buffer.finishDrawing();
        WorldVertexBufferUploader.draw(buffer);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        RenderSystem.enableAlphaTest();
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4354);
        RenderSystem.shadeModel(7424);
        RenderSystem.popMatrix();
    }

    public static LivingEntity getTarget() {
        return target;
    }
}