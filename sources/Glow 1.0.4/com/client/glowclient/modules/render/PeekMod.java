package com.client.glowclient.modules.render;

import com.client.glowclient.gui.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import com.client.glowclient.events.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import java.util.concurrent.atomic.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.input.*;
import java.util.function.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.inventory.*;
import com.client.glowclient.modules.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import java.util.concurrent.locks.*;
import java.util.*;
import com.client.glowclient.*;
import com.client.glowclient.utils.*;

public class PeekMod extends ModuleContainer
{
    private final Lock h;
    private static final int D = 1;
    public static BooleanValue rightClick;
    private boolean F;
    public static final ResourceLocation I;
    private int e;
    private int a;
    private final int i = 29;
    private static final int g = 76;
    private static final int K = 0;
    private boolean c;
    private final List<GuiPeek> k;
    private static final Minecraft H;
    private int f;
    private boolean M;
    private int G;
    private static final int d = 2;
    private boolean L;
    public static BooleanValue toolTips;
    private static Block[] B;
    private boolean b;
    
    private boolean e() {
        return this.L && this.b;
    }
    
    public static boolean M(final PeekMod peekMod) {
        return peekMod.e();
    }
    
    @SubscribeEvent
    public void M(final RenderTooltipEvent$Pre renderTooltipEvent$Pre) {
        if (PeekMod.toolTips.M()) {
            if (!(PeekMod.H.currentScreen instanceof GuiContainer) || this.c) {
                return;
            }
            if (this.M) {
                renderTooltipEvent$Pre.setCanceled(true);
                return;
            }
            if (renderTooltipEvent$Pre.getStack().getItem() instanceof ItemShulkerBox) {
                renderTooltipEvent$Pre.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void M(final GuiOpenEvent guiOpenEvent) {
        if (guiOpenEvent.getGui() == null) {
            this.d();
        }
    }
    
    private static boolean M(final Block block) {
        final Block[] b;
        final int length = (b = PeekMod.B).length;
        int n;
        int i = n = 0;
        while (i < length) {
            if (b[n] == block) {
                return true;
            }
            i = ++n;
        }
        return false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (PeekMod.rightClick.M() && PeekMod.H.gameSettings.keyBindUseItem.isKeyDown()) {
            ItemStack itemStack = null;
            if (!PeekMod.H.player.getHeldItemOffhand().isEmpty()) {
                itemStack = PeekMod.H.player.getHeldItemOffhand();
            }
            if (!PeekMod.H.player.getHeldItemMainhand().isEmpty()) {
                itemStack = PeekMod.H.player.getHeldItemMainhand();
            }
            if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() instanceof ItemBlock && M(((ItemBlock)itemStack.getItem()).getBlock())) {
                if (itemStack.hasTagCompound()) {
                    PeekMod.H.displayGuiScreen((GuiScreen)new GuiChest((IInventory)PeekMod.H.player.inventory, (IInventory)M(itemStack.getTagCompound().getCompoundTag("BlockEntityTag"))));
                    return;
                }
                PeekMod.H.displayGuiScreen((GuiScreen)new GuiChest((IInventory)PeekMod.H.player.inventory, (IInventory)new InventoryBasic("Shulker Box", true, 27)));
            }
        }
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    public static void M(final NBTTagCompound nbtTagCompound, final NonNullList<ItemStack> list) {
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        int n;
        int i = n = 0;
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag;
            final NBTTagCompound nbtTagCompound2 = compoundTag = tagList.getCompoundTagAt(n);
            final int n2 = nbtTagCompound2.getByte("Slot") & 0xFF;
            final int integer = nbtTagCompound2.getInteger("Damage");
            if (n2 >= 0 && n2 < list.size()) {
                final ItemStack itemStack = new ItemStack(compoundTag);
                list.set(n2, (Object)itemStack);
                itemStack.itemDamage = integer;
            }
            i = ++n;
        }
    }
    
    private boolean M(final int n, @Nullable final GuiPeek guiPeek) {
        if (n < 0) {
            return false;
        }
        if (guiPeek == null && n > 1 && n == this.k.size() - 1) {
            this.k.remove(n);
            final int n2 = n - 1;
            return n2 <= 1 || this.M(n2).isPresent() || this.M(n2, null);
        }
        if (n > this.k.size() - 1) {
            int max;
            int i = max = Math.max(this.k.size(), 1);
            while (i < n) {
                this.k.add(max++, null);
                i = max;
            }
            this.k.add(n, guiPeek);
            return true;
        }
        this.k.set(n, guiPeek);
        return true;
    }
    
    @Override
    public void E() {
        this.D();
    }
    
    private void a() {
        int n;
        int i = n = 0;
        while (i < 2) {
            this.M(n++, null);
            i = n;
        }
        PeekMod peekMod = this;
        while (peekMod.k.size() > 2) {
            peekMod = this;
            this.M(this.k.size() - 1, null);
        }
    }
    
    private static void M(final AtomicInteger atomicInteger, final AtomicInteger atomicInteger2, final GuiScreenEvent$DrawScreenEvent$Post guiScreenEvent$DrawScreenEvent$Post, final GuiPeek guiPeek) {
        guiPeek.b = atomicInteger.get();
        guiPeek.B = atomicInteger2.get();
        guiPeek.drawScreen(guiScreenEvent$DrawScreenEvent$Post.getMouseX(), guiScreenEvent$DrawScreenEvent$Post.getMouseY(), guiScreenEvent$DrawScreenEvent$Post.getRenderPartialTicks());
        atomicInteger2.set(atomicInteger2.get() + 76 + 1);
    }
    
    private void d() {
        final boolean l = false;
        this.c = l;
        this.M = l;
        this.F = l;
        this.b = l;
        this.L = l;
        final int n = -1;
        this.f = n;
        this.e = n;
        this.a();
    }
    
    @SubscribeEvent
    public void M(final GuiScreenEvent$KeyboardInputEvent guiScreenEvent$KeyboardInputEvent) {
        if (Keyboard.getEventKey() == 29) {
            if (Keyboard.getEventKeyState()) {
                this.L = true;
                return;
            }
            final boolean b = false;
            this.b = b;
            this.L = b;
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void M(final GuiScreenEvent$DrawScreenEvent$Post guiScreenEvent$DrawScreenEvent$Post) {
        if (PeekMod.toolTips.M()) {
            if (!(PeekMod.H.currentScreen instanceof GuiContainer)) {
                return;
            }
            this.h.lock();
            try {
                final GuiContainer guiContainer = (GuiContainer)guiScreenEvent$DrawScreenEvent$Post.getGui();
                if (!this.e()) {
                    final Slot slotUnderMouse;
                    if ((slotUnderMouse = guiContainer.getSlotUnderMouse()) == null || !slotUnderMouse.getHasStack() || slotUnderMouse.getStack().isEmpty() || !(slotUnderMouse.getStack().getItem() instanceof ItemShulkerBox)) {
                        this.M(0, null);
                    }
                    else if (!ItemStack.areItemStacksEqual((ItemStack)this.M(0).map((Function<? super Ce, ? extends ItemStack>)Ce::getParentShulker).orElse(ItemStack.EMPTY), slotUnderMouse.getStack())) {
                        this.M(0, this.M(slotUnderMouse.getStack(), 1));
                    }
                    final ItemStack itemStack;
                    PeekMod peekMod;
                    if ((itemStack = P.M().getItemStack()).isEmpty() || !(itemStack.getItem() instanceof ItemShulkerBox)) {
                        peekMod = this;
                        this.M(1, null);
                    }
                    else {
                        if (!ItemStack.areItemStacksEqual((ItemStack)this.M(1).map((Function<? super Ce, ? extends ItemStack>)Ce::getParentShulker).orElse(ItemStack.EMPTY), itemStack)) {
                            this.M(1, this.M(itemStack, 0));
                        }
                        peekMod = this;
                    }
                    if (peekMod.L && !this.b && this.k.stream().anyMatch(Objects::nonNull)) {
                        this.b = true;
                    }
                }
                AtomicInteger atomicInteger;
                AtomicInteger atomicInteger2;
                PeekMod peekMod2;
                if (!this.e() || (this.e == -1 && this.f == -1)) {
                    final int n = (int)this.k.stream().filter(Objects::nonNull).count();
                    final int e = guiScreenEvent$DrawScreenEvent$Post.getMouseX() + this.a;
                    this.e = e;
                    atomicInteger = new AtomicInteger(e);
                    final int f = guiScreenEvent$DrawScreenEvent$Post.getMouseY() - 76 * n / 2 + this.G;
                    this.f = f;
                    atomicInteger2 = new AtomicInteger(f);
                    peekMod2 = this;
                }
                else {
                    atomicInteger = new AtomicInteger(this.e);
                    atomicInteger2 = new AtomicInteger(this.f);
                    peekMod2 = this;
                }
                peekMod2.M = false;
                this.k.stream().filter(Objects::nonNull).sorted().forEach((Consumer<? super Object>)Wd::M);
                this.h.unlock();
            }
            finally {
                this.h.unlock();
            }
            GlStateManager.enableLighting();
            final float n2 = 1.0f;
            GlStateManager.color(n2, n2, n2, n2);
        }
    }
    
    private GuiPeek M(final ItemStack itemStack, final int n) {
        return new GuiPeek(this, new Eg(new PF(this.M(itemStack)), 27), itemStack, n);
    }
    
    @Override
    public void D() {
        this.h.lock();
        try {
            this.d();
            this.h.unlock();
        }
        finally {
            this.h.unlock();
        }
    }
    
    public PeekMod() {
        final int n = 16;
        super(Category.RENDER, "PeekMod", false, -1, "Get a view into shulker boxes without placing them (Thanks Fr1kin for the tooltips code)");
        final Block[] b = new Block[n];
        b[0] = Blocks.BLACK_SHULKER_BOX;
        b[1] = Blocks.BLUE_SHULKER_BOX;
        b[2] = Blocks.BROWN_SHULKER_BOX;
        b[3] = Blocks.CYAN_SHULKER_BOX;
        b[4] = Blocks.GRAY_SHULKER_BOX;
        b[5] = Blocks.GREEN_SHULKER_BOX;
        b[6] = Blocks.LIGHT_BLUE_SHULKER_BOX;
        b[7] = Blocks.LIME_SHULKER_BOX;
        b[8] = Blocks.MAGENTA_SHULKER_BOX;
        b[9] = Blocks.ORANGE_SHULKER_BOX;
        b[10] = Blocks.PINK_SHULKER_BOX;
        b[11] = Blocks.PURPLE_SHULKER_BOX;
        b[12] = Blocks.RED_SHULKER_BOX;
        b[13] = Blocks.SILVER_SHULKER_BOX;
        b[14] = Blocks.WHITE_SHULKER_BOX;
        b[15] = Blocks.YELLOW_SHULKER_BOX;
        PeekMod.B = b;
        final int f = -1;
        final int e = -1;
        final boolean c = false;
        final boolean b2 = false;
        final boolean b3 = false;
        final int n2 = 2;
        final int i = 29;
        final int g = -36;
        this.a = 0;
        this.G = g;
        this.k = (List<GuiPeek>)Lists.newArrayListWithExpectedSize(n2);
        this.h = new ReentrantLock();
        this.L = b3;
        this.b = b3;
        this.F = b2;
        this.M = b2;
        this.c = c;
        this.e = e;
        this.f = f;
    }
    
    private static InventoryBasic M(final NBTTagCompound nbtTagCompound) {
        final NonNullList withSize = NonNullList.withSize(27, (Object)ItemStack.EMPTY);
        String string = "Shulker Box";
        if (nbtTagCompound.hasKey("Items", 9)) {
            M(nbtTagCompound, (NonNullList<ItemStack>)withSize);
        }
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            string = nbtTagCompound.getString("CustomName");
        }
        final InventoryBasic inventoryBasic = new InventoryBasic(string, true, withSize.size());
        int n;
        int i = n = 0;
        while (i < withSize.size()) {
            final InventoryBasic inventoryBasic2 = inventoryBasic;
            final NonNullList<ItemStack> list = (NonNullList<ItemStack>)withSize;
            final int n2 = n++;
            inventoryBasic2.setInventorySlotContents(n2, (ItemStack)list.get(n2));
            i = n;
        }
        return inventoryBasic;
    }
    
    private Optional<Ce> M(final int n) {
        if (Ob.M(this.k, n)) {
            return Optional.ofNullable((Ce)this.k.get(n));
        }
        return Optional.empty();
    }
    
    public static boolean D(final PeekMod peekMod, final boolean c) {
        return peekMod.c = c;
    }
    
    static {
        PeekMod.rightClick = ValueFactory.M("PeekMod", "RightClick", "Right click a held shulker to view its inventory", true);
        PeekMod.toolTips = ValueFactory.M("PeekMod", "ToolTips", "Shows shulker inventory when hovered over", true);
        H = Minecraft.getMinecraft();
        I = new ResourceLocation("textures/gui/container/shulker_box.png");
    }
    
    public static boolean M(final PeekMod peekMod, final boolean m) {
        return peekMod.M = m;
    }
    
    private List<ItemStack> M(final ItemStack itemStack) {
        final NonNullList withSize = NonNullList.withSize(27, (Object)ItemStack.EMPTY);
        final NBTTagCompound tagCompound;
        final NBTTagCompound compoundTag;
        if ((tagCompound = itemStack.getTagCompound()) != null && tagCompound.hasKey("BlockEntityTag", 10) && (compoundTag = tagCompound.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
            M(compoundTag, (NonNullList<ItemStack>)withSize);
        }
        return (List<ItemStack>)withSize;
    }
    
    public static Minecraft M() {
        return PeekMod.H;
    }
}
