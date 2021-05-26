package com.mamda.tp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
/**
 *
 */
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private TPUser user;
    @Column(nullable = false,unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;

    public RefreshToken(TPUser user,String token,Instant expiryDate){
        this.user=user; this.token=token; this.expiryDate=expiryDate;
    }
}
