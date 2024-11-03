package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.util.Key;
import io.netty.buffer.ByteBuf;

public class ParticleType extends Type<Particle> {
   private final Int2ObjectMap<ParticleType.ParticleReader> readers;

   public ParticleType(Int2ObjectMap<ParticleType.ParticleReader> readers) {
      super("Particle", Particle.class);
      this.readers = readers;
   }

   public ParticleType() {
      this(new Int2ObjectArrayMap<>());
   }

   public ParticleType.ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol) {
      return this.filler(protocol, true);
   }

   public ParticleType.ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
      return new ParticleType.ParticleTypeFiller(protocol, useMappedNames);
   }

   public void write(ByteBuf buffer, Particle object) throws Exception {
      Type.VAR_INT.writePrimitive(buffer, object.getId());

      for (Particle.ParticleData<?> data : object.getArguments()) {
         data.write(buffer);
      }
   }

   public Particle read(ByteBuf buffer) throws Exception {
      int type = Type.VAR_INT.readPrimitive(buffer);
      Particle particle = new Particle(type);
      ParticleType.ParticleReader reader = this.readers.get(type);
      if (reader != null) {
         reader.read(buffer, particle);
      }

      return particle;
   }

   public static ParticleType.ParticleReader itemHandler(Type<Item> itemType) {
      return (buf, particle) -> particle.add(itemType, itemType.read(buf));
   }

   @FunctionalInterface
   public interface ParticleReader {
      void read(ByteBuf var1, Particle var2) throws Exception;
   }

   public final class ParticleTypeFiller {
      private final ParticleMappings mappings;
      private final boolean useMappedNames;

      private ParticleTypeFiller(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
         this.mappings = protocol.getMappingData().getParticleMappings();
         this.useMappedNames = useMappedNames;
      }

      public ParticleType.ParticleTypeFiller reader(String identifier, ParticleType.ParticleReader reader) {
         ParticleType.this.readers.put(this.useMappedNames ? this.mappings.mappedId(identifier) : this.mappings.id(identifier), reader);
         return this;
      }

      public ParticleType.ParticleTypeFiller reader(int id, ParticleType.ParticleReader reader) {
         ParticleType.this.readers.put(id, reader);
         return this;
      }
   }

   public static final class Readers {
      public static final ParticleType.ParticleReader BLOCK = (buf, particle) -> particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
      public static final ParticleType.ParticleReader ITEM1_13 = ParticleType.itemHandler(Type.ITEM1_13);
      public static final ParticleType.ParticleReader ITEM1_13_2 = ParticleType.itemHandler(Type.ITEM1_13_2);
      public static final ParticleType.ParticleReader ITEM1_20_2 = ParticleType.itemHandler(Type.ITEM1_20_2);
      public static final ParticleType.ParticleReader DUST = (buf, particle) -> {
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
      };
      public static final ParticleType.ParticleReader DUST_TRANSITION = (buf, particle) -> {
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
      };
      public static final ParticleType.ParticleReader VIBRATION = (buf, particle) -> {
         particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
         String resourceLocation = Type.STRING.read(buf);
         particle.add(Type.STRING, resourceLocation);
         resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
         if (resourceLocation.equals("block")) {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
         } else if (resourceLocation.equals("entity")) {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
         } else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
         }

         particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
      };
      public static final ParticleType.ParticleReader VIBRATION1_19 = (buf, particle) -> {
         String resourceLocation = Type.STRING.read(buf);
         particle.add(Type.STRING, resourceLocation);
         resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
         if (resourceLocation.equals("block")) {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
         } else if (resourceLocation.equals("entity")) {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         } else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
         }

         particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
      };
      public static final ParticleType.ParticleReader VIBRATION1_20_3 = (buf, particle) -> {
         int sourceTypeId = Type.VAR_INT.readPrimitive(buf);
         particle.add(Type.VAR_INT, sourceTypeId);
         if (sourceTypeId == 0) {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
         } else if (sourceTypeId == 1) {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
         } else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + sourceTypeId);
         }

         particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
      };
      public static final ParticleType.ParticleReader SCULK_CHARGE = (buf, particle) -> particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(buf));
      public static final ParticleType.ParticleReader SHRIEK = (buf, particle) -> particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
   }
}
