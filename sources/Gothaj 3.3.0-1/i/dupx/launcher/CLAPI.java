package i.dupx.launcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

public class CLAPI {
   private static final long USER_CACHE_EXPIRE_TIME = 60000L;
   private static final Map<String, CLAPI.CLUserInfo> userInfoCache = new HashMap<>();
   private static final CLAPI.CLUserInfo loadingUserInfo = new CLAPI.CLUserInfo(null);
   private static String lastServer = null;
   private static boolean privacyMode = System.getProperty("clprivacy", "false").equals("true");
   private static CLAPI.IClient client;
   private static List<byte[]> packetQueue = new LinkedList<>();
   private static Set<String> friends = new HashSet<>();
   private static BiConsumer<Integer, DataInputStream> internalDataProcessor;

   static {
      new Thread(CLAPI::cleanupCache).start();
      new Thread(CLAPI::clapi_reader).start();
   }

   public static void setClassAcceptor(BiConsumer<Integer, DataInputStream> internalDataProcessor) {
      CLAPI.internalDataProcessor = internalDataProcessor;
   }

   public static void setClient(CLAPI.IClient client) {
      CLAPI.client = client;
   }

   public static Map<String, CLAPI.CLUserInfo> getUserInfoCache() {
      return Collections.unmodifiableMap(userInfoCache);
   }

