package org.newdawn.slick.util.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;













public class XMLElement
{
  private Element dom;
  private XMLElementList children;
  private String name;
  
  XMLElement(Element xmlElement)
  {
    dom = xmlElement;
    name = dom.getTagName();
  }
  




  public String[] getAttributeNames()
  {
    NamedNodeMap map = dom.getAttributes();
    String[] names = new String[map.getLength()];
    
    for (int i = 0; i < names.length; i++) {
      names[i] = map.item(i).getNodeName();
    }
    
    return names;
  }
  




  public String getName()
  {
    return name;
  }
  





  public String getAttribute(String name)
  {
    return dom.getAttribute(name);
  }
  






  public String getAttribute(String name, String def)
  {
    String value = dom.getAttribute(name);
    if ((value == null) || (value.length() == 0)) {
      return def;
    }
    
    return value;
  }
  




  public int getIntAttribute(String name)
    throws SlickXMLException
  {
    try
    {
      return Integer.parseInt(getAttribute(name));
    } catch (NumberFormatException e) {
      throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not an integer", e);
    }
  }
  





  public int getIntAttribute(String name, int def)
    throws SlickXMLException
  {
    try
    {
      return Integer.parseInt(getAttribute(name, def));
    } catch (NumberFormatException e) {
      throw new SlickXMLException("Value read: '" + getAttribute(name, new StringBuilder().append(def).toString()) + "' is not an integer", e);
    }
  }
  




  public double getDoubleAttribute(String name)
    throws SlickXMLException
  {
    try
    {
      return Double.parseDouble(getAttribute(name));
    } catch (NumberFormatException e) {
      throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not a double", e);
    }
  }
  





  public double getDoubleAttribute(String name, double def)
    throws SlickXMLException
  {
    try
    {
      return Double.parseDouble(getAttribute(name, def));
    } catch (NumberFormatException e) {
      throw new SlickXMLException("Value read: '" + getAttribute(name, new StringBuilder().append(def).toString()) + "' is not a double", e);
    }
  }
  





  public boolean getBooleanAttribute(String name)
    throws SlickXMLException
  {
    String value = getAttribute(name);
    if (value.equalsIgnoreCase("true")) {
      return true;
    }
    if (value.equalsIgnoreCase("false")) {
      return false;
    }
    
    throw new SlickXMLException("Value read: '" + getAttribute(name) + "' is not a boolean");
  }
  







  public boolean getBooleanAttribute(String name, boolean def)
    throws SlickXMLException
  {
    String value = getAttribute(name, def);
    if (value.equalsIgnoreCase("true")) {
      return true;
    }
    if (value.equalsIgnoreCase("false")) {
      return false;
    }
    
    throw new SlickXMLException("Value read: '" + getAttribute(name, new StringBuilder().append(def).toString()) + "' is not a boolean");
  }
  




  public String getContent()
  {
    String content = "";
    
    NodeList list = dom.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      if ((list.item(i) instanceof Text)) {
        content = content + list.item(i).getNodeValue();
      }
    }
    
    return content;
  }
  




  public XMLElementList getChildren()
  {
    if (children != null) {
      return children;
    }
    
    NodeList list = dom.getChildNodes();
    children = new XMLElementList();
    
    for (int i = 0; i < list.getLength(); i++) {
      if ((list.item(i) instanceof Element)) {
        children.add(new XMLElement((Element)list.item(i)));
      }
    }
    
    return children;
  }
  





  public XMLElementList getChildrenByName(String name)
  {
    XMLElementList selected = new XMLElementList();
    XMLElementList children = getChildren();
    
    for (int i = 0; i < children.size(); i++) {
      if (children.get(i).getName().equals(name)) {
        selected.add(children.get(i));
      }
    }
    
    return selected;
  }
  


  public String toString()
  {
    String value = "[XML " + getName();
    String[] attrs = getAttributeNames();
    
    for (int i = 0; i < attrs.length; i++) {
      value = value + " " + attrs[i] + "=" + getAttribute(attrs[i]);
    }
    
    value = value + "]";
    
    return value;
  }
}
