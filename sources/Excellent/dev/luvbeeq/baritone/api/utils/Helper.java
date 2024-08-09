package dev.luvbeeq.baritone.api.utils;

import dev.excellent.Excellent;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.luvbeeq.baritone.api.BaritoneAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.*;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * An ease-of-access interface to provide the {@link Minecraft} game instance,
 * chat and console logging mechanisms, and the Baritone chat prefix.
 *
 * @author Brady
 * @since 8/1/2018
 */
public interface Helper {

    /**
     * Instance of {@link Helper}. Used for static-context reference.
     */
    Helper HELPER = new Helper() {
    };

    /**
     * The main game instance returned by {@link Minecraft#getInstance()}.
     * Deprecated since {@link IPlayerContext#minecraft()} should be used instead (In the majority of cases).
     */

    static ITextComponent getPrefix() {
        IFormattableTextComponent component = new StringTextComponent("");
        if (Excellent.getInst().getThemeManager() != null) {
            component.appendString("§7[")
                    .append(ChatUtil.genGradientText("Baritone",
                            Excellent.getInst().getThemeManager().getTheme().getFirstColor(),
                            Excellent.getInst().getThemeManager().getTheme().getSecondColor()))
                    .appendString("§7]");
        }
        return component;
    }

    /**
     * Send a message to display as a toast popup
     *
     * @param title   The title to display in the popup
     * @param message The message to display in the popup
     */
    default void logToast(ITextComponent title, ITextComponent message) {
        Minecraft.getInstance().execute(() -> BaritoneAPI.getSettings().toaster.value.accept(title, message));
    }

    /**
     * Send a message to display as a toast popup
     *
     * @param title   The title to display in the popup
     * @param message The message to display in the popup
     */
    default void logToast(String title, String message) {
        logToast(new StringTextComponent(title), new StringTextComponent(message));
    }

    /**
     * Send a message to display as a toast popup
     *
     * @param message The message to display in the popup
     */
    default void logToast(String message) {
        logToast(Helper.getPrefix(), new StringTextComponent(message));
    }

    /**
     * Send a message as a desktop notification
     *
     * @param message The message to display in the notification
     */
    default void logNotification(String message) {
        logNotification(message, false);
    }

    /**
     * Send a message as a desktop notification
     *
     * @param message The message to display in the notification
     * @param error   Whether to log as an error
     */
    default void logNotification(String message, boolean error) {
        if (BaritoneAPI.getSettings().desktopNotifications.value) {
            logNotificationDirect(message, error);
        }
    }

    /**
     * Send a message as a desktop notification regardless of desktopNotifications
     * (should only be used for critically important messages)
     *
     * @param message The message to display in the notification
     */
    default void logNotificationDirect(String message) {
        logNotificationDirect(message, false);
    }

    /**
     * Send a message as a desktop notification regardless of desktopNotifications
     * (should only be used for critically important messages)
     *
     * @param message The message to display in the notification
     * @param error   Whether to log as an error
     */
    default void logNotificationDirect(String message, boolean error) {
        Minecraft.getInstance().execute(() -> BaritoneAPI.getSettings().notifier.value.accept(message, error));
    }

    /**
     * Send a message to chat only if chatDebug is on
     *
     * @param message The message to display in chat
     */
    default void logDebug(String message) {
        if (!BaritoneAPI.getSettings().chatDebug.value) {
            //System.out.println("Suppressed debug message:");
            //System.out.println(message);
            return;
        }
        // We won't log debug chat into toasts
        // Because only a madman would want that extreme spam -_-
        logDirect(message, false);
    }

    /**
     * Send components to chat with the [Baritone] prefix
     *
     * @param logAsToast Whether to log as a toast notification
     * @param components The components to send
     */
    default void logDirect(boolean logAsToast, ITextComponent... components) {
        TextComponent component = new StringTextComponent("");
        component.append(getPrefix());
        component.append(new StringTextComponent(" "));
        Arrays.asList(components).forEach(component::append);
        if (logAsToast) {
            logToast(getPrefix(), component);
        } else {
            Minecraft.getInstance().execute(() -> BaritoneAPI.getSettings().logger.value.accept(component));
        }
    }

    /**
     * Send components to chat with the [Baritone] prefix
     *
     * @param components The components to send
     */
    default void logDirect(ITextComponent... components) {
        logDirect(BaritoneAPI.getSettings().logAsToast.value, components);
    }

    /**
     * Send a message to chat regardless of chatDebug (should only be used for critically important messages, or as a
     * direct response to a chat command)
     *
     * @param message    The message to display in chat
     * @param color      The color to print that message in
     * @param logAsToast Whether to log as a toast notification
     */
    default void logDirect(String message, TextFormatting color, boolean logAsToast) {
        Stream.of(message.split("\n")).forEach(line -> {
            TextComponent component = new StringTextComponent(line.replace("\t", "    "));
            component.setStyle(component.getStyle().setFormatting(color));
            logDirect(logAsToast, component);
        });
    }

    /**
     * Send a message to chat regardless of chatDebug (should only be used for critically important messages, or as a
     * direct response to a chat command)
     *
     * @param message The message to display in chat
     * @param color   The color to print that message in
     */
    default void logDirect(String message, TextFormatting color) {
        logDirect(message, color, BaritoneAPI.getSettings().logAsToast.value);
    }

    /**
     * Send a message to chat regardless of chatDebug (should only be used for critically important messages, or as a
     * direct response to a chat command)
     *
     * @param message    The message to display in chat
     * @param logAsToast Whether to log as a toast notification
     */
    default void logDirect(String message, boolean logAsToast) {
        logDirect(message, TextFormatting.GRAY, logAsToast);
    }

    /**
     * Send a message to chat regardless of chatDebug (should only be used for critically important messages, or as a
     * direct response to a chat command)
     *
     * @param message The message to display in chat
     */
    default void logDirect(String message) {
        logDirect(message, BaritoneAPI.getSettings().logAsToast.value);
    }
}
