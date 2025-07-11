package dev.billio.endpoint.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JsonWebTokenizer {

    //region Constants
    private final Long ACCESS_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000L; // 24 hours
    private final Long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L; // 7 days
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Securely generated secret key for signing JWTs
    //endregion Constants

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided resolver function.
     *
     * @param token    the JWT token
     * @param resolver a function to resolve the claim from the Claims object
     * @param <T>      the type of the claim to be extracted
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    /**
     * Generates a JWT token for the given user details with default claims and expiration time.
     *
     * @param userDetails the user details for which the token is generated
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()), userDetails);
    }

    /**
     * Generates a JWT token with specified claims for the given user details.
     *
     * @param claims      additional claims to include in the token
     * @param userDetails the user details for which the token is generated
     * @return the generated JWT token
     */
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, ACCESS_TOKEN_EXPIRATION);
    }

    /**
     * Generates a refresh token for the given user details with the default expiration time.
     *
     * @param userDetails the user details for which the refresh token is generated
     * @return the generated refresh token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(
                Map.of("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()),
                userDetails,
                REFRESH_TOKEN_EXPIRATION
        );
    }

    /**
     * Builds a JWT token with the specified claims, user details, and expiration time.
     *
     * @param claims      additional claims to include in the token
     * @param userDetails the user details for which the token is generated
     * @param expiration  the expiration time in milliseconds
     * @return the generated JWT token
     */
    private String buildToken(Map<String, Object> claims, UserDetails userDetails, Long expiration) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the JWT token against the provided user details.
     *
     * @param token       the JWT token to validate
     * @param userDetails the user details to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims contained in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the expiration time for the refresh token.
     *
     * @return the expiration time in milliseconds
     */
    public Long getExpirationTime() {
        return this.ACCESS_TOKEN_EXPIRATION; // Returns the same expiration time as for the regular token
    }

    /**
     * Retrieves the expiration time for the refresh token.
     *
     * @return the expiration time in milliseconds
     */
    public Long getRefreshExpirationTime() {
        return this.REFRESH_TOKEN_EXPIRATION; // Returns the expiration time for the refresh token
    }
    
}