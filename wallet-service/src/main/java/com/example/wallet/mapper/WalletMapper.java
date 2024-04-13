package com.example.wallet.mapper;

import com.example.wallet.dto.WalletDto;
import com.example.wallet.model.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WalletMapper {

    public WalletDto toWalletDto(Wallet wallet) {
        return new WalletDto(wallet.getWalletId(), new BigDecimal(wallet.getAmount()));
    }
}
