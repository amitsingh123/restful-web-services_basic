package com.amit.rest.restfulwebservices.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "description about user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min = 2, message = "name should be at least 2 characters")
    @ApiModelProperty(notes ="name should be at least 2 characters" )
    private String name;
    @Past
    @ApiModelProperty(notes ="date of birth should be in past" )
    private Date birthDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
