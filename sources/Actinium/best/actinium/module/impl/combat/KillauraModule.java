package best.actinium.module.impl.combat;

import best.actinium.Actinium;
import best.actinium.component.componets.RotationComponent;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.input.ClickEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.IAccess;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.player.PlayerUtil;
import best.actinium.util.player.RotationsUtils;
import best.actinium.module.impl.movement.scaffold.ScaffoldWalkModule;
import best.actinium.util.render.ChatUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjglx.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Float.NaN;

@ModuleInfo(
        name = "KillAura",
        description = "Automatically attacks entities for you.",
        category = ModuleCategory.COMBAT
)
public class KillauraModule extends Module {
    public ModeProperty attackMode = new ModeProperty("Attack Mode", this, new String[] { "Single", "Switch", "Multi" }, "Single"),
            blockMode = new ModeProperty("Blocking Mode", this, new String[] { "None", "Vanilla","Blink", "Watchdog"}, "Vanilla"),
            rotationMode = new ModeProperty("Rotation Mode", this, new String[] { "None", "Normal"}, "Normal"),
            sortingMode = new ModeProperty("Sorting Mode", this, new String[] { "Health", "Range", "HurtTime", "Armor"}, "Health"),
            attackTiming = new ModeProperty("Attack Timing", this, new String[] { "Pre", "Post", "Legit" }, "Legit"),
            randomMode = new ModeProperty("Random Mode", this, new String[] {"None", "Normal", "Doubled", "Gaussian"}, "None");

    public BooleanProperty blockInteract = new BooleanProperty("Block Interact", this,false);
    public BooleanProperty fakeAutoblock = new BooleanProperty("Fake AutoBlock", this, false);

    public NumberProperty maxTargets = new NumberProperty("Max Targets", this, 2, 2, 10, 1);
    public NumberProperty minAPS = new NumberProperty("Min APS", this, 1, 7, 20, 0.1),
            maxAPS = new NumberProperty("Max APS", this, 1, 12, 20, 0.1);

    public NumberProperty randomization = new NumberProperty("Randomization", this, 0, 1, 3, 0.1);

    public NumberProperty swingRange = new NumberProperty("Swing Range", this, 3, 3, 6, 0.1),
            attackRange = new NumberProperty("Attack Range", this, 3, 3, 6, 0.1),
            wallsRange = new NumberProperty("Walls Range", this, 3, 3, 6, 0.1),
            blockRange = new NumberProperty("Block Range", this, 3, 3, 6, 0.1),
            rotationRange = new NumberProperty("Rotation Range", this, 3, 3, 6, 0.1);

    public NumberProperty blockChance = new NumberProperty("Block Chance", this, 0, 100, 100, 1);
    public NumberProperty switchDelay = new NumberProperty("Switch Delay", this, 0, 350, 5000, 50);
    public BooleanProperty throughWalls = new BooleanProperty("Through Walls", this, true),
            rayTracing = new BooleanProperty("Ray Tracing", this, false);

    public BooleanProperty autoDisable = new BooleanProperty("Auto Disable", this, true),
            ignoreUI = new BooleanProperty("Ignore UI", this, true);

    public BooleanProperty targetPlayers = new BooleanProperty("Players", this, true),
            targetAnimals = new BooleanProperty("Animals", this, false),
            targetMonsters = new BooleanProperty("Monsters", this, false),
            targetInvisible = new BooleanProperty("Invisible", this, false);

    public BooleanProperty reverseSorting = new BooleanProperty("Reverse Sorting", this, false);

    public static EntityLivingBase target, renderTarget;
    public List<EntityLivingBase> list =  new ArrayList<>();

    private int targetIndex = 0;
    private float yaw, pitch;
    public static boolean fake, blocking, attacking;
    private boolean sent = false;
    private double currentAPS = 20;
    private final TimerUtil attackTimer = new TimerUtil(), switchTimer = new TimerUtil();
    /* ab stuff */
    private int stage;
    private boolean blinking;
    
    @Override
    public void onEnable() {
        attackTimer.reset();

        if (IAccess.mc.theWorld != null && IAccess.mc.thePlayer != null) {
            yaw = IAccess.mc.thePlayer.rotationYaw;
            pitch = IAccess.mc.thePlayer.rotationPitch;
        }

        fake = blockMode.is("Fake");

        super.onEnable();
    }

    @Override
    public void onDisable() {

        target = null;
        renderTarget = null;

        list.clear();

        unblock();

        blocking = false;
        attacking = false;

        super.onDisable();
    }

    @Callback
    public void onTickEvent(TickEvent event) {
        if (IAccess.mc.theWorld == null || IAccess.mc.thePlayer == null) {
            return;
        }

        updateTargets();

        if (target == null)
            return;

        if (!list.isEmpty()) {
            if (targetIndex > list.size() - 1) {
                targetIndex = 0;
            }
            target = list.get(targetIndex);
            calculateRotations(target);
        }
    }

