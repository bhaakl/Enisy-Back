package kg.bhaakl.tssra.services;

import io.jsonwebtoken.Claims;
import kg.bhaakl.tssra.dto.AuthenticationDTO;
import kg.bhaakl.tssra.models.JwtResponse;
import kg.bhaakl.tssra.security.JwtProvider;
import kg.bhaakl.tssra.security.PersonDetails;
import kg.bhaakl.tssra.util.UserException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PersonDetailsService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull AuthenticationDTO authRequest) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                        authRequest.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect credentials!");
        }
        final PersonDetails user = (PersonDetails) authInputToken.getPrincipal();
        final String accessToken = jwtProvider.generateAccessToken(user.getPerson());
        final String refreshToken = jwtProvider.generateRefreshToken(user.getPerson());
        refreshStorage.put(user.getUsername(), refreshToken);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final PersonDetails user = (PersonDetails) userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user.getPerson());
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final PersonDetails user = (PersonDetails) userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user.getPerson());
                final String newRefreshToken = jwtProvider.generateRefreshToken(user.getPerson());
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new UserException("Invalid JWT Token!");
    }

    public Authentication getAuthInfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
