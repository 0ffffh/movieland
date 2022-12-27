//package com.movieland.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Getter
//@Setter
//@EqualsAndHashCode
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Table(name = "roles")
//public class Role implements GrantedAuthority {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Column(name = "name", nullable = false, unique = true, length = 50)
//    private String name;
//
//    public Role(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public String getAuthority() {
//        return name;
//    }
//}
