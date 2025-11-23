// src/main/java/com/cinema/film/model/Salle.java
package com.cinema.film.model;

import jakarta.persistence.*;
import lombok.Data;

/*Un constructeur vide est obligatoire dans une entité JPA parce que Hibernate/JPA crée les objets automatiquement par réflexion lors du chargement depuis la base. Pour pouvoir instancier la classe sans connaître ses paramètres, le framework a besoin d’un constructeur
public ou protégé sans argument. Sans lui, l'entité ne peut pas être créée.*/
@Entity
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private Integer capacite; // nombre de places

    private String equipements; // "3D, 4DX, IMAX"

    public Salle() {
    };

    public Salle(Long id, String nom, Integer capacite, String equipements) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.equipements = equipements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getEquipements() {
        return equipements;
    }

    public void setEquipements(String equipements) {
        this.equipements = equipements;
    }


}