    @Callback
    public void onUpdate(UpdateEvent e) {
        if(target != null) {
            RotationComponent.setRotations(new Vector2f(yaw,pitch));
        }
    }

    @Callback
    public void onMotionEvent(MotionEvent event) {
        this.setSuffix(attackMode.getMode());

        if (IAccess.mc.theWorld == null || IAccess.mc.thePlayer == null) {
            return;
        }

        attacking = target != null && !Actinium.INSTANCE.getModuleManager().get(ScaffoldWalkModule.class).isEnabled();

        if (IAccess.mc.thePlayer.ticksExisted == 0 && autoDisable.isEnabled()) {
            this.toggle();
            return;
        }

        if (IAccess.mc.currentScreen != null && ignoreUI.isEnabled()) {
            return;
        }

        target = !list.isEmpty() ? list.get(0) : null;

        if (
                   (event.getType() == EventType.POST && attackTiming.is("Pre"))
                || (event.getType() == EventType.PRE && attackTiming.is("Post"))
                || attackTiming.is("Legit")
                || target == null
        ) {
            return;
        }

        if(blockMode.is("Watchdog") && mc.thePlayer.getDistanceToEntity(target) > blockRange.getValue()) {
            mc.gameSettings.keyBindUseItem.pressed = false;
        }

        runAttackLoop();
    }


    @Callback
    public void onLegitClickEvent(ClickEvent event) {

        if (target == null) {
            return;
        }

        if (attackTiming.is("Legit"))
            runAttackLoop();
    }

    private void calculateRotations(EntityLivingBase target) {
        float[] rotations = new float[] {0, 0};

        switch (rotationMode.getMode()) {
            case "None": {
                rotations = new float[] { IAccess.mc.thePlayer.rotationYaw, IAccess.mc.thePlayer.rotationPitch };
                break;
            }
            case "Normal": {
                rotations = RotationsUtils.getRotations(target);
                break;
            }
            default: {
                break;
            }
        }

        yaw = rotations[0];
        pitch = rotations[1];

        switch (randomMode.getMode()) {
            case "Normal": {
                yaw += (float) (Math.random() * randomization.getValue());
                pitch += (float) (Math.random() * randomization.getValue());
                break;
            }
            case "Advanced": {
                yaw += RandomUtil.getAdvancedRandom(0, randomization.getValue().floatValue() * 1_000) / 1_000;
                pitch += RandomUtil.getAdvancedRandom(0, randomization.getValue().floatValue() * 1_000) / 1_000;
                break;
            }
            default: {
                break;
            }
        }
    }

