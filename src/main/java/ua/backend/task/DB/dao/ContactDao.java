package ua.backend.task.DB.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.backend.task.DB.entity.ContactEntity;

/**
 * Created by Slavik on 30.06.17.
 */
@Repository("contactDao")
public interface ContactDao extends CrudRepository<ContactEntity, Long> {
    public Page<ContactEntity> findAll(Pageable pageable);
}
