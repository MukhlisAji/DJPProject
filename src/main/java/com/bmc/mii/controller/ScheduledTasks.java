package com.bmc.mii.controller;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("scheduledTasks")
public class ScheduledTasks {

    protected static Logger logger = Logger.getLogger("Scheduler Jobs:");

    //@Scheduled(fixedDelay = 60000)
    public void reportCurrentTime() throws IOException, FileNotFoundException, DocumentException {
        logger.info("Scheduler jobs is running from xml");

        DjpController djpController = new DjpController();
        djpController.printRequest();
        djpController.printCompleted();
        djpController.printRejected();

    }
}
