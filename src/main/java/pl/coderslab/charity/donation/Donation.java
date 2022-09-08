package pl.coderslab.charity.donation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.user.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table( name = "donations")
@ToString
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(1)
    @NotNull
    private Integer quantity;
    @ManyToMany
    @NotEmpty
    private List<Category> categories = new ArrayList<>();
    @ManyToOne
    @NotNull
    private Institution institution;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate pickUpDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime pickUpTime;
    @NotBlank(message = "cant be blank")
    private String street;
    @NotBlank(message = "cant be blank")
    private String city;
    @NotBlank(message = "cant be blank")
    private String zipCode;
    private String pickUpComment;
    @NotBlank(message = "cant be blank")
    private String phoneNumber;
    @Column(columnDefinition = "varchar(255) default 'nie odebrano'")
    private String status = "nie odebrano";
    @ManyToOne
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpSuccessDate;

}
