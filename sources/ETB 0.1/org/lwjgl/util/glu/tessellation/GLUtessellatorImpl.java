package org.lwjgl.util.glu.tessellation;

import org.lwjgl.util.glu.GLUtessellator;
import org.lwjgl.util.glu.GLUtessellatorCallback;
import org.lwjgl.util.glu.GLUtessellatorCallbackAdapter;



























































































public class GLUtessellatorImpl
  implements GLUtessellator
{
  public static final int TESS_MAX_CACHE = 100;
  private int state;
  private GLUhalfEdge lastEdge;
  GLUmesh mesh;
  double[] normal = new double[3];
  double[] sUnit = new double[3];
  double[] tUnit = new double[3];
  

  private double relTolerance;
  
  int windingRule;
  
  boolean fatalError;
  
  Dict dict;
  
  PriorityQ pq;
  
  GLUvertex event;
  
  boolean flagBoundary;
  
  boolean boundaryOnly;
  
  GLUface lonelyTriList;
  
  private boolean flushCacheOnNextVertex;
  
  int cacheCount;
  
  CachedVertex[] cache = new CachedVertex[100];
  
  private Object polygonData;
  
  private GLUtessellatorCallback callBegin;
  
  private GLUtessellatorCallback callEdgeFlag;
  
  private GLUtessellatorCallback callVertex;
  
  private GLUtessellatorCallback callEnd;
  
  private GLUtessellatorCallback callError;
  
  private GLUtessellatorCallback callCombine;
  
  private GLUtessellatorCallback callBeginData;
  private GLUtessellatorCallback callEdgeFlagData;
  private GLUtessellatorCallback callVertexData;
  private GLUtessellatorCallback callEndData;
  private GLUtessellatorCallback callErrorData;
  private GLUtessellatorCallback callCombineData;
  private static final double GLU_TESS_DEFAULT_TOLERANCE = 0.0D;
  private static GLUtessellatorCallback NULL_CB = new GLUtessellatorCallbackAdapter();
  


  public GLUtessellatorImpl()
  {
    state = 0;
    
    normal[0] = 0.0D;
    normal[1] = 0.0D;
    normal[2] = 0.0D;
    
    relTolerance = 0.0D;
    windingRule = 100130;
    flagBoundary = false;
    boundaryOnly = false;
    
    callBegin = NULL_CB;
    callEdgeFlag = NULL_CB;
    callVertex = NULL_CB;
    callEnd = NULL_CB;
    callError = NULL_CB;
    callCombine = NULL_CB;
    

    callBeginData = NULL_CB;
    callEdgeFlagData = NULL_CB;
    callVertexData = NULL_CB;
    callEndData = NULL_CB;
    callErrorData = NULL_CB;
    callCombineData = NULL_CB;
    
    polygonData = null;
    
    for (int i = 0; i < cache.length; i++) {
      cache[i] = new CachedVertex();
    }
  }
  
  public static GLUtessellator gluNewTess()
  {
    return new GLUtessellatorImpl();
  }
  


  private void makeDormant()
  {
    if (mesh != null) {
      Mesh.__gl_meshDeleteMesh(mesh);
    }
    state = 0;
    lastEdge = null;
    mesh = null;
  }
  
  private void requireState(int newState) {
    if (state != newState) gotoState(newState);
  }
  
  private void gotoState(int newState) {
    while (state != newState)
    {


      if (state < newState) {
        if (state == 0) {
          callErrorOrErrorData(100151);
          gluTessBeginPolygon(null);
        } else if (state == 1) {
          callErrorOrErrorData(100152);
          gluTessBeginContour();
        }
      }
      else if (state == 2) {
        callErrorOrErrorData(100154);
        gluTessEndContour();
      } else if (state == 1) {
        callErrorOrErrorData(100153);
        
        makeDormant();
      }
    }
  }
  
  public void gluDeleteTess()
  {
    requireState(0);
  }
  
  public void gluTessProperty(int which, double value) {
    switch (which) {
    case 100142: 
      if ((value >= 0.0D) && (value <= 1.0D)) {
        relTolerance = value; return;
      }
      break;
    case 100140: 
      int windingRule = (int)value;
      if (windingRule == value)
      {
        switch (windingRule) {
        case 100130: 
        case 100131: 
        case 100132: 
        case 100133: 
        case 100134: 
          this.windingRule = windingRule; return;
        }
        
      }
      
      break;
    case 100141: 
      boundaryOnly = (value != 0.0D);
      return;
    
    default: 
      callErrorOrErrorData(100900);
      return;
    }
    callErrorOrErrorData(100901);
  }
  
  public void gluGetTessProperty(int which, double[] value, int value_offset)
  {
    switch (which)
    {
    case 100142: 
      assert ((0.0D <= relTolerance) && (relTolerance <= 1.0D));
      value[value_offset] = relTolerance;
      break;
    case 100140: 
      assert ((windingRule == 100130) || (windingRule == 100131) || (windingRule == 100132) || (windingRule == 100133) || (windingRule == 100134));
      



      value[value_offset] = windingRule;
      break;
    case 100141: 
      assert ((boundaryOnly == true) || (!boundaryOnly));
      value[value_offset] = (boundaryOnly ? 1.0D : 0.0D);
      break;
    default: 
      value[value_offset] = 0.0D;
      callErrorOrErrorData(100900);
    }
  }
  
  public void gluTessNormal(double x, double y, double z)
  {
    normal[0] = x;
    normal[1] = y;
    normal[2] = z;
  }
  
  public void gluTessCallback(int which, GLUtessellatorCallback aCallback) {
    switch (which) {
    case 100100: 
      callBegin = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100106: 
      callBeginData = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100104: 
      callEdgeFlag = (aCallback == null ? NULL_CB : aCallback);
      


      flagBoundary = (aCallback != null);
      return;
    case 100110: 
      callEdgeFlagData = (this.callBegin = aCallback == null ? NULL_CB : aCallback);
      


      flagBoundary = (aCallback != null);
      return;
    case 100101: 
      callVertex = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100107: 
      callVertexData = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100102: 
      callEnd = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100108: 
      callEndData = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100103: 
      callError = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100109: 
      callErrorData = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100105: 
      callCombine = (aCallback == null ? NULL_CB : aCallback);
      return;
    case 100111: 
      callCombineData = (aCallback == null ? NULL_CB : aCallback);
      return;
    }
    
    

    callErrorOrErrorData(100900);
  }
  



  private boolean addVertex(double[] coords, Object vertexData)
  {
    GLUhalfEdge e = lastEdge;
    if (e == null)
    {

      e = Mesh.__gl_meshMakeEdge(mesh);
      if (e == null) return false;
      if (!Mesh.__gl_meshSplice(e, Sym)) { return false;
      }
    }
    else
    {
      if (Mesh.__gl_meshSplitEdge(e) == null) return false;
      e = Lnext;
    }
    

    Org.data = vertexData;
    Org.coords[0] = coords[0];
    Org.coords[1] = coords[1];
    Org.coords[2] = coords[2];
    





    winding = 1;
    Sym.winding = -1;
    
    lastEdge = e;
    
    return true;
  }
  
  private void cacheVertex(double[] coords, Object vertexData) {
    if (cache[cacheCount] == null) {
      cache[cacheCount] = new CachedVertex();
    }
    
    CachedVertex v = cache[cacheCount];
    
    data = vertexData;
    coords[0] = coords[0];
    coords[1] = coords[1];
    coords[2] = coords[2];
    cacheCount += 1;
  }
  
  private boolean flushCache()
  {
    CachedVertex[] v = cache;
    
    mesh = Mesh.__gl_meshNewMesh();
    if (mesh == null) { return false;
    }
    for (int i = 0; i < cacheCount; i++) {
      CachedVertex vertex = v[i];
      if (!addVertex(coords, data)) return false;
    }
    cacheCount = 0;
    flushCacheOnNextVertex = false;
    
    return true;
  }
  
  public void gluTessVertex(double[] coords, int coords_offset, Object vertexData)
  {
    boolean tooLarge = false;
    
    double[] clamped = new double[3];
    
    requireState(2);
    
    if (flushCacheOnNextVertex) {
      if (!flushCache()) {
        callErrorOrErrorData(100902);
        return;
      }
      lastEdge = null;
    }
    for (int i = 0; i < 3; i++) {
      double x = coords[(i + coords_offset)];
      if (x < -1.0E150D) {
        x = -1.0E150D;
        tooLarge = true;
      }
      if (x > 1.0E150D) {
        x = 1.0E150D;
        tooLarge = true;
      }
      clamped[i] = x;
    }
    if (tooLarge) {
      callErrorOrErrorData(100155);
    }
    
    if (mesh == null) {
      if (cacheCount < 100) {
        cacheVertex(clamped, vertexData);
        return;
      }
      if (!flushCache()) {
        callErrorOrErrorData(100902);
        return;
      }
    }
    
    if (!addVertex(clamped, vertexData)) {
      callErrorOrErrorData(100902);
    }
  }
  
  public void gluTessBeginPolygon(Object data)
  {
    requireState(0);
    
    state = 1;
    cacheCount = 0;
    flushCacheOnNextVertex = false;
    mesh = null;
    
    polygonData = data;
  }
  
  public void gluTessBeginContour()
  {
    requireState(1);
    
    state = 2;
    lastEdge = null;
    if (cacheCount > 0)
    {



      flushCacheOnNextVertex = true;
    }
  }
  
  public void gluTessEndContour()
  {
    requireState(2);
    state = 1;
  }
  
  public void gluTessEndPolygon()
  {
    try
    {
      requireState(1);
      state = 0;
      
      if (this.mesh == null) {
        if (!flagBoundary)
        {





          if (Render.__gl_renderCache(this)) {
            polygonData = null;
            return;
          }
        }
        if (!flushCache()) { throw new RuntimeException();
        }
      }
      


      Normal.__gl_projectPolygon(this);
      






      if (!Sweep.__gl_computeInterior(this)) {
        throw new RuntimeException();
      }
      
      GLUmesh mesh = this.mesh;
      if (!fatalError) {
        boolean rc = true;
        




        if (boundaryOnly) {
          rc = TessMono.__gl_meshSetWindingNumber(mesh, 1, true);
        } else {
          rc = TessMono.__gl_meshTessellateInterior(mesh);
        }
        if (!rc) { throw new RuntimeException();
        }
        Mesh.__gl_meshCheckMesh(mesh);
        
        if ((callBegin != NULL_CB) || (callEnd != NULL_CB) || (callVertex != NULL_CB) || (callEdgeFlag != NULL_CB) || (callBeginData != NULL_CB) || (callEndData != NULL_CB) || (callVertexData != NULL_CB) || (callEdgeFlagData != NULL_CB))
        {




          if (boundaryOnly) {
            Render.__gl_renderBoundary(this, mesh);
          } else {
            Render.__gl_renderMesh(this, mesh);
          }
        }
      }
      













      Mesh.__gl_meshDeleteMesh(mesh);
      polygonData = null;
      mesh = null;
    } catch (Exception e) {
      e.printStackTrace();
      callErrorOrErrorData(100902);
    }
  }
  



  public void gluBeginPolygon()
  {
    gluTessBeginPolygon(null);
    gluTessBeginContour();
  }
  

  public void gluNextContour(int type)
  {
    gluTessEndContour();
    gluTessBeginContour();
  }
  
  public void gluEndPolygon()
  {
    gluTessEndContour();
    gluTessEndPolygon();
  }
  
  void callBeginOrBeginData(int a) {
    if (callBeginData != NULL_CB) {
      callBeginData.beginData(a, polygonData);
    } else
      callBegin.begin(a);
  }
  
  void callVertexOrVertexData(Object a) {
    if (callVertexData != NULL_CB) {
      callVertexData.vertexData(a, polygonData);
    } else
      callVertex.vertex(a);
  }
  
  void callEdgeFlagOrEdgeFlagData(boolean a) {
    if (callEdgeFlagData != NULL_CB) {
      callEdgeFlagData.edgeFlagData(a, polygonData);
    } else
      callEdgeFlag.edgeFlag(a);
  }
  
  void callEndOrEndData() {
    if (callEndData != NULL_CB) {
      callEndData.endData(polygonData);
    } else
      callEnd.end();
  }
  
  void callCombineOrCombineData(double[] coords, Object[] vertexData, float[] weights, Object[] outData) {
    if (callCombineData != NULL_CB) {
      callCombineData.combineData(coords, vertexData, weights, outData, polygonData);
    } else
      callCombine.combine(coords, vertexData, weights, outData);
  }
  
  void callErrorOrErrorData(int a) {
    if (callErrorData != NULL_CB) {
      callErrorData.errorData(a, polygonData);
    } else {
      callError.error(a);
    }
  }
}
