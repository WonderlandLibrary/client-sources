package lol.base.managers;

import de.florianmichael.rclasses.pattern.storage.Storage;
import lol.base.BaseClient;
import lol.base.addons.CategoryAddon;
import lol.base.addons.ModuleAddon;
import lol.base.annotations.ModuleInfo;
import lol.base.radbus.Listen;
import lol.main.IMinecraft;
import lol.main.events.KeyboardEvent;
import lol.main.events.Render2DEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager extends Storage<ModuleAddon> implements IMinecraft {

    public void init() {
        BaseClient.get().eventManager.subscribe(this);
        Reflections reflections = new Reflections("lol.main.modules");
        reflections.getTypesAnnotatedWith(ModuleInfo.class).forEach(clazz -> {
            try {
                this.add((ModuleAddon) clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public <V extends ModuleAddon> V getByClass(final Class<V> clazz) {
        final ModuleAddon feature = this.getList().stream().filter(m -> m.getClass().equals(clazz)).findFirst().orElse(null);
        if (feature == null) return null;
        return clazz.cast(feature);
    }

    public <M extends ModuleAddon> M getByName(String input) {
        return (M) this.getList().stream().filter(module -> module.name.equalsIgnoreCase(input)).findFirst().orElse(null);
    }

    public List<ModuleAddon> getModulesByCategory(CategoryAddon input) {
        return this.getList().stream().filter(module -> module.category.equals(input)).collect(Collectors.toList());
    }

    public List<ModuleAddon> getByEnable() {
        return this.getList().stream().filter(moduleFeature -> moduleFeature.enabled).collect(Collectors.toList());
    }

    @Listen
    public void onKeyboard(KeyboardEvent event) {
        this.getList().forEach(module -> {
            if (module.keyBind == event.keyCode) {
                module.setEnabled(!module.enabled);
            }
        });
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        this.getList().forEach(module -> {
            if (module != null) {
                if (!mc.gameSettings.showDebugInfo && module.enabled) {
                    module.onRender();
                }
            }
        });
    }

}
