package com.example.wallet.service;

import com.example.wallet.dto.IncomingDataDto;
import com.example.wallet.dto.WalletDto;

import java.util.UUID;

public interface WalletService {
    WalletDto makeADeposit(IncomingDataDto incomingData);

    WalletDto getData(UUID walletId);
}
