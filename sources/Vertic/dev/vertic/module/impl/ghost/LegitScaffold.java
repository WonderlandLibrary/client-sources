package dev.vertic.module.impl.ghost;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.motion.PreMotionEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.setting.impl.ModeSetting;
import dev.vertic.setting.impl.NumberSetting;
import dev.vertic.util.math.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemBlock;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class LegitScaffold extends Module {

    private final NumberSetting delay = new NumberSetting("Delay", 75, 0, 200, 5);
    private final BooleanSetting noForward = new BooleanSetting("No forward", true);
    private final BooleanSetting blocksOnly = new BooleanSetting("Blocks only", true);
    private final BooleanSetting sneakInAir = new BooleanSetting("Sneak in air", true);
    private final BooleanSetting onlyOnSneak = new BooleanSetting("Only on sneak", false);

    /*
     * Normal vars
     * */
    private boolean sneaking;
    private final TimerUtil timer = new TimerUtil();

    public LegitScaffold() {
        super("LegitScaffold", "Sneaks at the edges of blocks.", Category.GHOST);
        this.addSettings(delay, noForward, blocksOnly, sneakInAir, onlyOnSneak);
    }

    @Override
    protected void onDisable() {
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) {
            mc.gameSettings.keyBindSneak.setPressed(false);
        }
    }

    @EventLink
    public void onPreMotion(final PreMotionEvent event) {
        long time = 0;

        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() instanceof BlockAir
                && (sneakInAir.isEnabled() || mc.thePlayer.onGround)
                && (!blocksOnly.isEnabled() || (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))
                && (!onlyOnSneak.isEnabled() || Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()))
                && (!noForward.isEnabled() || !Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()))
        ) {
            sneaking = true;
            time = System.currentTimeMillis();
            timer.setTime(System.currentTimeMillis());
            mc.gameSettings.keyBindSneak.setPressed(true);
        } else {
            if (sneaking && timer.hasTimeElapsed(time + delay.getInt())) {
                mc.gameSettings.keyBindSneak.setPressed(false);
                sneaking = false;
            }
        }
    }

}