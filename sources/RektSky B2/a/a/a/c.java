package a.a.a;

import java.awt.image.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.*;

public abstract class c implements BufferedImageOp, Cloneable
{
    @Override
    public BufferedImage createCompatibleDestImage(final BufferedImage bufferedImage, ColorModel colorModel) {
        if (colorModel == null) {
            colorModel = bufferedImage.getColorModel();
        }
        return new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(bufferedImage.getWidth(), bufferedImage.getHeight()), colorModel.isAlphaPremultiplied(), null);
    }
    
    @Override
    public Rectangle2D getBounds2D(final BufferedImage bufferedImage) {
        return new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }
    
    @Override
    public Point2D getPoint2D(final Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            point2D2 = new Point2D.Double();
        }
        point2D2.setLocation(point2D.getX(), point2D.getY());
        return point2D2;
    }
    
    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
