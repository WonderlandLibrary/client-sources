/*     */ package org.newdawn.slick.particles;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.geom.Vector2f;
/*     */ import org.newdawn.slick.util.Log;
/*     */ import org.newdawn.slick.util.ResourceLoader;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleIO
/*     */ {
/*     */   public static ParticleSystem loadConfiguredSystem(String ref, Color mask) throws IOException {
/*  52 */     return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), null, null, mask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParticleSystem loadConfiguredSystem(String ref) throws IOException {
/*  67 */     return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParticleSystem loadConfiguredSystem(File ref) throws IOException {
/*  82 */     return loadConfiguredSystem(new FileInputStream(ref), null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParticleSystem loadConfiguredSystem(InputStream ref, Color mask) throws IOException {
/*  97 */     return loadConfiguredSystem(ref, null, null, mask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParticleSystem loadConfiguredSystem(InputStream ref) throws IOException {
/* 111 */     return loadConfiguredSystem(ref, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParticleSystem loadConfiguredSystem(String ref, ConfigurableEmitterFactory factory) throws IOException {
/* 128 */     return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), factory, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParticleSystem loadConfiguredSystem(File ref, ConfigurableEmitterFactory factory) throws IOException {
/* 146 */     return loadConfiguredSystem(new FileInputStream(ref), factory, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParticleSystem loadConfiguredSystem(InputStream ref, ConfigurableEmitterFactory factory) throws IOException {
/* 163 */     return loadConfiguredSystem(ref, factory, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParticleSystem loadConfiguredSystem(InputStream ref, ConfigurableEmitterFactory factory, ParticleSystem system, Color mask) throws IOException {
/* 182 */     if (factory == null) {
/* 183 */       factory = new ConfigurableEmitterFactory() {
/*     */           public ConfigurableEmitter createEmitter(String name) {
/* 185 */             return new ConfigurableEmitter(name);
/*     */           }
/*     */         };
/*     */     }
/*     */     try {
/* 190 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/*     */       
/* 192 */       Document document = builder.parse(ref);
/*     */       
/* 194 */       Element element = document.getDocumentElement();
/* 195 */       if (!element.getNodeName().equals("system")) {
/* 196 */         throw new IOException("Not a particle system file");
/*     */       }
/*     */       
/* 199 */       if (system == null) {
/* 200 */         system = new ParticleSystem("org/newdawn/slick/data/particle.tga", 2000, mask);
/*     */       }
/*     */       
/* 203 */       boolean additive = "true".equals(element.getAttribute("additive"));
/* 204 */       if (additive) {
/* 205 */         system.setBlendingMode(1);
/*     */       } else {
/* 207 */         system.setBlendingMode(2);
/*     */       } 
/* 209 */       boolean points = "true".equals(element.getAttribute("points"));
/* 210 */       system.setUsePoints(points);
/*     */       
/* 212 */       NodeList list = element.getElementsByTagName("emitter");
/* 213 */       for (int i = 0; i < list.getLength(); i++) {
/* 214 */         Element em = (Element)list.item(i);
/* 215 */         ConfigurableEmitter emitter = factory.createEmitter("new");
/* 216 */         elementToEmitter(em, emitter);
/*     */         
/* 218 */         system.addEmitter(emitter);
/*     */       } 
/*     */       
/* 221 */       system.setRemoveCompletedEmitters(false);
/* 222 */       return system;
/* 223 */     } catch (IOException e) {
/* 224 */       Log.error(e);
/* 225 */       throw e;
/* 226 */     } catch (Exception e) {
/* 227 */       Log.error(e);
/* 228 */       throw new IOException("Unable to load particle system config");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveConfiguredSystem(File file, ParticleSystem system) throws IOException {
/* 244 */     saveConfiguredSystem(new FileOutputStream(file), system);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveConfiguredSystem(OutputStream out, ParticleSystem system) throws IOException {
/*     */     try {
/* 260 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/*     */       
/* 262 */       Document document = builder.newDocument();
/*     */       
/* 264 */       Element root = document.createElement("system");
/* 265 */       root.setAttribute("additive", "" + ((system.getBlendingMode() == 1) ? 1 : 0));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 270 */       root.setAttribute("points", "" + system.usePoints());
/*     */       
/* 272 */       document.appendChild(root);
/* 273 */       for (int i = 0; i < system.getEmitterCount(); i++) {
/* 274 */         ParticleEmitter current = system.getEmitter(i);
/* 275 */         if (current instanceof ConfigurableEmitter) {
/* 276 */           Element element = emitterToElement(document, (ConfigurableEmitter)current);
/*     */           
/* 278 */           root.appendChild(element);
/*     */         } else {
/* 280 */           throw new RuntimeException("Only ConfigurableEmitter instances can be stored");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 285 */       Result result = new StreamResult(new OutputStreamWriter(out, "utf-8"));
/*     */       
/* 287 */       DOMSource source = new DOMSource(document);
/*     */       
/* 289 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 290 */       Transformer xformer = factory.newTransformer();
/* 291 */       xformer.setOutputProperty("indent", "yes");
/*     */       
/* 293 */       xformer.transform(source, result);
/* 294 */     } catch (Exception e) {
/* 295 */       Log.error(e);
/* 296 */       throw new IOException("Unable to save configured particle system");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEmitter loadEmitter(String ref) throws IOException {
/* 312 */     return loadEmitter(ResourceLoader.getResourceAsStream(ref), (ConfigurableEmitterFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEmitter loadEmitter(File ref) throws IOException {
/* 325 */     return loadEmitter(new FileInputStream(ref), (ConfigurableEmitterFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEmitter loadEmitter(InputStream ref) throws IOException {
/* 340 */     return loadEmitter(ref, (ConfigurableEmitterFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEmitter loadEmitter(String ref, ConfigurableEmitterFactory factory) throws IOException {
/* 358 */     return loadEmitter(ResourceLoader.getResourceAsStream(ref), factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEmitter loadEmitter(File ref, ConfigurableEmitterFactory factory) throws IOException {
/* 375 */     return loadEmitter(new FileInputStream(ref), factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEmitter loadEmitter(InputStream ref, ConfigurableEmitterFactory factory) throws IOException {
/* 393 */     if (factory == null) {
/* 394 */       factory = new ConfigurableEmitterFactory() {
/*     */           public ConfigurableEmitter createEmitter(String name) {
/* 396 */             return new ConfigurableEmitter(name);
/*     */           }
/*     */         };
/*     */     }
/*     */     try {
/* 401 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/*     */       
/* 403 */       Document document = builder.parse(ref);
/*     */       
/* 405 */       if (!document.getDocumentElement().getNodeName().equals("emitter")) {
/* 406 */         throw new IOException("Not a particle emitter file");
/*     */       }
/*     */       
/* 409 */       ConfigurableEmitter emitter = factory.createEmitter("new");
/* 410 */       elementToEmitter(document.getDocumentElement(), emitter);
/*     */       
/* 412 */       return emitter;
/* 413 */     } catch (IOException e) {
/* 414 */       Log.error(e);
/* 415 */       throw e;
/* 416 */     } catch (Exception e) {
/* 417 */       Log.error(e);
/* 418 */       throw new IOException("Unable to load emitter");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveEmitter(File file, ConfigurableEmitter emitter) throws IOException {
/* 434 */     saveEmitter(new FileOutputStream(file), emitter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveEmitter(OutputStream out, ConfigurableEmitter emitter) throws IOException {
/*     */     try {
/* 450 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/*     */       
/* 452 */       Document document = builder.newDocument();
/*     */       
/* 454 */       document.appendChild(emitterToElement(document, emitter));
/* 455 */       Result result = new StreamResult(new OutputStreamWriter(out, "utf-8"));
/*     */       
/* 457 */       DOMSource source = new DOMSource(document);
/*     */       
/* 459 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 460 */       Transformer xformer = factory.newTransformer();
/* 461 */       xformer.setOutputProperty("indent", "yes");
/*     */       
/* 463 */       xformer.transform(source, result);
/* 464 */     } catch (Exception e) {
/* 465 */       Log.error(e);
/* 466 */       throw new IOException("Failed to save emitter");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element getFirstNamedElement(Element element, String name) {
/* 480 */     NodeList list = element.getElementsByTagName(name);
/* 481 */     if (list.getLength() == 0) {
/* 482 */       return null;
/*     */     }
/*     */     
/* 485 */     return (Element)list.item(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void elementToEmitter(Element element, ConfigurableEmitter emitter) {
/* 498 */     emitter.name = element.getAttribute("name");
/* 499 */     emitter.setImageName(element.getAttribute("imageName"));
/*     */     
/* 501 */     String renderType = element.getAttribute("renderType");
/* 502 */     emitter.usePoints = 1;
/* 503 */     if (renderType.equals("quads")) {
/* 504 */       emitter.usePoints = 3;
/*     */     }
/* 506 */     if (renderType.equals("points")) {
/* 507 */       emitter.usePoints = 2;
/*     */     }
/*     */     
/* 510 */     String useOriented = element.getAttribute("useOriented");
/* 511 */     if (useOriented != null) {
/* 512 */       emitter.useOriented = "true".equals(useOriented);
/*     */     }
/* 514 */     String useAdditive = element.getAttribute("useAdditive");
/* 515 */     if (useAdditive != null) {
/* 516 */       emitter.useAdditive = "true".equals(useAdditive);
/*     */     }
/* 518 */     parseRangeElement(getFirstNamedElement(element, "spawnInterval"), emitter.spawnInterval);
/*     */     
/* 520 */     parseRangeElement(getFirstNamedElement(element, "spawnCount"), emitter.spawnCount);
/*     */     
/* 522 */     parseRangeElement(getFirstNamedElement(element, "initialLife"), emitter.initialLife);
/*     */     
/* 524 */     parseRangeElement(getFirstNamedElement(element, "initialSize"), emitter.initialSize);
/*     */     
/* 526 */     parseRangeElement(getFirstNamedElement(element, "xOffset"), emitter.xOffset);
/*     */     
/* 528 */     parseRangeElement(getFirstNamedElement(element, "yOffset"), emitter.yOffset);
/*     */     
/* 530 */     parseRangeElement(getFirstNamedElement(element, "initialDistance"), emitter.initialDistance);
/*     */     
/* 532 */     parseRangeElement(getFirstNamedElement(element, "speed"), emitter.speed);
/* 533 */     parseRangeElement(getFirstNamedElement(element, "length"), emitter.length);
/*     */     
/* 535 */     parseRangeElement(getFirstNamedElement(element, "emitCount"), emitter.emitCount);
/*     */ 
/*     */     
/* 538 */     parseValueElement(getFirstNamedElement(element, "spread"), emitter.spread);
/*     */     
/* 540 */     parseValueElement(getFirstNamedElement(element, "angularOffset"), emitter.angularOffset);
/*     */     
/* 542 */     parseValueElement(getFirstNamedElement(element, "growthFactor"), emitter.growthFactor);
/*     */     
/* 544 */     parseValueElement(getFirstNamedElement(element, "gravityFactor"), emitter.gravityFactor);
/*     */     
/* 546 */     parseValueElement(getFirstNamedElement(element, "windFactor"), emitter.windFactor);
/*     */     
/* 548 */     parseValueElement(getFirstNamedElement(element, "startAlpha"), emitter.startAlpha);
/*     */     
/* 550 */     parseValueElement(getFirstNamedElement(element, "endAlpha"), emitter.endAlpha);
/*     */     
/* 552 */     parseValueElement(getFirstNamedElement(element, "alpha"), emitter.alpha);
/* 553 */     parseValueElement(getFirstNamedElement(element, "size"), emitter.size);
/* 554 */     parseValueElement(getFirstNamedElement(element, "velocity"), emitter.velocity);
/*     */     
/* 556 */     parseValueElement(getFirstNamedElement(element, "scaleY"), emitter.scaleY);
/*     */ 
/*     */     
/* 559 */     Element color = getFirstNamedElement(element, "color");
/* 560 */     NodeList steps = color.getElementsByTagName("step");
/* 561 */     emitter.colors.clear();
/* 562 */     for (int i = 0; i < steps.getLength(); i++) {
/* 563 */       Element step = (Element)steps.item(i);
/* 564 */       float offset = Float.parseFloat(step.getAttribute("offset"));
/* 565 */       float r = Float.parseFloat(step.getAttribute("r"));
/* 566 */       float g = Float.parseFloat(step.getAttribute("g"));
/* 567 */       float b = Float.parseFloat(step.getAttribute("b"));
/*     */       
/* 569 */       emitter.addColorPoint(offset, new Color(r, g, b, 1.0F));
/*     */     } 
/*     */ 
/*     */     
/* 573 */     emitter.replay();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element emitterToElement(Document document, ConfigurableEmitter emitter) {
/* 587 */     Element root = document.createElement("emitter");
/* 588 */     root.setAttribute("name", emitter.name);
/* 589 */     root.setAttribute("imageName", (emitter.imageName == null) ? "" : emitter.imageName);
/*     */     
/* 591 */     root.setAttribute("useOriented", emitter.useOriented ? "true" : "false");
/*     */ 
/*     */     
/* 594 */     root.setAttribute("useAdditive", emitter.useAdditive ? "true" : "false");
/*     */ 
/*     */ 
/*     */     
/* 598 */     if (emitter.usePoints == 1) {
/* 599 */       root.setAttribute("renderType", "inherit");
/*     */     }
/* 601 */     if (emitter.usePoints == 2) {
/* 602 */       root.setAttribute("renderType", "points");
/*     */     }
/* 604 */     if (emitter.usePoints == 3) {
/* 605 */       root.setAttribute("renderType", "quads");
/*     */     }
/*     */     
/* 608 */     root.appendChild(createRangeElement(document, "spawnInterval", emitter.spawnInterval));
/*     */     
/* 610 */     root.appendChild(createRangeElement(document, "spawnCount", emitter.spawnCount));
/*     */     
/* 612 */     root.appendChild(createRangeElement(document, "initialLife", emitter.initialLife));
/*     */     
/* 614 */     root.appendChild(createRangeElement(document, "initialSize", emitter.initialSize));
/*     */     
/* 616 */     root.appendChild(createRangeElement(document, "xOffset", emitter.xOffset));
/*     */     
/* 618 */     root.appendChild(createRangeElement(document, "yOffset", emitter.yOffset));
/*     */     
/* 620 */     root.appendChild(createRangeElement(document, "initialDistance", emitter.initialDistance));
/*     */     
/* 622 */     root.appendChild(createRangeElement(document, "speed", emitter.speed));
/* 623 */     root.appendChild(createRangeElement(document, "length", emitter.length));
/*     */ 
/*     */     
/* 626 */     root.appendChild(createRangeElement(document, "emitCount", emitter.emitCount));
/*     */ 
/*     */     
/* 629 */     root.appendChild(createValueElement(document, "spread", emitter.spread));
/*     */ 
/*     */     
/* 632 */     root.appendChild(createValueElement(document, "angularOffset", emitter.angularOffset));
/*     */     
/* 634 */     root.appendChild(createValueElement(document, "growthFactor", emitter.growthFactor));
/*     */     
/* 636 */     root.appendChild(createValueElement(document, "gravityFactor", emitter.gravityFactor));
/*     */     
/* 638 */     root.appendChild(createValueElement(document, "windFactor", emitter.windFactor));
/*     */     
/* 640 */     root.appendChild(createValueElement(document, "startAlpha", emitter.startAlpha));
/*     */     
/* 642 */     root.appendChild(createValueElement(document, "endAlpha", emitter.endAlpha));
/*     */     
/* 644 */     root.appendChild(createValueElement(document, "alpha", emitter.alpha));
/* 645 */     root.appendChild(createValueElement(document, "size", emitter.size));
/* 646 */     root.appendChild(createValueElement(document, "velocity", emitter.velocity));
/*     */     
/* 648 */     root.appendChild(createValueElement(document, "scaleY", emitter.scaleY));
/*     */ 
/*     */ 
/*     */     
/* 652 */     Element color = document.createElement("color");
/* 653 */     ArrayList<ConfigurableEmitter.ColorRecord> list = emitter.colors;
/* 654 */     for (int i = 0; i < list.size(); i++) {
/* 655 */       ConfigurableEmitter.ColorRecord record = list.get(i);
/* 656 */       Element step = document.createElement("step");
/* 657 */       step.setAttribute("offset", "" + record.pos);
/* 658 */       step.setAttribute("r", "" + record.col.r);
/* 659 */       step.setAttribute("g", "" + record.col.g);
/* 660 */       step.setAttribute("b", "" + record.col.b);
/*     */       
/* 662 */       color.appendChild(step);
/*     */     } 
/*     */     
/* 665 */     root.appendChild(color);
/*     */     
/* 667 */     return root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element createRangeElement(Document document, String name, ConfigurableEmitter.Range range) {
/* 683 */     Element element = document.createElement(name);
/* 684 */     element.setAttribute("min", "" + range.getMin());
/* 685 */     element.setAttribute("max", "" + range.getMax());
/* 686 */     element.setAttribute("enabled", "" + range.isEnabled());
/*     */     
/* 688 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element createValueElement(Document document, String name, ConfigurableEmitter.Value value) {
/* 704 */     Element element = document.createElement(name);
/*     */ 
/*     */     
/* 707 */     if (value instanceof ConfigurableEmitter.SimpleValue) {
/* 708 */       element.setAttribute("type", "simple");
/* 709 */       element.setAttribute("value", "" + value.getValue(0.0F));
/* 710 */     } else if (value instanceof ConfigurableEmitter.RandomValue) {
/* 711 */       element.setAttribute("type", "random");
/* 712 */       element.setAttribute("value", "" + ((ConfigurableEmitter.RandomValue)value).getValue());
/*     */     
/*     */     }
/* 715 */     else if (value instanceof ConfigurableEmitter.LinearInterpolator) {
/* 716 */       element.setAttribute("type", "linear");
/* 717 */       element.setAttribute("min", "" + ((ConfigurableEmitter.LinearInterpolator)value).getMin());
/*     */       
/* 719 */       element.setAttribute("max", "" + ((ConfigurableEmitter.LinearInterpolator)value).getMax());
/*     */       
/* 721 */       element.setAttribute("active", "" + ((ConfigurableEmitter.LinearInterpolator)value).isActive());
/*     */ 
/*     */       
/* 724 */       ArrayList<Vector2f> curve = ((ConfigurableEmitter.LinearInterpolator)value).getCurve();
/* 725 */       for (int i = 0; i < curve.size(); i++) {
/* 726 */         Vector2f point = curve.get(i);
/*     */         
/* 728 */         Element pointElement = document.createElement("point");
/* 729 */         pointElement.setAttribute("x", "" + point.x);
/* 730 */         pointElement.setAttribute("y", "" + point.y);
/*     */         
/* 732 */         element.appendChild(pointElement);
/*     */       } 
/*     */     } else {
/* 735 */       Log.warn("unkown value type ignored: " + value.getClass());
/*     */     } 
/*     */     
/* 738 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void parseRangeElement(Element element, ConfigurableEmitter.Range range) {
/* 751 */     if (element == null) {
/*     */       return;
/*     */     }
/* 754 */     range.setMin(Float.parseFloat(element.getAttribute("min")));
/* 755 */     range.setMax(Float.parseFloat(element.getAttribute("max")));
/* 756 */     range.setEnabled("true".equals(element.getAttribute("enabled")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void parseValueElement(Element element, ConfigurableEmitter.Value value) {
/* 769 */     if (element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 773 */     String type = element.getAttribute("type");
/* 774 */     String v = element.getAttribute("value");
/*     */     
/* 776 */     if (type == null || type.length() == 0) {
/*     */       
/* 778 */       if (value instanceof ConfigurableEmitter.SimpleValue) {
/* 779 */         ((ConfigurableEmitter.SimpleValue)value).setValue(Float.parseFloat(v));
/* 780 */       } else if (value instanceof ConfigurableEmitter.RandomValue) {
/* 781 */         ((ConfigurableEmitter.RandomValue)value).setValue(Float.parseFloat(v));
/*     */       } else {
/* 783 */         Log.warn("problems reading element, skipping: " + element);
/*     */       }
/*     */     
/*     */     }
/* 787 */     else if (type.equals("simple")) {
/* 788 */       ((ConfigurableEmitter.SimpleValue)value).setValue(Float.parseFloat(v));
/* 789 */     } else if (type.equals("random")) {
/* 790 */       ((ConfigurableEmitter.RandomValue)value).setValue(Float.parseFloat(v));
/* 791 */     } else if (type.equals("linear")) {
/* 792 */       String min = element.getAttribute("min");
/* 793 */       String max = element.getAttribute("max");
/* 794 */       String active = element.getAttribute("active");
/*     */       
/* 796 */       NodeList points = element.getElementsByTagName("point");
/*     */       
/* 798 */       ArrayList<Vector2f> curve = new ArrayList();
/* 799 */       for (int i = 0; i < points.getLength(); i++) {
/* 800 */         Element point = (Element)points.item(i);
/*     */         
/* 802 */         float x = Float.parseFloat(point.getAttribute("x"));
/* 803 */         float y = Float.parseFloat(point.getAttribute("y"));
/*     */         
/* 805 */         curve.add(new Vector2f(x, y));
/*     */       } 
/*     */       
/* 808 */       ((ConfigurableEmitter.LinearInterpolator)value).setCurve(curve);
/* 809 */       ((ConfigurableEmitter.LinearInterpolator)value).setMin(Integer.parseInt(min));
/* 810 */       ((ConfigurableEmitter.LinearInterpolator)value).setMax(Integer.parseInt(max));
/* 811 */       ((ConfigurableEmitter.LinearInterpolator)value).setActive("true".equals(active));
/*     */     } else {
/* 813 */       Log.warn("unkown type detected: " + type);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\particles\ParticleIO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */