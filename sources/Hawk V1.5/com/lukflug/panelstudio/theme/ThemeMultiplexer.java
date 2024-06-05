package com.lukflug.panelstudio.theme;

public abstract class ThemeMultiplexer implements Theme {
   protected final Renderer containerRenderer = new ThemeMultiplexer.ContainerRenderer(this);
   protected final Renderer panelRenderer = new ThemeMultiplexer.PanelRenderer(this);
   protected final Renderer componentRenderer = new ThemeMultiplexer.ComponentRenderer(this);

   public Renderer getPanelRenderer() {
      return this.panelRenderer;
   }

   public Renderer getComponentRenderer() {
      return this.componentRenderer;
   }

   public Renderer getContainerRenderer() {
      return this.containerRenderer;
   }

   protected abstract Theme getTheme();

   protected class ComponentRenderer extends RendererProxy {
      final ThemeMultiplexer this$0;

      protected ComponentRenderer(ThemeMultiplexer var1) {
         this.this$0 = var1;
      }

      protected Renderer getRenderer() {
         return this.this$0.getTheme().getComponentRenderer();
      }
   }

   protected class PanelRenderer extends RendererProxy {
      final ThemeMultiplexer this$0;

      protected Renderer getRenderer() {
         return this.this$0.getTheme().getPanelRenderer();
      }

      protected PanelRenderer(ThemeMultiplexer var1) {
         this.this$0 = var1;
      }
   }

   protected class ContainerRenderer extends RendererProxy {
      final ThemeMultiplexer this$0;

      protected ContainerRenderer(ThemeMultiplexer var1) {
         this.this$0 = var1;
      }

      protected Renderer getRenderer() {
         return this.this$0.getTheme().getContainerRenderer();
      }
   }
}
