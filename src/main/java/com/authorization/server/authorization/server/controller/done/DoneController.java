package com.authorization.server.authorization.server.controller.done;


import com.authorization.server.authorization.server.dto.common.DoneBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class DoneController {

    @GetMapping("/done")
    public String showDoneMessage(@ModelAttribute DoneBean doneBean) {

        return "done";
    }
}
