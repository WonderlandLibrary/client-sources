package org.yaml.snakeyaml.constructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.util.EnumUtils;

public class Constructor extends SafeConstructor {
   public Constructor(LoaderOptions loadingConfig) {
      this(Object.class, loadingConfig);
   }

   public Constructor(Class<? extends Object> theRoot, LoaderOptions loadingConfig) {
      this(new TypeDescription(checkRoot(theRoot)), null, loadingConfig);
   }

   private static Class<? extends Object> checkRoot(Class<? extends Object> theRoot) {
      if (theRoot == null) {
         throw new NullPointerException("Root class must be provided.");
      } else {
         return theRoot;
      }
   }

   public Constructor(TypeDescription theRoot, LoaderOptions loadingConfig) {
      this(theRoot, null, loadingConfig);
   }

   public Constructor(TypeDescription theRoot, Collection<TypeDescription> moreTDs, LoaderOptions loadingConfig) {
      super(loadingConfig);
      if (theRoot == null) {
         throw new NullPointerException("Root type must be provided.");
      } else {
         this.yamlConstructors.put(null, new Constructor.ConstructYamlObject());
         if (!Object.class.equals(theRoot.getType())) {
            this.rootTag = new Tag(theRoot.getType());
         }

         this.yamlClassConstructors.put(NodeId.scalar, new Constructor.ConstructScalar());
         this.yamlClassConstructors.put(NodeId.mapping, new Constructor.ConstructMapping());
         this.yamlClassConstructors.put(NodeId.sequence, new Constructor.ConstructSequence());
         this.addTypeDescription(theRoot);
         if (moreTDs != null) {
            for (TypeDescription td : moreTDs) {
               this.addTypeDescription(td);
            }
         }
      }
   }

   public Constructor(String theRoot, LoaderOptions loadingConfig) throws ClassNotFoundException {
      this((Class<? extends Object>)Class.forName(check(theRoot)), loadingConfig);
   }

   private static String check(String s) {
      if (s == null) {
         throw new NullPointerException("Root type must be provided.");
      } else if (s.trim().length() == 0) {
         throw new YAMLException("Root type must be provided.");
      } else {
         return s;
      }
   }

   protected Class<?> getClassForNode(Node node) {
      Class<? extends Object> classForTag = this.typeTags.get(node.getTag());
      if (classForTag == null) {
         String name = node.getTag().getClassName();

         Class<?> cl;
         try {
            cl = this.getClassForName(name);
         } catch (ClassNotFoundException var6) {
            throw new YAMLException("Class not found: " + name);
         }

         this.typeTags.put(node.getTag(), (Class<? extends Object>)cl);
         return cl;
      } else {
         return classForTag;
      }
   }

   protected Class<?> getClassForName(String name) throws ClassNotFoundException {
      try {
         return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
      } catch (ClassNotFoundException var3) {
         return Class.forName(name);
      }
   }

   protected class ConstructMapping implements Construct {
      @Override
      public Object construct(Node node) {
         MappingNode mnode = (MappingNode)node;
         if (Map.class.isAssignableFrom(node.getType())) {
            return node.isTwoStepsConstruction() ? Constructor.this.newMap(mnode) : Constructor.this.constructMapping(mnode);
         } else if (Collection.class.isAssignableFrom(node.getType())) {
            return node.isTwoStepsConstruction() ? Constructor.this.newSet(mnode) : Constructor.this.constructSet(mnode);
         } else {
            Object obj = Constructor.this.newInstance(mnode);
            if (obj != BaseConstructor.NOT_INSTANTIATED_OBJECT) {
               return node.isTwoStepsConstruction() ? obj : this.constructJavaBean2ndStep(mnode, obj);
            } else {
               throw new ConstructorException(null, null, "Can't create an instance for " + mnode.getTag(), node.getStartMark());
            }
         }
      }

