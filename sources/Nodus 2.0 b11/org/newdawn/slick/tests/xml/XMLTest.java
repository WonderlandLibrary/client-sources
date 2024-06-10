/*   1:    */ package org.newdawn.slick.tests.xml;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.newdawn.slick.SlickException;
/*   5:    */ import org.newdawn.slick.util.xml.XMLElement;
/*   6:    */ import org.newdawn.slick.util.xml.XMLElementList;
/*   7:    */ import org.newdawn.slick.util.xml.XMLParser;
/*   8:    */ 
/*   9:    */ public class XMLTest
/*  10:    */ {
/*  11:    */   private static void fail(String message)
/*  12:    */   {
/*  13: 20 */     throw new RuntimeException(message);
/*  14:    */   }
/*  15:    */   
/*  16:    */   private static void assertNotNull(Object object1)
/*  17:    */   {
/*  18: 29 */     if (object1 == null) {
/*  19: 30 */       throw new RuntimeException("TEST FAILS: " + object1 + " must not be null");
/*  20:    */     }
/*  21:    */   }
/*  22:    */   
/*  23:    */   private static void assertEquals(float a1, float a2)
/*  24:    */   {
/*  25: 41 */     if (a1 != a2) {
/*  26: 42 */       throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   private static void assertEquals(int a1, int a2)
/*  31:    */   {
/*  32: 53 */     if (a1 != a2) {
/*  33: 54 */       throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   private static void assertEquals(Object a1, Object a2)
/*  38:    */   {
/*  39: 65 */     if (!a1.equals(a2)) {
/*  40: 66 */       throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void main(String[] argv)
/*  45:    */     throws SlickException
/*  46:    */   {
/*  47: 77 */     XMLParser parser = new XMLParser();
/*  48:    */     
/*  49: 79 */     XMLElement root = parser.parse("testdata/test.xml");
/*  50:    */     
/*  51: 81 */     assertEquals(root.getName(), "testRoot");
/*  52: 82 */     System.out.println(root);
/*  53: 83 */     assertNotNull(root.getChildrenByName("simple").get(0).getContent());
/*  54: 84 */     System.out.println(root.getChildrenByName("simple").get(0).getContent());
/*  55:    */     
/*  56: 86 */     XMLElement parent = root.getChildrenByName("parent").get(0);
/*  57: 87 */     assertEquals(parent.getChildrenByName("grandchild").size(), 0);
/*  58: 88 */     assertEquals(parent.getChildrenByName("child").size(), 2);
/*  59:    */     
/*  60: 90 */     assertEquals(parent.getChildrenByName("child").get(0).getChildren().size(), 2);
/*  61: 91 */     XMLElement child = parent.getChildrenByName("child").get(0).getChildren().get(0);
/*  62:    */     
/*  63: 93 */     String name = child.getAttribute("name");
/*  64: 94 */     String test = child.getAttribute("nothere", "defaultValue");
/*  65: 95 */     int age = child.getIntAttribute("age");
/*  66:    */     
/*  67: 97 */     assertEquals(name, "bob");
/*  68: 98 */     assertEquals(test, "defaultValue");
/*  69: 99 */     assertEquals(age, 1);
/*  70:    */     
/*  71:101 */     XMLElement other = root.getChildrenByName("other").get(0);
/*  72:102 */     float x = (float)other.getDoubleAttribute("x");
/*  73:103 */     float y = (float)other.getDoubleAttribute("y", 1.0D);
/*  74:104 */     float z = (float)other.getDoubleAttribute("z", 83.0D);
/*  75:    */     
/*  76:106 */     assertEquals(x, 5.3F);
/*  77:107 */     assertEquals(y, 5.4F);
/*  78:108 */     assertEquals(z, 83.0F);
/*  79:    */     try
/*  80:    */     {
/*  81:111 */       z = (float)other.getDoubleAttribute("z");
/*  82:112 */       fail("Attribute z as a double should fail");
/*  83:    */     }
/*  84:    */     catch (SlickException localSlickException) {}
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.xml.XMLTest
 * JD-Core Version:    0.7.0.1
 */