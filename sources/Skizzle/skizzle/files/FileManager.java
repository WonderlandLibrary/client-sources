/*
 * Decompiled with CFR 0.150.
 */
package skizzle.files;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import net.minecraft.client.Minecraft;
import skizzle.Client;
import skizzle.files.SettingValueModule;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.DescriptionSetting;
import skizzle.settings.KeybindSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.settings.Setting;

public class FileManager {
    public Minecraft mc = Minecraft.getMinecraft();

    public void updateClickGUIPanels() {
        String[] Nigga;
        FileManager Nigga2;
        String Nigga3 = Nigga2.readFile(Qprot0.0("\ua356\u71d9\u9835\ub91b\ue0b5\u26f0\u8c2a\ucf10") + File.separator + Client.currentProfile + File.separator + Qprot0.0("\ua345\u71c7\u9833\ub91e\ue0b7\u26fb\u8c3a\ucf0a"));
        String[] arrstring = Nigga = Nigga3.split("\n");
        int n = Nigga.length;
        for (int i = 0; i < n; ++i) {
            String Nigga4 = arrstring[i];
            for (Module.Category Nigga5 : Module.Category.values()) {
                int Nigga6;
                String Nigga7;
                if (!Nigga4.startsWith(String.valueOf(Nigga5.name) + ";")) continue;
                String Nigga8 = Nigga4.replace(String.valueOf(Nigga5.name) + ";", "");
                if (Nigga8.contains(Qprot0.0("\ua35e\u7191"))) {
                    Nigga7 = Nigga8.replace(Qprot0.0("\ua35e\u7191"), "");
                    try {
                        Nigga5.cgX = Nigga6 = Integer.parseInt(Nigga7);
                    }
                    catch (Exception exception) {}
                }
                if (Nigga8.contains(Qprot0.0("\ua35f\u7191"))) {
                    Nigga7 = Nigga8.replace(Qprot0.0("\ua35f\u7191"), "");
                    try {
                        Nigga5.cgY = Nigga6 = Integer.parseInt(Nigga7);
                    }
                    catch (Exception exception) {}
                }
                if (!Nigga8.contains(Qprot0.0("\ua343\u71d3\u982a\ub91c\ue0b2\u26f8\u8c2a\ucf07\u49a1"))) continue;
                Nigga7 = Nigga8.replace(Qprot0.0("\ua343\u71d3\u982a\ub91c\ue0b2\u26f8\u8c2a\ucf07\u49a1"), "");
                try {
                    Nigga6 = Boolean.parseBoolean(Nigga7) ? 1 : 0;
                    Nigga5.clickGuiExpand = Nigga6;
                }
                catch (Exception exception) {}
            }
        }
    }