      @Override
      public void construct2ndStep(Node node, Object object) {
         if (Map.class.isAssignableFrom(node.getType())) {
            Constructor.this.constructMapping2ndStep((MappingNode)node, (Map<Object, Object>)object);
         } else if (Set.class.isAssignableFrom(node.getType())) {
            Constructor.this.constructSet2ndStep((MappingNode)node, (Set<Object>)object);
         } else {
            this.constructJavaBean2ndStep((MappingNode)node, object);
         }
      }

      protected Object constructJavaBean2ndStep(MappingNode node, Object object) {
         Constructor.this.flattenMapping(node, true);
         Class<? extends Object> beanType = node.getType();

         for (NodeTuple tuple : node.getValue()) {
            Node valueNode = tuple.getValueNode();
            String key = (String)Constructor.this.constructObject(tuple.getKeyNode());

            try {
               TypeDescription memberDescription = Constructor.this.typeDefinitions.get(beanType);
               Property property = memberDescription == null ? this.getProperty(beanType, key) : memberDescription.getProperty(key);
               if (!property.isWritable()) {
                  throw new YAMLException("No writable property '" + key + "' on class: " + beanType.getName());
               }

               valueNode.setType((Class<? extends Object>)property.getType());
               boolean typeDetected = memberDescription != null && memberDescription.setupPropertyType(key, valueNode);
               if (!typeDetected && valueNode.getNodeId() != NodeId.scalar) {
                  Class<?>[] arguments = property.getActualTypeArguments();
                  if (arguments != null && arguments.length > 0) {
                     if (valueNode.getNodeId() == NodeId.sequence) {
                        Class<?> t = arguments[0];
                        SequenceNode snode = (SequenceNode)valueNode;
                        snode.setListType((Class<? extends Object>)t);
                     } else if (Map.class.isAssignableFrom(valueNode.getType())) {
                        Class<?> keyType = arguments[0];
                        Class<?> valueType = arguments[1];
                        MappingNode mnode = (MappingNode)valueNode;
                        mnode.setTypes((Class<? extends Object>)keyType, (Class<? extends Object>)valueType);
                        mnode.setUseClassConstructor(Boolean.valueOf(true));
                     } else if (Collection.class.isAssignableFrom(valueNode.getType())) {
                        Class<?> t = arguments[0];
                        MappingNode mnode = (MappingNode)valueNode;
                        mnode.setOnlyKeyType((Class<? extends Object>)t);
                        mnode.setUseClassConstructor(Boolean.valueOf(true));
                     }
                  }
               }

               Object value = memberDescription != null ? this.newInstance(memberDescription, key, valueNode) : Constructor.this.constructObject(valueNode);
               if ((property.getType() == float.class || property.getType() == Float.class) && value instanceof Double) {
                  value = ((Double)value).floatValue();
               }

               if (property.getType() == String.class && Tag.BINARY.equals(valueNode.getTag()) && value instanceof byte[]) {
                  value = new String((byte[])value);
               }

               if (memberDescription == null || !memberDescription.setProperty(object, key, value)) {
                  property.set(object, value);
               }
            } catch (DuplicateKeyException var16) {
               throw var16;
            } catch (Exception var17) {
               throw new ConstructorException(
                  "Cannot create property=" + key + " for JavaBean=" + object, node.getStartMark(), var17.getMessage(), valueNode.getStartMark(), var17
               );
            }
         }

         return object;
      }

      private Object newInstance(TypeDescription memberDescription, String propertyName, Node node) {
         Object newInstance = memberDescription.newInstance(propertyName, node);
         if (newInstance != null) {
            Constructor.this.constructedObjects.put(node, newInstance);
            return Constructor.this.constructObjectNoCheck(node);
         } else {
            return Constructor.this.constructObject(node);
         }
      }

      protected Property getProperty(Class<? extends Object> type, String name) {
         return Constructor.this.getPropertyUtils().getProperty(type, name);
      }
   }

