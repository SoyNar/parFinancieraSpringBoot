package par.financiera.financiera.Domain;


import jakarta.persistence.*;
import lombok.*;
import par.financiera.financiera.Utils.GoalStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    private double currentAmount;

    private double goalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "goal",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Suggestions> suggestion;


}
