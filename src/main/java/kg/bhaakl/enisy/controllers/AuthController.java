package kg.bhaakl.enisy.controllers;

import kg.bhaakl.enisy.dto.AuthenticationDTO;
import kg.bhaakl.enisy.dto.UserDTO;
import kg.bhaakl.enisy.exceptions.MyUnAuthorizedException;
import kg.bhaakl.enisy.models.JwtResponse;
import kg.bhaakl.enisy.models.RefreshJwtRequest;
import kg.bhaakl.enisy.models.User;
import kg.bhaakl.enisy.services.AuthService;
import kg.bhaakl.enisy.services.RegistrationService;
import kg.bhaakl.enisy.util.ErrorSenderToClient;
import kg.bhaakl.enisy.util.UserValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<Map<String, Object>> performRegistration(@RequestBody @Valid UserDTO userDTO,
                                                                   BindingResult bindingResult) {
        User user = convertToUser(userDTO);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errors = ErrorSenderToClient.returnErrorsToClient(bindingResult);
            return ResponseEntity.ok(Map.of("res", false, "errors", errors));
        }
        registrationService.register(user);
        return ResponseEntity.ok(Map.of("res", true, "message",
                "'" + user.getUsername() + "'" + ", успешно зарегистрирован"));
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

    @GetMapping("logout")
    public ResponseEntity<Map<Object, Object>> logout() {
        return ResponseEntity.ok(authService.logout())  ;
    }

    @GetMapping("check")
    public ResponseEntity<Map<Object, Object>> check(HttpEntity<String> entity) {
        System.out.println("Check.");
        final HttpHeaders request = entity.getHeaders();
        final String AUTHORIZATION = "Authorization";
        final String bearer = request.getFirst(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            final String token = bearer.substring(7);
            if (!authService.validateAccessToken(token)) {
                throw new MyUnAuthorizedException("Access-token invalid!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(Map.of("res", true));
    }

    public User convertToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
