package com.mamda.tp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permissions = new ArrayList<>();

    // @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    // @JsonIgnore
    // private List<TPUser> users = new ArrayList<>();
}