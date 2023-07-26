package ru.iliya132.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.iliya132.config.EncoderConfig;
import ru.iliya132.model.User;
import ru.iliya132.repository.IUserRepository;

@Service
@Import(EncoderConfig.class)
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepository;
    @Autowired
    private EncoderConfig encoderConfig;

    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow(() ->
                new UsernameNotFoundException("Nothing found for " + username));
    }

    public User saveUser(User user) {
        user.setPassword(encoderConfig.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
