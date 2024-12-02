package com.example.trady.dto;

import com.example.trady.entity.Pcategory;
import com.example.trady.entity.Product;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class PcategoryForm {

    private Long id;
    private String pname;

    public Pcategory toEntity(){
        return new Pcategory(id,pname);
    }
    public void logInfo() {
        log.info("id: {}, pname: {}", id, pname);
    }
}
