package ch.etml.es.payroll.Services;
import ch.etml.es.payroll.Controllers.DepartmentAlreadyExistsException;
import ch.etml.es.payroll.Entities.Department;
import ch.etml.es.payroll.Repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private static DepartmentRepository repository = null;

    public DepartmentService(DepartmentRepository repository) {
        DepartmentService.repository = repository;
    }

    public static Department create(Department department) {
        Department existing = repository.findByAcronym(department.getAcronym())
                .orElse(null);

        if (existing != null) {
            throw new DepartmentAlreadyExistsException(department.getAcronym());
        }
        return repository.save(department);
    }
}
