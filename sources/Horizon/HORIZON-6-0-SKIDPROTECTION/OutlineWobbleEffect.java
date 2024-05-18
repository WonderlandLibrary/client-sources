package HORIZON-6-0-SKIDPROTECTION;

import java.awt.geom.PathIterator;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;
import java.awt.Shape;
import java.util.Iterator;
import java.util.List;
import java.awt.Color;
import java.awt.Stroke;

public class OutlineWobbleEffect extends OutlineEffect
{
    private float HorizonCode_Horizon_È;
    private float Â;
    
    public OutlineWobbleEffect() {
        this.HorizonCode_Horizon_È = 1.0f;
        this.Â = 1.0f;
        this.HorizonCode_Horizon_È(new HorizonCode_Horizon_È((HorizonCode_Horizon_È)null));
    }
    
    public float Ó() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final float detail) {
        this.HorizonCode_Horizon_È = detail;
    }
    
    public float à() {
        return this.Â;
    }
    
    public void Â(final float amplitude) {
        this.Â = amplitude;
    }
    
    public OutlineWobbleEffect(final int width, final Color color) {
        super(width, color);
        this.HorizonCode_Horizon_È = 1.0f;
        this.Â = 1.0f;
    }
    
    @Override
    public String toString() {
        return "Outline (Wobble)";
    }
    
    @Override
    public List Â() {
        final List values = super.Â();
        values.remove(2);
        values.add(EffectUtil.HorizonCode_Horizon_È("Detail", this.HorizonCode_Horizon_È, 1.0f, 50.0f, "This setting controls how detailed the outline will be. Smaller numbers cause the outline to have more detail."));
        values.add(EffectUtil.HorizonCode_Horizon_È("Amplitude", this.Â, 0.5f, 50.0f, "This setting controls the amplitude of the outline."));
        return values;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final List values) {
        super.HorizonCode_Horizon_È(values);
        for (final ConfigurableEffect.HorizonCode_Horizon_È value : values) {
            if (value.HorizonCode_Horizon_È().equals("Detail")) {
                this.HorizonCode_Horizon_È = (float)value.Ý();
            }
            else {
                if (!value.HorizonCode_Horizon_È().equals("Amplitude")) {
                    continue;
                }
                this.Â = (float)value.Ý();
            }
        }
    }
    
    private class HorizonCode_Horizon_È implements Stroke
    {
        private static final float Â = 1.0f;
        
        @Override
        public Shape createStrokedShape(Shape shape) {
            final GeneralPath result = new GeneralPath();
            shape = new BasicStroke(OutlineWobbleEffect.this.HorizonCode_Horizon_È(), 2, OutlineWobbleEffect.this.Ø­áŒŠá()).createStrokedShape(shape);
            final PathIterator it = new FlatteningPathIterator(shape.getPathIterator(null), 1.0);
            final float[] points = new float[6];
            float moveX = 0.0f;
            float moveY = 0.0f;
            float lastX = 0.0f;
            float lastY = 0.0f;
            float thisX = 0.0f;
            float thisY = 0.0f;
            int type = 0;
            float next = 0.0f;
            while (!it.isDone()) {
                type = it.currentSegment(points);
                switch (type) {
                    case 0: {
                        lastX = (moveX = this.HorizonCode_Horizon_È(points[0]));
                        lastY = (moveY = this.HorizonCode_Horizon_È(points[1]));
                        result.moveTo(moveX, moveY);
                        next = 0.0f;
                        break;
                    }
                    case 4: {
                        points[0] = moveX;
                        points[1] = moveY;
                    }
                    case 1: {
                        thisX = this.HorizonCode_Horizon_È(points[0]);
                        thisY = this.HorizonCode_Horizon_È(points[1]);
                        final float dx = thisX - lastX;
                        final float dy = thisY - lastY;
                        final float distance = (float)Math.sqrt(dx * dx + dy * dy);
                        if (distance >= next) {
                            final float r = 1.0f / distance;
                            while (distance >= next) {
                                final float x = lastX + next * dx * r;
                                final float y = lastY + next * dy * r;
                                result.lineTo(this.HorizonCode_Horizon_È(x), this.HorizonCode_Horizon_È(y));
                                next += OutlineWobbleEffect.this.HorizonCode_Horizon_È;
                            }
                        }
                        next -= distance;
                        lastX = thisX;
                        lastY = thisY;
                        break;
                    }
                }
                it.next();
            }
            return result;
        }
        
        private float HorizonCode_Horizon_È(final float x) {
            return x + (float)Math.random() * OutlineWobbleEffect.this.Â * 2.0f - 1.0f;
        }
    }
}
