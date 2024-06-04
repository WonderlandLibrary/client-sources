package org.lwjgl.util.glu.tessellation;














































class Render
{
  private static final boolean USE_OPTIMIZED_CODE_PATH = false;
  












































  private static final RenderFan renderFan = new RenderFan(null);
  private static final RenderStrip renderStrip = new RenderStrip(null);
  private static final RenderTriangle renderTriangle = new RenderTriangle(null);
  private static final int SIGN_INCONSISTENT = 2;
  private Render() {}
  
  private static class FaceCount {
    long size;
    GLUhalfEdge eStart;
    Render.renderCallBack render;
    
    private FaceCount() {}
    
    private FaceCount(long size, GLUhalfEdge eStart, Render.renderCallBack render) { this.size = size;
      this.eStart = eStart;
      this.render = render;
    }
  }
  



















  public static void __gl_renderMesh(GLUtessellatorImpl tess, GLUmesh mesh)
  {
    lonelyTriList = null;
    
    for (GLUface f = fHead.next; f != fHead; f = next) {
      marked = false;
    }
    for (f = fHead.next; f != fHead; f = next)
    {




      if ((inside) && (!marked)) {
        RenderMaximumFaceGroup(tess, f);
        assert (marked);
      }
    }
    if (lonelyTriList != null) {
      RenderLonelyTriangles(tess, lonelyTriList);
      lonelyTriList = null;
    }
  }
  







  static void RenderMaximumFaceGroup(GLUtessellatorImpl tess, GLUface fOrig)
  {
    GLUhalfEdge e = anEdge;
    FaceCount max = new FaceCount(null);
    

    size = 1L;
    eStart = e;
    render = renderTriangle;
    
    if (!flagBoundary) {
      FaceCount newFace = MaximumFan(e);
      if (size > size) {
        max = newFace;
      }
      newFace = MaximumFan(Lnext);
      if (size > size) {
        max = newFace;
      }
      newFace = MaximumFan(Onext.Sym);
      if (size > size) {
        max = newFace;
      }
      
      newFace = MaximumStrip(e);
      if (size > size) {
        max = newFace;
      }
      newFace = MaximumStrip(Lnext);
      if (size > size) {
        max = newFace;
      }
      newFace = MaximumStrip(Onext.Sym);
      if (size > size) {
        max = newFace;
      }
    }
    render.render(tess, eStart, size);
  }
  







  private static boolean Marked(GLUface f)
  {
    return (!inside) || (marked);
  }
  
  private static GLUface AddToTrail(GLUface f, GLUface t) {
    trail = t;
    marked = true;
    return f;
  }
  
  private static void FreeTrail(GLUface t)
  {
    while (t != null) {
      marked = false;
      t = trail;
    }
  }
  






  static FaceCount MaximumFan(GLUhalfEdge eOrig)
  {
    FaceCount newFace = new FaceCount(0L, null, renderFan, null);
    GLUface trail = null;
    

    for (GLUhalfEdge e = eOrig; !Marked(Lface); e = Onext) {
      trail = AddToTrail(Lface, trail);
      size += 1L;
    }
    for (e = eOrig; !Marked(Sym.Lface); e = Sym.Lnext) {
      trail = AddToTrail(Sym.Lface, trail);
      size += 1L;
    }
    eStart = e;
    
    FreeTrail(trail);
    return newFace;
  }
  
  private static boolean IsEven(long n)
  {
    return (n & 1L) == 0L;
  }
  









  static FaceCount MaximumStrip(GLUhalfEdge eOrig)
  {
    FaceCount newFace = new FaceCount(0L, null, renderStrip, null);
    long headSize = 0L;long tailSize = 0L;
    GLUface trail = null;
    

    for (GLUhalfEdge e = eOrig; !Marked(Lface); e = Onext) {
      trail = AddToTrail(Lface, trail);
      tailSize += 1L;
      e = Lnext.Sym;
      if (Marked(Lface)) break;
      trail = AddToTrail(Lface, trail);tailSize += 1L;
    }
    GLUhalfEdge eTail = e;
    
    for (e = eOrig; !Marked(Sym.Lface); e = Sym.Onext.Sym) {
      trail = AddToTrail(Sym.Lface, trail);
      headSize += 1L;
      e = Sym.Lnext;
      if (Marked(Sym.Lface)) break;
      trail = AddToTrail(Sym.Lface, trail);headSize += 1L;
    }
    GLUhalfEdge eHead = e;
    
    size = (tailSize + headSize);
    if (IsEven(tailSize)) {
      eStart = Sym;
    } else if (IsEven(headSize)) {
      eStart = eHead;

    }
    else
    {
      size -= 1L;
      eStart = Onext;
    }
    
    FreeTrail(trail);
    return newFace;
  }
  
  private static abstract interface renderCallBack { public abstract void render(GLUtessellatorImpl paramGLUtessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong);
  }
  
  private static class RenderTriangle implements Render.renderCallBack { private RenderTriangle() {}
    
