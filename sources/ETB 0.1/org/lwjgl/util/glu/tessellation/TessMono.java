package org.lwjgl.util.glu.tessellation;


























































class TessMono
{
  TessMono() {}
  

























































  static boolean __gl_meshTessellateMonoRegion(GLUface face)
  {
    GLUhalfEdge up = anEdge;
    assert ((Lnext != up) && (Lnext.Lnext != up));
    
    while (Geom.VertLeq(Sym.Org, Org)) { up = Onext.Sym;
    }
    while (Geom.VertLeq(Org, Sym.Org)) { up = Lnext;
    }
    GLUhalfEdge lo = Onext.Sym;
    
    while (Lnext != lo) {
      if (Geom.VertLeq(Sym.Org, Org))
      {



        while ((Lnext != up) && ((Geom.EdgeGoesLeft(Lnext)) || (Geom.EdgeSign(Org, Sym.Org, Lnext.Sym.Org) <= 0.0D)))
        {
          GLUhalfEdge tempHalfEdge = Mesh.__gl_meshConnect(Lnext, lo);
          if (tempHalfEdge == null) return false;
          lo = Sym;
        }
        lo = Onext.Sym;
      }
      else {
        while ((Lnext != up) && ((Geom.EdgeGoesRight(Onext.Sym)) || (Geom.EdgeSign(Sym.Org, Org, Onext.Sym.Org) >= 0.0D)))
        {
          GLUhalfEdge tempHalfEdge = Mesh.__gl_meshConnect(up, Onext.Sym);
          if (tempHalfEdge == null) return false;
          up = Sym;
        }
        up = Lnext;
      }
    }
    



    assert (Lnext != up);
    while (Lnext.Lnext != up) {
      GLUhalfEdge tempHalfEdge = Mesh.__gl_meshConnect(Lnext, lo);
      if (tempHalfEdge == null) return false;
      lo = Sym;
    }
    
    return true;
  }
  



  public static boolean __gl_meshTessellateInterior(GLUmesh mesh)
  {
    GLUface next;
    


    for (GLUface f = fHead.next; f != fHead; f = next)
    {
      next = next;
      if ((inside) && 
        (!__gl_meshTessellateMonoRegion(f))) { return false;
      }
    }
    
    return true;
  }
  




  public static void __gl_meshDiscardExterior(GLUmesh mesh)
  {
    GLUface next;
    


    for (GLUface f = fHead.next; f != fHead; f = next)
    {
      next = next;
      if (!inside) {
        Mesh.__gl_meshZapFace(f);
      }
    }
  }
  





  public static boolean __gl_meshSetWindingNumber(GLUmesh mesh, int value, boolean keepOnlyBoundary)
  {
    GLUhalfEdge eNext;
    




    for (GLUhalfEdge e = eHead.next; e != eHead; e = eNext) {
      eNext = next;
      if (Sym.Lface.inside != Lface.inside)
      {

        winding = (Lface.inside ? value : -value);


      }
      else if (!keepOnlyBoundary) {
        winding = 0;
      }
      else if (!Mesh.__gl_meshDelete(e)) { return false;
      }
    }
    
    return true;
  }
}
