/*    */ package me.eagler.premium;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.util.Scanner;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JOptionPane;
/*    */ import me.eagler.Client;
/*    */ 
/*    */ public class HWIDCheck {
/* 17 */   private static JsonParser jsonParser = new JsonParser();
/*    */   
/*    */   public void loadHwid() throws Exception {
/* 20 */     String[] hwidSplit = getHwid().split(";");
/* 21 */     String a = hwidSplit[0];
/* 22 */     String b = hwidSplit[1];
/* 23 */     HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL("http://88.218.227.109/" + b + ".json"))
/* 24 */       .openConnection();
/* 25 */     System.out.println("Versuche, HWID abzufragen: " + b);
/* 26 */     httpURLConnection.setRequestProperty("User-Agent", "Quiet PREMIUM");
/* 27 */     httpURLConnection.setReadTimeout(5000);
/* 28 */     httpURLConnection.setConnectTimeout(2000);
/* 29 */     httpURLConnection.connect();
/* 30 */     int i = httpURLConnection.getResponseCode();
/* 31 */     if (i / 100 == 2) {
/* 32 */       String s = "";
/*    */       
/* 34 */       Scanner scanner = new Scanner(httpURLConnection.getInputStream()); for (; scanner
/* 35 */         .hasNext(); s = String.valueOf(String.valueOf(s)) + scanner.next());
/*    */       
/* 37 */       scanner.close();
/* 38 */       handleJsonString(s);
/*    */     } else {
/* 40 */       System.exit(0);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void loadVersion() throws Exception {
/* 45 */     HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL(
/* 46 */         "http://88.218.227.109/VERSION" + Client.instance.VERSION + ".json")).openConnection();
/* 47 */     httpURLConnection.setRequestProperty("User-Agent", "Quiet PREMIUM");
/* 48 */     httpURLConnection.setReadTimeout(5000);
/* 49 */     httpURLConnection.setConnectTimeout(2000);
/* 50 */     httpURLConnection.connect();
/* 51 */     int i = httpURLConnection.getResponseCode();
/* 52 */     if (i / 100 == 2) {
/* 53 */       String s = "";
/*    */       
/* 55 */       Scanner scanner = new Scanner(httpURLConnection.getInputStream()); for (; scanner
/* 56 */         .hasNext(); s = String.valueOf(String.valueOf(s)) + scanner.next());
/*    */       
/* 58 */       scanner.close();
/*    */     } else {
/* 60 */       JOptionPane jp = new JOptionPane();
/* 61 */       JOptionPane.showMessageDialog(new JFrame(), "NEW VERSION: http://quietclient.eu", "New Version", 1);
/* 62 */       System.exit(0);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void handleJsonString(String jsonCode) {
/* 67 */     JsonElement jsonelement = jsonParser.parse(jsonCode);
/* 68 */     JsonObject jsonobject = jsonelement.getAsJsonObject();
/* 69 */     if (jsonobject.has("encrypted")) {
/* 70 */       System.out.println("Quiet Premium aktiviert");
/*    */     } else {
/* 72 */       System.out.println("Quiet Premium deaktiviert ");
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String getHwid() throws NoSuchAlgorithmException, UnsupportedEncodingException {
/* 77 */     String hwid = "";
/* 78 */     String main = (String.valueOf(String.valueOf(System.getenv("PROCESSOR_IDENTIFIER"))) + System.getenv("COMPUTERNAME") + 
/* 79 */       System.getProperty("user.name")).trim();
/* 80 */     System.out.println("hwid: " + main);
/* 81 */     byte[] bytes = main.getBytes("UTF-8");
/* 82 */     MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/* 83 */     byte[] md5 = messageDigest.digest(bytes);
/* 84 */     int i = 0;
/*    */     byte b;
/*    */     int j;
/*    */     byte[] arrayOfByte1;
/* 88 */     for (j = (arrayOfByte1 = md5).length, b = 0; b < j; ) {
/* 89 */       byte b1 = arrayOfByte1[b];
/* 90 */       hwid = String.valueOf(String.valueOf(hwid)) + Integer.toHexString(b1 + 255 | 0x100).substring(0, 3);
/* 91 */       if (i != md5.length - 1)
/* 92 */         hwid = String.valueOf(String.valueOf(hwid)) + "-"; 
/* 93 */       i++;
/* 94 */       b = (byte)(b + 1);
/*    */     } 
/* 96 */     System.out.println("hwid (encrypted): " + hwid);
/* 97 */     System.out.println("full generated hwid: " + main + ";" + hwid);
/* 98 */     return String.valueOf(String.valueOf(main)) + ";" + hwid;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\premium\HWIDCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */