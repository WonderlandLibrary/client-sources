package rina.turok.bope.bopemod.manager;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.commands.BopeListCommand;
import rina.turok.turok.draw.TurokRenderHelp;

public class BopeEventManager {
   private String tag;
   private Minecraft mc = Minecraft.getMinecraft();

   public BopeEventManager(String tag) {
      this.tag = tag;
   }

   @SubscribeEvent
   public void onUpdate(LivingUpdateEvent event) {
      if (!event.isCanceled()) {
         ;
      }
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (this.mc.player != null) {
         Bope.get_module_manager().update();
      }
   }

   @SubscribeEvent
   public void onWorldRender(RenderWorldLastEvent event) {
      if (!event.isCanceled()) {
         Bope.get_module_manager().render(event);
      }
   }

   @SubscribeEvent
   public void onRender(Post event) {
      if (!event.isCanceled()) {
         ElementType target = ElementType.EXPERIENCE;
         if (!this.mc.player.isCreative() && this.mc.player.getRidingEntity() instanceof AbstractHorse) {
            target = ElementType.HEALTHMOUNT;
         }

         if (event.getType() == target) {
            Bope.get_module_manager().render();
            if (!Bope.get_module_manager().get_module_with_tag("GUI").is_active()) {
               Bope.get_hud_manager().render();
            }

            GL11.glPushMatrix();
            GL11.glEnable(3553);
            GL11.glEnable(3042);
            GlStateManager.enableBlend();
            GL11.glPopMatrix();
            TurokRenderHelp.release_gl();
         }

      }
   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL,
      receiveCanceled = true
   )
   public void onKeyInput(KeyInputEvent event) {
      if (Keyboard.getEventKeyState()) {
         Bope.get_module_manager().bind(Keyboard.getEventKey());
      }

   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL
   )
   public void onChat(ClientChatEvent event) {
      String message = event.getMessage();
      Bope.get_command_manager();
      String[] message_args = BopeCommandManager.command_list.get_message(event.getMessage());
      boolean true_command = false;
      if (message_args.length > 0) {
         if (this.mc.player != null) {
            Bope.send_client_log(this.mc.player.getName() + ": " + message);
         }

         event.setCanceled(true);
         this.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
         Bope.get_command_manager();
         BopeListCommand var10000 = BopeCommandManager.command_list;
         Iterator var5 = BopeListCommand.get_pure_command_list().iterator();

         while(var5.hasNext()) {
            BopeCommand command = (BopeCommand)var5.next();

            try {
               Bope.get_command_manager();
               if (BopeCommandManager.command_list.get_message(event.getMessage())[0].equalsIgnoreCase(command.get_name())) {
                  Bope.get_command_manager();
                  true_command = command.get_message(BopeCommandManager.command_list.get_message(event.getMessage()));
               }
            } catch (Exception var8) {
            }
         }

         if (!true_command) {
            Bope.get_command_manager();
            if (BopeCommandManager.command_list.has_prefix(event.getMessage())) {
               BopeMessage.send_client_message("Try use \"help list\" to get list of commands.");
               true_command = false;
            }
         }
      }

   }

   @SubscribeEvent
   public void onInputUpdate(InputUpdateEvent event) {
      Bope.ZERO_ALPINE_EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
      Bope.ZERO_ALPINE_EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onPlayerPush(PlayerSPPushOutOfBlocksEvent event) {
      Bope.ZERO_ALPINE_EVENT_BUS.post(event);
   }

   public String get_tag() {
      return this.tag;
   }
}
