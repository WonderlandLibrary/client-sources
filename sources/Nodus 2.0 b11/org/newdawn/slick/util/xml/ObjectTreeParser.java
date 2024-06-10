/*   1:    */ package org.newdawn.slick.util.xml;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.Field;
/*   6:    */ import java.lang.reflect.InvocationTargetException;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import org.newdawn.slick.util.Log;
/*  11:    */ import org.newdawn.slick.util.ResourceLoader;
/*  12:    */ 
/*  13:    */ public class ObjectTreeParser
/*  14:    */ {
/*  15: 50 */   private HashMap nameToClass = new HashMap();
/*  16:    */   private String defaultPackage;
/*  17: 54 */   private ArrayList ignored = new ArrayList();
/*  18: 56 */   private String addMethod = "add";
/*  19:    */   
/*  20:    */   public ObjectTreeParser() {}
/*  21:    */   
/*  22:    */   public ObjectTreeParser(String defaultPackage)
/*  23:    */   {
/*  24: 71 */     this.defaultPackage = defaultPackage;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addElementMapping(String elementName, Class elementClass)
/*  28:    */   {
/*  29: 81 */     this.nameToClass.put(elementName, elementClass);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void addIgnoredElement(String elementName)
/*  33:    */   {
/*  34: 90 */     this.ignored.add(elementName);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setAddMethodName(String methodName)
/*  38:    */   {
/*  39:101 */     this.addMethod = methodName;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setDefaultPackage(String defaultPackage)
/*  43:    */   {
/*  44:111 */     this.defaultPackage = defaultPackage;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object parse(String ref)
/*  48:    */     throws SlickXMLException
/*  49:    */   {
/*  50:124 */     return parse(ref, ResourceLoader.getResourceAsStream(ref));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object parse(String name, InputStream in)
/*  54:    */     throws SlickXMLException
/*  55:    */   {
/*  56:137 */     XMLParser parser = new XMLParser();
/*  57:138 */     XMLElement root = parser.parse(name, in);
/*  58:    */     
/*  59:140 */     return traverse(root);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object parseOnto(String ref, Object target)
/*  63:    */     throws SlickXMLException
/*  64:    */   {
/*  65:154 */     return parseOnto(ref, ResourceLoader.getResourceAsStream(ref), target);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object parseOnto(String name, InputStream in, Object target)
/*  69:    */     throws SlickXMLException
/*  70:    */   {
/*  71:168 */     XMLParser parser = new XMLParser();
/*  72:169 */     XMLElement root = parser.parse(name, in);
/*  73:    */     
/*  74:171 */     return traverse(root, target);
/*  75:    */   }
/*  76:    */   
/*  77:    */   private Class getClassForElementName(String name)
/*  78:    */   {
/*  79:182 */     Class clazz = (Class)this.nameToClass.get(name);
/*  80:183 */     if (clazz != null) {
/*  81:184 */       return clazz;
/*  82:    */     }
/*  83:187 */     if (this.defaultPackage != null) {
/*  84:    */       try
/*  85:    */       {
/*  86:189 */         return Class.forName(this.defaultPackage + "." + name);
/*  87:    */       }
/*  88:    */       catch (ClassNotFoundException localClassNotFoundException) {}
/*  89:    */     }
/*  90:195 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   private Object traverse(XMLElement current)
/*  94:    */     throws SlickXMLException
/*  95:    */   {
/*  96:207 */     return traverse(current, null);
/*  97:    */   }
/*  98:    */   
/*  99:    */   private Object traverse(XMLElement current, Object instance)
/* 100:    */     throws SlickXMLException
/* 101:    */   {
/* 102:220 */     String name = current.getName();
/* 103:221 */     if (this.ignored.contains(name)) {
/* 104:222 */       return null;
/* 105:    */     }
/* 106:    */     Class clazz;
/* 107:    */     Class clazz;
/* 108:227 */     if (instance == null) {
/* 109:228 */       clazz = getClassForElementName(name);
/* 110:    */     } else {
/* 111:230 */       clazz = instance.getClass();
/* 112:    */     }
/* 113:233 */     if (clazz == null) {
/* 114:234 */       throw new SlickXMLException("Unable to map element " + name + " to a class, define the mapping");
/* 115:    */     }
/* 116:    */     try
/* 117:    */     {
/* 118:238 */       if (instance == null)
/* 119:    */       {
/* 120:239 */         instance = clazz.newInstance();
/* 121:    */         
/* 122:241 */         Method elementNameMethod = getMethod(clazz, "setXMLElementName", new Class[] { String.class });
/* 123:242 */         if (elementNameMethod != null) {
/* 124:243 */           invoke(elementNameMethod, instance, new Object[] { name });
/* 125:    */         }
/* 126:245 */         Method contentMethod = getMethod(clazz, "setXMLElementContent", new Class[] { String.class });
/* 127:246 */         if (contentMethod != null) {
/* 128:247 */           invoke(contentMethod, instance, new Object[] { current.getContent() });
/* 129:    */         }
/* 130:    */       }
/* 131:251 */       String[] attrs = current.getAttributeNames();
/* 132:252 */       for (int i = 0; i < attrs.length; i++)
/* 133:    */       {
/* 134:253 */         String methodName = "set" + attrs[i];
/* 135:    */         
/* 136:255 */         Method method = findMethod(clazz, methodName);
/* 137:257 */         if (method == null)
/* 138:    */         {
/* 139:258 */           Field field = findField(clazz, attrs[i]);
/* 140:259 */           if (field != null)
/* 141:    */           {
/* 142:260 */             String value = current.getAttribute(attrs[i]);
/* 143:261 */             Object typedValue = typeValue(value, field.getType());
/* 144:262 */             setField(field, instance, typedValue);
/* 145:    */           }
/* 146:    */           else
/* 147:    */           {
/* 148:264 */             Log.info("Unable to find property on: " + clazz + " for attribute: " + attrs[i]);
/* 149:    */           }
/* 150:    */         }
/* 151:    */         else
/* 152:    */         {
/* 153:267 */           String value = current.getAttribute(attrs[i]);
/* 154:268 */           Object typedValue = typeValue(value, method.getParameterTypes()[0]);
/* 155:269 */           invoke(method, instance, new Object[] { typedValue });
/* 156:    */         }
/* 157:    */       }
/* 158:273 */       XMLElementList children = current.getChildren();
/* 159:274 */       for (int i = 0; i < children.size(); i++)
/* 160:    */       {
/* 161:275 */         XMLElement element = children.get(i);
/* 162:    */         
/* 163:277 */         Object child = traverse(element);
/* 164:278 */         if (child != null)
/* 165:    */         {
/* 166:279 */           String methodName = this.addMethod;
/* 167:    */           
/* 168:281 */           Method method = findMethod(clazz, methodName, child.getClass());
/* 169:282 */           if (method == null) {
/* 170:283 */             Log.info("Unable to find method to add: " + child + " to " + clazz);
/* 171:    */           } else {
/* 172:285 */             invoke(method, instance, new Object[] { child });
/* 173:    */           }
/* 174:    */         }
/* 175:    */       }
/* 176:290 */       return instance;
/* 177:    */     }
/* 178:    */     catch (InstantiationException e)
/* 179:    */     {
/* 180:292 */       throw new SlickXMLException("Unable to instance " + clazz + " for element " + name + ", no zero parameter constructor?", e);
/* 181:    */     }
/* 182:    */     catch (IllegalAccessException e)
/* 183:    */     {
/* 184:294 */       throw new SlickXMLException("Unable to instance " + clazz + " for element " + name + ", no zero parameter constructor?", e);
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   private Object typeValue(String value, Class clazz)
/* 189:    */     throws SlickXMLException
/* 190:    */   {
/* 191:307 */     if (clazz == String.class) {
/* 192:308 */       return value;
/* 193:    */     }
/* 194:    */     try
/* 195:    */     {
/* 196:312 */       clazz = mapPrimitive(clazz);
/* 197:313 */       return clazz.getConstructor(new Class[] { String.class }).newInstance(new Object[] { value });
/* 198:    */     }
/* 199:    */     catch (Exception e)
/* 200:    */     {
/* 201:315 */       throw new SlickXMLException("Failed to convert: " + value + " to the expected primitive type: " + clazz, e);
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   private Class mapPrimitive(Class clazz)
/* 206:    */   {
/* 207:326 */     if (clazz == Integer.TYPE) {
/* 208:327 */       return Integer.class;
/* 209:    */     }
/* 210:329 */     if (clazz == Double.TYPE) {
/* 211:330 */       return Double.class;
/* 212:    */     }
/* 213:332 */     if (clazz == Float.TYPE) {
/* 214:333 */       return Float.class;
/* 215:    */     }
/* 216:335 */     if (clazz == Boolean.TYPE) {
/* 217:336 */       return Boolean.class;
/* 218:    */     }
/* 219:338 */     if (clazz == Long.TYPE) {
/* 220:339 */       return Long.class;
/* 221:    */     }
/* 222:342 */     throw new RuntimeException("Unsupported primitive: " + clazz);
/* 223:    */   }
/* 224:    */   
/* 225:    */   private Field findField(Class clazz, String name)
/* 226:    */   {
/* 227:355 */     Field[] fields = clazz.getDeclaredFields();
/* 228:356 */     for (int i = 0; i < fields.length; i++) {
/* 229:357 */       if (fields[i].getName().equalsIgnoreCase(name))
/* 230:    */       {
/* 231:358 */         if (fields[i].getType().isPrimitive()) {
/* 232:359 */           return fields[i];
/* 233:    */         }
/* 234:361 */         if (fields[i].getType() == String.class) {
/* 235:362 */           return fields[i];
/* 236:    */         }
/* 237:    */       }
/* 238:    */     }
/* 239:367 */     return null;
/* 240:    */   }
/* 241:    */   
/* 242:    */   private Method findMethod(Class clazz, String name)
/* 243:    */   {
/* 244:380 */     Method[] methods = clazz.getDeclaredMethods();
/* 245:381 */     for (int i = 0; i < methods.length; i++) {
/* 246:382 */       if (methods[i].getName().equalsIgnoreCase(name))
/* 247:    */       {
/* 248:383 */         Method method = methods[i];
/* 249:384 */         Class[] params = method.getParameterTypes();
/* 250:386 */         if (params.length == 1) {
/* 251:387 */           return method;
/* 252:    */         }
/* 253:    */       }
/* 254:    */     }
/* 255:392 */     return null;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private Method findMethod(Class clazz, String name, Class parameter)
/* 259:    */   {
/* 260:404 */     Method[] methods = clazz.getDeclaredMethods();
/* 261:405 */     for (int i = 0; i < methods.length; i++) {
/* 262:406 */       if (methods[i].getName().equalsIgnoreCase(name))
/* 263:    */       {
/* 264:407 */         Method method = methods[i];
/* 265:408 */         Class[] params = method.getParameterTypes();
/* 266:410 */         if ((params.length == 1) && 
/* 267:411 */           (method.getParameterTypes()[0].isAssignableFrom(parameter))) {
/* 268:412 */           return method;
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:418 */     return null;
/* 273:    */   }
/* 274:    */   
/* 275:    */   private void setField(Field field, Object instance, Object value)
/* 276:    */     throws SlickXMLException
/* 277:    */   {
/* 278:    */     try
/* 279:    */     {
/* 280:431 */       field.setAccessible(true);
/* 281:432 */       field.set(instance, value);
/* 282:    */     }
/* 283:    */     catch (IllegalArgumentException e)
/* 284:    */     {
/* 285:434 */       throw new SlickXMLException("Failed to set: " + field + " for an XML attribute, is it valid?", e);
/* 286:    */     }
/* 287:    */     catch (IllegalAccessException e)
/* 288:    */     {
/* 289:436 */       throw new SlickXMLException("Failed to set: " + field + " for an XML attribute, is it valid?", e);
/* 290:    */     }
/* 291:    */     finally
/* 292:    */     {
/* 293:438 */       field.setAccessible(false);
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   private void invoke(Method method, Object instance, Object[] params)
/* 298:    */     throws SlickXMLException
/* 299:    */   {
/* 300:    */     try
/* 301:    */     {
/* 302:452 */       method.setAccessible(true);
/* 303:453 */       method.invoke(instance, params);
/* 304:    */     }
/* 305:    */     catch (IllegalArgumentException e)
/* 306:    */     {
/* 307:455 */       throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e);
/* 308:    */     }
/* 309:    */     catch (IllegalAccessException e)
/* 310:    */     {
/* 311:457 */       throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e);
/* 312:    */     }
/* 313:    */     catch (InvocationTargetException e)
/* 314:    */     {
/* 315:459 */       throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e);
/* 316:    */     }
/* 317:    */     finally
/* 318:    */     {
/* 319:461 */       method.setAccessible(false);
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   private Method getMethod(Class clazz, String name, Class[] params)
/* 324:    */   {
/* 325:    */     try
/* 326:    */     {
/* 327:476 */       return clazz.getMethod(name, params);
/* 328:    */     }
/* 329:    */     catch (SecurityException e)
/* 330:    */     {
/* 331:478 */       return null;
/* 332:    */     }
/* 333:    */     catch (NoSuchMethodException e) {}
/* 334:480 */     return null;
/* 335:    */   }
/* 336:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.xml.ObjectTreeParser
 * JD-Core Version:    0.7.0.1
 */