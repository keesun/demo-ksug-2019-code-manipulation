package me.whiteship;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
public class Location {

    @Id @GeneratedValue
    private Long id;

    private String name;
}
