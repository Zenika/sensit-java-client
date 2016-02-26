package com.zenika.sensit.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Generic resource returned by the sensit api. All resources have some fields in common : 
 * <ul>
 *  <li>HATEOAS links</li>
 *  <li>number of results</li>
 *  <li>data (the actual resource or collection)</li>
 * </ul>
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class SensitResponse<T> {
    
    /**
     * Number of results (number of element in a collection/page, or 1 if a single resource is contained).
     */
    private Integer results;
    
    /**
     * HATEOS links pointing to first/last/previous/next page
     */
    private Links links;
    
    /**
     * The actual resource or collection.
     */
    private T data;
    
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Links {
        private String last;
        private String next;
        private String prev;
        private String first;
    }
}
