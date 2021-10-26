package de.abramov.backend.rest.dto;

public class DummyDTO {

    private int id;
    private int dummyNumber;
    private String dummyText;
    private String phone;

    /**
     * C'Tor
     */
    public DummyDTO() {
        //Used By ModelMapper Struct
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
}
