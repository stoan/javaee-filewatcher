package com.housescent.javaeefilewatcher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
@DependsOn("FileWatcher")
public class ServiceStarter {

    private FileWatcher fileWatcher;

    public ServiceStarter() {
    }

    @Inject
    public ServiceStarter(FileWatcher fileWatcher) {
        this.fileWatcher = fileWatcher;
    }

    @PostConstruct
    private void init() {
        fileWatcher.startService();
    }

    @PreDestroy
    private void destroy() {
        fileWatcher.stopService();
        fileWatcher.stopFileWatcher();
    }
}