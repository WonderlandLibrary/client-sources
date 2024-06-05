package ClassSub;

import javafx.scene.media.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import java.util.concurrent.*;
import net.minecraft.client.renderer.*;
import java.net.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class Class286
{
    public static Class286 instance;
    public Class296 currentTrack;
    public MediaPlayer mediaPlayer;
    public Thread loadingThread;
    public double volume;
    public ArrayList<Class296> allTracks;
    public HashMap<Long, ResourceLocation> artsLocations;
    public HashMap<Long, ResourceLocation> circleLocations;
    public File musicFolder;
    public File circleImage;
    public ArrayList<Class63> lyric;
    public ArrayList<Class63> tLyric;
    public boolean isLoop;
    public boolean playLyric;
    public boolean playTranslateLyric;
    public static boolean lyricCoding;
    public static boolean showMsg;
    
    
    public Class286() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokespecial   java/lang/Object.<init>:()V
        //     8: aload_0        
        //     9: aconst_null    
        //    10: putfield        ClassSub/Class286.currentTrack:LClassSub/Class296;
        //    13: aload_0        
        //    14: aconst_null    
        //    15: putfield        ClassSub/Class286.loadingThread:Ljava/lang/Thread;
        //    18: aload_0        
        //    19: ldc2_w          0.2
        //    22: putfield        ClassSub/Class286.volume:D
        //    25: aload_0        
        //    26: new             Ljava/util/ArrayList;
        //    29: dup            
        //    30: invokespecial   java/util/ArrayList.<init>:()V
        //    33: putfield        ClassSub/Class286.allTracks:Ljava/util/ArrayList;
        //    36: aload_0        
        //    37: new             Ljava/util/HashMap;
        //    40: dup            
        //    41: invokespecial   java/util/HashMap.<init>:()V
        //    44: putfield        ClassSub/Class286.artsLocations:Ljava/util/HashMap;
        //    47: aload_0        
        //    48: new             Ljava/util/HashMap;
        //    51: dup            
        //    52: invokespecial   java/util/HashMap.<init>:()V
        //    55: putfield        ClassSub/Class286.circleLocations:Ljava/util/HashMap;
        //    58: aload_0        
        //    59: new             Ljava/util/ArrayList;
        //    62: dup            
        //    63: invokespecial   java/util/ArrayList.<init>:()V
        //    66: putfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //    69: aload_0        
        //    70: new             Ljava/util/ArrayList;
        //    73: dup            
        //    74: invokespecial   java/util/ArrayList.<init>:()V
        //    77: putfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //    80: aload_0        
        //    81: ldc             0
        //    83: putfield        ClassSub/Class286.isLoop:Z
        //    86: aload_0        
        //    87: ldc             0
        //    89: putfield        ClassSub/Class286.playLyric:Z
        //    92: aload_0        
        //    93: ldc             0
        //    95: putfield        ClassSub/Class286.playTranslateLyric:Z
        //    98: aload_0        
        //    99: putstatic       ClassSub/Class286.instance:LClassSub/Class286;
        //   102: aload_0        
        //   103: new             Ljava/io/File;
        //   106: dup            
        //   107: new             Ljava/lang/StringBuilder;
        //   110: dup            
        //   111: invokespecial   java/lang/StringBuilder.<init>:()V
        //   114: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   117: getfield        net/minecraft/client/Minecraft.mcDataDir:Ljava/io/File;
        //   120: invokevirtual   java/io/File.toString:()Ljava/lang/String;
        //   123: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   126: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   129: getstatic       java/io/File.separator:Ljava/lang/String;
        //   132: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   135: ldc             "Hanabi"
        //   137: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   140: getstatic       java/io/File.separator:Ljava/lang/String;
        //   143: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   146: ldc             "music"
        //   148: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   151: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   154: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   157: putfield        ClassSub/Class286.musicFolder:Ljava/io/File;
        //   160: aload_0        
        //   161: getfield        ClassSub/Class286.musicFolder:Ljava/io/File;
        //   164: invokevirtual   java/io/File.exists:()Z
        //   167: ifne            178
        //   170: aload_0        
        //   171: getfield        ClassSub/Class286.musicFolder:Ljava/io/File;
        //   174: invokevirtual   java/io/File.mkdirs:()Z
        //   177: pop            
        //   178: aload_0        
        //   179: new             Ljava/io/File;
        //   182: dup            
        //   183: new             Ljava/lang/StringBuilder;
        //   186: dup            
        //   187: invokespecial   java/lang/StringBuilder.<init>:()V
        //   190: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   193: getfield        net/minecraft/client/Minecraft.mcDataDir:Ljava/io/File;
        //   196: invokevirtual   java/io/File.toString:()Ljava/lang/String;
        //   199: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   202: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   205: getstatic       java/io/File.separator:Ljava/lang/String;
        //   208: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   211: ldc             "Hanabi"
        //   213: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   216: getstatic       java/io/File.separator:Ljava/lang/String;
        //   219: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   222: ldc             "circleImage"
        //   224: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   227: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   230: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   233: putfield        ClassSub/Class286.circleImage:Ljava/io/File;
        //   236: aload_0        
        //   237: getfield        ClassSub/Class286.circleImage:Ljava/io/File;
        //   240: invokevirtual   java/io/File.exists:()Z
        //   243: ifne            254
        //   246: aload_0        
        //   247: getfield        ClassSub/Class286.circleImage:Ljava/io/File;
        //   250: invokevirtual   java/io/File.mkdirs:()Z
        //   253: pop            
        //   254: new             Ljava/util/concurrent/CountDownLatch;
        //   257: dup            
        //   258: ldc             1
        //   260: invokespecial   java/util/concurrent/CountDownLatch.<init>:(I)V
        //   263: astore_1       
        //   264: new             LClassSub/Class112;
        //   267: dup            
        //   268: aload_0        
        //   269: aload_1        
        //   270: invokespecial   ClassSub/Class112.<init>:(LClassSub/Class286;Ljava/util/concurrent/CountDownLatch;)V
        //   273: invokestatic    javax/swing/SwingUtilities.invokeLater:(Ljava/lang/Runnable;)V
        //   276: aload_1        
        //   277: invokevirtual   java/util/concurrent/CountDownLatch.await:()V
        //   280: goto            290
        //   283: nop            
        //   284: athrow         
        //   285: astore_2       
        //   286: aload_2        
        //   287: invokevirtual   java/lang/InterruptedException.printStackTrace:()V
        //   290: return         
        //   291: nop            
        //   292: nop            
        //   293: nop            
        //   294: athrow         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  276    280    285    290    Ljava/lang/InterruptedException;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void getCircle(final Class296 class296) {
        class Class105 implements IImageBuffer
        {
            final Class296 val$track;
            final ResourceLocation val$rl2;
            final Class286 this$0;
            
            
            Class105(final Class286 this$0, final Class296 val$track, final ResourceLocation val$rl2) {
                this.this$0 = this$0;
                this.val$track = val$track;
                this.val$rl2 = val$rl2;
            }
            
            public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
                return bufferedImage;
            }
            
            public void skinAvailable() {
                this.this$0.circleLocations.put(this.val$track.getId(), this.val$rl2);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: getfield        ClassSub/Class286.circleLocations:Ljava/util/HashMap;
        //     8: aload_1        
        //     9: invokevirtual   ClassSub/Class296.getId:()J
        //    12: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //    15: invokevirtual   java/util/HashMap.containsKey:(Ljava/lang/Object;)Z
        //    18: ifeq            27
        //    21: return         
        //    22: nop            
        //    23: nop            
        //    24: nop            
        //    25: nop            
        //    26: athrow         
        //    27: new             Ljava/io/File;
        //    30: dup            
        //    31: new             Ljava/lang/StringBuilder;
        //    34: dup            
        //    35: invokespecial   java/lang/StringBuilder.<init>:()V
        //    38: aload_0        
        //    39: getfield        ClassSub/Class286.circleImage:Ljava/io/File;
        //    42: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //    45: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    48: getstatic       java/io/File.separator:Ljava/lang/String;
        //    51: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    54: aload_1        
        //    55: invokevirtual   ClassSub/Class296.getId:()J
        //    58: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //    61: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    64: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    67: invokevirtual   java/io/File.exists:()Z
        //    70: ifne            115
        //    73: aload_0        
        //    74: aload_1        
        //    75: ldc             128
        //    77: new             Ljava/lang/StringBuilder;
        //    80: dup            
        //    81: invokespecial   java/lang/StringBuilder.<init>:()V
        //    84: getstatic       ClassSub/Class286.instance:LClassSub/Class286;
        //    87: getfield        ClassSub/Class286.circleImage:Ljava/io/File;
        //    90: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //    93: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    96: getstatic       java/io/File.separator:Ljava/lang/String;
        //    99: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   102: aload_1        
        //   103: invokevirtual   ClassSub/Class296.getId:()J
        //   106: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //   109: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   112: invokevirtual   ClassSub/Class286.makeCirclePicture:(LClassSub/Class296;ILjava/lang/String;)V
        //   115: new             Lnet/minecraft/util/ResourceLocation;
        //   118: dup            
        //   119: new             Ljava/lang/StringBuilder;
        //   122: dup            
        //   123: invokespecial   java/lang/StringBuilder.<init>:()V
        //   126: ldc             "circle/"
        //   128: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   131: aload_1        
        //   132: invokevirtual   ClassSub/Class296.getId:()J
        //   135: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //   138: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   141: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   144: astore_2       
        //   145: new             LClassSub/Class105;
        //   148: dup            
        //   149: aload_0        
        //   150: aload_1        
        //   151: aload_2        
        //   152: invokespecial   ClassSub/Class105.<init>:(LClassSub/Class286;LClassSub/Class296;Lnet/minecraft/util/ResourceLocation;)V
        //   155: astore_3       
        //   156: new             Lnet/minecraft/client/renderer/ThreadDownloadImageData;
        //   159: dup            
        //   160: new             Ljava/io/File;
        //   163: dup            
        //   164: new             Ljava/lang/StringBuilder;
        //   167: dup            
        //   168: invokespecial   java/lang/StringBuilder.<init>:()V
        //   171: getstatic       ClassSub/Class286.instance:LClassSub/Class286;
        //   174: getfield        ClassSub/Class286.circleImage:Ljava/io/File;
        //   177: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //   180: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   183: getstatic       java/io/File.separator:Ljava/lang/String;
        //   186: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   189: aload_1        
        //   190: invokevirtual   ClassSub/Class296.getId:()J
        //   193: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //   196: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   199: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   202: aconst_null    
        //   203: aconst_null    
        //   204: checkcast       Lnet/minecraft/util/ResourceLocation;
        //   207: aload_3        
        //   208: invokespecial   net/minecraft/client/renderer/ThreadDownloadImageData.<init>:(Ljava/io/File;Ljava/lang/String;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/IImageBuffer;)V
        //   211: astore          4
        //   213: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   216: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //   219: aload_2        
        //   220: aload           4
        //   222: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.loadTexture:(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/texture/ITextureObject;)Z
        //   225: pop            
        //   226: goto            236
        //   229: nop            
        //   230: athrow         
        //   231: astore_2       
        //   232: aload_2        
        //   233: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   236: return         
        //   237: nop            
        //   238: nop            
        //   239: nop            
        //   240: nop            
        //   241: athrow         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  27     226    231    236    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void makeCirclePicture(final Class296 class296, final int n, final String s) {
        try {
            final BufferedImage read = ImageIO.read(new URL(class296.getPicUrl()));
            final BufferedImage bufferedImage = new BufferedImage(n, n, 6);
            final Graphics2D graphics = bufferedImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            final int n2 = 0;
            graphics.setClip(new Ellipse2D.Double(n2, n2, n - n2 * 2, n - n2 * 2));
            graphics.drawImage(read, n2, n2, n - n2 * 2, n - n2 * 2, null);
            graphics.dispose();
            try (final FileOutputStream fileOutputStream = new FileOutputStream(s)) {
                ImageIO.write(bufferedImage, "png", fileOutputStream);
            }
            catch (Exception ex2) {}
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void next() {
        if (!this.allTracks.isEmpty()) {
            if (this.currentTrack == null) {
                this.play(this.allTracks.get(0));
                return;
            }
            int n = 0;
            for (final Class296 class296 : this.allTracks) {
                if (n != 0) {
                    this.play(class296);
                    return;
                }
                if (class296.getId() != this.currentTrack.getId()) {
                    continue;
                }
                n = 1;
            }
        }
    }
    
    public void play(final Class296 currentTrack) {
        class Class70 implements Runnable
        {
            final Class296 val$track;
            final Class286 this$0;
            
            
            Class70(final Class286 this$0, final Class296 val$track) {
                this.this$0 = this$0;
                this.val$track = val$track;
            }
            
            @Override
            public void run() {
                Class164.INSTANCE.downLoadSong(this.this$0.musicFolder.getAbsolutePath(), String.valueOf(this.val$track.getId()), Class164.INSTANCE.getDownloadUrl(String.valueOf(this.val$track.getId())));
                final File file = new File(this.this$0.musicFolder, this.val$track.getId() + ".mp3");
                this.this$0.play(this.val$track);
                this.this$0.loadingThread = null;
            }
        }
        class Class252 implements Runnable
        {
            final Class286 this$0;
            
            
            Class252(final Class286 this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            public void run() {
                int n = 1;
                int n2 = 0;
                final Class296 currentTrack = Class286.getInstance().getCurrentTrack();
                while (true) {
                    final long n3 = (long)Class286.getInstance().getMediaPlayer().getCurrentTime().toMillis();
                    try {
                        if (n3 < Class286.getInstance().lyric.get(n).getTime()) {
                            if (n2 == 0) {
                                Class344.INSTANCE.ly = Class286.getInstance().lyric.get(n - 1).getText();
                                if (Class286.instance.tLyric.isEmpty()) {
                                    try {
                                        Class344.INSTANCE.tly = Class286.getInstance().lyric.get(n).getText();
                                    }
                                    catch (Exception ex) {
                                        Class344.INSTANCE.tly = "";
                                    }
                                }
                            }
                            n2 = 1;
                        }
                        else {
                            ++n;
                            n2 = 0;
                        }
                    }
                    catch (Exception ex2) {
                        Class344.INSTANCE.ly = "";
                    }
                    if (n - 1 > Class286.getInstance().lyric.size() || Class286.getInstance().getCurrentTrack() != currentTrack || !this.this$0.playLyric) {
                        break;
                    }
                    try {
                        Thread.sleep(50L);
                    }
                    catch (Exception ex3) {}
                }
            }
        }
        class Class133 implements Runnable
        {
            final Class286 this$0;
            
            
            Class133(final Class286 this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            public void run() {
                int n = 1;
                int n2 = 0;
                final Class296 currentTrack = Class286.getInstance().getCurrentTrack();
                while (true) {
                    final long n3 = (long)Class286.getInstance().getMediaPlayer().getCurrentTime().toMillis();
                    try {
                        if (n3 <= Class286.getInstance().tLyric.get(n).getTime()) {
                            if (n2 == 0) {
                                Class344.INSTANCE.tly = Class286.getInstance().tLyric.get(n - 1).getText();
                            }
                            n2 = 1;
                        }
                        else {
                            ++n;
                            n2 = 0;
                        }
                    }
                    catch (Exception ex) {
                        Class344.INSTANCE.tly = "";
                    }
                    if (n - 1 > Class286.getInstance().tLyric.size() || Class286.getInstance().getCurrentTrack() != currentTrack || !this.this$0.playTranslateLyric) {
                        break;
                    }
                    try {
                        Thread.sleep(50L);
                    }
                    catch (Exception ex2) {}
                }
            }
        }
        class Class241 implements Runnable
        {
            final Class296 val$track;
            final Class286 this$0;
            
            
            Class241(final Class286 this$0, final Class296 val$track) {
                this.this$0 = this$0;
                this.val$track = val$track;
            }
            
            @Override
            public void run() {
                if (Class286.instance.isLoop) {
                    this.this$0.play(this.val$track);
                }
                else {
                    this.this$0.next();
                }
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: ldc             0
        //     7: putfield        ClassSub/Class286.playLyric:Z
        //    10: aload_0        
        //    11: ldc             0
        //    13: putfield        ClassSub/Class286.playTranslateLyric:Z
        //    16: aload_0        
        //    17: aload_1        
        //    18: putfield        ClassSub/Class286.currentTrack:LClassSub/Class296;
        //    21: aload_0        
        //    22: pop            
        //    23: getstatic       ClassSub/Class286.showMsg:Z
        //    26: ifne            36
        //    29: aload_0        
        //    30: pop            
        //    31: ldc             1
        //    33: putstatic       ClassSub/Class286.showMsg:Z
        //    36: aload_0        
        //    37: getfield        ClassSub/Class286.mediaPlayer:Ljavafx/scene/media/MediaPlayer;
        //    40: ifnull          50
        //    43: aload_0        
        //    44: getfield        ClassSub/Class286.mediaPlayer:Ljavafx/scene/media/MediaPlayer;
        //    47: invokevirtual   javafx/scene/media/MediaPlayer.stop:()V
        //    50: getstatic       ClassSub/Class344.INSTANCE:LClassSub/Class344;
        //    53: ldc_w           ""
        //    56: putfield        ClassSub/Class344.ly:Ljava/lang/String;
        //    59: getstatic       ClassSub/Class344.INSTANCE:LClassSub/Class344;
        //    62: ldc_w           ""
        //    65: putfield        ClassSub/Class344.tly:Ljava/lang/String;
        //    68: new             Ljava/io/File;
        //    71: dup            
        //    72: aload_0        
        //    73: getfield        ClassSub/Class286.musicFolder:Ljava/io/File;
        //    76: new             Ljava/lang/StringBuilder;
        //    79: dup            
        //    80: invokespecial   java/lang/StringBuilder.<init>:()V
        //    83: aload_1        
        //    84: invokevirtual   ClassSub/Class296.getId:()J
        //    87: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //    90: ldc_w           ".mp3"
        //    93: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    96: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    99: invokespecial   java/io/File.<init>:(Ljava/io/File;Ljava/lang/String;)V
        //   102: astore_2       
        //   103: getstatic       ClassSub/Class164.INSTANCE:LClassSub/Class164;
        //   106: aload_0        
        //   107: getfield        ClassSub/Class286.musicFolder:Ljava/io/File;
        //   110: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //   113: aload_1        
        //   114: invokevirtual   ClassSub/Class296.getId:()J
        //   117: invokestatic    java/lang/String.valueOf:(J)Ljava/lang/String;
        //   120: invokevirtual   ClassSub/Class164.isFileExist:(Ljava/lang/String;Ljava/lang/String;)Z
        //   123: ifne            177
        //   126: aload_0        
        //   127: getfield        ClassSub/Class286.loadingThread:Ljava/lang/Thread;
        //   130: ifnull          140
        //   133: aload_0        
        //   134: getfield        ClassSub/Class286.loadingThread:Ljava/lang/Thread;
        //   137: invokevirtual   java/lang/Thread.interrupt:()V
        //   140: aload_0        
        //   141: new             Ljava/lang/Thread;
        //   144: dup            
        //   145: new             LClassSub/Class70;
        //   148: dup            
        //   149: aload_0        
        //   150: aload_1        
        //   151: invokespecial   ClassSub/Class70.<init>:(LClassSub/Class286;LClassSub/Class296;)V
        //   154: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //   157: putfield        ClassSub/Class286.loadingThread:Ljava/lang/Thread;
        //   160: aload_0        
        //   161: getfield        ClassSub/Class286.loadingThread:Ljava/lang/Thread;
        //   164: invokevirtual   java/lang/Thread.start:()V
        //   167: goto            673
        //   170: nop            
        //   171: nop            
        //   172: nop            
        //   173: nop            
        //   174: nop            
        //   175: nop            
        //   176: athrow         
        //   177: getstatic       ClassSub/Class164.INSTANCE:LClassSub/Class164;
        //   180: aload_1        
        //   181: invokevirtual   ClassSub/Class296.getId:()J
        //   184: invokestatic    java/lang/String.valueOf:(J)Ljava/lang/String;
        //   187: invokevirtual   ClassSub/Class164.requestLyric:(Ljava/lang/String;)[Ljava/lang/String;
        //   190: checkcast       [Ljava/lang/String;
        //   193: astore_3       
        //   194: aload_0        
        //   195: getfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //   198: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   201: ifne            211
        //   204: aload_0        
        //   205: getfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //   208: invokevirtual   java/util/ArrayList.clear:()V
        //   211: aload_0        
        //   212: getfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //   215: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   218: ifne            228
        //   221: aload_0        
        //   222: getfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //   225: invokevirtual   java/util/ArrayList.clear:()V
        //   228: aload_3        
        //   229: ldc             0
        //   231: aaload         
        //   232: ldc_w           ""
        //   235: if_acmpeq       327
        //   238: aload_3        
        //   239: ldc             0
        //   241: aaload         
        //   242: ldc_w           "NO LYRIC"
        //   245: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   248: ifeq            303
        //   251: getstatic       ClassSub/Class344.INSTANCE:LClassSub/Class344;
        //   254: new             Ljava/lang/StringBuilder;
        //   257: dup            
        //   258: invokespecial   java/lang/StringBuilder.<init>:()V
        //   261: aload_0        
        //   262: getfield        ClassSub/Class286.currentTrack:LClassSub/Class296;
        //   265: invokevirtual   ClassSub/Class296.getName:()Ljava/lang/String;
        //   268: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   271: ldc_w           " - "
        //   274: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   277: aload_0        
        //   278: getfield        ClassSub/Class286.currentTrack:LClassSub/Class296;
        //   281: invokevirtual   ClassSub/Class296.getArtists:()Ljava/lang/String;
        //   284: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   287: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   290: putfield        ClassSub/Class344.ly:Ljava/lang/String;
        //   293: goto            353
        //   296: nop            
        //   297: athrow         
        //   298: nop            
        //   299: nop            
        //   300: nop            
        //   301: nop            
        //   302: athrow         
        //   303: aload_0        
        //   304: getstatic       ClassSub/Class164.INSTANCE:LClassSub/Class164;
        //   307: aload_3        
        //   308: ldc             0
        //   310: aaload         
        //   311: invokevirtual   ClassSub/Class164.analyzeLyric:(Ljava/lang/String;)Ljava/util/ArrayList;
        //   314: putfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //   317: goto            353
        //   320: nop            
        //   321: athrow         
        //   322: nop            
        //   323: nop            
        //   324: nop            
        //   325: nop            
        //   326: athrow         
        //   327: getstatic       ClassSub/Class344.INSTANCE:LClassSub/Class344;
        //   330: ldc_w           "(\u53d1\u751f\u9519\u8bef\u6216\u6b4c\u66f2\u4e0d\u5305\u542b\u6b4c\u8bcd)"
        //   333: putfield        ClassSub/Class344.ly:Ljava/lang/String;
        //   336: aload_0        
        //   337: getfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //   340: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   343: ifne            353
        //   346: aload_0        
        //   347: getfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //   350: invokevirtual   java/util/ArrayList.clear:()V
        //   353: aload_3        
        //   354: ldc             1
        //   356: aaload         
        //   357: ldc_w           ""
        //   360: if_acmpeq       419
        //   363: aload_3        
        //   364: ldc             1
        //   366: aaload         
        //   367: ldc_w           "NO LYRIC"
        //   370: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   373: ifeq            395
        //   376: getstatic       ClassSub/Class344.INSTANCE:LClassSub/Class344;
        //   379: ldc_w           "(\u7eaf\u97f3\u4e50,\u8bf7\u6b23\u8d4f)"
        //   382: putfield        ClassSub/Class344.tly:Ljava/lang/String;
        //   385: goto            445
        //   388: nop            
        //   389: athrow         
        //   390: nop            
        //   391: nop            
        //   392: nop            
        //   393: nop            
        //   394: athrow         
        //   395: aload_0        
        //   396: getstatic       ClassSub/Class164.INSTANCE:LClassSub/Class164;
        //   399: aload_3        
        //   400: ldc             1
        //   402: aaload         
        //   403: invokevirtual   ClassSub/Class164.analyzeLyric:(Ljava/lang/String;)Ljava/util/ArrayList;
        //   406: putfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //   409: goto            445
        //   412: nop            
        //   413: athrow         
        //   414: nop            
        //   415: nop            
        //   416: nop            
        //   417: nop            
        //   418: athrow         
        //   419: getstatic       ClassSub/Class344.INSTANCE:LClassSub/Class344;
        //   422: ldc_w           "(\u83b7\u53d6\u65f6\u51fa\u73b0\u9519\u8bef\u6216\u8bd1\u6587\u4e0d\u5b58\u5728)"
        //   425: putfield        ClassSub/Class344.tly:Ljava/lang/String;
        //   428: aload_0        
        //   429: getfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //   432: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   435: ifne            445
        //   438: aload_0        
        //   439: getfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //   442: invokevirtual   java/util/ArrayList.clear:()V
        //   445: goto            536
        //   448: nop            
        //   449: athrow         
        //   450: astore_3       
        //   451: aload_0        
        //   452: getfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //   455: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   458: ifne            468
        //   461: aload_0        
        //   462: getfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //   465: invokevirtual   java/util/ArrayList.clear:()V
        //   468: aload_0        
        //   469: getfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //   472: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   475: ifne            485
        //   478: aload_0        
        //   479: getfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //   482: invokevirtual   java/util/ArrayList.clear:()V
        //   485: getstatic       ClassSub/Class344.INSTANCE:LClassSub/Class344;
        //   488: new             Ljava/lang/StringBuilder;
        //   491: dup            
        //   492: invokespecial   java/lang/StringBuilder.<init>:()V
        //   495: aload_0        
        //   496: getfield        ClassSub/Class286.currentTrack:LClassSub/Class296;
        //   499: invokevirtual   ClassSub/Class296.getName:()Ljava/lang/String;
        //   502: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   505: ldc_w           " - "
        //   508: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   511: aload_0        
        //   512: getfield        ClassSub/Class286.currentTrack:LClassSub/Class296;
        //   515: invokevirtual   ClassSub/Class296.getArtists:()Ljava/lang/String;
        //   518: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   521: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   524: putfield        ClassSub/Class344.ly:Ljava/lang/String;
        //   527: getstatic       ClassSub/Class344.INSTANCE:LClassSub/Class344;
        //   530: ldc_w           "(\u83b7\u53d6\u6b4c\u8bcd\u65f6\u51fa\u73b0\u9519\u8bef)"
        //   533: putfield        ClassSub/Class344.tly:Ljava/lang/String;
        //   536: new             Ljavafx/scene/media/Media;
        //   539: dup            
        //   540: aload_2        
        //   541: invokevirtual   java/io/File.toURI:()Ljava/net/URI;
        //   544: invokevirtual   java/net/URI.toString:()Ljava/lang/String;
        //   547: invokespecial   javafx/scene/media/Media.<init>:(Ljava/lang/String;)V
        //   550: astore_3       
        //   551: aload_0        
        //   552: new             Ljavafx/scene/media/MediaPlayer;
        //   555: dup            
        //   556: aload_3        
        //   557: invokespecial   javafx/scene/media/MediaPlayer.<init>:(Ljavafx/scene/media/Media;)V
        //   560: putfield        ClassSub/Class286.mediaPlayer:Ljavafx/scene/media/MediaPlayer;
        //   563: aload_0        
        //   564: getfield        ClassSub/Class286.mediaPlayer:Ljavafx/scene/media/MediaPlayer;
        //   567: aload_0        
        //   568: getfield        ClassSub/Class286.volume:D
        //   571: invokevirtual   javafx/scene/media/MediaPlayer.setVolume:(D)V
        //   574: aload_0        
        //   575: getfield        ClassSub/Class286.mediaPlayer:Ljavafx/scene/media/MediaPlayer;
        //   578: ldc             1
        //   580: invokevirtual   javafx/scene/media/MediaPlayer.setAutoPlay:(Z)V
        //   583: aload_0        
        //   584: ldc             1
        //   586: putfield        ClassSub/Class286.playLyric:Z
        //   589: aload_0        
        //   590: ldc             1
        //   592: putfield        ClassSub/Class286.playTranslateLyric:Z
        //   595: aload_0        
        //   596: getfield        ClassSub/Class286.lyric:Ljava/util/ArrayList;
        //   599: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   602: ifne            626
        //   605: new             Ljava/lang/Thread;
        //   608: dup            
        //   609: new             LClassSub/Class252;
        //   612: dup            
        //   613: aload_0        
        //   614: invokespecial   ClassSub/Class252.<init>:(LClassSub/Class286;)V
        //   617: ldc_w           "#Original Lyric Thread#"
        //   620: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;Ljava/lang/String;)V
        //   623: invokevirtual   java/lang/Thread.start:()V
        //   626: aload_0        
        //   627: getfield        ClassSub/Class286.tLyric:Ljava/util/ArrayList;
        //   630: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   633: ifne            657
        //   636: new             Ljava/lang/Thread;
        //   639: dup            
        //   640: new             LClassSub/Class133;
        //   643: dup            
        //   644: aload_0        
        //   645: invokespecial   ClassSub/Class133.<init>:(LClassSub/Class286;)V
        //   648: ldc_w           "#Translate Lyric Thread#"
        //   651: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;Ljava/lang/String;)V
        //   654: invokevirtual   java/lang/Thread.start:()V
        //   657: aload_0        
        //   658: getfield        ClassSub/Class286.mediaPlayer:Ljavafx/scene/media/MediaPlayer;
        //   661: new             LClassSub/Class241;
        //   664: dup            
        //   665: aload_0        
        //   666: aload_1        
        //   667: invokespecial   ClassSub/Class241.<init>:(LClassSub/Class286;LClassSub/Class296;)V
        //   670: invokevirtual   javafx/scene/media/MediaPlayer.setOnEndOfMedia:(Ljava/lang/Runnable;)V
        //   673: return         
        //   674: nop            
        //   675: nop            
        //   676: nop            
        //   677: nop            
        //   678: athrow         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  177    296    450    536    Ljava/lang/Exception;
        //  303    320    450    536    Ljava/lang/Exception;
        //  327    388    450    536    Ljava/lang/Exception;
        //  395    412    450    536    Ljava/lang/Exception;
        //  419    445    450    536    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Class296 getCurrentTrack() {
        return this.currentTrack;
    }
    
    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }
    
    public Thread getLoadingThread() {
        return this.loadingThread;
    }
    
    public void setVolume(final double volume) {
        this.volume = volume;
    }
    
    public ArrayList<Class296> getPlaylist() {
        return this.allTracks;
    }
    
    public static Class286 getInstance() {
        if (Class286.instance == null) {
            Class286.instance = new Class286();
        }
        return Class286.instance;
    }
    
    static {
        Class286.lyricCoding = true;
        Class286.showMsg = false;
    }
}
