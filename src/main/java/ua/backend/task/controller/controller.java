package ua.backend.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.backend.task.Bean.ContactBean;
import ua.backend.task.DB.dao.ContactDao;
import ua.backend.task.filter.Filter;

/**
 * Created by Slavik on 30.06.17.
 */
@RequestMapping("/hello")
@Controller
public class controller {
    private static final int MAX = 1000000;
    @Autowired
    @Qualifier("filterService")
    private Filter filter;

    @Autowired
    private ContactDao contactDao;


    @RequestMapping("/")
    public String index() {
        return "redirect:/hello";
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Example: /hello/contacts?nameFilter=^A.*";
    }

    @RequestMapping(value = "/contacts", params = "nameFilter")
    @ResponseBody
    public ContactBean contacts(@RequestParam("nameFilter") String nameFilter) {
        filter.setPattern(nameFilter);
        long totalCount = contactDao.count();
        if (totalCount <= MAX) {
            return filter.filterAll();
        } else {
            return filter.startFilter(0, MAX);
        }
    }

    @RequestMapping( value = "/contacts", params = {"nameFilter", "page"})
    @ResponseBody
    public ContactBean contacts(@RequestParam("nameFilter") String nameFilter,
                                 @RequestParam("page") int page,
                                 @RequestParam(value = "cnt", required = false, defaultValue = "1000000") int cntPerPage) {
        filter.setPattern(nameFilter);
        if (page < 0) {
            page = 0;
        }
        if (cntPerPage > MAX || cntPerPage < 1) {
            cntPerPage = MAX;
        }
        return filter.startFilter(page, cntPerPage);
    }
}
