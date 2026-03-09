package ch.etml.es.payroll.Controllers;

public class DepartmentAlreadyExistsException extends RuntimeException{

    public DepartmentAlreadyExistsException(String acronym){
        super("Department " + acronym + " already exists");}
}
