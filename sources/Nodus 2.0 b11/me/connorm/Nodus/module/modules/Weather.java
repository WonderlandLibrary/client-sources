/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 10:   */ import net.minecraft.server.MinecraftServer;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ 
/* 13:   */ public class Weather
/* 14:   */   extends NodusModule
/* 15:   */ {
/* 16:   */   public Weather()
/* 17:   */   {
/* 18:15 */     super("Weather", Category.WORLD);
/* 19:   */   }
/* 20:   */   
/* 21:   */   @EventTarget
/* 22:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 23:   */   {
/* 24:21 */     Nodus.theNodus.getMinecraft().theWorld.setRainStrength(0.0F);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void onDisable()
/* 28:   */   {
/* 29:26 */     Nodus.theNodus.getMinecraft().theWorld.setRainStrength(MinecraftServer.getServer().getEntityWorld().getRainStrength(1.0F));
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Weather
 * JD-Core Version:    0.7.0.1
 */