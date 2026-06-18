//defines the package this belong to
package main.java.com.example.weatherapi;

//imports spirng boots helper class that starts teh applciation 
import org.springframework.boot.SpringApplication; 

//import the annotation that this is spring boot
import org.springframework.boot.autoconfigure.SpringBootApplication; 

//tells spring boo this is the main applcaitoin class 
@SpringBootApplication
public class WeatherApiApplication {
    //this is entry point of the java program 
    public static void main(String[] args){
        SpringApplicatinon.run(WeatherApiApplication.class, args); 
    }
}
