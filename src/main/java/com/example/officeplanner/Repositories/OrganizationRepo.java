package com.example.officeplanner.Repositories;

import com.example.officeplanner.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrganizationRepo extends JpaRepository<Organization, Integer> {
    @Query("SELECT o FROM Organization o WHERE o.id=?1")
    public Organization findOrganizationById(Integer id);
}

