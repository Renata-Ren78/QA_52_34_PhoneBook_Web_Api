package data_providers;

import dto.ContactDto;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactDataProvider {

    @DataProvider
    public Iterator<ContactDto> dataProviderInvalidContactData() {
        List<ContactDto> list = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/test/resources/invalid_contact.csv"))) {

            String line = bufferedReader.readLine();

            while (line != null) {
                String[] splitLine = line.split(",", -1);

                list.add(ContactDto.builder()
                        .name(splitLine[0])
                        .lastName(splitLine[1])
                        .email(splitLine[2])
                        .phone(splitLine[3])
                        .address(splitLine[4])
                        .description(splitLine[5])
                        .build());

                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list.listIterator();
    }
}


