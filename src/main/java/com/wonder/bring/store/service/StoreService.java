package com.wonder.bring.store.service;

import com.wonder.bring.store.api.dto.Store;
import com.wonder.bring.common.dto.DefaultResponse;

/**
 * Create by YoungEun on 2018-12-29.
 */

public interface StoreService {
    DefaultResponse<Store> getStoreInfo(final int storeIdx); //매장 상세 정보 조회
}
