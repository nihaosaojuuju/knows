package cn.tedu.knows.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.tedu.knows.portal.mapper")
public class KnowsPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsPortalApplication.class, args);
    }
}
