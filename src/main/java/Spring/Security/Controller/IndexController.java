package Spring.Security.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    // localhost:8080
    // localhost:8080/
    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // SecurityConfig파일 작성 전 : 스프링 시큐리티가 자동적으로 낚아챔
    // SecurityConfig파일 작성 후 : 스프링 시큐리티가 낚아채지 않음
    @GetMapping("/login")
    public @ResponseBody String login() {
        return "login";
    }

    @GetMapping("/join")
    public @ResponseBody String join() {
        return "join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody  String joinProc() {
        return "회원가입 완료됨!";
    }
}
