package qu.nothingless;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConfigurationPropertiesScan
@EnableScheduling  // 开启定时任务
@EnableAsync  // 开启异步支持
@SpringBootApplication
@ComponentScan(basePackages  = {"qu.nothingless","mu.nothingless"})
@MapperScan(basePackages = {"qu.nothingless.mapper", "mu.nothingless.mapper"})
public class MuquApplication {
	public static void main(String[] args) {
		SpringApplication.run(MuquApplication.class, args);
	}


}
