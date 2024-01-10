package com.example.BedSync;

    public record AuthToken(String token) {

        // Getter

        public String getToken() {
            return token;
        }
    }