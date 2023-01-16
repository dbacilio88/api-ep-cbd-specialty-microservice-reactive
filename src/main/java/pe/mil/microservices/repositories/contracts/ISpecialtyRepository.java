package pe.mil.microservices.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.mil.microservices.repositories.entities.SpecialtyEntity;

@Repository
public interface ISpecialtyRepository extends JpaRepository<SpecialtyEntity, Long> {
    boolean existsBySpecialtyId(Long customerId);


}
