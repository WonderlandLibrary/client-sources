package jaco.mp3.player.plaf;

import java.awt.*;

final class c implements LayoutManager
{
    private /* synthetic */ MP3PlayerUICompact a;
    
    c(final MP3PlayerUICompact a) {
        this.a = a;
    }
    
    @Override
    public final void layoutContainer(final Container container) {
        synchronized (container.getTreeLock()) {
            final Dimension size = container.getSize();
            final Dimension preferredSize = this.a.a.getPreferredSize();
            final Rectangle bounds;
            (bounds = new Rectangle(preferredSize)).x = (size.width - preferredSize.width) / 2;
            bounds.y = (size.height - preferredSize.height) / 2;
            this.a.a.setBounds(bounds);
        }
        // monitorexit(container.getTreeLock())
    }
    
    @Override
    public final Dimension preferredLayoutSize(final Container container) {
        final Insets insets = container.getInsets();
        final Dimension dimension2;
        final Dimension dimension = dimension2 = new Dimension(this.a.a.getPreferredSize());
        dimension.width = dimension2.width + insets.left + insets.right;
        dimension2.height = dimension2.height + insets.top + insets.bottom;
        return dimension2;
    }
    
    @Override
    public final Dimension minimumLayoutSize(final Container container) {
        final Insets insets = container.getInsets();
        final Dimension dimension2;
        final Dimension dimension = dimension2 = new Dimension(this.a.a.getMinimumSize());
        dimension.width = dimension2.width + insets.left + insets.right;
        dimension2.height = dimension2.height + insets.top + insets.bottom;
        return dimension2;
    }
    
    @Override
    public final void removeLayoutComponent(final Component component) {
    }
    
    @Override
    public final void addLayoutComponent(final String s, final Component component) {
    }
}
