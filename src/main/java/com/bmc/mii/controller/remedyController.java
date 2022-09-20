/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmc.mii.controller;

import com.bmc.arsys.api.ARException;
import org.apache.log4j.Logger;

import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.AttachmentValue;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.EntryListInfo;
import com.bmc.arsys.api.Value;
import com.bmc.mii.domain.ConfigFile;
import com.bmc.mii.domain.ConfigurationValue;
import com.bmc.mii.domain.RemedyAPI;
import com.bmc.mii.domain.SendPDF;
import com.bmc.mii.remedy.RemedyConnection;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 *
 * @author MukhlisAj
 */
public class remedyController {

    protected static Logger logger = Logger.getLogger("RemedyConnection: ");

    public void sendPdftoWorkInfo(SendPDF sendPDF) throws IOException {
        //logger.info("++++++++++++++++++++++++ searching get Pull Data +++++++++++++++++++++++++++++++++");
        //Get configuration value from sscconfig.properties
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        //SRT connection setting up
        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser remedySession = remedyConnection.connectToRemedy(configValue);

        String workInfoFormName = "SRM:WorkInfo";
        Entry workInfoEntry = new Entry();

        AttachmentValue attachment = new AttachmentValue();
        attachment.setName(sendPDF.namafile);

        attachment.setValue(sendPDF.namafile);

        //attachment1 10001831
        workInfoEntry.put(10001831, new Value(attachment));
        workInfoEntry.put(10001821, new Value(sendPDF.srInstanceId));//SRInstanceID 
        workInfoEntry.put(1000000829, new Value(sendPDF.requstNumber));//Request Number 
        workInfoEntry.put(303449900, new Value("General Information"));//WorkInfoTypeSelection 
        workInfoEntry.put(10001952, new Value("Public"));//View Access  
        //Status
        workInfoEntry.put(10006800, new Value(sendPDF.namafile));//Summary 
        workInfoEntry.put(10000101, new Value(sendPDF.namafile)); //Notes 
        //SR_RequestNumber 
        //SRID
        workInfoEntry.put(10001953, new Value("Yes"));//Secure Log 
        workInfoEntry.put(10001950, new Value("General Information"));//WorkInfoType 
        //WorkInfoSubmitDate

        try {
            String resultAttachment = remedySession.createEntry(workInfoFormName, workInfoEntry);
        } catch (ARException e) {
            // TODO Auto-generated catch block
            logger.info("entry failed" + e.toString());
        }
        logger.info("OK");
    }

    public void sendPdftoWOIWorkInfo(SendPDF sendPDF) throws IOException {
        //logger.info("++++++++++++++++++++++++ searching get Pull Data +++++++++++++++++++++++++++++++++");
        //Get configuration value from sscconfig.properties
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        //SRT connection setting up
        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser remedySession = remedyConnection.connectToRemedy(configValue);

        String workInfoFormName = "WOI:WorkInfo";
        Entry workInfoEntry = new Entry();

        AttachmentValue attachment = new AttachmentValue();
        attachment.setName(sendPDF.namafile);

        attachment.setValue(sendPDF.namafile);

        //attachment1 10001831
        workInfoEntry.put(1000000351, new Value(attachment));
        workInfoEntry.put(1000000182, new Value(sendPDF.woNumber));//WoNumber
        workInfoEntry.put(1000000170, new Value("General Information"));//WorkInfoTypeSelection 
        workInfoEntry.put(1000000761, new Value("Public"));//WorkInfoTypeSelection 
        workInfoEntry.put(1000001476, new Value("Yes"));//WorkInfoTypeSelection 
        //Status
        workInfoEntry.put(1000000000, new Value(sendPDF.namafile));//Summary 
        workInfoEntry.put(1000000151, new Value(sendPDF.namafile)); //Notes 
        //SR_RequestNumber 

        try {
            String resultAttachment = remedySession.createEntry(workInfoFormName, workInfoEntry);
        } catch (ARException e) {
            // TODO Auto-generated catch block
            logger.info("entry failed" + e.toString());
        }
        logger.info("OK");
    }

}
