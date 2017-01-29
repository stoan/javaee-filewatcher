package com.housescent.javaeefilewatcher;

import javax.ejb.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Singleton
@Lock(LockType.READ)
public class FileWatcher {

    private WatchService watchService;
    private volatile boolean isCancelled = false;

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void startService() {
        try {

            watchService = FileSystems.getDefault().newWatchService();

            Path currentDir = Paths.get("C:\\inbox");

            //Registered events you interested in, created, modified and deleted
            WatchKey key = currentDir.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            while (!isCancelled) {

                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    //Logger goes here
                } catch (ClosedWatchServiceException e) {
                    //Logger goes here
                }

                List<WatchEvent<?>> keys = key.pollEvents();
                for (WatchEvent<?> watchEvent : keys) {
                    WatchEvent.Kind<?> watchEventKind = watchEvent.kind();

                    if (watchEventKind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    if (watchEventKind == StandardWatchEventKinds.ENTRY_CREATE) {

                        System.out.println("File Created: " + watchEvent.context());
                    }
                    if (watchEventKind == StandardWatchEventKinds.ENTRY_MODIFY) {

                        System.out.println("File Modified: " + watchEvent.context());
                    }
                    if (watchEventKind == StandardWatchEventKinds.ENTRY_DELETE) {

                        System.out.println("File Delete: " + watchEvent.context());
                    }
                    key.reset();
                }
            }
        } catch (IOException e) {
            //Logger goes here
        }
    }

    public void stopService() {
        isCancelled = true;
    }

    public void stopFileWatcher() {
        try {
            if (watchService != null) {
                watchService.close();
            }

        } catch (IOException e) {
            //Logger here
        }
    }
}