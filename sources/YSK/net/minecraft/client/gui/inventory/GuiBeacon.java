package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.*;
import net.minecraft.potion.*;

public class GuiBeacon extends GuiContainer
{
    private boolean buttonsNotDrawn;
    private static final Logger logger;
    private IInventory tileBeacon;
    private ConfirmButton beaconConfirmButton;
    private static final ResourceLocation beaconGuiTextures;
    private static final String[] I;
    
    static void access$1(final GuiBeacon guiBeacon, final String s, final int n, final int n2) {
        guiBeacon.drawCreativeTabHoveringText(s, n, n2);
    }
    
    public GuiBeacon(final InventoryPlayer inventoryPlayer, final IInventory tileBeacon) {
        super(new ContainerBeacon(inventoryPlayer, tileBeacon));
        this.tileBeacon = tileBeacon;
        this.xSize = 3 + 111 + 106 + 10;
        this.ySize = 151 + 93 - 174 + 149;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        beaconGuiTextures = new ResourceLocation(GuiBeacon.I["".length()]);
    }
    
    static ResourceLocation access$0() {
        return GuiBeacon.beaconGuiTextures;
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        RenderHelper.disableStandardItemLighting();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiBeacon.I["  ".length()], new Object["".length()]), 0x4 ^ 0x3A, 0x47 ^ 0x4D, 12048482 + 9642444 - 9411109 + 2457815);
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiBeacon.I["   ".length()], new Object["".length()]), 70 + 150 - 147 + 96, 0xA5 ^ 0xAF, 3943920 + 6001008 - 3832979 + 8625683);
        final Iterator<GuiButton> iterator = this.buttonList.iterator();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final GuiButton guiButton = iterator.next();
            if (guiButton.isMouseOver()) {
                guiButton.drawButtonForegroundLayer(n - this.guiLeft, n2 - this.guiTop);
                "".length();
                if (-1 == 4) {
                    throw null;
                }
                break;
            }
        }
        RenderHelper.enableGUIStandardItemLighting();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiBeacon.beaconGuiTextures);
        final int n4 = (this.width - this.xSize) / "  ".length();
        final int n5 = (this.height - this.ySize) / "  ".length();
        this.drawTexturedModalRect(n4, n5, "".length(), "".length(), this.xSize, this.ySize);
        this.itemRender.zLevel = 100.0f;
        this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.emerald), n4 + (0x3F ^ 0x15), n5 + (0x3E ^ 0x53));
        this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.diamond), n4 + (0x9A ^ 0xB0) + (0x7A ^ 0x6C), n5 + (0xC8 ^ 0xA5));
        this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.gold_ingot), n4 + (0xA1 ^ 0x8B) + (0x15 ^ 0x39), n5 + (0xFA ^ 0x97));
        this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.iron_ingot), n4 + (0xBF ^ 0x95) + (0xDD ^ 0x9F), n5 + (0xE ^ 0x63));
        this.itemRender.zLevel = 0.0f;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == -"  ".length()) {
            this.mc.displayGuiScreen(null);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (guiButton.id == -" ".length()) {
            final String s = GuiBeacon.I[" ".length()];
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.tileBeacon.getField(" ".length()));
            packetBuffer.writeInt(this.tileBeacon.getField("  ".length()));
            this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(s, packetBuffer));
            this.mc.displayGuiScreen(null);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (guiButton instanceof PowerButton) {
            if (((PowerButton)guiButton).func_146141_c()) {
                return;
            }
            final int id = guiButton.id;
            final int n = id & 191 + 95 - 145 + 114;
            if (id >> (0x73 ^ 0x7B) < "   ".length()) {
                this.tileBeacon.setField(" ".length(), n);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                this.tileBeacon.setField("  ".length(), n);
            }
            this.buttonList.clear();
            this.initGui();
            this.updateScreen();
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(this.beaconConfirmButton = new ConfirmButton(-" ".length(), this.guiLeft + (139 + 108 - 104 + 21), this.guiTop + (0x4 ^ 0x6F)));
        this.buttonList.add(new CancelButton(-"  ".length(), this.guiLeft + (101 + 174 - 237 + 152), this.guiTop + (0x36 ^ 0x5D)));
        this.buttonsNotDrawn = (" ".length() != 0);
        this.beaconConfirmButton.enabled = ("".length() != 0);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final int field = this.tileBeacon.getField("".length());
        final int field2 = this.tileBeacon.getField(" ".length());
        final int field3 = this.tileBeacon.getField("  ".length());
        if (this.buttonsNotDrawn && field >= 0) {
            this.buttonsNotDrawn = ("".length() != 0);
            int i = "".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
            while (i <= "  ".length()) {
                final int length = TileEntityBeacon.effectsList[i].length;
                final int n = length * (0xA0 ^ 0xB6) + (length - " ".length()) * "  ".length();
                int j = "".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                while (j < length) {
                    final int id = TileEntityBeacon.effectsList[i][j].id;
                    final PowerButton powerButton = new PowerButton(i << (0x65 ^ 0x6D) | id, this.guiLeft + (0x3 ^ 0x4F) + j * (0x8B ^ 0x93) - n / "  ".length(), this.guiTop + (0x8B ^ 0x9D) + i * (0x1C ^ 0x5), id, i);
                    this.buttonList.add(powerButton);
                    if (i >= field) {
                        powerButton.enabled = ("".length() != 0);
                        "".length();
                        if (-1 == 4) {
                            throw null;
                        }
                    }
                    else if (id == field2) {
                        powerButton.func_146140_b(" ".length() != 0);
                    }
                    ++j;
                }
                ++i;
            }
            final int length2 = "   ".length();
            final int n2 = TileEntityBeacon.effectsList[length2].length + " ".length();
            final int n3 = n2 * (0x45 ^ 0x53) + (n2 - " ".length()) * "  ".length();
            int k = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
            while (k < n2 - " ".length()) {
                final int id2 = TileEntityBeacon.effectsList[length2][k].id;
                final PowerButton powerButton2 = new PowerButton(length2 << (0x87 ^ 0x8F) | id2, this.guiLeft + (159 + 73 - 187 + 122) + k * (0x72 ^ 0x6A) - n3 / "  ".length(), this.guiTop + (0x44 ^ 0x6B), id2, length2);
                this.buttonList.add(powerButton2);
                if (length2 >= field) {
                    powerButton2.enabled = ("".length() != 0);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (id2 == field3) {
                    powerButton2.func_146140_b(" ".length() != 0);
                }
                ++k;
            }
            if (field2 > 0) {
                final PowerButton powerButton3 = new PowerButton(length2 << (0xB4 ^ 0xBC) | field2, this.guiLeft + (137 + 144 - 243 + 129) + (n2 - " ".length()) * (0x78 ^ 0x60) - n3 / "  ".length(), this.guiTop + (0x3D ^ 0x12), field2, length2);
                this.buttonList.add(powerButton3);
                if (length2 >= field) {
                    powerButton3.enabled = ("".length() != 0);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (field2 == field3) {
                    powerButton3.func_146140_b(" ".length() != 0);
                }
            }
        }
        final ConfirmButton beaconConfirmButton = this.beaconConfirmButton;
        int enabled;
        if (this.tileBeacon.getStackInSlot("".length()) != null && field2 > 0) {
            enabled = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        beaconConfirmButton.enabled = (enabled != 0);
    }
    
    private static void I() {
        (I = new String[0x22 ^ 0x26])["".length()] = I("\u0000&0=9\u0006&;f+\u0001*g*#\u001a7) \"\u00111g+)\u0015 ''b\u0004-/", "tCHIL");
        GuiBeacon.I[" ".length()] = I("\u000f1\u0011\t\u0003#\u0011\u0002%", "BrmKf");
        GuiBeacon.I["  ".length()] = I(",>\u001c\fC:2\u0011\n\u00026y\u0000\u001b\u000456\u0002\u0010", "XWpim");
        GuiBeacon.I["   ".length()] = I("\u0018\u0010'\u0007_\u000e\u001c*\u0001\u001e\u0002W8\u0007\u0012\u0003\u0017/\u0003\u0003\u0015", "lyKbq");
    }
    
    static class Button extends GuiButton
    {
        private final ResourceLocation field_146145_o;
        private boolean field_146142_r;
        private static final String[] I;
        private final int field_146143_q;
        private final int field_146144_p;
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                minecraft.getTextureManager().bindTexture(GuiBeacon.access$0());
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                int hovered;
                if (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height) {
                    hovered = " ".length();
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else {
                    hovered = "".length();
                }
                this.hovered = (hovered != 0);
                final int n3 = 48 + 167 - 31 + 35;
                int length = "".length();
                if (!this.enabled) {
                    length += this.width * "  ".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (this.field_146142_r) {
                    length += this.width * " ".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (this.hovered) {
                    length += this.width * "   ".length();
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, length, n3, this.width, this.height);
                if (!GuiBeacon.access$0().equals(this.field_146145_o)) {
                    minecraft.getTextureManager().bindTexture(this.field_146145_o);
                }
                this.drawTexturedModalRect(this.xPosition + "  ".length(), this.yPosition + "  ".length(), this.field_146144_p, this.field_146143_q, 0x3 ^ 0x11, 0xB9 ^ 0xAB);
            }
        }
        
        static {
            I();
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
                if (1 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("", "qBamk");
        }
        
        public void func_146140_b(final boolean field_146142_r) {
            this.field_146142_r = field_146142_r;
        }
        
        protected Button(final int n, final int n2, final int n3, final ResourceLocation field_146145_o, final int field_146144_p, final int field_146143_q) {
            super(n, n2, n3, 0xB6 ^ 0xA0, 0x69 ^ 0x7F, Button.I["".length()]);
            this.field_146145_o = field_146145_o;
            this.field_146144_p = field_146144_p;
            this.field_146143_q = field_146143_q;
        }
        
        public boolean func_146141_c() {
            return this.field_146142_r;
        }
    }
    
    class ConfirmButton extends Button
    {
        private static final String[] I;
        final GuiBeacon this$0;
        
        public ConfirmButton(final GuiBeacon this$0, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            super(n, n2, n3, GuiBeacon.access$0(), 0xD4 ^ 0x8E, 179 + 65 - 63 + 39);
        }
        
        static {
            I();
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
                if (4 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0006\u00000~\u0005\u000e\u001b<", "auYPa");
        }
        
        @Override
        public void drawButtonForegroundLayer(final int n, final int n2) {
            GuiBeacon.access$1(this.this$0, I18n.format(ConfirmButton.I["".length()], new Object["".length()]), n, n2);
        }
    }
    
    class PowerButton extends Button
    {
        private final int field_146149_p;
        final GuiBeacon this$0;
        private final int field_146148_q;
        private static final String[] I;
        
        @Override
        public void drawButtonForegroundLayer(final int n, final int n2) {
            String s = I18n.format(Potion.potionTypes[this.field_146149_p].getName(), new Object["".length()]);
            if (this.field_146148_q >= "   ".length() && this.field_146149_p != Potion.regeneration.id) {
                s = String.valueOf(s) + PowerButton.I["".length()];
            }
            GuiBeacon.access$1(this.this$0, s, n, n2);
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
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
        }
        
        public PowerButton(final GuiBeacon this$0, final int n, final int n2, final int n3, final int field_146149_p, final int field_146148_q) {
            this.this$0 = this$0;
            super(n, n2, n3, GuiContainer.inventoryBackground, "".length() + Potion.potionTypes[field_146149_p].getStatusIconIndex() % (0x8E ^ 0x86) * (0x46 ^ 0x54), 30 + 140 - 55 + 83 + Potion.potionTypes[field_146149_p].getStatusIconIndex() / (0x31 ^ 0x39) * (0x61 ^ 0x73));
            this.field_146149_p = field_146149_p;
            this.field_146148_q = field_146148_q;
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("S?\u0007", "svNfp");
        }
    }
    
    class CancelButton extends Button
    {
        private static final String[] I;
        final GuiBeacon this$0;
        
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
                if (1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void drawButtonForegroundLayer(final int n, final int n2) {
            GuiBeacon.access$1(this.this$0, I18n.format(CancelButton.I["".length()], new Object["".length()]), n, n2);
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("6\u001e\u001dc&0\u0005\u0017()", "QktME");
        }
        
        public CancelButton(final GuiBeacon this$0, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            super(n, n2, n3, GuiBeacon.access$0(), 0x1A ^ 0x6A, 32 + 85 - 103 + 206);
        }
        
        static {
            I();
        }
    }
}
