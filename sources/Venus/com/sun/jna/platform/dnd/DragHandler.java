/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.dnd;

import com.sun.jna.platform.dnd.GhostedDragImage;
import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;

public abstract class DragHandler
implements DragSourceListener,
DragSourceMotionListener,
DragGestureListener {
    public static final Dimension MAX_GHOST_SIZE = new Dimension(250, 250);
    public static final float DEFAULT_GHOST_ALPHA = 0.5f;
    public static final int UNKNOWN_MODIFIERS = -1;
    public static final Transferable UNKNOWN_TRANSFERABLE = null;
    protected static final int MOVE = 2;
    protected static final int COPY = 1;
    protected static final int LINK = 0x40000000;
    protected static final int NONE = 0;
    static final int MOVE_MASK = 64;
    static final boolean OSX = System.getProperty("os.name").toLowerCase().indexOf("mac") != -1;
    static final int COPY_MASK = OSX ? 512 : 128;
    static final int LINK_MASK = OSX ? 768 : 192;
    static final int KEY_MASK = 9152;
    private static int modifiers = -1;
    private static Transferable transferable = UNKNOWN_TRANSFERABLE;
    private int supportedActions;
    private boolean fixCursor = true;
    private Component dragSource;
    private GhostedDragImage ghost;
    private Point imageOffset;
    private Dimension maxGhostSize = MAX_GHOST_SIZE;
    private float ghostAlpha = 0.5f;
    private String lastAction;
    private boolean moved;

    static int getModifiers() {
        return modifiers;
    }

    public static Transferable getTransferable(DropTargetEvent dropTargetEvent) {
        if (dropTargetEvent instanceof DropTargetDragEvent) {
            try {
                return (Transferable)dropTargetEvent.getClass().getMethod("getTransferable", null).invoke(dropTargetEvent, null);
            } catch (Exception exception) {
            }
        } else if (dropTargetEvent instanceof DropTargetDropEvent) {
            return ((DropTargetDropEvent)dropTargetEvent).getTransferable();
        }
        return transferable;
    }

    protected DragHandler(Component component, int n) {
        Object object;
        this.dragSource = component;
        this.supportedActions = n;
        try {
            String[] stringArray;
            String string;
            object = System.getProperty("DragHandler.alpha");
            if (object != null) {
                try {
                    this.ghostAlpha = Float.parseFloat((String)object);
                } catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            if ((string = System.getProperty("DragHandler.maxDragImageSize")) != null && (stringArray = string.split("x")).length == 2) {
                try {
                    this.maxGhostSize = new Dimension(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[5]));
                } catch (NumberFormatException numberFormatException) {}
            }
        } catch (SecurityException securityException) {
            // empty catch block
        }
        this.disableSwingDragSupport(component);
        object = DragSource.getDefaultDragSource();
        ((DragSource)object).createDefaultDragGestureRecognizer(component, this.supportedActions, this);
    }

    private void disableSwingDragSupport(Component component) {
        if (component instanceof JTree) {
            ((JTree)component).setDragEnabled(true);
        } else if (component instanceof JList) {
            ((JList)component).setDragEnabled(true);
        } else if (component instanceof JTable) {
            ((JTable)component).setDragEnabled(true);
        } else if (component instanceof JTextComponent) {
            ((JTextComponent)component).setDragEnabled(true);
        } else if (component instanceof JColorChooser) {
            ((JColorChooser)component).setDragEnabled(true);
        } else if (component instanceof JFileChooser) {
            ((JFileChooser)component).setDragEnabled(true);
        }
    }

    protected boolean canDrag(DragGestureEvent dragGestureEvent) {
        int n = dragGestureEvent.getTriggerEvent().getModifiersEx() & 0x23C0;
        if (n == 64) {
            return (this.supportedActions & 2) != 0;
        }
        if (n == COPY_MASK) {
            return (this.supportedActions & 1) != 0;
        }
        if (n == LINK_MASK) {
            return (this.supportedActions & 0x40000000) != 0;
        }
        return false;
    }

    protected void setModifiers(int n) {
        modifiers = n;
    }

    protected abstract Transferable getTransferable(DragGestureEvent var1);

    protected Icon getDragIcon(DragGestureEvent dragGestureEvent, Point point) {
        return null;
    }

    protected void dragStarted(DragGestureEvent dragGestureEvent) {
    }

    public void dragGestureRecognized(DragGestureEvent dragGestureEvent) {
        block7: {
            if ((dragGestureEvent.getDragAction() & this.supportedActions) != 0 && this.canDrag(dragGestureEvent)) {
                this.setModifiers(dragGestureEvent.getTriggerEvent().getModifiersEx() & 0x23C0);
                Transferable transferable = this.getTransferable(dragGestureEvent);
                if (transferable == null) {
                    return;
                }
                try {
                    Point point = new Point(0, 0);
                    Icon icon = this.getDragIcon(dragGestureEvent, point);
                    Point point2 = dragGestureEvent.getDragOrigin();
                    this.imageOffset = new Point(point.x - point2.x, point.y - point2.y);
                    Icon icon2 = this.scaleDragIcon(icon, this.imageOffset);
                    Cursor cursor = null;
                    if (icon2 != null && DragSource.isDragImageSupported()) {
                        GraphicsConfiguration graphicsConfiguration = dragGestureEvent.getComponent().getGraphicsConfiguration();
                        dragGestureEvent.startDrag(cursor, this.createDragImage(graphicsConfiguration, icon2), this.imageOffset, transferable, this);
                    } else {
                        if (icon2 != null) {
                            Point point3 = this.dragSource.getLocationOnScreen();
                            point3.translate(point2.x, point2.y);
                            Point point4 = new Point(-this.imageOffset.x, -this.imageOffset.y);
                            this.ghost = new GhostedDragImage(this.dragSource, icon2, this.getImageLocation(point3), point4);
                            this.ghost.setAlpha(this.ghostAlpha);
                        }
                        dragGestureEvent.startDrag(cursor, transferable, this);
                    }
                    this.dragStarted(dragGestureEvent);
                    this.moved = false;
                    dragGestureEvent.getDragSource().addDragSourceMotionListener(this);
                    DragHandler.transferable = transferable;
                } catch (InvalidDnDOperationException invalidDnDOperationException) {
                    if (this.ghost == null) break block7;
                    this.ghost.dispose();
                    this.ghost = null;
                }
            }
        }
    }

    protected Icon scaleDragIcon(Icon icon, Point point) {
        return icon;
    }

    protected Image createDragImage(GraphicsConfiguration graphicsConfiguration, Icon icon) {
        int n = icon.getIconWidth();
        int n2 = icon.getIconHeight();
        BufferedImage bufferedImage = graphicsConfiguration.createCompatibleImage(n, n2, 3);
        Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setComposite(AlphaComposite.Clear);
        graphics2D.fillRect(0, 0, n, n2);
        graphics2D.setComposite(AlphaComposite.getInstance(2, this.ghostAlpha));
        icon.paintIcon(this.dragSource, graphics2D, 0, 0);
        graphics2D.dispose();
        return bufferedImage;
    }

    private int reduce(int n) {
        if ((n & 2) != 0 && n != 2) {
            return 1;
        }
        if ((n & 1) != 0 && n != 1) {
            return 0;
        }
        return n;
    }

    protected Cursor getCursorForAction(int n) {
        switch (n) {
            case 2: {
                return DragSource.DefaultMoveDrop;
            }
            case 1: {
                return DragSource.DefaultCopyDrop;
            }
            case 0x40000000: {
                return DragSource.DefaultLinkDrop;
            }
        }
        return DragSource.DefaultMoveNoDrop;
    }

    protected int getAcceptableDropAction(int n) {
        return this.reduce(this.supportedActions & n);
    }

    protected int getDropAction(DragSourceEvent dragSourceEvent) {
        if (dragSourceEvent instanceof DragSourceDragEvent) {
            DragSourceDragEvent dragSourceDragEvent = (DragSourceDragEvent)dragSourceEvent;
            return dragSourceDragEvent.getDropAction();
        }
        if (dragSourceEvent instanceof DragSourceDropEvent) {
            return ((DragSourceDropEvent)dragSourceEvent).getDropAction();
        }
        return 1;
    }

    protected int adjustDropAction(DragSourceEvent dragSourceEvent) {
        int n = this.getDropAction(dragSourceEvent);
        if (dragSourceEvent instanceof DragSourceDragEvent) {
            int n2;
            DragSourceDragEvent dragSourceDragEvent = (DragSourceDragEvent)dragSourceEvent;
            if (n == 0 && (n2 = dragSourceDragEvent.getGestureModifiersEx() & 0x23C0) == 0) {
                n = this.getAcceptableDropAction(dragSourceDragEvent.getTargetActions());
            }
        }
        return n;
    }

    protected void updateCursor(DragSourceEvent dragSourceEvent) {
        if (!this.fixCursor) {
            return;
        }
        Cursor cursor = this.getCursorForAction(this.adjustDropAction(dragSourceEvent));
        dragSourceEvent.getDragSourceContext().setCursor(cursor);
    }

    static String actionString(int n) {
        switch (n) {
            case 2: {
                return "MOVE";
            }
            case 3: {
                return "MOVE|COPY";
            }
            case 0x40000002: {
                return "MOVE|LINK";
            }
            case 0x40000003: {
                return "MOVE|COPY|LINK";
            }
            case 1: {
                return "COPY";
            }
            case 0x40000001: {
                return "COPY|LINK";
            }
            case 0x40000000: {
                return "LINK";
            }
        }
        return "NONE";
    }

    private void describe(String string, DragSourceEvent dragSourceEvent) {
    }

    public void dragDropEnd(DragSourceDropEvent dragSourceDropEvent) {
        this.describe("end", dragSourceDropEvent);
        this.setModifiers(-1);
        transferable = UNKNOWN_TRANSFERABLE;
        if (this.ghost != null) {
            if (dragSourceDropEvent.getDropSuccess()) {
                this.ghost.dispose();
            } else {
                this.ghost.returnToOrigin();
            }
            this.ghost = null;
        }
        DragSource dragSource = dragSourceDropEvent.getDragSourceContext().getDragSource();
        dragSource.removeDragSourceMotionListener(this);
        this.moved = false;
    }

    private Point getImageLocation(Point point) {
        point.translate(this.imageOffset.x, this.imageOffset.y);
        return point;
    }

    public void dragEnter(DragSourceDragEvent dragSourceDragEvent) {
        this.describe("enter", dragSourceDragEvent);
        if (this.ghost != null) {
            this.ghost.move(this.getImageLocation(dragSourceDragEvent.getLocation()));
        }
        this.updateCursor(dragSourceDragEvent);
    }

    public void dragMouseMoved(DragSourceDragEvent dragSourceDragEvent) {
        this.describe("move", dragSourceDragEvent);
        if (this.ghost != null) {
            this.ghost.move(this.getImageLocation(dragSourceDragEvent.getLocation()));
        }
        if (this.moved) {
            this.updateCursor(dragSourceDragEvent);
        }
        this.moved = true;
    }

    public void dragOver(DragSourceDragEvent dragSourceDragEvent) {
        this.describe("over", dragSourceDragEvent);
        if (this.ghost != null) {
            this.ghost.move(this.getImageLocation(dragSourceDragEvent.getLocation()));
        }
        this.updateCursor(dragSourceDragEvent);
    }

    public void dragExit(DragSourceEvent dragSourceEvent) {
        this.describe("exit", dragSourceEvent);
    }

    public void dropActionChanged(DragSourceDragEvent dragSourceDragEvent) {
        this.describe("change", dragSourceDragEvent);
        this.setModifiers(dragSourceDragEvent.getGestureModifiersEx() & 0x23C0);
        if (this.ghost != null) {
            this.ghost.move(this.getImageLocation(dragSourceDragEvent.getLocation()));
        }
        this.updateCursor(dragSourceDragEvent);
    }
}

