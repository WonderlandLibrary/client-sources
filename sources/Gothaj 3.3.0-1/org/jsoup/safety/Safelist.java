package org.jsoup.safety;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

public class Safelist {
   private final Set<Safelist.TagName> tagNames = new HashSet<>();
   private final Map<Safelist.TagName, Set<Safelist.AttributeKey>> attributes = new HashMap<>();
   private final Map<Safelist.TagName, Map<Safelist.AttributeKey, Safelist.AttributeValue>> enforcedAttributes = new HashMap<>();
   private final Map<Safelist.TagName, Map<Safelist.AttributeKey, Set<Safelist.Protocol>>> protocols = new HashMap<>();
   private boolean preserveRelativeLinks = false;

   public static Safelist none() {
      return new Safelist();
   }

   public static Safelist simpleText() {
      return new Safelist().addTags("b", "em", "i", "strong", "u");
   }

   public static Safelist basic() {
      return new Safelist()
         .addTags(
            "a",
            "b",
            "blockquote",
            "br",
            "cite",
            "code",
            "dd",
            "dl",
            "dt",
            "em",
            "i",
            "li",
            "ol",
            "p",
            "pre",
            "q",
            "small",
            "span",
            "strike",
            "strong",
            "sub",
            "sup",
            "u",
            "ul"
         )
         .addAttributes("a", "href")
         .addAttributes("blockquote", "cite")
         .addAttributes("q", "cite")
         .addProtocols("a", "href", "ftp", "http", "https", "mailto")
         .addProtocols("blockquote", "cite", "http", "https")
         .addProtocols("cite", "cite", "http", "https")
         .addEnforcedAttribute("a", "rel", "nofollow");
   }

   public static Safelist basicWithImages() {
      return basic().addTags("img").addAttributes("img", "align", "alt", "height", "src", "title", "width").addProtocols("img", "src", "http", "https");
   }

   public static Safelist relaxed() {
      return new Safelist()
         .addTags(
            "a",
            "b",
            "blockquote",
            "br",
            "caption",
            "cite",
            "code",
            "col",
            "colgroup",
            "dd",
            "div",
            "dl",
            "dt",
            "em",
            "h1",
            "h2",
            "h3",
            "h4",
            "h5",
            "h6",
            "i",
            "img",
            "li",
            "ol",
            "p",
            "pre",
            "q",
            "small",
            "span",
            "strike",
            "strong",
            "sub",
            "sup",
            "table",
            "tbody",
            "td",
            "tfoot",
            "th",
            "thead",
            "tr",
            "u",
            "ul"
         )
         .addAttributes("a", "href", "title")
         .addAttributes("blockquote", "cite")
         .addAttributes("col", "span", "width")
         .addAttributes("colgroup", "span", "width")
         .addAttributes("img", "align", "alt", "height", "src", "title", "width")
         .addAttributes("ol", "start", "type")
         .addAttributes("q", "cite")
         .addAttributes("table", "summary", "width")
         .addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
         .addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width")
         .addAttributes("ul", "type")
         .addProtocols("a", "href", "ftp", "http", "https", "mailto")
         .addProtocols("blockquote", "cite", "http", "https")
         .addProtocols("cite", "cite", "http", "https")
         .addProtocols("img", "src", "http", "https")
         .addProtocols("q", "cite", "http", "https");
   }

   public Safelist() {
   }

   public Safelist(Safelist copy) {
      this();
      this.tagNames.addAll(copy.tagNames);

      for (Entry<Safelist.TagName, Set<Safelist.AttributeKey>> copyTagAttributes : copy.attributes.entrySet()) {
         this.attributes.put(copyTagAttributes.getKey(), new HashSet<>(copyTagAttributes.getValue()));
      }

      for (Entry<Safelist.TagName, Map<Safelist.AttributeKey, Safelist.AttributeValue>> enforcedEntry : copy.enforcedAttributes.entrySet()) {
         this.enforcedAttributes.put(enforcedEntry.getKey(), new HashMap<>(enforcedEntry.getValue()));
      }

      for (Entry<Safelist.TagName, Map<Safelist.AttributeKey, Set<Safelist.Protocol>>> protocolsEntry : copy.protocols.entrySet()) {
         Map<Safelist.AttributeKey, Set<Safelist.Protocol>> attributeProtocolsCopy = new HashMap<>();

         for (Entry<Safelist.AttributeKey, Set<Safelist.Protocol>> attributeProtocols : protocolsEntry.getValue().entrySet()) {
            attributeProtocolsCopy.put(attributeProtocols.getKey(), new HashSet<>(attributeProtocols.getValue()));
         }

         this.protocols.put(protocolsEntry.getKey(), attributeProtocolsCopy);
      }

      this.preserveRelativeLinks = copy.preserveRelativeLinks;
   }

   public Safelist addTags(String... tags) {
      Validate.notNull(tags);

      for (String tagName : tags) {
         Validate.notEmpty(tagName);
         this.tagNames.add(Safelist.TagName.valueOf(tagName));
      }

      return this;
   }

