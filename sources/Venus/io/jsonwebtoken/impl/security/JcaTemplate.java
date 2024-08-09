/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.CheckedSupplier;
import io.jsonwebtoken.impl.lang.DefaultRegistry;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.Providers;
import io.jsonwebtoken.impl.security.Randoms;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

public class JcaTemplate {
    private static final List<InstanceFactory<?>> FACTORIES = Collections.of(new CipherFactory(), new KeyFactoryFactory(), new SecretKeyFactoryFactory(), new KeyGeneratorFactory(), new KeyPairGeneratorFactory(), new KeyAgreementFactory(), new MessageDigestFactory(), new SignatureFactory(), new MacFactory(), new AlgorithmParametersFactory(), new CertificateFactoryFactory());
    private static final Registry<Class<?>, InstanceFactory<?>> REGISTRY = new DefaultRegistry("JCA Instance Factory", "instance class", FACTORIES, new Function<InstanceFactory<?>, Class<?>>(){

        @Override
        public Class<?> apply(InstanceFactory<?> instanceFactory) {
            return instanceFactory.getInstanceClass();
        }

        @Override
        public Object apply(Object object) {
            return this.apply((InstanceFactory)object);
        }
    });
    private final String jcaName;
    private final Provider provider;
    private final SecureRandom secureRandom;

    protected Provider findBouncyCastle() {
        return Providers.findBouncyCastle();
    }

    JcaTemplate(String string) {
        this(string, null);
    }

    JcaTemplate(String string, Provider provider) {
        this(string, provider, null);
    }

    JcaTemplate(String string, Provider provider, SecureRandom secureRandom) {
        this.jcaName = Assert.hasText(string, "jcaName string cannot be null or empty.");
        this.secureRandom = secureRandom != null ? secureRandom : Randoms.secureRandom();
        this.provider = provider;
    }

    private <T, R> R execute(Class<T> clazz, CheckedFunction<T, R> checkedFunction, Provider provider) throws Exception {
        InstanceFactory instanceFactory = (InstanceFactory)REGISTRY.get(clazz);
        Assert.notNull(instanceFactory, "Unsupported JCA instance class.");
        Object t = instanceFactory.get(this.jcaName, provider);
        T t2 = Assert.isInstanceOf(clazz, t, "Factory instance does not match expected type.");
        return checkedFunction.apply(t2);
    }

    private <T> T execute(Class<?> clazz, CheckedSupplier<T> checkedSupplier) throws SecurityException {
        try {
            return checkedSupplier.get();
        } catch (SecurityException securityException) {
            throw securityException;
        } catch (Throwable throwable) {
            String string = clazz.getSimpleName() + " callback execution failed: " + throwable.getMessage();
            throw new SecurityException(string, throwable);
        }
    }

