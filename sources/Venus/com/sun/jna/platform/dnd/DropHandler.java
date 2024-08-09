/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.dnd;

import com.sun.jna.platform.dnd.DragHandler;
import com.sun.jna.platform.dnd.DropTargetPainter;
import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class DropHandler
implements DropTargetListener {
    private int acceptedActions;
    private List<DataFlavor> acceptedFlavors;
    private DropTarget dropTarget;
    private boolean active = true;
    private DropTargetPainter painter;
    private String lastAction;

    public DropHandler(Component component, int n) {
        this(component, n, new DataFlavor[0]);
    }

    public DropHandler(Component component, int n, DataFlavor[] dataFlavorArray) {
        this(component, n, dataFlavorArray, null);
    }

    public DropHandler(Component component, int n, DataFlavor[] dataFlavorArray, DropTargetPainter dropTargetPainter) {
        this.acceptedActions = n;
        this.acceptedFlavors = Arrays.asList(dataFlavorArray);
        this.painter = dropTargetPainter;
        this.dropTarget = new DropTarget(component, n, this, this.active);
    }

    protected DropTarget getDropTarget() {
        return this.dropTarget;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean bl) {
        this.active = bl;
        if (this.dropTarget != null) {
            this.dropTarget.setActive(bl);
        }
    }

    protected int getDropActionsForFlavors(DataFlavor[] dataFlavorArray) {
        return this.acceptedActions;
    }

    protected int getDropAction(DropTargetEvent dropTargetEvent) {
        int n;
        DropTargetEvent dropTargetEvent2;
        int n2 = 0;
        int n3 = 0;
        Point point = null;
        DataFlavor[] dataFlavorArray = new DataFlavor[]{};
        if (dropTargetEvent instanceof DropTargetDragEvent) {
            dropTargetEvent2 = (DropTargetDragEvent)dropTargetEvent;
            n2 = ((DropTargetDragEvent)dropTargetEvent2).getDropAction();
            n3 = ((DropTargetDragEvent)dropTargetEvent2).getSourceActions();
            dataFlavorArray = ((DropTargetDragEvent)dropTargetEvent2).getCurrentDataFlavors();
            point = ((DropTargetDragEvent)dropTargetEvent2).getLocation();
        } else if (dropTargetEvent instanceof DropTargetDropEvent) {
            dropTargetEvent2 = (DropTargetDropEvent)dropTargetEvent;
            n2 = ((DropTargetDropEvent)dropTargetEvent2).getDropAction();
            n3 = ((DropTargetDropEvent)dropTargetEvent2).getSourceActions();
            dataFlavorArray = ((DropTargetDropEvent)dropTargetEvent2).getCurrentDataFlavors();
            point = ((DropTargetDropEvent)dropTargetEvent2).getLocation();
        }
        if (this.isSupported(dataFlavorArray) && (n2 = this.getDropAction(dropTargetEvent, n2, n3, n = this.getDropActionsForFlavors(dataFlavorArray))) != 0 && this.canDrop(dropTargetEvent, n2, point)) {
            return n2;
        }
        return 1;
    }

    protected int getDropAction(DropTargetEvent dropTargetEvent, int n, int n2, int n3) {
        int n4;
        boolean bl = this.modifiersActive(n);
        if ((n & n3) == 0 && !bl) {
            int n5;
            n = n5 = n3 & n2;
        } else if (bl && (n4 = n & n3 & n2) != n) {
            n = n4;
        }
        return n;
    }

    protected boolean modifiersActive(int n) {
        int n2 = DragHandler.getModifiers();
        if (n2 == -1) {
            return n != 0x40000000 && n != 1;
        }
        return n2 != 0;
    }

    private void describe(String string, DropTargetEvent dropTargetEvent) {
    }

    protected int acceptOrReject(DropTargetDragEvent dropTargetDragEvent) {
        int n = this.getDropAction(dropTargetDragEvent);
        if (n != 0) {
            dropTargetDragEvent.acceptDrag(n);
        } else {
            dropTargetDragEvent.rejectDrag();
        }
        return n;
    }

    public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
        this.describe("enter(tgt)", dropTargetDragEvent);
        int n = this.acceptOrReject(dropTargetDragEvent);
        this.paintDropTarget(dropTargetDragEvent, n, dropTargetDragEvent.getLocation());
    }

    public void dragOver(DropTargetDragEvent dropTargetDragEvent) {
        this.describe("over(tgt)", dropTargetDragEvent);
        int n = this.acceptOrReject(dropTargetDragEvent);
        this.paintDropTarget(dropTargetDragEvent, n, dropTargetDragEvent.getLocation());
    }

    public void dragExit(DropTargetEvent dropTargetEvent) {
        this.describe("exit(tgt)", dropTargetEvent);
        this.paintDropTarget(dropTargetEvent, 0, null);
    }

    public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {
        this.describe("change(tgt)", dropTargetDragEvent);
        int n = this.acceptOrReject(dropTargetDragEvent);
        this.paintDropTarget(dropTargetDragEvent, n, dropTargetDragEvent.getLocation());
    }

    public void drop(DropTargetDropEvent dropTargetDropEvent) {
        this.describe("drop(tgt)", dropTargetDropEvent);
        int n = this.getDropAction(dropTargetDropEvent);
        if (n != 0) {
            dropTargetDropEvent.acceptDrop(n);
            try {
                this.drop(dropTargetDropEvent, n);
                dropTargetDropEvent.dropComplete(false);
            } catch (Exception exception) {
                dropTargetDropEvent.dropComplete(true);
            }
        } else {
            dropTargetDropEvent.rejectDrop();
        }
        this.paintDropTarget(dropTargetDropEvent, 0, dropTargetDropEvent.getLocation());
    }

    protected boolean isSupported(DataFlavor[] dataFlavorArray) {
        HashSet<DataFlavor> hashSet = new HashSet<DataFlavor>(Arrays.asList(dataFlavorArray));
        hashSet.retainAll(this.acceptedFlavors);
        return !hashSet.isEmpty();
    }

    protected void paintDropTarget(DropTargetEvent dropTargetEvent, int n, Point point) {
        if (this.painter != null) {
            this.painter.paintDropTarget(dropTargetEvent, n, point);
        }
    }

    protected boolean canDrop(DropTargetEvent dropTargetEvent, int n, Point point) {
        return false;
    }

    protected abstract void drop(DropTargetDropEvent var1, int var2) throws UnsupportedFlavorException, IOException;
}

