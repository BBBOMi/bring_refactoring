package com.wonder.bring.map.service.implement;

import com.wonder.bring.map.api.dto.Point;
import com.wonder.bring.store.api.dto.StorePreview;
import com.wonder.bring.map.mapper.MapMapper;
import com.wonder.bring.common.dto.DefaultResponse;
import com.wonder.bring.map.service.MapService;
import com.wonder.bring.common.utils.Message;
import com.wonder.bring.common.utils.Status;
import com.wonder.bring.store.mapper.StoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by bomi on 2019-01-02.
 */

@Slf4j
@Service
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    private final StoreMapper storeMapper;

    public MapServiceImpl(final MapMapper mapMapper, final StoreMapper storeMapper) {
        this.mapMapper = mapMapper;
        this.storeMapper = storeMapper;
    }

    /**
     * 주위의 매장 위도 경도 좌표 조회
     * @param latitude
     *      지금 현재 나의 위도
     * @param longitude
     *      지금 현재 나의 경도
     * @return 1km 이내의 매장 위치 좌표
     */
   @Override
    public DefaultResponse getStores(final Optional<Double> latitude, final Optional<Double> longitude) {
       // 파라미터가 존재하면
       if(latitude.isPresent() && longitude.isPresent()) {
           // 빈 값이 아니라면
           if(!latitude.get().equals("") && !longitude.get().equals("")) {
               List<Point> list = mapMapper.findStorePointsByLongitudeAndLatitude(longitude.get(), latitude.get());
               if(list.isEmpty()) {
                   return DefaultResponse.of(Status.OK, Message.FIND_STORE_SUCCESS);
               }
               return DefaultResponse.of(Status.OK, Message.FIND_POINT_SUCCESS, list);
               // 빈 값이면
           } else {
               return DefaultResponse.of(Status.BAD_REQUEST, Message.NOT_GET_MY_POINT);
           }
       }
       // 존재하지 않으면
       return DefaultResponse.of(Status.BAD_REQUEST, Message.NOT_GET_MY_POINT);
   }

    /**
     * 선택한 매장의 정보 조회
     * @param storeIdx
     *      선택한 매장의 고유 idx
     * @return 결과 데이터
     */
    @Override
    public DefaultResponse getStoreInfo(final int storeIdx) {
        StorePreview store = storeMapper.findStoreInfoByStoreIdx(storeIdx);

        if(store == null) {
            return DefaultResponse.of(Status.NOT_FOUND, Message.FIND_STORE_FAIL);
        } else {
            store.setStorePhotoUrls(storeMapper.findStorePhotosByStoreIdx(storeIdx));
            return DefaultResponse.of(Status.OK, Message.FIND_STORE_SUCCESS, store);
        }
    }
}
