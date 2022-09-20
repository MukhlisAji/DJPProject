/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmc.mii.poi;

import com.bmc.mii.controller.remedyController;
import com.bmc.mii.domain.SendPDF;
import com.bmc.mii.domain.djp.PdfComplete;
import com.bmc.mii.domain.djp.PdfRejection;
import com.bmc.mii.domain.djp.PdfRequest;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author mukhlis purnomo aji
 */
public class ReportDocument {

    Document document = new Document(PageSize.A4, 25, 25, 20, 25);

    public void generateRequestReport(PdfRequest pdfRequest) throws FileNotFoundException, DocumentException, BadElementException, IOException {

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Surat Permintaan_" + pdfRequest.SysID + ".pdf"));

        document.open();

        Font fontHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 11, BaseColor.BLACK);
        Font fontUntukIsi = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
        Font fontUntukIsi2 = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
        Font fontTable = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLACK);
        Font fontTableHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);

        Image logo = Image.getInstance(Toolkit.getDefaultToolkit().createImage("C:\\Users\\mii\\DJPLogo.png"), Color.WHITE);

        logo.setAlignment(logo.ALIGN_LEFT);
        logo.setPaddingTop(15);
        PdfPTable header = new PdfPTable(2);
        header.setHorizontalAlignment(header.ALIGN_LEFT);
        header.setWidthPercentage(100);

        header.setWidths(new float[]{15, 85});
        // set defaults
        header.setTotalWidth(500f);
        header.setLockedWidth(true);
        header.getDefaultCell().setFixedHeight(30);
        header.getDefaultCell().setBorderColor(BaseColor.WHITE);
        header.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        header.addCell(logo);

        PdfPCell cell = new PdfPCell(new Phrase("KEMENTERIAN KEUANGAN REPUBLIK INDONESIA\nDIREKTORAT JENDERAL PAJAK\n" + pdfRequest.HeadKanwil.toUpperCase() + "\n" + pdfRequest.HeadKPP.toUpperCase(), fontHeader));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(10, 0);
        header.addCell(cell);

        document.add(header);

        Paragraph paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi);
        paragraph1.setSpacingAfter(5f);
        paragraph1.setSpacingBefore(5f);
        paragraph1.setLeading(11, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);

        paragraph1.add("\n\nFORMULIR PERMINTAAN PERUBAHAN DATA PADA SISTEM INFORMASI DIREKTORAT JENDERAL PAJAK");
        paragraph1.add("\nNomor : " + pdfRequest.Nomor);
        document.add(paragraph1);

        paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi2);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\n\n\nHari/Tanggal");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfRequest.haritgl);

        paragraph1.add("\nKantor");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfRequest.Kantor);

        paragraph1.add("\nNama Pengusul");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfRequest.NamaPengusul);

        paragraph1.add("\nJabatan");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfRequest.Jabatan);

        paragraph1.add("\nSeksi");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfRequest.Seksi);
        paragraph1.add("\n\n");

        document.add(paragraph1);

        PdfPTable TContent = new PdfPTable(7);

        TContent.setWidthPercentage(100);
        TContent.setTotalWidth(545f);
        TContent.setWidths(new int[]{3, 17, 17, 18, 14, 14, 18});
        TContent.setLockedWidth(true);

        cell = new PdfPCell();
        paragraph1 = new Paragraph("NO");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        paragraph1.setSpacingBefore(5f);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("JENIS KASUS");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        paragraph1.setSpacingBefore(5f);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("NOMOR KASUS/NOMOR PRODUK HUKUM*");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("NPWP/NOP DAN NAMA WAJIB PAJAK**");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("DATA SEMULA");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("DATA MENJADI");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("ALASAN PERUBAHAN DATA");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        TContent.addCell(new Phrase("1", fontTable));
        TContent.addCell(new Phrase(pdfRequest.JenisKasus, fontTable));
        TContent.addCell(new Phrase(pdfRequest.NoKasus, fontTable));
        TContent.addCell(new Phrase(pdfRequest.NPWP, fontTable));
        TContent.addCell(new Phrase(pdfRequest.DataSemula, fontTable));
        TContent.addCell(new Phrase(pdfRequest.DataMenjadi, fontTable));
        TContent.addCell(new Phrase(pdfRequest.AlasanPerubahan, fontTable));
        document.add(TContent);

        paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi2);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\nPenjelasan kronologis masalah yang terjadi :\n");
        paragraph1.add(pdfRequest.Kronologis);

        paragraph1.add("\n\nDengan ini pula kami menyatakan bahwa :\n");
        document.add(paragraph1);

        TContent = new PdfPTable(2);
        TContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
        TContent.setWidths(new int[]{4, 96});
        TContent.setWidthPercentage(100);

        TContent.setTotalWidth(515f);
        TContent.setLockedWidth(true);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("1.", fontUntukIsi2));
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("Kami belum melakukan pengiriman produk hukum kepada Wajib Pajak dan/atau tindakan administrasi perpajakan lanjutan terkait dengan produk hukum ini.");
        paragraph1.setLeading(10, 0);
        paragraph1.setFont(fontUntukIsi2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("2.", fontUntukIsi2));
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("Segala konsekuensi hukum yang timbul berkaitan dengan permintaan perubahan data ini menjadi tanggung jawab kami sepenuhnya sebagai pemohon perubahan data.");
        paragraph1.setLeading(10, 0);
        paragraph1.setFont(fontUntukIsi2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        document.add(TContent);

        TContent = new PdfPTable(3);
        TContent.setWidthPercentage(100);
        TContent.setTotalWidth(545f);
        TContent.setWidths(new int[]{34, 33, 33});
        TContent.setLockedWidth(true);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\n\n\nYang Mengusulkan,\n\n\n\n" + pdfRequest.NamaPengusul + "\nNIP. " + pdfRequest.NIPPengusul, fontUntukIsi2));
        TContent.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\n\nMengetahui,\n"+ pdfRequest.JobTitleMengetahui+"\n\n\n\n" + pdfRequest.Mengetahui + "\nNIP. " + pdfRequest.NIPMengetahui, fontUntukIsi2));
        TContent.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\n\nMenyetujui,\nKepala Kantor\n\n\n\n" + pdfRequest.Menyetujui + "\nNIP. " + pdfRequest.NIPMenyetujui, fontUntukIsi2));
        TContent.addCell(cell);
        document.add(TContent);

        paragraph1 = new Paragraph();
        paragraph1.setFont(fontTable);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\n\n\n*)Diisi apabila atas perubahan data tersebut ada nomor kasus/nomor produk hukum");
        paragraph1.add("\n**)Diisi apabila NPWP/NOP yang berkenan dengan perubahan data tersebut");
        paragraph1.add("\n***)Dokumen ini dibuat oleh sistem komputer sehingga tidak memerlukan tanda tangan");
        document.add(paragraph1);
        document.close();

        //sending pdf to srm:workinfo
        SendPDF sendPDF = new SendPDF();
        sendPDF.namafile = "Surat Permintaan_" + pdfRequest.SysID + ".pdf";
        sendPDF.srInstanceId = pdfRequest.srInstanceId;
        sendPDF.requstNumber = pdfRequest.requstNumber;
        remedyController remedyController = new remedyController();
        remedyController.sendPdftoWorkInfo(sendPDF);

    }

    public void generateCompletionReport(PdfComplete pdfComplete) throws FileNotFoundException, DocumentException, BadElementException, IOException {

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Surat Penyelesaian_" + pdfComplete.SysID + ".pdf"));

        document.open();

        Font fontHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 11, BaseColor.BLACK);
        Font fontUntukIsi = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
        Font fontUntukIsi2 = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
        Font fontTable = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLACK);
        Font fontTableHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);

        Image logo = Image.getInstance(Toolkit.getDefaultToolkit().createImage("C:\\Users\\mii\\DJPLogo.png"), Color.WHITE);

        logo.setAlignment(logo.ALIGN_LEFT);
        logo.setPaddingTop(15);
        PdfPTable header = new PdfPTable(2);
        header.setHorizontalAlignment(header.ALIGN_LEFT);
        header.setWidthPercentage(100);

        header.setWidths(new float[]{15, 85});
        // set defaults
        header.setTotalWidth(500f);
        header.setLockedWidth(true);
        header.getDefaultCell().setFixedHeight(30);
        header.getDefaultCell().setBorderColor(BaseColor.WHITE);
        header.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        header.addCell(logo);

        PdfPCell cell = new PdfPCell(new Phrase("KEMENTERIAN KEUANGAN REPUBLIK INDONESIA\nDIREKTORAT JENDERAL PAJAK\n" + pdfComplete.HeadKanwil.toUpperCase() + "\n" + pdfComplete.HeadKPP.toUpperCase(), fontHeader));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(10, 0);
        header.addCell(cell);

        document.add(header);

        Paragraph paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi);
        paragraph1.setSpacingAfter(5f);
        paragraph1.setSpacingBefore(5f);
        paragraph1.setLeading(11, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);

        paragraph1.add("\n\nBERITA ACARA PENYELESAIAN PERUBAHAN DATA PADA SISTEM INFORMASI DIREKTORAT JENDERAL PAJAK");
        paragraph1.add("\nNomor : " + pdfComplete.Nomor);
        document.add(paragraph1);

        paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi2);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\n\n\nPada hari ini " + pdfComplete.haritgl + " bertempat di Jakarta, saya : ");

        paragraph1.add("\n\nNama");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfComplete.Nama);

        paragraph1.add("\nNIP");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfComplete.NIP);

        paragraph1.add("\nJabatan");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfComplete.Jabatan);
        paragraph1.add("\n\n");
        paragraph1.add("Menyatakan telah melakukan perubahan data " + pdfComplete.Nomor + " dengan kondisi sebagai berikut :\n\n");

        document.add(paragraph1);

        PdfPTable TContent = new PdfPTable(7);

        TContent.setWidthPercentage(100);
        TContent.setTotalWidth(545f);
        TContent.setWidths(new int[]{3, 17, 17, 18, 14, 14, 18});
        TContent.setLockedWidth(true);

        cell = new PdfPCell();
        paragraph1 = new Paragraph("NO");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        paragraph1.setSpacingBefore(5f);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("JENIS PERUBAHAN DATA");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        paragraph1.setSpacingBefore(5f);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("NOMOR KASUS/NOMOR PRODUK HUKUM*");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("NPWP/NOP DAN NAMA WAJIB PAJAK**");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("DATA SEMULA");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("DATA MENJADI");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("PERUBAHAN YANG DILAKUKAN");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        TContent.addCell(new Phrase("1", fontTable));
        TContent.addCell(new Phrase(pdfComplete.JenisPerubahanData, fontTable));
        TContent.addCell(new Phrase(pdfComplete.NoKasus, fontTable));
        TContent.addCell(new Phrase(pdfComplete.NPWP, fontTable));
        TContent.addCell(new Phrase(pdfComplete.DataSemula, fontTable));
        TContent.addCell(new Phrase(pdfComplete.DataMenjadi, fontTable));
        TContent.addCell(new Phrase(pdfComplete.PerubahanDilakukan, fontTable));
        document.add(TContent);

        paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi2);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\nPenjelasan perubahan data yang dilakukan :\n");
        paragraph1.add(pdfComplete.PerubahanDilakukan);

        paragraph1.add("\n\nDemikian Pernyataan ini dibuat dengan sesungguhnya berdasarkan Surat Permintaan Perubahan Data Nomor Surat : " + pdfComplete.NoBA + " tanggal : " + pdfComplete.tglBA + ".");
        document.add(paragraph1);

        TContent = new PdfPTable(2);
        TContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
        TContent.setWidths(new int[]{4, 96});
        TContent.setWidthPercentage(100);

        TContent = new PdfPTable(3);
        TContent.setWidthPercentage(100);
        TContent.setTotalWidth(545f);
        TContent.setWidths(new int[]{34, 33, 33});
        TContent.setLockedWidth(true);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\n\n\n\nYang Melakukan,\n\n\n\n" + pdfComplete.Nama + "\nNIP. " + pdfComplete.NIP, fontUntukIsi2));
        TContent.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\n\n\nMenyetujui,\n"+ pdfComplete.JobTitleMenyetujui+"\n\n\n\n" + pdfComplete.Menyetujui + "\nNIP. " + pdfComplete.NIPMenyetujui, fontUntukIsi2));
        TContent.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\nMengetahui,\nKepala Subdirektorat Pemantauan dan Pelayanan Sistem Informasi\n\n\n\n" + pdfComplete.Mengetahui + "\nNIP. " + pdfComplete.NIPMengetahui, fontUntukIsi2));
        TContent.addCell(cell);
        document.add(TContent);
        paragraph1 = new Paragraph();
        paragraph1.setFont(fontTable);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\n\n\n*)Diisi apabila atas perubahan data tersebut ada nomor kasus/nomor produk hukum");
        paragraph1.add("\n**)Diisi apabila NPWP/NOP yang berkenan dengan perubahan data tersebut");
        paragraph1.add("\n***)Dokumen ini dibuat oleh sistem komputer sehingga tidak memerlukan tanda tangan");
        document.add(paragraph1);
        document.close();

        //sending pdf to srm:workinfo
        SendPDF sendPDF = new SendPDF();
        sendPDF.namafile = "Surat Penyelesaian_" + pdfComplete.SysID + ".pdf";
        sendPDF.srInstanceId = pdfComplete.srInstanceId;
        sendPDF.requstNumber = pdfComplete.requstNumber;
        sendPDF.woNumber = pdfComplete.woNumber;
        remedyController remedyController = new remedyController();
        remedyController.sendPdftoWOIWorkInfo(sendPDF);

    }

    public void generateRejectionReport(PdfRejection pdfRejection) throws FileNotFoundException, DocumentException, BadElementException, IOException {

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Surat Penolakan_" + pdfRejection.SysID + ".pdf"));

        document.open();

        Font fontHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 11, BaseColor.BLACK);
        Font fontUntukIsi = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
        Font fontUntukIsi2 = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
        Font fontTable = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLACK);
        Font fontTableHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);

        Image logo = Image.getInstance(Toolkit.getDefaultToolkit().createImage("C:\\Users\\mii\\DJPLogo.png"), Color.WHITE);

        logo.setAlignment(logo.ALIGN_LEFT);
        logo.setPaddingTop(15);
        PdfPTable header = new PdfPTable(2);
        header.setHorizontalAlignment(header.ALIGN_LEFT);
        header.setWidthPercentage(100);

        header.setWidths(new float[]{15, 85});
        // set defaults
        header.setTotalWidth(500f);
        header.setLockedWidth(true);
        header.getDefaultCell().setFixedHeight(30);
        header.getDefaultCell().setBorderColor(BaseColor.WHITE);
        header.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        header.addCell(logo);
        String kanwil = "KANTOR WILAYAH DJP RIAU";
        String KPP = "KPP Pratama Pekan Baru Riau";

        PdfPCell cell = new PdfPCell(new Phrase("KEMENTERIAN KEUANGAN REPUBLIK INDONESIA\nDIREKTORAT JENDERAL PAJAK\n" +pdfRejection.HeadKanwil.toUpperCase() + "\n" + pdfRejection.HeadKPP.toUpperCase(), fontHeader));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(10, 0);
        header.addCell(cell);

        document.add(header);

        Paragraph paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi);
        paragraph1.setSpacingAfter(5f);
        paragraph1.setSpacingBefore(5f);
        paragraph1.setLeading(11, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);

        paragraph1.add("\n\nBERITA ACARA PENOLAKAN PERUBAHAN DATA PADA SISTEM INFORMASI DIREKTORAT JENDERAL PAJAK");
        paragraph1.add("\nNomor : " + pdfRejection.Nomor);
        document.add(paragraph1);

        paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi2);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\n\n\nPada hari ini " + pdfRejection.haritgl + " bertempat di Jakarta, saya : ");

        paragraph1.add("\n\nNama");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfRejection.Nama);

        paragraph1.add("\nNIP");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " +pdfRejection.NIP);

        paragraph1.add("\nJabatan");
        paragraph1.add(Chunk.TABBING);
        paragraph1.add(": " + pdfRejection.Jabatan);
        paragraph1.add("\n\n");
        paragraph1.add("Menyatakan telah menolak perubahan data  dengan kondisi sebagai berikut :\n\n");

        document.add(paragraph1);

        PdfPTable TContent = new PdfPTable(7);

        TContent.setWidthPercentage(100);
        TContent.setTotalWidth(545f);
        TContent.setWidths(new int[]{3, 17, 17, 18, 14, 14, 18});
        TContent.setLockedWidth(true);

        cell = new PdfPCell();
        paragraph1 = new Paragraph("NO");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        paragraph1.setSpacingBefore(5f);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("JENIS PERUBAHAN DATA");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        paragraph1.setSpacingBefore(5f);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("NOMOR KASUS/NOMOR PRODUK HUKUM*");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("NPWP/NOP DAN NAMA WAJIB PAJAK**");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("DATA SEMULA");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("DATA MENJADI");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        cell = new PdfPCell();
        paragraph1 = new Paragraph("PERUBAHAN YANG DILAKUKAN");
        paragraph1.setAlignment(paragraph1.ALIGN_CENTER);
        paragraph1.setFont(fontTableHeader);
        cell.addElement(paragraph1);
        TContent.addCell(cell);
        TContent.addCell(new Phrase("1", fontTable));
        TContent.addCell(new Phrase(pdfRejection.JenisPerubahanData, fontTable));
        TContent.addCell(new Phrase(pdfRejection.NoKasus, fontTable));
        TContent.addCell(new Phrase(pdfRejection.NPWP, fontTable));
        TContent.addCell(new Phrase(pdfRejection.DataSemula, fontTable));
        TContent.addCell(new Phrase(pdfRejection.DataMenjadi, fontTable));
        TContent.addCell(new Phrase("Tidak Ada", fontTable));
        document.add(TContent);

        paragraph1 = new Paragraph();
        paragraph1.setFont(fontUntukIsi2);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\nPenjelasan penolakan data yang dilakukan :\n");
        paragraph1.add(pdfRejection.PenolakanDilakukan);

        paragraph1.add("\n\nDemikian Pernyataan ini dibuat dengan sesungguhnya berdasarkan Surat Permintaan Perubahan Data Nomor Surat : " + pdfRejection.NoBA + " tanggal : " + pdfRejection.tglBA + ".");
        document.add(paragraph1);

        TContent = new PdfPTable(2);
        TContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
        TContent.setWidths(new int[]{4, 96});
        TContent.setWidthPercentage(100);

        TContent = new PdfPTable(3);
        TContent.setWidthPercentage(100);
        TContent.setTotalWidth(545f);
        TContent.setWidths(new int[]{34, 33, 33});
        TContent.setLockedWidth(true);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\n\n\n\nYang Melakukan,\n\n\n\n" + pdfRejection.Nama + "\nNIP. " + pdfRejection.NIP, fontUntukIsi2));
        TContent.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\n\n\nMenyetujui,\n"+ pdfRejection.JobTitleMenyetujui +"\n\n\n\n" + pdfRejection.Menyetujui + "\nNIP. " + pdfRejection.NIPMenyetujui, fontUntukIsi2));
        TContent.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(new Phrase("\n\nMengetahui,\nKepala Subdirektorat Pemantauan dan Pelayanan Sistem Informasi\n\n\n\n" + pdfRejection.Mengetahui + "\nNIP. " + pdfRejection.NIPMengetahui, fontUntukIsi2));
        TContent.addCell(cell);
        document.add(TContent);

        paragraph1 = new Paragraph();
        paragraph1.setFont(fontTable);
        paragraph1.setLeading(10, 0);
        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);

        paragraph1.add("\n\n\n*)Diisi apabila atas perubahan data tersebut ada nomor kasus/nomor produk hukum");
        paragraph1.add("\n**)Diisi apabila NPWP/NOP yang berkenan dengan perubahan data tersebut");
        paragraph1.add("\n***)Dokumen ini dibuat oleh sistem komputer sehingga tidak memerlukan tanda tangan");
        document.add(paragraph1);
        document.close();
        
        //sending pdf to srm:workinfo
        SendPDF sendPDF = new SendPDF();
        sendPDF.namafile = "Surat Penolakan_" + pdfRejection.SysID + ".pdf";
        sendPDF.srInstanceId = pdfRejection.srInstanceId;
        sendPDF.requstNumber = pdfRejection.requstNumber;
        sendPDF.woNumber = pdfRejection.woNumber;
        remedyController remedyController = new remedyController();
        remedyController.sendPdftoWOIWorkInfo(sendPDF);

    }

}
