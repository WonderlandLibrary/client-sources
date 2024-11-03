package com.viaversion.viaversion.libs.mcstructs.snbt;

import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12.SNbtDeserializer_v1_12;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12.SNbtSerializer_v1_12;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_13.SNbtDeserializer_v1_13;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_14.SNbtDeserializer_v1_14;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_14.SNbtSerializer_v1_14;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_7.SNbtDeserializer_v1_7;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_7.SNbtSerializer_v1_7;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_8.SNbtDeserializer_v1_8;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_8.SNbtSerializer_v1_8;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class SNbtSerializer<T extends Tag> {
   public static final SNbtSerializer<Tag> V1_7 = new SNbtSerializer<>(SNbtSerializer_v1_7::new, SNbtDeserializer_v1_7::new);
   public static final SNbtSerializer<CompoundTag> V1_8 = new SNbtSerializer<>(SNbtSerializer_v1_8::new, SNbtDeserializer_v1_8::new);
   public static final SNbtSerializer<CompoundTag> V1_12 = new SNbtSerializer<>(SNbtSerializer_v1_12::new, SNbtDeserializer_v1_12::new);
   public static final SNbtSerializer<CompoundTag> V1_13 = new SNbtSerializer<>(SNbtSerializer_v1_12::new, SNbtDeserializer_v1_13::new);
   public static final SNbtSerializer<CompoundTag> V1_14 = new SNbtSerializer<>(SNbtSerializer_v1_14::new, SNbtDeserializer_v1_14::new);
   public static final SNbtSerializer<CompoundTag> LATEST = V1_14;
   private final Supplier<ISNbtSerializer> serializerSupplier;
   private final Supplier<ISNbtDeserializer<T>> deserializerSupplier;
   private ISNbtSerializer serializer;
   private ISNbtDeserializer<T> deserializer;

   public SNbtSerializer(Supplier<ISNbtSerializer> serializerSupplier, Supplier<ISNbtDeserializer<T>> deserializerSupplier) {
      this.serializerSupplier = serializerSupplier;
      this.deserializerSupplier = deserializerSupplier;
   }

   public ISNbtSerializer getSerializer() {
      if (this.serializer == null) {
         this.serializer = this.serializerSupplier.get();
      }

      return this.serializer;
   }

   public ISNbtDeserializer<T> getDeserializer() {
      if (this.deserializer == null) {
         this.deserializer = this.deserializerSupplier.get();
      }

      return this.deserializer;
   }

   public String serialize(Tag tag) throws SNbtSerializeException {
      return this.getSerializer().serialize(tag);
   }

   @Nullable
   public String trySerialize(Tag tag) {
      try {
         return this.serialize(tag);
      } catch (SNbtSerializeException var3) {
         return null;
      }
   }

   public T deserialize(String s) throws SNbtDeserializeException {
      return this.getDeserializer().deserialize(s);
   }

   @Nullable
   public T tryDeserialize(String s) {
      try {
         return this.deserialize(s);
      } catch (SNbtDeserializeException var3) {
         return null;
      }
   }
}
