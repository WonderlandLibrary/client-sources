package net.minecraft.client.gui;

import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import java.net.*;
import org.lwjgl.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class GuiScreenResourcePacks extends GuiScreen
{
    private static final String[] I;
    private GuiResourcePackAvailable availableResourcePacksList;
    private GuiResourcePackSelected selectedResourcePacksList;
    private static final Logger logger;
    private boolean changed;
    private final GuiScreen parentScreen;
    private List<ResourcePackListEntry> selectedResourcePacks;
    private List<ResourcePackListEntry> availableResourcePacks;
    
    public List<ResourcePackListEntry> getSelectedResourcePacks() {
        return this.selectedResourcePacks;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton("  ".length(), this.width / "  ".length() - (60 + 89 - 0 + 5), this.height - (0x32 ^ 0x2), I18n.format(GuiScreenResourcePacks.I["".length()], new Object["".length()])));
        this.buttonList.add(new GuiOptionButton(" ".length(), this.width / "  ".length() + (0x3C ^ 0x38), this.height - (0x17 ^ 0x27), I18n.format(GuiScreenResourcePacks.I[" ".length()], new Object["".length()])));
        if (!this.changed) {
            this.availableResourcePacks = (List<ResourcePackListEntry>)Lists.newArrayList();
            this.selectedResourcePacks = (List<ResourcePackListEntry>)Lists.newArrayList();
            final ResourcePackRepository resourcePackRepository = this.mc.getResourcePackRepository();
            resourcePackRepository.updateRepositoryEntriesAll();
            final ArrayList arrayList = Lists.newArrayList((Iterable)resourcePackRepository.getRepositoryEntriesAll());
            arrayList.removeAll(resourcePackRepository.getRepositoryEntries());
            final Iterator<ResourcePackRepository.Entry> iterator = arrayList.iterator();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.availableResourcePacks.add(new ResourcePackListEntryFound(this, iterator.next()));
            }
            final Iterator iterator2 = Lists.reverse((List)resourcePackRepository.getRepositoryEntries()).iterator();
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, iterator2.next()));
            }
            this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
        }
        (this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 183 + 152 - 218 + 83, this.height, this.availableResourcePacks)).setSlotXBoundsFromLeft(this.width / "  ".length() - (0x82 ^ 0x86) - (32 + 6 + 18 + 144));
        this.availableResourcePacksList.registerScrollButtons(0x7A ^ 0x7D, 0x67 ^ 0x6F);
        (this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 94 + 120 - 201 + 187, this.height, this.selectedResourcePacks)).setSlotXBoundsFromLeft(this.width / "  ".length() + (0x15 ^ 0x11));
        this.selectedResourcePacksList.registerScrollButtons(0x54 ^ 0x53, 0x6A ^ 0x62);
    }
    
    public void markChanged() {
        this.changed = (" ".length() != 0);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x1E ^ 0x10])["".length()] = I("\u0018\r\u0018\u001c?\u0018\u000b\u000e#+\t\u0003E\u001c:\u000f\u0006-\u001c&\u000e\r\u0019", "jhksJ");
        GuiScreenResourcePacks.I[" ".length()] = I("\u001d$\u001ax5\u0015?\u0016", "zQsVQ");
        GuiScreenResourcePacks.I["  ".length()] = I("e,\n\u0014_(0\u0017I\u001f:<\u0017", "JYyfp");
        GuiScreenResourcePacks.I["   ".length()] = I(":&\u0017$7\u0017n\u0016h<\t,\fh5\u0010%\u0007", "yIbHS");
        GuiScreenResourcePacks.I[0xC5 ^ 0xC1] = I("\u001b+'k\u0000\u0000#cj&X57$\u0017\ffa\n\u0015\u001d(c#\f\u0014#aeG]5a", "xFCEe");
        GuiScreenResourcePacks.I[0xA9 ^ 0xAC] = I("\u00115\u001b\u000e+<}\u001aB \"?\u0000B);6\u000b", "RZnbO");
        GuiScreenResourcePacks.I[0xE ^ 0x8] = I("\u0001\u0010\u000e)L\n\u0006\ff&\u000e\u0002\u0013<\r\u001b", "kqxHb");
        GuiScreenResourcePacks.I[0xBE ^ 0xB9] = I("\u0011$'\u00156\u0005*'>#", "vASQS");
        GuiScreenResourcePacks.I[0x1B ^ 0x13] = I("\u0006\u0017?&:\u0001", "dePQI");
        GuiScreenResourcePacks.I[0x5A ^ 0x53] = I("\"\t\u0007(\u0000\u000fA\u0006d\u000b\u0011\u0003\u001cd\b\b\b\u0019", "afrDd");
        GuiScreenResourcePacks.I[0x86 ^ 0x8C] = I(" \u001e<$\r\u0001\ty<\r\u000eN*3\u0017\u001b\u000b4j\u0007\u0003\u000f*9E", "onYJd");
        GuiScreenResourcePacks.I[0x24 ^ 0x2F] = I(".\b;$ygN", "HaWAC");
        GuiScreenResourcePacks.I[0x62 ^ 0x6E] = I("#\n6\u001e>#\f !*2\u0004k\u0005\"%\u0003 ", "QoEqK");
        GuiScreenResourcePacks.I[0x50 ^ 0x5D] = I("\n5\t\u0003\u0019\n3\u001f<\r\u001b;T\n\u0003\u00144\u001f\u001e%\u00166\u0015", "xPzll");
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.selectedResourcePacksList.handleMouseInput();
        this.availableResourcePacksList.handleMouseInput();
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == "  ".length()) {
                final File dirResourcepacks = this.mc.getResourcePackRepository().getDirResourcepacks();
                final String absolutePath = dirResourcepacks.getAbsolutePath();
                Label_0199: {
                    if (Util.getOSType() == Util.EnumOS.OSX) {
                        try {
                            GuiScreenResourcePacks.logger.info(absolutePath);
                            final Runtime runtime = Runtime.getRuntime();
                            final String[] array = new String["  ".length()];
                            array["".length()] = GuiScreenResourcePacks.I["  ".length()];
                            array[" ".length()] = absolutePath;
                            runtime.exec(array);
                            return;
                        }
                        catch (IOException ex) {
                            GuiScreenResourcePacks.logger.error(GuiScreenResourcePacks.I["   ".length()], (Throwable)ex);
                            "".length();
                            if (1 <= 0) {
                                throw null;
                            }
                            break Label_0199;
                        }
                    }
                    if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                        final String s = GuiScreenResourcePacks.I[0x2D ^ 0x29];
                        final Object[] array2 = new Object[" ".length()];
                        array2["".length()] = absolutePath;
                        final String format = String.format(s, array2);
                        try {
                            Runtime.getRuntime().exec(format);
                            return;
                        }
                        catch (IOException ex2) {
                            GuiScreenResourcePacks.logger.error(GuiScreenResourcePacks.I[0x9E ^ 0x9B], (Throwable)ex2);
                        }
                    }
                }
                int n = "".length();
                try {
                    final Class<?> forName = Class.forName(GuiScreenResourcePacks.I[0x8 ^ 0xE]);
                    final Object invoke = forName.getMethod(GuiScreenResourcePacks.I[0x4A ^ 0x4D], (Class<?>[])new Class["".length()]).invoke(null, new Object["".length()]);
                    final Class<?> clazz = forName;
                    final String s2 = GuiScreenResourcePacks.I[0x8C ^ 0x84];
                    final Class[] array3 = new Class[" ".length()];
                    array3["".length()] = URI.class;
                    final Method method = clazz.getMethod(s2, (Class<?>[])array3);
                    final Object o = invoke;
                    final Object[] array4 = new Object[" ".length()];
                    array4["".length()] = dirResourcepacks.toURI();
                    method.invoke(o, array4);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                catch (Throwable t) {
                    GuiScreenResourcePacks.logger.error(GuiScreenResourcePacks.I[0x63 ^ 0x6A], t);
                    n = " ".length();
                }
                if (n != 0) {
                    GuiScreenResourcePacks.logger.info(GuiScreenResourcePacks.I[0x8D ^ 0x87]);
                    Sys.openURL(GuiScreenResourcePacks.I[0x30 ^ 0x3B] + absolutePath);
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
            }
            else if (guiButton.id == " ".length()) {
                if (this.changed) {
                    final ArrayList arrayList = Lists.newArrayList();
                    final Iterator<ResourcePackListEntry> iterator = this.selectedResourcePacks.iterator();
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final ResourcePackListEntry resourcePackListEntry = iterator.next();
                        if (resourcePackListEntry instanceof ResourcePackListEntryFound) {
                            arrayList.add(((ResourcePackListEntryFound)resourcePackListEntry).func_148318_i());
                        }
                    }
                    Collections.reverse(arrayList);
                    this.mc.getResourcePackRepository().setRepositories(arrayList);
                    this.mc.gameSettings.resourcePacks.clear();
                    this.mc.gameSettings.field_183018_l.clear();
                    final Iterator<ResourcePackRepository.Entry> iterator2 = (Iterator<ResourcePackRepository.Entry>)arrayList.iterator();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    while (iterator2.hasNext()) {
                        final ResourcePackRepository.Entry entry = iterator2.next();
                        this.mc.gameSettings.resourcePacks.add(entry.getResourcePackName());
                        if (entry.func_183027_f() != " ".length()) {
                            this.mc.gameSettings.field_183018_l.add(entry.getResourcePackName());
                        }
                    }
                    this.mc.gameSettings.saveOptions();
                    this.mc.refreshResources();
                }
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
    
    public GuiScreenResourcePacks(final GuiScreen parentScreen) {
        this.changed = ("".length() != 0);
        this.parentScreen = parentScreen;
    }
    
    public List<ResourcePackListEntry> getListContaining(final ResourcePackListEntry resourcePackListEntry) {
        List<ResourcePackListEntry> list;
        if (this.hasResourcePackEntry(resourcePackListEntry)) {
            list = this.selectedResourcePacks;
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            list = this.availableResourcePacks;
        }
        return list;
    }
    
    public boolean hasResourcePackEntry(final ResourcePackListEntry resourcePackListEntry) {
        return this.selectedResourcePacks.contains(resourcePackListEntry);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawBackground("".length());
        this.availableResourcePacksList.drawScreen(n, n2, n3);
        this.selectedResourcePacksList.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiScreenResourcePacks.I[0x42 ^ 0x4E], new Object["".length()]), this.width / "  ".length(), 0x70 ^ 0x60, 4078514 + 10487438 - 12071657 + 14282920);
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiScreenResourcePacks.I[0x78 ^ 0x75], new Object["".length()]), this.width / "  ".length() - (0x1D ^ 0x50), this.height - (0x65 ^ 0x7F), 5251322 + 5762494 - 5596331 + 3004019);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.availableResourcePacksList.mouseClicked(n, n2, n3);
        this.selectedResourcePacksList.mouseClicked(n, n2, n3);
    }
    
    public List<ResourcePackListEntry> getAvailableResourcePacks() {
        return this.availableResourcePacks;
    }
}
