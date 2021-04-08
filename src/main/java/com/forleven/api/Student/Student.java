package com.forleven.api.Student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    ArrayList<String> phone = new ArrayList<String>();
}
