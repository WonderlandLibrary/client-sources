package io.github.liticane.monoxide.protection.antitamper.impl;

import net.minecraft.util.Util;
import io.github.liticane.monoxide.protection.antitamper.AntiTamper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AntiVM extends AntiTamper {

    public AntiVM() {
        super("AntiVM", "Checks the client for VMs");
    }

    public static boolean run() {
        return Util.getOSType() == Util.EnumOS.WINDOWS && (
                isVirtualMachine("wmic computersystem get model", new String[]{"virtualbox", "vmware", "kvm", "hyper-v"}) &&
                        isVirtualMachine("WMIC BIOS GET SERIALNUMBER", new String[]{"0"}) &&
                        isVirtualMachine("wmic baseboard get Manufacturer", new String[]{"Microsoft Corporation"})
        );
    }

    private static boolean isVirtualMachine(String command, String[] prohibitedPhrases) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith("ERROR")) {
                    String sanitizedLine = line.trim().replaceAll("\\s+", "");

                    for (String prohibitedPhrase : prohibitedPhrases) {
                        if (sanitizedLine.contains(prohibitedPhrase)) {
                            Destruction.selfDestructJARFile();
                            return false;
                        }
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

}