   public static boolean getPrivacyMode() {
      return privacyMode;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void setPrivacyMode(boolean privacyMode) {
      CLAPI.privacyMode = privacyMode;

      try {
         Throwable t = null;
         Object var2 = null;

         try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
               DataOutputStream dos = new DataOutputStream(bos);

               try {
                  dos.writeByte(5);
                  dos.writeBoolean(privacyMode);
                  sendPacket(bos.toByteArray());
               } finally {
                  if (dos != null) {
                     dos.close();
                  }
               }
            } catch (Throwable var18) {
               if (t == null) {
                  t = var18;
               } else if (t != var18) {
                  t.addSuppressed(var18);
               }

               if (bos != null) {
                  bos.close();
               }

               throw t;
            }

            if (bos != null) {
               bos.close();
            }
         } catch (Throwable var19) {
            if (t == null) {
               t = var19;
            } else if (t != var19) {
               t.addSuppressed(var19);
            }

            throw t;
         }
      } catch (Throwable var20) {
         System.err.println("Failed to send setPrivacyMode(" + privacyMode + ")");
         var20.printStackTrace();
      }
   }

   public static boolean isFriend(String username) {
      synchronized (friends) {
         return friends.contains(username);
      }
   }

   public static String getCLUsername() {
      return System.getProperty("clname", "");
   }

   public static String getCLToken() {
      return System.getProperty("cltoken", "");
   }

   public static int getClientRole() {
      return Integer.parseInt(System.getProperty("clrole", ""));
   }

   public static long getFirstBuyStamp() {
      return Long.parseLong(System.getProperty("clfirstBuy", "0"));
   }

   public static String getBranch() {
      return System.getProperty("clbranch", "");
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void reportUsername(String username, String server) {
      if (!Objects.equals(server, lastServer)) {
         synchronized (userInfoCache) {
            userInfoCache.clear();
         }

         lastServer = server;
      }

      try {
         Throwable var26 = null;
         Object var3 = null;

         try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
               DataOutputStream dos = new DataOutputStream(bos);

               try {
                  dos.writeByte(2);
                  dos.writeBoolean(username != null);
                  if (username != null) {
                     writeString(dos, username);
                  }

                  dos.writeBoolean(server != null);
                  if (server != null) {
                     writeString(dos, server);
                  }

                  sendPacket(bos.toByteArray());
               } finally {
                  if (dos != null) {
                     dos.close();
                  }
               }
            } catch (Throwable var23) {
               if (var26 == null) {
                  var26 = var23;
               } else if (var26 != var23) {
                  var26.addSuppressed(var23);
               }

               if (bos != null) {
                  bos.close();
               }

               throw var26;
            }

            if (bos != null) {
               bos.close();
            }
         } catch (Throwable var24) {
            if (var26 == null) {
               var26 = var24;
            } else if (var26 != var24) {
               var26.addSuppressed(var24);
            }

            throw var26;
         }
      } catch (Throwable var25) {
         System.err.println("Failed to send reportUsername('" + username + "', '" + server + "')");
         var25.printStackTrace();
      }
   }

   public static String getCapeUrl(String username) {
      return "https://api.haze.yt:8443/getCape?token=" + getCLToken() + "&username=" + URLEncoder.encode(username);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static CLAPI.CLUserInfo getUserInfo(String username) {
      synchronized (userInfoCache) {
         CLAPI.CLUserInfo userData = userInfoCache.get(username);
         if (userData == null) {
            userData = loadingUserInfo;
            userInfoCache.put(username, loadingUserInfo);

            try {
               Throwable t = null;
               Object var4 = null;

               try {
                  ByteArrayOutputStream bos = new ByteArrayOutputStream();

                  try {
                     DataOutputStream dos = new DataOutputStream(bos);

                     try {
                        dos.writeByte(3);
                        writeString(dos, username);
                        sendPacket(bos.toByteArray());
                     } finally {
                        if (dos != null) {
                           dos.close();
                        }
                     }
                  } catch (Throwable var23) {
                     if (t == null) {
                        t = var23;
                     } else if (t != var23) {
                        t.addSuppressed(var23);
                     }

                     if (bos != null) {
                        bos.close();
                     }

                     throw t;
                  }

                  if (bos != null) {
                     bos.close();
                  }
               } catch (Throwable var24) {
                  if (t == null) {
                     t = var24;
                  } else if (t != var24) {
                     t.addSuppressed(var24);
                  }

                  throw t;
               }
            } catch (Throwable var25) {
               System.err.println("Failed to send setPrivacyMode(" + privacyMode + ")");
            }
         }

         return userData;
      }
   }

   private static void cleanupCache() {
      Thread.currentThread().setName("CLAPI Cleanup Cache");

      while (true) {
         try {
            long time = System.currentTimeMillis();
            synchronized (userInfoCache) {
               userInfoCache.values().removeIf(userInfo -> time > userInfo.expire);
            }
         } catch (Throwable var13) {
            var13.printStackTrace();
         } finally {
            try {
               Thread.sleep(30000L);
            } catch (InterruptedException var11) {
            }
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void clapi_reader() {
      Thread.currentThread().setName("CLAPI CL Connection");

      try {
         Throwable var0 = null;
         Object var1 = null;

         try {
            Socket socket = new Socket("127.0.0.1", Integer.parseInt(System.getProperty("clport", "0")));

            try {
               DataInputStream dataInput = new DataInputStream(socket.getInputStream());
               DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
               new Thread(() -> clapi_writer(dataOutput)).start();

               while (socket.isConnected() && !socket.isClosed()) {
                  byte[] data = readByteArray(dataInput);

                  try {
                     Throwable t = null;
                     Object var7 = null;

                     try {
                        DataInputStream packetStream = new DataInputStream(new ByteArrayInputStream(data));

                        try {
                           readPacket(packetStream.readUnsignedByte(), packetStream);
                        } finally {
                           if (packetStream != null) {
                              packetStream.close();
                           }
                        }
                     } catch (Throwable var38) {
                        if (t == null) {
                           t = var38;
                        } else if (t != var38) {
                           t.addSuppressed(var38);
                        }

                        throw t;
                     }
                  } catch (SocketException var39) {
                     break;
                  } catch (Throwable var40) {
                     System.err.println("Failed to read packet data");
                     var40.printStackTrace();
                  }
               }
            } finally {
               if (socket != null) {
                  socket.close();
               }
            }
         } catch (Throwable var42) {
            if (var0 == null) {
               var0 = var42;
            } else if (var0 != var42) {
               var0.addSuppressed(var42);
            }

            throw var0;
         }
      } catch (Throwable var43) {
      }
   }

   private static void clapi_writer(DataOutputStream output) {
      Thread.currentThread().setName("CLAPI CL Connection");

      while (true) {
         try {
            byte[] packet;
            synchronized (packetQueue) {
               packet = packetQueue.isEmpty() ? null : packetQueue.remove(0);
            }

            if (packet == null) {
               Thread.sleep(1L);
            } else {
               output.writeInt(packet.length);
               output.write(packet);
               output.flush();
            }
         } catch (Throwable var4) {
            System.err.println("Failed to write packet data");
            var4.printStackTrace();
         }
      }
   }

   public static void sendPacket(byte[] data) {
      synchronized (packetQueue) {
         packetQueue.add(data);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void sendError(String error) throws IOException {
      Throwable var1 = null;
      Object var2 = null;

      try {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();

         try {
            DataOutputStream dos = new DataOutputStream(bos);

            try {
               dos.writeByte(7);
               writeString(dos, error);
               sendPacket(bos.toByteArray());
            } finally {
               if (dos != null) {
                  dos.close();
               }
            }
         } catch (Throwable var15) {
            if (var1 == null) {
               var1 = var15;
            } else if (var1 != var15) {
               var1.addSuppressed(var15);
            }

            if (bos != null) {
               bos.close();
            }

            throw var1;
         }

         if (bos != null) {
            bos.close();
         }
      } catch (Throwable var16) {
         if (var1 == null) {
            var1 = var16;
         } else if (var1 != var16) {
            var1.addSuppressed(var16);
         }

         throw var1;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   protected static void readPacket(int packetType, DataInputStream input) throws IOException {
      switch (packetType) {
         case 0:
            if (client != null) {
               client.loadCurrentConfig(readString(input));
            } else {
               sendError("Client = null");
            }
            break;
         case 1:
            if (client != null) {
               Throwable var28 = null;
               Object var29 = null;

               try {
                  ByteArrayOutputStream bos = new ByteArrayOutputStream();

                  try {
                     DataOutputStream dos = new DataOutputStream(bos);

                     try {
                        dos.writeByte(1);
                        dos.writeInt(input.readInt());
                        writeString(dos, client.writeCurrentConfig());
                        sendPacket(bos.toByteArray());
                     } finally {
                        if (dos != null) {
                           dos.close();
                        }
                     }
                  } catch (Throwable var24) {
                     if (var28 == null) {
                        var28 = var24;
                     } else if (var28 != var24) {
                        var28.addSuppressed(var24);
                     }

                     if (bos != null) {
                        bos.close();
                     }

                     throw var28;
                  }

                  if (bos != null) {
                     bos.close();
                  }
               } catch (Throwable var25) {
                  if (var28 == null) {
                     var28 = var25;
                  } else if (var28 != var25) {
                     var28.addSuppressed(var25);
                  }

                  throw var28;
               }
            } else {
               sendError("Client = null");
            }
            break;
         case 2:
         default:
            internalDataProcessor.accept(packetType, input);
            break;
         case 3:
            synchronized (userInfoCache) {
               userInfoCache.put(readString(input), new CLAPI.CLUserInfo(input, null));
               break;
            }
         case 4:
            synchronized (friends) {
               friends.clear();

               String[] var6;
               for (String added : var6 = readStringArray(input)) {
                  friends.add(added);
               }
               break;
            }
         case 5:
            privacyMode = input.readBoolean();
            break;
         case 6:
            if (client != null) {
               client.joinServer(readString(input));
            } else {
               sendError("Client = null");
            }
      }
   }

   protected static String[] readStringArray(DataInputStream input) throws IOException {
      String[] strings = new String[input.readUnsignedShort()];

      for (int i = 0; i < strings.length; i++) {
         strings[i] = readString(input);
      }

      return strings;
   }

   public static byte[] readByteArray(DataInputStream input) throws IOException {
      byte[] bytes = new byte[input.readInt()];
      input.readFully(bytes);
      return bytes;
   }

   protected static void writeString(DataOutputStream output, String str) throws IOException {
      writeByteArray(output, str.getBytes("UTF-8"));
   }

   protected static void writeByteArray(DataOutputStream output, byte[] bytes) throws IOException {
      output.writeInt(bytes.length);
      output.write(bytes);
   }

   protected static String readString(DataInputStream input) throws IOException {
      return new String(readByteArray(input), "UTF-8");
   }

   public static class CLUserInfo {
      public final String nickname;
      public final int role;
      public final String runningClient;
      public final String runningBranch;
      public final String mc_name;
      private long expire;

      private CLUserInfo(DataInputStream dis) throws IOException {
         if (dis.readBoolean()) {
            this.nickname = CLAPI.readString(dis);
            this.role = dis.readUnsignedByte();
            this.runningClient = CLAPI.readString(dis);
            this.runningBranch = CLAPI.readString(dis);
            this.mc_name = CLAPI.readString(dis);
            this.expire = System.currentTimeMillis() + 60000L;
         } else {
            this.nickname = "None";
            this.role = Integer.MAX_VALUE;
            this.runningClient = "-";
            this.runningBranch = "-";
            this.mc_name = "-";
         }
      }

      private CLUserInfo() {
         this.nickname = "Loading...";
         this.role = Integer.MAX_VALUE;
         this.runningClient = "None";
         this.runningBranch = "None";
         this.expire = Long.MAX_VALUE;
         this.mc_name = "Unknown";
      }

      @Override
      public String toString() {
         return "nickname="
            + this.nickname
            + ","
            + "role="
            + this.role
            + ","
            + "runningClient="
            + this.runningClient
            + ","
            + "runningBranch="
            + this.runningBranch;
      }
   }

   public interface IClient {
      String writeCurrentConfig();

      void loadCurrentConfig(String var1);

      void joinServer(String var1);
   }
}
