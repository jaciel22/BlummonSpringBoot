package com.blummon.users.controller;

import com.blummon.users.dto.AccesDto;
import com.blummon.users.dto.UserDto;
import com.blummon.users.entity.UserEntity;
import com.blummon.users.repository.UserRepository;
import com.blummon.users.service.UserService;
import com.blummon.users.vo.VoResponse;
import com.blummon.users.vo.VoResultado;
import com.blummon.users.vo.VoUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.blummon.users.common.GlobalConstant.API_USER;
import static java.util.Objects.isNull;

@RestController
@RequestMapping(API_USER)
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<?> getUsers(){
        try {
            List<UserEntity> optUser = userService.findAll();
            if (optUser.isEmpty()) {
                VoResultado voResultado = new VoResultado();
                VoUserResponse voUserResponse = new VoUserResponse();

                List<UserEntity> usuariosVacios = new ArrayList<>();

                voResultado.setUsuarios(usuariosVacios);
                voUserResponse.setResultado(voResultado);
                voUserResponse.setMensaje("No se encontró información.");
                voUserResponse.generarFolioAutomatico();
                return new ResponseEntity<>(voUserResponse, HttpStatus.NOT_FOUND);
            }

            VoResultado voResultado = new VoResultado();
            VoUserResponse voUserResponse= new VoUserResponse();
            voResultado.setUsuarios(optUser);
            voUserResponse.setResultado(voResultado);
            voUserResponse.setMensaje("Operacion exitosa");
            voUserResponse.generarFolioAutomatico();
            return new ResponseEntity<>(voUserResponse,HttpStatus.OK);
        }catch (Exception e) {
            return null;
        }
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<?> getUserByAccess(@RequestBody AccesDto userAccess){
        try {

            UserEntity optUser = userService.findUser(userAccess.getUsername(),userAccess.getPassword());
            if (optUser == null) {
                return ResponseEntity.noContent().build();
            }
            VoResultado voResultado = new VoResultado();
            VoUserResponse voUserResponse= new VoUserResponse();
            voResultado.setUsuarios(Collections.singletonList(optUser));
            voUserResponse.setResultado(voResultado);
            voUserResponse.setResultado(voResultado);
            voUserResponse.setMensaje("Operacion exitosa");
            voUserResponse.generarFolioAutomatico();
            return new ResponseEntity<>(voUserResponse,HttpStatus.OK);
        }catch (Exception e) {
            return null;
        }
    }


    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<VoResponse> saveUser(@RequestBody @Validated UserEntity userEntity, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            VoResponse response = new VoResponse();
            response.setMensaje("Error de validación: " + errorMessage);
            return ResponseEntity.badRequest().body(response);
        }

        UserEntity savedUser = userService.createUser(userEntity.getName(), userEntity.getUsername(), userEntity.getLastName(), userEntity.getPassword());

        if (isNull(savedUser)) {
            return ResponseEntity.badRequest().build();
        }

        VoResponse response = new VoResponse();
        response.generarFolioAutomatico();
        response.setMensaje("Operación exitosa");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable (value="id",required=true) Long id,@RequestBody UserEntity userEntity) {

        try {
            userEntity.setIdUser(id);
            UserEntity oClienteEntity = userService.update(userEntity);
            if (isNull(oClienteEntity)) {
                return ResponseEntity.badRequest().build();
            }
            VoResponse response = new VoResponse();
            response.generarFolioAutomatico();
            response.setMensaje("Operacion exitosa");
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable (value="id",required=true) Long id) {

        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setIdUser(id);
            if (userService.delete(userEntity)) {
                VoResponse response = new VoResponse();
                response.generarFolioAutomatico();
                response.setMensaje("Operacion exitosa");
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return null;
        }
    }
}
