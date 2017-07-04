package ua.backend.task.DB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.backend.task.DB.dao.ContactDao;
import ua.backend.task.DB.entity.ContactEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Slavik on 30.06.17.
 */
@Component
public class DbService {
    private int times = 200; //iteration
    private int countPerTime = 5000; // contacts must be saved by iteration
    private static final Logger LOGGER = LoggerFactory.getLogger(DbService.class);

    @Autowired
    private ContactDao contactDao;

    public DbService() {}

    public void create() {
        // check DB
        if (contactDao.count() == 0) {
            createDB();
        }
    }



    public void setCountPerTime(int countPerTime) {
        this.countPerTime = countPerTime;
    }

    public void setTimes(int times) {
        this.times = times;
    }


    private void createDB() {
        LOGGER.info("Creating table");
        List<String> firstNames = getFile("first_name.csv");
        List<String> lastNames = getFile("second_name.csv");
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        List<ContactEntity> contacts = new ArrayList<>();
        for (int j = 0; j < times; j++) {
            for (int i = 0; i < countPerTime; i++) {
                //generate random name and put to array
                sb.append(firstNames.get(rnd.nextInt(firstNames.size())))
                        .append(lastNames.get(rnd.nextInt(lastNames.size())));
                contacts.add(new ContactEntity(sb.toString()));
                sb.setLength(0);
            }
            contactDao.save(contacts);
            contacts.clear();
            LOGGER.info("Creating part " + j + " complete");
        }
        LOGGER.info("complete");
    }

    private List<String> getFile(String fileName) {
        List<String> result = new ArrayList<>();
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}
