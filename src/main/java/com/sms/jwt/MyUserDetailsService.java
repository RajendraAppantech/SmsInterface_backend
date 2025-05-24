package com.sms.jwt;

import com.sms.entity.UserMaster;
import com.sms.repositories.Repositories;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
   @Autowired
   private Repositories.UserMasterRepository masterRepository;

   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      UserMaster master = this.masterRepository.findByUserId(username.toUpperCase());
      if (master == null) {
         throw new UsernameNotFoundException("User not found");
      } else {
         return new User(username.toUpperCase(), master.getPasswd(), new ArrayList());
      }
   }
}
