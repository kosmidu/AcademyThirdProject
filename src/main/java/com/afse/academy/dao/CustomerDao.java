package com.afse.academy.dao;

import com.afse.academy.model.Customer;

public interface CustomerDao {
    void add(Customer customer);

    void update(Customer customerNew, Customer customerCurrent);

    void delete(Customer customer);
}