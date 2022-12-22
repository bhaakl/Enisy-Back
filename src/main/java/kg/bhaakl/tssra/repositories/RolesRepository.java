package kg.bhaakl.tssra.repositories;

import kg.bhaakl.tssra.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    @Query(value = "select distinct r from Roles r join fetch r.user where r.userId=:id")
    List<Roles> getRolesWithJoinFetch(Integer id);

}
