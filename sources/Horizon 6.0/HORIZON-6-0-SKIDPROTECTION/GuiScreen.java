package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import org.apache.commons.lang3.StringUtils;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;
import java.io.IOException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import java.net.URI;
import java.util.List;
import com.google.common.base.Splitter;
import java.util.Set;
import org.apache.logging.log4j.Logger;

public abstract class GuiScreen extends Gui_1808253012 implements GuiYesNoCallback
{
    private static final Logger HorizonCode_Horizon_È;
    private static final Set Â;
    private static final Splitter Ý;
    protected static Minecraft Ñ¢á;
    protected RenderItem ŒÏ;
    public static int Çªà¢;
    public static int Ê;
    protected List ÇŽÉ;
    protected List ˆá;
    public boolean ÇŽÕ;
    protected FontRenderer É;
    private GuiButton Ø­áŒŠá;
    private int Âµá€;
    private long Ó;
    private int à;
    private URI Ø;
    private static final String áŒŠÆ = "CL_00000710";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = Sets.newHashSet((Object[])new String[] { "http", "https" });
        Ý = Splitter.on('\n');
    }
    
    public GuiScreen() {
        this.ÇŽÉ = Lists.newArrayList();
        this.ˆá = Lists.newArrayList();
    }
    
    public void HorizonCode_Horizon_È(final int n, final int n2, final float n3) {
        if (!CompressedStreamTools.HorizonCode_Horizon_È && !(this instanceof GuiLogin)) {
            GuiScreen.Ñ¢á.HorizonCode_Horizon_È(new GuiLogin());
        }
        for (int i = 0; i < this.ÇŽÉ.size(); ++i) {
            ((GuiButton)this.ÇŽÉ.get(i)).Ý(GuiScreen.Ñ¢á, n, n2);
        }
        for (int j = 0; j < this.ˆá.size(); ++j) {
            ((GuiLabel)this.ˆá.get(j)).HorizonCode_Horizon_È(GuiScreen.Ñ¢á, n, n2);
        }
    }
    
    protected void HorizonCode_Horizon_È(final char c, final int n) throws IOException {
        if (n == 1) {
            GuiScreen.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
            if (GuiScreen.Ñ¢á.¥Æ == null) {
                GuiScreen.Ñ¢á.Å();
            }
        }
    }
    
    public static void áˆºÑ¢Õ() {
    }
    
    public static String ÂµÈ() {
        try {
            final Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception ex) {}
        return "";
    }
    
    public static void Ø­áŒŠá(final String s) {
        if (!StringUtils.isEmpty((CharSequence)s)) {
            try {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
            }
            catch (Exception ex) {}
        }
    }
    
    protected void HorizonCode_Horizon_È(final ItemStack itemStack, final int n, final int n2) {
        final List horizonCode_Horizon_È = itemStack.HorizonCode_Horizon_È(GuiScreen.Ñ¢á.á, GuiScreen.Ñ¢á.ŠÄ.¥É);
        for (int i = 0; i < horizonCode_Horizon_È.size(); ++i) {
            if (i == 0) {
                horizonCode_Horizon_È.set(i, itemStack.µÕ().Âµá€ + horizonCode_Horizon_È.get(i));
            }
            else {
                horizonCode_Horizon_È.set(i, EnumChatFormatting.Ø + horizonCode_Horizon_È.get(i));
            }
        }
        this.HorizonCode_Horizon_È(horizonCode_Horizon_È, n, n2);
    }
    
    protected void HorizonCode_Horizon_È(final String s, final int n, final int n2) {
        this.HorizonCode_Horizon_È(Arrays.asList(s), n, n2);
    }
    
    protected void HorizonCode_Horizon_È(final List list, final int n, final int n2) {
        if (!list.isEmpty()) {
            GlStateManager.Ñ¢á();
            RenderHelper.HorizonCode_Horizon_È();
            GlStateManager.Ó();
            GlStateManager.áŒŠÆ();
            int n3 = 0;
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                final int horizonCode_Horizon_È = this.É.HorizonCode_Horizon_È(iterator.next());
                if (horizonCode_Horizon_È > n3) {
                    n3 = horizonCode_Horizon_È;
                }
            }
            int n4 = n + 12;
            int n5 = n2 - 12;
            int n6 = 8;
            if (list.size() > 1) {
                n6 += 2 + (list.size() - 1) * 10;
            }
            if (n4 + n3 > GuiScreen.Çªà¢) {
                n4 -= 28 + n3;
            }
            if (n5 + n6 + 6 > GuiScreen.Ê) {
                n5 = GuiScreen.Ê - n6 - 6;
            }
            GuiScreen.ŠÄ = 300.0f;
            this.ŒÏ.HorizonCode_Horizon_È = 300.0f;
            final int n7 = -267386864;
            Gui_1808253012.HorizonCode_Horizon_È(n4 - 3, n5 - 4, n4 + n3 + 3, n5 - 3, n7, n7);
            Gui_1808253012.HorizonCode_Horizon_È(n4 - 3, n5 + n6 + 3, n4 + n3 + 3, n5 + n6 + 4, n7, n7);
            Gui_1808253012.HorizonCode_Horizon_È(n4 - 3, n5 - 3, n4 + n3 + 3, n5 + n6 + 3, n7, n7);
            Gui_1808253012.HorizonCode_Horizon_È(n4 - 4, n5 - 3, n4 - 3, n5 + n6 + 3, n7, n7);
            Gui_1808253012.HorizonCode_Horizon_È(n4 + n3 + 3, n5 - 3, n4 + n3 + 4, n5 + n6 + 3, n7, n7);
            final int n8 = 1347420415;
            final int n9 = (n8 & 0xFEFEFE) >> 1 | (n8 & 0xFF000000);
            Gui_1808253012.HorizonCode_Horizon_È(n4 - 3, n5 - 3 + 1, n4 - 3 + 1, n5 + n6 + 3 - 1, n8, n9);
            Gui_1808253012.HorizonCode_Horizon_È(n4 + n3 + 2, n5 - 3 + 1, n4 + n3 + 3, n5 + n6 + 3 - 1, n8, n9);
            Gui_1808253012.HorizonCode_Horizon_È(n4 - 3, n5 - 3, n4 + n3 + 3, n5 - 3 + 1, n8, n8);
            Gui_1808253012.HorizonCode_Horizon_È(n4 - 3, n5 + n6 + 2, n4 + n3 + 3, n5 + n6 + 3, n9, n9);
            for (int i = 0; i < list.size(); ++i) {
                this.É.HorizonCode_Horizon_È(list.get(i), n4, (float)n5, -1);
                if (i == 0) {
                    n5 += 2;
                }
                n5 += 10;
            }
            GuiScreen.ŠÄ = 0.0f;
            this.ŒÏ.HorizonCode_Horizon_È = 0.0f;
            GlStateManager.Âµá€();
            GlStateManager.áˆºÑ¢Õ();
            RenderHelper.Â();
            GlStateManager.ŠÄ();
        }
    }
    
    protected void HorizonCode_Horizon_È(final IChatComponent chatComponent, final int n, final int n2) {
        if (chatComponent != null && chatComponent.à().áŒŠÆ() != null) {
            final HoverEvent áœšæ = chatComponent.à().áŒŠÆ();
            if (áœšæ.HorizonCode_Horizon_È() == HoverEvent.HorizonCode_Horizon_È.Ý) {
                ItemStack horizonCode_Horizon_È = null;
                try {
                    final NBTTagCompound horizonCode_Horizon_È2 = JsonToNBT.HorizonCode_Horizon_È(áœšæ.Â().Ø());
                    if (horizonCode_Horizon_È2 instanceof NBTTagCompound) {
                        horizonCode_Horizon_È = ItemStack.HorizonCode_Horizon_È(horizonCode_Horizon_È2);
                    }
                }
                catch (NBTException ex) {}
                if (horizonCode_Horizon_È != null) {
                    this.HorizonCode_Horizon_È(horizonCode_Horizon_È, n, n2);
                }
                else {
                    this.HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­ + "Invalid Item!", n, n2);
                }
            }
            else if (áœšæ.HorizonCode_Horizon_È() == HoverEvent.HorizonCode_Horizon_È.Ø­áŒŠá) {
                if (GuiScreen.Ñ¢á.ŠÄ.¥É) {
                    try {
                        final NBTTagCompound horizonCode_Horizon_È3 = JsonToNBT.HorizonCode_Horizon_È(áœšæ.Â().Ø());
                        if (horizonCode_Horizon_È3 instanceof NBTTagCompound) {
                            final ArrayList arrayList = Lists.newArrayList();
                            final NBTTagCompound nbtTagCompound = horizonCode_Horizon_È3;
                            arrayList.add(nbtTagCompound.áˆºÑ¢Õ("name"));
                            if (nbtTagCompound.Â("type", 8)) {
                                final String áˆºÑ¢Õ = nbtTagCompound.áˆºÑ¢Õ("type");
                                arrayList.add("Type: " + áˆºÑ¢Õ + " (" + EntityList.HorizonCode_Horizon_È(áˆºÑ¢Õ) + ")");
                            }
                            arrayList.add(nbtTagCompound.áˆºÑ¢Õ("id"));
                            this.HorizonCode_Horizon_È(arrayList, n, n2);
                        }
                        else {
                            this.HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­ + "Invalid Entity!", n, n2);
                        }
                    }
                    catch (NBTException ex2) {
                        this.HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­ + "Invalid Entity!", n, n2);
                    }
                }
            }
            else if (áœšæ.HorizonCode_Horizon_È() == HoverEvent.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(GuiScreen.Ý.splitToList((CharSequence)áœšæ.Â().áŒŠÆ()), n, n2);
            }
            else if (áœšæ.HorizonCode_Horizon_È() == HoverEvent.HorizonCode_Horizon_È.Â) {
                final StatBase horizonCode_Horizon_È4 = StatList.HorizonCode_Horizon_È(áœšæ.Â().Ø());
                if (horizonCode_Horizon_È4 != null) {
                    final IChatComponent âµá€ = horizonCode_Horizon_È4.Âµá€();
                    final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stats.tooltip.type." + (horizonCode_Horizon_È4.Ø­áŒŠá() ? "achievement" : "statistic"), new Object[0]);
                    chatComponentTranslation.à().Â(Boolean.valueOf(true));
                    final String str = (horizonCode_Horizon_È4 instanceof Achievement) ? ((Achievement)horizonCode_Horizon_È4).Ó() : null;
                    final ArrayList arrayList2 = Lists.newArrayList((Object[])new String[] { âµá€.áŒŠÆ(), chatComponentTranslation.áŒŠÆ() });
                    if (str != null) {
                        arrayList2.addAll(this.É.Ý(str, 150));
                    }
                    this.HorizonCode_Horizon_È(arrayList2, n, n2);
                }
                else {
                    this.HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­ + "Invalid statistic/achievement!", n, n2);
                }
            }
            GlStateManager.Ó();
        }
    }
    
    protected void HorizonCode_Horizon_È(final String s, final boolean b) {
    }
    
    protected boolean HorizonCode_Horizon_È(final IChatComponent chatComponent) {
        if (chatComponent == null) {
            return false;
        }
        final ClickEvent ø = chatComponent.à().Ø();
        if (£à()) {
            if (chatComponent.à().áˆºÑ¢Õ() != null) {
                this.HorizonCode_Horizon_È(chatComponent.à().áˆºÑ¢Õ(), false);
            }
        }
        else if (ø != null) {
            if (ø.HorizonCode_Horizon_È() == ClickEvent.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                if (!GuiScreen.Ñ¢á.ŠÄ.£áŒŠá) {
                    return false;
                }
                try {
                    final URI ø2 = new URI(ø.Â());
                    if (!GuiScreen.Â.contains(ø2.getScheme().toLowerCase())) {
                        throw new URISyntaxException(ø.Â(), "Unsupported protocol: " + ø2.getScheme().toLowerCase());
                    }
                    if (GuiScreen.Ñ¢á.ŠÄ.áˆº) {
                        this.Ø = ø2;
                        GuiScreen.Ñ¢á.HorizonCode_Horizon_È(new GuiConfirmOpenLink(this, ø.Â(), 31102009, false));
                    }
                    else {
                        this.HorizonCode_Horizon_È(ø2);
                    }
                }
                catch (URISyntaxException ex) {
                    GuiScreen.HorizonCode_Horizon_È.error("Can't open url for " + ø, (Throwable)ex);
                }
            }
            else if (ø.HorizonCode_Horizon_È() == ClickEvent.HorizonCode_Horizon_È.Â) {
                this.HorizonCode_Horizon_È(new File(ø.Â()).toURI());
            }
            else if (ø.HorizonCode_Horizon_È() == ClickEvent.HorizonCode_Horizon_È.Âµá€) {
                this.HorizonCode_Horizon_È(ø.Â(), true);
            }
            else if (ø.HorizonCode_Horizon_È() == ClickEvent.HorizonCode_Horizon_È.Ý) {
                this.Â(ø.Â(), false);
            }
            else if (ø.HorizonCode_Horizon_È() == ClickEvent.HorizonCode_Horizon_È.Ø­áŒŠá) {
                if (GuiScreen.Ñ¢á.Ä().Âµá€(ø.Â()) == null) {
                    GuiScreen.HorizonCode_Horizon_È.error("Tried to handle twitch user but couldn't find them!");
                }
            }
            else {
                GuiScreen.HorizonCode_Horizon_È.error("Don't know how to handle " + ø);
            }
            return true;
        }
        return false;
    }
    
    public void Âµá€(final String s) {
        this.Â(s, true);
    }
    
    public void Â(final String p_146239_1_, final boolean b) {
        if (b) {
            GuiScreen.Ñ¢á.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(p_146239_1_);
        }
        GuiScreen.Ñ¢á.á.Â(p_146239_1_);
    }
    
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int n) throws IOException {
        if (n == 0) {
            for (int i = 0; i < this.ÇŽÉ.size(); ++i) {
                final GuiButton ø­áŒŠá = this.ÇŽÉ.get(i);
                if (ø­áŒŠá.Â(GuiScreen.Ñ¢á, mouseX, mouseY)) {
                    (this.Ø­áŒŠá = ø­áŒŠá).HorizonCode_Horizon_È(GuiScreen.Ñ¢á.£ÂµÄ());
                    this.HorizonCode_Horizon_È(ø­áŒŠá);
                }
            }
        }
    }
    
    protected void Â(final int mouseX, final int mouseY, final int n) {
        if (this.Ø­áŒŠá != null && n == 0) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(mouseX, mouseY);
            this.Ø­áŒŠá = null;
        }
    }
    
    protected void HorizonCode_Horizon_È(final int n, final int n2, final int n3, final long n4) {
    }
    
    protected void HorizonCode_Horizon_È(final GuiButton guiButton) throws IOException {
    }
    
    public void HorizonCode_Horizon_È(final Minecraft ñ¢á, final int çªà¢, final int ê) {
        GuiScreen.Ñ¢á = ñ¢á;
        this.ŒÏ = ñ¢á.áˆºÏ();
        this.É = ñ¢á.µà;
        GuiScreen.Çªà¢ = çªà¢;
        GuiScreen.Ê = ê;
        this.ÇŽÉ.clear();
        this.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È() {
    }
    
    public void á() throws IOException {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.n_();
            }
        }
        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {
                this.ˆÏ­();
            }
        }
    }
    
    public void n_() throws IOException {
        final int n = Mouse.getEventX() * GuiScreen.Çªà¢ / GuiScreen.Ñ¢á.Ó;
        final int n2 = GuiScreen.Ê - Mouse.getEventY() * GuiScreen.Ê / GuiScreen.Ñ¢á.à - 1;
        final int eventButton = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (GuiScreen.Ñ¢á.ŠÄ.ÂµÕ && this.à++ > 0) {
                return;
            }
            this.Âµá€ = eventButton;
            this.Ó = Minecraft.áƒ();
            this.HorizonCode_Horizon_È(n, n2, this.Âµá€);
        }
        else if (eventButton != -1) {
            if (GuiScreen.Ñ¢á.ŠÄ.ÂµÕ && --this.à > 0) {
                return;
            }
            this.Âµá€ = -1;
            this.Â(n, n2, eventButton);
        }
        else if (this.Âµá€ != -1 && this.Ó > 0L) {
            this.HorizonCode_Horizon_È(n, n2, this.Âµá€, Minecraft.áƒ() - this.Ó);
        }
    }
    
    public void ˆÏ­() throws IOException {
        if (Keyboard.getEventKeyState()) {
            this.HorizonCode_Horizon_È(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        GuiScreen.Ñ¢á.Ñ¢Â();
    }
    
    public void Ý() {
    }
    
    public void q_() {
    }
    
    public void £á() {
        this.Â(0);
    }
    
    public void Â(final int n) {
        if (GuiScreen.Ñ¢á.áŒŠÆ != null) {
            if (!Horizon.ÂµÈ) {
                Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiScreen.Çªà¢, GuiScreen.Ê, 1627389951, 1610612736);
            }
            else {
                Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiScreen.Çªà¢, GuiScreen.Ê, 1610612736, 805306368);
            }
        }
        else {
            this.Ý(n);
        }
    }
    
    public void Ý(final int n) {
        GlStateManager.Ó();
        GlStateManager.£á();
        final Tessellator horizonCode_Horizon_È = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer ý = horizonCode_Horizon_È.Ý();
        GuiScreen.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiScreen.Šáƒ);
        final float n2 = 32.0f;
        ý.Â();
        ý.Ý(4210752);
        ý.HorizonCode_Horizon_È(0.0, GuiScreen.Ê, 0.0, 0.0, GuiScreen.Ê / n2 + n);
        ý.HorizonCode_Horizon_È(GuiScreen.Çªà¢, GuiScreen.Ê, 0.0, GuiScreen.Çªà¢ / n2, GuiScreen.Ê / n2 + n);
        ý.HorizonCode_Horizon_È(GuiScreen.Çªà¢, 0.0, 0.0, GuiScreen.Çªà¢ / n2, n);
        ý.HorizonCode_Horizon_È(0.0, 0.0, 0.0, 0.0, n);
        horizonCode_Horizon_È.Â();
    }
    
    public boolean Ø­áŒŠá() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean b, final int n) {
        if (n == 31102009) {
            if (b) {
                this.HorizonCode_Horizon_È(this.Ø);
            }
            this.Ø = null;
            GuiScreen.Ñ¢á.HorizonCode_Horizon_È(this);
        }
    }
    
    private void HorizonCode_Horizon_È(final URI uri) {
        try {
            final Class<?> forName = Class.forName("java.awt.Desktop");
            forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), uri);
        }
        catch (Throwable t) {
            GuiScreen.HorizonCode_Horizon_È.error("Couldn't open link", t);
        }
    }
    
    public static boolean Å() {
        return Minecraft.HorizonCode_Horizon_È ? (Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)) : (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
    }
    
    public static boolean £à() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
    
    public static boolean µà() {
        return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
    }
    
    public static boolean Ø­áŒŠá(final int n) {
        return n == 45 && Å();
    }
    
    public static boolean Âµá€(final int n) {
        return n == 47 && Å();
    }
    
    public static boolean Ó(final int n) {
        return n == 46 && Å();
    }
    
    public static boolean à(final int n) {
        return n == 30 && Å();
    }
    
    public void Â(final Minecraft minecraft, final int n, final int n2) {
        this.HorizonCode_Horizon_È(minecraft, n, n2);
    }
}
