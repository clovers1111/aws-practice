package clovers1111.awsweek2practice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Builder
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long orderId;

    @Column
    private String key;

    @Column
    private String bucket;

    @Column
    private String description;

    public Order() {

    }


    public void setId(Long id) {
        this.orderId = id;
    }

    public Long getId() {
        return orderId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
