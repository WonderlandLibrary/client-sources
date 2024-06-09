package com.client.glowclient;

import org.apache.commons.io.*;
import java.io.*;
import java.util.zip.*;
import java.text.*;
import java.util.*;

public class oA
{
    private static final DateFormat b;
    
    private static void M(final File srcFile, final File destFile, final int n, final L l) throws IOException {
        if (srcFile.isDirectory()) {
            if (!destFile.exists()) {
                destFile.mkdir();
            }
            final String[] list;
            final int length = (list = srcFile.list()).length;
            int n2;
            int i = n2 = 0;
            while (i < length) {
                final String s = list[n2];
                final File file = new File(srcFile, s);
                final File file2 = new File(destFile, s);
                ++n2;
                M(file, file2, n, l);
                i = n2;
            }
        }
        else {
            l.M(destFile.getPath().substring(n));
            FileUtils.copyFile(srcFile, destFile, true);
        }
    }
    
    private static void M(final File p0, final ZipOutputStream p1, final int p2, final L p3) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/io/File.listFiles:()[Ljava/io/File;
        //     4: dup            
        //     5: astore          4
        //     7: arraylength    
        //     8: istore          5
        //    10: iconst_0       
        //    11: dup            
        //    12: istore          6
        //    14: iload           5
        //    16: if_icmpge       216
        //    19: aload           4
        //    21: iload           6
        //    23: aaload         
        //    24: dup            
        //    25: astore          7
        //    27: invokevirtual   java/io/File.isFile:()Z
        //    30: ifeq            192
        //    33: aload           7
        //    35: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //    38: iload_2        
        //    39: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //    42: astore          8
        //    44: aload_3        
        //    45: aload           8
        //    47: invokeinterface com/client/glowclient/L.M:(Ljava/lang/String;)V
        //    52: new             Ljava/util/zip/ZipEntry;
        //    55: dup            
        //    56: aload           8
        //    58: invokespecial   java/util/zip/ZipEntry.<init>:(Ljava/lang/String;)V
        //    61: astore          8
        //    63: aload_1        
        //    64: aload           8
        //    66: invokevirtual   java/util/zip/ZipOutputStream.putNextEntry:(Ljava/util/zip/ZipEntry;)V
        //    69: new             Ljava/io/FileInputStream;
        //    72: dup            
        //    73: aload           7
        //    75: invokespecial   java/io/FileInputStream.<init>:(Ljava/io/File;)V
        //    78: astore          8
        //    80: aconst_null    
        //    81: astore          9
        //    83: aload           8
        //    85: aload_1        
        //    86: invokestatic    org/apache/commons/io/IOUtils.copy:(Ljava/io/InputStream;Ljava/io/OutputStream;)I
        //    89: pop            
        //    90: aload           8
        //    92: ifnull          185
        //    95: aload           9
        //    97: ifnull          124
        //   100: aload           8
        //   102: invokevirtual   java/io/FileInputStream.close:()V
        //   105: aload_1        
        //   106: goto            186
        //   109: athrow         
        //   110: astore          10
        //   112: aload_1        
        //   113: aload           9
        //   115: aload           10
        //   117: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   120: goto            186
        //   123: athrow         
        //   124: aload           8
        //   126: invokevirtual   java/io/FileInputStream.close:()V
        //   129: aload_1        
        //   130: goto            186
        //   133: astore          10
        //   135: aload           10
        //   137: dup            
        //   138: astore          9
        //   140: athrow         
        //   141: astore          11
        //   143: aload           8
        //   145: ifnull          182
        //   148: aload           9
        //   150: ifnull          177
        //   153: aload           8
        //   155: invokevirtual   java/io/FileInputStream.close:()V
        //   158: aload           11
        //   160: goto            184
        //   163: astore          10
        //   165: aload           11
        //   167: aload           9
        //   169: aload           10
        //   171: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   174: goto            184
        //   177: aload           8
        //   179: invokevirtual   java/io/FileInputStream.close:()V
        //   182: aload           11
        //   184: athrow         
        //   185: aload_1        
        //   186: invokevirtual   java/util/zip/ZipOutputStream.closeEntry:()V
        //   189: goto            208
        //   192: aload           7
        //   194: invokevirtual   java/io/File.isDirectory:()Z
        //   197: ifeq            208
        //   200: aload           7
        //   202: aload_1        
        //   203: iload_2        
        //   204: aload_3        
        //   205: invokestatic    com/client/glowclient/oA.M:(Ljava/io/File;Ljava/util/zip/ZipOutputStream;ILcom/client/glowclient/L;)V
        //   208: iinc            6, 1
        //   211: iload           6
        //   213: goto            14
        //   216: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    StackMapTable: 00 10 FF 00 0E 00 07 07 00 0D 07 00 41 01 07 00 1D 07 00 43 01 01 00 01 01 FF 00 5E 00 00 00 01 07 00 27 FF 00 00 00 0A 07 00 0D 07 00 41 01 07 00 1D 07 00 43 01 01 07 00 0D 07 00 50 05 00 01 07 00 27 FF 00 0C 00 00 00 01 07 00 27 FF 00 00 00 0A 07 00 0D 07 00 41 01 07 00 1D 07 00 43 01 01 07 00 0D 07 00 50 05 00 00 48 07 00 27 FF 00 07 00 0A 07 00 0D 07 00 41 01 07 00 1D 07 00 43 01 01 07 00 0D 07 00 50 07 00 27 00 01 07 00 27 FF 00 15 00 0C 07 00 0D 07 00 41 01 07 00 1D 07 00 43 01 01 07 00 0D 07 00 50 07 00 27 00 07 00 27 00 01 07 00 27 0D 04 41 07 00 27 FF 00 00 00 0A 07 00 0D 07 00 41 01 07 00 1D 07 00 43 01 01 07 00 0D 07 00 50 05 00 00 40 07 00 41 F9 00 05 0F FA 00 07
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  100    105    110    123    Ljava/lang/Throwable;
        //  83     90     133    141    Ljava/lang/Throwable;
        //  83     90     141    185    Any
        //  153    158    163    177    Ljava/lang/Throwable;
        //  133    143    141    185    Any
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private oA() {
        super();
    }
    
    static {
        b = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    }
    
    public static void D(final File file, final File file2, final L l) throws IOException {
        l.M(M(file));
        M(file, file2, file.getPath().length() + 1, l);
    }
    
    private static int M(final File file) {
        if (!file.isDirectory()) {
            return 0;
        }
        int n = 0;
        final File[] listFiles;
        final int length = (listFiles = file.listFiles()).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final File file2;
            if ((file2 = listFiles[n2]).isDirectory()) {
                n += M(file2);
            }
            else {
                ++n;
            }
            i = ++n2;
        }
        return n;
    }
    
