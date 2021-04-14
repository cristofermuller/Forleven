package com.forleven.api.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forleven.api.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {

    @NotBlank(message = "{name.not.blank}")
    @Size(min = 3, max = 20, message = "{name.size}")
    private String name;

    @NotBlank(message = "{surname.not.blank}")
    @Size(min = 3, max = 20, message = "{surname.size}")
    private String surname;

    @NotBlank(message = "{enrollment.not.blank}")
    @Size(min = 3, max = 20, message = "{enrollment.size}")
    private String enrollment;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private ArrayList<String> phone = new ArrayList<>();

    public Student build() {
        return new Student()
                .setName(this.name)
                .setSurname(this.surname)
                .setEnrollment(this.enrollment)
                .setPhone(this.phone);
    }

}
