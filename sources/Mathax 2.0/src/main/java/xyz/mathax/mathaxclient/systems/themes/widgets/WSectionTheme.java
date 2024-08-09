package xyz.mathax.mathaxclient.systems.themes.widgets;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WTriangle;
import xyz.mathax.mathaxclient.gui.widgets.containers.WSection;

public class WSectionTheme extends WSection {
    public WSectionTheme(String title, boolean expanded, WWidget headerWidget) {
        super(title, expanded, headerWidget);
    }

    @Override
    protected WHeader createHeader() {
        return new WMatHaxHeader(title);
    }

    protected class WMatHaxHeader extends WHeader {
        private WTriangle triangle;

        public WMatHaxHeader(String title) {
            super(title);
        }

        @Override
        public void init() {
            add(theme.horizontalSeparator(title)).expandX();

            if (headerWidget != null) add(headerWidget);

            triangle = new WHeaderTriangle();
            triangle.theme = theme;
            triangle.action = this::onClick;

            add(triangle);
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            triangle.rotation = (1 - animProgress) * -90;
        }
    }

    protected static class WHeaderTriangle extends WTriangle implements WidgetTheme {
        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            renderer.rotatedQuad(GuiRenderer.TRIANGLE, theme().textColorSetting.get(), x, y, width, height, rotation);
        }
    }
}
