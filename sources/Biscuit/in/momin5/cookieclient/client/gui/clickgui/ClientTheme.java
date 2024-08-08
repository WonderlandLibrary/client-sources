package in.momin5.cookieclient.client.gui.clickgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.*;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.client.modules.client.ClickGuiMod;

import java.awt.*;

public class ClientTheme implements Theme {
    protected ColorScheme scheme;
    protected Renderer componentRenderer,containerRenderer,panelRenderer;
    protected DescriptionRenderer descriptionRenderer;
    protected int image;

    public ClientTheme (ColorScheme scheme, int height, int border) {
        this.scheme=scheme;
        panelRenderer=new ComponentRenderer(0,height,border);
        containerRenderer=new ComponentRenderer(1,height,border);
        componentRenderer=new ComponentRenderer(2,height,border);
        //image =
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
            context.getInterface().drawString(stringPos,text,getFontColor(focus));
        }

        @Override
        public void renderTitle(Context context, String text, boolean focus, boolean active, boolean open) {
            super.renderTitle(context,text,focus,active,open);
            //int a = context.getInterface().loadImage("cookie.png");
            //System.out.println(a);
            if (!active && level<2 || active && level>0) {
                Point p1, p2, p3;
                Color color = new Color(255,255,255,255);
                    if (open) {
                        p3 = new Point(context.getPos().x + context.getSize().width - 2, context.getPos().y + context.getSize().height / 4); // point 1 the left top of triangle
                        p2 = new Point(context.getPos().x + context.getSize().width - context.getSize().height / 2, context.getPos().y + context.getSize().height * 3 / 4);
                        p1 = new Point(context.getPos().x + context.getSize().width - context.getSize().height + 2, context.getPos().y + context.getSize().height / 4); // the left bottom one
                    } else {
                        p3 = new Point(context.getPos().x + context.getSize().width - context.getSize().height * 3 / 4, context.getPos().y + 2);
                        p2 = new Point(context.getPos().x + context.getSize().width - context.getSize().height / 4, context.getPos().y + context.getSize().height / 2);
                        p1 = new Point(context.getPos().x + context.getSize().width - context.getSize().height * 3 / 4, context.getPos().y + context.getSize().height - 2);
                    }
                    Point p10 = new Point(context.getPos().x + context.getSize().width - context.getSize().height + 2, context.getPos().y + context.getSize().height / 2);
                    Point p11 = new Point(context.getPos().x + context.getSize().width - context.getSize().height + 6, context.getPos().y + context.getSize().height /2 );
                    Point p12 = new Point(context.getPos().x + context.getSize().width - context.getSize().height + 10, context.getPos().y + context.getSize().height /2);
                    Rectangle rectangle = new Rectangle(p10);
                    Rectangle rectangle1 = new Rectangle(p11);
                    Rectangle rectangle2 = new Rectangle(p12);
                    rectangle2.height = rectangle2.width = 1;
                    rectangle1.height = rectangle1.width =  1;
                    rectangle.height = rectangle.width = 1;

                    context.getInterface().fillRect(rectangle,color,color,color,color);
                    context.getInterface().fillRect(rectangle1,color,color,color,color);
                    context.getInterface().fillRect(rectangle2,color,color,color,color);
                    //context.getInterface().drawImage(new Rectangle(p1),90,true,a);
                    //context.getInterface().drawLine(p1,p3,color,color);
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
            if (active && level>0) color=getColorScheme().getActiveColor();
                // background
            else color=getColorScheme().getBackgroundColor();
            // inactive modules
            if (!active && level<2) color=getColorScheme().getBackgroundColor();
            // category
            if (active && level<1) color= ((ClickGuiMod) ModuleManager.getModule("ClickGUI")).categoryColor.getValue();
            color=new Color(color.getRed(),color.getGreen(),color.getBlue(),getColorScheme().getOpacity());
            return color;
        }

        @Override
        public Color getBackgroundColor (boolean focus) {
            return new Color(0,0,0,0);
        }

        @Override
        public ColorScheme getDefaultColorScheme() {
            return ClientTheme.this.scheme;
        }
    }
}
