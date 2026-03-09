package ch.etml.es.payroll.Controllers;
import ch.etml.es.payroll.Entities.Department;
import ch.etml.es.payroll.Repositories.DepartmentRepository;
import ch.etml.es.payroll.Services.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class DepartmentController {

    private final DepartmentRepository repository;

    DepartmentController(DepartmentRepository repository){
        this.repository = repository;
    }

    /* curl sample :
    curl -X GET localhost:8080/api/v1/departments | jq
    */
    @GetMapping("/api/v1/departments")
    List<Department> all(){
        return repository.findAll();
    }

    /* curl sample :
    curl -X GET localhost:8080/api/v1/departments/1
    */
    @GetMapping("/api/v1/departments/{id}")
    Department one(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    /* curl sample :
        curl -i -X POST localhost:8080/api/v1/departments ^
            -H "Content-type:application/json" ^
            -d "{\"acronym\": \"MKT\", \"description\": \"Marketing\"}"
    */
    @PostMapping("/api/v1/departments")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department created = DepartmentService.create(department);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(created);
    }
}
