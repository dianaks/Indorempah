package com.blibli.future.configuration;

import com.blibli.future.Model.Product;
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
        Product p = new Product();
        p.setName("Cabai Rawit");
        p.setCategory("Spice");
        p.setDescription("Cabai rawit atau cabai kathur, adalah buah dan tumbuhan anggota genus Capsicum. " +
                "Selain di Indonesia, ia juga tumbuh dan populer sebagai bumbu masakan " +
                "di negara-negara Asia Tenggara lainnya. ");
        p.setUnit("gram");
        p.setPrice(100000);
        p.setStatus("Available");
        p.setPicture("http://puriegarden.com/wp-content/uploads/2013/12/cabe-rawit.jpg");
        productRepo.save(p);

        Product x = new Product();
        x.setName("Daun Mint");
        x.setCategory("Herbs");
        x.setDescription("Daun mint sudah banyak dikenal karena sering digunakan dalam " +
                        "berbagai macam olahan makanan, seperti sup, permen, atau hanya sekedar pemanis hidangan." +
                "Selain untuk menambah rasa pada makanan, herbal dengan bau yang segar ini memiliki banyak manfaat. ");
        x.setUnit("gram");
        x.setPrice(100000);
        x.setStatus("Available");
        x.setPicture("https://vaidyamishra.com/blog/wp-content/uploads/2015/06/mint-leaves.jpg");
        productRepo.save(x);

        Product z = new Product();
        z.setName("Kayu Manis");
        z.setCategory("Herbs");
        z.setDescription("Kayu manis ialah sejenis pohon penghasil rempah-rempah. " +
                "Termasuk ke dalam jenis rempah-rempah yang amat beraroma, manis, dan pedas. " +
                "Orang biasa menggunakan rempah-rempah dalam makanan yang dibakar manis, anggur panas.");
        z.setUnit("gram");
        z.setPrice(100000);
        z.setStatus("Available");
        z.setPicture("http://supermetroemall.com/image/cache/data/Bumbu/Kayu_manis_batan_512850cd73853-500x500.jpg");
        productRepo.save(z);

    }

}