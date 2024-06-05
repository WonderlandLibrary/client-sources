package net.minecraft.server.management;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import net.minecraft.server.management.PlayerProfileCache$ProfileEntry;

final class PlayerProfileCache$1 implements ParameterizedType {
   public Type[] getActualTypeArguments() {
      return new Type[]{PlayerProfileCache$ProfileEntry.class};
   }

   public Type getRawType() {
      return List.class;
   }

   public Type getOwnerType() {
      return null;
   }
}
