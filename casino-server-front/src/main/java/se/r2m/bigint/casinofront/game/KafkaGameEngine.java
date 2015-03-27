package se.r2m.bigint.casinofront.game;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class KafkaGameEngine implements GameEngine, InitializingBean {

    private static Logger log = LoggerFactory.getLogger(KafkaGameEngine.class);

    @Value("${kafka.bootstrap.servers}")
    String brokerList;

    @Value("${kafka.producer.type}")
    String producerType;

    @Value("${kafka.game.topic}")
    String topic;

    private KafkaProducer<String, String> producer;

    private KafkaConsumer<String, String> consumer;

    public void startup() {
        log.info("Using broker list " + brokerList);
        log.info("Using producer.type " + producerType);

        Serializer<String> valSer = new StringSerializer();
        Serializer<String> keySer = new StringSerializer();
        producer = new KafkaProducer<String, String>(getConfigProducer(), keySer, valSer);
        consumer = new KafkaConsumer<String, String>(getConfigConsumer());
    }

    private Map<String, Object> getConfigProducer() {
        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put("bootstrap.servers", brokerList);
        conf.put("producer.type", producerType);
        conf.put("partitioner.class", "kafka.producer.DefaultPartitioner");
        // conf.put("batch.size", 1);
        conf.put("request.required.acks", "1");
        return conf;
    }

    private Map<String, Object> getConfigConsumer() {
        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put("bootstrap.servers", brokerList);
        conf.put("group.id", "group01");
        conf.put("key.deserializer", StringDeserializer.class);
        conf.put("value.deserializer", StringDeserializer.class);
        conf.put("partition.assignment.strategy", "roundrobin");
        return conf;
    }

    public GameOutput doPlay(GameInput input) {

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic,
                        getInputString(input));
        log.info("Sending to kafka {}", record);
        producer.send(record,
                        new Callback() {
                            public void onCompletion(RecordMetadata metadata, Exception e) {
                                if (e != null)
                                    e.printStackTrace();
                                System.out.println("The offset of the record we just sent is: " + metadata.offset());
                            }
                        });
        // consumer.subscribe("game.event.reply");
        // log.info("Polling reply");
        // Map<String, ConsumerRecords<String, String>> poll =
        // consumer.poll(5000);
        // log.info("Got reply " + poll);

        return new GameOutput(1, 1, 0, 10000);
        
    }

    private String getInputString(GameInput input) {
        // TODO Change
        return input.toString();
    }

    public void afterPropertiesSet() throws Exception {
        startup();
    }
}
