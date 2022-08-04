package iam.sde.udemyblog.controller;

import iam.sde.udemyblog.entity.Role;
import iam.sde.udemyblog.entity.User;
import iam.sde.udemyblog.payload.JwtAuthResponse;
import iam.sde.udemyblog.payload.LoginDto;
import iam.sde.udemyblog.payload.SignupDto;
import iam.sde.udemyblog.repository.RoleRepository;
import iam.sde.udemyblog.repository.UserRepository;
import iam.sde.udemyblog.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    // IntelliJ gives warning for field injection (but why?)
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    // TODO: Login with email seems not to be working [INVESTIGATE]
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody LoginDto loginDto) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        // Get token from token provider class
        String token = jwtTokenProvider.generateToken(auth);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupDto signupDto) {

        if (userRepository.existsUserByUsername(signupDto.getUsername()) ||
                userRepository.existsUserByEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }

        Role role = roleRepository.findRoleByName("admin").get();
        User user = User.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .email(signupDto.getEmail())
                .roles(Collections.singleton(role))
                .build();

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully! ", HttpStatus.OK);
    }
}
