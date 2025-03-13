package ro.upt.ac.portofolii.security;//package ro.upt.ac.portofolii.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//        private final UserDetailsService userDetailsService;
//
//        public SecurityConfig(UserDetailsService userDetailsService) {
//                this.userDetailsService = userDetailsService;
//        }
//
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//                http
//                        .csrf(AbstractHttpConfigurer::disable)
//                        .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/student/**").hasRole("STUDENT")
//                                .requestMatchers("/tutor/**").hasRole("TUTOR")
//                                .requestMatchers("/cadru-didactic/**").hasRole("CADRU_DIDACTIC")
//                                .anyRequest().authenticated()
//                        )
//                        .formLogin(form -> form
//                                .loginPage("/login")
//                                .usernameParameter("username")
//                                .passwordParameter("password")
//                                .defaultSuccessUrl("/", true)
//                                .permitAll()
//                        )
//                        .logout(logout -> logout
//                                .logoutUrl("/logout")
//                                .logoutSuccessUrl("/login?logout")
//                                .permitAll()
//                        )
//                        .httpBasic(withDefaults());
//
//                return http.build();
//        }
//
//        @Bean
//        public AuthenticationProvider authenticationProvider() {
//                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//                provider.setUserDetailsService(userDetailsService);
//                provider.setPasswordEncoder(passwordEncoder());
//                return provider;
//        }
//
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//                return new BCryptPasswordEncoder();
//        }
//
//        @Bean
//        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//                return config.getAuthenticationManager();
//        }
//
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

    @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}

