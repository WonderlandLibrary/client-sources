package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.client.stream.ChatController;
import net.minecraft.client.stream.ChatController$2;
import net.minecraft.client.stream.ChatController$EnumChannelState;
import net.minecraft.client.stream.ChatController$EnumEmoticonMode;
import tv.twitch.ErrorCode;
import tv.twitch.chat.ChatBadgeData;
import tv.twitch.chat.ChatChannelInfo;
import tv.twitch.chat.ChatEvent;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.IChatChannelListener;

public class ChatController$ChatChannelListener implements IChatChannelListener {
   protected String field_176048_a;
   protected boolean field_176046_b;
   protected ChatController$EnumChannelState field_176047_c;
   protected List field_176044_d;
   protected LinkedList field_176045_e;
   protected LinkedList field_176042_f;
   protected ChatBadgeData field_176043_g;
   final ChatController this$0;

   public ChatController$ChatChannelListener(ChatController var1, String var2) {
      this.this$0 = var1;
      this.field_176048_a = null;
      this.field_176046_b = false;
      this.field_176047_c = ChatController$EnumChannelState.Created;
      this.field_176044_d = Lists.newArrayList();
      this.field_176045_e = new LinkedList();
      this.field_176042_f = new LinkedList();
      this.field_176043_g = null;
      this.field_176048_a = var2;
   }

   public ChatController$EnumChannelState func_176040_a() {
      return this.field_176047_c;
   }

   public boolean func_176038_a(boolean var1) {
      this.field_176046_b = var1;
      ErrorCode var2 = ErrorCode.TTV_EC_SUCCESS;
      var2 = this.this$0.field_153008_f.connectAnonymous(this.field_176048_a, this);
      if(ErrorCode.failed(var2)) {
         String var3 = ErrorCode.getString(var2);
         this.this$0.func_152995_h(String.format("Error connecting: %s", new Object[]{var3}));
         this.func_176036_d(this.field_176048_a);
         return false;
      } else {
         this.func_176035_a(ChatController$EnumChannelState.Connecting);
         this.func_176041_h();
         return true;
      }
   }

   public boolean func_176034_g() {
      switch(ChatController$2.$SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState[this.field_176047_c.ordinal()]) {
      case 1:
      case 2:
         ErrorCode var1 = this.this$0.field_153008_f.disconnect(this.field_176048_a);
         if(ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.this$0.func_152995_h(String.format("Error disconnecting: %s", new Object[]{var2}));
            return false;
         }

         this.func_176035_a(ChatController$EnumChannelState.Disconnecting);
         return true;
      case 3:
      case 4:
      case 5:
      default:
         return false;
      }
   }

   protected void func_176035_a(ChatController$EnumChannelState var1) {
      if(var1 != this.field_176047_c) {
         this.field_176047_c = var1;
      }

   }

   public void func_176032_a(String var1) {
      if(this.this$0.field_175995_l == ChatController$EnumEmoticonMode.None) {
         this.field_176045_e.clear();
         this.field_176042_f.clear();
      } else {
         if(!this.field_176045_e.isEmpty()) {
            ListIterator var2 = this.field_176045_e.listIterator();

            while(var2.hasNext()) {
               ChatRawMessage var3 = (ChatRawMessage)var2.next();
               if(var3.userName.equals(var1)) {
                  var2.remove();
               }
            }
         }

         if(!this.field_176042_f.isEmpty()) {
            ListIterator var5 = this.field_176042_f.listIterator();

            while(var5.hasNext()) {
               ChatTokenizedMessage var6 = (ChatTokenizedMessage)var5.next();
               if(var6.displayName.equals(var1)) {
                  var5.remove();
               }
            }
         }
      }

      try {
         if(this.this$0.field_153003_a != null) {
            this.this$0.field_153003_a.func_176019_a(this.field_176048_a, var1);
         }
      } catch (Exception var4) {
         this.this$0.func_152995_h(var4.toString());
      }

   }

   public boolean func_176037_b(String var1) {
      if(this.field_176047_c != ChatController$EnumChannelState.Connected) {
         return false;
      } else {
         ErrorCode var2 = this.this$0.field_153008_f.sendMessage(this.field_176048_a, var1);
         if(ErrorCode.failed(var2)) {
            String var3 = ErrorCode.getString(var2);
            this.this$0.func_152995_h(String.format("Error sending chat message: %s", new Object[]{var3}));
            return false;
         } else {
            return true;
         }
      }
   }

   protected void func_176041_h() {
      if(this.this$0.field_175995_l != ChatController$EnumEmoticonMode.None && this.field_176043_g == null) {
         ErrorCode var1 = this.this$0.field_153008_f.downloadBadgeData(this.field_176048_a);
         if(ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.this$0.func_152995_h(String.format("Error trying to download badge data: %s", new Object[]{var2}));
         }
      }

   }

