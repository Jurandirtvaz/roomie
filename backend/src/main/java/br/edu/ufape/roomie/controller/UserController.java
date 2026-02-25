package br.edu.ufape.roomie.controller;

import br.edu.ufape.roomie.dto.UpdateUserDTO;
import br.edu.ufape.roomie.dto.UserResponseDTO;
import br.edu.ufape.roomie.model.User;
import br.edu.ufape.roomie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping("/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UpdateUserDTO dto) {
        User userUpdated = userService.updateProfile(currentUser.getId(), dto);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(userUpdated.getId());
        response.setName(userUpdated.getName());
        response.setEmail(userUpdated.getEmail());
        response.setGender(userUpdated.getGender());
        if (userUpdated.getTelefones() != null) {
            response.setPhones(userUpdated.getTelefones().stream()
                    .map(telefone -> telefone.getNumero())
                    .collect(Collectors.toList()));
        }
        response.setRole(userUpdated.getRole());

        return ResponseEntity.ok(response);
    }
}