   protected class ConstructScalar extends AbstractConstruct {
      @Override
      public Object construct(Node nnode) {
         ScalarNode node = (ScalarNode)nnode;
         Class<?> type = node.getType();
         Object instance = Constructor.this.newInstance(type, node, false);
         if (instance != BaseConstructor.NOT_INSTANTIATED_OBJECT) {
            return instance;
         } else {
            Object result;
            if (!type.isPrimitive()
               && type != String.class
               && !Number.class.isAssignableFrom(type)
               && type != Boolean.class
               && !Date.class.isAssignableFrom(type)
               && type != Character.class
               && type != BigInteger.class
               && type != BigDecimal.class
               && !Enum.class.isAssignableFrom(type)
               && !Tag.BINARY.equals(node.getTag())
               && !Calendar.class.isAssignableFrom(type)
               && type != UUID.class) {
               java.lang.reflect.Constructor<?>[] javaConstructors = type.getDeclaredConstructors();
               int oneArgCount = 0;
               java.lang.reflect.Constructor<?> javaConstructor = null;

               for (java.lang.reflect.Constructor<?> c : javaConstructors) {
                  if (c.getParameterTypes().length == 1) {
                     oneArgCount++;
                     javaConstructor = c;
                  }
               }

               if (javaConstructor == null) {
                  throw new YAMLException("No single argument constructor found for " + type);
               }

               Object argument;
               if (oneArgCount == 1) {
                  argument = this.constructStandardJavaInstance(javaConstructor.getParameterTypes()[0], node);
               } else {
                  argument = Constructor.this.constructScalar(node);

                  try {
                     javaConstructor = type.getDeclaredConstructor(String.class);
                  } catch (Exception var14) {
                     throw new YAMLException(
                        "Can't construct a java object for scalar " + node.getTag() + "; No String constructor found. Exception=" + var14.getMessage(), var14
                     );
                  }
               }

               try {
                  javaConstructor.setAccessible(true);
                  result = javaConstructor.newInstance(argument);
               } catch (Exception var13) {
                  throw new ConstructorException(
                     null, null, "Can't construct a java object for scalar " + node.getTag() + "; exception=" + var13.getMessage(), node.getStartMark(), var13
                  );
               }
            } else {
               result = this.constructStandardJavaInstance(type, node);
            }

            return result;
         }
      }

