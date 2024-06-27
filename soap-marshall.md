**Dependencies**
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>3.0.0</version>
</dependency>

**AppConfig.java**
@Configuration
public class AppConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.yourpackage");
        return marshaller;
    }
}


@Configuration
public class AppConfig {

    @Bean
    public JakartaXmlMappingProvider marshaller() {
        JakartaXmlMappingProvider marshaller = new JakartaXmlMappingProvider();
        marshaller.setContextPath("com.yourpackage");
        return marshaller;
    }
}


**Employee.java**
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {
    @XmlElement(name = "EmployeeId")
    private String employeeId;

    @XmlElement(name = "FirstName")
    private String firstName;

    @XmlElement(name = "LastName")
    private String lastName;

    // Getters and Setters
    // Omitted for brevity
}

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employees {

    @XmlElement(name = "Employee")
    private List<Employee> employee;

    // Getters and Setters
    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }
}



**SoapXmlProcessor .java**
import jakarta.xml.bind.*;
import jakarta.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.List;

public class SoapXmlProcessor {

    private JAXBContext jaxbContext;

    public SoapXmlProcessor() {
        try {
            jaxbContext = JAXBContext.newInstance(Employee.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Error initializing JAXB context", e);
        }
    }

    public List<Employee> unmarshalSoapXmlToList(String soapXml) {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StreamSource source = new StreamSource(new StringReader(soapXml));

            // Unmarshal XML into a List<Employee>
            Employees employees = (Employees) unmarshaller.unmarshal(source);
            return employees.getEmployee();
        } catch (JAXBException e) {
            // Handle JAXBException appropriately
            e.printStackTrace();
            return null;
        }
    }
}


**main**
public class Main {
    public static void main(String[] args) {
        String soapXml = "<Employees><Employee><EmployeeId>1</EmployeeId><FirstName>John</FirstName><LastName>Doe</LastName></Employee><Employee><EmployeeId>2</EmployeeId><FirstName>Jane</FirstName><LastName>Smith</LastName></Employee></Employees>";

        SoapXmlProcessor processor = new SoapXmlProcessor();
        List<Employee> employees = processor.unmarshalSoapXmlToList(soapXml);

        if (employees != null) {
            for (Employee employee : employees) {
                System.out.println("Employee ID: " + employee.getEmployeeId());
                System.out.println("First Name: " + employee.getFirstName());
                System.out.println("Last Name: " + employee.getLastName());
                System.out.println();
            }
        }
    }
}

**SoapXmlProcessor.java**
import jakarta.xml.bind.*;
import jakarta.xml.transform.stream.StreamSource;
import jakarta.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

public class SoapXmlProcessor {

    private JAXBContext jaxbContext;

    public SoapXmlProcessor(Class<?>... classesToBeBound) {
        try {
            jaxbContext = JAXBContext.newInstance(classesToBeBound);
        } catch (JAXBException e) {
            throw new RuntimeException("Error initializing JAXB context", e);
        }
    }

    public String marshalToSoapXml(List<Employee> employees) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Wrap the list in a root element if necessary
            EmployeesWrapper wrapper = new EmployeesWrapper();
            wrapper.setEmployeeList(employees);

            StringWriter sw = new StringWriter();
            marshaller.marshal(wrapper, new StreamResult(sw));

            return sw.toString();
        } catch (JAXBException e) {
            // Handle JAXBException appropriately
            e.printStackTrace();
            return null;
        }
    }

    public List<Employee> unmarshalToSoapXml(String soapXml) {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StreamSource source = new StreamSource(new StringReader(soapXml));

            // Unmarshal XML into EmployeesWrapper
            EmployeesWrapper wrapper = (EmployeesWrapper) unmarshaller.unmarshal(source);
            return wrapper.getEmployeeList();
        } catch (JAXBException e) {
            // Handle JAXBException appropriately
            e.printStackTrace();
            return null;
        }
    }

    // Helper class to wrap the list for marshalling
    @XmlRootElement(name = "Employees")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class EmployeesWrapper {
        @XmlElement(name = "Employee")
        private List<Employee> employeeList;

        // Getters and Setters
        public List<Employee> getEmployeeList() {
            return employeeList;
        }

        public void setEmployeeList(List<Employee> employeeList) {
            this.employeeList = employeeList;
        }
    }
}

**Main.java**
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Example: Marshalling a list of Employee objects to SOAP XML
        List<Employee> employees = new ArrayList<>();
        
        Employee employee1 = new Employee();
        employee1.setEmployeeId("1");
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        
        Employee employee2 = new Employee();
        employee2.setEmployeeId("2");
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        
        employees.add(employee1);
        employees.add(employee2);
        
        SoapXmlProcessor processor = new SoapXmlProcessor(Employee.class);
        String soapXml = processor.marshalToSoapXml(employees);
        
        if (soapXml != null) {
            System.out.println("Generated SOAP XML:");
            System.out.println(soapXml);
        }
        
        // Example: Unmarshalling SOAP XML to a list of Employee objects
        String soapXmlList = "<Employees><Employee><EmployeeId>1</EmployeeId><FirstName>John</FirstName><LastName>Doe</LastName></Employee><Employee><EmployeeId>2</EmployeeId><FirstName>Jane</FirstName><LastName>Smith</LastName></Employee></Employees>";
        
        List<Employee> parsedEmployees = processor.unmarshalToSoapXml(soapXmlList);
        
        if (parsedEmployees != null) {
            System.out.println("\nParsed Employees:");
            for (Employee emp : parsedEmployees) {
                System.out.println("Employee ID: " + emp.getEmployeeId());
                System.out.println("First Name: " + emp.getFirstName());
                System.out.println("Last Name: " + emp.getLastName());
                System.out.println();
            }
        }
    }
}