    public static void M(final File p0, final File p1, final L p2) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: invokestatic    com/client/glowclient/oA.M:(Ljava/io/File;)I
        //     5: invokeinterface com/client/glowclient/L.M:(I)V
        //    10: new             Ljava/io/FileOutputStream;
        //    13: dup            
        //    14: aload_1        
        //    15: invokespecial   java/io/FileOutputStream.<init>:(Ljava/io/File;)V
        //    18: astore_1       
        //    19: aconst_null    
        //    20: astore_3       
        //    21: new             Ljava/util/zip/ZipOutputStream;
        //    24: dup            
        //    25: aload_1        
        //    26: invokespecial   java/util/zip/ZipOutputStream.<init>:(Ljava/io/OutputStream;)V
        //    29: astore          4
        //    31: aconst_null    
        //    32: astore          5
        //    34: aload_0        
        //    35: aload           4
        //    37: aload_0        
        //    38: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //    41: invokevirtual   java/lang/String.length:()I
        //    44: iconst_1       
        //    45: iadd           
        //    46: aload_2        
        //    47: invokestatic    com/client/glowclient/oA.M:(Ljava/io/File;Ljava/util/zip/ZipOutputStream;ILcom/client/glowclient/L;)V
        //    50: aload           4
        //    52: ifnull          139
        //    55: aload           5
        //    57: ifnull          82
        //    60: aload           4
        //    62: invokevirtual   java/util/zip/ZipOutputStream.close:()V
        //    65: aload_1        
        //    66: goto            140
        //    69: athrow         
        //    70: astore_2       
        //    71: aload_1        
        //    72: aload           5
        //    74: aload_2        
        //    75: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //    78: goto            140
        //    81: athrow         
        //    82: aload           4
        //    84: invokevirtual   java/util/zip/ZipOutputStream.close:()V
        //    87: aload_1        
        //    88: goto            140
        //    91: astore_2       
        //    92: aload_2        
        //    93: dup            
        //    94: astore          5
        //    96: athrow         
        //    97: astore          6
        //    99: aload           4
        //   101: ifnull          136
        //   104: aload           5
        //   106: ifnull          131
        //   109: aload           4
        //   111: invokevirtual   java/util/zip/ZipOutputStream.close:()V
        //   114: aload           6
        //   116: goto            138
        //   119: astore_2       
        //   120: aload           6
        //   122: aload           5
        //   124: aload_2        
        //   125: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   128: goto            138
        //   131: aload           4
        //   133: invokevirtual   java/util/zip/ZipOutputStream.close:()V
        //   136: aload           6
        //   138: athrow         
        //   139: aload_1        
        //   140: ifnull          210
        //   143: aload_3        
        //   144: ifnull          161
        //   147: aload_1        
        //   148: invokevirtual   java/io/FileOutputStream.close:()V
        //   151: return         
        //   152: astore          4
        //   154: aload_3        
        //   155: aload           4
        //   157: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   160: return         
        //   161: aload_1        
        //   162: invokevirtual   java/io/FileOutputStream.close:()V
        //   165: return         
        //   166: astore          4
        //   168: aload           4
        //   170: dup            
        //   171: astore_3       
        //   172: athrow         
        //   173: astore          7
        //   175: aload_1        
        //   176: ifnull          207
        //   179: aload_3        
        //   180: ifnull          203
        //   183: aload_1        
        //   184: invokevirtual   java/io/FileOutputStream.close:()V
        //   187: aload           7
        //   189: goto            209
        //   192: astore_2       
        //   193: aload           7
        //   195: aload_3        
        //   196: aload_2        
        //   197: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   200: goto            209
        //   203: aload_1        
        //   204: invokevirtual   java/io/FileOutputStream.close:()V
        //   207: aload           7
        //   209: athrow         
        //   210: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    StackMapTable: 00 15 FF 00 45 00 00 00 01 07 00 27 FF 00 00 00 06 07 00 0D 07 00 7E 07 00 1D 05 07 00 41 05 00 01 07 00 27 FF 00 0A 00 00 00 01 07 00 27 FF 00 00 00 06 07 00 0D 07 00 7E 07 00 1D 05 07 00 41 05 00 00 48 07 00 27 FF 00 05 00 06 07 00 0D 07 00 7E 07 00 04 05 07 00 41 07 00 27 00 01 07 00 27 FF 00 15 00 07 07 00 0D 07 00 7E 07 00 04 05 07 00 41 07 00 27 07 00 27 00 01 07 00 27 0B 04 41 07 00 27 FF 00 00 00 06 07 00 0D 07 00 7E 07 00 1D 05 07 00 41 05 00 00 FF 00 00 00 06 07 00 0D 07 00 7E 07 00 04 05 07 00 41 05 00 01 07 00 7E 4B 07 00 27 08 FF 00 04 00 04 07 00 0D 07 00 7E 07 00 04 05 00 01 07 00 27 FF 00 06 00 04 07 00 0D 07 00 7E 07 00 04 07 00 27 00 01 07 00 27 FF 00 12 00 08 07 00 0D 07 00 7E 07 00 04 07 00 27 00 00 00 07 00 27 00 01 07 00 27 0A 03 41 07 00 27 FF 00 00 00 06 07 00 0D 07 00 7E 07 00 04 05 07 00 41 05 00 00
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  60     65     70     81     Ljava/lang/Throwable;
        //  34     50     91     97     Ljava/lang/Throwable;
        //  34     50     97     139    Any
        //  109    114    119    131    Ljava/lang/Throwable;
        //  91     99     97     139    Any
        //  147    151    152    161    Ljava/lang/Throwable;
        //  21     69     166    173    Ljava/lang/Throwable;
        //  70     81     166    173    Ljava/lang/Throwable;
        //  82     139    166    173    Ljava/lang/Throwable;
        //  21     69     173    210    Any
        //  70     81     173    210    Any
        //  82     139    173    210    Any
        //  183    187    192    203    Ljava/lang/Throwable;
        //  166    175    173    210    Any
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void M(final File file, String string, final Fd fd, final L l) throws IOException {
        string = new StringBuilder().insert(0, string).append("_").append(oA.b.format(new Date())).toString();
        switch (Kb.b[fd.ordinal()]) {
            case 1: {}
            case 2: {
                final File file2;
                if ((file2 = new File(file.getParentFile(), string)).exists()) {
                    throw new IOException(new StringBuilder().insert(0, "Backup folder (").append(file2).append(") already exists!").toString());
                }
                D(file, file2, l);
            }
            case 3: {
                final File file3;
                if ((file3 = new File(file.getParentFile(), new StringBuilder().insert(0, string).append(".zip").toString())).exists()) {
                    throw new IOException(new StringBuilder().insert(0, "Backup file (").append(file3).append(") already exists!").toString());
                }
                M(file, file3, l);
            }
            default: {}
        }
    }
}
