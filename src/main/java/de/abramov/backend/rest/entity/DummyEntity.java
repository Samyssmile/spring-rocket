package de.abramov.backend.rest.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Use this as your Entity Blueprint.
 */
@Entity
@Table
public class DummyEntity extends RepresentationModel<DummyEntity> implements IEntity {

    @Schema(description = "Unique identifier of the Dummy Element.", example = "1", required = false)
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private int id;

    @Schema(description = "Just an example number", example = "42", required = false)
    @Column
    private int dummyNumber;

    @Schema(
            description = "Lorem ipsum here",
            example = "Jon Snow the king in the north.",
            required = false)
    @Column(length = 300)
    private String dummyText;

    @Schema(description = "Phone number of the contact.", example = "62482211", required = false)
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number")
    @Size(max = 25)
    private String phone;

    @JsonCreator
    public DummyEntity(
            @JsonProperty("dummyNumber") int number,
            @JsonProperty("dummyText") String text,
            @JsonProperty("phone") String phone) {
        this.dummyNumber = number;
        this.dummyText = text;
        this.phone = phone;
    }

    public DummyEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDummyNumber() {
        return dummyNumber;
    }

    public void setDummyNumber(int dummyNumber) {
        this.dummyNumber = dummyNumber;
    }

    public String getDummyText() {
        return dummyText;
    }

    public void setDummyText(String dummyText) {
        this.dummyText = dummyText;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DummyEntity that = (DummyEntity) o;
        return id == that.id
                && dummyNumber == that.dummyNumber
                && Objects.equals(dummyText, that.dummyText)
                && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, dummyNumber, dummyText, phone);
    }

    @Override
    public String toString() {
        return "DummyEntity{" +
                "id=" + id +
                ", dummyNumber=" + dummyNumber +
                ", dummyText='" + dummyText + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
