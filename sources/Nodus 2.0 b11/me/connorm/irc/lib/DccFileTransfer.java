/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.BufferedOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.net.InetAddress;
/*  10:    */ import java.net.ServerSocket;
/*  11:    */ import java.net.Socket;
/*  12:    */ 
/*  13:    */ public class DccFileTransfer
/*  14:    */ {
/*  15:    */   public static final int BUFFER_SIZE = 1024;
/*  16:    */   private PircBot _bot;
/*  17:    */   private DccManager _manager;
/*  18:    */   private String _nick;
/*  19:    */   
/*  20:    */   DccFileTransfer(PircBot bot, DccManager manager, String nick, String login, String hostname, String type, String filename, long address, int port, long size)
/*  21:    */   {
/*  22: 40 */     this._bot = bot;
/*  23: 41 */     this._manager = manager;
/*  24: 42 */     this._nick = nick;
/*  25: 43 */     this._login = login;
/*  26: 44 */     this._hostname = hostname;
/*  27: 45 */     this._type = type;
/*  28: 46 */     this._file = new File(filename);
/*  29: 47 */     this._address = address;
/*  30: 48 */     this._port = port;
/*  31: 49 */     this._size = size;
/*  32: 50 */     this._received = false;
/*  33:    */     
/*  34: 52 */     this._incoming = true;
/*  35:    */   }
/*  36:    */   
/*  37:    */   DccFileTransfer(PircBot bot, DccManager manager, File file, String nick, int timeout)
/*  38:    */   {
/*  39: 60 */     this._bot = bot;
/*  40: 61 */     this._manager = manager;
/*  41: 62 */     this._nick = nick;
/*  42: 63 */     this._file = file;
/*  43: 64 */     this._size = file.length();
/*  44: 65 */     this._timeout = timeout;
/*  45: 66 */     this._received = true;
/*  46:    */     
/*  47: 68 */     this._incoming = false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public synchronized void receive(File file, boolean resume)
/*  51:    */   {
/*  52: 83 */     if (!this._received)
/*  53:    */     {
/*  54: 84 */       this._received = true;
/*  55: 85 */       this._file = file;
/*  56: 87 */       if ((this._type.equals("SEND")) && (resume))
/*  57:    */       {
/*  58: 88 */         this._progress = file.length();
/*  59: 89 */         if (this._progress == 0L)
/*  60:    */         {
/*  61: 90 */           doReceive(file, false);
/*  62:    */         }
/*  63:    */         else
/*  64:    */         {
/*  65: 93 */           this._bot.sendCTCPCommand(this._nick, "DCC RESUME file.ext " + this._port + " " + this._progress);
/*  66: 94 */           this._manager.addAwaitingResume(this);
/*  67:    */         }
/*  68:    */       }
/*  69:    */       else
/*  70:    */       {
/*  71: 98 */         this._progress = file.length();
/*  72: 99 */         doReceive(file, resume);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   void doReceive(final File file, final boolean resume)
/*  78:    */   {
/*  79:167 */     new Thread()
/*  80:    */     {
/*  81:    */       public void run()
/*  82:    */       {
/*  83:112 */         BufferedOutputStream foutput = null;
/*  84:113 */         Exception exception = null;
/*  85:    */         try
/*  86:    */         {
/*  87:118 */           int[] ip = DccFileTransfer.this._bot.longToIp(DccFileTransfer.this._address);
/*  88:119 */           String ipStr = ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
/*  89:    */           
/*  90:    */ 
/*  91:122 */           DccFileTransfer.this._socket = new Socket(ipStr, DccFileTransfer.this._port);
/*  92:123 */           DccFileTransfer.this._socket.setSoTimeout(30000);
/*  93:124 */           DccFileTransfer.this._startTime = System.currentTimeMillis();
/*  94:    */           
/*  95:    */ 
/*  96:127 */           DccFileTransfer.this._manager.removeAwaitingResume(DccFileTransfer.this);
/*  97:    */           
/*  98:129 */           BufferedInputStream input = new BufferedInputStream(DccFileTransfer.this._socket.getInputStream());
/*  99:130 */           BufferedOutputStream output = new BufferedOutputStream(DccFileTransfer.this._socket.getOutputStream());
/* 100:    */           
/* 101:    */ 
/* 102:133 */           foutput = new BufferedOutputStream(new FileOutputStream(file.getCanonicalPath(), resume));
/* 103:    */           
/* 104:135 */           byte[] inBuffer = new byte[1024];
/* 105:136 */           byte[] outBuffer = new byte[4];
/* 106:137 */           int bytesRead = 0;
/* 107:138 */           while ((bytesRead = input.read(inBuffer, 0, inBuffer.length)) != -1)
/* 108:    */           {
/* 109:139 */             foutput.write(inBuffer, 0, bytesRead);
/* 110:140 */             DccFileTransfer.this._progress += bytesRead;
/* 111:    */             
/* 112:142 */             outBuffer[0] = ((byte)(int)(DccFileTransfer.this._progress >> 24 & 0xFF));
/* 113:143 */             outBuffer[1] = ((byte)(int)(DccFileTransfer.this._progress >> 16 & 0xFF));
/* 114:144 */             outBuffer[2] = ((byte)(int)(DccFileTransfer.this._progress >> 8 & 0xFF));
/* 115:145 */             outBuffer[3] = ((byte)(int)(DccFileTransfer.this._progress >> 0 & 0xFF));
/* 116:146 */             output.write(outBuffer);
/* 117:147 */             output.flush();
/* 118:148 */             DccFileTransfer.this.delay();
/* 119:    */           }
/* 120:150 */           foutput.flush();
/* 121:    */         }
/* 122:    */         catch (Exception e)
/* 123:    */         {
/* 124:153 */           exception = e;
/* 125:    */           try
/* 126:    */           {
/* 127:157 */             foutput.close();
/* 128:158 */             DccFileTransfer.this._socket.close();
/* 129:    */           }
/* 130:    */           catch (Exception localException1) {}
/* 131:    */         }
/* 132:    */         finally
/* 133:    */         {
/* 134:    */           try
/* 135:    */           {
/* 136:157 */             foutput.close();
/* 137:158 */             DccFileTransfer.this._socket.close();
/* 138:    */           }
/* 139:    */           catch (Exception localException2) {}
/* 140:    */         }
/* 141:165 */         DccFileTransfer.this._bot.onFileTransferFinished(DccFileTransfer.this, exception);
/* 142:    */       }
/* 143:    */     }.start();
/* 144:    */   }
/* 145:    */   
/* 146:    */   void doSend(final boolean allowResume)
/* 147:    */   {
/* 148:279 */     new Thread()
/* 149:    */     {
/* 150:    */       public void run()
/* 151:    */       {
/* 152:178 */         BufferedInputStream finput = null;
/* 153:179 */         Exception exception = null;
/* 154:    */         try
/* 155:    */         {
/* 156:183 */           ServerSocket ss = null;
/* 157:    */           
/* 158:185 */           int[] ports = DccFileTransfer.this._bot.getDccPorts();
/* 159:186 */           if (ports == null)
/* 160:    */           {
/* 161:188 */             ss = new ServerSocket(0);
/* 162:    */           }
/* 163:    */           else
/* 164:    */           {
/* 165:191 */             for (int i = 0; i < ports.length; i++) {
/* 166:    */               try
/* 167:    */               {
/* 168:193 */                 ss = new ServerSocket(ports[i]);
/* 169:    */               }
/* 170:    */               catch (Exception localException1) {}
/* 171:    */             }
/* 172:201 */             if (ss == null) {
/* 173:203 */               throw new IOException("All ports returned by getDccPorts() are in use.");
/* 174:    */             }
/* 175:    */           }
/* 176:207 */           ss.setSoTimeout(DccFileTransfer.this._timeout);
/* 177:208 */           DccFileTransfer.this._port = ss.getLocalPort();
/* 178:209 */           InetAddress inetAddress = DccFileTransfer.this._bot.getDccInetAddress();
/* 179:210 */           if (inetAddress == null) {
/* 180:211 */             inetAddress = DccFileTransfer.this._bot.getInetAddress();
/* 181:    */           }
/* 182:213 */           byte[] ip = inetAddress.getAddress();
/* 183:214 */           long ipNum = DccFileTransfer.this._bot.ipToLong(ip);
/* 184:    */           
/* 185:    */ 
/* 186:    */ 
/* 187:218 */           String safeFilename = DccFileTransfer.this._file.getName().replace(' ', '_');
/* 188:219 */           safeFilename = safeFilename.replace('\t', '_');
/* 189:221 */           if (allowResume) {
/* 190:222 */             DccFileTransfer.this._manager.addAwaitingResume(DccFileTransfer.this);
/* 191:    */           }
/* 192:226 */           DccFileTransfer.this._bot.sendCTCPCommand(DccFileTransfer.this._nick, "DCC SEND " + safeFilename + " " + ipNum + " " + DccFileTransfer.this._port + " " + DccFileTransfer.this._file.length());
/* 193:    */           
/* 194:    */ 
/* 195:229 */           DccFileTransfer.this._socket = ss.accept();
/* 196:230 */           DccFileTransfer.this._socket.setSoTimeout(30000);
/* 197:231 */           DccFileTransfer.this._startTime = System.currentTimeMillis();
/* 198:234 */           if (allowResume) {
/* 199:235 */             DccFileTransfer.this._manager.removeAwaitingResume(DccFileTransfer.this);
/* 200:    */           }
/* 201:239 */           ss.close();
/* 202:    */           
/* 203:241 */           BufferedOutputStream output = new BufferedOutputStream(DccFileTransfer.this._socket.getOutputStream());
/* 204:242 */           BufferedInputStream input = new BufferedInputStream(DccFileTransfer.this._socket.getInputStream());
/* 205:243 */           finput = new BufferedInputStream(new FileInputStream(DccFileTransfer.this._file));
/* 206:246 */           if (DccFileTransfer.this._progress > 0L)
/* 207:    */           {
/* 208:247 */             long bytesSkipped = 0L;
/* 209:248 */             while (bytesSkipped < DccFileTransfer.this._progress) {
/* 210:249 */               bytesSkipped += finput.skip(DccFileTransfer.this._progress - bytesSkipped);
/* 211:    */             }
/* 212:    */           }
/* 213:253 */           byte[] outBuffer = new byte[1024];
/* 214:254 */           byte[] inBuffer = new byte[4];
/* 215:255 */           int bytesRead = 0;
/* 216:256 */           while ((bytesRead = finput.read(outBuffer, 0, outBuffer.length)) != -1)
/* 217:    */           {
/* 218:257 */             output.write(outBuffer, 0, bytesRead);
/* 219:258 */             output.flush();
/* 220:259 */             input.read(inBuffer, 0, inBuffer.length);
/* 221:260 */             DccFileTransfer.this._progress += bytesRead;
/* 222:261 */             DccFileTransfer.this.delay();
/* 223:    */           }
/* 224:    */         }
/* 225:    */         catch (Exception e)
/* 226:    */         {
/* 227:265 */           exception = e;
/* 228:    */           try
/* 229:    */           {
/* 230:269 */             finput.close();
/* 231:270 */             DccFileTransfer.this._socket.close();
/* 232:    */           }
/* 233:    */           catch (Exception localException2) {}
/* 234:    */         }
/* 235:    */         finally
/* 236:    */         {
/* 237:    */           try
/* 238:    */           {
/* 239:269 */             finput.close();
/* 240:270 */             DccFileTransfer.this._socket.close();
/* 241:    */           }
/* 242:    */           catch (Exception localException3) {}
/* 243:    */         }
/* 244:277 */         DccFileTransfer.this._bot.onFileTransferFinished(DccFileTransfer.this, exception);
/* 245:    */       }
/* 246:    */     }.start();
/* 247:    */   }
/* 248:    */   
/* 249:    */   void setProgress(long progress)
/* 250:    */   {
/* 251:287 */     this._progress = progress;
/* 252:    */   }
/* 253:    */   
/* 254:    */   private void delay()
/* 255:    */   {
/* 256:295 */     if (this._packetDelay > 0L) {
/* 257:    */       try
/* 258:    */       {
/* 259:297 */         Thread.sleep(this._packetDelay);
/* 260:    */       }
/* 261:    */       catch (InterruptedException localInterruptedException) {}
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String getNick()
/* 266:    */   {
/* 267:313 */     return this._nick;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String getLogin()
/* 271:    */   {
/* 272:324 */     return this._login;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String getHostname()
/* 276:    */   {
/* 277:335 */     return this._hostname;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public File getFile()
/* 281:    */   {
/* 282:346 */     return this._file;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public int getPort()
/* 286:    */   {
/* 287:357 */     return this._port;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public boolean isIncoming()
/* 291:    */   {
/* 292:369 */     return this._incoming;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean isOutgoing()
/* 296:    */   {
/* 297:381 */     return !isIncoming();
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setPacketDelay(long millis)
/* 301:    */   {
/* 302:395 */     this._packetDelay = millis;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public long getPacketDelay()
/* 306:    */   {
/* 307:406 */     return this._packetDelay;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public long getSize()
/* 311:    */   {
/* 312:417 */     return this._size;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public long getProgress()
/* 316:    */   {
/* 317:430 */     return this._progress;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public double getProgressPercentage()
/* 321:    */   {
/* 322:443 */     return 100.0D * (getProgress() / getSize());
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void close()
/* 326:    */   {
/* 327:    */     try
/* 328:    */     {
/* 329:452 */       this._socket.close();
/* 330:    */     }
/* 331:    */     catch (Exception localException) {}
/* 332:    */   }
/* 333:    */   
/* 334:    */   public long getTransferRate()
/* 335:    */   {
/* 336:468 */     long time = (System.currentTimeMillis() - this._startTime) / 1000L;
/* 337:469 */     if (time <= 0L) {
/* 338:470 */       return 0L;
/* 339:    */     }
/* 340:472 */     return getProgress() / time;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public long getNumericalAddress()
/* 344:    */   {
/* 345:481 */     return this._address;
/* 346:    */   }
/* 347:    */   
/* 348:488 */   private String _login = null;
/* 349:489 */   private String _hostname = null;
/* 350:    */   private String _type;
/* 351:    */   private long _address;
/* 352:    */   private int _port;
/* 353:    */   private long _size;
/* 354:    */   private boolean _received;
/* 355:496 */   private Socket _socket = null;
/* 356:497 */   private long _progress = 0L;
/* 357:498 */   private File _file = null;
/* 358:499 */   private int _timeout = 0;
/* 359:    */   private boolean _incoming;
/* 360:501 */   private long _packetDelay = 0L;
/* 361:503 */   private long _startTime = 0L;
/* 362:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.DccFileTransfer
 * JD-Core Version:    0.7.0.1
 */