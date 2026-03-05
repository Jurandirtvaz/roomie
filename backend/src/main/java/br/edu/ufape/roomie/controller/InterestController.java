package br.edu.ufape.roomie.controller;

import br.edu.ufape.roomie.model.User;
import br.edu.ufape.roomie.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;

    @PostMapping("/{id}/interest")
    public ResponseEntity<String> expressInterest(
            @PathVariable("id") Long propertyId,
            @AuthenticationPrincipal User loggedInUser) {

        try {
            interestService.registerInterest(propertyId, loggedInUser);
            return ResponseEntity.ok("Interesse registrado com sucesso. O administrador foi notificado.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}