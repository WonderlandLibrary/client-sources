package net.shoreline.client.util;

import net.minecraft.util.Identifier;

/**
 * @author linus
 * @see Identifier
 * @since 1.0
 */
public class ClientIdentifier {
    /**
     * FOR CLIENT ONLY. NOT FOR MINECRAFT NAMESPACES
     *
     * @param val
     * @return
     */
    public static Identifier toId(String val) {
        String[] id = val.split(":");
        if (id[0].equals("block") || id[0].equals("item")) {
            return new Identifier(String.format("minecraft:%s", id[1]));
        }
        return null;
    }
}
