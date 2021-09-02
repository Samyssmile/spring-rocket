package de.abramov.backend.rest.dto;

import java.util.Objects;

/**
 * Transport a JWT
 */
public class TokenDTO {

    private String jsonWebToken;

    public TokenDTO(String jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    public String getJsonWebToken() {
        return jsonWebToken;
    }

    public void setJsonWebToken(String jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenDTO tokenDTO = (TokenDTO) o;
        return Objects.equals(jsonWebToken, tokenDTO.jsonWebToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonWebToken);
    }
}
