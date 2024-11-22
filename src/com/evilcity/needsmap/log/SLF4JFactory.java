package com.evilcity.needsmap.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class SLF4JFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String s) {
        return new com.evilcity.needsmap.log.Logger(s);
    }
}
