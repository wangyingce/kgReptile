package cc.leevi.webbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class WebBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebBaseApplication.class, args);
    }

}
