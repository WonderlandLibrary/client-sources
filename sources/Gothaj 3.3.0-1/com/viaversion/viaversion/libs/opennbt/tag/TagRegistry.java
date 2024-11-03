package com.viaversion.viaversion.libs.opennbt.tag;

import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.IOException;
import org.jetbrains.annotations.Nullable;

public final class TagRegistry {
   public static final int END = 0;
   private static final int HIGHEST_ID = 12;
   private static final TagRegistry.RegisteredTagType[] TAGS = new TagRegistry.RegisteredTagType[13];
   private static final Object2IntMap<Class<? extends Tag>> TAG_TO_ID = new Object2IntOpenHashMap<>();

   public static <T extends Tag> void register(int id, Class<T> tag, TagRegistry.TagSupplier<T> supplier) {
      if (id < 0 || id > 12) {
         throw new IllegalArgumentException("Tag ID must be between 0 and 12");
      } else if (TAGS[id] != null) {
         throw new IllegalArgumentException("Tag ID \"" + id + "\" is already in use.");
      } else if (TAG_TO_ID.containsKey(tag)) {
         throw new IllegalArgumentException("Tag \"" + tag.getSimpleName() + "\" is already registered.");
      } else {
         TAGS[id] = new TagRegistry.RegisteredTagType(tag, supplier);
         TAG_TO_ID.put(tag, id);
      }
   }

   @Nullable
   public static Class<? extends Tag> getClassFor(int id) {
      return id >= 0 && id < TAGS.length ? TAGS[id].type : null;
   }

   public static int getIdFor(Class<? extends Tag> clazz) {
      return TAG_TO_ID.getInt(clazz);
   }

   public static Tag read(int id, DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
      TagRegistry.TagSupplier<?> supplier = id > 0 && id < TAGS.length ? TAGS[id].supplier : null;
      if (supplier == null) {
         throw new IllegalArgumentException("Could not find tag with ID \"" + id + "\".");
      } else {
         return supplier.create(in, tagLimiter, nestingLevel);
      }
   }

   static {
      TAG_TO_ID.defaultReturnValue(-1);
      register(1, ByteTag.class, (in, tagLimiter, nestingLevel) -> ByteTag.read(in, tagLimiter));
      register(2, ShortTag.class, (in, tagLimiter, nestingLevel) -> ShortTag.read(in, tagLimiter));
      register(3, IntTag.class, (in, tagLimiter, nestingLevel) -> IntTag.read(in, tagLimiter));
      register(4, LongTag.class, (in, tagLimiter, nestingLevel) -> LongTag.read(in, tagLimiter));
      register(5, FloatTag.class, (in, tagLimiter, nestingLevel) -> FloatTag.read(in, tagLimiter));
      register(6, DoubleTag.class, (in, tagLimiter, nestingLevel) -> DoubleTag.read(in, tagLimiter));
      register(7, ByteArrayTag.class, (in, tagLimiter, nestingLevel) -> ByteArrayTag.read(in, tagLimiter));
      register(8, StringTag.class, (in, tagLimiter, nestingLevel) -> StringTag.read(in, tagLimiter));
      register(9, ListTag.class, ListTag::read);
      register(10, CompoundTag.class, CompoundTag::read);
      register(11, IntArrayTag.class, (in, tagLimiter, nestingLevel) -> IntArrayTag.read(in, tagLimiter));
      register(12, LongArrayTag.class, (in, tagLimiter, nestingLevel) -> LongArrayTag.read(in, tagLimiter));
   }

   private static final class RegisteredTagType {
      private final Class<? extends Tag> type;
      private final TagRegistry.TagSupplier<? extends Tag> supplier;

      private <T extends Tag> RegisteredTagType(Class<T> type, TagRegistry.TagSupplier<T> supplier) {
         this.type = type;
         this.supplier = supplier;
      }
   }

   @FunctionalInterface
   public interface TagSupplier<T extends Tag> {
      T create(DataInput var1, TagLimiter var2, int var3) throws IOException;
   }
}
