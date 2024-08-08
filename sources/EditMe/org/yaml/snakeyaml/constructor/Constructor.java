package org.yaml.snakeyaml.constructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public class Constructor extends SafeConstructor {
   public Constructor() {
      this(Object.class);
   }

   public Constructor(Class var1) {
      this(new TypeDescription(checkRoot(var1)));
   }

   private static Class checkRoot(Class var0) {
      if (var0 == null) {
         throw new NullPointerException("Root class must be provided.");
      } else {
         return var0;
      }
   }

   public Constructor(TypeDescription var1) {
      this(var1, (Collection)null);
   }

   public Constructor(TypeDescription var1, Collection var2) {
      if (var1 == null) {
         throw new NullPointerException("Root type must be provided.");
      } else {
         this.yamlConstructors.put((Object)null, new Constructor.ConstructYamlObject(this));
         if (!Object.class.equals(var1.getType())) {
            this.rootTag = new Tag(var1.getType());
         }

         this.yamlClassConstructors.put(NodeId.scalar, new Constructor.ConstructScalar(this));
         this.yamlClassConstructors.put(NodeId.mapping, new Constructor.ConstructMapping(this));
         this.yamlClassConstructors.put(NodeId.sequence, new Constructor.ConstructSequence(this));
         this.addTypeDescription(var1);
         if (var2 != null) {
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
               TypeDescription var4 = (TypeDescription)var3.next();
               this.addTypeDescription(var4);
            }
         }

      }
   }

   public Constructor(String var1) throws ClassNotFoundException {
      this(Class.forName(check(var1)));
   }

   private static final String check(String var0) {
      if (var0 == null) {
         throw new NullPointerException("Root type must be provided.");
      } else if (var0.trim().length() == 0) {
         throw new YAMLException("Root type must be provided.");
      } else {
         return var0;
      }
   }

   protected Class getClassForNode(Node var1) {
      Class var2 = (Class)this.typeTags.get(var1.getTag());
      if (var2 == null) {
         String var3 = var1.getTag().getClassName();

         Class var4;
         try {
            var4 = this.getClassForName(var3);
         } catch (ClassNotFoundException var6) {
            throw new YAMLException("Class not found: " + var3);
         }

         this.typeTags.put(var1.getTag(), var4);
         return var4;
      } else {
         return var2;
      }
   }

   protected Class getClassForName(String var1) throws ClassNotFoundException {
      try {
         return Class.forName(var1, true, Thread.currentThread().getContextClassLoader());
      } catch (ClassNotFoundException var3) {
         return Class.forName(var1);
      }
   }

   protected class ConstructSequence implements Construct {
      final Constructor this$0;

      protected ConstructSequence(Constructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         SequenceNode var2 = (SequenceNode)var1;
         if (Set.class.isAssignableFrom(var1.getType())) {
            if (var1.isTwoStepsConstruction()) {
               throw new YAMLException("Set cannot be recursive.");
            } else {
               return this.this$0.constructSet(var2);
            }
         } else if (Collection.class.isAssignableFrom(var1.getType())) {
            return var1.isTwoStepsConstruction() ? this.this$0.newList(var2) : this.this$0.constructSequence(var2);
         } else if (var1.getType().isArray()) {
            return var1.isTwoStepsConstruction() ? this.this$0.createArray(var1.getType(), var2.getValue().size()) : this.this$0.constructArray(var2);
         } else {
            ArrayList var3 = new ArrayList(var2.getValue().size());
            java.lang.reflect.Constructor[] var4 = var1.getType().getDeclaredConstructors();
            int var5 = var4.length;

            int var6;
            for(var6 = 0; var6 < var5; ++var6) {
               java.lang.reflect.Constructor var7 = var4[var6];
               if (var2.getValue().size() == var7.getParameterTypes().length) {
                  var3.add(var7);
               }
            }

            if (!var3.isEmpty()) {
               Iterator var18;
               if (var3.size() == 1) {
                  Object[] var15 = new Object[var2.getValue().size()];
                  java.lang.reflect.Constructor var17 = (java.lang.reflect.Constructor)var3.get(0);
                  var6 = 0;

                  Node var20;
                  for(var18 = var2.getValue().iterator(); var18.hasNext(); var15[var6++] = this.this$0.constructObject(var20)) {
                     var20 = (Node)var18.next();
                     Class var21 = var17.getParameterTypes()[var6];
                     var20.setType(var21);
                  }

                  try {
                     var17.setAccessible(true);
                     return var17.newInstance(var15);
                  } catch (Exception var12) {
                     throw new YAMLException(var12);
                  }
               }

               List var14 = this.this$0.constructSequence(var2);
               Class[] var16 = new Class[var14.size()];
               var6 = 0;

               for(var18 = var14.iterator(); var18.hasNext(); ++var6) {
                  Object var8 = var18.next();
                  var16[var6] = var8.getClass();
               }

               var18 = var3.iterator();

               while(var18.hasNext()) {
                  java.lang.reflect.Constructor var19 = (java.lang.reflect.Constructor)var18.next();
                  Class[] var9 = var19.getParameterTypes();
                  boolean var10 = true;

                  for(int var11 = 0; var11 < var9.length; ++var11) {
                     if (!this.wrapIfPrimitive(var9[var11]).isAssignableFrom(var16[var11])) {
                        var10 = false;
                        break;
                     }
                  }

                  if (var10) {
                     try {
                        var19.setAccessible(true);
                        return var19.newInstance(var14.toArray());
                     } catch (Exception var13) {
                        throw new YAMLException(var13);
                     }
                  }
               }
            }

            throw new YAMLException("No suitable constructor with " + String.valueOf(var2.getValue().size()) + " arguments found for " + var1.getType());
         }
      }

      private final Class wrapIfPrimitive(Class var1) {
         if (!var1.isPrimitive()) {
            return var1;
         } else if (var1 == Integer.TYPE) {
            return Integer.class;
         } else if (var1 == Float.TYPE) {
            return Float.class;
         } else if (var1 == Double.TYPE) {
            return Double.class;
         } else if (var1 == Boolean.TYPE) {
            return Boolean.class;
         } else if (var1 == Long.TYPE) {
            return Long.class;
         } else if (var1 == Character.TYPE) {
            return Character.class;
         } else if (var1 == Short.TYPE) {
            return Short.class;
         } else if (var1 == Byte.TYPE) {
            return Byte.class;
         } else {
            throw new YAMLException("Unexpected primitive " + var1);
         }
      }

      public void construct2ndStep(Node var1, Object var2) {
         SequenceNode var3 = (SequenceNode)var1;
         if (List.class.isAssignableFrom(var1.getType())) {
            List var4 = (List)var2;
            this.this$0.constructSequenceStep2(var3, var4);
         } else {
            if (!var1.getType().isArray()) {
               throw new YAMLException("Immutable objects cannot be recursive.");
            }

            this.this$0.constructArrayStep2(var3, var2);
         }

      }
   }

   protected class ConstructScalar extends AbstractConstruct {
      final Constructor this$0;

      protected ConstructScalar(Constructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         ScalarNode var2 = (ScalarNode)var1;
         Class var3 = var2.getType();

         try {
            return this.this$0.newInstance(var3, var2, false);
         } catch (InstantiationException var15) {
            Object var4;
            if (!var3.isPrimitive() && var3 != String.class && !Number.class.isAssignableFrom(var3) && var3 != Boolean.class && !Date.class.isAssignableFrom(var3) && var3 != Character.class && var3 != BigInteger.class && var3 != BigDecimal.class && !Enum.class.isAssignableFrom(var3) && !Tag.BINARY.equals(var2.getTag()) && !Calendar.class.isAssignableFrom(var3) && var3 != UUID.class) {
               java.lang.reflect.Constructor[] var5 = var3.getDeclaredConstructors();
               int var6 = 0;
               java.lang.reflect.Constructor var7 = null;
               java.lang.reflect.Constructor[] var8 = var5;
               int var9 = var5.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  java.lang.reflect.Constructor var11 = var8[var10];
                  if (var11.getParameterTypes().length == 1) {
                     ++var6;
                     var7 = var11;
                  }
               }

               if (var7 == null) {
                  try {
                     return this.this$0.newInstance(var3, var2, false);
                  } catch (InstantiationException var13) {
                     throw new YAMLException("No single argument constructor found for " + var3 + " : " + var13.getMessage());
                  }
               }

               Object var16;
               if (var6 == 1) {
                  var16 = this.constructStandardJavaInstance(var7.getParameterTypes()[0], var2);
               } else {
                  var16 = this.this$0.constructScalar(var2);

                  try {
                     var7 = var3.getDeclaredConstructor(String.class);
                  } catch (Exception var14) {
                     throw new YAMLException("Can't construct a java object for scalar " + var2.getTag() + "; No String constructor found. Exception=" + var14.getMessage(), var14);
                  }
               }

               try {
                  var7.setAccessible(true);
                  var4 = var7.newInstance(var16);
               } catch (Exception var12) {
                  throw new ConstructorException((String)null, (Mark)null, "Can't construct a java object for scalar " + var2.getTag() + "; exception=" + var12.getMessage(), var2.getStartMark(), var12);
               }
            } else {
               var4 = this.constructStandardJavaInstance(var3, var2);
            }

            return var4;
         }
      }

      private Object constructStandardJavaInstance(Class var1, ScalarNode var2) {
         Object var3;
         Construct var4;
         if (var1 == String.class) {
            var4 = (Construct)this.this$0.yamlConstructors.get(Tag.STR);
            var3 = var4.construct(var2);
         } else if (var1 != Boolean.class && var1 != Boolean.TYPE) {
            if (var1 != Character.class && var1 != Character.TYPE) {
               if (Date.class.isAssignableFrom(var1)) {
                  var4 = (Construct)this.this$0.yamlConstructors.get(Tag.TIMESTAMP);
                  Date var10 = (Date)var4.construct(var2);
                  if (var1 == Date.class) {
                     var3 = var10;
                  } else {
                     try {
                        java.lang.reflect.Constructor var6 = var1.getConstructor(Long.TYPE);
                        var3 = var6.newInstance(var10.getTime());
                     } catch (RuntimeException var8) {
                        throw var8;
                     } catch (Exception var9) {
                        throw new YAMLException("Cannot construct: '" + var1 + "'");
                     }
                  }
               } else if (var1 != Float.class && var1 != Double.class && var1 != Float.TYPE && var1 != Double.TYPE && var1 != BigDecimal.class) {
                  if (var1 != Byte.class && var1 != Short.class && var1 != Integer.class && var1 != Long.class && var1 != BigInteger.class && var1 != Byte.TYPE && var1 != Short.TYPE && var1 != Integer.TYPE && var1 != Long.TYPE) {
                     if (Enum.class.isAssignableFrom(var1)) {
                        String var11 = var2.getValue();

                        try {
                           var3 = Enum.valueOf(var1, var11);
                        } catch (Exception var7) {
                           throw new YAMLException("Unable to find enum value '" + var11 + "' for enum class: " + var1.getName());
                        }
                     } else if (Calendar.class.isAssignableFrom(var1)) {
                        SafeConstructor.ConstructYamlTimestamp var12 = new SafeConstructor.ConstructYamlTimestamp();
                        var12.construct(var2);
                        var3 = var12.getCalendar();
                     } else if (Number.class.isAssignableFrom(var1)) {
                        SafeConstructor.ConstructYamlFloat var13 = this.this$0.new ConstructYamlFloat();
                        var3 = var13.construct(var2);
                     } else if (UUID.class == var1) {
                        var3 = UUID.fromString(var2.getValue());
                     } else {
                        if (!this.this$0.yamlConstructors.containsKey(var2.getTag())) {
                           throw new YAMLException("Unsupported class: " + var1);
                        }

                        var3 = ((Construct)this.this$0.yamlConstructors.get(var2.getTag())).construct(var2);
                     }
                  } else {
                     var4 = (Construct)this.this$0.yamlConstructors.get(Tag.INT);
                     var3 = var4.construct(var2);
                     if (var1 != Byte.class && var1 != Byte.TYPE) {
                        if (var1 != Short.class && var1 != Short.TYPE) {
                           if (var1 != Integer.class && var1 != Integer.TYPE) {
                              if (var1 != Long.class && var1 != Long.TYPE) {
                                 var3 = new BigInteger(var3.toString());
                              } else {
                                 var3 = Long.valueOf(var3.toString());
                              }
                           } else {
                              var3 = Integer.parseInt(var3.toString());
                           }
                        } else {
                           var3 = Short.valueOf(var3.toString());
                        }
                     } else {
                        var3 = Byte.valueOf(var3.toString());
                     }
                  }
               } else if (var1 == BigDecimal.class) {
                  var3 = new BigDecimal(var2.getValue());
               } else {
                  var4 = (Construct)this.this$0.yamlConstructors.get(Tag.FLOAT);
                  var3 = var4.construct(var2);
                  if (var1 == Float.class || var1 == Float.TYPE) {
                     var3 = new Float((Double)var3);
                  }
               }
            } else {
               var4 = (Construct)this.this$0.yamlConstructors.get(Tag.STR);
               String var5 = (String)var4.construct(var2);
               if (var5.length() == 0) {
                  var3 = null;
               } else {
                  if (var5.length() != 1) {
                     throw new YAMLException("Invalid node Character: '" + var5 + "'; length: " + var5.length());
                  }

                  var3 = var5.charAt(0);
               }
            }
         } else {
            var4 = (Construct)this.this$0.yamlConstructors.get(Tag.BOOL);
            var3 = var4.construct(var2);
         }

         return var3;
      }
   }

   protected class ConstructYamlObject implements Construct {
      final Constructor this$0;

      protected ConstructYamlObject(Constructor var1) {
         this.this$0 = var1;
      }

      private Construct getConstructor(Node var1) {
         Class var2 = this.this$0.getClassForNode(var1);
         var1.setType(var2);
         Construct var3 = (Construct)this.this$0.yamlClassConstructors.get(var1.getNodeId());
         return var3;
      }

      public Object construct(Node var1) {
         Object var2 = null;

         try {
            var2 = this.getConstructor(var1).construct(var1);
            return var2;
         } catch (ConstructorException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new ConstructorException((String)null, (Mark)null, "Can't construct a java object for " + var1.getTag() + "; exception=" + var5.getMessage(), var1.getStartMark(), var5);
         }
      }

      public void construct2ndStep(Node var1, Object var2) {
         try {
            this.getConstructor(var1).construct2ndStep(var1, var2);
         } catch (Exception var4) {
            throw new ConstructorException((String)null, (Mark)null, "Can't construct a second step for a java object for " + var1.getTag() + "; exception=" + var4.getMessage(), var1.getStartMark(), var4);
         }
      }
   }

   protected class ConstructMapping implements Construct {
      final Constructor this$0;

      protected ConstructMapping(Constructor var1) {
         this.this$0 = var1;
      }

      public Object construct(Node var1) {
         MappingNode var2 = (MappingNode)var1;
         if (Map.class.isAssignableFrom(var1.getType())) {
            return var1.isTwoStepsConstruction() ? this.this$0.newMap(var2) : this.this$0.constructMapping(var2);
         } else if (Collection.class.isAssignableFrom(var1.getType())) {
            return var1.isTwoStepsConstruction() ? this.this$0.newSet(var2) : this.this$0.constructSet(var2);
         } else {
            Object var3 = this.this$0.newInstance(var2);
            return var1.isTwoStepsConstruction() ? var3 : this.constructJavaBean2ndStep(var2, var3);
         }
      }

      public void construct2ndStep(Node var1, Object var2) {
         if (Map.class.isAssignableFrom(var1.getType())) {
            this.this$0.constructMapping2ndStep((MappingNode)var1, (Map)var2);
         } else if (Set.class.isAssignableFrom(var1.getType())) {
            this.this$0.constructSet2ndStep((MappingNode)var1, (Set)var2);
         } else {
            this.constructJavaBean2ndStep((MappingNode)var1, var2);
         }

      }

      protected Object constructJavaBean2ndStep(MappingNode var1, Object var2) {
         this.this$0.flattenMapping(var1);
         Class var3 = var1.getType();
         List var4 = var1.getValue();
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            NodeTuple var6 = (NodeTuple)var5.next();
            if (!(var6.getKeyNode() instanceof ScalarNode)) {
               throw new YAMLException("Keys must be scalars but found: " + var6.getKeyNode());
            }

            ScalarNode var7 = (ScalarNode)var6.getKeyNode();
            Node var8 = var6.getValueNode();
            var7.setType(String.class);
            String var9 = (String)this.this$0.constructObject(var7);

            try {
               TypeDescription var10 = (TypeDescription)this.this$0.typeDefinitions.get(var3);
               Property var11 = var10 == null ? this.getProperty(var3, var9) : var10.getProperty(var9);
               if (!var11.isWritable()) {
                  throw new YAMLException("No writable property '" + var9 + "' on class: " + var3.getName());
               }

               var8.setType(var11.getType());
               boolean var12 = var10 != null ? var10.setupPropertyType(var9, var8) : false;
               if (!var12 && var8.getNodeId() != NodeId.scalar) {
                  Class[] var13 = var11.getActualTypeArguments();
                  if (var13 != null && var13.length > 0) {
                     Class var14;
                     if (var8.getNodeId() == NodeId.sequence) {
                        var14 = var13[0];
                        SequenceNode var15 = (SequenceNode)var8;
                        var15.setListType(var14);
                     } else if (Set.class.isAssignableFrom(var8.getType())) {
                        var14 = var13[0];
                        MappingNode var19 = (MappingNode)var8;
                        var19.setOnlyKeyType(var14);
                        var19.setUseClassConstructor(true);
                     } else if (Map.class.isAssignableFrom(var8.getType())) {
                        var14 = var13[0];
                        Class var20 = var13[1];
                        MappingNode var16 = (MappingNode)var8;
                        var16.setTypes(var14, var20);
                        var16.setUseClassConstructor(true);
                     }
                  }
               }

               Object var18 = var10 != null ? this.newInstance(var10, var9, var8) : this.this$0.constructObject(var8);
               if ((var11.getType() == Float.TYPE || var11.getType() == Float.class) && var18 instanceof Double) {
                  var18 = ((Double)var18).floatValue();
               }

               if (var11.getType() == String.class && Tag.BINARY.equals(var8.getTag()) && var18 instanceof byte[]) {
                  var18 = new String((byte[])((byte[])var18));
               }

               if (var10 == null || !var10.setProperty(var2, var9, var18)) {
                  var11.set(var2, var18);
               }
            } catch (Exception var17) {
               throw new ConstructorException("Cannot create property=" + var9 + " for JavaBean=" + var2, var1.getStartMark(), var17.getMessage(), var8.getStartMark(), var17);
            }
         }

         return var2;
      }

      private Object newInstance(TypeDescription var1, String var2, Node var3) {
         Object var4 = var1.newInstance(var2, var3);
         if (var4 != null) {
            this.this$0.constructedObjects.put(var3, var4);
            return this.this$0.constructObjectNoCheck(var3);
         } else {
            return this.this$0.constructObject(var3);
         }
      }

      protected Property getProperty(Class var1, String var2) {
         return this.this$0.getPropertyUtils().getProperty(var1, var2);
      }
   }
}
