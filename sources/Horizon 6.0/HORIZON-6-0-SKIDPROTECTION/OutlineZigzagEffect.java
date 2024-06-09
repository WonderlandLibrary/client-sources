package HORIZON-6-0-SKIDPROTECTION;

import java.awt.geom.PathIterator;
import java.awt.BasicStroke;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.Shape;
import java.util.Iterator;
import java.util.List;
import java.awt.Color;
import java.awt.Stroke;

public class OutlineZigzagEffect extends OutlineEffect
{
    private float HorizonCode_Horizon_È;
    private float Â;
    
    public OutlineZigzagEffect() {
        this.HorizonCode_Horizon_È = 1.0f;
        this.Â = 3.0f;
        this.HorizonCode_Horizon_È(new HorizonCode_Horizon_È((HorizonCode_Horizon_È)null));
    }
    
    public float Ó() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final float wavelength) {
        this.Â = wavelength;
    }
    
    public float à() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void Â(final float amplitude) {
        this.HorizonCode_Horizon_È = amplitude;
    }
    
    public OutlineZigzagEffect(final int width, final Color color) {
        super(width, color);
        this.HorizonCode_Horizon_È = 1.0f;
        this.Â = 3.0f;
    }
    
    @Override
    public String toString() {
        return "Outline (Zigzag)";
    }
    
    @Override
    public List Â() {
        final List values = super.Â();
        values.add(EffectUtil.HorizonCode_Horizon_È("Wavelength", this.Â, 1.0f, 100.0f, "This setting controls the wavelength of the outline. The smaller the value, the more segments will be used to draw the outline."));
        values.add(EffectUtil.HorizonCode_Horizon_È("Amplitude", this.HorizonCode_Horizon_È, 0.5f, 50.0f, "This setting controls the amplitude of the outline. The bigger the value, the more the zigzags will vary."));
        return values;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final List values) {
        super.HorizonCode_Horizon_È(values);
        for (final ConfigurableEffect.HorizonCode_Horizon_È value : values) {
            if (value.HorizonCode_Horizon_È().equals("Wavelength")) {
                this.Â = (float)value.Ý();
            }
            else {
                if (!value.HorizonCode_Horizon_È().equals("Amplitude")) {
                    continue;
                }
                this.HorizonCode_Horizon_È = (float)value.Ý();
            }
        }
    }
    
    private class HorizonCode_Horizon_È implements Stroke
    {
        private static final float Â = 1.0f;
        
        @Override
        public Shape createStrokedShape(final Shape shape) {
            final GeneralPath result = new GeneralPath();
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
            int phase = 0;
            while (!it.isDone()) {
                type = it.currentSegment(points);
                switch (type) {
                    case 0: {
                        lastX = (moveX = points[0]);
                        lastY = (moveY = points[1]);
                        result.moveTo(moveX, moveY);
                        next = OutlineZigzagEffect.this.Â / 2.0f;
                        break;
                    }
                    case 4: {
                        points[0] = moveX;
                        points[1] = moveY;
                    }
                    case 1: {
                        thisX = points[0];
                        thisY = points[1];
                        final float dx = thisX - lastX;
                        final float dy = thisY - lastY;
                        final float distance = (float)Math.sqrt(dx * dx + dy * dy);
                        if (distance >= next) {
                            final float r = 1.0f / distance;
                            while (distance >= next) {
                                final float x = lastX + next * dx * r;
                                final float y = lastY + next * dy * r;
                                if ((phase & 0x1) == 0x0) {
                                    result.lineTo(x + OutlineZigzagEffect.this.HorizonCode_Horizon_È * dy * r, y - OutlineZigzagEffect.this.HorizonCode_Horizon_È * dx * r);
                                }
                                else {
                                    result.lineTo(x - OutlineZigzagEffect.this.HorizonCode_Horizon_È * dy * r, y + OutlineZigzagEffect.this.HorizonCode_Horizon_È * dx * r);
                                }
                                next += OutlineZigzagEffect.this.Â;
                                ++phase;
                            }
                        }
                        next -= distance;
                        lastX = thisX;
                        lastY = thisY;
                        if (type == 4) {
                            result.closePath();
                            break;
                        }
                        break;
                    }
                }
                it.next();
            }
            return new BasicStroke(OutlineZigzagEffect.this.HorizonCode_Horizon_È(), 2, OutlineZigzagEffect.this.Ø­áŒŠá()).createStrokedShape(result);
        }
    }
}
