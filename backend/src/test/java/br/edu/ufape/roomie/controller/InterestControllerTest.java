package br.edu.ufape.roomie.controller;

import br.edu.ufape.roomie.model.User;
import br.edu.ufape.roomie.service.InterestService;
import br.edu.ufape.roomie.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = InterestController.class, excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
class InterestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private InterestService interestService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("Deveria devolver código HTTP 200 quando o interesse for registrado com sucesso")
    void testaRegistrarInteresseComSucesso() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("estudante@ufape.edu.br");

        var response = mvc.perform(post("/announcements/1/interest")
                        .with(user(mockUser)))
                .andReturn().getResponse();

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Interesse registrado com sucesso. O administrador foi notificado.");
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 409 quando o usuário já demonstrou interesse anteriormente")
    void testaRegistrarInteresseDuplicado() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("estudante@ufape.edu.br");

        doThrow(new IllegalStateException("Você já demonstrou interesse neste imóvel."))
                .when(interestService).registerInterest(eq(1L), any());

        var response = mvc.perform(post("/announcements/1/interest")
                        .with(user(mockUser)))
                .andReturn().getResponse();

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.getContentAsString()).isEqualTo("Você já demonstrou interesse neste imóvel.");
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 404 quando o imóvel não for encontrado")
    void testaRegistrarInteresseImovelNaoEncontrado() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("estudante@ufape.edu.br");

        doThrow(new RuntimeException("Imóvel não encontrado."))
                .when(interestService).registerInterest(eq(999L), any());

        var response = mvc.perform(post("/announcements/999/interest")
                        .with(user(mockUser)))
                .andReturn().getResponse();

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("Imóvel não encontrado.");
    }
}