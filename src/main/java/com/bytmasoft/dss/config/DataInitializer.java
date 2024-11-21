package com.bytmasoft.dss.config;



import com.bytmasoft.dss.dto.AddressCreateDTO;
import com.bytmasoft.dss.dto.AddressResponseDTO;
import com.bytmasoft.dss.dto.SchoolCreateDTO;
import com.bytmasoft.dss.service.AddressService;
import com.bytmasoft.dss.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {


    private final AppPropertiesConfig appPropertiesConfig;

    private final SchoolService schoolService;
private final AddressService addressService;

@Override
    public void run(String... args) throws Exception {
        if(appPropertiesConfig.isInitialize()){
            if(schoolService.countAll() == 0){

              /*AddressResponseDTO addressResponseDTO = addressService.add(AddressCreateDTO.builder()
                                                                                 .city("Weinheim")
                                                                                 .country("Deutschland")
                                                                                 .postalCode("69469")
                                                                                 .streetNumber("91")

                                           .build());*/

                schoolService.add(SchoolCreateDTO.builder()

                                          .addressCreateDTO(AddressCreateDTO.builder()
                                                                    .city("Weinheim")
                                                                    .country("Deutschland")
                                                                    .street("Konrad-adenauerstr")
                                                                    .postalCode("69469")
                                                                    .streetNumber("91")

                                                                    .build())
                                          .email("abakar61@web.de")
                                          .phone("123456789")
                                          .description("School for beginner")
                                          .name("Wadi Intermediate School")
                                          .website("www.google.de")
                                          .build());
                System.out.println("Initial data created successfully");
            }else {
                System.out.println("Data initialization flag is false, skipping initialization");
            }
        }
    }
}
