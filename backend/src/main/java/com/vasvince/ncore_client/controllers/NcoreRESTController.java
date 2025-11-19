package com.vasvince.ncore_client.controllers;

import com.vasvince.ncore_client.entities.FileInfoResponse;
import com.vasvince.ncore_client.services.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api")
public class NcoreRESTController {

    private static final Logger logger = LoggerFactory.getLogger(NcoreRESTController.class);

    private final FileService ncoreService;

    @Autowired
    public NcoreRESTController(final FileService ncoreService) {
        Objects.requireNonNull(ncoreService, "ncore service is null");
        this.ncoreService = ncoreService;
    }

    @GetMapping("listFiles")
    public List<FileInfoResponse> getAllFiles(HttpServletRequest request) throws IOException {
        String requestIp = getClientIp(request);
        logger.info("getAllFiles endpoint invoked from: {}", requestIp);
        return ncoreService.getAllFiles();
    }



    private String getClientIp(HttpServletRequest request) {
        //Proxy, load-balancer bypass
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
