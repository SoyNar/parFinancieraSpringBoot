package par.financiera.financiera.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import par.financiera.financiera.Domain.Goals;
import par.financiera.financiera.Domain.User;

import java.util.Optional;
@Repository
public interface IGoalsRepository extends JpaRepository<Goals,Long> {

   Optional<Goals> findById(Long id);
   Optional<Goals> findByIdAndUser_Id(Long idUser, Long idGoal);
}
