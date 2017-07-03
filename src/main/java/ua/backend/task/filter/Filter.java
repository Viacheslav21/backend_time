package ua.backend.task.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ua.backend.task.Bean.ContactBean;
import ua.backend.task.DB.dao.ContactDao;
import ua.backend.task.DB.entity.ContactEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Slavik on 30.06.17.
 */
@Component("filterService")
@Scope("prototype")
public class Filter {
    private List<ContactEntity> result = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Filter.class);

    @Autowired
    private ContactDao contactDao;

    private Pattern pattern;

    public void setPattern(String regex) {
        pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }

    private void filterIt(Iterator<ContactEntity> toFilter) {
        while(toFilter.hasNext()) {
            ContactEntity next = toFilter.next();
            if(!pattern.matcher(next.getName()).matches()) {
                result.add(next);
            }
        }
    }

    public ContactBean filterAll() {
        LOGGER.info("Start filtering for all data");
        List<ContactEntity> contacts = (List<ContactEntity>)contactDao.findAll();
        filterIt(contacts.iterator());
        ContactBean model = new ContactBean();
        model.setContacts(result);
        model.setTotalCount(contactDao.count());
        LOGGER.info("Filtering complete");
        return model;
    }

    public ContactBean startFilter(int startPage, int cntPerPage) {
        LOGGER.info("Start filtering");
        Page<ContactEntity> contacts = contactDao.findAll(new PageRequest(startPage, cntPerPage, Sort.Direction.ASC, "id"));
        filterIt(contacts.iterator());
        ContactBean model = new ContactBean();
        model.setContacts(result);
        model.setTotalCount(contactDao.count());
        model.setCurrentPage(startPage);
        model.setTotalPage(contacts.getTotalPages());
        LOGGER.info("Filtering complete");
        return model;
    }
}
