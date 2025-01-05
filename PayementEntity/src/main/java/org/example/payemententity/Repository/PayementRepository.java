package org.example.payemententity.Repository;

import jdk.dynalink.linker.LinkerServices;
import org.example.payemententity.Models.Payement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayementRepository extends JpaRepository<Payement,Integer> {

    @Query("select p from Payement p where p.userId =:userId")
    public List<Payement> getAllByUserId(@Param("userId")Integer userId);
}
