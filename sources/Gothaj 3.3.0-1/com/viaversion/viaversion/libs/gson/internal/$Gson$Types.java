package com.viaversion.viaversion.libs.gson.internal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;

public final class $Gson$Types {
   static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

   private $Gson$Types() {
      throw new UnsupportedOperationException();
   }

   public static ParameterizedType newParameterizedTypeWithOwner(Type ownerType, Type rawType, Type... typeArguments) {
      return new $Gson$Types.ParameterizedTypeImpl(ownerType, rawType, typeArguments);
   }

   public static GenericArrayType arrayOf(Type componentType) {
      return new $Gson$Types.GenericArrayTypeImpl(componentType);
   }

   public static WildcardType subtypeOf(Type bound) {
      Type[] upperBounds;
      if (bound instanceof WildcardType) {
         upperBounds = ((WildcardType)bound).getUpperBounds();
      } else {
         upperBounds = new Type[]{bound};
      }

      return new $Gson$Types.WildcardTypeImpl(upperBounds, EMPTY_TYPE_ARRAY);
   }

   public static WildcardType supertypeOf(Type bound) {
      Type[] lowerBounds;
      if (bound instanceof WildcardType) {
         lowerBounds = ((WildcardType)bound).getLowerBounds();
      } else {
         lowerBounds = new Type[]{bound};
      }

      return new $Gson$Types.WildcardTypeImpl(new Type[]{Object.class}, lowerBounds);
   }

   public static Type canonicalize(Type type) {
      if (type instanceof Class) {
         Class<?> c = (Class<?>)type;
         return (Type)(c.isArray() ? new $Gson$Types.GenericArrayTypeImpl(canonicalize(c.getComponentType())) : c);
      } else if (type instanceof ParameterizedType) {
         ParameterizedType p = (ParameterizedType)type;
         return new $Gson$Types.ParameterizedTypeImpl(p.getOwnerType(), p.getRawType(), p.getActualTypeArguments());
      } else if (type instanceof GenericArrayType) {
         GenericArrayType g = (GenericArrayType)type;
         return new $Gson$Types.GenericArrayTypeImpl(g.getGenericComponentType());
      } else if (type instanceof WildcardType) {
         WildcardType w = (WildcardType)type;
         return new $Gson$Types.WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
      } else {
         return type;
      }
   }

