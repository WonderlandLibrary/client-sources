/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.recipebook.GhostRecipe;
import net.minecraft.client.gui.recipebook.IRecipeUpdateListener;
import net.minecraft.client.gui.recipebook.RecipeBookPage;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.recipebook.RecipeTabToggleWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.client.CUpdateRecipeBookStatusPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class RecipeBookGui
extends AbstractGui
implements IRenderable,
IGuiEventListener,
IRecipeUpdateListener,
IRecipePlacer<Ingredient> {
    protected static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_book.png");
    private static final ITextComponent field_241620_l_ = new TranslationTextComponent("gui.recipebook.search_hint").mergeStyle(TextFormatting.ITALIC).mergeStyle(TextFormatting.GRAY);
    private static final ITextComponent field_243410_j = new TranslationTextComponent("gui.recipebook.toggleRecipes.craftable");
    private static final ITextComponent field_243411_k = new TranslationTextComponent("gui.recipebook.toggleRecipes.all");
    private int xOffset;
    private int width;
    private int height;
    protected final GhostRecipe ghostRecipe = new GhostRecipe();
    private final List<RecipeTabToggleWidget> recipeTabs = Lists.newArrayList();
    private RecipeTabToggleWidget currentTab;
    protected ToggleWidget toggleRecipesBtn;
    protected RecipeBookContainer<?> field_201522_g;
    protected Minecraft mc;
    private TextFieldWidget searchBar;
    private String lastSearch = "";
    private ClientRecipeBook recipeBook;
    private final RecipeBookPage recipeBookPage = new RecipeBookPage();
    private final RecipeItemHelper stackedContents = new RecipeItemHelper();
    private int timesInventoryChanged;
    private boolean field_199738_u;

    public void init(int n, int n2, Minecraft minecraft, boolean bl, RecipeBookContainer<?> recipeBookContainer) {
        this.mc = minecraft;
        this.width = n;
        this.height = n2;
        this.field_201522_g = recipeBookContainer;
        minecraft.player.openContainer = recipeBookContainer;
        this.recipeBook = minecraft.player.getRecipeBook();
        this.timesInventoryChanged = minecraft.player.inventory.getTimesChanged();
        if (this.isVisible()) {
            this.initSearchBar(bl);
        }
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    public void initSearchBar(boolean bl) {
        this.xOffset = bl ? 0 : 86;
        int n = (this.width - 147) / 2 - this.xOffset;
        int n2 = (this.height - 166) / 2;
        this.stackedContents.clear();
        this.mc.player.inventory.accountStacks(this.stackedContents);
        this.field_201522_g.fillStackedContents(this.stackedContents);
        String string = this.searchBar != null ? this.searchBar.getText() : "";
        this.searchBar = new TextFieldWidget(this.mc.fontRenderer, n + 25, n2 + 14, 80, 14, new TranslationTextComponent("itemGroup.search"));
        this.searchBar.setMaxStringLength(50);
        this.searchBar.setEnableBackgroundDrawing(true);
        this.searchBar.setVisible(false);
        this.searchBar.setTextColor(0xFFFFFF);
        this.searchBar.setText(string);
        this.recipeBookPage.init(this.mc, n, n2);
        this.recipeBookPage.addListener(this);
        this.toggleRecipesBtn = new ToggleWidget(n + 110, n2 + 12, 26, 16, this.recipeBook.func_242141_a(this.field_201522_g));
        this.func_205702_a();
        this.recipeTabs.clear();
        for (RecipeBookCategories recipeBookCategories : RecipeBookCategories.func_243236_a(this.field_201522_g.func_241850_m())) {
            this.recipeTabs.add(new RecipeTabToggleWidget(recipeBookCategories));
        }
        if (this.currentTab != null) {
            this.currentTab = this.recipeTabs.stream().filter(this::lambda$initSearchBar$0).findFirst().orElse(null);
        }
        if (this.currentTab == null) {
            this.currentTab = this.recipeTabs.get(0);
        }
        this.currentTab.setStateTriggered(false);
        this.updateCollections(true);
        this.updateTabs();
    }

    @Override
    public boolean changeFocus(boolean bl) {
        return true;
    }

    protected void func_205702_a() {
        this.toggleRecipesBtn.initTextureValues(152, 41, 28, 18, RECIPE_BOOK);
    }

    public void removed() {
        this.searchBar = null;
        this.currentTab = null;
        this.mc.keyboardListener.enableRepeatEvents(true);
    }

    public int updateScreenPosition(boolean bl, int n, int n2) {
        int n3 = this.isVisible() && !bl ? 177 + (n - n2 - 200) / 2 : (n - n2) / 2;
        return n3;
    }

    public void toggleVisibility() {
        this.setVisible(!this.isVisible());
    }

    public boolean isVisible() {
        return this.recipeBook.func_242142_a(this.field_201522_g.func_241850_m());
    }

    protected void setVisible(boolean bl) {
        this.recipeBook.func_242143_a(this.field_201522_g.func_241850_m(), bl);
        if (!bl) {
            this.recipeBookPage.setInvisible();
        }
        this.sendUpdateSettings();
    }

    public void slotClicked(@Nullable Slot slot) {
        if (slot != null && slot.slotNumber < this.field_201522_g.getSize()) {
            this.ghostRecipe.clear();
            if (this.isVisible()) {
                this.updateStackedContents();
            }
        }
    }

    private void updateCollections(boolean bl) {
        List<RecipeList> list = this.recipeBook.getRecipes(this.currentTab.func_201503_d());
        list.forEach(this::lambda$updateCollections$1);
        ArrayList<RecipeList> arrayList = Lists.newArrayList(list);
        arrayList.removeIf(RecipeBookGui::lambda$updateCollections$2);
        arrayList.removeIf(RecipeBookGui::lambda$updateCollections$3);
        String string = this.searchBar.getText();
        if (!string.isEmpty()) {
            ObjectLinkedOpenHashSet objectLinkedOpenHashSet = new ObjectLinkedOpenHashSet(this.mc.getSearchTree(SearchTreeManager.RECIPES).search(string.toLowerCase(Locale.ROOT)));
            arrayList.removeIf(arg_0 -> RecipeBookGui.lambda$updateCollections$4(objectLinkedOpenHashSet, arg_0));
        }
        if (this.recipeBook.func_242141_a(this.field_201522_g)) {
            arrayList.removeIf(RecipeBookGui::lambda$updateCollections$5);
        }
        this.recipeBookPage.updateLists(arrayList, bl);
    }

    private void updateTabs() {
        int n = (this.width - 147) / 2 - this.xOffset - 30;
        int n2 = (this.height - 166) / 2 + 3;
        int n3 = 27;
        int n4 = 0;
        for (RecipeTabToggleWidget recipeTabToggleWidget : this.recipeTabs) {
            RecipeBookCategories recipeBookCategories = recipeTabToggleWidget.func_201503_d();
            if (recipeBookCategories != RecipeBookCategories.CRAFTING_SEARCH && recipeBookCategories != RecipeBookCategories.FURNACE_SEARCH) {
                if (!recipeTabToggleWidget.func_199500_a(this.recipeBook)) continue;
                recipeTabToggleWidget.setPosition(n, n2 + 27 * n4++);
                recipeTabToggleWidget.startAnimation(this.mc);
                continue;
            }
            recipeTabToggleWidget.visible = true;
            recipeTabToggleWidget.setPosition(n, n2 + 27 * n4++);
        }
    }

    public void tick() {
        if (this.isVisible()) {
            if (this.timesInventoryChanged != this.mc.player.inventory.getTimesChanged()) {
                this.updateStackedContents();
                this.timesInventoryChanged = this.mc.player.inventory.getTimesChanged();
            }
            this.searchBar.tick();
        }
    }

    private void updateStackedContents() {
        this.stackedContents.clear();
        this.mc.player.inventory.accountStacks(this.stackedContents);
        this.field_201522_g.fillStackedContents(this.stackedContents);
        this.updateCollections(true);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, 0.0f, 100.0f);
            this.mc.getTextureManager().bindTexture(RECIPE_BOOK);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int n3 = (this.width - 147) / 2 - this.xOffset;
            int n4 = (this.height - 166) / 2;
            this.blit(matrixStack, n3, n4, 1, 1, 147, 166);
            if (!this.searchBar.isFocused() && this.searchBar.getText().isEmpty()) {
                RecipeBookGui.drawString(matrixStack, this.mc.fontRenderer, field_241620_l_, n3 + 25, n4 + 14, -1);
            } else {
                this.searchBar.render(matrixStack, n, n2, f);
            }
            for (RecipeTabToggleWidget recipeTabToggleWidget : this.recipeTabs) {
                recipeTabToggleWidget.render(matrixStack, n, n2, f);
            }
            this.toggleRecipesBtn.render(matrixStack, n, n2, f);
            this.recipeBookPage.func_238927_a_(matrixStack, n3, n4, n, n2, f);
            RenderSystem.popMatrix();
        }
    }

    public void func_238924_c_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        if (this.isVisible()) {
            this.recipeBookPage.func_238926_a_(matrixStack, n3, n4);
            if (this.toggleRecipesBtn.isHovered()) {
                ITextComponent iTextComponent = this.func_230478_f_();
                if (this.mc.currentScreen != null) {
                    this.mc.currentScreen.renderTooltip(matrixStack, iTextComponent, n3, n4);
                }
            }
            this.func_238925_d_(matrixStack, n, n2, n3, n4);
        }
    }

    private ITextComponent func_230478_f_() {
        return this.toggleRecipesBtn.isStateTriggered() ? this.func_230479_g_() : field_243411_k;
    }

    protected ITextComponent func_230479_g_() {
        return field_243410_j;
    }

    private void func_238925_d_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        ItemStack itemStack = null;
        for (int i = 0; i < this.ghostRecipe.size(); ++i) {
            GhostRecipe.GhostIngredient ghostIngredient = this.ghostRecipe.get(i);
            int n5 = ghostIngredient.getX() + n;
            int n6 = ghostIngredient.getY() + n2;
            if (n3 < n5 || n4 < n6 || n3 >= n5 + 16 || n4 >= n6 + 16) continue;
            itemStack = ghostIngredient.getItem();
        }
        if (itemStack != null && this.mc.currentScreen != null) {
            this.mc.currentScreen.func_243308_b(matrixStack, this.mc.currentScreen.getTooltipFromItem(itemStack), n3, n4);
        }
    }

    public void func_230477_a_(MatrixStack matrixStack, int n, int n2, boolean bl, float f) {
        this.ghostRecipe.func_238922_a_(matrixStack, this.mc, n, n2, bl, f);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.recipeBookPage.func_198955_a(d, d2, n, (this.width - 147) / 2 - this.xOffset, (this.height - 166) / 2, 147, 1)) {
                IRecipe<?> iRecipe = this.recipeBookPage.getLastClickedRecipe();
                RecipeList recipeList = this.recipeBookPage.getLastClickedRecipeList();
                if (iRecipe != null && recipeList != null) {
                    if (!recipeList.isCraftable(iRecipe) && this.ghostRecipe.getRecipe() == iRecipe) {
                        return true;
                    }
                    this.ghostRecipe.clear();
                    this.mc.playerController.sendPlaceRecipePacket(this.mc.player.openContainer.windowId, iRecipe, Screen.hasShiftDown());
                    if (!this.isOffsetNextToMainGUI()) {
                        this.setVisible(true);
                    }
                }
                return false;
            }
            if (this.searchBar.mouseClicked(d, d2, n)) {
                return false;
            }
            if (this.toggleRecipesBtn.mouseClicked(d, d2, n)) {
                boolean bl = this.toggleCraftableFilter();
                this.toggleRecipesBtn.setStateTriggered(bl);
                this.sendUpdateSettings();
                this.updateCollections(true);
                return false;
            }
            for (RecipeTabToggleWidget recipeTabToggleWidget : this.recipeTabs) {
                if (!recipeTabToggleWidget.mouseClicked(d, d2, n)) continue;
                if (this.currentTab != recipeTabToggleWidget) {
                    this.currentTab.setStateTriggered(true);
                    this.currentTab = recipeTabToggleWidget;
                    this.currentTab.setStateTriggered(false);
                    this.updateCollections(false);
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private boolean toggleCraftableFilter() {
        RecipeBookCategory recipeBookCategory = this.field_201522_g.func_241850_m();
        boolean bl = !this.recipeBook.func_242145_b(recipeBookCategory);
        this.recipeBook.func_242146_b(recipeBookCategory, bl);
        return bl;
    }

    public boolean func_195604_a(double d, double d2, int n, int n2, int n3, int n4, int n5) {
        if (!this.isVisible()) {
            return false;
        }
        boolean bl = d < (double)n || d2 < (double)n2 || d >= (double)(n + n3) || d2 >= (double)(n2 + n4);
        boolean bl2 = (double)(n - 147) < d && d < (double)n && (double)n2 < d2 && d2 < (double)(n2 + n4);
        return bl && !bl2 && !this.currentTab.isHovered();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        this.field_199738_u = false;
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (n == 256 && !this.isOffsetNextToMainGUI()) {
                this.setVisible(true);
                return false;
            }
            if (this.searchBar.keyPressed(n, n2, n3)) {
                this.updateSearch();
                return false;
            }
            if (this.searchBar.isFocused() && this.searchBar.getVisible() && n != 256) {
                return false;
            }
            if (this.mc.gameSettings.keyBindChat.matchesKey(n, n2) && !this.searchBar.isFocused()) {
                this.field_199738_u = true;
                this.searchBar.setFocused2(false);
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean keyReleased(int n, int n2, int n3) {
        this.field_199738_u = false;
        return IGuiEventListener.super.keyReleased(n, n2, n3);
    }

    @Override
    public boolean charTyped(char c, int n) {
        if (this.field_199738_u) {
            return true;
        }
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.searchBar.charTyped(c, n)) {
                this.updateSearch();
                return false;
            }
            return IGuiEventListener.super.charTyped(c, n);
        }
        return true;
    }

    @Override
    public boolean isMouseOver(double d, double d2) {
        return true;
    }

    private void updateSearch() {
        String string = this.searchBar.getText().toLowerCase(Locale.ROOT);
        this.pirateRecipe(string);
        if (!string.equals(this.lastSearch)) {
            this.updateCollections(true);
            this.lastSearch = string;
        }
    }

    private void pirateRecipe(String string) {
        if ("excitedze".equals(string)) {
            LanguageManager languageManager = this.mc.getLanguageManager();
            Language language = languageManager.getLanguage("en_pt");
            if (languageManager.getCurrentLanguage().compareTo(language) == 0) {
                return;
            }
            languageManager.setCurrentLanguage(language);
            this.mc.gameSettings.language = language.getCode();
            this.mc.reloadResources();
            this.mc.gameSettings.saveOptions();
        }
    }

    private boolean isOffsetNextToMainGUI() {
        return this.xOffset == 86;
    }

    public void recipesUpdated() {
        this.updateTabs();
        if (this.isVisible()) {
            this.updateCollections(true);
        }
    }

    @Override
    public void recipesShown(List<IRecipe<?>> list) {
        for (IRecipe<?> iRecipe : list) {
            this.mc.player.removeRecipeHighlight(iRecipe);
        }
    }

    public void setupGhostRecipe(IRecipe<?> iRecipe, List<Slot> list) {
        ItemStack itemStack = iRecipe.getRecipeOutput();
        this.ghostRecipe.setRecipe(iRecipe);
        this.ghostRecipe.addIngredient(Ingredient.fromStacks(itemStack), list.get((int)0).xPos, list.get((int)0).yPos);
        this.placeRecipe(this.field_201522_g.getWidth(), this.field_201522_g.getHeight(), this.field_201522_g.getOutputSlot(), iRecipe, iRecipe.getIngredients().iterator(), 0);
    }

    @Override
    public void setSlotContents(Iterator<Ingredient> iterator2, int n, int n2, int n3, int n4) {
        Ingredient ingredient = iterator2.next();
        if (!ingredient.hasNoMatchingItems()) {
            Slot slot = (Slot)this.field_201522_g.inventorySlots.get(n);
            this.ghostRecipe.addIngredient(ingredient, slot.xPos, slot.yPos);
        }
    }

    protected void sendUpdateSettings() {
        if (this.mc.getConnection() != null) {
            RecipeBookCategory recipeBookCategory = this.field_201522_g.func_241850_m();
            boolean bl = this.recipeBook.func_242139_a().func_242151_a(recipeBookCategory);
            boolean bl2 = this.recipeBook.func_242139_a().func_242158_b(recipeBookCategory);
            this.mc.getConnection().sendPacket(new CUpdateRecipeBookStatusPacket(recipeBookCategory, bl, bl2));
        }
    }

    private static boolean lambda$updateCollections$5(RecipeList recipeList) {
        return !recipeList.containsCraftableRecipes();
    }

    private static boolean lambda$updateCollections$4(ObjectSet objectSet, RecipeList recipeList) {
        return !objectSet.contains(recipeList);
    }

    private static boolean lambda$updateCollections$3(RecipeList recipeList) {
        return !recipeList.containsValidRecipes();
    }

    private static boolean lambda$updateCollections$2(RecipeList recipeList) {
        return !recipeList.isNotEmpty();
    }

    private void lambda$updateCollections$1(RecipeList recipeList) {
        recipeList.canCraft(this.stackedContents, this.field_201522_g.getWidth(), this.field_201522_g.getHeight(), this.recipeBook);
    }

    private boolean lambda$initSearchBar$0(RecipeTabToggleWidget recipeTabToggleWidget) {
        return recipeTabToggleWidget.func_201503_d().equals((Object)this.currentTab.func_201503_d());
    }
}

