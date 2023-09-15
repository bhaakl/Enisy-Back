package kg.bhaakl.enisy.models;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    USER("USER"),
    GUEST("GUEST");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}
