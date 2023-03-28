//package ru.kata.spring.boot_security.demo.configs;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//
//
//@Component
//public class AuthProviderImp implements AuthenticationProvider {
//
//    private UserService userService;
//
//    @Autowired
//    public AuthProviderImp(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//
//        UserDetails user = userService.loadUserByUsername(username);
//
//        String password = authentication.getPrincipal().toString();
//
//        if (!password.equals(user.getPassword())) {
//            throw new BadCredentialsException("Пароль неверен.");
//        }
//        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
