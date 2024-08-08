package org.yaml.snakeyaml.representer;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.StreamReader;

class SafeRepresenter extends BaseRepresenter {
   protected Map classTags;
   protected TimeZone timeZone = null;
   public static Pattern MULTILINE_PATTERN = Pattern.compile("\n|\u0085|\u2028|\u2029");

   public SafeRepresenter() {
      this.nullRepresenter = new SafeRepresenter.RepresentNull(this);
      this.representers.put(String.class, new SafeRepresenter.RepresentString(this));
      this.representers.put(Boolean.class, new SafeRepresenter.RepresentBoolean(this));
      this.representers.put(Character.class, new SafeRepresenter.RepresentString(this));
      this.representers.put(UUID.class, new SafeRepresenter.RepresentUuid(this));
      this.representers.put(byte[].class, new SafeRepresenter.RepresentByteArray(this));
      SafeRepresenter.RepresentPrimitiveArray var1 = new SafeRepresenter.RepresentPrimitiveArray(this);
      this.representers.put(short[].class, var1);
      this.representers.put(int[].class, var1);
      this.representers.put(long[].class, var1);
      this.representers.put(float[].class, var1);
      this.representers.put(double[].class, var1);
      this.representers.put(char[].class, var1);
      this.representers.put(boolean[].class, var1);
      this.multiRepresenters.put(Number.class, new SafeRepresenter.RepresentNumber(this));
      this.multiRepresenters.put(List.class, new SafeRepresenter.RepresentList(this));
      this.multiRepresenters.put(Map.class, new SafeRepresenter.RepresentMap(this));
      this.multiRepresenters.put(Set.class, new SafeRepresenter.RepresentSet(this));
      this.multiRepresenters.put(Iterator.class, new SafeRepresenter.RepresentIterator(this));
      this.multiRepresenters.put((new Object[0]).getClass(), new SafeRepresenter.RepresentArray(this));
      this.multiRepresenters.put(Date.class, new SafeRepresenter.RepresentDate(this));
      this.multiRepresenters.put(Enum.class, new SafeRepresenter.RepresentEnum(this));
      this.multiRepresenters.put(Calendar.class, new SafeRepresenter.RepresentDate(this));
      this.classTags = new HashMap();
   }

   protected Tag getTag(Class var1, Tag var2) {
      return this.classTags.containsKey(var1) ? (Tag)this.classTags.get(var1) : var2;
   }

   public Tag addClassTag(Class var1, Tag var2) {
      if (var2 == null) {
         throw new NullPointerException("Tag must be provided.");
      } else {
         return (Tag)this.classTags.put(var1, var2);
      }
   }

   public TimeZone getTimeZone() {
      return this.timeZone;
   }

   public void setTimeZone(TimeZone var1) {
      this.timeZone = var1;
   }

   protected class RepresentUuid implements Represent {
      final SafeRepresenter this$0;

      protected RepresentUuid(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         return this.this$0.representScalar(this.this$0.getTag(var1.getClass(), new Tag(UUID.class)), var1.toString());
      }
   }

   protected class RepresentByteArray implements Represent {
      final SafeRepresenter this$0;

      protected RepresentByteArray(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         char[] var2 = Base64Coder.encode((byte[])((byte[])var1));
         return this.this$0.representScalar(Tag.BINARY, String.valueOf(var2), '|');
      }
   }

   protected class RepresentEnum implements Represent {
      final SafeRepresenter this$0;

      protected RepresentEnum(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         Tag var2 = new Tag(var1.getClass());
         return this.this$0.representScalar(this.this$0.getTag(var1.getClass(), var2), ((Enum)var1).name());
      }
   }

   protected class RepresentDate implements Represent {
      final SafeRepresenter this$0;

