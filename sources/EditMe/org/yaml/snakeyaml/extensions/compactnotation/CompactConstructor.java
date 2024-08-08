package org.yaml.snakeyaml.extensions.compactnotation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class CompactConstructor extends Constructor {
   private static final Pattern GUESS_COMPACT = Pattern.compile("\\p{Alpha}.*\\s*\\((?:,?\\s*(?:(?:\\w*)|(?:\\p{Alpha}\\w*\\s*=.+))\\s*)+\\)");
   private static final Pattern FIRST_PATTERN = Pattern.compile("(\\p{Alpha}.*)(\\s*)\\((.*?)\\)");
   private static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\s*(\\p{Alpha}\\w*)\\s*=(.+)");
   private Construct compactConstruct;

   protected Object constructCompactFormat(ScalarNode var1, CompactData var2) {
      try {
         Object var3 = this.createInstance(var1, var2);
         HashMap var4 = new HashMap(var2.getProperties());
         this.setProperties(var3, var4);
         return var3;
      } catch (Exception var5) {
         throw new YAMLException(var5);
      }
   }

   protected Object createInstance(ScalarNode var1, CompactData var2) throws Exception {
      Class var3 = this.getClassForName(var2.getPrefix());
      Class[] var4 = new Class[var2.getArguments().size()];

      for(int var5 = 0; var5 < var4.length; ++var5) {
         var4[var5] = String.class;
      }

      java.lang.reflect.Constructor var6 = var3.getDeclaredConstructor(var4);
      var6.setAccessible(true);
      return var6.newInstance(var2.getArguments().toArray());
   }

   protected void setProperties(Object var1, Map var2) throws Exception {
      if (var2 == null) {
         throw new NullPointerException("Data for Compact Object Notation cannot be null.");
      } else {
         Iterator var3 = var2.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            String var5 = (String)var4.getKey();
            Property var6 = this.getPropertyUtils().getProperty(var1.getClass(), var5);

            try {
               var6.set(var1, var4.getValue());
            } catch (IllegalArgumentException var8) {
               throw new YAMLException("Cannot set property='" + var5 + "' with value='" + var2.get(var5) + "' (" + var2.get(var5).getClass() + ") in " + var1);
            }
         }

      }
   }

   public CompactData getCompactData(String var1) {
      if (!var1.endsWith(")")) {
         return null;
      } else if (var1.indexOf(40) < 0) {
         return null;
      } else {
         Matcher var2 = FIRST_PATTERN.matcher(var1);
         if (var2.matches()) {
            String var3 = var2.group(1).trim();
            String var4 = var2.group(3);
            CompactData var5 = new CompactData(var3);
            if (var4.length() == 0) {
               return var5;
            } else {
               String[] var6 = var4.split("\\s*,\\s*");

               for(int var7 = 0; var7 < var6.length; ++var7) {
                  String var8 = var6[var7];
                  if (var8.indexOf(61) < 0) {
                     var5.getArguments().add(var8);
                  } else {
                     Matcher var9 = PROPERTY_NAME_PATTERN.matcher(var8);
                     if (!var9.matches()) {
                        return null;
                     }

                     String var10 = var9.group(1);
                     String var11 = var9.group(2).trim();
                     var5.getProperties().put(var10, var11);
                  }
               }

               return var5;
            }
         } else {
            return null;
         }
      }
   }

   private Construct getCompactConstruct() {
      if (this.compactConstruct == null) {
         this.compactConstruct = this.createCompactConstruct();
      }

      return this.compactConstruct;
   }

   protected Construct createCompactConstruct() {
      return new CompactConstructor.ConstructCompactObject(this);
   }

   protected Construct getConstructor(Node var1) {
      if (var1 instanceof MappingNode) {
         MappingNode var2 = (MappingNode)var1;
         List var3 = var2.getValue();
         if (var3.size() == 1) {
            NodeTuple var4 = (NodeTuple)var3.get(0);
            Node var5 = var4.getKeyNode();
            if (var5 instanceof ScalarNode) {
               ScalarNode var6 = (ScalarNode)var5;
               if (GUESS_COMPACT.matcher(var6.getValue()).matches()) {
                  return this.getCompactConstruct();
               }
            }
         }
      } else if (var1 instanceof ScalarNode) {
         ScalarNode var7 = (ScalarNode)var1;
         if (GUESS_COMPACT.matcher(var7.getValue()).matches()) {
            return this.getCompactConstruct();
         }
      }

      return super.getConstructor(var1);
   }

   protected void applySequence(Object var1, List var2) {
      try {
         Property var3 = this.getPropertyUtils().getProperty(var1.getClass(), this.getSequencePropertyName(var1.getClass()));
         var3.set(var1, var2);
      } catch (Exception var4) {
         throw new YAMLException(var4);
      }
   }

   protected String getSequencePropertyName(Class var1) {
      Set var2 = this.getPropertyUtils().getProperties(var1);
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Property var4 = (Property)var3.next();
         if (!List.class.isAssignableFrom(var4.getType())) {
            var3.remove();
         }
      }

      if (var2.size() == 0) {
         throw new YAMLException("No list property found in " + var1);
      } else if (var2.size() > 1) {
         throw new YAMLException("Many list properties found in " + var1 + "; Please override getSequencePropertyName() to specify which property to use.");
      } else {
         return ((Property)var2.iterator().next()).getName();
      }
   }

   static List access$000(CompactConstructor var0, SequenceNode var1) {
      return var0.constructSequence(var1);
   }

   static Object access$100(CompactConstructor var0, ScalarNode var1) {
      return var0.constructScalar(var1);
   }

   public class ConstructCompactObject extends Constructor.ConstructMapping {
      final CompactConstructor this$0;

      public ConstructCompactObject(CompactConstructor var1) {
         super();
         this.this$0 = var1;
      }

      public void construct2ndStep(Node var1, Object var2) {
         MappingNode var3 = (MappingNode)var1;
         NodeTuple var4 = (NodeTuple)var3.getValue().iterator().next();
         Node var5 = var4.getValueNode();
         if (var5 instanceof MappingNode) {
            var5.setType(var2.getClass());
            this.constructJavaBean2ndStep((MappingNode)var5, var2);
         } else {
            this.this$0.applySequence(var2, this.this$0.constructSequence((SequenceNode)var5));
         }

      }

      public Object construct(Node var1) {
         ScalarNode var2 = null;
         if (var1 instanceof MappingNode) {
            MappingNode var3 = (MappingNode)var1;
            NodeTuple var4 = (NodeTuple)var3.getValue().iterator().next();
            var1.setTwoStepsConstruction(true);
            var2 = (ScalarNode)var4.getKeyNode();
         } else {
            var2 = (ScalarNode)var1;
         }

         CompactData var5 = this.this$0.getCompactData(var2.getValue());
         return var5 == null ? this.this$0.constructScalar(var2) : this.this$0.constructCompactFormat(var2, var5);
      }
   }
}
