// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import java.util.Random;
import events.listeners.EventUpdate;
import events.listeners.EventPreMotion;
import events.Event;
import net.minecraft.client.settings.KeyBinding;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Eagle extends Module
{
    public Eagle() {
        super("Eagle", Type.World, "Eagle", 0, Category.World);
        Aqua.setmgr.register(new Setting("PlaceTicks", this, 2.0, 1.0, 20.0, false));
        Aqua.setmgr.register(new Setting("PlaceTicks2", this, 2.0, 1.0, 20.0, false));
        Aqua.setmgr.register(new Setting("OnlyOnAir", this, true));
        Aqua.setmgr.register(new Setting("RandomPlaceTicks", this, true));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPreMotion) {}
        if (event instanceof EventUpdate) {
            Eagle.mc.thePlayer.setSprinting(false);
            Eagle.mc.gameSettings.keyBindSprint.pressed = false;
            final float ticks1 = (float)Aqua.setmgr.getSetting("EaglePlaceTicks").getCurrentNumber();
            final float ticks2 = (float)Aqua.setmgr.getSetting("EaglePlaceTicks2").getCurrentNumber();
            final float TICKS = (float)MathHelper.getRandomDoubleInRange(new Random(), ticks1, ticks2);
            final float roundTicks = (float)Math.round(TICKS);
            final float ticks3 = Aqua.setmgr.getSetting("EagleRandomPlaceTicks").isState() ? roundTicks : ((float)Aqua.setmgr.getSetting("EaglePlaceTicks").getCurrentNumber());
            final BlockPos blockPos = new BlockPos(Eagle.mc.thePlayer.posX, Eagle.mc.thePlayer.posY - 1.0, Eagle.mc.thePlayer.posZ);
            Label_0240: {
                if (Aqua.setmgr.getSetting("EagleOnlyOnAir").isState()) {
                    if (Eagle.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air || Eagle.mc.thePlayer.ticksExisted % Math.round(ticks3) != 0) {
                        break Label_0240;
                    }
                }
                else if (Eagle.mc.thePlayer.ticksExisted % Math.round(ticks3) != 0) {
                    break Label_0240;
                }
                Eagle.mc.rightClickMouse();
            }
            Eagle.mc.gameSettings.keyBindSneak.pressed = (Eagle.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air);
        }
    }
}
