package par.financiera.financiera.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration  implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry options) {
        options.addMapping("/api/**") // solo permite cors en las rutas que inician con api
                .allowedOrigins("http://localhost:8081") // el origen osea la url
                .allowedMethods("GET,POST,PUT,DELETE")  // especificamos los metodos a permitir
                .allowedHeaders("*")  //permite todos los encabezados
                .allowCredentials(true); // permitir cookies y autenticacion
    }
}