   public Safelist removeTags(String... tags) {
      Validate.notNull(tags);

      for (String tag : tags) {
         Validate.notEmpty(tag);
         Safelist.TagName tagName = Safelist.TagName.valueOf(tag);
         if (this.tagNames.remove(tagName)) {
            this.attributes.remove(tagName);
            this.enforcedAttributes.remove(tagName);
            this.protocols.remove(tagName);
         }
      }

      return this;
   }

   public Safelist addAttributes(String tag, String... attributes) {
      Validate.notEmpty(tag);
      Validate.notNull(attributes);
      Validate.isTrue(attributes.length > 0, "No attribute names supplied.");
      Safelist.TagName tagName = Safelist.TagName.valueOf(tag);
      this.tagNames.add(tagName);
      Set<Safelist.AttributeKey> attributeSet = new HashSet<>();

      for (String key : attributes) {
         Validate.notEmpty(key);
         attributeSet.add(Safelist.AttributeKey.valueOf(key));
      }

      if (this.attributes.containsKey(tagName)) {
         Set<Safelist.AttributeKey> currentSet = this.attributes.get(tagName);
         currentSet.addAll(attributeSet);
      } else {
         this.attributes.put(tagName, attributeSet);
      }

      return this;
   }

   public Safelist removeAttributes(String tag, String... attributes) {
      Validate.notEmpty(tag);
      Validate.notNull(attributes);
      Validate.isTrue(attributes.length > 0, "No attribute names supplied.");
      Safelist.TagName tagName = Safelist.TagName.valueOf(tag);
      Set<Safelist.AttributeKey> attributeSet = new HashSet<>();

      for (String key : attributes) {
         Validate.notEmpty(key);
         attributeSet.add(Safelist.AttributeKey.valueOf(key));
      }

      if (this.tagNames.contains(tagName) && this.attributes.containsKey(tagName)) {
         Set<Safelist.AttributeKey> currentSet = this.attributes.get(tagName);
         currentSet.removeAll(attributeSet);
         if (currentSet.isEmpty()) {
            this.attributes.remove(tagName);
         }
      }

      if (tag.equals(":all")) {
         for (Safelist.TagName name : this.attributes.keySet()) {
            Set<Safelist.AttributeKey> currentSet = this.attributes.get(name);
            currentSet.removeAll(attributeSet);
            if (currentSet.isEmpty()) {
               this.attributes.remove(name);
            }
         }
      }

      return this;
   }

   public Safelist addEnforcedAttribute(String tag, String attribute, String value) {
      Validate.notEmpty(tag);
      Validate.notEmpty(attribute);
      Validate.notEmpty(value);
      Safelist.TagName tagName = Safelist.TagName.valueOf(tag);
      this.tagNames.add(tagName);
      Safelist.AttributeKey attrKey = Safelist.AttributeKey.valueOf(attribute);
      Safelist.AttributeValue attrVal = Safelist.AttributeValue.valueOf(value);
      if (this.enforcedAttributes.containsKey(tagName)) {
         this.enforcedAttributes.get(tagName).put(attrKey, attrVal);
      } else {
         Map<Safelist.AttributeKey, Safelist.AttributeValue> attrMap = new HashMap<>();
         attrMap.put(attrKey, attrVal);
         this.enforcedAttributes.put(tagName, attrMap);
      }

      return this;
   }

   public Safelist removeEnforcedAttribute(String tag, String attribute) {
      Validate.notEmpty(tag);
      Validate.notEmpty(attribute);
      Safelist.TagName tagName = Safelist.TagName.valueOf(tag);
      if (this.tagNames.contains(tagName) && this.enforcedAttributes.containsKey(tagName)) {
         Safelist.AttributeKey attrKey = Safelist.AttributeKey.valueOf(attribute);
         Map<Safelist.AttributeKey, Safelist.AttributeValue> attrMap = this.enforcedAttributes.get(tagName);
         attrMap.remove(attrKey);
         if (attrMap.isEmpty()) {
            this.enforcedAttributes.remove(tagName);
         }
      }

      return this;
   }

   public Safelist preserveRelativeLinks(boolean preserve) {
      this.preserveRelativeLinks = preserve;
      return this;
   }

   public Safelist addProtocols(String tag, String attribute, String... protocols) {
      Validate.notEmpty(tag);
      Validate.notEmpty(attribute);
      Validate.notNull(protocols);
      Safelist.TagName tagName = Safelist.TagName.valueOf(tag);
      Safelist.AttributeKey attrKey = Safelist.AttributeKey.valueOf(attribute);
      Map<Safelist.AttributeKey, Set<Safelist.Protocol>> attrMap;
      if (this.protocols.containsKey(tagName)) {
         attrMap = this.protocols.get(tagName);
      } else {
         attrMap = new HashMap<>();
         this.protocols.put(tagName, attrMap);
      }

      Set<Safelist.Protocol> protSet;
      if (attrMap.containsKey(attrKey)) {
         protSet = attrMap.get(attrKey);
      } else {
         protSet = new HashSet<>();
         attrMap.put(attrKey, protSet);
      }

      for (String protocol : protocols) {
         Validate.notEmpty(protocol);
         Safelist.Protocol prot = Safelist.Protocol.valueOf(protocol);
         protSet.add(prot);
      }

      return this;
   }

