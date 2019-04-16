package com.app.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Level {
    @Id
    @GeneratedValue
    private Long id;
    private LevelPoint nameLevel;
    @OneToMany(/*cascade = CascadeType.PERSIST, */mappedBy = "level")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Question> questions = new HashSet<>();
}
