/*  1:   */ package me.connorm.Nodus.utils;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.BufferedWriter;
/*  5:   */ import java.io.File;
/*  6:   */ import java.io.FileReader;
/*  7:   */ import java.io.FileWriter;
/*  8:   */ import java.io.IOException;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ 
/* 11:   */ public class FileUtils
/* 12:   */ {
/* 13:   */   public static ArrayList<String> read(File dir)
/* 14:   */   {
/* 15:14 */     ArrayList lines = new ArrayList();
/* 16:   */     try
/* 17:   */     {
/* 18:17 */       BufferedReader br = new BufferedReader(new FileReader(dir));
/* 19:   */       String curLine;
/* 20:19 */       while (((curLine = br.readLine()) != null) && (!curLine.startsWith("#")))
/* 21:   */       {
/* 22:   */         String curLine;
/* 23:21 */         lines.add(curLine);
/* 24:   */       }
/* 25:23 */       br.close();
/* 26:   */     }
/* 27:   */     catch (IOException e)
/* 28:   */     {
/* 29:27 */       e.printStackTrace();
/* 30:   */     }
/* 31:29 */     return lines;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static void print(ArrayList<String> lines, File dir)
/* 35:   */   {
/* 36:   */     try
/* 37:   */     {
/* 38:36 */       BufferedWriter bw = new BufferedWriter(new FileWriter(dir));
/* 39:37 */       for (String s : lines) {
/* 40:38 */         bw.write(s + "\r\n");
/* 41:   */       }
/* 42:40 */       bw.close();
/* 43:   */     }
/* 44:   */     catch (IOException e)
/* 45:   */     {
/* 46:43 */       e.printStackTrace();
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.utils.FileUtils
 * JD-Core Version:    0.7.0.1
 */