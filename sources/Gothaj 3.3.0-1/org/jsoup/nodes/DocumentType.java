package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;

public class DocumentType extends LeafNode {
   public static final String PUBLIC_KEY = "PUBLIC";
   public static final String SYSTEM_KEY = "SYSTEM";
   private static final String NAME = "name";
   private static final String PUB_SYS_KEY = "pubSysKey";
   private static final String PUBLIC_ID = "publicId";
   private static final String SYSTEM_ID = "systemId";

   public DocumentType(String name, String publicId, String systemId) {
      Validate.notNull(name);
      Validate.notNull(publicId);
      Validate.notNull(systemId);
      this.attr("name", name);
      this.attr("publicId", publicId);
      this.attr("systemId", systemId);
      this.updatePubSyskey();
   }

   public void setPubSysKey(String value) {
      if (value != null) {
         this.attr("pubSysKey", value);
      }
   }

   private void updatePubSyskey() {
      if (this.has("publicId")) {
         this.attr("pubSysKey", "PUBLIC");
      } else if (this.has("systemId")) {
         this.attr("pubSysKey", "SYSTEM");
      }
   }

   public String name() {
      return this.attr("name");
   }

   public String publicId() {
      return this.attr("publicId");
   }

   public String systemId() {
      return this.attr("systemId");
   }

   @Override
   public String nodeName() {
      return "#doctype";
   }

   @Override
   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
      if (this.siblingIndex > 0 && out.prettyPrint()) {
         accum.append('\n');
      }

      if (out.syntax() == Document.OutputSettings.Syntax.html && !this.has("publicId") && !this.has("systemId")) {
         accum.append("<!doctype");
      } else {
         accum.append("<!DOCTYPE");
      }

      if (this.has("name")) {
         accum.append(" ").append(this.attr("name"));
      }

      if (this.has("pubSysKey")) {
         accum.append(" ").append(this.attr("pubSysKey"));
      }

      if (this.has("publicId")) {
         accum.append(" \"").append(this.attr("publicId")).append('"');
      }

      if (this.has("systemId")) {
         accum.append(" \"").append(this.attr("systemId")).append('"');
      }

      accum.append('>');
   }

   @Override
   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
   }

   private boolean has(String attribute) {
      return !StringUtil.isBlank(this.attr(attribute));
   }
}
