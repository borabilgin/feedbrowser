package com.demo.imagebrowser.controller;

import com.demo.imagebrowser.domain.FeedCategory;
import com.demo.imagebrowser.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure/category")
public class FeedCategoryController {

    @Autowired
    private FeedService feedService;

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeedCategory> addCategory(@RequestParam() String categoryName) {
        FeedCategory category = feedService.addCategory(categoryName);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
