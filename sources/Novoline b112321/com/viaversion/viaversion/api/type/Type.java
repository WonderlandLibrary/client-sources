package com.viaversion.viaversion.api.type;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.ByteBufReader;
import com.viaversion.viaversion.api.type.ByteBufWriter;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.api.type.types.BooleanType;
import com.viaversion.viaversion.api.type.types.ByteArrayType;
import com.viaversion.viaversion.api.type.types.ByteType;
import com.viaversion.viaversion.api.type.types.FloatType;
import com.viaversion.viaversion.api.type.types.ShortType;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.api.type.types.UnsignedByteType;
import com.viaversion.viaversion.api.type.types.VarIntType;
import com.viaversion.viaversion.api.type.types.VarLongType;
import com.viaversion.viaversion.api.type.types.minecraft.BlockChangeRecordType;
import com.viaversion.viaversion.api.type.types.minecraft.EulerAngleType;
import com.viaversion.viaversion.api.type.types.minecraft.FlatItemArrayType;
import com.viaversion.viaversion.api.type.types.minecraft.FlatItemType;
import com.viaversion.viaversion.api.type.types.minecraft.FlatVarIntItemArrayType;
import com.viaversion.viaversion.api.type.types.minecraft.FlatVarIntItemType;
import com.viaversion.viaversion.api.type.types.minecraft.ItemArrayType;
import com.viaversion.viaversion.api.type.types.minecraft.ItemType;
import com.viaversion.viaversion.api.type.types.minecraft.VarLongBlockChangeRecordType;
import com.viaversion.viaversion.api.type.types.minecraft.VillagerDataType;
import net.a26;
import net.v5;
import net.v_;
import net.vd;
import net.vj;
import net.vx;
import net.w4;
import net.w6;
import net.wA;
import net.wC;
import net.wD;
import net.wI;
import net.wT;
import net.wX;
import net.wc;
import net.we;
import net.wh;
import net.wq;
import net.wy;

public abstract class Type implements ByteBufReader, ByteBufWriter {
   public static final Type k;
   /** @deprecated */
   @Deprecated
   public static final Type BYTE_ARRAY;
   public static final Type BYTE_ARRAY_PRIMITIVE;
   public static final Type REMAINING_BYTES;
   public static final Type M;
   /** @deprecated */
   @Deprecated
   public static final Type Q;
   public static final Type c;
   /** @deprecated */
   @Deprecated
   public static final Type BOOLEAN_ARRAY;
   public static final Type I;
   /** @deprecated */
   @Deprecated
   public static final Type U;
   public static final Type m;
   /** @deprecated */
   @Deprecated
   public static final Type DOUBLE_ARRAY;
   public static final Type g;
   /** @deprecated */
   @Deprecated
   public static final Type LONG_ARRAY;
   public static final FloatType FLOAT;
   /** @deprecated */
   @Deprecated
   public static final Type FLOAT_ARRAY;
   public static final ShortType SHORT;
   /** @deprecated */
   @Deprecated
   public static final Type r;
   public static final Type Z;
   /** @deprecated */
   @Deprecated
   public static final Type H;
   public static final Type p;
   public static final Type STRING;
   public static final Type STRING_ARRAY;
   public static final Type UUID;
   public static final Type UUID_INT_ARRAY;
   public static final Type UUID_ARRAY;
   public static final VarIntType VAR_INT;
   /** @deprecated */
   @Deprecated
   public static final Type UNSIGNED_SHORT_ARRAY;
   public static final Type VAR_INT_ARRAY_PRIMITIVE;
   public static final Type A;
   public static final VarLongType VAR_LONG;
   /** @deprecated */
   @Deprecated
   public static final Type VAR_LONG_ARRAY;
   public static final Type af;
   public static final Type POSITION;
   public static final Type POSITION1_14;
   public static final Type ROTATION;
   public static final Type VECTOR;
   public static final Type ac;
   public static final Type j;
   public static final Type OPTIONAL_UUID;
   public static final Type X;
   public static final Type v;
   public static final Type b;
   public static final Type ITEM;
   public static final Type ITEM_ARRAY;
   public static final Type BLOCK_CHANGE_RECORD;
   public static final Type BLOCK_CHANGE_RECORD_ARRAY;
   public static final Type VAR_LONG_BLOCK_CHANGE_RECORD;
   public static final Type VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY;
   public static final Type VILLAGER_DATA;
   public static final Type FLAT_ITEM;
   public static final Type FLAT_VAR_INT_ITEM;
   public static final Type FLAT_ITEM_ARRAY;
   public static final Type FLAT_VAR_INT_ITEM_ARRAY;
   public static final Type FLAT_ITEM_ARRAY_VAR_INT;
   public static final Type FLAT_VAR_INT_ITEM_ARRAY_VAR_INT;
   private final Class outputClass;
   private final String typeName;
   private static PacketRemapper[] N;

