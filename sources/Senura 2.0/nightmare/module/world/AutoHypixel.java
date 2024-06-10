/*     */ package nightmare.module.world;
/*     */ 
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*     */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*     */ import net.minecraft.network.play.server.S02PacketChat;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.event.EventTarget;
/*     */ import nightmare.event.impl.EventLoadWorld;
/*     */ import nightmare.event.impl.EventReceivePacket;
/*     */ import nightmare.event.impl.EventSendPacket;
/*     */ import nightmare.event.impl.EventUpdate;
/*     */ import nightmare.gui.notification.NotificationManager;
/*     */ import nightmare.gui.notification.NotificationType;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ import nightmare.utils.TimerUtils;
/*     */ 
/*     */ public class AutoHypixel
/*     */   extends Module {
/*  22 */   private TimerUtils timer = new TimerUtils();
/*     */   
/*  24 */   public String playCommand = "";
/*     */   
/*     */   private boolean notification = true;
/*     */   
/*     */   private boolean autoplay = false, autogg = false;
/*     */   
/*     */   public AutoHypixel() {
/*  31 */     super("AutoHypixel", 0, Category.WORLD);
/*     */     
/*  33 */     Nightmare.instance.settingsManager.rSetting(new Setting("AutoGG", this, false));
/*  34 */     Nightmare.instance.settingsManager.rSetting(new Setting("AutoPlay", this, false));
/*  35 */     Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 3.0D, 0.0D, 5.0D, true));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventTarget
/*     */   public void onUpdate(EventUpdate event) {
/*  41 */     int delay = (int)Nightmare.instance.settingsManager.getSettingByName(this, "Delay").getValDouble();
/*     */     
/*  43 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "AutoGG").getValBoolean() && 
/*  44 */       this.autogg == true) {
/*  45 */       mc.field_71439_g.func_71165_d("/achat gg");
/*  46 */       this.autogg = false;
/*     */     } 
/*     */ 
/*     */     
/*  50 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "AutoPlay").getValBoolean()) {
/*  51 */       if (this.autoplay == true) {
/*  52 */         if (this.notification == true) {
/*  53 */           NotificationManager.show(NotificationType.INFO, "AutoHypixel", "Sending you to next game", delay * 1000);
/*  54 */           this.notification = false;
/*     */         } 
/*  56 */         if (this.timer.delay((1000 * delay))) {
/*  57 */           mc.field_71439_g.func_71165_d(this.playCommand);
/*  58 */           this.timer.reset();
/*  59 */           this.autoplay = false;
/*  60 */           this.notification = true;
/*     */         } 
/*     */       } else {
/*  63 */         this.timer.reset();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @EventTarget
/*     */   public void onLoadWorld(EventLoadWorld event) {
/*  70 */     this.autoplay = false;
/*  71 */     this.autogg = false;
/*     */   }
/*     */   
/*     */   @EventTarget
/*     */   public void onReceivePacket(EventReceivePacket event) {
/*  76 */     if (event.getPacket() instanceof S02PacketChat) {
/*  77 */       S02PacketChat chatPacket = (S02PacketChat)event.getPacket();
/*  78 */       String chatMessage = chatPacket.func_148915_c().func_150260_c();
/*  79 */       if (chatMessage.contains("WINNER!") || chatMessage.contains("1st Killer -") || chatMessage.contains("Top Survivors")) {
/*  80 */         this.autogg = true;
/*     */       }
/*     */       
/*  83 */       if (chatMessage.contains("WINNER!") || chatMessage.contains("1st Killer -") || chatMessage.contains("Top Survivors") || chatMessage.contains("You died!")) {
/*  84 */         this.autoplay = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventTarget
/*     */   public void onSendPacket(EventSendPacket e) {
/*  92 */     if (this.playCommand.startsWith("/play ")) {
/*  93 */       String display = this.playCommand.replace("/play ", "").replace("_", " ");
/*  94 */       boolean nextUp = true;
/*  95 */       for (char c : display.toCharArray()) {
/*  96 */         if (c == ' ') {
/*  97 */           nextUp = true;
/*     */         
/*     */         }
/* 100 */         else if (nextUp) {
/* 101 */           nextUp = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     if (e.getPacket() instanceof C0EPacketClickWindow) {
/*     */       
/* 108 */       C0EPacketClickWindow packet = (C0EPacketClickWindow)e.getPacket();
/*     */ 
/*     */       
/* 111 */       if (packet.func_149546_g() == null) {
/*     */         return;
/*     */       }
/*     */       
/* 115 */       String itemname = packet.func_149546_g().func_82833_r();
/*     */       
/* 117 */       if (packet.func_149546_g().func_82833_r().startsWith("§a")) {
/* 118 */         int itemID = Item.func_150891_b(packet.func_149546_g().func_77973_b());
/* 119 */         if (itemID == 381 || itemID == 368) {
/* 120 */           if (itemname.contains("SkyWars")) {
/* 121 */             if (itemname.contains("Doubles")) {
/* 122 */               if (itemname.contains("Normal")) {
/* 123 */                 this.playCommand = "/play teams_normal";
/* 124 */               } else if (itemname.contains("Insane")) {
/* 125 */                 this.playCommand = "/play teams_insane";
/*     */               } 
/* 127 */             } else if (itemname.contains("Solo")) {
/* 128 */               if (itemname.contains("Normal")) {
/* 129 */                 this.playCommand = "/play solo_normal";
/* 130 */               } else if (itemname.contains("Insane")) {
/* 131 */                 this.playCommand = "/play solo_insane";
/*     */               } 
/*     */             } 
/*     */           }
/* 135 */         } else if (itemID == 355 && 
/* 136 */           itemname.contains("Bed Wars")) {
/* 137 */           if (itemname.contains("4v4")) {
/* 138 */             this.playCommand = "/play bedwars_four_four";
/* 139 */           } else if (itemname.contains("3v3")) {
/* 140 */             this.playCommand = "/play bedwars_four_three";
/* 141 */           } else if (itemname.contains("Doubles")) {
/* 142 */             this.playCommand = "/play bedwars_eight_two";
/* 143 */           } else if (itemname.contains("Solo")) {
/* 144 */             this.playCommand = "/play bedwars_eight_one";
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 149 */     } else if (e.getPacket() instanceof C01PacketChatMessage) {
/* 150 */       C01PacketChatMessage packet = (C01PacketChatMessage)e.getPacket();
/* 151 */       if (packet.func_149439_c().startsWith("/play"))
/* 152 */         this.playCommand = packet.func_149439_c(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\world\AutoHypixel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */