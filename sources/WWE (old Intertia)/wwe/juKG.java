package wwe;

import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import wwe.modules.render.*;
import java.util.*;
import java.io.*;

public class juKG extends GuiScreen
{
    public juKG() {
        super();
    }
    
    public void func_73866_w_() {
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawBackground(1);
        final NonNullList create = NonNullList.create();
        for (final Block block : Block.REGISTRY) {
            if (block != Blocks.AIR) {
                block.getSubBlocks((CreativeTabs)null, create);
            }
            if (juKG.\u2000\u200c\u2003\u200a) {
                throw null;
            }
        }
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        final int n7 = n4;
        for (final ItemStack itemStack : create) {
            RenderHelper.enableGUIStandardItemLighting();
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n4, n6);
            if (Xray.fNgC(Block.getBlockFromItem(itemStack.getItem()))) {
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n4 + 10, n6 + 1);
            }
            RenderHelper.disableStandardItemLighting();
            n4 += 20;
            if (++n5 % 30 == 0) {
                n6 += 20;
                n4 = n7;
            }
            if (juKG.\u2000\u200c\u2003\u200a) {
                throw null;
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73876_c() {
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    public void func_73869_a(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
    }
}
