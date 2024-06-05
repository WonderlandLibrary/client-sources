package net.minecraft.client.audio;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import net.minecraft.client.audio.SoundList;

final class SoundHandler$1 implements ParameterizedType {
   public Type[] getActualTypeArguments() {
      return new Type[]{String.class, SoundList.class};
   }

   public Type getRawType() {
      return Map.class;
   }

   public Type getOwnerType() {
      return null;
   }
}
