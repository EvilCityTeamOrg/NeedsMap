import org.slf4j.spi.SLF4JServiceProvider;

// Nope! We won't use maven or gradle here
// I'm really sorry for whatever the hell I'm using
// We don't have much time

module NeedsMap {
    requires org.slf4j;
    requires spark.core;
    requires javax.servlet.api;

    provides SLF4JServiceProvider with com.evilcity.needsmap.log.SLF4JProvider;
}