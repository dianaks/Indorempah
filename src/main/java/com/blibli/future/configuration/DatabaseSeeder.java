package com.blibli.future.configuration;

import com.blibli.future.Model.Product;
import com.blibli.future.Model.User;
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
    @Autowired
    UserRepository userRepo;

    @PostConstruct // jalanin fungsi ini saat pertama kali spring dijalankan
    private void mockupData() {
        Product o = new Product();
        o.setName("Lengkuas");
        o.setCategory("Other");
        o.setDescription("Lengkuas, laos atau kelawas merupakan jenis tumbuhan umbi-umbian" +
                " yang bisa hidup di daerah dataran tinggi maupun dataran rendah. ");
        o.setUnit("kg");
        o.setPrice(70000);
        o.setStatus("Available");
        o.setPicture("http://www.sekedarinfo.com/wp-content/uploads/2015/09/Manfaat-Lengkuas-atau-Laos-Untuk-pengobatan.jpg");
        o.setAmount("10");
        productRepo.save(o);

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
        p.setAmount("10");
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
        q.setAmount("10");
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
        r.setAmount("10");
        productRepo.save(r);

        Product s = new Product();
        s.setName("Lidah Buaya");
        s.setCategory("Herbs");
        s.setDescription("Lidah buaya adalah sejenis tumbuhan yang sudah dikenal sejak " +
                "ribuan tahun silam dan digunakan sebagai penyubur rambut, penyembuh luka, dan untuk perawatan kulit.");
        s.setUnit("kg");
        s.setPrice(2000);
        s.setStatus("Available");
        s.setPicture("http://www.tokomesin.com/wp-content/uploads/2015/04/lidah-buaya-tokomesin.jpg?x52897");
        s.setAmount("10");
        productRepo.save(s);

        Product t = new Product();
        t.setName("Kemangi");
        t.setCategory("Herbs");
        t.setDescription("Kemangi adalah terna kecil yang daunnya biasa dimakan sebagai lalap. " +
                "Aroma daunnya khas, kuat namun lembut dengan sentuhan aroma limau");
        t.setUnit("sack");
        t.setPrice(25000);
        t.setStatus("Available");
        t.setPicture("http://halosehat.com/wp-content/uploads/2015/10/daun-kemangi.jpg");
        t.setAmount("10");
        productRepo.save(t);

        Product u = new Product();
        u.setName("Cabai Keriting");
        u.setCategory("Spice");
        u.setDescription("Cabai keriting adalah buah dari salah satu jenis tanaman cabai-cabaian " +
                "yang berbentuk panjang dan ramping namun memiliki tekstur keriting. ");
        u.setUnit("kg");
        u.setPrice(70000);
        u.setStatus("Available");
        u.setPicture("http://www.anneahira.com/images/cabe-merah-keriting.jpg");
        u.setAmount("10");
        productRepo.save(u);

        Product v = new Product();
        v.setName("Lada Putih");
        v.setCategory("Spice");
        v.setDescription("Lada, disebut juga Merica/Sahang, yang mempunyai nama Latin Piper " +
                "Albi Linn adalah sebuah tanaman yang kaya akan kandungan kimia, " +
                "seperti minyak lada, minyak lemak, juga pati.");

        v.setUnit("kg");
        v.setPrice(170000);
        v.setStatus("Available");
        v.setPicture("https://s4.bukalapak.com/img/900868492/medium/213032_d6caed99_5385_42bc_9dba_748a00de935e.jpg");
        v.setAmount("10");
        productRepo.save(v);

        Product w = new Product();
        w.setName("Jahe");
        w.setCategory("Other");
        w.setDescription(" Jahe, adalah tanaman rimpang yang sangat populer sebagai rempah-rempah dan bahan obat.");

        w.setUnit("kg");
        w.setPrice(20000);
        w.setStatus("Available");
        w.setPicture("http://obatherbalibuhamil.com/wp-content/uploads/2015/07/Manfaat-Dan-Khasiat-Jahe-Bagi-Kesehatan-Ibu-Hamil1.jpg");
        w.setAmount("10");
        productRepo.save(w);

        Product x = new Product();
        x.setName("Daun Mint");
        x.setCategory("Herbs");
        x.setDescription("Daun Mint Kering, Daun mint sudah banyak dikenal karena sering digunakan dalam " +
                        "berbagai macam olahan makanan, seperti sup, permen, atau hanya sekedar pemanis hidangan.");
        x.setUnit("kg");
        x.setPrice(30000);
        x.setStatus("Available");
        x.setPicture("http://kiosmakanansehat.com/wp-content/uploads/2016/08/daun-mint.jpg");
        x.setAmount("10");
        productRepo.save(x);

        Product y = new Product();
        y.setName("Lada Hitam");
        y.setCategory("Spice");
        y.setDescription("Lada, disebut juga Merica/Sahang, yang mempunyai nama Latin Piper " +
                "Albi Linn adalah sebuah tanaman yang kaya akan kandungan kimia, " +
                "seperti minyak lada, minyak lemak, juga pati. ");
        y.setUnit("kg");
        y.setPrice(95000);
        y.setStatus("Available");
        y.setPicture("http://images.solopos.com/2015/05/Lada-hitam.jpg");
        y.setAmount("10");
        productRepo.save(y);

        Product z = new Product();
        z.setName("Kayu Manis");
        z.setCategory("Herbs");
        z.setDescription("Kayu manis batang, Kayu manis ialah sejenis pohon penghasil rempah-rempah. " +
                "Termasuk ke dalam jenis rempah-rempah yang amat beraroma, manis, dan pedas.");
        z.setUnit("kg");
        z.setPrice(25000);
        z.setStatus("Available");
        z.setPicture("http://supermetroemall.com/image/cache/data/Bumbu/Kayu_manis_batan_512850cd73853-500x500.jpg");
        z.setAmount("10");
        productRepo.save(z);

        User dummy = new User();
        dummy.setUsername("dummy");
        dummy.setEmail("dummy@email.com");
        dummy.setPassword("dummy");
        userRepo.save(dummy);

    }

}