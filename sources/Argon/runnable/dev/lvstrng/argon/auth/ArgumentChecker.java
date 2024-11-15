package dev.lvstrng.argon.auth;

import java.lang.management.ManagementFactory;

public class ArgumentChecker {
    private static final String[] args = new String[]
            {
                    "-XX:+StartAttachListener",
                    "-Djavax.net.ssl.trustStorePassword",
                    "-Xdebug",
                    "-DproxyHost",
                    "-Xnoagent",
                    "-javaagent",
                    "-Xrun",
                    "-Djavax.net.ssl.trustStore",
                    "-DproxySet",
                    "-Xrunjdwp",
                    "-DproxyPort",
                    "-agentlib",
                    "-agentpath",
                    "-verbose",
                    "-XBootclasspath"
            };

    public static boolean invalid_args() {
        final String jvm_args = ManagementFactory.getRuntimeMXBean().getInputArguments().toString();

        for (final String arg : args)
            if (jvm_args.contains(arg)) return true;

        return false;
    }
}