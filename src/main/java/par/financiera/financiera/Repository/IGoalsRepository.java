package par.financiera.financiera.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import par.financiera.financiera.Domain.Goals;

public interface IGoalsRepository extends JpaRepository<Goals,Long> {
}
