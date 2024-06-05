package com.lukflug.panelstudio.theme;

public interface Theme {
   Renderer getPanelRenderer();

   Renderer getContainerRenderer();

   Renderer getComponentRenderer();
}
