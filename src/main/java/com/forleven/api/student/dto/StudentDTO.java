package com.forleven.api.student.dto;

import com.forleven.api.student.entity.Phone;
import com.forleven.api.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {

    private Long id;

    @NotBlank(message = "{name.not.blank}")
    @Size(min = 3, max = 20, message = "{name.size}")
    private String name;

    @NotBlank(message = "{surname.not.blank}")
    @Size(min = 3, max = 20, message = "{surname.size}")
    private String surname;

    @NotBlank(message = "{enrollment.not.blank}")
    @Size(min = 3, max = 20, message = "{enrollment.size}")
    private String enrollment;

    private List<Phone> phones;

    public Student build() {
        return new Student()
                .setName(this.name)
                .setSurname(this.surname)
                .setEnrollment(this.enrollment)
                .setPhones(this.phones);
    }

}
