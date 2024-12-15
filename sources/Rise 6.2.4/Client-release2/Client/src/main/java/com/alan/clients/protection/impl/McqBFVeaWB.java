package com.alan.clients.protection.impl;

import com.alan.clients.Client;
import com.alan.clients.protection.ProtectionCheck;
import com.alan.clients.protection.api.McqBFVadWB;

import java.util.List;

public final class McqBFVeaWB extends ProtectionCheck {

    public McqBFVeaWB() {
        super(McqBFVadWB.INITIALIZE, false);
    }

    @Override
    public boolean check() {
        final List<String> arguments = Client.INSTANCE.getMcqAFVeaWB().getJvmArguments();

//        final boolean malicious = arguments.stream().anyMatch(s -> s.contains("javaagent")
//                || s.contains("agentlib") || s.contains("Xdebug")
//                || s.contains("Xrunjdwp:") || s.contains("noverify")
//        );

        final boolean required = arguments.contains("-" +
                "X" +
                "X" +
                ":" +
                "+" +
                "D" +
                "i" +
                "s" +
                "a" +
                "b" +
                "l" +
                "e" +
                "A" +
                "t" +
                "t" +
                "a" +
                "c" +
                "h" +
                "M" +
                "e" +
                "c" +
                "h" +
                "a" +
                "n" +
                "i" +
                "s" +
                "m");
        if (!required) System.out.println("P" +
                "l" +
                "e" +
                "a" +
                "s" +
                "e" +
                " " +
                "r" +
                "e" +
                "p" +
                "l" +
                "a" +
                "c" +
                "e" +
                " " +
                "t" +
                "h" +
                "e" +
                " " +
                "s" +
                "t" +
                "a" +
                "r" +
                "t" +
                "." +
                "c" +
                "m" +
                "d" +
                " " +
                "w" +
                "i" +
                "t" +
                "h" +
                " " +
                "t" +
                "h" +
                "e" +
                " " +
                "l" +
                "a" +
                "t" +
                "e" +
                "s" +
                "t" +
                " " +
                "o" +
                "n" +
                "e" +
                " " +
                "y" +
                "o" +
                "u" +
                " " +
                "d" +
                "o" +
                "w" +
                "n" +
                "l" +
                "o" +
                "a" +
                "d" +
                "e" +
                "d" +
                " " +
                "f" +
                "r" +
                "o" +
                "m" +
                " " +
                "V" +
                "a" +
                "n" +
                "t" +
                "a" +
                "g" +
                "e" +
                "," +
                " " +
                "o" +
                "r" +
                " " +
                "a" +
                "d" +
                "d" +
                " " +
                "-" +
                "X" +
                "X" +
                ":" +
                "+" +
                "D" +
                "i" +
                "s" +
                "a" +
                "b" +
                "l" +
                "e" +
                "A" +
                "t" +
                "t" +
                "a" +
                "c" +
                "h" +
                "M" +
                "e" +
                "c" +
                "h" +
                "a" +
                "n" +
                "i" +
                "s" +
                "m" +
                " " +
                "t" +
                "o" +
                " " +
                "y" +
                "o" +
                "u" +
                "r" +
                " " +
                "s" +
                "t" +
                "a" +
                "r" +
                "t" +
                "." +
                "c" +
                "m" +
                "d" +
                " " +
                "y" +
                "o" +
                "u" +
                "r" +
                "s" +
                "e" +
                "l" +
                "f" +
                ".");

        return /*malicious || */!required;
    }
}
