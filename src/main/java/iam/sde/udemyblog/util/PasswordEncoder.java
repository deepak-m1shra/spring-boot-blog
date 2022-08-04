package iam.sde.udemyblog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    @Value("${test.property}")
    private static String testProperty;
    public static void main(String[] args) {

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("admin"));
//        System.out.println(passwordEncoder.encode("password"));

        System.out.println(testProperty);



    }
}
