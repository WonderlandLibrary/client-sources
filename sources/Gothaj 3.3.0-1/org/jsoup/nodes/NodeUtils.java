package org.jsoup.nodes;

import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.helper.W3CDom;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Parser;
import org.w3c.dom.NodeList;

final class NodeUtils {
   static Document.OutputSettings outputSettings(Node node) {
      Document owner = node.ownerDocument();
      return owner != null ? owner.outputSettings() : new Document("").outputSettings();
   }

   static Parser parser(Node node) {
      Document doc = node.ownerDocument();
      return doc != null && doc.parser() != null ? doc.parser() : new Parser(new HtmlTreeBuilder());
   }

   static <T extends Node> List<T> selectXpath(String xpath, Element el, Class<T> nodeType) {
      Validate.notEmpty(xpath);
      Validate.notNull(el);
      Validate.notNull(nodeType);
      W3CDom w3c = new W3CDom().namespaceAware(false);
      org.w3c.dom.Document wDoc = w3c.fromJsoup(el);
      org.w3c.dom.Node contextNode = w3c.contextNode(wDoc);
      NodeList nodeList = w3c.selectXpath(xpath, contextNode);
      return w3c.sourceNodes(nodeList, nodeType);
   }
}
