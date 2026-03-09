package ch.etml.es.payroll.controllers;


import ch.etml.es.payroll.PayrollApplication;
import ch.etml.es.payroll.entities.Employee;
import ch.etml.es.payroll.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
        classes = PayrollApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class EmployeeDeleteTest {
    private static final String BASE_URL = "/v1/employees";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee existingEmployee;

    @BeforeEach
    void given_an_existing_employee() {
        // GIVEN
        employeeRepository.deleteAll();

        Employee employee = new Employee("Doe", "Supervisor");
        existingEmployee = employeeRepository.save(employee);
    }

    @Test
    void when_deleting_existing_employee_then_success() {
        // WHEN
        ResponseEntity<Void> response =
                restTemplate.exchange(
                        BASE_URL + "/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        existingEmployee.getId()
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(employeeRepository.findById(existingEmployee.getId())).isEmpty();
    }

    @Test
    void when_deleting_nonexisting_employee_then_success() {
        // GIVEN
        Long nonExistentEmployeeId = 999L;

        // WHEN
        ResponseEntity<String> response =
                restTemplate.exchange(
                        BASE_URL + "/{id}",
                        HttpMethod.DELETE,
                        null,
                        String.class,
                        nonExistentEmployeeId
                );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Employee with id " + nonExistentEmployeeId + " not found");
    }
}
