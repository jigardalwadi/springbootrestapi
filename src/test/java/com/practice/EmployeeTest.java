package com.practice;

import com.jayway.jsonpath.DocumentContext;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.practice.EmployeeApplication;
import com.practice.entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.TimeZone;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeApplication.class, webEnvironment = RANDOM_PORT)
public class EmployeeTest {

    @Autowired
    private TestRestTemplate restTemplate;
    Employee employee = new Employee(1l, "name", "ceo", "nothing", new Date());

    @Before
    public void setUp() throws Exception {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("spring.datasource.url");

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testCreate(){
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/company/employees",employee,String.class);
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        DocumentContext createJson = parse(responseEntity.getBody());
        assertThat(createJson.read("$.id",Long.class)).isEqualTo(employee.getId());
        assertThat(createJson.read("$.name",String.class)).isEqualTo(employee.getName());
        assertThat(createJson.read("$.designation",String.class)).isEqualTo(employee.getDesignation());
        assertThat(createJson.read("$.expertise",String.class)).isEqualTo(employee.getExpertise());
    }


    @Test
    public void testGet(){
        Long id = createTimeEntry();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/company/employees/" +id,String.class);
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        DocumentContext createJson = parse(responseEntity.getBody());
        assertThat(createJson.read("$.id",Long.class)).isEqualTo(employee.getId());
        assertThat(createJson.read("$.name",String.class)).isEqualTo(employee.getName());
        assertThat(createJson.read("$.designation",String.class)).isEqualTo(employee.getDesignation());
        assertThat(createJson.read("$.expertise",String.class)).isEqualTo(employee.getExpertise());
    }
    private Long createTimeEntry() {
        HttpEntity<Employee> entity = new HttpEntity(employee);

        ResponseEntity<Employee> response = restTemplate.exchange("/company/employees", HttpMethod.POST, entity, Employee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        return response.getBody().getId();
    }
}
