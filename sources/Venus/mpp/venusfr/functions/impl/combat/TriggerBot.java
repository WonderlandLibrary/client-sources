/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.AttackUtil;
import mpp.venusfr.utils.player.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

@FunctionRegister(name="TriggerBot", type=Category.Combat)
public class TriggerBot
extends Function {
    private final BooleanSetting players = new BooleanSetting("\u0418\u0433\u0440\u043e\u043a\u0438", true);
    private final BooleanSetting mobs = new BooleanSetting("\u041c\u043e\u0431\u044b", true);
    private final BooleanSetting animals = new BooleanSetting("\u0416\u0438\u0432\u043e\u0442\u043d\u044b\u0435", true);
    private final BooleanSetting onlyCrit = new BooleanSetting("\u0422\u043e\u043b\u044c\u043a\u043e \u043a\u0440\u0438\u0442\u044b", true);
    private final BooleanSetting shieldBreak = new BooleanSetting("\u041b\u043e\u043c\u0430\u0442\u044c \u0449\u0438\u0442", false);
    private final StopWatch stopWatch = new StopWatch();

    public TriggerBot() {
        this.addSettings(this.players, this.mobs, this.animals, this.onlyCrit, this.shieldBreak);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        Entity entity2 = this.getValidEntity();
        if (entity2 == null || TriggerBot.mc.player == null) {
            return;
        }
        if (this.shouldAttack()) {
            this.stopWatch.setLastMS(500L);
            this.attackEntity(entity2);
        }
    }

    private boolean shouldAttack() {
        return AttackUtil.isPlayerFalling((Boolean)this.onlyCrit.get(), true, false) && this.stopWatch.hasTimeElapsed();
    }

    private void attackEntity(Entity entity2) {
        boolean bl = false;
        if (((Boolean)this.onlyCrit.get()).booleanValue() && CEntityActionPacket.lastUpdatedSprint) {
            TriggerBot.mc.player.connection.sendPacket(new CEntityActionPacket(TriggerBot.mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
            bl = true;
        }
        TriggerBot.mc.playerController.attackEntity(TriggerBot.mc.player, entity2);
        TriggerBot.mc.player.swingArm(Hand.MAIN_HAND);
        if (((Boolean)this.shieldBreak.get()).booleanValue() && entity2 instanceof PlayerEntity) {
            TriggerBot.breakShieldPlayer(entity2);
        }
        if (bl) {
            TriggerBot.mc.player.connection.sendPacket(new CEntityActionPacket(TriggerBot.mc.player, CEntityActionPacket.Action.START_SPRINTING));
        }
    }

    private Entity getValidEntity() {
        Entity entity2;
        if (TriggerBot.mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY && this.checkEntity((LivingEntity)(entity2 = ((EntityRayTraceResult)TriggerBot.mc.objectMouseOver).getEntity()))) {
            return entity2;
        }
        return null;
    }

    public static void breakShieldPlayer(Entity entity2) {
        if (((LivingEntity)entity2).isBlocking()) {
            int n = InventoryUtil.getInstance().getAxeInInventory(true);
            int n2 = InventoryUtil.getInstance().getAxeInInventory(false);
            if (n2 == -1 && n != -1) {
                int n3 = InventoryUtil.getInstance().findBestSlotInHotBar();
                TriggerBot.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, TriggerBot.mc.player);
                TriggerBot.mc.playerController.windowClick(0, n3 + 36, 0, ClickType.PICKUP, TriggerBot.mc.player);
                TriggerBot.mc.player.connection.sendPacket(new CHeldItemChangePacket(n3));
                TriggerBot.mc.playerController.attackEntity(TriggerBot.mc.player, entity2);
                TriggerBot.mc.player.swingArm(Hand.MAIN_HAND);
                TriggerBot.mc.player.connection.sendPacket(new CHeldItemChangePacket(TriggerBot.mc.player.inventory.currentItem));
                TriggerBot.mc.playerController.windowClick(0, n3 + 36, 0, ClickType.PICKUP, TriggerBot.mc.player);
                TriggerBot.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, TriggerBot.mc.player);
            }
            if (n2 != -1) {
                TriggerBot.mc.player.connection.sendPacket(new CHeldItemChangePacket(n2));
                TriggerBot.mc.playerController.attackEntity(TriggerBot.mc.player, entity2);
                TriggerBot.mc.player.swingArm(Hand.MAIN_HAND);
                TriggerBot.mc.player.connection.sendPacket(new CHeldItemChangePacket(TriggerBot.mc.player.inventory.currentItem));
            }
        }
    }

    private boolean checkEntity(LivingEntity livingEntity) {
        if (FriendStorage.isFriend(livingEntity.getName().getString())) {
            return true;
        }
        AttackUtil attackUtil = new AttackUtil();
        if (((Boolean)this.players.get()).booleanValue()) {
            attackUtil.apply(AttackUtil.EntityType.PLAYERS);
        }
        if (((Boolean)this.mobs.get()).booleanValue()) {
            attackUtil.apply(AttackUtil.EntityType.MOBS);
        }
        if (((Boolean)this.animals.get()).booleanValue()) {
            attackUtil.apply(AttackUtil.EntityType.ANIMALS);
        }
        return attackUtil.ofType(livingEntity, attackUtil.build()) != null && livingEntity.isAlive();
    }

    @Override
    public void onDisable() {
        this.stopWatch.reset();
        super.onDisable();
    }
}

