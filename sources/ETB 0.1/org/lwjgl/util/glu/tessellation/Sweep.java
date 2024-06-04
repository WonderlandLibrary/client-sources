package org.lwjgl.util.glu.tessellation;


























class Sweep
{
  private static final boolean TOLERANCE_NONZERO = false;
  























  private static final double SENTINEL_COORD = 4.0E150D;
  
























  private Sweep() {}
  
























  private static void DebugEvent(GLUtessellatorImpl tess) {}
  
























  private static void AddWinding(GLUhalfEdge eDst, GLUhalfEdge eSrc)
  {
    winding += winding;
    Sym.winding += Sym.winding;
  }
  
  private static ActiveRegion RegionBelow(ActiveRegion r)
  {
    return (ActiveRegion)Dict.dictKey(Dict.dictPred(nodeUp));
  }
  
  private static ActiveRegion RegionAbove(ActiveRegion r) {
    return (ActiveRegion)Dict.dictKey(Dict.dictSucc(nodeUp));
  }
  










  static boolean EdgeLeq(GLUtessellatorImpl tess, ActiveRegion reg1, ActiveRegion reg2)
  {
    GLUvertex event = tess.event;
    


    GLUhalfEdge e1 = eUp;
    GLUhalfEdge e2 = eUp;
    
    if (Sym.Org == event) {
      if (Sym.Org == event)
      {


        if (Geom.VertLeq(Org, Org)) {
          return Geom.EdgeSign(Sym.Org, Org, Org) <= 0.0D;
        }
        return Geom.EdgeSign(Sym.Org, Org, Org) >= 0.0D;
      }
      return Geom.EdgeSign(Sym.Org, event, Org) <= 0.0D;
    }
    if (Sym.Org == event) {
      return Geom.EdgeSign(Sym.Org, event, Org) >= 0.0D;
    }
    

    double t1 = Geom.EdgeEval(Sym.Org, event, Org);
    double t2 = Geom.EdgeEval(Sym.Org, event, Org);
    return t1 >= t2;
  }
  
  static void DeleteRegion(GLUtessellatorImpl tess, ActiveRegion reg)
  {
    if (fixUpperEdge)
    {



      assert (eUp.winding == 0);
    }
    eUp.activeRegion = null;
    Dict.dictDelete(dict, nodeUp);
  }
  



  static boolean FixUpperEdge(ActiveRegion reg, GLUhalfEdge newEdge)
  {
    assert (fixUpperEdge);
    if (!Mesh.__gl_meshDelete(eUp)) return false;
    fixUpperEdge = false;
    eUp = newEdge;
    activeRegion = reg;
    
    return true;
  }
  
  static ActiveRegion TopLeftRegion(ActiveRegion reg) {
    GLUvertex org = eUp.Org;
    

    do
    {
      reg = RegionAbove(reg);
    } while (eUp.Org == org);
    



    if (fixUpperEdge) {
      GLUhalfEdge e = Mesh.__gl_meshConnect(RegionBeloweUp.Sym, eUp.Lnext);
      if (e == null) return null;
      if (!FixUpperEdge(reg, e)) return null;
      reg = RegionAbove(reg);
    }
    return reg;
  }
  
  static ActiveRegion TopRightRegion(ActiveRegion reg) {
    GLUvertex dst = eUp.Sym.Org;
    
    do
    {
      reg = RegionAbove(reg);
    } while (eUp.Sym.Org == dst);
    return reg;
  }
  







  static ActiveRegion AddRegionBelow(GLUtessellatorImpl tess, ActiveRegion regAbove, GLUhalfEdge eNewUp)
  {
    ActiveRegion regNew = new ActiveRegion();
    if (regNew == null) { throw new RuntimeException();
    }
    eUp = eNewUp;
    
    nodeUp = Dict.dictInsertBefore(dict, nodeUp, regNew);
    if (nodeUp == null) throw new RuntimeException();
    fixUpperEdge = false;
    sentinel = false;
    dirty = false;
    
    activeRegion = regNew;
    return regNew;
  }
  
  static boolean IsWindingInside(GLUtessellatorImpl tess, int n) {
    switch (windingRule) {
    case 100130: 
      return (n & 0x1) != 0;
    case 100131: 
      return n != 0;
    case 100132: 
      return n > 0;
    case 100133: 
      return n < 0;
    case 100134: 
      return (n >= 2) || (n <= -2);
    }
    
    
    throw new InternalError();
  }
  

  static void ComputeWinding(GLUtessellatorImpl tess, ActiveRegion reg)
  {
    windingNumber = (RegionAbovewindingNumber + eUp.winding);
    inside = IsWindingInside(tess, windingNumber);
  }
  







  static void FinishRegion(GLUtessellatorImpl tess, ActiveRegion reg)
  {
    GLUhalfEdge e = eUp;
    GLUface f = Lface;
    
    inside = inside;
    anEdge = e;
    DeleteRegion(tess, reg);
  }
  
















  static GLUhalfEdge FinishLeftRegions(GLUtessellatorImpl tess, ActiveRegion regFirst, ActiveRegion regLast)
  {
    ActiveRegion regPrev = regFirst;
    GLUhalfEdge ePrev = eUp;
    while (regPrev != regLast) {
      fixUpperEdge = false;
      ActiveRegion reg = RegionBelow(regPrev);
      GLUhalfEdge e = eUp;
      if (Org != Org) {
        if (!fixUpperEdge)
        {





          FinishRegion(tess, regPrev);
          break;
        }
        


        e = Mesh.__gl_meshConnect(Onext.Sym, Sym);
        if (e == null) throw new RuntimeException();
        if (!FixUpperEdge(reg, e)) { throw new RuntimeException();
        }
      }
      
      if (Onext != e) {
        if (!Mesh.__gl_meshSplice(Sym.Lnext, e)) throw new RuntimeException();
        if (!Mesh.__gl_meshSplice(ePrev, e)) throw new RuntimeException();
      }
      FinishRegion(tess, regPrev);
      ePrev = eUp;
      regPrev = reg;
    }
    return ePrev;
  }
  














  static void AddRightEdges(GLUtessellatorImpl tess, ActiveRegion regUp, GLUhalfEdge eFirst, GLUhalfEdge eLast, GLUhalfEdge eTopLeft, boolean cleanUp)
  {
    boolean firstTime = true;
    

    GLUhalfEdge e = eFirst;
    do {
      assert (Geom.VertLeq(Org, Sym.Org));
      AddRegionBelow(tess, regUp, Sym);
      e = Onext;
    } while (e != eLast);
    




    if (eTopLeft == null) {
      eTopLeft = RegionBeloweUp.Sym.Onext;
    }
    ActiveRegion regPrev = regUp;
    GLUhalfEdge ePrev = eTopLeft;
    ActiveRegion reg;
    for (;;) { reg = RegionBelow(regPrev);
      e = eUp.Sym;
      if (Org != Org)
        break;
      if (Onext != ePrev)
      {
        if (!Mesh.__gl_meshSplice(Sym.Lnext, e)) throw new RuntimeException();
        if (!Mesh.__gl_meshSplice(Sym.Lnext, e)) { throw new RuntimeException();
        }
      }
      windingNumber -= winding;
      inside = IsWindingInside(tess, windingNumber);
      



      dirty = true;
      if ((!firstTime) && (CheckForRightSplice(tess, regPrev))) {
        AddWinding(e, ePrev);
        DeleteRegion(tess, regPrev);
        if (!Mesh.__gl_meshDelete(ePrev)) throw new RuntimeException();
      }
      firstTime = false;
      regPrev = reg;
      ePrev = e;
    }
    dirty = true;
    assert (windingNumber - winding == windingNumber);
    
    if (cleanUp)
    {
      WalkDirtyRegions(tess, regPrev);
    }
  }
  

  static void CallCombine(GLUtessellatorImpl tess, GLUvertex isect, Object[] data, float[] weights, boolean needed)
  {
    double[] coords = new double[3];
    

    coords[0] = coords[0];
    coords[1] = coords[1];
    coords[2] = coords[2];
    
    Object[] outData = new Object[1];
    tess.callCombineOrCombineData(coords, data, weights, outData);
    data = outData[0];
    if (data == null) {
      if (!needed) {
        data = data[0];
      } else if (!fatalError)
      {



        tess.callErrorOrErrorData(100156);
        fatalError = true;
      }
    }
  }
  




  static void SpliceMergeVertices(GLUtessellatorImpl tess, GLUhalfEdge e1, GLUhalfEdge e2)
  {
    Object[] data = new Object[4];
    float[] weights = { 0.5F, 0.5F, 0.0F, 0.0F };
    
    data[0] = Org.data;
    data[1] = Org.data;
    CallCombine(tess, Org, data, weights, false);
    if (!Mesh.__gl_meshSplice(e1, e2)) { throw new RuntimeException();
    }
  }
  






  static void VertexWeights(GLUvertex isect, GLUvertex org, GLUvertex dst, float[] weights)
  {
    double t1 = Geom.VertL1dist(org, isect);
    double t2 = Geom.VertL1dist(dst, isect);
    
    weights[0] = ((float)(0.5D * t2 / (t1 + t2)));
    weights[1] = ((float)(0.5D * t1 / (t1 + t2)));
    coords[0] += weights[0] * coords[0] + weights[1] * coords[0];
    coords[1] += weights[0] * coords[1] + weights[1] * coords[1];
    coords[2] += weights[0] * coords[2] + weights[1] * coords[2];
  }
  







  static void GetIntersectData(GLUtessellatorImpl tess, GLUvertex isect, GLUvertex orgUp, GLUvertex dstUp, GLUvertex orgLo, GLUvertex dstLo)
  {
    Object[] data = new Object[4];
    float[] weights = new float[4];
    float[] weights1 = new float[2];
    float[] weights2 = new float[2];
    
    data[0] = data;
    data[1] = data;
    data[2] = data;
    data[3] = data; double 
    
      tmp73_72 = (coords[2] = 0.0D);coords[1] = tmp73_72;coords[0] = tmp73_72;
    VertexWeights(isect, orgUp, dstUp, weights1);
    VertexWeights(isect, orgLo, dstLo, weights2);
    System.arraycopy(weights1, 0, weights, 0, 2);
    System.arraycopy(weights2, 0, weights, 2, 2);
    
    CallCombine(tess, isect, data, weights, true);
  }
  
























  static boolean CheckForRightSplice(GLUtessellatorImpl tess, ActiveRegion regUp)
  {
    ActiveRegion regLo = RegionBelow(regUp);
    GLUhalfEdge eUp = eUp;
    GLUhalfEdge eLo = eUp;
    
    if (Geom.VertLeq(Org, Org)) {
      if (Geom.EdgeSign(Sym.Org, Org, Org) > 0.0D) { return false;
      }
      
      if (!Geom.VertEq(Org, Org))
      {
        if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
        if (!Mesh.__gl_meshSplice(eUp, Sym.Lnext)) throw new RuntimeException();
        dirty = (regLo.dirty = 1);
      }
      else if (Org != Org)
      {
        pq.pqDelete(Org.pqHandle);
        SpliceMergeVertices(tess, Sym.Lnext, eUp);
      }
    } else {
      if (Geom.EdgeSign(Sym.Org, Org, Org) < 0.0D) { return false;
      }
      
      RegionAbovedirty = (regUp.dirty = 1);
      if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
      if (!Mesh.__gl_meshSplice(Sym.Lnext, eUp)) throw new RuntimeException();
    }
    return true;
  }
  

















  static boolean CheckForLeftSplice(GLUtessellatorImpl tess, ActiveRegion regUp)
  {
    ActiveRegion regLo = RegionBelow(regUp);
    GLUhalfEdge eUp = eUp;
    GLUhalfEdge eLo = eUp;
    

    assert (!Geom.VertEq(Sym.Org, Sym.Org));
    
    if (Geom.VertLeq(Sym.Org, Sym.Org)) {
      if (Geom.EdgeSign(Sym.Org, Sym.Org, Org) < 0.0D) { return false;
      }
      
      RegionAbovedirty = (regUp.dirty = 1);
      GLUhalfEdge e = Mesh.__gl_meshSplitEdge(eUp);
      if (e == null) throw new RuntimeException();
      if (!Mesh.__gl_meshSplice(Sym, e)) throw new RuntimeException();
      Lface.inside = inside;
    } else {
      if (Geom.EdgeSign(Sym.Org, Sym.Org, Org) > 0.0D) { return false;
      }
      
      dirty = (regLo.dirty = 1);
      GLUhalfEdge e = Mesh.__gl_meshSplitEdge(eLo);
      if (e == null) throw new RuntimeException();
      if (!Mesh.__gl_meshSplice(Lnext, Sym)) throw new RuntimeException();
      Sym.Lface.inside = inside;
    }
    return true;
  }
  









  static boolean CheckForIntersect(GLUtessellatorImpl tess, ActiveRegion regUp)
  {
    ActiveRegion regLo = RegionBelow(regUp);
    GLUhalfEdge eUp = eUp;
    GLUhalfEdge eLo = eUp;
    GLUvertex orgUp = Org;
    GLUvertex orgLo = Org;
    GLUvertex dstUp = Sym.Org;
    GLUvertex dstLo = Sym.Org;
    
    GLUvertex isect = new GLUvertex();
    


    assert (!Geom.VertEq(dstLo, dstUp));
    assert (Geom.EdgeSign(dstUp, event, orgUp) <= 0.0D);
    assert (Geom.EdgeSign(dstLo, event, orgLo) >= 0.0D);
    assert ((orgUp != event) && (orgLo != event));
    assert ((!fixUpperEdge) && (!fixUpperEdge));
    
    if (orgUp == orgLo) { return false;
    }
    double tMinUp = Math.min(t, t);
    double tMaxLo = Math.max(t, t);
    if (tMinUp > tMaxLo) { return false;
    }
    if (Geom.VertLeq(orgUp, orgLo)) {
      if (Geom.EdgeSign(dstLo, orgUp, orgLo) > 0.0D) return false;
    }
    else if (Geom.EdgeSign(dstUp, orgLo, orgUp) < 0.0D) { return false;
    }
    

    DebugEvent(tess);
    
    Geom.EdgeIntersect(dstUp, orgUp, dstLo, orgLo, isect);
    
    assert (Math.min(t, t) <= t);
    assert (t <= Math.max(t, t));
    assert (Math.min(s, s) <= s);
    assert (s <= Math.max(s, s));
    
    if (Geom.VertLeq(isect, event))
    {





      s = event.s;
      t = event.t;
    }
    





    GLUvertex orgMin = Geom.VertLeq(orgUp, orgLo) ? orgUp : orgLo;
    if (Geom.VertLeq(orgMin, isect)) {
      s = s;
      t = t;
    }
    
    if ((Geom.VertEq(isect, orgUp)) || (Geom.VertEq(isect, orgLo)))
    {
      CheckForRightSplice(tess, regUp);
      return false;
    }
    
    if (((!Geom.VertEq(dstUp, event)) && (Geom.EdgeSign(dstUp, event, isect) >= 0.0D)) || ((!Geom.VertEq(dstLo, event)) && (Geom.EdgeSign(dstLo, event, isect) <= 0.0D)))
    {






      if (dstLo == event)
      {
        if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
        if (!Mesh.__gl_meshSplice(Sym, eUp)) throw new RuntimeException();
        regUp = TopLeftRegion(regUp);
        if (regUp == null) throw new RuntimeException();
        eUp = RegionBeloweUp;
        FinishLeftRegions(tess, RegionBelow(regUp), regLo);
        AddRightEdges(tess, regUp, Sym.Lnext, eUp, eUp, true);
        return true;
      }
      if (dstUp == event)
      {
        if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
        if (!Mesh.__gl_meshSplice(Lnext, Sym.Lnext)) throw new RuntimeException();
        regLo = regUp;
        regUp = TopRightRegion(regUp);
        GLUhalfEdge e = RegionBeloweUp.Sym.Onext;
        eUp = Sym.Lnext;
        eLo = FinishLeftRegions(tess, regLo, null);
        AddRightEdges(tess, regUp, Onext, Sym.Onext, e, true);
        return true;
      }
      



      if (Geom.EdgeSign(dstUp, event, isect) >= 0.0D) {
        RegionAbovedirty = (regUp.dirty = 1);
        if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
        Org.s = event.s;
        Org.t = event.t;
      }
      if (Geom.EdgeSign(dstLo, event, isect) <= 0.0D) {
        dirty = (regLo.dirty = 1);
        if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
        Org.s = event.s;
        Org.t = event.t;
      }
      
      return false;
    }
    








    if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
    if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
    if (!Mesh.__gl_meshSplice(Sym.Lnext, eUp)) throw new RuntimeException();
    Org.s = s;
    Org.t = t;
    Org.pqHandle = pq.pqInsert(Org);
    if (Org.pqHandle == Long.MAX_VALUE) {
      pq.pqDeletePriorityQ();
      pq = null;
      throw new RuntimeException();
    }
    GetIntersectData(tess, Org, orgUp, dstUp, orgLo, dstLo);
    RegionAbovedirty = (regUp.dirty = regLo.dirty = 1);
    return false;
  }
  







  static void WalkDirtyRegions(GLUtessellatorImpl tess, ActiveRegion regUp)
  {
    ActiveRegion regLo = RegionBelow(regUp);
    

    for (;;)
    {
      if (dirty) {
        regUp = regLo;
        regLo = RegionBelow(regLo);
      } else {
        if (!dirty) {
          regLo = regUp;
          regUp = RegionAbove(regUp);
          if ((regUp == null) || (!dirty))
          {
            return;
          }
        }
        dirty = false;
        GLUhalfEdge eUp = eUp;
        GLUhalfEdge eLo = eUp;
        
        if (Sym.Org != Sym.Org)
        {
          if (CheckForLeftSplice(tess, regUp))
          {




            if (fixUpperEdge) {
              DeleteRegion(tess, regLo);
              if (!Mesh.__gl_meshDelete(eLo)) throw new RuntimeException();
              regLo = RegionBelow(regUp);
              eLo = eUp;
            } else if (fixUpperEdge) {
              DeleteRegion(tess, regUp);
              if (!Mesh.__gl_meshDelete(eUp)) throw new RuntimeException();
              regUp = RegionAbove(regLo);
              eUp = eUp;
            }
          }
        }
        if (Org != Org) {
          if ((Sym.Org != Sym.Org) && (!fixUpperEdge) && (!fixUpperEdge) && ((Sym.Org == event) || (Sym.Org == event)))
          {









            if (!CheckForIntersect(tess, regUp)) {}



          }
          else
          {

            CheckForRightSplice(tess, regUp);
          }
        }
        if ((Org == Org) && (Sym.Org == Sym.Org))
        {
          AddWinding(eLo, eUp);
          DeleteRegion(tess, regUp);
          if (!Mesh.__gl_meshDelete(eUp)) throw new RuntimeException();
          regUp = RegionAbove(regLo);
        }
      }
    }
  }
  
































  static void ConnectRightVertex(GLUtessellatorImpl tess, ActiveRegion regUp, GLUhalfEdge eBottomLeft)
  {
    GLUhalfEdge eTopLeft = Onext;
    ActiveRegion regLo = RegionBelow(regUp);
    GLUhalfEdge eUp = eUp;
    GLUhalfEdge eLo = eUp;
    boolean degenerate = false;
    
    if (Sym.Org != Sym.Org) {
      CheckForIntersect(tess, regUp);
    }
    



    if (Geom.VertEq(Org, event)) {
      if (!Mesh.__gl_meshSplice(Sym.Lnext, eUp)) throw new RuntimeException();
      regUp = TopLeftRegion(regUp);
      if (regUp == null) throw new RuntimeException();
      eTopLeft = RegionBeloweUp;
      FinishLeftRegions(tess, RegionBelow(regUp), regLo);
      degenerate = true;
    }
    if (Geom.VertEq(Org, event)) {
      if (!Mesh.__gl_meshSplice(eBottomLeft, Sym.Lnext)) throw new RuntimeException();
      eBottomLeft = FinishLeftRegions(tess, regLo, null);
      degenerate = true;
    }
    if (degenerate) {
      AddRightEdges(tess, regUp, Onext, eTopLeft, eTopLeft, true); return;
    }
    

    GLUhalfEdge eNew;
    

    if (Geom.VertLeq(Org, Org)) {
      eNew = Sym.Lnext;
    } else {
      eNew = eUp;
    }
    GLUhalfEdge eNew = Mesh.__gl_meshConnect(Onext.Sym, eNew);
    if (eNew == null) { throw new RuntimeException();
    }
    


    AddRightEdges(tess, regUp, eNew, Onext, Onext, false);
    Sym.activeRegion.fixUpperEdge = true;
    WalkDirtyRegions(tess, regUp);
  }
  

















  static void ConnectLeftDegenerate(GLUtessellatorImpl tess, ActiveRegion regUp, GLUvertex vEvent)
  {
    GLUhalfEdge e = eUp;
    if (Geom.VertEq(Org, vEvent))
    {


      if (!$assertionsDisabled) throw new AssertionError();
      SpliceMergeVertices(tess, e, anEdge);
      return;
    }
    
    if (!Geom.VertEq(Sym.Org, vEvent))
    {
      if (Mesh.__gl_meshSplitEdge(Sym) == null) throw new RuntimeException();
      if (fixUpperEdge)
      {
        if (!Mesh.__gl_meshDelete(Onext)) throw new RuntimeException();
        fixUpperEdge = false;
      }
      if (!Mesh.__gl_meshSplice(anEdge, e)) throw new RuntimeException();
      SweepEvent(tess, vEvent);
      return;
    }
    



    if (!$assertionsDisabled) throw new AssertionError();
    regUp = TopRightRegion(regUp);
    ActiveRegion reg = RegionBelow(regUp);
    GLUhalfEdge eTopRight = eUp.Sym;
    GLUhalfEdge eLast; GLUhalfEdge eTopLeft = eLast = Onext;
    if (fixUpperEdge)
    {


      assert (eTopLeft != eTopRight);
      DeleteRegion(tess, reg);
      if (!Mesh.__gl_meshDelete(eTopRight)) throw new RuntimeException();
      eTopRight = Sym.Lnext;
    }
    if (!Mesh.__gl_meshSplice(anEdge, eTopRight)) throw new RuntimeException();
    if (!Geom.EdgeGoesLeft(eTopLeft))
    {
      eTopLeft = null;
    }
    AddRightEdges(tess, regUp, Onext, eLast, eTopLeft, true);
  }
  

















  static void ConnectLeftVertex(GLUtessellatorImpl tess, GLUvertex vEvent)
  {
    ActiveRegion tmp = new ActiveRegion();
    



    eUp = anEdge.Sym;
    
    ActiveRegion regUp = (ActiveRegion)Dict.dictKey(Dict.dictSearch(dict, tmp));
    ActiveRegion regLo = RegionBelow(regUp);
    GLUhalfEdge eUp = eUp;
    GLUhalfEdge eLo = eUp;
    

    if (Geom.EdgeSign(Sym.Org, vEvent, Org) == 0.0D) {
      ConnectLeftDegenerate(tess, regUp, vEvent);
      return;
    }
    



    ActiveRegion reg = Geom.VertLeq(Sym.Org, Sym.Org) ? regUp : regLo;
    
    if ((inside) || (fixUpperEdge)) { GLUhalfEdge eNew;
      if (reg == regUp) {
        GLUhalfEdge eNew = Mesh.__gl_meshConnect(anEdge.Sym, Lnext);
        if (eNew == null) throw new RuntimeException();
      } else {
        GLUhalfEdge tempHalfEdge = Mesh.__gl_meshConnect(Sym.Onext.Sym, anEdge);
        if (tempHalfEdge == null) { throw new RuntimeException();
        }
        eNew = Sym;
      }
      if (fixUpperEdge) {
        if (!FixUpperEdge(reg, eNew)) throw new RuntimeException();
      } else {
        ComputeWinding(tess, AddRegionBelow(tess, regUp, eNew));
      }
      SweepEvent(tess, vEvent);

    }
    else
    {
      AddRightEdges(tess, regUp, anEdge, anEdge, null, true);
    }
  }
  







  static void SweepEvent(GLUtessellatorImpl tess, GLUvertex vEvent)
  {
    event = vEvent;
    DebugEvent(tess);
    




    GLUhalfEdge e = anEdge;
    while (activeRegion == null) {
      e = Onext;
      if (e == anEdge)
      {
        ConnectLeftVertex(tess, vEvent);
        return;
      }
    }
    







    ActiveRegion regUp = TopLeftRegion(activeRegion);
    if (regUp == null) throw new RuntimeException();
    ActiveRegion reg = RegionBelow(regUp);
    GLUhalfEdge eTopLeft = eUp;
    GLUhalfEdge eBottomLeft = FinishLeftRegions(tess, reg, null);
    





    if (Onext == eTopLeft)
    {
      ConnectRightVertex(tess, regUp, eBottomLeft);
    } else {
      AddRightEdges(tess, regUp, Onext, eTopLeft, eTopLeft, true);
    }
  }
  












  static void AddSentinel(GLUtessellatorImpl tess, double t)
  {
    ActiveRegion reg = new ActiveRegion();
    if (reg == null) { throw new RuntimeException();
    }
    GLUhalfEdge e = Mesh.__gl_meshMakeEdge(mesh);
    if (e == null) { throw new RuntimeException();
    }
    Org.s = 4.0E150D;
    Org.t = t;
    Sym.Org.s = -4.0E150D;
    Sym.Org.t = t;
    event = Sym.Org;
    
    eUp = e;
    windingNumber = 0;
    inside = false;
    fixUpperEdge = false;
    sentinel = true;
    dirty = false;
    nodeUp = Dict.dictInsert(dict, reg);
    if (nodeUp == null) { throw new RuntimeException();
    }
  }
  




  static void InitEdgeDict(GLUtessellatorImpl tess)
  {
    dict = Dict.dictNewDict(tess, new Dict.DictLeq() {
      public boolean leq(Object frame, Object key1, Object key2) {
        return Sweep.EdgeLeq(val$tess, (ActiveRegion)key1, (ActiveRegion)key2);
      }
    });
    if (dict == null) { throw new RuntimeException();
    }
    AddSentinel(tess, -4.0E150D);
    AddSentinel(tess, 4.0E150D);
  }
  

  static void DoneEdgeDict(GLUtessellatorImpl tess)
  {
    int fixedEdges = 0;
    
    ActiveRegion reg;
    while ((reg = (ActiveRegion)Dict.dictKey(Dict.dictMin(dict))) != null)
    {




      if (!sentinel) {
        assert (fixUpperEdge);
        if (!$assertionsDisabled) { fixedEdges++; if (fixedEdges != 1) throw new AssertionError();
        } }
      assert (windingNumber == 0);
      DeleteRegion(tess, reg);
    }
    
    Dict.dictDeleteDict(dict);
  }
  




  static void RemoveDegenerateEdges(GLUtessellatorImpl tess)
  {
    GLUhalfEdge eHead = mesh.eHead;
    
    GLUhalfEdge eNext;
    for (GLUhalfEdge e = next; e != eHead; e = eNext) {
      eNext = next;
      GLUhalfEdge eLnext = Lnext;
      
      if ((Geom.VertEq(Org, Sym.Org)) && (Lnext.Lnext != e))
      {

        SpliceMergeVertices(tess, eLnext, e);
        if (!Mesh.__gl_meshDelete(e)) throw new RuntimeException();
        e = eLnext;
        eLnext = Lnext;
      }
      if (Lnext == e)
      {

        if (eLnext != e) {
          if ((eLnext == eNext) || (eLnext == Sym)) {
            eNext = next;
          }
          if (!Mesh.__gl_meshDelete(eLnext)) throw new RuntimeException();
        }
        if ((e == eNext) || (e == Sym)) {
          eNext = next;
        }
        if (!Mesh.__gl_meshDelete(e)) { throw new RuntimeException();
        }
      }
    }
  }
  






  static boolean InitPriorityQ(GLUtessellatorImpl tess)
  {
    PriorityQ pq = tess.pq = PriorityQ.pqNewPriorityQ(new PriorityQ.Leq() {
      public boolean leq(Object key1, Object key2) {
        return Geom.VertLeq((GLUvertex)key1, (GLUvertex)key2);
      }
    });
    if (pq == null) { return false;
    }
    GLUvertex vHead = mesh.vHead;
    for (GLUvertex v = next; v != vHead; v = next) {
      pqHandle = pq.pqInsert(v);
      if (pqHandle == Long.MAX_VALUE) break;
    }
    if ((v != vHead) || (!pq.pqInit())) {
      tess.pq.pqDeletePriorityQ();
      tess.pq = null;
      return false;
    }
    
    return true;
  }
  
  static void DonePriorityQ(GLUtessellatorImpl tess)
  {
    pq.pqDeletePriorityQ();
  }
  









  static boolean RemoveDegenerateFaces(GLUmesh mesh)
  {
    GLUface fNext;
    







    for (GLUface f = fHead.next; f != fHead; f = fNext) {
      fNext = next;
      GLUhalfEdge e = anEdge;
      assert (Lnext != e);
      
      if (Lnext.Lnext == e)
      {
        AddWinding(Onext, e);
        if (!Mesh.__gl_meshDelete(e)) return false;
      }
    }
    return true;
  }
  








  public static boolean __gl_computeInterior(GLUtessellatorImpl tess)
  {
    fatalError = false;
    






    RemoveDegenerateEdges(tess);
    if (!InitPriorityQ(tess)) return false;
    InitEdgeDict(tess);
    
    GLUvertex v;
    while ((v = (GLUvertex)pq.pqExtractMin()) != null) {
      for (;;) {
        GLUvertex vNext = (GLUvertex)pq.pqMinimum();
        if ((vNext == null) || (!Geom.VertEq(vNext, v))) {
          break;
        }
        












        vNext = (GLUvertex)pq.pqExtractMin();
        SpliceMergeVertices(tess, anEdge, anEdge);
      }
      SweepEvent(tess, v);
    }
    


    event = dictKeydictMindict))).eUp.Org;
    DebugEvent(tess);
    DoneEdgeDict(tess);
    DonePriorityQ(tess);
    
    if (!RemoveDegenerateFaces(mesh)) return false;
    Mesh.__gl_meshCheckMesh(mesh);
    
    return true;
  }
}
