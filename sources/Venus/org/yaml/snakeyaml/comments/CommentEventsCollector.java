/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.comments;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.Parser;

public class CommentEventsCollector {
    private List<CommentLine> commentLineList;
    private final Queue<Event> eventSource;
    private final CommentType[] expectedCommentTypes;

    public CommentEventsCollector(Parser parser, CommentType ... commentTypeArray) {
        this.eventSource = new AbstractQueue<Event>(this, parser){
            final Parser val$parser;
            final CommentEventsCollector this$0;
            {
                this.this$0 = commentEventsCollector;
                this.val$parser = parser;
            }

            @Override
            public boolean offer(Event event) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Event poll() {
                return this.val$parser.getEvent();
            }

            @Override
            public Event peek() {
                return this.val$parser.peekEvent();
            }

            @Override
            public Iterator<Event> iterator() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int size() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object peek() {
                return this.peek();
            }

            @Override
            public Object poll() {
                return this.poll();
            }

            @Override
            public boolean offer(Object object) {
                return this.offer((Event)object);
            }
        };
        this.expectedCommentTypes = commentTypeArray;
        this.commentLineList = new ArrayList<CommentLine>();
    }

    public CommentEventsCollector(Queue<Event> queue, CommentType ... commentTypeArray) {
        this.eventSource = queue;
        this.expectedCommentTypes = commentTypeArray;
        this.commentLineList = new ArrayList<CommentLine>();
    }

    private boolean isEventExpected(Event event) {
        if (event == null || !event.is(Event.ID.Comment)) {
            return true;
        }
        CommentEvent commentEvent = (CommentEvent)event;
        for (CommentType commentType : this.expectedCommentTypes) {
            if (commentEvent.getCommentType() != commentType) continue;
            return false;
        }
        return true;
    }

    public CommentEventsCollector collectEvents() {
        this.collectEvents(null);
        return this;
    }

    public Event collectEvents(Event event) {
        if (event != null) {
            if (this.isEventExpected(event)) {
                this.commentLineList.add(new CommentLine((CommentEvent)event));
            } else {
                return event;
            }
        }
        while (this.isEventExpected(this.eventSource.peek())) {
            this.commentLineList.add(new CommentLine((CommentEvent)this.eventSource.poll()));
        }
        return null;
    }

    public Event collectEventsAndPoll(Event event) {
        Event event2 = this.collectEvents(event);
        return event2 != null ? event2 : this.eventSource.poll();
    }

    public List<CommentLine> consume() {
        try {
            List<CommentLine> list = this.commentLineList;
            return list;
        } finally {
            this.commentLineList = new ArrayList<CommentLine>();
        }
    }

    public boolean isEmpty() {
        return this.commentLineList.isEmpty();
    }
}

