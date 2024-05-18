package net.minecraft.client.resources;

import java.io.*;
import com.mojang.authlib.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.common.cache.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import java.awt.image.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

public class SkinManager
{
    private final TextureManager textureManager;
    private final MinecraftSessionService sessionService;
    private static final String[] I;
    private final File skinCacheDir;
    private static final ExecutorService THREAD_POOL;
    private final LoadingCache<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> skinCacheLoader;
    
    public void loadProfileTextures(final GameProfile gameProfile, final SkinAvailableCallback skinAvailableCallback, final boolean b) {
        SkinManager.THREAD_POOL.submit(new Runnable(this, gameProfile, b, skinAvailableCallback) {
            private final GameProfile val$profile;
            private final SkinAvailableCallback val$skinAvailableCallback;
            private final boolean val$requireSecure;
            final SkinManager this$0;
            
            static SkinManager access$0(final SkinManager$3 runnable) {
                return runnable.this$0;
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void run() {
                final HashMap hashMap = Maps.newHashMap();
                try {
                    hashMap.putAll(SkinManager.access$0(this.this$0).getTextures(this.val$profile, this.val$requireSecure));
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                }
                catch (InsecureTextureException ex) {}
                if (hashMap.isEmpty() && this.val$profile.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
                    this.val$profile.getProperties().clear();
                    this.val$profile.getProperties().putAll((Multimap)Minecraft.getMinecraft().func_181037_M());
                    hashMap.putAll(SkinManager.access$0(this.this$0).getTextures(this.val$profile, (boolean)("".length() != 0)));
                }
                Minecraft.getMinecraft().addScheduledTask(new Runnable(this, hashMap, this.val$skinAvailableCallback) {
                    final SkinManager$3 this$1;
                    private final SkinAvailableCallback val$skinAvailableCallback;
                    private final Map val$map;
                    
                    @Override
                    public void run() {
                        if (this.val$map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            SkinManager$3.access$0(this.this$1).loadSkin(this.val$map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, this.val$skinAvailableCallback);
                        }
                        if (this.val$map.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                            SkinManager$3.access$0(this.this$1).loadSkin(this.val$map.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, this.val$skinAvailableCallback);
                        }
                    }
                    
                    private static String I(final String s, final String s2) {
                        final StringBuilder sb = new StringBuilder();
                        final char[] charArray = s2.toCharArray();
                        int length = "".length();
                        final char[] charArray2 = s.toCharArray();
                        final int length2 = charArray2.length;
                        int i = "".length();
                        while (i < length2) {
                            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                            ++length;
                            ++i;
                            "".length();
                            if (4 == -1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                });
            }
        });
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001b/\u0007\u001d\u0018G", "hDnsk");
        SkinManager.I[" ".length()] = I("0\u0016", "HngxD");
    }
    
    static MinecraftSessionService access$0(final SkinManager skinManager) {
        return skinManager.sessionService;
    }
    
    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache(final GameProfile gameProfile) {
        return (Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>)this.skinCacheLoader.getUnchecked((Object)gameProfile);
    }
    
    public SkinManager(final TextureManager textureManager, final File skinCacheDir, final MinecraftSessionService sessionService) {
        this.textureManager = textureManager;
        this.skinCacheDir = skinCacheDir;
        this.sessionService = sessionService;
        this.skinCacheLoader = (LoadingCache<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>>)CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build((CacheLoader)new CacheLoader<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>>(this) {
            final SkinManager this$0;
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> load(final GameProfile gameProfile) throws Exception {
                return (Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>)Minecraft.getMinecraft().getSessionService().getTextures(gameProfile, (boolean)("".length() != 0));
            }
            
            public Object load(final Object o) throws Exception {
                return this.load((GameProfile)o);
            }
        });
    }
    
    static {
        I();
        THREAD_POOL = new ThreadPoolExecutor("".length(), "  ".length(), 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    }
    
    public ResourceLocation loadSkin(final MinecraftProfileTexture minecraftProfileTexture, final MinecraftProfileTexture.Type type) {
        return this.loadSkin(minecraftProfileTexture, type, null);
    }
    
    public ResourceLocation loadSkin(final MinecraftProfileTexture minecraftProfileTexture, final MinecraftProfileTexture.Type type, final SkinAvailableCallback skinAvailableCallback) {
        final ResourceLocation resourceLocation = new ResourceLocation(SkinManager.I["".length()] + minecraftProfileTexture.getHash());
        if (this.textureManager.getTexture(resourceLocation) != null) {
            if (skinAvailableCallback != null) {
                skinAvailableCallback.skinAvailable(type, resourceLocation, minecraftProfileTexture);
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
        }
        else {
            final File skinCacheDir = this.skinCacheDir;
            String substring;
            if (minecraftProfileTexture.getHash().length() > "  ".length()) {
                substring = minecraftProfileTexture.getHash().substring("".length(), "  ".length());
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                substring = SkinManager.I[" ".length()];
            }
            final File file = new File(new File(skinCacheDir, substring), minecraftProfileTexture.getHash());
            ImageBufferDownload imageBufferDownload;
            if (type == MinecraftProfileTexture.Type.SKIN) {
                imageBufferDownload = new ImageBufferDownload();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                imageBufferDownload = null;
            }
            this.textureManager.loadTexture(resourceLocation, new ThreadDownloadImageData(file, minecraftProfileTexture.getUrl(), DefaultPlayerSkin.getDefaultSkinLegacy(), new IImageBuffer(this, imageBufferDownload, skinAvailableCallback, type, resourceLocation, minecraftProfileTexture) {
                private final IImageBuffer val$iimagebuffer;
                private final MinecraftProfileTexture val$profileTexture;
                private final MinecraftProfileTexture.Type val$p_152789_2_;
                final SkinManager this$0;
                private final ResourceLocation val$resourcelocation;
                private final SkinAvailableCallback val$skinAvailableCallback;
                
                @Override
                public void skinAvailable() {
                    if (this.val$iimagebuffer != null) {
                        this.val$iimagebuffer.skinAvailable();
                    }
                    if (this.val$skinAvailableCallback != null) {
                        this.val$skinAvailableCallback.skinAvailable(this.val$p_152789_2_, this.val$resourcelocation, this.val$profileTexture);
                    }
                }
                
                @Override
                public BufferedImage parseUserSkin(BufferedImage userSkin) {
                    if (this.val$iimagebuffer != null) {
                        userSkin = this.val$iimagebuffer.parseUserSkin(userSkin);
                    }
                    return userSkin;
                }
                
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (-1 >= 4) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            }));
        }
        return resourceLocation;
    }
    
    public interface SkinAvailableCallback
    {
        void skinAvailable(final MinecraftProfileTexture.Type p0, final ResourceLocation p1, final MinecraftProfileTexture p2);
    }
}
