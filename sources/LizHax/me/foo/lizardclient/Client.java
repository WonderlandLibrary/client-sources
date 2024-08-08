package me.foo.lizardclient;

import java.util.ArrayList;
import java.util.List;

import me.foo.lizardclient.command.CommandManager;
import me.foo.lizardclient.module.Module;
import me.foo.lizardclient.module.ModuleManager;
import me.foo.lizardclient.ui.UIRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@Mod(modid = Client.MODID, name = Client.NAME, version = Client.VERSION)
@Mod.EventBusSubscriber
public class Client {
   public static String clientName = "Lizard Client";
   public static String clientVersion = "a1.2";
   
   public static UIRenderer uiRenderer;
   
   public static ModuleManager moduleManager;
   
   public static CommandManager commandManager;
   public static List<Block> espblocks = new ArrayList<>();
   
   public static Minecraft mc = FMLClientHandler.instance().getClient();
   
	//Forge
			
	public static final String MODID = "null-client";
	public static final String NAME = "NULL Client";
	public static final String VERSION = "1.0";

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		
		uiRenderer = new UIRenderer();
		moduleManager = new ModuleManager();
		commandManager = new CommandManager();
		moduleManager.Init();
	}
	
	@SubscribeEvent
	public static void onGui(RenderGameOverlayEvent event) {
		uiRenderer.draw();
	}
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		for(Module module : moduleManager.getEnabledModules())
			module.onClientTick(event);
	}
  
/*    */   public static void onPreUpdate() {
/* 48 */     for (Module module : moduleManager.getEnabledModules()) {
/* 49 */       module.onPreUpdate();
/*    */     }
/*    */   }
/*    */   
/*    */   public static void onPostUpdate() {
/* 54 */     for (Module module : moduleManager.getEnabledModules()) {
/* 55 */       module.onPostUpdate();
/*    */     }
/*    */   }

		@SubscribeEvent
		public static void onKeyPressed(KeyInputEvent event) {
/* 60 */     for (Module module : moduleManager.moduleList) {
/* 61 */       module.onKeyPressed(event);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void addChatMessage(String s) {
/* 66 */     mc.player.sendMessage((ITextComponent)new TextComponentString("§lNull Client: §r" + s));
/*    */   }
/*    */   
/*    */   public static boolean onSendChatMessage(String s) {
/* 70 */     if (s.startsWith(".")) {
/* 71 */       commandManager.callCommand(s.substring(1));
/* 72 */       return false;
/*    */     } 
/* 74 */     for (Module m : moduleManager.moduleList) {
/* 75 */       if (m.isEnabled().booleanValue()) {
/* 76 */         return m.onSendChatMessage(s);
			}
		} 
		return true;
	}

	@SubscribeEvent
	public static boolean onReceiveChatMessage(ClientChatEvent event) {
/* 83 */     for (Module m : moduleManager.moduleList) {
/* 84 */       if (m.isEnabled().booleanValue()) {
/* 85 */         return m.onRecieveChatMessage(event);
/*    */       }
/*    */     } 
/*    */     
/* 89 */     return true;
/*    */   }
/*    */ }