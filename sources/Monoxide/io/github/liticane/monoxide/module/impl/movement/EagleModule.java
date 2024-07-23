package io.github.liticane.monoxide.module.impl.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import org.lwjglx.input.Keyboard;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.math.time.TimeHelper;

@ModuleData(name = "Eagle", description = "Makes you shift on block edges.", category = ModuleCategory.MOVEMENT)
public class EagleModule extends Module {
    private final NumberValue<Integer> delay = new NumberValue<Integer>("Sneak Delay", this, 30, 0, 300, 0);

    private final TimeHelper timer = new TimeHelper();

    @Listen
    public final void onMotion(UpdateMotionEvent updateMotionEvent) {
        if (updateMotionEvent.getType() == UpdateMotionEvent.Type.MID) {
            if (Methods.mc.theWorld.getBlockState(new BlockPos(Methods.mc.thePlayer.posX, Methods.mc.thePlayer.posY - 1.0, Methods.mc.thePlayer.posZ)).getBlock() instanceof BlockAir && Methods.mc.thePlayer.onGround) {
                if(timer.hasReached(delay.getValue(), true)) {
                    Methods.mc.gameSettings.keyBindSneak.pressed = true;
                }
            } else {
                Methods.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown(Methods.mc.gameSettings.keyBindSneak.getKeyCode());
            }
        }
    }

    @Override
    public void onDisable() { Methods.mc.gameSettings.keyBindSneak.pressed = false; }
}
