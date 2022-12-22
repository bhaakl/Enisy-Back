package kg.bhaakl.tssra.controllers;

import kg.bhaakl.tssra.dto.AuthenticationDTO;
import kg.bhaakl.tssra.dto.UserDTO;
import kg.bhaakl.tssra.models.JwtResponse;
import kg.bhaakl.tssra.models.RefreshJwtRequest;
import kg.bhaakl.tssra.models.User;
import kg.bhaakl.tssra.services.AuthService;
import kg.bhaakl.tssra.services.RegistrationService;
import kg.bhaakl.tssra.util.ErrorSenderToClient;
import kg.bhaakl.tssra.util.UserValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @NonNull
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final RegistrationService registrationService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> performLogin(@RequestBody AuthenticationDTO authRequest) {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("registration")
    public ResponseEntity<Map<String, String>> performRegistration(@RequestBody @Valid UserDTO userDTO,
                                                                   BindingResult bindingResult) {
        User user = convertToUser(userDTO);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            ErrorSenderToClient.returnErrorsToClient(bindingResult);
        }
        registrationService.register(user);
        return ResponseEntity.ok(Map.of("message", "Are you registered with Tssra"));
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    public User convertToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
