package ez.h.features.visual;

import java.util.*;
import java.util.concurrent.atomic.*;
import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class ChunkAnimator extends Feature
{
    final WeakHashMap<bxr, AtomicLong> lifespans;
    OptionSlider delay;
    
    @EventTarget
    public void onChunkRender(final EventRenderChunk eventRenderChunk) {
        if (ChunkAnimator.mc.h == null || ChunkAnimator.mc.f == null) {
            return;
        }
        if (!this.lifespans.containsKey(eventRenderChunk.RenderChunk)) {
            this.lifespans.put(eventRenderChunk.RenderChunk, new AtomicLong(-1L));
        }
    }
    
    @EventTarget
    public void onChunk(final EventRenderChunkContainer eventRenderChunkContainer) {
        if (this.lifespans.containsKey(eventRenderChunkContainer.RenderChunk)) {
            final AtomicLong atomicLong = this.lifespans.get(eventRenderChunkContainer.RenderChunk);
            long n = atomicLong.get();
            if (n == -1L) {
                n = System.currentTimeMillis();
                atomicLong.set(n);
            }
            final long n2 = System.currentTimeMillis() - n;
            if (n2 <= (int)this.delay.getNum()) {
                final double n3 = eventRenderChunkContainer.RenderChunk.k().q();
                bus.b(0.0, -n3 + n3 / (int)this.delay.getNum() * n2, 0.0);
            }
        }
    }
    
    public ChunkAnimator() {
        super("ChunkAnimator", "\u0410\u043d\u0438\u043c\u0438\u0440\u0443\u0435\u0442 \u043f\u043e\u044f\u0432\u043b\u0435\u043d\u0438\u0435 \u0447\u0430\u043d\u043a\u043e\u0432.", Category.VISUAL);
        this.lifespans = new WeakHashMap<bxr, AtomicLong>();
        this.delay = new OptionSlider(this, "Speed", 1000.0f, 250.0f, 5000.0f, OptionSlider.SliderType.MS);
        this.addOptions(this.delay);
    }
}
