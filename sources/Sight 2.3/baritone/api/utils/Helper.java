/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.api.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Stream;

import baritone.api.BaritoneAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

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
    Helper HELPER = new Helper() {};

    /**
     * Instance of the game
     */
    Minecraft mc = Minecraft.getMinecraft();

    static IChatComponent getPrefix() {
        // Inner text component
        final Calendar now = Calendar.getInstance();
        final boolean xd = now.get(Calendar.MONTH) == Calendar.APRIL && now.get(Calendar.DAY_OF_MONTH) <= 3;
        IChatComponent baritone = new ChatComponentText(xd ? "Baritoe" : BaritoneAPI.getSettings().shortBaritonePrefix.value ? "B" : "Baritone");
        baritone.getChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE);

        // Outer brackets
        IChatComponent prefix = new ChatComponentText("");
        prefix.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
        prefix.appendText("[");
        prefix.appendSibling(baritone);
        prefix.appendText("]");

        return prefix;
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
        logDirect(message);
    }

    /**
     * Send components to chat with the [Baritone] prefix
     *
     * @param components The components to send
     */
    default void logDirect(IChatComponent... components) {
    	IChatComponent component = new ChatComponentText("");
        component.appendSibling(getPrefix());
        component.appendSibling(new ChatComponentText(" "));
        Arrays.asList(components).forEach(component::appendSibling);
        Minecraft.getMinecraft().addScheduledTask(() -> BaritoneAPI.getSettings().logger.value.accept(component));
    }

    /**
     * Send a message to chat regardless of chatDebug (should only be used for critically important messages, or as a
     * direct response to a chat command)
     *
     * @param message The message to display in chat
     * @param color   The color to print that message in
     */
    default void logDirect(String message, EnumChatFormatting color) {
        Stream.of(message.split("\n")).forEach(line -> {
        	IChatComponent component = new ChatComponentText(line.replace("\t", "    "));
            component.getChatStyle().setColor(color);
            logDirect(component);
        });
    }

    /**
     * Send a message to chat regardless of chatDebug (should only be used for critically important messages, or as a
     * direct response to a chat command)
     *
     * @param message The message to display in chat
     */
    default void logDirect(String message) {
        logDirect(message, EnumChatFormatting.GRAY);
    }
}
