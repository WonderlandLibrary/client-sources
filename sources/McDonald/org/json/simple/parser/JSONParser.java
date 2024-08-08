// 
// Decompiled by Procyon v0.5.36
// 

package org.json.simple.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.StringReader;
import java.io.Reader;
import java.util.LinkedList;

public class JSONParser
{
    public static final int S_INIT = 0;
    public static final int S_IN_FINISHED_VALUE = 1;
    public static final int S_IN_OBJECT = 2;
    public static final int S_IN_ARRAY = 3;
    public static final int S_PASSED_PAIR_KEY = 4;
    public static final int S_IN_PAIR_VALUE = 5;
    public static final int S_END = 6;
    public static final int S_IN_ERROR = -1;
    private LinkedList handlerStatusStack;
    private Yylex lexer;
    private Yytoken token;
    private int status;
    
    public JSONParser() {
        this.lexer = new Yylex((Reader)null);
        this.token = null;
        this.status = 0;
    }
    
    private int peekStatus(final LinkedList statusStack) {
        if (statusStack.size() == 0) {
            return -1;
        }
        final Integer status = statusStack.getFirst();
        return status;
    }
    
    public void reset() {
        this.token = null;
        this.status = 0;
        this.handlerStatusStack = null;
    }
    
    public void reset(final Reader in) {
        this.lexer.yyreset(in);
        this.reset();
    }
    
    public int getPosition() {
        return this.lexer.getPosition();
    }
    
    public Object parse(final String s) throws ParseException {
        return this.parse(s, (ContainerFactory)null);
    }
    
    public Object parse(final String s, final ContainerFactory containerFactory) throws ParseException {
        final StringReader in = new StringReader(s);
        try {
            return this.parse(in, containerFactory);
        }
        catch (IOException ie) {
            throw new ParseException(-1, 2, ie);
        }
    }
    
    public Object parse(final Reader in) throws IOException, ParseException {
        return this.parse(in, (ContainerFactory)null);
    }
    
    public Object parse(final Reader in, final ContainerFactory containerFactory) throws IOException, ParseException {
        this.reset(in);
        final LinkedList statusStack = new LinkedList();
        final LinkedList valueStack = new LinkedList();
        try {
            do {
                this.nextToken();
                Label_0922: {
                    switch (this.status) {
                        case 0: {
                            switch (this.token.type) {
                                case 0: {
                                    this.status = 1;
                                    statusStack.addFirst(new Integer(this.status));
                                    valueStack.addFirst(this.token.value);
                                    break Label_0922;
                                }
                                case 1: {
                                    this.status = 2;
                                    statusStack.addFirst(new Integer(this.status));
                                    valueStack.addFirst(this.createObjectContainer(containerFactory));
                                    break Label_0922;
                                }
                                case 3: {
                                    this.status = 3;
                                    statusStack.addFirst(new Integer(this.status));
                                    valueStack.addFirst(this.createArrayContainer(containerFactory));
                                    break Label_0922;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0922;
                                }
                            }
                            break;
                        }
                        case 1: {
                            if (this.token.type == -1) {
                                return valueStack.removeFirst();
                            }
                            throw new ParseException(this.getPosition(), 1, this.token);
                        }
                        case 2: {
                            switch (this.token.type) {
                                case 5: {
                                    break Label_0922;
                                }
                                case 0: {
                                    if (this.token.value instanceof String) {
                                        final String key = (String)this.token.value;
                                        valueStack.addFirst(key);
                                        this.status = 4;
                                        statusStack.addFirst(new Integer(this.status));
                                        break Label_0922;
                                    }
                                    this.status = -1;
                                    break Label_0922;
                                }
                                case 2: {
                                    if (valueStack.size() > 1) {
                                        statusStack.removeFirst();
                                        valueStack.removeFirst();
                                        this.status = this.peekStatus(statusStack);
                                        break Label_0922;
                                    }
                                    this.status = 1;
                                    break Label_0922;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0922;
                                }
                            }
                            break;
                        }
                        case 4: {
                            switch (this.token.type) {
                                case 6: {
                                    break;
                                }
                                case 0: {
                                    statusStack.removeFirst();
                                    final String key = valueStack.removeFirst();
                                    final Map parent = valueStack.getFirst();
                                    parent.put(key, this.token.value);
                                    this.status = this.peekStatus(statusStack);
                                    break;
                                }
                                case 3: {
                                    statusStack.removeFirst();
                                    final String key = valueStack.removeFirst();
                                    final Map parent = valueStack.getFirst();
                                    final List newArray = this.createArrayContainer(containerFactory);
                                    parent.put(key, newArray);
                                    this.status = 3;
                                    statusStack.addFirst(new Integer(this.status));
                                    valueStack.addFirst(newArray);
                                    break;
                                }
                                case 1: {
                                    statusStack.removeFirst();
                                    final String key = valueStack.removeFirst();
                                    final Map parent = valueStack.getFirst();
                                    final Map newObject = this.createObjectContainer(containerFactory);
                                    parent.put(key, newObject);
                                    this.status = 2;
                                    statusStack.addFirst(new Integer(this.status));
                                    valueStack.addFirst(newObject);
                                    break;
                                }
                                default: {
                                    this.status = -1;
                                    break;
                                }
                            }
                            break;
                        }
                        case 3: {
                            switch (this.token.type) {
                                case 5: {
                                    break;
                                }
                                case 0: {
                                    final List val = valueStack.getFirst();
                                    val.add(this.token.value);
                                    break;
                                }
                                case 4: {
                                    if (valueStack.size() > 1) {
                                        statusStack.removeFirst();
                                        valueStack.removeFirst();
                                        this.status = this.peekStatus(statusStack);
                                        break;
                                    }
                                    this.status = 1;
                                    break;
                                }
                                case 1: {
                                    final List val = valueStack.getFirst();
                                    final Map newObject2 = this.createObjectContainer(containerFactory);
                                    val.add(newObject2);
                                    this.status = 2;
                                    statusStack.addFirst(new Integer(this.status));
                                    valueStack.addFirst(newObject2);
                                    break;
                                }
                                case 3: {
                                    final List val = valueStack.getFirst();
                                    final List newArray = this.createArrayContainer(containerFactory);
                                    val.add(newArray);
                                    this.status = 3;
                                    statusStack.addFirst(new Integer(this.status));
                                    valueStack.addFirst(newArray);
                                    break;
                                }
                                default: {
                                    this.status = -1;
                                    break;
                                }
                            }
                            break;
                        }
                        case -1: {
                            throw new ParseException(this.getPosition(), 1, this.token);
                        }
                    }
                }
                if (this.status == -1) {
                    throw new ParseException(this.getPosition(), 1, this.token);
                }
            } while (this.token.type != -1);
        }
        catch (IOException ie) {
            throw ie;
        }
        throw new ParseException(this.getPosition(), 1, this.token);
    }
    
    private void nextToken() throws ParseException, IOException {
        this.token = this.lexer.yylex();
        if (this.token == null) {
            this.token = new Yytoken(-1, null);
        }
    }
    
    private Map createObjectContainer(final ContainerFactory containerFactory) {
        if (containerFactory == null) {
            return new JSONObject();
        }
        final Map m = containerFactory.createObjectContainer();
        if (m == null) {
            return new JSONObject();
        }
        return m;
    }
    
    private List createArrayContainer(final ContainerFactory containerFactory) {
        if (containerFactory == null) {
            return new JSONArray();
        }
        final List l = containerFactory.creatArrayContainer();
        if (l == null) {
            return new JSONArray();
        }
        return l;
    }
    
    public void parse(final String s, final ContentHandler contentHandler) throws ParseException {
        this.parse(s, contentHandler, false);
    }
    
    public void parse(final String s, final ContentHandler contentHandler, final boolean isResume) throws ParseException {
        final StringReader in = new StringReader(s);
        try {
            this.parse(in, contentHandler, isResume);
        }
        catch (IOException ie) {
            throw new ParseException(-1, 2, ie);
        }
    }
    
    public void parse(final Reader in, final ContentHandler contentHandler) throws IOException, ParseException {
        this.parse(in, contentHandler, false);
    }
    
    public void parse(final Reader in, final ContentHandler contentHandler, boolean isResume) throws IOException, ParseException {
        if (!isResume) {
            this.reset(in);
            this.handlerStatusStack = new LinkedList();
        }
        else if (this.handlerStatusStack == null) {
            isResume = false;
            this.reset(in);
            this.handlerStatusStack = new LinkedList();
        }
        final LinkedList statusStack = this.handlerStatusStack;
        try {
            do {
                Label_0911: {
                    switch (this.status) {
                        case 0: {
                            contentHandler.startJSON();
                            this.nextToken();
                            switch (this.token.type) {
                                case 0: {
                                    this.status = 1;
                                    statusStack.addFirst(new Integer(this.status));
                                    if (!contentHandler.primitive(this.token.value)) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 1: {
                                    this.status = 2;
                                    statusStack.addFirst(new Integer(this.status));
                                    if (!contentHandler.startObject()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 3: {
                                    this.status = 3;
                                    statusStack.addFirst(new Integer(this.status));
                                    if (!contentHandler.startArray()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0911;
                                }
                            }
                            break;
                        }
                        case 1: {
                            this.nextToken();
                            if (this.token.type == -1) {
                                contentHandler.endJSON();
                                this.status = 6;
                                return;
                            }
                            this.status = -1;
                            throw new ParseException(this.getPosition(), 1, this.token);
                        }
                        case 2: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 5: {
                                    break Label_0911;
                                }
                                case 0: {
                                    if (!(this.token.value instanceof String)) {
                                        this.status = -1;
                                        break Label_0911;
                                    }
                                    final String key = (String)this.token.value;
                                    this.status = 4;
                                    statusStack.addFirst(new Integer(this.status));
                                    if (!contentHandler.startObjectEntry(key)) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 2: {
                                    if (statusStack.size() > 1) {
                                        statusStack.removeFirst();
                                        this.status = this.peekStatus(statusStack);
                                    }
                                    else {
                                        this.status = 1;
                                    }
                                    if (!contentHandler.endObject()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0911;
                                }
                            }
                            break;
                        }
                        case 4: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 6: {
                                    break Label_0911;
                                }
                                case 0: {
                                    statusStack.removeFirst();
                                    this.status = this.peekStatus(statusStack);
                                    if (!contentHandler.primitive(this.token.value)) {
                                        return;
                                    }
                                    if (!contentHandler.endObjectEntry()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 3: {
                                    statusStack.removeFirst();
                                    statusStack.addFirst(new Integer(5));
                                    this.status = 3;
                                    statusStack.addFirst(new Integer(this.status));
                                    if (!contentHandler.startArray()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 1: {
                                    statusStack.removeFirst();
                                    statusStack.addFirst(new Integer(5));
                                    this.status = 2;
                                    statusStack.addFirst(new Integer(this.status));
                                    if (!contentHandler.startObject()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0911;
                                }
                            }
                            break;
                        }
                        case 5: {
                            statusStack.removeFirst();
                            this.status = this.peekStatus(statusStack);
                            if (!contentHandler.endObjectEntry()) {
                                return;
                            }
                            break;
                        }
                        case 3: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 5: {
                                    break Label_0911;
                                }
                                case 0: {
                                    if (!contentHandler.primitive(this.token.value)) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 4: {
                                    if (statusStack.size() > 1) {
                                        statusStack.removeFirst();
                                        this.status = this.peekStatus(statusStack);
                                    }
                                    else {
                                        this.status = 1;
                                    }
                                    if (!contentHandler.endArray()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 1: {
                                    this.status = 2;
                                    statusStack.addFirst(new Integer(this.status));
                                    if (!contentHandler.startObject()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 3: {
                                    this.status = 3;
                                    statusStack.addFirst(new Integer(this.status));
                                    if (!contentHandler.startArray()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0911;
                                }
                            }
                            break;
                        }
                        case 6: {
                            return;
                        }
                        case -1: {
                            throw new ParseException(this.getPosition(), 1, this.token);
                        }
                    }
                }
                if (this.status == -1) {
                    throw new ParseException(this.getPosition(), 1, this.token);
                }
            } while (this.token.type != -1);
        }
        catch (IOException ie) {
            this.status = -1;
            throw ie;
        }
        catch (ParseException pe) {
            this.status = -1;
            throw pe;
        }
        catch (RuntimeException re) {
            this.status = -1;
            throw re;
        }
        catch (Error e) {
            this.status = -1;
            throw e;
        }
        this.status = -1;
        throw new ParseException(this.getPosition(), 1, this.token);
    }
}
