package pw.latematt.xiv.ui.managers.alt;

import net.minecraft.client.gui.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.file.XIVFile;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GuiAltManager extends GuiScreen implements GuiYesNoCallback {
    private GuiScreen parent;
    private AltSlot slot;

    public GuiTextField username, keyword, search;
    public GuiPasswordField password;
    private AuthThread thread;

    public GuiAltManager(GuiScreen parent) {
        this.parent = parent;

        XIV.getInstance().getFileManager().loadFile("alts");
    }

    public List<AltAccount> getAccounts() {
        if (search != null && search.getText().length() > 0) {
            if (search.getText().startsWith("k:")) {
                String text = search.getText().substring(2);

                return XIV.getInstance().getAltManager().getContents().stream().filter(account -> account.getKeyword().toLowerCase().contains(text.toLowerCase())).filter(account -> account.getKeyword().length() > 0).collect(Collectors.toCollection(ArrayList::new));
            }
            if (search.getText().startsWith("@")) {
                String text = search.getText().substring(1);

                // Custom search filters and stuff.. yay? Add on to this if you want...
                if (text.equalsIgnoreCase("empty")) {
                    return XIV.getInstance().getAltManager().getContents().stream().filter(account -> account.getKeyword().isEmpty()).collect(Collectors.toCollection(ArrayList::new));
                } else if (text.equalsIgnoreCase("notempty") || text.equalsIgnoreCase("nonempty") || text.equalsIgnoreCase("!empty")) {
                    return XIV.getInstance().getAltManager().getContents().stream().filter(account -> !account.getKeyword().isEmpty()).collect(Collectors.toCollection(ArrayList::new));
                }
                return XIV.getInstance().getAltManager().getContents();
            } else if (search.getText().startsWith("!")) {
                String text = search.getText().substring(1);

                // Search for opposite of text given
                return XIV.getInstance().getAltManager().getContents().stream().filter(account -> !account.getUsername().toLowerCase().contains(text.toLowerCase()) && !account.getKeyword().toLowerCase().contains(text.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
            }
            return XIV.getInstance().getAltManager().getContents().stream().filter(account -> account.getUsername().toLowerCase().contains(search.getText().toLowerCase()) || account.getKeyword().toLowerCase().contains(search.getText().toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        } else {
            return XIV.getInstance().getAltManager().getContents();
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        this.slot = new AltSlot(this, mc, width, height, 25, height - 98, 36);
        this.slot.registerScrollButtons(7, 8);

        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height - 26, 200, 20, "Cancel"));

        this.buttonList.add(new GuiButton(1, width / 2 + 2, height - 70, 98, 20, "Login"));

        this.buttonList.add(new GuiButton(2, width / 2 - 100, height - 92, 98, 20, "Add"));
        this.buttonList.add(new GuiButton(3, width / 2 + 2, height - 92, 98, 20, "Remove"));

        this.buttonList.add(new GuiButton(4, width / 2 - 100, height - 48, 98, 20, "Random"));

        this.buttonList.add(new GuiButton(5, width - 28, height - 86, 20, 20, "+"));

        this.buttonList.add(new GuiButton(6, 7, height - 26, (153 / 2) - 2, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(7, 7 + (153 / 2), height - 26, (153 / 2) - 2, 20, "Set Current"));

        this.buttonList.add(new GuiButton(8, width / 2 - 100, height - 70, 98, 20, "Edit"));
        this.buttonList.add(new GuiButton(9, width / 2 + 2, height - 48, 98, 20, "Import Alts"));

        this.username = new GuiTextField(0, mc.fontRendererObj, 8, height - 86, 150, 20);
        this.username.setVisible(true);

        this.password = new GuiPasswordField(1, mc.fontRendererObj, 8, height - 52, 150, 20);
        this.password.setVisible(true);

        this.keyword = new GuiTextField(3, mc.fontRendererObj, width - 182, height - 86, 150, 20);
        this.keyword.setVisible(true);
        if (slot.getAlt() != null)
            this.keyword.setText(slot.getAlt().getKeyword());

        this.search = new GuiTextField(3, mc.fontRendererObj, width - 182, height - 52, 150, 20);
        this.search.setVisible(true);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.enabled) {
            if (button.id == 0) {
                mc.displayGuiScreen(parent);
            } else if (button.id == 1) {
                login(slot.getAlt());
            } else if (button.id == 2) {
                XIV.getInstance().getAltManager().add(username.getText(), password.getText());
                username.setText("");
                password.setText("");
            } else if (button.id == 3) {
                GuiYesNo gui = new GuiYesNo(this, "Are you sure you want to remove the alt \"" + this.slot.getAlt().getUsername() + "\"?", "", 2);
                mc.displayGuiScreen(gui);
            } else if (button.id == 4) {
                Random random = new Random();

                if (this.slot.getSize() > 1) {
                    int next = random.nextInt(this.slot.getSize());
                    if (this.slot.getSelected() != next) {
                        this.slot.setSelected(next);

                        this.actionPerformed(buttonList.get(1));
                    } else {
                        this.actionPerformed(button);
                    }
                }
            } else if (button.id == 5) {
                slot.getAlt().setKeyword(keyword.getText());
            } else if (button.id == 6) {
                login(new AltAccount(username.getText(), password.getText()));

                username.setText("");
                password.setText("");
            } else if (button.id == 7) {
                AltAccount current = this.slot.getAlt();

                current.setUsername(username.getText());
                current.setPassword(password.getText());

                username.setText("");
                password.setText("");
            } else if (button.id == 8) {
                AltAccount current = this.slot.getAlt();

                username.setText(current.getUsername());
                password.setText(current.getPassword());
            } else if (button.id == 9) {
                Thread fileFindThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setDialogTitle("XIV Alt Finder");
                        chooser.setVisible(true);
                        chooser.setMultiSelectionEnabled(false);

                        File foundFile = null;

                        int returnValue = chooser.showOpenDialog(null);

                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            foundFile = chooser.getSelectedFile();
                        }

                        if (foundFile != null) {
                            int lastSize = getAccounts().size();

                            try {
                                BufferedReader reader = new BufferedReader(new FileReader(foundFile));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    String[] account = line.split(":");

                                    if (account.length > 1) {
                                        if (account.length > 2) {
                                            XIV.getInstance().getAltManager().add(account[0], account[1], account[2]);
                                        } else {
                                            XIV.getInstance().getAltManager().add(account[0], account[1]);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (getAccounts().size() > lastSize) {
                                slot.setSelected(lastSize);
                            }
                        }
                    }
                });
                fileFindThread.start();
            }
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        XIV.getInstance().getFileManager().saveFile("alts");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.slot.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (slot.getAlt() == null) {
            buttonList.get(8).enabled = false;
            buttonList.get(4).enabled = false;
            buttonList.get(3).enabled = false;
            buttonList.get(1).enabled = false;
        } else {
            buttonList.get(8).enabled = true;
            buttonList.get(4).enabled = true;
            buttonList.get(3).enabled = true;
            buttonList.get(1).enabled = true;
        }

        if (slot.getAlt() == null) {
            buttonList.get(5).enabled = false;
        } else {
            if (keyword.getText().equals("")) {
                buttonList.get(5).displayString = "x";
                buttonList.get(5).enabled = !slot.getAlt().getKeyword().equals("");
            } else {
                buttonList.get(5).displayString = "+";
                buttonList.get(5).enabled = true;
            }
        }
        buttonList.get(2).enabled = !username.getText().equals("") && !password.getText().equals("");
        buttonList.get(7).enabled = !username.getText().equals("") && !password.getText().equals("") && this.slot.getAlt() != null;

        mc.fontRendererObj.drawStringWithShadow("Username:", 8, height - 96, 0xFFFFFFFF);
        username.drawTextBox();

        mc.fontRendererObj.drawStringWithShadow("Password:", 8, height - 62, 0xFFFFFFFF);
        password.drawTextBox();

        mc.fontRendererObj.drawStringWithShadow("Keyword:", width - 182, height - 96, 0xFFFFFFFF);
        keyword.drawTextBox();

        String filters = "Custom Filters: '!', '@empty', '@!empty', 'k:'";
        mc.fontRendererObj.drawStringWithShadow(filters, width - 105 - (mc.fontRendererObj.getStringWidth(filters) / 2), height - 28, 0xFFFFFFFF);

        mc.fontRendererObj.drawStringWithShadow("Search:", width - 182, height - 62, 0xFFFFFFFF);
        search.drawTextBox();

        drawCenteredString(mc.fontRendererObj, String.format("Accounts: \247a%s\247f/\247c%s\247f/\247e%s\247f", getAccounts().size(), XIV.getInstance().getAltManager().getContents().size() - getAccounts().size(), XIV.getInstance().getAltManager().getContents().size()), width / 2, 2, 0xFFFFFFFF);
        drawCenteredString(mc.fontRendererObj, thread == null ? "\247aLogged in as\247r " + mc.getSession().getUsername() : thread.getStatus(), width / 2, 12, 0xFFFFFFFF);

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parent);
        } else if (keyCode == Keyboard.KEY_UP) {
            if(this.slot.getAlt(this.slot.getSelected() - 1) != null) {
                this.slot.setSelected(this.slot.getSelected() - 1);
            }
        } else if (keyCode == Keyboard.KEY_DOWN) {
            if(this.slot.getAlt(this.slot.getSelected() + 1) != null) {
                this.slot.setSelected(this.slot.getSelected() + 1);
            }
        } else if (keyCode == Keyboard.KEY_DELETE) {
            if(this.slot.getAlt() != null) {
                this.actionPerformed(this.buttonList.get(3));
            }
        }

        if (keyCode == Keyboard.KEY_TAB) {
            if (username.isFocused()) {
                username.setFocused(!username.isFocused());
                password.setFocused(true);
            } else if (password.isFocused()) {
                password.setFocused(!password.isFocused());
                keyword.setFocused(true);
            } else if (keyword.isFocused()) {
                keyword.setFocused(!keyword.isFocused());
                search.setFocused(true);
            } else if (search.isFocused()) {
                search.setFocused(!search.isFocused());
                username.setFocused(true);
            } else if (!username.isFocused() && !password.isFocused() && !keyword.isFocused() && !search.isFocused()) {
                username.setFocused(true);
            }
        }

        username.textboxKeyTyped(typedChar, keyCode);
        password.textboxKeyTyped(typedChar, keyCode);
        keyword.textboxKeyTyped(typedChar, keyCode);
        search.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == Keyboard.KEY_RETURN) {
            if (keyword.isFocused()) {
                this.actionPerformed(buttonList.get(5));
            } else if (!username.getText().equals("") && !password.getText().equals("")) {
                this.actionPerformed(buttonList.get(2));
            } else if(this.slot.getAlt() != null) {
                this.actionPerformed(buttonList.get(1));
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        username.mouseClicked(mouseX, mouseY, mouseButton);
        password.mouseClicked(mouseX, mouseY, mouseButton);
        keyword.mouseClicked(mouseX, mouseY, mouseButton);
        search.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        username.updateCursorCounter();
        password.updateCursorCounter();
        keyword.updateCursorCounter();
        search.updateCursorCounter();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        this.slot.func_178039_p();
    }

    public void login(AltAccount alt) {
        thread = new AuthThread(alt);
        new Thread(thread, "Account Authentication Thread").start();
    }

    @Override
    public void confirmClicked(boolean yes, int idk) {
        if (yes)
            XIV.getInstance().getAltManager().remove(slot.getAlt().getUsername());
        mc.displayGuiScreen(this);
    }
}
