package ClassSub;

import java.awt.image.*;
import java.awt.*;
import java.util.*;

public class Class38 implements Class193
{
    private Color color;
    
    
    public Class38() {
        this.color = Color.white;
    }
    
    public Class38(final Color color) {
        this.color = Color.white;
        this.color = color;
    }
    
    @Override
    public void draw(final BufferedImage bufferedImage, final Graphics2D graphics2D, final Class139 class139, final Class167 class140) {
        graphics2D.setColor(this.color);
        graphics2D.fill(class140.getShape());
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(final Color color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        this.color = color;
    }
    
    @Override
    public String toString() {
        return "Color";
    }
    
    @Override
    public List getValues() {
        final ArrayList<Class111> list = new ArrayList<Class111>();
        list.add(Class116.colorValue("Color", this.color));
        return list;
    }
    
    @Override
    public void setValues(final List list) {
        for (final Class111 class111 : list) {
            if (class111.getName().equals("Color")) {
                this.setColor((Color)class111.getObject());
            }
        }
    }
}
