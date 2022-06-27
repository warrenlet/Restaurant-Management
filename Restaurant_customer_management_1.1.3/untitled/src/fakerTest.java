import com.github.javafaker.Faker;

import java.util.Locale;

public class fakerTest {


    public static void main(String[] args) {
        Faker faker;
        faker = new Faker(new Locale("US"));


        //generate food data to past into database menu query
//        for(int i = 0; i < 20; i ++){
//            System.out.println("(" + "\"" + faker.food().dish() + "\","  + faker.number().randomDouble(2,5,30) + "),");
//        }


        for(int i = 0; i < 5; i ++){
            System.out.println(faker.name().firstName() + " " + faker.name().lastName() + " " + faker.phoneNumber().cellPhone());
        }
    }
}
