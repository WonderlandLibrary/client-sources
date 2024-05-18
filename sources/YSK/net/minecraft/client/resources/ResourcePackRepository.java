package net.minecraft.client.resources;

import java.util.concurrent.locks.*;
import net.minecraft.client.*;
import com.google.common.hash.*;
import com.google.common.io.*;
import net.minecraft.client.gui.*;
import java.util.concurrent.*;
import com.google.common.util.concurrent.*;
import org.apache.commons.io.filefilter.*;
import com.google.common.collect.*;
import org.apache.commons.io.comparator.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.settings.*;
import java.util.*;
import net.minecraft.client.resources.data.*;
import java.awt.image.*;
import net.minecraft.util.*;
import java.io.*;
import org.apache.commons.io.*;
import net.minecraft.client.renderer.texture.*;

public class ResourcePackRepository
{
    public final IResourcePack rprDefaultResourcePack;
    private List<Entry> repositoryEntries;
    private static final String[] I;
    private static final Logger logger;
    private ListenableFuture<Object> field_177322_i;
    private final File dirServerResourcepacks;
    public final IMetadataSerializer rprMetadataSerializer;
    private final ReentrantLock lock;
    private final File dirResourcepacks;
    private List<Entry> repositoryEntriesAll;
    private IResourcePack resourcePackInstance;
    private static final FileFilter resourcePackFilter;
    
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void fixDirResourcepacks() {
        if (this.dirResourcepacks.exists()) {
            if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs())) {
                ResourcePackRepository.logger.warn(ResourcePackRepository.I[" ".length()] + this.dirResourcepacks);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
        }
        else if (!this.dirResourcepacks.mkdirs()) {
            ResourcePackRepository.logger.warn(ResourcePackRepository.I["  ".length()] + this.dirResourcepacks);
        }
    }
    
    public void func_148529_f() {
        this.lock.lock();
        try {
            if (this.field_177322_i != null) {
                this.field_177322_i.cancel((boolean)(" ".length() != 0));
            }
            this.field_177322_i = null;
            if (this.resourcePackInstance != null) {
                this.resourcePackInstance = null;
                Minecraft.getMinecraft().scheduleResourcesRefresh();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
        }
        finally {
            this.lock.unlock();
        }
        this.lock.unlock();
    }
    
    public void setRepositories(final List<Entry> list) {
        this.repositoryEntries.clear();
        this.repositoryEntries.addAll(list);
    }
    
    public File getDirResourcepacks() {
        return this.dirResourcepacks;
    }
    
    public IResourcePack getResourcePackInstance() {
        return this.resourcePackInstance;
    }
    
    private static void I() {
        (I = new String[0x5B ^ 0x57])["".length()] = I("7\u00069\r9\u0000\u0007t\u0011*\t\u00067\u0016*\u0001C&\u0007<\n\u0016&\u0001*E\u00135\u0001$E\u0018)B-\u0000\u00005\u0017<\u0000C=\u0016h\u0016C:\ro\t\f:\u0005*\u0017C7\r\"\u0015\u0002 \u000b-\t\u0006", "ecTbO");
        ResourcePackRepository.I[" ".length()] = I("\u0010)\u000e4  g\u001b9l7\"\f$)$3\nv> 4\u0000#>&\"\u001f7/.g\t9 !\"\u001dzl,3O34,4\u001b%l'2\u001bv%6g\u000198e&O2%7\"\f\"#7>Uv", "EGoVL");
        ResourcePackRepository.I["  ".length()] = I("3,5\u0018\u000b\u0003b \u0015G\u000501\u001b\u0013\u0003b&\u001f\u0014\t7&\u0019\u0002\u0016#7\u0011G\u0000-8\u001e\u0002\u0014xt", "fBTzg");
        ResourcePackRepository.I["   ".length()] = I("\u0017\u0019#k.yo{\u001b3}r?b", "IBBFH");
        ResourcePackRepository.I[0xA4 ^ 0xA0] = I("-\u001d\u0012\u000098", "AxuaZ");
        ResourcePackRepository.I[0x99 ^ 0x9C] = I("0\u0000\u0005\u0014m", "viiqM");
        ResourcePackRepository.I[0x9C ^ 0x9A] = I("S\u000f\t(b\u0004\u0015\u0007\"%S\u000f\t?*SO\r42\u0016\u0004\u001c)&S", "sghLB");
        ResourcePackRepository.I[0x36 ^ 0x31] = I("Im\u0014,&\u000b)R", "eMrCS");
        ResourcePackRepository.I[0x19 ^ 0x11] = I("o[R\u0011\u001d*\u0010\u0006<\u0016!U\u001b!V", "FurUx");
        ResourcePackRepository.I[0x86 ^ 0x8F] = I("1#\b/U", "wJdJu");
        ResourcePackRepository.I[0x43 ^ 0x49] = I("H\u0012\t>\u001e\f\u001fA?R\n\u0014F#\u0013\u001b\u0019\u0003/\\H5\u0003'\u0017\u001c\u0018\b,R\u0001\u0005H", "hqfKr");
        ResourcePackRepository.I[0x1E ^ 0x15] = I("\r\u000e\u0016\u0004# \u0005\u001dA8%\u000fZ\u00122;\u001d\u001f\u0013w;\u000e\t\u000e\";\b\u001fA'(\b\u0011A", "IkzaW");
    }
    
    public ListenableFuture<Object> downloadResourcePack(final String s, final String s2) {
        String s3;
        if (s2.matches(ResourcePackRepository.I["   ".length()])) {
            s3 = s2;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            s3 = ResourcePackRepository.I[0x73 ^ 0x77];
        }
        final File resourcePackInstance = new File(this.dirServerResourcepacks, s3);
        this.lock.lock();
        try {
            this.func_148529_f();
            if (resourcePackInstance.exists() && s2.length() == (0x83 ^ 0xAB)) {
                try {
                    final String string = Hashing.sha1().hashBytes(Files.toByteArray(resourcePackInstance)).toString();
                    if (string.equals(s2)) {
                        return this.setResourcePackInstance(resourcePackInstance);
                    }
                    ResourcePackRepository.logger.warn(ResourcePackRepository.I[0x29 ^ 0x2C] + resourcePackInstance + ResourcePackRepository.I[0x4A ^ 0x4C] + s2 + ResourcePackRepository.I[0x3D ^ 0x3A] + string + ResourcePackRepository.I[0x49 ^ 0x41]);
                    FileUtils.deleteQuietly(resourcePackInstance);
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                catch (IOException ex) {
                    ResourcePackRepository.logger.warn(ResourcePackRepository.I[0xA7 ^ 0xAE] + resourcePackInstance + ResourcePackRepository.I[0x82 ^ 0x88], (Throwable)ex);
                    FileUtils.deleteQuietly(resourcePackInstance);
                }
            }
            this.func_183028_i();
            final GuiScreenWorking guiScreenWorking = new GuiScreenWorking();
            final Map<String, String> sessionInfo = Minecraft.getSessionInfo();
            final Minecraft minecraft = Minecraft.getMinecraft();
            Futures.getUnchecked((Future)minecraft.addScheduledTask(new Runnable(this, minecraft, guiScreenWorking) {
                final ResourcePackRepository this$0;
                private final Minecraft val$minecraft;
                private final GuiScreenWorking val$guiscreenworking;
                
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
                        if (4 < 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public void run() {
                    this.val$minecraft.displayGuiScreen(this.val$guiscreenworking);
                }
            }));
            Futures.addCallback((ListenableFuture)(this.field_177322_i = HttpUtil.downloadResourcePack(resourcePackInstance, s, sessionInfo, 38179778 + 11492536 - 10817850 + 13574336, guiScreenWorking, minecraft.getProxy())), (FutureCallback)new FutureCallback<Object>(this, resourcePackInstance, SettableFuture.create()) {
                final ResourcePackRepository this$0;
                private final SettableFuture val$settablefuture;
                private final File val$file1;
                
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
                        if (0 == 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                public void onSuccess(final Object o) {
                    this.this$0.setResourcePackInstance(this.val$file1);
                    this.val$settablefuture.set((Object)null);
                }
                
                public void onFailure(final Throwable exception) {
                    this.val$settablefuture.setException(exception);
                }
            });
            return this.field_177322_i;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public List<Entry> getRepositoryEntries() {
        return (List<Entry>)ImmutableList.copyOf((Collection)this.repositoryEntries);
    }
    
    private void func_183028_i() {
        final ArrayList arrayList = Lists.newArrayList((Iterable)FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, (IOFileFilter)null));
        Collections.sort((List<Object>)arrayList, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        int length = "".length();
        final Iterator<File> iterator = arrayList.iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            final File file = iterator.next();
            if (length++ >= (0x13 ^ 0x19)) {
                ResourcePackRepository.logger.info(ResourcePackRepository.I[0x21 ^ 0x2A] + file.getName());
                FileUtils.deleteQuietly(file);
            }
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        resourcePackFilter = new FileFilter() {
            private static final String[] I;
            
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
            
            @Override
            public boolean accept(final File file) {
                int n;
                if (file.isFile() && file.getName().endsWith(ResourcePackRepository$1.I["".length()])) {
                    n = " ".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                final int n2 = n;
                int n3;
                if (file.isDirectory() && new File(file, ResourcePackRepository$1.I[" ".length()]).isFile()) {
                    n3 = " ".length();
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                final int n4 = n3;
                if (n2 == 0 && n4 == 0) {
                    return "".length() != 0;
                }
                return " ".length() != 0;
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("Y\u0017\u000e5", "wmgEu");
                ResourcePackRepository$1.I[" ".length()] = I("\u0012/%;x\u000f-+5\"\u0003", "bNFPV");
            }
            
            static {
                I();
            }
        };
    }
    
    public void updateRepositoryEntriesAll() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<File> iterator = this.getResourcePackFiles().iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Entry entry = new Entry(iterator.next(), null);
            if (!this.repositoryEntriesAll.contains(entry)) {
                try {
                    entry.updateResourcePack();
                    arrayList.add(entry);
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                    continue;
                }
                catch (Exception ex) {
                    arrayList.remove(entry);
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    continue;
                }
            }
            final int index = this.repositoryEntriesAll.indexOf(entry);
            if (index > -" ".length() && index < this.repositoryEntriesAll.size()) {
                arrayList.add(this.repositoryEntriesAll.get(index));
            }
        }
        this.repositoryEntriesAll.removeAll(arrayList);
        final Iterator<Entry> iterator2 = this.repositoryEntriesAll.iterator();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (iterator2.hasNext()) {
            iterator2.next().closeResourcePack();
        }
        this.repositoryEntriesAll = (List<Entry>)arrayList;
    }
    
    public ResourcePackRepository(final File dirResourcepacks, final File dirServerResourcepacks, final IResourcePack rprDefaultResourcePack, final IMetadataSerializer rprMetadataSerializer, final GameSettings gameSettings) {
        this.lock = new ReentrantLock();
        this.repositoryEntriesAll = (List<Entry>)Lists.newArrayList();
        this.repositoryEntries = (List<Entry>)Lists.newArrayList();
        this.dirResourcepacks = dirResourcepacks;
        this.dirServerResourcepacks = dirServerResourcepacks;
        this.rprDefaultResourcePack = rprDefaultResourcePack;
        this.rprMetadataSerializer = rprMetadataSerializer;
        this.fixDirResourcepacks();
        this.updateRepositoryEntriesAll();
        final Iterator iterator = gameSettings.resourcePacks.iterator();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final Iterator<Entry> iterator2 = this.repositoryEntriesAll.iterator();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final Entry entry = iterator2.next();
                if (entry.getResourcePackName().equals(s)) {
                    if (entry.func_183027_f() == " ".length() || gameSettings.field_183018_l.contains(entry.getResourcePackName())) {
                        this.repositoryEntries.add(entry);
                        "".length();
                        if (4 == -1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        iterator.remove();
                        final Logger logger = ResourcePackRepository.logger;
                        final String s2 = ResourcePackRepository.I["".length()];
                        final Object[] array = new Object[" ".length()];
                        array["".length()] = entry.getResourcePackName();
                        logger.warn(s2, array);
                    }
                }
            }
        }
    }
    
    private List<File> getResourcePackFiles() {
        List<File> list;
        if (this.dirResourcepacks.isDirectory()) {
            list = Arrays.asList(this.dirResourcepacks.listFiles(ResourcePackRepository.resourcePackFilter));
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            list = Collections.emptyList();
        }
        return list;
    }
    
    public ListenableFuture<Object> setResourcePackInstance(final File file) {
        this.resourcePackInstance = new FileResourcePack(file);
        return Minecraft.getMinecraft().scheduleResourcesRefresh();
    }
    
    public List<Entry> getRepositoryEntriesAll() {
        return (List<Entry>)ImmutableList.copyOf((Collection)this.repositoryEntriesAll);
    }
    
    public class Entry
    {
        private PackMetadataSection rePackMetadataSection;
        final ResourcePackRepository this$0;
        private final File resourcePackFile;
        private ResourceLocation locationTexturePackIcon;
        private IResourcePack reResourcePack;
        private BufferedImage texturePackIcon;
        private static final String[] I;
        
        private Entry(final ResourcePackRepository this$0, final File resourcePackFile) {
            this.this$0 = this$0;
            this.resourcePackFile = resourcePackFile;
        }
        
        public void updateResourcePack() throws IOException {
            AbstractResourcePack reResourcePack;
            if (this.resourcePackFile.isDirectory()) {
                reResourcePack = new FolderResourcePack(this.resourcePackFile);
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
            else {
                reResourcePack = new FileResourcePack(this.resourcePackFile);
            }
            this.reResourcePack = reResourcePack;
            this.rePackMetadataSection = this.reResourcePack.getPackMetadata(this.this$0.rprMetadataSerializer, Entry.I["".length()]);
            try {
                this.texturePackIcon = this.reResourcePack.getPackImage();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            catch (IOException ex) {}
            if (this.texturePackIcon == null) {
                this.texturePackIcon = this.this$0.rprDefaultResourcePack.getPackImage();
            }
            this.closeResourcePack();
        }
        
        private static void I() {
            (I = new String[0x8F ^ 0x89])["".length()] = I("30\u000e9", "CQmRG");
            Entry.I[" ".length()] = I("\u0015\u0017\u0000\u0000\u0010\u0013\u0017\b\u0015\u0006\n\u001b\u001b\u001b\u000b", "arxte");
            Entry.I["  ".length()] = I("\u0006&.%\u000b&,x4\u0006,#v)\u0004\"-,%Gg'*d\n&;+-\t(h\u007f4\u0006,#\u007fd\u0014*+,-\b!a", "OHXDg");
            Entry.I["   ".length()] = I("G6{j?X`%", "bEAOL");
            Entry.I[0x98 ^ 0x9C] = I("\f;$.7\u0018", "jTHJR");
            Entry.I[0x0 ^ 0x5] = I("3\u0007\u0007", "InwOl");
        }
        
        @Override
        public boolean equals(final Object o) {
            int n;
            if (this == o) {
                n = " ".length();
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else if (o instanceof Entry) {
                n = (this.toString().equals(o.toString()) ? 1 : 0);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
        }
        
        public int func_183027_f() {
            return this.rePackMetadataSection.getPackFormat();
        }
        
        @Override
        public String toString() {
            final String s = Entry.I["   ".length()];
            final Object[] array = new Object["   ".length()];
            array["".length()] = this.resourcePackFile.getName();
            final int length = " ".length();
            String s2;
            if (this.resourcePackFile.isDirectory()) {
                s2 = Entry.I[0x47 ^ 0x43];
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
            else {
                s2 = Entry.I[0xB9 ^ 0xBC];
            }
            array[length] = s2;
            array["  ".length()] = this.resourcePackFile.lastModified();
            return String.format(s, array);
        }
        
        static {
            I();
        }
        
        public String getTexturePackDescription() {
            String s;
            if (this.rePackMetadataSection == null) {
                s = EnumChatFormatting.RED + Entry.I["  ".length()];
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                s = this.rePackMetadataSection.getPackDescription().getFormattedText();
            }
            return s;
        }
        
        public String getResourcePackName() {
            return this.reResourcePack.getPackName();
        }
        
        Entry(final ResourcePackRepository resourcePackRepository, final File file, final Entry entry) {
            this(resourcePackRepository, file);
        }
        
        public IResourcePack getResourcePack() {
            return this.reResourcePack;
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        public void closeResourcePack() {
            if (this.reResourcePack instanceof Closeable) {
                IOUtils.closeQuietly((Closeable)this.reResourcePack);
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
                if (3 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public void bindTexturePackIcon(final TextureManager textureManager) {
            if (this.locationTexturePackIcon == null) {
                this.locationTexturePackIcon = textureManager.getDynamicTextureLocation(Entry.I[" ".length()], new DynamicTexture(this.texturePackIcon));
            }
            textureManager.bindTexture(this.locationTexturePackIcon);
        }
    }
}
