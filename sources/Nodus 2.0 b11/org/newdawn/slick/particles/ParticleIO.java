/*   1:    */ package org.newdawn.slick.particles;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.OutputStream;
/*   9:    */ import java.io.OutputStreamWriter;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import javax.xml.parsers.DocumentBuilder;
/*  12:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  13:    */ import javax.xml.transform.Result;
/*  14:    */ import javax.xml.transform.Transformer;
/*  15:    */ import javax.xml.transform.TransformerFactory;
/*  16:    */ import javax.xml.transform.dom.DOMSource;
/*  17:    */ import javax.xml.transform.stream.StreamResult;
/*  18:    */ import org.newdawn.slick.Color;
/*  19:    */ import org.newdawn.slick.geom.Vector2f;
/*  20:    */ import org.newdawn.slick.util.Log;
/*  21:    */ import org.newdawn.slick.util.ResourceLoader;
/*  22:    */ import org.w3c.dom.Document;
/*  23:    */ import org.w3c.dom.Element;
/*  24:    */ import org.w3c.dom.NodeList;
/*  25:    */ 
/*  26:    */ public class ParticleIO
/*  27:    */ {
/*  28:    */   public static ParticleSystem loadConfiguredSystem(String ref, Color mask)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 52 */     return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), 
/*  32: 53 */       null, null, mask);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static ParticleSystem loadConfiguredSystem(String ref)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 67 */     return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), 
/*  39: 68 */       null, null, null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static ParticleSystem loadConfiguredSystem(File ref)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45: 82 */     return loadConfiguredSystem(new FileInputStream(ref), null, null, null);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static ParticleSystem loadConfiguredSystem(InputStream ref, Color mask)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 97 */     return loadConfiguredSystem(ref, null, null, mask);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static ParticleSystem loadConfiguredSystem(InputStream ref)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:111 */     return loadConfiguredSystem(ref, null, null, null);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static ParticleSystem loadConfiguredSystem(String ref, ConfigurableEmitterFactory factory)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:128 */     return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), 
/*  64:129 */       factory, null, null);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static ParticleSystem loadConfiguredSystem(File ref, ConfigurableEmitterFactory factory)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:146 */     return loadConfiguredSystem(new FileInputStream(ref), factory, null, null);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static ParticleSystem loadConfiguredSystem(InputStream ref, ConfigurableEmitterFactory factory)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:163 */     return loadConfiguredSystem(ref, factory, null, null);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static ParticleSystem loadConfiguredSystem(InputStream ref, ConfigurableEmitterFactory factory, ParticleSystem system, Color mask)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82:182 */     if (factory == null) {
/*  83:183 */       factory = new ConfigurableEmitterFactory()
/*  84:    */       {
/*  85:    */         public ConfigurableEmitter createEmitter(String name)
/*  86:    */         {
/*  87:185 */           return new ConfigurableEmitter(name);
/*  88:    */         }
/*  89:    */       };
/*  90:    */     }
/*  91:    */     try
/*  92:    */     {
/*  93:190 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance()
/*  94:191 */         .newDocumentBuilder();
/*  95:192 */       Document document = builder.parse(ref);
/*  96:    */       
/*  97:194 */       Element element = document.getDocumentElement();
/*  98:195 */       if (!element.getNodeName().equals("system")) {
/*  99:196 */         throw new IOException("Not a particle system file");
/* 100:    */       }
/* 101:199 */       if (system == null) {
/* 102:200 */         system = new ParticleSystem("org/newdawn/slick/data/particle.tga", 
/* 103:201 */           2000, mask);
/* 104:    */       }
/* 105:203 */       boolean additive = "true".equals(element.getAttribute("additive"));
/* 106:204 */       if (additive) {
/* 107:205 */         system.setBlendingMode(1);
/* 108:    */       } else {
/* 109:207 */         system.setBlendingMode(2);
/* 110:    */       }
/* 111:209 */       boolean points = "true".equals(element.getAttribute("points"));
/* 112:210 */       system.setUsePoints(points);
/* 113:    */       
/* 114:212 */       NodeList list = element.getElementsByTagName("emitter");
/* 115:213 */       for (int i = 0; i < list.getLength(); i++)
/* 116:    */       {
/* 117:214 */         Element em = (Element)list.item(i);
/* 118:215 */         ConfigurableEmitter emitter = factory.createEmitter("new");
/* 119:216 */         elementToEmitter(em, emitter);
/* 120:    */         
/* 121:218 */         system.addEmitter(emitter);
/* 122:    */       }
/* 123:221 */       system.setRemoveCompletedEmitters(false);
/* 124:222 */       return system;
/* 125:    */     }
/* 126:    */     catch (IOException e)
/* 127:    */     {
/* 128:224 */       Log.error(e);
/* 129:225 */       throw e;
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:227 */       Log.error(e);
/* 134:228 */       throw new IOException("Unable to load particle system config");
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static void saveConfiguredSystem(File file, ParticleSystem system)
/* 139:    */     throws IOException
/* 140:    */   {
/* 141:244 */     saveConfiguredSystem(new FileOutputStream(file), system);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static void saveConfiguredSystem(OutputStream out, ParticleSystem system)
/* 145:    */     throws IOException
/* 146:    */   {
/* 147:    */     try
/* 148:    */     {
/* 149:260 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance()
/* 150:261 */         .newDocumentBuilder();
/* 151:262 */       Document document = builder.newDocument();
/* 152:    */       
/* 153:264 */       Element root = document.createElement("system");
/* 154:265 */       root
/* 155:266 */         .setAttribute(
/* 156:267 */         "additive", 
/* 157:    */         
/* 158:269 */         system.getBlendingMode() == 1);
/* 159:270 */       root.setAttribute("points", system.usePoints());
/* 160:    */       
/* 161:272 */       document.appendChild(root);
/* 162:273 */       for (int i = 0; i < system.getEmitterCount(); i++)
/* 163:    */       {
/* 164:274 */         ParticleEmitter current = system.getEmitter(i);
/* 165:275 */         if ((current instanceof ConfigurableEmitter))
/* 166:    */         {
/* 167:276 */           Element element = emitterToElement(document, 
/* 168:277 */             (ConfigurableEmitter)current);
/* 169:278 */           root.appendChild(element);
/* 170:    */         }
/* 171:    */         else
/* 172:    */         {
/* 173:280 */           throw new RuntimeException(
/* 174:281 */             "Only ConfigurableEmitter instances can be stored");
/* 175:    */         }
/* 176:    */       }
/* 177:285 */       Result result = new StreamResult(new OutputStreamWriter(out, 
/* 178:286 */         "utf-8"));
/* 179:287 */       DOMSource source = new DOMSource(document);
/* 180:    */       
/* 181:289 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 182:290 */       Transformer xformer = factory.newTransformer();
/* 183:291 */       xformer.setOutputProperty("indent", "yes");
/* 184:    */       
/* 185:293 */       xformer.transform(source, result);
/* 186:    */     }
/* 187:    */     catch (Exception e)
/* 188:    */     {
/* 189:295 */       Log.error(e);
/* 190:296 */       throw new IOException("Unable to save configured particle system");
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static ConfigurableEmitter loadEmitter(String ref)
/* 195:    */     throws IOException
/* 196:    */   {
/* 197:312 */     return loadEmitter(ResourceLoader.getResourceAsStream(ref), null);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static ConfigurableEmitter loadEmitter(File ref)
/* 201:    */     throws IOException
/* 202:    */   {
/* 203:325 */     return loadEmitter(new FileInputStream(ref), null);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static ConfigurableEmitter loadEmitter(InputStream ref)
/* 207:    */     throws IOException
/* 208:    */   {
/* 209:340 */     return loadEmitter(ref, null);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public static ConfigurableEmitter loadEmitter(String ref, ConfigurableEmitterFactory factory)
/* 213:    */     throws IOException
/* 214:    */   {
/* 215:358 */     return loadEmitter(ResourceLoader.getResourceAsStream(ref), factory);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static ConfigurableEmitter loadEmitter(File ref, ConfigurableEmitterFactory factory)
/* 219:    */     throws IOException
/* 220:    */   {
/* 221:375 */     return loadEmitter(new FileInputStream(ref), factory);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public static ConfigurableEmitter loadEmitter(InputStream ref, ConfigurableEmitterFactory factory)
/* 225:    */     throws IOException
/* 226:    */   {
/* 227:393 */     if (factory == null) {
/* 228:394 */       factory = new ConfigurableEmitterFactory()
/* 229:    */       {
/* 230:    */         public ConfigurableEmitter createEmitter(String name)
/* 231:    */         {
/* 232:396 */           return new ConfigurableEmitter(name);
/* 233:    */         }
/* 234:    */       };
/* 235:    */     }
/* 236:    */     try
/* 237:    */     {
/* 238:401 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance()
/* 239:402 */         .newDocumentBuilder();
/* 240:403 */       Document document = builder.parse(ref);
/* 241:405 */       if (!document.getDocumentElement().getNodeName().equals("emitter")) {
/* 242:406 */         throw new IOException("Not a particle emitter file");
/* 243:    */       }
/* 244:409 */       ConfigurableEmitter emitter = factory.createEmitter("new");
/* 245:410 */       elementToEmitter(document.getDocumentElement(), emitter);
/* 246:    */       
/* 247:412 */       return emitter;
/* 248:    */     }
/* 249:    */     catch (IOException e)
/* 250:    */     {
/* 251:414 */       Log.error(e);
/* 252:415 */       throw e;
/* 253:    */     }
/* 254:    */     catch (Exception e)
/* 255:    */     {
/* 256:417 */       Log.error(e);
/* 257:418 */       throw new IOException("Unable to load emitter");
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   public static void saveEmitter(File file, ConfigurableEmitter emitter)
/* 262:    */     throws IOException
/* 263:    */   {
/* 264:434 */     saveEmitter(new FileOutputStream(file), emitter);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public static void saveEmitter(OutputStream out, ConfigurableEmitter emitter)
/* 268:    */     throws IOException
/* 269:    */   {
/* 270:    */     try
/* 271:    */     {
/* 272:450 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance()
/* 273:451 */         .newDocumentBuilder();
/* 274:452 */       Document document = builder.newDocument();
/* 275:    */       
/* 276:454 */       document.appendChild(emitterToElement(document, emitter));
/* 277:455 */       Result result = new StreamResult(new OutputStreamWriter(out, 
/* 278:456 */         "utf-8"));
/* 279:457 */       DOMSource source = new DOMSource(document);
/* 280:    */       
/* 281:459 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 282:460 */       Transformer xformer = factory.newTransformer();
/* 283:461 */       xformer.setOutputProperty("indent", "yes");
/* 284:    */       
/* 285:463 */       xformer.transform(source, result);
/* 286:    */     }
/* 287:    */     catch (Exception e)
/* 288:    */     {
/* 289:465 */       Log.error(e);
/* 290:466 */       throw new IOException("Failed to save emitter");
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:    */   private static Element getFirstNamedElement(Element element, String name)
/* 295:    */   {
/* 296:480 */     NodeList list = element.getElementsByTagName(name);
/* 297:481 */     if (list.getLength() == 0) {
/* 298:482 */       return null;
/* 299:    */     }
/* 300:485 */     return (Element)list.item(0);
/* 301:    */   }
/* 302:    */   
/* 303:    */   private static void elementToEmitter(Element element, ConfigurableEmitter emitter)
/* 304:    */   {
/* 305:498 */     emitter.name = element.getAttribute("name");
/* 306:499 */     emitter.setImageName(element.getAttribute("imageName"));
/* 307:    */     
/* 308:501 */     String renderType = element.getAttribute("renderType");
/* 309:502 */     emitter.usePoints = 1;
/* 310:503 */     if (renderType.equals("quads")) {
/* 311:504 */       emitter.usePoints = 3;
/* 312:    */     }
/* 313:506 */     if (renderType.equals("points")) {
/* 314:507 */       emitter.usePoints = 2;
/* 315:    */     }
/* 316:510 */     String useOriented = element.getAttribute("useOriented");
/* 317:511 */     if (useOriented != null) {
/* 318:512 */       emitter.useOriented = "true".equals(useOriented);
/* 319:    */     }
/* 320:514 */     String useAdditive = element.getAttribute("useAdditive");
/* 321:515 */     if (useAdditive != null) {
/* 322:516 */       emitter.useAdditive = "true".equals(useAdditive);
/* 323:    */     }
/* 324:518 */     parseRangeElement(getFirstNamedElement(element, "spawnInterval"), 
/* 325:519 */       emitter.spawnInterval);
/* 326:520 */     parseRangeElement(getFirstNamedElement(element, "spawnCount"), 
/* 327:521 */       emitter.spawnCount);
/* 328:522 */     parseRangeElement(getFirstNamedElement(element, "initialLife"), 
/* 329:523 */       emitter.initialLife);
/* 330:524 */     parseRangeElement(getFirstNamedElement(element, "initialSize"), 
/* 331:525 */       emitter.initialSize);
/* 332:526 */     parseRangeElement(getFirstNamedElement(element, "xOffset"), 
/* 333:527 */       emitter.xOffset);
/* 334:528 */     parseRangeElement(getFirstNamedElement(element, "yOffset"), 
/* 335:529 */       emitter.yOffset);
/* 336:530 */     parseRangeElement(getFirstNamedElement(element, "initialDistance"), 
/* 337:531 */       emitter.initialDistance);
/* 338:532 */     parseRangeElement(getFirstNamedElement(element, "speed"), emitter.speed);
/* 339:533 */     parseRangeElement(getFirstNamedElement(element, "length"), 
/* 340:534 */       emitter.length);
/* 341:535 */     parseRangeElement(getFirstNamedElement(element, "emitCount"), 
/* 342:536 */       emitter.emitCount);
/* 343:    */     
/* 344:538 */     parseValueElement(getFirstNamedElement(element, "spread"), 
/* 345:539 */       emitter.spread);
/* 346:540 */     parseValueElement(getFirstNamedElement(element, "angularOffset"), 
/* 347:541 */       emitter.angularOffset);
/* 348:542 */     parseValueElement(getFirstNamedElement(element, "growthFactor"), 
/* 349:543 */       emitter.growthFactor);
/* 350:544 */     parseValueElement(getFirstNamedElement(element, "gravityFactor"), 
/* 351:545 */       emitter.gravityFactor);
/* 352:546 */     parseValueElement(getFirstNamedElement(element, "windFactor"), 
/* 353:547 */       emitter.windFactor);
/* 354:548 */     parseValueElement(getFirstNamedElement(element, "startAlpha"), 
/* 355:549 */       emitter.startAlpha);
/* 356:550 */     parseValueElement(getFirstNamedElement(element, "endAlpha"), 
/* 357:551 */       emitter.endAlpha);
/* 358:552 */     parseValueElement(getFirstNamedElement(element, "alpha"), emitter.alpha);
/* 359:553 */     parseValueElement(getFirstNamedElement(element, "size"), emitter.size);
/* 360:554 */     parseValueElement(getFirstNamedElement(element, "velocity"), 
/* 361:555 */       emitter.velocity);
/* 362:556 */     parseValueElement(getFirstNamedElement(element, "scaleY"), 
/* 363:557 */       emitter.scaleY);
/* 364:    */     
/* 365:559 */     Element color = getFirstNamedElement(element, "color");
/* 366:560 */     NodeList steps = color.getElementsByTagName("step");
/* 367:561 */     emitter.colors.clear();
/* 368:562 */     for (int i = 0; i < steps.getLength(); i++)
/* 369:    */     {
/* 370:563 */       Element step = (Element)steps.item(i);
/* 371:564 */       float offset = Float.parseFloat(step.getAttribute("offset"));
/* 372:565 */       float r = Float.parseFloat(step.getAttribute("r"));
/* 373:566 */       float g = Float.parseFloat(step.getAttribute("g"));
/* 374:567 */       float b = Float.parseFloat(step.getAttribute("b"));
/* 375:    */       
/* 376:569 */       emitter.addColorPoint(offset, new Color(r, g, b, 1.0F));
/* 377:    */     }
/* 378:573 */     emitter.replay();
/* 379:    */   }
/* 380:    */   
/* 381:    */   private static Element emitterToElement(Document document, ConfigurableEmitter emitter)
/* 382:    */   {
/* 383:587 */     Element root = document.createElement("emitter");
/* 384:588 */     root.setAttribute("name", emitter.name);
/* 385:589 */     root.setAttribute("imageName", emitter.imageName == null ? "" : 
/* 386:590 */       emitter.imageName);
/* 387:591 */     root
/* 388:592 */       .setAttribute("useOriented", emitter.useOriented ? "true" : 
/* 389:593 */       "false");
/* 390:594 */     root
/* 391:595 */       .setAttribute("useAdditive", emitter.useAdditive ? "true" : 
/* 392:596 */       "false");
/* 393:598 */     if (emitter.usePoints == 1) {
/* 394:599 */       root.setAttribute("renderType", "inherit");
/* 395:    */     }
/* 396:601 */     if (emitter.usePoints == 2) {
/* 397:602 */       root.setAttribute("renderType", "points");
/* 398:    */     }
/* 399:604 */     if (emitter.usePoints == 3) {
/* 400:605 */       root.setAttribute("renderType", "quads");
/* 401:    */     }
/* 402:608 */     root.appendChild(createRangeElement(document, "spawnInterval", 
/* 403:609 */       emitter.spawnInterval));
/* 404:610 */     root.appendChild(createRangeElement(document, "spawnCount", 
/* 405:611 */       emitter.spawnCount));
/* 406:612 */     root.appendChild(createRangeElement(document, "initialLife", 
/* 407:613 */       emitter.initialLife));
/* 408:614 */     root.appendChild(createRangeElement(document, "initialSize", 
/* 409:615 */       emitter.initialSize));
/* 410:616 */     root.appendChild(createRangeElement(document, "xOffset", 
/* 411:617 */       emitter.xOffset));
/* 412:618 */     root.appendChild(createRangeElement(document, "yOffset", 
/* 413:619 */       emitter.yOffset));
/* 414:620 */     root.appendChild(createRangeElement(document, "initialDistance", 
/* 415:621 */       emitter.initialDistance));
/* 416:622 */     root.appendChild(createRangeElement(document, "speed", emitter.speed));
/* 417:623 */     root
/* 418:624 */       .appendChild(createRangeElement(document, "length", 
/* 419:625 */       emitter.length));
/* 420:626 */     root.appendChild(createRangeElement(document, "emitCount", 
/* 421:627 */       emitter.emitCount));
/* 422:    */     
/* 423:629 */     root
/* 424:630 */       .appendChild(createValueElement(document, "spread", 
/* 425:631 */       emitter.spread));
/* 426:632 */     root.appendChild(createValueElement(document, "angularOffset", 
/* 427:633 */       emitter.angularOffset));
/* 428:634 */     root.appendChild(createValueElement(document, "growthFactor", 
/* 429:635 */       emitter.growthFactor));
/* 430:636 */     root.appendChild(createValueElement(document, "gravityFactor", 
/* 431:637 */       emitter.gravityFactor));
/* 432:638 */     root.appendChild(createValueElement(document, "windFactor", 
/* 433:639 */       emitter.windFactor));
/* 434:640 */     root.appendChild(createValueElement(document, "startAlpha", 
/* 435:641 */       emitter.startAlpha));
/* 436:642 */     root.appendChild(createValueElement(document, "endAlpha", 
/* 437:643 */       emitter.endAlpha));
/* 438:644 */     root.appendChild(createValueElement(document, "alpha", emitter.alpha));
/* 439:645 */     root.appendChild(createValueElement(document, "size", emitter.size));
/* 440:646 */     root.appendChild(createValueElement(document, "velocity", 
/* 441:647 */       emitter.velocity));
/* 442:648 */     root
/* 443:649 */       .appendChild(createValueElement(document, "scaleY", 
/* 444:650 */       emitter.scaleY));
/* 445:    */     
/* 446:652 */     Element color = document.createElement("color");
/* 447:653 */     ArrayList list = emitter.colors;
/* 448:654 */     for (int i = 0; i < list.size(); i++)
/* 449:    */     {
/* 450:655 */       ConfigurableEmitter.ColorRecord record = (ConfigurableEmitter.ColorRecord)list.get(i);
/* 451:656 */       Element step = document.createElement("step");
/* 452:657 */       step.setAttribute("offset", record.pos);
/* 453:658 */       step.setAttribute("r", record.col.r);
/* 454:659 */       step.setAttribute("g", record.col.g);
/* 455:660 */       step.setAttribute("b", record.col.b);
/* 456:    */       
/* 457:662 */       color.appendChild(step);
/* 458:    */     }
/* 459:665 */     root.appendChild(color);
/* 460:    */     
/* 461:667 */     return root;
/* 462:    */   }
/* 463:    */   
/* 464:    */   private static Element createRangeElement(Document document, String name, ConfigurableEmitter.Range range)
/* 465:    */   {
/* 466:683 */     Element element = document.createElement(name);
/* 467:684 */     element.setAttribute("min", range.getMin());
/* 468:685 */     element.setAttribute("max", range.getMax());
/* 469:686 */     element.setAttribute("enabled", range.isEnabled());
/* 470:    */     
/* 471:688 */     return element;
/* 472:    */   }
/* 473:    */   
/* 474:    */   private static Element createValueElement(Document document, String name, ConfigurableEmitter.Value value)
/* 475:    */   {
/* 476:704 */     Element element = document.createElement(name);
/* 477:707 */     if ((value instanceof ConfigurableEmitter.SimpleValue))
/* 478:    */     {
/* 479:708 */       element.setAttribute("type", "simple");
/* 480:709 */       element.setAttribute("value", value.getValue(0.0F));
/* 481:    */     }
/* 482:710 */     else if ((value instanceof ConfigurableEmitter.RandomValue))
/* 483:    */     {
/* 484:711 */       element.setAttribute("type", "random");
/* 485:712 */       element
/* 486:713 */         .setAttribute("value", 
/* 487:714 */         ((ConfigurableEmitter.RandomValue)value).getValue());
/* 488:    */     }
/* 489:715 */     else if ((value instanceof ConfigurableEmitter.LinearInterpolator))
/* 490:    */     {
/* 491:716 */       element.setAttribute("type", "linear");
/* 492:717 */       element.setAttribute("min", 
/* 493:718 */         ((ConfigurableEmitter.LinearInterpolator)value).getMin());
/* 494:719 */       element.setAttribute("max", 
/* 495:720 */         ((ConfigurableEmitter.LinearInterpolator)value).getMax());
/* 496:721 */       element.setAttribute("active", 
/* 497:722 */         ((ConfigurableEmitter.LinearInterpolator)value).isActive());
/* 498:    */       
/* 499:724 */       ArrayList curve = ((ConfigurableEmitter.LinearInterpolator)value).getCurve();
/* 500:725 */       for (int i = 0; i < curve.size(); i++)
/* 501:    */       {
/* 502:726 */         Vector2f point = (Vector2f)curve.get(i);
/* 503:    */         
/* 504:728 */         Element pointElement = document.createElement("point");
/* 505:729 */         pointElement.setAttribute("x", point.x);
/* 506:730 */         pointElement.setAttribute("y", point.y);
/* 507:    */         
/* 508:732 */         element.appendChild(pointElement);
/* 509:    */       }
/* 510:    */     }
/* 511:    */     else
/* 512:    */     {
/* 513:735 */       Log.warn("unkown value type ignored: " + value.getClass());
/* 514:    */     }
/* 515:738 */     return element;
/* 516:    */   }
/* 517:    */   
/* 518:    */   private static void parseRangeElement(Element element, ConfigurableEmitter.Range range)
/* 519:    */   {
/* 520:751 */     if (element == null) {
/* 521:752 */       return;
/* 522:    */     }
/* 523:754 */     range.setMin(Float.parseFloat(element.getAttribute("min")));
/* 524:755 */     range.setMax(Float.parseFloat(element.getAttribute("max")));
/* 525:756 */     range.setEnabled("true".equals(element.getAttribute("enabled")));
/* 526:    */   }
/* 527:    */   
/* 528:    */   private static void parseValueElement(Element element, ConfigurableEmitter.Value value)
/* 529:    */   {
/* 530:769 */     if (element == null) {
/* 531:770 */       return;
/* 532:    */     }
/* 533:773 */     String type = element.getAttribute("type");
/* 534:774 */     String v = element.getAttribute("value");
/* 535:776 */     if ((type == null) || (type.length() == 0))
/* 536:    */     {
/* 537:778 */       if ((value instanceof ConfigurableEmitter.SimpleValue)) {
/* 538:779 */         ((ConfigurableEmitter.SimpleValue)value).setValue(Float.parseFloat(v));
/* 539:780 */       } else if ((value instanceof ConfigurableEmitter.RandomValue)) {
/* 540:781 */         ((ConfigurableEmitter.RandomValue)value).setValue(Float.parseFloat(v));
/* 541:    */       } else {
/* 542:783 */         Log.warn("problems reading element, skipping: " + element);
/* 543:    */       }
/* 544:    */     }
/* 545:787 */     else if (type.equals("simple"))
/* 546:    */     {
/* 547:788 */       ((ConfigurableEmitter.SimpleValue)value).setValue(Float.parseFloat(v));
/* 548:    */     }
/* 549:789 */     else if (type.equals("random"))
/* 550:    */     {
/* 551:790 */       ((ConfigurableEmitter.RandomValue)value).setValue(Float.parseFloat(v));
/* 552:    */     }
/* 553:791 */     else if (type.equals("linear"))
/* 554:    */     {
/* 555:792 */       String min = element.getAttribute("min");
/* 556:793 */       String max = element.getAttribute("max");
/* 557:794 */       String active = element.getAttribute("active");
/* 558:    */       
/* 559:796 */       NodeList points = element.getElementsByTagName("point");
/* 560:    */       
/* 561:798 */       ArrayList curve = new ArrayList();
/* 562:799 */       for (int i = 0; i < points.getLength(); i++)
/* 563:    */       {
/* 564:800 */         Element point = (Element)points.item(i);
/* 565:    */         
/* 566:802 */         float x = Float.parseFloat(point.getAttribute("x"));
/* 567:803 */         float y = Float.parseFloat(point.getAttribute("y"));
/* 568:    */         
/* 569:805 */         curve.add(new Vector2f(x, y));
/* 570:    */       }
/* 571:808 */       ((ConfigurableEmitter.LinearInterpolator)value).setCurve(curve);
/* 572:809 */       ((ConfigurableEmitter.LinearInterpolator)value).setMin(Integer.parseInt(min));
/* 573:810 */       ((ConfigurableEmitter.LinearInterpolator)value).setMax(Integer.parseInt(max));
/* 574:811 */       ((ConfigurableEmitter.LinearInterpolator)value).setActive("true".equals(active));
/* 575:    */     }
/* 576:    */     else
/* 577:    */     {
/* 578:813 */       Log.warn("unkown type detected: " + type);
/* 579:    */     }
/* 580:    */   }
/* 581:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.particles.ParticleIO
 * JD-Core Version:    0.7.0.1
 */