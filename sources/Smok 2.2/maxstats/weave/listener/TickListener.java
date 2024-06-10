package maxstats.weave.listener;

import java.util.ArrayList;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.rats.impl.visual.Esp;
import me.sleepyfish.smok.rats.impl.blatant.Aura;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.render.RenderUtils;
import me.sleepyfish.smok.rats.impl.legit.Auto_Tool;
import me.sleepyfish.smok.rats.impl.legit.Aim_Assist;
import me.sleepyfish.smok.utils.entities.TargetUtils;
import me.sleepyfish.smok.rats.impl.visual.Target_Hud;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.RenderWorldEvent;
import net.weavemc.loader.api.event.TickEvent;

// Class from SMok Client by SleepyFish
public class TickListener {

    public static ArrayList<BlockPos> bedPos = new ArrayList<>();

    private Timer timer = new Timer();

   @SubscribeEvent
   public void onTick(TickEvent e) {
       EventTick event = new EventTick();
       event.call();
   }

    @SubscribeEvent
    public void onMyEvent(RenderWorldEvent e) {
        if (Smok.inst.injected) {
            if (Smok.inst.rotManager.isRotating() && Smok.inst.rotManager.rayTracePos != null && Smok.inst.rotManager.renderRayTrace) {
                RenderUtils.drawBlock(Smok.inst.mc.objectMouseOver.getBlockPos(), ColorUtils.getClientColor(1).getRGB());
            }

            if (Smok.inst.ratManager.getRatByClass(Esp.class).isEnabled() || Auto_Tool.bedCheck.isEnabled()) {
                if (Esp.bedESP.isEnabled() || Auto_Tool.bedCheck.isEnabled()) {
                    double breakRange = Esp.bedESPRange.getValue();

                    if (this.timer.delay(25L)) {
                        for (double y = breakRange; y >= -breakRange; --y) {
                            for (double x = -breakRange; x <= breakRange; ++x) {
                                for (double z = -breakRange; z <= breakRange; ++z) {
                                    BlockPos block = new BlockPos(Smok.inst.mc.thePlayer.posX + x, Smok.inst.mc.thePlayer.posY + y, Smok.inst.mc.thePlayer.posZ + z);

                                    if (Utils.getBlock(block) == Blocks.bed) {
                                        ClientUtils.bedNearby = true;
                                        if (!bedPos.contains(block)) {
                                            bedPos.add(block);
                                            this.timer.reset();
                                        }
                                    } else {
                                        ClientUtils.bedNearby = false;

                                        if (bedPos.contains(block)) {
                                            bedPos.remove(block);
                                            this.timer.reset();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (bedPos != null) {
                        int count = 0;

                        for (BlockPos pos : bedPos) {
                            RenderUtils.drawBlock(pos, ColorUtils.getClientColor(count * 100).getRGB());
                            count++;
                        }
                    }
                }
            }

            if (Smok.inst.ratManager.getRatByClass(Aim_Assist.class).isEnabled() || Smok.inst.ratManager.getRatByClass(Target_Hud.class).isEnabled() || Smok.inst.ratManager.getRatByClass(Aura.class).isEnabled()) {
                TargetUtils.onUpdate();
            }
        }
    }

}