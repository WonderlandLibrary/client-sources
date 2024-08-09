/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.tutorial;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.tutorial.ITutorialStep;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

public class Tutorial {
    private final Minecraft minecraft;
    @Nullable
    private ITutorialStep tutorialStep;
    private List<ToastTimeInfo> field_244696_c = Lists.newArrayList();

    public Tutorial(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public void handleMovement(MovementInput movementInput) {
        if (this.tutorialStep != null) {
            this.tutorialStep.handleMovement(movementInput);
        }
    }

    public void onMouseMove(double d, double d2) {
        if (this.tutorialStep != null) {
            this.tutorialStep.onMouseMove(d, d2);
        }
    }

    public void onMouseHover(@Nullable ClientWorld clientWorld, @Nullable RayTraceResult rayTraceResult) {
        if (this.tutorialStep != null && rayTraceResult != null && clientWorld != null) {
            this.tutorialStep.onMouseHover(clientWorld, rayTraceResult);
        }
    }

    public void onHitBlock(ClientWorld clientWorld, BlockPos blockPos, BlockState blockState, float f) {
        if (this.tutorialStep != null) {
            this.tutorialStep.onHitBlock(clientWorld, blockPos, blockState, f);
        }
    }

    public void openInventory() {
        if (this.tutorialStep != null) {
            this.tutorialStep.openInventory();
        }
    }

    public void handleSetSlot(ItemStack itemStack) {
        if (this.tutorialStep != null) {
            this.tutorialStep.handleSetSlot(itemStack);
        }
    }

    public void stop() {
        if (this.tutorialStep != null) {
            this.tutorialStep.onStop();
            this.tutorialStep = null;
        }
    }

    public void reload() {
        if (this.tutorialStep != null) {
            this.stop();
        }
        this.tutorialStep = this.minecraft.gameSettings.tutorialStep.create(this);
    }

    public void func_244698_a(TutorialToast tutorialToast, int n) {
        this.field_244696_c.add(new ToastTimeInfo(tutorialToast, n));
        this.minecraft.getToastGui().add(tutorialToast);
    }

    public void func_244697_a(TutorialToast tutorialToast) {
        this.field_244696_c.removeIf(arg_0 -> Tutorial.lambda$func_244697_a$0(tutorialToast, arg_0));
        tutorialToast.hide();
    }

    public void tick() {
        this.field_244696_c.removeIf(Tutorial::lambda$tick$1);
        if (this.tutorialStep != null) {
            if (this.minecraft.world != null) {
                this.tutorialStep.tick();
            } else {
                this.stop();
            }
        } else if (this.minecraft.world != null) {
            this.reload();
        }
    }

    public void setStep(TutorialSteps tutorialSteps) {
        this.minecraft.gameSettings.tutorialStep = tutorialSteps;
        this.minecraft.gameSettings.saveOptions();
        if (this.tutorialStep != null) {
            this.tutorialStep.onStop();
            this.tutorialStep = tutorialSteps.create(this);
        }
    }

    public Minecraft getMinecraft() {
        return this.minecraft;
    }

    public GameType getGameType() {
        return this.minecraft.playerController == null ? GameType.NOT_SET : this.minecraft.playerController.getCurrentGameType();
    }

    public static ITextComponent createKeybindComponent(String string) {
        return new KeybindTextComponent("key." + string).mergeStyle(TextFormatting.BOLD);
    }

    private static boolean lambda$tick$1(ToastTimeInfo toastTimeInfo) {
        return toastTimeInfo.func_244704_a();
    }

    private static boolean lambda$func_244697_a$0(TutorialToast tutorialToast, ToastTimeInfo toastTimeInfo) {
        return toastTimeInfo.field_244701_a == tutorialToast;
    }

    static final class ToastTimeInfo {
        private final TutorialToast field_244701_a;
        private final int field_244702_b;
        private int field_244703_c;

        private ToastTimeInfo(TutorialToast tutorialToast, int n) {
            this.field_244701_a = tutorialToast;
            this.field_244702_b = n;
        }

        private boolean func_244704_a() {
            this.field_244701_a.setProgress(Math.min((float)(++this.field_244703_c) / (float)this.field_244702_b, 1.0f));
            if (this.field_244703_c > this.field_244702_b) {
                this.field_244701_a.hide();
                return false;
            }
            return true;
        }
    }
}

