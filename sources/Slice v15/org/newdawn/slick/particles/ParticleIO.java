package org.newdawn.slick.particles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;




















public class ParticleIO
{
  public ParticleIO() {}
  
  public static ParticleSystem loadConfiguredSystem(String ref, Color mask)
    throws IOException
  {
    return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), 
      null, null, mask);
  }
  








  public static ParticleSystem loadConfiguredSystem(String ref)
    throws IOException
  {
    return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), 
      null, null, null);
  }
  








  public static ParticleSystem loadConfiguredSystem(File ref)
    throws IOException
  {
    return loadConfiguredSystem(new FileInputStream(ref), null, null, null);
  }
  









  public static ParticleSystem loadConfiguredSystem(InputStream ref, Color mask)
    throws IOException
  {
    return loadConfiguredSystem(ref, null, null, mask);
  }
  








  public static ParticleSystem loadConfiguredSystem(InputStream ref)
    throws IOException
  {
    return loadConfiguredSystem(ref, null, null, null);
  }
  











  public static ParticleSystem loadConfiguredSystem(String ref, ConfigurableEmitterFactory factory)
    throws IOException
  {
    return loadConfiguredSystem(ResourceLoader.getResourceAsStream(ref), 
      factory, null, null);
  }
  











  public static ParticleSystem loadConfiguredSystem(File ref, ConfigurableEmitterFactory factory)
    throws IOException
  {
    return loadConfiguredSystem(new FileInputStream(ref), factory, null, null);
  }
  











  public static ParticleSystem loadConfiguredSystem(InputStream ref, ConfigurableEmitterFactory factory)
    throws IOException
  {
    return loadConfiguredSystem(ref, factory, null, null);
  }
  













  public static ParticleSystem loadConfiguredSystem(InputStream ref, ConfigurableEmitterFactory factory, ParticleSystem system, Color mask)
    throws IOException
  {
    if (factory == null) {
      factory = new ConfigurableEmitterFactory() {
        public ConfigurableEmitter createEmitter(String name) {
          return new ConfigurableEmitter(name);
        }
      };
    }
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder();
      Document document = builder.parse(ref);
      
      Element element = document.getDocumentElement();
      if (!element.getNodeName().equals("system")) {
        throw new IOException("Not a particle system file");
      }
      
      if (system == null) {
        system = new ParticleSystem("org/newdawn/slick/data/particle.tga", 
          2000, mask);
      }
      boolean additive = "true".equals(element.getAttribute("additive"));
      if (additive) {
        system.setBlendingMode(1);
      } else {
        system.setBlendingMode(2);
      }
      boolean points = "true".equals(element.getAttribute("points"));
      system.setUsePoints(points);
      
      NodeList list = element.getElementsByTagName("emitter");
      for (int i = 0; i < list.getLength(); i++) {
        Element em = (Element)list.item(i);
        ConfigurableEmitter emitter = factory.createEmitter("new");
        elementToEmitter(em, emitter);
        
        system.addEmitter(emitter);
      }
      
      system.setRemoveCompletedEmitters(false);
      return system;
    } catch (IOException e) {
      Log.error(e);
      throw e;
    } catch (Exception e) {
      Log.error(e);
      throw new IOException("Unable to load particle system config");
    }
  }
  









  public static void saveConfiguredSystem(File file, ParticleSystem system)
    throws IOException
  {
    saveConfiguredSystem(new FileOutputStream(file), system);
  }
  








  public static void saveConfiguredSystem(OutputStream out, ParticleSystem system)
    throws IOException
  {
    try
    {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder();
      Document document = builder.newDocument();
      
      Element root = document.createElement("system");
      root
        .setAttribute(
        "additive", 
        
        system.getBlendingMode() == 1);
      root.setAttribute("points", system.usePoints());
      
      document.appendChild(root);
      for (int i = 0; i < system.getEmitterCount(); i++) {
        ParticleEmitter current = system.getEmitter(i);
        if ((current instanceof ConfigurableEmitter)) {
          Element element = emitterToElement(document, 
            (ConfigurableEmitter)current);
          root.appendChild(element);
        } else {
          throw new RuntimeException(
            "Only ConfigurableEmitter instances can be stored");
        }
      }
      
      Result result = new StreamResult(new OutputStreamWriter(out, 
        "utf-8"));
      DOMSource source = new DOMSource(document);
      
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer xformer = factory.newTransformer();
      xformer.setOutputProperty("indent", "yes");
      
      xformer.transform(source, result);
    } catch (Exception e) {
      Log.error(e);
      throw new IOException("Unable to save configured particle system");
    }
  }
  









  public static ConfigurableEmitter loadEmitter(String ref)
    throws IOException
  {
    return loadEmitter(ResourceLoader.getResourceAsStream(ref), null);
  }
  







  public static ConfigurableEmitter loadEmitter(File ref)
    throws IOException
  {
    return loadEmitter(new FileInputStream(ref), null);
  }
  









  public static ConfigurableEmitter loadEmitter(InputStream ref)
    throws IOException
  {
    return loadEmitter(ref, null);
  }
  












  public static ConfigurableEmitter loadEmitter(String ref, ConfigurableEmitterFactory factory)
    throws IOException
  {
    return loadEmitter(ResourceLoader.getResourceAsStream(ref), factory);
  }
  











  public static ConfigurableEmitter loadEmitter(File ref, ConfigurableEmitterFactory factory)
    throws IOException
  {
    return loadEmitter(new FileInputStream(ref), factory);
  }
  












  public static ConfigurableEmitter loadEmitter(InputStream ref, ConfigurableEmitterFactory factory)
    throws IOException
  {
    if (factory == null) {
      factory = new ConfigurableEmitterFactory() {
        public ConfigurableEmitter createEmitter(String name) {
          return new ConfigurableEmitter(name);
        }
      };
    }
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder();
      Document document = builder.parse(ref);
      
      if (!document.getDocumentElement().getNodeName().equals("emitter")) {
        throw new IOException("Not a particle emitter file");
      }
      
      ConfigurableEmitter emitter = factory.createEmitter("new");
      elementToEmitter(document.getDocumentElement(), emitter);
      
      return emitter;
    } catch (IOException e) {
      Log.error(e);
      throw e;
    } catch (Exception e) {
      Log.error(e);
      throw new IOException("Unable to load emitter");
    }
  }
  









  public static void saveEmitter(File file, ConfigurableEmitter emitter)
    throws IOException
  {
    saveEmitter(new FileOutputStream(file), emitter);
  }
  








  public static void saveEmitter(OutputStream out, ConfigurableEmitter emitter)
    throws IOException
  {
    try
    {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder();
      Document document = builder.newDocument();
      
      document.appendChild(emitterToElement(document, emitter));
      Result result = new StreamResult(new OutputStreamWriter(out, 
        "utf-8"));
      DOMSource source = new DOMSource(document);
      
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer xformer = factory.newTransformer();
      xformer.setOutputProperty("indent", "yes");
      
      xformer.transform(source, result);
    } catch (Exception e) {
      Log.error(e);
      throw new IOException("Failed to save emitter");
    }
  }
  








  private static Element getFirstNamedElement(Element element, String name)
  {
    NodeList list = element.getElementsByTagName(name);
    if (list.getLength() == 0) {
      return null;
    }
    
    return (Element)list.item(0);
  }
  








  private static void elementToEmitter(Element element, ConfigurableEmitter emitter)
  {
    name = element.getAttribute("name");
    emitter.setImageName(element.getAttribute("imageName"));
    
    String renderType = element.getAttribute("renderType");
    usePoints = 1;
    if (renderType.equals("quads")) {
      usePoints = 3;
    }
    if (renderType.equals("points")) {
      usePoints = 2;
    }
    
    String useOriented = element.getAttribute("useOriented");
    if (useOriented != null) {
      useOriented = "true".equals(useOriented);
    }
    String useAdditive = element.getAttribute("useAdditive");
    if (useAdditive != null) {
      useAdditive = "true".equals(useAdditive);
    }
    parseRangeElement(getFirstNamedElement(element, "spawnInterval"), 
      spawnInterval);
    parseRangeElement(getFirstNamedElement(element, "spawnCount"), 
      spawnCount);
    parseRangeElement(getFirstNamedElement(element, "initialLife"), 
      initialLife);
    parseRangeElement(getFirstNamedElement(element, "initialSize"), 
      initialSize);
    parseRangeElement(getFirstNamedElement(element, "xOffset"), 
      xOffset);
    parseRangeElement(getFirstNamedElement(element, "yOffset"), 
      yOffset);
    parseRangeElement(getFirstNamedElement(element, "initialDistance"), 
      initialDistance);
    parseRangeElement(getFirstNamedElement(element, "speed"), speed);
    parseRangeElement(getFirstNamedElement(element, "length"), 
      length);
    parseRangeElement(getFirstNamedElement(element, "emitCount"), 
      emitCount);
    
    parseValueElement(getFirstNamedElement(element, "spread"), 
      spread);
    parseValueElement(getFirstNamedElement(element, "angularOffset"), 
      angularOffset);
    parseValueElement(getFirstNamedElement(element, "growthFactor"), 
      growthFactor);
    parseValueElement(getFirstNamedElement(element, "gravityFactor"), 
      gravityFactor);
    parseValueElement(getFirstNamedElement(element, "windFactor"), 
      windFactor);
    parseValueElement(getFirstNamedElement(element, "startAlpha"), 
      startAlpha);
    parseValueElement(getFirstNamedElement(element, "endAlpha"), 
      endAlpha);
    parseValueElement(getFirstNamedElement(element, "alpha"), alpha);
    parseValueElement(getFirstNamedElement(element, "size"), size);
    parseValueElement(getFirstNamedElement(element, "velocity"), 
      velocity);
    parseValueElement(getFirstNamedElement(element, "scaleY"), 
      scaleY);
    
    Element color = getFirstNamedElement(element, "color");
    NodeList steps = color.getElementsByTagName("step");
    colors.clear();
    for (int i = 0; i < steps.getLength(); i++) {
      Element step = (Element)steps.item(i);
      float offset = Float.parseFloat(step.getAttribute("offset"));
      float r = Float.parseFloat(step.getAttribute("r"));
      float g = Float.parseFloat(step.getAttribute("g"));
      float b = Float.parseFloat(step.getAttribute("b"));
      
      emitter.addColorPoint(offset, new Color(r, g, b, 1.0F));
    }
    

    emitter.replay();
  }
  









  private static Element emitterToElement(Document document, ConfigurableEmitter emitter)
  {
    Element root = document.createElement("emitter");
    root.setAttribute("name", name);
    root.setAttribute("imageName", imageName == null ? "" : 
      imageName);
    root
      .setAttribute("useOriented", useOriented ? "true" : 
      "false");
    root
      .setAttribute("useAdditive", useAdditive ? "true" : 
      "false");
    
    if (usePoints == 1) {
      root.setAttribute("renderType", "inherit");
    }
    if (usePoints == 2) {
      root.setAttribute("renderType", "points");
    }
    if (usePoints == 3) {
      root.setAttribute("renderType", "quads");
    }
    
    root.appendChild(createRangeElement(document, "spawnInterval", 
      spawnInterval));
    root.appendChild(createRangeElement(document, "spawnCount", 
      spawnCount));
    root.appendChild(createRangeElement(document, "initialLife", 
      initialLife));
    root.appendChild(createRangeElement(document, "initialSize", 
      initialSize));
    root.appendChild(createRangeElement(document, "xOffset", 
      xOffset));
    root.appendChild(createRangeElement(document, "yOffset", 
      yOffset));
    root.appendChild(createRangeElement(document, "initialDistance", 
      initialDistance));
    root.appendChild(createRangeElement(document, "speed", speed));
    root
      .appendChild(createRangeElement(document, "length", 
      length));
    root.appendChild(createRangeElement(document, "emitCount", 
      emitCount));
    
    root
      .appendChild(createValueElement(document, "spread", 
      spread));
    root.appendChild(createValueElement(document, "angularOffset", 
      angularOffset));
    root.appendChild(createValueElement(document, "growthFactor", 
      growthFactor));
    root.appendChild(createValueElement(document, "gravityFactor", 
      gravityFactor));
    root.appendChild(createValueElement(document, "windFactor", 
      windFactor));
    root.appendChild(createValueElement(document, "startAlpha", 
      startAlpha));
    root.appendChild(createValueElement(document, "endAlpha", 
      endAlpha));
    root.appendChild(createValueElement(document, "alpha", alpha));
    root.appendChild(createValueElement(document, "size", size));
    root.appendChild(createValueElement(document, "velocity", 
      velocity));
    root
      .appendChild(createValueElement(document, "scaleY", 
      scaleY));
    
    Element color = document.createElement("color");
    ArrayList list = colors;
    for (int i = 0; i < list.size(); i++) {
      ConfigurableEmitter.ColorRecord record = (ConfigurableEmitter.ColorRecord)list.get(i);
      Element step = document.createElement("step");
      step.setAttribute("offset", pos);
      step.setAttribute("r", col.r);
      step.setAttribute("g", col.g);
      step.setAttribute("b", col.b);
      
      color.appendChild(step);
    }
    
    root.appendChild(color);
    
    return root;
  }
  











  private static Element createRangeElement(Document document, String name, ConfigurableEmitter.Range range)
  {
    Element element = document.createElement(name);
    element.setAttribute("min", range.getMin());
    element.setAttribute("max", range.getMax());
    element.setAttribute("enabled", range.isEnabled());
    
    return element;
  }
  











  private static Element createValueElement(Document document, String name, ConfigurableEmitter.Value value)
  {
    Element element = document.createElement(name);
    

    if ((value instanceof ConfigurableEmitter.SimpleValue)) {
      element.setAttribute("type", "simple");
      element.setAttribute("value", value.getValue(0.0F));
    } else if ((value instanceof ConfigurableEmitter.RandomValue)) {
      element.setAttribute("type", "random");
      element
        .setAttribute("value", 
        ((ConfigurableEmitter.RandomValue)value).getValue());
    } else if ((value instanceof ConfigurableEmitter.LinearInterpolator)) {
      element.setAttribute("type", "linear");
      element.setAttribute("min", 
        ((ConfigurableEmitter.LinearInterpolator)value).getMin());
      element.setAttribute("max", 
        ((ConfigurableEmitter.LinearInterpolator)value).getMax());
      element.setAttribute("active", 
        ((ConfigurableEmitter.LinearInterpolator)value).isActive());
      
      ArrayList curve = ((ConfigurableEmitter.LinearInterpolator)value).getCurve();
      for (int i = 0; i < curve.size(); i++) {
        Vector2f point = (Vector2f)curve.get(i);
        
        Element pointElement = document.createElement("point");
        pointElement.setAttribute("x", x);
        pointElement.setAttribute("y", y);
        
        element.appendChild(pointElement);
      }
    } else {
      Log.warn("unkown value type ignored: " + value.getClass());
    }
    
    return element;
  }
  








  private static void parseRangeElement(Element element, ConfigurableEmitter.Range range)
  {
    if (element == null) {
      return;
    }
    range.setMin(Float.parseFloat(element.getAttribute("min")));
    range.setMax(Float.parseFloat(element.getAttribute("max")));
    range.setEnabled("true".equals(element.getAttribute("enabled")));
  }
  








  private static void parseValueElement(Element element, ConfigurableEmitter.Value value)
  {
    if (element == null) {
      return;
    }
    
    String type = element.getAttribute("type");
    String v = element.getAttribute("value");
    
    if ((type == null) || (type.length() == 0))
    {
      if ((value instanceof ConfigurableEmitter.SimpleValue)) {
        ((ConfigurableEmitter.SimpleValue)value).setValue(Float.parseFloat(v));
      } else if ((value instanceof ConfigurableEmitter.RandomValue)) {
        ((ConfigurableEmitter.RandomValue)value).setValue(Float.parseFloat(v));
      } else {
        Log.warn("problems reading element, skipping: " + element);
      }
      
    }
    else if (type.equals("simple")) {
      ((ConfigurableEmitter.SimpleValue)value).setValue(Float.parseFloat(v));
    } else if (type.equals("random")) {
      ((ConfigurableEmitter.RandomValue)value).setValue(Float.parseFloat(v));
    } else if (type.equals("linear")) {
      String min = element.getAttribute("min");
      String max = element.getAttribute("max");
      String active = element.getAttribute("active");
      
      NodeList points = element.getElementsByTagName("point");
      
      ArrayList curve = new ArrayList();
      for (int i = 0; i < points.getLength(); i++) {
        Element point = (Element)points.item(i);
        
        float x = Float.parseFloat(point.getAttribute("x"));
        float y = Float.parseFloat(point.getAttribute("y"));
        
        curve.add(new Vector2f(x, y));
      }
      
      ((ConfigurableEmitter.LinearInterpolator)value).setCurve(curve);
      ((ConfigurableEmitter.LinearInterpolator)value).setMin(Integer.parseInt(min));
      ((ConfigurableEmitter.LinearInterpolator)value).setMax(Integer.parseInt(max));
      ((ConfigurableEmitter.LinearInterpolator)value).setActive("true".equals(active));
    } else {
      Log.warn("unkown type detected: " + type);
    }
  }
}
