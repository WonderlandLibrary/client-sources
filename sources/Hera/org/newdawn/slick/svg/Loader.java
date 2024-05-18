package org.newdawn.slick.svg;

import org.newdawn.slick.geom.Transform;
import org.w3c.dom.Element;

public interface Loader {
  void loadChildren(Element paramElement, Transform paramTransform) throws ParsingException;
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\Loader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */