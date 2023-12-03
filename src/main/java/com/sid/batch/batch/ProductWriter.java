package com.sid.batch.batch;

import com.sid.batch.model.Product;
import com.sid.batch.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class ProductWriter  implements ItemWriter<Product> {
    @Autowired
    private ProductRepo productRepo;
    @Override
    public void write(Chunk<? extends Product> chunk) throws Exception {
       log.info("Writing:{}",chunk.getItems().size());
       productRepo.saveAll(chunk.getItems());
    }
}
