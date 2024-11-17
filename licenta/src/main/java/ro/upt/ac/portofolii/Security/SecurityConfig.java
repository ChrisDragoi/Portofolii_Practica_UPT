package ro.upt.ac.portofolii.Security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ro.upt.ac.portofolii.student.StudentService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

        private final StudentService studentDetailsService;

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(studentDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                        .authorizeHttpRequests(registry -> {
                                registry.requestMatchers("/login").permitAll();
                                registry.anyRequest().authenticated();
                        })
                        .formLogin(httpForm -> {
                                httpForm.loginPage("/login")
                                        .defaultSuccessUrl("/index", true) // Destinația implicită după autentificare
                                        .permitAll();
                        })
                        .logout(logout -> {
                                logout.logoutUrl("/logout")
                                        .logoutSuccessUrl("/login")
                                        .permitAll();
                        })
                        .build();
        }
}
