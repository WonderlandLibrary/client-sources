/*    */ package me.foo.lizardclient.module;
import org.lwjgl.input.Keyboard;

import me.foo.lizardclient.Client;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Module
/*    */ {
/*    */   public String name;
/*    */   private Category category;
/*    */   public int keyBind;
/*    */   private String[] modes;
/*    */   private String description;
/* 16 */   private Boolean enabled = Boolean.valueOf(false);
/*    */   
/* 18 */   protected Minecraft mc = FMLClientHandler.instance().getClient();;
/*    */   
/* 20 */   public Boolean disable = Boolean.valueOf(false);
/*    */   
/*    */   public Module(String name, Category category) {
/* 23 */     this(name, category, null, 0, new String[] { "Default" });
/*    */   }
/*    */   public Module(String name, Category category, String description) {
/* 26 */     this(name, category, description, 0, new String[] { "Default" });
/*    */   }
/*    */   public Module(String name, Category category, int keyBind) {
/* 29 */     this(name, category, null, keyBind, new String[] { "Default" });
/*    */   }
/*    */   public Module(String name, Category category, String description, int keyBind, String... modes) {
/* 32 */     this.name = name;
/* 33 */     this.category = category;
/* 34 */     this.description = description;
/* 35 */     this.keyBind = keyBind;
/* 36 */     this.modes = modes;
/*    */   }
/*    */   
/*    */   public Boolean isEnabled() {
/* 40 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(Boolean enabled) {
/* 44 */     this.enabled = enabled;
/* 45 */     if ((enabled = Boolean.valueOf(true)).booleanValue()) {
/* 46 */       this.disable = Boolean.valueOf(false);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender() {}
/*    */ 
		@SubscribeEvent
		public void onPlayerUpdate(LivingUpdateEvent event) {}

/*    */   
/*    */   public void onPreUpdate() {}
/*    */ 
/*    */   
/*    */   public void onPostUpdate() {}
/*    */ 
/*    */   
/*    */   public void onKeyPressed(KeyInputEvent event) {
/* 63 */     if (Keyboard.getEventCharacter() == this.keyBind) {
/* 64 */       Toggle();
/*    */     }

			Client.onSendChatMessage("Test123 (If you see this, keypresses are working)");
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 69 */     this.disable = Boolean.valueOf(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void Toggle() {
/* 74 */     setEnabled(Boolean.valueOf(!isEnabled().booleanValue()));
/*    */   }
/*    */   
/*    */   public boolean onSendChatMessage(String s) {
/* 78 */     return true;
/*    */   }
/*    */   public boolean onRecieveChatMessage(ClientChatEvent event) {
/* 81 */     return true;
/*    */   }

	public void onClientTick(ClientTickEvent event) {
		
	}
/*    */ }