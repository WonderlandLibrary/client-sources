package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import javax.xml.transform.Transformer;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.Writer;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStreamWriter;
import org.w3c.dom.Node;
import java.io.OutputStream;
import java.io.FileOutputStream;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

public class ParticleIO
{
    public static ParticleSystem HorizonCode_Horizon_È(final String ref, final Color mask) throws IOException {
        return HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), null, null, mask);
    }
    
    public static ParticleSystem HorizonCode_Horizon_È(final String ref) throws IOException {
        return HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), null, null, null);
    }
    
    public static ParticleSystem HorizonCode_Horizon_È(final File ref) throws IOException {
        return HorizonCode_Horizon_È(new FileInputStream(ref), null, null, null);
    }
    
    public static ParticleSystem HorizonCode_Horizon_È(final InputStream ref, final Color mask) throws IOException {
        return HorizonCode_Horizon_È(ref, null, null, mask);
    }
    
    public static ParticleSystem HorizonCode_Horizon_È(final InputStream ref) throws IOException {
        return HorizonCode_Horizon_È(ref, null, null, null);
    }
    
    public static ParticleSystem HorizonCode_Horizon_È(final String ref, final ConfigurableEmitterFactory factory) throws IOException {
        return HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), factory, null, null);
    }
    
    public static ParticleSystem HorizonCode_Horizon_È(final File ref, final ConfigurableEmitterFactory factory) throws IOException {
        return HorizonCode_Horizon_È(new FileInputStream(ref), factory, null, null);
    }
    
    public static ParticleSystem HorizonCode_Horizon_È(final InputStream ref, final ConfigurableEmitterFactory factory) throws IOException {
        return HorizonCode_Horizon_È(ref, factory, null, null);
    }
    
    public static ParticleSystem HorizonCode_Horizon_È(final InputStream ref, ConfigurableEmitterFactory factory, ParticleSystem system, final Color mask) throws IOException {
        if (factory == null) {
            factory = new ConfigurableEmitterFactory() {
                @Override
                public ConfigurableEmitter HorizonCode_Horizon_È(final String name) {
                    return new ConfigurableEmitter(name);
                }
            };
        }
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document document = builder.parse(ref);
            final Element element = document.getDocumentElement();
            if (!element.getNodeName().equals("system")) {
                throw new IOException("Not a particle system file");
            }
            if (system == null) {
                system = new ParticleSystem("org/newdawn/slick/data/particle.tga", 2000, mask);
            }
            final boolean additive = "true".equals(element.getAttribute("additive"));
            if (additive) {
                system.HorizonCode_Horizon_È(1);
            }
            else {
                system.HorizonCode_Horizon_È(2);
            }
            final boolean points = "true".equals(element.getAttribute("points"));
            system.Ý(points);
            final NodeList list = element.getElementsByTagName("emitter");
            for (int i = 0; i < list.getLength(); ++i) {
                final Element em = (Element)list.item(i);
                final ConfigurableEmitter emitter = factory.HorizonCode_Horizon_È("new");
                HorizonCode_Horizon_È(em, emitter);
                system.HorizonCode_Horizon_È(emitter);
            }
            system.Â(false);
            return system;
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È(e);
            throw e;
        }
        catch (Exception e2) {
            Log.HorizonCode_Horizon_È(e2);
            throw new IOException("Unable to load particle system config");
        }
    }
    
    public static void HorizonCode_Horizon_È(final File file, final ParticleSystem system) throws IOException {
        HorizonCode_Horizon_È(new FileOutputStream(file), system);
    }
    
    public static void HorizonCode_Horizon_È(final OutputStream out, final ParticleSystem system) throws IOException {
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document document = builder.newDocument();
            final Element root = document.createElement("system");
            root.setAttribute("additive", new StringBuilder().append(system.Ø­áŒŠá() == 1).toString());
            root.setAttribute("points", new StringBuilder().append(system.Ý()).toString());
            document.appendChild(root);
            for (int i = 0; i < system.Âµá€(); ++i) {
                final ParticleEmitter current = system.Â(i);
                if (!(current instanceof ConfigurableEmitter)) {
                    throw new RuntimeException("Only ConfigurableEmitter instances can be stored");
                }
                final Element element = HorizonCode_Horizon_È(document, (ConfigurableEmitter)current);
                root.appendChild(element);
            }
            final Result result = new StreamResult(new OutputStreamWriter(out, "utf-8"));
            final DOMSource source = new DOMSource(document);
            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer xformer = factory.newTransformer();
            xformer.setOutputProperty("indent", "yes");
            xformer.transform(source, result);
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new IOException("Unable to save configured particle system");
        }
    }
    
    public static ConfigurableEmitter Â(final String ref) throws IOException {
        return Â(ResourceLoader.HorizonCode_Horizon_È(ref), null);
    }
    
    public static ConfigurableEmitter Â(final File ref) throws IOException {
        return Â(new FileInputStream(ref), null);
    }
    
    public static ConfigurableEmitter Â(final InputStream ref) throws IOException {
        return Â(ref, null);
    }
    
    public static ConfigurableEmitter Â(final String ref, final ConfigurableEmitterFactory factory) throws IOException {
        return Â(ResourceLoader.HorizonCode_Horizon_È(ref), factory);
    }
    
    public static ConfigurableEmitter Â(final File ref, final ConfigurableEmitterFactory factory) throws IOException {
        return Â(new FileInputStream(ref), factory);
    }
    
    public static ConfigurableEmitter Â(final InputStream ref, ConfigurableEmitterFactory factory) throws IOException {
        if (factory == null) {
            factory = new ConfigurableEmitterFactory() {
                @Override
                public ConfigurableEmitter HorizonCode_Horizon_È(final String name) {
                    return new ConfigurableEmitter(name);
                }
            };
        }
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document document = builder.parse(ref);
            if (!document.getDocumentElement().getNodeName().equals("emitter")) {
                throw new IOException("Not a particle emitter file");
            }
            final ConfigurableEmitter emitter = factory.HorizonCode_Horizon_È("new");
            HorizonCode_Horizon_È(document.getDocumentElement(), emitter);
            return emitter;
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È(e);
            throw e;
        }
        catch (Exception e2) {
            Log.HorizonCode_Horizon_È(e2);
            throw new IOException("Unable to load emitter");
        }
    }
    
    public static void HorizonCode_Horizon_È(final File file, final ConfigurableEmitter emitter) throws IOException {
        HorizonCode_Horizon_È(new FileOutputStream(file), emitter);
    }
    
    public static void HorizonCode_Horizon_È(final OutputStream out, final ConfigurableEmitter emitter) throws IOException {
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document document = builder.newDocument();
            document.appendChild(HorizonCode_Horizon_È(document, emitter));
            final Result result = new StreamResult(new OutputStreamWriter(out, "utf-8"));
            final DOMSource source = new DOMSource(document);
            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer xformer = factory.newTransformer();
            xformer.setOutputProperty("indent", "yes");
            xformer.transform(source, result);
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new IOException("Failed to save emitter");
        }
    }
    
    private static Element HorizonCode_Horizon_È(final Element element, final String name) {
        final NodeList list = element.getElementsByTagName(name);
        if (list.getLength() == 0) {
            return null;
        }
        return (Element)list.item(0);
    }
    
    private static void HorizonCode_Horizon_È(final Element element, final ConfigurableEmitter emitter) {
        emitter.ŠÄ = element.getAttribute("name");
        emitter.Â(element.getAttribute("imageName"));
        final String renderType = element.getAttribute("renderType");
        emitter.Šáƒ = 1;
        if (renderType.equals("quads")) {
            emitter.Šáƒ = 3;
        }
        if (renderType.equals("points")) {
            emitter.Šáƒ = 2;
        }
        final String useOriented = element.getAttribute("useOriented");
        if (useOriented != null) {
            emitter.Ï­Ðƒà = "true".equals(useOriented);
        }
        final String useAdditive = element.getAttribute("useAdditive");
        if (useAdditive != null) {
            emitter.áŒŠà = "true".equals(useAdditive);
        }
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "spawnInterval"), emitter.HorizonCode_Horizon_È);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "spawnCount"), emitter.Â);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "initialLife"), emitter.Ý);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "initialSize"), emitter.Ø­áŒŠá);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "xOffset"), emitter.Âµá€);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "yOffset"), emitter.Ó);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "initialDistance"), emitter.áŒŠÆ);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "speed"), emitter.áˆºÑ¢Õ);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "length"), emitter.£á);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "emitCount"), emitter.Æ);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "spread"), emitter.à);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "angularOffset"), emitter.Ø);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "growthFactor"), emitter.ÂµÈ);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "gravityFactor"), emitter.á);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "windFactor"), emitter.ˆÏ­);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "startAlpha"), emitter.£à);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "endAlpha"), emitter.µà);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "alpha"), emitter.ˆà);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "size"), emitter.¥Æ);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "velocity"), emitter.Ø­à);
        HorizonCode_Horizon_È(HorizonCode_Horizon_È(element, "scaleY"), emitter.µÕ);
        final Element color = HorizonCode_Horizon_È(element, "color");
        final NodeList steps = color.getElementsByTagName("step");
        emitter.Å.clear();
        for (int i = 0; i < steps.getLength(); ++i) {
            final Element step = (Element)steps.item(i);
            final float offset = Float.parseFloat(step.getAttribute("offset"));
            final float r = Float.parseFloat(step.getAttribute("r"));
            final float g = Float.parseFloat(step.getAttribute("g"));
            final float b = Float.parseFloat(step.getAttribute("b"));
            emitter.HorizonCode_Horizon_È(offset, new Color(r, g, b, 1.0f));
        }
        emitter.Ó();
    }
    
    private static Element HorizonCode_Horizon_È(final Document document, final ConfigurableEmitter emitter) {
        final Element root = document.createElement("emitter");
        root.setAttribute("name", emitter.ŠÄ);
        root.setAttribute("imageName", (emitter.Ñ¢á == null) ? "" : emitter.Ñ¢á);
        root.setAttribute("useOriented", emitter.Ï­Ðƒà ? "true" : "false");
        root.setAttribute("useAdditive", emitter.áŒŠà ? "true" : "false");
        if (emitter.Šáƒ == 1) {
            root.setAttribute("renderType", "inherit");
        }
        if (emitter.Šáƒ == 2) {
            root.setAttribute("renderType", "points");
        }
        if (emitter.Šáƒ == 3) {
            root.setAttribute("renderType", "quads");
        }
        root.appendChild(HorizonCode_Horizon_È(document, "spawnInterval", emitter.HorizonCode_Horizon_È));
        root.appendChild(HorizonCode_Horizon_È(document, "spawnCount", emitter.Â));
        root.appendChild(HorizonCode_Horizon_È(document, "initialLife", emitter.Ý));
        root.appendChild(HorizonCode_Horizon_È(document, "initialSize", emitter.Ø­áŒŠá));
        root.appendChild(HorizonCode_Horizon_È(document, "xOffset", emitter.Âµá€));
        root.appendChild(HorizonCode_Horizon_È(document, "yOffset", emitter.Ó));
        root.appendChild(HorizonCode_Horizon_È(document, "initialDistance", emitter.áŒŠÆ));
        root.appendChild(HorizonCode_Horizon_È(document, "speed", emitter.áˆºÑ¢Õ));
        root.appendChild(HorizonCode_Horizon_È(document, "length", emitter.£á));
        root.appendChild(HorizonCode_Horizon_È(document, "emitCount", emitter.Æ));
        root.appendChild(HorizonCode_Horizon_È(document, "spread", emitter.à));
        root.appendChild(HorizonCode_Horizon_È(document, "angularOffset", emitter.Ø));
        root.appendChild(HorizonCode_Horizon_È(document, "growthFactor", emitter.ÂµÈ));
        root.appendChild(HorizonCode_Horizon_È(document, "gravityFactor", emitter.á));
        root.appendChild(HorizonCode_Horizon_È(document, "windFactor", emitter.ˆÏ­));
        root.appendChild(HorizonCode_Horizon_È(document, "startAlpha", emitter.£à));
        root.appendChild(HorizonCode_Horizon_È(document, "endAlpha", emitter.µà));
        root.appendChild(HorizonCode_Horizon_È(document, "alpha", emitter.ˆà));
        root.appendChild(HorizonCode_Horizon_È(document, "size", emitter.¥Æ));
        root.appendChild(HorizonCode_Horizon_È(document, "velocity", emitter.Ø­à));
        root.appendChild(HorizonCode_Horizon_È(document, "scaleY", emitter.µÕ));
        final Element color = document.createElement("color");
        final ArrayList list = emitter.Å;
        for (int i = 0; i < list.size(); ++i) {
            final ConfigurableEmitter.HorizonCode_Horizon_È record = list.get(i);
            final Element step = document.createElement("step");
            step.setAttribute("offset", new StringBuilder().append(record.HorizonCode_Horizon_È).toString());
            step.setAttribute("r", new StringBuilder().append(record.Â.£à).toString());
            step.setAttribute("g", new StringBuilder().append(record.Â.µà).toString());
            step.setAttribute("b", new StringBuilder().append(record.Â.ˆà).toString());
            color.appendChild(step);
        }
        root.appendChild(color);
        return root;
    }
    
    private static Element HorizonCode_Horizon_È(final Document document, final String name, final ConfigurableEmitter.Ø­áŒŠá range) {
        final Element element = document.createElement(name);
        element.setAttribute("min", new StringBuilder().append(range.Ø­áŒŠá()).toString());
        element.setAttribute("max", new StringBuilder().append(range.Ý()).toString());
        element.setAttribute("enabled", new StringBuilder().append(range.Â()).toString());
        return element;
    }
    
    private static Element HorizonCode_Horizon_È(final Document document, final String name, final ConfigurableEmitter.Ó value) {
        final Element element = document.createElement(name);
        if (value instanceof ConfigurableEmitter.Âµá€) {
            element.setAttribute("type", "simple");
            element.setAttribute("value", new StringBuilder().append(value.HorizonCode_Horizon_È(0.0f)).toString());
        }
        else if (value instanceof ConfigurableEmitter.Ý) {
            element.setAttribute("type", "random");
            element.setAttribute("value", new StringBuilder().append(((ConfigurableEmitter.Ý)value).HorizonCode_Horizon_È()).toString());
        }
        else if (value instanceof ConfigurableEmitter.Â) {
            element.setAttribute("type", "linear");
            element.setAttribute("min", new StringBuilder().append(((ConfigurableEmitter.Â)value).Ø­áŒŠá()).toString());
            element.setAttribute("max", new StringBuilder().append(((ConfigurableEmitter.Â)value).Ý()).toString());
            element.setAttribute("active", new StringBuilder().append(((ConfigurableEmitter.Â)value).Â()).toString());
            final ArrayList curve = ((ConfigurableEmitter.Â)value).HorizonCode_Horizon_È();
            for (int i = 0; i < curve.size(); ++i) {
                final Vector2f point = curve.get(i);
                final Element pointElement = document.createElement("point");
                pointElement.setAttribute("x", new StringBuilder().append(point.HorizonCode_Horizon_È).toString());
                pointElement.setAttribute("y", new StringBuilder().append(point.Â).toString());
                element.appendChild(pointElement);
            }
        }
        else {
            Log.Â("unkown value type ignored: " + value.getClass());
        }
        return element;
    }
    
    private static void HorizonCode_Horizon_È(final Element element, final ConfigurableEmitter.Ø­áŒŠá range) {
        if (element == null) {
            return;
        }
        range.Â(Float.parseFloat(element.getAttribute("min")));
        range.HorizonCode_Horizon_È(Float.parseFloat(element.getAttribute("max")));
        range.HorizonCode_Horizon_È("true".equals(element.getAttribute("enabled")));
    }
    
    private static void HorizonCode_Horizon_È(final Element element, final ConfigurableEmitter.Ó value) {
        if (element == null) {
            return;
        }
        final String type = element.getAttribute("type");
        final String v = element.getAttribute("value");
        if (type == null || type.length() == 0) {
            if (value instanceof ConfigurableEmitter.Âµá€) {
                ((ConfigurableEmitter.Âµá€)value).Â(Float.parseFloat(v));
            }
            else if (value instanceof ConfigurableEmitter.Ý) {
                ((ConfigurableEmitter.Ý)value).Â(Float.parseFloat(v));
            }
            else {
                Log.Â("problems reading element, skipping: " + element);
            }
        }
        else if (type.equals("simple")) {
            ((ConfigurableEmitter.Âµá€)value).Â(Float.parseFloat(v));
        }
        else if (type.equals("random")) {
            ((ConfigurableEmitter.Ý)value).Â(Float.parseFloat(v));
        }
        else if (type.equals("linear")) {
            final String min = element.getAttribute("min");
            final String max = element.getAttribute("max");
            final String active = element.getAttribute("active");
            final NodeList points = element.getElementsByTagName("point");
            final ArrayList curve = new ArrayList();
            for (int i = 0; i < points.getLength(); ++i) {
                final Element point = (Element)points.item(i);
                final float x = Float.parseFloat(point.getAttribute("x"));
                final float y = Float.parseFloat(point.getAttribute("y"));
                curve.add(new Vector2f(x, y));
            }
            ((ConfigurableEmitter.Â)value).HorizonCode_Horizon_È(curve);
            ((ConfigurableEmitter.Â)value).Â(Integer.parseInt(min));
            ((ConfigurableEmitter.Â)value).HorizonCode_Horizon_È(Integer.parseInt(max));
            ((ConfigurableEmitter.Â)value).HorizonCode_Horizon_È("true".equals(active));
        }
        else {
            Log.Â("unkown type detected: " + type);
        }
    }
}
