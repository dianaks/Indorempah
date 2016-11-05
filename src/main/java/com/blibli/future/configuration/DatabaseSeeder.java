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
        p.setUnit("kg");
        p.setPrice(54000);
        p.setStatus("Available");
        p.setPicture("http://puriegarden.com/wp-content/uploads/2013/12/cabe-rawit.jpg");
        productRepo.save(p);

        Product q = new Product();
        q.setName("Pala");
        q.setCategory("Other");
        q.setDescription("Pala (Myristica fragrans) merupakan tumbuhan berupa pohon " +
                "yang berasal dari kepulauan Banda, Maluku.");
        q.setUnit("kg");
        q.setPrice(100000);
        q.setStatus("Available");
        q.setPicture("http://palasari.co.id/wp-content/uploads/2015/01/buah-pala.jpg");
        productRepo.save(q);

        Product r = new Product();
        r.setName("Kunyit");
        r.setCategory("Other");
        r.setDescription("Kunyit asli. Kunyit atau kunir, adalah termasuk salah satu tanaman rempah-rempah dan " +
                "obat asli dari wilayah Asia Tenggara.");
        r.setUnit("kg");
        r.setPrice(1500);
        r.setStatus("Available");
        r.setPicture("https://muslimahzone.com/assets/2012/12/Turmeric.gif");
        productRepo.save(r);

        Product x = new Product();
        x.setName("Daun Mint");
        x.setCategory("Herbs");
        x.setDescription("Daun Mint Kering, Daun mint sudah banyak dikenal karena sering digunakan dalam " +
                        "berbagai macam olahan makanan, seperti sup, permen, atau hanya sekedar pemanis hidangan.");
        x.setUnit("kg");
        x.setPrice(30000);
        x.setStatus("Available");
        x.setPicture("http://kiosmakanansehat.com/wp-content/uploads/2016/08/daun-mint.jpg");
        productRepo.save(x);

        Product z = new Product();
        z.setName("Kayu Manis");
        z.setCategory("Herbs");
        z.setDescription("Kayu manis batang, Kayu manis ialah sejenis pohon penghasil rempah-rempah. " +
                "Termasuk ke dalam jenis rempah-rempah yang amat beraroma, manis, dan pedas.");
        z.setUnit("kg");
        z.setPrice(25000);
        z.setStatus("Available");
        z.setPicture("http://supermetroemall.com/image/cache/data/Bumbu/Kayu_manis_batan_512850cd73853-500x500.jpg");
        productRepo.save(z);

    }

}