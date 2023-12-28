package com.task.Vodafone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    //Whenever a user is called go immediately fetch his Role
    //Cascade if something happens to the user associate the action with his role too
    @JoinTable(//indicated that it's the owning side of an M2M
            name="users_roles", // name of the join table used to handle M2M
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            //Put Value "ID" of User Entity into "USER_ID" column of the join table "user_roles"
            //joincolum use data in t1
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
            //Put value "ID" of Role Entity into "ROLE_ID" column of the join table "user_roles"
            //inversecolumn use data from t2
    private List<Role> roles = new ArrayList<>();
    //Create a list of roles that can be associated to the Users

}
