package com.blibli.future.configuration;

import com.blibli.future.repository.ProductRepository;
import com.blibli.future.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Nita on 16/10/2016.
 */
@Component
public class DatabaseSeeder {
    @Autowired
    ProductRepository productRepo;

    @PostConstruct // jalanin fungsi ini saat pertama kali spring dijalankan
    private void mockupData() {


    }
}