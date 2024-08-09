package wtf.resolute.moduled.impl.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.utiled.client.IMinecraft;
import wtf.resolute.utiled.math.TimerUtil;

@ModuleAnontion(name = "FTclip", type = Categories.Movement,server = "FT")
public class FTclip extends Module {

    private final TimerUtil timer = new TimerUtil();

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (event instanceof EventUpdate && IMinecraft.mc.world.getBlockState(new BlockPos(IMinecraft.mc.player.getPosX(), IMinecraft.mc.player.getPosY() + 1.0, IMinecraft.mc.player.getPosZ())).getBlock() == Blocks.AIR && this.timer.hasTimeElapsed(500L) && !mc.player.isOnGround()) {
            IMinecraft.mc.player.setPosition(IMinecraft.mc.player.getPosX(), IMinecraft.mc.player.getPosY() - 35.0, IMinecraft.mc.player.getPosZ());
            this.timer.reset();
        }

    }
}
