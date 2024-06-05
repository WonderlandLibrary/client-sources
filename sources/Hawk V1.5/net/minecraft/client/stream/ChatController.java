package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tv.twitch.AuthToken;
import tv.twitch.Core;
import tv.twitch.ErrorCode;
import tv.twitch.StandardCoreAPI;
import tv.twitch.chat.Chat;
import tv.twitch.chat.ChatBadgeData;
import tv.twitch.chat.ChatChannelInfo;
import tv.twitch.chat.ChatEmoticonData;
import tv.twitch.chat.ChatEvent;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizationOption;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.IChatAPIListener;
import tv.twitch.chat.IChatChannelListener;
import tv.twitch.chat.StandardChatAPI;

public class ChatController {
   protected IChatAPIListener field_175999_p;
   protected Core field_175992_e = null;
   protected AuthToken field_153012_j;
   protected ChatController.ChatState field_153011_i;
   protected int field_175994_o;
   private static final Logger LOGGER = LogManager.getLogger();
   protected ChatController.EnumEmoticonMode field_175995_l;
   protected String field_153007_e = "";
   protected String field_153004_b = "";
   private static final String __OBFID = "CL_00001819";
   protected HashMap field_175998_i;
   protected Chat field_153008_f = null;
   protected int field_175993_n;
   protected ChatEmoticonData field_175996_m;
   protected String field_153006_d = "";
   protected ChatController.EnumEmoticonMode field_175997_k;
   protected ChatController.ChatListener field_153003_a = null;
   protected int field_153015_m;

   protected boolean func_175987_a(String var1, boolean var2) {
      if (this.field_153011_i != ChatController.ChatState.Initialized) {
         return false;
      } else if (this.field_175998_i.containsKey(var1)) {
         this.func_152995_h(String.valueOf((new StringBuilder("Already in channel: ")).append(var1)));
         return false;
      } else if (var1 != null && !var1.equals("")) {
         ChatController.ChatChannelListener var3 = new ChatController.ChatChannelListener(this, var1);
         this.field_175998_i.put(var1, var3);
         boolean var4 = var3.func_176038_a(var2);
         if (!var4) {
            this.field_175998_i.remove(var1);
         }

         return var4;
      } else {
         return false;
      }
   }

   public ChatController() {
      this.field_153011_i = ChatController.ChatState.Uninitialized;
      this.field_153012_j = new AuthToken();
      this.field_175998_i = new HashMap();
      this.field_153015_m = 128;
      this.field_175997_k = ChatController.EnumEmoticonMode.None;
      this.field_175995_l = ChatController.EnumEmoticonMode.None;
      this.field_175996_m = null;
      this.field_175993_n = 500;
      this.field_175994_o = 2000;
      this.field_175999_p = new IChatAPIListener(this) {
         final ChatController this$0;
         private static final String __OBFID = "CL_00002373";

         public void chatEmoticonDataDownloadCallback(ErrorCode var1) {
            if (ErrorCode.succeeded(var1)) {
               this.this$0.func_152988_s();
            }

         }

         {
            this.this$0 = var1;
         }

         public void chatShutdownCallback(ErrorCode var1) {
            if (ErrorCode.succeeded(var1)) {
               ErrorCode var2 = this.this$0.field_175992_e.shutdown();
               if (ErrorCode.failed(var2)) {
                  String var3 = ErrorCode.getString(var2);
                  this.this$0.func_152995_h(String.format("Error shutting down the Twitch sdk: %s", var3));
               }

               this.this$0.func_175985_a(ChatController.ChatState.Uninitialized);
            } else {
               this.this$0.func_175985_a(ChatController.ChatState.Initialized);
               this.this$0.func_152995_h(String.format("Error shutting down Twith chat: %s", var1));
            }

            try {
               if (this.this$0.field_153003_a != null) {
                  this.this$0.field_153003_a.func_176022_e(var1);
               }
            } catch (Exception var4) {
               this.this$0.func_152995_h(var4.toString());
            }

         }

         public void chatInitializationCallback(ErrorCode var1) {
            if (ErrorCode.succeeded(var1)) {
               this.this$0.field_153008_f.setMessageFlushInterval(this.this$0.field_175993_n);
               this.this$0.field_153008_f.setUserChangeEventInterval(this.this$0.field_175994_o);
               this.this$0.func_153001_r();
               this.this$0.func_175985_a(ChatController.ChatState.Initialized);
            } else {
               this.this$0.func_175985_a(ChatController.ChatState.Uninitialized);
            }

            try {
               if (this.this$0.field_153003_a != null) {
                  this.this$0.field_153003_a.func_176023_d(var1);
               }
            } catch (Exception var3) {
               this.this$0.func_152995_h(var3.toString());
            }

         }
      };
      this.field_175992_e = Core.getInstance();
      if (this.field_175992_e == null) {
         this.field_175992_e = new Core(new StandardCoreAPI());
      }

      this.field_153008_f = new Chat(new StandardChatAPI());
   }

