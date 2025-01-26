package par.financiera.financiera.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import par.financiera.financiera.Domain.CashFlow;
import par.financiera.financiera.Utils.TypeCash;

import java.util.List;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow,Long> {


    List<CashFlow> findByUserIdAndType(Long userId, TypeCash type);
    @Query("SELECT c FROM CashFlow c WHERE c.user.id = :userId AND c.type = :type AND FUNCTION('MONTH', c.date) = :month")
    List<CashFlow> findByUserIdAndTypeAndMonth(@Param("userId") Long userId, @Param("type") TypeCash type, @Param("month") String month);


}
