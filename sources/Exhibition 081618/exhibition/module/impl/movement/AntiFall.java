package exhibition.module.impl.movement;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMove;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.module.impl.player.NoFall;
import exhibition.util.Timer;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public class AntiFall extends Module {
   private Timer timer = new Timer();
   private boolean saveMe;
   private String VOID = "VOID";
   private String DISTANCE = "DIST";

   public AntiFall(ModuleData data) {
      super(data);
      this.settings.put(this.VOID, new Setting(this.VOID, true, "Only catch when falling into void."));
      this.settings.put(this.DISTANCE, new Setting(this.DISTANCE, Integer.valueOf(2), "The fall distance needed to catch.", 1.0D, 2.0D, 10.0D));
   }

   @RegisterEvent(
      events = {EventMove.class, EventPacket.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMove) {
         label65: {
            EventMove em = (EventMove)event.cast();
            if ((!this.isBlockUnder() || this.saveMe || !((Module)Client.getModuleManager().get(NoFall.class)).isEnabled()) && !mc.thePlayer.capabilities.isFlying) {
               if ((!this.saveMe || !this.timer.delay(250.0F)) && !mc.thePlayer.isCollidedVertically) {
                  int dist = ((Number)((Setting)this.settings.get(this.DISTANCE)).getValue()).intValue();
                  if (mc.thePlayer.fallDistance >= (float)dist && !Client.getModuleManager().isEnabled(Fly.class) && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() == Blocks.air && (!((Boolean)((Setting)this.settings.get(this.VOID)).getValue()).booleanValue() || !this.isBlockUnder())) {
                     if (!this.saveMe) {
                        this.saveMe = true;
                        this.timer.reset();
                     } else {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + (double)dist, mc.thePlayer.posZ);
                     }
                  }
                  break label65;
               }

               this.saveMe = false;
               this.timer.reset();
               return;
            }

            return;
         }
      }

      if (event instanceof EventPacket) {
         EventPacket ep = (EventPacket)event;
         if (ep.isIncoming() && ep.getPacket() instanceof S08PacketPlayerPosLook) {
            if (mc.thePlayer.fallDistance > 0.0F) {
               mc.thePlayer.fallDistance = 0.0F;
            }

            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
            this.saveMe = false;
            this.timer.reset();
         }
      }

   }

   private boolean isBlockUnder() {
      for(int i = (int)mc.thePlayer.posY; i > 0; --i) {
         BlockPos pos = new BlockPos(mc.thePlayer.posX, (double)i, mc.thePlayer.posZ);
         if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
            return true;
         }
      }

      return false;
   }
}
