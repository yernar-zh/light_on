package com.example.light_on.filter;

import com.example.light_on.models.Country;
import com.example.light_on.service.CountryService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CountryResolverRequestFilter extends OncePerRequestFilter {

    private final CountryService countryService;

    public CountryResolverRequestFilter(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
//        String ipAddress = request.getRemoteAddr();
        String ipAddress = "194.187.246.166";
        Country country = countryService.findOrCreateByIp(ipAddress);
        request.setAttribute("CURRENT_COUNTRY_ATTRIBUTE", country);
        filterChain.doFilter(request, response);
    }

}