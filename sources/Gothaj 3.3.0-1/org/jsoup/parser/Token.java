package org.jsoup.parser;

import javax.annotation.Nullable;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;

abstract class Token {
   Token.TokenType type;
   static final int Unset = -1;
   private int startPos;
   private int endPos = -1;

   private Token() {
   }

   String tokenType() {
      return this.getClass().getSimpleName();
   }

   Token reset() {
      this.startPos = -1;
      this.endPos = -1;
      return this;
   }

   int startPos() {
      return this.startPos;
   }

   void startPos(int pos) {
      this.startPos = pos;
   }

   int endPos() {
      return this.endPos;
   }

   void endPos(int pos) {
      this.endPos = pos;
   }

   static void reset(StringBuilder sb) {
      if (sb != null) {
         sb.delete(0, sb.length());
      }
   }

   final boolean isDoctype() {
      return this.type == Token.TokenType.Doctype;
   }

   final Token.Doctype asDoctype() {
      return (Token.Doctype)this;
   }

   final boolean isStartTag() {
      return this.type == Token.TokenType.StartTag;
   }

   final Token.StartTag asStartTag() {
      return (Token.StartTag)this;
   }

   final boolean isEndTag() {
      return this.type == Token.TokenType.EndTag;
   }

   final Token.EndTag asEndTag() {
      return (Token.EndTag)this;
   }

   final boolean isComment() {
      return this.type == Token.TokenType.Comment;
   }

   final Token.Comment asComment() {
      return (Token.Comment)this;
   }

   final boolean isCharacter() {
      return this.type == Token.TokenType.Character;
   }

   final boolean isCData() {
      return this instanceof Token.CData;
   }

   final Token.Character asCharacter() {
      return (Token.Character)this;
   }

   final boolean isEOF() {
      return this.type == Token.TokenType.EOF;
   }

   static final class CData extends Token.Character {
      CData(String data) {
         this.data(data);
      }

      @Override
      public String toString() {
         return "<![CDATA[" + this.getData() + "]]>";
      }
   }

   static class Character extends Token {
      private String data;

      Character() {
         this.type = Token.TokenType.Character;
      }

      @Override
      Token reset() {
         super.reset();
         this.data = null;
         return this;
      }

      Token.Character data(String data) {
         this.data = data;
         return this;
      }

      String getData() {
         return this.data;
      }

      @Override
      public String toString() {
         return this.getData();
      }
   }

   static final class Comment extends Token {
      private final StringBuilder data = new StringBuilder();
      private String dataS;
      boolean bogus = false;

      @Override
      Token reset() {
         super.reset();
         reset(this.data);
         this.dataS = null;
         this.bogus = false;
         return this;
      }

      Comment() {
         this.type = Token.TokenType.Comment;
      }

      String getData() {
         return this.dataS != null ? this.dataS : this.data.toString();
      }

      final Token.Comment append(String append) {
         this.ensureData();
         if (this.data.length() == 0) {
            this.dataS = append;
         } else {
            this.data.append(append);
         }

         return this;
      }

      final Token.Comment append(char append) {
         this.ensureData();
         this.data.append(append);
         return this;
      }

      private void ensureData() {
         if (this.dataS != null) {
            this.data.append(this.dataS);
            this.dataS = null;
         }
      }

      @Override
      public String toString() {
         return "<!--" + this.getData() + "-->";
      }
   }

   static final class Doctype extends Token {
      final StringBuilder name = new StringBuilder();
      String pubSysKey = null;
      final StringBuilder publicIdentifier = new StringBuilder();
      final StringBuilder systemIdentifier = new StringBuilder();
      boolean forceQuirks = false;

      Doctype() {
         this.type = Token.TokenType.Doctype;
      }

      @Override
      Token reset() {
         super.reset();
         reset(this.name);
         this.pubSysKey = null;
         reset(this.publicIdentifier);
         reset(this.systemIdentifier);
         this.forceQuirks = false;
         return this;
      }

      String getName() {
         return this.name.toString();
      }

      String getPubSysKey() {
         return this.pubSysKey;
      }

      String getPublicIdentifier() {
         return this.publicIdentifier.toString();
      }

      public String getSystemIdentifier() {
         return this.systemIdentifier.toString();
      }

      public boolean isForceQuirks() {
         return this.forceQuirks;
      }

      @Override
      public String toString() {
         return "<!doctype " + this.getName() + ">";
      }
   }

   static final class EOF extends Token {
      EOF() {
         this.type = Token.TokenType.EOF;
      }

      @Override
      Token reset() {
         super.reset();
         return this;
      }

      @Override
      public String toString() {
         return "";
      }
   }

   static final class EndTag extends Token.Tag {
      EndTag() {
         this.type = Token.TokenType.EndTag;
      }

      @Override
      public String toString() {
         return "</" + this.toStringName() + ">";
      }
   }

   static final class StartTag extends Token.Tag {
      StartTag() {
         this.type = Token.TokenType.StartTag;
      }

      @Override
      Token.Tag reset() {
         super.reset();
         this.attributes = null;
         return this;
      }

