package telran.employee.dao;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CompanyImpl implements  Company{
    Employee[] employees;
    int size;

    public CompanyImpl(int capacity) {
        employees = new Employee[capacity];
    }

    @Override
    public boolean addEmployee(Employee employee) {
        // Есть место, id uniq, сотрудник нот нулл
        int newId;
        if (employee == null) {
            return false;
        } else {
            newId = employee.getId();
        }
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null && !existId(newId)) {
                employees[i] = employee;
                this.size = i+1;
                return true;
            }
        }
        return false;
    }

    @Override
    public Employee removeEmployee(int id) {
        Employee delEmpl;
        if (!existId(id)){
            return null;
        }
        int index = getNumberOfEmployee(id);
        delEmpl = employees[index];
        for (int i = index; i < size - 1; i++) {
            employees[i] = employees[i + 1];
        }
        employees[size - 1] = null;
        size--;
        return delEmpl;
    }

    private int getNumberOfEmployee(int id) {
        if (!existId(id)){
            return -1;
        }
        for (int i = 0; i < employees.length; i++) {
            if (employees[i].getId() == id){
                return i;
            }
        }
        return -1;
    }

    @Override
    public Employee findEmployee(int id) {
        for (Employee employee:employees){
            if (employee.getId() == id){
                return employee;
            }
        }
        return null;
    }

    private boolean existId(int id){
        for (Employee employee:employees){
            if (employee != null && employee.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public double totalSalary() {
        double totalSalary = 0.0;
        for (Employee employee:employees){
            if (employee == null){
                return totalSalary;
            }
            totalSalary += employee.calcSalary();
        }
        return totalSalary;
    }

    @Override
    public double averageSalary() {
        double totalSalary = 0.0;
        int nEmployees = 0;
        if (employees[0] == null) return totalSalary;
        for (Employee employee:employees){
            if (employee == null){
                break;
            }
            nEmployees++;
            totalSalary += employee.calcSalary();
        }
        BigDecimal bd = new BigDecimal(totalSalary/nEmployees);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printEmployees() {
        for (Employee employee : employees) {
            if (employee == null) break;
            //System.out.println(employee.toString());
            System.out.println("Id: " + employee.getId() +
                    " Name: " + employee.getFirstName() +
                    " Last name: " + employee.getLastName() +
                    " Hours: " + employee.getHours() +
                    " Salary: " + employee.calcSalary());
        }
    }

    @Override
    public double totalSales() {
        double totalSales = 0.0;
        for (Employee employee : employees) {
            if (employee == null) {
                return totalSales;
            } else if (employee instanceof SalesManager) {
                SalesManager saler = (SalesManager) employee;
                totalSales += saler.getSalesValue();
            }
        }
        return totalSales;
    }
}
