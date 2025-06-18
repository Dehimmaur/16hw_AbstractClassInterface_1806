package telran.employee.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.employee.dao.Company;
import telran.employee.dao.CompanyImpl;
import telran.employee.model.Employee;
import telran.employee.model.Manager;
import telran.employee.model.SalesManager;
import telran.employee.model.WageEmployee;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {
    Company company;
    Employee[] firm;



    @BeforeEach
    void setUp() {
        company = new CompanyImpl(5);
        firm = new Employee[5];
        firm[0] = new Manager(1000, "John", "Smith", 180, 20_000, 20);
        firm[1] = new WageEmployee(2000, "Mary", "Smith", 180, 50);
        firm[2] = new SalesManager(3000, "Peter", "Jackson", 180, 100_000, 0.05);
        firm[3] = new SalesManager(4000, "Tigran", "Petrosian", 90, 500_000, 0.1);
        for (int i = 0; i < firm.length; i++) {
            company.addEmployee(firm[i]);
        }
    }

    @Test
    void addEmployee() {
        assertFalse(company.addEmployee(firm[3]));
        Employee employee = new SalesManager(5000, "Andy", "Jackson", 90, 40_000, 0.1);
        assertTrue(company.addEmployee(employee));
        assertEquals(5, company.size());
        employee = new SalesManager(6000, "Andy", "Jackson", 90, 40_000, 0.1);
        assertFalse(company.addEmployee(employee));
    }

    @Test
    void removeEmployee() {
        company.removeEmployee(4000);
        assertEquals(3, company.size());
        Employee employee = new SalesManager(3000, "Peter", "Jackson", 180, 100_000, 0.05);
        assertEquals(employee, company.removeEmployee(3000));
        assertEquals(null, company.removeEmployee(10_000));
        assertEquals(2, company.size());
    }

    @Test
    void findEmployee() {
        Employee empl = new SalesManager(4000, "Tigran", "Petrosian", 90, 500_000, 0.1);
        assertEquals(empl,company.findEmployee(4000));
        assertNotEquals(empl,company.findEmployee(3000));
        assertNotEquals(empl,company.findEmployee(2000));
    }

    @Test
    void totalSalary() {
        assertEquals(89_080, company.totalSalary());
        company.removeEmployee(1000);
        company.removeEmployee(2000);
        company.removeEmployee(3000);
        company.removeEmployee(4000);
        assertEquals(0.0, company.totalSalary());
    }

    @Test
    void averageSalary() {
        assertEquals(22_270, company.averageSalary());
        company.removeEmployee(4000);
        assertEquals(13026.67, company.averageSalary());
        company.removeEmployee(1000);
        company.removeEmployee(2000);
        assertEquals(180*36, company.averageSalary());
    }

    @Test
    void size() {
        assertEquals(4, company.size());
        Employee employee = new SalesManager(5000, "Andy", "Jackson", 90, 40_000, 0.1);
        company.addEmployee(employee);
        assertEquals(5, company.size());
        company.removeEmployee(4000);
        company.removeEmployee(3000);
        company.removeEmployee(2000);
        assertEquals(2, company.size());
    }

    @Test
    void printEmployees() {
        company.printEmployees();
        company.removeEmployee(3000);
        company.removeEmployee(1000);
        company.printEmployees();
    }

    @Test
    void totalSales() {
        assertEquals(600_000, company.totalSales());
        company.removeEmployee(3000);
        assertEquals(500_000, company.totalSales());
        company.removeEmployee(4000);
        assertEquals(0.0, company.totalSales());
        Employee employee = new SalesManager(5000, "Andy", "Jackson", 90, 40_000, 0.1);
        company.addEmployee(employee);
        assertEquals(40_000, company.totalSales());
    }
}