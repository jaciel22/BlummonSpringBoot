package com.blummon.users.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;




@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática autoincremental
    @Column(name = "id_user")
    private Long idUser;

    private String name;

    @Column(name = "last_name")
    @NotBlank(message = "El apellido es requerido")
    private String lastName;

    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;

    @NotBlank(message = "El password es requerido")
    private String password;

    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    @PrePersist
    public void prePersist() {
        creationDate = OffsetDateTime.now();
    }



}
