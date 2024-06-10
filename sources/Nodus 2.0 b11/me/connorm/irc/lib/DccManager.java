/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class DccManager
/*   7:    */ {
/*   8:    */   private PircBot _bot;
/*   9:    */   
/*  10:    */   DccManager(PircBot bot)
/*  11:    */   {
/*  12: 36 */     this._bot = bot;
/*  13:    */   }
/*  14:    */   
/*  15:    */   boolean processRequest(String nick, String login, String hostname, String request)
/*  16:    */   {
/*  17: 46 */     StringTokenizer tokenizer = new StringTokenizer(request);
/*  18: 47 */     tokenizer.nextToken();
/*  19: 48 */     String type = tokenizer.nextToken();
/*  20: 49 */     String filename = tokenizer.nextToken();
/*  21: 51 */     if (type.equals("SEND"))
/*  22:    */     {
/*  23: 52 */       long address = Long.parseLong(tokenizer.nextToken());
/*  24: 53 */       int port = Integer.parseInt(tokenizer.nextToken());
/*  25: 54 */       long size = -1L;
/*  26:    */       try
/*  27:    */       {
/*  28: 56 */         size = Long.parseLong(tokenizer.nextToken());
/*  29:    */       }
/*  30:    */       catch (Exception localException) {}
/*  31: 62 */       DccFileTransfer transfer = new DccFileTransfer(this._bot, this, nick, login, hostname, type, filename, address, port, size);
/*  32: 63 */       this._bot.onIncomingFileTransfer(transfer);
/*  33:    */     }
/*  34: 66 */     else if (type.equals("RESUME"))
/*  35:    */     {
/*  36: 67 */       int port = Integer.parseInt(tokenizer.nextToken());
/*  37: 68 */       long progress = Long.parseLong(tokenizer.nextToken());
/*  38:    */       
/*  39: 70 */       DccFileTransfer transfer = null;
/*  40: 71 */       synchronized (this._awaitingResume)
/*  41:    */       {
/*  42: 72 */         for (int i = 0; i < this._awaitingResume.size(); i++)
/*  43:    */         {
/*  44: 73 */           transfer = (DccFileTransfer)this._awaitingResume.elementAt(i);
/*  45: 74 */           if ((transfer.getNick().equals(nick)) && (transfer.getPort() == port))
/*  46:    */           {
/*  47: 75 */             this._awaitingResume.removeElementAt(i);
/*  48: 76 */             break;
/*  49:    */           }
/*  50:    */         }
/*  51:    */       }
/*  52: 81 */       if (transfer != null)
/*  53:    */       {
/*  54: 82 */         transfer.setProgress(progress);
/*  55: 83 */         this._bot.sendCTCPCommand(nick, "DCC ACCEPT file.ext " + port + " " + progress);
/*  56:    */       }
/*  57:    */     }
/*  58: 87 */     else if (type.equals("ACCEPT"))
/*  59:    */     {
/*  60: 88 */       int port = Integer.parseInt(tokenizer.nextToken());
/*  61: 89 */       long progress = Long.parseLong(tokenizer.nextToken());
/*  62:    */       
/*  63: 91 */       DccFileTransfer transfer = null;
/*  64: 92 */       synchronized (this._awaitingResume)
/*  65:    */       {
/*  66: 93 */         for (int i = 0; i < this._awaitingResume.size(); i++)
/*  67:    */         {
/*  68: 94 */           transfer = (DccFileTransfer)this._awaitingResume.elementAt(i);
/*  69: 95 */           if ((transfer.getNick().equals(nick)) && (transfer.getPort() == port))
/*  70:    */           {
/*  71: 96 */             this._awaitingResume.removeElementAt(i);
/*  72: 97 */             break;
/*  73:    */           }
/*  74:    */         }
/*  75:    */       }
/*  76:102 */       if (transfer != null) {
/*  77:103 */         transfer.doReceive(transfer.getFile(), true);
/*  78:    */       }
/*  79:    */     }
/*  80:107 */     else if (type.equals("CHAT"))
/*  81:    */     {
/*  82:108 */       long address = Long.parseLong(tokenizer.nextToken());
/*  83:109 */       int port = Integer.parseInt(tokenizer.nextToken());
/*  84:    */       
/*  85:111 */       final DccChat chat = new DccChat(this._bot, nick, login, hostname, address, port);
/*  86:    */       
/*  87:113 */       new Thread()
/*  88:    */       {
/*  89:    */         public void run()
/*  90:    */         {
/*  91:115 */           DccManager.this._bot.onIncomingChatRequest(chat);
/*  92:    */         }
/*  93:    */       }.start();
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:120 */       return false;
/*  98:    */     }
/*  99:123 */     return true;
/* 100:    */   }
/* 101:    */   
/* 102:    */   void addAwaitingResume(DccFileTransfer transfer)
/* 103:    */   {
/* 104:134 */     synchronized (this._awaitingResume)
/* 105:    */     {
/* 106:135 */       this._awaitingResume.addElement(transfer);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   void removeAwaitingResume(DccFileTransfer transfer)
/* 111:    */   {
/* 112:144 */     this._awaitingResume.removeElement(transfer);
/* 113:    */   }
/* 114:    */   
/* 115:149 */   private Vector _awaitingResume = new Vector();
/* 116:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.DccManager
 * JD-Core Version:    0.7.0.1
 */