package com.example.light_on.service;

import com.example.light_on.dto.CountryDto;
import com.example.light_on.models.Country;
import com.example.light_on.models.Room;
import com.example.light_on.repository.CountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    private CountryDto findByIpAddress(String ipAddress) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.ipstack.com/" + ipAddress + "?access_key=db08fa7d9e47bb6b7467fb5f9079fd45";
        ResponseEntity<CountryDto> response = restTemplate.getForEntity(url, CountryDto.class);
        return response.getBody();
    }


    public Country findOrCreateByIp(String ipAddress) {
        CountryDto countryDto = findByIpAddress(ipAddress);
        Country country = countryRepository.findByCode(countryDto.getCountryCode()).orElseGet(() -> {
            Country tmp = new Country();
            tmp.setCode(countryDto.getCountryCode());
            return tmp;
        });

        country.setName(countryDto.getCountryName());
        countryRepository.save(country);

        return country;
    }

    public List<Country> findAll() {
        return countryRepository.findAll();
    }
}
