package com.example.RitualEase.Sequrity;
import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Entity.User;
import com.example.RitualEase.Repository.PanditRepository;
import com.example.RitualEase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PanditRepository panditRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(), user.getPassword(), List.of(authority));
        }

        Pandit pandit = panditRepository.findByUserName(username);
        if (pandit != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_PANDIT");
            return new org.springframework.security.core.userdetails.User(
                    pandit.getUserName(), pandit.getPassword(), List.of(authority));
        }

//        return new org.springframework.security.core.userdetails.User(
//                user.getUserName(),
//                user.getPassword(),jgjvjcjjgvjc
//                Collections.singletonList(authority)
            throw new UsernameNotFoundException("User or Pandit Not Found");
    }
}
