package net.minecraft.client.stream;

import java.util.HashMap;
import java.util.HashSet;
import net.agv;
import net.minecraft.client.stream.ChatController$2;
import net.minecraft.client.stream.ChatController$ChatChannelListener;
import net.minecraft.client.stream.ChatController$ChatListener;
import net.minecraft.client.stream.ChatController$ChatState;
import net.minecraft.client.stream.ChatController$EnumChannelState;
import net.minecraft.client.stream.ChatController$EnumEmoticonMode;
import net.minecraft.client.stream.TwitchStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tv.twitch.AuthToken;
import tv.twitch.Core;
import tv.twitch.ErrorCode;
import tv.twitch.StandardCoreAPI;
import tv.twitch.chat.Chat;
import tv.twitch.chat.ChatEmoticonData;
import tv.twitch.chat.ChatTokenizationOption;
import tv.twitch.chat.IChatAPIListener;
import tv.twitch.chat.StandardChatAPI;

public class ChatController {
   private static final Logger LOGGER = LogManager.getLogger();
   protected ChatController$ChatListener field_153003_a = null;
   protected String l = "";
   protected String field_153006_d = "";
   protected String field_153007_e = "";
   protected Core field_175992_e = Core.getInstance();
   protected Chat field_153008_f;
   protected ChatController$ChatState field_153011_i = ChatController$ChatState.Uninitialized;
   protected AuthToken m = new AuthToken();
   protected HashMap field_175998_i = new HashMap();
   protected int field_153015_m = 128;
   protected ChatController$EnumEmoticonMode field_175997_k = ChatController$EnumEmoticonMode.None;
   protected ChatController$EnumEmoticonMode field_175995_l = ChatController$EnumEmoticonMode.None;
   protected ChatEmoticonData field_175996_m = null;
   protected int field_175993_n = 500;
   protected int field_175994_o = 2000;
   protected IChatAPIListener field_175999_p = new agv(this);

   public void func_152990_a(ChatController$ChatListener var1) {
      this.field_153003_a = var1;
   }

   public void func_152994_a(AuthToken var1) {
      this.m = var1;
   }

   public void func_152984_a(String var1) {
      this.field_153006_d = var1;
   }

   public void func_152998_c(String var1) {
      this.l = var1;
   }

   public ChatController$ChatState func_153000_j() {
      return this.field_153011_i;
   }

   public boolean func_175990_d(String var1) {
      if(!this.field_175998_i.containsKey(var1)) {
         return false;
      } else {
         ChatController$ChatChannelListener var2 = (ChatController$ChatChannelListener)this.field_175998_i.get(var1);
         return var2.func_176040_a() == ChatController$EnumChannelState.Connected;
      }
   }

   public ChatController$EnumChannelState func_175989_e(String var1) {
      if(!this.field_175998_i.containsKey(var1)) {
         return ChatController$EnumChannelState.Disconnected;
      } else {
         ChatController$ChatChannelListener var2 = (ChatController$ChatChannelListener)this.field_175998_i.get(var1);
         return var2.func_176040_a();
      }
   }

   public ChatController() {
      if(this.field_175992_e == null) {
         this.field_175992_e = new Core(new StandardCoreAPI());
      }

      this.field_153008_f = new Chat(new StandardChatAPI());
   }

   public boolean func_175984_n() {
      if(this.field_153011_i != ChatController$ChatState.Uninitialized) {
         return false;
      } else {
         this.func_175985_a(ChatController$ChatState.Initializing);
         ErrorCode var1 = this.field_175992_e.initialize(this.field_153006_d, (String)null);
         if(ErrorCode.failed(var1)) {
            this.func_175985_a(ChatController$ChatState.Uninitialized);
            String var5 = ErrorCode.getString(var1);
            this.func_152995_h(String.format("Error initializing Twitch sdk: %s", new Object[]{var5}));
            return false;
         } else {
            this.field_175995_l = this.field_175997_k;
            HashSet var2 = new HashSet();
            switch(ChatController$2.$SwitchMap$net$minecraft$client$stream$ChatController$EnumEmoticonMode[this.field_175997_k.ordinal()]) {
            case 1:
               var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
               break;
            case 2:
               var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
               break;
            case 3:
               var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
            }

            var1 = this.field_153008_f.initialize(var2, this.field_175999_p);
            if(ErrorCode.failed(var1)) {
               this.field_175992_e.shutdown();
               this.func_175985_a(ChatController$ChatState.Uninitialized);
               String var3 = ErrorCode.getString(var1);
               this.func_152995_h(String.format("Error initializing Twitch chat: %s", new Object[]{var3}));
               return false;
            } else {
               this.func_175985_a(ChatController$ChatState.Initialized);
               return true;
            }
         }
      }
   }

   public boolean func_152986_d(String var1) {
      return this.func_175987_a(var1, false);
   }

