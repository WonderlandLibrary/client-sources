package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.Toggleable;

public interface PanelManager {
   void showComponent(FixedComponent var1);

   void hideComponent(FixedComponent var1);

   Toggleable getComponentToggleable(FixedComponent var1);
}
