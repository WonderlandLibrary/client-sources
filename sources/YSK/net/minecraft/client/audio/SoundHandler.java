package net.minecraft.client.audio;

import java.lang.reflect.*;
import org.apache.commons.io.*;
import net.minecraft.client.resources.*;
import java.io.*;
import org.apache.logging.log4j.*;
import com.google.gson.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.*;
import java.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;

public class SoundHandler implements ITickable, IResourceManagerReloadListener
{
    private static int[] $SWITCH_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type;
    private static final ParameterizedType TYPE;
    private static final String[] I;
    private static final Gson GSON;
    public static final SoundPoolEntry missing_sound;
    private final IResourceManager mcResourceManager;
    private final SoundManager sndManager;
    private static final Logger logger;
    private final SoundRegistry sndRegistry;
    
    protected Map<String, SoundList> getSoundMap(final InputStream inputStream) {
        Map map;
        try {
            map = (Map)SoundHandler.GSON.fromJson((Reader)new InputStreamReader(inputStream), (Type)SoundHandler.TYPE);
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        IOUtils.closeQuietly(inputStream);
        return (Map<String, SoundList>)map;
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static SoundRegistry access$0(final SoundHandler soundHandler) {
        return soundHandler.sndRegistry;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type() {
        final int[] $switch_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type = SoundHandler.$SWITCH_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type;
        if ($switch_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type != null) {
            return $switch_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type;
        }
        final int[] $switch_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type2 = new int[SoundList.SoundEntry.Type.values().length];
        try {
            $switch_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type2[SoundList.SoundEntry.Type.FILE.ordinal()] = " ".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type2[SoundList.SoundEntry.Type.SOUND_EVENT.ordinal()] = "  ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        return SoundHandler.$SWITCH_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type = $switch_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type2;
    }
    
    public void unloadSounds() {
        this.sndManager.unloadSoundSystem();
    }
    
    public void stopSounds() {
        this.sndManager.stopAllSounds();
    }
    
    @Override
    public void update() {
        this.sndManager.updateAllSounds();
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.sndManager.reloadSoundSystem();
        this.sndRegistry.clearMap();
        final Iterator<String> iterator = resourceManager.getResourceDomains().iterator();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            try {
                final Iterator<IResource> iterator2 = resourceManager.getAllResources(new ResourceLocation(s, SoundHandler.I[" ".length()])).iterator();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final IResource resource = iterator2.next();
                    try {
                        final Iterator<Map.Entry<String, SoundList>> iterator3 = this.getSoundMap(resource.getInputStream()).entrySet().iterator();
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                        while (iterator3.hasNext()) {
                            final Map.Entry<String, SoundList> entry = iterator3.next();
                            this.loadSoundResource(new ResourceLocation(s, entry.getKey()), entry.getValue());
                        }
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                        continue;
                    }
                    catch (RuntimeException ex) {
                        SoundHandler.logger.warn(SoundHandler.I["  ".length()], (Throwable)ex);
                    }
                }
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                continue;
            }
            catch (IOException ex2) {}
        }
    }
    
    private void loadSoundResource(final ResourceLocation resourceLocation, final SoundList list) {
        int n;
        if (((RegistrySimple<ResourceLocation, V>)this.sndRegistry).containsKey(resourceLocation)) {
            n = "".length();
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        SoundEventAccessorComposite soundEventAccessorComposite;
        if (n2 == 0 && !list.canReplaceExisting()) {
            soundEventAccessorComposite = this.sndRegistry.getObject(resourceLocation);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            if (n2 == 0) {
                final Logger logger = SoundHandler.logger;
                final String s = SoundHandler.I["   ".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = resourceLocation;
                logger.debug(s, array);
            }
            soundEventAccessorComposite = new SoundEventAccessorComposite(resourceLocation, 1.0, 1.0, list.getSoundCategory());
            this.sndRegistry.registerSound(soundEventAccessorComposite);
        }
        final Iterator<SoundList.SoundEntry> iterator = list.getSoundList().iterator();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final SoundList.SoundEntry soundEntry = iterator.next();
            final String soundEntryName = soundEntry.getSoundEntryName();
            final ResourceLocation resourceLocation2 = new ResourceLocation(soundEntryName);
            String s2;
            if (soundEntryName.contains(SoundHandler.I[0xA ^ 0xE])) {
                s2 = resourceLocation2.getResourceDomain();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                s2 = resourceLocation.getResourceDomain();
            }
            final String s3 = s2;
            ISoundEventAccessor<SoundPoolEntry> soundEventAccessor = null;
            switch ($SWITCH_TABLE$net$minecraft$client$audio$SoundList$SoundEntry$Type()[soundEntry.getSoundEntryType().ordinal()]) {
                case 1: {
                    final ResourceLocation resourceLocation3 = new ResourceLocation(s3, SoundHandler.I[0x99 ^ 0x9C] + resourceLocation2.getResourcePath() + SoundHandler.I[0x2D ^ 0x2B]);
                    InputStream inputStream = null;
                    try {
                        inputStream = this.mcResourceManager.getResource(resourceLocation3).getInputStream();
                        "".length();
                        if (4 <= 2) {
                            throw null;
                        }
                    }
                    catch (FileNotFoundException ex2) {
                        final Logger logger2 = SoundHandler.logger;
                        final String s4 = SoundHandler.I[0x3B ^ 0x3C];
                        final Object[] array2 = new Object["  ".length()];
                        array2["".length()] = resourceLocation3;
                        array2[" ".length()] = resourceLocation;
                        logger2.warn(s4, array2);
                        IOUtils.closeQuietly(inputStream);
                        "".length();
                        if (-1 >= 0) {
                            throw null;
                        }
                        continue;
                    }
                    catch (IOException ex) {
                        SoundHandler.logger.warn(SoundHandler.I[0x70 ^ 0x78] + resourceLocation3 + SoundHandler.I[0xCA ^ 0xC3] + resourceLocation, (Throwable)ex);
                        IOUtils.closeQuietly(inputStream);
                        "".length();
                        if (-1 >= 0) {
                            throw null;
                        }
                        continue;
                    }
                    finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                    IOUtils.closeQuietly(inputStream);
                    soundEventAccessor = new SoundEventAccessor(new SoundPoolEntry(resourceLocation3, soundEntry.getSoundEntryPitch(), soundEntry.getSoundEntryVolume(), soundEntry.isStreaming()), soundEntry.getSoundEntryWeight());
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    soundEventAccessor = new ISoundEventAccessor<SoundPoolEntry>(this, s3, soundEntry) {
                        final ResourceLocation field_148726_a = new ResourceLocation(s, soundEntry.getSoundEntryName());
                        final SoundHandler this$0;
                        
                        @Override
                        public int getWeight() {
                            final SoundEventAccessorComposite soundEventAccessorComposite = SoundHandler.access$0(this.this$0).getObject(this.field_148726_a);
                            int n;
                            if (soundEventAccessorComposite == null) {
                                n = "".length();
                                "".length();
                                if (1 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                n = soundEventAccessorComposite.getWeight();
                            }
                            return n;
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
                                if (1 == 2) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        @Override
                        public SoundPoolEntry cloneEntry() {
                            final SoundEventAccessorComposite soundEventAccessorComposite = SoundHandler.access$0(this.this$0).getObject(this.field_148726_a);
                            SoundPoolEntry soundPoolEntry;
                            if (soundEventAccessorComposite == null) {
                                soundPoolEntry = SoundHandler.missing_sound;
                                "".length();
                                if (4 != 4) {
                                    throw null;
                                }
                            }
                            else {
                                soundPoolEntry = soundEventAccessorComposite.cloneEntry();
                            }
                            return soundPoolEntry;
                        }
                        
                        @Override
                        public Object cloneEntry() {
                            return this.cloneEntry();
                        }
                    };
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                    break;
                }
                default: {
                    throw new IllegalStateException(SoundHandler.I[0x25 ^ 0x2F]);
                }
            }
            soundEventAccessorComposite.addSoundToEventPool(soundEventAccessor);
        }
    }
    
    public SoundEventAccessorComposite getSound(final ResourceLocation resourceLocation) {
        return this.sndRegistry.getObject(resourceLocation);
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        GSON = new GsonBuilder().registerTypeAdapter((Type)SoundList.class, (Object)new SoundListSerializer()).create();
        TYPE = new ParameterizedType() {
            @Override
            public Type getRawType() {
                return Map.class;
            }
            
            @Override
            public Type[] getActualTypeArguments() {
                final Type[] array = new Type["  ".length()];
                array["".length()] = String.class;
                array[" ".length()] = SoundList.class;
                return array;
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
                    if (1 >= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        missing_sound = new SoundPoolEntry(new ResourceLocation(SoundHandler.I["".length()]), 0.0, 0.0, "".length() != 0);
    }
    
    public void playDelayedSound(final ISound sound, final int n) {
        this.sndManager.playDelayedSound(sound, n);
    }
    
    public void setListener(final EntityPlayer entityPlayer, final float n) {
        this.sndManager.setListener(entityPlayer, n);
    }
    
    public SoundEventAccessorComposite getRandomSoundFromCategories(final SoundCategory... array) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ResourceLocation> iterator = ((RegistrySimple<ResourceLocation, V>)this.sndRegistry).getKeys().iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final SoundEventAccessorComposite soundEventAccessorComposite = this.sndRegistry.getObject(iterator.next());
            if (ArrayUtils.contains((Object[])array, (Object)soundEventAccessorComposite.getSoundCategory())) {
                arrayList.add(soundEventAccessorComposite);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (SoundEventAccessorComposite)arrayList.get(new Random().nextInt(arrayList.size()));
    }
    
    public SoundHandler(final IResourceManager mcResourceManager, final GameSettings gameSettings) {
        this.sndRegistry = new SoundRegistry();
        this.mcResourceManager = mcResourceManager;
        this.sndManager = new SoundManager(this, gameSettings);
    }
    
    public void stopSound(final ISound sound) {
        this.sndManager.stopSound(sound);
    }
    
    public void setSoundLevel(final SoundCategory soundCategory, final float n) {
        if (soundCategory == SoundCategory.MASTER && n <= 0.0f) {
            this.stopSounds();
        }
        this.sndManager.setSoundCategoryVolume(soundCategory, n);
    }
    
    private static void I() {
        (I = new String[0x68 ^ 0x63])["".length()] = I("< &\u000fO<,!\u001d\u001c?\"\r\u001d\u001a$+6", "QERnu");
        SoundHandler.I[" ".length()] = I("#\u001c\u0000\u0002\u001e#]\u001f\u001f\u0015>", "Psulz");
        SoundHandler.I["  ".length()] = I("\u0002)\u0007\u0016\u000b\"#Q\u0004\b>)\u0015\u0004I!4\u001e\u0019", "KGqwg");
        SoundHandler.I["   ".length()] = I("#7(\u00007\u00127<L%\u001e'6\bv\u0014$=\u0002\"Q>7\u000f7\u0005;7\u0002v\n/", "qRXlV");
        SoundHandler.I[0x25 ^ 0x21] = I("X", "bTdAH");
        SoundHandler.I[0x10 ^ 0x15] = I("\u0010\"9\u0005\b\u0010b", "cMLkl");
        SoundHandler.I[0x9B ^ 0x9D] = I("|.\u0010!", "RAwFp");
        SoundHandler.I[0x19 ^ 0x1E] = I("7.8)J\n:t(\u0005\u00144t\"\u0005\u0005g14\u0003\u00023xl\t\u0010):#\u001eQ&0(J\u00183t8\u0005Q\"\")\u0004\u0005g/1", "qGTLj");
        SoundHandler.I[0x7 ^ 0xF] = I("\u0002\u0018\u0019?\u0016a\u0019\u0003'R-\u0018\r7R2\u0018\u0019=\u0016a\u0011\u0005?\u0017a", "AwlSr");
        SoundHandler.I[0x62 ^ 0x6B] = I("bi\"+\n &5j\u0005*-a#\u0010n=.j\u00018,/>D", "NIAJd");
        SoundHandler.I[0x3E ^ 0x34] = I(";+T\u0013:'E2\u000b67", "retJu");
    }
    
    public void pauseSounds() {
        this.sndManager.pauseAllSounds();
    }
    
    public void playSound(final ISound sound) {
        this.sndManager.playSound(sound);
    }
    
    public void resumeSounds() {
        this.sndManager.resumeAllSounds();
    }
    
    public boolean isSoundPlaying(final ISound sound) {
        return this.sndManager.isSoundPlaying(sound);
    }
}
