package com.wonder.bring.order.api;

import com.wonder.bring.config.security.jwt.JwtService;
import com.wonder.bring.order.api.dto.OrderRequest;
import com.wonder.bring.order.service.OrderService;
import com.wonder.bring.config.security.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wonder.bring.common.dto.DefaultResponse.FAIL_DEFAULT_RESPONSE;
import static com.wonder.bring.config.security.auth.AuthAspect.AUTHORIZATION;

@Slf4j
@RequestMapping("orders")
@RestController
public class OrderController {
    private final OrderService orderService;
    private final JwtService jwtService;

    public OrderController(final OrderService orderService, final JwtService jwtService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    //주문 생성하기
    @Auth
    @PostMapping("")
    public ResponseEntity createOrder(@RequestHeader(AUTHORIZATION) final String header, @RequestBody OrderRequest orderRequest) {
        try {
            int userIdx = jwtService.decode(header).getUserIdx();
            return new ResponseEntity<>(orderService.createOrder(userIdx, orderRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //개인의 전체 주문내역 조회
    @Auth
    @GetMapping("")
    public ResponseEntity getOrderList(@RequestHeader(AUTHORIZATION) final String header) {
        try {
            int userIdx = jwtService.decode(header).getUserIdx();
            return new ResponseEntity<>(orderService.getOrderList(userIdx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //주문상세메뉴 조회
    @Auth
    @GetMapping("/{orderIdx}")
    public ResponseEntity getOrderDetailList(@PathVariable(value = "orderIdx") final int orderIdx) {
        try {
            return new ResponseEntity<>(orderService.getOrderDetailList(orderIdx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