      protected RepresentDate(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         Calendar var2;
         if (var1 instanceof Calendar) {
            var2 = (Calendar)var1;
         } else {
            var2 = Calendar.getInstance(this.this$0.getTimeZone() == null ? TimeZone.getTimeZone("UTC") : this.this$0.timeZone);
            var2.setTime((Date)var1);
         }

         int var3 = var2.get(1);
         int var4 = var2.get(2) + 1;
         int var5 = var2.get(5);
         int var6 = var2.get(11);
         int var7 = var2.get(12);
         int var8 = var2.get(13);
         int var9 = var2.get(14);
         StringBuilder var10 = new StringBuilder(String.valueOf(var3));

         while(var10.length() < 4) {
            var10.insert(0, "0");
         }

         var10.append("-");
         if (var4 < 10) {
            var10.append("0");
         }

         var10.append(String.valueOf(var4));
         var10.append("-");
         if (var5 < 10) {
            var10.append("0");
         }

         var10.append(String.valueOf(var5));
         var10.append("T");
         if (var6 < 10) {
            var10.append("0");
         }

         var10.append(String.valueOf(var6));
         var10.append(":");
         if (var7 < 10) {
            var10.append("0");
         }

         var10.append(String.valueOf(var7));
         var10.append(":");
         if (var8 < 10) {
            var10.append("0");
         }

         var10.append(String.valueOf(var8));
         if (var9 > 0) {
            if (var9 < 10) {
               var10.append(".00");
            } else if (var9 < 100) {
               var10.append(".0");
            } else {
               var10.append(".");
            }

            var10.append(String.valueOf(var9));
         }

         int var11 = var2.getTimeZone().getOffset(var2.get(0), var2.get(1), var2.get(2), var2.get(5), var2.get(7), var2.get(14));
         if (var11 == 0) {
            var10.append('Z');
         } else {
            if (var11 < 0) {
               var10.append('-');
               var11 *= -1;
            } else {
               var10.append('+');
            }

            int var12 = var11 / '\uea60';
            int var13 = var12 / 60;
            int var14 = var12 % 60;
            if (var13 < 10) {
               var10.append('0');
            }

            var10.append(var13);
            var10.append(':');
            if (var14 < 10) {
               var10.append('0');
            }

            var10.append(var14);
         }

         return this.this$0.representScalar(this.this$0.getTag(var1.getClass(), Tag.TIMESTAMP), var10.toString(), (Character)null);
      }
   }

   protected class RepresentSet implements Represent {
      final SafeRepresenter this$0;

      protected RepresentSet(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         LinkedHashMap var2 = new LinkedHashMap();
         Set var3 = (Set)var1;
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            var2.put(var5, (Object)null);
         }

