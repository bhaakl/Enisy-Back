package kg.bhaakl.enisy.repositories;

import kg.bhaakl.enisy.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    @Query(value = "select distinct r from Roles r join fetch r.user where r.userId=:id")
    @QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")})
    List<Roles> getRolesWithJoinFetch(Integer id);

}
