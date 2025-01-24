package par.financiera.financiera.Domain;

import jakarta.persistence.*;
import lombok.*;
import par.financiera.financiera.Utils.GoalStatus;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "predictions")
public class Predictions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDate startDate;

    private LocalDate endDate;

    private String resultData;

    @Enumerated(EnumType.STRING)
    private GoalStatus status;


}
