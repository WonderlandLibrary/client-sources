/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class InventoryScreen
extends DisplayEffectsScreen<PlayerContainer>
implements IRecipeShownListener,
IMinecraft {
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
    private float oldMouseX;
    private float oldMouseY;
    private final RecipeBookGui recipeBookGui = new RecipeBookGui();
    private boolean removeRecipeBookGui;
    private boolean widthTooNarrow;
    private boolean buttonClicked;

    public InventoryScreen(PlayerEntity playerEntity) {
        super(playerEntity.container, playerEntity.inventory, new TranslationTextComponent("container.crafting"));
        this.passEvents = true;
        this.titleX = 97;
    }

    @Override
    public void tick() {
        if (this.minecraft.playerController.isInCreativeMode()) {
            this.minecraft.displayGuiScreen(new CreativeScreen(this.minecraft.player));
        } else {
            this.recipeBookGui.tick();
        }
    }

    @Override
    protected void init() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        if (this.minecraft.playerController.isInCreativeMode()) {
            this.minecraft.displayGuiScreen(new CreativeScreen(this.minecraft.player));
        } else {
            super.init();
            this.addButton(new Button(this.width / 2 - 50, this.height / 2 - 125, 100, 20, new StringTextComponent("\u0412\u044b\u0431\u0440\u043e\u0441\u0438\u0442\u044c \u0432\u0441\u0451"), this::lambda$init$0));
            this.widthTooNarrow = this.width < 379;
            this.recipeBookGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow, (RecipeBookContainer)this.container);
            this.removeRecipeBookGui = true;
            this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
            this.children.add(this.recipeBookGui);
            this.setFocusedDefault(this.recipeBookGui);
            this.addButton(new ImageButton(this.guiLeft + 104, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, this::lambda$init$1));
        }
    }

    public void dropItems() {
        for (int i = 0; i < ((PlayerContainer)this.container).getInventory().size(); ++i) {
            if (InventoryScreen.mc.currentScreen != this) break;
            InventoryScreen.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, InventoryScreen.mc.player);
            InventoryScreen.mc.playerController.windowClick(0, -999, 0, ClickType.PICKUP, InventoryScreen.mc.player);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int n, int n2) {
        this.font.func_243248_b(matrixStack, this.title, this.titleX, this.titleY, 0x404040);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        boolean bl = this.hasActivePotionEffects = !this.recipeBookGui.isVisible();
        if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
            this.drawGuiContainerBackgroundLayer(matrixStack, f, n, n2);
            this.recipeBookGui.render(matrixStack, n, n2, f);
        } else {
            this.recipeBookGui.render(matrixStack, n, n2, f);
            super.render(matrixStack, n, n2, f);
            this.recipeBookGui.func_230477_a_(matrixStack, this.guiLeft, this.guiTop, false, f);
        }
        this.renderHoveredTooltip(matrixStack, n, n2);
        this.recipeBookGui.func_238924_c_(matrixStack, this.guiLeft, this.guiTop, n, n2);
        this.oldMouseX = n;
        this.oldMouseY = n2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int n3 = this.guiLeft;
        int n4 = this.guiTop;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        InventoryScreen.drawEntityOnScreen(n3 + 51, n4 + 75, 30, (float)(n3 + 51) - this.oldMouseX, (float)(n4 + 75 - 50) - this.oldMouseY, this.minecraft.player);
    }

    public static void drawEntityOnScreen(int n, int n2, int n3, float f, float f2, LivingEntity livingEntity) {
        float f3 = (float)Math.atan(f / 40.0f);
        float f4 = (float)Math.atan(f2 / 40.0f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(n, n2, 1050.0f);
        RenderSystem.scalef(1.0f, 1.0f, -1.0f);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(0.0, 0.0, 1000.0);
        matrixStack.scale(n3, n3, n3);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0f);
        Quaternion quaternion2 = Vector3f.XP.rotationDegrees(f4 * 20.0f);
        quaternion.multiply(quaternion2);
        matrixStack.rotate(quaternion);
        float f5 = livingEntity.renderYawOffset;
        float f6 = livingEntity.rotationYaw;
        float f7 = livingEntity.rotationPitch;
        float f8 = livingEntity.prevRotationYawHead;
        float f9 = livingEntity.rotationYawHead;
        livingEntity.renderYawOffset = 180.0f + f3 * 20.0f;
        livingEntity.rotationYaw = 180.0f + f3 * 40.0f;
        livingEntity.rotationPitch = -f4 * 20.0f;
        livingEntity.rotationYawHead = livingEntity.rotationYaw;
        livingEntity.prevRotationYawHead = livingEntity.rotationYaw;
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        quaternion2.conjugate();
        entityRendererManager.setCameraOrientation(quaternion2);
        entityRendererManager.setRenderShadow(true);
        IRenderTypeBuffer.Impl impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        RenderSystem.runAsFancy(() -> InventoryScreen.lambda$drawEntityOnScreen$2(entityRendererManager, livingEntity, matrixStack, impl));
        impl.finish();
        entityRendererManager.setRenderShadow(false);
        livingEntity.renderYawOffset = f5;
        livingEntity.rotationYaw = f6;
        livingEntity.rotationPitch = f7;
        livingEntity.prevRotationYawHead = f8;
        livingEntity.rotationYawHead = f9;
        RenderSystem.popMatrix();
    }

    @Override
    protected boolean isPointInRegion(int n, int n2, int n3, int n4, double d, double d2) {
        return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isPointInRegion(n, n2, n3, n4, d, d2);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.recipeBookGui.mouseClicked(d, d2, n)) {
            this.setListener(this.recipeBookGui);
            return false;
        }
        return this.widthTooNarrow && this.recipeBookGui.isVisible() ? false : super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return false;
        }
        return super.mouseReleased(d, d2, n);
    }

    @Override
    protected boolean hasClickedOutside(double d, double d2, int n, int n2, int n3) {
        boolean bl = d < (double)n || d2 < (double)n2 || d >= (double)(n + this.xSize) || d2 >= (double)(n2 + this.ySize);
        return this.recipeBookGui.func_195604_a(d, d2, this.guiLeft, this.guiTop, this.xSize, this.ySize, n3) && bl;
    }

    @Override
    protected void handleMouseClick(Slot slot, int n, int n2, ClickType clickType) {
        super.handleMouseClick(slot, n, n2, clickType);
        this.recipeBookGui.slotClicked(slot);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookGui.recipesUpdated();
    }

    @Override
    public void onClose() {
        if (this.removeRecipeBookGui) {
            this.recipeBookGui.removed();
        }
        super.onClose();
    }

    @Override
    public RecipeBookGui getRecipeGui() {
        return this.recipeBookGui;
    }

    private static void lambda$drawEntityOnScreen$2(EntityRendererManager entityRendererManager, LivingEntity livingEntity, MatrixStack matrixStack, IRenderTypeBuffer.Impl impl) {
        entityRendererManager.renderEntityStatic(livingEntity, 0.0, 0.0, 0.0, 0.0f, 1.0f, matrixStack, impl, 0xF000F0);
    }

    private void lambda$init$1(Button button) {
        this.recipeBookGui.initSearchBar(this.widthTooNarrow);
        this.recipeBookGui.toggleVisibility();
        this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
        ((ImageButton)button).setPosition(this.guiLeft + 104, this.height / 2 - 22);
        this.buttonClicked = true;
    }

    private void lambda$init$0(Button button) {
        if (InventoryScreen.mc.player != null && InventoryScreen.mc.playerController != null) {
            this.dropItems();
        }
    }
}

