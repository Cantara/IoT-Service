package com.altran.iot.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        if (!file.delete()) {
                            log.warn("Unable to delete directory file " + file);
                        }
                    }
                }
            }
        }
        boolean exist = path.exists();
        boolean deleted = path.delete();
        if (exist && !deleted) {
            log.warn("Unable to delete directory " + path);
        }

        log.info("Folder {} was deleted successfully.", path.getAbsolutePath());
    }

    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                log.trace("Exception closing InputStream. Doing nothing.", e);
            }
        }
    }

    public static boolean localFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }


}
