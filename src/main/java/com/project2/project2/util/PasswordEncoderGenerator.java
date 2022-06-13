

package com.project2.project2.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordEncoderGenerator {
    
   public static void main(String[] args) {
		
       
       PasswordEncoder passEncoder = new BCryptPasswordEncoder();
       System.out.println(passEncoder.encode("lucas38209086"));
       
       
	}

}
