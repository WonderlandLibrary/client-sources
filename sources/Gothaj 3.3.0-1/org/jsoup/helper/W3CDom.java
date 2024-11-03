package org.jsoup.helper;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class W3CDom {
   public static final String SourceProperty = "jsoupSource";
   private static final String ContextProperty = "jsoupContextSource";
   private static final String ContextNodeProperty = "jsoupContextNode";
   public static final String XPathFactoryProperty = "javax.xml.xpath.XPathFactory:jsoup";
   protected DocumentBuilderFactory factory;
   private boolean namespaceAware = true;

   public W3CDom() {
      this.factory = DocumentBuilderFactory.newInstance();
      this.factory.setNamespaceAware(true);
   }

   public boolean namespaceAware() {
      return this.namespaceAware;
   }

   public W3CDom namespaceAware(boolean namespaceAware) {
      this.namespaceAware = namespaceAware;
      this.factory.setNamespaceAware(namespaceAware);
      return this;
   }

   public static Document convert(org.jsoup.nodes.Document in) {
      return new W3CDom().fromJsoup(in);
   }

   public static String asString(Document doc, @Nullable Map<String, String> properties) {
      try {
         DOMSource domSource = new DOMSource(doc);
         StringWriter writer = new StringWriter();
         StreamResult result = new StreamResult(writer);
         TransformerFactory tf = TransformerFactory.newInstance();
         Transformer transformer = tf.newTransformer();
         if (properties != null) {
            transformer.setOutputProperties(propertiesFromMap(properties));
         }

         if (doc.getDoctype() != null) {
            DocumentType doctype = doc.getDoctype();
            if (!StringUtil.isBlank(doctype.getPublicId())) {
               transformer.setOutputProperty("doctype-public", doctype.getPublicId());
            }

            if (!StringUtil.isBlank(doctype.getSystemId())) {
               transformer.setOutputProperty("doctype-system", doctype.getSystemId());
            } else if (doctype.getName().equalsIgnoreCase("html") && StringUtil.isBlank(doctype.getPublicId()) && StringUtil.isBlank(doctype.getSystemId())) {
               transformer.setOutputProperty("doctype-system", "about:legacy-compat");
            }
         }

         transformer.transform(domSource, result);
         return writer.toString();
      } catch (TransformerException var8) {
         throw new IllegalStateException(var8);
      }
   }

   static Properties propertiesFromMap(Map<String, String> map) {
      Properties props = new Properties();
      props.putAll(map);
      return props;
   }

   public static HashMap<String, String> OutputHtml() {
      return methodMap("html");
   }

   public static HashMap<String, String> OutputXml() {
      return methodMap("xml");
   }

   private static HashMap<String, String> methodMap(String method) {
      HashMap<String, String> map = new HashMap<>();
      map.put("method", method);
      return map;
   }

   public Document fromJsoup(org.jsoup.nodes.Document in) {
      return this.fromJsoup((Element)in);
   }

   public Document fromJsoup(Element in) {
      Validate.notNull(in);

      try {
         DocumentBuilder builder = this.factory.newDocumentBuilder();
         DOMImplementation impl = builder.getDOMImplementation();
         Document out = builder.newDocument();
         org.jsoup.nodes.Document inDoc = in.ownerDocument();
         org.jsoup.nodes.DocumentType doctype = inDoc != null ? inDoc.documentType() : null;
         if (doctype != null) {
            DocumentType documentType = impl.createDocumentType(doctype.name(), doctype.publicId(), doctype.systemId());
            out.appendChild(documentType);
         }

         out.setXmlStandalone(true);
         Element context = in instanceof org.jsoup.nodes.Document ? in.child(0) : in;
         out.setUserData("jsoupContextSource", context, null);
         this.convert((Element)(inDoc != null ? inDoc : in), out);
         return out;
      } catch (ParserConfigurationException var8) {
         throw new IllegalStateException(var8);
      }
   }

   public void convert(org.jsoup.nodes.Document in, Document out) {
      this.convert((Element)in, out);
   }

   public void convert(Element in, Document out) {
      W3CDom.W3CBuilder builder = new W3CDom.W3CBuilder(out);
      builder.namespaceAware = this.namespaceAware;
      org.jsoup.nodes.Document inDoc = in.ownerDocument();
      if (inDoc != null) {
         if (!StringUtil.isBlank(inDoc.location())) {
            out.setDocumentURI(inDoc.location());
         }

         builder.syntax = inDoc.outputSettings().syntax();
      }

      Element rootEl = in instanceof org.jsoup.nodes.Document ? in.child(0) : in;
      NodeTraversor.traverse(builder, rootEl);
   }

   public NodeList selectXpath(String xpath, Document doc) {
      return this.selectXpath(xpath, (Node)doc);
   }

   public NodeList selectXpath(String xpath, Node contextNode) {
      Validate.notEmptyParam(xpath, "xpath");
      Validate.notNullParam(contextNode, "contextNode");

      try {
         String property = System.getProperty("javax.xml.xpath.XPathFactory:jsoup");
         XPathFactory xPathFactory = property != null ? XPathFactory.newInstance("jsoup") : XPathFactory.newInstance();
         XPathExpression expression = xPathFactory.newXPath().compile(xpath);
         NodeList nodeList = (NodeList)expression.evaluate(contextNode, XPathConstants.NODESET);
         Validate.notNull(nodeList);
         return nodeList;
      } catch (XPathFactoryConfigurationException | XPathExpressionException var7) {
         throw new Selector.SelectorParseException("Could not evaluate XPath query [%s]: %s", xpath, var7.getMessage());
      }
   }

   public <T extends org.jsoup.nodes.Node> List<T> sourceNodes(NodeList nodeList, Class<T> nodeType) {
      Validate.notNull(nodeList);
      Validate.notNull(nodeType);
      List<T> nodes = new ArrayList<>(nodeList.getLength());

      for (int i = 0; i < nodeList.getLength(); i++) {
         Node node = nodeList.item(i);
         Object source = node.getUserData("jsoupSource");
         if (nodeType.isInstance(source)) {
            nodes.add(nodeType.cast(source));
         }
      }

      return nodes;
   }

   public Node contextNode(Document wDoc) {
      return (Node)wDoc.getUserData("jsoupContextNode");
   }

   public String asString(Document doc) {
      return asString(doc, null);
   }

   protected static class W3CBuilder implements NodeVisitor {
      private static final String xmlnsKey = "xmlns";
      private static final String xmlnsPrefix = "xmlns:";
      private final Document doc;
      private boolean namespaceAware = true;
      private final Stack<HashMap<String, String>> namespacesStack = new Stack<>();
      private Node dest;
      private org.jsoup.nodes.Document.OutputSettings.Syntax syntax = org.jsoup.nodes.Document.OutputSettings.Syntax.xml;
      @Nullable
      private final Element contextElement;

      public W3CBuilder(Document doc) {
         this.doc = doc;
         this.namespacesStack.push(new HashMap<>());
         this.dest = doc;
         this.contextElement = (Element)doc.getUserData("jsoupContextSource");
      }

      @Override
      public void head(org.jsoup.nodes.Node source, int depth) {
         this.namespacesStack.push(new HashMap<>(this.namespacesStack.peek()));
         if (source instanceof Element) {
            Element sourceEl = (Element)source;
            String prefix = this.updateNamespaces(sourceEl);
            String namespace = this.namespaceAware ? this.namespacesStack.peek().get(prefix) : null;
            String tagName = sourceEl.tagName();

            try {
               org.w3c.dom.Element el = namespace == null && tagName.contains(":")
                  ? this.doc.createElementNS("", tagName)
                  : this.doc.createElementNS(namespace, tagName);
               this.copyAttributes(sourceEl, el);
               this.append(el, sourceEl);
               if (sourceEl == this.contextElement) {
                  this.doc.setUserData("jsoupContextNode", el, null);
               }

               this.dest = el;
            } catch (DOMException var8) {
               this.append(this.doc.createTextNode("<" + tagName + ">"), sourceEl);
            }
         } else if (source instanceof TextNode) {
            TextNode sourceText = (TextNode)source;
            Text text = this.doc.createTextNode(sourceText.getWholeText());
            this.append(text, sourceText);
         } else if (source instanceof Comment) {
            Comment sourceComment = (Comment)source;
            org.w3c.dom.Comment comment = this.doc.createComment(sourceComment.getData());
            this.append(comment, sourceComment);
         } else if (source instanceof DataNode) {
            DataNode sourceData = (DataNode)source;
            Text node = this.doc.createTextNode(sourceData.getWholeData());
            this.append(node, sourceData);
         }
      }

      private void append(Node append, org.jsoup.nodes.Node source) {
         append.setUserData("jsoupSource", source, null);
         this.dest.appendChild(append);
      }

      @Override
      public void tail(org.jsoup.nodes.Node source, int depth) {
         if (source instanceof Element && this.dest.getParentNode() instanceof org.w3c.dom.Element) {
            this.dest = this.dest.getParentNode();
         }

         this.namespacesStack.pop();
      }

      private void copyAttributes(org.jsoup.nodes.Node source, org.w3c.dom.Element el) {
         for (Attribute attribute : source.attributes()) {
            String key = Attribute.getValidKey(attribute.getKey(), this.syntax);
            if (key != null) {
               el.setAttribute(key, attribute.getValue());
            }
         }
      }

      private String updateNamespaces(Element el) {
         for (Attribute attr : el.attributes()) {
            String key = attr.getKey();
            String prefix;
            if (key.equals("xmlns")) {
               prefix = "";
            } else {
               if (!key.startsWith("xmlns:")) {
                  continue;
               }

               prefix = key.substring("xmlns:".length());
            }

            this.namespacesStack.peek().put(prefix, attr.getValue());
         }

         int pos = el.tagName().indexOf(58);
         return pos > 0 ? el.tagName().substring(0, pos) : "";
      }
   }
}
