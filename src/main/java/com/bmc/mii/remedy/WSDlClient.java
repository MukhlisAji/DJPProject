/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bmc.mii.remedy;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author mukhlisaj
 */
public class WSDlClient {

    public static void main(String[] args) throws MalformedURLException {
        WSDlClient testHelloAge = new WSDlClient();
        testHelloAge.Send_XML();
    }

    public void geHelloAge() throws MalformedURLException {
        URL wsURL = new URL("https://apps.pertamina.com/arsys/WSDL/public/ptmkpsscrmdqa03/PTM:SSC:HR:DTM:WorkOrderCreate");
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString = "";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;

        String xmlInput
                = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:PTM:SSC:HR:DTM:WorkOrderCreate\">\n"
                + "   <soapenv:Header>\n"
                + "      <urn:AuthenticationInfo>\n"
                + "         <urn:userName>appadmin</urn:userName>\n"
                + "         <urn:password>123456</urn:password>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:authentication>?</urn:authentication>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:locale>?</urn:locale>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:timeZone>?</urn:timeZone>\n"
                + "      </urn:AuthenticationInfo>\n"
                + "   </soapenv:Header>\n"
                + "   <soapenv:Body>\n"
                + "      <urn:New_Create_Operation_0>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:RequestorLoginID>appadmin</urn:RequestorLoginID>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:TransactionNumber>dtm2124234234</urn:TransactionNumber>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:ServiceName>?</urn:ServiceName>\n"
                + "      </urn:New_Create_Operation_0>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

        try {
            //checking connection before call
            HttpURLConnection con = (HttpURLConnection) wsURL.openConnection();
            int status = con.getResponseCode();
            if (status == 200) {
                //parser.parse(UrlToParse);
                System.out.println("Connection is OK: ");
            } else {
                System.out.println("Http responde code: " + status);
            }

            connection = wsURL.openConnection();
            httpConn = (HttpURLConnection) connection;

            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
//            System.out.println("buffer : " + xmlInput);

            String SOAPAction = "urn:PTM:SSC:HR:DTM:WorkOrderCreate/New_Create_Operation_0";
            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Length", String
                    .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml;charset=utf-8");

            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Host", "apps.pertamina.com");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();

            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);

            while ((responseString = in.readLine()) != null) {
                outputString = outputString + responseString;
            }
            System.out.println(outputString);
            System.out.println("\n\n");

            // Get the response from the web service call
//            Document document = parseXmlFile(outputString);
//
//            NodeList nodeLst = document.getElementsByTagName("faultstring");
//            String webServiceResponse = nodeLst.item(0).getTextContent();
//            System.out.println("The response from the web service call is : " + webServiceResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


        public void Send_XML() {
            try {
                String url = "https://apps.pertamina.com/arsys/services/ARService?server=ptmkpsscrmdqa03&webService=PTM:SSC:HR:DTM:WorkOrderCreate";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
                String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:PTM:SSC:HR:DTM:WorkOrderCreate\">\n"
                + "   <soapenv:Header>\n"
                + "      <urn:AuthenticationInfo>\n"
                + "         <urn:userName>appadmin</urn:userName>\n"
                + "         <urn:password>123456</urn:password>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:authentication>?</urn:authentication>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:locale>?</urn:locale>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:timeZone>?</urn:timeZone>\n"
                + "      </urn:AuthenticationInfo>\n"
                + "   </soapenv:Header>\n"
                + "   <soapenv:Body>\n"
                + "      <urn:New_Create_Operation_0>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:RequestorLoginID>appadmin</urn:RequestorLoginID>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:TransactionNumber>dtm2124234234</urn:TransactionNumber>\n"
                + "         <!--Optional:-->\n"
                + "         <urn:ServiceName>?</urn:ServiceName>\n"
                + "      </urn:New_Create_Operation_0>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(xml);
                wr.flush();
                wr.close();
                String responseStatus = con.getResponseMessage();
                System.out.println(responseStatus);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("response:" + response.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        
    }


