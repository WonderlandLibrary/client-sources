/*
 * Decompiled with CFR 0.145.
 */
package superblaubeere27;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import superblaubeere27.particle.ParticleSystem;

public class ParticleSystemDemo
extends BasicGame {
    private ParticleSystem particleSystem;
    private boolean mouse;
    private boolean rainbow;
    private int dist;

    public ParticleSystemDemo() {
        super("ParticleSystem");
    }

    public static void main(String[] args) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(6, 2));
        JSpinner width = new JSpinner();
        width.setValue(1280);
        JSpinner height = new JSpinner();
        height.setValue(720);
        JCheckBox fullScreen = new JCheckBox("");
        JCheckBox connectMouse = new JCheckBox("");
        JCheckBox rainBow = new JCheckBox("");
        JLabel distLabel = new JLabel("connectingDistance (150):");
        JSlider dist = new JSlider(0, 100, 500, 150);
        dist.addChangeListener(e2 -> distLabel.setText("connectingDistance (".concat(String.valueOf(dist.getValue()).concat("):"))));
        jPanel.add(new JLabel("Width:"));
        jPanel.add(width);
        jPanel.add(new JLabel("Height:"));
        jPanel.add(height);
        jPanel.add(new JLabel("Fullscreen:"));
        jPanel.add(fullScreen);
        jPanel.add(new JLabel("Rainbow:"));
        jPanel.add(rainBow);
        jPanel.add(new JLabel("ConnectMouse:"));
        jPanel.add(connectMouse);
        jPanel.add(distLabel);
        jPanel.add(dist);
        if (JOptionPane.showConfirmDialog(null, jPanel, "ParticleSystem settings", 2) != 0) {
            return;
        }
        try {
            ParticleSystemDemo demo = new ParticleSystemDemo();
            AppGameContainer container = new AppGameContainer(demo);
            demo.mouse = connectMouse.isSelected();
            demo.dist = dist.getValue();
            demo.rainbow = rainBow.isSelected();
            container.setDisplayMode((Integer)width.getValue(), (Integer)height.getValue(), fullScreen.isSelected());
            container.setVSync(true);
            container.setShowFPS(true);
            container.start();
        }
        catch (SlickException e3) {
            JOptionPane.showMessageDialog(null, e3.getLocalizedMessage(), "ERROR", 0);
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.particleSystem = new ParticleSystem(200, this.mouse, this.rainbow, this.dist);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        this.particleSystem.tick(delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.clearAlphaMap();
        graphics.clear();
        this.particleSystem.render();
    }
}

