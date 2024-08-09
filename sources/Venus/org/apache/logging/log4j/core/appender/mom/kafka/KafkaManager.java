/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.kafka.clients.producer.Callback
 *  org.apache.kafka.clients.producer.Producer
 *  org.apache.kafka.clients.producer.ProducerRecord
 *  org.apache.kafka.clients.producer.RecordMetadata
 */
package org.apache.logging.log4j.core.appender.mom.kafka;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.mom.kafka.DefaultKafkaProducerFactory;
import org.apache.logging.log4j.core.appender.mom.kafka.KafkaProducerFactory;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.util.Log4jThread;

public class KafkaManager
extends AbstractManager {
    public static final String DEFAULT_TIMEOUT_MILLIS = "30000";
    static KafkaProducerFactory producerFactory = new DefaultKafkaProducerFactory();
    private final Properties config = new Properties();
    private Producer<byte[], byte[]> producer;
    private final int timeoutMillis;
    private final String topic;
    private final boolean syncSend;

    public KafkaManager(LoggerContext loggerContext, String string, String string2, boolean bl, Property[] propertyArray) {
        super(loggerContext, string);
        this.topic = Objects.requireNonNull(string2, "topic");
        this.syncSend = bl;
        this.config.setProperty("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        this.config.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        this.config.setProperty("batch.size", "0");
        for (Property property : propertyArray) {
            this.config.setProperty(property.getName(), property.getValue());
        }
        this.timeoutMillis = Integer.parseInt(this.config.getProperty("timeout.ms", DEFAULT_TIMEOUT_MILLIS));
    }

    @Override
    public boolean releaseSub(long l, TimeUnit timeUnit) {
        if (l > 0L) {
            this.closeProducer(l, timeUnit);
        } else {
            this.closeProducer(this.timeoutMillis, TimeUnit.MILLISECONDS);
        }
        return false;
    }

    private void closeProducer(long l, TimeUnit timeUnit) {
        if (this.producer != null) {
            Log4jThread log4jThread = new Log4jThread(new Runnable(this){
                final KafkaManager this$0;
                {
                    this.this$0 = kafkaManager;
                }

                @Override
                public void run() {
                    if (KafkaManager.access$000(this.this$0) != null) {
                        KafkaManager.access$000(this.this$0).close();
                    }
                }
            }, "KafkaManager-CloseThread");
            log4jThread.setDaemon(false);
            log4jThread.start();
            try {
                log4jThread.join(timeUnit.toMillis(l));
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(byte[] byArray) throws ExecutionException, InterruptedException, TimeoutException {
        if (this.producer != null) {
            ProducerRecord producerRecord = new ProducerRecord(this.topic, (Object)byArray);
            if (this.syncSend) {
                Future future = this.producer.send(producerRecord);
                future.get(this.timeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                this.producer.send(producerRecord, new Callback(this){
                    final KafkaManager this$0;
                    {
                        this.this$0 = kafkaManager;
                    }

                    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                        if (exception != null) {
                            KafkaManager.access$100().error("Unable to write to Kafka [" + this.this$0.getName() + "].", (Throwable)exception);
                        }
                    }
                });
            }
        }
    }

    public void startup() {
        this.producer = producerFactory.newKafkaProducer(this.config);
    }

    public String getTopic() {
        return this.topic;
    }

    static Producer access$000(KafkaManager kafkaManager) {
        return kafkaManager.producer;
    }

    static Logger access$100() {
        return LOGGER;
    }
}

