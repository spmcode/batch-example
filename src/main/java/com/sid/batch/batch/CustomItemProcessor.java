package com.sid.batch.batch;

import com.sid.batch.model.Product;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product item) throws Exception {
        //Logic that we need for example if we want to create something new from data
        //Discounted price
        double dPer=Double.parseDouble(item.getDiscount());
        double oPrice=Double.parseDouble(item.getPrice());
        double dPrice=(dPer/100)*oPrice;
        double finalP=oPrice-dPrice;
        item.setDiscountedPrice(String.valueOf(finalP));
        return item;
    }
}
