package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;
import java.net.Proxy;
import net.minecraft.util.Session;

public class GameConfiguration$UserInformation {
   public final Session session;
   public final PropertyMap userProperties;
   public final PropertyMap field_181172_c;
   public final Proxy proxy;

   public GameConfiguration$UserInformation(Session var1, PropertyMap var2, PropertyMap var3, Proxy var4) {
      this.session = var1;
      this.userProperties = var2;
      this.field_181172_c = var3;
      this.proxy = var4;
   }
}
