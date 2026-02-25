package br.edu.ufape.roomie.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String name;
    private String email;
    private String currentPassword;
    private String newPassword;
}
