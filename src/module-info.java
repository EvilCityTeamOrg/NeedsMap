import org.slf4j.spi.SLF4JServiceProvider;

// Nope! We won't use maven or gradle here
// I'm really sorry for whatever the hell I'm using
// We don't have much time

module NeedsMap {
    requires org.slf4j;
    requires spark.core;
    requires javax.servlet.api;
    requires org.mongodb.bson.record.codec;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires java.desktop;

    provides SLF4JServiceProvider with com.evilcity.needsmap.log.SLF4JProvider;
}