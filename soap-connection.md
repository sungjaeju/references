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

**Step 2: Create Java POJOs
Define the Java classes corresponding to the XML structure.

Employee.java
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Employee")
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private List<String> skills;

    @XmlElement(name = "EmployeeId")
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @XmlElement(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement(name = "Skills")
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}



**Step 3: Create and Send the SOAP Request
Here’s the updated Java example:

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.*;

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
            System.out.println("\n");

            // Handle the SOAP response and convert to POJOs
            Employees employees = handleSOAPResponse(soapResponse);
            if (employees != null) {
                for (Employee employee : employees.getEmployees()) {
                    System.out.println("Employee ID: " + employee.getEmployeeId());
                    System.out.println("First Name: " + employee.getFirstName());
                    System.out.println("Last Name: " + employee.getLastName());
                    System.out.println("Skills: " + String.join(", ", employee.getSkills()));
                }
            }

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

    private static Employees handleSOAPResponse(SOAPMessage soapResponse) throws SOAPException, JAXBException {
        SOAPBody soapBody = soapResponse.getSOAPBody();

        // Check for any SOAP faults
        if (soapBody.hasFault()) {
            SOAPFault fault = soapBody.getFault();
            System.err.println("SOAP Fault: " + fault.getFaultString());
            return null;
        }

        // Unmarshal the SOAP body to Java objects
        JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Employees) unmarshaller.unmarshal(soapBody.extractContentAsDocument());
    }
}

Explanation
SOAP Connection: Create a connection to the SOAP server using SOAPConnectionFactory.
SOAP Request: Create the SOAP request message using MessageFactory and set the SOAP XML content.
Send Request: Send the SOAP request to the server using soapConnection.call(), and receive the response.
Print Response: Print the response to the console.
Handle SOAP Response:
Check for SOAP faults and handle them appropriately.
