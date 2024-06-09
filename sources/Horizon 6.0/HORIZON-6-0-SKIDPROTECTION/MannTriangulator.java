package HORIZON-6-0-SKIDPROTECTION;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MannTriangulator implements Triangulator
{
    private static final double Ý = 1.0E-5;
    protected Â HorizonCode_Horizon_È;
    protected Â Â;
    private Â Ø­áŒŠá;
    private HorizonCode_Horizon_È Âµá€;
    private List Ó;
    
    public MannTriangulator() {
        this.Ó = new ArrayList();
        this.HorizonCode_Horizon_È = this.Âµá€();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.HorizonCode_Horizon_È(new Vector2f(x, y));
    }
    
    public void HorizonCode_Horizon_È() {
        while (this.Â != null) {
            this.Â = this.HorizonCode_Horizon_È(this.Â);
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        this.Â = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        final Â newHole = this.Âµá€();
        newHole.Â = this.Â;
        this.Â = newHole;
    }
    
    private void HorizonCode_Horizon_È(final Vector2f pt) {
        if (this.Â == null) {
            final HorizonCode_Horizon_È p = this.Â(pt);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p);
        }
        else {
            final HorizonCode_Horizon_È p = this.Â(pt);
            this.Â.HorizonCode_Horizon_È(p);
        }
    }
    
    private Vector2f[] HorizonCode_Horizon_È(Vector2f[] result) {
        this.HorizonCode_Horizon_È.Â();
        for (Â hole = this.Â; hole != null; hole = hole.Â) {
            hole.Â();
        }
        while (this.Â != null) {
            HorizonCode_Horizon_È pHole = this.Â.HorizonCode_Horizon_È;
        Label_0243:
            do {
                if (pHole.Ó <= 0.0) {
                    HorizonCode_Horizon_È pContour = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                    do {
                        if (pHole.Ø­áŒŠá(pContour) && pContour.Ø­áŒŠá(pHole) && !this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(pHole.HorizonCode_Horizon_È, pContour.HorizonCode_Horizon_È)) {
                            Â hole2 = this.Â;
                            while (!hole2.HorizonCode_Horizon_È(pHole.HorizonCode_Horizon_È, pContour.HorizonCode_Horizon_È)) {
                                if ((hole2 = hole2.Â) == null) {
                                    final HorizonCode_Horizon_È newPtContour = this.Â(pContour.HorizonCode_Horizon_È);
                                    pContour.Â(newPtContour);
                                    final HorizonCode_Horizon_È newPtHole = this.Â(pHole.HorizonCode_Horizon_È);
                                    pHole.HorizonCode_Horizon_È(newPtHole);
                                    pContour.Ý = pHole;
                                    pHole.Â = pContour;
                                    newPtHole.Ý = newPtContour;
                                    newPtContour.Â = newPtHole;
                                    pContour.Â();
                                    pHole.Â();
                                    newPtContour.Â();
                                    newPtHole.Â();
                                    this.Â.HorizonCode_Horizon_È = null;
                                    break Label_0243;
                                }
                            }
                        }
                    } while ((pContour = pContour.Ý) != this.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                }
            } while ((pHole = pHole.Ý) != this.Â.HorizonCode_Horizon_È);
            this.Â = this.HorizonCode_Horizon_È(this.Â);
        }
        final int numTriangles = this.HorizonCode_Horizon_È.Ý() - 2;
        final int neededSpace = numTriangles * 3 + 1;
        if (result.length < neededSpace) {
            result = (Vector2f[])Array.newInstance(result.getClass().getComponentType(), neededSpace);
        }
        int idx = 0;
        while (true) {
            HorizonCode_Horizon_È pContour2 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            if (pContour2 == null) {
                break;
            }
            if (pContour2.Ý == pContour2.Â) {
                break;
            }
            do {
                if (pContour2.Ó > 0.0) {
                    final HorizonCode_Horizon_È prev = pContour2.Â;
                    final HorizonCode_Horizon_È next = pContour2.Ý;
                    if ((next.Ý == prev || (prev.Ø­áŒŠá(next) && next.Ø­áŒŠá(prev))) && !this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(prev.HorizonCode_Horizon_È, next.HorizonCode_Horizon_È)) {
                        result[idx++] = pContour2.HorizonCode_Horizon_È;
                        result[idx++] = next.HorizonCode_Horizon_È;
                        result[idx++] = prev.HorizonCode_Horizon_È;
                        break;
                    }
                    continue;
                }
            } while ((pContour2 = pContour2.Ý) != this.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
            final HorizonCode_Horizon_È prev = pContour2.Â;
            final HorizonCode_Horizon_È next = pContour2.Ý;
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È = prev;
            pContour2.HorizonCode_Horizon_È();
            this.HorizonCode_Horizon_È(pContour2);
            next.Â();
            prev.Â();
        }
        result[idx] = null;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        return result;
    }
    
    private Â Âµá€() {
        final Â pb = this.Ø­áŒŠá;
        if (pb != null) {
            this.Ø­áŒŠá = pb.Â;
            pb.Â = null;
            return pb;
        }
        return new Â();
    }
    
    private Â HorizonCode_Horizon_È(final Â pb) {
        final Â next = pb.Â;
        pb.HorizonCode_Horizon_È();
        pb.Â = this.Ø­áŒŠá;
        this.Ø­áŒŠá = pb;
        return next;
    }
    
    private HorizonCode_Horizon_È Â(final Vector2f pt) {
        final HorizonCode_Horizon_È p = this.Âµá€;
        if (p != null) {
            this.Âµá€ = p.Ý;
            p.Ý = null;
            p.Â = null;
            p.HorizonCode_Horizon_È = pt;
            return p;
        }
        return new HorizonCode_Horizon_È(pt);
    }
    
    private void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p) {
        p.Ý = this.Âµá€;
        this.Âµá€ = p;
    }
    
    private void Â(final HorizonCode_Horizon_È head) {
        head.Â.Ý = this.Âµá€;
        head.Â = null;
        this.Âµá€ = head;
    }
    
    @Override
    public boolean Â() {
        final Vector2f[] temp = this.HorizonCode_Horizon_È(new Vector2f[0]);
        for (int i = 0; i < temp.length && temp[i] != null; ++i) {
            this.Ó.add(temp[i]);
        }
        return true;
    }
    
    @Override
    public int Ý() {
        return this.Ó.size() / 3;
    }
    
    @Override
    public float[] HorizonCode_Horizon_È(final int tri, final int i) {
        final Vector2f pt = this.Ó.get(tri * 3 + i);
        return new float[] { pt.HorizonCode_Horizon_È, pt.Â };
    }
    
    private static class HorizonCode_Horizon_È implements Serializable
    {
        protected Vector2f HorizonCode_Horizon_È;
        protected HorizonCode_Horizon_È Â;
        protected HorizonCode_Horizon_È Ý;
        protected double Ø­áŒŠá;
        protected double Âµá€;
        protected double Ó;
        protected double à;
        
        public HorizonCode_Horizon_È(final Vector2f pt) {
            this.HorizonCode_Horizon_È = pt;
        }
        
        public void HorizonCode_Horizon_È() {
            this.Â.Ý = this.Ý;
            this.Ý.Â = this.Â;
            this.Ý = null;
            this.Â = null;
        }
        
        public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p) {
            this.Â.Ý = p;
            p.Â = this.Â;
            p.Ý = this;
            this.Â = p;
        }
        
        public void Â(final HorizonCode_Horizon_È p) {
            this.Ý.Â = p;
            p.Â = this;
            p.Ý = this.Ý;
            this.Ý = p;
        }
        
        private double Â(final double x, final double y) {
            return Math.sqrt(x * x + y * y);
        }
        
        public void Â() {
            if (this.Â.HorizonCode_Horizon_È.equals(this.HorizonCode_Horizon_È)) {
                final Vector2f horizonCode_Horizon_È = this.HorizonCode_Horizon_È;
                horizonCode_Horizon_È.HorizonCode_Horizon_È += 0.01f;
            }
            double dx1 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È - this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            double dy1 = this.HorizonCode_Horizon_È.Â - this.Â.HorizonCode_Horizon_È.Â;
            final double len1 = this.Â(dx1, dy1);
            dx1 /= len1;
            dy1 /= len1;
            if (this.Ý.HorizonCode_Horizon_È.equals(this.HorizonCode_Horizon_È)) {
                final Vector2f horizonCode_Horizon_È2 = this.HorizonCode_Horizon_È;
                horizonCode_Horizon_È2.Â += 0.01f;
            }
            double dx2 = this.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            double dy2 = this.Ý.HorizonCode_Horizon_È.Â - this.HorizonCode_Horizon_È.Â;
            final double len2 = this.Â(dx2, dy2);
            dx2 /= len2;
            dy2 /= len2;
            final double nx1 = -dy1;
            final double ny1 = dx1;
            this.Ø­áŒŠá = (nx1 - dy2) * 0.5;
            this.Âµá€ = (ny1 + dx2) * 0.5;
            if (this.Ø­áŒŠá * this.Ø­áŒŠá + this.Âµá€ * this.Âµá€ < 1.0E-5) {
                this.Ø­áŒŠá = dx1;
                this.Âµá€ = dy2;
                this.Ó = 1.0;
                if (dx1 * dx2 + dy1 * dy2 > 0.0) {
                    this.Ø­áŒŠá = -dx1;
                    this.Âµá€ = -dy1;
                }
            }
            else {
                this.Ó = this.Ø­áŒŠá * dx2 + this.Âµá€ * dy2;
            }
        }
        
        public double Ý(final HorizonCode_Horizon_È p) {
            final double dx = p.HorizonCode_Horizon_È.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            final double dy = p.HorizonCode_Horizon_È.Â - this.HorizonCode_Horizon_È.Â;
            final double dlen = this.Â(dx, dy);
            return (this.Ø­áŒŠá * dx + this.Âµá€ * dy) / dlen;
        }
        
        public boolean Ý() {
            return this.Ó < 0.0;
        }
        
        public boolean HorizonCode_Horizon_È(final double dx, final double dy) {
            final boolean sidePrev = (this.Â.HorizonCode_Horizon_È.Â - this.HorizonCode_Horizon_È.Â) * dx + (this.HorizonCode_Horizon_È.HorizonCode_Horizon_È - this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È) * dy >= 0.0;
            final boolean sideNext = (this.HorizonCode_Horizon_È.Â - this.Ý.HorizonCode_Horizon_È.Â) * dx + (this.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È.HorizonCode_Horizon_È) * dy >= 0.0;
            return (this.Ó < 0.0) ? (sidePrev | sideNext) : (sidePrev & sideNext);
        }
        
        public boolean Ø­áŒŠá(final HorizonCode_Horizon_È p) {
            return this.HorizonCode_Horizon_È(p.HorizonCode_Horizon_È.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È.HorizonCode_Horizon_È, p.HorizonCode_Horizon_È.Â - this.HorizonCode_Horizon_È.Â);
        }
    }
    
    protected class Â implements Serializable
    {
        protected HorizonCode_Horizon_È HorizonCode_Horizon_È;
        protected Â Â;
        
        public void HorizonCode_Horizon_È() {
            if (this.HorizonCode_Horizon_È != null) {
                MannTriangulator.this.Â(this.HorizonCode_Horizon_È);
                this.HorizonCode_Horizon_È = null;
            }
        }
        
        public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p) {
            if (this.HorizonCode_Horizon_È != null) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p);
            }
            else {
                this.HorizonCode_Horizon_È = p;
                p.Ý = p;
                p.Â = p;
            }
        }
        
        public void Â() {
            if (this.HorizonCode_Horizon_È == null) {
                return;
            }
            HorizonCode_Horizon_È p = this.HorizonCode_Horizon_È;
            do {
                p.Â();
            } while ((p = p.Ý) != this.HorizonCode_Horizon_È);
        }
        
        public boolean HorizonCode_Horizon_È(final Vector2f v1, final Vector2f v2) {
            final double dxA = v2.HorizonCode_Horizon_È - v1.HorizonCode_Horizon_È;
            final double dyA = v2.Â - v1.Â;
            HorizonCode_Horizon_È p = this.HorizonCode_Horizon_È;
            while (true) {
                final HorizonCode_Horizon_È n = p.Ý;
                if (p.HorizonCode_Horizon_È != v1 && n.HorizonCode_Horizon_È != v1 && p.HorizonCode_Horizon_È != v2 && n.HorizonCode_Horizon_È != v2) {
                    final double dxB = n.HorizonCode_Horizon_È.HorizonCode_Horizon_È - p.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                    final double dyB = n.HorizonCode_Horizon_È.Â - p.HorizonCode_Horizon_È.Â;
                    final double d = dxA * dyB - dyA * dxB;
                    if (Math.abs(d) > 1.0E-5) {
                        final double tmp1 = p.HorizonCode_Horizon_È.HorizonCode_Horizon_È - v1.HorizonCode_Horizon_È;
                        final double tmp2 = p.HorizonCode_Horizon_È.Â - v1.Â;
                        final double tA = (dyB * tmp1 - dxB * tmp2) / d;
                        final double tB = (dyA * tmp1 - dxA * tmp2) / d;
                        if (tA >= 0.0 && tA <= 1.0 && tB >= 0.0 && tB <= 1.0) {
                            return true;
                        }
                    }
                }
                if (n == this.HorizonCode_Horizon_È) {
                    return false;
                }
                p = n;
            }
        }
        
        public int Ý() {
            if (this.HorizonCode_Horizon_È == null) {
                return 0;
            }
            int count = 0;
            HorizonCode_Horizon_È p = this.HorizonCode_Horizon_È;
            do {
                ++count;
            } while ((p = p.Ý) != this.HorizonCode_Horizon_È);
            return count;
        }
        
        public boolean HorizonCode_Horizon_È(final Vector2f point) {
            return this.HorizonCode_Horizon_È != null && (this.HorizonCode_Horizon_È.Â.HorizonCode_Horizon_È.equals(point) || this.HorizonCode_Horizon_È.HorizonCode_Horizon_È.equals(point));
        }
    }
}
