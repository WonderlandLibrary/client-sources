package net;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.FJ;
import net.Ql;
import net.Ua;
import net.Uj;
import net.aJ1;
import net.aPN;
import net.a_E;
import net.au7;
import net.awZ;
import net.gZ;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public final class F9 extends FJ {
   public F9(gZ var1) {
      super(var1, "f", "Adds friend", "friend");
   }

   private void b() {
      this.a("Friends help:", ".friend", new Uj[]{aJ1.a("add (name)", "adds friend"), aJ1.a("remove (name)", "removes friend"), aJ1.a("list", "shows friends")});
   }

   public void b(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.b();
      } else {
         if(var1.length == 1) {
            String var3 = var1[0];
            String var4 = var3.toLowerCase();
            byte var5 = -1;
            switch(var4.hashCode()) {
            case 108:
               if(!var4.equals("l")) {
                  break;
               }

               var5 = 0;
            case 3322014:
               if(!var4.equals("list")) {
                  break;
               }

               var5 = 1;
            case 94746189:
               if(var4.equals("clear")) {
                  var5 = 2;
               }
            }

            switch(var5) {
            case 0:
            case 1:
               List var6 = this.h.k().a(au7.FRIEND);
               Ua var7 = aJ1.b("Friends list:");
               if(var6.isEmpty()) {
                  var7.a((IChatComponent)aJ1.a(" (empty)", EnumChatFormatting.RED));
               }

               this.a(var7, true);
               Iterator var8 = var6.iterator();
               if(var8.hasNext()) {
                  String var9 = (String)var8.next();
                  this.a((Ua)aJ1.b(" - ").a((IChatComponent)aJ1.a(var9, EnumChatFormatting.GRAY)));
               }
            case 2:
               if(this.h.k().a(au7.FRIEND, F9::lambda$process$0)) {
                  this.e("Friend list was cleared");
               }

               this.e("Friend list is empty");
            default:
               au7 var14 = this.h.k().a(var3);
               if(var14 == au7.TARGET) {
                  this.a(var3);
               }

               if(var14 == au7.FRIEND) {
                  this.b(var3);
               }
            }
         }

         try {
            String var11 = var1[0];
            String var12 = var1[1];
            String var13 = var11.toLowerCase();
            byte var15 = -1;
            switch(var13.hashCode()) {
            case 96417:
               if(!var13.equals("add")) {
                  break;
               }

               var15 = 0;
            case -934610812:
               if(!var13.equals("remove")) {
                  break;
               }

               var15 = 1;
            case -1335458389:
               if(!var13.equals("delete")) {
                  break;
               }

               var15 = 2;
            case 99339:
               if(!var13.equals("del")) {
                  break;
               }

               var15 = 3;
            case 112794:
               if(var13.equals("rem")) {
                  var15 = 4;
               }
            }

            switch(var15) {
            case 0:
               this.a(var12);
            case 1:
            case 2:
            case 3:
            case 4:
               this.b(var12);
            default:
               this.f("Illegal command specified: " + var1[0] + "!");
            }
         } catch (Exception var10) {
            var10.printStackTrace();
         }

      }
   }

   public void a(String var1) {
      Ql.a(var1, "name");
      String var3 = var1.toLowerCase();
      awZ var4 = this.h.k();
      a_E.b();
      au7 var5 = var4.a(var3);
      switch(aPN.a[var5.ordinal()]) {
      case 1:
         if(var4.c(var3, au7.TARGET)) {
            this.h("Removed " + var1 + " from targets!");
         }

         this.h("Cannot remove " + var1 + " from targets!");
      case 2:
         this.f(var1 + " is friend already!");
         return;
      default:
         boolean var6 = var4.a(var3, au7.FRIEND);
         this.e("Added " + var1 + " to friends!");
         awZ var10000 = var4;

         try {
            var10000.d().a();
         } catch (IOException var8) {
            this.f("Can\'t save to file");
            var4.e().warn("An error occurred while saving friends list", var8);
            this.f("Cannot add " + var1 + " to friends!");
         }

      }
   }

   public void b(String var1) {
      a_E.b();
      Ql.a(var1, "name");
      String var3 = var1.toLowerCase();
      awZ var4 = this.h.k();
      if(var4.a(var3) != au7.FRIEND) {
         this.f(var1 + " is not friend!");
      } else {
         boolean var5 = var4.b(var3);
         this.e("Removed " + var1 + " from friends!");
         awZ var10000 = var4;

         try {
            var10000.d().a();
         } catch (IOException var7) {
            this.f("Can\'t save to file");
            var4.e().warn("An error occurred while saving friends list", var7);
            this.f("Cannot remove " + var1 + " from friends!");
         }

      }
   }

   public List a(String[] var1) {
      int[] var2 = a_E.b();
      switch(var1.length) {
      case 1:
         return this.a(Stream.of(new String[]{"add", "remove", "list"}), var1[0], true);
      case 2:
         if(var1[0].equalsIgnoreCase("add")) {
            return this.a(NetHandlerPlayClient.e.values().stream().map(NetworkPlayerInfo::getGameProfile).map(GameProfile::getName).filter(this::lambda$completeTabOptions$1), var1[1], true);
         } else {
            if(var1[0].equalsIgnoreCase("remove")) {
               return this.a(this.h.k().a(au7.FRIEND), var1[1], true);
            }

            return null;
         }
      default:
         return null;
      }
   }

   private boolean lambda$completeTabOptions$1(String var1) {
      int[] var2 = a_E.b();
      return !this.h.k().b(var1, au7.FRIEND);
   }

   private static boolean lambda$process$0(Entry var0) {
      return true;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
