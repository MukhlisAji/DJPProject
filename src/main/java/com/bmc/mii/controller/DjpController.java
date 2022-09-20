/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmc.mii.controller;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.EntryListInfo;
import com.bmc.arsys.api.Value;
//import static com.bmc.mii.controller.ConnectionTesting.logger;
import com.bmc.mii.domain.ConfigurationValue;
import com.bmc.mii.domain.RemedyAPI;
import com.bmc.mii.domain.RemedyAttachment;
import com.bmc.mii.domain.djp.PdfComplete;
import com.bmc.mii.domain.djp.PdfRejection;
import com.bmc.mii.domain.djp.PdfRequest;
import com.bmc.mii.poi.ReportDocument;
import com.bmc.mii.remedy.RemedyConnection;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author MukhlisAj
 */
@Controller
public class DjpController {

    /*
     */
    protected static Logger logger = Logger.getLogger("DJPController: ");

    @RequestMapping(value = "getFilename", method = RequestMethod.GET)
    public String getFilename(Model model) {
        //Get configuration value from DCconfig.properties
        ApplicationContext context = new AnnotationConfigApplicationContext(com.bmc.mii.domain.ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        //Connection testing
        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser remedyServer = remedyConnection.connectToRemedy(configValue);

        RemedyAPI remedyAPI = new RemedyAPI();

        ArrayList<RemedyAttachment> arrayofAttachment = new ArrayList<RemedyAttachment>();
        List<EntryListInfo> eListInfos = remedyAPI.getRemedyRecordByQuery(remedyServer, configValue.getRemedyMiddleFormRequest(), "'Status'=\"0\"");

        logger.info("array " + eListInfos);
        System.out.println(arrayofAttachment);
        String trueName = "";
        for (RemedyAttachment remedyAttachment : arrayofAttachment) {
            String Filename = remedyAttachment.getFilename();
            trueName = Filename.substring(0, Filename.indexOf("."));
            logger.info("Nama file = " + trueName);

        }
        model.addAttribute("result", "base 64 is : " + trueName);
        return "result";
    }

    @RequestMapping(value = "printRequest", method = RequestMethod.GET)
    public String printRequest() throws IOException, FileNotFoundException, DocumentException {
        //get Config Value
        ApplicationContext context = new AnnotationConfigApplicationContext(com.bmc.mii.domain.ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        //connection
        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser remedySession = remedyConnection.connectToRemedy(configValue);

        RemedyAPI remedyAPI = new RemedyAPI();

        List<EntryListInfo> listInfos = remedyAPI.getRemedyRecordByQuery(remedySession, configValue.getRemedyMiddleFormRequest(), "'Status Print__c'=\"0\" AND 'SR Field 4__c' != $NULL$");

        try {
            for (EntryListInfo listInfo : listInfos) {
                PdfRequest pdfRequest = new PdfRequest();
                Entry requestRecord = remedySession.getEntry(configValue.getRemedyMiddleFormRequest(), listInfo.getEntryID(), null);
                requestRecord.put(536870950, new Value("2"));
                remedySession.setEntry(configValue.getRemedyMiddleFormRequest(), requestRecord.getEntryId(), requestRecord, null, 0);
                pdfRequest.HeadKanwil = getValueFromRemedy(requestRecord, 536870930);
                if (pdfRequest.HeadKanwil.contains("Kanwil")) {
                    pdfRequest.HeadKanwil = pdfRequest.HeadKanwil.replaceAll("Kanwil", "Kantor Wilayah");
                }
                pdfRequest.HeadKPP = getValueFromRemedy(requestRecord, 536870931);
                pdfRequest.Nomor = getValueFromRemedy(requestRecord, 536870914);
//                pdfRequest.haritgl = getValueFromRemedy(requestRecord, 536870923);
                TimeZone indoZone = TimeZone.getTimeZone("Asia/Jakarta");
                pdfRequest.haritglTimestamp = getValueFromRemedy(requestRecord, 536870919);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                dateFormat.setTimeZone(indoZone);
                String dates = dateFormat(pdfRequest.haritglTimestamp, dateFormat);

                SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
                monthFormat.setTimeZone(indoZone);
                String month = dateFormat(pdfRequest.haritglTimestamp, monthFormat);
                logger.info("bulan : " + month);
                month = bulan(month);

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                yearFormat.setTimeZone(indoZone);
                String year = dateFormat(pdfRequest.haritglTimestamp, yearFormat);

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                timeFormat.setTimeZone(indoZone);
                String jam = dateFormat(pdfRequest.haritglTimestamp, timeFormat);

                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                dayFormat.setTimeZone(indoZone);
                String day = dateFormat(pdfRequest.haritglTimestamp, dayFormat);
                logger.info("hari : " + day);

                pdfRequest.haritgl = hari(day) + " / " + dates + " " + month + " " + year;
                pdfRequest.Kantor = getValueFromRemedy(requestRecord, 536870928);
                pdfRequest.NamaPengusul = getValueFromRemedy(requestRecord, 536870922);
                pdfRequest.Jabatan = getValueFromRemedy(requestRecord, 536870926);
                pdfRequest.Seksi = getValueFromRemedy(requestRecord, 536870927);
                pdfRequest.JenisKasus = getValueFromRemedy(requestRecord, 536870924);
                pdfRequest.NoKasus = getValueFromRemedy(requestRecord, 536870925);
                pdfRequest.NPWP = getValueFromRemedy(requestRecord, 536870915);
                pdfRequest.DataSemula = getValueFromRemedy(requestRecord, 536870916);
                pdfRequest.DataMenjadi = getValueFromRemedy(requestRecord, 536870917);
                pdfRequest.AlasanPerubahan = getValueFromRemedy(requestRecord, 536870918);
                pdfRequest.Kronologis = getValueFromRemedy(requestRecord, 536870920);
                pdfRequest.Pengusul = getValueFromRemedy(requestRecord, 536870922);
                pdfRequest.NIPPengusul = getValueFromRemedy(requestRecord, 536870933);
                pdfRequest.Mengetahui = getValueFromRemedy(requestRecord, 536870921);
                pdfRequest.NIPMengetahui = getValueFromRemedy(requestRecord, 536870934);
                pdfRequest.JobTitleMengetahui = getValueFromRemedy(requestRecord, 536870936);
                pdfRequest.Menyetujui = getValueFromRemedy(requestRecord, 536870929);
                pdfRequest.NIPMenyetujui = getValueFromRemedy(requestRecord, 536870935);
                pdfRequest.SysID = getValueFromRemedy(requestRecord, 536870932);
                pdfRequest.timestampTgl = getValueFromRemedy(requestRecord, 536870919);
                logger.info("timestamp : " + pdfRequest.timestampTgl);

                //get instanceID
                List<EntryListInfo> eListReqs = remedyAPI.getRemedyRecordByQuery(remedySession, "SRM:Request", "'SysRequestID'=\"" + pdfRequest.SysID + "\" ");
                for (EntryListInfo eListReq : eListReqs) {
                    Entry recordReq = remedySession.getEntry("SRM:Request", eListReq.getEntryID(), null);

                    pdfRequest.srInstanceId = recordReq.get(179).getValue().toString();
                    pdfRequest.requstNumber = recordReq.get(1000000829).getValue().toString();
                }

                ReportDocument reportDocument = new ReportDocument();
                reportDocument.generateRequestReport(pdfRequest);
                requestRecord.put(536870950, new Value("1"));
                remedySession.setEntry(configValue.getRemedyMiddleFormRequest(), requestRecord.getEntryId(), requestRecord, null, 0);
                logger.info("Success generated pdf NoID : " + pdfRequest.Nomor);
                logger.info("----------------------------------------------------------------");

            }
        } catch (ARException | IOException e) {
            logger.info("ARException Error on get data: " + e.toString());
        }

        return "result";

    }

    @RequestMapping(value = "printCompleted", method = RequestMethod.GET)
    public String printCompleted() throws IOException, FileNotFoundException, DocumentException {
        //get Config Value
        ApplicationContext context = new AnnotationConfigApplicationContext(com.bmc.mii.domain.ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        //connection
        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser remedySession = remedyConnection.connectToRemedy(configValue);

        RemedyAPI remedyAPI = new RemedyAPI();

        List<EntryListInfo> listInfos = remedyAPI.getRemedyRecordByQuery(remedySession, configValue.getRemedyMiddleFormCompleted(), "'Status Print'=\"0\" AND 'Type Report'=\"1\" AND 'SR Field 6' != $NULL$");

        try {
            for (EntryListInfo listInfo : listInfos) {
                PdfComplete pdfCompleted = new PdfComplete();
                Entry requestRecord = remedySession.getEntry(configValue.getRemedyMiddleFormCompleted(), listInfo.getEntryID(), null);
                requestRecord.put(536870951, new Value("2"));
                remedySession.setEntry(configValue.getRemedyMiddleFormCompleted(), requestRecord.getEntryId(), requestRecord, null, 0);
                pdfCompleted.SysID = getValueFromRemedy(requestRecord, 536870913);
                pdfCompleted.woNumber = getValueFromRemedy(requestRecord, 536870945);
                pdfCompleted.HeadKanwil = getValueFromRemedy(requestRecord, 536870930);
                 if (pdfCompleted.HeadKanwil.contains("Kanwil")) {
                    pdfCompleted.HeadKanwil = pdfCompleted.HeadKanwil.replaceAll("Kanwil", "Kantor Wilayah");
                }
                pdfCompleted.HeadKPP = getValueFromRemedy(requestRecord, 536870914);
                pdfCompleted.Nomor = getValueFromRemedy(requestRecord, 536870935);
                String hariTimestamp = getValueFromRemedy(requestRecord, 536870922);
                TimeZone indoZone = TimeZone.getTimeZone("Asia/Jakarta");

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                dateFormat.setTimeZone(indoZone);
                String dates = dateFormat(hariTimestamp, dateFormat);

                SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
                monthFormat.setTimeZone(indoZone);
                String month = dateFormat(hariTimestamp, monthFormat);
                month = bulan(month);

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                yearFormat.setTimeZone(indoZone);
                String year = dateFormat(hariTimestamp, yearFormat);

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                timeFormat.setTimeZone(indoZone);
                String jam = dateFormat(hariTimestamp, timeFormat);

                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                dayFormat.setTimeZone(indoZone);
                String day = dateFormat(hariTimestamp, dayFormat);
                day = hari(day);
                
                
                pdfCompleted.haritgl = day + ", tanggal " + dates + ", bulan " + month + ", tahun " + year + " Pukul " + jam; 
                pdfCompleted.Nama = getValueFromRemedy(requestRecord, 536870937);
                pdfCompleted.NIP = getValueFromRemedy(requestRecord, 536870939);
                pdfCompleted.Jabatan = getValueFromRemedy(requestRecord, 536870940);
                pdfCompleted.JenisPerubahanData = getValueFromRemedy(requestRecord, 536870944);
                pdfCompleted.NoKasus = getValueFromRemedy(requestRecord, 536870946);
                pdfCompleted.NPWP = getValueFromRemedy(requestRecord, 536870941);
                pdfCompleted.DataSemula = getValueFromRemedy(requestRecord, 536870932);
                pdfCompleted.DataMenjadi = getValueFromRemedy(requestRecord, 536870938);
                pdfCompleted.PerubahanDilakukan = getValueFromRemedy(requestRecord, 536870942);
                pdfCompleted.Menyetujui = getValueFromRemedy(requestRecord, 536870943);
                pdfCompleted.NIPMenyetujui = getValueFromRemedy(requestRecord, 536870918);
                pdfCompleted.ygMelakukan = getValueFromRemedy(requestRecord, 536870947);
                pdfCompleted.Mengetahui = getValueFromRemedy(requestRecord, 536870936);
                pdfCompleted.NIPMengetahui = getValueFromRemedy(requestRecord, 536870917);
                pdfCompleted.JobTitleMenyetujui = getValueFromRemedy(requestRecord, 536870925);
                pdfCompleted.NoBA = getValueFromRemedy(requestRecord, 536870920);
                String timestampBA = getValueFromRemedy(requestRecord, 536870923);
                String datesBA = dateFormat(timestampBA, dateFormat);
                String monthBA = dateFormat(timestampBA, monthFormat);
                monthBA = bulan(monthBA);
                String yearBA = dateFormat(timestampBA, yearFormat);
                pdfCompleted.tglBA = datesBA + " " + monthBA + " " + yearBA;

                //get instanceID
                List<EntryListInfo> eListReqs = remedyAPI.getRemedyRecordByQuery(remedySession, "SRM:Request", "'SysRequestID'=\"" + pdfCompleted.SysID + "\" ");
                for (EntryListInfo eListReq : eListReqs) {
                    Entry recordReq = remedySession.getEntry("SRM:Request", eListReq.getEntryID(), null);

                    pdfCompleted.srInstanceId = recordReq.get(179).getValue().toString();
                    pdfCompleted.requstNumber = recordReq.get(1000000829).getValue().toString();
                }

                ReportDocument reportDocument = new ReportDocument();
                reportDocument.generateCompletionReport(pdfCompleted);

                requestRecord.put(536870951, new Value("1"));
                remedySession.setEntry(configValue.getRemedyMiddleFormCompleted(), requestRecord.getEntryId(), requestRecord, null, 0);
                logger.info("Success generated pdf NoID : " + pdfCompleted.Nomor);
                logger.info("----------------------------------------------------------------");
            }
        } catch (ARException | IOException e) {
            logger.info("ARException Error on get data: " + e.toString());
        }

        return "result";

    }

    @RequestMapping(value = "printRejected", method = RequestMethod.GET)
    public String printRejected() throws IOException, FileNotFoundException, DocumentException {
        //get Config Value
        ApplicationContext context = new AnnotationConfigApplicationContext(com.bmc.mii.domain.ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        //connection
        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser remedySession = remedyConnection.connectToRemedy(configValue);

        RemedyAPI remedyAPI = new RemedyAPI();

        List<EntryListInfo> listInfos = remedyAPI.getRemedyRecordByQuery(remedySession, configValue.getRemedyMiddleFormCompleted(), "'Status Print'=\"0\" AND 'Type Report'=\"0\" AND 'SR Field 6' != $NULL$");

        try {
            for (EntryListInfo listInfo : listInfos) {
                PdfRejection pdfRejection = new PdfRejection();
                Entry requestRecord = remedySession.getEntry(configValue.getRemedyMiddleFormCompleted(), listInfo.getEntryID(), null);
                requestRecord.put(536870951, new Value("2"));
                remedySession.setEntry(configValue.getRemedyMiddleFormCompleted(), requestRecord.getEntryId(), requestRecord, null, 0);
                pdfRejection.SysID = getValueFromRemedy(requestRecord, 536870913);
                pdfRejection.woNumber = getValueFromRemedy(requestRecord, 536870945);
                pdfRejection.HeadKanwil = getValueFromRemedy(requestRecord, 536870930);
                 if (pdfRejection.HeadKanwil.contains("Kanwil")) {
                    pdfRejection.HeadKanwil = pdfRejection.HeadKanwil.replaceAll("Kanwil", "Kantor Wilayah");
                }
                pdfRejection.HeadKPP = getValueFromRemedy(requestRecord, 536870914);
                pdfRejection.Nomor = getValueFromRemedy(requestRecord, 536870935);
                String hariTimestamp = getValueFromRemedy(requestRecord, 536870922);
                TimeZone indoZone = TimeZone.getTimeZone("Asia/Jakarta");

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                dateFormat.setTimeZone(indoZone);
                String dates = dateFormat(hariTimestamp, dateFormat);

                SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
                monthFormat.setTimeZone(indoZone);
                String month = dateFormat(hariTimestamp, monthFormat);
                month = bulan(month);

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                yearFormat.setTimeZone(indoZone);
                String year = dateFormat(hariTimestamp, yearFormat);

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                timeFormat.setTimeZone(indoZone);
                String jam = dateFormat(hariTimestamp, timeFormat);

                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                dayFormat.setTimeZone(indoZone);
                String day = dateFormat(hariTimestamp, dayFormat);
                day = hari(day);
                
                
                pdfRejection.haritgl = day + ", tanggal " + dates + ", bulan " + month + ", tahun " + year + " Pukul " + jam;
                pdfRejection.Nama = getValueFromRemedy(requestRecord, 536870937);
                pdfRejection.NIP = getValueFromRemedy(requestRecord, 536870939);
                pdfRejection.Jabatan = getValueFromRemedy(requestRecord, 536870940);
                pdfRejection.JenisPerubahanData = getValueFromRemedy(requestRecord, 536870944);
                pdfRejection.NoKasus = getValueFromRemedy(requestRecord, 536870946);
                pdfRejection.NPWP = getValueFromRemedy(requestRecord, 536870941);
                pdfRejection.DataSemula = getValueFromRemedy(requestRecord, 536870932);
                pdfRejection.DataMenjadi = getValueFromRemedy(requestRecord, 536870938);
                pdfRejection.PenolakanDilakukan = getValueFromRemedy(requestRecord, 536870934);
                pdfRejection.Menyetujui = getValueFromRemedy(requestRecord, 536870943);
                pdfRejection.NIPMenyetujui = getValueFromRemedy(requestRecord, 536870918);
                pdfRejection.ygMelakukan = getValueFromRemedy(requestRecord, 536870947);
                pdfRejection.Mengetahui = getValueFromRemedy(requestRecord, 536870936);
                pdfRejection.JobTitleMenyetujui = getValueFromRemedy(requestRecord, 536870925);
                pdfRejection.NIPMengetahui = getValueFromRemedy(requestRecord, 536870917);
                pdfRejection.NoBA = getValueFromRemedy(requestRecord, 536870920);
                String timestampBA = getValueFromRemedy(requestRecord, 536870923);
                String datesBA = dateFormat(timestampBA, dateFormat);
                String monthBA = dateFormat(timestampBA, monthFormat);
                monthBA = bulan(monthBA);
                String yearBA = dateFormat(timestampBA, yearFormat);
                pdfRejection.tglBA = datesBA + " " + monthBA + " " + yearBA;

                //get instanceID
                List<EntryListInfo> eListReqs = remedyAPI.getRemedyRecordByQuery(remedySession, "SRM:Request", "'SysRequestID'=\"" + pdfRejection.SysID + "\" ");
                for (EntryListInfo eListReq : eListReqs) {
                    Entry recordReq = remedySession.getEntry("SRM:Request", eListReq.getEntryID(), null);

                    pdfRejection.srInstanceId = recordReq.get(179).getValue().toString();
                    pdfRejection.requstNumber = recordReq.get(1000000829).getValue().toString();
                }

                ReportDocument reportDocument = new ReportDocument();
                reportDocument.generateRejectionReport(pdfRejection);

                requestRecord.put(536870951, new Value("1"));
                remedySession.setEntry(configValue.getRemedyMiddleFormCompleted(), requestRecord.getEntryId(), requestRecord, null, 0);
                logger.info("Success generated pdf NoID : " + pdfRejection.Nomor);
                logger.info("----------------------------------------------------------------");
            }
        } catch (ARException | IOException e) {
            logger.info("ARException Error on get data: " + e.toString());
        }

        return "result";

    }

    public String getValueFromRemedy(Entry requestRecord, Object fieldID) {
        if (requestRecord.get(fieldID).getValue() == null) {
            return "";
        }

        return requestRecord.get(fieldID).getValue().toString();
    }

    private static String dateFormat(String date, SimpleDateFormat dateFormat) {

        if (date.isEmpty()) {
            return "";
        }
        String time = date.substring(11, 21);
        long longParsedTime = Long.parseLong(time);
        Date dateTime = new Date(longParsedTime * 1000);

        return dateFormat.format(dateTime);
    }

    private String hari(String day) {
        switch (day) {
            case "Sunday":
                return "Ahad";
            case "Monday":
                return "Senin";
            case "Tuesday":
                return "Selasa";
            case "Wednesday":
                return "Rabu";
            case "Thursday":
                return "Kamis";
            case "Friday":
                return "Jumat";
            case "Saturday":
                return "Sabtu";
        }
        return day;
    }

    private String bulan(String month) {
        switch (month) {
            case "January":
                return "Januari";
            case "February":
                return "Februari";
            case "March":
                return "Maret";
            case "April":
                return "April";
            case "May":
                return "Mei";
            case "June":
                return "Juni";
            case "July":
                return "Juli";
            case "August":
                return "Agustus";
            case "September":
                return "September";
            case "October":
                return "Oktober";
            case "November":
                return "November";
            case "December":
                return "Desember";
        }
        return month;
    }

    @RequestMapping(value = "gethari", method = RequestMethod.GET)
    public String tesHari(Model model) {
        String date = "[Timestamp=1644287687]";
        String time = date.substring(11, 21);
        long longParsedTime = Long.parseLong(time);
        Date dateTime = new Date(longParsedTime * 1000);

        TimeZone indoZone = TimeZone.getTimeZone("Asia/Jakarta");

        SimpleDateFormat estDateFormat = new SimpleDateFormat("dd");
        estDateFormat.setTimeZone(indoZone);
        String dates = estDateFormat.format(dateTime);

        SimpleDateFormat bulan = new SimpleDateFormat("MMMM");
        bulan.setTimeZone(indoZone);
        String month = bulan.format(dateTime);
        month = bulan(month);

        SimpleDateFormat tahun = new SimpleDateFormat("yyyy");
        tahun.setTimeZone(indoZone);
        String year = tahun.format(dateTime);

        SimpleDateFormat times = new SimpleDateFormat("HH:mm");
        times.setTimeZone(indoZone);
        String jam = times.format(dateTime);
        
        SimpleDateFormat times1 = new SimpleDateFormat("hh:mm");
        times1.setTimeZone(indoZone);
        String jam1 = times1.format(dateTime);

        SimpleDateFormat days = new SimpleDateFormat("EEEE");
        days.setTimeZone(indoZone);
        String day = days.format(dateTime);
        day = hari(day);

        String test = dateFormat(date, estDateFormat);

        model.addAttribute("day", day + ", tanggal " + dates + ", bulan " + month + ", tahun " + year + " Pukul " + jam);
        model.addAttribute("day1", day + ", tanggal " + dates + ", bulan " + month + ", tahun " + year + " Pukul " + jam1);
        return "result";

    }

}
