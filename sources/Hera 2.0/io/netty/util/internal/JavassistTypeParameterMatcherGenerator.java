/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.lang.reflect.Method;
/*    */ import javassist.ClassClassPath;
/*    */ import javassist.ClassPath;
/*    */ import javassist.ClassPool;
/*    */ import javassist.CtClass;
/*    */ import javassist.NotFoundException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class JavassistTypeParameterMatcherGenerator
/*    */ {
/* 32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JavassistTypeParameterMatcherGenerator.class);
/*    */ 
/*    */   
/* 35 */   private static final ClassPool classPool = new ClassPool(true);
/*    */   
/*    */   static {
/* 38 */     classPool.appendClassPath((ClassPath)new ClassClassPath(NoOpTypeParameterMatcher.class));
/*    */   }
/*    */   
/*    */   public static void appendClassPath(ClassPath classpath) {
/* 42 */     classPool.appendClassPath(classpath);
/*    */   }
/*    */   
/*    */   public static void appendClassPath(String pathname) throws NotFoundException {
/* 46 */     classPool.appendClassPath(pathname);
/*    */   }
/*    */   
/*    */   public static TypeParameterMatcher generate(Class<?> type) {
/* 50 */     ClassLoader classLoader = PlatformDependent.getContextClassLoader();
/* 51 */     if (classLoader == null) {
/* 52 */       classLoader = PlatformDependent.getSystemClassLoader();
/*    */     }
/* 54 */     return generate(type, classLoader);
/*    */   }
/*    */   
/*    */   public static TypeParameterMatcher generate(Class<?> type, ClassLoader classLoader) {
/* 58 */     String typeName = typeName(type);
/* 59 */     String className = "io.netty.util.internal.__matchers__." + typeName + "Matcher";
/*    */     
/*    */     try {
/* 62 */       return (TypeParameterMatcher)Class.forName(className, true, classLoader).newInstance();
/* 63 */     } catch (Exception e) {
/*    */ 
/*    */ 
/*    */       
/* 67 */       CtClass c = classPool.getAndRename(NoOpTypeParameterMatcher.class.getName(), className);
/* 68 */       c.setModifiers(c.getModifiers() | 0x10);
/* 69 */       c.getDeclaredMethod("match").setBody("{ return $1 instanceof " + typeName + "; }");
/* 70 */       byte[] byteCode = c.toBytecode();
/* 71 */       c.detach();
/* 72 */       Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
/*    */       
/* 74 */       method.setAccessible(true);
/*    */       
/* 76 */       Class<?> generated = (Class)method.invoke(classLoader, new Object[] { className, byteCode, Integer.valueOf(0), Integer.valueOf(byteCode.length) });
/* 77 */       if (type != Object.class) {
/* 78 */         logger.debug("Generated: {}", generated.getName());
/*    */       }
/*    */ 
/*    */       
/* 82 */       return (TypeParameterMatcher)generated.newInstance();
/* 83 */     } catch (RuntimeException e) {
/* 84 */       throw e;
/* 85 */     } catch (Exception e) {
/* 86 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String typeName(Class<?> type) {
/* 91 */     if (type.isArray()) {
/* 92 */       return typeName(type.getComponentType()) + "[]";
/*    */     }
/*    */     
/* 95 */     return type.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\JavassistTypeParameterMatcherGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */