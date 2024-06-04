package org.lwjgl.util.glu.tessellation;


















































class Mesh
{
  private Mesh() {}
  
















































  static GLUhalfEdge MakeEdge(GLUhalfEdge eNext)
  {
    GLUhalfEdge e = new GLUhalfEdge(true);
    
    GLUhalfEdge eSym = new GLUhalfEdge(false);
    


    if (!first) {
      eNext = Sym;
    }
    



    GLUhalfEdge ePrev = Sym.next;
    next = ePrev;
    Sym.next = e;
    next = eNext;
    Sym.next = eSym;
    
    Sym = eSym;
    Onext = e;
    Lnext = eSym;
    Org = null;
    Lface = null;
    winding = 0;
    activeRegion = null;
    
    Sym = e;
    Onext = eSym;
    Lnext = e;
    Org = null;
    Lface = null;
    winding = 0;
    activeRegion = null;
    
    return e;
  }
  





  static void Splice(GLUhalfEdge a, GLUhalfEdge b)
  {
    GLUhalfEdge aOnext = Onext;
    GLUhalfEdge bOnext = Onext;
    
    Sym.Lnext = b;
    Sym.Lnext = a;
    Onext = bOnext;
    Onext = aOnext;
  }
  








  static void MakeVertex(GLUvertex newVertex, GLUhalfEdge eOrig, GLUvertex vNext)
  {
    GLUvertex vNew = newVertex;
    
    assert (vNew != null);
    

    GLUvertex vPrev = prev;
    prev = vPrev;
    next = vNew;
    next = vNext;
    prev = vNew;
    
    anEdge = eOrig;
    data = null;
    


    GLUhalfEdge e = eOrig;
    do {
      Org = vNew;
      e = Onext;
    } while (e != eOrig);
  }
  







  static void MakeFace(GLUface newFace, GLUhalfEdge eOrig, GLUface fNext)
  {
    GLUface fNew = newFace;
    
    assert (fNew != null);
    

    GLUface fPrev = prev;
    prev = fPrev;
    next = fNew;
    next = fNext;
    prev = fNew;
    
    anEdge = eOrig;
    data = null;
    trail = null;
    marked = false;
    



    inside = inside;
    

    GLUhalfEdge e = eOrig;
    do {
      Lface = fNew;
      e = Lnext;
    } while (e != eOrig);
  }
  





  static void KillEdge(GLUhalfEdge eDel)
  {
    if (!first) {
      eDel = Sym;
    }
    

    GLUhalfEdge eNext = next;
    GLUhalfEdge ePrev = Sym.next;
    Sym.next = ePrev;
    Sym.next = eNext;
  }
  



  static void KillVertex(GLUvertex vDel, GLUvertex newOrg)
  {
    GLUhalfEdge eStart = anEdge;
    


    GLUhalfEdge e = eStart;
    do {
      Org = newOrg;
      e = Onext;
    } while (e != eStart);
    

    GLUvertex vPrev = prev;
    GLUvertex vNext = next;
    prev = vPrev;
    next = vNext;
  }
  


  static void KillFace(GLUface fDel, GLUface newLface)
  {
    GLUhalfEdge eStart = anEdge;
    


    GLUhalfEdge e = eStart;
    do {
      Lface = newLface;
      e = Lnext;
    } while (e != eStart);
    

    GLUface fPrev = prev;
    GLUface fNext = next;
    prev = fPrev;
    next = fNext;
  }
  





  public static GLUhalfEdge __gl_meshMakeEdge(GLUmesh mesh)
  {
    GLUvertex newVertex1 = new GLUvertex();
    GLUvertex newVertex2 = new GLUvertex();
    GLUface newFace = new GLUface();
    

    GLUhalfEdge e = MakeEdge(eHead);
    if (e == null) { return null;
    }
    MakeVertex(newVertex1, e, vHead);
    MakeVertex(newVertex2, Sym, vHead);
    MakeFace(newFace, e, fHead);
    return e;
  }
  























  public static boolean __gl_meshSplice(GLUhalfEdge eOrg, GLUhalfEdge eDst)
  {
    boolean joiningLoops = false;
    boolean joiningVertices = false;
    
    if (eOrg == eDst) { return true;
    }
    if (Org != Org)
    {
      joiningVertices = true;
      KillVertex(Org, Org);
    }
    if (Lface != Lface)
    {
      joiningLoops = true;
      KillFace(Lface, Lface);
    }
    

    Splice(eDst, eOrg);
    
    if (!joiningVertices) {
      GLUvertex newVertex = new GLUvertex();
      



      MakeVertex(newVertex, eDst, Org);
      Org.anEdge = eOrg;
    }
    if (!joiningLoops) {
      GLUface newFace = new GLUface();
      



      MakeFace(newFace, eDst, Lface);
      Lface.anEdge = eOrg;
    }
    
    return true;
  }
  










  static boolean __gl_meshDelete(GLUhalfEdge eDel)
  {
    GLUhalfEdge eDelSym = Sym;
    boolean joiningLoops = false;
    



    if (Lface != Sym.Lface)
    {
      joiningLoops = true;
      KillFace(Lface, Sym.Lface);
    }
    
    if (Onext == eDel) {
      KillVertex(Org, null);
    }
    else {
      Sym.Lface.anEdge = Sym.Lnext;
      Org.anEdge = Onext;
      
      Splice(eDel, Sym.Lnext);
      if (!joiningLoops) {
        GLUface newFace = new GLUface();
        

        MakeFace(newFace, eDel, Lface);
      }
    }
    



    if (Onext == eDelSym) {
      KillVertex(Org, null);
      KillFace(Lface, null);
    }
    else {
      Lface.anEdge = Sym.Lnext;
      Org.anEdge = Onext;
      Splice(eDelSym, Sym.Lnext);
    }
    

    KillEdge(eDel);
    
    return true;
  }
  












  static GLUhalfEdge __gl_meshAddEdgeVertex(GLUhalfEdge eOrg)
  {
    GLUhalfEdge eNew = MakeEdge(eOrg);
    
    GLUhalfEdge eNewSym = Sym;
    

    Splice(eNew, Lnext);
    

    Org = Sym.Org;
    
    GLUvertex newVertex = new GLUvertex();
    
    MakeVertex(newVertex, eNewSym, Org);
    
    Lface = (eNewSym.Lface = Lface);
    
    return eNew;
  }
  





  public static GLUhalfEdge __gl_meshSplitEdge(GLUhalfEdge eOrg)
  {
    GLUhalfEdge tempHalfEdge = __gl_meshAddEdgeVertex(eOrg);
    
    GLUhalfEdge eNew = Sym;
    

    Splice(Sym, Sym.Sym.Lnext);
    Splice(Sym, eNew);
    

    Sym.Org = Org;
    Sym.Org.anEdge = Sym;
    Sym.Lface = Sym.Lface;
    winding = winding;
    Sym.winding = Sym.winding;
    
    return eNew;
  }
  











  static GLUhalfEdge __gl_meshConnect(GLUhalfEdge eOrg, GLUhalfEdge eDst)
  {
    boolean joiningLoops = false;
    GLUhalfEdge eNew = MakeEdge(eOrg);
    
    GLUhalfEdge eNewSym = Sym;
    
    if (Lface != Lface)
    {
      joiningLoops = true;
      KillFace(Lface, Lface);
    }
    

    Splice(eNew, Lnext);
    Splice(eNewSym, eDst);
    

    Org = Sym.Org;
    Org = Org;
    Lface = (eNewSym.Lface = Lface);
    

    Lface.anEdge = eNewSym;
    
    if (!joiningLoops) {
      GLUface newFace = new GLUface();
      

      MakeFace(newFace, eNew, Lface);
    }
    return eNew;
  }
  









  static void __gl_meshZapFace(GLUface fZap)
  {
    GLUhalfEdge eStart = anEdge;
    



    GLUhalfEdge eNext = Lnext;
    GLUhalfEdge e;
    do { e = eNext;
      eNext = Lnext;
      
      Lface = null;
      if (Sym.Lface == null)
      {

        if (Onext == e) {
          KillVertex(Org, null);
        }
        else {
          Org.anEdge = Onext;
          Splice(e, Sym.Lnext);
        }
        GLUhalfEdge eSym = Sym;
        if (Onext == eSym) {
          KillVertex(Org, null);
        }
        else {
          Org.anEdge = Onext;
          Splice(eSym, Sym.Lnext);
        }
        KillEdge(e);
      }
    } while (e != eStart);
    

    GLUface fPrev = prev;
    GLUface fNext = next;
    prev = fPrev;
    next = fNext;
  }
  







