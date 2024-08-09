package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;

@FunctionRegister(name = "FullBright", type = Category.RENDER, desc = "Изменяет гамму")
public class FullBright extends Function {

    private double previousGamma; // Сохраняем предыдущее значение гаммы

    @Subscribe
    private void onUpdate(EventUpdate event) {
        Minecraft minecraft = Minecraft.getInstance();
        GameSettings gameOptions = minecraft.gameSettings;

        // Включаем функцию FullBright
        enableFullBright(gameOptions);
    }

    @Override
    public void onDisable() {
        Minecraft minecraft = Minecraft.getInstance();
        GameSettings gameOptions = minecraft.gameSettings;

        // Выключаем функцию FullBright и восстанавливаем предыдущее значение гаммы
        restorePreviousGamma();
        super.onDisable();
        // Добавляем отладочный вывод для проверки
        System.out.println("Gamma restored to: " + gameOptions.gamma);
    }

    private void enableFullBright(GameSettings gameOptions) {
        previousGamma = gameOptions.gamma; // Сохраняем текущее значение гаммы
        gameOptions.gamma = 1000.0; // Устанавливаем высокое значение гаммы для FullBright

        // Добавляем отладочный вывод для проверки
        System.out.println("Gamma set to 1000.0 for FullBright");
    }

    private void restorePreviousGamma() {
        Minecraft minecraft = Minecraft.getInstance();
        GameSettings gameOptions = minecraft.gameSettings;

        gameOptions.gamma = previousGamma; // Восстанавливаем предыдущее значение гаммы

        // Добавляем отладочный вывод для проверки
        System.out.println("Restored gamma to: " + previousGamma);
    }
}
