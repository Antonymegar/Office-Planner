package com.example.officeplanner;


import com.example.officeplanner.Repositories.OrganizationRepo;
import com.example.officeplanner.Repositories.RoleRepository;
import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CreateOrganizationTest {
    @Autowired
    private TestEntityManager entitymanager;
    @Autowired
    private OrganizationRepo orgRepo;
@Test
public void createOrganization(){
     Organization organization  = new Organization();
     organization.setOrg_name("TRACOM SERVICES LIMITED");
     Organization savedOrganization = orgRepo.save(organization);
     Organization existOrganization = entitymanager.find(Organization.class, savedOrganization.getId());
     assertThat(organization.getOrg_name()).isEqualTo(existOrganization.getOrg_name());
      }
}

