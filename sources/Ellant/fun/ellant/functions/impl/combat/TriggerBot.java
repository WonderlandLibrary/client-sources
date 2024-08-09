package fun.ellant.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.AttackUtil;
import fun.ellant.utils.player.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.command.friends.FriendStorage;

@FunctionRegister(name = "TriggerBot", type = Category.COMBAT, desc = "Типо килка но ручная")
public class TriggerBot extends Function {

    private final BooleanSetting players = new BooleanSetting("Игроки", true);
    private final BooleanSetting mobs = new BooleanSetting("Мобы", true);
    private final BooleanSetting animals = new BooleanSetting("Животные", true);
    private final BooleanSetting onlyCrit = new BooleanSetting("Только криты", true);
    private final BooleanSetting shieldBreak = new BooleanSetting("Ломать щит", false);

    public TriggerBot() {
        addSettings(players, mobs, animals, onlyCrit, shieldBreak);
    }

    private final StopWatch stopWatch = new StopWatch();

    @Subscribe
    public void onUpdate(EventUpdate e) {
        Entity entity = getValidEntity();

        if (entity == null || mc.player == null) {
            return;
        }

        if (shouldAttack()) {
            stopWatch.setLastMS(500);
            attackEntity(entity);
        }
    }

    private boolean shouldAttack() {
        return AttackUtil.isPlayerFalling(onlyCrit.get(), true, false) && (stopWatch.hasTimeElapsed());
    }

    private void attackEntity(Entity entity) {
        boolean shouldStopSprinting = false;
        if (onlyCrit.get() && CEntityActionPacket.lastUpdatedSprint) {
            mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
            shouldStopSprinting = true;
        }

        mc.playerController.attackEntity(mc.player, entity);
        mc.player.swingArm(Hand.MAIN_HAND);
        if (shieldBreak.get() && entity instanceof PlayerEntity)
            breakShieldPlayer(entity);

        if (shouldStopSprinting) {
            mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
        }
    }

    private Entity getValidEntity() {
        if (mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) mc.objectMouseOver).getEntity();
            if (checkEntity((LivingEntity) entity)) {
                return entity;
            }
        }
        return null;
    }

    public static void breakShieldPlayer(Entity entity) {
        if (((LivingEntity) entity).isBlocking()) {
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

    private boolean checkEntity(LivingEntity entity) {
        if (FriendStorage.isFriend(entity.getName().getString())) {
            return false; // не бей своих сучка
        }

        AttackUtil entitySelector = new AttackUtil();

        if (players.get()) {
            entitySelector.apply(AttackUtil.EntityType.PLAYERS);
        }
        if (mobs.get()) {
            entitySelector.apply(AttackUtil.EntityType.MOBS);
        }
        if (animals.get()) {
            entitySelector.apply(AttackUtil.EntityType.ANIMALS);
        }
        return entitySelector.ofType(entity, entitySelector.build()) != null && entity.isAlive();
    }

    @Override
    public void onDisable() {
        stopWatch.reset();
        super.onDisable();
    }
}