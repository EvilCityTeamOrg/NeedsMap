package com.evilcity.needsmap.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class SLF4JProvider implements SLF4JServiceProvider {
    private SLF4JFactory SLF4JFactory;

    @Override
    public ILoggerFactory getLoggerFactory() {
        return SLF4JFactory;
    }

    @Override
    public IMarkerFactory getMarkerFactory() {
        return null;
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        return new NOPMDCAdapter();
    }

    @Override
    public String getRequestedApiVersion() {
        String REQUESTED_API_VERSION = "2.0.13";
        return REQUESTED_API_VERSION;
    }

    @Override
    public void initialize() {
        SLF4JFactory = new SLF4JFactory();
    }
}
