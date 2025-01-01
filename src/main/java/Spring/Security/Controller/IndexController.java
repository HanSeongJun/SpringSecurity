package Spring.Security.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // localhost:8080
    // localhost:8080/
    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }
}
