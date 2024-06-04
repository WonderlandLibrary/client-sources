package yung.purity.management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import yung.purity.Client;













public class FileManager
{
  private static File dir = new File(getMinecraftmcDataDir, instancename);
  
  public FileManager() {}
  
  public static void init() {
    if (!dir.exists()) {
      dir.mkdir();
    }
  }
  
  /* Error */
  public static java.util.List<String> read(String file)
  {
    // Byte code:
    //   0: new 54	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 55	java/util/ArrayList:<init>	()V
    //   7: astore_1
    //   8: getstatic 36	yung/purity/management/FileManager:dir	Ljava/io/File;
    //   11: invokevirtual 45	java/io/File:exists	()Z
    //   14: ifne +10 -> 24
    //   17: getstatic 36	yung/purity/management/FileManager:dir	Ljava/io/File;
    //   20: invokevirtual 48	java/io/File:mkdir	()Z
    //   23: pop
    //   24: new 11	java/io/File
    //   27: dup
    //   28: getstatic 36	yung/purity/management/FileManager:dir	Ljava/io/File;
    //   31: aload_0
    //   32: invokespecial 34	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   35: astore_2
    //   36: aload_2
    //   37: invokevirtual 45	java/io/File:exists	()Z
    //   40: ifne +8 -> 48
    //   43: aload_2
    //   44: invokevirtual 60	java/io/File:createNewFile	()Z
    //   47: pop
    //   48: aconst_null
    //   49: astore_3
    //   50: aconst_null
    //   51: astore 4
    //   53: new 62	java/io/FileInputStream
    //   56: dup
    //   57: aload_2
    //   58: invokespecial 65	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   61: astore 5
    //   63: new 67	java/io/InputStreamReader
    //   66: dup
    //   67: aload 5
    //   69: invokespecial 70	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   72: astore 6
    //   74: new 72	java/io/BufferedReader
    //   77: dup
    //   78: aload 6
    //   80: invokespecial 75	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   83: astore 7
    //   85: ldc 77
    //   87: astore 8
    //   89: goto +12 -> 101
    //   92: aload_1
    //   93: aload 8
    //   95: invokeinterface 85 2 0
    //   100: pop
    //   101: aload 7
    //   103: invokevirtual 89	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   106: dup
    //   107: astore 8
    //   109: ifnonnull -17 -> 92
    //   112: aload 7
    //   114: ifnull +24 -> 138
    //   117: aload 7
    //   119: invokevirtual 92	java/io/BufferedReader:close	()V
    //   122: goto +16 -> 138
    //   125: astore_3
    //   126: aload 7
    //   128: ifnull +8 -> 136
    //   131: aload 7
    //   133: invokevirtual 92	java/io/BufferedReader:close	()V
    //   136: aload_3
    //   137: athrow
    //   138: aload 6
    //   140: ifnull +47 -> 187
    //   143: aload 6
    //   145: invokevirtual 93	java/io/InputStreamReader:close	()V
    //   148: goto +39 -> 187
    //   151: astore 4
    //   153: aload_3
    //   154: ifnonnull +9 -> 163
    //   157: aload 4
    //   159: astore_3
    //   160: goto +15 -> 175
    //   163: aload_3
    //   164: aload 4
    //   166: if_acmpeq +9 -> 175
    //   169: aload_3
    //   170: aload 4
    //   172: invokevirtual 97	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   175: aload 6
    //   177: ifnull +8 -> 185
    //   180: aload 6
    //   182: invokevirtual 93	java/io/InputStreamReader:close	()V
    //   185: aload_3
    //   186: athrow
    //   187: aload 5
    //   189: ifnull +78 -> 267
    //   192: aload 5
    //   194: invokevirtual 98	java/io/FileInputStream:close	()V
    //   197: goto +70 -> 267
    //   200: astore 4
    //   202: aload_3
    //   203: ifnonnull +9 -> 212
    //   206: aload 4
    //   208: astore_3
    //   209: goto +15 -> 224
    //   212: aload_3
    //   213: aload 4
    //   215: if_acmpeq +9 -> 224
    //   218: aload_3
    //   219: aload 4
    //   221: invokevirtual 97	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   224: aload 5
    //   226: ifnull +8 -> 234
    //   229: aload 5
    //   231: invokevirtual 98	java/io/FileInputStream:close	()V
    //   234: aload_3
    //   235: athrow
    //   236: astore 4
    //   238: aload_3
    //   239: ifnonnull +9 -> 248
    //   242: aload 4
    //   244: astore_3
    //   245: goto +15 -> 260
    //   248: aload_3
    //   249: aload 4
    //   251: if_acmpeq +9 -> 260
    //   254: aload_3
    //   255: aload 4
    //   257: invokevirtual 97	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   260: aload_3
    //   261: athrow
    //   262: astore_2
    //   263: aload_2
    //   264: invokevirtual 101	java/io/IOException:printStackTrace	()V
    //   267: aload_1
    //   268: areturn
    // Line number table:
    //   Java source line #36	-> byte code offset #0
    //   Java source line #39	-> byte code offset #8
    //   Java source line #40	-> byte code offset #17
    //   Java source line #43	-> byte code offset #24
    //   Java source line #45	-> byte code offset #36
    //   Java source line #46	-> byte code offset #43
    //   Java source line #50	-> byte code offset #48
    //   Java source line #51	-> byte code offset #63
    //   Java source line #52	-> byte code offset #74
    //   Java source line #53	-> byte code offset #85
    //   Java source line #54	-> byte code offset #89
    //   Java source line #55	-> byte code offset #92
    //   Java source line #54	-> byte code offset #101
    //   Java source line #57	-> byte code offset #112
    //   Java source line #60	-> byte code offset #262
    //   Java source line #61	-> byte code offset #263
    //   Java source line #64	-> byte code offset #267
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	269	0	file	String
    //   7	261	1	out	java.util.List<String>
    //   35	23	2	f	File
    //   262	2	2	e	IOException
    //   49	1	3	localObject1	Object
    //   125	29	3	localObject2	Object
    //   159	102	3	localObject3	Object
    //   51	1	4	localObject4	Object
    //   151	20	4	localThrowable1	Throwable
    //   200	20	4	localThrowable2	Throwable
    //   236	20	4	localThrowable3	Throwable
    //   61	169	5	fis	java.io.FileInputStream
    //   72	109	6	isr	java.io.InputStreamReader
    //   83	49	7	br	java.io.BufferedReader
    //   87	21	8	line	String
    // Exception table:
    //   from	to	target	type
    //   85	112	125	finally
    //   74	138	151	finally
    //   63	187	200	finally
    //   53	236	236	finally
    //   8	262	262	java/io/IOException
  }
  
  public static void save(String file, String content)
  {
    try
    {
      File f = new File(dir, file);
      
      if (!f.exists()) {
        f.createNewFile();
      }
      
      Object localObject1 = null;Object localObject4 = null; Object localObject3; label95: try { writer = new FileWriter(f);
      } finally { FileWriter writer;
        localObject3 = localThrowable; break label95; if (localObject3 != localThrowable) localObject3.addSuppressed(localThrowable);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
