##Step-by-Step Guide:
Add necessary dependencies
Create and send the SOAP request
Print the SOAP response

**Step 1: Add Necessary Dependencies**
Add the following dependencies to your pom.xml:

<dependencies>
    <dependency>
        <groupId>jakarta.xml.soap</groupId>
        <artifactId>jakarta.xml.soap-api</artifactId>
        <version>2.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jaxb</groupId>
        <artifactId>jaxb-runtime</artifactId>
        <version>3.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jaxb</groupId>
        <artifactId>jaxb-core</artifactId>
        <version>3.0.2</version>
    </dependency>
</dependencies>

**Step 2: Create and Send the SOAP Request
Here's a complete Java example using Jakarta XML SOAP:

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SOAPClient {
    public static void main(String[] args) {
        try {
            // Create a SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = "http://www.example.com/soap-service"; // replace with your web service URL
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

            // Process the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);

            soapConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest() throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        String soapXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://www.example.com/soap-service-namespace\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ns:Employees>\n" +
                "         <ns:Employee>\n" +
                "            <ns:EmployeeId>1</ns:EmployeeId>\n" +
                "            <ns:FirstName>John</ns:FirstName>\n" +
                "            <ns:LastName>Doe</ns:LastName>\n" +
                "            <ns:Skills>\n" +
                "               <ns:skill>Java</ns:skill>\n" +
                "               <ns:skill>C++</ns:skill>\n" +
                "            </ns:Skills>\n" +
                "         </ns:Employee>\n" +
                "         <ns:Employee>\n" +
                "            <ns:EmployeeId>2</ns:EmployeeId>\n" +
                "            <ns:FirstName>Jane</ns:FirstName>\n" +
                "            <ns:LastName>Smith</ns:LastName>\n" +
                "            <ns:Skills>\n" +
                "               <ns:skill>Java</ns:skill>\n" +
                "               <ns:skill>C++</ns:skill>\n" +
                "            </ns:Skills>\n" +
                "         </ns:Employee>\n" +
                "      </ns:Employees>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(soapXml.getBytes(StandardCharsets.UTF_8));
        soapMessage = messageFactory.createMessage(new MimeHeaders(), byteArrayInputStream);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "http://www.example.com/soap-service-action"); // replace with your SOAPAction

        soapMessage.saveChanges();

        /* Print the request message, just for debugging */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
}

**Explanation**
SOAP Connection: Use SOAPConnectionFactory and SOAPConnection to create a connection to the SOAP server.
SOAP Request: createSOAPRequest() constructs the SOAP request message.
SOAP Message Creation: The provided SOAP XML is used to create the SOAP message using MessageFactory.
Send Request: The SOAP request is sent to the server using soapConnection.call(), and the response is received.
Print Response: The response is printed to the console for inspection.
Notes
Replace placeholder values (http://www.example.com/soap-service, http://www.example.com/soap-service-action, http://www.example.com/soap-service-namespace) with actual values based on your SOAP service's WSDL.
The jakarta.xml.soap package is used to create and handle the SOAP messages, ensuring compatibility with Jakarta EE.