   public Safelist removeProtocols(String tag, String attribute, String... removeProtocols) {
      Validate.notEmpty(tag);
      Validate.notEmpty(attribute);
      Validate.notNull(removeProtocols);
      Safelist.TagName tagName = Safelist.TagName.valueOf(tag);
      Safelist.AttributeKey attr = Safelist.AttributeKey.valueOf(attribute);
      Validate.isTrue(this.protocols.containsKey(tagName), "Cannot remove a protocol that is not set.");
      Map<Safelist.AttributeKey, Set<Safelist.Protocol>> tagProtocols = this.protocols.get(tagName);
      Validate.isTrue(tagProtocols.containsKey(attr), "Cannot remove a protocol that is not set.");
      Set<Safelist.Protocol> attrProtocols = tagProtocols.get(attr);

      for (String protocol : removeProtocols) {
         Validate.notEmpty(protocol);
         attrProtocols.remove(Safelist.Protocol.valueOf(protocol));
      }

      if (attrProtocols.isEmpty()) {
         tagProtocols.remove(attr);
         if (tagProtocols.isEmpty()) {
            this.protocols.remove(tagName);
         }
      }

      return this;
   }

   protected boolean isSafeTag(String tag) {
      return this.tagNames.contains(Safelist.TagName.valueOf(tag));
   }

   protected boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
      Safelist.TagName tag = Safelist.TagName.valueOf(tagName);
      Safelist.AttributeKey key = Safelist.AttributeKey.valueOf(attr.getKey());
      Set<Safelist.AttributeKey> okSet = this.attributes.get(tag);
      if (okSet == null || !okSet.contains(key)) {
         Map<Safelist.AttributeKey, Safelist.AttributeValue> enforcedSet = this.enforcedAttributes.get(tag);
         if (enforcedSet != null) {
            Attributes expect = this.getEnforcedAttributes(tagName);
            String attrKey = attr.getKey();
            if (expect.hasKeyIgnoreCase(attrKey)) {
               return expect.getIgnoreCase(attrKey).equals(attr.getValue());
            }
         }

         return !tagName.equals(":all") && this.isSafeAttribute(":all", el, attr);
      } else if (!this.protocols.containsKey(tag)) {
         return true;
      } else {
         Map<Safelist.AttributeKey, Set<Safelist.Protocol>> attrProts = this.protocols.get(tag);
         return !attrProts.containsKey(key) || this.testValidProtocol(el, attr, attrProts.get(key));
      }
   }

   private boolean testValidProtocol(Element el, Attribute attr, Set<Safelist.Protocol> protocols) {
      String value = el.absUrl(attr.getKey());
      if (value.length() == 0) {
         value = attr.getValue();
      }

      if (!this.preserveRelativeLinks) {
         attr.setValue(value);
      }

      for (Safelist.Protocol protocol : protocols) {
         String prot = protocol.toString();
         if (prot.equals("#")) {
            if (this.isValidAnchor(value)) {
               return true;
            }
         } else {
            prot = prot + ":";
            if (Normalizer.lowerCase(value).startsWith(prot)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isValidAnchor(String value) {
      return value.startsWith("#") && !value.matches(".*\\s.*");
   }

   Attributes getEnforcedAttributes(String tagName) {
      Attributes attrs = new Attributes();
      Safelist.TagName tag = Safelist.TagName.valueOf(tagName);
      if (this.enforcedAttributes.containsKey(tag)) {
         Map<Safelist.AttributeKey, Safelist.AttributeValue> keyVals = this.enforcedAttributes.get(tag);

         for (Entry<Safelist.AttributeKey, Safelist.AttributeValue> entry : keyVals.entrySet()) {
            attrs.put(entry.getKey().toString(), entry.getValue().toString());
         }
      }

      return attrs;
   }

   static class AttributeKey extends Safelist.TypedValue {
      AttributeKey(String value) {
         super(value);
      }

      static Safelist.AttributeKey valueOf(String value) {
         return new Safelist.AttributeKey(value);
      }
   }

   static class AttributeValue extends Safelist.TypedValue {
      AttributeValue(String value) {
         super(value);
      }

      static Safelist.AttributeValue valueOf(String value) {
         return new Safelist.AttributeValue(value);
      }
   }

   static class Protocol extends Safelist.TypedValue {
      Protocol(String value) {
         super(value);
      }

      static Safelist.Protocol valueOf(String value) {
         return new Safelist.Protocol(value);
      }
   }

   static class TagName extends Safelist.TypedValue {
      TagName(String value) {
         super(value);
      }

      static Safelist.TagName valueOf(String value) {
         return new Safelist.TagName(value);
      }
   }

   abstract static class TypedValue {
      private final String value;

      TypedValue(String value) {
         Validate.notNull(value);
         this.value = value;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         return 31 * result + (this.value == null ? 0 : this.value.hashCode());
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            Safelist.TypedValue other = (Safelist.TypedValue)obj;
            return this.value == null ? other.value == null : this.value.equals(other.value);
         }
      }

      @Override
      public String toString() {
         return this.value;
      }
   }
}
