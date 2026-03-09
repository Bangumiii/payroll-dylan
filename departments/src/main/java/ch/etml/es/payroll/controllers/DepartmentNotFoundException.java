package ch.etml.es.payroll.controllers;

public class DepartmentNotFoundException extends RuntimeException{

    DepartmentNotFoundException(Long id){
        super("Could not find department " + id);
    }
}
