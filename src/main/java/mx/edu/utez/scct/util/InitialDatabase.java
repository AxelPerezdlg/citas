package mx.edu.utez.scct.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialDatabase implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String myPassword = "123";
        System.out.println(myPassword + " Encode: " + passwordEncoder.encode(myPassword));
    }

}