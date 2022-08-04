package com.himera.Messengermongo.services;

import com.himera.Messengermongo.models.Role;
import com.himera.Messengermongo.models.User;
import com.himera.Messengermongo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, MailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user!=null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found");
    }

    public boolean searchEmailInDB(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null){
            StringBuilder code = new StringBuilder();
            SecureRandom random = new SecureRandom();
            for (int i = 0; i < 6; i++) {
                code.append(random.nextInt(10));
            }
            user.setActivationCode(String.valueOf(code));
            userRepository.save(user);

            String message = "Ласкаво просимо до Українського Месенджера. Ваша код активації " + code + ".";
            mailSender.send(user.getEmail(), "Код активації", message);
            return true;
        }
        return false;
    }

    public void addUser(String email) {
        User userFromDb = userRepository.findByEmail(email);
        if (userFromDb != null)return;

        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }

        User user = new User(
                null,
                null,
                true,
                email
                ,String.valueOf(code),
                Collections.singleton(Role.USER),
                null
        );

        userRepository.save(user);

        String message = "Ласкаво просимо до Українського Месенджера. Ваша код активації " + code + ".";
        mailSender.send(email, "Код активації", message);
    }

    public User activeUser(String code) {
        User userFromDb = userRepository.findByActivationCode(code);
        if (userFromDb != null){
            userFromDb.setActivationCode(null);
            userRepository.save(userFromDb);

            return userFromDb;
        }
        return null;
    }

    public boolean saveUser(String username, String password, User user) {
        if (user != null){
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).get();
    }

    public void saveAll(User...users) {
        userRepository.saveAll(Arrays.asList(users));
    }
}
