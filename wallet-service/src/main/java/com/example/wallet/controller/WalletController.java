package com.example.wallet.controller;

import com.example.wallet.dto.IncomingDataDto;
import com.example.wallet.dto.WalletDto;
import com.example.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService service;

    @PostMapping("/wallet")
    public WalletDto makeADeposit(@Valid @RequestBody IncomingDataDto incomingDataDto) {
        return service.makeADeposit(incomingDataDto);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public WalletDto getData(@PathVariable UUID WALLET_UUID) {
        return service.getData(WALLET_UUID);
    }
}
