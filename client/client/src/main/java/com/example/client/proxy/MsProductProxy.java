package com.example.client.proxy;


import com.example.client.bean.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "product", url = "localhost:8091")
public interface MsProductProxy {
    @GetMapping(value = "/products")
    public List<ProductBean> list();
    @GetMapping(value = "/product/{id}")
    public Optional<ProductBean> get(@PathVariable Long id);
}
