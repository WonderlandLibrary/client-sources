package org.newdawn.slick.util.xml;

import java.util.ArrayList;
import java.util.Collection;






public class XMLElementList
{
  private ArrayList list = new ArrayList();
  





  public XMLElementList() {}
  




  public void add(XMLElement element)
  {
    list.add(element);
  }
  




  public int size()
  {
    return list.size();
  }
  





  public XMLElement get(int i)
  {
    return (XMLElement)list.get(i);
  }
  





  public boolean contains(XMLElement element)
  {
    return list.contains(element);
  }
  




  public void addAllTo(Collection collection)
  {
    collection.addAll(list);
  }
}