    public static {
        throw throwable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean checkActiveModule(Module Nigga) {
        try {
            String[] Nigga2;
            FileManager Nigga3;
            String Nigga4 = Nigga3.readFile(Qprot0.0("\ua356\u71d9\u9835\u87aa\ueac4\u26f0\u8c2a\ucf10") + File.separator + Client.currentProfile + File.separator + Qprot0.0("\ua34b\u71c4\u983e\u87b9\ueac1\u26f9\u8c3c"));
            String[] arrstring = Nigga2 = Nigga4.split(",");
            int n = Nigga2.length;
            int n2 = 0;
            while (true) {
                if (n2 >= n) {
                    return false;
                }
                String Nigga5 = arrstring[n2];
                if (Nigga5.equals(Nigga.name)) {
                    return true;
                }
                ++n2;
            }
        }
        catch (Exception exception) {}
        return false;
    }

    public static boolean checkNewVersion() {
        block6: {
            String Nigga;
            block5: {
                try {
                    float Nigga2;
                    String Nigga3 = FileManager.checkPasteData(Qprot0.0("\ua34e\u71df\u982e\uabc6\u9ed8\u26a6\u8c60\ucf4c\u5b20\u3580\uacc2\uaf18\u21dd\u7e7d\ueafb\u3f4d\u42a7\u1522\u1b3d\u8187\u5acf\u01d8\ub312\ue449\u5923\u1e72\u2f08\u765a\u4e46\u1454\u428d\u882c\u2ee0"));
                    String[] Nigga4 = Nigga3.split(Qprot0.0("\ua366\u7191"));
                    Nigga = Nigga4[0];
                    String cfr_ignored_0 = Nigga4[1];
                    if (!Nigga.contains(Qprot0.0("\ua364\u71ee\u980e\uabf7"))) break block5;
                    if (Client.isBeta && (Nigga2 = Float.parseFloat(Nigga.replace(Qprot0.0("\ua306\u71e9\u981f\uabe2\u9eea"), ""))) > Client.versionF) {
                        return true;
                    }
                    break block6;
                }
                catch (IOException iOException) {}
            }
            float Nigga5 = Float.parseFloat(Nigga);
            if (Nigga5 > Client.versionF) {
                return true;
            }
        }
        return false;
    }

    public void writeSettings() {
    }

    public static String checkPasteData(String Nigga) throws IOException {
        URL Nigga2 = new URL(Nigga);
        Scanner Nigga3 = new Scanner(Nigga2.openStream());
        StringBuffer Nigga4 = new StringBuffer();
        while (Nigga3.hasNext()) {
            Nigga4.append(String.valueOf(Nigga3.next()) + "\n");
        }
        String Nigga5 = Nigga4.toString();
        Nigga5 = Nigga5.replaceAll(Qprot0.0("\ua31a\u71f0\u9804\uc17d\u286b\u26b6\u8c71"), "");
        return Nigga5;
    }

    public boolean hasBeta() throws IOException {
        FileManager Nigga;
        String Nigga2 = Nigga.readFile(Qprot0.0("\ua345\u71c7\u9833\u9a39\ucf53\u26e8\u8c10\ucf0a\u6ade"));
        if (Nigga2.equals("")) {
            Nigga.writeFile(Client.getMotherboardSN(), "", Qprot0.0("\ua345\u71c7\u9833\u9a39\ucf53\u26e8\u8c10\ucf0a\u6ade"));
        } else if (Nigga2.equals(Client.getMotherboardSN())) {
            if (FileManager.checkPasteData(Qprot0.0("\ua34e\u71df\u982e\u9a2c\ucf4e\u26a6\u8c60\ucf4c\u6aca\u6416\uacc2\uaf18\u21dd\u4f97\ubb6d\u3f4d\u42a7\u1522\u2ad7\ud011\u5acf\u01d8\ub312\ud5a3\u08b5\u1e18\u2f1d\u7639\u7fbe\u459a\u42f5\u8810\u2e99")).contains(String.valueOf(Client.getMotherboardSN()) + Qprot0.0("\ua31c\u71c9\u983f\u9a28\ucf5c"))) {
                return true;
            }
            if (FileManager.checkPasteData(Qprot0.0("\ua34e\u71df\u982e\u9a2c\ucf4e\u26a6\u8c60\ucf4c\u6aca\u6416\uacc2\uaf18\u21dd\u4f97\ubb6d\u3f4d\u42a7\u1522\u2ad7\ud011\u5acf\u01d8\ub312\ud5a3\u08b5\u1e18\u2f1d\u7639\u7fbe\u459a\u42f5\u8810\u2e99")).contains(String.valueOf(Client.getMotherboardSN()) + Qprot0.0("\ua31c\u71c9\u9836\u9a3d\ucf5e\u26f7\u8c23\ucf0a\u6ac9\u6403\uacd4\uaf08"))) {
                Minecraft.logger.error(Qprot0.0("\ua373\u71d8\u983f\u9a2e\ucf1d\u26fe\u8c23\ucf02\u6ad9\u641c\uacdd\uaf05\u21cb\u4f81\ubb61\u3f47\u42a5\u1561\u2ac8\ud010\u5a85\u01cb\ub300\ud5b1\u08ba\u1e4b\u2f11\u7661\u7f87\u4583\u42b3\u881d\u2eb4\u90b2\ud594\u8171\uddac\uc9d1\u7723\uebca\ua05e\u3c1d\ubb93\u041e\u2f8e\u0573\ua15e\u90a2\u2df3\u8cd1\u8fce\uf41e\u1e78\u0d6c\u9afb\u865d\u690b\uefff\u8865\u6a17\u090a\u0999\uc12c\u0326\ubfc7\ub255\u4ad9\ufc27\u47c1\u6474\u2a59\ub0e3\u34ff\ua1cd\u2943\u5a13\u468f\ue57f\u0ff6\u12cd\ue5d1\ud42e\u15c6\ue5ce\u6389\u886b\u70e7\udf38\uf492\u6d32\u4756\uc328\u6a7f\u500c\ue34b\u7db6\u946c"));
                Client.openWebLink(Client.discordLink);
                System.exit(0);
                return false;
            }
        }
        return false;
    }

    public void updateActiveModules() {
        try {
            FileManager Nigga;
            String Nigga2 = "";
            for (Module Nigga3 : Client.modules) {
                String Nigga4;
                if (!Nigga3.toggled || Nigga3.name.equals(Qprot0.0("\ua361\u71c3\u9835\u16dd\u7be7"))) continue;
                Nigga2 = Nigga2.equals("") ? Nigga3.name : (Nigga4 = String.valueOf(Nigga2) + "," + Nigga3.name);
            }
            Nigga.writeFile(Nigga2, Qprot0.0("\ua356\u71d9\u9835\u16c8\u7bfa\u26f0\u8c2a\ucf10") + File.separator + Client.currentProfile, Qprot0.0("\ua34b\u71c4\u983e\u16db\u7bff\u26f9\u8c3c"));
        }
        catch (Exception exception) {}
    }

    public SettingValueModule getSettingsForModule(String Nigga) {
        return null;
    }

    public void writeFile(String Nigga, String Nigga2, String Nigga3) {
        Nigga2 = String.valueOf(File.separator) + Nigga2;
        if (Nigga2.equals("")) {
            Nigga2 = "";
        }
        String Nigga4 = Qprot0.0("\ua308\u71df\u9822\u1c79");
        if (Nigga3.contains(Qprot0.0("\ua308\u71c1\u9829\u1c62\u4502"))) {
            Nigga3 = Nigga3.replace(Qprot0.0("\ua308\u71c1\u9829\u1c62\u4502"), "");
            Nigga4 = Qprot0.0("\ua308\u71c1\u9829\u1c62\u4502");
        }
        String Nigga5 = String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()) + File.separator + Client.name + Nigga2 + File.separator + Nigga3 + Nigga4;
        File Nigga6 = new File(String.valueOf(String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath())) + File.separator + Client.name + Nigga2 + File.separator);
        if (!Nigga6.exists()) {
            Nigga6.mkdirs();
        }
        try {
            Throwable throwable = null;
            Object var8_10 = null;
            try (FileWriter Nigga7 = new FileWriter(Nigga5);){
                Nigga7.write(Nigga);
                Nigga7.close();
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (Exception Nigga8) {
            System.out.println(Qprot0.0("\ua363\u71f9\u9808\u1c42\u453e\u26bc\u8c18\ucf31\ueca2\uee72\uacf8\uaf22\u21ff\uc984\u3113\u3f6a\u42c5\u1504\uacc9\u5a69\u5aa1\u01fe\ub332\u53bf\u82eb") + Nigga8);
        }
    }

    public String[] readSettings() {
        try {
            FileManager Nigga;
            String Nigga2 = Nigga.readFile(Qprot0.0("\ua356\u71d9\u9835\u2649\u0b7b\u26f0\u8c2a\ucf10") + File.separator + Client.currentProfile + File.separator + Qprot0.0("\ua355\u71ce\u982e\u265b\u0b7b\u26f2\u8c28\ucf10"));
            String[] Nigga3 = Nigga2.split("\n");
            return Nigga3;
        }
        catch (Exception exception) {
            String Nigga = Qprot0.0("\ua32c\u71a1");
            String[] Nigga4 = Nigga.split("\n");
            return Nigga4;
        }
    }

    public void createFile() {
    }

    public void updateSettings() {
        FileManager Nigga;
        Nigga.writeSettings();
        String Nigga2 = Qprot0.0("\ua309\u7184\u981e\u25b5\u0f7f\u26d2\u8c00\ucf37\ud53c\ua450\uacf5\uaf25\u21ec");
        for (Module Nigga3 : Client.modules) {
            String Nigga4 = Nigga3.name.toLowerCase();
            String Nigga5 = Nigga4.replace(" ", "-");
            for (Setting Nigga6 : Nigga3.settings) {
                if (Nigga6 instanceof DescriptionSetting) continue;
                String Nigga7 = Nigga6.name.toLowerCase();
                String Nigga8 = Nigga7.replace(" ", "_");
                if (Nigga6 instanceof BooleanSetting) {
                    Nigga2 = Nigga2.equals("") ? String.valueOf(Nigga5) + ";" + Nigga8 + ":" + ((BooleanSetting)Nigga6).isEnabled() : String.valueOf(Nigga2) + "\n" + Nigga5 + ";" + Nigga8 + ":" + ((BooleanSetting)Nigga6).isEnabled();
                }
                if (Nigga6 instanceof NumberSetting) {
                    Nigga2 = Nigga2.equals("") ? String.valueOf(Nigga5) + ";" + Nigga8 + ":" + ((NumberSetting)Nigga6).getValue() : String.valueOf(Nigga2) + "\n" + Nigga5 + ";" + Nigga8 + ":" + ((NumberSetting)Nigga6).getValue();
                }
                if (Nigga6 instanceof ModeSetting) {
                    Nigga2 = Nigga2.equals("") ? String.valueOf(Nigga5) + ";" + Nigga8 + ":" + ((ModeSetting)Nigga6).getIndex() : String.valueOf(Nigga2) + "\n" + Nigga5 + ";" + Nigga8 + ":" + ((ModeSetting)Nigga6).getIndex();
                }
                if (!(Nigga6 instanceof KeybindSetting)) continue;
                Nigga2 = Nigga2.equals("") ? String.valueOf(Nigga5) + ";" + Nigga8 + ":" + ((KeybindSetting)Nigga6).code : String.valueOf(Nigga2) + "\n" + Nigga5 + ";" + Nigga8 + ":" + ((KeybindSetting)Nigga6).code;
            }
        }
        Nigga.writeFile(Nigga2, Qprot0.0("\ua356\u71d9\u9835\u259c\u0f36\u26f0\u8c2a\ucf10") + File.separator + Client.currentProfile, Qprot0.0("\ua355\u71ce\u982e\u258e\u0f36\u26f2\u8c28\ucf10"));
    }

    public void updateClickGUISettings() {
        FileManager Nigga;
        String Nigga2 = Qprot0.0("\ua309\u7184\u981e\u0f06\u7208\u26d2\u8c00\ucf37\uff8f\ud927\uacf5\uaf25\u21ec");
        for (Module.Category Nigga3 : Module.Category.values()) {
            Nigga2 = String.valueOf(Nigga2) + "\n" + Nigga3.name + Qprot0.0("\ua31d\u71d3\u9860") + Nigga3.cgX;
            Nigga2 = String.valueOf(Nigga2) + "\n" + Nigga3.name + Qprot0.0("\ua31d\u71d2\u9860") + Nigga3.cgY;
            Nigga2 = String.valueOf(Nigga2) + "\n" + Nigga3.name + Qprot0.0("\ua31d\u71ce\u9822\u0f39\u7249\u26f2\u8c2b\ucf06\uffcb\ud958") + Nigga3.clickGuiExpand;
        }
        Nigga.writeFile(Nigga2, Qprot0.0("\ua356\u71d9\u9835\u0f2f\u7241\u26f0\u8c2a\ucf10") + File.separator + Client.currentProfile, Qprot0.0("\ua345\u71c7\u9833\u0f2a\u7243\u26fb\u8c3a\ucf0a"));
    }

    public String readFile(String Nigga) {
        try {
            String cfr_ignored_0 = String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()) + File.separator + Client.name + File.separator + Nigga + Qprot0.0("\ua308\u71df\u9822\uadb0");
            File Nigga2 = new File(String.valueOf(String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath())) + File.separator + Qprot0.0("\ua375\u71c0\u9833\uadbe\u94cf\u26f0\u8c2a") + File.separator + Nigga + Qprot0.0("\ua308\u71df\u9822\uadb0"));
            BufferedReader Nigga3 = new BufferedReader(new FileReader(Nigga2));
            String Nigga4 = "";
            String Nigga5 = Nigga3.readLine();
            while ((Nigga4 = Nigga3.readLine()) != null) {
                String Nigga6;
                if (Nigga4.equals("") || Nigga4.startsWith("/")) continue;
                Nigga5 = Nigga6 = String.valueOf(Nigga5) + "\n" + Nigga4;
            }
            return Nigga5;
        }
        catch (Exception exception) {
            return "";
        }
    }

    public FileManager() {
        FileManager Nigga;
    }

    public static void downloadUpdate() {
        String Nigga = Qprot0.0("\ua34e\u71df\u982e\uc2f7\u3789\u26a6\u8c60\ucf4c\u3205\u9cdf\uacc6\uaf02\u21d4\u1741\u43a2\u3f47\u42b8\u1574\u725a\u288f\u5ace\u01c7\ub316\u8d6b\uf034\u1e40\u2f18\u7661\u2740\ubd01\u42e9\u8816\u2ebe\uc824\u2d18\u8129\uddab\uc9d4\u2fa1\u131a\ua05b\u3c4b\ubbd1\u5cd3\ud711\u0520\ua149\u90fd\u752a\u741d\u8fc5\uf444\u1e70\u55a8\u6233\u8644\u694a\uefa8\ud0b0\u92c4\u0959\u09c1\uc175\u5bbb\u4720\ub24a\u4a91\ufc7d\u1f08\u9cab\u2a48\ub0bd\u34e6\uf94d\ud1d9");
        String Nigga2 = String.valueOf(String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath())) + File.separator + Qprot0.0("\ua350\u71ce\u9828\uc2f4\u3793\u26f3\u8c21\ucf10") + File.separator + Qprot0.0("\ua375\u71c0\u9833\uc2fd\u3780\u26f0\u8c2a") + File.separator + Qprot0.0("\ua375\u71c0\u9833\uc2fd\u3780\u26f0\u8c2a\ucf4d\u320b\u9cd1\uacc3");
        File Nigga3 = new File(String.valueOf(String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath())) + File.separator + Qprot0.0("\ua350\u71ce\u9828\uc2f4\u3793\u26f3\u8c21\ucf10") + File.separator + Qprot0.0("\ua375\u71c0\u9833\uc2fd\u3780\u26f0\u8c2a") + File.separator);
        if (!Nigga3.exists()) {
            Nigga3.mkdir();
        }
        System.out.println(Qprot0.0("\ua375\u71df\u983b\uc2f5\u378e\u26f5\u8c21\ucf04\u3241\u9cd4\uacde\uaf1b\u21d6\u1742\u43ac\u3f42\u42ed\u1532\u724d\u2895\u5ace"));
        Runnable Nigga4 = new Runnable(Nigga, Nigga2){
            public String val$FILE_URL;
            public String val$FILE_NAME;
            {
                1 Nigga;
                Nigga.val$FILE_URL = string;
                Nigga.val$FILE_NAME = string2;
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                try {
                    Throwable throwable = null;
                    Object var2_3 = null;
                    try {
                        1 Nigga;
                        BufferedInputStream Nigga2 = new BufferedInputStream(new URL(Nigga.val$FILE_URL).openStream());
                        try {
                            try (FileOutputStream Nigga3 = new FileOutputStream(Nigga.val$FILE_NAME);){
                                int Nigga4;
                                byte[] Nigga5 = new byte[1024];
                                while ((Nigga4 = Nigga2.read(Nigga5, 0, 1024)) != -1) {
                                    Nigga3.write(Nigga5, 0, Nigga4);
                                }
                            }
                            if (Nigga2 == null) return;
                        }
                        catch (Throwable throwable2) {
                            if (throwable == null) {
                                throwable = throwable2;
                            } else if (throwable != throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                            if (Nigga2 == null) throw throwable;
                            Nigga2.close();
                            throw throwable;
                        }
                        Nigga2.close();
                        return;
                    }
                    catch (Throwable throwable3) {
                        if (throwable == null) {
                            throwable = throwable3;
                            throw throwable;
                        } else {
                            if (throwable == throwable3) throw throwable;
                            throwable.addSuppressed(throwable3);
                        }
                        throw throwable;
                    }
                }
                catch (IOException iOException) {}
            }

            public static {
                throw throwable;
            }
        };
        new Thread(Nigga4).start();
    }
}