    public void render(GLUtessellatorImpl tess, GLUhalfEdge e, long size) { assert (size == 1L);
      lonelyTriList = Render.AddToTrail(Lface, lonelyTriList);
    }
  }
  





  static void RenderLonelyTriangles(GLUtessellatorImpl tess, GLUface f)
  {
    int edgeState = -1;
    
    tess.callBeginOrBeginData(4);
    for (; 
        f != null; f = trail)
    {

      GLUhalfEdge e = anEdge;
      do {
        if (flagBoundary)
        {


          int newState = !Sym.Lface.inside ? 1 : 0;
          if (edgeState != newState) {
            edgeState = newState;
            tess.callEdgeFlagOrEdgeFlagData(edgeState != 0);
          }
        }
        tess.callVertexOrVertexData(Org.data);
        
        e = Lnext;
      } while (e != anEdge);
    }
    tess.callEndOrEndData();
  }
  
  private static class RenderFan implements Render.renderCallBack
  {
    private RenderFan() {}
    
    public void render(GLUtessellatorImpl tess, GLUhalfEdge e, long size)
    {
      tess.callBeginOrBeginData(6);
      tess.callVertexOrVertexData(Org.data);
      tess.callVertexOrVertexData(Sym.Org.data);
      
      while (!Render.Marked(Lface)) {
        Lface.marked = true;
        size -= 1L;
        e = Onext;
        tess.callVertexOrVertexData(Sym.Org.data);
      }
      
      assert (size == 0L);
      tess.callEndOrEndData();
    }
  }
  
  private static class RenderStrip implements Render.renderCallBack
  {
    private RenderStrip() {}
    
    public void render(GLUtessellatorImpl tess, GLUhalfEdge e, long size)
    {
      tess.callBeginOrBeginData(5);
      tess.callVertexOrVertexData(Org.data);
      tess.callVertexOrVertexData(Sym.Org.data);
      
      while (!Render.Marked(Lface)) {
        Lface.marked = true;
        size -= 1L;
        e = Lnext.Sym;
        tess.callVertexOrVertexData(Org.data);
        if (Render.Marked(Lface))
          break;
        Lface.marked = true;
        size -= 1L;
        e = Onext;
        tess.callVertexOrVertexData(Sym.Org.data);
      }
      
      assert (size == 0L);
      tess.callEndOrEndData();
    }
  }
  








  public static void __gl_renderBoundary(GLUtessellatorImpl tess, GLUmesh mesh)
  {
    for (GLUface f = fHead.next; f != fHead; f = next) {
      if (inside) {
        tess.callBeginOrBeginData(2);
        GLUhalfEdge e = anEdge;
        do {
          tess.callVertexOrVertexData(Org.data);
          e = Lnext;
        } while (e != anEdge);
        tess.callEndOrEndData();
      }
    }
  }
  












  static int ComputeNormal(GLUtessellatorImpl tess, double[] norm, boolean check)
  {
    CachedVertex[] v = cache;
    
    int vn = cacheCount;
    


    double[] n = new double[3];
    int sign = 0;
    













    if (!check) {
      double tmp32_31 = (norm[2] = 0.0D);norm[1] = tmp32_31;norm[0] = tmp32_31;
    }
    
    int vc = 1;
    double xc = coords[0] - 0coords[0];
    double yc = coords[1] - 0coords[1];
    double zc = coords[2] - 0coords[2];
    for (;;) { vc++; if (vc >= vn) break;
      double xp = xc;
      double yp = yc;
      double zp = zc;
      xc = coords[0] - 0coords[0];
      yc = coords[1] - 0coords[1];
      zc = coords[2] - 0coords[2];
      

      n[0] = (yp * zc - zp * yc);
      n[1] = (zp * xc - xp * zc);
      n[2] = (xp * yc - yp * xc);
      
      double dot = n[0] * norm[0] + n[1] * norm[1] + n[2] * norm[2];
      if (!check)
      {


        if (dot >= 0.0D) {
          norm[0] += n[0];
          norm[1] += n[1];
          norm[2] += n[2];
        } else {
          norm[0] -= n[0];
          norm[1] -= n[1];
          norm[2] -= n[2];
        }
      } else if (dot != 0.0D)
      {
        if (dot > 0.0D) {
          if (sign < 0) return 2;
          sign = 1;
        } else {
          if (sign > 0) return 2;
          sign = -1;
        }
      }
    }
    return sign;
  }
  






  public static boolean __gl_renderCache(GLUtessellatorImpl tess)
  {
    CachedVertex[] v = cache;
    
    int vn = cacheCount;
    

    double[] norm = new double[3];
    

    if (cacheCount < 3)
    {
      return true;
    }
    
    norm[0] = normal[0];
    norm[1] = normal[1];
    norm[2] = normal[2];
    if ((norm[0] == 0.0D) && (norm[1] == 0.0D) && (norm[2] == 0.0D)) {
      ComputeNormal(tess, norm, false);
    }
    
    int sign = ComputeNormal(tess, norm, true);
    if (sign == 2)
    {
      return false;
    }
    if (sign == 0)
    {
      return true;
    }
    

    return false;
  }
}
