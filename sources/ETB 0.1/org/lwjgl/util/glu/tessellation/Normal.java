package org.lwjgl.util.glu.tessellation;



















class Normal
{
  static boolean SLANTED_SWEEP;
  

















  static double S_UNIT_X;
  
















  static double S_UNIT_Y;
  
















  private static final boolean TRUE_PROJECT = false;
  

















  static
  {
    if (SLANTED_SWEEP)
    {








      S_UNIT_X = 0.5094153956495538D;
      S_UNIT_Y = 0.8605207462201063D;
    } else {
      S_UNIT_X = 1.0D;
      S_UNIT_Y = 0.0D;
    }
  }
  
  private static double Dot(double[] u, double[] v) {
    return u[0] * v[0] + u[1] * v[1] + u[2] * v[2];
  }
  
  static void Normalize(double[] v) {
    double len = v[0] * v[0] + v[1] * v[1] + v[2] * v[2];
    
    assert (len > 0.0D);
    len = Math.sqrt(len);
    v[0] /= len;
    v[1] /= len;
    v[2] /= len;
  }
  
  static int LongAxis(double[] v) {
    int i = 0;
    
    if (Math.abs(v[1]) > Math.abs(v[0])) {
      i = 1;
    }
    if (Math.abs(v[2]) > Math.abs(v[i])) {
      i = 2;
    }
    return i;
  }
  



  static void ComputeNormal(GLUtessellatorImpl tess, double[] norm)
  {
    GLUvertex vHead = mesh.vHead;
    

    double[] maxVal = new double[3];
    double[] minVal = new double[3];
    GLUvertex[] minVert = new GLUvertex[3];
    GLUvertex[] maxVert = new GLUvertex[3];
    double[] d1 = new double[3];
    double[] d2 = new double[3];
    double[] tNorm = new double[3]; double 
    
      tmp60_59 = (maxVal[2] = -2.0E150D);maxVal[1] = tmp60_59;maxVal[0] = tmp60_59; double 
      tmp77_76 = (minVal[2] = 2.0E150D);minVal[1] = tmp77_76;minVal[0] = tmp77_76;
    
    for (GLUvertex v = next; v != vHead; v = next) {
      for (int i = 0; i < 3; i++) {
        double c = coords[i];
        if (c < minVal[i]) {
          minVal[i] = c;
          minVert[i] = v;
        }
        if (c > maxVal[i]) {
          maxVal[i] = c;
          maxVert[i] = v;
        }
      }
    }
    



    int i = 0;
    if (maxVal[1] - minVal[1] > maxVal[0] - minVal[0]) {
      i = 1;
    }
    if (maxVal[2] - minVal[2] > maxVal[i] - minVal[i]) {
      i = 2;
    }
    if (minVal[i] >= maxVal[i])
    {
      norm[0] = 0.0D;
      norm[1] = 0.0D;
      norm[2] = 1.0D;
      return;
    }
    



    double maxLen2 = 0.0D;
    GLUvertex v1 = minVert[i];
    GLUvertex v2 = maxVert[i];
    d1[0] = (coords[0] - coords[0]);
    d1[1] = (coords[1] - coords[1]);
    d1[2] = (coords[2] - coords[2]);
    for (v = next; v != vHead; v = next) {
      d2[0] = (coords[0] - coords[0]);
      d2[1] = (coords[1] - coords[1]);
      d2[2] = (coords[2] - coords[2]);
      tNorm[0] = (d1[1] * d2[2] - d1[2] * d2[1]);
      tNorm[1] = (d1[2] * d2[0] - d1[0] * d2[2]);
      tNorm[2] = (d1[0] * d2[1] - d1[1] * d2[0]);
      double tLen2 = tNorm[0] * tNorm[0] + tNorm[1] * tNorm[1] + tNorm[2] * tNorm[2];
      if (tLen2 > maxLen2) {
        maxLen2 = tLen2;
        norm[0] = tNorm[0];
        norm[1] = tNorm[1];
        norm[2] = tNorm[2];
      }
    }
    
    if (maxLen2 <= 0.0D)
    {
      double tmp547_546 = (norm[2] = 0.0D);norm[1] = tmp547_546;norm[0] = tmp547_546;
      norm[LongAxis(d1)] = 1.0D;
    }
  }
  
  static void CheckOrientation(GLUtessellatorImpl tess)
  {
    GLUface fHead = mesh.fHead;
    GLUvertex vHead = mesh.vHead;
    




    double area = 0.0D;
    for (GLUface f = next; f != fHead; f = next) {
      GLUhalfEdge e = anEdge;
      if (winding > 0)
        do {
          area += (Org.s - Sym.Org.s) * (Org.t + Sym.Org.t);
          e = Lnext;
        } while (e != anEdge);
    }
    if (area < 0.0D)
    {
      for (GLUvertex v = next; v != vHead; v = next) {
        t = (-t);
      }
      tUnit[0] = (-tUnit[0]);
      tUnit[1] = (-tUnit[1]);
      tUnit[2] = (-tUnit[2]);
    }
  }
  


  public static void __gl_projectPolygon(GLUtessellatorImpl tess)
  {
    GLUvertex vHead = mesh.vHead;
    
    double[] norm = new double[3];
    

    boolean computedNormal = false;
    
    norm[0] = normal[0];
    norm[1] = normal[1];
    norm[2] = normal[2];
    if ((norm[0] == 0.0D) && (norm[1] == 0.0D) && (norm[2] == 0.0D)) {
      ComputeNormal(tess, norm);
      computedNormal = true;
    }
    double[] sUnit = tess.sUnit;
    double[] tUnit = tess.tUnit;
    int i = LongAxis(norm);
    
























    sUnit[i] = 0.0D;
    sUnit[((i + 1) % 3)] = S_UNIT_X;
    sUnit[((i + 2) % 3)] = S_UNIT_Y;
    
    tUnit[i] = 0.0D;
    tUnit[((i + 1) % 3)] = (norm[i] > 0.0D ? -S_UNIT_Y : S_UNIT_Y);
    tUnit[((i + 2) % 3)] = (norm[i] > 0.0D ? S_UNIT_X : -S_UNIT_X);
    


    for (GLUvertex v = next; v != vHead; v = next) {
      s = Dot(coords, sUnit);
      t = Dot(coords, tUnit);
    }
    if (computedNormal) {
      CheckOrientation(tess);
    }
  }
  
  private Normal() {}
}