   protected void func_175985_a(ChatController.ChatState var1) {
      if (var1 != this.field_153011_i) {
         this.field_153011_i = var1;

         try {
            if (this.field_153003_a != null) {
               this.field_153003_a.func_176017_a(var1);
            }
         } catch (Exception var3) {
            this.func_152995_h(var3.toString());
         }
      }

   }

   public void func_152994_a(AuthToken var1) {
      this.field_153012_j = var1;
   }

   public void func_175988_p() {
      if (this.func_153000_j() != ChatController.ChatState.Uninitialized) {
         this.func_152993_m();
         if (this.func_153000_j() == ChatController.ChatState.ShuttingDown) {
            while(this.func_153000_j() != ChatController.ChatState.Uninitialized) {
               try {
                  Thread.sleep(200L);
                  this.func_152997_n();
               } catch (InterruptedException var2) {
               }
            }
         }
      }

   }

   public boolean func_175986_a(String var1, String var2) {
      if (this.field_153011_i != ChatController.ChatState.Initialized) {
         return false;
      } else if (!this.field_175998_i.containsKey(var1)) {
         this.func_152995_h(String.valueOf((new StringBuilder("Not in channel: ")).append(var1)));
         return false;
      } else {
         ChatController.ChatChannelListener var3 = (ChatController.ChatChannelListener)this.field_175998_i.get(var1);
         return var3.func_176037_b(var2);
      }
   }

   protected void func_152988_s() {
      if (this.field_175996_m == null) {
         this.field_175996_m = new ChatEmoticonData();
         ErrorCode var1 = this.field_153008_f.getEmoticonData(this.field_175996_m);
         if (ErrorCode.succeeded(var1)) {
            try {
               if (this.field_153003_a != null) {
                  this.field_153003_a.func_176021_d();
               }
            } catch (Exception var3) {
               this.func_152995_h(var3.toString());
            }
         } else {
            this.func_152995_h(String.valueOf((new StringBuilder("Error preparing emoticon data: ")).append(ErrorCode.getString(var1))));
         }
      }

   }

   public boolean func_175984_n() {
      if (this.field_153011_i != ChatController.ChatState.Uninitialized) {
         return false;
      } else {
         this.func_175985_a(ChatController.ChatState.Initializing);
         ErrorCode var1 = this.field_175992_e.initialize(this.field_153006_d, (String)null);
         if (ErrorCode.failed(var1)) {
            this.func_175985_a(ChatController.ChatState.Uninitialized);
            String var4 = ErrorCode.getString(var1);
            this.func_152995_h(String.format("Error initializing Twitch sdk: %s", var4));
            return false;
         } else {
            this.field_175995_l = this.field_175997_k;
            HashSet var2 = new HashSet();
            switch(this.field_175997_k) {
            case None:
               var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
               break;
            case Url:
               var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
               break;
            case TextureAtlas:
               var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
            }

            var1 = this.field_153008_f.initialize(var2, this.field_175999_p);
            if (ErrorCode.failed(var1)) {
               this.field_175992_e.shutdown();
               this.func_175985_a(ChatController.ChatState.Uninitialized);
               String var3 = ErrorCode.getString(var1);
               this.func_152995_h(String.format("Error initializing Twitch chat: %s", var3));
               return false;
            } else {
               this.func_175985_a(ChatController.ChatState.Initialized);
               return true;
            }
         }
      }
   }

   public ChatController.EnumChannelState func_175989_e(String var1) {
      if (!this.field_175998_i.containsKey(var1)) {
         return ChatController.EnumChannelState.Disconnected;
      } else {
         ChatController.ChatChannelListener var2 = (ChatController.ChatChannelListener)this.field_175998_i.get(var1);
         return var2.func_176040_a();
      }
   }

