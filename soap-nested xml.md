**Step 1: Create JAXB Annotated Classes**
First, create Java classes to represent the XML structure. Each class should be annotated with JAXB annotations.

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

// Root element
@XmlRootElement(name = "Employees")
public class Employees {
    private List<Employee> employees;

    @XmlElement(name = "Employee")
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

@XmlType(propOrder = {"employeeId", "firstName", "lastName", "skills"})
class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private Skills skills;

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
    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }
}

@XmlType
class Skills {
    private List<String> skill;

    @XmlElement(name = "skill")
    public List<String> getSkill() {
        return skill;
    }

    public void setSkill(List<String> skill) {
        this.skill = skill;
    }
}


**Step 2: Marshall Java Objects to XML**
Create a class with a method to convert Java objects to XML using JAXB.

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.util.Arrays;

public class JAXBExample {
    public static void main(String[] args) {
        try {
            // Create sample data
            Skills skills1 = new Skills();
            skills1.setSkill(Arrays.asList("Java", "C++"));

            Employee emp1 = new Employee();
            emp1.setEmployeeId(1);
            emp1.setFirstName("John");
            emp1.setLastName("Doe");
            emp1.setSkills(skills1);

            Skills skills2 = new Skills();
            skills2.setSkill(Arrays.asList("Java", "C++"));

            Employee emp2 = new Employee();
            emp2.setEmployeeId(2);
            emp2.setFirstName("Jane");
            emp2.setLastName("Smith");
            emp2.setSkills(skills2);

            Employees employees = new Employees();
            employees.setEmployees(Arrays.asList(emp1, emp2));

            // Create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(Employees.class);
            Marshaller marshaller = context.createMarshaller();

            // Optional: format the XML output
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Marshal the employees object to System.out
            marshaller.marshal(employees, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}


**Step 3: Unmarshall XML Back to Java Objects**
Create a class with a method to convert XML back to Java objects using JAXB.
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;

public class JAXBUnmarshalExample {
    public static void main(String[] args) {
        try {
            // Sample XML input
            String xmlInput = "<Employees>\n" +
                    "    <Employee>\n" +
                    "        <EmployeeId>1</EmployeeId>\n" +
                    "        <FirstName>John</FirstName>\n" +
                    "        <LastName>Doe</LastName>\n" +
                    "        <Skills>\n" +
                    "            <skill>Java</skill>\n" +
                    "            <skill>C++</skill>\n" +
                    "        </Skills>\n" +
                    "    </Employee>\n" +
                    "    <Employee>\n" +
                    "        <EmployeeId>2</EmployeeId>\n" +
                    "        <FirstName>Jane</FirstName>\n" +
                    "        <LastName>Smith</LastName>\n" +
                    "        <Skills>\n" +
                    "            <skill>Java</skill>\n" +
                    "            <skill>C++</skill>\n" +
                    "        </Skills>\n" +
                    "    </Employee>\n" +
                    "</Employees>";

            // Create JAXB context and instantiate unmarshaller
            JAXBContext context = JAXBContext.newInstance(Employees.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Unmarshal the XML input to Java objects
            StringReader reader = new StringReader(xmlInput);
            Employees employees = (Employees) unmarshaller.unmarshal(reader);

            // Output the Java objects
            for (Employee emp : employees.getEmployees()) {
                System.out.println("Employee ID: " + emp.getEmployeeId());
                System.out.println("First Name: " + emp.getFirstName());
                System.out.println("Last Name: " + emp.getLastName());
                System.out.println("Skills: " + emp.getSkills().getSkill());
                System.out.println();
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}

