/*   1:    */ package org.newdawn.slick.util.xml;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Element;
/*   4:    */ import org.w3c.dom.NamedNodeMap;
/*   5:    */ import org.w3c.dom.Node;
/*   6:    */ import org.w3c.dom.NodeList;
/*   7:    */ import org.w3c.dom.Text;
/*   8:    */ 
/*   9:    */ public class XMLElement
/*  10:    */ {
/*  11:    */   private Element dom;
/*  12:    */   private XMLElementList children;
/*  13:    */   private String name;
/*  14:    */   
/*  15:    */   XMLElement(Element xmlElement)
/*  16:    */   {
/*  17: 29 */     this.dom = xmlElement;
/*  18: 30 */     this.name = this.dom.getTagName();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String[] getAttributeNames()
/*  22:    */   {
/*  23: 39 */     NamedNodeMap map = this.dom.getAttributes();
/*  24: 40 */     String[] names = new String[map.getLength()];
/*  25: 42 */     for (int i = 0; i < names.length; i++) {
/*  26: 43 */       names[i] = map.item(i).getNodeName();
/*  27:    */     }
/*  28: 46 */     return names;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getName()
/*  32:    */   {
/*  33: 55 */     return this.name;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getAttribute(String name)
/*  37:    */   {
/*  38: 65 */     return this.dom.getAttribute(name);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getAttribute(String name, String def)
/*  42:    */   {
/*  43: 76 */     String value = this.dom.getAttribute(name);
/*  44: 77 */     if ((value == null) || (value.length() == 0)) {
/*  45: 78 */       return def;
/*  46:    */     }
/*  47: 81 */     return value;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIntAttribute(String name)
/*  51:    */     throws SlickXMLException
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55: 93 */       return Integer.parseInt(getAttribute(name));
/*  56:    */     }
/*  57:    */     catch (NumberFormatException e)
/*  58:    */     {
/*  59: 95 */       throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not an integer", e);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIntAttribute(String name, int def)
/*  64:    */     throws SlickXMLException
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:109 */       return Integer.parseInt(getAttribute(name, def));
/*  69:    */     }
/*  70:    */     catch (NumberFormatException e)
/*  71:    */     {
/*  72:111 */       throw new SlickXMLException("Value read: '" + getAttribute(name, new StringBuilder().append(def).toString()) + "' is not an integer", e);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public double getDoubleAttribute(String name)
/*  77:    */     throws SlickXMLException
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:124 */       return Double.parseDouble(getAttribute(name));
/*  82:    */     }
/*  83:    */     catch (NumberFormatException e)
/*  84:    */     {
/*  85:126 */       throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not a double", e);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public double getDoubleAttribute(String name, double def)
/*  90:    */     throws SlickXMLException
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:140 */       return Double.parseDouble(getAttribute(name, def));
/*  95:    */     }
/*  96:    */     catch (NumberFormatException e)
/*  97:    */     {
/*  98:142 */       throw new SlickXMLException("Value read: '" + getAttribute(name, new StringBuilder().append(def).toString()) + "' is not a double", e);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean getBooleanAttribute(String name)
/* 103:    */     throws SlickXMLException
/* 104:    */   {
/* 105:154 */     String value = getAttribute(name);
/* 106:155 */     if (value.equalsIgnoreCase("true")) {
/* 107:156 */       return true;
/* 108:    */     }
/* 109:158 */     if (value.equalsIgnoreCase("false")) {
/* 110:159 */       return false;
/* 111:    */     }
/* 112:162 */     throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not a boolean");
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean getBooleanAttribute(String name, boolean def)
/* 116:    */     throws SlickXMLException
/* 117:    */   {
/* 118:175 */     String value = getAttribute(name, def);
/* 119:176 */     if (value.equalsIgnoreCase("true")) {
/* 120:177 */       return true;
/* 121:    */     }
/* 122:179 */     if (value.equalsIgnoreCase("false")) {
/* 123:180 */       return false;
/* 124:    */     }
/* 125:183 */     throw new SlickXMLException("Value read: '" + getAttribute(name, new StringBuilder().append(def).toString()) + "' is not a boolean");
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getContent()
/* 129:    */   {
/* 130:192 */     String content = "";
/* 131:    */     
/* 132:194 */     NodeList list = this.dom.getChildNodes();
/* 133:195 */     for (int i = 0; i < list.getLength(); i++) {
/* 134:196 */       if ((list.item(i) instanceof Text)) {
/* 135:197 */         content = content + list.item(i).getNodeValue();
/* 136:    */       }
/* 137:    */     }
/* 138:201 */     return content;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public XMLElementList getChildren()
/* 142:    */   {
/* 143:210 */     if (this.children != null) {
/* 144:211 */       return this.children;
/* 145:    */     }
/* 146:214 */     NodeList list = this.dom.getChildNodes();
/* 147:215 */     this.children = new XMLElementList();
/* 148:217 */     for (int i = 0; i < list.getLength(); i++) {
/* 149:218 */       if ((list.item(i) instanceof Element)) {
/* 150:219 */         this.children.add(new XMLElement((Element)list.item(i)));
/* 151:    */       }
/* 152:    */     }
/* 153:223 */     return this.children;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public XMLElementList getChildrenByName(String name)
/* 157:    */   {
/* 158:233 */     XMLElementList selected = new XMLElementList();
/* 159:234 */     XMLElementList children = getChildren();
/* 160:236 */     for (int i = 0; i < children.size(); i++) {
/* 161:237 */       if (children.get(i).getName().equals(name)) {
/* 162:238 */         selected.add(children.get(i));
/* 163:    */       }
/* 164:    */     }
/* 165:242 */     return selected;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String toString()
/* 169:    */   {
/* 170:249 */     String value = "[XML " + getName();
/* 171:250 */     String[] attrs = getAttributeNames();
/* 172:252 */     for (int i = 0; i < attrs.length; i++) {
/* 173:253 */       value = value + " " + attrs[i] + "=" + getAttribute(attrs[i]);
/* 174:    */     }
/* 175:256 */     value = value + "]";
/* 176:    */     
/* 177:258 */     return value;
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.xml.XMLElement
 * JD-Core Version:    0.7.0.1
 */