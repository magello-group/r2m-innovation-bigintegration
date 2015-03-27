package se.r2m.bigint.storm;

import java.util.Arrays;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.kafka.Broker;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StaticHosts;
import storm.kafka.StringScheme;
import storm.kafka.trident.GlobalPartitionInformation;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class KafkaTest {

    private static Logger log = LoggerFactory.getLogger(KafkaTest.class);
    
    public static void main(String[] args) {
        
        log.info("Starting up..:");
        KafkaTest kafkaTest = new KafkaTest();
        kafkaTest.run();
    }

    private void run() {
        Broker brokerForPartition0 = new Broker("52.17.55.220", 9092);// localhost:9092
        GlobalPartitionInformation partitionInfo = new GlobalPartitionInformation();
        partitionInfo.addPartition(0, brokerForPartition0);
        StaticHosts hosts = new StaticHosts(partitionInfo);

        // KafkaConfig kc = new KafkaConfig(hosts, "game.events");
        SpoutConfig spoutConf = new SpoutConfig(hosts, "game.events", "/zkRoot", UUID.randomUUID().toString());
        spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
        spoutConf.zkServers = Arrays.asList("52.17.55.220");
        spoutConf.zkPort = 2181;
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConf);

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("mySpout", kafkaSpout, 1);
        // builder.setBolt("exclaim1", new ExclamationBolt(),
        // 3).shuffleGrouping("word");
        // builder.setBolt("exclaim2", new ExclamationBolt(),
        // 2).shuffleGrouping("exclaim1");

        Config conf = new Config();
        conf.setDebug(true);


        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("myTopology", conf, builder.createTopology());
        Utils.sleep(300000);
        cluster.killTopology("myTopology");
        cluster.shutdown();

    }
}
