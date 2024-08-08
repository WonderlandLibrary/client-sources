package com.example.editme.events;

import com.example.editme.EditmeMod;
import com.example.editme.commands.Command;
import com.example.editme.commands.PeekCommand;
import com.example.editme.modules.render.BossStack;
import com.example.editme.util.client.Wrapper;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.render.EditmeTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.world.ChunkEvent.Load;
import net.minecraftforge.event.world.ChunkEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ForgeEventProcessor {
   private int displayWidth;
   private int displayHeight;

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerDrawn(Post var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onEntityJoinWorldEvent(EntityJoinWorldEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onAttackEntity(AttackEntityEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onLeftClickBlock(LeftClickBlock var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent var1) {
      if (Wrapper.getPlayer() != null) {
         ModuleManager.onUpdate();
         EditmeMod.EVENT_BUS.post(new EventClientTick());
      }
   }

   @SubscribeEvent
   public void onWorldTickEvent(WorldTickEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onLivingEntityUseItemEventTick(Start var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onWorldRender(RenderWorldLastEvent var1) {
      if (!var1.isCanceled()) {
         ModuleManager.onWorldRender(var1);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerDrawn(Pre var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onChunkLoaded(Unload var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onChunkLoaded(Load var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onLivingAttackEvent(LivingAttackEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onPlayerPush(PlayerSPPushOutOfBlocksEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onRenderBlockOverlay(RenderBlockOverlayEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onRenderPre(net.minecraftforge.client.event.RenderGameOverlayEvent.Pre var1) {
      if (var1.getType() == ElementType.BOSSINFO && ModuleManager.isModuleEnabled("BossStack")) {
         var1.setCanceled(true);
      }

   }

   @SubscribeEvent
   public void onClientChat(ClientChatReceivedEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onChatSent(ClientChatEvent var1) {
      if (var1.getMessage().startsWith(Command.getCommandPrefix())) {
         var1.setCanceled(true);

         try {
            Wrapper.getMinecraft().field_71456_v.func_146158_b().func_146239_a(var1.getMessage());
            if (var1.getMessage().length() > 1) {
               EditmeMod.getInstance().commandManager.callCommand(var1.getMessage().substring(Command.getCommandPrefix().length() - 1));
            } else {
               Command.sendChatMessage("Please enter a command.");
            }
         } catch (Exception var3) {
            var3.printStackTrace();
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Error occured while running command! (").append(var3.getMessage()).append(")")));
         }

         var1.setMessage("");
      }

   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL,
      receiveCanceled = true
   )
   public void onKeyInput(KeyInputEvent var1) {
      if (Keyboard.getEventKeyState()) {
         ModuleManager.onBind(Keyboard.getEventKey());
      }

   }

   @SubscribeEvent
   public void onMouseEvent(MouseEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
      if (var1.isCanceled()) {
         var1.setCanceled(true);
      }

   }

   @SubscribeEvent
   public void onFogRender(FogDensity var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onInputUpdate(InputUpdateEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }

   @SubscribeEvent
   public void onUpdate(LivingUpdateEvent var1) {
      if (!var1.isCanceled()) {
         if (Minecraft.func_71410_x().field_71443_c != this.displayWidth || Minecraft.func_71410_x().field_71440_d != this.displayHeight) {
            EditmeMod.EVENT_BUS.post(new DisplaySizeChangedEvent());
            this.displayWidth = Minecraft.func_71410_x().field_71443_c;
            this.displayHeight = Minecraft.func_71410_x().field_71440_d;
         }

         if (PeekCommand.sb != null) {
            ScaledResolution var2 = new ScaledResolution(Minecraft.func_71410_x());
            int var3 = var2.func_78326_a();
            int var4 = var2.func_78328_b();
            GuiShulkerBox var5 = new GuiShulkerBox(Wrapper.getPlayer().field_71071_by, PeekCommand.sb);
            var5.func_146280_a(Wrapper.getMinecraft(), var3, var4);
            Minecraft.func_71410_x().func_147108_a(var5);
            PeekCommand.sb = null;
         }

      }
   }

   @SubscribeEvent
   public void onRender(net.minecraftforge.client.event.RenderGameOverlayEvent.Post var1) {
      if (!var1.isCanceled()) {
         ElementType var2 = ElementType.EXPERIENCE;
         if (!Wrapper.getPlayer().func_184812_l_() && Wrapper.getPlayer().func_184187_bx() instanceof AbstractHorse) {
            var2 = ElementType.HEALTHMOUNT;
         }

         if (var1.getType() == var2) {
            ModuleManager.onRender();
            GL11.glPushMatrix();
            GL11.glPopMatrix();
            EditmeTessellator.releaseGL();
         } else if (var1.getType() == ElementType.BOSSINFO && ModuleManager.isModuleEnabled("BossStack")) {
            BossStack.render(var1);
         }

      }
   }

   @SubscribeEvent
   public void onLivingDamageEvent(LivingDamageEvent var1) {
      EditmeMod.EVENT_BUS.post(var1);
   }
}
