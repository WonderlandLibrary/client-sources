/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.dnd;

import com.sun.jna.platform.WindowUtils;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GhostedDragImage {
    private static final float DEFAULT_ALPHA = 0.5f;
    private Window dragImage;
    private Point origin;
    private static final int SLIDE_INTERVAL = 33;

    public GhostedDragImage(Component component, Icon icon, Point point, Point point2) {
        Window window = component instanceof Window ? (Window)component : SwingUtilities.getWindowAncestor(component);
        GraphicsConfiguration graphicsConfiguration = window.getGraphicsConfiguration();
        this.dragImage = new Window(this, JOptionPane.getRootFrame(), graphicsConfiguration, icon){
            private static final long serialVersionUID = 1L;
            final Icon val$icon;
            final GhostedDragImage this$0;
            {
                this.this$0 = ghostedDragImage;
                this.val$icon = icon;
                super(window, graphicsConfiguration);
            }

            public void paint(Graphics graphics) {
                this.val$icon.paintIcon(this, graphics, 0, 0);
            }

            public Dimension getPreferredSize() {
                return new Dimension(this.val$icon.getIconWidth(), this.val$icon.getIconHeight());
            }

            public Dimension getMinimumSize() {
                return this.getPreferredSize();
            }

            public Dimension getMaximumSize() {
                return this.getPreferredSize();
            }
        };
        this.dragImage.setFocusableWindowState(true);
        this.dragImage.setName("###overrideRedirect###");
        Icon icon2 = new Icon(this, icon, point2){
            final Icon val$icon;
            final Point val$cursorOffset;
            final GhostedDragImage this$0;
            {
                this.this$0 = ghostedDragImage;
                this.val$icon = icon;
                this.val$cursorOffset = point;
            }

            public int getIconHeight() {
                return this.val$icon.getIconHeight();
            }

            public int getIconWidth() {
                return this.val$icon.getIconWidth();
            }

            public void paintIcon(Component component, Graphics graphics, int n, int n2) {
                graphics = graphics.create();
                Area area = new Area(new Rectangle(n, n2, this.getIconWidth(), this.getIconHeight()));
                area.subtract(new Area(new Rectangle(n + this.val$cursorOffset.x - 1, n2 + this.val$cursorOffset.y - 1, 3, 3)));
                graphics.setClip(area);
                this.val$icon.paintIcon(component, graphics, n, n2);
                graphics.dispose();
            }
        };
        this.dragImage.pack();
        WindowUtils.setWindowMask(this.dragImage, icon2);
        WindowUtils.setWindowAlpha(this.dragImage, 0.5f);
        this.move(point);
        this.dragImage.setVisible(false);
    }

    public void setAlpha(float f) {
        WindowUtils.setWindowAlpha(this.dragImage, f);
    }

    public void dispose() {
        this.dragImage.dispose();
        this.dragImage = null;
    }

    public void move(Point point) {
        if (this.origin == null) {
            this.origin = point;
        }
        this.dragImage.setLocation(point.x, point.y);
    }

    public void returnToOrigin() {
        Timer timer = new Timer(33, null);
        timer.addActionListener(new ActionListener(this, timer){
            final Timer val$timer;
            final GhostedDragImage this$0;
            {
                this.this$0 = ghostedDragImage;
                this.val$timer = timer;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                Point point = GhostedDragImage.access$000(this.this$0).getLocationOnScreen();
                Point point2 = new Point(GhostedDragImage.access$100(this.this$0));
                int n = (point2.x - point.x) / 2;
                int n2 = (point2.y - point.y) / 2;
                if (n != 0 || n2 != 0) {
                    point.translate(n, n2);
                    this.this$0.move(point);
                } else {
                    this.val$timer.stop();
                    this.this$0.dispose();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    static Window access$000(GhostedDragImage ghostedDragImage) {
        return ghostedDragImage.dragImage;
    }

    static Point access$100(GhostedDragImage ghostedDragImage) {
        return ghostedDragImage.origin;
    }
}

