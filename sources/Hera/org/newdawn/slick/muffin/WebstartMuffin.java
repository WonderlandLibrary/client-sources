/*     */ package org.newdawn.slick.muffin;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.jnlp.BasicService;
/*     */ import javax.jnlp.FileContents;
/*     */ import javax.jnlp.PersistenceService;
/*     */ import javax.jnlp.ServiceManager;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebstartMuffin
/*     */   implements Muffin
/*     */ {
/*     */   public void saveFile(HashMap scoreMap, String fileName) throws IOException {
/*     */     PersistenceService ps;
/*     */     URL configURL;
/*     */     try {
/*  37 */       ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService");
/*     */       
/*  39 */       BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
/*     */       
/*  41 */       URL baseURL = bs.getCodeBase();
/*     */       
/*  43 */       configURL = new URL(baseURL, fileName);
/*  44 */     } catch (Exception e) {
/*  45 */       Log.error(e);
/*  46 */       throw new IOException("Failed to save state: ");
/*     */     } 
/*     */     
/*     */     try {
/*  50 */       ps.delete(configURL);
/*  51 */     } catch (Exception e) {
/*  52 */       Log.info("No exisiting Muffin Found - First Save");
/*     */     } 
/*     */     
/*     */     try {
/*  56 */       ps.create(configURL, 1024L);
/*     */       
/*  58 */       FileContents fc = ps.get(configURL);
/*  59 */       DataOutputStream oos = new DataOutputStream(fc.getOutputStream(false));
/*     */ 
/*     */ 
/*     */       
/*  63 */       Set keys = scoreMap.keySet();
/*     */ 
/*     */       
/*  66 */       for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
/*  67 */         String key = i.next();
/*     */         
/*  69 */         oos.writeUTF(key);
/*     */         
/*  71 */         if (fileName.endsWith("Number")) {
/*  72 */           double d = ((Double)scoreMap.get(key)).doubleValue();
/*  73 */           oos.writeDouble(d); continue;
/*     */         } 
/*  75 */         String value = (String)scoreMap.get(key);
/*  76 */         oos.writeUTF(value);
/*     */       } 
/*     */ 
/*     */       
/*  80 */       oos.flush();
/*  81 */       oos.close();
/*  82 */     } catch (Exception e) {
/*  83 */       Log.error(e);
/*  84 */       throw new IOException("Failed to store map of state data");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap loadFile(String fileName) throws IOException {
/*  92 */     HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
/*     */     
/*     */     try {
/*  95 */       PersistenceService ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService");
/*     */       
/*  97 */       BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
/*     */       
/*  99 */       URL baseURL = bs.getCodeBase();
/* 100 */       URL configURL = new URL(baseURL, fileName);
/* 101 */       FileContents fc = ps.get(configURL);
/* 102 */       DataInputStream ois = new DataInputStream(fc.getInputStream());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       if (fileName.endsWith("Number")) {
/*     */         String key;
/*     */         
/* 111 */         while ((key = ois.readUTF()) != null) {
/* 112 */           double value = ois.readDouble();
/*     */           
/* 114 */           hashMap.put(key, new Double(value));
/*     */         } 
/*     */       } else {
/*     */         String key;
/*     */         
/* 119 */         while ((key = ois.readUTF()) != null) {
/* 120 */           String value = ois.readUTF();
/*     */           
/* 122 */           hashMap.put(key, value);
/*     */         } 
/*     */       } 
/*     */       
/* 126 */       ois.close();
/* 127 */     } catch (EOFException e) {
/*     */     
/* 129 */     } catch (IOException e) {
/*     */     
/* 131 */     } catch (Exception e) {
/* 132 */       Log.error(e);
/* 133 */       throw new IOException("Failed to load state from webstart muffin");
/*     */     } 
/*     */     
/* 136 */     return hashMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\muffin\WebstartMuffin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */