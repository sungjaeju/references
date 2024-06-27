**Step 1: Add Necessary Dependencies**
If you are using Maven, add the following dependencies to your pom.xml:

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
</dependencies>


**Step 2: Convert SOAP XML String to SOAPMessage
Here's the complete example showing how to convert a SOAP XML string to a SOAPMessage object using Jakarta XML SOAP:

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SOAPMessageExample {
    public static void main(String[] args) {
        // Sample SOAP XML string
        String soapXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <Employees>\n" +
                "         <Employee>\n" +
                "            <EmployeeId>1</EmployeeId>\n" +
                "            <FirstName>John</FirstName>\n" +
                "            <LastName>Doe</LastName>\n" +
                "            <Skills>\n" +
                "               <skill>Java</skill>\n" +
                "               <skill>C++</skill>\n" +
                "            </Skills>\n" +
                "         </Employee>\n" +
                "         <Employee>\n" +
                "            <EmployeeId>2</EmployeeId>\n" +
                "            <FirstName>Jane</FirstName>\n" +
                "            <LastName>Smith</LastName>\n" +
                "            <Skills>\n" +
                "               <skill>Java</skill>\n" +
                "               <skill>C++</skill>\n" +
                "            </Skills>\n" +
                "         </Employee>\n" +
                "      </Employees>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        try {
            // Convert the SOAP XML string to an InputStream
            InputStream is = new ByteArrayInputStream(soapXml.getBytes());

            // Create a SOAPMessage from the InputStream
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage(null, is);

            // Output the SOAPMessage to console
            soapMessage.writeTo(System.out);
        } catch (SOAPException | IOException e) {
            e.printStackTrace();
        }
    }
}
**Explanation
SOAP XML String: The soapXml variable contains the SOAP XML string.
Convert to InputStream: The SOAP XML string is converted to an InputStream using ByteArrayInputStream.
Create SOAPMessage: The MessageFactory class is used to create a SOAPMessage from the InputStream. Note that we use jakarta.xml.soap.MessageFactory here.
Output the SOAPMessage: The SOAPMessage.writeTo() method is used to output the SOAPMessage to the console.


**Step 1: Add Necessary Dependencies
If you are using Maven, ensure you have the necessary dependencies in your pom.xml:
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
        <groupId>com.sun.xml.messaging.saaj</groupId>
        <artifactId>saaj-impl</artifactId>
        <version>2.0.1</version>
    </dependency>
</dependencies>

**Step 2: Create and Send a SOAP Request
Here is an example that demonstrates how to create a SOAP request, send it to a web service, and receive a response:
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

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "http://www.example.com/soap-service-action"); // replace with your SOAPAction

        soapMessage.saveChanges();

        /* Print the request message, just for debugging */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "ns";
        String myNamespaceURI = "http://www.example.com/soap-service-namespace"; // replace with your namespace URI

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        /*
        Constructed SOAP Request Message:
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.example.com/soap-service-namespace">
           <soapenv:Header/>
           <soapenv:Body>
              <ns:myRequest>
                 <ns:myParameter>value</ns:myParameter>
              </ns:myRequest>
           </soapenv:Body>
        </soapenv:Envelope>
        */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("myRequest", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("myParameter", myNamespace);
        soapBodyElem1.addTextNode("value"); // replace with your request parameters
    }
}