         return this.this$0.representMapping(this.this$0.getTag(var1.getClass(), Tag.SET), var2, (Boolean)null);
      }
   }

   protected class RepresentMap implements Represent {
      final SafeRepresenter this$0;

      protected RepresentMap(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         return this.this$0.representMapping(this.this$0.getTag(var1.getClass(), Tag.MAP), (Map)var1, (Boolean)null);
      }
   }

   protected class RepresentPrimitiveArray implements Represent {
      final SafeRepresenter this$0;

      protected RepresentPrimitiveArray(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         Class var2 = var1.getClass().getComponentType();
         if (Byte.TYPE == var2) {
            return this.this$0.representSequence(Tag.SEQ, this.asByteList(var1), (Boolean)null);
         } else if (Short.TYPE == var2) {
            return this.this$0.representSequence(Tag.SEQ, this.asShortList(var1), (Boolean)null);
         } else if (Integer.TYPE == var2) {
            return this.this$0.representSequence(Tag.SEQ, this.asIntList(var1), (Boolean)null);
         } else if (Long.TYPE == var2) {
            return this.this$0.representSequence(Tag.SEQ, this.asLongList(var1), (Boolean)null);
         } else if (Float.TYPE == var2) {
            return this.this$0.representSequence(Tag.SEQ, this.asFloatList(var1), (Boolean)null);
         } else if (Double.TYPE == var2) {
            return this.this$0.representSequence(Tag.SEQ, this.asDoubleList(var1), (Boolean)null);
         } else if (Character.TYPE == var2) {
            return this.this$0.representSequence(Tag.SEQ, this.asCharList(var1), (Boolean)null);
         } else if (Boolean.TYPE == var2) {
            return this.this$0.representSequence(Tag.SEQ, this.asBooleanList(var1), (Boolean)null);
         } else {
            throw new YAMLException("Unexpected primitive '" + var2.getCanonicalName() + "'");
         }
      }

      private List asByteList(Object var1) {
         byte[] var2 = (byte[])((byte[])var1);
         ArrayList var3 = new ArrayList(var2.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.add(var2[var4]);
         }

         return var3;
      }

      private List asShortList(Object var1) {
         short[] var2 = (short[])((short[])var1);
         ArrayList var3 = new ArrayList(var2.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.add(var2[var4]);
         }

         return var3;
      }

      private List asIntList(Object var1) {
         int[] var2 = (int[])((int[])var1);
         ArrayList var3 = new ArrayList(var2.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.add(var2[var4]);
         }

         return var3;
      }

      private List asLongList(Object var1) {
         long[] var2 = (long[])((long[])var1);
         ArrayList var3 = new ArrayList(var2.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.add(var2[var4]);
         }

         return var3;
      }

      private List asFloatList(Object var1) {
         float[] var2 = (float[])((float[])var1);
         ArrayList var3 = new ArrayList(var2.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.add(var2[var4]);
         }

         return var3;
      }

      private List asDoubleList(Object var1) {
         double[] var2 = (double[])((double[])var1);
         ArrayList var3 = new ArrayList(var2.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.add(var2[var4]);
         }

         return var3;
      }

      private List asCharList(Object var1) {
         char[] var2 = (char[])((char[])var1);
         ArrayList var3 = new ArrayList(var2.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.add(var2[var4]);
         }

         return var3;
      }

      private List asBooleanList(Object var1) {
         boolean[] var2 = (boolean[])((boolean[])var1);
         ArrayList var3 = new ArrayList(var2.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.add(var2[var4]);
         }

         return var3;
      }
   }

   protected class RepresentArray implements Represent {
      final SafeRepresenter this$0;

      protected RepresentArray(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         Object[] var2 = (Object[])((Object[])var1);
         List var3 = Arrays.asList(var2);
         return this.this$0.representSequence(Tag.SEQ, var3, (Boolean)null);
      }
   }

   private static class IteratorWrapper implements Iterable {
      private Iterator iter;

      public IteratorWrapper(Iterator var1) {
         this.iter = var1;
      }

      public Iterator iterator() {
         return this.iter;
      }
   }

   protected class RepresentIterator implements Represent {
      final SafeRepresenter this$0;

      protected RepresentIterator(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         Iterator var2 = (Iterator)var1;
         return this.this$0.representSequence(this.this$0.getTag(var1.getClass(), Tag.SEQ), new SafeRepresenter.IteratorWrapper(var2), (Boolean)null);
      }
   }

   protected class RepresentList implements Represent {
      final SafeRepresenter this$0;

      protected RepresentList(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         return this.this$0.representSequence(this.this$0.getTag(var1.getClass(), Tag.SEQ), (List)var1, (Boolean)null);
      }
   }

   protected class RepresentNumber implements Represent {
      final SafeRepresenter this$0;

      protected RepresentNumber(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         Tag var2;
         String var3;
         if (!(var1 instanceof Byte) && !(var1 instanceof Short) && !(var1 instanceof Integer) && !(var1 instanceof Long) && !(var1 instanceof BigInteger)) {
            Number var4 = (Number)var1;
            var2 = Tag.FLOAT;
            if (var4.equals(Double.NaN)) {
               var3 = ".NaN";
            } else if (var4.equals(Double.POSITIVE_INFINITY)) {
               var3 = ".inf";
            } else if (var4.equals(Double.NEGATIVE_INFINITY)) {
               var3 = "-.inf";
            } else {
               var3 = var4.toString();
            }
         } else {
            var2 = Tag.INT;
            var3 = var1.toString();
         }

         return this.this$0.representScalar(this.this$0.getTag(var1.getClass(), var2), var3);
      }
   }

   protected class RepresentBoolean implements Represent {
      final SafeRepresenter this$0;

      protected RepresentBoolean(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         String var2;
         if (Boolean.TRUE.equals(var1)) {
            var2 = "true";
         } else {
            var2 = "false";
         }

         return this.this$0.representScalar(Tag.BOOL, var2);
      }
   }

   protected class RepresentString implements Represent {
      final SafeRepresenter this$0;

      protected RepresentString(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         Tag var2 = Tag.STR;
         Character var3 = null;
         String var4 = var1.toString();
         if (!StreamReader.isPrintable(var4)) {
            var2 = Tag.BINARY;

            char[] var5;
            try {
               byte[] var6 = var4.getBytes("UTF-8");
               String var7 = new String(var6, "UTF-8");
               if (!var7.equals(var4)) {
                  throw new YAMLException("invalid string value has occurred");
               }

               var5 = Base64Coder.encode(var6);
            } catch (UnsupportedEncodingException var8) {
               throw new YAMLException(var8);
            }

            var4 = String.valueOf(var5);
            var3 = '|';
         }

         if (this.this$0.defaultScalarStyle == null && SafeRepresenter.MULTILINE_PATTERN.matcher(var4).find()) {
            var3 = '|';
         }

         return this.this$0.representScalar(var2, var4, var3);
      }
   }

   protected class RepresentNull implements Represent {
      final SafeRepresenter this$0;

      protected RepresentNull(SafeRepresenter var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         return this.this$0.representScalar(Tag.NULL, "null");
      }
   }
}
