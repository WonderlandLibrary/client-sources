package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.*;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.mixin.PlayerInteractEntityC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.*;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

@RegisterModule(
    name = "Auto Clicker",
    uniqueId = "autoclicker",
    description = "Automatically Left Clicks For You",
    category = ModuleCategory.Combat
)
public class AutoClicker extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        attacks = 0;
        if (C.mc.options != null) KeyBindUtil.pressKey(
            C.mc.options.attackKey,
            false
        );
        leftDown = false;
    }

    @ConfigOption(
        name = "Only If Holding Sword",
        description = "Only Clicks If Holding A Sword",
        order = 1
    )
    public Boolean onlyHoldingSword = true;

    @ConfigOption(
        name = "Block",
        description = "Blocks Every 5 Or So CPS",
        order = 1
    )
    public Boolean block = true;

    @ConfigOption(
        name = "Only If Left Click Down",
        description = "Only Clicks If Left Click Down",
        order = 2
    )
    public Boolean onlyOnClick = true;

    @ConfigOption(
        name = "Only While Looking At Entity",
        description = "Only Clicks If Looking At Entity",
        order = 3
    )
    public Boolean onlyWhenLockeIn = true;

    @ConfigParentId("donthitselect")
    @ConfigOption(
        name = "Don't Hit Select",
        description = "Swings Constantly Even If Unable To Hit Enemies",
        order = 4
    )
    public Boolean dontHitSelect = false;

    @ConfigMinFor("cps")
    @ConfigChild("donthitselect")
    @ConfigOption(
        name = "Min CPS",
        description = "Minimum CPS",
        order = 4.1,
        max = 20,
        precision = 1
    )
    public Float minCPS = 15f;

    @ConfigMaxFor("cps")
    @ConfigChild("donthitselect")
    @ConfigOption(
        name = "Max CPS",
        description = "Maximum CPS",
        order = 4.2,
        max = 20,
        precision = 1
    )
    public Float maxCPS = 17f;

    @ConfigOption(
        name = "If Not Looking At Block",
        description = "Doesn't CLick If Looking At Blocks",
        order = 5
    )
    public Boolean noBlocks = false;

    int attacks = 0;

    boolean pressedOverride = false;
    boolean leftDown = false;

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre event) {
        if (pressedOverride) {
            if (!C.mc.options.attackKey.isPressed()) {
                leftDown = false;
                lastLeftDown = true;
            }
            KeyBindUtil.pressKey(C.mc.options.attackKey, false);
            pressedOverride = false;
        }
    }

    boolean lastLeftDown = false;

    @SubscribeEvent
    public void onMotionPost(MotionEvent.Post event) {
        EntityHitResult entityHitResult = WorldUtil.rayTraceEntity(
            event.getPitch(),
            event.getYaw(),
            5
        );
        boolean lookingAtEnt =
            entityHitResult != null &&
            entityHitResult.getType() == HitResult.Type.ENTITY;

        BlockHitResult hitResultBlock = WorldUtil.rayTrace(
            C.p().getPitch(),
            C.p().getYaw(),
            5
        );

        if (C.mc.currentScreen != null) {
            attacks = 0;
            return;
        }

        if (
            onlyWhenLockeIn &&
            (!lookingAtEnt ||
                !TargetUtil.shouldTarget(entityHitResult.getEntity()))
        ) {
            attacks = 0;
            return;
        }

        if (onlyOnClick && !C.mc.mouse.wasLeftButtonClicked()) {
            attacks = 0;
            return;
        }

        if (
            onlyHoldingSword &&
            !(C.p().getStackInHand(Hand.MAIN_HAND).getItem() instanceof
                SwordItem)
        ) {
            attacks = 0;
            return;
        }
        if (
            !dontHitSelect &&
            (lastHitEntity != null &&
                lastHitEntity.hurtTime > 5 &&
                C.p().hurtTime < 5)
        ) {
            attacks = 0;
            return;
        }

        if (
            noBlocks &&
            (hitResultBlock != null &&
                hitResultBlock.getType() == HitResult.Type.BLOCK &&
                entityHitResult == null)
        ) {
            attacks = 0;
            if (
                !C.mc.options.attackKey.isPressed() &&
                C.mc.mouse.wasLeftButtonClicked()
            ) KeyBindUtil.pressKey(C.mc.options.attackKey, true);
            return;
        }
        if (attacks > 0 || !dontHitSelect) {
            if (block) {
                if (lastHitEntity != null) {
                    if (
                        C.p()
                            .getInventory()
                            .getStack(C.p().getInventory().selectedSlot)
                            .getItem() instanceof
                        SwordItem
                    ) {
                        if (MovementUtil.ticks % 4 == 0) {
                            C.mc.options.useKey.setPressed(true);
                        } else {
                            C.mc.options.useKey.setPressed(false);
                        }
                    }
                } else C.mc.options.useKey.setPressed(false);
            }

            KeyBindUtil.pressKey(C.mc.options.attackKey, true);
            pressedOverride = true;

            /*
            if (entityHitResult != null && entityHitResult.getEntity() != null)
                PacketUtil.sendPacket(new PlayerInteractEntityC2SPacket(entityHitResult.getEntity().getId(), C.p().isSneaking(), PlayerInteractEntityC2SPacket.ATTACK), false);
                C.p().swingHand(Hand.MAIN_HAND);
             */

            attacks--;
        }
    }

    long lastAttack = 0;
    int nextCps = 10;

    @SubscribeEvent
    public void onRender(RenderTickEvent event) {
        int cpsAverage = (int) (minCPS + ((maxCPS - minCPS) * Math.random()));
        cpsAverage = Math.min(Math.max(1, cpsAverage), 20);
        if (System.currentTimeMillis() - lastAttack > (1000 / nextCps)) {
            nextCps = cpsAverage;
            lastAttack = System.currentTimeMillis();
            attacks++;
        }
    }

    private @Nullable PlayerEntity lastHitEntity = null;

    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Post e) {
        if (e.getPacket() instanceof PlayerInteractEntityC2SPacket p) {
            int eId = ((PlayerInteractEntityC2SPacketAccessor) p).getEntityId();
            if (
                C.w().getEntityById(eId) instanceof PlayerEntity ent &&
                !TargetUtil.isBot(ent)
            ) {
                lastHitEntity = ent;
            }
        }
    }
}
