package in.momin5.cookieclient.client.gui.clickgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.*;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;

import java.awt.*;

public class NewTheme implements Theme {
    protected ColorScheme scheme;
    protected Renderer componentRenderer,containerRenderer,panelRenderer;
    protected DescriptionRenderer descriptionRenderer;

    public NewTheme (ColorScheme scheme, int height, int border) {
        this.scheme =scheme;
        panelRenderer =new ComponentRenderer(0,height,border);
        containerRenderer = new ComponentRenderer(1,height,border);
        componentRenderer = new ComponentRenderer(2,height,border);
    }

    @Override
    public Renderer getPanelRenderer() {
        return panelRenderer;
    }

    @Override
    public Renderer getContainerRenderer() {
        return containerRenderer;
    }

    @Override
    public Renderer getComponentRenderer() {
        return componentRenderer;
    }

    public DescriptionRenderer getDescription() {
        return descriptionRenderer;
    }


    protected class ComponentRenderer extends RendererBase {
        protected final int level,border;

        public ComponentRenderer (int level, int height, int border) {
            super(height + 2 * border,0,0,0,0);
            this.level=level;
            this.border=border;
        }

        @Override
        public void renderRect (Context context, String text, boolean focus, boolean active, Rectangle rectangle, boolean overlay) {
            Color color=getMainColor(focus,active);
            context.getInterface().fillRect(rectangle,color,color,color,color);
            if (overlay) {
                Color overlayColor;
                if (context.isHovered()) {
                    overlayColor=new Color(255,255,255,64);
                } else {
                    overlayColor=new Color(255,255,255,0);
                }
                context.getInterface().fillRect(context.getRect(),overlayColor,overlayColor,overlayColor,overlayColor);
            }
            Point stringPos=new Point(rectangle.getLocation());
            stringPos.translate(0,border);
            context.getInterface().drawString(stringPos,text,new CustomColor(255,255,255,255));
        }

        @Override
        public void renderTitle(Context context, String text, boolean focus, boolean active, boolean open) {
            super.renderTitle(context,text,focus,active,open);
            if (!active && level<2 || active && level>0) {
                Point p1, p2, p3;
                Color color = new Color(255,255,255,255);
                if (open) {
                    p3 = new Point(context.getPos().x + context.getSize().width - 2, context.getPos().y + context.getSize().height / 4);
                    p2 = new Point(context.getPos().x + context.getSize().width - context.getSize().height / 2, context.getPos().y + context.getSize().height * 3 / 4);
                    p1 = new Point(context.getPos().x + context.getSize().width - context.getSize().height + 2, context.getPos().y + context.getSize().height / 4);
                } else {
                    p3 = new Point(context.getPos().x + context.getSize().width - context.getSize().height * 3 / 4, context.getPos().y + 2);
                    p2 = new Point(context.getPos().x + context.getSize().width - context.getSize().height / 4, context.getPos().y + context.getSize().height / 2);
                    p1 = new Point(context.getPos().x + context.getSize().width - context.getSize().height * 3 / 4, context.getPos().y + context.getSize().height - 2);
                }
                //context.getInterface().fillTriangle(p1, p2, p3, color, color, color);
                context.getInterface().drawLine(p1,p2,color,color);
                context.getInterface().drawLine(p2,p3,color,color);
            }
        }

        @Override
        public void renderBackground (Context context, boolean focus) {
        }

        @Override
        public void renderBorder (Context context, boolean focus, boolean active, boolean open) {
        }


        @Override
        public Color getMainColor (boolean focus, boolean active) {
            Color color;
            // active modules

            if (active && level>0) color= //getColorScheme().getActiveColor();
                    new CustomColor(177,0,188, 137);
                // background
            else color=//getColorScheme().getBackgroundColor();
            new CustomColor(45,50,59,255);
            // inactive modules
            if (!active && level<2) color=//getColorScheme().getBackgroundColor();
                    new CustomColor(45,50,59,255);
            // category
            if (active && level<1) color= new Color(114, 100, 149,255);
            color=new Color(color.getRed(),color.getGreen(),color.getBlue(),255);
            return color;
        }

        @Override
        public Color getBackgroundColor (boolean focus) {
            return new Color(0,0,0,0);
        }

        @Override
        public ColorScheme getDefaultColorScheme() {
            return this.scheme;
        }
    }
}
