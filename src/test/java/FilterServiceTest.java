import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.backend.task.Bean.ContactBean;
import ua.backend.task.Config;
import ua.backend.task.DB.dao.ContactDao;
import ua.backend.task.DB.entity.ContactEntity;
import ua.backend.task.filter.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Config.class})
@EntityScan(basePackages = {"ua.backend.task.DB.entity"})
@EnableJpaRepositories(basePackages = {"ua.backend.task.DB.dao"})
@EnableTransactionManagement
public class FilterServiceTest {

    @Autowired
    private ContactDao dao;

    @Autowired
    private Filter filter;

    private static final List<String> names = Arrays.asList("Danette Abriola", "Clint Fortuno",
            "Annamaria Panitz", "Clifford Cordono", "Melani Macdonell", "Marita Grippen", "Farah Marcel",
            "Parthenia Ocegueda", "Jeromy Tilley", "Geraldo Cassette", "Clarita Pefferkorn",
            "Romeo Chastin", "Voncili Costilow");

    private static final List<String> RESULT = Arrays.asList("Clint Fortuno",
            "Clifford Cordono",
            "Voncili Costilow");

    @Before
    public void setUp() throws Exception {
        filter.setPattern("^.*[ae].*$");
        List<ContactEntity> contacts = new ArrayList<>();
        for (String name : names) {
            contacts.add(new ContactEntity(name));
        }
        dao.save(contacts);
    }

    @After
    public void tearDown() throws Exception {
        dao.deleteAll();
    }

    private boolean haveValue(List<String> source, String value) {
        for (String v : source) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testFilterAll() throws Exception {
        final ContactBean result = filter.filterAll();
        assertEquals(names.size(), result.getTotalCount());
        assertEquals(RESULT.size(), result.getContacts().size());
        for(ContactEntity contact : result.getContacts()) {
            assertTrue(haveValue(RESULT, contact.getName()));
        }
    }

    @Test
    public void testRun() throws Exception {
        final int currentPage = 0;
        final long totalCount = 13;
        final int totalPage = 3;
        final ContactBean result = filter.startFilter(currentPage, 5);
        assertNotNull(result);
        assertEquals(currentPage, (int) result.getCurrentPage());
        assertEquals(totalPage, (int)result.getTotalPage());
        assertEquals(totalCount, result.getTotalCount());
        for(ContactEntity contact : result.getContacts()) {
            assertTrue(haveValue(RESULT, contact.getName()));
        }
    }


}