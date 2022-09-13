package cn.guanjm.web;

import cn.guanjm.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    public PageController(){
    }

    @GetMapping("/article/{id}.html")
    public String toDetailPage(@PathVariable("id") Long id, Model model) {
        Map<String, Object> attributes = pageService.loadModel(id);
        model.addAllAttributes(attributes);
        return "detail";
    }


}
