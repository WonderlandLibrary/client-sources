/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.gui;

import com.google.common.collect.Lists;
import com.mojang.util.QueueLogAppender;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.gui.PlayerListComponent;
import net.minecraft.server.gui.StatsComponent;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftServerGui
extends JComponent {
    private static final Font SERVER_GUI_FONT = new Font("Monospaced", 0, 12);
    private static final Logger LOGGER = LogManager.getLogger();
    private final DedicatedServer server;
    private Thread field_206932_d;
    private final Collection<Runnable> field_219051_e = Lists.newArrayList();
    private final AtomicBoolean field_219052_f = new AtomicBoolean();

    public static MinecraftServerGui func_219048_a(DedicatedServer dedicatedServer) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            // empty catch block
        }
        JFrame jFrame = new JFrame("Minecraft server");
        MinecraftServerGui minecraftServerGui = new MinecraftServerGui(dedicatedServer);
        jFrame.setDefaultCloseOperation(2);
        jFrame.add(minecraftServerGui);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(false);
        jFrame.addWindowListener(new WindowAdapter(minecraftServerGui, jFrame, dedicatedServer){
            final MinecraftServerGui val$minecraftservergui;
            final JFrame val$jframe;
            final DedicatedServer val$p_219048_0_;
            {
                this.val$minecraftservergui = minecraftServerGui;
                this.val$jframe = jFrame;
                this.val$p_219048_0_ = dedicatedServer;
            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (!this.val$minecraftservergui.field_219052_f.getAndSet(false)) {
                    this.val$jframe.setTitle("Minecraft server - shutting down!");
                    this.val$p_219048_0_.initiateShutdown(false);
                    this.val$minecraftservergui.func_219046_f();
                }
            }
        });
        minecraftServerGui.func_219045_a(jFrame::dispose);
        minecraftServerGui.start();
        return minecraftServerGui;
    }

    private MinecraftServerGui(DedicatedServer dedicatedServer) {
        this.server = dedicatedServer;
        this.setPreferredSize(new Dimension(854, 480));
        this.setLayout(new BorderLayout());
        try {
            this.add((Component)this.getLogComponent(), "Center");
            this.add((Component)this.getStatsComponent(), "West");
        } catch (Exception exception) {
            LOGGER.error("Couldn't build server GUI", (Throwable)exception);
        }
    }

    public void func_219045_a(Runnable runnable) {
        this.field_219051_e.add(runnable);
    }

    private JComponent getStatsComponent() {
        JPanel jPanel = new JPanel(new BorderLayout());
        StatsComponent statsComponent = new StatsComponent(this.server);
        this.field_219051_e.add(statsComponent::func_219053_a);
        jPanel.add((Component)statsComponent, "North");
        jPanel.add((Component)this.getPlayerListComponent(), "Center");
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
        return jPanel;
    }

    private JComponent getPlayerListComponent() {
        PlayerListComponent playerListComponent = new PlayerListComponent(this.server);
        JScrollPane jScrollPane = new JScrollPane(playerListComponent, 22, 30);
        jScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
        return jScrollPane;
    }

    private JComponent getLogComponent() {
        JPanel jPanel = new JPanel(new BorderLayout());
        JTextArea jTextArea = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(jTextArea, 22, 30);
        jTextArea.setEditable(true);
        jTextArea.setFont(SERVER_GUI_FONT);
        JTextField jTextField = new JTextField();
        jTextField.addActionListener(arg_0 -> this.lambda$getLogComponent$0(jTextField, arg_0));
        jTextArea.addFocusListener(new FocusAdapter(this){
            final MinecraftServerGui this$0;
            {
                this.this$0 = minecraftServerGui;
            }

            @Override
            public void focusGained(FocusEvent focusEvent) {
            }
        });
        jPanel.add((Component)jScrollPane, "Center");
        jPanel.add((Component)jTextField, "South");
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
        this.field_206932_d = new Thread(() -> this.lambda$getLogComponent$1(jTextArea, jScrollPane));
        this.field_206932_d.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        this.field_206932_d.setDaemon(false);
        return jPanel;
    }

    public void start() {
        this.field_206932_d.start();
    }

    public void func_219050_b() {
        if (!this.field_219052_f.getAndSet(false)) {
            this.func_219046_f();
        }
    }

    private void func_219046_f() {
        this.field_219051_e.forEach(Runnable::run);
    }

    public void appendLine(JTextArea jTextArea, JScrollPane jScrollPane, String string) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> this.lambda$appendLine$2(jTextArea, jScrollPane, string));
        } else {
            Document document = jTextArea.getDocument();
            JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
            boolean bl = false;
            if (jScrollPane.getViewport().getView() == jTextArea) {
                bl = (double)jScrollBar.getValue() + jScrollBar.getSize().getHeight() + (double)(SERVER_GUI_FONT.getSize() * 4) > (double)jScrollBar.getMaximum();
            }
            try {
                document.insertString(document.getLength(), string, null);
            } catch (BadLocationException badLocationException) {
                // empty catch block
            }
            if (bl) {
                jScrollBar.setValue(Integer.MAX_VALUE);
            }
        }
    }

    private void lambda$appendLine$2(JTextArea jTextArea, JScrollPane jScrollPane, String string) {
        this.appendLine(jTextArea, jScrollPane, string);
    }

    private void lambda$getLogComponent$1(JTextArea jTextArea, JScrollPane jScrollPane) {
        String string;
        while ((string = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
            this.appendLine(jTextArea, jScrollPane, string);
        }
    }

    private void lambda$getLogComponent$0(JTextField jTextField, ActionEvent actionEvent) {
        String string = jTextField.getText().trim();
        if (!string.isEmpty()) {
            this.server.handleConsoleInput(string, this.server.getCommandSource());
        }
        jTextField.setText("");
    }
}