   protected boolean func_175987_a(String var1, boolean var2) {
      if(this.field_153011_i != ChatController$ChatState.Initialized) {
         return false;
      } else if(this.field_175998_i.containsKey(var1)) {
         this.func_152995_h("Already in channel: " + var1);
         return false;
      } else if(!var1.equals("")) {
         ChatController$ChatChannelListener var3 = new ChatController$ChatChannelListener(this, var1);
         this.field_175998_i.put(var1, var3);
         boolean var4 = var3.func_176038_a(var2);
         this.field_175998_i.remove(var1);
         return var4;
      } else {
         return false;
      }
   }

   public boolean func_175991_l(String var1) {
      if(this.field_153011_i != ChatController$ChatState.Initialized) {
         return false;
      } else if(!this.field_175998_i.containsKey(var1)) {
         this.func_152995_h("Not in channel: " + var1);
         return false;
      } else {
         ChatController$ChatChannelListener var2 = (ChatController$ChatChannelListener)this.field_175998_i.get(var1);
         return var2.func_176034_g();
      }
   }

   public boolean func_152993_m() {
      if(this.field_153011_i != ChatController$ChatState.Initialized) {
         return false;
      } else {
         ErrorCode var1 = this.field_153008_f.shutdown();
         if(ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.func_152995_h(String.format("Error shutting down chat: %s", new Object[]{var2}));
            return false;
         } else {
            this.func_152996_t();
            this.func_175985_a(ChatController$ChatState.ShuttingDown);
            return true;
         }
      }
   }

   public void func_175988_p() {
      if(this.func_153000_j() != ChatController$ChatState.Uninitialized) {
         this.func_152993_m();
         if(this.func_153000_j() == ChatController$ChatState.ShuttingDown) {
            while(this.func_153000_j() != ChatController$ChatState.Uninitialized) {
               long var10000 = 200L;

               try {
                  Thread.sleep(var10000);
                  this.func_152997_n();
               } catch (InterruptedException var2) {
                  ;
               }
            }
         }
      }

   }

   public void func_152997_n() {
      if(this.field_153011_i != ChatController$ChatState.Uninitialized) {
         ErrorCode var1 = this.field_153008_f.flushEvents();
         if(ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.func_152995_h(String.format("Error flushing chat events: %s", new Object[]{var2}));
         }
      }

   }

   public boolean func_175986_a(String var1, String var2) {
      if(this.field_153011_i != ChatController$ChatState.Initialized) {
         return false;
      } else if(!this.field_175998_i.containsKey(var1)) {
         this.func_152995_h("Not in channel: " + var1);
         return false;
      } else {
         ChatController$ChatChannelListener var3 = (ChatController$ChatChannelListener)this.field_175998_i.get(var1);
         return var3.func_176037_b(var2);
      }
   }

   protected void func_175985_a(ChatController$ChatState var1) {
      if(var1 != this.field_153011_i) {
         this.field_153011_i = var1;

         try {
            if(this.field_153003_a != null) {
               this.field_153003_a.func_176017_a(var1);
            }
         } catch (Exception var3) {
            this.func_152995_h(var3.toString());
         }
      }

   }

   protected void func_153001_r() {
      if(this.field_175995_l != ChatController$EnumEmoticonMode.None && this.field_175996_m == null) {
         ErrorCode var1 = this.field_153008_f.downloadEmoticonData();
         if(ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.func_152995_h(String.format("Error trying to download emoticon data: %s", new Object[]{var2}));
         }
      }

   }

   protected void func_152988_s() {
      if(this.field_175996_m == null) {
         this.field_175996_m = new ChatEmoticonData();
         ErrorCode var1 = this.field_153008_f.getEmoticonData(this.field_175996_m);
         if(ErrorCode.succeeded(var1)) {
            try {
               if(this.field_153003_a != null) {
                  this.field_153003_a.func_176021_d();
               }
            } catch (Exception var3) {
               this.func_152995_h(var3.toString());
            }
         } else {
            this.func_152995_h("Error preparing emoticon data: " + ErrorCode.getString(var1));
         }
      }

   }

   protected void func_152996_t() {
      if(this.field_175996_m != null) {
         ErrorCode var1 = this.field_153008_f.clearEmoticonData();
         if(ErrorCode.succeeded(var1)) {
            this.field_175996_m = null;

            try {
               if(this.field_153003_a != null) {
                  this.field_153003_a.func_176024_e();
               }
            } catch (Exception var3) {
               this.func_152995_h(var3.toString());
            }
         } else {
            this.func_152995_h("Error clearing emoticon data: " + ErrorCode.getString(var1));
         }
      }

   }

   protected void func_152995_h(String var1) {
      LOGGER.error(TwitchStream.STREAM_MARKER, "[Chat controller] {}", new Object[]{var1});
   }

   private static InterruptedException a(InterruptedException var0) {
      return var0;
   }
}