  public static GLUmesh __gl_meshNewMesh()
  {
    GLUmesh mesh = new GLUmesh();
    
    GLUvertex v = vHead;
    GLUface f = fHead;
    GLUhalfEdge e = eHead;
    GLUhalfEdge eSym = eHeadSym;
    
    prev = v;next = v;
    anEdge = null;
    data = null;
    
    next = (f.prev = f);
    anEdge = null;
    data = null;
    trail = null;
    marked = false;
    inside = false;
    
    next = e;
    Sym = eSym;
    Onext = null;
    Lnext = null;
    Org = null;
    Lface = null;
    winding = 0;
    activeRegion = null;
    
    next = eSym;
    Sym = e;
    Onext = null;
    Lnext = null;
    Org = null;
    Lface = null;
    winding = 0;
    activeRegion = null;
    
    return mesh;
  }
  



  static GLUmesh __gl_meshUnion(GLUmesh mesh1, GLUmesh mesh2)
  {
    GLUface f1 = fHead;
    GLUvertex v1 = vHead;
    GLUhalfEdge e1 = eHead;
    GLUface f2 = fHead;
    GLUvertex v2 = vHead;
    GLUhalfEdge e2 = eHead;
    

    if (next != f2) {
      prev.next = next;
      next.prev = prev;
      prev.next = f1;
      prev = prev;
    }
    
    if (next != v2) {
      prev.next = next;
      next.prev = prev;
      prev.next = v1;
      prev = prev;
    }
    
    if (next != e2) {
      Sym.next.Sym.next = next;
      next.Sym.next = Sym.next;
      Sym.next.Sym.next = e1;
      Sym.next = Sym.next;
    }
    
    return mesh1;
  }
  


  static void __gl_meshDeleteMeshZap(GLUmesh mesh)
  {
    GLUface fHead = mesh.fHead;
    
    while (next != fHead) {
      __gl_meshZapFace(next);
    }
    assert (vHead.next == vHead);
  }
  


  public static void __gl_meshDeleteMesh(GLUmesh mesh)
  {
    GLUface fNext;
    

    for (GLUface f = fHead.next; f != fHead; f = fNext) {
      fNext = next;
    }
    GLUvertex vNext;
    for (GLUvertex v = vHead.next; v != vHead; v = vNext) {
      vNext = next;
    }
    GLUhalfEdge eNext;
    for (GLUhalfEdge e = eHead.next; e != eHead; e = eNext)
    {
      eNext = next;
    }
  }
  

  public static void __gl_meshCheckMesh(GLUmesh mesh)
  {
    GLUface fHead = mesh.fHead;
    GLUvertex vHead = mesh.vHead;
    GLUhalfEdge eHead = mesh.eHead;
    



    GLUface fPrev = fHead;
    GLUface f; for (fPrev = fHead; (f = next) != fHead; fPrev = f) {
      assert (prev == fPrev);
      GLUhalfEdge e = anEdge;
      do {
        assert (Sym != e);
        assert (Sym.Sym == e);
        assert (Lnext.Onext.Sym == e);
        assert (Onext.Sym.Lnext == e);
        assert (Lface == f);
        e = Lnext;
      } while (e != anEdge);
    }
    assert ((prev == fPrev) && (anEdge == null) && (data == null));
    
    GLUvertex vPrev = vHead;
    GLUvertex v; for (vPrev = vHead; (v = next) != vHead; vPrev = v) {
      assert (prev == vPrev);
      GLUhalfEdge e = anEdge;
      do {
        assert (Sym != e);
        assert (Sym.Sym == e);
        assert (Lnext.Onext.Sym == e);
        assert (Onext.Sym.Lnext == e);
        assert (Org == v);
        e = Onext;
      } while (e != anEdge);
    }
    assert ((prev == vPrev) && (anEdge == null) && (data == null));
    
    GLUhalfEdge ePrev = eHead;
    GLUhalfEdge e; for (ePrev = eHead; (e = next) != eHead; ePrev = e) {
      assert (Sym.next == Sym);
      assert (Sym != e);
      assert (Sym.Sym == e);
      assert (Org != null);
      assert (Sym.Org != null);
      assert (Lnext.Onext.Sym == e);
      assert (Onext.Sym.Lnext == e);
    }
    assert ((Sym.next == Sym) && (Sym == eHeadSym) && (Sym.Sym == e) && (Org == null) && (Sym.Org == null) && (Lface == null) && (Sym.Lface == null));
  }
}
