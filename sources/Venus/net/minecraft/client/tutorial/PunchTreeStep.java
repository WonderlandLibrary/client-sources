/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.tutorial;

import net.minecraft.block.BlockState;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.tutorial.FindTreeStep;
import net.minecraft.client.tutorial.ITutorialStep;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class PunchTreeStep
implements ITutorialStep {
    private static final ITextComponent TITLE = new TranslationTextComponent("tutorial.punch_tree.title");
    private static final ITextComponent DESCRIPTION = new TranslationTextComponent("tutorial.punch_tree.description", Tutorial.createKeybindComponent("attack"));
    private final Tutorial tutorial;
    private TutorialToast toast;
    private int timeWaiting;
    private int resetCount;

    public PunchTreeStep(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    @Override
    public void tick() {
        ++this.timeWaiting;
        if (this.tutorial.getGameType() != GameType.SURVIVAL) {
            this.tutorial.setStep(TutorialSteps.NONE);
        } else {
            ClientPlayerEntity clientPlayerEntity;
            if (this.timeWaiting == 1 && (clientPlayerEntity = this.tutorial.getMinecraft().player) != null) {
                if (clientPlayerEntity.inventory.hasTag(ItemTags.LOGS)) {
                    this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                    return;
                }
                if (FindTreeStep.hasPunchedTreesPreviously(clientPlayerEntity)) {
                    this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                    return;
                }
            }
            if ((this.timeWaiting >= 600 || this.resetCount > 3) && this.toast == null) {
                this.toast = new TutorialToast(TutorialToast.Icons.TREE, TITLE, DESCRIPTION, true);
                this.tutorial.getMinecraft().getToastGui().add(this.toast);
            }
        }
    }

    @Override
    public void onStop() {
        if (this.toast != null) {
            this.toast.hide();
            this.toast = null;
        }
    }

    @Override
    public void onHitBlock(ClientWorld clientWorld, BlockPos blockPos, BlockState blockState, float f) {
        boolean bl = blockState.isIn(BlockTags.LOGS);
        if (bl && f > 0.0f) {
            if (this.toast != null) {
                this.toast.setProgress(f);
            }
            if (f >= 1.0f) {
                this.tutorial.setStep(TutorialSteps.OPEN_INVENTORY);
            }
        } else if (this.toast != null) {
            this.toast.setProgress(0.0f);
        } else if (bl) {
            ++this.resetCount;
        }
    }

    @Override
    public void handleSetSlot(ItemStack itemStack) {
        if (ItemTags.LOGS.contains(itemStack.getItem())) {
            this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
        }
    }
}

