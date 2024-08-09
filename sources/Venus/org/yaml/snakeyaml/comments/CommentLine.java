/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.comments;

import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.CommentEvent;

public class CommentLine {
    private final Mark startMark;
    private final Mark endMark;
    private final String value;
    private final CommentType commentType;

    public CommentLine(CommentEvent commentEvent) {
        this(commentEvent.getStartMark(), commentEvent.getEndMark(), commentEvent.getValue(), commentEvent.getCommentType());
    }

    public CommentLine(Mark mark, Mark mark2, String string, CommentType commentType) {
        this.startMark = mark;
        this.endMark = mark2;
        this.value = string;
        this.commentType = commentType;
    }

    public Mark getEndMark() {
        return this.endMark;
    }

    public Mark getStartMark() {
        return this.startMark;
    }

    public CommentType getCommentType() {
        return this.commentType;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return "<" + this.getClass().getName() + " (type=" + (Object)((Object)this.getCommentType()) + ", value=" + this.getValue() + ")>";
    }
}

