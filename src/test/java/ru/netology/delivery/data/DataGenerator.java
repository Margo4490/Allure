package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@Data
    @Getter

    public class DataGenerator {
        private DataGenerator() {
        }

        static Faker faker = new Faker(new Locale("ru-RU"));


        public static String generateDate(int days) {

            String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return date;
        }
        public static String[] cities = new String[]{
                "Брянск", "Вологда", "Екатеринбург", "Краснодар", "Майкоп",
                "Москва", "Санкт-Петербург", "Тверь", "Ульяновск"
        };

        public static String generateCity() {
            Random generator = new Random();
            int randomIndex = generator.nextInt(9);
            return cities[randomIndex];
        }

        public static String getName() {
            Faker faker = new Faker(new Locale("ru"));
            return faker.name().fullName().replaceAll("ё", "е");
        }

        public static String getNumberPhone() {
            Faker faker = new Faker(new Locale("ru"));
            String phoneNumber = faker.phoneNumber().phoneNumber();
            return phoneNumber;

        }

        public static RegistrationInfo getUser() {
            RegistrationInfo user = new RegistrationInfo(generateCity(), getName(), getNumberPhone());
            return user;
        }

        @Value
        public static class RegistrationInfo {
            String login;
            String password;
            String status;
        }
    }

