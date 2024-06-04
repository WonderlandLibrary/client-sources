package org.lwjgl.util.glu.tessellation;

















































class Geom
{
  private Geom() {}
  















































  static double EdgeEval(GLUvertex u, GLUvertex v, GLUvertex w)
  {
    assert ((VertLeq(u, v)) && (VertLeq(v, w)));
    
    double gapL = s - s;
    double gapR = s - s;
    
    if (gapL + gapR > 0.0D) {
      if (gapL < gapR) {
        return t - t + (t - t) * (gapL / (gapL + gapR));
      }
      return t - t + (t - t) * (gapR / (gapL + gapR));
    }
    

    return 0.0D;
  }
  

  static double EdgeSign(GLUvertex u, GLUvertex v, GLUvertex w)
  {
    assert ((VertLeq(u, v)) && (VertLeq(v, w)));
    
    double gapL = s - s;
    double gapR = s - s;
    
    if (gapL + gapR > 0.0D) {
      return (t - t) * gapL + (t - t) * gapR;
    }
    
    return 0.0D;
  }
  
















  static double TransEval(GLUvertex u, GLUvertex v, GLUvertex w)
  {
    assert ((TransLeq(u, v)) && (TransLeq(v, w)));
    
    double gapL = t - t;
    double gapR = t - t;
    
    if (gapL + gapR > 0.0D) {
      if (gapL < gapR) {
        return s - s + (s - s) * (gapL / (gapL + gapR));
      }
      return s - s + (s - s) * (gapR / (gapL + gapR));
    }
    

    return 0.0D;
  }
  





  static double TransSign(GLUvertex u, GLUvertex v, GLUvertex w)
  {
    assert ((TransLeq(u, v)) && (TransLeq(v, w)));
    
    double gapL = t - t;
    double gapR = t - t;
    
    if (gapL + gapR > 0.0D) {
      return (s - s) * gapL + (s - s) * gapR;
    }
    
    return 0.0D;
  }
  






  static boolean VertCCW(GLUvertex u, GLUvertex v, GLUvertex w)
  {
    return s * (t - t) + s * (t - t) + s * (t - t) >= 0.0D;
  }
  







  static double Interpolate(double a, double x, double b, double y)
  {
    a = a < 0.0D ? 0.0D : a;
    b = b < 0.0D ? 0.0D : b;
    if (a <= b) {
      if (b == 0.0D) {
        return (x + y) / 2.0D;
      }
      return x + (y - x) * (a / (a + b));
    }
    
    return y + (x - y) * (b / (a + b));
  }
  
















  static void EdgeIntersect(GLUvertex o1, GLUvertex d1, GLUvertex o2, GLUvertex d2, GLUvertex v)
  {
    if (!VertLeq(o1, d1)) {
      GLUvertex temp = o1;
      o1 = d1;
      d1 = temp;
    }
    if (!VertLeq(o2, d2)) {
      GLUvertex temp = o2;
      o2 = d2;
      d2 = temp;
    }
    if (!VertLeq(o1, o2)) {
      GLUvertex temp = o1;
      o1 = o2;
      o2 = temp;
      temp = d1;
      d1 = d2;
      d2 = temp;
    }
    
    if (!VertLeq(o2, d1))
    {
      s = ((s + s) / 2.0D);
    } else if (VertLeq(d1, d2))
    {
      double z1 = EdgeEval(o1, o2, d1);
      double z2 = EdgeEval(o2, d1, d2);
      if (z1 + z2 < 0.0D) {
        z1 = -z1;
        z2 = -z2;
      }
      s = Interpolate(z1, s, z2, s);
    }
    else {
      double z1 = EdgeSign(o1, o2, d1);
      double z2 = -EdgeSign(o1, d2, d1);
      if (z1 + z2 < 0.0D) {
        z1 = -z1;
        z2 = -z2;
      }
      s = Interpolate(z1, s, z2, s);
    }
    


    if (!TransLeq(o1, d1)) {
      GLUvertex temp = o1;
      o1 = d1;
      d1 = temp;
    }
    if (!TransLeq(o2, d2)) {
      GLUvertex temp = o2;
      o2 = d2;
      d2 = temp;
    }
    if (!TransLeq(o1, o2)) {
      GLUvertex temp = o2;
      o2 = o1;
      o1 = temp;
      temp = d2;
      d2 = d1;
      d1 = temp;
    }
    
    if (!TransLeq(o2, d1))
    {
      t = ((t + t) / 2.0D);
    } else if (TransLeq(d1, d2))
    {
      double z1 = TransEval(o1, o2, d1);
      double z2 = TransEval(o2, d1, d2);
      if (z1 + z2 < 0.0D) {
        z1 = -z1;
        z2 = -z2;
      }
      t = Interpolate(z1, t, z2, t);
    }
    else {
      double z1 = TransSign(o1, o2, d1);
      double z2 = -TransSign(o1, d2, d1);
      if (z1 + z2 < 0.0D) {
        z1 = -z1;
        z2 = -z2;
      }
      t = Interpolate(z1, t, z2, t);
    }
  }
  
  static boolean VertEq(GLUvertex u, GLUvertex v) {
    return (s == s) && (t == t);
  }
  
  static boolean VertLeq(GLUvertex u, GLUvertex v) {
    return (s < s) || ((s == s) && (t <= t));
  }
  

  static boolean TransLeq(GLUvertex u, GLUvertex v)
  {
    return (t < t) || ((t == t) && (s <= s));
  }
  
  static boolean EdgeGoesLeft(GLUhalfEdge e) {
    return VertLeq(Sym.Org, Org);
  }
  
  static boolean EdgeGoesRight(GLUhalfEdge e) {
    return VertLeq(Org, Sym.Org);
  }
  
  static double VertL1dist(GLUvertex u, GLUvertex v) {
    return Math.abs(s - s) + Math.abs(t - t);
  }
}
