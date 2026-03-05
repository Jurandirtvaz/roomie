package br.edu.ufape.roomie.service;

import br.edu.ufape.roomie.model.Property;
import br.edu.ufape.roomie.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NotificationServiceTest {

    private final NotificationService notificationService = new NotificationService();

    @Test
    @DisplayName("Deve notificar proprietário sem lançar exceções")
    void shouldNotifyOwnerWithoutThrowing() {
        User owner = new User();
        owner.setEmail("proprietario@ufape.edu.br");

        User student = new User();
        student.setName("João");

        Property property = new Property();
        property.setTitle("Quarto próximo à UFAPE");

        assertDoesNotThrow(() ->
                notificationService.notifyOwnerAboutInterest(owner, student, property)
        );
    }
}
