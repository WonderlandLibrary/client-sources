/*     */ package org.newdawn.slick.tests.xml;
/*     */ 
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.util.xml.XMLElement;
/*     */ import org.newdawn.slick.util.xml.XMLParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLTest
/*     */ {
/*     */   private static void fail(String message) {
/*  20 */     throw new RuntimeException(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertNotNull(Object object1) {
/*  29 */     if (object1 == null) {
/*  30 */       throw new RuntimeException("TEST FAILS: " + object1 + " must not be null");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertEquals(float a1, float a2) {
/*  41 */     if (a1 != a2) {
/*  42 */       throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertEquals(int a1, int a2) {
/*  53 */     if (a1 != a2) {
/*  54 */       throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertEquals(Object a1, Object a2) {
/*  65 */     if (!a1.equals(a2)) {
/*  66 */       throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) throws SlickException {
/*  77 */     XMLParser parser = new XMLParser();
/*     */     
/*  79 */     XMLElement root = parser.parse("testdata/test.xml");
/*     */     
/*  81 */     assertEquals(root.getName(), "testRoot");
/*  82 */     System.out.println(root);
/*  83 */     assertNotNull(root.getChildrenByName("simple").get(0).getContent());
/*  84 */     System.out.println(root.getChildrenByName("simple").get(0).getContent());
/*     */     
/*  86 */     XMLElement parent = root.getChildrenByName("parent").get(0);
/*  87 */     assertEquals(parent.getChildrenByName("grandchild").size(), 0);
/*  88 */     assertEquals(parent.getChildrenByName("child").size(), 2);
/*     */     
/*  90 */     assertEquals(parent.getChildrenByName("child").get(0).getChildren().size(), 2);
/*  91 */     XMLElement child = parent.getChildrenByName("child").get(0).getChildren().get(0);
/*     */     
/*  93 */     String name = child.getAttribute("name");
/*  94 */     String test = child.getAttribute("nothere", "defaultValue");
/*  95 */     int age = child.getIntAttribute("age");
/*     */     
/*  97 */     assertEquals(name, "bob");
/*  98 */     assertEquals(test, "defaultValue");
/*  99 */     assertEquals(age, 1);
/*     */     
/* 101 */     XMLElement other = root.getChildrenByName("other").get(0);
/* 102 */     float x = (float)other.getDoubleAttribute("x");
/* 103 */     float y = (float)other.getDoubleAttribute("y", 1.0D);
/* 104 */     float z = (float)other.getDoubleAttribute("z", 83.0D);
/*     */     
/* 106 */     assertEquals(x, 5.3F);
/* 107 */     assertEquals(y, 5.4F);
/* 108 */     assertEquals(z, 83.0F);
/*     */     
/*     */     try {
/* 111 */       z = (float)other.getDoubleAttribute("z");
/* 112 */       fail("Attribute z as a double should fail");
/* 113 */     } catch (SlickException e) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\xml\XMLTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */