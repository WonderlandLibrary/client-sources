/*     */ package org.newdawn.slick.util.xml;
/*     */ 
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
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
/*     */ public class XMLElement
/*     */ {
/*     */   private Element dom;
/*     */   private XMLElementList children;
/*     */   private String name;
/*     */   
/*     */   XMLElement(Element xmlElement) {
/*  29 */     this.dom = xmlElement;
/*  30 */     this.name = this.dom.getTagName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAttributeNames() {
/*  39 */     NamedNodeMap map = this.dom.getAttributes();
/*  40 */     String[] names = new String[map.getLength()];
/*     */     
/*  42 */     for (int i = 0; i < names.length; i++) {
/*  43 */       names[i] = map.item(i).getNodeName();
/*     */     }
/*     */     
/*  46 */     return names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  55 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String name) {
/*  65 */     return this.dom.getAttribute(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String name, String def) {
/*  76 */     String value = this.dom.getAttribute(name);
/*  77 */     if (value == null || value.length() == 0) {
/*  78 */       return def;
/*     */     }
/*     */     
/*  81 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIntAttribute(String name) throws SlickXMLException {
/*     */     try {
/*  93 */       return Integer.parseInt(getAttribute(name));
/*  94 */     } catch (NumberFormatException e) {
/*  95 */       throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not an integer", e);
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
/*     */   public int getIntAttribute(String name, int def) throws SlickXMLException {
/*     */     try {
/* 109 */       return Integer.parseInt(getAttribute(name, "" + def));
/* 110 */     } catch (NumberFormatException e) {
/* 111 */       throw new SlickXMLException("Value read: '" + getAttribute(name, "" + def) + "' is not an integer", e);
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
/*     */   public double getDoubleAttribute(String name) throws SlickXMLException {
/*     */     try {
/* 124 */       return Double.parseDouble(getAttribute(name));
/* 125 */     } catch (NumberFormatException e) {
/* 126 */       throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not a double", e);
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
/*     */   public double getDoubleAttribute(String name, double def) throws SlickXMLException {
/*     */     try {
/* 140 */       return Double.parseDouble(getAttribute(name, "" + def));
/* 141 */     } catch (NumberFormatException e) {
/* 142 */       throw new SlickXMLException("Value read: '" + getAttribute(name, "" + def) + "' is not a double", e);
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
/*     */   public boolean getBooleanAttribute(String name) throws SlickXMLException {
/* 154 */     String value = getAttribute(name);
/* 155 */     if (value.equalsIgnoreCase("true")) {
/* 156 */       return true;
/*     */     }
/* 158 */     if (value.equalsIgnoreCase("false")) {
/* 159 */       return false;
/*     */     }
/*     */     
/* 162 */     throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not a boolean");
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
/*     */   public boolean getBooleanAttribute(String name, boolean def) throws SlickXMLException {
/* 175 */     String value = getAttribute(name, "" + def);
/* 176 */     if (value.equalsIgnoreCase("true")) {
/* 177 */       return true;
/*     */     }
/* 179 */     if (value.equalsIgnoreCase("false")) {
/* 180 */       return false;
/*     */     }
/*     */     
/* 183 */     throw new SlickXMLException("Value read: '" + getAttribute(name, "" + def) + "' is not a boolean");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContent() {
/* 192 */     String content = "";
/*     */     
/* 194 */     NodeList list = this.dom.getChildNodes();
/* 195 */     for (int i = 0; i < list.getLength(); i++) {
/* 196 */       if (list.item(i) instanceof org.w3c.dom.Text) {
/* 197 */         content = content + list.item(i).getNodeValue();
/*     */       }
/*     */     } 
/*     */     
/* 201 */     return content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElementList getChildren() {
/* 210 */     if (this.children != null) {
/* 211 */       return this.children;
/*     */     }
/*     */     
/* 214 */     NodeList list = this.dom.getChildNodes();
/* 215 */     this.children = new XMLElementList();
/*     */     
/* 217 */     for (int i = 0; i < list.getLength(); i++) {
/* 218 */       if (list.item(i) instanceof Element) {
/* 219 */         this.children.add(new XMLElement((Element)list.item(i)));
/*     */       }
/*     */     } 
/*     */     
/* 223 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElementList getChildrenByName(String name) {
/* 233 */     XMLElementList selected = new XMLElementList();
/* 234 */     XMLElementList children = getChildren();
/*     */     
/* 236 */     for (int i = 0; i < children.size(); i++) {
/* 237 */       if (children.get(i).getName().equals(name)) {
/* 238 */         selected.add(children.get(i));
/*     */       }
/*     */     } 
/*     */     
/* 242 */     return selected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 249 */     String value = "[XML " + getName();
/* 250 */     String[] attrs = getAttributeNames();
/*     */     
/* 252 */     for (int i = 0; i < attrs.length; i++) {
/* 253 */       value = value + " " + attrs[i] + "=" + getAttribute(attrs[i]);
/*     */     }
/*     */     
/* 256 */     value = value + "]";
/*     */     
/* 258 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\xml\XMLElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */