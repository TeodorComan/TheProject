package org.project.system.product.repository;

import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.user.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface PredefinedStructureRepository extends JpaRepository<PredefinedStructure,Long> {


    @Query("SELECT ps FROM PredefinedStructure ps WHERE ps.name = 1 AND ps.createdBy.owner = 2")
    Optional<PredefinedStructure> findByNameAndAccount(String name, Account account);
}
