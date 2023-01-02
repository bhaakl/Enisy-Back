package kg.bhaakl.enisy.services;

import kg.bhaakl.enisy.models.Roles;
import kg.bhaakl.enisy.repositories.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleService {
    private final RolesRepository rolesRepository;

    @Transactional
    public void save(Roles roles) {
        rolesRepository.save(roles);
    }

    public Roles getRefById(int id) throws EntityNotFoundException{
        return rolesRepository.getReferenceById(id);
    }

    public List<Roles> getRolesOfUserById(Integer id) {
        return rolesRepository.getRolesWithJoinFetch(id);
    }
}
