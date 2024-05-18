package pw.latematt.xiv.mod.mods.misc.tray;

import pw.latematt.xiv.XIV;
import pw.latematt.xiv.mod.ModType;

import javax.swing.*;
import java.awt.*;
import java.awt.TrayIcon;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

/**
 * @author Rederpz
 */
public class XIVTray {
    private ExternalFrame externalGUI;
    private TrayIcon icon;

    public XIVTray() {
        if (!SystemTray.isSupported())
            return;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        URL iconUrl = this.getClass().getResource("/trayicon.png");
        Toolkit tk = new JFrame().getToolkit();
        this.icon = new TrayIcon(tk.getImage(iconUrl), "XIV");

        PopupMenu menu = new PopupMenu();
        this.icon.setPopupMenu(menu);

        this.externalGUI = new ExternalFrame();

        MenuItem showExternal = new MenuItem("Show External GUI");
        showExternal.addActionListener(e -> externalGUI.load());

        MenuItem hideClient = new MenuItem("Hide Client");
        hideClient.addActionListener(e -> {
            XIV.getInstance().getListenerManager().setCancelled(!XIV.getInstance().getListenerManager().isCancelled());
            icon.displayMessage("XIV", String.format("Client is now %s.", XIV.getInstance().getListenerManager().isCancelled() ? "hidden" : "shown"), TrayIcon.MessageType.INFO);
            hideClient.setLabel(String.format("%s Client", XIV.getInstance().getListenerManager().isCancelled() ? "Show" : "Hide"));
        });

        showExternal.setEnabled(true);
        hideClient.setEnabled(true);
        menu.add(showExternal);
        menu.add(hideClient);
        if (XIV.getInstance().getModManager().find("trayicon") != null) {
            MenuItem hideTray = new MenuItem("Hide Tray Icon");
            hideTray.addActionListener(e -> XIV.getInstance().getModManager().find("trayicon").setEnabled(false));
            hideTray.setEnabled(true);
            menu.add(hideTray);
        }
    }

    public void load() throws AWTException {
        SystemTray.getSystemTray().remove(icon);
        SystemTray.getSystemTray().add(icon);
    }

    public void unload() {
        externalGUI.unload();
        SystemTray.getSystemTray().remove(icon);
    }

    private class ExternalFrame extends JFrame {
        private final Color background = new Color(238, 238, 238);
        private final Font font = new Font("Verdana", Font.BOLD, 12);

        public ExternalFrame() {
            setTitle("XIV External GUI");
            setResizable(false);
            getContentPane().setLayout(new GridLayout(1, 2));
            setFont(font);
            setBackground(background);
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
                e.printStackTrace();
            }

            JTabbedPane pane = new JTabbedPane();
            pane.setBackground(background);
            pane.setFont(font);

            for (ModType type : ModType.values()) {
                JPanel panel = new JPanel(new GridLayout(0, 2));

                XIV.getInstance().getModManager().getContents().stream()
                        .filter(mod -> mod.getModType() == type)
                        .forEach(mod -> {
                            JCheckBox box = new JCheckBox(mod.getName());
                            box.setSelected(mod.isEnabled());
                            box.setFont(font);
                            box.setBackground(background);

                            box.addItemListener(e -> mod.setEnabled(e.getStateChange() == ItemEvent.SELECTED));
                            panel.add(box, "Center");
                        });

                if ((XIV.getInstance().getModManager().getContents().size() & 1) != 0) {
                    JTextArea area = new JTextArea();
                    area.setFocusable(false);
                    area.setEditable(false);
                    area.setBackground(background);
                    area.setFont(font);

                    panel.add(area, "Center");
                }

                pane.addTab(type.getName(), panel);
            }

            JPanel panel = new JPanel(new GridLayout(0, 2));

            JLabel label = new JLabel("Command Input: ");
            label.setFont(font);
            label.setBackground(background);

            panel.add(label, "Center");

            final JTextField field = new JTextField("");
            field.setEditable(true);
            field.setFocusable(true);
            field.setFont(font);
            field.setBackground(background);
            field.addKeyListener(new KeyListener() {
                @Override
                public void keyPressed(KeyEvent key) {
                    if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                        XIV.getInstance().getCommandManager().parseCommand(XIV.getInstance().getCommandManager().getPrefix() + field.getText());
                        field.setText("");
                    }
                }

                @Override
                public void keyReleased(KeyEvent key) {
                }

                @Override
                public void keyTyped(KeyEvent key) {
                }
            });

            panel.add(field, "Center");
            pane.add("Commands", panel);
            getContentPane().add(pane, "Center");
            pack();
        }

        public void load() {
            this.setVisible(true);
        }

        public void unload() {
            this.setVisible(false);
        }
    }
}