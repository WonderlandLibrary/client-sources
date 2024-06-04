package org.lwjgl.util.glu;












public class Registry
  extends Util
{
  private static final String versionString = "1.3";
  










  private static final String extensionString = "GLU_EXT_nurbs_tessellator GLU_EXT_object_space_tess ";
  











  public Registry() {}
  










  public static String gluGetString(int name)
  {
    if (name == 100800)
      return "1.3";
    if (name == 100801) {
      return "GLU_EXT_nurbs_tessellator GLU_EXT_object_space_tess ";
    }
    return null;
  }
  









  public static boolean gluCheckExtension(String extName, String extString)
  {
    if ((extString == null) || (extName == null)) {
      return false;
    }
    return extString.indexOf(extName) != -1;
  }
}
