package par.financiera.financiera.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import par.financiera.financiera.Domain.CashFlow;
import par.financiera.financiera.Utils.TypeCash;

import java.util.List;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow,Long> {


    List<CashFlow> findByUserIdAndType(Long userId, TypeCash type);

}
