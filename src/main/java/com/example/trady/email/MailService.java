package com.example.trady.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "jungb1203@naver.com";

    public void mailSend(MailDto mailDto) throws MessagingException {
        // MimeMessage -> HTML 메일을 보냄
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true는 멀티파트 메시지를 활성화

        helper.setFrom(MailService.FROM_ADDRESS);
        helper.setTo(mailDto.getAddress());
        helper.setSubject("[Trady] 주문 완료: " + mailDto.getTitle());

        String htmlContent = "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>주문이 완료되었습니다!</div>" +

                "<div class='product-details'>" +
                "<p><strong>상품명:</strong> " + mailDto.getTitle() + "</p>" +
                "<p><strong>사이즈:</strong> " + mailDto.getSize() + "</p>" +
                "<p><strong>가격:</strong> <span class='price'>" + mailDto.getPrice() + " 원</span></p>" +
                "</div>" +

                "<div class='order-summary'>" +
                "<p><strong>주문 정보:</strong></p>" +
                "<p>주문하신 상품은 <strong>" + mailDto.getTitle() + "</strong>입니다.</p>" +
                "<p><strong>가격:</strong> <span class='price'>" + mailDto.getPrice() + " 원</span></p>" +
                "<p><strong>배송지:</strong> " + mailDto.getEmail() + "</p>" +
                "<p><strong>이메일:</strong> " + mailDto.getAddress() + "</p>" +
                "</div>" +

                "<div class='footer'>" +
                "<p>감사합니다. Trady에서 쇼핑을 즐기세요!</p>" +
                "<p><a href='http://localhost:8080/products/all'>Trady 쇼핑몰</a></p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        helper.setText(htmlContent, true); // HTML 형식을 허용하는지 여부
        mailSender.send(message);
    }

}
