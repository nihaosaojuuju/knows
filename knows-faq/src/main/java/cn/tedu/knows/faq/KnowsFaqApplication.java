package cn.tedu.knows.faq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//这个注解意思是启动这个项目注册到nacos注册中心,这样这个项目就是一个微服务项目
@EnableDiscoveryClient
@MapperScan("cn.tedu.knows.faq.mapper")
public class KnowsFaqApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsFaqApplication.class, args);
    }
    // @Bean表示会将下面方法的返回值保存到Spring容器中
    @Bean
    // LoadBalanced是负载均衡的意思
    // 微服务间调用不经过网关,所以网关中设置的负载均衡无效
    // 所以要设置Ribbon的负载均衡
    @LoadBalanced
    // RestTemplate就是能够实现微服务间调用的对象
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
