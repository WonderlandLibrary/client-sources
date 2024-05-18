package org.newdawn.slick.svg.inkscape;

import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.Loader;
import org.newdawn.slick.svg.ParsingException;
import org.w3c.dom.Element;

public abstract interface ElementProcessor
{
  public abstract void process(Loader paramLoader, Element paramElement, Diagram paramDiagram, Transform paramTransform)
    throws ParsingException;
  
  public abstract boolean handles(Element paramElement);
}
