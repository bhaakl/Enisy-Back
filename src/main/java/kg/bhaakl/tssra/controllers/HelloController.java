package kg.bhaakl.tssra.controllers;

import kg.bhaakl.tssra.models.JwtAuthentication;
import kg.bhaakl.tssra.services.AuthService;
import kg.bhaakl.tssra.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RoleService roleService;
    @GetMapping("showUserInfo")
    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> showUserInfo() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        System.out.println("----------------------------");
        System.out.println(authInfo.getRoles());
        System.out.println("----------------------------");
        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }

    @GetMapping("admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
    }
}
