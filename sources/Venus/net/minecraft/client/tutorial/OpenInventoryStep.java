/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.tutorial;

import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.tutorial.ITutorialStep;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class OpenInventoryStep
implements ITutorialStep {
    private static final ITextComponent TITLE = new TranslationTextComponent("tutorial.open_inventory.title");
    private static final ITextComponent DESCRIPTION = new TranslationTextComponent("tutorial.open_inventory.description", Tutorial.createKeybindComponent("inventory"));
    private final Tutorial tutorial;
    private TutorialToast toast;
    private int timeWaiting;

    public OpenInventoryStep(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    @Override
    public void tick() {
        ++this.timeWaiting;
        if (this.tutorial.getGameType() != GameType.SURVIVAL) {
            this.tutorial.setStep(TutorialSteps.NONE);
        } else if (this.timeWaiting >= 600 && this.toast == null) {
            this.toast = new TutorialToast(TutorialToast.Icons.RECIPE_BOOK, TITLE, DESCRIPTION, false);
            this.tutorial.getMinecraft().getToastGui().add(this.toast);
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
    public void openInventory() {
        this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
    }
}

