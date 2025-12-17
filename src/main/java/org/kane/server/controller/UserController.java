package org.kane.server.controller;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.UserEditDTO;
import org.kane.server.DTO.UserShowNameDTO;
import org.kane.server.mappers.UserMapper;
import org.kane.server.mappers.UserShowNameMapper;
import org.kane.server.services.UserService;
import org.kane.server.validations.annotations.ResponseErrorValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    private final UserShowNameMapper userShowNameMapper;
    private final UserMapper userMapper;
    private final ResponseErrorValidation responseErrorValidation;


    @GetMapping()
    public ResponseEntity<UserShowNameDTO> getCurrentUser(Principal principal) {
        var userDTO = Optional.of(userService.getCurrentUser(principal))
                .map(userShowNameMapper::map)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserEditDTO userDTO,
                                             BindingResult bindingResult,
                                             Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) return errors;
        var userUpd = userService.updateUser(userDTO, principal)
                .map(userShowNameMapper::map)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(userUpd);
    }
}
