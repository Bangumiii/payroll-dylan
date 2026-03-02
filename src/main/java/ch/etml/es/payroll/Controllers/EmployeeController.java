package ch.etml.es.payroll.Controllers;

import ch.etml.es.payroll.Entities.Employee;
import ch.etml.es.payroll.Repositories.EmployeeRepository;
import ch.etml.es.payroll.Services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    /* curl sample :
    curl -X GET localhost:8080/api/v1/employees | jq
    */
    @GetMapping("/api/v1/employees")
    List<ch.etml.es.payroll.Entities.Employee> all(){
        return repository.findAll();
    }

    /* curl sample :
    curl -X GET localhost:8080/api/v1/employees/1
    */
    @GetMapping("/api/v1/employees/{id}")
    ch.etml.es.payroll.Entities.Employee one(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    /* curl sample :
    curl -i -X POST localhost:8080/api/v1/employees/post \
    -H "Content-Type: application/json" \
    -d '{"name":"Russell George", "role":"driver"}'
    */
    @PostMapping("/api/v1/employees")
    public ResponseEntity<Employee> hireEmployee(@RequestBody Employee newEmployee){
        Employee create = EmployeeService.hire(newEmployee);

        URI location =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(create.getId()).toUri();
        return ResponseEntity.created(location).body(create);
    }
}
