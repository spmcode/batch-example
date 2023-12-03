package com.sid.batch.batch;

import com.sid.batch.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;


@Slf4j
public class CustomProductProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product item) throws Exception {

        log.info("Process At Second Process {}",item);
        item.setDescription(item.getDescription()+" Has Been Process");
        return item;
    }
}
