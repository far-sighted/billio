package dev.billio.endpoint.dto;

import lombok.*;

public class AuthenticationDto {

    /**
     * Represents a request for user authentication containing username and password.
     * This class is used to encapsulate the authentication details required for login.
     */
    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationRequest {
        //region Fields
        private String username;
        private String password;
        //endregion
    }

    /**
     * Constructs an AuthenticationRequest with the provided username and password.
     */
    @Data
    @AllArgsConstructor
    public static class JwtResponseDTO {
        //region Fields
        private Token token;
        private RefreshToken refresh;
        //endregion Fields

        /**
         * Constructs a JwtResponseDTO with the provided token and refresh token.
         */
        @Data
        @AllArgsConstructor
        public static class Token {
            private String token;
            private Long expiresIn;
        }

        /**
         * Constructs a RefreshToken with the provided token and expiration time.
         */
        @Data
        @AllArgsConstructor
        public static class RefreshToken {
            private String token;
            private Long expiresIn;
        }

    }

}