      private Object constructStandardJavaInstance(Class type, ScalarNode node) {
         Object result;
         if (type == String.class) {
            Construct stringConstructor = Constructor.this.yamlConstructors.get(Tag.STR);
            result = stringConstructor.construct(node);
         } else if (type == Boolean.class || type == boolean.class) {
            Construct boolConstructor = Constructor.this.yamlConstructors.get(Tag.BOOL);
            result = boolConstructor.construct(node);
         } else if (type == Character.class || type == char.class) {
            Construct charConstructor = Constructor.this.yamlConstructors.get(Tag.STR);
            String ch = (String)charConstructor.construct(node);
            if (ch.length() == 0) {
               result = null;
            } else {
               if (ch.length() != 1) {
                  throw new YAMLException("Invalid node Character: '" + ch + "'; length: " + ch.length());
               }

               result = ch.charAt(0);
            }
         } else if (Date.class.isAssignableFrom(type)) {
            Construct dateConstructor = Constructor.this.yamlConstructors.get(Tag.TIMESTAMP);
            Date date = (Date)dateConstructor.construct(node);
            if (type == Date.class) {
               result = date;
            } else {
               try {
                  java.lang.reflect.Constructor<?> constr = type.getConstructor(long.class);
                  result = constr.newInstance(date.getTime());
               } catch (RuntimeException var8) {
                  throw var8;
               } catch (Exception var9) {
                  throw new YAMLException("Cannot construct: '" + type + "'");
               }
            }
         } else if (type != Float.class && type != Double.class && type != float.class && type != double.class && type != BigDecimal.class) {
            if (type == Byte.class
               || type == Short.class
               || type == Integer.class
               || type == Long.class
               || type == BigInteger.class
               || type == byte.class
               || type == short.class
               || type == int.class
               || type == long.class) {
               Construct intConstructor = Constructor.this.yamlConstructors.get(Tag.INT);
               result = intConstructor.construct(node);
               if (type == Byte.class || type == byte.class) {
                  result = Integer.valueOf(result.toString()).byteValue();
               } else if (type == Short.class || type == short.class) {
                  result = Integer.valueOf(result.toString()).shortValue();
               } else if (type == Integer.class || type == int.class) {
                  result = Integer.parseInt(result.toString());
               } else if (type != Long.class && type != long.class) {
                  result = new BigInteger(result.toString());
               } else {
                  result = Long.valueOf(result.toString());
               }
            } else if (Enum.class.isAssignableFrom(type)) {
               String enumValueName = node.getValue();

               try {
                  if (Constructor.this.loadingConfig.isEnumCaseSensitive()) {
                     result = Enum.valueOf(type, enumValueName);
                  } else {
                     result = EnumUtils.findEnumInsensitiveCase(type, enumValueName);
                  }
               } catch (Exception var7) {
                  throw new YAMLException("Unable to find enum value '" + enumValueName + "' for enum class: " + type.getName());
               }
            } else if (Calendar.class.isAssignableFrom(type)) {
               SafeConstructor.ConstructYamlTimestamp contr = new SafeConstructor.ConstructYamlTimestamp();
               contr.construct(node);
               result = contr.getCalendar();
            } else if (Number.class.isAssignableFrom(type)) {
               SafeConstructor.ConstructYamlFloat contr = Constructor.this.new ConstructYamlFloat();
               result = contr.construct(node);
            } else if (UUID.class == type) {
               result = UUID.fromString(node.getValue());
            } else {
               if (!Constructor.this.yamlConstructors.containsKey(node.getTag())) {
                  throw new YAMLException("Unsupported class: " + type);
               }

               result = Constructor.this.yamlConstructors.get(node.getTag()).construct(node);
            }
         } else if (type == BigDecimal.class) {
            result = new BigDecimal(node.getValue());
         } else {
            Construct doubleConstructor = Constructor.this.yamlConstructors.get(Tag.FLOAT);
            result = doubleConstructor.construct(node);
            if (type == Float.class || type == float.class) {
               result = ((Double)result).floatValue();
            }
         }

         return result;
      }
   }

