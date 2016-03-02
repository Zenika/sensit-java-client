package com.zenika.sensit.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @NoArgsConstructor
    public static class Links {
        private String last;
        private Integer lastPageNum;
        private String next;
        private Integer nextPageNum;
        private String prev;
        private Integer prevPageNum;
        private String first;
        private Integer firstPageNum;
        
        private final Pattern pagePattern = Pattern.compile("page=([0-9]+)");
        
        public void setLast(String lastLink) {
            this.last = lastLink;
            Matcher m = pagePattern.matcher(lastLink);
            if(m.find()) {
                lastPageNum = Integer.parseInt(m.group(1));
            }
        }
        
        public void setNext(String nextLink) {
            this.next = nextLink;
            Matcher m = pagePattern.matcher(nextLink);
            if(m.find()) {
                nextPageNum = Integer.parseInt(m.group(1));
            }
        }
        
        public void setPrev(String prevLink) {
            this.prev = prevLink;
            Matcher m = pagePattern.matcher(prevLink);
            if(m.find()) {
                prevPageNum = Integer.parseInt(m.group(1));
            }
        }
        
        public void setFirst(String firstLink) {
            this.first = firstLink;
            Matcher m = pagePattern.matcher(firstLink);
            if(m.find()) {
                firstPageNum = Integer.parseInt(m.group(1));
            }
        }
    }
}