   public Type(String var1, Class var2) {
      this.outputClass = var2;
      this.typeName = var1;
   }

   public Class getOutputClass() {
      return this.outputClass;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public Class getBaseClass() {
      return this.getClass();
   }

   public String toString() {
      PacketRemapper[] var1 = a26.b();
      String var10000 = "Type|" + this.typeName;
      if(PacketRemapper.b() == null) {
         a26.b((PacketRemapper[])(new PacketRemapper[5]));
      }

      return var10000;
   }

   static {
      if(b() != null) {
         b(new PacketRemapper[4]);
      }

      k = new ByteType();
      BYTE_ARRAY = new ArrayType(k);
      BYTE_ARRAY_PRIMITIVE = new ByteArrayType();
      REMAINING_BYTES = new v5();
      M = new UnsignedByteType();
      Q = new ArrayType(M);
      c = new BooleanType();
      BOOLEAN_ARRAY = new ArrayType(c);
      I = new v_();
      U = new ArrayType(I);
      m = new vd();
      DOUBLE_ARRAY = new ArrayType(m);
      g = new vj();
      LONG_ARRAY = new ArrayType(g);
      FLOAT = new FloatType();
      FLOAT_ARRAY = new ArrayType(FLOAT);
      SHORT = new ShortType();
      r = new ArrayType(SHORT);
      Z = new wc();
      H = new ArrayType(Z);
      p = new vx();
      STRING = new StringType();
      STRING_ARRAY = new ArrayType(STRING);
      UUID = new wA();
      UUID_INT_ARRAY = new UUIDIntArrayType();
      UUID_ARRAY = new ArrayType(UUID);
      VAR_INT = new VarIntType();
      UNSIGNED_SHORT_ARRAY = new ArrayType(VAR_INT);
      VAR_INT_ARRAY_PRIMITIVE = new wC();
      A = new wX();
      VAR_LONG = new VarLongType();
      VAR_LONG_ARRAY = new ArrayType(VAR_LONG);
      af = new wD();
      POSITION = new wT();
      POSITION1_14 = new wh();
      ROTATION = new EulerAngleType();
      VECTOR = new wy();
      ac = new w4();
      j = new ArrayType(ac);
      OPTIONAL_UUID = new w6();
      X = new we();
      v = new wI();
      b = new wq();
      ITEM = new ItemType();
      ITEM_ARRAY = new ItemArrayType();
      BLOCK_CHANGE_RECORD = new BlockChangeRecordType();
      BLOCK_CHANGE_RECORD_ARRAY = new ArrayType(BLOCK_CHANGE_RECORD);
      VAR_LONG_BLOCK_CHANGE_RECORD = new VarLongBlockChangeRecordType();
      VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY = new ArrayType(VAR_LONG_BLOCK_CHANGE_RECORD);
      VILLAGER_DATA = new VillagerDataType();
      FLAT_ITEM = new FlatItemType();
      FLAT_VAR_INT_ITEM = new FlatVarIntItemType();
      FLAT_ITEM_ARRAY = new FlatItemArrayType();
      FLAT_VAR_INT_ITEM_ARRAY = new FlatVarIntItemArrayType();
      FLAT_ITEM_ARRAY_VAR_INT = new ArrayType(FLAT_ITEM);
      FLAT_VAR_INT_ITEM_ARRAY_VAR_INT = new ArrayType(FLAT_VAR_INT_ITEM);
   }

   public static void b(PacketRemapper[] var0) {
      N = var0;
   }

   public static PacketRemapper[] b() {
      return N;
   }
}
