/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DemoPlayerInteractionManager
extends PlayerInteractionManager {
    private boolean displayedIntro;
    private boolean demoTimeExpired;
    private int demoEndedReminder;
    private int gameModeTicks;

    public DemoPlayerInteractionManager(ServerWorld serverWorld) {
        super(serverWorld);
    }

    @Override
    public void tick() {
        super.tick();
        ++this.gameModeTicks;
        long l = this.world.getGameTime();
        long l2 = l / 24000L + 1L;
        if (!this.displayedIntro && this.gameModeTicks > 20) {
            this.displayedIntro = true;
            this.player.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241769_f_, 0.0f));
        }
        boolean bl = this.demoTimeExpired = l > 120500L;
        if (this.demoTimeExpired) {
            ++this.demoEndedReminder;
        }
        if (l % 24000L == 500L) {
            if (l2 <= 6L) {
                if (l2 == 6L) {
                    this.player.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241769_f_, 104.0f));
                } else {
                    this.player.sendMessage(new TranslationTextComponent("demo.day." + l2), Util.DUMMY_UUID);
                }
            }
        } else if (l2 == 1L) {
            if (l == 100L) {
                this.player.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241769_f_, 101.0f));
            } else if (l == 175L) {
                this.player.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241769_f_, 102.0f));
            } else if (l == 250L) {
                this.player.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241769_f_, 103.0f));
            }
        } else if (l2 == 5L && l % 24000L == 22000L) {
            this.player.sendMessage(new TranslationTextComponent("demo.day.warning"), Util.DUMMY_UUID);
        }
    }

    private void sendDemoReminder() {
        if (this.demoEndedReminder > 100) {
            this.player.sendMessage(new TranslationTextComponent("demo.reminder"), Util.DUMMY_UUID);
            this.demoEndedReminder = 0;
        }
    }

    @Override
    public void func_225416_a(BlockPos blockPos, CPlayerDiggingPacket.Action action, Direction direction, int n) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
        } else {
            super.func_225416_a(blockPos, action, direction, n);
        }
    }

    @Override
    public ActionResultType processRightClick(ServerPlayerEntity serverPlayerEntity, World world, ItemStack itemStack, Hand hand) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return ActionResultType.PASS;
        }
        return super.processRightClick(serverPlayerEntity, world, itemStack, hand);
    }

    @Override
    public ActionResultType func_219441_a(ServerPlayerEntity serverPlayerEntity, World world, ItemStack itemStack, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return ActionResultType.PASS;
        }
        return super.func_219441_a(serverPlayerEntity, world, itemStack, hand, blockRayTraceResult);
    }
}

