/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.engine.clicking;

import de.dietrichpaul.clientbase.event.KeyPressedStateListener;
import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;

public class ClickEngine implements ClickCallback, KeyPressedStateListener {

    private final Set<ClickSpoof> spoofs = new HashSet<>();

    private int left;
    private int right;
    private boolean enableLeft;
    private boolean enableRight;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public ClickEngine() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(KeyPressedStateListener.class, this);
    }

    public void add(ClickSpoof clickSpoof) {
        spoofs.add(clickSpoof);
    }

    public void update() {
        enableLeft = false;
        enableRight = false;
        spoofs.stream()
                .filter(ClickSpoof::isToggled)
                .filter(ClickSpoof::canClick)
                .max(Comparator.comparingInt(ClickSpoof::getPriority))
                .ifPresent(spoof -> spoof.click(this));
    }

    @Override
    public void onKeyPressedState(KeyPressedStateEvent keyPressedStateEvent) {
        if (keyPressedStateEvent.keyBinding == mc.options.attackKey && enableLeft || keyPressedStateEvent.keyBinding == mc.options.useKey && enableRight)
            keyPressedStateEvent.pressed = true;
    }

    @Override
    public void left() {
        if (mc.attackCooldown > 0) return;
        mc.options.attackKey.timesPressed++;
        enableLeft = true;
    }

    @Override
    public void right() {
        mc.options.useKey.timesPressed++;
        enableRight = true;
    }

    @Override
    public void attackBlock(BlockPos pos, Direction side) {
        mc.interactionManager.attackBlock(pos, side);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    @Override
    public void useBlock(BlockPos pos, Hand hand, BlockHitResult hitResult) {
        ItemStack itemStack = mc.player.getStackInHand(hand);

        int count = itemStack.getCount();
        ActionResult interactBlock = mc.interactionManager.interactBlock(mc.player, hand, hitResult);
        if (interactBlock.isAccepted()) {
            if (interactBlock.shouldSwingHand()) {
                mc.player.swingHand(hand);
                if (!itemStack.isEmpty() && (itemStack.getCount() != count || mc.interactionManager.hasCreativeInventory())) {
                    mc.gameRenderer.firstPersonRenderer.resetEquipProgress(hand);
                }
            }
            return;
        }

        if (interactBlock == ActionResult.FAIL) {
            return;
        }

        if (!itemStack.isEmpty()) {
            ActionResult interactItem = mc.interactionManager.interactItem(mc.player, hand);
            if (interactItem.isAccepted()) {
                if (interactItem.shouldSwingHand()) {
                    mc.player.swingHand(hand);
                }
                mc.gameRenderer.firstPersonRenderer.resetEquipProgress(hand);
            }
        }
    }

    @Override
    public void attackEntity(Entity entity) {
        mc.interactionManager.attackEntity(mc.player, entity);
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}
