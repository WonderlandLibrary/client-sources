package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.settings.*;
import wwe.utils.*;
import wwe.modules.movement.*;
import wwe.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;

@Mixin(value = { MovementInputFromOptions.class }, priority = 9999)
public class MixinMovementInputFromOptions extends MovementInput
{
    @Shadow
    @Final
    private GameSettings field_78903_e;
    
    public MixinMovementInputFromOptions() {
        super();
    }
    
    @Overwrite
    public void func_78898_a() {
        final ozOH ozOH = new ozOH(this);
        hSNE.Seqo(ozOH);
        if (ozOH.CEMd()) {
            return;
        }
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.gameSettings.keyBindForward.isKeyDown() || this.isKeyDown(this.gameSettings.keyBindForward)) {
            ++this.moveForward;
            this.forwardKeyDown = true;
        }
        else {
            this.forwardKeyDown = false;
        }
        if (this.gameSettings.keyBindBack.isKeyDown() || this.isKeyDown(this.gameSettings.keyBindBack)) {
            --this.moveForward;
            this.backKeyDown = true;
        }
        else {
            this.backKeyDown = false;
        }
        if (this.gameSettings.keyBindLeft.isKeyDown() || this.isKeyDown(this.gameSettings.keyBindLeft)) {
            ++this.moveStrafe;
            this.leftKeyDown = true;
        }
        else {
            this.leftKeyDown = false;
        }
        if (this.gameSettings.keyBindRight.isKeyDown() || this.isKeyDown(this.gameSettings.keyBindRight)) {
            --this.moveStrafe;
            this.rightKeyDown = true;
        }
        else {
            this.rightKeyDown = false;
        }
        this.jump = (this.gameSettings.keyBindJump.isKeyDown() || this.isKeyDown(this.gameSettings.keyBindJump));
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if (this.sneak) {
            this.moveStrafe *= (float)0.3;
            this.moveForward *= (float)0.3;
        }
        final cfjG cfjG = new cfjG(this);
        hSNE.Seqo(cfjG);
        if (cfjG.CEMd()) {
            return;
        }
    }
    
    public boolean isKeyDown(final KeyBinding keyBinding) {
        if (vlwv.hNxW.qHPF((Class<AutoWalk>)NESr.class).IuiN() && keyBinding.equals(Wrapper.mc.gameSettings.keyBindForward)) {
            return true;
        }
        if ((!vlwv.hNxW.qHPF((Class<InventoryWalk>)yorN.class).chat.OnGi() && Wrapper.mc.currentScreen instanceof GuiChat) || (!vlwv.hNxW.qHPF((Class<InventoryWalk>)yorN.class).chat.OnGi() && Wrapper.mc.currentScreen instanceof GuiEditSign)) {
            return false;
        }
        if (vlwv.hNxW.qHPF((Class<InventoryWalk>)yorN.class).IuiN()) {
            return Keyboard.isKeyDown(keyBinding.getKeyCode());
        }
        return keyBinding.isKeyDown();
    }
}
