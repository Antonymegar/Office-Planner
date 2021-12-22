package com.example.officeplanner;

import com.example.officeplanner.Repositories.RoleRepository;
import com.example.officeplanner.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository repo;
    @Test
    public void testCreateRoles() {
        Role employee = new Role("EMPLOYEE");
        Role admin = new Role("ADMIN");
        Role officer = new Role("OFFICER");

        repo.saveAll(List.of(employee, admin, officer));

        List<Role> listRoles = repo.findAll();

        assertThat(listRoles.size()).isEqualTo(3);
    }

}
