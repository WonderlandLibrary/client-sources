package com.lukflug.panelstudio;

import java.awt.Point;

public interface FixedComponent extends Component {
   void setPosition(Interface var1, Point var2);

   Point getPosition(Interface var1);

   void saveConfig(Interface var1, PanelConfig var2);

   void loadConfig(Interface var1, PanelConfig var2);

   int getWidth(Interface var1);
}
