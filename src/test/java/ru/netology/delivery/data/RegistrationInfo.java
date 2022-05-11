package ru.netology.delivery.data;

import lombok.Data;


    @Data
    public class RegistrationInfo {
        public RegistrationInfo() {
             String city;
             String name;
             String phone;

        }
    }
