package kg.bhaakl.enisy.services;

import io.jsonwebtoken.Claims;
import kg.bhaakl.enisy.dto.AuthenticationDTO;
import kg.bhaakl.enisy.exceptions.MyUnAuthorizedException;
import kg.bhaakl.enisy.exceptions.UserException;
import kg.bhaakl.enisy.models.JwtAuthentication;
import kg.bhaakl.enisy.models.JwtResponse;
import kg.bhaakl.enisy.security.JwtProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final USERDetailsService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull AuthenticationDTO authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new JwtResponse(false, "Неправильный пароль или логин!");
        }
        final UserDetails user = (UserDetails) authentication.getPrincipal();
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getUsername(), refreshToken);
        return new JwtResponse(true, accessToken, refreshToken);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDetails user = userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(true, accessToken, null);
            }
        }
//        throw new UserException("Invalid Access Token!");
        return new JwtResponse(false, "Invalid Access Token!");
//        throw new MyUnAuthorizedException("Invalid Access Token!");
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDetails user = userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(true, accessToken, newRefreshToken);
            }
        }
        return new JwtResponse(false, "Invalid Refresh Token!");
//        throw new MyUnAuthorizedException("Invalid Refresh Token!");
    }

    @GetMapping("logout")
    public Map<Object, Object> logout() {
        refreshStorage.clear();
        return Map.of("res", true);
    }

    public Boolean validateAccessToken(String token) {
        return jwtProvider.validateAccessToken(token);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
