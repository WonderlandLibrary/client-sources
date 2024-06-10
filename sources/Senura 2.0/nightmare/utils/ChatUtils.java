/*    */ package nightmare.utils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import nightmare.Nightmare;
/*    */ 
/*    */ public class ChatUtils
/*    */ {
/*    */   public static void sendPrivateChatMessage(String message) {
/* 11 */     message = "[" + Nightmare.instance.getName() + "] " + message;
/*    */     
/* 13 */     (Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(message));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\ChatUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */