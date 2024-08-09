/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.patchy;

import java.util.Hashtable;
import java.util.function.Predicate;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class BlockingDC
implements DirContext {
    private final Predicate<String> blockList;
    private final DirContext parent;

    public BlockingDC(Predicate<String> predicate, DirContext dirContext) {
        this.blockList = predicate;
        this.parent = dirContext;
    }

    @Override
    public Attributes getAttributes(String string, String[] stringArray) throws NamingException {
        if (this.blockList.test(string)) {
            return new BasicAttributes();
        }
        return this.parent.getAttributes(string, stringArray);
    }

    @Override
    public Attributes getAttributes(String string) throws NamingException {
        if (this.blockList.test(string)) {
            return new BasicAttributes();
        }
        return this.parent.getAttributes(string);
    }

    @Override
    public Attributes getAttributes(Name name) throws NamingException {
        return this.parent.getAttributes(name);
    }

    @Override
    public Attributes getAttributes(Name name, String[] stringArray) throws NamingException {
        return this.parent.getAttributes(name, stringArray);
    }

    @Override
    public void modifyAttributes(Name name, int n, Attributes attributes) throws NamingException {
        this.parent.modifyAttributes(name, n, attributes);
    }

    @Override
    public void modifyAttributes(String string, int n, Attributes attributes) throws NamingException {
        this.parent.modifyAttributes(string, n, attributes);
    }

    @Override
    public void modifyAttributes(Name name, ModificationItem[] modificationItemArray) throws NamingException {
        this.parent.modifyAttributes(name, modificationItemArray);
    }

    @Override
    public void modifyAttributes(String string, ModificationItem[] modificationItemArray) throws NamingException {
        this.parent.modifyAttributes(string, modificationItemArray);
    }

    @Override
    public void bind(Name name, Object object, Attributes attributes) throws NamingException {
        this.parent.bind(name, object, attributes);
    }

    @Override
    public void bind(String string, Object object, Attributes attributes) throws NamingException {
        this.parent.bind(string, object, attributes);
    }

    @Override
    public void rebind(Name name, Object object, Attributes attributes) throws NamingException {
        this.parent.rebind(name, object, attributes);
    }

    @Override
    public void rebind(String string, Object object, Attributes attributes) throws NamingException {
        this.parent.rebind(string, object, attributes);
    }

    @Override
    public DirContext createSubcontext(Name name, Attributes attributes) throws NamingException {
        return this.parent.createSubcontext(name, attributes);
    }

    @Override
    public DirContext createSubcontext(String string, Attributes attributes) throws NamingException {
        return this.parent.createSubcontext(string, attributes);
    }

    @Override
    public DirContext getSchema(Name name) throws NamingException {
        return this.parent.getSchema(name);
    }

    @Override
    public DirContext getSchema(String string) throws NamingException {
        return this.parent.getSchema(string);
    }

    @Override
    public DirContext getSchemaClassDefinition(Name name) throws NamingException {
        return this.parent.getSchemaClassDefinition(name);
    }

    @Override
    public DirContext getSchemaClassDefinition(String string) throws NamingException {
        return this.parent.getSchemaClassDefinition(string);
    }

    @Override
    public NamingEnumeration<SearchResult> search(Name name, Attributes attributes, String[] stringArray) throws NamingException {
        return this.parent.search(name, attributes, stringArray);
    }

    @Override
    public NamingEnumeration<SearchResult> search(String string, Attributes attributes, String[] stringArray) throws NamingException {
        return this.parent.search(string, attributes, stringArray);
    }

    @Override
    public NamingEnumeration<SearchResult> search(Name name, Attributes attributes) throws NamingException {
        return this.parent.search(name, attributes);
    }

    @Override
    public NamingEnumeration<SearchResult> search(String string, Attributes attributes) throws NamingException {
        return this.parent.search(string, attributes);
    }

    @Override
    public NamingEnumeration<SearchResult> search(Name name, String string, SearchControls searchControls) throws NamingException {
        return this.parent.search(name, string, searchControls);
    }

    @Override
    public NamingEnumeration<SearchResult> search(String string, String string2, SearchControls searchControls) throws NamingException {
        return this.parent.search(string, string2, searchControls);
    }

    @Override
    public NamingEnumeration<SearchResult> search(Name name, String string, Object[] objectArray, SearchControls searchControls) throws NamingException {
        return this.parent.search(name, string, objectArray, searchControls);
    }

    @Override
    public NamingEnumeration<SearchResult> search(String string, String string2, Object[] objectArray, SearchControls searchControls) throws NamingException {
        return this.parent.search(string, string2, objectArray, searchControls);
    }

    @Override
    public Object lookup(Name name) throws NamingException {
        return this.parent.lookup(name);
    }

    @Override
    public Object lookup(String string) throws NamingException {
        return this.parent.lookup(string);
    }

    @Override
    public void bind(Name name, Object object) throws NamingException {
        this.parent.bind(name, object);
    }

    @Override
    public void bind(String string, Object object) throws NamingException {
        this.parent.bind(string, object);
    }

    @Override
    public void rebind(Name name, Object object) throws NamingException {
        this.parent.rebind(name, object);
    }

    @Override
    public void rebind(String string, Object object) throws NamingException {
        this.parent.rebind(string, object);
    }

    @Override
    public void unbind(Name name) throws NamingException {
        this.parent.unbind(name);
    }

    @Override
    public void unbind(String string) throws NamingException {
        this.parent.unbind(string);
    }

    @Override
    public void rename(Name name, Name name2) throws NamingException {
        this.parent.rename(name, name2);
    }

    @Override
    public void rename(String string, String string2) throws NamingException {
        this.parent.rename(string, string2);
    }

    @Override
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return this.parent.list(name);
    }

    @Override
    public NamingEnumeration<NameClassPair> list(String string) throws NamingException {
        return this.parent.list(string);
    }

    @Override
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return this.parent.listBindings(name);
    }

    @Override
    public NamingEnumeration<Binding> listBindings(String string) throws NamingException {
        return this.parent.listBindings(string);
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {
        this.parent.destroySubcontext(name);
    }

    @Override
    public void destroySubcontext(String string) throws NamingException {
        this.parent.destroySubcontext(string);
    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        return this.parent.createSubcontext(name);
    }

    @Override
    public Context createSubcontext(String string) throws NamingException {
        return this.parent.createSubcontext(string);
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        return this.parent.lookupLink(name);
    }

    @Override
    public Object lookupLink(String string) throws NamingException {
        return this.parent.lookupLink(string);
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return this.parent.getNameParser(name);
    }

    @Override
    public NameParser getNameParser(String string) throws NamingException {
        return this.parent.getNameParser(string);
    }

    @Override
    public Name composeName(Name name, Name name2) throws NamingException {
        return this.parent.composeName(name, name2);
    }

    @Override
    public String composeName(String string, String string2) throws NamingException {
        return this.parent.composeName(string, string2);
    }

    @Override
    public Object addToEnvironment(String string, Object object) throws NamingException {
        return this.parent.addToEnvironment(string, object);
    }

    @Override
    public Object removeFromEnvironment(String string) throws NamingException {
        return this.parent.removeFromEnvironment(string);
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return this.parent.getEnvironment();
    }

    @Override
    public void close() throws NamingException {
        this.parent.close();
    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return this.parent.getNameInNamespace();
    }
}

