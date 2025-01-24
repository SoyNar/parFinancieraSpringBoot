package par.financiera.financiera.Domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suggetion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Suggestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goals_id")
    private Goals goal;
}
