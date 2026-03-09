package ch.etml.es.payroll.Controllers;

public class EmployeeAlreadyExistsExeption extends RuntimeException {
    public EmployeeAlreadyExistsExeption(String message) {
        super(message);
    }
}
