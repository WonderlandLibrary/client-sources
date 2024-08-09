/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.DnsMessage;
import io.netty.handler.codec.dns.DnsOpCode;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDnsMessage
extends AbstractReferenceCounted
implements DnsMessage {
    private static final ResourceLeakDetector<DnsMessage> leakDetector;
    private static final int SECTION_QUESTION;
    private static final int SECTION_COUNT = 4;
    private final ResourceLeakTracker<DnsMessage> leak = leakDetector.track(this);
    private short id;
    private DnsOpCode opCode;
    private boolean recursionDesired;
    private byte z;
    private Object questions;
    private Object answers;
    private Object authorities;
    private Object additionals;
    static final boolean $assertionsDisabled;

    protected AbstractDnsMessage(int n) {
        this(n, DnsOpCode.QUERY);
    }

    protected AbstractDnsMessage(int n, DnsOpCode dnsOpCode) {
        this.setId(n);
        this.setOpCode(dnsOpCode);
    }

    @Override
    public int id() {
        return this.id & 0xFFFF;
    }

    @Override
    public DnsMessage setId(int n) {
        this.id = (short)n;
        return this;
    }

    @Override
    public DnsOpCode opCode() {
        return this.opCode;
    }

    @Override
    public DnsMessage setOpCode(DnsOpCode dnsOpCode) {
        this.opCode = ObjectUtil.checkNotNull(dnsOpCode, "opCode");
        return this;
    }

    @Override
    public boolean isRecursionDesired() {
        return this.recursionDesired;
    }

    @Override
    public DnsMessage setRecursionDesired(boolean bl) {
        this.recursionDesired = bl;
        return this;
    }

    @Override
    public int z() {
        return this.z;
    }

    @Override
    public DnsMessage setZ(int n) {
        this.z = (byte)(n & 7);
        return this;
    }

    @Override
    public int count(DnsSection dnsSection) {
        return this.count(AbstractDnsMessage.sectionOrdinal(dnsSection));
    }

    private int count(int n) {
        Object object = this.sectionAt(n);
        if (object == null) {
            return 1;
        }
        if (object instanceof DnsRecord) {
            return 0;
        }
        List list = (List)object;
        return list.size();
    }

    @Override
    public int count() {
        int n = 0;
        for (int i = 0; i < 4; ++i) {
            n += this.count(i);
        }
        return n;
    }

    @Override
    public <T extends DnsRecord> T recordAt(DnsSection dnsSection) {
        return this.recordAt(AbstractDnsMessage.sectionOrdinal(dnsSection));
    }

    private <T extends DnsRecord> T recordAt(int n) {
        Object object = this.sectionAt(n);
        if (object == null) {
            return null;
        }
        if (object instanceof DnsRecord) {
            return AbstractDnsMessage.castRecord(object);
        }
        List list = (List)object;
        if (list.isEmpty()) {
            return null;
        }
        return AbstractDnsMessage.castRecord(list.get(0));
    }

    @Override
    public <T extends DnsRecord> T recordAt(DnsSection dnsSection, int n) {
        return this.recordAt(AbstractDnsMessage.sectionOrdinal(dnsSection), n);
    }

    private <T extends DnsRecord> T recordAt(int n, int n2) {
        Object object = this.sectionAt(n);
        if (object == null) {
            throw new IndexOutOfBoundsException("index: " + n2 + " (expected: none)");
        }
        if (object instanceof DnsRecord) {
            if (n2 == 0) {
                return AbstractDnsMessage.castRecord(object);
            }
            throw new IndexOutOfBoundsException("index: " + n2 + "' (expected: 0)");
        }
        List list = (List)object;
        return AbstractDnsMessage.castRecord(list.get(n2));
    }

    @Override
    public DnsMessage setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        this.setRecord(AbstractDnsMessage.sectionOrdinal(dnsSection), dnsRecord);
        return this;
    }

    private void setRecord(int n, DnsRecord dnsRecord) {
        this.clear(n);
        this.setSection(n, AbstractDnsMessage.checkQuestion(n, dnsRecord));
    }

    @Override
    public <T extends DnsRecord> T setRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        return this.setRecord(AbstractDnsMessage.sectionOrdinal(dnsSection), n, dnsRecord);
    }

    private <T extends DnsRecord> T setRecord(int n, int n2, DnsRecord dnsRecord) {
        AbstractDnsMessage.checkQuestion(n, dnsRecord);
        Object object = this.sectionAt(n);
        if (object == null) {
            throw new IndexOutOfBoundsException("index: " + n2 + " (expected: none)");
        }
        if (object instanceof DnsRecord) {
            if (n2 == 0) {
                this.setSection(n, dnsRecord);
                return AbstractDnsMessage.castRecord(object);
            }
            throw new IndexOutOfBoundsException("index: " + n2 + " (expected: 0)");
        }
        List list = (List)object;
        return AbstractDnsMessage.castRecord(list.set(n2, dnsRecord));
    }

    @Override
    public DnsMessage addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        this.addRecord(AbstractDnsMessage.sectionOrdinal(dnsSection), dnsRecord);
        return this;
    }

    private void addRecord(int n, DnsRecord dnsRecord) {
        AbstractDnsMessage.checkQuestion(n, dnsRecord);
        Object object = this.sectionAt(n);
        if (object == null) {
            this.setSection(n, dnsRecord);
            return;
        }
        if (object instanceof DnsRecord) {
            ArrayList<DnsRecord> arrayList = AbstractDnsMessage.newRecordList();
            arrayList.add((DnsRecord)AbstractDnsMessage.castRecord(object));
            arrayList.add(dnsRecord);
            this.setSection(n, arrayList);
            return;
        }
        List list = (List)object;
        list.add(dnsRecord);
    }

    @Override
    public DnsMessage addRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        this.addRecord(AbstractDnsMessage.sectionOrdinal(dnsSection), n, dnsRecord);
        return this;
    }

    private void addRecord(int n, int n2, DnsRecord dnsRecord) {
        AbstractDnsMessage.checkQuestion(n, dnsRecord);
        Object object = this.sectionAt(n);
        if (object == null) {
            if (n2 != 0) {
                throw new IndexOutOfBoundsException("index: " + n2 + " (expected: 0)");
            }
            this.setSection(n, dnsRecord);
            return;
        }
        if (object instanceof DnsRecord) {
            ArrayList<DnsRecord> arrayList;
            if (n2 == 0) {
                arrayList = AbstractDnsMessage.newRecordList();
                arrayList.add(dnsRecord);
                arrayList.add((DnsRecord)AbstractDnsMessage.castRecord(object));
            } else if (n2 == 1) {
                arrayList = AbstractDnsMessage.newRecordList();
                arrayList.add((DnsRecord)AbstractDnsMessage.castRecord(object));
                arrayList.add(dnsRecord);
            } else {
                throw new IndexOutOfBoundsException("index: " + n2 + " (expected: 0 or 1)");
            }
            this.setSection(n, arrayList);
            return;
        }
        List list = (List)object;
        list.add(n2, dnsRecord);
    }

    @Override
    public <T extends DnsRecord> T removeRecord(DnsSection dnsSection, int n) {
        return this.removeRecord(AbstractDnsMessage.sectionOrdinal(dnsSection), n);
    }

    private <T extends DnsRecord> T removeRecord(int n, int n2) {
        Object object = this.sectionAt(n);
        if (object == null) {
            throw new IndexOutOfBoundsException("index: " + n2 + " (expected: none)");
        }
        if (object instanceof DnsRecord) {
            if (n2 != 0) {
                throw new IndexOutOfBoundsException("index: " + n2 + " (expected: 0)");
            }
            T t = AbstractDnsMessage.castRecord(object);
            this.setSection(n, null);
            return t;
        }
        List list = (List)object;
        return AbstractDnsMessage.castRecord(list.remove(n2));
    }

    @Override
    public DnsMessage clear(DnsSection dnsSection) {
        this.clear(AbstractDnsMessage.sectionOrdinal(dnsSection));
        return this;
    }

    @Override
    public DnsMessage clear() {
        for (int i = 0; i < 4; ++i) {
            this.clear(i);
        }
        return this;
    }

    private void clear(int n) {
        List list;
        Object object = this.sectionAt(n);
        this.setSection(n, null);
        if (object instanceof ReferenceCounted) {
            ((ReferenceCounted)object).release();
        } else if (object instanceof List && !(list = (List)object).isEmpty()) {
            for (Object e : list) {
                ReferenceCountUtil.release(e);
            }
        }
    }

    @Override
    public DnsMessage touch() {
        return (DnsMessage)super.touch();
    }

    @Override
    public DnsMessage touch(Object object) {
        if (this.leak != null) {
            this.leak.record(object);
        }
        return this;
    }

    @Override
    public DnsMessage retain() {
        return (DnsMessage)super.retain();
    }

    @Override
    public DnsMessage retain(int n) {
        return (DnsMessage)super.retain(n);
    }

    @Override
    protected void deallocate() {
        this.clear();
        ResourceLeakTracker<DnsMessage> resourceLeakTracker = this.leak;
        if (resourceLeakTracker != null) {
            boolean bl = resourceLeakTracker.close(this);
            if (!$assertionsDisabled && !bl) {
                throw new AssertionError();
            }
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof DnsMessage)) {
            return true;
        }
        DnsMessage dnsMessage = (DnsMessage)object;
        if (this.id() != dnsMessage.id()) {
            return true;
        }
        return this instanceof DnsQuery ? !(dnsMessage instanceof DnsQuery) : dnsMessage instanceof DnsQuery;
    }

    public int hashCode() {
        return this.id() * 31 + (this instanceof DnsQuery ? 0 : 1);
    }

    private Object sectionAt(int n) {
        switch (n) {
            case 0: {
                return this.questions;
            }
            case 1: {
                return this.answers;
            }
            case 2: {
                return this.authorities;
            }
            case 3: {
                return this.additionals;
            }
        }
        throw new Error();
    }

    private void setSection(int n, Object object) {
        switch (n) {
            case 0: {
                this.questions = object;
                return;
            }
            case 1: {
                this.answers = object;
                return;
            }
            case 2: {
                this.authorities = object;
                return;
            }
            case 3: {
                this.additionals = object;
                return;
            }
        }
        throw new Error();
    }

    private static int sectionOrdinal(DnsSection dnsSection) {
        return ObjectUtil.checkNotNull(dnsSection, "section").ordinal();
    }

    private static DnsRecord checkQuestion(int n, DnsRecord dnsRecord) {
        if (n == SECTION_QUESTION && !(ObjectUtil.checkNotNull(dnsRecord, "record") instanceof DnsQuestion)) {
            throw new IllegalArgumentException("record: " + dnsRecord + " (expected: " + StringUtil.simpleClassName(DnsQuestion.class) + ')');
        }
        return dnsRecord;
    }

    private static <T extends DnsRecord> T castRecord(Object object) {
        return (T)((DnsRecord)object);
    }

    private static ArrayList<DnsRecord> newRecordList() {
        return new ArrayList<DnsRecord>(2);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    static {
        $assertionsDisabled = !AbstractDnsMessage.class.desiredAssertionStatus();
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(DnsMessage.class);
        SECTION_QUESTION = DnsSection.QUESTION.ordinal();
    }
}