      Token.StartTag nameAttr(String name, Attributes attributes) {
         this.tagName = name;
         this.attributes = attributes;
         this.normalName = ParseSettings.normalName(this.tagName);
         return this;
      }

      @Override
      public String toString() {
         return this.hasAttributes() && this.attributes.size() > 0
            ? "<" + this.toStringName() + " " + this.attributes.toString() + ">"
            : "<" + this.toStringName() + ">";
      }
   }

   abstract static class Tag extends Token {
      @Nullable
      protected String tagName;
      @Nullable
      protected String normalName;
      private final StringBuilder attrName = new StringBuilder();
      @Nullable
      private String attrNameS;
      private boolean hasAttrName = false;
      private final StringBuilder attrValue = new StringBuilder();
      @Nullable
      private String attrValueS;
      private boolean hasAttrValue = false;
      private boolean hasEmptyAttrValue = false;
      boolean selfClosing = false;
      @Nullable
      Attributes attributes;
      private static final int MaxAttributes = 512;

      Token.Tag reset() {
         super.reset();
         this.tagName = null;
         this.normalName = null;
         reset(this.attrName);
         this.attrNameS = null;
         this.hasAttrName = false;
         reset(this.attrValue);
         this.attrValueS = null;
         this.hasEmptyAttrValue = false;
         this.hasAttrValue = false;
         this.selfClosing = false;
         this.attributes = null;
         return this;
      }

      final void newAttribute() {
         if (this.attributes == null) {
            this.attributes = new Attributes();
         }

         if (this.hasAttrName && this.attributes.size() < 512) {
            String name = this.attrName.length() > 0 ? this.attrName.toString() : this.attrNameS;
            name = name.trim();
            if (name.length() > 0) {
               String value;
               if (this.hasAttrValue) {
                  value = this.attrValue.length() > 0 ? this.attrValue.toString() : this.attrValueS;
               } else if (this.hasEmptyAttrValue) {
                  value = "";
               } else {
                  value = null;
               }

               this.attributes.add(name, value);
            }
         }

         reset(this.attrName);
         this.attrNameS = null;
         this.hasAttrName = false;
         reset(this.attrValue);
         this.attrValueS = null;
         this.hasAttrValue = false;
         this.hasEmptyAttrValue = false;
      }

      final boolean hasAttributes() {
         return this.attributes != null;
      }

      final boolean hasAttribute(String key) {
         return this.attributes != null && this.attributes.hasKey(key);
      }

      final void finaliseTag() {
         if (this.hasAttrName) {
            this.newAttribute();
         }
      }

      final String name() {
         Validate.isFalse(this.tagName == null || this.tagName.length() == 0);
         return this.tagName;
      }

      final String normalName() {
         return this.normalName;
      }

      final String toStringName() {
         return this.tagName != null ? this.tagName : "[unset]";
      }

      final Token.Tag name(String name) {
         this.tagName = name;
         this.normalName = ParseSettings.normalName(this.tagName);
         return this;
      }

      final boolean isSelfClosing() {
         return this.selfClosing;
      }

      final void appendTagName(String append) {
         append = append.replace('\u0000', '�');
         this.tagName = this.tagName == null ? append : this.tagName.concat(append);
         this.normalName = ParseSettings.normalName(this.tagName);
      }

      final void appendTagName(char append) {
         this.appendTagName(String.valueOf(append));
      }

      final void appendAttributeName(String append) {
         append = append.replace('\u0000', '�');
         this.ensureAttrName();
         if (this.attrName.length() == 0) {
            this.attrNameS = append;
         } else {
            this.attrName.append(append);
         }
      }

      final void appendAttributeName(char append) {
         this.ensureAttrName();
         this.attrName.append(append);
      }

      final void appendAttributeValue(String append) {
         this.ensureAttrValue();
         if (this.attrValue.length() == 0) {
            this.attrValueS = append;
         } else {
            this.attrValue.append(append);
         }
      }

      final void appendAttributeValue(char append) {
         this.ensureAttrValue();
         this.attrValue.append(append);
      }

      final void appendAttributeValue(char[] append) {
         this.ensureAttrValue();
         this.attrValue.append(append);
      }

      final void appendAttributeValue(int[] appendCodepoints) {
         this.ensureAttrValue();

         for (int codepoint : appendCodepoints) {
            this.attrValue.appendCodePoint(codepoint);
         }
      }

      final void setEmptyAttributeValue() {
         this.hasEmptyAttrValue = true;
      }

      private void ensureAttrName() {
         this.hasAttrName = true;
         if (this.attrNameS != null) {
            this.attrName.append(this.attrNameS);
            this.attrNameS = null;
         }
      }

      private void ensureAttrValue() {
         this.hasAttrValue = true;
         if (this.attrValueS != null) {
            this.attrValue.append(this.attrValueS);
            this.attrValueS = null;
         }
      }

      @Override
      public abstract String toString();
   }

   public static enum TokenType {
      Doctype,
      StartTag,
      EndTag,
      Comment,
      Character,
      EOF;
   }
}
