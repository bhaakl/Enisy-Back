package kg.bhaakl.enisy.controllers;

import kg.bhaakl.enisy.models.JwtAuthentication;
import kg.bhaakl.enisy.services.AuthService;
import kg.bhaakl.enisy.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
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
        System.out.println("IN roleService:");
        System.out.println(roleService.getRolesOfUserById(9));
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
