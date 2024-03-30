package com.potential.hackathon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageResponse<T> {

    private T data;
    private PageInfo pageInfo;

    @Getter
    @Setter
    @Builder
    public static class PageInfo {
        private boolean isLast;
        private int pageNumber;
        private int pageSize;
        private int total;
    }
}
