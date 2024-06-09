/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NetUtil
/*     */ {
/*     */   public static final Inet4Address LOCALHOST4;
/*     */   public static final Inet6Address LOCALHOST6;
/*     */   public static final InetAddress LOCALHOST;
/*     */   public static final NetworkInterface LOOPBACK_IF;
/*     */   public static final int SOMAXCONN;
/*  74 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NetUtil.class);
/*     */   
/*     */   static {
/*  77 */     byte[] LOCALHOST4_BYTES = { Byte.MAX_VALUE, 0, 0, 1 };
/*  78 */     byte[] LOCALHOST6_BYTES = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
/*     */ 
/*     */     
/*  81 */     Inet4Address localhost4 = null;
/*     */     try {
/*  83 */       localhost4 = (Inet4Address)InetAddress.getByAddress(LOCALHOST4_BYTES);
/*  84 */     } catch (Exception e) {
/*     */       
/*  86 */       PlatformDependent.throwException(e);
/*     */     } 
/*  88 */     LOCALHOST4 = localhost4;
/*     */ 
/*     */     
/*  91 */     Inet6Address localhost6 = null;
/*     */     try {
/*  93 */       localhost6 = (Inet6Address)InetAddress.getByAddress(LOCALHOST6_BYTES);
/*  94 */     } catch (Exception e) {
/*     */       
/*  96 */       PlatformDependent.throwException(e);
/*     */     } 
/*  98 */     LOCALHOST6 = localhost6;
/*     */ 
/*     */     
/* 101 */     List<NetworkInterface> ifaces = new ArrayList<NetworkInterface>();
/*     */     try {
/* 103 */       for (Enumeration<NetworkInterface> i = NetworkInterface.getNetworkInterfaces(); i.hasMoreElements(); ) {
/* 104 */         NetworkInterface iface = i.nextElement();
/*     */         
/* 106 */         if (iface.getInetAddresses().hasMoreElements()) {
/* 107 */           ifaces.add(iface);
/*     */         }
/*     */       } 
/* 110 */     } catch (SocketException e) {
/* 111 */       logger.warn("Failed to retrieve the list of available network interfaces", e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     NetworkInterface loopbackIface = null;
/* 118 */     InetAddress loopbackAddr = null;
/* 119 */     label103: for (NetworkInterface iface : ifaces) {
/* 120 */       for (Enumeration<InetAddress> i = iface.getInetAddresses(); i.hasMoreElements(); ) {
/* 121 */         InetAddress addr = i.nextElement();
/* 122 */         if (addr.isLoopbackAddress()) {
/*     */           
/* 124 */           loopbackIface = iface;
/* 125 */           loopbackAddr = addr;
/*     */           
/*     */           break label103;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     if (loopbackIface == null) {
/*     */       try {
/* 134 */         for (NetworkInterface iface : ifaces) {
/* 135 */           if (iface.isLoopback()) {
/* 136 */             Enumeration<InetAddress> i = iface.getInetAddresses();
/* 137 */             if (i.hasMoreElements()) {
/*     */               
/* 139 */               loopbackIface = iface;
/* 140 */               loopbackAddr = i.nextElement();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 146 */         if (loopbackIface == null) {
/* 147 */           logger.warn("Failed to find the loopback interface");
/*     */         }
/* 149 */       } catch (SocketException e) {
/* 150 */         logger.warn("Failed to find the loopback interface", e);
/*     */       } 
/*     */     }
/*     */     
/* 154 */     if (loopbackIface != null) {
/*     */       
/* 156 */       logger.debug("Loopback interface: {} ({}, {})", new Object[] { loopbackIface.getName(), loopbackIface.getDisplayName(), loopbackAddr.getHostAddress() });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 162 */     else if (loopbackAddr == null) {
/*     */       try {
/* 164 */         if (NetworkInterface.getByInetAddress(LOCALHOST6) != null) {
/* 165 */           logger.debug("Using hard-coded IPv6 localhost address: {}", localhost6);
/* 166 */           loopbackAddr = localhost6;
/*     */         } 
/* 168 */       } catch (Exception e) {
/*     */       
/*     */       } finally {
/* 171 */         if (loopbackAddr == null) {
/* 172 */           logger.debug("Using hard-coded IPv4 localhost address: {}", localhost4);
/* 173 */           loopbackAddr = localhost4;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 179 */     LOOPBACK_IF = loopbackIface;
/* 180 */     LOCALHOST = loopbackAddr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     int somaxconn = PlatformDependent.isWindows() ? 200 : 128;
/* 187 */     File file = new File("/proc/sys/net/core/somaxconn");
/* 188 */     if (file.exists()) {
/* 189 */       BufferedReader in = null;
/*     */       try {
/* 191 */         in = new BufferedReader(new FileReader(file));
/* 192 */         somaxconn = Integer.parseInt(in.readLine());
/* 193 */         if (logger.isDebugEnabled()) {
/* 194 */           logger.debug("{}: {}", file, Integer.valueOf(somaxconn));
/*     */         }
/* 196 */       } catch (Exception e) {
/* 197 */         logger.debug("Failed to get SOMAXCONN from: {}", file, e);
/*     */       } finally {
/* 199 */         if (in != null) {
/*     */           try {
/* 201 */             in.close();
/* 202 */           } catch (Exception e) {}
/*     */         
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 208 */     else if (logger.isDebugEnabled()) {
/* 209 */       logger.debug("{}: {} (non-existent)", file, Integer.valueOf(somaxconn));
/*     */     } 
/*     */ 
/*     */     
/* 213 */     SOMAXCONN = somaxconn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] createByteArrayFromIpAddressString(String ipAddressString) {
/* 222 */     if (isValidIpV4Address(ipAddressString)) {
/* 223 */       StringTokenizer tokenizer = new StringTokenizer(ipAddressString, ".");
/*     */ 
/*     */       
/* 226 */       byte[] byteAddress = new byte[4];
/* 227 */       for (int i = 0; i < 4; i++) {
/* 228 */         String token = tokenizer.nextToken();
/* 229 */         int tempInt = Integer.parseInt(token);
/* 230 */         byteAddress[i] = (byte)tempInt;
/*     */       } 
/*     */       
/* 233 */       return byteAddress;
/*     */     } 
/*     */     
/* 236 */     if (isValidIpV6Address(ipAddressString)) {
/* 237 */       if (ipAddressString.charAt(0) == '[') {
/* 238 */         ipAddressString = ipAddressString.substring(1, ipAddressString.length() - 1);
/*     */       }
/*     */       
/* 241 */       StringTokenizer tokenizer = new StringTokenizer(ipAddressString, ":.", true);
/* 242 */       ArrayList<String> hexStrings = new ArrayList<String>();
/* 243 */       ArrayList<String> decStrings = new ArrayList<String>();
/* 244 */       String token = "";
/* 245 */       String prevToken = "";
/* 246 */       int doubleColonIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       while (tokenizer.hasMoreTokens()) {
/* 255 */         prevToken = token;
/* 256 */         token = tokenizer.nextToken();
/*     */         
/* 258 */         if (":".equals(token)) {
/* 259 */           if (":".equals(prevToken)) {
/* 260 */             doubleColonIndex = hexStrings.size(); continue;
/* 261 */           }  if (!prevToken.isEmpty())
/* 262 */             hexStrings.add(prevToken);  continue;
/*     */         } 
/* 264 */         if (".".equals(token)) {
/* 265 */           decStrings.add(prevToken);
/*     */         }
/*     */       } 
/*     */       
/* 269 */       if (":".equals(prevToken)) {
/* 270 */         if (":".equals(token)) {
/* 271 */           doubleColonIndex = hexStrings.size();
/*     */         } else {
/* 273 */           hexStrings.add(token);
/*     */         } 
/* 275 */       } else if (".".equals(prevToken)) {
/* 276 */         decStrings.add(token);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 281 */       int hexStringsLength = 8;
/*     */ 
/*     */ 
/*     */       
/* 285 */       if (!decStrings.isEmpty()) {
/* 286 */         hexStringsLength -= 2;
/*     */       }
/*     */ 
/*     */       
/* 290 */       if (doubleColonIndex != -1) {
/* 291 */         int numberToInsert = hexStringsLength - hexStrings.size();
/* 292 */         for (int j = 0; j < numberToInsert; j++) {
/* 293 */           hexStrings.add(doubleColonIndex, "0");
/*     */         }
/*     */       } 
/*     */       
/* 297 */       byte[] ipByteArray = new byte[16];
/*     */       
/*     */       int i;
/* 300 */       for (i = 0; i < hexStrings.size(); i++) {
/* 301 */         convertToBytes(hexStrings.get(i), ipByteArray, i * 2);
/*     */       }
/*     */ 
/*     */       
/* 305 */       for (i = 0; i < decStrings.size(); i++) {
/* 306 */         ipByteArray[i + 12] = (byte)(Integer.parseInt((String)decStrings.get(i)) & 0xFF);
/*     */       }
/* 308 */       return ipByteArray;
/*     */     } 
/* 310 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void convertToBytes(String hexWord, byte[] ipByteArray, int byteIndex) {
/* 318 */     int hexWordLength = hexWord.length();
/* 319 */     int hexWordIndex = 0;
/* 320 */     ipByteArray[byteIndex] = 0;
/* 321 */     ipByteArray[byteIndex + 1] = 0;
/*     */ 
/*     */ 
/*     */     
/* 325 */     if (hexWordLength > 3) {
/* 326 */       int i = getIntValue(hexWord.charAt(hexWordIndex++));
/* 327 */       ipByteArray[byteIndex] = (byte)(ipByteArray[byteIndex] | i << 4);
/*     */     } 
/*     */ 
/*     */     
/* 331 */     if (hexWordLength > 2) {
/* 332 */       int i = getIntValue(hexWord.charAt(hexWordIndex++));
/* 333 */       ipByteArray[byteIndex] = (byte)(ipByteArray[byteIndex] | i);
/*     */     } 
/*     */ 
/*     */     
/* 337 */     if (hexWordLength > 1) {
/* 338 */       int i = getIntValue(hexWord.charAt(hexWordIndex++));
/* 339 */       ipByteArray[byteIndex + 1] = (byte)(ipByteArray[byteIndex + 1] | i << 4);
/*     */     } 
/*     */ 
/*     */     
/* 343 */     int charValue = getIntValue(hexWord.charAt(hexWordIndex));
/* 344 */     ipByteArray[byteIndex + 1] = (byte)(ipByteArray[byteIndex + 1] | charValue & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   static int getIntValue(char c) {
/* 349 */     switch (c) {
/*     */       case '0':
/* 351 */         return 0;
/*     */       case '1':
/* 353 */         return 1;
/*     */       case '2':
/* 355 */         return 2;
/*     */       case '3':
/* 357 */         return 3;
/*     */       case '4':
/* 359 */         return 4;
/*     */       case '5':
/* 361 */         return 5;
/*     */       case '6':
/* 363 */         return 6;
/*     */       case '7':
/* 365 */         return 7;
/*     */       case '8':
/* 367 */         return 8;
/*     */       case '9':
/* 369 */         return 9;
/*     */     } 
/*     */     
/* 372 */     c = Character.toLowerCase(c);
/* 373 */     switch (c) {
/*     */       case 'a':
/* 375 */         return 10;
/*     */       case 'b':
/* 377 */         return 11;
/*     */       case 'c':
/* 379 */         return 12;
/*     */       case 'd':
/* 381 */         return 13;
/*     */       case 'e':
/* 383 */         return 14;
/*     */       case 'f':
/* 385 */         return 15;
/*     */     } 
/* 387 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean isValidIpV6Address(String ipAddress) {
/* 391 */     int length = ipAddress.length();
/* 392 */     boolean doubleColon = false;
/* 393 */     int numberOfColons = 0;
/* 394 */     int numberOfPeriods = 0;
/* 395 */     int numberOfPercent = 0;
/* 396 */     StringBuilder word = new StringBuilder();
/* 397 */     char c = Character.MIN_VALUE;
/*     */     
/* 399 */     int offset = 0;
/*     */     
/* 401 */     if (length < 2) {
/* 402 */       return false;
/*     */     }
/*     */     
/* 405 */     for (int i = 0; i < length; i++) {
/* 406 */       char prevChar = c;
/* 407 */       c = ipAddress.charAt(i);
/* 408 */       switch (c) {
/*     */ 
/*     */         
/*     */         case '[':
/* 412 */           if (i != 0) {
/* 413 */             return false;
/*     */           }
/* 415 */           if (ipAddress.charAt(length - 1) != ']') {
/* 416 */             return false;
/*     */           }
/* 418 */           offset = 1;
/* 419 */           if (length < 4) {
/* 420 */             return false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case ']':
/* 426 */           if (i != length - 1) {
/* 427 */             return false;
/*     */           }
/* 429 */           if (ipAddress.charAt(0) != '[') {
/* 430 */             return false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case '.':
/* 436 */           numberOfPeriods++;
/* 437 */           if (numberOfPeriods > 3) {
/* 438 */             return false;
/*     */           }
/* 440 */           if (!isValidIp4Word(word.toString())) {
/* 441 */             return false;
/*     */           }
/* 443 */           if (numberOfColons != 6 && !doubleColon) {
/* 444 */             return false;
/*     */           }
/*     */ 
/*     */           
/* 448 */           if (numberOfColons == 7 && ipAddress.charAt(offset) != ':' && ipAddress.charAt(1 + offset) != ':')
/*     */           {
/* 450 */             return false;
/*     */           }
/* 452 */           word.delete(0, word.length());
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case ':':
/* 459 */           if (i == offset && (ipAddress.length() <= i || ipAddress.charAt(i + 1) != ':')) {
/* 460 */             return false;
/*     */           }
/*     */           
/* 463 */           numberOfColons++;
/* 464 */           if (numberOfColons > 7) {
/* 465 */             return false;
/*     */           }
/* 467 */           if (numberOfPeriods > 0) {
/* 468 */             return false;
/*     */           }
/* 470 */           if (prevChar == ':') {
/* 471 */             if (doubleColon) {
/* 472 */               return false;
/*     */             }
/* 474 */             doubleColon = true;
/*     */           } 
/* 476 */           word.delete(0, word.length());
/*     */           break;
/*     */         case '%':
/* 479 */           if (numberOfColons == 0) {
/* 480 */             return false;
/*     */           }
/* 482 */           numberOfPercent++;
/*     */ 
/*     */           
/* 485 */           if (i + 1 >= length)
/*     */           {
/*     */             
/* 488 */             return false;
/*     */           }
/*     */           try {
/* 491 */             if (Integer.parseInt(ipAddress.substring(i + 1)) < 0) {
/* 492 */               return false;
/*     */             }
/* 494 */           } catch (NumberFormatException e) {
/*     */ 
/*     */ 
/*     */             
/* 498 */             return false;
/*     */           } 
/*     */           break;
/*     */         
/*     */         default:
/* 503 */           if (numberOfPercent == 0) {
/* 504 */             if (word != null && word.length() > 3) {
/* 505 */               return false;
/*     */             }
/* 507 */             if (!isValidHexChar(c)) {
/* 508 */               return false;
/*     */             }
/*     */           } 
/* 511 */           word.append(c);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 516 */     if (numberOfPeriods > 0) {
/*     */       
/* 518 */       if (numberOfPeriods != 3 || !isValidIp4Word(word.toString()) || numberOfColons >= 7) {
/* 519 */         return false;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 524 */       if (numberOfColons != 7 && !doubleColon) {
/* 525 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 531 */       if (numberOfPercent == 0 && 
/* 532 */         word.length() == 0 && ipAddress.charAt(length - 1 - offset) == ':' && ipAddress.charAt(length - 2 - offset) != ':')
/*     */       {
/* 534 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 539 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidIp4Word(String word) {
/* 544 */     if (word.length() < 1 || word.length() > 3) {
/* 545 */       return false;
/*     */     }
/* 547 */     for (int i = 0; i < word.length(); i++) {
/* 548 */       char c = word.charAt(i);
/* 549 */       if (c < '0' || c > '9') {
/* 550 */         return false;
/*     */       }
/*     */     } 
/* 553 */     return (Integer.parseInt(word) <= 255);
/*     */   }
/*     */   
/*     */   static boolean isValidHexChar(char c) {
/* 557 */     return ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidIpV4Address(String value) {
/* 568 */     int periods = 0;
/*     */     
/* 570 */     int length = value.length();
/*     */     
/* 572 */     if (length > 15) {
/* 573 */       return false;
/*     */     }
/*     */     
/* 576 */     StringBuilder word = new StringBuilder();
/* 577 */     for (int i = 0; i < length; i++) {
/* 578 */       char c = value.charAt(i);
/* 579 */       if (c == '.')
/* 580 */       { periods++;
/* 581 */         if (periods > 3) {
/* 582 */           return false;
/*     */         }
/* 584 */         if (word.length() == 0) {
/* 585 */           return false;
/*     */         }
/* 587 */         if (Integer.parseInt(word.toString()) > 255) {
/* 588 */           return false;
/*     */         }
/* 590 */         word.delete(0, word.length()); }
/* 591 */       else { if (!Character.isDigit(c)) {
/* 592 */           return false;
/*     */         }
/* 594 */         if (word.length() > 2) {
/* 595 */           return false;
/*     */         }
/* 597 */         word.append(c); }
/*     */     
/*     */     } 
/*     */     
/* 601 */     if (word.length() == 0 || Integer.parseInt(word.toString()) > 255) {
/* 602 */       return false;
/*     */     }
/*     */     
/* 605 */     return (periods == 3);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\NetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */