package com.example.trady.dto;

import com.example.trady.entity.Pcategory;
import com.example.trady.entity.Product;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class ProductForm {

    private Long id;
    private String pname;
    private MultipartFile pimg;
    private Long categoryId;
    private LocalDateTime pdate;
    private String formattedPrice;

    public Product toEntity(Pcategory pcategory, String filePath) {
        return new Product(id, pname, filePath, pcategory, pdate); // filePath를 추가
    }


    public void logInfo() {
        log.info("id: {}, pname: {},  pimg: {}, categoryId: {}", id, pname, pimg, categoryId);
    }

    public MultipartFile getPimg() {
        return pimg;
    }

    public void setPimg(MultipartFile pimg) {
        this.pimg = pimg;
    }

    // formattedPrice의 getter 및 setter 추가
    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }
}
