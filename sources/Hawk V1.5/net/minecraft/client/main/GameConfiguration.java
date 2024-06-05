package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.net.Proxy;
import net.minecraft.util.Session;

public class GameConfiguration {
   public final GameConfiguration.UserInformation field_178745_a;
   public final GameConfiguration.FolderInformation field_178744_c;
   private static final String __OBFID = "CL_00001918";
   public final GameConfiguration.ServerInformation field_178742_e;
   public final GameConfiguration.GameInformation field_178741_d;
   public final GameConfiguration.DisplayInformation field_178743_b;

   public GameConfiguration(GameConfiguration.UserInformation var1, GameConfiguration.DisplayInformation var2, GameConfiguration.FolderInformation var3, GameConfiguration.GameInformation var4, GameConfiguration.ServerInformation var5) {
      this.field_178745_a = var1;
      this.field_178743_b = var2;
      this.field_178744_c = var3;
      this.field_178741_d = var4;
      this.field_178742_e = var5;
   }

   public static class FolderInformation {
      public final String field_178757_d;
      private static final String __OBFID = "CL_00001916";
      public final File field_178759_c;
      public final File field_178760_a;
      public final File field_178758_b;

      public FolderInformation(File var1, File var2, File var3, String var4) {
         this.field_178760_a = var1;
         this.field_178758_b = var2;
         this.field_178759_c = var3;
         this.field_178757_d = var4;
      }
   }

   public static class ServerInformation {
      private static final String __OBFID = "CL_00001914";
      public final String field_178754_a;
      public final int field_178753_b;

      public ServerInformation(String var1, int var2) {
         this.field_178754_a = var1;
         this.field_178753_b = var2;
      }
   }

   public static class DisplayInformation {
      public final int field_178762_b;
      public final boolean field_178761_d;
      private static final String __OBFID = "CL_00001917";
      public final boolean field_178763_c;
      public final int field_178764_a;

      public DisplayInformation(int var1, int var2, boolean var3, boolean var4) {
         this.field_178764_a = var1;
         this.field_178762_b = var2;
         this.field_178763_c = var3;
         this.field_178761_d = var4;
      }
   }

   public static class UserInformation {
      public final Proxy field_178751_c;
      public final PropertyMap field_178750_b;
      private static final String __OBFID = "CL_00001913";
      public final Session field_178752_a;

      public UserInformation(Session var1, PropertyMap var2, Proxy var3) {
         this.field_178752_a = var1;
         this.field_178750_b = var2;
         this.field_178751_c = var3;
      }
   }

   public static class GameInformation {
      public final String field_178755_b;
      public final boolean field_178756_a;
      private static final String __OBFID = "CL_00001915";

      public GameInformation(boolean var1, String var2) {
         this.field_178756_a = var1;
         this.field_178755_b = var2;
      }
   }
}
