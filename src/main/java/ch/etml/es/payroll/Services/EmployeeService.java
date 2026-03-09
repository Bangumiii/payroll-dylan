package ch.etml.es.payroll.Services;

import ch.etml.es.payroll.Controllers.EmployeeAlreadyExistsExeption;
import ch.etml.es.payroll.Entities.Employee;
import ch.etml.es.payroll.Repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private static EmployeeRepository repository = null;

    public EmployeeService(EmployeeRepository repository) {
        EmployeeService.repository = repository;
    }

    public static Employee hire (Employee newEmployee){
        Employee existing = repository.findByName(newEmployee.getName()).orElse(null);
        if(existing != null){
            throw new EmployeeAlreadyExistsExeption("Employee already exists");
        }

        return repository.save(newEmployee);
    }
}
