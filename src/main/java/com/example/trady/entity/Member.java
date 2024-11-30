package com.example.trady.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Entity
public class Member {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(Member.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userid;

    private String username;

    private String password;

    private String email;

    private String phonenumber;

    private String addr;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Qna> qnaList;

    public Member(Long userid, String username, String password, String email, String phonenumber, String addr) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.addr = addr;
    }

    public void logInfo() {
        log.info("userid: {}, username: {}, password: {}, email: {}, phonenumber: {}, addr: {}", new Object[]{this.userid, this.username, this.password, this.email, this.phonenumber, this.addr});
    }

    @Generated
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Generated
    public void setUsername(String username) {
        this.username = username;
    }

    @Generated
    public void setPassword(String password) {
        this.password = password;
    }

    @Generated
    public void setEmail(String email) {
        this.email = email;
    }

    @Generated
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Generated
    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Generated
    public Long getUserid() {
        return this.userid;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public String getPhonenumber() {
        return this.phonenumber;
    }

    @Generated
    public String getAddr() {
        return this.addr;
    }

    @Generated
    public String toString() {
        Long var10000 = this.getUserid();
        return "Member(userid=" + var10000 + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ", email=" + this.getEmail() + ", phonenumber=" + this.getPhonenumber() + ", addr=" + this.getAddr() + ")";
    }

    @Generated
    public Member() {
    }
}
