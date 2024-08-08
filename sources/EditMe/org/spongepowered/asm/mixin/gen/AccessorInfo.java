package org.spongepowered.asm.mixin.gen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public class AccessorInfo extends SpecialMethodInfo {
   protected static final Pattern PATTERN_ACCESSOR = Pattern.compile("^(get|set|is|invoke|call)(([A-Z])(.*?))(_\\$md.*)?$");
   protected final Type[] argTypes;
   protected final Type returnType;
   protected final AccessorInfo.AccessorType type;
   private final Type targetFieldType;
   protected final MemberInfo target;
   protected FieldNode targetField;
   protected MethodNode targetMethod;

   public AccessorInfo(MixinTargetContext var1, MethodNode var2) {
      this(var1, var2, Accessor.class);
   }

   protected AccessorInfo(MixinTargetContext var1, MethodNode var2, Class var3) {
      super(var1, var2, Annotations.getVisible(var2, var3));
      this.argTypes = Type.getArgumentTypes(var2.desc);
      this.returnType = Type.getReturnType(var2.desc);
      this.type = this.initType();
      this.targetFieldType = this.initTargetFieldType();
      this.target = this.initTarget();
   }

   protected AccessorInfo.AccessorType initType() {
      return this.returnType.equals(Type.VOID_TYPE) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
   }

   protected Type initTargetFieldType() {
      switch(this.type) {
      case FIELD_GETTER:
         if (this.argTypes.length > 0) {
            throw new InvalidAccessorException(this.mixin, this + " must take exactly 0 arguments, found " + this.argTypes.length);
         }

         return this.returnType;
      case FIELD_SETTER:
         if (this.argTypes.length != 1) {
            throw new InvalidAccessorException(this.mixin, this + " must take exactly 1 argument, found " + this.argTypes.length);
         }

         return this.argTypes[0];
      default:
         throw new InvalidAccessorException(this.mixin, "Computed unsupported accessor type " + this.type + " for " + this);
      }
   }

   protected MemberInfo initTarget() {
      MemberInfo var1 = new MemberInfo(this.getTargetName(), (String)null, this.targetFieldType.getDescriptor());
      this.annotation.visit("target", var1.toString());
      return var1;
   }

   protected String getTargetName() {
      String var1 = (String)Annotations.getValue(this.annotation);
      if (Strings.isNullOrEmpty(var1)) {
         String var2 = this.inflectTarget();
         if (var2 == null) {
            throw new InvalidAccessorException(this.mixin, "Failed to inflect target name for " + this + ", supported prefixes: [get, set, is]");
         } else {
            return var2;
         }
      } else {
         return MemberInfo.parse(var1, this.mixin).name;
      }
   }

   protected String inflectTarget() {
      return inflectTarget(this.method.name, this.type, this.toString(), this.mixin, this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
   }

   public static String inflectTarget(String var0, AccessorInfo.AccessorType var1, String var2, IMixinContext var3, boolean var4) {
      Matcher var5 = PATTERN_ACCESSOR.matcher(var0);
      if (var5.matches()) {
         String var6 = var5.group(1);
         String var7 = var5.group(3);
         String var8 = var5.group(4);
         String var9 = String.format("%s%s", toLowerCase(var7, !isUpperCase(var8)), var8);
         if (!var1.isExpectedPrefix(var6) && var4) {
            LogManager.getLogger("mixin").warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[]{var2, var6, var1.getExpectedPrefixes()});
         }

         return MemberInfo.parse(var9, var3).name;
      } else {
         return null;
      }
   }

   public final MemberInfo getTarget() {
      return this.target;
   }

   public final Type getTargetFieldType() {
      return this.targetFieldType;
   }

   public final FieldNode getTargetField() {
      return this.targetField;
   }

   public final MethodNode getTargetMethod() {
      return this.targetMethod;
   }

   public final Type getReturnType() {
      return this.returnType;
   }

   public final Type[] getArgTypes() {
      return this.argTypes;
   }

   public String toString() {
      return String.format("%s->@%s[%s]::%s%s", this.mixin.toString(), Bytecode.getSimpleName(this.annotation), this.type.toString(), this.method.name, this.method.desc);
   }

   public void locate() {
      this.targetField = this.findTargetField();
   }

   public MethodNode generate() {
      MethodNode var1 = this.type.getGenerator(this).generate();
      Bytecode.mergeAnnotations(this.method, var1);
      return var1;
   }

   private FieldNode findTargetField() {
      return (FieldNode)this.findTarget(this.classNode.fields);
   }

   protected Object findTarget(List var1) {
      Object var2 = null;
      ArrayList var3 = new ArrayList();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         String var6 = getNodeDesc(var5);
         if (var6 != null && var6.equals(this.target.desc)) {
            String var7 = getNodeName(var5);
            if (var7 != null) {
               if (var7.equals(this.target.name)) {
                  var2 = var5;
               }

               if (var7.equalsIgnoreCase(this.target.name)) {
                  var3.add(var5);
               }
            }
         }
      }

      if (var2 != null) {
         if (var3.size() > 1) {
            LogManager.getLogger("mixin").debug("{} found an exact match for {} but other candidates were found!", new Object[]{this, this.target});
         }

         return var2;
      } else if (var3.size() == 1) {
         return var3.get(0);
      } else {
         String var8 = var3.size() == 0 ? "No" : "Multiple";
         throw new InvalidAccessorException(this, var8 + " candidates were found matching " + this.target + " in " + this.classNode.name + " for " + this);
      }
   }

   private static String getNodeDesc(Object var0) {
      return var0 instanceof MethodNode ? ((MethodNode)var0).desc : (var0 instanceof FieldNode ? ((FieldNode)var0).desc : null);
   }

   private static String getNodeName(Object var0) {
      return var0 instanceof MethodNode ? ((MethodNode)var0).name : (var0 instanceof FieldNode ? ((FieldNode)var0).name : null);
   }

   public static AccessorInfo of(MixinTargetContext var0, MethodNode var1, Class var2) {
      if (var2 == Accessor.class) {
         return new AccessorInfo(var0, var1);
      } else if (var2 == Invoker.class) {
         return new InvokerInfo(var0, var1);
      } else {
         throw new InvalidAccessorException(var0, "Could not parse accessor for unknown type " + var2.getName());
      }
   }

   private static String toLowerCase(String var0, boolean var1) {
      return var1 ? var0.toLowerCase() : var0;
   }

   private static boolean isUpperCase(String var0) {
      return var0.toUpperCase().equals(var0);
   }

   public static enum AccessorType {
      FIELD_GETTER(ImmutableSet.of("get", "is")) {
         AccessorGenerator getGenerator(AccessorInfo var1) {
            return new AccessorGeneratorFieldGetter(var1);
         }
      },
      FIELD_SETTER(ImmutableSet.of("set")) {
         AccessorGenerator getGenerator(AccessorInfo var1) {
            return new AccessorGeneratorFieldSetter(var1);
         }
      },
      METHOD_PROXY(ImmutableSet.of("call", "invoke")) {
         AccessorGenerator getGenerator(AccessorInfo var1) {
            return new AccessorGeneratorMethodProxy(var1);
         }
      };

      private final Set expectedPrefixes;
      private static final AccessorInfo.AccessorType[] $VALUES = new AccessorInfo.AccessorType[]{FIELD_GETTER, FIELD_SETTER, METHOD_PROXY};

      private AccessorType(Set var3) {
         this.expectedPrefixes = var3;
      }

      public boolean isExpectedPrefix(String var1) {
         return this.expectedPrefixes.contains(var1);
      }

      public String getExpectedPrefixes() {
         return this.expectedPrefixes.toString();
      }

      abstract AccessorGenerator getGenerator(AccessorInfo var1);

      AccessorType(Set var3, Object var4) {
         this(var3);
      }
   }
}