    private void runPreBlocking() {
        boolean shouldInteract = blockInteract.isEnabled();

        int chance = (int) Math.round(100 * Math.random());

        if (target != null && !blockMode.is("None")) {
            unblock();
        }

        if (chance <= blockChance.getValue()) {
            switch (blockMode.getMode()) {
                case "Vanilla": {
                    block(true);
                    break;
                }

                case "Watchdog": {
                    if(canBlock()) {
                        blocking = true;

                        PlayerUtil.sendClick(1, true);
                        //mc.gameSettings.keyBindUseItem.pressed = true;
                       // PacketUtil.sendSilent(new C02PacketUseEntity(mc.thePlayer,new Vec3(NaN,NaN,NaN)));
                        //silent
                        IAccess.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(IAccess.mc.thePlayer.inventory.getCurrentItem2()));
                    }
                   // block(true);
                   // PacketUtil.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(NaN, NaN, NaN), 1, null, NaN, NaN, NaN));;
                    //maybe do it on post / pre like noslow
                   // if (sent && mc.thePlayer.ticksExisted % 3 == 0) {
                     //   PacketUtil.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0, 0, 0));
                   // }
                    break;
                }
                case "BlocksMC": {
                    if (IAccess.mc.thePlayer.ticksExisted % 3 == 0)
                        block(shouldInteract);
                    else
                        unblock();
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void runAttackLoop() {
        if (!attacking) {
            return;
        }

        if (attackTimer.hasTimeElapsed(1000 / currentAPS)) {
            switch (attackMode.getMode()) {
                case "Single":
                    target = (!list.isEmpty()) ? list.get(0) : null;

                    if (target != null) {
                        attack(target);
                        calculateRotations(target);
                    }

                    break;
                case "Switch":
                    if (list.isEmpty()) {
                        targetIndex = 0;
                        target = null;
                    } else {
                        if (targetIndex >= list.size()) {
                            targetIndex = 0;
                        }

                        target = list.get(targetIndex);
                        attack(target);
                        calculateRotations(target);

                        if (switchTimer.hasTimeElapsed(switchDelay.getValue())) {
                            targetIndex++;
                            switchTimer.reset();
                        }
                    }

                    break;
                case "Multi":
                    target = !list.isEmpty() ? list.get(0) : null;
                    list.forEach(t -> {
                        attack(t);
                        calculateRotations(t);
                    });
                    break;
            }

            currentAPS = RandomUtil.getAdvancedRandom(
                    minAPS.getValue().floatValue(),
                    maxAPS.getValue().floatValue()
            );

            attackTimer.reset();
        }
    }


    private void runPostBlocking() {
        boolean shouldInteract = blockInteract.isEnabled();

        int chance = (int) Math.round(100 * Math.random());

        if (target != null && !blockMode.is("None")) {
            unblock();
        }

        if (chance <= blockChance.getValue()) {
            if (blockMode.getMode().equals("PostAttack")) {
                block(shouldInteract);
            }
        }
    }

    private void attack(EntityLivingBase entity) {
        if (IAccess.mc.thePlayer.getDistanceToEntity(entity) <= swingRange.getValue()) {
            IAccess.mc.thePlayer.swingItem();
        }

        if (IAccess.mc.thePlayer.getDistanceToEntity(entity) <= blockRange.getValue()) {
            runPreBlocking();
        }

        if (!IAccess.mc.thePlayer.canEntityBeSeen(entity) && IAccess.mc.thePlayer.getDistanceToEntity(entity) > wallsRange.getValue())
            return;

        if (rayTracing.isEnabled() && !RotationsUtils.isMouseOver(yaw, pitch, target, attackRange.getValue().floatValue()))
            return;

        if (IAccess.mc.thePlayer.getDistanceToEntity(entity) <= attackRange.getValue()) {
            IAccess.mc.playerController.attackEntity(IAccess.mc.thePlayer, entity);
            IAccess.mc.thePlayer.onCriticalHit(entity);
        }

        if (IAccess.mc.thePlayer.getDistanceToEntity(entity) <= blockRange.getValue()) {
            runPostBlocking();
        }
    }

    private void updateTargets() {
        this.list = IAccess.mc.theWorld.loadedEntityList

                .stream()

                .filter(entity -> entity instanceof EntityLivingBase)

                .map(entity -> (EntityLivingBase) entity)

                .filter(livingEntity -> {

                    if (!targetPlayers.isEnabled() && livingEntity instanceof EntityPlayer) {
                        return false;
                    }

                    if (
                            !targetAnimals.isEnabled() && (
                                    livingEntity instanceof EntityAnimal
                                    || livingEntity instanceof EntitySquid
                                    || livingEntity instanceof EntityVillager
                            )
                    ) {
                        return false;
                    }

                    if (
                            !targetMonsters.isEnabled() && (
                                    livingEntity instanceof EntityMob
                                    || livingEntity instanceof EntitySlime
                            )
                    ) {
                        return false;
                    }

                    if (!targetInvisible.isEnabled() && livingEntity.isInvisible()) {
                        return false;
                    }

                    if (livingEntity instanceof EntityArmorStand || livingEntity.deathTime != 0 || livingEntity.isDead) {
                        return false;
                    }

                    return livingEntity != IAccess.mc.thePlayer;
                })

                .filter(entity -> IAccess.mc.thePlayer.getDistanceToEntity(entity) <= rotationRange.getValue())

                .sorted(Comparator.comparingDouble(entity -> {
                    switch (sortingMode.getMode()) {
                        case "Health":
                            return entity.getHealth();
                        case "Range":
                            return IAccess.mc.thePlayer.getDistanceToEntity(entity);
                        case "HurtTime":
                            return entity.hurtTime;
                        case "Armor":
                            return entity.getTotalArmorValue();
                        default:
                            return -1;
                    }
                }))

                .collect(Collectors.toList());

        if (this.reverseSorting.isEnabled()) {
            Collections.reverse(list);
        }

    }

    private void block(boolean interact) {
        if (!canBlock()) {
            return;
        }

        if (!blocking) {
            if (interact && target != null && IAccess.mc.objectMouseOver.entityHit == target) {
                IAccess.mc.playerController.interactWithEntitySendPacket(IAccess.mc.thePlayer, target);
            }

            IAccess.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(IAccess.mc.thePlayer.inventory.getCurrentItem()));
            blocking = true;
        }
    }

    private void unblock() {
        if (blocking) {
            if (!IAccess.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                IAccess.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            } else {
                KeyBinding.setKeyBindState(IAccess.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            }
            blocking = false;
        }
    }

    private boolean canBlock() {
        return IAccess.mc.thePlayer.inventory.getCurrentItem() != null && IAccess.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
    }
}