   public boolean func_175990_d(String var1) {
      if (!this.field_175998_i.containsKey(var1)) {
         return false;
      } else {
         ChatController.ChatChannelListener var2 = (ChatController.ChatChannelListener)this.field_175998_i.get(var1);
         return var2.func_176040_a() == ChatController.EnumChannelState.Connected;
      }
   }

   public void func_152998_c(String var1) {
      this.field_153004_b = var1;
   }

   public void func_152997_n() {
      if (this.field_153011_i != ChatController.ChatState.Uninitialized) {
         ErrorCode var1 = this.field_153008_f.flushEvents();
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.func_152995_h(String.format("Error flushing chat events: %s", var2));
         }
      }

   }

   public boolean func_175991_l(String var1) {
      if (this.field_153011_i != ChatController.ChatState.Initialized) {
         return false;
      } else if (!this.field_175998_i.containsKey(var1)) {
         this.func_152995_h(String.valueOf((new StringBuilder("Not in channel: ")).append(var1)));
         return false;
      } else {
         ChatController.ChatChannelListener var2 = (ChatController.ChatChannelListener)this.field_175998_i.get(var1);
         return var2.func_176034_g();
      }
   }

   public void func_152990_a(ChatController.ChatListener var1) {
      this.field_153003_a = var1;
   }

   public boolean func_152986_d(String var1) {
      return this.func_175987_a(var1, false);
   }

   protected void func_153001_r() {
      if (this.field_175995_l != ChatController.EnumEmoticonMode.None && this.field_175996_m == null) {
         ErrorCode var1 = this.field_153008_f.downloadEmoticonData();
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.func_152995_h(String.format("Error trying to download emoticon data: %s", var2));
         }
      }

   }

   public boolean func_152993_m() {
      if (this.field_153011_i != ChatController.ChatState.Initialized) {
         return false;
      } else {
         ErrorCode var1 = this.field_153008_f.shutdown();
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.func_152995_h(String.format("Error shutting down chat: %s", var2));
            return false;
         } else {
            this.func_152996_t();
            this.func_175985_a(ChatController.ChatState.ShuttingDown);
            return true;
         }
      }
   }

   protected void func_152995_h(String var1) {
      LOGGER.error(TwitchStream.field_152949_a, "[Chat controller] {}", new Object[]{var1});
   }

   public void func_152984_a(String var1) {
      this.field_153006_d = var1;
   }

   protected void func_152996_t() {
      if (this.field_175996_m != null) {
         ErrorCode var1 = this.field_153008_f.clearEmoticonData();
         if (ErrorCode.succeeded(var1)) {
            this.field_175996_m = null;

            try {
               if (this.field_153003_a != null) {
                  this.field_153003_a.func_176024_e();
               }
            } catch (Exception var3) {
               this.func_152995_h(var3.toString());
            }
         } else {
            this.func_152995_h(String.valueOf((new StringBuilder("Error clearing emoticon data: ")).append(ErrorCode.getString(var1))));
         }
      }

   }

   public ChatController.ChatState func_153000_j() {
      return this.field_153011_i;
   }

   public interface ChatListener {
      void func_180605_a(String var1, ChatRawMessage[] var2);

      void func_176022_e(ErrorCode var1);

      void func_180607_b(String var1);

      void func_176023_d(ErrorCode var1);

      void func_176020_d(String var1);

      void func_176019_a(String var1, String var2);

      void func_176024_e();

      void func_176025_a(String var1, ChatTokenizedMessage[] var2);

      void func_180606_a(String var1);

      void func_176018_a(String var1, ChatUserInfo[] var2, ChatUserInfo[] var3, ChatUserInfo[] var4);

      void func_176017_a(ChatController.ChatState var1);

      void func_176016_c(String var1);

      void func_176021_d();
   }

   public class ChatChannelListener implements IChatChannelListener {
      final ChatController this$0;
      protected ChatController.EnumChannelState field_176047_c;
      protected String field_176048_a;
      protected boolean field_176046_b;
      protected ChatBadgeData field_176043_g;
      protected LinkedList field_176045_e;
      private static final String __OBFID = "CL_00002370";
      protected List field_176044_d;
      protected LinkedList field_176042_f;

      public boolean func_176038_a(boolean var1) {
         this.field_176046_b = var1;
         ErrorCode var2 = ErrorCode.TTV_EC_SUCCESS;
         if (var1) {
            var2 = this.this$0.field_153008_f.connectAnonymous(this.field_176048_a, this);
         } else {
            var2 = this.this$0.field_153008_f.connect(this.field_176048_a, this.this$0.field_153004_b, this.this$0.field_153012_j.data, this);
         }

         if (ErrorCode.failed(var2)) {
            String var3 = ErrorCode.getString(var2);
            this.this$0.func_152995_h(String.format("Error connecting: %s", var3));
            this.func_176036_d(this.field_176048_a);
            return false;
         } else {
            this.func_176035_a(ChatController.EnumChannelState.Connecting);
            this.func_176041_h();
            return true;
         }
      }

      public void chatChannelUserChangeCallback(String var1, ChatUserInfo[] var2, ChatUserInfo[] var3, ChatUserInfo[] var4) {
         int var5;
         int var6;
         for(var5 = 0; var5 < var3.length; ++var5) {
            var6 = this.field_176044_d.indexOf(var3[var5]);
            if (var6 >= 0) {
               this.field_176044_d.remove(var6);
            }
         }

         for(var5 = 0; var5 < var4.length; ++var5) {
            var6 = this.field_176044_d.indexOf(var4[var5]);
            if (var6 >= 0) {
               this.field_176044_d.remove(var6);
            }

            this.field_176044_d.add(var4[var5]);
         }

         for(var5 = 0; var5 < var2.length; ++var5) {
            this.field_176044_d.add(var2[var5]);
         }

         try {
            if (this.this$0.field_153003_a != null) {
               this.this$0.field_153003_a.func_176018_a(this.field_176048_a, var2, var3, var4);
            }
         } catch (Exception var8) {
            this.this$0.func_152995_h(var8.toString());
         }

      }

      public void chatChannelRawMessageCallback(String var1, ChatRawMessage[] var2) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.field_176045_e.addLast(var2[var3]);
         }

         try {
            if (this.this$0.field_153003_a != null) {
               this.this$0.field_153003_a.func_180605_a(this.field_176048_a, var2);
            }
         } catch (Exception var4) {
            this.this$0.func_152995_h(var4.toString());
         }

         while(this.field_176045_e.size() > this.this$0.field_153015_m) {
            this.field_176045_e.removeFirst();
         }

      }

      private void func_176030_k() {
         if (this.field_176047_c != ChatController.EnumChannelState.Disconnected) {
            this.func_176035_a(ChatController.EnumChannelState.Disconnected);
            this.func_176036_d(this.field_176048_a);
            this.func_176033_j();
         }

      }

      public void chatStatusCallback(String var1, ErrorCode var2) {
         if (!ErrorCode.succeeded(var2)) {
            this.this$0.field_175998_i.remove(var1);
            this.func_176030_k();
         }

      }

      public void chatBadgeDataDownloadCallback(String var1, ErrorCode var2) {
         if (ErrorCode.succeeded(var2)) {
            this.func_176039_i();
         }

      }

      public boolean func_176037_b(String var1) {
         if (this.field_176047_c != ChatController.EnumChannelState.Connected) {
            return false;
         } else {
            ErrorCode var2 = this.this$0.field_153008_f.sendMessage(this.field_176048_a, var1);
            if (ErrorCode.failed(var2)) {
               String var3 = ErrorCode.getString(var2);
               this.this$0.func_152995_h(String.format("Error sending chat message: %s", var3));
               return false;
            } else {
               return true;
            }
         }
      }

      protected void func_176033_j() {
         if (this.field_176043_g != null) {
            ErrorCode var1 = this.this$0.field_153008_f.clearBadgeData(this.field_176048_a);
            if (ErrorCode.succeeded(var1)) {
               this.field_176043_g = null;

               try {
                  if (this.this$0.field_153003_a != null) {
                     this.this$0.field_153003_a.func_176020_d(this.field_176048_a);
                  }
               } catch (Exception var3) {
                  this.this$0.func_152995_h(var3.toString());
               }
            } else {
               this.this$0.func_152995_h(String.valueOf((new StringBuilder("Error releasing badge data: ")).append(ErrorCode.getString(var1))));
            }
         }

      }

      public void chatChannelTokenizedMessageCallback(String var1, ChatTokenizedMessage[] var2) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.field_176042_f.addLast(var2[var3]);
         }

         try {
            if (this.this$0.field_153003_a != null) {
               this.this$0.field_153003_a.func_176025_a(this.field_176048_a, var2);
            }
         } catch (Exception var4) {
            this.this$0.func_152995_h(var4.toString());
         }

         while(this.field_176042_f.size() > this.this$0.field_153015_m) {
            this.field_176042_f.removeFirst();
         }

      }

      protected void func_176035_a(ChatController.EnumChannelState var1) {
         if (var1 != this.field_176047_c) {
            this.field_176047_c = var1;
         }

      }

      public void func_176032_a(String var1) {
         if (this.this$0.field_175995_l == ChatController.EnumEmoticonMode.None) {
            this.field_176045_e.clear();
            this.field_176042_f.clear();
         } else {
            ListIterator var2;
            if (this.field_176045_e.size() > 0) {
               var2 = this.field_176045_e.listIterator();

               while(var2.hasNext()) {
                  ChatRawMessage var3 = (ChatRawMessage)var2.next();
                  if (var3.userName.equals(var1)) {
                     var2.remove();
                  }
               }
            }

            if (this.field_176042_f.size() > 0) {
               var2 = this.field_176042_f.listIterator();

               while(var2.hasNext()) {
                  ChatTokenizedMessage var5 = (ChatTokenizedMessage)var2.next();
                  if (var5.displayName.equals(var1)) {
                     var2.remove();
                  }
               }
            }
         }

         try {
            if (this.this$0.field_153003_a != null) {
               this.this$0.field_153003_a.func_176019_a(this.field_176048_a, var1);
            }
         } catch (Exception var4) {
            this.this$0.func_152995_h(var4.toString());
         }

      }

      protected void func_176036_d(String var1) {
         try {
            if (this.this$0.field_153003_a != null) {
               this.this$0.field_153003_a.func_180607_b(var1);
            }
         } catch (Exception var3) {
            this.this$0.func_152995_h(var3.toString());
         }

      }

      protected void func_176031_c(String var1) {
         try {
            if (this.this$0.field_153003_a != null) {
               this.this$0.field_153003_a.func_180606_a(var1);
            }
         } catch (Exception var3) {
            this.this$0.func_152995_h(var3.toString());
         }

      }

      public ChatChannelListener(ChatController var1, String var2) {
         this.this$0 = var1;
         this.field_176048_a = null;
         this.field_176046_b = false;
         this.field_176047_c = ChatController.EnumChannelState.Created;
         this.field_176044_d = Lists.newArrayList();
         this.field_176045_e = new LinkedList();
         this.field_176042_f = new LinkedList();
         this.field_176043_g = null;
         this.field_176048_a = var2;
      }

      protected void func_176039_i() {
         if (this.field_176043_g == null) {
            this.field_176043_g = new ChatBadgeData();
            ErrorCode var1 = this.this$0.field_153008_f.getBadgeData(this.field_176048_a, this.field_176043_g);
            if (ErrorCode.succeeded(var1)) {
               try {
                  if (this.this$0.field_153003_a != null) {
                     this.this$0.field_153003_a.func_176016_c(this.field_176048_a);
                  }
               } catch (Exception var3) {
                  this.this$0.func_152995_h(var3.toString());
               }
            } else {
               this.this$0.func_152995_h(String.valueOf((new StringBuilder("Error preparing badge data: ")).append(ErrorCode.getString(var1))));
            }
         }

      }

      protected void func_176041_h() {
         if (this.this$0.field_175995_l != ChatController.EnumEmoticonMode.None && this.field_176043_g == null) {
            ErrorCode var1 = this.this$0.field_153008_f.downloadBadgeData(this.field_176048_a);
            if (ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152995_h(String.format("Error trying to download badge data: %s", var2));
            }
         }

      }

      public boolean func_176034_g() {
         switch(this.field_176047_c) {
         case Connected:
         case Connecting:
            ErrorCode var1 = this.this$0.field_153008_f.disconnect(this.field_176048_a);
            if (ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152995_h(String.format("Error disconnecting: %s", var2));
               return false;
            }

            this.func_176035_a(ChatController.EnumChannelState.Disconnecting);
            return true;
         case Created:
         case Disconnected:
         case Disconnecting:
         default:
            return false;
         }
      }

      public void chatClearCallback(String var1, String var2) {
         this.func_176032_a(var2);
      }

      public void chatChannelMembershipCallback(String var1, ChatEvent var2, ChatChannelInfo var3) {
         switch(var2) {
         case TTV_CHAT_JOINED_CHANNEL:
            this.func_176035_a(ChatController.EnumChannelState.Connected);
            this.func_176031_c(var1);
            break;
         case TTV_CHAT_LEFT_CHANNEL:
            this.func_176030_k();
         }

      }

      public ChatController.EnumChannelState func_176040_a() {
         return this.field_176047_c;
      }
   }

   public static enum EnumEmoticonMode {
      private static final String __OBFID = "CL_00002369";
      TextureAtlas("TextureAtlas", 2);

      private static final ChatController.EnumEmoticonMode[] ENUM$VALUES = new ChatController.EnumEmoticonMode[]{None, Url, TextureAtlas};
      private static final ChatController.EnumEmoticonMode[] $VALUES = new ChatController.EnumEmoticonMode[]{None, Url, TextureAtlas};
      None("None", 0),
      Url("Url", 1);

      private EnumEmoticonMode(String var3, int var4) {
      }
   }

   public static enum EnumChannelState {
      Connected("Connected", 2),
      Disconnecting("Disconnecting", 3),
      Created("Created", 0);

      private static final ChatController.EnumChannelState[] $VALUES = new ChatController.EnumChannelState[]{Created, Connecting, Connected, Disconnecting, Disconnected};
      Connecting("Connecting", 1),
      Disconnected("Disconnected", 4);

      private static final String __OBFID = "CL_00002371";
      private static final ChatController.EnumChannelState[] ENUM$VALUES = new ChatController.EnumChannelState[]{Created, Connecting, Connected, Disconnecting, Disconnected};

      private EnumChannelState(String var3, int var4) {
      }
   }

   public static enum ChatState {
      private static final ChatController.ChatState[] $VALUES = new ChatController.ChatState[]{Uninitialized, Initializing, Initialized, ShuttingDown};
      Initializing("Initializing", 1);

      private static final ChatController.ChatState[] ENUM$VALUES = new ChatController.ChatState[]{Uninitialized, Initializing, Initialized, ShuttingDown};
      ShuttingDown("ShuttingDown", 3);

      private static final String __OBFID = "CL_00001817";
      Initialized("Initialized", 2),
      Uninitialized("Uninitialized", 0);

      private ChatState(String var3, int var4) {
      }
   }

   static final class SwitchEnumEmoticonMode {
      static final int[] field_175974_b;
      static final int[] field_175976_a;
      static final int[] field_175975_c = new int[ChatController.EnumEmoticonMode.values().length];
      private static final String __OBFID = "CL_00002372";

      static {
         try {
            field_175975_c[ChatController.EnumEmoticonMode.None.ordinal()] = 1;
         } catch (NoSuchFieldError var10) {
         }

         try {
            field_175975_c[ChatController.EnumEmoticonMode.Url.ordinal()] = 2;
         } catch (NoSuchFieldError var9) {
         }

         try {
            field_175975_c[ChatController.EnumEmoticonMode.TextureAtlas.ordinal()] = 3;
         } catch (NoSuchFieldError var8) {
         }

         field_175974_b = new int[ChatEvent.values().length];

         try {
            field_175974_b[ChatEvent.TTV_CHAT_JOINED_CHANNEL.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
         }

         try {
            field_175974_b[ChatEvent.TTV_CHAT_LEFT_CHANNEL.ordinal()] = 2;
         } catch (NoSuchFieldError var6) {
         }

         field_175976_a = new int[ChatController.EnumChannelState.values().length];

         try {
            field_175976_a[ChatController.EnumChannelState.Connected.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_175976_a[ChatController.EnumChannelState.Connecting.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_175976_a[ChatController.EnumChannelState.Created.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_175976_a[ChatController.EnumChannelState.Disconnected.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_175976_a[ChatController.EnumChannelState.Disconnecting.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
