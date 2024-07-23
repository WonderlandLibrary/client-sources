package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.Component;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.ValueComponent;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.impl.BooleanComponent;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.impl.ModeComponent;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.impl.MultiBooleanComponent;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl.setting.impl.NumberComponent;
import io.github.liticane.monoxide.util.render.animation.advanced.Animation;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseBackIn;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseInOutQuad;
import io.github.liticane.monoxide.util.render.updated.NewRenderUtil;
import io.github.liticane.monoxide.util.render.updated.NewStencilUtil;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjglx.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings({"unused", "rawtypes"})
public class SettingPopUpComponent extends Component {

    private Animation openAnimation;
    private Animation backgroundAnimation;
    private Animation scrollAnimation;

    private float scrollY, lastScrollY;

    @Setter
    private Module module;

    private final List<ValueComponent> settings = new ArrayList<>();

    public SettingPopUpComponent(MonoxideClickGuiScreen parent, Module module) {
        super(parent);
        this.module = module;
    }

    @Override
    public void init() {
        openAnimation = new EaseBackIn(500, 0.5F, 2F);
        openAnimation.setDirection(Direction.FORWARDS);
        openAnimation.reset();

        backgroundAnimation = new EaseInOutQuad(500, .5F);
        backgroundAnimation.setDirection(Direction.FORWARDS);
        backgroundAnimation.reset();

        scrollAnimation = new EaseInOutQuad(250, 1.0F);
        scrollAnimation.setDirection(Direction.FORWARDS);
        scrollAnimation.reset();

        if (!settings.isEmpty())
            settings.clear();

        List<Value> values = ValueManager.getInstance().getValues(module, true);

        for (Value value : values) {
            if (value instanceof BooleanValue booleanValue) {
                settings.add(new BooleanComponent(parent, booleanValue));
            }
            if (value instanceof MultiBooleanValue multiBooleanValue) {
                settings.add(new MultiBooleanComponent(parent, multiBooleanValue));
            }
            if (value instanceof ModeValue modeValue) {
                settings.add(new ModeComponent(parent, modeValue));
            }
            if (value instanceof NumberValue numberValue) {
                settings.add(new NumberComponent(parent, numberValue));
            }
        }

        settings.forEach(Component::init);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (module == null)
            return;

        ScaledResolution scale = new ScaledResolution(
                Minecraft.getMinecraft()
        );

        this.x = scale.getScaledWidth() / 2.0F - 175;
        this.y = scale.getScaledHeight() / 2.0F - 175 + 20;

        this.width = 350;
        this.height = 350;

        this.handleScrolling();

        NewRenderUtil.rectangle(0, 0, scale.getScaledWidth(), scale.getScaledHeight(), new Color(0, 0, 0, (int) (155 * backgroundAnimation.getOutput()) * 2));

        NewRenderUtil.scale(scale.getScaledWidth() / 2f, scale.getScaledHeight() / 2f, openAnimation.getOutput().floatValue() + .5F, () -> {
            fontRenderer24.drawStringWithShadow(
                    module.getName(),
                    x, y - 20,
                    Color.WHITE.getRGB()
            );

            NewRenderUtil.rectangle(x, y, width, height, new Color(25, 25, 25));

            NewStencilUtil.renderStencil(
                    () -> NewRenderUtil.rectangle(x, y, width, height, Color.WHITE),
                    () -> {
                        AtomicReference<Float> offset = new AtomicReference<>((float) 0);

                        settings.forEach(setting -> {
                            if (!setting.canShow())
                                return;

                            int index = settings.indexOf(setting);

                            setting.setWidth(width);
                            setting.setHeight(13);

                            setting.setX(x);
                            setting.setY(y + offset.get() + scrollY);

                            setting.draw(mouseX, mouseY);

                            offset.updateAndGet(v -> v + setting.getHeight() + 5);
                        });
                    }
            );
        });

        if (openAnimation.isDone() && openAnimation.getDirection() == Direction.BACKWARDS) {
            expanded = false;
        }

        super.draw(mouseX, mouseY);
    }

    @Override
    public void clicked(int mouseX, int mouseY, int button) {
        if (module == null)
            return;

        settings.forEach(setting -> setting.clicked(mouseX, mouseY, button));

        super.clicked(mouseX, mouseY, button);
    }

    @Override
    public void released(int mouseX, int mouseY, int button) {
        if (module == null)
            return;

        settings.forEach(setting -> setting.released(mouseX, mouseY, button));

        super.released(mouseX, mouseY, button);
    }

    @Override
    public void keyboard(char character, int keyCode) {
        if (module == null)
            return;

        super.keyboard(character, keyCode);

        if (keyCode == 1) {
            openAnimation.setDirection(Direction.BACKWARDS);
            openAnimation.reset();

            backgroundAnimation.setDirection(Direction.BACKWARDS);
            backgroundAnimation.reset();
        }

        settings.forEach(setting -> setting.keyboard(character, keyCode));
    }

    private void handleScrolling() {
        int wheel = Mouse.getDWheel();

        if (wheel > 0 && scrollY != 0) {
            scrollY += 6.5F;
        } else if (wheel < 0) {
            scrollY -= 6.5F;
        }
    }

}