    private <T, R> R execute(Class<T> clazz, CheckedFunction<T, R> checkedFunction) throws SecurityException {
        return (R)this.execute(clazz, new CheckedSupplier<R>(this, clazz, checkedFunction){
            final Class val$clazz;
            final CheckedFunction val$fn;
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
                this.val$clazz = clazz;
                this.val$fn = checkedFunction;
            }

            @Override
            public R get() throws Exception {
                return JcaTemplate.access$100(this.this$0, this.val$clazz, this.val$fn, JcaTemplate.access$000(this.this$0));
            }
        });
    }

    protected <T, R> R fallback(Class<T> clazz, CheckedFunction<T, R> checkedFunction) throws SecurityException {
        return (R)this.execute(clazz, new CheckedSupplier<R>(this, clazz, checkedFunction){
            final Class val$clazz;
            final CheckedFunction val$callback;
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
                this.val$clazz = clazz;
                this.val$callback = checkedFunction;
            }

            @Override
            public R get() throws Exception {
                try {
                    return JcaTemplate.access$100(this.this$0, this.val$clazz, this.val$callback, JcaTemplate.access$000(this.this$0));
                } catch (Exception exception) {
                    try {
                        Provider provider = this.this$0.findBouncyCastle();
                        if (provider != null) {
                            return JcaTemplate.access$100(this.this$0, this.val$clazz, this.val$callback, provider);
                        }
                    } catch (Throwable throwable) {
                        // empty catch block
                    }
                    throw exception;
                }
            }
        });
    }

    public <R> R withCipher(CheckedFunction<Cipher, R> checkedFunction) throws SecurityException {
        return this.execute(Cipher.class, checkedFunction);
    }

    public <R> R withKeyFactory(CheckedFunction<KeyFactory, R> checkedFunction) throws SecurityException {
        return this.execute(KeyFactory.class, checkedFunction);
    }

    public <R> R withSecretKeyFactory(CheckedFunction<SecretKeyFactory, R> checkedFunction) throws SecurityException {
        return this.execute(SecretKeyFactory.class, checkedFunction);
    }

    public <R> R withKeyGenerator(CheckedFunction<KeyGenerator, R> checkedFunction) throws SecurityException {
        return this.execute(KeyGenerator.class, checkedFunction);
    }

    public <R> R withKeyAgreement(CheckedFunction<KeyAgreement, R> checkedFunction) throws SecurityException {
        return this.execute(KeyAgreement.class, checkedFunction);
    }

    public <R> R withKeyPairGenerator(CheckedFunction<KeyPairGenerator, R> checkedFunction) throws SecurityException {
        return this.execute(KeyPairGenerator.class, checkedFunction);
    }

    public <R> R withMessageDigest(CheckedFunction<MessageDigest, R> checkedFunction) throws SecurityException {
        return this.execute(MessageDigest.class, checkedFunction);
    }

    public <R> R withSignature(CheckedFunction<Signature, R> checkedFunction) throws SecurityException {
        return this.execute(Signature.class, checkedFunction);
    }

    public <R> R withMac(CheckedFunction<Mac, R> checkedFunction) throws SecurityException {
        return this.execute(Mac.class, checkedFunction);
    }

    public <R> R withAlgorithmParameters(CheckedFunction<AlgorithmParameters, R> checkedFunction) throws SecurityException {
        return this.execute(AlgorithmParameters.class, checkedFunction);
    }

    public <R> R withCertificateFactory(CheckedFunction<CertificateFactory, R> checkedFunction) throws SecurityException {
        return this.execute(CertificateFactory.class, checkedFunction);
    }

    public SecretKey generateSecretKey(int n) {
        return this.withKeyGenerator(new CheckedFunction<KeyGenerator, SecretKey>(this, n){
            final int val$keyBitLength;
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
                this.val$keyBitLength = n;
            }

            @Override
            public SecretKey apply(KeyGenerator keyGenerator) {
                keyGenerator.init(this.val$keyBitLength, JcaTemplate.access$200(this.this$0));
                return keyGenerator.generateKey();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyGenerator)object);
            }
        });
    }

    public KeyPair generateKeyPair() {
        return this.withKeyPairGenerator(new CheckedFunction<KeyPairGenerator, KeyPair>(this){
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
            }

            @Override
            public KeyPair apply(KeyPairGenerator keyPairGenerator) {
                return keyPairGenerator.generateKeyPair();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyPairGenerator)object);
            }
        });
    }

    public KeyPair generateKeyPair(int n) {
        return this.withKeyPairGenerator(new CheckedFunction<KeyPairGenerator, KeyPair>(this, n){
            final int val$keyBitLength;
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
                this.val$keyBitLength = n;
            }

            @Override
            public KeyPair apply(KeyPairGenerator keyPairGenerator) {
                keyPairGenerator.initialize(this.val$keyBitLength, JcaTemplate.access$200(this.this$0));
                return keyPairGenerator.generateKeyPair();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyPairGenerator)object);
            }
        });
    }

    public KeyPair generateKeyPair(AlgorithmParameterSpec algorithmParameterSpec) {
        return this.withKeyPairGenerator(new CheckedFunction<KeyPairGenerator, KeyPair>(this, algorithmParameterSpec){
            final AlgorithmParameterSpec val$params;
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
                this.val$params = algorithmParameterSpec;
            }

            @Override
            public KeyPair apply(KeyPairGenerator keyPairGenerator) throws InvalidAlgorithmParameterException {
                keyPairGenerator.initialize(this.val$params, JcaTemplate.access$200(this.this$0));
                return keyPairGenerator.generateKeyPair();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyPairGenerator)object);
            }
        });
    }

    public PublicKey generatePublic(KeySpec keySpec) {
        return this.fallback(KeyFactory.class, new CheckedFunction<KeyFactory, PublicKey>(this, keySpec){
            final KeySpec val$spec;
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
                this.val$spec = keySpec;
            }

            @Override
            public PublicKey apply(KeyFactory keyFactory) throws Exception {
                return keyFactory.generatePublic(this.val$spec);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        });
    }

    protected boolean isJdk11() {
        return System.getProperty("java.version").startsWith("11");
    }

    private boolean isJdk8213363Bug(InvalidKeySpecException invalidKeySpecException) {
        return this.isJdk11() && ("XDH".equals(this.jcaName) || "X25519".equals(this.jcaName) || "X448".equals(this.jcaName)) && invalidKeySpecException.getCause() instanceof InvalidKeyException && !Objects.isEmpty(invalidKeySpecException.getStackTrace()) && "sun.security.ec.XDHKeyFactory".equals(invalidKeySpecException.getStackTrace()[0].getClassName()) && "engineGeneratePrivate".equals(invalidKeySpecException.getStackTrace()[0].getMethodName());
    }

    private int getJdk8213363BugExpectedSize(InvalidKeyException invalidKeyException) {
        String string = invalidKeyException.getMessage();
        String string2 = "key length must be ";
        if (Strings.hasText(string) && string.startsWith(string2)) {
            String string3 = string.substring(string2.length());
            try {
                return Integer.parseInt(string3);
            } catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return 1;
    }

    private KeySpec respecIfNecessary(InvalidKeySpecException invalidKeySpecException, KeySpec keySpec) {
        InvalidKeyException invalidKeyException;
        int n;
        if (!(keySpec instanceof PKCS8EncodedKeySpec)) {
            return null;
        }
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = (PKCS8EncodedKeySpec)keySpec;
        byte[] byArray = pKCS8EncodedKeySpec.getEncoded();
        if (this.isJdk8213363Bug(invalidKeySpecException) && ((n = this.getJdk8213363BugExpectedSize(invalidKeyException = Assert.isInstanceOf(InvalidKeyException.class, invalidKeySpecException.getCause(), "Unexpected argument."))) == 32 || n == 56) && Bytes.length(byArray) >= n) {
            byte[] byArray2 = new byte[n];
            System.arraycopy(byArray, byArray.length - n, byArray2, 0, n);
            EdwardsCurve edwardsCurve = n == 32 ? EdwardsCurve.X25519 : EdwardsCurve.X448;
            return edwardsCurve.privateKeySpec(byArray2, true);
        }
        return null;
    }

    protected PrivateKey generatePrivate(KeyFactory keyFactory, KeySpec keySpec) throws InvalidKeySpecException {
        return keyFactory.generatePrivate(keySpec);
    }

    public PrivateKey generatePrivate(KeySpec keySpec) {
        return this.fallback(KeyFactory.class, new CheckedFunction<KeyFactory, PrivateKey>(this, keySpec){
            final KeySpec val$spec;
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
                this.val$spec = keySpec;
            }

            @Override
            public PrivateKey apply(KeyFactory keyFactory) throws Exception {
                try {
                    return this.this$0.generatePrivate(keyFactory, this.val$spec);
                } catch (InvalidKeySpecException invalidKeySpecException) {
                    KeySpec keySpec = JcaTemplate.access$300(this.this$0, invalidKeySpecException, this.val$spec);
                    if (keySpec != null) {
                        return this.this$0.generatePrivate(keyFactory, keySpec);
                    }
                    throw invalidKeySpecException;
                }
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        });
    }

    public X509Certificate generateX509Certificate(byte[] byArray) {
        return this.fallback(CertificateFactory.class, new CheckedFunction<CertificateFactory, X509Certificate>(this, byArray){
            final byte[] val$x509DerBytes;
            final JcaTemplate this$0;
            {
                this.this$0 = jcaTemplate;
                this.val$x509DerBytes = byArray;
            }

            @Override
            public X509Certificate apply(CertificateFactory certificateFactory) throws CertificateException {
                InputStream inputStream = Streams.of(this.val$x509DerBytes);
                return (X509Certificate)certificateFactory.generateCertificate(inputStream);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((CertificateFactory)object);
            }
        });
    }

    static Provider access$000(JcaTemplate jcaTemplate) {
        return jcaTemplate.provider;
    }

    static Object access$100(JcaTemplate jcaTemplate, Class clazz, CheckedFunction checkedFunction, Provider provider) throws Exception {
        return jcaTemplate.execute(clazz, checkedFunction, provider);
    }

    static SecureRandom access$200(JcaTemplate jcaTemplate) {
        return jcaTemplate.secureRandom;
    }

    static KeySpec access$300(JcaTemplate jcaTemplate, InvalidKeySpecException invalidKeySpecException, KeySpec keySpec) {
        return jcaTemplate.respecIfNecessary(invalidKeySpecException, keySpec);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class CertificateFactoryFactory
    extends JcaInstanceFactory<CertificateFactory> {
        CertificateFactoryFactory() {
            super(CertificateFactory.class);
        }

        @Override
        protected CertificateFactory doGet(String string, Provider provider) throws Exception {
            return provider != null ? CertificateFactory.getInstance(string, provider) : CertificateFactory.getInstance(string);
        }

        @Override
        protected Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class AlgorithmParametersFactory
    extends JcaInstanceFactory<AlgorithmParameters> {
        AlgorithmParametersFactory() {
            super(AlgorithmParameters.class);
        }

        @Override
        protected AlgorithmParameters doGet(String string, Provider provider) throws Exception {
            return provider != null ? AlgorithmParameters.getInstance(string, provider) : AlgorithmParameters.getInstance(string);
        }

        @Override
        protected Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class MacFactory
    extends JcaInstanceFactory<Mac> {
        MacFactory() {
            super(Mac.class);
        }

        @Override
        public Mac doGet(String string, Provider provider) throws NoSuchAlgorithmException {
            return provider != null ? Mac.getInstance(string, provider) : Mac.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SignatureFactory
    extends JcaInstanceFactory<Signature> {
        SignatureFactory() {
            super(Signature.class);
        }

        @Override
        public Signature doGet(String string, Provider provider) throws NoSuchAlgorithmException {
            return provider != null ? Signature.getInstance(string, provider) : Signature.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class MessageDigestFactory
    extends JcaInstanceFactory<MessageDigest> {
        MessageDigestFactory() {
            super(MessageDigest.class);
        }

        @Override
        public MessageDigest doGet(String string, Provider provider) throws NoSuchAlgorithmException {
            return provider != null ? MessageDigest.getInstance(string, provider) : MessageDigest.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class KeyAgreementFactory
    extends JcaInstanceFactory<KeyAgreement> {
        KeyAgreementFactory() {
            super(KeyAgreement.class);
        }

        @Override
        public KeyAgreement doGet(String string, Provider provider) throws NoSuchAlgorithmException {
            return provider != null ? KeyAgreement.getInstance(string, provider) : KeyAgreement.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class KeyPairGeneratorFactory
    extends JcaInstanceFactory<KeyPairGenerator> {
        KeyPairGeneratorFactory() {
            super(KeyPairGenerator.class);
        }

        @Override
        public KeyPairGenerator doGet(String string, Provider provider) throws NoSuchAlgorithmException {
            return provider != null ? KeyPairGenerator.getInstance(string, provider) : KeyPairGenerator.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class KeyGeneratorFactory
    extends JcaInstanceFactory<KeyGenerator> {
        KeyGeneratorFactory() {
            super(KeyGenerator.class);
        }

        @Override
        public KeyGenerator doGet(String string, Provider provider) throws NoSuchAlgorithmException {
            return provider != null ? KeyGenerator.getInstance(string, provider) : KeyGenerator.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SecretKeyFactoryFactory
    extends JcaInstanceFactory<SecretKeyFactory> {
        SecretKeyFactoryFactory() {
            super(SecretKeyFactory.class);
        }

        @Override
        public SecretKeyFactory doGet(String string, Provider provider) throws NoSuchAlgorithmException {
            return provider != null ? SecretKeyFactory.getInstance(string, provider) : SecretKeyFactory.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class KeyFactoryFactory
    extends JcaInstanceFactory<KeyFactory> {
        KeyFactoryFactory() {
            super(KeyFactory.class);
        }

        @Override
        public KeyFactory doGet(String string, Provider provider) throws NoSuchAlgorithmException {
            return provider != null ? KeyFactory.getInstance(string, provider) : KeyFactory.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class CipherFactory
    extends JcaInstanceFactory<Cipher> {
        CipherFactory() {
            super(Cipher.class);
        }

        @Override
        public Cipher doGet(String string, Provider provider) throws NoSuchPaddingException, NoSuchAlgorithmException {
            return provider != null ? Cipher.getInstance(string, provider) : Cipher.getInstance(string);
        }

        @Override
        public Object doGet(String string, Provider provider) throws Exception {
            return this.doGet(string, provider);
        }
    }

    private static abstract class JcaInstanceFactory<T>
    implements InstanceFactory<T> {
        private final Class<T> clazz;
        private final ConcurrentMap<String, Boolean> FALLBACK_ATTEMPTS = new ConcurrentHashMap<String, Boolean>();

        JcaInstanceFactory(Class<T> clazz) {
            this.clazz = Assert.notNull(clazz, "Class argument cannot be null.");
        }

        @Override
        public Class<T> getInstanceClass() {
            return this.clazz;
        }

        @Override
        public String getId() {
            return this.clazz.getSimpleName();
        }

        protected Provider findBouncyCastle() {
            return Providers.findBouncyCastle();
        }

        @Override
        public final T get(String string, Provider provider) throws Exception {
            Assert.hasText(string, "jcaName cannot be null or empty.");
            Provider provider2 = provider;
            Boolean bl = (Boolean)this.FALLBACK_ATTEMPTS.get(string);
            if (provider2 == null && bl != null && bl.booleanValue()) {
                provider2 = this.findBouncyCastle();
            }
            try {
                return this.doGet(string, provider2);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                Provider provider3;
                if (provider == null && bl == null && (provider3 = this.findBouncyCastle()) != null) {
                    try {
                        T t = this.doGet(string, provider3);
                        this.FALLBACK_ATTEMPTS.putIfAbsent(string, Boolean.TRUE);
                        return t;
                    } catch (Throwable throwable) {
                        this.FALLBACK_ATTEMPTS.putIfAbsent(string, Boolean.FALSE);
                    }
                }
                throw this.wrap(noSuchAlgorithmException, string, provider, null);
            } catch (Exception exception) {
                throw this.wrap(exception, string, provider, null);
            }
        }

        protected abstract T doGet(String var1, Provider var2) throws Exception;

        protected Exception wrap(Exception exception, String string, Provider provider, Provider provider2) {
            String string2 = "Unable to obtain '" + string + "' " + this.getId() + " instance from ";
            string2 = provider != null ? string2 + "specified '" + provider + "' Provider" : string2 + "default JCA Provider";
            if (provider2 != null) {
                string2 = string2 + " or fallback '" + provider2 + "' Provider";
            }
            string2 = string2 + ": " + exception.getMessage();
            return this.wrap(string2, exception);
        }

        protected Exception wrap(String string, Exception exception) {
            if (Signature.class.isAssignableFrom(this.clazz) || Mac.class.isAssignableFrom(this.clazz)) {
                return new SignatureException(string, exception);
            }
            return new SecurityException(string, exception);
        }
    }

    private static interface InstanceFactory<T>
    extends Identifiable {
        public Class<T> getInstanceClass();

        public T get(String var1, Provider var2) throws Exception;
    }
}

