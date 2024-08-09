package xyz.mathax.mathaxclient.gui.screens.modules;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.tabs.builtin.ModulesTab;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import xyz.mathax.mathaxclient.gui.tabs.Tabs;
import xyz.mathax.mathaxclient.utils.gui.Cell;
import xyz.mathax.mathaxclient.gui.widgets.containers.WContainer;
import xyz.mathax.mathaxclient.gui.widgets.containers.WSection;
import xyz.mathax.mathaxclient.gui.widgets.containers.WVerticalList;
import xyz.mathax.mathaxclient.gui.widgets.containers.WWindow;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import net.minecraft.item.Items;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.render.CategoryIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModulesScreen extends TabScreen {
    private WCategoryController controller;

    public ModulesScreen(Theme theme) {
        super(theme, Tabs.get(ModulesTab.class));
    }

    @Override
    public void initWidgets() {
        controller = add(new WCategoryController()).widget();

        // Help
        WVerticalList help = add(theme.verticalList()).pad(4).bottom().widget();
        help.add(theme.label("Left click - Toggle module"));
        help.add(theme.label("Right click - Open module settings"));
    }

    @Override
    protected void init() {
        super.init();
        controller.refresh();
    }

    // Category

    protected WWindow createCategory(WContainer container, Category category) {
        WWindow window = theme.window(category.name);
        window.id = category.name;
        window.padding = 0;
        window.spacing = 0;

        switch (theme.categoryIcons()) {
            case Custom -> window.beforeHeaderInit = wContainer -> wContainer.add(theme.guiTexture(32, 32, 0, category.getIcons().getLeft())).pad(2);
            case Minecraft -> window.beforeHeaderInit = wContainer -> wContainer.add(theme.item(category.getIcons().getRight().getDefaultStack())).pad(2);
        }

        container.add(window);
        window.view.scrollOnlyWhenMouseOver = true;
        window.view.hasScrollBar = false;
        window.view.spacing = 0;

        for (Module module : Modules.get().getGroup(category)) {
            window.add(theme.module(module)).expandX();
        }

        return window;
    }

    // Search

    protected void createSearchW(WContainer w, String text) {
        if (!text.isEmpty()) {
            // Titles
            Set<Module> modules = Modules.get().searchNames(text);
            if (modules.size() > 0) {
                WSection section = w.add(theme.section("Modules")).expandX().widget();
                section.spacing = 0;

                int count = 0;
                for (Module module : modules) {
                    if (count >= Config.get().moduleSearchCountSetting.get() || count >= modules.size()) {
                        break;
                    }

                    section.add(theme.module(module)).expandX();

                    count++;
                }
            }

            // Settings
            modules = Modules.get().searchSettingNames(text);
            if (modules.size() > 0) {
                WSection section = w.add(theme.section("Settings")).expandX().widget();
                section.spacing = 0;

                int count = 0;
                for (Module module : modules) {
                    if (count >= Config.get().moduleSearchCountSetting.get() || count >= modules.size()) {
                        break;
                    }

                    section.add(theme.module(module)).expandX();

                    count++;
                }
            }
        }
    }

    protected WWindow createSearch(WContainer container) {
        String name = "Search";
        WWindow window = theme.window(name);
        window.id = name;

        switch (theme.categoryIcons()) {
            case Custom -> window.beforeHeaderInit = wContainer -> wContainer.add(theme.guiTexture(32, 32, 0, GuiRenderer.SEARCH)).pad(2);
            case Minecraft -> window.beforeHeaderInit = wContainer -> wContainer.add(theme.item(Items.COMPASS.getDefaultStack())).pad(2);
        }

        container.add(window);
        window.view.scrollOnlyWhenMouseOver = true;
        window.view.hasScrollBar = false;
        window.view.maxHeight -= 20;

        WVerticalList l = theme.verticalList();

        WTextBox text = window.add(theme.textBox("")).minWidth(140).expandX().widget();
        text.setFocused(true);
        text.action = () -> {
            l.clear();
            createSearchW(l, text.get());
        };

        window.add(l).expandX();
        createSearchW(l, text.get());

        return window;
    }

    // Favorites

    protected Cell<WWindow> createFavorites(WContainer container) {
        boolean hasFavorites = Modules.get().getAll().stream().anyMatch(module -> module.favorite);
        if (!hasFavorites) {
            return null;
        }

        String name = "Favorites";
        WWindow window = theme.window(name);
        window.id = name;
        window.padding = 0;
        window.spacing = 0;

        switch (theme.categoryIcons()) {
            case Custom -> window.beforeHeaderInit = wContainer -> wContainer.add(theme.guiTexture(32, 32, 0, GuiRenderer.FAVORITES)).pad(2);
            case Minecraft -> window.beforeHeaderInit = wContainer -> wContainer.add(theme.item(Items.NETHER_STAR.getDefaultStack())).pad(2);
        }

        Cell<WWindow> cell = container.add(window);
        window.view.scrollOnlyWhenMouseOver = true;
        window.view.hasScrollBar = false;
        window.view.spacing = 0;

        createFavoritesW(window);
        return cell;
    }

    protected boolean createFavoritesW(WWindow window) {
        boolean hasFavorites = false;

        for (Module module : Modules.get().getAll()) {
            if (module.favorite) {
                window.add(theme.module(module)).expandX();
                hasFavorites = true;
            }
        }

        return hasFavorites;
    }

    @Override
    public void reload() {}

    // Stuff

    protected class WCategoryController extends WContainer {
        public final List<WWindow> windows = new ArrayList<>();
        private Cell<WWindow> favorites;

        @Override
        public void init() {
            for (Category category : Modules.loopCategories()) {
                windows.add(createCategory(this, category));
            }

            windows.add(createSearch(this));

            refresh();
        }

        protected void refresh() {
            if (favorites == null) {
                favorites = createFavorites(this);
                if (favorites != null) {
                    windows.add(favorites.widget());
                }
            } else {
                favorites.widget().clear();

                if (!createFavoritesW(favorites.widget())) {
                    remove(favorites);
                    windows.remove(favorites.widget());
                    favorites = null;
                }
            }
        }

        @Override
        protected void onCalculateWidgetPositions() {
            double pad = theme.scale(4);
            double h = theme.scale(40);

            double x = this.x + pad;
            double y = this.y;

            for (Cell<?> cell : cells) {
                double windowWidth = Utils.getWindowWidth();
                double windowHeight = Utils.getWindowHeight();

                if (x + cell.width > windowWidth) {
                    x = x + pad;
                    y += h;
                }

                if (x > windowWidth) {
                    x = windowWidth / 2.0 - cell.width / 2.0;
                    if (x < 0) {
                        x = 0;
                    }
                }

                if (y > windowHeight) {
                    y = windowHeight / 2.0 - cell.height / 2.0;
                    if (y < 0) {
                        y = 0;
                    }
                }

                cell.x = x;
                cell.y = y;

                cell.width = cell.widget().width;
                cell.height = cell.widget().height;

                cell.alignWidget();

                x += cell.width + pad;
            }
        }
    }
}
