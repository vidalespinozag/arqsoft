package com.acme.banking.platform.shared.interfaces.rest;

import lombok.Value;

@Value
public class PaginationMetadata {
    private final long totalItemCount;
    private final long totalPageCount;
    private final long page;
    private final long limit;

    public PaginationMetadata(long totalItemCount, long page, long limit)
    {
        this.totalItemCount = totalItemCount;
        this.page = page;
        this.limit = limit;
        totalPageCount = (long)Math.ceil(totalItemCount / (double)limit);
    }
}