   public static Class<?> getRawType(Type type) {
      if (type instanceof Class) {
         return (Class<?>)type;
      } else if (type instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)type;
         Type rawType = parameterizedType.getRawType();
         $Gson$Preconditions.checkArgument(rawType instanceof Class);
         return (Class<?>)rawType;
      } else if (type instanceof GenericArrayType) {
         Type componentType = ((GenericArrayType)type).getGenericComponentType();
         return Array.newInstance(getRawType(componentType), 0).getClass();
      } else if (type instanceof TypeVariable) {
         return Object.class;
      } else if (type instanceof WildcardType) {
         Type[] bounds = ((WildcardType)type).getUpperBounds();

         assert bounds.length == 1;

         return getRawType(bounds[0]);
      } else {
         String className = type == null ? "null" : type.getClass().getName();
         throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
      }
   }

   private static boolean equal(Object a, Object b) {
      return Objects.equals(a, b);
   }

   public static boolean equals(Type a, Type b) {
      if (a == b) {
         return true;
      } else if (a instanceof Class) {
         return a.equals(b);
      } else if (a instanceof ParameterizedType) {
         if (!(b instanceof ParameterizedType)) {
            return false;
         } else {
            ParameterizedType pa = (ParameterizedType)a;
            ParameterizedType pb = (ParameterizedType)b;
            return equal(pa.getOwnerType(), pb.getOwnerType())
               && pa.getRawType().equals(pb.getRawType())
               && Arrays.equals((Object[])pa.getActualTypeArguments(), (Object[])pb.getActualTypeArguments());
         }
      } else if (a instanceof GenericArrayType) {
         if (!(b instanceof GenericArrayType)) {
            return false;
         } else {
            GenericArrayType ga = (GenericArrayType)a;
            GenericArrayType gb = (GenericArrayType)b;
            return equals(ga.getGenericComponentType(), gb.getGenericComponentType());
         }
      } else if (a instanceof WildcardType) {
         if (!(b instanceof WildcardType)) {
            return false;
         } else {
            WildcardType wa = (WildcardType)a;
            WildcardType wb = (WildcardType)b;
            return Arrays.equals((Object[])wa.getUpperBounds(), (Object[])wb.getUpperBounds())
               && Arrays.equals((Object[])wa.getLowerBounds(), (Object[])wb.getLowerBounds());
         }
      } else if (a instanceof TypeVariable) {
         if (!(b instanceof TypeVariable)) {
            return false;
         } else {
            TypeVariable<?> va = (TypeVariable<?>)a;
            TypeVariable<?> vb = (TypeVariable<?>)b;
            return va.getGenericDeclaration() == vb.getGenericDeclaration() && va.getName().equals(vb.getName());
         }
      } else {
         return false;
      }
   }

   public static String typeToString(Type type) {
      return type instanceof Class ? ((Class)type).getName() : type.toString();
   }

   private static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> supertype) {
      if (supertype == rawType) {
         return context;
      } else {
         if (supertype.isInterface()) {
            Class<?>[] interfaces = rawType.getInterfaces();
            int i = 0;

            for (int length = interfaces.length; i < length; i++) {
               if (interfaces[i] == supertype) {
                  return rawType.getGenericInterfaces()[i];
               }

               if (supertype.isAssignableFrom(interfaces[i])) {
                  return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], supertype);
               }
            }
         }

         if (!rawType.isInterface()) {
            while (rawType != Object.class) {
               Class<?> rawSupertype = rawType.getSuperclass();
               if (rawSupertype == supertype) {
                  return rawType.getGenericSuperclass();
               }

               if (supertype.isAssignableFrom(rawSupertype)) {
                  return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, supertype);
               }

               rawType = rawSupertype;
            }
         }

         return supertype;
      }
   }

   private static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
      if (context instanceof WildcardType) {
         Type[] bounds = ((WildcardType)context).getUpperBounds();

         assert bounds.length == 1;

         context = bounds[0];
      }

      $Gson$Preconditions.checkArgument(supertype.isAssignableFrom(contextRawType));
      return resolve(context, contextRawType, getGenericSupertype(context, contextRawType, supertype));
   }

   public static Type getArrayComponentType(Type array) {
      return (Type)(array instanceof GenericArrayType ? ((GenericArrayType)array).getGenericComponentType() : ((Class)array).getComponentType());
   }

   public static Type getCollectionElementType(Type context, Class<?> contextRawType) {
      Type collectionType = getSupertype(context, contextRawType, Collection.class);
      return (Type)(collectionType instanceof ParameterizedType ? ((ParameterizedType)collectionType).getActualTypeArguments()[0] : Object.class);
   }

   public static Type[] getMapKeyAndValueTypes(Type context, Class<?> contextRawType) {
      if (context == Properties.class) {
         return new Type[]{String.class, String.class};
      } else {
         Type mapType = getSupertype(context, contextRawType, Map.class);
         if (mapType instanceof ParameterizedType) {
            ParameterizedType mapParameterizedType = (ParameterizedType)mapType;
            return mapParameterizedType.getActualTypeArguments();
         } else {
            return new Type[]{Object.class, Object.class};
         }
      }
   }

   public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
      return resolve(context, contextRawType, toResolve, new HashMap<>());
   }

   private static Type resolve(Type context, Class<?> contextRawType, Type toResolve, Map<TypeVariable<?>, Type> visitedTypeVariables) {
      TypeVariable<?> resolving = null;

      while (true) {
         if (toResolve instanceof TypeVariable) {
            TypeVariable<?> typeVariable = (TypeVariable<?>)toResolve;
            Type previouslyResolved = visitedTypeVariables.get(typeVariable);
            if (previouslyResolved != null) {
               return previouslyResolved == void.class ? toResolve : previouslyResolved;
            }

            visitedTypeVariables.put(typeVariable, void.class);
            if (resolving == null) {
               resolving = typeVariable;
            }

            toResolve = resolveTypeVariable(context, contextRawType, typeVariable);
            if (toResolve != typeVariable) {
               continue;
            }
         } else if (toResolve instanceof Class && ((Class)toResolve).isArray()) {
            Class<?> original = (Class<?>)toResolve;
            Type componentType = original.getComponentType();
            Type newComponentType = resolve(context, contextRawType, componentType, visitedTypeVariables);
            toResolve = (Type)(equal(componentType, newComponentType) ? original : arrayOf(newComponentType));
         } else if (toResolve instanceof GenericArrayType) {
            GenericArrayType original = (GenericArrayType)toResolve;
            Type componentType = original.getGenericComponentType();
            Type newComponentType = resolve(context, contextRawType, componentType, visitedTypeVariables);
            toResolve = equal(componentType, newComponentType) ? original : arrayOf(newComponentType);
         } else if (toResolve instanceof ParameterizedType) {
            ParameterizedType original = (ParameterizedType)toResolve;
            Type ownerType = original.getOwnerType();
            Type newOwnerType = resolve(context, contextRawType, ownerType, visitedTypeVariables);
            boolean changed = !equal(newOwnerType, ownerType);
            Type[] args = original.getActualTypeArguments();
            int t = 0;

            for (int length = args.length; t < length; t++) {
               Type resolvedTypeArgument = resolve(context, contextRawType, args[t], visitedTypeVariables);
               if (!equal(resolvedTypeArgument, args[t])) {
                  if (!changed) {
                     args = (Type[])args.clone();
                     changed = true;
                  }

                  args[t] = resolvedTypeArgument;
               }
            }

            toResolve = changed ? newParameterizedTypeWithOwner(newOwnerType, original.getRawType(), args) : original;
         } else {
            label83:
            if (toResolve instanceof WildcardType) {
               WildcardType original = (WildcardType)toResolve;
               Type[] originalLowerBound = original.getLowerBounds();
               Type[] originalUpperBound = original.getUpperBounds();
               if (originalLowerBound.length == 1) {
                  Type lowerBound = resolve(context, contextRawType, originalLowerBound[0], visitedTypeVariables);
                  if (lowerBound != originalLowerBound[0]) {
                     toResolve = supertypeOf(lowerBound);
                     break label83;
                  }
               } else if (originalUpperBound.length == 1) {
                  Type upperBound = resolve(context, contextRawType, originalUpperBound[0], visitedTypeVariables);
                  if (upperBound != originalUpperBound[0]) {
                     toResolve = subtypeOf(upperBound);
                     break label83;
                  }
               }

               toResolve = original;
            }
         }

         if (resolving != null) {
            visitedTypeVariables.put(resolving, toResolve);
         }

         return toResolve;
      }
   }

   private static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
      Class<?> declaredByRaw = declaringClassOf(unknown);
      if (declaredByRaw == null) {
         return unknown;
      } else {
         Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
         if (declaredBy instanceof ParameterizedType) {
            int index = indexOf(declaredByRaw.getTypeParameters(), unknown);
            return ((ParameterizedType)declaredBy).getActualTypeArguments()[index];
         } else {
            return unknown;
         }
      }
   }

   private static int indexOf(Object[] array, Object toFind) {
      int i = 0;

      for (int length = array.length; i < length; i++) {
         if (toFind.equals(array[i])) {
            return i;
         }
      }

      throw new NoSuchElementException();
   }

   private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
      GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
      return genericDeclaration instanceof Class ? (Class)genericDeclaration : null;
   }

   static void checkNotPrimitive(Type type) {
      $Gson$Preconditions.checkArgument(!(type instanceof Class) || !((Class)type).isPrimitive());
   }

   private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
      private final Type componentType;
      private static final long serialVersionUID = 0L;

      public GenericArrayTypeImpl(Type componentType) {
         Objects.requireNonNull(componentType);
         this.componentType = $Gson$Types.canonicalize(componentType);
      }

      @Override
      public Type getGenericComponentType() {
         return this.componentType;
      }

      @Override
      public boolean equals(Object o) {
         return o instanceof GenericArrayType && $Gson$Types.equals(this, (GenericArrayType)o);
      }

      @Override
      public int hashCode() {
         return this.componentType.hashCode();
      }

      @Override
      public String toString() {
         return $Gson$Types.typeToString(this.componentType) + "[]";
      }
   }

   private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
      private final Type ownerType;
      private final Type rawType;
      private final Type[] typeArguments;
      private static final long serialVersionUID = 0L;

      public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
         Objects.requireNonNull(rawType);
         if (rawType instanceof Class) {
            Class<?> rawTypeAsClass = (Class<?>)rawType;
            boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers()) || rawTypeAsClass.getEnclosingClass() == null;
            $Gson$Preconditions.checkArgument(ownerType != null || isStaticOrTopLevelClass);
         }

         this.ownerType = ownerType == null ? null : $Gson$Types.canonicalize(ownerType);
         this.rawType = $Gson$Types.canonicalize(rawType);
         this.typeArguments = (Type[])typeArguments.clone();
         int t = 0;

         for (int length = this.typeArguments.length; t < length; t++) {
            Objects.requireNonNull(this.typeArguments[t]);
            $Gson$Types.checkNotPrimitive(this.typeArguments[t]);
            this.typeArguments[t] = $Gson$Types.canonicalize(this.typeArguments[t]);
         }
      }

      @Override
      public Type[] getActualTypeArguments() {
         return (Type[])this.typeArguments.clone();
      }

      @Override
      public Type getRawType() {
         return this.rawType;
      }

      @Override
      public Type getOwnerType() {
         return this.ownerType;
      }

      @Override
      public boolean equals(Object other) {
         return other instanceof ParameterizedType && $Gson$Types.equals(this, (ParameterizedType)other);
      }

      private static int hashCodeOrZero(Object o) {
         return o != null ? o.hashCode() : 0;
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode((Object[])this.typeArguments) ^ this.rawType.hashCode() ^ hashCodeOrZero(this.ownerType);
      }

      @Override
      public String toString() {
         int length = this.typeArguments.length;
         if (length == 0) {
            return $Gson$Types.typeToString(this.rawType);
         } else {
            StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
            stringBuilder.append($Gson$Types.typeToString(this.rawType)).append("<").append($Gson$Types.typeToString(this.typeArguments[0]));

            for (int i = 1; i < length; i++) {
               stringBuilder.append(", ").append($Gson$Types.typeToString(this.typeArguments[i]));
            }

            return stringBuilder.append(">").toString();
         }
      }
   }

   private static final class WildcardTypeImpl implements WildcardType, Serializable {
      private final Type upperBound;
      private final Type lowerBound;
      private static final long serialVersionUID = 0L;

      public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
         $Gson$Preconditions.checkArgument(lowerBounds.length <= 1);
         $Gson$Preconditions.checkArgument(upperBounds.length == 1);
         if (lowerBounds.length == 1) {
            Objects.requireNonNull(lowerBounds[0]);
            $Gson$Types.checkNotPrimitive(lowerBounds[0]);
            $Gson$Preconditions.checkArgument(upperBounds[0] == Object.class);
            this.lowerBound = $Gson$Types.canonicalize(lowerBounds[0]);
            this.upperBound = Object.class;
         } else {
            Objects.requireNonNull(upperBounds[0]);
            $Gson$Types.checkNotPrimitive(upperBounds[0]);
            this.lowerBound = null;
            this.upperBound = $Gson$Types.canonicalize(upperBounds[0]);
         }
      }

      @Override
      public Type[] getUpperBounds() {
         return new Type[]{this.upperBound};
      }

      @Override
      public Type[] getLowerBounds() {
         return this.lowerBound != null ? new Type[]{this.lowerBound} : $Gson$Types.EMPTY_TYPE_ARRAY;
      }

      @Override
      public boolean equals(Object other) {
         return other instanceof WildcardType && $Gson$Types.equals(this, (WildcardType)other);
      }

      @Override
      public int hashCode() {
         return (this.lowerBound != null ? 31 + this.lowerBound.hashCode() : 1) ^ 31 + this.upperBound.hashCode();
      }

      @Override
      public String toString() {
         if (this.lowerBound != null) {
            return "? super " + $Gson$Types.typeToString(this.lowerBound);
         } else {
            return this.upperBound == Object.class ? "?" : "? extends " + $Gson$Types.typeToString(this.upperBound);
         }
      }
   }
}
