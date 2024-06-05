/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.command.impl;

import java.util.Arrays;
import java.util.List;
import digital.rbq.command.AbstractCommand;
import digital.rbq.module.impl.visuals.hud.HUDMod;
import digital.rbq.utils.Logger;

public final class ClientNameCommand
extends AbstractCommand {
    private final List<Character> chars = Arrays.asList(Character.valueOf('0'), Character.valueOf('1'), Character.valueOf('2'), Character.valueOf('3'), Character.valueOf('4'), Character.valueOf('5'), Character.valueOf('6'), Character.valueOf('7'), Character.valueOf('8'), Character.valueOf('9'), Character.valueOf('a'), Character.valueOf('b'), Character.valueOf('c'), Character.valueOf('d'), Character.valueOf('e'), Character.valueOf('k'), Character.valueOf('m'), Character.valueOf('o'), Character.valueOf('l'), Character.valueOf('n'), Character.valueOf('r'));

    public ClientNameCommand() {
        super("clientname", "Change text displayed on watermark.", "clientname <name>", "clientname", "name", "rename");
    }

    @Override
    public void execute(String ... arguments) {
        if (arguments.length >= 2) {
            StringBuilder string = new StringBuilder();
            for (int i = 1; i < arguments.length; ++i) {
                String tempString = arguments[i];
                tempString = tempString.replace('&', '\u00a7');
                string.append(tempString).append(" ");
            }
            Logger.log(String.format("Changed client name to '%s\u00a77' was '%s\u00a77'.", string.toString().trim(), HUDMod.clientName));
            HUDMod.clientName = string.toString().trim();
        } else {
            this.usage();
        }
    }
}

