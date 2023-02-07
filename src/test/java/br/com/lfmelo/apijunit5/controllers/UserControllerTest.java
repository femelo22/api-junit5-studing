package br.com.lfmelo.apijunit5.controllers;

import br.com.lfmelo.apijunit5.controllers.exception.NotFoundException;
import br.com.lfmelo.apijunit5.entities.User;
import br.com.lfmelo.apijunit5.entities.dtos.UserDTO;
import br.com.lfmelo.apijunit5.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static br.com.lfmelo.apijunit5.factories.UserFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    static String USER_API = "/api/users";

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService service;


    @Test
    @DisplayName("Deve cadastrar um usuario")
    public void createUserTest() throws Exception {
        //Cenario
        UserDTO dto = buildUserDTO();
        User user = buildSavedUser();
        BDDMockito.given( service.registerUser(Mockito.any(User.class))).willReturn(user);
        String json = new ObjectMapper().writeValueAsString(dto);

        //Execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //Validacao
        mvc
                .perform(request)
                .andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect( MockMvcResultMatchers.jsonPath("name").value(dto.getName()))
                .andExpect( MockMvcResultMatchers.jsonPath("email").value(dto.getEmail()))
                .andExpect( MockMvcResultMatchers.jsonPath("password").value(dto.getPassword()))
                .andExpect( MockMvcResultMatchers.jsonPath("created").isNotEmpty());
    }


    @Test
    @DisplayName("Deve lancar erro de validacao quando nao houver dados suficiente para criacao de usuario.")
    public void createInvalidUserTest() throws Exception {
        //Cenario
        String json = new ObjectMapper().writeValueAsString(new UserDTO());

        //Execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //Validacao
        mvc
                .perform(request)
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("errors", hasSize(3)));
    }


    @Test
    @DisplayName("Deve obter informacoes de um usuario por ID")
    public void returnUserByIdTest() throws Exception {
        //Cenario
        Integer id = 1;
        User user = buildSavedUser();
        BDDMockito.given(service.findUserById(id)).willReturn(user);

        //Execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(USER_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        //Validacao
        mvc
                .perform(request)
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("id").value(id))
                .andExpect( MockMvcResultMatchers.jsonPath("name").value(user.getName()))
                .andExpect( MockMvcResultMatchers.jsonPath("email").value(user.getEmail()))
                .andExpect( MockMvcResultMatchers.jsonPath("password").value(user.getPassword()))
                .andExpect( MockMvcResultMatchers.jsonPath("created").isNotEmpty());
    }

    @Test
    @DisplayName("Deve lancar excecao quando nao encontrar um usuario por ID")
    public void userNotFoundByIdTest() throws Exception {
        //Cenario
        String msgError = "User not found.";
        User user = buildSavedUser();
        BDDMockito.given(service.findUserById(1)).willThrow(new NotFoundException(msgError));

        //Execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(USER_API.concat("/" + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //Validacao
        mvc
                .perform(request)
                .andExpect( status().isNotFound() );
    }


    @Test
    @DisplayName("Deve atualizar um usuario")
    public void updateUserTest() throws Exception {
        //Cenario
        Integer id = 1;
        User updateUser = buildUpdatedUser();
        UserDTO dto = buildUserDTO();
        String json = new ObjectMapper().writeValueAsString(dto);
        BDDMockito.given( service.findUserById(id) ).willReturn(updateUser);
        BDDMockito.given( service.updateUser(id, dto) ).willReturn(updateUser);

        //Execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(USER_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Validacao
        mvc
                .perform(request)
                .andExpect( status().isNoContent() );
    }


    @Test
    @DisplayName("Deve deletar um usuario por ID")
    public void deleteUserTest() throws Exception {
        //Cenario
        User user = buildSavedUser();
        BDDMockito.given( service.findUserById(Mockito.anyInt())).willReturn(user);

        //Execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(USER_API.concat("/" + 1));

        //Validacao
        mvc
                .perform(request)
                .andExpect(status().isNoContent());
    }
}
