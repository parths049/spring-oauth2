package com.poc.authenticate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poc.enums.Role;
import com.poc.model.User;
import com.poc.repository.UserRepository;

@Service("authenticateService")
public class AuthenticationService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = (User) userRepository.findByEmailIgnoreCase(username);
    List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
    return buildUserForAuthentication(user, authorities);
  }

  private List<GrantedAuthority> buildUserAuthority(Role role) {
    Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
    setAuths.add(new SimpleGrantedAuthority(role.name()));
    List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(setAuths);
    return grantedAuthorities;
  }

  private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        user.isAccountNonLocked(),user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isEnabled(), authorities);
  }
}
