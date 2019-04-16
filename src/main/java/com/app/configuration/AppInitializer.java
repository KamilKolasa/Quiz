package com.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class AppInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppConfiguration.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // Polskie znaki
    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    //-------------

    // Przesyłanie/przechowywanie plików za pomocą Multipart
    private static final String LOCATION = "C:/mytemp"; //konfigurujemy miejsce gdzie multipart bedzie tymczasowo
    //przechowywal kolejne kawalki przesylanego przez nas pliku - TO NIE MUSI BYC KATALOG GDZIE CHCESZ PRZECHOWAC PLIK
    private static final long MAX_FILE_SIZE = 5242880; //ilosc bajtow ktore moze max jeden plik kiedy go przesylasz
    private static final long MAX_REQUEST_SIZE = 20971520; //max ilosc bajtow ktore moga miec wszystkie pliki przesylane w ramach
    //formularza
    private static final int FILE_SIZE_THRESHOLD = 0; //powyzej tej liczby bajtow plik bedzie przechowywany w czesciach
    //w LOCATION podczas przesylania


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(
                LOCATION,
                MAX_FILE_SIZE,
                MAX_REQUEST_SIZE,
                FILE_SIZE_THRESHOLD
        ));
    }
    //-------------
}