   protected void func_176039_i() {
      if(this.field_176043_g == null) {
         this.field_176043_g = new ChatBadgeData();
         ErrorCode var1 = this.this$0.field_153008_f.getBadgeData(this.field_176048_a, this.field_176043_g);
         if(ErrorCode.succeeded(var1)) {
            try {
               if(this.this$0.field_153003_a != null) {
                  this.this$0.field_153003_a.func_176016_c(this.field_176048_a);
               }
            } catch (Exception var3) {
               this.this$0.func_152995_h(var3.toString());
            }
         } else {
            this.this$0.func_152995_h("Error preparing badge data: " + ErrorCode.getString(var1));
         }
      }

   }

   protected void func_176033_j() {
      if(this.field_176043_g != null) {
         ErrorCode var1 = this.this$0.field_153008_f.clearBadgeData(this.field_176048_a);
         if(ErrorCode.succeeded(var1)) {
            this.field_176043_g = null;

            try {
               if(this.this$0.field_153003_a != null) {
                  this.this$0.field_153003_a.func_176020_d(this.field_176048_a);
               }
            } catch (Exception var3) {
               this.this$0.func_152995_h(var3.toString());
            }
         } else {
            this.this$0.func_152995_h("Error releasing badge data: " + ErrorCode.getString(var1));
         }
      }

   }

   protected void func_176031_c(String var1) {
      try {
         if(this.this$0.field_153003_a != null) {
            this.this$0.field_153003_a.func_180606_a(var1);
         }
      } catch (Exception var3) {
         this.this$0.func_152995_h(var3.toString());
      }

   }

   protected void func_176036_d(String var1) {
      try {
         if(this.this$0.field_153003_a != null) {
            this.this$0.field_153003_a.func_180607_b(var1);
         }
      } catch (Exception var3) {
         this.this$0.func_152995_h(var3.toString());
      }

   }

   private void func_176030_k() {
      if(this.field_176047_c != ChatController$EnumChannelState.Disconnected) {
         this.func_176035_a(ChatController$EnumChannelState.Disconnected);
         this.func_176036_d(this.field_176048_a);
         this.func_176033_j();
      }

   }

   public void chatStatusCallback(String var1, ErrorCode var2) {
      if(!ErrorCode.succeeded(var2)) {
         this.this$0.field_175998_i.remove(var1);
         this.func_176030_k();
      }

   }

   public void chatChannelMembershipCallback(String var1, ChatEvent var2, ChatChannelInfo var3) {
      switch(ChatController$2.$SwitchMap$tv$twitch$chat$ChatEvent[var2.ordinal()]) {
      case 1:
         this.func_176035_a(ChatController$EnumChannelState.Connected);
         this.func_176031_c(var1);
         break;
      case 2:
         this.func_176030_k();
      }

   }

   public void chatChannelUserChangeCallback(String var1, ChatUserInfo[] var2, ChatUserInfo[] var3, ChatUserInfo[] var4) {
      for(ChatUserInfo var8 : var3) {
         int var9 = this.field_176044_d.indexOf(var8);
         this.field_176044_d.remove(var9);
      }

      for(ChatUserInfo var17 : var4) {
         int var19 = this.field_176044_d.indexOf(var17);
         this.field_176044_d.remove(var19);
         this.field_176044_d.add(var17);
      }

      for(ChatUserInfo var18 : var2) {
         this.field_176044_d.add(var18);
      }

      try {
         if(this.this$0.field_153003_a != null) {
            this.this$0.field_153003_a.func_176018_a(this.field_176048_a, var2, var3, var4);
         }
      } catch (Exception var10) {
         this.this$0.func_152995_h(var10.toString());
      }

   }

   public void chatChannelRawMessageCallback(String var1, ChatRawMessage[] var2) {
      for(ChatRawMessage var6 : var2) {
         this.field_176045_e.addLast(var6);
      }

      try {
         if(this.this$0.field_153003_a != null) {
            this.this$0.field_153003_a.func_180605_a(this.field_176048_a, var2);
         }
      } catch (Exception var7) {
         this.this$0.func_152995_h(var7.toString());
      }

      while(this.field_176045_e.size() > this.this$0.field_153015_m) {
         this.field_176045_e.removeFirst();
      }

   }

   public void chatChannelTokenizedMessageCallback(String var1, ChatTokenizedMessage[] var2) {
      for(ChatTokenizedMessage var6 : var2) {
         this.field_176042_f.addLast(var6);
      }

      try {
         if(this.this$0.field_153003_a != null) {
            this.this$0.field_153003_a.func_176025_a(this.field_176048_a, var2);
         }
      } catch (Exception var7) {
         this.this$0.func_152995_h(var7.toString());
      }

      while(this.field_176042_f.size() > this.this$0.field_153015_m) {
         this.field_176042_f.removeFirst();
      }

   }

   public void chatClearCallback(String var1, String var2) {
      this.func_176032_a(var2);
   }

   public void chatBadgeDataDownloadCallback(String var1, ErrorCode var2) {
      if(ErrorCode.succeeded(var2)) {
         this.func_176039_i();
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
