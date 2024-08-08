package org.yaml.snakeyaml.constructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeConstructor extends BaseConstructor {
   public static final SafeConstructor.ConstructUndefined undefinedConstructor = new SafeConstructor.ConstructUndefined();
   private static final Map BOOL_VALUES = new HashMap();
   private static final Pattern TIMESTAMP_REGEXP;
   private static final Pattern YMD_REGEXP;

   public SafeConstructor() {
      this.yamlConstructors.put(Tag.NULL, new SafeConstructor.ConstructYamlNull(this));
      this.yamlConstructors.put(Tag.BOOL, new SafeConstructor.ConstructYamlBool(this));
      this.yamlConstructors.put(Tag.INT, new SafeConstructor.ConstructYamlInt(this));
      this.yamlConstructors.put(Tag.FLOAT, new SafeConstructor.ConstructYamlFloat(this));
      this.yamlConstructors.put(Tag.BINARY, new SafeConstructor.ConstructYamlBinary(this));
      this.yamlConstructors.put(Tag.TIMESTAMP, new SafeConstructor.ConstructYamlTimestamp());
      this.yamlConstructors.put(Tag.OMAP, new SafeConstructor.ConstructYamlOmap(this));
      this.yamlConstructors.put(Tag.PAIRS, new SafeConstructor.ConstructYamlPairs(this));
      this.yamlConstructors.put(Tag.SET, new SafeConstructor.ConstructYamlSet(this));
      this.yamlConstructors.put(Tag.STR, new SafeConstructor.ConstructYamlStr(this));
      this.yamlConstructors.put(Tag.SEQ, new SafeConstructor.ConstructYamlSeq(this));
      this.yamlConstructors.put(Tag.MAP, new SafeConstructor.ConstructYamlMap(this));
      this.yamlConstructors.put((Object)null, undefinedConstructor);
      this.yamlClassConstructors.put(NodeId.scalar, undefinedConstructor);
      this.yamlClassConstructors.put(NodeId.sequence, undefinedConstructor);
      this.yamlClassConstructors.put(NodeId.mapping, undefinedConstructor);
   }

   protected void flattenMapping(MappingNode var1) {
      this.processDuplicateKeys(var1);
      if (var1.isMerged()) {
         var1.setValue(this.mergeNode(var1, true, new HashMap(), new ArrayList()));
      }

   }

   protected void processDuplicateKeys(MappingNode var1) {
      List var2 = var1.getValue();
      HashMap var3 = new HashMap(var2.size());
      TreeSet var4 = new TreeSet();
      int var5 = 0;

      Iterator var6;
      for(var6 = var2.iterator(); var6.hasNext(); ++var5) {
         NodeTuple var7 = (NodeTuple)var6.next();
         Node var8 = var7.getKeyNode();
         if (!var8.getTag().equals(Tag.MERGE)) {
            Object var9 = this.constructObject(var8);
            if (var9 != null) {
               try {
                  var9.hashCode();
               } catch (Exception var11) {
                  throw new ConstructorException("while constructing a mapping", var1.getStartMark(), "found unacceptable key " + var9, var7.getKeyNode().getStartMark(), var11);
               }
            }

            Integer var10 = (Integer)var3.put(var9, var5);
            if (var10 != null) {
               if (!this.isAllowDuplicateKeys()) {
                  throw new IllegalStateException("duplicate key: " + var9);
               }

               var4.add(var10);
            }
         }
      }

      var6 = var4.descendingIterator();

      while(var6.hasNext()) {
         var2.remove((Integer)var6.next());
      }

   }

   private List mergeNode(MappingNode var1, boolean var2, Map var3, List var4) {
      Iterator var5 = var1.getValue().iterator();

      while(true) {
         label56:
         while(var5.hasNext()) {
            NodeTuple var6 = (NodeTuple)var5.next();
            Node var7 = var6.getKeyNode();
            Node var8 = var6.getValueNode();
            if (var7.getTag().equals(Tag.MERGE)) {
               var5.remove();
               switch(var8.getNodeId()) {
               case mapping:
                  MappingNode var15 = (MappingNode)var8;
                  this.mergeNode(var15, false, var3, var4);
                  break;
               case sequence:
                  SequenceNode var10 = (SequenceNode)var8;
                  List var11 = var10.getValue();
                  Iterator var12 = var11.iterator();

                  while(true) {
                     if (!var12.hasNext()) {
                        continue label56;
                     }

                     Node var13 = (Node)var12.next();
                     if (!(var13 instanceof MappingNode)) {
                        throw new ConstructorException("while constructing a mapping", var1.getStartMark(), "expected a mapping for merging, but found " + var13.getNodeId(), var13.getStartMark());
                     }

                     MappingNode var14 = (MappingNode)var13;
                     this.mergeNode(var14, false, var3, var4);
                  }
               default:
                  throw new ConstructorException("while constructing a mapping", var1.getStartMark(), "expected a mapping or list of mappings for merging, but found " + var8.getNodeId(), var8.getStartMark());
               }
            } else {
               Object var9 = this.constructObject(var7);
               if (!var3.containsKey(var9)) {
                  var4.add(var6);
                  var3.put(var9, var4.size() - 1);
               } else if (var2) {
                  var4.set((Integer)var3.get(var9), var6);
               }
            }
         }

         return var4;
      }
   }

   protected void constructMapping2ndStep(MappingNode var1, Map var2) {
      this.flattenMapping(var1);
      super.constructMapping2ndStep(var1, var2);
   }

   protected void constructSet2ndStep(MappingNode var1, Set var2) {
      this.flattenMapping(var1);
      super.constructSet2ndStep(var1, var2);
   }

   private Number createNumber(int var1, String var2, int var3) {
      if (var1 < 0) {
         var2 = "-" + var2;
      }

      Object var4;
      try {
         var4 = Integer.valueOf(var2, var3);
      } catch (NumberFormatException var8) {
         try {
            var4 = Long.valueOf(var2, var3);
         } catch (NumberFormatException var7) {
            var4 = new BigInteger(var2, var3);
         }
      }

      return (Number)var4;
   }

   static Map access$000() {
      return BOOL_VALUES;
   }

   static Number access$100(SafeConstructor var0, int var1, String var2, int var3) {
      return var0.createNumber(var1, var2, var3);
   }

   static Pattern access$200() {
      return YMD_REGEXP;
   }

   static Pattern access$300() {
      return TIMESTAMP_REGEXP;
   }

   static {
      BOOL_VALUES.put("yes", Boolean.TRUE);
      BOOL_VALUES.put("no", Boolean.FALSE);
      BOOL_VALUES.put("true", Boolean.TRUE);
      BOOL_VALUES.put("false", Boolean.FALSE);
      BOOL_VALUES.put("on", Boolean.TRUE);
      BOOL_VALUES.put("off", Boolean.FALSE);
      TIMESTAMP_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)(?:(?:[Tt]|[ \t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(?:\\.([0-9]*))?(?:[ \t]*(?:Z|([-+][0-9][0-9]?)(?::([0-9][0-9])?)?))?)?$");
      YMD_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)$");
   }

   public static final class ConstructUndefined extends AbstractConstruct {
      public Object construct(Node var1) {
         throw new ConstructorException((String)null, (Mark)null, "could not determine a constructor for the tag " + var1.getTag(), var1.getStartMark());
      }
   }

   public class ConstructYamlMap implements Construct {
      final SafeConstructor this$0;

      public ConstructYamlMap(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         return var1.isTwoStepsConstruction() ? this.this$0.createDefaultMap() : this.this$0.constructMapping((MappingNode)var1);
      }

      public void construct2ndStep(Node var1, Object var2) {
         if (var1.isTwoStepsConstruction()) {
            this.this$0.constructMapping2ndStep((MappingNode)var1, (Map)var2);
         } else {
            throw new YAMLException("Unexpected recursive mapping structure. Node: " + var1);
         }
      }
   }

   public class ConstructYamlSeq implements Construct {
      final SafeConstructor this$0;

      public ConstructYamlSeq(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         SequenceNode var2 = (SequenceNode)var1;
         return var1.isTwoStepsConstruction() ? this.this$0.newList(var2) : this.this$0.constructSequence(var2);
      }

      public void construct2ndStep(Node var1, Object var2) {
         if (var1.isTwoStepsConstruction()) {
            this.this$0.constructSequenceStep2((SequenceNode)var1, (List)var2);
         } else {
            throw new YAMLException("Unexpected recursive sequence structure. Node: " + var1);
         }
      }
   }

   public class ConstructYamlStr extends AbstractConstruct {
      final SafeConstructor this$0;

      public ConstructYamlStr(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         return this.this$0.constructScalar((ScalarNode)var1);
      }
   }

   public class ConstructYamlSet implements Construct {
      final SafeConstructor this$0;

      public ConstructYamlSet(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         if (var1.isTwoStepsConstruction()) {
            return this.this$0.constructedObjects.containsKey(var1) ? this.this$0.constructedObjects.get(var1) : this.this$0.createDefaultSet();
         } else {
            return this.this$0.constructSet((MappingNode)var1);
         }
      }

      public void construct2ndStep(Node var1, Object var2) {
         if (var1.isTwoStepsConstruction()) {
            this.this$0.constructSet2ndStep((MappingNode)var1, (Set)var2);
         } else {
            throw new YAMLException("Unexpected recursive set structure. Node: " + var1);
         }
      }
   }

   public class ConstructYamlPairs extends AbstractConstruct {
      final SafeConstructor this$0;

      public ConstructYamlPairs(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         if (!(var1 instanceof SequenceNode)) {
            throw new ConstructorException("while constructing pairs", var1.getStartMark(), "expected a sequence, but found " + var1.getNodeId(), var1.getStartMark());
         } else {
            SequenceNode var2 = (SequenceNode)var1;
            ArrayList var3 = new ArrayList(var2.getValue().size());
            Iterator var4 = var2.getValue().iterator();

            while(var4.hasNext()) {
               Node var5 = (Node)var4.next();
               if (!(var5 instanceof MappingNode)) {
                  throw new ConstructorException("while constructingpairs", var1.getStartMark(), "expected a mapping of length 1, but found " + var5.getNodeId(), var5.getStartMark());
               }

               MappingNode var6 = (MappingNode)var5;
               if (var6.getValue().size() != 1) {
                  throw new ConstructorException("while constructing pairs", var1.getStartMark(), "expected a single mapping item, but found " + var6.getValue().size() + " items", var6.getStartMark());
               }

               Node var7 = ((NodeTuple)var6.getValue().get(0)).getKeyNode();
               Node var8 = ((NodeTuple)var6.getValue().get(0)).getValueNode();
               Object var9 = this.this$0.constructObject(var7);
               Object var10 = this.this$0.constructObject(var8);
               var3.add(new Object[]{var9, var10});
            }

            return var3;
         }
      }
   }

   public class ConstructYamlOmap extends AbstractConstruct {
      final SafeConstructor this$0;

      public ConstructYamlOmap(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         LinkedHashMap var2 = new LinkedHashMap();
         if (!(var1 instanceof SequenceNode)) {
            throw new ConstructorException("while constructing an ordered map", var1.getStartMark(), "expected a sequence, but found " + var1.getNodeId(), var1.getStartMark());
         } else {
            SequenceNode var3 = (SequenceNode)var1;
            Iterator var4 = var3.getValue().iterator();

            while(var4.hasNext()) {
               Node var5 = (Node)var4.next();
               if (!(var5 instanceof MappingNode)) {
                  throw new ConstructorException("while constructing an ordered map", var1.getStartMark(), "expected a mapping of length 1, but found " + var5.getNodeId(), var5.getStartMark());
               }

               MappingNode var6 = (MappingNode)var5;
               if (var6.getValue().size() != 1) {
                  throw new ConstructorException("while constructing an ordered map", var1.getStartMark(), "expected a single mapping item, but found " + var6.getValue().size() + " items", var6.getStartMark());
               }

               Node var7 = ((NodeTuple)var6.getValue().get(0)).getKeyNode();
               Node var8 = ((NodeTuple)var6.getValue().get(0)).getValueNode();
               Object var9 = this.this$0.constructObject(var7);
               Object var10 = this.this$0.constructObject(var8);
               var2.put(var9, var10);
            }

            return var2;
         }
      }
   }

   public static class ConstructYamlTimestamp extends AbstractConstruct {
      private Calendar calendar;

      public Calendar getCalendar() {
         return this.calendar;
      }

      public Object construct(Node var1) {
         ScalarNode var2 = (ScalarNode)var1;
         String var3 = var2.getValue();
         Matcher var4 = SafeConstructor.YMD_REGEXP.matcher(var3);
         String var5;
         String var6;
         String var7;
         if (var4.matches()) {
            var5 = var4.group(1);
            var6 = var4.group(2);
            var7 = var4.group(3);
            this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            this.calendar.clear();
            this.calendar.set(1, Integer.parseInt(var5));
            this.calendar.set(2, Integer.parseInt(var6) - 1);
            this.calendar.set(5, Integer.parseInt(var7));
            return this.calendar.getTime();
         } else {
            var4 = SafeConstructor.TIMESTAMP_REGEXP.matcher(var3);
            if (!var4.matches()) {
               throw new YAMLException("Unexpected timestamp: " + var3);
            } else {
               var5 = var4.group(1);
               var6 = var4.group(2);
               var7 = var4.group(3);
               String var8 = var4.group(4);
               String var9 = var4.group(5);
               String var10 = var4.group(6);
               String var11 = var4.group(7);
               if (var11 != null) {
                  var10 = var10 + "." + var11;
               }

               double var12 = Double.parseDouble(var10);
               int var14 = (int)Math.round(Math.floor(var12));
               int var15 = (int)Math.round((var12 - (double)var14) * 1000.0D);
               String var16 = var4.group(8);
               String var17 = var4.group(9);
               TimeZone var18;
               if (var16 != null) {
                  String var19 = var17 != null ? ":" + var17 : "00";
                  var18 = TimeZone.getTimeZone("GMT" + var16 + var19);
               } else {
                  var18 = TimeZone.getTimeZone("UTC");
               }

               this.calendar = Calendar.getInstance(var18);
               this.calendar.set(1, Integer.parseInt(var5));
               this.calendar.set(2, Integer.parseInt(var6) - 1);
               this.calendar.set(5, Integer.parseInt(var7));
               this.calendar.set(11, Integer.parseInt(var8));
               this.calendar.set(12, Integer.parseInt(var9));
               this.calendar.set(13, var14);
               this.calendar.set(14, var15);
               return this.calendar.getTime();
            }
         }
      }
   }

   public class ConstructYamlBinary extends AbstractConstruct {
      final SafeConstructor this$0;

      public ConstructYamlBinary(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         String var2 = this.this$0.constructScalar((ScalarNode)var1).toString().replaceAll("\\s", "");
         byte[] var3 = Base64Coder.decode(var2.toCharArray());
         return var3;
      }
   }

   public class ConstructYamlFloat extends AbstractConstruct {
      final SafeConstructor this$0;

      public ConstructYamlFloat(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         String var2 = this.this$0.constructScalar((ScalarNode)var1).toString().replaceAll("_", "");
         byte var3 = 1;
         char var4 = var2.charAt(0);
         if (var4 == '-') {
            var3 = -1;
            var2 = var2.substring(1);
         } else if (var4 == '+') {
            var2 = var2.substring(1);
         }

         String var5 = var2.toLowerCase();
         if (".inf".equals(var5)) {
            return new Double(var3 == -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
         } else if (".nan".equals(var5)) {
            return new Double(Double.NaN);
         } else if (var2.indexOf(58) == -1) {
            Double var12 = Double.valueOf(var2);
            return new Double(var12 * (double)var3);
         } else {
            String[] var6 = var2.split(":");
            int var7 = 1;
            double var8 = 0.0D;
            int var10 = 0;

            for(int var11 = var6.length; var10 < var11; ++var10) {
               var8 += Double.parseDouble(var6[var11 - var10 - 1]) * (double)var7;
               var7 *= 60;
            }

            return new Double((double)var3 * var8);
         }
      }
   }

   public class ConstructYamlInt extends AbstractConstruct {
      final SafeConstructor this$0;

      public ConstructYamlInt(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         String var2 = this.this$0.constructScalar((ScalarNode)var1).toString().replaceAll("_", "");
         byte var3 = 1;
         char var4 = var2.charAt(0);
         if (var4 == '-') {
            var3 = -1;
            var2 = var2.substring(1);
         } else if (var4 == '+') {
            var2 = var2.substring(1);
         }

         boolean var5 = true;
         if ("0".equals(var2)) {
            return 0;
         } else {
            byte var11;
            if (var2.startsWith("0b")) {
               var2 = var2.substring(2);
               var11 = 2;
            } else if (var2.startsWith("0x")) {
               var2 = var2.substring(2);
               var11 = 16;
            } else {
               if (!var2.startsWith("0")) {
                  if (var2.indexOf(58) == -1) {
                     return this.this$0.createNumber(var3, var2, 10);
                  }

                  String[] var6 = var2.split(":");
                  int var7 = 1;
                  int var8 = 0;
                  int var9 = 0;

                  for(int var10 = var6.length; var9 < var10; ++var9) {
                     var8 = (int)((long)var8 + Long.parseLong(var6[var10 - var9 - 1]) * (long)var7);
                     var7 *= 60;
                  }

                  return this.this$0.createNumber(var3, String.valueOf(var8), 10);
               }

               var2 = var2.substring(1);
               var11 = 8;
            }

            return this.this$0.createNumber(var3, var2, var11);
         }
      }
   }

   public class ConstructYamlBool extends AbstractConstruct {
      final SafeConstructor this$0;

      public ConstructYamlBool(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         String var2 = (String)this.this$0.constructScalar((ScalarNode)var1);
         return SafeConstructor.BOOL_VALUES.get(var2.toLowerCase());
      }
   }

   public class ConstructYamlNull extends AbstractConstruct {
      final SafeConstructor this$0;

      public ConstructYamlNull(SafeConstructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         this.this$0.constructScalar((ScalarNode)var1);
         return null;
      }
   }
}
