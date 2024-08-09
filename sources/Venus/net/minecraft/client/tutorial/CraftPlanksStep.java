/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.tutorial;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.tutorial.ITutorialStep;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class CraftPlanksStep
implements ITutorialStep {
    private static final ITextComponent TITLE = new TranslationTextComponent("tutorial.craft_planks.title");
    private static final ITextComponent DESCRIPTION = new TranslationTextComponent("tutorial.craft_planks.description");
    private final Tutorial tutorial;
    private TutorialToast toast;
    private int timeWaiting;

    public CraftPlanksStep(Tutorial tutorial) {
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
                if (clientPlayerEntity.inventory.hasTag(ItemTags.PLANKS)) {
                    this.tutorial.setStep(TutorialSteps.NONE);
                    return;
                }
                if (CraftPlanksStep.hasCrafted(clientPlayerEntity, ItemTags.PLANKS)) {
                    this.tutorial.setStep(TutorialSteps.NONE);
                    return;
                }
            }
            if (this.timeWaiting >= 1200 && this.toast == null) {
                this.toast = new TutorialToast(TutorialToast.Icons.WOODEN_PLANKS, TITLE, DESCRIPTION, false);
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
    public void handleSetSlot(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (ItemTags.PLANKS.contains(item)) {
            this.tutorial.setStep(TutorialSteps.NONE);
        }
    }

    public static boolean hasCrafted(ClientPlayerEntity clientPlayerEntity, ITag<Item> iTag) {
        for (Item item : iTag.getAllElements()) {
            if (clientPlayerEntity.getStats().getValue(Stats.ITEM_CRAFTED.get(item)) <= 0) continue;
            return false;
        }
        return true;
    }
}

