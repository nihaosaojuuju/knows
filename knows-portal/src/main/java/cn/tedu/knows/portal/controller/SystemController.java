package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
// lombok提供的注解@Slf4j,它能够在当前类的属性中添加一个记录日志的属性log
// 我们在控制器方法中可以使用它来输出信息到日志中
@Slf4j
public class SystemController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public String register(
            //RegisterVO参数前添加@Validated注解,表示启动SpringValidation验证
            // 在控制器运行之前框架就会按照设置好的规则进行验证工作
            @Validated RegisterVO registerVO,
            // 这个参数是接收上面验证结果的对象
            // 我们在代码中可以判断这个result中是否包含错误信息,以得知验证结果
            BindingResult result) {
        // 使用@Slf4j提供的log对象记录日志
        log.debug("接收到表单信息:{}", registerVO);
        if (result.hasErrors()) {
            // 进入这个if证明验证没通过,要返回错误信息
            String msg = result.getFieldError().getDefaultMessage();
            return msg;
        }
        userService.registerStudent(registerVO);
        return "ok";
    }
    // ${}代表从application.properties文件中获得配置数据的注解
    @Value("${knows.resource.path}")
    private File resourcePath;
    @Value("${knows.resource.host}")
    private String resourceHost;

    // 文件上传的方法
    @PostMapping("/upload/file")
    public String upload(MultipartFile imageFile) throws IOException {
        // 1.确定文件保存的路径
        //  会将不同日期的文件保存在不同的文件夹中,所以使用当前年月日组成文件夹名
        // path:2022/01/04
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                .format(LocalDateTime.now());
        // 确定并实例化要上传的文件夹路径对象
        //  F:/upload/2022/01/04
        File folder = new File(resourcePath, path);
        //创建文件夹
        folder.mkdirs();
        // 2.确定文件名
        // 获得原始文件名以截取文件后缀名
        String fileName = imageFile.getOriginalFilename();//原始文件名
        String ext = fileName.substring(fileName.lastIndexOf("."));
        // 创建随机新文件名以尽量减少文件名重复几率
        //   name:aidjvas-32ujd-91nbasj-ahdsfuhauasdf.jpg
        String name = UUID.randomUUID().toString()+ext;
        // 3.执行上传
        // 实例化要保存的文件对象(路径名+文件名的对象)
       File file = new File(folder,name);
        // 输出实际上传路径以方便测试观察
        log.debug("文件的上传路径名为:{}",file.getAbsolutePath());
        //执行上传
        imageFile.transferTo(file);
        //我们要实现回显必须得到上传的文件在静态资源服务器的路径
        //例如:http://localhost:8899/2022/03/17/aaaxadxxxxxx.jpg
        //         resourceHost    /     path   /      name
        // 我们拼接获得访问这个上传文件的url
        String url = resourceHost+"/"+path+"/"+name;
        log.debug("回显图片的url:{}",url);
        //返回结果 返回url以回显图片
        return url;
    }
}
