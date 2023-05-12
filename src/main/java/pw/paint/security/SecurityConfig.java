//package pw.paint.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import pw.paint.service.UserServiceImpl;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    private final UserServiceImpl userService;
//
//    public SecurityConfig(UserServiceImpl userService) {
//        this.userService = userService;
//    }
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeHttpRequests((authz) -> authz
////                        .anyRequest().authenticated()
////                )
////                .httpBasic(withDefaults());
////        return http.build();
////    }
//
////    @Bean
////    public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
////        EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean =
////                EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
////        contextSourceFactoryBean.setPort(0);
////        return contextSourceFactoryBean;
////    }
////
////    @Bean
////    AuthenticationManager ldapAuthenticationManager(
////            BaseLdapPathContextSource contextSource) {
////        LdapBindAuthenticationManagerFactory factory =
////                new LdapBindAuthenticationManagerFactory(contextSource);
////        factory.setUserDnPatterns("uid={0},ou=people");
////        factory.setUserDetailsContextMapper(new PersonContextMapper());
////        return factory.createAuthenticationManager();
////    }
//
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(userService);
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        return authentication -> {
//            User user = (User) userService.loadUserByUsername(authentication.getName());
//
//            if (user == null || !passwordEncoder().matches(authentication.getCredentials().toString(), user.getPassword())) {
//                throw new BadCredentialsException("Invalid username or password");
//            }
//            return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
//        };
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//
//
