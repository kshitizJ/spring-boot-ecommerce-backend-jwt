package backend.ecommerce.domain;

import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String productId;

    @Column(nullable = false)
    private String name;

    private String description;
    private String subDescription;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer rating;

    // @Column(nullable = false)
    private String productImage;

    @OneToMany(mappedBy = "id")
    private List<Comment> comments;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Kolkata")
    private LocalDateTime addedDate;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Kolkata")
    private LocalDateTime lastModified;

}
