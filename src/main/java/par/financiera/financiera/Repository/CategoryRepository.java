package par.financiera.financiera.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import par.financiera.financiera.Domain.Categories;

import java.util.Optional;

@Repository
public interface CategoryRepository  extends JpaRepository<Categories,Long> {
    Optional<Categories> findById(Long id);
    Optional<Categories> findByTitle(String title);
}
