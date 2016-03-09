package tw.edu.ncu.cc.signin.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import tw.edu.ncu.cc.signin.server.config.MvcConfig
import tw.edu.ncu.cc.signin.server.config.SecurityConfig
import tw.edu.ncu.cc.signin.server.config.BeanConfig
import tw.edu.ncu.cc.springboot.config.hikaricp.EnableHikariConfiguration

@SpringBootApplication
@EnableHikariConfiguration
@EnableJpaRepositories
@EnableSpringDataWebSupport
@Import( [ BeanConfig, MvcConfig, SecurityConfig ] )
public class Application extends SpringBootServletInitializer {

    public static void main( String[] args ) {
        SpringApplication.run( Application.class, args )
    }

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder builder ) {
        return builder.sources( Application.class )
    }

}
