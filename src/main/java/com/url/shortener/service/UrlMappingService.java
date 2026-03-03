package com.url.shortener.service;

import com.url.shortener.dtos.UrlMappingDTO;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.repository.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
@Service
public class UrlMappingService {

    private UrlMappingRepository urlMappingRepository;

    public UrlMappingDTO createShortUrl(String originalUrl, User user){
        String shortUrl=generateShortUrl();
        UrlMapping urlMapping=new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        UrlMapping savedUrlMapping =urlMappingRepository.save(urlMapping);
        return convertToDto(savedUrlMapping);
    }
    public UrlMappingDTO convertToDto(UrlMapping urlMapping){
        UrlMappingDTO urlMappingDTO=new UrlMappingDTO();
        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setUsername(urlMapping.getUser().getUsername());
        urlMappingDTO.setCreatedDate(urlMapping.getCreatedDate());
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setClickCount(urlMapping.getClickCount());

        return urlMappingDTO;
    }

    public String generateShortUrl(){
        String chars="asdfghjklqwertyuiopzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random =new Random();
        StringBuilder shortUrl=new StringBuilder(8);

        for(int i=0;i<8;i++){
            shortUrl.append(chars.charAt(random.nextInt(chars.length())));
        }
        return shortUrl.toString();
    }
}