   protected class ConstructSequence implements Construct {
      @Override
      public Object construct(Node node) {
         SequenceNode snode = (SequenceNode)node;
         if (Set.class.isAssignableFrom(node.getType())) {
            if (node.isTwoStepsConstruction()) {
               throw new YAMLException("Set cannot be recursive.");
            } else {
               return Constructor.this.constructSet(snode);
            }
         } else if (Collection.class.isAssignableFrom(node.getType())) {
            return node.isTwoStepsConstruction() ? Constructor.this.newList(snode) : Constructor.this.constructSequence(snode);
         } else if (node.getType().isArray()) {
            return node.isTwoStepsConstruction()
               ? Constructor.this.createArray(node.getType(), snode.getValue().size())
               : Constructor.this.constructArray(snode);
         } else {
            List<java.lang.reflect.Constructor<?>> possibleConstructors = new ArrayList<>(snode.getValue().size());

            for (java.lang.reflect.Constructor<?> constructor : node.getType().getDeclaredConstructors()) {
               if (snode.getValue().size() == constructor.getParameterTypes().length) {
                  possibleConstructors.add(constructor);
               }
            }

            if (!possibleConstructors.isEmpty()) {
               if (possibleConstructors.size() == 1) {
                  Object[] argumentList = new Object[snode.getValue().size()];
                  java.lang.reflect.Constructor<?> c = possibleConstructors.get(0);
                  int index = 0;

                  for (Node argumentNode : snode.getValue()) {
                     Class<?> type = c.getParameterTypes()[index];
                     argumentNode.setType((Class<? extends Object>)type);
                     argumentList[index++] = Constructor.this.constructObject(argumentNode);
                  }

                  try {
                     c.setAccessible(true);
                     return c.newInstance(argumentList);
                  } catch (Exception var12) {
                     throw new YAMLException(var12);
                  }
               }

               List<Object> argumentList = Constructor.this.constructSequence(snode);
               Class<?>[] parameterTypes = new Class[argumentList.size()];
               int index = 0;

               for (Object parameter : argumentList) {
                  parameterTypes[index] = parameter.getClass();
                  index++;
               }

               for (java.lang.reflect.Constructor<?> c : possibleConstructors) {
                  Class<?>[] argTypes = c.getParameterTypes();
                  boolean foundConstructor = true;

                  for (int i = 0; i < argTypes.length; i++) {
                     if (!this.wrapIfPrimitive(argTypes[i]).isAssignableFrom(parameterTypes[i])) {
                        foundConstructor = false;
                        break;
                     }
                  }

                  if (foundConstructor) {
                     try {
                        c.setAccessible(true);
                        return c.newInstance(argumentList.toArray());
                     } catch (Exception var13) {
                        throw new YAMLException(var13);
                     }
                  }
               }
            }

            throw new YAMLException("No suitable constructor with " + snode.getValue().size() + " arguments found for " + node.getType());
         }
      }

      private Class<? extends Object> wrapIfPrimitive(Class<?> clazz) {
         if (!clazz.isPrimitive()) {
            return (Class<? extends Object>)clazz;
         } else if (clazz == int.class) {
            return Integer.class;
         } else if (clazz == float.class) {
            return Float.class;
         } else if (clazz == double.class) {
            return Double.class;
         } else if (clazz == boolean.class) {
            return Boolean.class;
         } else if (clazz == long.class) {
            return Long.class;
         } else if (clazz == char.class) {
            return Character.class;
         } else if (clazz == short.class) {
            return Short.class;
         } else if (clazz == byte.class) {
            return Byte.class;
         } else {
            throw new YAMLException("Unexpected primitive " + clazz);
         }
      }

      @Override
      public void construct2ndStep(Node node, Object object) {
         SequenceNode snode = (SequenceNode)node;
         if (List.class.isAssignableFrom(node.getType())) {
            List<Object> list = (List<Object>)object;
            Constructor.this.constructSequenceStep2(snode, list);
         } else {
            if (!node.getType().isArray()) {
               throw new YAMLException("Immutable objects cannot be recursive.");
            }

            Constructor.this.constructArrayStep2(snode, object);
         }
      }
   }

   protected class ConstructYamlObject implements Construct {
      private Construct getConstructor(Node node) {
         Class<?> cl = Constructor.this.getClassForNode(node);
         node.setType((Class<? extends Object>)cl);
         return Constructor.this.yamlClassConstructors.get(node.getNodeId());
      }

      @Override
      public Object construct(Node node) {
         try {
            return this.getConstructor(node).construct(node);
         } catch (ConstructorException var3) {
            throw var3;
         } catch (Exception var4) {
            throw new ConstructorException(
               null, null, "Can't construct a java object for " + node.getTag() + "; exception=" + var4.getMessage(), node.getStartMark(), var4
            );
         }
      }

      @Override
      public void construct2ndStep(Node node, Object object) {
         try {
            this.getConstructor(node).construct2ndStep(node, object);
         } catch (Exception var4) {
            throw new ConstructorException(
               null,
               null,
               "Can't construct a second step for a java object for " + node.getTag() + "; exception=" + var4.getMessage(),
               node.getStartMark(),
               var4
            );
         }
      }
   }
}
