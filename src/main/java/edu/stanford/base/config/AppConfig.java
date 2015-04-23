/*package edu.stanford.base.config;


import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ImportResource( { "classpath*:/applicationContext.xml" } )
@ComponentScan( basePackages = "edu.stanford" )
public class AppConfig{
   
   @Bean
   public static PropertyPlaceholderConfigurer properties(){
      PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
      final Resource[] resources = new ClassPathResource[ ] { 
         new ClassPathResource( "jdbc.properties" ) 
         //new ClassPathResource( "restfull.properties" ) 
      };
      ppc.setLocations( resources );
      ppc.setIgnoreUnresolvablePlaceholders( true );
      return ppc;
   }
   
}
*/