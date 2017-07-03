package ua.backend.task.Bean;

import ua.backend.task.DB.entity.ContactEntity;

import java.util.List;

/**
 * Created by Slavik on 30.06.17.
 */
public class ContactBean{
    private List<ContactEntity> contacts;
    private long totalCount;
    private Integer currentPage;
    private Integer totalPage;

    public List<ContactEntity> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactEntity> contacts) {
        this.contacts = contacts;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}

